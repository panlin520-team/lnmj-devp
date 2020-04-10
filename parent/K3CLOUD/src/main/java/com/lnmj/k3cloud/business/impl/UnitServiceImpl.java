package com.lnmj.k3cloud.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IUnitService;
import com.lnmj.k3cloud.entity.base.BaseAuditBean;
import com.lnmj.k3cloud.entity.base.BaseDeleteBean;
import com.lnmj.k3cloud.entity.base.BaseSubmitBean;
import com.lnmj.k3cloud.entity.base.BaseViewBean;
import com.lnmj.k3cloud.entity.product.unit.FUnitGroupId;
import com.lnmj.k3cloud.entity.product.unit.UnitModel;
import com.lnmj.k3cloud.entity.product.unit.UnitSaveParam;
import com.lnmj.k3cloud.pojo.Incident;
import com.lnmj.k3cloud.repository.IIncidentDao;
import com.lnmj.k3cloud.util.InvokeHelper;
import com.lnmj.k3cloud.util.K3cloudConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service("UnitServiceImpl")
public class UnitServiceImpl implements IUnitService {
    @Resource
    private IIncidentDao iIncidentDao;
    @Override
    public ResponseResult saveUnit(String acctId, String dataCentreUserName, String dataCentrePassword, String fname, String groupId, String froundtype, Integer id, Boolean isautosubmitandaudit){
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if(login){

            UnitSaveParam unitSaveParam = new UnitSaveParam();
            UnitModel unitModel = new UnitModel();
            unitModel.setFname(fname);
            FUnitGroupId fUnitGroupId = new FUnitGroupId();
            fUnitGroupId.setFnumber(groupId);
            unitModel.setFUnitGroupId(fUnitGroupId);
            unitModel.setFroundtype(froundtype);
            unitSaveParam.setModel(unitModel);
            if (isautosubmitandaudit != null) {
                unitSaveParam.setIsautosubmitandaudit(isautosubmitandaudit);
            }
            if (id != null) {
                unitModel.setFunitid(id);
            }
            String json = JSON.toJSONString(unitSaveParam);
            try {
                JSONObject save = InvokeHelper.Save("BD_UNIT", json);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)save.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("单位保存");
                    incident.setExcuteIncidentName("BD_UNIT,Save");
                    incident.setExcuteIncidentJSON(json);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return  ResponseResult.success(save);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult enableUnit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Enable("BD_UNIT","Enable", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("单位启用");
                    incident.setExcuteIncidentName("BD_UNIT,Enable");
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
    public ResponseResult forbidUnit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Forbid("BD_UNIT","Forbid", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("单位禁用");
                    incident.setExcuteIncidentName("BD_UNIT,Forbid");
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
    public ResponseResult deleteUnit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Delete("BD_UNIT", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("单位删除");
                    incident.setExcuteIncidentName("BD_UNIT,Delete");
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
    public ResponseResult unAuditUnit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setIds(ids);
                baseAuditBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.UnAudit("BD_UNIT", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("单位反审核");
                    incident.setExcuteIncidentName("BD_UNIT,UnAudit");
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
    public ResponseResult auditUnit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setIds(ids);
                baseAuditBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.Audit("BD_UNIT", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("单位审核");
                    incident.setExcuteIncidentName("BD_UNIT,Audit");
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
    public ResponseResult submitUnit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
                baseSubmitBean.setIds(ids);
                baseSubmitBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseSubmitBean);
                JSONObject jsonObject = InvokeHelper.Submit("BD_UNIT", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("单位提交");
                    incident.setExcuteIncidentName("BD_UNIT,Submit");
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
    public ResponseResult viewUnit(String acctId,String dataCentreUserName,String dataCentrePassword,String number, String id) {
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseViewBean baseViewBean = new BaseViewBean();
                baseViewBean.setNumber(number);
                baseViewBean.setId(id);
                String a = JSON.toJSONString(baseViewBean);
                JSONObject jsonObject = InvokeHelper.View("BD_UNIT", a);

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
