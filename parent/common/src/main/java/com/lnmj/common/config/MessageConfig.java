package com.lnmj.common.config;


import com.cloopen.rest.sdk.CCPRestSDK.*;

/**
 * @Author: yilihua
 * @Date: 2019/10/9 15:28
 * @Description:  荣联云通讯 应用信息
 */
public class MessageConfig {
    //沙盒环境（用于应用开发调试）：sandboxapp.cloopen.com
    //生产环境（用户应用上线使用）：app.cloopen.com
    public static final String SERVER_IP = "app.cloopen.com";
    public static final String SERVER_PORT = "8883";
    //ACOUNT SID和AUTH TOKEN在登陆官网后，应用-管理控制台”中查看开发者主账号获取
    public static final String ACCOUNT_SID = "8a216da86dae9014016daf6036c80141";
    public static final String ACCOUNT_TOKEN = "d26671484cca4238b1b1923f62586f9d"; //AUTH TOKEN
    //子账号信息
//    private static final String SUBACCOUNT_SID;
//    private static final String SUBACCOUNT_Token;
    //应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID
    public static final String App_ID = "8a216da86dae9014016daf6037170148";
    //返回结果类型 BodyType.Type_XML,BodyType.Type_JSON
    public static final BodyType BODY_TYPE = BodyType.Type_JSON;
    //返回成功码
    public static final String SUCCESS_CODE = "000000";
    //呼叫id
//    public static final String Callsid;

}
