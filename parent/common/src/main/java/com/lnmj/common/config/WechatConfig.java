package com.lnmj.common.config;

/**
 * @Auther: panlin
 * @Date: 2019/4/24 16:08
 * @Description:
 */
public class WechatConfig {
    //appid  -测试
    public static final String APPID = "wxaef7b0c5b7f0b97d";
    //appsercret -测试
    public static final String APPSERCRET = "9125a6164145ae373fb4593ff774ed73";
    // 临时二维码
    public static final String QR_SCENE = "QR_SCENE"; //-1
    // 永久二维码
    public static final String QR_LIMIT_SCENE = "QR_LIMIT_SCENE";//0
    // 永久二维码(字符串)
    public static final String QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";//1
    // 创建二维码
    public static final String CREATE_TICKET_PATH = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
    // 通过ticket换取二维码
    public static final String SHOW_QRCODE_PATH = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
    // 长链接转成短链接
    public static final String WECHAT_SHORT_QRCODE_URL = "https://api.weixin.qq.com/cgi-bin/shorturl";

    public static final String LONG2SHORT = "long2short";

    public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

}
