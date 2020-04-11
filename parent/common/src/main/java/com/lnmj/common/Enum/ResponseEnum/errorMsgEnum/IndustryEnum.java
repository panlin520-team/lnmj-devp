package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;


public enum IndustryEnum{

    LIST_INFOMATION_ISNULL(3, "行业信息不存在"),
    ADD_INDUSTRY_FAIL(3, "添加行业出错"),
    INDUSTRY_NAME_IS_EXIST(3, "行业名称已经存在"),
    UPDATE_INDUSTRY_FAIL(3, "更新行业出错"),
    DELETE_INDUSTRY_FAIL(3, "删除行业出错"),
    ;

    private final Integer code;
    private final String desc;

    IndustryEnum(Integer code, String desc) {
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
