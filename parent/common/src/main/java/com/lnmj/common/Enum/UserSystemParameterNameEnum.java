package com.lnmj.common.Enum;


public enum UserSystemParameterNameEnum {
    ISOLDUSER(1, "新老用户判定时间条件"),
    LOSERUSER(2, "丢失用户判定时间条件"),
    ;
    private final int code;
    private final String desc;

    UserSystemParameterNameEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
