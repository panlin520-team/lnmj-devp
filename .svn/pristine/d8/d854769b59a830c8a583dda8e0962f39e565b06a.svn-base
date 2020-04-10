package com.lnmj.system.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.entity.BackupRestore;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/6/11 11:04
 * @Description: 备份还原Service接口
 */
@Service("iBackupRestoreService")
public interface IBackupRestoreService {

    ResponseResult backup(String tableSchema, String tableName,String createOperator) ;

    ResponseResult restore(String tableSchema, String fileUrl, String createOperator);

    ResponseResult selectBackupRestoreList(int pageNum,int pageSize,BackupRestore backupRestore);

    ResponseResult showSchema();

    ResponseResult showTable(String schema);
}
