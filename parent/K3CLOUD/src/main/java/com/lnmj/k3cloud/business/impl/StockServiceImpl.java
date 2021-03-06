package com.lnmj.k3cloud.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IStockService;
import com.lnmj.k3cloud.entity.base.*;
import com.lnmj.k3cloud.entity.organization.organizationForm.addSendParam.Fmodifierid;
import com.lnmj.k3cloud.entity.stock.*;
import com.lnmj.k3cloud.entity.stock.MarketOutstorageSaveParam.*;
import com.lnmj.k3cloud.entity.stock.MarketReturnSaveParam.*;
import com.lnmj.k3cloud.entity.stock.OtherInstorageSaveParam.*;
import com.lnmj.k3cloud.entity.stock.OtherOutstorageSaveParam.FOutstorageEntity;
import com.lnmj.k3cloud.entity.stock.OtherOutstorageSaveParam.OtherOutstorageModel;
import com.lnmj.k3cloud.entity.stock.OtherOutstorageSaveParam.OtherOutstorageSaveParam;
import com.lnmj.k3cloud.entity.stock.PurchaseInstorageSaveParam.*;
import com.lnmj.k3cloud.entity.stock.PurchaseReturnSaveParam.*;
import com.lnmj.k3cloud.entity.stock.inventory.InventorySaveParam;
import com.lnmj.k3cloud.entity.stock.stock.FCustomerId;
import com.lnmj.k3cloud.entity.stock.stock.StockModel;
import com.lnmj.k3cloud.entity.stock.stock.StockSaveParam;
import com.lnmj.k3cloud.entity.stock.stockStatus.StockStatusModel;
import com.lnmj.k3cloud.entity.stock.stockStatus.StockStatusSaveParam;
import com.lnmj.k3cloud.pojo.Incident;
import com.lnmj.k3cloud.repository.IIncidentDao;
import com.lnmj.k3cloud.repository.IK3CLOUDDao;
import com.lnmj.k3cloud.util.InvokeHelper;
import com.lnmj.k3cloud.util.K3cloudConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.*;

@Service("StockServiceImpl")
public class StockServiceImpl implements IStockService {
    @Resource
    private IIncidentDao iIncidentDao;
    @Resource
    private IK3CLOUDDao ik3CLOUDDao;

    //====================================采购入库==============================================
    @Override
    public ResponseResult savePurchaseInstorage(String acctId,
                                                String dataCentreUserName,
                                                String dataCentrePassword,
                                                String orgK3Number,
                                                String fPurchaseOrgId,
                                                String fSupplierId,
                                                String fDate,
                                                String fSupplyAddress,
                                                String fCONTACTNUMBER,
                                                String jsonArrayProduct
    ) {
        List produdctArray = new ArrayList();
        JSONArray productArrayIn = JSON.parseArray(jsonArrayProduct);
        for (int i = 0; i < productArrayIn.size(); i++) {
            JSONObject objectAll = new JSONObject(new LinkedHashMap());

            objectAll.put("FRowType", "Standard");

            JSONObject objectFMaterialId = new JSONObject();
            objectFMaterialId.put("FNumber", productArrayIn.getJSONObject(i).getString("productK3Number"));
            objectAll.put("FMaterialId", objectFMaterialId);

            JSONObject objectFUnitID = new JSONObject();
            objectFUnitID.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
            objectAll.put("FUnitID", objectFUnitID);

            objectAll.put("FRealQty", productArrayIn.getJSONObject(i).getDouble("number"));

            JSONObject objectFPriceUnitID = new JSONObject();
            objectFPriceUnitID.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
            objectAll.put("FPriceUnitID", objectFPriceUnitID);

            JSONObject objectFStockId = new JSONObject();
            objectFStockId.put("FNumber", productArrayIn.getJSONObject(i).getString("stockK3Nunber"));
            objectAll.put("FStockId", objectFStockId);

            JSONObject objectFStockStatusId = new JSONObject();
            objectFStockStatusId.put("FNumber", "KCZT01_SYS");
            objectAll.put("FStockStatusId", objectFStockStatusId);

            objectAll.put("FGiveAway", false);

            objectAll.put("FCheckInComing", false);

            objectAll.put("FIsReceiveUpdateStock", false);

            objectAll.put("FPriceBaseQty", productArrayIn.getJSONObject(i).getDouble("number"));

            JSONObject objectFRemainInStockUnitId = new JSONObject();
            objectFRemainInStockUnitId.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
            objectAll.put("FRemainInStockUnitId", objectFRemainInStockUnitId);

            objectAll.put("FBILLINGCLOSE", false);

            objectAll.put("FRemainInStockQty", productArrayIn.getJSONObject(i).getDouble("number"));

            objectAll.put("FAPNotJoinQty", productArrayIn.getJSONObject(i).getDouble("number"));

            objectAll.put("FRemainInStockBaseQty", productArrayIn.getJSONObject(i).getDouble("number"));

            produdctArray.add(objectAll);
        }
        String produdctArrayStr = produdctArray.toString();
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                //必填项：
                //   收料组织：FStockOrgId  (必填项)
                //	 入库日期：FDate  (必填项)
                //	 单据类型：FBillTypeID  (必填项)
                //	 货主类型：FOwnerTypeIdHead  (必填项)
                //	 货主：FOwnerIdHead  (必填项)
                //	 采购组织：FPurchaseOrgId  (必填项)
                //	 供应商：FSupplierId  (必填项)
                //	 结算组织：FSettleOrgId  (必填项)
                //	 结算币别：FSettleCurrId  (必填项)
                //	 定价时点：FPriceTimePoint  (必填项)
                //	 物料编码：FMaterialId  (必填项)
                //	 库存单位：FUnitID  (必填项)
                //	 计价单位：FPriceUnitID  (必填项)
                //	 采购单位：FRemainInStockUnitId  (必填项)


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
                        "    \"Model\": {\n" +
                        "        \"FID\": 0,\n" +
                        "        \"FBillTypeID\": {\n" +
                        "            \"FNUMBER\": \"RKD01_SYS\"\n" +
                        "        },\n" +
                        "        \"FDate\": \"" + fDate + "\",\n" +
                        "        \"FStockOrgId\": {\n" +
                        "            \"FNumber\": \"" + orgK3Number + "\"\n" +
                        "        },\n" +
                        "        \"FDemandOrgId\": {\n" +
                        "            \"FNumber\": \"" + orgK3Number + "\"\n" +
                        "        },\n" +
                        "        \"FPurchaseOrgId\": {\n" +
                        "            \"FNumber\": \"" + fPurchaseOrgId + "\"\n" +
                        "        },\n" +
                        "        \"FSupplierId\": {\n" +
                        "            \"FNumber\": \"" + fSupplierId + "\"\n" +
                        "        },\n" +
                        "        \"FSupplyId\": {\n" +
                        "            \"FNumber\": \"" + fSupplierId + "\"\n" +
                        "        },\n" +
                        "        \"FSupplyAddress\": \"" + fSupplyAddress + "\",\n" +
                        "        \"FSettleId\": {\n" +
                        "            \"FNumber\": \"" + fSupplierId + "\"\n" +
                        "        },\n" +
                        "        \"FChargeId\": {\n" +
                        "            \"FNumber\": \"" + fSupplierId + "\"\n" +
                        "        },\n" +
                        "        \"FOwnerTypeIdHead\": \"BD_OwnerOrg\",\n" +
                        "        \"FOwnerIdHead\": {\n" +
                        "            \"FNumber\": \"" + orgK3Number + "\"\n" +
                        "        },\n" +
                        "        \"FProviderContactID\": {\n" +
                        "            \"FCONTACTNUMBER\": \"" + fCONTACTNUMBER + "\"\n" +
                        "        },\n" +
                        "        \"FInStockFin\": {\n" +
                        "            \"FSettleOrgId\": {\n" +
                        "                \"FNumber\": \"" + fPurchaseOrgId + "\"\n" +
                        "            },\n" +
                        "            \"FSettleCurrId\": {\n" +
                        "                \"FNumber\": \"PRE001\"\n" +
                        "            },\n" +
                        "            \"FIsIncludedTax\": true,\n" +
                        "            \"FPriceTimePoint\": \"1\",\n" +
                        "            \"FLocalCurrId\": {\n" +
                        "                \"FNumber\": \"PRE001\"\n" +
                        "            },\n" +
                        "            \"FExchangeTypeId\": {\n" +
                        "                \"FNumber\": \"HLTX01_SYS\"\n" +
                        "            },\n" +
                        "            \"FExchangeRate\": 1.0,\n" +
                        "            \"FISPRICEEXCLUDETAX\": true\n" +
                        "        },\n" +
                        "        \"FInStockEntry\": " + produdctArrayStr + "\n" +
                        "    }\n" +
                        "}";
                JSONObject iv_salesic = InvokeHelper.Save("STK_InStock", param);


                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) iv_salesic.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("采购入库保存");
                    incident.setExcuteIncidentName("STK_InStock,Save");
                    incident.setExcuteIncidentJSON(param);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                String Id = (((Map) (iv_salesic.get("Result")))).get("Id").toString();
                //提交
                this.submitPurchaseInstorage(acctId, dataCentreUserName, dataCentrePassword, null, Id);
                //审核
                this.auditPurchaseInstorage(acctId, dataCentreUserName, dataCentrePassword, null, Id);
                return ResponseResult.success(iv_salesic);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }
    }

    @Override
    public ResponseResult savePurchaseOrder(String acctId, String dataCentreUserName, String dataCentrePassword, String fPurchaseOrgId, String fSupplierId, String fDate, String jsonArrayProduct) {
        List produdctArray = new ArrayList();
        JSONArray productArrayIn = JSON.parseArray(jsonArrayProduct);
        for (int i = 0; i < productArrayIn.size(); i++) {
            JSONObject objectAll = new JSONObject(new LinkedHashMap());

            objectAll.put("FProductType", "1");

            JSONObject objectFMaterialId = new JSONObject();
            objectFMaterialId.put("FNumber", productArrayIn.getJSONObject(i).getString("productK3Number"));
            objectAll.put("FMaterialId", objectFMaterialId);

            objectAll.put("FMaterialDesc", productArrayIn.getJSONObject(i).getString("fMaterialDesc"));

            JSONObject objectFUnitID = new JSONObject();
            objectFUnitID.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
            objectAll.put("FUnitID", objectFUnitID);

            objectAll.put("FQty", productArrayIn.getJSONObject(i).getDouble("number"));

            JSONObject objectFPriceUnitID = new JSONObject();
            objectFPriceUnitID.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
            objectAll.put("FPriceUnitID", objectFPriceUnitID);

            objectAll.put("FPriceUnitQty", productArrayIn.getJSONObject(i).getDouble("number"));

            objectAll.put("FPriceBaseQty", productArrayIn.getJSONObject(i).getDouble("number"));

            objectAll.put("FDeliveryDate", fDate);

            objectAll.put("FPrice", productArrayIn.getJSONObject(i).getDouble("fPrice"));

            objectAll.put("FTaxPrice", productArrayIn.getJSONObject(i).getDouble("fPrice"));


            JSONObject objectFRequireOrgId = new JSONObject();
            objectFRequireOrgId.put("FNumber", productArrayIn.getJSONObject(i).getString("fRequireOrgId"));
            objectAll.put("FRequireOrgId", objectFRequireOrgId);


            JSONObject objectFReceiveOrgId = new JSONObject();
            objectFReceiveOrgId.put("FNumber", productArrayIn.getJSONObject(i).getString("fRequireOrgId"));
            objectAll.put("FReceiveOrgId", objectFReceiveOrgId);


            JSONObject objectFEntrySettleOrgId = new JSONObject();
            objectFEntrySettleOrgId.put("FNumber", productArrayIn.getJSONObject(i).getString("fRequireOrgId"));
            objectAll.put("FEntrySettleOrgId", objectFEntrySettleOrgId);

            objectAll.put("FGiveAway", false);

            JSONObject objectFStockUnitID = new JSONObject();
            objectFStockUnitID.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
            objectAll.put("FStockUnitID", objectFStockUnitID);

            objectAll.put("FStockQty", productArrayIn.getJSONObject(i).getDouble("number"));
            objectAll.put("FStockBaseQty", productArrayIn.getJSONObject(i).getDouble("number"));
            objectAll.put("FDeliveryControl", false);
            objectAll.put("FTimeControl", false);
            objectAll.put("FDeliveryMaxQty", productArrayIn.getJSONObject(i).getDouble("number"));
            objectAll.put("FDeliveryMinQty", productArrayIn.getJSONObject(i).getDouble("number"));
            objectAll.put("FDeliveryEarlyDate", fDate);
            objectAll.put("FDeliveryLastDate", fDate);
            objectAll.put("FPriceCoefficient", 1.0);
            objectAll.put("FPlanConfirm", true);


            JSONObject objectFSalUnitID = new JSONObject();
            objectFSalUnitID.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
            objectAll.put("FSalUnitID", objectFSalUnitID);

            objectAll.put("FSalQty", productArrayIn.getJSONObject(i).getDouble("number"));

            JSONObject objectFCentSettleOrgId = new JSONObject();
            objectFCentSettleOrgId.put("FNumber", productArrayIn.getJSONObject(i).getString("fRequireOrgId"));
            objectAll.put("FCentSettleOrgId", objectFCentSettleOrgId);

            JSONObject objectFDispSettleOrgId = new JSONObject();
            objectFDispSettleOrgId.put("FNumber", productArrayIn.getJSONObject(i).getString("fRequireOrgId"));
            objectAll.put("FDispSettleOrgId", objectFDispSettleOrgId);

            JSONObject objectFDeliveryStockStatus = new JSONObject();
            objectFDeliveryStockStatus.put("FNumber", "KCZT02_SYS");
            objectAll.put("FDeliveryStockStatus", objectFDeliveryStockStatus);

            objectAll.put("FIsStock", false);

            objectAll.put("FSalBaseQty", productArrayIn.getJSONObject(i).getDouble("number"));

            JSONObject objectFEntryPayOrgId = new JSONObject();
            objectFEntryPayOrgId.put("FNumber", productArrayIn.getJSONObject(i).getString("fRequireOrgId"));
            objectAll.put("FEntryPayOrgId", objectFEntryPayOrgId);

            JSONArray jsonArrayFEntryDeliveryPlan = new JSONArray();
            JSONObject objectAll2 = new JSONObject();
            objectAll2.put("FDeliveryDate_Plan", fDate);
            objectAll2.put("FPlanQty", objectFEntryPayOrgId);
            objectAll2.put("FPREARRIVALDATE", fDate);
            jsonArrayFEntryDeliveryPlan.add(objectAll2);

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
                        "    \"IsAutoSubmitAndAudit\": \"true\",\n" +
                        "    \"Model\": {\n" +
                        "        \"FID\": 0,\n" +
                        "        \"FBillTypeID\": {\n" +
                        "            \"FNUMBER\": \"CGDD01_SYS\"\n" +
                        "        },\n" +
                        "        \"FDate\": \"" + fDate + "\",\n" +
                        "        \"FSupplierId\": {\n" +
                        "            \"FNumber\": \"" + fSupplierId + "\"\n" +
                        "        },\n" +
                        "        \"FPurchaseOrgId\": {\n" +
                        "            \"FNumber\": \"" + fPurchaseOrgId + "\"\n" +
                        "        },\n" +
                        "        \"FProviderId\": {\n" +
                        "            \"FNumber\": \"" + fSupplierId + "\"\n" +
                        "        },\n" +
                        "        \"FSettleId\": {\n" +
                        "            \"FNumber\": \"" + fSupplierId + "\"\n" +
                        "        },\n" +
                        "        \"FChargeId\": {\n" +
                        "            \"FNumber\": \"" + fSupplierId + "\"\n" +
                        "        },\n" +
                        "        \"FIsModificationOperator\": false,\n" +
                        "        \"FPOOrderFinance\": {\n" +
                        "            \"FSettleCurrId\": {\n" +
                        "                \"FNumber\": \"PRE001\"\n" +
                        "            },\n" +
                        "            \"FExchangeTypeId\": {\n" +
                        "                \"FNumber\": \"HLTX01_SYS\"\n" +
                        "            },\n" +
                        "            \"FExchangeRate\": 1.0,\n" +
                        "            \"FPriceTimePoint\": \"1\",\n" +
                        "            \"FIsIncludedTax\": true,\n" +
                        "            \"FISPRICEEXCLUDETAX\": true,\n" +
                        "            \"FLocalCurrId\": {\n" +
                        "                \"FNumber\": \"PRE001\"\n" +
                        "            },\n" +
                        "            \"FSupToOderExchangeBusRate\": 1.0,\n" +
                        "            \"FSEPSETTLE\": false\n" +
                        "        },\n" +
                        "        \"FPOOrderEntry\": "+produdctArrayStr+"\n" +
                        "    }\n" +
                        "}";
                JSONObject iv_salesic = InvokeHelper.Save("PUR_PurchaseOrder", param);


                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) iv_salesic.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("采购订单保存");
                    incident.setExcuteIncidentName("PUR_PurchaseOrder,Save");
                    incident.setExcuteIncidentJSON(param);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                String Id = (((Map) (iv_salesic.get("Result")))).get("Id").toString();
                return ResponseResult.success(iv_salesic);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        } else {
            return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
        }
    }

    @Override
    public ResponseResult deletePurchaseInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Delete("STK_InStock", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("采购入库删除");
                    incident.setExcuteIncidentName("STK_InStock,Delete");
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
    public ResponseResult unAuditPurchaseInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setIds(ids);
                baseAuditBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.UnAudit("STK_InStock", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("采购入库反审核");
                    incident.setExcuteIncidentName("STK_InStock,UnAudit");
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
    public ResponseResult auditPurchaseInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        List<String> resultList = null;
        if (numbers != null) {
            String[] numbersStrList = numbers.split(",");
            resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        }
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseAuditBean baseAuditBean = new BaseAuditBean();
                baseAuditBean.setIds(ids);
                baseAuditBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseAuditBean);
                JSONObject jsonObject = InvokeHelper.Audit("STK_InStock", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("采购入库审核");
                    incident.setExcuteIncidentName("STK_InStock,Audit");
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
    public ResponseResult submitPurchaseInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        List<String> resultList = null;
        if (numbers != null) {
            String[] numbersStrList = numbers.split(",");
            resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        }

        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
                baseSubmitBean.setIds(ids);
                baseSubmitBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseSubmitBean);
                JSONObject jsonObject = InvokeHelper.Submit("STK_InStock", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("采购入库提交");
                    incident.setExcuteIncidentName("STK_InStock,Submit");
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
    public ResponseResult viewPurchaseInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseViewBean baseViewBean = new BaseViewBean();
                baseViewBean.setNumber(number);
                baseViewBean.setId(id);
                String a = JSON.toJSONString(baseViewBean);
                JSONObject jsonObject = InvokeHelper.View("STK_InStock", a);

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
    public ResponseResult cancelPurchaseInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Cancel("STK_InStock", "Cancel", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("采购入库取消");
                    incident.setExcuteIncidentName("STK_InStock,Cancel");
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
    public ResponseResult unCancelPurchaseInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.UnCancel("STK_InStock", "UnCancel", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("采购入库反取消");
                    incident.setExcuteIncidentName("STK_InStock,UnCancel");
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
    public ResponseResult batchSavePurchaseInstorage() {
        return null;
    }

    //===============================================其他入库=========================================
    @Override
    public ResponseResult saveOtherInstorage(String acctId, String dataCentreUserName, String dataCentrePassword,
                                             String fBillTypeID,
                                             String fStockOrgId,
                                             String fStockDirect,
                                             String fDate,
                                             String fDEPTID,
                                             String fSUPPLIERID,
                                             String fOwnerTypeIdHead,
                                             String fOwnerIdHead,
                                             String fBaseCurrId,
                                             String jsonArrayProduct,
                                             String fUnitID,
                                             String fSTOCKID,
                                             String fSTOCKSTATUSID,
                                             String fQty,
                                             String fOWNERTYPEID,
                                             String fOWNERID,
                                             String fKEEPERTYPEID,
                                             String fKEEPERID) {
        List produdctArray = new ArrayList();
        JSONArray productArrayIn = JSON.parseArray(jsonArrayProduct);
        for (int i = 0; i < productArrayIn.size(); i++) {
            JSONObject objectAll = new JSONObject(new LinkedHashMap());
            JSONObject objectFMATERIALID1 = new JSONObject();
            objectFMATERIALID1.put("FNumber", productArrayIn.getJSONObject(i).getString("productK3Number"));
            objectAll.put("FMATERIALID", objectFMATERIALID1);


            JSONObject objectFUnitID1 = new JSONObject();
            objectFUnitID1.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
            objectAll.put("FUnitID", objectFUnitID1);

            JSONObject objectFSTOCKID1 = new JSONObject();
            objectFSTOCKID1.put("FNumber", fSTOCKID);
            objectAll.put("FSTOCKID", objectFSTOCKID1);

            JSONObject objectFSTOCKSTATUSID1 = new JSONObject();
            objectFSTOCKSTATUSID1.put("FNumber", fSTOCKSTATUSID);
            objectAll.put("FSTOCKSTATUSID", objectFSTOCKSTATUSID1);

            objectAll.put("FOWNERTYPEID", fOWNERTYPEID);

            JSONObject objectFOWNERID1 = new JSONObject();
            objectFOWNERID1.put("FNumber", fOWNERID);
            objectAll.put("FOWNERID", objectFOWNERID1);

            objectAll.put("FKEEPERTYPEID", fKEEPERTYPEID);

            JSONObject objectFKEEPERID1 = new JSONObject();
            objectFKEEPERID1.put("FNumber", fKEEPERID);
            objectAll.put("FKEEPERID", objectFKEEPERID1);

            objectAll.put("FQty", productArrayIn.getJSONObject(i).getDouble("number"));

            produdctArray.add(objectAll);
        }
        String produdctArrayStr = produdctArray.toString();
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                //必填项：
                // 基本信息：FBillHead
                //   库存组织：FStockOrgId  (必填项)
                //	 日期：FDate  (必填项)
                //	 单据类型：FBillTypeID  (必填项)
                //	 货主类型：FOwnerTypeIdHead  (必填项)
                //	 库存方向：FStockDirect  (必填项)
                //明细信息：FEntity
                //	 物料编码：FMATERIALID  (必填项)
                //	 物料名称：FMATERIALNAME
                //	 收货仓库：FSTOCKID  (必填项)
                //	 单位：FUnitID  (必填项)
                //	 库存状态：FSTOCKSTATUSID  (必填项)
                //	 实收数量(基本单位)：FBASEQTY
                //	 库存辅单位：FSecUNITID
                //	 实收数量(库存辅单位)：FSecQTY
                //	 货主类型：FOWNERTYPEID  (必填项)
                //	 货主：FOWNERID  (必填项)
                //	 保管者类型：FKEEPERTYPEID  (必填项)
                //	 保管者：FKEEPERID  (必填项)
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
                        "    \"IsAutoSubmitAndAudit\": \"true\",\n" +
                        "    \"Model\": {\n" +
                        "        \"FID\": 0,\n" +
                        "        \"FBillTypeID\": {\n" +
                        "            \"FNUMBER\": \"" + fBillTypeID + "\"\n" +
                        "        },\n" +
                        "        \"FStockOrgId\": {\n" +
                        "            \"FNumber\": \"" + fStockOrgId + "\"\n" +
                        "        },\n" +
                        "        \"FStockDirect\": \"" + fStockDirect + "\",\n" +
                        "        \"FDate\": \"" + fDate + "\",\n" +
                        "        \"FSUPPLIERID\": {\n" +
                        "            \"FNumber\": \"" + fSUPPLIERID + "\"\n" +
                        "        },\n" +
                        "        \"FDEPTID\": {\n" +
                        "            \"FNumber\": \"" + fDEPTID + "\"\n" +
                        "        },\n" +
                        "        \"FOwnerTypeIdHead\": \"" + fOwnerTypeIdHead + "\",\n" +
                        "        \"FStockDirect\": \"" + fStockDirect + "\",\n" +
                        "        \"FOwnerIdHead\": {\n" +
                        "            \"FNumber\": \"" + fOwnerIdHead + "\"\n" +
                        "        },\n" +
                        "        \"FBaseCurrId\": {\n" +
                        "            \"FNumber\": \"" + fBaseCurrId + "\"\n" +
                        "        },\n" +
                        "        \"FEntity\": " + produdctArrayStr + "\n" +
                        "    }\n" +
                        "}";
                JSONObject jsonObject = InvokeHelper.Save("STK_MISCELLANEOUS", param);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("其他入库保存");
                    incident.setExcuteIncidentName("STK_MISCELLANEOUS,Save");
                    incident.setExcuteIncidentJSON(param);
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
    public ResponseResult viewOtherInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseViewBean baseViewBean = new BaseViewBean();
            baseViewBean.setNumber(number);
            baseViewBean.setId(id);
            String s = JSON.toJSONString(baseViewBean);
            try {
                JSONObject jsonObject = InvokeHelper.View("STK_MISCELLANEOUS", s);

                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult submitOtherInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Submit("STK_MISCELLANEOUS", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("其他入库提交");
                    incident.setExcuteIncidentName("STK_MISCELLANEOUS,Submit");
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
    public ResponseResult auditOtherInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseAuditBean baseAuditBean = new BaseAuditBean();
            baseAuditBean.setNumbers(stringList);
            baseAuditBean.setIds(ids);
            String s = JSON.toJSONString(baseAuditBean);
            try {
                JSONObject jsonObject = InvokeHelper.Audit("STK_MISCELLANEOUS", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("其他入库审核");
                    incident.setExcuteIncidentName("STK_MISCELLANEOUS,Audit");
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
    public ResponseResult unAuditOtherInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseAuditBean baseAuditBean = new BaseAuditBean();
            baseAuditBean.setNumbers(stringList);
            baseAuditBean.setIds(ids);
            String s = JSON.toJSONString(baseAuditBean);
            try {
                JSONObject jsonObject = InvokeHelper.UnAudit("STK_MISCELLANEOUS", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("其他入库反审核");
                    incident.setExcuteIncidentName("STK_MISCELLANEOUS,UnAudit");
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
    public ResponseResult deleteOtherInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        if (login) {
            BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
            baseDeleteBean.setNumbers(stringList);
            baseDeleteBean.setIds(ids);
            String s = JSON.toJSONString(baseDeleteBean);
            try {
                JSONObject delete = InvokeHelper.Delete("STK_MISCELLANEOUS", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) delete.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("其他入库删除");
                    incident.setExcuteIncidentName("STK_MISCELLANEOUS,Delete");
                    incident.setExcuteIncidentJSON(s);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return ResponseResult.success(delete);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult unCancelOtherInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.UnCancel("STK_MISCELLANEOUS", "UnCancel", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("其他入库反取消");
                    incident.setExcuteIncidentName("STK_MISCELLANEOUS,UnCancel");
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
    public ResponseResult cancelOtherInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Cancel("STK_MISCELLANEOUS", "Cancel", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("其他入库取消");
                    incident.setExcuteIncidentName("STK_MISCELLANEOUS,Cancel");
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
    public ResponseResult batchSaveOtherInstorage() {
        return null;
    }

    //=====================================仓库状态============================================
    @Override
    public ResponseResult batchSaveStockStatus() {
        return null;
    }

    @Override
    public ResponseResult viewStockStatus(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseViewBean baseViewBean = new BaseViewBean();
            baseViewBean.setNumber(number);
            baseViewBean.setId(id);
            String s = JSON.toJSONString(baseViewBean);
            try {
                JSONObject jsonObject = InvokeHelper.View("BD_StockStatus", s);

                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult submitStockStatus(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Submit("BD_StockStatus", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("仓库状态提交");
                    incident.setExcuteIncidentName("BD_StockStatus,Submit");
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
    public ResponseResult auditStockStatus(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseAuditBean baseAuditBean = new BaseAuditBean();
            baseAuditBean.setNumbers(stringList);
            baseAuditBean.setIds(ids);
            String s = JSON.toJSONString(baseAuditBean);
            try {
                JSONObject jsonObject = InvokeHelper.Audit("BD_StockStatus", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("仓库状态审核");
                    incident.setExcuteIncidentName("BD_StockStatus,Audit");
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
    public ResponseResult unAuditStockStatus(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseAuditBean baseAuditBean = new BaseAuditBean();
            baseAuditBean.setNumbers(stringList);
            baseAuditBean.setIds(ids);
            String s = JSON.toJSONString(baseAuditBean);
            try {
                JSONObject jsonObject = InvokeHelper.UnAudit("BD_StockStatus", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("仓库状态反审核");
                    incident.setExcuteIncidentName("BD_StockStatus,UnAudit");
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
    public ResponseResult deleteStockStatus(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        if (login) {
            BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
            baseDeleteBean.setNumbers(stringList);
            baseDeleteBean.setIds(ids);
            String s = JSON.toJSONString(baseDeleteBean);
            try {
                JSONObject delete = InvokeHelper.Delete("BD_StockStatus", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) delete.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("仓库状态删除");
                    incident.setExcuteIncidentName("BD_StockStatus,Delete");
                    incident.setExcuteIncidentJSON(s);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return ResponseResult.success(delete);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult enableStockStatus(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Enable("BD_StockStatus", "Enable", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("仓库状态启用");
                    incident.setExcuteIncidentName("BD_StockStatus,Enable");
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
    public ResponseResult forbidStockStatus(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Forbid("BD_StockStatus", "Forbid", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("仓库状态禁用");
                    incident.setExcuteIncidentName("BD_StockStatus,Forbid");
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
    public ResponseResult saveStockStatus(String acctId, String dataCentreUserName, String dataCentrePassword, String fName, String fNumberCreateOrg, String fNumberUseOrg, String fType, String fNumber, String fStockStatusId) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                //必填项：
                //  名称：FName  (必填项)
                //  创建组织：FCreateOrgId  (必填项)
                //	 使用组织：FUseOrgId  (必填项)
                //	 类型：FType  (必填项)
                StockStatusModel model = new StockStatusModel();
                model.setFName(fName);
                FCreateOrgId fCreateOrgId = new FCreateOrgId();
                fCreateOrgId.setFNumber(fNumberCreateOrg);
                model.setFCreateOrgId(fCreateOrgId);
                FUseOrgId fUseOrgId = new FUseOrgId();
                fUseOrgId.setFNumber(fNumberUseOrg);
                model.setFUseOrgId(fUseOrgId);
                model.setFType(fType);
                if (!StringUtils.isBlank(fNumber)) {
                    model.setFNumber(fNumber);
                }
                if (!StringUtils.isBlank(fStockStatusId)) {
                    model.setFStockStatusId(Integer.valueOf(fStockStatusId));
                }
                StockStatusSaveParam stockStatusSaveParam = new StockStatusSaveParam();
                stockStatusSaveParam.setModel(model);
                String s = JSON.toJSONString(stockStatusSaveParam);
                JSONObject jsonObect = InvokeHelper.Save("BD_StockStatus", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObect.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("仓库状态保存");
                    incident.setExcuteIncidentName("BD_StockStatus,Save");
                    incident.setExcuteIncidentJSON(s);
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
    public ResponseResult batchSaveStock() {
        return null;
    }

    //================================仓库============================================
    @Override
    public ResponseResult viewStock(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseViewBean baseViewBean = new BaseViewBean();
            baseViewBean.setNumber(number);
            baseViewBean.setId(id);
            String s = JSON.toJSONString(baseViewBean);
            try {
                JSONObject jsonObject = InvokeHelper.View("BD_STOCK", s);

                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult submitStock(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Submit("BD_STOCK", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("仓库提交");
                    incident.setExcuteIncidentName("BD_STOCK,Submit");
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
    public ResponseResult auditStock(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseAuditBean baseAuditBean = new BaseAuditBean();
            baseAuditBean.setNumbers(stringList);
            baseAuditBean.setIds(ids);
            String s = JSON.toJSONString(baseAuditBean);
            try {
                JSONObject jsonObject = InvokeHelper.Audit("BD_STOCK", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("仓库审核");
                    incident.setExcuteIncidentName("BD_STOCK,Audit");
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
    public ResponseResult unAuditStock(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseAuditBean baseAuditBean = new BaseAuditBean();
            baseAuditBean.setNumbers(stringList);
            baseAuditBean.setIds(ids);
            String s = JSON.toJSONString(baseAuditBean);
            try {
                JSONObject jsonObject = InvokeHelper.UnAudit("BD_STOCK", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("仓库反审核");
                    incident.setExcuteIncidentName("BD_STOCK,UnAudit");
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
    public ResponseResult deleteStock(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        if (login) {
            BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
            baseDeleteBean.setNumbers(stringList);
            baseDeleteBean.setIds(ids);
            String s = JSON.toJSONString(baseDeleteBean);
            try {
                JSONObject delete = InvokeHelper.Delete("BD_STOCK", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) delete.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("仓库删除");
                    incident.setExcuteIncidentName("BD_STOCK,Delete");
                    incident.setExcuteIncidentJSON(s);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return ResponseResult.success(delete);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult enableStock(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Enable("BD_STOCK", "Enable", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("仓库启用");
                    incident.setExcuteIncidentName("BD_STOCK,Enable");
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
    public ResponseResult forbidStock(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Forbid("BD_STOCK", "Forbid", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("仓库禁用");
                    incident.setExcuteIncidentName("BD_STOCK,Forbid");
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
    public ResponseResult saveStock(String acctId,
                                    String dataCentreUserName,
                                    String dataCentrePassword,
                                    String fName,
                                    String fNumberCreateOrg,
                                    String fNumerUseOrg,
                                    String fStockProperty,
                                    String fStockStatusType,
                                    String fNumber,
                                    String fStockId) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                //必填项：
                //   名称：FName  (必填项)
                //   创建组织：FCreateOrgId  (必填项)
                //	 使用组织：FUseOrgId  (必填项)
                //	 仓库属性：FStockProperty  (必填项)
                //	 库存状态类型：FStockStatusType  (必填项)
                StockModel stockModel = new StockModel();
                stockModel.setFName(fName);
                FCreateOrgId fCreateOrgId = new FCreateOrgId();
                fCreateOrgId.setFNumber(fNumberCreateOrg);
                stockModel.setFCreateOrgId(fCreateOrgId);
                FUseOrgId fUseOrgId = new FUseOrgId();
                fUseOrgId.setFNumber(fNumerUseOrg);
                stockModel.setFUseOrgId(fUseOrgId);
                stockModel.setFStockProperty(fStockProperty);
                stockModel.setFStockStatusType(fStockStatusType);
                if (!StringUtils.isBlank(fNumber)) {
                    stockModel.setFNumber(fNumber);
                }
                if (!StringUtils.isBlank(fStockId)) {
                    stockModel.setFStockId(Integer.valueOf(fStockId));
                }
                StockSaveParam stockSaveParam = new StockSaveParam();
                stockSaveParam.setModel(stockModel);
                stockSaveParam.setIsautosubmitandaudit(true);
                String s = JSON.toJSONString(stockSaveParam);
                JSONObject jsonObject = InvokeHelper.Save("BD_STOCK", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("仓库保存");
                    incident.setExcuteIncidentName("BD_STOCK,Save");
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

    //===================================即时库存=====================================
    @Override
    public ResponseResult saveInventory(String acctId, String dataCentreUserName, String dataCentrePassword, InventorySaveParam inventorySaveParam) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                //必填项：无
                String s = JSON.toJSONString(inventorySaveParam);
                JSONObject jsonObject = InvokeHelper.Save("STK_Inventory", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("即时库存保存");
                    incident.setExcuteIncidentName("STK_Inventory,Save");
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
    public ResponseResult batchSaveInventory() {
        return null;
    }

    @Override
    public ResponseResult queryInventory(String acctId, String dataCentreUserName, String dataCentrePassword, String formId, String fieldKeys) {
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

    //=======================================其他出库=================================
    @Override
    public ResponseResult saveOtherOutstorage(String acctId,
                                              String dataCentreUserName,
                                              String dataCentrePassword,
                                              String fStockOrgId,
                                              String fPickOrgId,
                                              String fDate,
                                              String fCustId,
                                              String fOwnerIdHead,
                                              String jsonArrayProduct,
                                              String fStockDirect) {
        List produdctArray = new ArrayList();
        JSONArray productArrayIn = JSON.parseArray(jsonArrayProduct);

        for (int i = 0; i < productArrayIn.size(); i++) {
            JSONObject objectAll = new JSONObject(new LinkedHashMap());

            JSONObject objectFMaterialId = new JSONObject();
            objectFMaterialId.put("FNumber", productArrayIn.getJSONObject(i).getString("productK3Number"));
            objectAll.put("FMaterialId", objectFMaterialId);

            JSONObject objectFUnitID = new JSONObject();
            objectFUnitID.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
            objectAll.put("FUnitID", objectFUnitID);

            objectAll.put("FQty", productArrayIn.getJSONObject(i).getDouble("number"));

            JSONObject objectFBaseUnitId = new JSONObject();
            objectFBaseUnitId.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
            objectAll.put("FBaseUnitId", objectFBaseUnitId);

            JSONObject objectFStockId = new JSONObject();
            objectFStockId.put("FNumber", productArrayIn.getJSONObject(i).getString("stockK3Nunber"));
            objectAll.put("FStockId", objectFStockId);

            objectAll.put("FOwnerTypeId", "BD_OwnerOrg");

            JSONObject objectFOwnerId = new JSONObject();
            objectFOwnerId.put("FNumber", productArrayIn.getJSONObject(i).getString("fOwnerID"));
            objectAll.put("FOwnerId", objectFOwnerId);

            JSONObject objectFStockStatusId = new JSONObject();
            objectFStockStatusId.put("FNumber", "KCZT01_SYS");
            objectAll.put("FStockStatusId", objectFStockStatusId);

            objectAll.put("FKeeperTypeId", "BD_KeeperOrg");

            objectAll.put("FDistribution", false);

            JSONObject objectFKeeperId = new JSONObject();
            objectFKeeperId.put("FNumber", productArrayIn.getJSONObject(i).getString("fOwnerID"));
            objectAll.put("FKeeperId", objectFKeeperId);


            produdctArray.add(objectAll);
        }
        String produdctArrayStr = produdctArray.toString();
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                //必填项：
                // 基本信息：FBillHead
                //   库存组织：FStockOrgId  (必填项)
                //	 日期：FDate  (必填项)
                //	 单据类型：FBillTypeID  (必填项)
                //	 货主类型：FOwnerTypeIdHead  (必填项)
                //	 库存方向：FStockDirect  (必填项)
                //	 业务类型：FBizType  (必填项)
                //明细信息：FEntity
                //	 物料编码：FMaterialId  (必填项)
                //	 物料名称：FMaterialName
                //	 发货仓库：FStockId  (必填项)
                //	 单位：FUnitID  (必填项)
                //	 库存状态：FStockStatusId  (必填项)
                //	 货主类型：FOwnerTypeId  (必填项)
                //	 货主：FOwnerId  (必填项)
                //	 保管者类型：FKeeperTypeId  (必填项)
                //	 保管者：FKeeperId  (必填项)
                //	 基本单位：FBaseUnitId  (必填项)
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
                        "    \"IsAutoSubmitAndAudit\": \"true\",\n" +
                        "    \"Model\": {\n" +
                        "        \"FID\": 0,\n" +
                        "        \"FBillTypeID\": {\n" +
                        "            \"FNUMBER\": \"QTCKD01_SYS\"\n" +
                        "        },\n" +
                        "        \"FStockOrgId\": {\n" +
                        "            \"FNumber\": \"" + fStockOrgId + "\"\n" +
                        "        },\n" +
                        "        \"FPickOrgId\": {\n" +
                        "            \"FNumber\": \"" + fPickOrgId + "\"\n" +
                        "        },\n" +
                        "        \"FStockDirect\": \"" + fStockDirect + "\",\n" +
                        "        \"FDate\": \"" + fDate + "\",\n" +
                        "        \"FCustId\": {\n" +
                        "            \"FNumber\": \"" + fCustId + "\"\n" +
                        "        },\n" +
                        "        \"FOwnerTypeIdHead\": \"BD_OwnerOrg\",\n" +
                        "        \"FOwnerIdHead\": {\n" +
                        "            \"FNumber\": \"" + fOwnerIdHead + "\"\n" +
                        "        },\n" +
                        "        \"FBaseCurrId\": {\n" +
                        "            \"FNumber\": \"PRE001\"\n" +
                        "        },\n" +
                        "        \"FEntity\": " + produdctArrayStr + "\n" +
                        "    }\n" +
                        "}";
                JSONObject jsonObject = InvokeHelper.Save("STK_MisDelivery", param);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("其他出库保存");
                    incident.setExcuteIncidentName("STK_MisDelivery,Save");
                    incident.setExcuteIncidentJSON(param);
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
    public ResponseResult viewOtherOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseViewBean baseViewBean = new BaseViewBean();
            baseViewBean.setNumber(number);
            baseViewBean.setId(id);
            String s = JSON.toJSONString(baseViewBean);
            try {
                JSONObject jsonObject = InvokeHelper.View("STK_MisDelivery", s);
                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult submitOtherOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Submit("STK_MisDelivery", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("其他出库提交");
                    incident.setExcuteIncidentName("STK_MisDelivery,Submit");
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
    public ResponseResult auditOtherOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseAuditBean baseAuditBean = new BaseAuditBean();
            baseAuditBean.setNumbers(stringList);
            baseAuditBean.setIds(ids);
            String s = JSON.toJSONString(baseAuditBean);
            try {
                JSONObject jsonObject = InvokeHelper.Audit("STK_MisDelivery", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("其他出库审核");
                    incident.setExcuteIncidentName("STK_MisDelivery,Audit");
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
    public ResponseResult unAuditOtherOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseAuditBean baseAuditBean = new BaseAuditBean();
            baseAuditBean.setNumbers(stringList);
            baseAuditBean.setIds(ids);
            String s = JSON.toJSONString(baseAuditBean);
            try {
                JSONObject jsonObject = InvokeHelper.UnAudit("STK_MisDelivery", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("其他出库反审核");
                    incident.setExcuteIncidentName("STK_MisDelivery,UnAudit");
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
    public ResponseResult deleteOtherOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        if (login) {
            BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
            baseDeleteBean.setNumbers(stringList);
            baseDeleteBean.setIds(ids);
            String s = JSON.toJSONString(baseDeleteBean);
            try {
                JSONObject delete = InvokeHelper.Delete("STK_MisDelivery", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) delete.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("其他出库删除");
                    incident.setExcuteIncidentName("STK_MisDelivery,Delete");
                    incident.setExcuteIncidentJSON(s);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return ResponseResult.success(delete);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult unCancelOtherOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.UnCancel("STK_MisDelivery", "UnCancel", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("其他出库反取消");
                    incident.setExcuteIncidentName("STK_MisDelivery,UnCancel");
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
    public ResponseResult cancelOtherOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Cancel("STK_MisDelivery", "Cancel", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("其他出库取消");
                    incident.setExcuteIncidentName("STK_MisDelivery,Cancel");
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
    public ResponseResult batchSaveOtherOutstorage() {
        return null;
    }

    //===================================销售出库=====================================
    @Override
    public ResponseResult saveMarketOutstorage(String acctId,
                                               String dataCentreUserName,
                                               String dataCentrePassword,
                                               String fNumberSaleOrg,
                                               String fDate,
                                               String fNumberStockOrg,
                                               String fNumberCustomer,
                                               String fNumberSettleOrg,
                                               String jsonArrayProduct) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                //必填项：
                //基本信息：FBillHead
                //	 销售组织：FSaleOrgId  (必填项)
                //	 日期：FDate  (必填项)
                //	 发货组织：FStockOrgId  (必填项)
                //	 客户：FCustomerID  (必填项)
                //	 单据类型：FBillTypeID  (必填项)
                //财务信息：SubHeadEntity
                //	 结算组织：FSettleOrgID  (必填项)
                //	 结算币别：FSettleCurrID  (必填项)
                //明细信息：FEntity
                //	 库存单位：FUnitID  (必填项)
                //	 货主：FOwnerID  (必填项)
                // 物流跟踪明细：FOutStockTrace
                //	 物流单号：FCarryBillNo  (必填项)
                List produdctArray = new ArrayList();
                JSONArray productArrayIn = JSON.parseArray(jsonArrayProduct);
                for (int i = 0; i < productArrayIn.size(); i++) {
                    JSONObject objectAll = new JSONObject(new LinkedHashMap());
                    objectAll.put("FRowType", "Standard");

                    JSONObject objectFMaterialID = new JSONObject();
                    objectFMaterialID.put("FNumber", productArrayIn.getJSONObject(i).getString("productK3Number"));
                    objectAll.put("FMaterialID", objectFMaterialID);

                    JSONObject objectFUnitID1 = new JSONObject();
                    objectFUnitID1.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
                    objectAll.put("FUnitID", objectFUnitID1);

                    objectAll.put("FRealQty", productArrayIn.getJSONObject(i).getString("number"));

                    objectAll.put("FIsFree", false);

                    objectAll.put("FOwnerTypeID", "BD_OwnerOrg");

                    JSONObject objectFOwnerID = new JSONObject();
                    objectFOwnerID.put("FNumber", productArrayIn.getJSONObject(i).getString("fOwnerID"));
                    objectAll.put("FOwnerID", objectFOwnerID);

                    JSONObject objectFStockID = new JSONObject();
                    objectFStockID.put("FNumber", productArrayIn.getJSONObject(i).getString("stockK3Nunber"));
                    objectAll.put("FStockID", objectFStockID);

                    JSONObject objectFStockStatusID = new JSONObject();
                    objectFStockStatusID.put("FNumber", "KCZT01_SYS");
                    objectAll.put("FStockStatusID", objectFStockStatusID);

                    JSONObject objectFSalUnitID = new JSONObject();
                    objectFSalUnitID.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
                    objectAll.put("FSalUnitID", objectFSalUnitID);

                    objectAll.put("FSALUNITQTY", productArrayIn.getJSONObject(i).getString("number"));
                    objectAll.put("FSALBASEQTY", productArrayIn.getJSONObject(i).getString("number"));
                    objectAll.put("FPRICEBASEQTY", productArrayIn.getJSONObject(i).getString("number"));
                    objectAll.put("FOUTCONTROL", false);
                    objectAll.put("FIsOverLegalOrg", false);
                    objectAll.put("FARNOTJOINQTY", productArrayIn.getJSONObject(i).getString("number"));
                    objectAll.put("FCheckDelivery", false);

                    produdctArray.add(objectAll);
                }
                String produdctArrayStr = produdctArray.toString();
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
                        "    \"Model\": {\n" +
                        "        \"FID\": 0,\n" +
                        "        \"FBillTypeID\": {\n" +
                        "            \"FNUMBER\": \"XSCKD01_SYS\"\n" +
                        "        },\n" +
                        "        \"FDate\": \"" + fDate + "\",\n" +
                        "        \"FSaleOrgId\": {\n" +
                        "            \"FNumber\": \"" + fNumberSaleOrg + "\"\n" +
                        "        },\n" +
                        "        \"FCustomerID\": {\n" +
                        "            \"FNumber\": \"" + fNumberCustomer + "\"\n" +
                        "        },\n" +
                        "        \"FStockOrgId\": {\n" +
                        "            \"FNumber\": \"" + fNumberStockOrg + "\"\n" +
                        "        },\n" +
                        "        \"FReceiverID\": {\n" +
                        "            \"FNumber\": \"" + fNumberCustomer + "\"\n" +
                        "        },\n" +
                        "        \"FSettleID\": {\n" +
                        "            \"FNumber\": \"" + fNumberCustomer + "\"\n" +
                        "        },\n" +
                        "        \"FPayerID\": {\n" +
                        "            \"FNumber\": \"" + fNumberCustomer + "\"\n" +
                        "        },\n" +
                        "        \"FOwnerTypeIdHead\": \"BD_OwnerOrg\",\n" +
                        "        \"FIsTotalServiceOrCost\": false,\n" +
                        "        \"SubHeadEntity\": {\n" +
                        "            \"FSettleCurrID\": {\n" +
                        "                \"FNumber\": \"PRE001\"\n" +
                        "            },\n" +
                        "            \"FSettleOrgID\": {\n" +
                        "                \"FNumber\": \"" + fNumberSettleOrg + "\"\n" +
                        "            },\n" +
                        "            \"FLocalCurrID\": {\n" +
                        "                \"FNumber\": \"PRE001\"\n" +
                        "            },\n" +
                        "            \"FExchangeTypeID\": {\n" +
                        "                \"FNumber\": \"HLTX01_SYS\"\n" +
                        "            },\n" +
                        "            \"FExchangeRate\": \"1\",\n" +
                        "            \"FIsIncludedTax\": true,\n" +
                        "            \"FIsPriceExcludeTax\": true\n" +
                        "        },\n" +
                        "        \"FEntity\": " + produdctArrayStr + "\n" +
                        "    }\n" +
                        "}";
                JSONObject jsonObject = InvokeHelper.Save("SAL_OUTSTOCK", param);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("销售出库保存");
                    incident.setExcuteIncidentName("SAL_OUTSTOCK,Save");
                    incident.setExcuteIncidentJSON(param);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                JSONObject saveresult = (JSONObject) jsonObject.get("Result");
                JSONObject saveStatus = (JSONObject) saveresult.get("ResponseStatus");
                Boolean isSaveSuccess = (Boolean) saveStatus.get("IsSuccess");
                Integer id = Integer.parseInt(saveresult.get("Id").toString());
                if (isSaveSuccess) {
                    //提交
                    this.submitMarketOutstorage(acctId, dataCentreUserName, dataCentrePassword, null, id.toString());
                    //审核
                    ResponseResult responseResultAudit = this.auditMarketOutstorage(acctId, dataCentreUserName, dataCentrePassword, null, id.toString());
                    JSONObject objectHashMap = (JSONObject) responseResultAudit.getResult();
                    JSONObject auditResult = (JSONObject) objectHashMap.get("Result");
                    JSONObject auditStatus = (JSONObject) auditResult.get("ResponseStatus");
                    Boolean isAuditSuccess = (Boolean) auditStatus.get("IsSuccess");
                    if (isAuditSuccess) {
                        return ResponseResult.success(jsonObject);
                    }
                } else {
                    return ResponseResult.success("k3插入失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult marketOutstorageOrderSave(String acctId, String dataCentreUserName, String dataCentrePassword, String fNumberSaleOrg, String fDate, String fNumberCustomer, String jsonArrayProduct,String fSaleDeptId,String fSalerId) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                List produdctArray = new ArrayList();
                JSONArray productArrayIn = JSON.parseArray(jsonArrayProduct);
                for (int i = 0; i < productArrayIn.size(); i++) {
                    JSONObject objectAll = new JSONObject(new LinkedHashMap());
                    objectAll.put("FRowType", "Standard");

                    JSONObject objectFMaterialID = new JSONObject();
                    objectFMaterialID.put("FNumber", productArrayIn.getJSONObject(i).getString("productK3Number"));
                    objectAll.put("FMaterialID", objectFMaterialID);

                    JSONObject objectFUnitID1 = new JSONObject();
                    objectFUnitID1.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
                    objectAll.put("FUnitID", objectFUnitID1);

                    objectAll.put("FQty", productArrayIn.getJSONObject(i).getString("number"));

                    objectAll.put("FPrice", productArrayIn.getJSONObject(i).getString("fPrice"));

                    objectAll.put("FTaxPrice", productArrayIn.getJSONObject(i).getString("fPrice"));

                    objectAll.put("FIsFree", false);

                    objectAll.put("FDeliveryDate", fDate);

                    JSONObject objectFStockOrgId = new JSONObject();
                    objectFStockOrgId.put("FNumber", fNumberSaleOrg);
                    objectAll.put("FStockOrgId", objectFStockOrgId);

                    JSONObject objectFSettleOrgIds = new JSONObject();
                    objectFSettleOrgIds.put("FNumber", fNumberSaleOrg);
                    objectAll.put("FSettleOrgIds", objectFSettleOrgIds);

                    JSONObject objectFSupplyOrgId = new JSONObject();
                    objectFSupplyOrgId.put("FNumber", fNumberSaleOrg);
                    objectAll.put("FSupplyOrgId", objectFSupplyOrgId);

                    objectAll.put("FOwnerTypeID", "BD_OwnerOrg");

                    JSONObject objectFOwnerID = new JSONObject();
                    objectFOwnerID.put("FNumber", fNumberSaleOrg);
                    objectAll.put("FOwnerID", objectFOwnerID);

                    objectAll.put("FReserveType", "1");

                    objectAll.put("FPriceBaseQty", productArrayIn.getJSONObject(i).getString("number"));

                    JSONObject objectFStockUnitID = new JSONObject();
                    objectFStockUnitID.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
                    objectAll.put("FStockUnitID", objectFStockUnitID);

                    objectAll.put("FStockQty", productArrayIn.getJSONObject(i).getString("number"));

                    objectAll.put("FStockBaseQty", productArrayIn.getJSONObject(i).getString("number"));

                    objectAll.put("FOUTLMTUNIT", "SAL");

                    JSONObject objectFOutLmtUnitID = new JSONObject();
                    objectFOutLmtUnitID.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
                    objectAll.put("FOutLmtUnitID", objectFOutLmtUnitID);

                    objectAll.put("FISMRP", false);



                    JSONArray jsonArrayFOrderEntryPlan = new JSONArray();
                    JSONObject objectAll2 = new JSONObject();
                    objectAll2.put("FPlanDate", fDate);
                    objectAll2.put("FPlanQty", productArrayIn.getJSONObject(i).getString("number"));
                    jsonArrayFOrderEntryPlan.add(objectAll2);
                    objectAll.put("FOrderEntryPlan",jsonArrayFOrderEntryPlan);

                    produdctArray.add(objectAll);
                }


                String produdctArrayStr = produdctArray.toString();
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
                        "    \"IsAutoSubmitAndAudit\": \"true\",\n" +
                        "    \"Model\": {\n" +
                        "        \"FID\": 0,\n" +
                        "        \"FBillTypeID\": {\n" +
                        "            \"FNUMBER\": \"XSDD01_SYS\"\n" +
                        "        },\n" +
                        "        \"FDate\": \""+fDate+"\",\n" +
                        "        \"FSaleOrgId\": {\n" +
                        "            \"FNumber\": \""+fNumberSaleOrg+"\"\n" +
                        "        },\n" +
                        "        \"FCustId\": {\n" +
                        "            \"FNumber\": \""+fNumberCustomer+"\"\n" +
                        "        },\n" +
                        "        \"FReceiveId\": {\n" +
                        "            \"FNumber\": \""+fNumberCustomer+"\"\n" +
                        "        },\n" +
                        "        \"FSaleDeptId\": {\n" +
                        "            \"FNumber\": \""+fSaleDeptId+"\"\n" +
                        "        },\n" +
                        "        \"FSalerId\": {\n" +
                        "            \"FNumber\": \""+fSalerId+"\"\n" +
                        "        },\n" +
                        "        \"FSettleId\": {\n" +
                        "            \"FNumber\": \""+fNumberCustomer+"\"\n" +
                        "        },\n" +
                        "        \"FChargeId\": {\n" +
                        "            \"FNumber\": \""+fNumberCustomer+"\"\n" +
                        "        },\n" +
                        "        \"FISINIT\": false,\n" +
                        "        \"FIsMobile\": false,\n" +
                        "        \"FSaleOrderFinance\": {\n" +
                        "            \"FSettleCurrId\": {\n" +
                        "                \"FNumber\": \"PRE001\"\n" +
                        "            },\n" +
                        "            \"FIsPriceExcludeTax\": true,\n" +
                        "            \"FIsIncludedTax\": true,\n" +
                        "            \"FExchangeTypeId\": {\n" +
                        "                \"FNumber\": \"HLTX01_SYS\"\n" +
                        "            },\n" +
                        "            \"FOverOrgTransDirect\": false\n" +
                        "        },\n" +
                        "        \"FSaleOrderEntry\": "+produdctArrayStr+",\n" +
                        "    }\n" +
                        "}";
                JSONObject jsonObject = InvokeHelper.Save("SAL_SaleOrder", param);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("销售订单保存");
                    incident.setExcuteIncidentName("SAL_SaleOrder,Save");
                    incident.setExcuteIncidentJSON(param);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult viewMarketOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseViewBean baseViewBean = new BaseViewBean();
            baseViewBean.setNumber(number);
            baseViewBean.setId(id);
            String s = JSON.toJSONString(baseViewBean);
            try {
                JSONObject jsonObject = InvokeHelper.View("SAL_OUTSTOCK", s);

                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult submitMarketOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        List<String> stringList = null;
        if (numbers != null) {
            String[] split = numbers.split(",");
            stringList = Arrays.asList(split);
        }

        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Submit("SAL_OUTSTOCK", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("销售出库提交");
                    incident.setExcuteIncidentName("SAL_OUTSTOCK,Submit");
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
    public ResponseResult auditMarketOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        List<String> stringList = null;
        if (numbers != null) {
            String[] split = numbers.split(",");
            stringList = Arrays.asList(split);
        }


        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseAuditBean baseAuditBean = new BaseAuditBean();
            baseAuditBean.setNumbers(stringList);
            baseAuditBean.setIds(ids);
            String s = JSON.toJSONString(baseAuditBean);
            try {
                JSONObject jsonObject = InvokeHelper.Audit("SAL_OUTSTOCK", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("销售出库审核");
                    incident.setExcuteIncidentName("SAL_OUTSTOCK,Audit");
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
    public ResponseResult unAuditMarketOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseAuditBean baseAuditBean = new BaseAuditBean();
            baseAuditBean.setNumbers(stringList);
            baseAuditBean.setIds(ids);
            String s = JSON.toJSONString(baseAuditBean);
            try {
                JSONObject jsonObject = InvokeHelper.UnAudit("SAL_OUTSTOCK", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("销售出库反审核");
                    incident.setExcuteIncidentName("SAL_OUTSTOCK,UnAudit");
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
    public ResponseResult deleteMarketOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        if (login) {
            BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
            baseDeleteBean.setNumbers(stringList);
            baseDeleteBean.setIds(ids);
            String s = JSON.toJSONString(baseDeleteBean);
            try {
                JSONObject delete = InvokeHelper.Delete("SAL_OUTSTOCK", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) delete.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("销售出库删除");
                    incident.setExcuteIncidentName("SAL_OUTSTOCK,Delete");
                    incident.setExcuteIncidentJSON(s);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return ResponseResult.success(delete);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult unCancelMarketOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.UnCancel("SAL_OUTSTOCK", "UnCancel", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("销售出库反取消");
                    incident.setExcuteIncidentName("SAL_OUTSTOCK,UnCancel");
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
    public ResponseResult cancelMarketOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Cancel("SAL_OUTSTOCK", "Cancel", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("销售出库取消");
                    incident.setExcuteIncidentName("SAL_OUTSTOCK,Cancel");
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
    public ResponseResult batchSaveMarketOutstorage() {
        return null;
    }


    //===========================================销售退货===============================================
    @Override
    public ResponseResult saveMarketReturn(String acctId,
                                           String dataCentreUserName,
                                           String dataCentrePassword,
                                           String fBillTypeID,
                                           String fDate,
                                           String fSaleOrgId,
                                           String fRetcustId,
                                           String fReturnReason,
                                           String fStockOrgId,
                                           String fReceiveCustId,
                                           String fSettleCustId,
                                           String fPayCustId,
                                           String fSettleCurrId,
                                           String fSettleOrgId,
                                           String jsonArrayProduct) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                List produdctArray = new ArrayList();
                JSONArray productArrayIn = JSON.parseArray(jsonArrayProduct);
                for (int i = 0; i < productArrayIn.size(); i++) {
                    JSONObject objectAll = new JSONObject(new LinkedHashMap());

                    objectAll.put("FRowType", "Standard");

                    JSONObject objectFMaterialId = new JSONObject();
                    objectFMaterialId.put("FNumber", productArrayIn.getJSONObject(i).getString("productK3Number"));
                    objectAll.put("FMaterialId", objectFMaterialId);

                    JSONObject objectFUnitID = new JSONObject();
                    objectFUnitID.put("FNumber", productArrayIn.getJSONObject(i).getString("fUnitID"));
                    objectAll.put("FUnitID", objectFUnitID);

                    objectAll.put("FRealQty", productArrayIn.getJSONObject(i).getDouble("fRealQty"));
                    objectAll.put("FIsFree", false);

                    JSONObject objectFReturnType = new JSONObject();
                    objectFReturnType.put("FNumber", "THLX01_SYS");
                    objectAll.put("FReturnType", objectFReturnType);

                    objectAll.put("FOwnerTypeId", "BD_OwnerOrg");


                    JSONObject objectFOwnerId = new JSONObject();
                    objectFOwnerId.put("FNumber", productArrayIn.getJSONObject(i).getString("fOwnerId"));
                    objectAll.put("FOwnerId", objectFOwnerId);


                    JSONObject objectFStockId = new JSONObject();
                    objectFStockId.put("FNumber", productArrayIn.getJSONObject(i).getString("fStockId"));
                    objectAll.put("FStockId", objectFStockId);


                    JSONObject objectFStockStatusId = new JSONObject();
                    objectFStockStatusId.put("FNumber", "KCZT01_SYS");
                    objectAll.put("FStockStatusId", objectFStockStatusId);

                    objectAll.put("FDeliveryDate", fDate);

                    JSONObject objectFSalUnitID = new JSONObject();
                    objectFSalUnitID.put("FNumber", productArrayIn.getJSONObject(i).getString("fSalUnitID"));
                    objectAll.put("FSalUnitID", objectFSalUnitID);

                    objectAll.put("FSalUnitQty", productArrayIn.getJSONObject(i).getDouble("fSalUnitQty"));

                    objectAll.put("FSalBaseQty", productArrayIn.getJSONObject(i).getDouble("fSalBaseQty"));

                    objectAll.put("FPriceBaseQty", productArrayIn.getJSONObject(i).getDouble("fPriceBaseQty"));

                    objectAll.put("FIsOverLegalOrg", false);
                    objectAll.put("FARNOTJOINQTY", productArrayIn.getJSONObject(i).getDouble("fARNOTJOINQTY"));
                    objectAll.put("FIsReturnCheck", false);


                    produdctArray.add(objectAll);
                }
                String produdctArrayStr = produdctArray.toString();


                String s = "{\n" +
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
                        "        \"FID\": 0,\n" +
                        "        \"FBillTypeID\": {\n" +
                        "            \"FNUMBER\": \"" + fBillTypeID + "\"\n" +
                        "        },\n" +
                        "        \"FDate\": \"" + fDate + "\",\n" +
                        "        \"FSaleOrgId\": {\n" +
                        "            \"FNumber\": \"" + fSaleOrgId + "\"\n" +
                        "        },\n" +
                        "        \"FRetcustId\": {\n" +
                        "            \"FNumber\": \"" + fRetcustId + "\"\n" +
                        "        },\n" +
                        "        \"FReturnReason\": {\n" +
                        "            \"FNumber\": \"" + fReturnReason + "\"\n" +
                        "        },\n" +
                        "        \"FTransferBizType\": {\n" +
                        "            \"FNumber\": \"OverOrgSal\"\n" +
                        "        },\n" +
                        "        \"FStockOrgId\": {\n" +
                        "            \"FNumber\": \"" + fSaleOrgId + "\"\n" +
                        "        },\n" +
                        "        \"FReceiveCustId\": {\n" +
                        "            \"FNumber\": \"" + fReceiveCustId + "\"\n" +
                        "        },\n" +
                        "        \"FSettleCustId\": {\n" +
                        "            \"FNumber\": \"" + fSettleCustId + "\"\n" +
                        "        },\n" +
                        "        \"FPayCustId\": {\n" +
                        "            \"FNumber\": \"" + fPayCustId + "\"\n" +
                        "        },\n" +
                        "        \"FOwnerTypeIdHead\": \"BD_OwnerOrg\",\n" +
                        "        \"FIsTotalServiceOrCost\": false,\n" +
                        "        \"SubHeadEntity\": {\n" +
                        "            \"FSettleCurrId\": {\n" +
                        "                \"FNumber\": \"" + fSettleCurrId + "\"\n" +
                        "            },\n" +
                        "            \"FSettleOrgId\": {\n" +
                        "                \"FNumber\": \"" + fSettleOrgId + "\"\n" +
                        "            },\n" +
                        "            \"FLocalCurrId\": {\n" +
                        "                \"FNumber\": \"" + fSettleCurrId + "\"\n" +
                        "            },\n" +
                        "            \"FExchangeTypeId\": {\n" +
                        "                \"FNumber\": \"HLTX01_SYS\"\n" +
                        "            },\n" +
                        "            \"FExchangeRate\": 1.0\n" +
                        "        },\n" +
                        "        \"FEntity\": " + produdctArrayStr + "\n" +
                        "    }\n" +
                        "}";
                JSONObject save = InvokeHelper.Save("SAL_RETURNSTOCK", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) save.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("销售退货单保存");
                    incident.setExcuteIncidentName("SAL_RETURNSTOCK,Save");
                    incident.setExcuteIncidentJSON(s);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return ResponseResult.success(save);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult viewMarketReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseViewBean baseViewBean = new BaseViewBean();
            baseViewBean.setNumber(number);
            baseViewBean.setId(id);
            String s = JSON.toJSONString(baseViewBean);
            try {
                JSONObject jsonObject = InvokeHelper.View("SAL_RETURNSTOCK", s);

                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult submitMarketReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Submit("SAL_RETURNSTOCK", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("销售退货单提交");
                    incident.setExcuteIncidentName("SAL_RETURNSTOCK,Submit");
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
    public ResponseResult auditMarketReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseAuditBean baseAuditBean = new BaseAuditBean();
            baseAuditBean.setNumbers(stringList);
            baseAuditBean.setIds(ids);
            String s = JSON.toJSONString(baseAuditBean);
            try {
                JSONObject jsonObject = InvokeHelper.Audit("SAL_RETURNSTOCK", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("销售退货单审核");
                    incident.setExcuteIncidentName("SAL_RETURNSTOCK,Audit");
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
    public ResponseResult unAuditMarketReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseAuditBean baseAuditBean = new BaseAuditBean();
            baseAuditBean.setNumbers(stringList);
            baseAuditBean.setIds(ids);
            String s = JSON.toJSONString(baseAuditBean);
            try {
                JSONObject jsonObject = InvokeHelper.UnAudit("SAL_RETURNSTOCK", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("销售退货单反审核");
                    incident.setExcuteIncidentName("SAL_RETURNSTOCK,UnAudit");
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
    public ResponseResult deleteMarketReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        if (login) {
            BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
            baseDeleteBean.setNumbers(stringList);
            baseDeleteBean.setIds(ids);
            String s = JSON.toJSONString(baseDeleteBean);
            try {
                JSONObject delete = InvokeHelper.Delete("SAL_RETURNSTOCK", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) delete.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("销售退货单删除");
                    incident.setExcuteIncidentName("SAL_RETURNSTOCK,Delete");
                    incident.setExcuteIncidentJSON(s);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return ResponseResult.success(delete);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult unCancelMarketReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.UnCancel("SAL_RETURNSTOCK", "UnCancel", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("销售退货单反取消");
                    incident.setExcuteIncidentName("SAL_RETURNSTOCK,UnCancel");
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
    public ResponseResult cancelMarketReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Cancel("SAL_RETURNSTOCK", "Cancel", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("销售退货单取消");
                    incident.setExcuteIncidentName("SAL_RETURNSTOCK,Cancel");
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
    public ResponseResult manageUnAuditMarketReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseAuditBean baseAuditBean = new BaseAuditBean();
            baseAuditBean.setNumbers(stringList);
            baseAuditBean.setIds(ids);
            String s = JSON.toJSONString(baseAuditBean);
            try {
                JSONObject jsonObject = InvokeHelper.ManageUnAudit("SAL_RETURNSTOCK", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("销售退货单后台反审核");
                    incident.setExcuteIncidentName("SAL_RETURNSTOCK,ManageUnAudit");
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
    public ResponseResult batchSaveMarketReturn() {
        return null;
    }

    //===========================================采购退料================================================
    @Override
    public ResponseResult savePurchaseReturn(String acctId,
                                             String dataCentreUserName,
                                             String dataCentrePassword,
                                             String fStockOrgId,
                                             String fDate,
                                             String fSupplierID,
                                             String jsonArrayProduct) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                List produdctArray = new ArrayList();
                JSONArray productArrayIn = JSON.parseArray(jsonArrayProduct);
                for (int i = 0; i < productArrayIn.size(); i++) {
                    JSONObject objectAll = new JSONObject(new LinkedHashMap());

                    objectAll.put("FRowType", "Standard");

                    JSONObject objectFMaterialId = new JSONObject();
                    objectFMaterialId.put("FNumber", productArrayIn.getJSONObject(i).getString("productK3Number"));
                    objectAll.put("FMaterialId", objectFMaterialId);

                    objectAll.put("FMaterialDesc", productArrayIn.getJSONObject(i).getString("productName"));

                    JSONObject objectFUnitID = new JSONObject();
                    objectFUnitID.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
                    objectAll.put("FUnitID", objectFUnitID);

                    objectAll.put("FRMREALQTY", productArrayIn.getJSONObject(i).getString("number"));

                    objectAll.put("FKEAPAMTQTY", productArrayIn.getJSONObject(i).getString("number"));

                    JSONObject objectFPRICEUNITID = new JSONObject();
                    objectFPRICEUNITID.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
                    objectAll.put("FPRICEUNITID", objectFPRICEUNITID);

                    JSONObject objectFStockId = new JSONObject();
                    objectFStockId.put("FNumber", productArrayIn.getJSONObject(i).getString("stockK3Nunber"));
                    objectAll.put("FStockId", objectFStockId);

                    JSONObject objectFStockStatusId = new JSONObject();
                    objectFStockStatusId.put("FNumber", "KCZT01_SYS");
                    objectAll.put("FStockStatusId", objectFStockStatusId);

                    objectAll.put("FIsReceiveUpdateStock", false);

                    objectAll.put("FGiveAway", false);

                    objectAll.put("FPriceBaseQty", productArrayIn.getJSONObject(i).getString("number"));

                    JSONObject objectFCarryUnitId = new JSONObject();
                    objectFCarryUnitId.put("FNumber", productArrayIn.getJSONObject(i).getString("unitK3Number"));
                    objectAll.put("FCarryUnitId", objectFCarryUnitId);

                    objectAll.put("FCarryQty", productArrayIn.getJSONObject(i).getString("number"));

                    objectAll.put("FCarryBaseQty", productArrayIn.getJSONObject(i).getString("number"));

                    objectAll.put("FBILLINGCLOSE", false);

                    objectAll.put("FIsStock", false);


                    produdctArray.add(objectAll);
                }
                String produdctArrayStr = produdctArray.toString();
                String s = "{\n" +
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
                        "        \"FID\": 0,\n" +
                        "        \"FBillTypeID\": {\n" +
                        "            \"FNUMBER\": \"TLD01_SYS\"\n" +
                        "        },\n" +
                        "        \"FDate\": \""+fDate+"\",\n" +
                        "        \"FMRTYPE\": \"B\",\n" +
                        "        \"FMRMODE\": \"B\",\n" +
                        "        \"FStockOrgId\": {\n" +
                        "            \"FNumber\": \""+fStockOrgId+"\"\n" +
                        "        },\n" +
                        "        \"FRequireOrgId\": {\n" +
                        "            \"FNumber\": \""+fStockOrgId+"\"\n" +
                        "        },\n" +
                        "        \"FPurchaseOrgId\": {\n" +
                        "            \"FNumber\": \""+fStockOrgId+"\"\n" +
                        "        },\n" +
                        "        \"FSupplierID\": {\n" +
                        "            \"FNumber\": \""+fSupplierID+"\"\n" +
                        "        },\n" +
                        "        \"FACCEPTORID\": {\n" +
                        "            \"FNumber\": \""+fSupplierID+"\"\n" +
                        "        },\n" +
                        "        \"FSettleId\": {\n" +
                        "            \"FNumber\": \""+fSupplierID+"\"\n" +
                        "        },\n" +
                        "        \"FCHARGEID\": {\n" +
                        "            \"FNumber\": \""+fSupplierID+"\"\n" +
                        "        },\n" +
                        "        \"FOwnerTypeIdHead\": \"BD_OwnerOrg\",\n" +
                        "        \"FOwnerIdHead\": {\n" +
                        "            \"FNumber\": \""+fStockOrgId+"\"\n" +
                        "        },\n" +
                        "        \"FPURMRBFIN\": {\n" +
                        "            \"FSettleOrgId\": {\n" +
                        "                \"FNumber\": \""+fStockOrgId+"\"\n" +
                        "            },\n" +
                        "            \"FSettleCurrId\": {\n" +
                        "                \"FNumber\": \"PRE001\"\n" +
                        "            },\n" +
                        "            \"FIsIncludedTax\": true,\n" +
                        "            \"FPRICETIMEPOINT\": \"1\",\n" +
                        "            \"FLOCALCURRID\": {\n" +
                        "                \"FNumber\": \"PRE001\"\n" +
                        "            },\n" +
                        "            \"FEXCHANGETYPEID\": {\n" +
                        "                \"FNumber\": \"HLTX01_SYS\"\n" +
                        "            },\n" +
                        "            \"FEXCHANGERATE\": 1.0,\n" +
                        "            \"FISPRICEEXCLUDETAX\": true\n" +
                        "        },\n" +
                        "        \"FPURMRBENTRY\": "+produdctArrayStr+"\n" +
                        "    }\n" +
                        "}";
                JSONObject jsonObject = InvokeHelper.Save("PUR_MRB", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("采购退料单保存");
                    incident.setExcuteIncidentName("PUR_MRB,Save");
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
    public ResponseResult viewPurchaseReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseViewBean baseViewBean = new BaseViewBean();
            baseViewBean.setNumber(number);
            baseViewBean.setId(id);
            String s = JSON.toJSONString(baseViewBean);
            try {
                JSONObject jsonObject = InvokeHelper.View("PUR_MRB", s);

                return ResponseResult.success(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult submitPurchaseReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseSubmitBean baseSubmitBean = new BaseSubmitBean();
            baseSubmitBean.setNumbers(stringList);
            baseSubmitBean.setIds(ids);
            String s = JSON.toJSONString(baseSubmitBean);
            try {
                JSONObject jsonObject = InvokeHelper.Submit("PUR_MRB", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("采购退料单提交");
                    incident.setExcuteIncidentName("PUR_MRB,Submit");
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
    public ResponseResult auditPurchaseReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseAuditBean baseAuditBean = new BaseAuditBean();
            baseAuditBean.setNumbers(stringList);
            baseAuditBean.setIds(ids);
            String s = JSON.toJSONString(baseAuditBean);
            try {
                JSONObject jsonObject = InvokeHelper.Audit("PUR_MRB", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("采购退料单审核");
                    incident.setExcuteIncidentName("PUR_MRB,Audit");
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
    public ResponseResult unAuditPurchaseReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseAuditBean baseAuditBean = new BaseAuditBean();
            baseAuditBean.setNumbers(stringList);
            baseAuditBean.setIds(ids);
            String s = JSON.toJSONString(baseAuditBean);
            try {
                JSONObject jsonObject = InvokeHelper.UnAudit("PUR_MRB", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("采购退料单反审核");
                    incident.setExcuteIncidentName("PUR_MRB,UnAudit");
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
    public ResponseResult deletePurchaseReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        if (login) {
            BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
            baseDeleteBean.setNumbers(stringList);
            baseDeleteBean.setIds(ids);
            String s = JSON.toJSONString(baseDeleteBean);
            try {
                JSONObject delete = InvokeHelper.Delete("PUR_MRB", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) delete.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("采购退料单删除");
                    incident.setExcuteIncidentName("PUR_MRB,Delete");
                    incident.setExcuteIncidentJSON(s);
                    incident.setExcuteIncidentProject("lnmj-k3cloud");
                    iIncidentDao.insertIncident(incident);
                }
                return ResponseResult.success(delete);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.success(e);
            }
        }
        return ResponseResult.success(K3cloudConfig.LOGIN_FAIL);
    }

    @Override
    public ResponseResult unCancelPurchaseReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.UnCancel("PUR_MRB", "UnCancel", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("采购退料单反取消");
                    incident.setExcuteIncidentName("PUR_MRB,UnCancel");
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
    public ResponseResult cancelPurchaseReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] numbersStrList = numbers.split(",");
        List<String> resultList = new ArrayList<>(Arrays.asList(numbersStrList));
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            try {
                BaseDeleteBean baseDeleteBean = new BaseDeleteBean();
                baseDeleteBean.setIds(ids);
                baseDeleteBean.setNumbers(resultList);
                String a = JSON.toJSONString(baseDeleteBean);
                JSONObject jsonObject = InvokeHelper.Cancel("PUR_MRB", "Cancel", a);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("采购退料单取消");
                    incident.setExcuteIncidentName("PUR_MRB,Cancel");
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
    public ResponseResult manageUnAuditPurchaseReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        String[] split = numbers.split(",");
        List<String> stringList = Arrays.asList(split);
        Boolean login = K3cloudConfig.login(acctId, dataCentreUserName, dataCentrePassword);
        if (login) {
            BaseAuditBean baseAuditBean = new BaseAuditBean();
            baseAuditBean.setNumbers(stringList);
            baseAuditBean.setIds(ids);
            String s = JSON.toJSONString(baseAuditBean);
            try {
                JSONObject jsonObject = InvokeHelper.ManageUnAudit("PUR_MRB", s);
                Boolean jsonObjectSaveIsSuccess = (Boolean) ((Map) ((Map) jsonObject.get("Result")).get("ResponseStatus")).get("IsSuccess");
                if (!jsonObjectSaveIsSuccess) {
                    //如果保存没成功  记录到数据库日志
                    Incident incident = new Incident();
                    incident.setIncidentName("采购退料单后台反审核");
                    incident.setExcuteIncidentName("PUR_MRB,ManageUnAudit");
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
    public ResponseResult startStock(String dataCentreName, String orgId, String startDate) {
        ik3CLOUDDao.startStock(dataCentreName, orgId, startDate);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult batchSavePurchaseReturn() {
        return null;
    }
}
