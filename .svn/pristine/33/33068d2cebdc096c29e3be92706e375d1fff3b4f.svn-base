package com.lnmj.system.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.system.entity.BackupRestore;
import com.lnmj.system.repository.IBackupRestoreDao;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: yilihua
 * @Date: 2019/6/4 15:39
 * @Description: 备份还原Dao
 */
@Repository
public class BackupRestoreDaoImpl extends BaseDao implements IBackupRestoreDao {

    @Override
    public int insertBackupRestore(BackupRestore backupRestore) {
        return super.insert("backupRestore.insertBackupRestore",backupRestore);
    }

    @Override
    public List<BackupRestore> selectBackupRestoreList(BackupRestore backupRestore) {
        return super.selectList("backupRestore.selectBackupRestore",backupRestore);
    }

    @Override
    public int useSchema(String schema) {
        return super.update("backupRestore.useSchema",schema);
    }

    @Override
    public List<String> showTable() {
        return super.selectList("backupRestore.showTable");
    }

    @Override
    public List<String> showSchema() {
        return super.selectList("backupRestore.showSchema");
    }
}
