package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

public enum ProductTypeEnum implements BaseEnum {
    PRODUCT(1, "产品"),
    SERVICE(2, "护理"),
    COSMETIC(3, "医美"),
    EXPERIENCECARD(4, "体验卡");

    private Integer code;
    private String desc;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    ProductTypeEnum(int code, String desc) { //加上public void 上面定义枚举会报错 The constructor Color(int, String) is undefined
        this.code = code;
        this.desc = desc;
    }
}
