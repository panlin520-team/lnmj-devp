package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

public enum IndustryEnum implements BaseEnum {
    MEIRONG(1, "美容"),
    MEIFA (2, "美发"),
    ;
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

    IndustryEnum(Integer code, String desc) { //加上public void 上面定义枚举会报错 The constructor Color(int, String) is undefined
        this.code = code;
        this.desc = desc;

    }
}
