package com.lnmj.common.Enum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description:
 */
public enum ResponseCodePMerchantSourceEnum {

    //来源商家
    MERCHANTSOURCE_ISNULL(1,"来源商家为空"),

    ;


    private final int code;
    private final String desc;

    ResponseCodePMerchantSourceEnum(int code, String desc) {
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
