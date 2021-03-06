package com.lnmj.k3cloud.business.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.ICustomerService;
import com.lnmj.k3cloud.entity.base.*;
import com.lnmj.k3cloud.entity.customer.*;
import com.lnmj.k3cloud.pojo.Incident;
import com.lnmj.k3cloud.repository.IIncidentDao;
import com.lnmj.k3cloud.util.InvokeHelper;
import com.lnmj.k3cloud.util.K3cloudConfig;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description 客户金蝶service
 * @Param
 * @Return
 * @Author Mr.Ren
 * @Date 2019/11/11
 * @Time
 */
@Service("customerServiceImpl")
public class CustomerServiceImpl implements ICustomerService {
    @Resource
    private IIncidentDao iIncidentDao;
    @Override
    public ResponseResult customerSave(String acctId,
                                       String dataCentreUserName,
                                       String dataCentrePassword,
                                       String fName,
                                       String fNumber,
                                       String fINVOICETITLE,
                                       String fCreateOrgId,
                                       String fUseOrgId,
                                       String fCustTypeId,
                                       String needUpDateField,
                                       Integer fCUSTID) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                if (needUpDateField == null) {
                    needUpDateField = "";
                }
                if (fCUSTID == null) {
                    fCUSTID = 0;
                }
                String paramStr;
                if (fCUSTID == 0) {
                    paramStr = "{\n" +
                            "    \"Creator\": \"\",\n" +
                            "    \"NeedUpDateFields\": [\"" + needUpDateField + "\"],\n" +
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
                            "        \"FCUSTID\": " + fCUSTID + ",\n" +
                            "        \"FCreateOrgId\": {\n" +
                            "            \"FNumber\": \"" + fCreateOrgId + "\"\n" +
                            "        },\n" +
                            "        \"FNumber\": \"" + fNumber + "\",\n" +
                            "        \"FUseOrgId\": {\n" +
                            "            \"FNumber\": \"" + fUseOrgId + "\"\n" +
                            "        },\n" +
                            "        \"FName\": \"" + fName + "\",\n" +
                            "        \"FCOUNTRY\": {\n" +
                            "            \"FNumber\": \"China\"\n" +
                            "        },\n" +
                            "        \"FINVOICETITLE\": \"" + fINVOICETITLE + "\",\n" +
                            "        \"FIsDefPayer\": false,\n" +
                            "        \"FIsGroup\": true,\n" +
                            "        \"FCustTypeId\": {\n" +
                            "            \"FNumber\": \"" + fCustTypeId + "\"\n" +
                            "        },\n" +
                            "        \"FTRADINGCURRID\": {\n" +
                            "            \"FNumber\": \"PRE001\"\n" +
                            "        },\n" +
                            "        \"FInvoiceType\": \"1\",\n" +
                            "        \"FTaxType\": {\n" +
                            "            \"FNumber\": \"SFL02_SYS\"\n" +
                            "        },\n" +
                            "        \"FPriority\": 1,\n" +
                            "        \"FTaxRate\": {\n" +
                            "            \"FNumber\": \"SL02_SYS\"\n" +
                            "        },\n" +
                            "        \"FISCREDITCHECK\": true,\n" +
                            "        \"FIsTrade\": true,\n" +
                            "        \"FT_BD_CUSTOMEREXT\": {\n" +
                            "            \"FEnableSL\": false\n" +
                            "        }\n" +
                            "    }\n" +
                            "}";
                } else {
                    paramStr = "{\n" +
                            "    \"Creator\": \"\",\n" +
                            "    \"NeedUpDateFields\": [\"FName\"],\n" +
                            "    \"NeedReturnFields\": [],\n" +
                            "    \"IsDeleteEntry\": \"true\",\n" +
                            "    \"SubSystemId\": \"\",\n" +
                            "    \"IsVerifyBaseDataField\": \"false\",\n" +
                            "    \"IsEntryBatchFill\": \"true\",\n" +
                            "    \"ValidateFlag\": \"true\",\n" +
                            "    \"NumberSearch\": \"true\",\n" +
                            "    \"InterationFlags\": \"\",\n" +
                            "    \"IsAutoSubmitAndAudit\": \"false\",\n" +
                            "    \"Model\": {\n" +
                            "        \"FCUSTID\": " + fCUSTID + ",\n" +
                            "        \"FName\": \"" + fName + "\"\n" +
                            "    }\n" +
                            "}";
                }
                JSONObject jsonObject = InvokeHelper.Save("BD_Customer", paramStr);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("客户保存");
                    incident.setExcuteIncidentName("BD_Customer,Save");
                    incident.setExcuteIncidentJSON(paramStr);
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
    public ResponseResult customerSaveBatch(String acctId, String dataCentreUserName, String dataCentrePassword,String jsonArrayCust) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                List custArray = new ArrayList();
                JSONArray custArrayIn = JSON.parseArray(jsonArrayCust);
                for (int i = 0; i < custArrayIn.size(); i++) {
                    JSONObject objectAll = new JSONObject(new LinkedHashMap());
                    objectAll.put("FCUSTID",0);

                    JSONObject objectFCreateOrgId = new JSONObject();
                    objectFCreateOrgId.put("FNumber",custArrayIn.getJSONObject(i).getString("fCreateOrgId"));
                    objectAll.put("FCreateOrgId",objectFCreateOrgId);

                    objectAll.put("FNumber",custArrayIn.getJSONObject(i).getString("fNumber"));

                    JSONObject objectFUseOrgId = new JSONObject();
                    objectFUseOrgId.put("FNumber",custArrayIn.getJSONObject(i).getString("fUseOrgId"));
                    objectAll.put("FUseOrgId",objectFUseOrgId);

                    objectAll.put("FName",custArrayIn.getJSONObject(i).getString("fName"));

                    JSONObject objectFCOUNTRY = new JSONObject();
                    objectFCOUNTRY.put("FNumber","China");
                    objectAll.put("FCOUNTRY",objectFCOUNTRY);

                    objectAll.put("FINVOICETITLE",custArrayIn.getJSONObject(i).getString("fINVOICETITLE"));

                    objectAll.put("FIsDefPayer",false);
                    objectAll.put("FIsGroup",true);

                    JSONObject objectFCustTypeId = new JSONObject();
                    objectFCustTypeId.put("FNumber","China");
                    objectAll.put("FCustTypeId",objectFCustTypeId);

                    JSONObject objectFCorrespondOrgId = new JSONObject();
                    objectFCorrespondOrgId.put("FNumber",custArrayIn.getJSONObject(i).getString("fCorrespondOrgId"));
                    objectAll.put("FCorrespondOrgId",objectFCorrespondOrgId);

                    JSONObject objectFTRADINGCURRID = new JSONObject();
                    objectFTRADINGCURRID.put("FNumber","PRE001");
                    objectAll.put("FTRADINGCURRID",objectFTRADINGCURRID);

                    objectAll.put("FInvoiceType","1");

                    JSONObject objectFTaxType = new JSONObject();
                    objectFTaxType.put("FNumber","SFL02_SYS");
                    objectAll.put("FTaxType",objectFTaxType);

                    objectAll.put("FPriority","1");

                    JSONObject objectFTaxRate = new JSONObject();
                    objectFTaxRate.put("FNumber","SL02_SYS");
                    objectAll.put("FTaxRate",objectFTaxRate);

                    objectAll.put("FISCREDITCHECK",true);

                    objectAll.put("FIsTrade",true);

                    JSONObject objectFT_BD_CUSTOMEREXT = new JSONObject();
                    objectFT_BD_CUSTOMEREXT.put("FEnableSL",false);
                    objectAll.put("FT_BD_CUSTOMEREXT",objectFT_BD_CUSTOMEREXT);

                    custArray.add(objectAll);
                }
                String  custArrayStr = custArray.toString();



                String paramStr = "{\n" +
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
                        "    \"Model\": "+custArrayStr+"\n" +
                        "}";
                JSONObject jsonObject = InvokeHelper.BatchSave("BD_Customer", paramStr);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("客户批量保存");
                    incident.setExcuteIncidentName("BD_Customer,BatchSave");
                    incident.setExcuteIncidentJSON(paramStr);
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
    public ResponseResult customerDelete(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        List<String> resultList = null;
        if (numbers != null) {
            String[] numbersStrList = numbers.split(",");
            resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        }
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                //先反审核
                this.customerUnAudit(acctId, dataCentreUserName, dataCentrePassword, null, ids);
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setNumbers(resultList);
                baseDeleteBean.setIds(ids);
                String deleteEmp = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Delete("BD_Customer", deleteEmp);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("客户删除");
                    incident.setExcuteIncidentName("BD_Customer,Delete");
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
    public ResponseResult customerView(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseViewBean baseViewBean = new BaseViewBean();
                baseViewBean.setNumber(number);
                baseViewBean.setId(id);
                String a = JSON.toJSONString(baseViewBean);
                JSONObject jsonObject = InvokeHelper.View("BD_Customer", a);

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
    public ResponseResult customerSubimt(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> numberList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
                baseSubmitBean.setNumbers(numberList);
                baseSubmitBean.setIds(ids);
                String submitEmp = JSON.toJSONString(baseSubmitBean);
                JSONObject jsonObject = InvokeHelper.Submit("BD_Customer", submitEmp);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("客户提交");
                    incident.setExcuteIncidentName("BD_Customer,Submit");
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
    public ResponseResult customerAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.Audit("BD_Customer", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("客户审核");
                    incident.setExcuteIncidentName("BD_Customer,Audit");
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
    public ResponseResult customerUnAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        List<String> resultList = null;
        if (numbers != null) {
            String[] numbersStrList = numbers.split(",");
            resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        }
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.UnAudit("BD_Customer", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("客户反审核");
                    incident.setExcuteIncidentName("BD_Customer,UnAudit");
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
    public ResponseResult customerAllocate(String acctId, String dataCentreUserName, String dataCentrePassword, String PkIds, String TOrgIds, Boolean isautosubmitandaudit) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDistributionBean baseDistributionBean = new BaseDistributionBean();
                baseDistributionBean.setPkIds(PkIds);
                baseDistributionBean.setTOrgIds(TOrgIds);
                if (isautosubmitandaudit != null) {
                    baseDistributionBean.setAutoSubmitAndAudit(isautosubmitandaudit);
                }
                String a = JSON.toJSONString(baseDistributionBean);
                JSONObject jsonObject = InvokeHelper.Allocate("BD_Customer", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("客户分配");
                    incident.setExcuteIncidentName("BD_Customer,Allocate");
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
