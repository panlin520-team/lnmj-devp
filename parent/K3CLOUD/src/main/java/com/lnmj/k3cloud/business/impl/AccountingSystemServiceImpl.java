package com.lnmj.k3cloud.business.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.AccountingSystemService;
import com.lnmj.k3cloud.entity.account.accountingSystem.*;
import com.lnmj.k3cloud.entity.base.BaseAuditBean;
import com.lnmj.k3cloud.entity.base.BaseDeleteBean;
import com.lnmj.k3cloud.entity.base.BaseSubmitBean;
import com.lnmj.k3cloud.entity.base.BaseViewBean;
import com.lnmj.k3cloud.pojo.Incident;
import com.lnmj.k3cloud.repository.IIncidentDao;
import com.lnmj.k3cloud.util.InvokeHelper;
import com.lnmj.k3cloud.util.K3cloudConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 客户金蝶service
 * @Param
 * @Return
 * @Author Mr.Ren
 * @Date 2019/11/11
 * @Time
 */
@Service("accountingSystemServiceImpl")
public class AccountingSystemServiceImpl implements AccountingSystemService {
    @Resource
    private IIncidentDao iIncidentDao;
    @Override
    public ResponseResult saveAccountingSystem(String acctId, String dataCentreUserName, String dataCentrePassword, String fACCTSYSTEMID, String fMAINORGID, String jsonArraySubOrg, String fNumber, String fName) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (login) {
            List subOrgArray = new ArrayList();
            JSONArray subOrgArrayIn = JSON.parseArray(jsonArraySubOrg);
            for (int i = 0; i < subOrgArrayIn.size(); i++) {
                JSONObject objectAll = new JSONObject(new LinkedHashMap());
                objectAll.put("FINVESTRATE", 100.0);

                JSONObject objectFSUBORGID = new JSONObject();
                objectFSUBORGID.put("FNumber", subOrgArrayIn.getJSONObject(i).getString("subK3Number"));
                objectAll.put("FSUBORGID", objectFSUBORGID);

                subOrgArray.add(objectAll);
            }
            String subOrgArrayStr = subOrgArray.toString();
            try {
                String model = null;
                if (!fACCTSYSTEMID.equals("0")) {
                    model = "{\n" +
                            "    \"Creator\": \"\",\n" +
                            "    \"NeedUpDateFields\": [],\n" +
                            "    \"NeedReturnFields\": [],\n" +
                            "    \"IsDeleteEntry\": \"true\",\n" +
                            "    \"SubSystemId\": \"\",\n" +
                            "    \"IsVerifyBaseDataField\": \"true\",\n" +
                            "    \"IsEntryBatchFill\": \"true\",\n" +
                            "    \"ValidateFlag\": \"true\",\n" +
                            "    \"NumberSearch\": \"true\",\n" +
                            "    \"InterationFlags\": \"\",\n" +
                            "    \"IsAutoSubmitAndAudit\": \"false\",\n" +
                            "    \"Model\": {\n" +
                            "        \"FACCTSYSTEMID\": " + fACCTSYSTEMID + ",\n" +
                            "        \"FVersionsNO\": \"1.0.0\",\n" +
                            "        \"FISDefault\": true,\n" +
                            "        \"FIsOriginal\": false,\n" +
                            "        \"FCreateDate\": \"" + sdf.format(new Date()) + "\",\n" +
                            "        \"FIsCorporate\": true,\n" +
                            "        \"FAccountSystemEntry\": [\n" +
                            "            {\n" +
                            "                \"FMAINORGID\": {\n" +
                            "                    \"FNumber\": \"" + fMAINORGID + "\"\n" +
                            "                },\n" +
                            "                \"FACOUNTTYPE\": \"1\",\n" +
                            "                \"FDefAcctPolicy\": {\n" +
                            "                    \"FNumber\": \"KJZC01_SYS\"\n" +
                            "                },\n" +
                            "                \"FAPPACCTPOLICY\": [\n" +
                            "                    {\n" +
                            "                        \"FNumber\": \"KJZC01_SYS\"\n" +
                            "                    }\n" +
                            "                ],\n" +
                            "                \"FSubEntity\": " + subOrgArrayStr + "\n" +
                            "            }\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}";
                } else {
                    model="{\n" +
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
                            "        \"FACCTSYSTEMID\": 0,\n" +
                            "        \"FVersionsNO\": \"1.0.0\",\n" +
                            "        \"FNumber\": \""+fNumber+"\",\n" +
                            "        \"FName\": \""+fName+"\",\n" +
                            "        \"FISDefault\": false,\n" +
                            "        \"FIsOriginal\": false,\n" +
                            "        \"FCreateDate\": \""+sdf.format(new Date())+"\",\n" +
                            "        \"FIsCorporate\": false,\n" +
                            "        \"FAccountSystemEntry\": [\n" +
                            "            {\n" +
                            "                \"FMAINORGID\": {\n" +
                            "                    \"FNumber\": \""+fMAINORGID+"\"\n" +
                            "                },\n" +
                            "                \"FACOUNTTYPE\": \"1\",\n" +
                            "                \"FDefAcctPolicy\": {\n" +
                            "                    \"FNumber\": \"KJZC01_SYS\"\n" +
                            "                },\n" +
                            "                \"FAPPACCTPOLICY\": [\n" +
                            "                    {\n" +
                            "                        \"FNumber\": \"KJZC01_SYS\"\n" +
                            "                    }\n" +
                            "                ],\n" +
                            "                \"FSubEntity\": "+subOrgArrayStr+"\n" +
                            "            }\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}";
                }
                JSONObject jsonObject = InvokeHelper.Save("Org_AccountSystem", model);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("会计核算体系保存");
                    incident.setExcuteIncidentName("Org_AccountSystem,Save");
                    incident.setExcuteIncidentJSON(model);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }


                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }
    }

    @Override
    public ResponseResult deleteAccountingSystem(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setNumbers(resultList);
                baseDeleteBean.setIds(ids);
                String deleteEmp = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Delete("Org_AccountSystem", deleteEmp);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("会计核算体系删除");
                    incident.setExcuteIncidentName("Org_AccountSystem,Delete");
                    incident.setExcuteIncidentJSON(deleteEmp);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }
    }

    @Override
    public ResponseResult viewAccountingSystem(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseViewBean baseViewBean = new BaseViewBean();
                baseViewBean.setNumber(numbers);
                baseViewBean.setId(id);
                String a = JSON.toJSONString(baseViewBean);
                JSONObject jsonObject = InvokeHelper.View("Org_AccountSystem", a);

                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }
    }

    @Override
    public ResponseResult subimtAccountingSystem(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> numberList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
                baseSubmitBean.setNumbers(numberList);
                baseSubmitBean.setIds(ids);
                String submitEmp = JSON.toJSONString(baseSubmitBean);
                JSONObject jsonObject = InvokeHelper.Submit("Org_AccountSystem", submitEmp);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("会计核算体系提交");
                    incident.setExcuteIncidentName("Org_AccountSystem,Submit");
                    incident.setExcuteIncidentJSON(submitEmp);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }
    }

    @Override
    public ResponseResult auditAccountingSystem(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.Audit("Org_AccountSystem", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("会计核算体系审核");
                    incident.setExcuteIncidentName("Org_AccountSystem,Audit");
                    incident.setExcuteIncidentJSON(a);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }
    }

    @Override
    public ResponseResult unAuditAccountingSystem(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.UnAudit("Org_AccountSystem", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("会计核算体系反审核");
                    incident.setExcuteIncidentName("Org_AccountSystem,UnAudit");
                    incident.setExcuteIncidentJSON(a);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }
    }
}
