package com.lnmj.common.Enum;

/**
 * @Auther: panlin
 * @Date: 2019/4/18 13:21
 * @Description:
 */
public enum LoginTypeEnum {
    PHONENUM_LOGIN(1, "手机号登录"),
    PASSWORD_LOGIN(2, "用户密码登录"),
    WX_LOGIN(3, "微信登录"),
    QQ_LOGIN(4, "QQ登录");


    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    LoginTypeEnum(int code, String desc) { //加上public void 上面定义枚举会报错 The constructor Color(int, String) is undefined
        this.code = code;
        this.desc = desc;

    }


}
