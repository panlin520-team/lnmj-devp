package com.lnmj.common.baseController;

import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Author: yilihua
 * @Date: 2019/5/30 15:30
 * @Description: 发送短信
 */
@Api(description = "短信")
@RestController
@RequestMapping("/sms")
public class SMSController {

    @ApiOperation(value = "发送短信", notes = "发送短信")
    @RequestMapping(value="/sendMessage", method = RequestMethod.POST)
    public ResponseResult sendMessage(String phone, String message) throws Exception
    {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn"); // http://gbk.api.smschinese.cn
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
        NameValuePair[] data ={ new NameValuePair("Uid", "SMS_20190530"),
                new NameValuePair("Key", "d41d8cd98f00b204e980"),
                new NameValuePair("smsMob",phone),
                new NameValuePair("smsText",message)};
        post.setRequestBody(data);

        client.executeMethod(post);
        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode:"+statusCode);
        for(Header h : headers)
        {
            System.out.println(h.toString());
        }
        String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
        System.out.println(result); //打印返回消息状态
        HashMap<String, Object> map = new HashMap<>();
        map.put("header",headers);
        map.put("statusCode",statusCode);
        map.put("result",result);
        post.releaseConnection();
        return ResponseResult.success(map);
    }

}
