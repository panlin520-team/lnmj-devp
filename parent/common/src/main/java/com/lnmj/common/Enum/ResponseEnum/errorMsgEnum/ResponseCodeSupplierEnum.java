package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

import com.lnmj.common.Enum.base.BaseEnum;

public enum ResponseCodeSupplierEnum implements BaseEnum {

    SUPPLIER_UPDATE_FAIL(37, "供应商修改失败");


    private final Integer code;
    private final String desc;

    ResponseCodeSupplierEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public void setCode(Integer code) {

    }

    @Override
    public void setDesc(String desc) {

    }
}
