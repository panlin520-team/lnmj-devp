package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

public enum RechargeChannelEnum implements BaseEnum {
    POS(1, "POS充值"),
    MALL(2, "商城充值"),
    PLAT_TO_POS(3, "平台-POS充值"),
    PLAT_TO_MALL(4, "平台-商城充值");


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

    RechargeChannelEnum(int code, String desc) { //加上public void 上面定义枚举会报错 The constructor Color(int, String) is undefined
        this.code = code;
        this.desc = desc;

    }
}
