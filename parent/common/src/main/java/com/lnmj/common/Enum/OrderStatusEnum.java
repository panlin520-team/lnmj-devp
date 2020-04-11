package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

public enum  OrderStatusEnum implements BaseEnum {
    UNPAID (1, "待支付"),
    PAID(2, "已支付"),
    SENDED (3, "已发货或服务中"),
    REFUND (4, "已退款"),
    CANCEL (5, "已取消");
    private Integer code;
    private String desc;
    @Override
    public Integer getCode() {
        return code;
    }
    @Override
    public void setCode(Integer code) {
        this.code = code;
    }
    @Override
    public String getDesc() {
        return desc;
    }
    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    OrderStatusEnum(Integer code, String desc) { //加上public void 上面定义枚举会报错 The constructor Color(int, String) is undefined
        this.code = code;
        this.desc = desc;

    }
}
