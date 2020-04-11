package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description:
 */
public enum ResponsePayEnum {
    RECHARGEAMOUNT_NOT_ENOUGH(3,"钱包余额不足"),
    PAY_TYPE_NULL(3,"支付类型不存在"),
    PAY_TYPE_ID_NULL(3,"支付类型id不能为空"),
    PAY_TYPE_DELETE_FAIL(3,"支付类型删除失败");
    private final int code;
    private final String desc;

    ResponsePayEnum(int code, String desc) {
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
