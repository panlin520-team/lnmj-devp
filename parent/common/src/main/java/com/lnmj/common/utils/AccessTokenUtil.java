package com.lnmj.common.utils;


import com.lnmj.common.config.WechatConfig;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: panlin
 * @Date: 2019/4/24 16:47
 * @Description:
 */
public class AccessTokenUtil {
    /**
     * 获得ACCESS_TOKEN
     *
     * @Title: getAccess_token
     * @Description: 获得ACCESS_TOKEN
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public static String getAccessToken(String appid, String appsercret) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("grant_type", "client_credential");
        paramsMap.put("appid", WechatConfig.APPID);
        paramsMap.put("secret", WechatConfig.APPSERCRET);
        String data = MapUtil.buildMap(paramsMap);
        String message = HttpUtil.sendGet(WechatConfig.GET_ACCESS_TOKEN_URL,data);
        String access_token = null;
        try {
            JSONObject demoJson = new JSONObject(message);
            access_token = demoJson.getString("access_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return access_token;
    }
}
