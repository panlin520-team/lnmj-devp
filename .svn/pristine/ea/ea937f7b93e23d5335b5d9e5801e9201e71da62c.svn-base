package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Author: yilihua
 * @Date: 2019/5/23 16:55
 * @Description:  预约返回枚举
 */
public enum ResponseCodeAppointmentEnum {
    //设置值字段
    USEROPTIONID_NOT_NULL(1, "id不能为空"),
    USERID_NOT_NULL(1, "用户id不能为空"),
    OPTION_ID_NOT_NULL(1, "属性id不能为空"),
    CARDNUMBER_NOT_NULL(1, "会员卡号不能为空"),
    USERVALUE_NOT_NULL(1, "属性值不能为空"),
    NOT_FIND_OPTIONVALUE(1, "设置值不存在"),

    //设置表字段
    OPTIONTITLE_NOT_NULL(1, "标题不能为空"),
    OPTIONID_NOT_NULL(1, "id不能为空"),
    OPTIONSORT_NOT_NULL(1, "排序不能为空"),
    OPTIONVALUE_NOT_NULL(1, "设置的值不能为空"),
    NOT_FIND_OPTION(1, "设置不存在"),

    //预约表字段
    DATE_NOT_NULL(1, "预约日期不能为空"),
    BEAUTICIAN_NOT_NULL(1, "美容师不能为空"),
    APPOINTMENTTYPE_NOT_NULL(1, "预约类型不能为空"),
    NAME_NOT_NULL(1, "顾客不能为空"),
    PRODUCTNAME_NOT_NULL(1, "项目不能为空"),
    PERFORMANCE_NOT_NULL(1, "业绩不能为空"),
    SELLING_NOT_NULL(1, "销售不能为空"),
    SOURCE_NOT_NULL(1, "来源不能为空"),
    DURATION_NOT_NULL(1, "护理时长不能为空"),
    STORENUMBER_NOT_NULL(1, "到店人数不能为空"),
    STORENAME_NOT_NULL(1, "护理门店不能为空"),
    NURSINGTIME_NOT_NULL(1, "护理时间不能为空"),
    CREATEOPERATOR_NOT_NULL(1, "添加人不能为空"),
    MODIFYOPERATOR_NOT_NULL(1, "修改人不能为空"),
    APPOINTMENTID_NOT_NULL(1, "修改的预约id不能为空"),
    APPOINTMENTSTATUS_NOT_NULL(1, "预约状态不能为空"),
    APPOINTMENTSTATUS_ERROR(1, "预约状态错误"),
    APPOINTMENT_BEAUTICIAN_STORE_ERROR(1, "项目预约员工的门店有误"),
    APPOINTMENT_BEAUTICIAN_REPEAT(1, "员工重复预约"),
    APPOINTMENT_PRODUCT_ERROR(1, "项目预约员工有误"),
    APPOINTMENT_BEAUTICIAN_ERROR(1, "项目还需要预约员工"),
    APPOINTMENT_DATE_ERROR(1, "护理日期有误"),
    APPOINTMENT_DATE_OVERDUE(1, "护理日期已过期"),
    NOT_FIND_APPOINTMENT(1, "预约不存在"),
    PARAMS_NOT_ALL_NULL(1, "需要传入参数"),

    //业务状态字段
    PUSH_FAILD(1, "推送失败"),
    NOT_FIND_USER(1, "没有查找到用户"),
    NOT_FIND_USER_WALLLET(1, "没有查找到用户"),
            ;

    private final int code;
    private final String desc;

    ResponseCodeAppointmentEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }
}
