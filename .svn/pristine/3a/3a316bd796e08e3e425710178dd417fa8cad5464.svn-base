package com.lnmj.k3cloud.business.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IPostService;
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
 * @Author: panlin
 * @Date: 2019-11-08 15:09
 * @Description: 金蝶service
 */
@Service("postServiceImpl")
public class PostServiceImpl implements IPostService {
    @Resource
    private IIncidentDao iIncidentDao;
    @Override
    public ResponseResult postSave(String acctId, String dataCentreUserName, String dataCentrePassword, String fName,
                                   String fCreateOrgId,
                                   String fUseOrgId,
                                   String fDept) {
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                /*PostAddJsonsRootBean postAddJsonsRootBean = new PostAddJsonsRootBean();
                PostAddModel model = new PostAddModel();
                model.setFname(fName);
                model.setFcreateorgid(new Fcreateorgid(fCreateOrgId));
                model.setFuseorgid(new Fuseorgid(fUseOrgId));
                model.setFeffectdate(fEffectDate);
                model.setFdept(new Fdept(fDept));

                JSONArray fpostentityListArray = JSON.parseArray(fPostRoleListStr);
                List<Fpostroleentitys> fpostroleentitys = new ArrayList<>();
                for (int i = 0; i < fpostentityListArray.size(); i++) {
                    String a = fpostentityListArray.getJSONObject(i).getString("fPostRole");
                    Fpostroleentitys fpostentity = new Fpostroleentitys();
                    fpostentity.setFpostrole(new Fpostrole(a));
                    fpostroleentitys.add(fpostentity);
                }
                model.setFpostroleentitys(fpostroleentitys);
                JSONArray fReportEntitysListArray = JSON.parseArray(fReportEntitysListStr);
                List<Freportentitys> freportentitysList = new ArrayList<>();
                for (int i = 0; i < fReportEntitysListArray.size(); i++) {
                    String a = fReportEntitysListArray.getJSONObject(i).getString("fSuperiorReportType");
                    String b = fReportEntitysListArray.getJSONObject(i).getString("fSuperiorPost");
                    Freportentitys freportentitys = new Freportentitys();
                    freportentitys.setFsuperiorpost(new Fsuperiorpost(a));
                    freportentitys.setFsuperiorreporttype(new Fsuperiorreporttype(b));
                    freportentitysList.add(freportentitys);
                }
                model.setFpostroleentitys(fpostroleentitys);
                postAddJsonsRootBean.setModel(model);
                String a = JSON.toJSONString(postAddJsonsRootBean);*/


                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String nowDate = format.format(date);

                String model = "{\n" +
                        "    \"IsAutoSubmitAndAudit\": \"true\",\n" +
                        "    \"Model\": {\n" +
                        "        \"FPOSTID\": 0,\n" +
                        "        \"FCreateOrgId\": {\n" +
                        "            \"FNumber\": \""+fCreateOrgId+"\"\n" +
                        "        },\n" +
                        "        \"FUseOrgId\": {\n" +
                        "            \"FNumber\": \""+fUseOrgId+"\"\n" +
                        "        },\n" +
                        "        \"FName\": \""+fName+"\",\n" +
                        "        \"FDept\": {\n" +
                        "            \"FNumber\": \""+fDept+"\"\n" +
                        "        },\n" +
                        "        \"FEffectDate\": \""+nowDate+"\",\n" +
                        "        \"FLapseDate\": \"9999-12-31 00:00:00\",\n" +
                        "        \"FHRPostSubHead\": {\n" +
                        "            \"FLEADERPOST\": false\n" +
                        "        },\n" +
                        "        \"FSHRMapEntity\": {}\n" +
                        "    }\n" +
                        "}";

                JSONObject jsonObject = InvokeHelper.Save("HR_ORG_HRPOST", model);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("岗位保存");
                    incident.setExcuteIncidentName("HR_ORG_HRPOST,Save");
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
    public ResponseResult postDelete(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setNumbers(resultList);
                baseDeleteBean.setIds(ids);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Delete("HR_ORG_HRPOST", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("岗位删除");
                    incident.setExcuteIncidentName("HR_ORG_HRPOST,Delete");
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
    public ResponseResult postView(String acctId,String dataCentreUserName,String dataCentrePassword,String number, String id) {
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseViewBean baseViewBean = new BaseViewBean();
                baseViewBean.setNumber(number);
                baseViewBean.setId(id);
                String a = JSON.toJSONString(baseViewBean);
                JSONObject jsonObject = InvokeHelper.View("HR_ORG_HRPOST", a);

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
    public ResponseResult postSubimt(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
                baseSubmitBean.setNumbers(resultList);
                baseSubmitBean.setIds(ids);
                String a = JSON.toJSONString(baseSubmitBean);
                JSONObject jsonObject = InvokeHelper.Submit("HR_ORG_HRPOST", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("岗位提交");
                    incident.setExcuteIncidentName("HR_ORG_HRPOST,Submit");
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
    public ResponseResult postAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.Audit("HR_ORG_HRPOST", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("岗位审核");
                    incident.setExcuteIncidentName("HR_ORG_HRPOST,Audit");
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
    public ResponseResult postUnAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId,dataCentreUserName,dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.UnAudit("HR_ORG_HRPOST", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("岗位反审核");
                    incident.setExcuteIncidentName("HR_ORG_HRPOST,UnAudit");
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
