package com.lnmj.k3cloud.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.ICashAccountService;
import com.lnmj.k3cloud.business.IInventoryAccountService;
import com.lnmj.k3cloud.entity.bank.cash.CashAccountModel;
import com.lnmj.k3cloud.entity.bank.cash.CashAccountParam;
import com.lnmj.k3cloud.entity.bank.cash.Fcreateorgid;
import com.lnmj.k3cloud.entity.bank.cash.Fuseorgid;
import com.lnmj.k3cloud.entity.base.BaseDeleteBean;
import com.lnmj.k3cloud.entity.base.BaseSubmitBean;
import com.lnmj.k3cloud.entity.base.BaseViewBean;
import com.lnmj.k3cloud.entity.cost.inventoryaccounting.*;
import com.lnmj.k3cloud.entity.supplier.Allocate_Supplier;
import com.lnmj.k3cloud.pojo.Incident;
import com.lnmj.k3cloud.repository.IIncidentDao;
import com.lnmj.k3cloud.util.InvokeHelper;
import com.lnmj.k3cloud.util.K3cloudConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: yilihua
 * @Date: 2020-01-13 11:12
 * @Description:
 */
@Service("InventoryAccountServiceImpl")
public class InventoryAccountServiceImpl implements IInventoryAccountService {
    @Resource
    private IIncidentDao iIncidentDao;
    @Override
    public ResponseResult saveInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String fACCTGRANGEID, String fACCTGORGID, String fACCTGSYSTEMID,String fName,String jsonArraySubOrg) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (login) {
            try {
                List subOrgArray = new ArrayList();
                JSONArray subOrgArrayIn = JSON.parseArray(jsonArraySubOrg);
                for (int i = 0; i < subOrgArrayIn.size(); i++) {
                    JSONObject objectAll = new JSONObject(new LinkedHashMap());
                    objectAll.put("FOwnerType", "BD_OwnerOrg");

                    JSONObject objectFOwner = new JSONObject();
                    objectFOwner.put("FNumber", subOrgArrayIn.getJSONObject(i).getString("subK3Number"));
                    objectAll.put("FOwner", objectFOwner);
                    if (fACCTGRANGEID.equals("0")){
                        JSONObject objectFSTOCKORGID = new JSONObject();
                        objectFSTOCKORGID.put("FNumber", subOrgArrayIn.getJSONObject(i).getString("subK3Number"));
                        objectAll.put("FSTOCKORGID", objectFSTOCKORGID);
                    }

                    subOrgArray.add(objectAll);
                }
                String subOrgArrayStr = subOrgArray.toString();
                String model = null;
                if (!fACCTGRANGEID.equals("0")) {
                    model = "{\n" +
                            "    \"Creator\": \"\",\n" +
                            "    \"NeedUpDateFields\": [],\n" +
                            "    \"NeedReturnFields\": [],\n" +
                            "    \"IsDeleteEntry\": \"false\",\n" +
                            "    \"SubSystemId\": \"\",\n" +
                            "    \"IsVerifyBaseDataField\": \"false\",\n" +
                            "    \"IsEntryBatchFill\": \"true\",\n" +
                            "    \"ValidateFlag\": \"true\",\n" +
                            "    \"NumberSearch\": \"true\",\n" +
                            "    \"InterationFlags\": \"\",\n" +
                            "    \"IsAutoSubmitAndAudit\": \"false\",\n" +
                            "    \"Model\": {\n" +
                            "        \"FACCTGRANGEID\": " + fACCTGRANGEID + ",\n" +
                            "        \"FACCTGSYSTEMID\": {\n" +
                            "            \"FNumber\": \"KJHSTX01_SYS\"\n" +
                            "        },\n" +
                            "        \"FACCTGORGID\": {\n" +
                            "            \"FNumber\": \"" + fACCTGORGID + "\"\n" +
                            "        },\n" +
                            "        \"FACCTPOLICYID\": {\n" +
                            "            \"FNumber\": \"KJZC01_SYS\"\n" +
                            "        },\n" +
                            "        \"FDIVIDEDBASIS\": \"2\",\n" +
                            "        \"FCreateDate\": \"" + sdf.format(new Date()) + "\",\n" +
                            "        \"FEntity\": " + subOrgArrayStr + "\n" +
                            "    }\n" +
                            "}";
                } else {
                    model = "{\n" +
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
                            "    \"IsAutoSubmitAndAudit\": \"false\",\n" +
                            "    \"Model\": {\n" +
                            "        \"FACCTGRANGEID\": 0,\n" +
                            "        \"FACCTGSYSTEMID\": {\n" +
                            "            \"FNumber\": \""+fACCTGSYSTEMID+"\"\n" +
                            "        },\n" +
                            "        \"FACCTGORGID\": {\n" +
                            "            \"FNumber\": \""+fACCTGORGID+"\"\n" +
                            "        },\n" +
                            "        \"FACCTPOLICYID\": {\n" +
                            "            \"FNumber\": \"KJZC01_SYS\"\n" +
                            "        },\n" +
                            "        \"FName\": \""+fName+"\",\n" +
                            "        \"FDIVIDEDBASIS\": \"3\",\n" +
                            "        \"FCreateDate\": \""+sdf.format(new Date())+"\",\n" +
                            "        \"FEntity\": "+subOrgArrayStr+"\n" +
                            "    }\n" +
                            "}";
                }
                JSONObject jsonObect = InvokeHelper.Save("HS_ACCTGRANGE", model);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObect.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("核算范围保存");
                    incident.setExcuteIncidentName("HS_ACCTGRANGE,Save");
                    incident.setExcuteIncidentJSON(model);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }



                Map map = new HashMap();
                if (!((Map) (((Map) (jsonObect.get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
                    map.put("msg", "核算范围生成失败:" + ((Map) (((List) (((Map) (((Map) (jsonObect.get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
                    map.put("isSuccess", false);
                    return ResponseResult.success(map);
                }


                this.submitInventoryAccount(acctId, dataCentreUserName, dataCentrePassword, ((Map) jsonObect.get("Result")).get("Number").toString(), null);
                this.auditInventoryAccount(acctId, dataCentreUserName, dataCentrePassword, ((Map) jsonObect.get("Result")).get("Number").toString(), null);
                String heSuanFanWeiK3Number = ((Map) (((Map) (jsonObect.get("Result"))))).get("Number").toString();
                map.put("msg", heSuanFanWeiK3Number);
                map.put("isSuccess", true);
                return ResponseResult.success(map);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult batchSaveInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword) {
        return null;
    }

    @Override
    public ResponseResult viewInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseViewBean baseViewBean = new BaseViewBean();
            baseViewBean.setNumber(number);
            baseViewBean.setId(id);
            String s = JSON.toJSONString(baseViewBean);
            try {
                JSONObject jsonObject = InvokeHelper.View("HS_ACCTGRANGE", s);
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult submitInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Submit("HS_ACCTGRANGE", s);
                Boolean jsonObjectSubmitIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSubmitIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("核算范围提交");
                    incident.setExcuteIncidentName("HS_ACCTGRANGE,Submit");
                    incident.setExcuteIncidentJSON(s);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult auditInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Audit("HS_ACCTGRANGE", s);
                Boolean jsonObjectAuditIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectAuditIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("核算范围审核");
                    incident.setExcuteIncidentName("HS_ACCTGRANGE,Audit");
                    incident.setExcuteIncidentJSON(s);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult unAuditInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.UnAudit("HS_ACCTGRANGE", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("核算范围反审核");
                    incident.setExcuteIncidentName("HS_ACCTGRANGE,UnAudit");
                    incident.setExcuteIncidentJSON(s);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult deleteInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Delete("HS_ACCTGRANGE", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("核算范围反删除");
                    incident.setExcuteIncidentName("HS_ACCTGRANGE,Delete");
                    incident.setExcuteIncidentJSON(s);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult forbidInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Forbid("HS_ACCTGRANGE", "Forbid", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("核算范围反禁用");
                    incident.setExcuteIncidentName("HS_ACCTGRANGE,Forbid");
                    incident.setExcuteIncidentJSON(s);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult enableInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Enable("HS_ACCTGRANGE", "Enable", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("核算范围反启用");
                    incident.setExcuteIncidentName("HS_ACCTGRANGE,Enable");
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
