package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

public enum MembershiplevelEnum implements BaseEnum {
    ONE(1, "一级"),
    TWO(2, "二级"),
    THREE(3, "三级"),
    FOUR(4, "四级"),
    FIVE(5, "五级"),
    SIX(6, "六级"),
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

    MembershiplevelEnum(Integer code, String desc) { //加上public void 上面定义枚举会报错 The constructor Color(int, String) is undefined
        this.code = code;
        this.desc = desc;

    }
}
