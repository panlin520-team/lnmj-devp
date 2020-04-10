package com.lnmj.k3cloud.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.ICashAccountService;
import com.lnmj.k3cloud.entity.bank.cash.CashAccountModel;
import com.lnmj.k3cloud.entity.bank.cash.CashAccountParam;
import com.lnmj.k3cloud.entity.bank.cash.Fcreateorgid;
import com.lnmj.k3cloud.entity.bank.cash.Fuseorgid;
import com.lnmj.k3cloud.entity.base.BaseDeleteBean;
import com.lnmj.k3cloud.entity.base.BaseSubmitBean;
import com.lnmj.k3cloud.entity.base.BaseViewBean;
import com.lnmj.k3cloud.entity.supplier.Allocate_Supplier;
import com.lnmj.k3cloud.pojo.Incident;
import com.lnmj.k3cloud.repository.IIncidentDao;
import com.lnmj.k3cloud.util.InvokeHelper;
import com.lnmj.k3cloud.util.K3cloudConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2020-01-13 11:12
 * @Description:
 */
@Service("CashAccountServiceImpl")
public class CashAccountServiceImpl implements ICashAccountService {
    @Resource
    private IIncidentDao iIncidentDao;
    @Override
    public ResponseResult saveCashAccount(String acctId,String dataCentreUserName,String dataCentrePassword,Boolean isautosubmitandaudit,String number,String name,String createOrgId,String useOrgid) {
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if(login){
            try {
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
                        "    \"IsAutoSubmitAndAudit\": \""+isautosubmitandaudit+"\",\n" +
                        "    \"Model\": {\n" +
                        "        \"FID\": 0,\n" +
                        "        \"FNumber\": \""+number+"\",\n" +
                        "        \"FCreateOrgId\": {\n" +
                        "            \"FNumber\": \""+createOrgId+"\"\n" +
                        "        },\n" +
                        "        \"FUseOrgId\": {\n" +
                        "            \"FNumber\": \""+useOrgid+"\"\n" +
                        "        },\n" +
                        "        \"FName\": \""+name+"\"\n" +
                        "    }\n" +
                        "}";
                JSONObject jsonObect = InvokeHelper.Save("CN_CASHACCOUNT", param);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObect.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("现金账号保存");
                    incident.setExcuteIncidentName("CN_CASHACCOUNT,Save");
                    incident.setExcuteIncidentJSON(param);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }

                return ResponseResult.success(jsonObect);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult batchSaveCashAccount(String acctId,String dataCentreUserName,String dataCentrePassword) {
        return null;
    }

    @Override
    public ResponseResult viewCashAccount(String acctId,String dataCentreUserName,String dataCentrePassword,String number, String id) {
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if(login){
            BaseViewBean baseViewBean = new BaseViewBean();
            baseViewBean.setNumber(number);
            baseViewBean.setId(id);
            String s = JSON.toJSONString(baseViewBean);
            try {
                JSONObject jsonObject = InvokeHelper.View("CN_CASHACCOUNT", s);
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult submitCashAccount(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if(login){
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Submit("CN_CASHACCOUNT", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("现金账号提交");
                    incident.setExcuteIncidentName("CN_CASHACCOUNT,Submit");
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
    public ResponseResult auditCashAccount(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if(login){
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Audit("CN_CASHACCOUNT", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("现金账号审核");
                    incident.setExcuteIncidentName("CN_CASHACCOUNT,Audit");
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
    public ResponseResult unAuditCashAccount(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if(login){
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.UnAudit("CN_CASHACCOUNT", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("现金账号反审核");
                    incident.setExcuteIncidentName("CN_CASHACCOUNT,UnAudit");
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
    public ResponseResult deleteCashAccount(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if(login){
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Delete("CN_CASHACCOUNT", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("现金账号反删除");
                    incident.setExcuteIncidentName("CN_CASHACCOUNT,Delete");
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
    public ResponseResult forbidCashAccount(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if(login){
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Forbid("CN_CASHACCOUNT","Forbid", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("现金账号禁用");
                    incident.setExcuteIncidentName("CN_CASHACCOUNT,Forbid");
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
    public ResponseResult allocateCashAccount(String acctId,String dataCentreUserName,String dataCentrePassword,String pkIds, String orgIds, Boolean isAutoSubmitAndAudit) {
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                Allocate_Supplier allocateSupplier = new Allocate_Supplier();
                allocateSupplier.setPkIds(pkIds);
                allocateSupplier.setTOrgIds(orgIds);
                allocateSupplier.setIsAutoSubmitAndAudit(isAutoSubmitAndAudit);
                String a = JSON.toJSONString(allocateSupplier);
                JSONObject jsonObject = InvokeHelper.Allocate("CN_CASHACCOUNT", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("现金账号分配");
                    incident.setExcuteIncidentName("CN_CASHACCOUNT,Allocate");
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
    public ResponseResult enableCashAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Enable("BD_StockStatus","Enable", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("现金账号启用");
                    incident.setExcuteIncidentName("CN_CASHACCOUNT,Enable");
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
