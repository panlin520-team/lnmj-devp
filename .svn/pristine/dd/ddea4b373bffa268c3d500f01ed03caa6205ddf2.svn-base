package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

public enum OrderTypeEnum implements BaseEnum {
    PRODUCT_ORDER(1, "商品订单"),//支付订单
    SERVICE_ORDER (2, "护理订单"),//支付订单
    CUSTOM_ORDER (4, "定制订单"),//支付订单
    RECHARGE_ORDER (5, "充值订单"),//储值订单
    EXPCARD_ORDER (6, "体验卡订单"),//支付订单
    TEART_ORDER (7, "赠送订单"),//支付订单
    MAKER_ORDER (8, "创客订单"),//支付订单
    COSMETIC_ORDER (9, "医美订单"),//支付订单
    APPOINTMENT_ORDER (10, "预约订单");//支付订单
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

    OrderTypeEnum(Integer code, String desc) { //加上public void 上面定义枚举会报错 The constructor Color(int, String) is undefined
        this.code = code;
        this.desc = desc;

    }
}
