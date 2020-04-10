package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description:
 */
public enum ResponseCodeCustomMadeEnum {
    //定制项目字段
    NURSEPROJECTNAME_IS_NULL(1,"定制项目名称不能为空"),
    NURSEPROJECTNAME_CONTENT_IS_NULL(1,"定制项目内容不能为空"),
    COUNT_IS_NULL(1,"定制项目次数不能为空"),
    NURSEPROJECT_PRICE_IS_NULL(1,"定制项目定价不能为空"),
    NURSEPROJECT_ORIGINALPRICE_IS_NULL(1,"定制项目原价不能为空"),
    NURSEPROJECT_EFFECTIVETIME_IS_NULL(1,"定制项目有效时间不能为空"),
    NAMAE_IS_NULL(1,"联系人不能为空"),
    VALIDITYTIME_IS_NULL(1,"有效期不能为空"),
    MANUALCOST_IS_NULL(1,"手工费不能为空"),
    INTEGRAL_IS_NULL(1,"积分不能为空"),
    MOBILE_IS_NULL(1,"联系方式不能为空"),
    PAYMODE_IS_NULL(1,"支付方式不能为空"),
    ATTRIBUTE_IS_NULL(1,"属性不能为空"),
    CUSTOMMADEID_IS_NULL(1,"定制项目id不能为空"),
    NURSESTORE_IS_NULL(1,"护理门店不能为空"),
    NURSETIME_IS_NULL(1,"护理时间不能为空"),
    BEAUTICIAN_IS_NULL(1,"美容师不能为空"),
    BEAUTICIANID_IS_NULL(1,"美容师id不能为空"),
    //业务状态
    ADD_NURSEPROJECT_FAIL(1, "新增定制项目失败"),
    CUSTOMMADE_IS_NULL(1, "没有找到相关定制项目"),
    USR_CUSTOMMADE_FAIL(1, "使用定制项目失败"),
    CUSTOMMADE_IS_NOT_FOUND(1,"没有找到指定定制项目信息"),

    //定制项目使用相关
    CONSUMECUSTOMMADE_IS_NULL(1,"没有找到定制项目使用信息"),
    INSERT_CUSTOMMADEORDERDETAIL_FAIL(1,"新增定制项目详情失败"),
    INSERT_ORDER_FAIL(1,"新增定制项目订单失败"),
    CUSTOMMADEORDERDETAIL_INFO_IS_NULL(1,"定制订单详情信息不能为空"),
    MEIRONGSHIANDTIME_CONSUMECUSTOMMADE_INFO_IS_NULL(1,"没有找到指定美容师或者护理时间对应定制项目使用信息"),

    ;

    private final int code;
    private final String desc;

    ResponseCodeCustomMadeEnum(int code, String desc) {
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
