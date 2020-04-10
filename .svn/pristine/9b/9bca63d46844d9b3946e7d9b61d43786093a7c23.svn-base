package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description:
 */
public enum ResponseCodeBackupRestoreEnum {

    FALID_BACKUP(3,"备份失败"),
    FALID_RESTORE(3,"还原失败"),
    NOT_BACKUP_FILE(3,"没有备份文件"),
    CREATEOPERATOR_NOT_NULL(3,"创建人不能为空"),
    RESTORE_FILE_NOT_NULL(3,"还原文件不能为空"),
    TABLE_SCHEMA_NOT_NULL(3,"还原数据库不能为空"),
    ;
    private final int code;
    private final String desc;

    ResponseCodeBackupRestoreEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }

}
