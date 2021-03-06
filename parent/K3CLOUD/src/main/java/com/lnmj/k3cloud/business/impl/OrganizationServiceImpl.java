package com.lnmj.k3cloud.business.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IOrganizationService;
import com.lnmj.k3cloud.entity.base.*;
import com.lnmj.k3cloud.entity.organization.organization.addSendParam.Fparentid;
import com.lnmj.k3cloud.entity.organization.organization.addSendParam.OrganizationAddJsonsRootBean;
import com.lnmj.k3cloud.entity.organization.organization.addSendParam.OrganizationAddModel;
import com.lnmj.k3cloud.entity.organization.organizationForm.addSendParam.FormAddJsonsRootBean;
import com.lnmj.k3cloud.entity.organization.organizationForm.addSendParam.FormAddModel;
import com.lnmj.k3cloud.entity.organization.organizationKongZhiCeNue.addSendParam.*;
import com.lnmj.k3cloud.entity.organization.organizationLiShuGuanXi.addSendParam.*;
import com.lnmj.k3cloud.entity.organization.organizationLiShuGuanXi.addSendParam.Forgid;
import com.lnmj.k3cloud.entity.organization.organizationYeWuZuZhi.addSendParam.*;
import com.lnmj.k3cloud.pojo.Incident;
import com.lnmj.k3cloud.repository.IIncidentDao;
import com.lnmj.k3cloud.repository.IK3CLOUDDao;
import com.lnmj.k3cloud.util.InvokeHelper;
import com.lnmj.k3cloud.util.K3cloudConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: panlin
 * @Date: 2019-11-08 15:09
 * @Description: 金蝶service
 */
@Service("organizationServiceImpl")
public class OrganizationServiceImpl implements IOrganizationService {
    @Resource
    private IK3CLOUDDao ik3CLOUDDao;
    @Resource
    private IIncidentDao iIncidentDao;

    //组织形态
    @Override
    public ResponseResult formSave(String acctId, String dataCentreUserName, String dataCentrePassword, String fname, String fNumber) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                FormAddJsonsRootBean formAddJsonsRootBean = new FormAddJsonsRootBean();
                FormAddModel model = new FormAddModel();
                model.setFname(fname);
                model.setFnumber(fNumber);
                formAddJsonsRootBean.setModel(model);
                String a = JSON.toJSONString(formAddJsonsRootBean);
                JSONObject jsonObject = InvokeHelper.Save("ORG_OrgBodyForm", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织形态保存");
                    incident.setExcuteIncidentName("ORG_OrgBodyForm,Save");
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
    public ResponseResult formDelete(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setNumbers(resultList);
                baseDeleteBean.setIds(ids);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Delete("ORG_OrgBodyForm", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织形态删除");
                    incident.setExcuteIncidentName("ORG_OrgBodyForm,Delete");
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
    public ResponseResult formView(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseViewBean baseViewBean = new BaseViewBean();
                baseViewBean.setNumber(number);
                baseViewBean.setId(id);
                String a = JSON.toJSONString(baseViewBean);
                JSONObject jsonObject = InvokeHelper.View("ORG_OrgBodyForm", a);

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
    public ResponseResult formSubimt(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {

        List<String> resultList = null;
        if (numbers != null) {
            String[] numbersStrList = numbers.split(",");
            resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        }

        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
                baseSubmitBean.setNumbers(resultList);
                baseSubmitBean.setIds(ids);
                String a = JSON.toJSONString(baseSubmitBean);
                JSONObject jsonObject = InvokeHelper.Submit("ORG_OrgBodyForm", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织形态提交");
                    incident.setExcuteIncidentName("ORG_OrgBodyForm,Submit");
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
    public ResponseResult formAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.Audit("ORG_OrgBodyForm", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织形态审核");
                    incident.setExcuteIncidentName("ORG_OrgBodyForm,Audit");
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
    public ResponseResult formUnAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.UnAudit("ORG_OrgBodyForm", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织形态反审核");
                    incident.setExcuteIncidentName("ORG_OrgBodyForm,UnAudit");
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

    //组织机构
    @Override
    public ResponseResult organizationSave(String fOrgID, String acctId, String dataCentreUserName, String dataCentrePassword, String fname, String fNumber, String fOrgFormID, Boolean fIsAccountOrg, String fAcctOrgType, String fParentID, Boolean fIsBusinessOrg, String fOrgFunctions, Boolean fStockBox) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                OrganizationAddJsonsRootBean organizationAddJsonsRootBean = new OrganizationAddJsonsRootBean();
                OrganizationAddModel model = new OrganizationAddModel();
                model.setFname(fname);
                model.setFnumber(fNumber);
                model.setForgformid(fOrgFormID);
                model.setFisaccountorg(fIsAccountOrg);
                model.setFacctorgtype(fAcctOrgType);
                model.setFisbusinessorg(fIsBusinessOrg);
                model.setFparentid(new Fparentid(fParentID));
                model.setForgfunctions(fOrgFunctions);

                model.setFSaleBox(true);
                model.setFPurchaseBox(true);
                model.setFStockBox(true);
                model.setFFactoryBox(true);
                model.setFQualityBox(true);
                model.setFClearingBox(true);
                model.setFAssetBox(true);
                model.setFCapitalBox(true);
                model.setFReceiptAndPayBox(true);
                model.setFMarketing(true);
                model.setFService(true);
                model.setFShareCenter(true);
                if (StringUtils.isNotBlank(fOrgID)) {
                    model.setForgid(Integer.parseInt(fOrgID));
                }
                organizationAddJsonsRootBean.setModel(model);
                organizationAddJsonsRootBean.setIsautosubmitandaudit(true);
                String a = JSON.toJSONString(organizationAddJsonsRootBean, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONObject jsonObjectSave = InvokeHelper.Save("ORG_Organizations", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObjectSave.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织保存");
                    incident.setExcuteIncidentName("ORG_Organizations,Save");
                    incident.setExcuteIncidentJSON(a);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                //提交 审核
                this.organizationSubimt(acctId, dataCentreUserName, dataCentrePassword, fNumber, null);
                this.organizationAudit(acctId, dataCentreUserName, dataCentrePassword, fNumber, null);


                return ResponseResult.success(jsonObjectSave);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }
    }

    @Override
    public ResponseResult organizationDelete(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setNumbers(resultList);
                baseDeleteBean.setIds(ids);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Delete("ORG_Organizations", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织删除");
                    incident.setExcuteIncidentName("ORG_Organizations,Delete");
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
    public ResponseResult organizationView(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseViewBean baseViewBean = new BaseViewBean();
                baseViewBean.setNumber(number);
                baseViewBean.setId(id);
                String a = JSON.toJSONString(baseViewBean);
                JSONObject jsonObject = InvokeHelper.View("ORG_Organizations", a);
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
    public ResponseResult organizationSubimt(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        List<String> resultList = null;
        if (numbers != null) {
            String[] numbersStrList = numbers.split(",");
            resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        }

        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
                baseSubmitBean.setNumbers(resultList);
                baseSubmitBean.setIds(ids);
                String a = JSON.toJSONString(baseSubmitBean);
                JSONObject jsonObject = InvokeHelper.Submit("ORG_Organizations", a);
                Boolean jsonObjectSubmitIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSubmitIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织提交");
                    incident.setExcuteIncidentName("ORG_Organizations,Submit");
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
    public ResponseResult organizationAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
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
                JSONObject jsonObject = InvokeHelper.Audit("ORG_Organizations", a);
                Boolean jsonObjectAuditIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectAuditIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织审核");
                    incident.setExcuteIncidentName("ORG_Organizations,Audit");
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
    public ResponseResult organizationUnAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.UnAudit("ORG_Organizations", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织反审核");
                    incident.setExcuteIncidentName("ORG_Organizations,UnAudit");
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
    public ResponseResult updateOrgFunctions(String dataCentreName, String orgId) {
        ik3CLOUDDao.updateOrgFunctions(dataCentreName, orgId);
        return ResponseResult.success();
    }

    //组织隶属关系
    @Override
    public ResponseResult organizationLiShuGuanXiSave(String fParentOrgId, String fOrgId, String acctId, String dataCentreUserName, String dataCentrePassword, String fname, String fNumber, String fRootOrgID, String fType, String fEndDate, String fStartDate, String fBackupOrg, String fAFFILIATIONID) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                OrganizationLiShuGuanXiAddJsonsRootBean organizationLiShuGuanXiAddJsonsRootBean = new OrganizationLiShuGuanXiAddJsonsRootBean();
                OrganizationLiShuGuanXiAddModel model = new OrganizationLiShuGuanXiAddModel();
               /* model.setFname(fname);
                model.setFnumber(fNumber);*/
                model.setFrootorgid(new Frootorgid(fRootOrgID));
                /* model.setFtype(new Ftype("001"));*/
                Date startDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/*                model.setFstartdate(sdf.format(startDate));
                model.setFenddate("9999-12-31");*/
                List<Faffiliationentry> faffiliationentryList = new ArrayList<>();
                Faffiliationentry faffiliationentry = new Faffiliationentry();
                faffiliationentry.setFparentorgid(new Fparentorgid(fParentOrgId));
                faffiliationentry.setForgid(new Forgid(fOrgId));
                faffiliationentryList.add(faffiliationentry);
                model.setFaffiliationentry(faffiliationentryList);
                List<Fbackuporgentity> fbackuporgentity = new ArrayList<>();
                fbackuporgentity.add(new Fbackuporgentity(fBackupOrg));
                model.setFbackuporgentity(fbackuporgentity);
                if (StringUtils.isNotBlank(fAFFILIATIONID)) {
                    model.setFaffiliationid(fAFFILIATIONID);
                }

                organizationLiShuGuanXiAddJsonsRootBean.setModel(model);
                organizationLiShuGuanXiAddJsonsRootBean.setIsautosubmitandaudit(true);
                String a = JSON.toJSONString(organizationLiShuGuanXiAddJsonsRootBean, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONObject jsonObject = InvokeHelper.Save("ORG_Affiliation", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织隶属关系保存");
                    incident.setExcuteIncidentName("ORG_Affiliation,Save");
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
    public ResponseResult organizationLiShuGuanXiBatchSave(String fParentOrgId, String fOrgId, String acctId, String dataCentreUserName, String dataCentrePassword, String fname, String fNumber, String fRootOrgID, String fType, String fEndDate, String fStartDate, String fBackupOrg, String fAFFILIATIONID, String jsonArray, Integer type, Boolean isDeleteEntry) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);

        if (login) {
            try {
                JSONArray jsonArrayFAffiliationEntry = new JSONArray();
                JSONObject objectAll1 = new JSONObject(new LinkedHashMap());

                JSONObject objectFOrgId = new JSONObject();
                objectFOrgId.put("FNumber", fOrgId);
                objectAll1.put("FOrgId", objectFOrgId);

                JSONObject objectFParentOrgId = new JSONObject();
                objectFParentOrgId.put("FNumber", fParentOrgId);
                objectAll1.put("FParentOrgId", objectFParentOrgId);

                jsonArrayFAffiliationEntry.add(objectAll1);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List<JSONObject> lishuArray = new ArrayList();
                JSONArray lishuArrayIn = JSON.parseArray(jsonArray);


                for (int i = 0; i < lishuArrayIn.size(); i++) {
                    JSONObject objectAll = new JSONObject(new LinkedHashMap());
                    if (type == 1) {
                        objectAll.put("FAFFILIATIONID", lishuArrayIn.getJSONObject(i).getString("id"));
                    } else {
                        objectAll.put("FAFFILIATIONID", 0);
                        objectAll.put("FNumber", lishuArrayIn.getJSONObject(i).getString("fType"));
                        if (lishuArrayIn.getJSONObject(i).getString("fType").equals("001")) {
                            objectAll.put("FName", "xs");
                        } else if (lishuArrayIn.getJSONObject(i).getString("fType").equals("002")) {
                            objectAll.put("FName", "cg");
                        } else if (lishuArrayIn.getJSONObject(i).getString("fType").equals("003")) {
                            objectAll.put("FName", "kc");
                        } else if (lishuArrayIn.getJSONObject(i).getString("fType").equals("004")) {
                            objectAll.put("FName", "gc");
                        } else if (lishuArrayIn.getJSONObject(i).getString("fType").equals("007")) {
                            objectAll.put("FName", "js");
                        } else if (lishuArrayIn.getJSONObject(i).getString("fType").equals("008")) {
                            objectAll.put("FName", "zc");
                        } else if (lishuArrayIn.getJSONObject(i).getString("fType").equals("009")) {
                            objectAll.put("FName", "zj");
                        } else if (lishuArrayIn.getJSONObject(i).getString("fType").equals("010")) {
                            objectAll.put("FName", "sf");
                        } else if (lishuArrayIn.getJSONObject(i).getString("fType").equals("011")) {
                            objectAll.put("FName", "zj");
                        } else if (lishuArrayIn.getJSONObject(i).getString("fType").equals("012")) {
                            objectAll.put("FName", "yx");
                        } else if (lishuArrayIn.getJSONObject(i).getString("fType").equals("013")) {
                            objectAll.put("FName", "fw");
                        } else if (lishuArrayIn.getJSONObject(i).getString("fType").equals("014")) {
                            objectAll.put("FName", "gx");
                        }

                        JSONObject objectFType = new JSONObject();
                        objectFType.put("FNumber", lishuArrayIn.getJSONObject(i).getString("fType"));
                        objectAll.put("FType", objectFType);

                        JSONObject objectFRootOrgID = new JSONObject();
                        objectFRootOrgID.put("FNumber", fRootOrgID);
                        objectAll.put("FRootOrgID", objectFRootOrgID);

                        objectAll.put("FStartDate", sdf.format(new Date()));
                        objectAll.put("FEndDate", "9999-12-31 00:00:00");
                        objectAll.put("FIsDefault", false);
                    }
                    objectAll.put("FAffiliationEntry", jsonArrayFAffiliationEntry);
                    lishuArray.add(objectAll);
                }


                if (type == 1) {
                    //如果是修改，先反审核
                    String Ids = "";
                    for (int i = 0; i < lishuArray.size(); i++) {
                        Ids = Ids + "," + lishuArray.get(i).getString("FAFFILIATIONID");
                    }
                    this.organizationLiShuGuanXiUnAudit(acctId, dataCentreUserName, dataCentrePassword, null, Ids.substring(1));
                }

                String lishuArrayStr = lishuArray.toString();
                String param = "{\n" +
                        "    \"NumberSearch\": \"true\",\n" +
                        "    \"ValidateFlag\": \"true\",\n" +
                        "    \"IsDeleteEntry\": \"" + isDeleteEntry + "\",\n" +
                        "    \"IsEntryBatchFill\": \"true\",\n" +
                        "    \"NeedUpDateFields\": [],\n" +
                        "    \"NeedReturnFields\": [],\n" +
                        "    \"SubSystemId\": \"\",\n" +
                        "    \"InterationFlags\": \"\",\n" +
                        "    \"IsAutoSubmitAndAudit\": \"true\",\n" +
                        "    \"Model\": " + lishuArrayStr + "\n" +
                        "}";
                JSONObject jsonObject = InvokeHelper.BatchSave("ORG_Affiliation", param);

                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织隶属关系批量保存");
                    incident.setExcuteIncidentName("ORG_Affiliation,BatchSave");
                    incident.setExcuteIncidentJSON(param);
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
    public ResponseResult organizationLiShuGuanXiDelete(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setNumbers(resultList);
                baseDeleteBean.setIds(ids);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Delete("ORG_Affiliation", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织隶属关系删除");
                    incident.setExcuteIncidentName("ORG_Affiliation,Delete");
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
    public ResponseResult organizationLiShuGuanXiView(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseViewBean baseViewBean = new BaseViewBean();
                baseViewBean.setNumber(number);
                baseViewBean.setId(id);
                String a = JSON.toJSONString(baseViewBean);
                JSONObject jsonObject = InvokeHelper.View("ORG_Affiliation", a);

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
    public ResponseResult organizationLiShuGuanXiSubimt(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
                baseSubmitBean.setNumbers(resultList);
                baseSubmitBean.setIds(ids);
                String a = JSON.toJSONString(baseSubmitBean);
                JSONObject jsonObject = InvokeHelper.Submit("ORG_Affiliation", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织隶属关系提交");
                    incident.setExcuteIncidentName("ORG_Affiliation,Submit");
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
    public ResponseResult organizationLiShuGuanXiAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.Audit("ORG_Affiliation", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织隶属关系审核");
                    incident.setExcuteIncidentName("ORG_Affiliation,Audit");
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
    public ResponseResult organizationLiShuGuanXiUnAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
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
                JSONObject jsonObject = InvokeHelper.UnAudit("ORG_Affiliation", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织隶属关系反审核");
                    incident.setExcuteIncidentName("ORG_Affiliation,UnAudit");
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
    public ResponseResult queryOrganizationLiShu(String acctId, String dataCentreUserName, String dataCentrePassword, String formId, String fieldKeys) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                //1.1.FormId：业务对象表单Id（必录）
                //1.2.FieldKeys：需查询的字段key集合，字符串类型，格式："key1,key2,..." （必录）
                // 注（查询单据体内码,需加单据体Key和下划线,如：FEntryKey_FEntryId）
                BaseQueryBean inventoryQueryParam = new BaseQueryBean();
                inventoryQueryParam.setFormId(formId);
                inventoryQueryParam.setFieldKeys(fieldKeys);
                String a = JSON.toJSONString(inventoryQueryParam);
                JSONArray jsonObject = InvokeHelper.Query(a);
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }
    }

    //组织业务关系
    @Override
    public ResponseResult organizationYeWuZuZhiSave(String fBRTypeId, String fRelationOrgID, String fOrgId, String acctId, String dataCentreUserName, String dataCentrePassword) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                OrganizationYeWuZuZhiAddJsonsRootBean organizationLiShuGuanXiAddJsonsRootBean = new OrganizationYeWuZuZhiAddJsonsRootBean();
                OrganizationYeWuZuZhiAddModel model = new OrganizationYeWuZuZhiAddModel();
                List<Fbizrelationentry> fbizrelationentryList = new ArrayList<>();
                Fbizrelationentry fbizrelationentry = new Fbizrelationentry();
                fbizrelationentry.setFrelationorgid(new Frelationorgid(fRelationOrgID));
                fbizrelationentry.setForgid(new com.lnmj.k3cloud.entity.organization.organizationYeWuZuZhi.addSendParam.Forgid(fOrgId));
                fbizrelationentryList.add(fbizrelationentry);
                /* model.setFbizrelationentry(fbizrelationentryList);*/
                model.setFbrtypeid(new Fbrtypeid(fBRTypeId));
                organizationLiShuGuanXiAddJsonsRootBean.setModel(model);
                organizationLiShuGuanXiAddJsonsRootBean.setIsautosubmitandaudit(true);
                String a = JSON.toJSONString(organizationLiShuGuanXiAddJsonsRootBean, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONObject jsonObject = InvokeHelper.Save("ORG_BizRelation", a);
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
    public ResponseResult organizationYeWuZuZhiBatchSave(String acctId, String dataCentreUserName, String dataCentrePassword, String jsonArray) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                List yewuArray = new ArrayList();
                JSONArray yewuArrayIn = JSON.parseArray(jsonArray);
                for (int i = 0; i < yewuArrayIn.size(); i++) {
                    JSONObject objectAll = new JSONObject(new LinkedHashMap());

                    objectAll.put("FBIZRELATIONID", yewuArrayIn.getJSONObject(i).getString("fBIZRELATIONID"));

                    if (yewuArrayIn.getJSONObject(i).getString("fBRTypeIdNumber") != null) {
                        JSONObject objectFBRTypeId = new JSONObject();
                        objectFBRTypeId.put("FNumber", yewuArrayIn.getJSONObject(i).getString("fBRTypeIdNumber"));
                        objectAll.put("FBRTypeId", objectFBRTypeId);
                    }
                    JSONArray fBizrelationEntryArray = yewuArrayIn.getJSONObject(i).getJSONArray("fBizrelationEntryArray");

                    JSONArray fBizrelationEntry = new JSONArray();
                    for (int j = 0; j < fBizrelationEntryArray.size(); j++) {
                        //FBizrelationEntry
                        JSONObject objectAll1 = new JSONObject();

                        JSONObject objectFOrgId = new JSONObject();
                        objectFOrgId.put("FNumber", fBizrelationEntryArray.getJSONObject(j).getString("fOrgId"));
                        objectAll1.put("FOrgId", objectFOrgId);

                        JSONObject objectFRelationOrgID = new JSONObject();
                        objectFRelationOrgID.put("FNumber", fBizrelationEntryArray.getJSONObject(j).getString("fRelationOrgID"));
                        objectAll1.put("FRelationOrgID", objectFRelationOrgID);

                        objectAll1.put("FISDEFAULT", false);
                        objectAll1.put("FIsdefaultsOrg", false);

                        fBizrelationEntry.add(objectAll1);
                    }
                    objectAll.put("FBizrelationEntry", fBizrelationEntry);
                    yewuArray.add(objectAll);
                }

                String yewuArrayStr = yewuArray.toString();

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
                        "    \"IsAutoSubmitAndAudit\": \"false\",\n" +
                        "    \"Model\": " + yewuArrayStr + "\n" +
                        "}";
                JSONObject jsonObject = InvokeHelper.BatchSave("ORG_BizRelation", param);

                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织业务关系批量保存");
                    incident.setExcuteIncidentName("ORG_BizRelation,BatchSave");
                    incident.setExcuteIncidentJSON(param);
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
    public ResponseResult organizationYeWuZuZhiDelete(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setNumbers(resultList);
                baseDeleteBean.setIds(ids);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Delete("ORG_BizRelation", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织业务关系删除");
                    incident.setExcuteIncidentName("ORG_BizRelation,Delete");
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
    public ResponseResult organizationYeWuZuZhiView(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseViewBean baseViewBean = new BaseViewBean();
                baseViewBean.setNumber(number);
                baseViewBean.setId(id);
                String a = JSON.toJSONString(baseViewBean);
                JSONObject jsonObject = InvokeHelper.View("ORG_BizRelation", a);


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
    public ResponseResult organizationYeWuZuZhiSubimt(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
                baseSubmitBean.setNumbers(resultList);
                baseSubmitBean.setIds(ids);
                String a = JSON.toJSONString(baseSubmitBean);
                JSONObject jsonObject = InvokeHelper.Submit("ORG_BizRelation", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织业务关系提交");
                    incident.setExcuteIncidentName("ORG_BizRelation,Submit");
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
    public ResponseResult organizationYeWuZuZhiAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.Audit("ORG_BizRelation", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织业务关系审核");
                    incident.setExcuteIncidentName("ORG_BizRelation,Audit");
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
    public ResponseResult organizationYeWuZuZhiUnAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.UnAudit("ORG_BizRelation", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("组织业务关系反审核");
                    incident.setExcuteIncidentName("ORG_BizRelation,UnAudit");
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
    public ResponseResult queryOrganizationYeWuZuZhi(String acctId, String dataCentreUserName, String dataCentrePassword, String formId, String fieldKeys) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                //1.1.FormId：业务对象表单Id（必录）
                //1.2.FieldKeys：需查询的字段key集合，字符串类型，格式："key1,key2,..." （必录）
                // 注（查询单据体内码,需加单据体Key和下划线,如：FEntryKey_FEntryId）
                BaseQueryBean inventoryQueryParam = new BaseQueryBean();
                inventoryQueryParam.setFormId(formId);
                inventoryQueryParam.setFieldKeys(fieldKeys);
                String a = JSON.toJSONString(inventoryQueryParam);
                JSONArray jsonObject = InvokeHelper.Query(a);
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }
    }

    //基础资料控制策略
    @Override
    public ResponseResult organizationKongZhiCeNueSave(String fBaseDataTypeId, String fCreateOrgId, String fControlTypeId, String acctId, String dataCentreUserName, String dataCentrePassword) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                OrganizationKongZhiCeNueAddJsonsRootBean organizationLiShuGuanXiAddJsonsRootBean = new OrganizationKongZhiCeNueAddJsonsRootBean();
                OrganizationKongZhiCeNueAddModel model = new OrganizationKongZhiCeNueAddModel();
                model.setFbasedatatypeid(new Fbasedatatypeid(fBaseDataTypeId));
                model.setFcreateorgid(new Fcreateorgid(fCreateOrgId));
                List<Ftargetorgentrys> ftargetorgentrysList = new ArrayList<>();
                Ftargetorgentrys ftargetorgentrys = new Ftargetorgentrys();
                List<Fpropertyentry> fpropertyentryList = new ArrayList<>();
                Fpropertyentry fpropertyentry = new Fpropertyentry();
                fpropertyentry.setFcontroltypeid(fControlTypeId);
                fpropertyentryList.add(fpropertyentry);
                ftargetorgentrys.setFpropertyentry(fpropertyentryList);
                ftargetorgentrysList.add(ftargetorgentrys);
                model.setFtargetorgentrys(ftargetorgentrysList);
                organizationLiShuGuanXiAddJsonsRootBean.setModel(model);
                organizationLiShuGuanXiAddJsonsRootBean.setIsautosubmitandaudit(true);
                String a = JSON.toJSONString(organizationLiShuGuanXiAddJsonsRootBean, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                JSONObject jsonObject = InvokeHelper.Save("ORG_BaseDataControlPolicy", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("基础资料控制策略保存");
                    incident.setExcuteIncidentName("ORG_BaseDataControlPolicy,Save");
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
    public ResponseResult organizationKongZhiCeNueBatchSave(String acctId, String dataCentreUserName, String dataCentrePassword, String jsonArray) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (login) {
            try {
                List ceNueArray = new ArrayList();
                JSONArray cuNueArrayIn = JSON.parseArray(jsonArray);
                JSONArray fTargetOrgEntrysArray = new JSONArray();

                JSONObject objectAll2 = new JSONObject(new LinkedHashMap());

                objectAll2.put("FTargetOrgEntryID", "");
                JSONObject fFTargetOrgIdObj = new JSONObject();
                fFTargetOrgIdObj.put("FNumber", cuNueArrayIn.getJSONObject(0).getString("ftargetorgid"));
                objectAll2.put("FTargetOrgId", fFTargetOrgIdObj);
                objectAll2.put("FPropertyLoaded", "false");
                objectAll2.put("FIsForbidden", "false");

                fTargetOrgEntrysArray.add(objectAll2);

                for (int i = 0; i < cuNueArrayIn.size(); i++) {
                    JSONObject objectAll = new JSONObject(new LinkedHashMap());

                    objectAll.put("FPolicyID", cuNueArrayIn.getJSONObject(i).getString("fpolicyid"));

                    JSONObject objectFCreateOrgId = new JSONObject();
                    objectFCreateOrgId.put("FNumber", "100");
                    objectAll.put("FCreateOrgId", objectFCreateOrgId);

                    objectAll.put("FCreateDate", sdf.format(new Date()));

                    objectAll.put("FTargetOrgEntrys", fTargetOrgEntrysArray);

                    ceNueArray.add(objectAll);
                }
                String ceNueArrayStr = ceNueArray.toString();
                String param = "{\n" +
                        "    \"NumberSearch\": \"true\",\n" +
                        "    \"ValidateFlag\": \"true\",\n" +
                        "    \"IsDeleteEntry\": \"false\",\n" +
                        "    \"IsEntryBatchFill\": \"true\",\n" +
                        "    \"NeedUpDateFields\": [],\n" +
                        "    \"NeedReturnFields\": [],\n" +
                        "    \"SubSystemId\": \"\",\n" +
                        "    \"InterationFlags\": \"\",\n" +
                        "    \"IsAutoSubmitAndAudit\": \"false\",\n" +
                        "    \"Model\": " + ceNueArrayStr + "\n" +
                        "}";
                JSONObject jsonObject = InvokeHelper.BatchSave("ORG_BaseDataControlPolicy", param);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("基础资料控制策略批量保存");
                    incident.setExcuteIncidentName("ORG_BaseDataControlPolicy,BatchSave");
                    incident.setExcuteIncidentJSON(param);
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
    public ResponseResult organizationKongZhiCeNueDelete(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setNumbers(resultList);
                baseDeleteBean.setIds(ids);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Delete("ORG_BaseDataControlPolicy", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("基础资料控制策略删除");
                    incident.setExcuteIncidentName("ORG_BaseDataControlPolicy,Delete");
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
    public ResponseResult organizationKongZhiCeNueView(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseViewBean baseViewBean = new BaseViewBean();
                baseViewBean.setNumber(number);
                baseViewBean.setId(id);
                String a = JSON.toJSONString(baseViewBean);
                JSONObject jsonObject = InvokeHelper.View("ORG_BaseDataControlPolicy", a);

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
    public ResponseResult organizationKongZhiCeNueSubimt(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
                baseSubmitBean.setNumbers(resultList);
                baseSubmitBean.setIds(ids);
                String a = JSON.toJSONString(baseSubmitBean);
                JSONObject jsonObject = InvokeHelper.Submit("ORG_BaseDataControlPolicy", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("基础资料控制策略提交");
                    incident.setExcuteIncidentName("ORG_BaseDataControlPolicy,Submit");
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
    public ResponseResult organizationKongZhiCeNueAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.Audit("ORG_BaseDataControlPolicy", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("基础资料控制策略审核");
                    incident.setExcuteIncidentName("ORG_BaseDataControlPolicy,Audit");
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
    public ResponseResult organizationKongZhiCeNueUnAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.UnAudit("ORG_BaseDataControlPolicy", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("基础资料控制策略反审核");
                    incident.setExcuteIncidentName("ORG_BaseDataControlPolicy,UnAudit");
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
    public ResponseResult queryOrganizationKongZhiCeNue(String acctId, String dataCentreUserName, String dataCentrePassword, String formId, String fieldKeys) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                //1.1.FormId：业务对象表单Id（必录）
                //1.2.FieldKeys：需查询的字段key集合，字符串类型，格式："key1,key2,..." （必录）
                // 注（查询单据体内码,需加单据体Key和下划线,如：FEntryKey_FEntryId）
                BaseQueryBean inventoryQueryParam = new BaseQueryBean();
                inventoryQueryParam.setFormId(formId);
                inventoryQueryParam.setFieldKeys(fieldKeys);
                String a = JSON.toJSONString(inventoryQueryParam);
                JSONArray jsonObject = InvokeHelper.Query(a);
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
    public ResponseResult organizationUserWeiHuSave(String acctId, String dataCentreUserName, String dataCentrePassword, String fOrgBaseId, String fUSERACCOUNT) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                JSONObject jsonObject = InvokeHelper.Save("Org_OrgUserRoleMap", "{\n" +
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
                        "        \"FORGID\": 0,\n" +
                        "        \"FOrgBaseId\": {\n" +
                        "            \"FNUMBER\": \"" + fOrgBaseId + "\"\n" +
                        "        },\n" +
                        "        \"FUserList\": [\n" +
                        "            {\n" +
                        "                \"FUserId\": {\n" +
                        "                    \"FUSERACCOUNT\": \"" + fUSERACCOUNT + "\"\n" +
                        "                },\n" +
                        "                \"FUserRoleList\": [\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"B2C01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"BD01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"BD02_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"BD09_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"BOS01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"BOS02_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"BOS03_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"BOS04_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"BOS05_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"BOS06_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"BOS07_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"BOS08_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"CRM_SV\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"FIN01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"FIN02_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"FIN03_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"FIN04_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"FIN05_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"FIN06_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"FIN07_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"FIN08_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"FIN09_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"FIN10_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"FIN11_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"FIN12_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"FIN13_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"FIN14_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"FIN20_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"FIN21_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"MFG01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"MFG02_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"MFG03_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"MFG04_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"MFG05_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"MFG06_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"MFG07_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"MFG08_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"MFG09_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"MFG10_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"MFG11_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"MFG12_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"MFG13_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"PLM_PRJ_01\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"PLM_PRJ_02\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"PLM_PRJ_03\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"PLM_PRJ_04\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"PLM_PRJ_05\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"PLM01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"QM01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCM01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCM02_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCM03_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCM04_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCM05_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCM06_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCM07_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCM08_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCM09_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCM10_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCM11_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCM12_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCM13_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCM14_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCM15_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCM16_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCM17_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCMCP01_SYS\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"FRoleId\": {\n" +
                        "                            \"FNUMBER\": \"SCMCP02_SYS\"\n" +
                        "                        }\n" +
                        "                    }\n" +
                        "                ]\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}");
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
    public ResponseResult organizationJiChuKongZhiSave(String acctId, String dataCentreUserName, String dataCentrePassword, String orgK3Number, String fTargetOrgId) {
        List<String> stringList = new ArrayList<>();
        stringList.add("BD_Supplier");
        stringList.add("BD_Customer");
        stringList.add("BD_MATERIAL");
        stringList.add("PLM_STD_PRJ_WORKCALTEMP");
        stringList.add("CR_BaseTmpInControl");
        stringList.add("WB_ReceiveFromBankPlan");
        stringList.add("DRP_ChannelDistribution");
        stringList.add("DRP_MatExtendList");
        stringList.add("ECC_Suite");
        stringList.add("ECC_Transport");
        stringList.add("Sal_StdExchangeRate");
        stringList.add("HR_ORG_HRPOST");
        stringList.add("HR_BaseDataModel");
        stringList.add("CN_INNERACCOUNT");
        stringList.add("FIN_OTHERS");
        stringList.add("BD_TAXRULE");
        stringList.add("CN_CASHACCOUNT");
        stringList.add("CN_BANKACNT");
        stringList.add("CRM_CUST");
        stringList.add("CRM_Segmentation");
        stringList.add("CRM_WeixinUser");
        stringList.add("CRM_SALE_COMPETITOR");
        stringList.add("CRM_SaleMethod");
        stringList.add("CRM_SalePhase");
        stringList.add("CRM_SV_PRODUCTFILE");
        stringList.add("CMK_RT_FloorInfo");
        stringList.add("CMK_BD_Brand");
        stringList.add("CMK_RT_BusinessCatalog");
        stringList.add("CMK_LS_CashierManage");
        stringList.add("ENG_FORMULA");
        stringList.add("ENG_Route");
        stringList.add("ENG_RouteF8");
        stringList.add("ENG_WorkCenter");
        stringList.add("ENG_Mould");
        stringList.add("ENG_MouldProdMix");
        stringList.add("ENG_Equipment");
        stringList.add("ENG_EqumCategory");
        stringList.add("ENG_Substitution");
        stringList.add("ENG_BOM");
        stringList.add("ENG_BBEBOM");
        stringList.add("ENG_Resource");
        stringList.add("ENG_ResourceCategory");
        stringList.add("ENG_Process");
        stringList.add("REM_RunProcedure");
        stringList.add("REM_ProdSubDayPlanF8");
        stringList.add("SFC_FeederSetEntryF8");
        stringList.add("SFC_OptPlanOperF8");
        stringList.add("SFC_OptPlanSeqF8");
        stringList.add("SFC_PCBLocation");
        stringList.add("SFC_SchedulingModel");
        stringList.add("SFC_AlertType");
        stringList.add("QM_BDInspectRule");
        List produdctArray = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        JSONArray fTargetOrgEntrysArray = new JSONArray();

        JSONObject objectAll2 = new JSONObject(new LinkedHashMap());

        objectAll2.put("FTargetOrgEntryID", "");

        JSONObject fFTargetOrgIdObj = new JSONObject();
        fFTargetOrgIdObj.put("FNumber", fTargetOrgId);
        objectAll2.put("FTargetOrgId", fFTargetOrgIdObj);

        objectAll2.put("FPropertyLoaded", "false");
        objectAll2.put("FIsForbidden", "false");

        fTargetOrgEntrysArray.add(objectAll2);

        for (int i = 0; i < stringList.size(); i++) {
            JSONObject objectAll = new JSONObject(new LinkedHashMap());

            objectAll.put("FPolicyID", 0);

            JSONObject objectFBaseDataTypeId = new JSONObject();
            objectFBaseDataTypeId.put("FNumber", stringList.get(i));
            objectAll.put("FBaseDataTypeId", objectFBaseDataTypeId);

            JSONObject objectFCreateOrgId = new JSONObject();
            objectFCreateOrgId.put("FNumber", orgK3Number);
            objectAll.put("FCreateOrgId", objectFCreateOrgId);


            objectAll.put("FCreateDate", sdf.format(new Date()));

            if (!stringList.get(i).equals("HR_ORG_HRPOST")){
                objectAll.put("FTargetOrgEntrys", fTargetOrgEntrysArray);
            }


            produdctArray.add(objectAll);
        }


        String produdctArrayStr = produdctArray.toString();
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
                        "    \"IsAutoSubmitAndAudit\": \"false\",\n" +
                        "    \"Model\": " + produdctArrayStr + "}";
                JSONObject jsonObject = InvokeHelper.BatchSave("ORG_BaseDataControlPolicy", param);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("基础资料控制策略批量保存");
                    incident.setExcuteIncidentName("ORG_BaseDataControlPolicy,BatchSave");
                    incident.setExcuteIncidentJSON(param);
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
    public ResponseResult organizationJiChuKongZhiSaveGW(String acctId, String dataCentreUserName, String dataCentrePassword, String orgK3Number) {
        List<String> stringList = new ArrayList<>();
        stringList.add("HR_ORG_HRPOST");

        List produdctArray = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < stringList.size(); i++) {
            JSONObject objectAll = new JSONObject(new LinkedHashMap());

            objectAll.put("FPolicyID", 0);

            JSONObject objectFBaseDataTypeId = new JSONObject();
            objectFBaseDataTypeId.put("FNumber", stringList.get(i));
            objectAll.put("FBaseDataTypeId", objectFBaseDataTypeId);

            JSONObject objectFCreateOrgId = new JSONObject();
            objectFCreateOrgId.put("FNumber", orgK3Number);
            objectAll.put("FCreateOrgId", objectFCreateOrgId);


            objectAll.put("FCreateDate", sdf.format(new Date()));

            produdctArray.add(objectAll);
        }


        String produdctArrayStr = produdctArray.toString();
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
                        "    \"IsAutoSubmitAndAudit\": \"false\",\n" +
                        "    \"Model\": " + produdctArrayStr + "}";
                JSONObject jsonObject = InvokeHelper.BatchSave("ORG_BaseDataControlPolicy", param);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("基础资料控制策略-岗位批量保存");
                    incident.setExcuteIncidentName("ORG_BaseDataControlPolicy,BatchSave");
                    incident.setExcuteIncidentJSON(param);
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
