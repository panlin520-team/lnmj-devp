package com.lnmj.common.baseController;

import com.lnmj.common.BaseEntity.Message;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.SendMessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/10/10 10:33
 * @Description: 短信
 */
@Api(description = "短信")
@RestController
@RequestMapping("/message")
public class MessageController {
    @Resource
    private SendMessageUtil sendMessageUtil;
    
    /***
    *@Description 发送短信
    *@Param [to, templateId, datas]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/10
    *@Time 10:39
    */
    @ApiOperation(value = "发送短信", notes = "发送短信")
    @RequestMapping(value="/sendMessage", method = RequestMethod.POST)
    public ResponseResult sendMessage(String mobiles,String templateId, String datas){
        String[] strs = datas.split(",");
        HashMap<String, Object> sendMessage = sendMessageUtil.SendMessage(mobiles, templateId, strs);
        Message message =  sendMessageUtil.saveMessage(sendMessage);
        return ResponseResult.success(message);
    }

    /***
    *@Description 查询短信
    *@Param [message]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/10
    *@Time 10:39
    */
    @ApiOperation(value = "查询短信", notes = "查询短信")
    @RequestMapping(value="/selectMessageList", method = RequestMethod.POST)
    public ResponseResult selectMessageList(Message message){
        List<Message> messageList = sendMessageUtil.selectMessageList(message);
        return ResponseResult.success(messageList);
    }







}
