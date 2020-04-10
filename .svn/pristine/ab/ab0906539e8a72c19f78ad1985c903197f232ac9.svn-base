package com.lnmj.common.Enum;

import com.lnmj.common.Enum.base.BaseEnum;

public enum SupplierTypeEnum implements BaseEnum {
    ORG_SUPPLIER (1, "组织供应商"),
    OTHER_SUPPLIER(2, "其他供应商");
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

    SupplierTypeEnum(Integer code, String desc) { //加上public void 上面定义枚举会报错 The constructor Color(int, String) is undefined
        this.code = code;
        this.desc = desc;

    }
}
