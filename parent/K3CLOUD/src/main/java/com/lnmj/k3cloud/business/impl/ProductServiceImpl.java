package com.lnmj.k3cloud.business.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IProductService;
import com.lnmj.k3cloud.entity.base.*;
import com.lnmj.k3cloud.entity.product.batchAdd.BatchAdd;
import com.lnmj.k3cloud.entity.product.batchAdd.ProductBatchAddJsonsRootBean;
import com.lnmj.k3cloud.entity.product.product.addSendParam.*;
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
 * @Author: panlin
 * @Date: 2019-11-08 15:09
 * @Description: 金蝶service
 */
@Service("productServiceImpl")
public class ProductServiceImpl implements IProductService {
    @Resource
    private IIncidentDao iIncidentDao;

    @Override
    public ResponseResult productSave(String acctId, String dataCentreUserName, String dataCentrePassword, String fname, Integer id, Boolean isautosubmitandaudit) {

        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                ProductAddJsonsRootBean productAddJsonsRootBean = new ProductAddJsonsRootBean();
                ProductAddModel productAddModel = new ProductAddModel();
                productAddModel.setFname(fname);

                productAddJsonsRootBean.setModel(productAddModel);
                productAddJsonsRootBean.setIsautosubmitandaudit(isautosubmitandaudit);
                productAddJsonsRootBean.setIsautosubmitandaudit(true);
                if (id != null) {
                    productAddModel.setFmaterialid(id);
                }
                String a = JSON.toJSONString(productAddJsonsRootBean
//                        , SerializerFeature.WriteMapNullValue,
//                        SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullBooleanAsFalse,
//                        SerializerFeature.WriteNullListAsEmpty
                );

                JSONObject jsonObject = InvokeHelper.Save("BD_MATERIAL", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("物料保存");
                    incident.setExcuteIncidentName("BD_MATERIAL,Save");
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
    public ResponseResult productDelete(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setNumbers(resultList);
                baseDeleteBean.setIds(ids);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Delete("BD_MATERIAL", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("物料删除");
                    incident.setExcuteIncidentName("BD_MATERIAL,Delete");
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
    public ResponseResult productView(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseViewBean baseViewBean = new BaseViewBean();
                baseViewBean.setNumber(number);
                baseViewBean.setId(id);
                String a = JSON.toJSONString(baseViewBean);
                JSONObject jsonObject = InvokeHelper.View("BD_MATERIAL", a);

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
    public ResponseResult productSubimt(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
                baseSubmitBean.setNumbers(resultList);
                baseSubmitBean.setIds(ids);
                String a = JSON.toJSONString(baseSubmitBean);
                JSONObject jsonObject = InvokeHelper.Submit("BD_MATERIAL", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("物料提交");
                    incident.setExcuteIncidentName("BD_MATERIAL,Submit");
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
    public ResponseResult productAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.Audit("BD_MATERIAL", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("物料审核");
                    incident.setExcuteIncidentName("BD_MATERIAL,Audit");
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
    public ResponseResult productUnAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.UnAudit("BD_MATERIAL", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("物料反审核");
                    incident.setExcuteIncidentName("BD_MATERIAL,UnAudit");
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
    public ResponseResult productAllocate(String acctId, String dataCentreUserName, String dataCentrePassword, String PkIds, String TOrgIds) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDistributionBean baseDistributionBean = new BaseDistributionBean();
                baseDistributionBean.setPkIds(PkIds);
                baseDistributionBean.setTOrgIds(TOrgIds);
                baseDistributionBean.setAutoSubmitAndAudit(true);
                String a = JSON.toJSONString(baseDistributionBean);
                JSONObject jsonObject = InvokeHelper.Allocate("BD_MATERIAL", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("物料分配");
                    incident.setExcuteIncidentName("BD_MATERIAL,Allocate");
                    incident.setExcuteIncidentJSON(a);
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
    public ResponseResult batchAdd(String acctId, String dataCentreUserName, String dataCentrePassword, String productArr) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                JSONArray jsonArray = JSON.parseArray(productArr);
                ProductBatchAddJsonsRootBean productBatchAddJsonsRootBean = new ProductBatchAddJsonsRootBean();
                List<BatchAdd> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    BatchAdd batchAdd = new BatchAdd();
                    batchAdd.setFname(jsonArray.getJSONObject(i).getString("FName"));
                    list.add(batchAdd);
                }
                productBatchAddJsonsRootBean.setModel(list);
                productBatchAddJsonsRootBean.setIsautosubmitandaudit(true);
                String a = JSON.toJSONString(productBatchAddJsonsRootBean);
                JSONObject jsonObject = InvokeHelper.BatchSave("BD_MATERIAL", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("物料批量保存");
                    incident.setExcuteIncidentName("BD_MATERIAL,BatchSave");
                    incident.setExcuteIncidentJSON(a);
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
}
