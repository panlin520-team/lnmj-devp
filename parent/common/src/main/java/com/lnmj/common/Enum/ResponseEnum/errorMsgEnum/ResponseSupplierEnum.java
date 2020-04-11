package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;


public enum ResponseSupplierEnum {

    SUPPLIER_NULL(3, "供应商不存在"),
    SUPPLIER_NAME_EXIST(3, "供应商名称已经存在"),
    SUPPLIER_CATEGORY_NAME_EXIST(3, "供应商分类名称已经存在"),
    SUPPLIER_CATEGORY_NULL(3, "供应商分类不存在"),
    SUPPLIER_CAIGOU_INTSTORAGE_USE(3, "供应商正在被采购入库单使用无法删除"),
    SUPPLIER_CATEGORY_ID_NULL(3, "供应商分类Id不能为空")
    ;

    private final Integer code;
    private final String desc;

    ResponseSupplierEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public Integer getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }

}
