package com.lnmj.k3cloud.business.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.ISupplierService;
import com.lnmj.k3cloud.entity.base.BaseAuditBean;
import com.lnmj.k3cloud.entity.base.BaseDeleteBean;
import com.lnmj.k3cloud.entity.base.BaseSubmitBean;
import com.lnmj.k3cloud.entity.base.BaseViewBean;
import com.lnmj.k3cloud.entity.product.batchAdd.BatchAdd;
import com.lnmj.k3cloud.entity.supplier.Allocate_Supplier;
import com.lnmj.k3cloud.entity.supplier.supplier.addSendParam.*;
import com.lnmj.k3cloud.entity.supplier.supplier.batchAdd.SupplierBatchAdd;
import com.lnmj.k3cloud.entity.supplier.supplier.batchAdd.SupplierBatchAddJsonsRootBean;
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
 * @Author: panlin
 * @Date: 2019-11-08 15:09
 * @Description: 金蝶service
 */
@Service("supplierServiceImpl")
public class SupplierServiceImpl implements ISupplierService {
    @Resource
    private IIncidentDao iIncidentDao;
    @Override
    public ResponseResult supplierSave(String acctId, String dataCentreUserName, String dataCentrePassword, String fName, String fCreateOrgId, String fUseOrgId, String fPayCurrencyId, String fLocationInfoListStr, String needUpDateField, Integer fSupplierId) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                JSONObject jsonObject;
                String a = null;
                if (needUpDateField == null) {
                     a = "{\n" +
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
                            "        \"FSupplierId\": 0,\n" +
                            "        \"FCreateOrgId\": {\n" +
                            "            \"FNumber\": \""+fCreateOrgId+"\"\n" +
                            "        },\n" +
                            "        \"FUseOrgId\": {\n" +
                            "            \"FNumber\": \""+fUseOrgId+"\"\n" +
                            "        },\n" +
                            "        \"FName\": \""+fName+" \",\n" +
                            "        \"FBaseInfo\": {\n" +
                            "            \"FSupplyClassify\": \"CG\"\n" +
                            "        },\n" +
                            "        \"FBusinessInfo\": {\n" +
                            "            \"FVmiBusiness\": false,\n" +
                            "            \"FEnableSL\": false\n" +
                            "        },\n" +
                            "        \"FFinanceInfo\": {\n" +
                            "            \"FPayCurrencyId\": {\n" +
                            "                \"FNumber\": \""+fPayCurrencyId+"\"\n" +
                            "            },\n" +
                            "            \"FTaxType\": {\n" +
                            "                \"FNumber\": \"SFL02_SYS\"\n" +
                            "            },\n" +
                            "            \"FInvoiceType\": \"1\",\n" +
                            "            \"FTaxRateId\": {\n" +
                            "                \"FNUMBER\": \"SL02_SYS\"\n" +
                            "            }\n" +
                            "        }\n" +
                            "    }\n" +
                            "}";
                    jsonObject = InvokeHelper.Save("BD_Supplier", a);
                }else{
                    SupplierAddJsonsRootBean supplierAddJsonsRootBean = new SupplierAddJsonsRootBean();
                    SupplierAddModel model = new SupplierAddModel();
                    model.setFname(fName);
                    model.setFsupplierid(fSupplierId);
                    supplierAddJsonsRootBean.setModel(model);
                    supplierAddJsonsRootBean.setIsautosubmitandaudit(false);
                    List<String> stringList = new ArrayList<>();
                    stringList.add(needUpDateField);
                    supplierAddJsonsRootBean.setNeedupdatefields(stringList);
                     a = JSON.toJSONString(supplierAddJsonsRootBean, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                    jsonObject = InvokeHelper.Save("BD_Supplier", a);
                }
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("供应商保存");
                    incident.setExcuteIncidentName("BD_Supplier,Save");
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
    public ResponseResult supplierDelete(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        List<String> resultList;
        if (StringUtils.isNotBlank(numbers)) {
            String[] numbersStrList = numbers.split(",");
            resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        } else {
            resultList = null;
        }

        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setNumbers(resultList);
                baseDeleteBean.setIds(ids);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Delete("BD_Supplier", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("供应商删除");
                    incident.setExcuteIncidentName("BD_Supplier,Delete");
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
    public ResponseResult supplierView(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseViewBean baseViewBean = new BaseViewBean();
                baseViewBean.setNumber(number);
                baseViewBean.setId(id);
                String a = JSON.toJSONString(baseViewBean);
                JSONObject jsonObject = InvokeHelper.View("BD_Supplier", a);

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
    public ResponseResult supplierSubimt(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
                baseSubmitBean.setNumbers(resultList);
                baseSubmitBean.setIds(ids);
                String a = JSON.toJSONString(baseSubmitBean);
                JSONObject jsonObject = InvokeHelper.Submit("BD_Supplier", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("供应商提交");
                    incident.setExcuteIncidentName("BD_Supplier,Submit");
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
    public ResponseResult supplierAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.Audit("BD_Supplier", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("供应商审核");
                    incident.setExcuteIncidentName("BD_Supplier,Audit");
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
    public ResponseResult supplierUnAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        List<String> resultList;
        if (StringUtils.isNotBlank(numbers)) {
            String[] numbersStrList = numbers.split(",");
            resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        } else {
            resultList = null;
        }
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setNumbers(resultList);
                baseAuditBean.setIds(ids);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.UnAudit("BD_Supplier", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("供应商反审核");
                    incident.setExcuteIncidentName("BD_Supplier,UnAudit");
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
    public ResponseResult supplierAllocate(String acctId, String dataCentreUserName, String dataCentrePassword, String pkIds, String orgIds, Boolean isAutoSubmitAndAudit) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                Allocate_Supplier allocateSupplier = new Allocate_Supplier();
                allocateSupplier.setPkIds(pkIds);
                allocateSupplier.setTOrgIds(orgIds);
                allocateSupplier.setIsAutoSubmitAndAudit(isAutoSubmitAndAudit);
                String a = JSON.toJSONString(allocateSupplier);
                JSONObject jsonObject = InvokeHelper.Allocate("BD_Supplier", a);

                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("供应商分配");
                    incident.setExcuteIncidentName("BD_Supplier,Allocate");
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
    public ResponseResult batchsave(String acctId, String dataCentreUserName, String dataCentrePassword, String supplierArr/*, String fName, String fCreateOrgId, String fUseOrgId, String fPayCurrencyId, String fLocationInfoListStr*/) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                JSONArray jsonArray = JSON.parseArray(supplierArr);
                SupplierBatchAddJsonsRootBean supplierBatchAddJsonsRootBean = new SupplierBatchAddJsonsRootBean();
                List<SupplierBatchAdd> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    SupplierBatchAdd supplierBatchAdd = new SupplierBatchAdd();
                    BatchAdd batchAdd = new BatchAdd();
                    supplierBatchAdd.setFname(jsonArray.getJSONObject(i).getString("FName"));
                    list.add(supplierBatchAdd);
                }
                supplierBatchAddJsonsRootBean.setModel(list);
                String a = JSON.toJSONString(supplierBatchAddJsonsRootBean);
                JSONObject jsonObject = InvokeHelper.BatchSave("BD_Supplier", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean)((Map)((Map)jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess){
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("供应商批量保存");
                    incident.setExcuteIncidentName("BD_Supplier,BatchSave");
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
