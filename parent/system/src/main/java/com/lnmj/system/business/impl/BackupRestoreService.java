package com.lnmj.system.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.ImageTypeEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeBackupRestoreEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.FtpFileUtil;
import com.lnmj.system.business.IBackupRestoreService;
import com.lnmj.system.entity.BackupRestore;
import com.lnmj.system.repository.IBackupRestoreDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/6/4 15:29
 * @Description: 备份还原Service
 */
@Service("backupRestoreService")
public class BackupRestoreService implements IBackupRestoreService {
    @Resource
    private IBackupRestoreDao backupRestoreDao;
    @Resource
    FtpFileUtil ftpFileUtil;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.user.host}")
    private String host;
    @Value("${spring.datasource.user.port}")
    private String port;
    
    /**
    *@Description 查看备份还原记录
    *@Param [backupRestore]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/6
    *@Time 14:50
    */
    @Override
    public ResponseResult selectBackupRestoreList(int pageNum,int pageSize,BackupRestore backupRestore) {
        PageHelper.startPage(pageNum,pageSize);
        List<BackupRestore> backupRestoreList = backupRestoreDao.selectBackupRestoreList(backupRestore);
        PageInfo<BackupRestore> pageInfo = new PageInfo(backupRestoreList);
        return ResponseResult.success(pageInfo);
    }

    @Override
    public ResponseResult showSchema() {
        return ResponseResult.success(backupRestoreDao.showSchema());
    }

    @Override
    public ResponseResult showTable(String schema) {
        //切换到目标数据库
        backupRestoreDao.useSchema(schema);
        List<String> strings = backupRestoreDao.showTable();
        //切换回系统数据库
        backupRestoreDao.useSchema("lnmj_system");
        return ResponseResult.success(strings);
    }

    /**
    *@Description 备份
    *@Param [tableSchema, tableName, createOperator]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/6
    *@Time 14:50
    */
    @Override
    public ResponseResult backup(String tableSchema, String tableName,String createOperator) {
        if(StringUtils.isBlank(tableSchema)&& !StringUtils.isBlank(tableName)){
            return ResponseResult.error(new Error(ResponseCodeBackupRestoreEnum.FALID_BACKUP.getCode(),
                    ResponseCodeBackupRestoreEnum.FALID_BACKUP.getDesc()+"：需传入数据库"));
        }
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        //备份文件
        Map map = backupMethod(tableSchema, tableName);
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        Boolean flag = (Boolean)map.get("flag");
        if(!flag){
            return ResponseResult.error(new Error(ResponseCodeBackupRestoreEnum.FALID_BACKUP.getCode(),
                    ResponseCodeBackupRestoreEnum.FALID_BACKUP.getDesc()));
        }
        if(StringUtils.isBlank(tableName) && StringUtils.isBlank(tableSchema)){
            tableName="all";
            tableSchema="all";
        }
        if(StringUtils.isBlank(tableName)){
            tableName="all";
        }
        //备份文件大小
        Long fileSize = (Long)map.get("fileSize");
        //备份文件路径
        String fileUrl = (String)map.get("url");
        //操作写入数据库
        BackupRestore backupRestore = new BackupRestore();
        backupRestore.setFileUrl(fileUrl);
        backupRestore.setStartTime(startTime);
        backupRestore.setEndTime(endTime);
        backupRestore.setFileSize(readableFileSize(fileSize));
        backupRestore.setContent(tableSchema+"."+tableName);
        backupRestore.setOperation("备份");
        backupRestore.setCreateOperator(createOperator);
        backupRestore.setModifyOperator(createOperator);
        backupRestoreDao.insertBackupRestore(backupRestore);
        return ResponseResult.success(backupRestore);
    }
    
    /**
    *@Description 还原
    *@Param [tableSchema, fileUrl, createOperator]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/6
    *@Time 14:50
    */
    @Override
    public ResponseResult restore(String tableSchema, String fileUrl, String createOperator) {
        //查询是否有备份文件
        BackupRestore backupRestoreTemp = new BackupRestore();
        backupRestoreTemp.setFileUrl(fileUrl);
        backupRestoreTemp.setOperation("备份");
        List<BackupRestore> backupRestoreList = backupRestoreDao.selectBackupRestoreList(backupRestoreTemp);
        if(backupRestoreList==null||backupRestoreList.size()==0){
            return ResponseResult.error(new Error(ResponseCodeBackupRestoreEnum.NOT_BACKUP_FILE.getCode(),
                    ResponseCodeBackupRestoreEnum.NOT_BACKUP_FILE.getDesc()+fileUrl));
        }
        //备份数据库作为还原的数据库和表
        String content = backupRestoreList.get(0).getContent();
        String database = "";
        if(content.contains(".")){
            database = StringUtils.split(content,"\\.")[0];
            String table = StringUtils.split(content,"\\.")[1];
            if(!("all".equals(table))){ //如果备份的数据是表，则使用传入的数据库
                content=tableSchema+"."+table;
            }else if("all".equals(table) && "all".equals(database)){  //备份的是所有数据库
                tableSchema = database; //将数据库置all
            }else{ //如果备份的数据是数据库，需要使用原来的数据库
                tableSchema = database;
            }
        }
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        //还原文件
        Map map = restoreMethod(tableSchema, fileUrl);
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        Boolean flag = (Boolean)map.get("flag");
        if(!flag){
            return ResponseResult.error(new Error(ResponseCodeBackupRestoreEnum.FALID_RESTORE.getCode(),
                    ResponseCodeBackupRestoreEnum.FALID_RESTORE.getDesc()));
        }
        //还原文件大小
        Long fileSize = (Long)map.get("fileSize");
        //备份文件路径
        //操作写入数据库
        BackupRestore backupRestore = new BackupRestore();
        backupRestore.setFileUrl(fileUrl);
        backupRestore.setStartTime(startTime);
        backupRestore.setEndTime(endTime);
        backupRestore.setFileSize(readableFileSize(fileSize));
        backupRestore.setContent(content);
        backupRestore.setOperation("还原");
        backupRestore.setCreateOperator(createOperator);
        backupRestore.setModifyOperator(createOperator);
        backupRestoreDao.insertBackupRestore(backupRestore);
        return ResponseResult.success(backupRestore);
    }

    /**
    *@Description 还原方法
    *@Param [tableSchema, fileUrl]
    *@Return java.util.Map
    *@Author yilihua
    *@Date 2019/6/5
    *@Time 10:24
    */
    private Map restoreMethod(String tableSchema,String fileUrl){
        Map map = new HashMap();
        map.put("flag",false);
        try {
            //从ftp下载文件
            String suffix = System.currentTimeMillis()+".sql";
            File file = File.createTempFile("temp",suffix);
            String fileName = file.getPath();
            OutputStream out  = new FileOutputStream(file);
            map = ftpFileUtil.downlFile(fileUrl, out);
            //获取数据库路径
            String mysqlPath = getMysqlPath();
            String stmt1 = mysqlPath + "\\mysqladmin " + " -h" + host + " -P" + port +
                    " -u" + username + " -p" + password +
                    " create "  + tableSchema;
            String stmt2 = "mysql -h" + host + " -P" + port +
                    " -u" + username + " -p" + password +
                    " "+tableSchema+" < " + fileName;
            String[] cmd = { "cmd", "/c", stmt2 };
            Process exec = Runtime.getRuntime().exec(stmt1);
            int i = exec.waitFor(); //存在数据库则i=1
            if(i != 0 ){   //等于0证明线程正常执行
                map.put("createDatabase","数据库已存在");
            }
            Process exec1 = Runtime.getRuntime().exec(cmd);
            int j = exec1.waitFor();    //j必须等于0
            if(j != 0){   //等于0证明线程正常执行
                map.put("flag",false);
            }
            //删除创建的文件
            file.deleteOnExit();
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
    }
    /**
    *@Description 备份方法(可以备份数据库，可以备份数据库的表，可以全部备份)
    *@Param [tableSchema, tableName]
    *@Return java.util.Map
    *@Author yilihua
    *@Date 2019/6/4
    *@Time 17:47
    */
    private Map backupMethod(String tableSchema, String tableName){
        Map map = new HashMap();
        map.put("flag",false);
        try {
            //备份文件名(备份文件暂存在临时文件路径下)
            String fileName = System.getProperty("java.io.tmpdir")+System.currentTimeMillis()+".sql";
            //读取数据库安装路径
            String mysqlPath = getMysqlPath();
            Runtime rt = Runtime.getRuntime();
            // 调用mysql的安装目录的命令
            //mysqldump -h192.168.0.10 -P3306 -uroot -p123456 --databases lnmj_system --tables s_configuration > a.sql
            String shell = "";
            if(!StringUtils.isBlank(tableName) && !StringUtils.isBlank(tableSchema)) {
                shell = mysqlPath + "\\mysqldump " + " -h" + host + " -P" + port +
                        " -u" + username + " -p" + password +
                        " --lock-all-tables=true" + " --result-file=" + fileName +
                        " --default-character-set=utf8" +
                        " --databases " + tableSchema + " --tables " + tableName;
            }else if(StringUtils.isBlank(tableName) && !StringUtils.isBlank(tableSchema)){
                shell = mysqlPath + "\\mysqldump " + " -h" + host + " -P" + port +
                        " -u" + username + " -p" + password +
                        " --lock-all-tables=true" + " --result-file=" + fileName +
                        " --default-character-set=utf8" +
                        " --databases " + tableSchema;
            }else {
                shell = mysqlPath + "\\mysqldump " + " -h" + host + " -P" + port +
                        " -u" + username + " -p" + password +
                        " --lock-all-tables=true" + " --result-file=" + fileName +
                        " --default-character-set=utf8" +
                        " --all-databases";
            }
            //执行备份命令
            Process child = rt.exec(shell);
            if (child.waitFor() == 0) { // 0 表示线程正常终止。
                //备份文件上传至ftp
                File file = new File(fileName);
                if(!file.exists()){
                    return map;
                }
                InputStream input = new FileInputStream(file);
                map = ftpFileUtil.uploadFile(file.getName(), input, ImageTypeEnum.BACKUP.getCode());
                //返回数据
                map.put("fileSize",file.length());
                //清理文件
                file.deleteOnExit();
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg",e.getMessage());
            return map;
        }
        return map;
    }
    /**
    *@Description 获取文件大小的b/kb/mbb/gb/tb
    *@Param [size]
    *@Return java.lang.String
    *@Author yilihua
    *@Date 2019/6/4
    *@Time 16:18
    */
    public String readableFileSize(Long size) {
        if (size==null||size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /**
     *@Description 获取mysql安装路径
     *@Param []
     *@Return java.lang.String
     *@Author yilihua
     *@Date 2019/6/4
     *@Time 11:33
     */
    public String getMysqlPath(){
        Map m=System.getenv();
        String s2=(String) m.get("Path");//获取本计算机环境变量中PATH的内容
        String s1=s2.substring(0, s2.indexOf("MySQL"));//截取索引从‘0’到”MySQL“字符串
        String s3=s2.substring(s1.lastIndexOf(":")-1, s2.length());//获取S1字符串中最后一个”：“的索引，然后截取从":"-1 索引处到最后的字符串
        String mySqlPath=s3.substring(0,s3.indexOf("bin")+3);//截取字符串S3 从索引”0“ 到”bin“的字符串。获取完整的mysql安装路径
        return mySqlPath;
    }
}
