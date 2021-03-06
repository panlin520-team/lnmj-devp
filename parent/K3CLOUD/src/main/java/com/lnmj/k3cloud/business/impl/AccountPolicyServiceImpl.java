package com.lnmj.k3cloud.business.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.AccountPolicyService;
import com.lnmj.k3cloud.entity.account.accountPolicy.*;
import com.lnmj.k3cloud.entity.account.accountPolicy.FDeprPolicyID;
import com.lnmj.k3cloud.entity.base.*;
import com.lnmj.k3cloud.entity.product.product.addSendParam.ProductAddModel;
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
    *@Description 客户金蝶service
    *@Param
    *@Return
    *@Author Mr.Ren
    *@Date 2019/11/11
    *@Time
    */
@Service("accountPolicyServiceImpl")
public class AccountPolicyServiceImpl implements AccountPolicyService {
    @Resource
    private IIncidentDao iIncidentDao;
    @Override
    public ResponseResult saveAccountPolicy(String acctId, String dataCentreUserName, String dataCentrePassword,String model, Boolean isautosubmitandaudit) {
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                JSONObject jsonmodel = JSON.parseObject(model);
                AccountPolicyModel accountPolicyModel = JSONObject.parseObject(jsonmodel.toJSONString(),AccountPolicyModel.class);
                AccountPolicyJsonRootBean accountPolicyJsonRootBean = new AccountPolicyJsonRootBean();
                if (isautosubmitandaudit != null) {
                    accountPolicyJsonRootBean.setIsautosubmitandaudit(isautosubmitandaudit);
                }
                accountPolicyJsonRootBean.setModel(accountPolicyModel);

                String AccountPolicy = JSON.toJSONString(accountPolicyJsonRootBean);
                JSONObject jsonObject = InvokeHelper.Save("BD_ACCTPOLICY",AccountPolicy);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("会计政策保存");
                    incident.setExcuteIncidentName("BD_ACCTPOLICY,Save");
                    incident.setExcuteIncidentJSON(AccountPolicy);
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
    public ResponseResult deleteAccountPolicy(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        List<String> resultList = new ArrayList();
        if (StringUtils.isNotBlank(numbers)) {
            String[] numbersStrList = numbers.split(",");
            resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        }
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setNumbers(resultList);
                baseDeleteBean.setIds(ids);
                String deleteEmp = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Delete("BD_ACCTPOLICY",deleteEmp);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("会计政策删除");
                    incident.setExcuteIncidentName("BD_ACCTPOLICY,Delete");
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
    public ResponseResult viewAccountPolicy(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String id) {
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseViewBean baseViewBean = new BaseViewBean();
                baseViewBean.setNumber(numbers);
                baseViewBean.setId(id);
                String a = JSON.toJSONString(baseViewBean);
                JSONObject jsonObject = InvokeHelper.View("BD_ACCTPOLICY", a);
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
    public ResponseResult subimtAccountPolicy(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        List<String> numberList = new ArrayList();
        if (StringUtils.isNotBlank(numbers)) {
            String[] numbersStrList = numbers.split(",");
            numberList = new ArrayList<>(Arrays.asList(numbersStrList));
        }
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
                baseSubmitBean.setNumbers(numberList);
                baseSubmitBean.setIds(ids);
                String submitEmp = JSON.toJSONString(baseSubmitBean);
                JSONObject jsonObject = InvokeHelper.Submit("BD_ACCTPOLICY",submitEmp);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("会计政策提交");
                    incident.setExcuteIncidentName("BD_ACCTPOLICY,Submit");
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
    public ResponseResult auditAccountPolicy(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        List<String> resultList = new ArrayList();
        if (StringUtils.isNotBlank(numbers)) {
            String[] numbersStrList = numbers.split(",");
            resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        }
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.Audit("BD_ACCTPOLICY", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("会计政策审核");
                    incident.setExcuteIncidentName("BD_ACCTPOLICY,Audit");
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
    public ResponseResult unAuditAccountPolicy(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        List<String> resultList = new ArrayList();
        if (StringUtils.isNotBlank(numbers)) {
            String[] numbersStrList = numbers.split(",");
            resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        }
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.UnAudit("BD_ACCTPOLICY", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("会计政策反审核");
                    incident.setExcuteIncidentName("BD_ACCTPOLICY,UnAudit");
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
    public ResponseResult accountPolicyAllocate(String acctId, String dataCentreUserName, String dataCentrePassword, String pkIds, String tOrgIds) {
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseDistributionBean baseDistributionBean = new BaseDistributionBean();
                baseDistributionBean.setPkIds(pkIds);
                baseDistributionBean.setTOrgIds(tOrgIds);
                String a = JSON.toJSONString(baseDistributionBean);
                JSONObject jsonObject = InvokeHelper.Allocate("BD_ACCTPOLICY", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("会计政策分配");
                    incident.setExcuteIncidentName("BD_ACCTPOLICY,Allocate");
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
