package com.lnmj.account.serviceProxy;

import com.google.gson.JsonSyntaxException;
import com.lnmj.account.entity.VO.WechatQRCodeVO;
import com.lnmj.common.config.WechatConfig;
import com.lnmj.common.utils.AccessTokenUtil;
import com.lnmj.common.utils.HttpUtil;
import com.lnmj.common.utils.JsonUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * @Auther: panlin
 * @Date: 2019/4/24 15:51
 * @Description:
 */
@Service("wxQrcodeService")
public class WxQrcodeService {
    public String createForeverStrTicket(String sceneStr) {
        String access_token = AccessTokenUtil.getAccessToken("wxaef7b0c5b7f0b97d", "9125a6164145ae373fb4593ff774ed73");
        // output data
        Map<String, String> intMap = new HashMap<>();
        intMap.put("scene_str", sceneStr);
        Map<String, Map<String, String>> mapMap = new HashMap<>();
        mapMap.put("scene", intMap);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("action_name", WechatConfig.QR_LIMIT_STR_SCENE);
        paramsMap.put("action_info", mapMap);
        String data = JsonUtil.toJsonString(paramsMap);
        data = HttpUtil.sendPost(WechatConfig.CREATE_TICKET_PATH + "?access_token=" + access_token, data);
        WechatQRCodeVO wechatQRCode = null;
        try {
            wechatQRCode = JsonUtil.fromJsonString(data, WechatQRCodeVO.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return wechatQRCode == null ? null : wechatQRCode.getTicket();
    }

}
