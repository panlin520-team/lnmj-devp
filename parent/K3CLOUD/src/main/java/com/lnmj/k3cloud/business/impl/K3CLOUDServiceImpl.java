package com.lnmj.k3cloud.business.impl;


import com.alibaba.fastjson.JSONObject;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IK3CLOUDService;
import com.lnmj.k3cloud.util.InvokeHelper;
import com.lnmj.k3cloud.entity.login.Login;
import com.lnmj.k3cloud.repository.IK3CLOUDDao;
import com.lnmj.k3cloud.util.K3cloudConfig;
import com.lnmj.k3cloud.util.MyHttpClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service("K3CLOUDServiceImpl")
public class K3CLOUDServiceImpl implements IK3CLOUDService {
    @Resource
    private IK3CLOUDDao k3cloudDao;
    public static String SessionId;

    @Override
    public ResponseResult k3Login(String acctID, String userName, String password, String lcid) {
        MyHttpClient httpClient = new MyHttpClient();
        String url = K3cloudConfig.PARENT_URL + K3cloudConfig.LOGIN_URL;
        HashMap map = new HashMap();
        map.put("acctID", acctID);
        map.put("username", userName);
        map.put("password", password);
        map.put("lcid", lcid);
        String a = httpClient.doPost(url, map);
        Login login = JSONObject.parseObject(a, Login.class);
        if (login.getIsSuccessByAPI() == true) {
            SessionId = login.getContext().getSessionId();
        }
        return ResponseResult.success(login);
    }

}
