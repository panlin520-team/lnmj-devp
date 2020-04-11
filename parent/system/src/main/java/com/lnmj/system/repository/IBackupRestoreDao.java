package com.lnmj.system.repository;

import com.lnmj.system.entity.BackupRestore;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/6/4 15:38
 * @Description: 备份还原Dao接口
 */
public interface IBackupRestoreDao {
    int insertBackupRestore(BackupRestore backupRestore);
    List<BackupRestore> selectBackupRestoreList(BackupRestore backupRestore);

    int useSchema(String schema);

    List<String> showTable();

    List<String> showSchema();
}
