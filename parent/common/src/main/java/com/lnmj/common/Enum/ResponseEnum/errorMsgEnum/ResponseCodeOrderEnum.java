package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description:
 */
public enum ResponseCodeOrderEnum {
    NOT_FIND_APPOINTMENT(1, "预约不存在"),
    BEAUTICIAN_NOT_NULL(1, "美容师不能为空"),
    DATE_NOT_NULL(1, "预约日期不能为空"),
    PRODUCTIDS_NULL(3,"商品不能为空"),
    STOREID_NULL(3,"店铺id不能为空"),
    BOOKINGTIME_NULL(3,"护理时间不能为空"),
    BOOKING_BEAUTICIAN_NULL(3,"护理美容师不能为空"),
    ORDER_TYPE_NULL(3,"订单类型不能为空"),
    ORDER_NUMBER_NULL(3,"总订单编号不能为空"),
    SELECT_ORDER_DETAIL_FAIL(3,"获取订单详情失败"),
    ORDER_CANCEL_FAIL(3,"取消订单失败"),
    ORDER_DELETE_FAIL(3,"删除订单失败"),
    ORDER_STATUS_NULL(3,"订单状态不能为空"),
    ORDER_MEMO_NULL(3,"订单水单号不能为空"),
    CARNUMBER_NULL(3,"会员卡号不能为空"),
    ORDER_STATUS_UPDATE_FAIL(3,"订单状态修改失败"),
    ORDER_MEMO_UPDATE_FAIL(3,"订单水单号修改失败"),
    ORDER_NULL(3,"暂无订单"),
    STOCK_PRODUCT_NOT_ENOUGH(3,"库存不足无法下单"),
    STOCK_PRODUCT_NULL(3,"仓库有及时库存"),
    ORDER_NOT_EXIST(3,"订单不存在"),
    TOTAL_PRICE_ERROR(3,"单项总价有误"),
    TOTAL_PRICE_ALL_ERROR_SUBORDER(3,"总合价有误:与订单子项金额合计不符"),
    TOTAL_PRICE_ALL_ERROR_PAY(3,"总合价有误:与各支付方式支付金额合计不符"),
    ORDER_DATE_ERROR(3,"订单日期有误"),
    EXP_CARD_USED_CAN_NOT_REFUSE(3,"体验卡已经使用过，无法退货"),
    CUSTOM_USED_CAN_NOT_REFUSE(3,"定制项目已经使用过，无法退货"),
    ORDER_NO_PAY(3,"订单状态必须为已支付才能修改"),
    ORDER_NO_POST(3,"订单护理项目所需要的职位有误"),
    ORDER_APPOINTMENT_NOT_EXIST(3,"预约订单不存在"),
    ACCOUNT_BALANCE_NOT_ENOUGH(3,"账户余额不足，无法支付"),
    AMOUNT_CAN_NOT_BIGGER_RECHARGE(3,"退款金额不能大于储值金额"),
    AMOUNT_CAN_NOT_BIGGER_RESIDUE_RECHARGE(3,"退款金额不能大于剩余储值金额"),
    REFUSE_FAIL(3,"退款失败"),
    AMOUNT_CAN_NOT_BIGGER_MEMBER_AMOUNT(3,"退款金额不能大于用户剩余账户余额"),
    CUSTOM_USE_RECORD_NULL(3,"定制项目使用记录不存在"),
    CUSTOM_REFUSE_RECORD_NULL(3,"定制项目退货使用记录不存在"),
    APPOINTMENT_ORDER_TIME_OCCUPY(3,"当前时间已经被占用，无法选择"),
    MEMO_EXIST(3,"水单号重复无法下单");
    private final int code;
    private final String desc;

    ResponseCodeOrderEnum(int code, String desc) {
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
