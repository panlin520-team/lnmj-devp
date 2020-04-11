package com.lnmj.common.Enum;

public enum ExperUpdateEfficientTypeEnum {
    SCOPE_OF_USE_ALL(1, "所有用户生效"),
    SCOPE_OF_USE_NEW(2, "新用户生效"),
    SCOPE_OF_USE_OLD(3, "老用户生效");

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    ExperUpdateEfficientTypeEnum(int code, String desc) { //加上public void 上面定义枚举会报错 The constructor Color(int, String) is undefined
        this.code = code;
        this.desc = desc;

    }
}
