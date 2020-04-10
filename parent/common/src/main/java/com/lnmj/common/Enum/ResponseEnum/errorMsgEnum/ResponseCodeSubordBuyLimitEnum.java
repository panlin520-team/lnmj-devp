package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description:
 */
public enum ResponseCodeSubordBuyLimitEnum {
    SUBORD_BUY_LIMIT_LIST_NULL(3,"限购列表不存在"),
    SUBORD_BUY_LIMIT_NUM_ZERO(3,"限购允许购买个数不能为0");
    private final int code;
    private final String desc;

    ResponseCodeSubordBuyLimitEnum(int code, String desc) {
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
