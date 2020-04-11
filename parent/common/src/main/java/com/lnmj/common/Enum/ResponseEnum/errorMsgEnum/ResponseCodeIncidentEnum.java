package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;


public enum ResponseCodeIncidentEnum {

    LIST_INFOMATION_ISNULL(3, "事件不存在"),
    INCIDENT_ID_NUT_NULL(3, "事件id不能为空"),
    CREATEOPERATOR_NOT_NULL(1, "添加人不能为空"),
    MODIFYOPERATOR_NOT_NULL(1, "修改人不能为空"),
    ADD_INCIDENT_FAIL(3, "添加事件出错"),
    UPDATE_INCIDENT_FAIL(3, "更新事件出错"),
    DELETE_INCIDENT_FAIL(3, "删除事件出错"),
    CLASS_NOT_FOUND(3, "没有找到需执行的事件所在的类"),
    CLASS_NOT_INSTANCE(3, "需执行的事件所在的类无法实例化"),
    METHOD_NOT_FOUND(3, "没有找到需执行的事件所在的类的方法"),
    METHOD_EXCUTE_FAILED(3, "没有找到需执行的事件所在的类的方法"),
    ;

    private final Integer code;
    private final String desc;

    ResponseCodeIncidentEnum(Integer code, String desc) {
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
