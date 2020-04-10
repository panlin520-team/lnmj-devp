package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;


public enum BaseSalaryEnum {

    LIST_INFOMATION_ISNULL(3, "底薪信息不存在"),
    ADD_BASESALARY_FAIL(3, "添加底薪出错"),
    UPDATE_BASESALARY_FAIL(3, "更新底薪出错"),
    DELETE_BASESALARY_FAIL(3, "删除底薪出错"),
    ;

    private final Integer code;
    private final String desc;

    BaseSalaryEnum(Integer code, String desc) {
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
