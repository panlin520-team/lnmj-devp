package com.lnmj.common.Enum;

public enum OrderSortStatusEnum {
    ORDER_BY_ASC(1,"ASC"),
    ORDER_BY_DESC(2,"DESC");

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

    OrderSortStatusEnum(int code, String desc) { //加上public void 上面定义枚举会报错 The constructor Color(int, String) is undefined
        this.code = code;
        this.desc = desc;

    }
}
