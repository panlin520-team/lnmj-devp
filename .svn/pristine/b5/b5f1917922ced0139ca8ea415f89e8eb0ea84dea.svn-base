package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

public enum PayTypeEnum implements BaseEnum {
    ALIPAY(1, "支付宝"),
    WECHAT(2, "微信"),
    CASH_RMB(3, "纸币"),
    CASH_POS(4, "pos刷卡"),
    ACCOUNT(5, "账户支付");
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

    PayTypeEnum(int code, String desc) { //加上public void 上面定义枚举会报错 The constructor Color(int, String) is undefined
        this.code = code;
        this.desc = desc;

    }
}
