package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;


public enum UnitEnum {

    LIST_INFOMATION_ISNULL(3, "单位信息不存在"),
    ADD_UNIT_FAIL(3, "添加单位出错"),
    UPDATE_UNIT_FAIL(3, "更新单位出错"),
    DELETE_UNIT_FAIL(3, "删除单位出错"),
    ;

    private final Integer code;
    private final String desc;

    UnitEnum(Integer code, String desc) {
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
