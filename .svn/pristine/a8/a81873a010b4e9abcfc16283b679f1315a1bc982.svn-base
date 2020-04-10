package com.lnmj.k3cloud.business.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.ICustomerService;
import com.lnmj.k3cloud.business.IDepartmentService;
import com.lnmj.k3cloud.entity.base.BaseAuditBean;
import com.lnmj.k3cloud.entity.base.BaseDeleteBean;
import com.lnmj.k3cloud.entity.base.BaseSubmitBean;
import com.lnmj.k3cloud.entity.base.BaseViewBean;
import com.lnmj.k3cloud.entity.customer.*;
import com.lnmj.k3cloud.entity.department.DepartmentJsonRootBean;
import com.lnmj.k3cloud.entity.department.DepartmentModel;
import com.lnmj.k3cloud.entity.department.FCreateOrgId;
import com.lnmj.k3cloud.entity.department.FUseOrgId;
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

/**
 * @Description 客户金蝶service
 * @Param
 * @Return
 * @Author Mr.Ren
 * @Date 2019/11/11
 * @Time
 */
@Service("departmentServiceImpl")
public class DepartmentServiceImpl implements IDepartmentService {
    @Resource
    private IIncidentDao iIncidentDao;
    @Override
    public ResponseResult Save(String acctId, String dataCentreUserName, String dataCentrePassword, String fName, String fCreateOrgId, String fUseOrgId, String updateField, Integer fDEPTID) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                DepartmentJsonRootBean departmentJsonRootBean = new DepartmentJsonRootBean();
                if (updateField != null) {
                    List<String> stringListUpdate = new ArrayList<>();
                    stringListUpdate.add(updateField);
                    departmentJsonRootBean.setNeedupdatefields(stringListUpdate);
                    departmentJsonRootBean.setIsautosubmitandaudit(false);
                }else{
                    departmentJsonRootBean.setIsautosubmitandaudit(true);
                }
                DepartmentModel departmentModel = new DepartmentModel();
                departmentModel.setFName(fName);
                departmentModel.setFCreateOrgId(new FCreateOrgId(fCreateOrgId));
                departmentModel.setFUseOrgId(new FUseOrgId(fUseOrgId));
                if (fDEPTID != null) {
                    departmentModel.setFDEPTID(fDEPTID);
                }
                departmentJsonRootBean.setModel(departmentModel);
                String department;
                if (fDEPTID == null){
                     department = JSON.toJSONString(departmentJsonRootBean, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                }else{
                     department = JSON.toJSONString(departmentJsonRootBean);
                }
                JSONObject jsonObject = InvokeHelper.Save("BD_Department", department);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("部门保存");
                    incident.setExcuteIncidentName("BD_Department,Save");
                    incident.setExcuteIncidentJSON(department);
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
    public ResponseResult Delete(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        List<String> resultList = null;
        if (numbers!=null){
            String[] numbersStrList = numbers.split(",");
             resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        }
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                //先反审核
                this.UnAudit(acctId, dataCentreUserName, dataCentrePassword,null,ids);
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setNumbers(resultList);
                baseDeleteBean.setIds(ids);
                String deleteEmp = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Delete("BD_Department", deleteEmp);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("部门删除");
                    incident.setExcuteIncidentName("BD_Department,Delete");
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
    public ResponseResult View(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseViewBean baseViewBean = new BaseViewBean();
                baseViewBean.setNumber(number);
                baseViewBean.setId(id);
                String a = JSON.toJSONString(baseViewBean);
                JSONObject jsonObject = InvokeHelper.View("BD_Department", a);

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
    public ResponseResult Subimt(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> numberList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
                baseSubmitBean.setNumbers(numberList);
                baseSubmitBean.setIds(ids);
                String submitEmp = JSON.toJSONString(baseSubmitBean);
                JSONObject jsonObject = InvokeHelper.Submit("BD_Department", submitEmp);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("部门提交");
                    incident.setExcuteIncidentName("BD_Department,Submit");
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
    public ResponseResult Audit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.Audit("BD_Department", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("部门审核");
                    incident.setExcuteIncidentName("BD_Department,Audit");
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
    public ResponseResult UnAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        List<String> resultList = null;
        if (numbers!=null){
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
                JSONObject jsonObject = InvokeHelper.UnAudit("BD_Department", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("部门反审核");
                    incident.setExcuteIncidentName("BD_Department,UnAudit");
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
