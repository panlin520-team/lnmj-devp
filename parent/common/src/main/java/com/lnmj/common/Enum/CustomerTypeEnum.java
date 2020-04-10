package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

public enum CustomerTypeEnum implements BaseEnum {
    OGENERAL_SALE_CUSTOMER (1, "普通销售客户"),
    CONSIGNMENT_SALES_CUSTOMER(2, "寄售客户"),
    INTER_SETTLE_CUSTOMER(3, "内部结算客户");
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

    CustomerTypeEnum(Integer code, String desc) { //加上public void 上面定义枚举会报错 The constructor Color(int, String) is undefined
        this.code = code;
        this.desc = desc;

    }
}
