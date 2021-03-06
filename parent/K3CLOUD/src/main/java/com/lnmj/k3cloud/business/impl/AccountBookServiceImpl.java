package com.lnmj.k3cloud.business.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.AccountBookService;
import com.lnmj.k3cloud.entity.account.accountBook.*;
import com.lnmj.k3cloud.entity.account.accountPolicy.FCurrencyID;
import com.lnmj.k3cloud.entity.account.accountPolicy.FRateTypeID;
import com.lnmj.k3cloud.pojo.Incident;
import com.lnmj.k3cloud.repository.IIncidentDao;
import com.lnmj.k3cloud.util.InvokeHelper;
import com.lnmj.k3cloud.util.K3cloudConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Map;

/**
    *@Description 客户金蝶service
    *@Param
    *@Return
    *@Author Mr.Ren
    *@Date 2019/11/11
    *@Time
    */
@Service("accountBookServiceImpl")
public class AccountBookServiceImpl implements AccountBookService {
    @Resource
    private IIncidentDao iIncidentDao;
    @Override
    public ResponseResult saveAccountBook(String acctId, String dataCentreUserName, String dataCentrePassword, String fName,String heSuanTiXiK3Number,String orgK3Number ) {
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                Calendar date = Calendar.getInstance();
                String year = String.valueOf(date.get(Calendar.YEAR));
                String month = String.valueOf(date.get(Calendar.MONTH));
                String yearMonth = year+"."+month;


                String param = "{\n" +
                        "    \"Creator\": \"\",\n" +
                        "    \"NeedUpDateFields\": [],\n" +
                        "    \"NeedReturnFields\": [],\n" +
                        "    \"IsDeleteEntry\": \"true\",\n" +
                        "    \"SubSystemId\": \"\",\n" +
                        "    \"IsVerifyBaseDataField\": \"false\",\n" +
                        "    \"IsEntryBatchFill\": \"true\",\n" +
                        "    \"ValidateFlag\": \"true\",\n" +
                        "    \"NumberSearch\": \"true\",\n" +
                        "    \"InterationFlags\": \"\",\n" +
                        "    \"IsAutoSubmitAndAudit\": \"true\",\n" +
                        "    \"Model\": {\n" +
                        "        \"FBOOKID\": 0,\n" +
                        "        \"FCRTYEARPERIOD\": \""+yearMonth+"\",\n" +
                        "        \"FCreateOrgId\": {\n" +
                        "            \"FNumber\": \""+orgK3Number+"\"\n" +
                        "        },\n" +
                        "        \"FAcctSystemID\": {\n" +
                        "            \"FNumber\": \""+heSuanTiXiK3Number+"\"\n" +
                        "        },\n" +
                        "        \"FAccountOrgID\": {\n" +
                        "            \"FNumber\": \""+orgK3Number+"\"\n" +
                        "        },\n" +
                        "        \"FAcctPolicyID\": {\n" +
                        "            \"FNumber\": \"KJZC01_SYS\"\n" +
                        "        },\n" +
                        "        \"FSTARTPERIOD\": \"2\",\n" +
                        "        \"FPeriodid\": {\n" +
                        "            \"FNumber\": \"KJRL01_SYS\"\n" +
                        "        },\n" +
                        "        \"FName\": \""+fName+"\",\n" +
                        "        \"FBOOKTYPE\": \"1\",\n" +
                        "        \"FACCTTABLEID\": {\n" +
                        "            \"FNumber\": \"PRE01\"\n" +
                        "        },\n" +
                        "        \"FCURRENCYID\": {\n" +
                        "            \"FNumber\": \"PRE001\"\n" +
                        "        },\n" +
                        "        \"FRATETYPEID\": {\n" +
                        "            \"FNumber\": \"HLTX01_SYS\"\n" +
                        "        },\n" +
                        "        \"FSTARTYEAR\": \""+year+"\",\n" +
                        "        \"FYEARANDPERIOD\": \""+yearMonth+"\",\n" +
                        "        \"FAPFinType\": \"1\",\n" +
                        "        \"FARFinType\": \"1\",\n" +
                        "        \"FCURRENTYEAR\": "+year+",\n" +
                        "        \"FCURRENTPERIOD\": 2\n" +
                        "    }\n" +
                        "}";
                JSONObject jsonObject = InvokeHelper.Save("BD_AccountBook",param);

                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("账簿保存");
                    incident.setExcuteIncidentName("BD_AccountBook,Save");
                    incident.setExcuteIncidentJSON(param);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }

                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }
    }

    @Override
    public ResponseResult deleteAccountBook(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        //TODO
        return null;
    }

    @Override
    public ResponseResult viewAccountBook(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String id) {
        //TODO
        return null;
    }

    @Override
    public ResponseResult subimtAccountBook(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        //TODO
        return null;
    }

    @Override
    public ResponseResult auditAccountBook(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        //TODO
        return null;
    }

    @Override
    public ResponseResult unAuditAccountBook(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        //TODO
        return null;
    }
}
