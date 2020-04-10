package com.lnmj.common.utils;

import com.cloopen.rest.sdk.CCPRestSDK;
import com.lnmj.common.BaseEntity.Message;
import com.lnmj.common.baseDao.IMessageDao;
import com.lnmj.common.config.MessageConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: yilihua
 * @Date: 2019/10/9 15:27
 * @Description:  荣联云通讯发送短信
 */
@Service
public class SendMessageUtil {

    @Resource
    private IMessageDao messageDao;

    public CCPRestSDK initMessage(){
        //初始化SDK
        CCPRestSDK ccpRestSmsSDK = new CCPRestSDK();

        //******************************注释*********************************************
        //*初始化服务器地址和端口                                                       *
        //*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
        //*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
        //*******************************************************************************
        ccpRestSmsSDK.init(MessageConfig.SERVER_IP,MessageConfig.SERVER_PORT);

        //******************************注释*********************************************
        //*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
        //*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
        //*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
        //*******************************************************************************
        ccpRestSmsSDK.setAccount(MessageConfig.ACCOUNT_SID,MessageConfig.ACCOUNT_TOKEN);

        //******************************注释*********************************************
        //*初始化应用ID                                                                 *
        //*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
        //*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
        //*******************************************************************************
        ccpRestSmsSDK.setAppId(MessageConfig.App_ID);
        return ccpRestSmsSDK;
    }

    public HashMap<String ,Object> getTemplate(CCPRestSDK ccpRestSDK,String templateId){
        //模板Id，不带此参数查询全部可用模板
        HashMap<String, Object> templateMap = ccpRestSDK.QuerySMSTemplate(templateId);
        return templateMap;
    }

    /***
    *@Description 发送短信
    *@Param [to, templateId, datas]
    *@Return java.util.HashMap<java.lang.String,java.lang.Object>
    *@Author yilihua
    *@Date 2019/10/9
    *@Time 17:20
    */
    public HashMap<String ,Object> SendMessage(String to,String templateId, String[] datas){
        HashMap<String, Object> result = new HashMap<>();
        //收件人
        result.put("mobile",to);
        //模板
        result.put("templateId",templateId);
        //datas
        result.put("datas",datas);

        CCPRestSDK ccpRestSmsSDK = initMessage();

        //******************************注释****************************************************************
        //*调用发送模板短信的接口发送短信                                                                  *
        //*参数顺序说明：                                                                                  *
        //*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号                          *
        //*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。    *
        //*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
        //*第三个参数是要替换的内容数组。																														       *
        //**************************************************************************************************

        //**************************************举例说明***********************************************************************
        //*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
        //*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});																		  *
        //*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
        //*********************************************************************************************************************
        HashMap<String,Object> tempMap = ccpRestSmsSDK.sendTemplateSMS(to,templateId,datas);
        System.out.println("SDKTestGetSubAccounts result=" + tempMap);

        //结果处理并返回
        if(MessageConfig.SUCCESS_CODE.equals(tempMap.get("statusCode"))){
            //result: {data={templateSMS={dateCreated=20191009164121,smsMessageSid=6eebccb563574c95a5477490aeb34944}},statusCode=000000}
            Set<String> tempMapKey = tempMap.keySet();
            for(String key:tempMapKey){
                result.put(key,tempMap.get(key));
            }
            HashMap<String,Object> data = (HashMap<String, Object>) tempMap.get("data");
            Set<String> dataKey = data.keySet();
            for(String key:dataKey){
                ArrayList<HashMap<String, Object>> templateSMS = (ArrayList<HashMap<String, Object>>)data.get(key);
                result.put(key,templateSMS);
                for(HashMap<String, Object> temp:templateSMS){
                    Set<String> templateSMSKey = temp.keySet();
                    for(String str:templateSMSKey){
                        result.put(str,temp.get(str));
                    }
                }
            }
            //解析dateCreated
            String dateCreated = (String)result.get("dateCreated");
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                Date parse = sdf.parse(dateCreated);
                result.put("dateCreated",parse);
            } catch (ParseException e) {

            }
        }else{
            //result: {statusMsg=【短信】应用未上线，模板短信接收号码外呼受限, statusCode=112310}
            Set<String> tempMapKey = tempMap.keySet();
            for(String key:tempMapKey){
                result.put(key,tempMap.get(key));
            }
            //异常返回输出错误码和错误信息
//            System.out.println("错误码=" + tempMap.get("statusCode") +" 错误信息= "+tempMap.get("statusMsg"));
        }
        //查询模板数据
        HashMap<String, Object> template = getTemplate(ccpRestSmsSDK,templateId);
        result.put("template",template);
        //拼接短信
//        result.put("message",);
        return result;
    }

    //保存短信数据
    public Message saveMessage(HashMap<String, Object> map){
        Message message = new Message();
//        message.setMessageType();
        if(map.get("mobile") != null){
            message.setMobile(map.get("mobile").toString());
        }
        if(map.get("templateId") != null){
            message.setTemplateId(map.get("templateId").toString());
        }
        if(map.get("datas") != null){
            message.setDatas(Arrays.toString((String[])map.get("datas")));
        }
        if(map.get("template") != null){
            message.setTemplate(map.get("template").toString());
        }
        if(map.get("message") != null){
            message.setMessage(map.get("message").toString());
        }
        if(map.get("statusCode") != null){
            message.setStatusCode(map.get("statusCode").toString());
        }
        if(map.get("TemplateSMS") != null){
            message.setTemplateSMS(map.get("TemplateSMS").toString());
        }
        if(map.get("statusCode") != null){
            message.setStatusCode(map.get("statusCode").toString());
        }
        if(map.get("dateCreated")!=null){
            message.setDateCreated((Date)map.get("dateCreated"));
        }
        if(map.get("smsMessageSid") != null){
            message.setSmsMessageSId(map.get("smsMessageSid").toString());
        }
        if(map.get("statusMsg") != null){
            message.setStatusMsg(map.get("statusMsg").toString());
        }
//        message.setRemark();
        messageDao.insertMessage(message);
        return message;
    }

    //查询发送的短信数据
    public List<Message> selectMessageList(Message message){
        List<Message> messageList = messageDao.selectMessageList(message);
        return messageList;
    }

}
