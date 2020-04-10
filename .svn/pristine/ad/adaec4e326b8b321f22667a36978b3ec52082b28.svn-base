package com.lnmj.authservice.common;

import org.springframework.util.Base64Utils;

/**
 * 头工具类
 */
public final class HeadersUtil {
    /**
     * 请求参数类型
     */
    public final static String CONTENT_TYPE_JSON="application/json";
    /**
     * 字符集
     */
    public final static String CHARSET_ZH="UTF-8";
    /**
     * 构建 Authorization
     * <br> 例：Basic YWRtaW46MTIzNDU2
     *
     * @return
     */
    public static String basicAuthHeader(String clientId,String clientSecret) {
        String auth = clientId + ":" + clientSecret;
        byte[] encodedAuth = Base64Utils.encode(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }

    public static void main(String[] args) {

        System.out.println(basicAuthHeader("user-service","123456"));
    }
}
