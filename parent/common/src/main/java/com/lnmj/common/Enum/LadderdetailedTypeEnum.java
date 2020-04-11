package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

public enum LadderdetailedTypeEnum implements BaseEnum {
    PRODUCT_LADDERDETAILED(1, "商品业绩"),//支付订单
    SERVICE_LADDERDETAILED (2, "护理业绩"),//支付订单
    RECHARGE_LADDERDETAILED (3, "充值业绩"),//储值订单
    COSMETIC_LADDERDETAILED (4, "医美业绩");//支付订单
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

    LadderdetailedTypeEnum(Integer code, String desc) { //加上public void 上面定义枚举会报错 The constructor Color(int, String) is undefined
        this.code = code;
        this.desc = desc;

    }
}
