package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description:
 */
public enum ResponseCodeWalletEnum {

    //钱包
    RECHARGE_RECORD_NOTEXIST(37, "充值记录不存在"),
    EXPENSECALENDAR_RECORD_NOTEXIST(37, "消费记录不存在"),
    USER_ID_NULL(37, "用户id不能为空"),
    VIPCARTNUMBER_NULL(37, "会员卡号不能为空"),
    RECHARGEAMOUNT_FAILUER(37, "充值失败"),
    WALLET_ISEXIST(37, "用户钱包已经存在"),
    WALLET_IS_NOT_EXIST(37, "用户钱包不存在"),
    OPENING_WALLET_NULL(37, "充值金额不能为空"),
    SELECT_WALLET_BALANCE_FAILUER(37, "查询钱包金额信息失败"),
    CONSUMPTION_RECORD_NOTEXIST(37, "消费记录不存在"),
    DELETE_EXPENSERECORD_FAILUER(37, "删除消费记录失败"),
    DELETE_EXPENSERECORD_SUCCESS(37, "删除消费记录成功"),
    IDS_COLLECTION_NULL(37, "id集合不能为空"),
    WALLET_ID_IS_NULL(37, "钱包id不能为空"),
    RECHARGEAMOUNT_IS_NULL(37, "充值金额不能为空"),
    ADD_RECHARGERECORDD_FAIL(37, "添加充值记录失败"),
    CARTNUMBER_ISNULL(37, "会员卡号不能为空"),
    TRANSACTIONTIME_ISNULL(37, "交易时间不能为空"),
    ADDRECHARGERECORD_FAIL(37, "添加充值记录失败"),
    NAME_IS_NULL(37, "联系人不能为空"),
    USER_ID_IS_NULL(37, "联系人不能为空"),
    TRANSFER_FAIL(37, "奖金转余额失败"),
    CASHBACK_IS_NULL(37, "获取返利审核失败"),
    CASHBACK_FAIL(37, "返利审核失败"),
    RETURNBACK_FAIL(37, "退货操作失败"),
    UPDATE_RECHARGEAMOUNT_FAIL(37, "更新充值金额失败"),
    INSERT_WALLET_FAIL(37, "添加钱包失败"),
    RECHARGE_FAIL(37, "执行充值失败"),
    //字段判断
    PAYTYPE_IS_ERROR(37, "充值类型有误"),
    RECHARGECHANNEL_NULL(37, "充值渠道不能为空"),
    PAY_STATUS_NULL(37, "支付状态不能为空"),
    BEAUTICIAN_IS_NULL(37, "美容师不能为空"),
    NAMEIS_NULL(37, "联系人不能为空"),
    MOBILE_NULL(37, "联系方式不能为空"),
    //逻辑判断
    RECHARGEAMOUNT_NULL(37, "充值金额不能为空"),
    DELETE_CASHRECORD_FAIL(37, "删除提现记录失败"),
    DELETE_CASHRECORD_SUCCESS(37, "删除提现记录成功"),
//    ADD_CASHRECORD_SUCCESS(37, "提现申请发起成功"),
    ADD_CASHRECORD_FAIL(37, "提现申请发起失败"),
    REFUSE_AUDIT_FAIL(37, "拒绝审核出错"),
    AUDIT_FAIL(37, "审核出错"),
    ADD_RECHARGE_PROFIL_FAIL(37, "添加充值收益失败"),
    AUDIT_CASH_FAIL(37, "审核提现出错"),
    UPDATE_WALLET_AMOUNT_FAIL(37, "更新钱包金额失败"),
    RECHARGEORDERINFO_IS_NULL(37, "充值订单数为空"),

    //账户管理
    ADD_ACCOUNT_ERROR(37, "账户添加失败"),
    UPDATE_ACCOUNT_ERROR(37, "账户修改失败"),
    DELETE_ACCOUNT_ERROR(37, "账户删除失败"),
    ZENGSONG_TYPE_EXIST(37, "赠送账户已经存在"),
    ACCOUNTTYPE_ISNULL(37, "充值账户类型为空"),
    TYPE_NAME_EXIST(37, "账户名称已经存在"),







    LOGIN_SUCCESS(37, "登录成功"),
    SUCCESS(37, "成功"),
    ERROR(37, "失败"),
    //资金池
    CAPITAL_POOL_FIND_NO(37, "资金池列表查看失败"),

    ;

    private final int code;
    private final String desc;

    ResponseCodeWalletEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
