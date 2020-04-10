package com.lnmj.common.utils;

import com.lnmj.common.Enum.ResponseEnum.ImageTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: panlin
 * @Date: 2019/5/10 12:06
 * @Description:
 */
@Component
public class FtpFileUtil {
    //ftp服务器ip地址
    @Value("${ftp.address}")
    private String FTP_ADDRESS;
    //端口号
    @Value("${ftp.port}")
    private String FTP_PORT;
    //用户名
    @Value("${ftp.username}")
    private String FTP_USERNAME;
    //密码
    @Value("${ftp.password}")
    private String FTP_PASSWORD;
    //图片物理路径
    @Value("${ftp.basepase}")
    private String FTP_BASEPATH;
    //图片地址
    @Value("${ftp.imagehost}")
    private String imagehost;
    private FTPClient ftp = null;

    public Map downlFile(String fileUrl, OutputStream out) {
        Map resutlMap = new HashMap<>();
        //获取文件名
        Map map = interceptFromUrl(fileUrl);
        if(!(Boolean) map.get("flag")){
            resutlMap.put("flag", false);
            resutlMap.put("msg", "fileUrl有错误");
            return resutlMap;
        }
        String suffix = (String) map.get("suffix");
        String imgExt = "jpg|jpeg|png|bmp|GIF|JPG|PNG|JPEG|sql|SQL";
        if (!imgExt.contains(suffix)) {
            resutlMap.put("flag", false);
            resutlMap.put("msg", "图片格式有误");
            return resutlMap;
        }
        ftp = new FTPClient();
        ftp.setControlEncoding("GBK");
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, Integer.parseInt(FTP_PORT));// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                resutlMap.put("flag", false);
                resutlMap.put("msg", "FTP连接失败");
                return resutlMap;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            //fileName
            String fileName = (String) map.get("fileName");
            //directory
            String directory = (String) map.get("directory");
            //读取文件
            ftp.changeWorkingDirectory(directory);//转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs)
            {
                if (ff.getName().equals(fileName))
                {
                    ftp.retrieveFile(ff.getName(), out);
                    out.close();
                    resutlMap.put("flag", true);
                    resutlMap.put("msg", "上传成功");
                }
            }
            ftp.logout();
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return resutlMap;
    }

    private Map interceptFromUrl(String fileUrl) {
        //  192.168.0.10:8089/backup/1559705289821220.sql
        Map map = new HashMap();
        if(StringUtils.isBlank(fileUrl)){
            map.put("flag",false);
            return map;
        }
        int i = fileUrl.lastIndexOf(".");
        String suffix = fileUrl.substring(i+1);
        String[] split = fileUrl.split("/");
        if(split.length!=3){
            map.put("flag",false);
            return map;
        }
        String host = split[0].split(":")[0];
        String port = split[0].split(":")[1];
        String directory = split[1];
        String fileName = split[2];
        map.put("suffix",suffix);
        map.put("host",host);
        map.put("port",port);
        map.put("directory",directory);
        map.put("fileName",fileName);
        map.put("flag",true);
        return map;
    }


    public Map uploadFile(String originFileName, InputStream input, String imageType) {
        Map resutlMap = new HashMap<>();
        String suffix = originFileName.split("\\.")[1];
        String imgExt = "jpg|jpeg|png|bmp|GIF|JPG|PNG|JPEG|sql|SQL";
        if (!imgExt.contains(suffix)) {
            resutlMap.put("flag", false);
            resutlMap.put("msg", "图片格式有误");
            return resutlMap;
        }
        ftp = new FTPClient();
        ftp.setControlEncoding("GBK");
        ImageTypeEnum[] items = ImageTypeEnum.values();
        boolean flag = false;
        String directory = null;
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, Integer.parseInt(FTP_PORT));// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                System.out.println();
                ftp.disconnect();
                resutlMap.put("flag", false);
                resutlMap.put("msg", "FTP连接失败");
                return resutlMap;
            }
            for (ImageTypeEnum item : items) {
                if (StringUtils.equals(item.getCode(), imageType)) {
                    directory = item.getCode();
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                resutlMap.put("flag", false);
                resutlMap.put("msg", "不存在这样的图片类型");
                return resutlMap;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            boolean isDirectory = ftp.changeWorkingDirectory("\\" + directory);
            if (!isDirectory) {
                ftp.makeDirectory("\\" + directory);
            }
            ftp.changeWorkingDirectory("\\" + directory);
            String newName = FileUtil.getFileName(originFileName);
            ftp.enterLocalPassiveMode();    //PORT方式和PASV方式，中文意思为主动式和被动式
            ftp.storeFile(newName, input);
            input.close();
            ftp.logout();
            resutlMap.put("flag", true);
            resutlMap.put("url", imagehost + "/" + directory + "/" + newName);
            resutlMap.put("msg", "上传成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return resutlMap;
    }

    public Map deleteFile(String fileName,String imageType) {
        Map resutlMap = new HashMap<>();
        ftp = new FTPClient();
        ftp.setControlEncoding("utf-8");
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, Integer.parseInt(FTP_PORT));
            ftp.login(FTP_USERNAME, FTP_PASSWORD);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                resutlMap.put("flag", false);
                resutlMap.put("msg", "FTP连接失败");
                return resutlMap;
            }
            ImageTypeEnum[] items = ImageTypeEnum.values();
            boolean flag = false;
            String directory = null;
            for (ImageTypeEnum item : items) {
                if (StringUtils.equals(item.getCode(), imageType)) {
                    directory = item.getCode();
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                resutlMap.put("flag", false);
                resutlMap.put("msg", "不存在这样的图片类型");
                return resutlMap;
            }
            int dele ;
            ftp.changeWorkingDirectory("\\" + directory);
            dele = ftp.dele(fileName);
            System.out.println(dele);
            if (dele>0){
                resutlMap.put("flag", true);
                resutlMap.put("msg", "删除成功");
            }else{
                resutlMap.put("flag", false);
                resutlMap.put("msg", "删除失败");
            }
            ftp.logout();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resutlMap;
    }

}
