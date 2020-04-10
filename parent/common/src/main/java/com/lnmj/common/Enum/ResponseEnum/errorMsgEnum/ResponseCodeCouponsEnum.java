package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description:
 */
public enum ResponseCodeCouponsEnum {

    //优惠券领取
    ONELIMIT_CANNOT_GET(3,"此用户领取数量达到上限，无法领取"),
    COUPONS_GET_OVER(3,"此优惠券已经领取完毕");

    private final int code;
    private final String desc;

    ResponseCodeCouponsEnum(int code, String desc) {
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
