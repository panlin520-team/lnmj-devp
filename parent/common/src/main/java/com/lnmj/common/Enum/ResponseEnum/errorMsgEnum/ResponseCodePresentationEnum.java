package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description:
 */
public enum ResponseCodePresentationEnum {
    //赠送字段
    PRESENTATION_NOT_FIND(1,"没有找到赠送记录"),
    PRESENTATIONID_NULL(1,"没有赠送记录"),
    ORDERTYPE_NOT_NULL(1,"订单类型不能为空"),
    ORDERNUMBER_NOT_NULL(1,"订单号不能为空"),
    ORDERLINK_NOT_NULL(1,"订单号不能为空"),
    PRODUCTID_NOT_NULL(1,"商品不能为空"),
    PRODUCTNAME_NOT_NULL(1,"商品不能为空"),
    PRESENTATION_END_TIME_NOT_NULL(1,"有效期不能为空"),
    CONTACTS_NOT_NULL(1,"联系人不能为空"),
    PHONE_NOT_NULL(1,"联系电话不能为空"),
    STOREID_NOT_NULL(1,"护理门店不能为空"),
    NURSESTORE_NOT_NULL(1,"护理门店不能为空"),
    BEAUTICIAN_NOT_NULL(1,"美容师不能为空"),
    BEAUTICIANID_NOT_NULL(1,"美容师不能为空"),
    NURSETIME_NOT_NULL(1,"护理时间不能为空"),
    ACHIEVEMENTPROPORTION_NOT_NULL(1,"业绩占比不能为空"),
    PRESENTATIONSTATUS_NOT_NULL(1,"业务状态不能为空"),
    CREATEOPERATOR_NOT_NULL(12,"创建人不能为空"),
    MODIFYOPERATOR_NOT_NULL(13,"修改人不能为空"),
    //业务状态
    SUCCESS_ADD_PRESENTATION(21, "成功添加赠送"),
    FAILD_ADD_PRESENTATION(22, "添加赠送失败"),
    SUCCESS_APPOINTMENT(23, "预约成功"),
    FAILD_APPOINTMENT(24, "预约失败"),
    SUCCESS_RETURN(25, "退货成功"),
    FAILD_RETURN(26, "退货失败"),
    SUCCESS_CONFIRM(27, "确认成功"),
    FAILD_CONFIRM(28, "确认失败"),
    SUCCESS_CANCEL(29, "取消成功"),
    FAILD_CANCEL(30, "取消失败"),
    ENUM_NAME_NULL(37, "ENUM字段名字不能为空"),
    ENUM_NAME_ERROR(37, "ENUM字段名字错误"),
    ;

    private final int code;
    private final String desc;

    ResponseCodePresentationEnum(int code, String desc) {
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
