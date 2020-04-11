package com.lnmj.k3cloud.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IBankAccountService;
import com.lnmj.k3cloud.entity.bank.account.*;
import com.lnmj.k3cloud.entity.base.BaseDeleteBean;
import com.lnmj.k3cloud.entity.base.BaseSubmitBean;
import com.lnmj.k3cloud.entity.base.BaseViewBean;
import com.lnmj.k3cloud.entity.supplier.Allocate_Supplier;
import com.lnmj.k3cloud.pojo.Incident;
import com.lnmj.k3cloud.repository.IIncidentDao;
import com.lnmj.k3cloud.util.InvokeHelper;
import com.lnmj.k3cloud.util.K3cloudConfig;
import org.apache.commons.lang3.StringUtils;
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
@Service("BankAccountServiceImpl")
public class BankAccountServiceImpl implements IBankAccountService {
    @Resource
    private IIncidentDao iIncidentDao;
    @Override
    public ResponseResult saveBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword,Boolean isautosubmitandaudit,
                                         String name, String number, String createOrgId, String useOrgId, String bankId,String upType) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
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
                        "        \"FBANKACNTID\": 0,\n" +
                        "        \"FCreateOrgId\": {\n" +
                        "            \"FNumber\": \""+createOrgId+"\"\n" +
                        "        },\n" +
                        "        \"FNumber\": \""+number+"\",\n" +
                        "        \"FBANKID\": {\n" +
                        "            \"FNumber\": \""+bankId+"\"\n" +
                        "        },\n" +
                        "        \"FName\": \""+name+"\",\n" +
                        "        \"FUseOrgId\": {\n" +
                        "            \"FNumber\": \""+useOrgId+"\"\n" +
                        "        },\n" +
                        "        \"FIsCancel\": false,\n" +
                        "        \"FIsFundUp\": true,\n" +
                        "        \"FISDEFAULTBANK\": false,\n" +
                        "        \"FIsSupBank\": false,\n" +
                        "        \"FUpType\": \""+upType+"\",\n" +
                        "        \"FIsSupBBC\": false\n" +
                        "    }\n" +
                        "}";
                JSONObject jsonObect = InvokeHelper.Save("CN_BANKACNT",param );

                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObect.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("银行账号保存");
                    incident.setExcuteIncidentName("CN_BANKACNT,Save");
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
    public ResponseResult batchSaveBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword) {
        return null;
    }

    @Override
    public ResponseResult viewBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseViewBean baseViewBean = new BaseViewBean();
            baseViewBean.setNumber(number);
            baseViewBean.setId(id);
            String s = JSON.toJSONString(baseViewBean);
            try {
                JSONObject jsonObject = InvokeHelper.View("CN_BANKACNT", s);
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult submitBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Submit("CN_BANKACNT", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("银行账号提交");
                    incident.setExcuteIncidentName("CN_BANKACNT,Submit");
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
    public ResponseResult auditBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Audit("CN_BANKACNT", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("银行账号审核");
                    incident.setExcuteIncidentName("CN_BANKACNT,Audit");
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
    public ResponseResult unAuditBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.UnAudit("CN_BANKACNT", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("银行账号反审核");
                    incident.setExcuteIncidentName("CN_BANKACNT,UnAudit");
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
    public ResponseResult deleteBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Delete("CN_BANKACNT", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("银行账号删除");
                    incident.setExcuteIncidentName("CN_BANKACNT,Delete");
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
    public ResponseResult forbidBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Forbid("CN_BANKACNT", "Forbid", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("银行账号禁用");
                    incident.setExcuteIncidentName("CN_BANKACNT,Forbid");
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
    public ResponseResult allocateBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String pkIds, String orgIds, Boolean isAutoSubmitAndAudit) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                Allocate_Supplier allocateSupplier = new Allocate_Supplier();
                allocateSupplier.setPkIds(pkIds);
                allocateSupplier.setTOrgIds(orgIds);
                allocateSupplier.setIsAutoSubmitAndAudit(isAutoSubmitAndAudit);
                String a = JSON.toJSONString(allocateSupplier);
                JSONObject jsonObject = InvokeHelper.Allocate("CN_BANKACNT", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("银行账号分配");
                    incident.setExcuteIncidentName("CN_BANKACNT,Allocate");
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
    public ResponseResult enableBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Enable("CN_BANKACNT", "Enable", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("银行账号启用");
                    incident.setExcuteIncidentName("CN_BANKACNT,Enable");
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
