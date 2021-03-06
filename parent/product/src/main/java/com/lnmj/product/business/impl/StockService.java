package com.lnmj.product.business.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.*;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeStockEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.EnumUtil;
import com.lnmj.common.utils.ListPageUntil;
import com.lnmj.common.utils.NumberUtils;
import com.lnmj.product.business.IStockService;
import com.lnmj.product.entity.*;
import com.lnmj.product.entity.VO.K3VO.*;
import com.lnmj.product.entity.VO.ProductNumberStockVO;
import com.lnmj.product.entity.VO.ProductNumberVO;
import com.lnmj.product.entity.VO.ProductStorageProductVO;
import com.lnmj.product.entity.VO.ProductStorageVO;
import com.lnmj.product.repository.*;
import com.lnmj.product.serviceProxy.K3CLOUDApi;
import com.lnmj.product.serviceProxy.StoreApi;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Hash;
import org.codehaus.jackson.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: yilihua
 * @Date: 2019/9/23 17:40
 * @Description: 仓库管理
 */
@Transactional
@Service("stockService")
public class StockService implements IStockService {
    @Resource
    private IStockProductDao stockProductDao;
    @Resource
    private IInstorageDao instorageDao;
    @Resource
    private IOutstorageDao outstorageDao;
    @Resource
    private IStockStatusDao stockStatusDao;
    @Resource
    private IStockDao stockDao;
    @Resource
    private IUnitDao unitDao;
    @Resource
    private StoreApi storeApi;
    @Resource
    private K3CLOUDApi k3CLOUDApi;
    @Resource
    private IProductNumberDao productNumberDao;
    @Resource
    private IServiceProductDao iServiceProductDao;
    @Resource
    private IProductDao iProductDao;

    @Override
    public ResponseResult selectStockStatusList() {
        List<StockStatus> stockStatusList = stockStatusDao.selectStockStatusList();
        if (stockStatusList != null && stockStatusList.size() > 0) {
            return ResponseResult.success(stockStatusList);
        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKSTATUS_NOT_FIND.getCode(),
                ResponseCodeStockEnum.STOCKSTATUS_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectStockProductList(int pageNum, int pageSize, String stock, String productCode, String productName) {
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isBlank(stock)) {
            map.put("stock", stock);
        }
        if (!StringUtils.isBlank(productCode)) {
            map.put("productCode", productCode);
        }
        if (!StringUtils.isBlank(productName)) {
            map.put("productName", productName);
        }
        List<StockProduct> stockProductList = stockProductDao.selectStockProductByStockIDAndProductID(map);
        if (stockProductList != null && stockProductList.size() > 0) {
            PageInfo<StockProduct> pageInfo = new PageInfo<>(stockProductList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_FIND.getCode(),
                ResponseCodeStockEnum.STOCKPRODUCT_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectStockProductListNoPage(String stock, String productCode) {
        List<StockProduct> stockProductList = new ArrayList<>();
        if (StringUtils.isBlank(stock) && StringUtils.isBlank(productCode)) {
            stockProductList = stockProductDao.selectStockProductList();
        } else if (!StringUtils.isBlank(stock) && StringUtils.isBlank(productCode)) {
            stockProductList = stockProductDao.selectStockProductByStockID(stock);
        } else if (StringUtils.isBlank(stock) && !StringUtils.isBlank(productCode)) {
            stockProductList = stockProductDao.selectStockProductByProductID(productCode);
        } else if (!StringUtils.isBlank(stock) && !StringUtils.isBlank(productCode)) {
            Map<String, Object> map = new HashMap<>();
            map.put("stock", stock);
            map.put("productCode", productCode);
            stockProductList = stockProductDao.selectStockProductByStockIDAndProductID(map);
        }
        if (stockProductList != null && stockProductList.size() > 0) {
            return ResponseResult.success(stockProductList);
        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_FIND.getCode(),
                ResponseCodeStockEnum.STOCKPRODUCT_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectInstorageList(int pageNum, int pageSize, String inStorageType, String inStorageDate, String stockGroup, String inventoryGroup, String stockId, String provider) {
        PageHelper.startPage(pageNum, pageSize);
        List<InstoragePreAudit> instorageList = new ArrayList<>();
//        if(StringUtils.isBlank(inStorageType) && StringUtils.isBlank(inStorageDate)){
////            instorageList = instorageDao.selectInstorageList();
////        }
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isBlank(inStorageType)) {
            map.put("inStorageType", inStorageType);
        }
        if (!StringUtils.isBlank(stockGroup)) {
            map.put("stockGroup", stockGroup);
        }
        if (!StringUtils.isBlank(inStorageDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            try {
                date = sdf.parse(inStorageDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            map.put("inStorageDate", date);
        }
        map.put("inventoryGroup", inventoryGroup);
        map.put("stockId", stockId);
        if (provider != null && provider.equals("0")) {
            provider = null;
        }

        map.put("provider", provider);
        instorageList = instorageDao.selectInstorageByTypeAndDate(map);
        if (instorageList == null || instorageList.size() <= 0) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGE_NOT_FIND.getCode(),
                    ResponseCodeStockEnum.INSTORAGE_NOT_FIND.getDesc()));
        }
        List<Unit> unitList = unitDao.selectUnitList(new HashMap());
        for (InstoragePreAudit instorage : instorageList) {
            if (instorage.getOutStorageId() != null) {
                OutstoragePreAudit outstoragePreAudit = new OutstoragePreAudit();
                outstoragePreAudit.setOutStorageId(instorage.getOutStorageId());
                OutstoragePreAudit outstoragePreAuditResult = outstorageDao.selectOutstoragePreAuditById(outstoragePreAudit);
                instorage.setSuppIsAudit(outstoragePreAuditResult.getOutStorageStatus());
            }


            Long afterAuditInStorageId = instorage.getAfterAuditInStorageId();
            //查询对应的实际入库单
            Instorage instorage1 = instorageDao.selectInstorageByID(afterAuditInStorageId);
            List<InStorageProduct> inStorageProductListDeam;
            //查询出预入库商品
            List<InstoragePreAuditProduct> instoragePreAuditProductList = instorageDao.selectPreInstorageProductByPreAuditId(instorage.getInStorageId());
            List<InStorageProduct> inStorageProductList = JSON.parseArray(JSONArray.parseArray(JSON.toJSONString(instoragePreAuditProductList)).toJSONString(), InStorageProduct.class);
            if (instorage1 != null) {
                Long inStorageId = instorage1.getInStorageId();
                List<InStorageProduct> inStorageProductListAfter = instorageDao.selectInStorageProductByInStorageID(inStorageId);
                if (inStorageProductListAfter != null && inStorageProductListAfter.size() != 0) {
                    inStorageProductListDeam = inStorageProductListAfter;
                } else {
                    inStorageProductListDeam = inStorageProductList;
                }
            } else {
                inStorageProductListDeam = inStorageProductList;
            }


            for (InStorageProduct inStorageProduct : inStorageProductListDeam) {
                String unitId = inStorageProduct.getUnit();
                for (Unit unit1 : unitList) {
                    if (unit1.getUnitId().toString().equals(unitId)) {
                        inStorageProduct.setUnitName(unit1.getUnitName());
                    }
                }
            }
            instorage.setInStorageProductList(inStorageProductListDeam);
        }

        if (instorageList.size() > 0) {
            PageInfo<InstoragePreAudit> pageInfo = new PageInfo<>(instorageList);
            return ResponseResult.success(pageInfo);
        }

        return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGE_NOT_FIND.getCode(),
                ResponseCodeStockEnum.INSTORAGE_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectOutstorageList(int pageNum, int pageSize, String outStorageType, String outStorageDate, String stockGroup, String inventoryGroup, String client) {
        PageHelper.startPage(pageNum, pageSize);
        List<OutstoragePreAudit> outstorageList = new ArrayList<>();
//        if(StringUtils.isBlank(outStorageType) && StringUtils.isBlank(outStorageDate)){
//            outstorageList = outstorageDao.selectOutstorageList();
//        }
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isBlank(outStorageType)) {
            map.put("outStorageType", outStorageType);
        }
        if (!StringUtils.isBlank(stockGroup)) {
            map.put("stockGroup", stockGroup);
        }
        if (!StringUtils.isBlank(outStorageDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            try {
                date = sdf.parse(outStorageDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            map.put("outStorageDate", date);
        }
        map.put("inventoryGroup", inventoryGroup);
        if (client != null && client.equals("0")) {
            client = null;
        }
        map.put("client", client);
        outstorageList = outstorageDao.selectOutstorageByTypeAndDate(map);


        if (outstorageList == null || outstorageList.size() <= 0) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGE_NOT_FIND.getCode(),
                    ResponseCodeStockEnum.OUTSTORAGE_NOT_FIND.getDesc()));
        }
        List<Unit> unitList = unitDao.selectUnitList(new HashMap());
        for (OutstoragePreAudit outstorage : outstorageList) {
            if (outstorage.getInStorageId() != null) {
                //查看他对应的入库是否确认收货
                InstoragePreAudit instoragePreAudit = new InstoragePreAudit();
                instoragePreAudit.setInStorageId(outstorage.getInStorageId());
                InstoragePreAudit instoragePreAuditResult = instorageDao.selectInstoragePreAuditById(instoragePreAudit);
                outstorage.setInstorageIsComfir(instoragePreAuditResult.getConfirmStatus());
            }


            Long afterAuditOutStorageId = outstorage.getAfterAuditOutStorageId();
            //查询对应的实际出库单
            Outstorage outstorage1 = outstorageDao.selectOutstorageByID(afterAuditOutStorageId);


            List<OutStorageProduct> outStorageProductListDeam;
            //查询对应的商品
            //查询预出库商品
            List<OutstoragePreAuditProduct> outstoragePreAuditProductList = outstorageDao.selectPreOutstorageProductByPreAuditId(outstorage.getOutStorageId());
            List<OutStorageProduct> outStorageProductList = JSON.parseArray(JSONArray.parseArray(JSON.toJSONString(outstoragePreAuditProductList)).toJSONString(), OutStorageProduct.class);
            if (outstorage1 != null) {
                Long outStorageId = outstorage1.getOutStorageId();
                List<OutStorageProduct> outStorageProductListAfter = outstorageDao.selectOutStorageProductByOutStorageID(outStorageId);
                if (outStorageProductListAfter != null && outStorageProductListAfter.size() != 0) {
                    outStorageProductListDeam = outStorageProductListAfter;
                } else {
                    outStorageProductListDeam = outStorageProductList;
                }
            } else {
                outStorageProductListDeam = outStorageProductList;
            }

            for (OutStorageProduct outStorageProduct : outStorageProductListDeam) {
                outStorageProduct.setOutStoregeDate(outstorage.getOutStorageDate());
                String unitId = outStorageProduct.getUnit();
                for (Unit unit1 : unitList) {
                    if (unit1.getUnitId().toString().equals(unitId)) {
                        outStorageProduct.setUnitName(unit1.getUnitName());
                    }
                }
            }
            outstorage.setOutStorageProductList(outStorageProductListDeam);
        }
        PageInfo<OutstoragePreAudit> pageInfo = new PageInfo<>(outstorageList);
        return ResponseResult.success(pageInfo);
    }


    @Override
    public ResponseResult instoragePreAudit(InstoragePreAudit instorage) {
        String instorageNumber = NumberUtils.getRandomOrderNo();
        String batch = NumberUtils.getRandomOrderNo();
        instorage.setInStorageNumber("YSH" + instorageNumber);
        instorage.setBatchNumber(batch);
        //单据编号，批号从K3获取
        //入库类型（其他入库、采购入库）
        instorage.setInStorageStatus("0");//单据状态,待审核
        String inStorageType = instorage.getInStorageType();
        if (StringUtils.isBlank(inStorageType)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGETYPE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.INSTORAGETYPE_NOT_NULL.getDesc()));
        }
        //todo  验证商品和单位,仓库是否可用,插入K3，获取单号，批号，验证供应商
        OutstoragePreAudit outstoragePreAudit = new OutstoragePreAudit();
        List<InStorageProduct> inStorageProductList = JSON.parseArray(instorage.getInStorageProductJson(), InStorageProduct.class);
        if (inStorageType.equals(InStorageTypeEnum.PURCHASE_INSTORAGE.getDesc())) {
            //采购入库: 单据类型、业务类型、日期、单据状态、收料组织、需求组织
            //采购组织、供应商、结算组织、结算币别、货主类型、货主
 /*           String supplierType = ((Map) storeApi.selectSupplierByCode(instorage.getProvider()).getResult()).get("supplierType").toString();
            if (supplierType.equals("1")) {
                //如果供应商的类型为组织 才 查看该组织是否有默认销售员
                HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
                String dataCentre = userNameAndPassword.get("dataCentre");
                String userName = userNameAndPassword.get("userName");
                String password = userNameAndPassword.get("password");


                //根据仓库 查看出库组织的类型
                ResponseResult responseResultEmployees = k3CLOUDApi.employeesView(dataCentre, userName, password, "XSY001", null);

                if (responseResultEmployees.isSuccess() && ((Map) ((Map) ((Map) responseResultEmployees.getResult()).get("Result")).get("ResponseStatus")) != null &&
                        ((Boolean) (((Map) ((Map) ((Map) responseResultEmployees.getResult()).get("Result")).get("ResponseStatus"))).get("IsSuccess")) == false) {
                    //如果不存在这样的员工
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getCode(),
                            ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getDesc()));
                } else if (((Map) ((Map) ((Map) responseResultEmployees.getResult()).get("Result")).get("ResponseStatus")) == null) {
                    String orgNumber = ((Map) ((Map) ((Map) ((Map) responseResultEmployees.getResult()).get("Result")).get("Result")).get("UseOrgId")).get("Number").toString();
                    //根据供应商编号查找供应商
                    String relationSubCompanyId = ((Map) storeApi.selectSupplierByCode(instorage.getProvider()).getResult()).get("relationSubCompanyId").toString();
                    String orgK3Number = ((Map) storeApi.selectSubsidiaryByID(Long.parseLong(relationSubCompanyId)).getResult()).get("k3Number").toString();
                    if (!orgNumber.equals(orgK3Number)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getCode(),
                                ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getDesc()));
                    }
                }
            }*/

            String invoicesType = instorage.getInvoicesType();
            if (StringUtils.isBlank(invoicesType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getDesc()));
            }
            Map<Integer, String> InStorageInvoicesTypeEnum = EnumUtil.getEnumToMap(InStorageInvoicesTypeEnum.class);
            boolean b = InStorageInvoicesTypeEnum.containsValue(invoicesType);
            if (!b) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_ERROR.getDesc()));
            }
            String businessType = instorage.getBusinessType();
            if (StringUtils.isBlank(businessType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.BUSINESSTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.BUSINESSTYPE_NOT_NULL.getDesc()));
            }
            Map<Integer, String> InStorageBusinessTypeEnum = EnumUtil.getEnumToMap(InStorageBusinessTypeEnum.class);
            boolean b1 = InStorageBusinessTypeEnum.containsValue(businessType);
            if (!b1) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.BUSINESSTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.BUSINESSTYPE_ERROR.getDesc()));
            }
            Date inStorageDate = instorage.getInStorageDate();
            if (inStorageDate == null) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getDesc()));
            }
            String inStorageStatus = instorage.getInStorageStatus();
            if (StringUtils.isBlank(inStorageStatus)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGESTATUS_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INSTORAGESTATUS_NOT_NULL.getDesc()));
            }
            String receiveGroup = instorage.getReceiveGroup();
            if (StringUtils.isBlank(receiveGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.RECEIVEGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.RECEIVEGROUP_NOT_NULL.getDesc()));
            }
            String needGroup = instorage.getNeedGroup();
            if (StringUtils.isBlank(needGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.NEEDGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.NEEDGROUP_NOT_NULL.getDesc()));
            }
            String purchaseGroup = instorage.getPurchaseGroup();
            if (StringUtils.isBlank(purchaseGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.PURCHASEGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.PURCHASEGROUP_NOT_NULL.getDesc()));
            }
            String provider = instorage.getProvider();
            if (StringUtils.isBlank(provider)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.PROVIDER_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.PROVIDER_NOT_NULL.getDesc()));
            }
            String settlementGroup = instorage.getSettlementGroup();
            if (StringUtils.isBlank(settlementGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SETTLEMENTGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SETTLEMENTGROUP_NOT_NULL.getDesc()));
            }
            String settlementCurrency = instorage.getSettlementCurrency();
            if (StringUtils.isBlank(settlementCurrency)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SETTLEMENTCURRENCY_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SETTLEMENTCURRENCY_NOT_NULL.getDesc()));
            }
            String shipperType = instorage.getShipperType();
            if (StringUtils.isBlank(shipperType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getDesc()));
            }
            String shipper = instorage.getShipper();
            if (StringUtils.isBlank(shipper)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPER_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPER_NOT_NULL.getDesc()));
            }

            if (StringUtils.isBlank(instorage.getInventoryGroup())) {
                instorage.setInventoryGroup(instorage.getInStorageProductList().get(0).getStock());
            }


            if (inStorageProductList != null && inStorageProductList.size() > 0) {
                for (InStorageProduct inStorageProduct : inStorageProductList) {
                    String productCode = inStorageProduct.getProductCode();
                    if (StringUtils.isBlank(productCode)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getDesc()));
                    }
                    String unit = inStorageProduct.getUnit();
                    if (StringUtils.isBlank(unit)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.UNIT_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.UNIT_NOT_NULL.getDesc()));
                    }
                    Integer receivedNumber = inStorageProduct.getReceivedNumber();
                    if (receivedNumber == null) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.RECEIVEDNUMBER_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.RECEIVEDNUMBER_NOT_NULL.getDesc()));
                    }
                    String stock = inStorageProduct.getStock();
                    if (StringUtils.isBlank(stock)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.STOCK_NOT_NULL.getDesc()));
                    }
                    String stockStatus = inStorageProduct.getStockStatus();
                    if (StringUtils.isBlank(stockStatus)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKSTATUS_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.STOCKSTATUS_NOT_NULL.getDesc()));
                    }
                    inStorageProduct.setBatchNumber(batch);
                }
            }


            outstoragePreAudit.setOutStorageType("销售出库");
            outstoragePreAudit.setInvoicesType("标准销售出库");
        }
        if (inStorageType.equals(InStorageTypeEnum.OTHER_INSTORAGE.getDesc())) {
            //其他入库：单据类型、库存组织、库存方向、日期、货主类型、货主、单据状态
            String invoicesType = instorage.getInvoicesType();
            if (StringUtils.isBlank(invoicesType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getDesc()));
            }
            String inventoryGroup = instorage.getInventoryGroup();
            if (StringUtils.isBlank(inventoryGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getDesc()));
            }
            String inventoryWay = instorage.getInventoryWay();
            if (StringUtils.isBlank(inventoryWay)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getDesc()));
            }
            Date inStorageDate = instorage.getInStorageDate();
            if (inStorageDate == null) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getDesc()));
            }
            String shipperType = instorage.getShipperType();
            if (StringUtils.isBlank(shipperType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getDesc()));
            }
            String shipper = instorage.getShipper();
            if (StringUtils.isBlank(shipper)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPER_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPER_NOT_NULL.getDesc()));
            }
            Map<Integer, String> InStorageInvoicesTypeEnum = EnumUtil.getEnumToMap(InStorageInvoicesTypeEnum.class);
            boolean b = InStorageInvoicesTypeEnum.containsValue(invoicesType);
            if (!b) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_ERROR.getDesc()));
            }
            Map<Integer, String> InventoryWayEnum = EnumUtil.getEnumToMap(InventoryWayEnum.class);
            boolean b1 = InventoryWayEnum.containsValue(inventoryWay);
            if (!b1) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYWAY_ERROR.getCode(),
                        ResponseCodeStockEnum.INVENTORYWAY_ERROR.getDesc()));
            }
            if (StringUtils.isBlank(instorage.getInventoryGroup())) {
                instorage.setInventoryGroup(instorage.getInStorageProductList().get(0).getStock());
            }

            if (inStorageProductList != null && inStorageProductList.size() > 0) {
                for (InStorageProduct inStorageProduct : inStorageProductList) {
                    String productCode = inStorageProduct.getProductCode();
                    if (StringUtils.isBlank(productCode)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getDesc()));
                    }
                    String unit = inStorageProduct.getUnit();
                    if (StringUtils.isBlank(unit)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.UNIT_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.UNIT_NOT_NULL.getDesc()));
                    }
                    Integer receivedNumber = inStorageProduct.getReceivedNumber();
                    if (receivedNumber == null) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.RECEIVEDNUMBER_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.RECEIVEDNUMBER_NOT_NULL.getDesc()));
                    }
                    String stock = inStorageProduct.getStock();
                    if (StringUtils.isBlank(stock)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.STOCK_NOT_NULL.getDesc()));
                    }
                    inStorageProduct.setBatchNumber(batch);
                }
            }


            outstoragePreAudit.setOutStorageType("其他出库");
            outstoragePreAudit.setInvoicesType("标准其他出库");
            outstoragePreAudit.setBusinessType("物料领用");

        }

        //同步生成对应客户的出库单
        //根据入库供应商查找供应商的信息
        Map map = (Map) (storeApi.selectSupplierByCode(instorage.getProvider()).getResult());
        String relationSubCompanyType = null;
        String relationSubCompanyId = null;
        String chukuOrgName = "";
        String chukuOrgK3Number = "";
        String chukuOrgId = "";
        boolean flag = true;
        if (map.get("relationSubCompanyType") != null && map.get("relationSubCompanyId") != null) {
            relationSubCompanyType = map.get("relationSubCompanyType").toString();
            relationSubCompanyId = map.get("relationSubCompanyId").toString();
            if (relationSubCompanyType.equals("2")) {
                //如果供应商是子公司
                Map mapSubCompany = (Map) (storeApi.selectSubsidiaryByID(Long.parseLong(relationSubCompanyId)).getResult());
                chukuOrgName = mapSubCompany.get("subsidiaryName").toString();
                chukuOrgK3Number = mapSubCompany.get("k3Number").toString();
                chukuOrgId = mapSubCompany.get("subsidiaryId").toString();
            } else if (relationSubCompanyType.equals("3")) {
                //如果供应商是分公司
                Map mapStore = (Map) (storeApi.selectStoreById(Long.parseLong(relationSubCompanyId)).getResult());
                chukuOrgName = mapStore.get("name").toString();
                chukuOrgK3Number = mapStore.get("k3Number").toString();
                chukuOrgId = mapStore.get("storeId").toString();
            }
        } else {
            //如果供应商不对应任何组织 那么就是从第三方供应商 供货  直接入库
            flag = false;
        }
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(inStorageProductList));


        if (flag) {
            //查看供应商的仓库编号
            Stock stock = new Stock();
            stock.setCompanyId(Long.parseLong(relationSubCompanyId));
            stock.setCompanyType(Long.parseLong(relationSubCompanyType));
            String stockCode = stockDao.selectStockByID(stock).get(0).getStockCode();
            Long stockId = stockDao.selectStockByID(stock).get(0).getStockId();
            //当前入库组织对应的客户编号
            Map mapCust = (Map) (storeApi.selectCustomerByConditon(instorage.getCompanyType(), instorage.getCompanyId()).getResult());
            String custK3Number = mapCust.get("k3Number").toString();
            String custK3Name = mapCust.get("name").toString();
            outstoragePreAudit.setOutStorageNumber(instorage.getInStorageNumber());
            outstoragePreAudit.setStockId(stockId.toString());
            outstoragePreAudit.setBatchNumber(instorage.getBatchNumber());
            outstoragePreAudit.setOutStorageDate(new Date());
            outstoragePreAudit.setInventoryGroup(stockCode);
            outstoragePreAudit.setSettlementCurrency("人民币");
            outstoragePreAudit.setMarketGroup(chukuOrgName);
            outstoragePreAudit.setClient(custK3Number);
            outstoragePreAudit.setClientName(custK3Name);
            outstoragePreAudit.setShipmentGroup(chukuOrgName);
            outstoragePreAudit.setStockGroup(stockCode);
            //其他出库的时候
            outstoragePreAudit.setReceiveGroup(instorage.getOrgK3Number());
            outstoragePreAudit.setInventoryWay("GENERAL");
            outstoragePreAudit.setShipperType("业务组织");
            outstoragePreAudit.setShipper(chukuOrgK3Number);


            JSONArray jsonArrayNew = new JSONArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = new JSONObject();
                object.put("sendableNumber", jsonArray.getJSONObject(i).get("receivableNumber"));
                object.put("sendNumber", jsonArray.getJSONObject(i).get("receivedNumber"));
                object.put("unitPrice", jsonArray.getJSONObject(i).get("unitPrice"));
                object.put("unit", jsonArray.getJSONObject(i).get("unit"));
                object.put("productCode", jsonArray.getJSONObject(i).get("productCode"));
                object.put("totalPrice", jsonArray.getJSONObject(i).get("totalPrice"));
                object.put("specification", jsonArray.getJSONObject(i).get("specification"));
                object.put("stockStatus", jsonArray.getJSONObject(i).get("stockStatus"));
                object.put("stock", stockCode);
                object.put("productType", jsonArray.getJSONObject(i).get("productType"));
                object.put("productName", jsonArray.getJSONObject(i).get("productName"));
                object.put("batchNumber", jsonArray.getJSONObject(i).get("batchNumber"));
                jsonArrayNew.add(object);
            }

            outstoragePreAudit.setOutStorageProductJson(jsonArrayNew.toJSONString());
            outstoragePreAudit.setOrgK3Number(chukuOrgK3Number);
            outstoragePreAudit.setInstorageOrgName(instorage.getInstorageOrgName());
            outstoragePreAudit.setStaffNumber(instorage.getStaffNumber());
            ResponseResult responseResult = this.outstoragePreAuditInside(outstoragePreAudit);
            instorage.setBatchNumber(batch);
            if (responseResult.isSuccess()) {
                instorage.setOutstorageOrgName(map.get("supplierName").toString());
                instorage.setOutStorageId(Long.parseLong(responseResult.getResult().toString()));
                instorage.setInStorageProductJson(null);
                instorageDao.insertInstoragePreAudit(instorage);
                OutstoragePreAudit outstoragePreAuditUpdate = new OutstoragePreAudit();
                outstoragePreAuditUpdate.setOutStorageId(Long.parseLong(responseResult.getResult().toString()));
                outstoragePreAuditUpdate.setInStorageId(instorage.getInStorageId());
                outstorageDao.updateOutstoragePreAuditById(outstoragePreAuditUpdate);

                //添加预入库商品
                Map mapAdd = new HashMap();
                List<InstoragePreAuditProduct> instoragePreAuditProductListAdd = new ArrayList<>();
                for (InStorageProduct inStorageProduct : inStorageProductList) {
                    InstoragePreAuditProduct instoragePreAuditProduct = new InstoragePreAuditProduct();
                    instoragePreAuditProduct.setInstoragePreAuditId(instorage.getInStorageId());
                    instoragePreAuditProduct.setUnitPrice(inStorageProduct.getUnitPrice());
                    instoragePreAuditProduct.setUnit(inStorageProduct.getUnit());
                    instoragePreAuditProduct.setProductCode(inStorageProduct.getProductCode());
                    instoragePreAuditProduct.setTotalPrice(inStorageProduct.getTotalPrice());
                    instoragePreAuditProduct.setStockStatus(inStorageProduct.getStockStatus());
                    instoragePreAuditProduct.setReceivedNumber(inStorageProduct.getReceivedNumber());
                    instoragePreAuditProduct.setStock(inStorageProduct.getStock());
                    instoragePreAuditProduct.setReceivableNumber(inStorageProduct.getReceivableNumber());
                    instoragePreAuditProduct.setProductType(inStorageProduct.getProductType());
                    instoragePreAuditProduct.setProductName(inStorageProduct.getProductName());
                    instoragePreAuditProduct.setBatchNumber(inStorageProduct.getBatchNumber());
                    instoragePreAuditProductListAdd.add(instoragePreAuditProduct);
                }
                mapAdd.put("list", instoragePreAuditProductListAdd);
                instorageDao.insertPreInstorageProduct(mapAdd);


                return ResponseResult.success("请购入库成功，请通知出库组织审核");
            } else {
                return responseResult;
            }
        } else {

            instorage.setOutstorageOrgName(map.get("supplierName").toString());
            instorage.setInStorageProductJson(null);
            int i = instorageDao.insertInstoragePreAudit(instorage);
            //添加预入库商品
            Map mapAdd = new HashMap();
            List<InstoragePreAuditProduct> instoragePreAuditProductListAdd = new ArrayList<>();
            for (InStorageProduct inStorageProduct : inStorageProductList) {
                InstoragePreAuditProduct instoragePreAuditProduct = new InstoragePreAuditProduct();
                instoragePreAuditProduct.setInstoragePreAuditId(instorage.getInStorageId());
                instoragePreAuditProduct.setUnitPrice(inStorageProduct.getUnitPrice());
                instoragePreAuditProduct.setUnit(inStorageProduct.getUnit());
                instoragePreAuditProduct.setProductCode(inStorageProduct.getProductCode());
                instoragePreAuditProduct.setTotalPrice(inStorageProduct.getTotalPrice());
                instoragePreAuditProduct.setStockStatus(inStorageProduct.getStockStatus());
                instoragePreAuditProduct.setReceivedNumber(inStorageProduct.getReceivedNumber());
                instoragePreAuditProduct.setStock(inStorageProduct.getStock());
                instoragePreAuditProduct.setReceivableNumber(inStorageProduct.getReceivableNumber());
                instoragePreAuditProduct.setProductType(inStorageProduct.getProductType());
                instoragePreAuditProduct.setProductName(inStorageProduct.getProductName());
                instoragePreAuditProduct.setBatchNumber(inStorageProduct.getBatchNumber());
                instoragePreAuditProductListAdd.add(instoragePreAuditProduct);
            }
            mapAdd.put("list", instoragePreAuditProductListAdd);
            instorageDao.insertPreInstorageProduct(mapAdd);

            InstoragePreAudit instoragePreAudit = new InstoragePreAudit();
            instoragePreAudit.setInStorageId(instorage.getInStorageId());
            instoragePreAudit.setConfirm(instorage.getReceiveGroup());
            instoragePreAudit.setConfirmStatus(1);
            instoragePreAudit.setConfirmTime(new Date());

            //查询出预入库商品
            List<InstoragePreAuditProduct> instoragePreAuditProductList = instorageDao.selectPreInstorageProductByPreAuditId(instorage.getInStorageId());


            InstoragePreAudit instoragePreAuditResult = instorageDao.selectInstoragePreAuditById(instoragePreAudit);
            instoragePreAuditResult.setInStorageProductJson(JSONArray.parseArray(JSON.toJSONString(instoragePreAuditProductList)).toJSONString());


            ResponseResult responseResult = this.instorage(instoragePreAuditResult);
            if (responseResult.isSuccess()) {
                InstoragePreAudit instoragePreAuditUpdate = new InstoragePreAudit();
                instoragePreAuditUpdate.setInStorageId(instorage.getInStorageId());
                instoragePreAuditUpdate.setAfterAuditInStorageId(Long.parseLong(responseResult.getResult().toString()));
                instorageDao.updateInstoragePreAuditById(instoragePreAuditUpdate);
            }
            return ResponseResult.success("入库成功");
        }
    }

    @Override
    public ResponseResult instoragePreAuditReturn(Long inStorageId, Integer number, Long inStorageProductID) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //查看当前入库的信息
        InstoragePreAudit instoragePreAuditSelect = new InstoragePreAudit();
        instoragePreAuditSelect.setInStorageId(inStorageId);
        InstoragePreAudit instoragePreAuditResult = instorageDao.selectInstoragePreAuditById(instoragePreAuditSelect);
        instoragePreAuditResult.setInventoryWay("RETURN");
        instoragePreAuditResult.setInStorageId(null);
        instoragePreAuditResult.setAfterAuditInStorageId(null);

        InStorageProduct inStorageProduct = instorageDao.selectInStorageProductByID(inStorageProductID);
        if (number > inStorageProduct.getReceivedNumber()) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                    ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc()));
        }

        //判断供应商是不是第三方的
        Map supplierMap = (Map) (storeApi.selectSupplierByCode(instoragePreAuditResult.getProvider()).getResult());
        if (supplierMap.get("supplierType").toString().equals("2")){
            //如果供应商为其他供应商 就直接退货  预审核
            List<InStorageProduct> list = new  ArrayList<>();
            inStorageProduct.setReceivableNumber(number);
            inStorageProduct.setReceivedNumber(number);
            list.add(inStorageProduct);
            JSONArray array= JSONArray.parseArray(JSON.toJSONString(list));
            instoragePreAuditResult.setInStorageProductJson(array.toJSONString());

           ResponseResult responseResult =  this.instorageReturn(instoragePreAuditResult);
           if (responseResult.isSuccess()){
               return ResponseResult.success("退货成功");
           }else{
               return responseResult;
           }

        }


        JSONArray jsonArrayProductIn = new JSONArray();
        JSONObject jsonObjectProductIn = new JSONObject();
        jsonObjectProductIn.put("productType", inStorageProduct.getProductType());
        jsonObjectProductIn.put("productCode", inStorageProduct.getProductCode());
        jsonObjectProductIn.put("productName", inStorageProduct.getProductName());
        jsonObjectProductIn.put("receivableNumber", number);
        jsonObjectProductIn.put("receivedNumber", number);
        jsonObjectProductIn.put("unit", inStorageProduct.getUnit());
        jsonObjectProductIn.put("unitPrice", inStorageProduct.getUnitPrice());
        jsonObjectProductIn.put("totalPrice", inStorageProduct.getUnitPrice().doubleValue() * number);
        jsonObjectProductIn.put("stock", inStorageProduct.getStock());
        jsonObjectProductIn.put("stockStatus", inStorageProduct.getStockStatus());
        jsonObjectProductIn.put("keeperType", inStorageProduct.getKeeperType());
        jsonObjectProductIn.put("keeper", inStorageProduct.getKeeper());
        jsonObjectProductIn.put("keeperCode", inStorageProduct.getKeeperCode());
        jsonObjectProductIn.put("isSend", inStorageProduct.getIsSend());
        jsonObjectProductIn.put("batchNumber", inStorageProduct.getBatchNumber());
        jsonArrayProductIn.add(jsonObjectProductIn);

        //同时插入其他出库
        OutstoragePreAudit outstoragePreAuditSelect = new OutstoragePreAudit();
        outstoragePreAuditSelect.setOutStorageId(instoragePreAuditResult.getOutStorageId());
        OutstoragePreAudit outstoragePreAuditResult = outstorageDao.selectOutstoragePreAuditById(outstoragePreAuditSelect);

        outstoragePreAuditResult.setInventoryWay("RETURN");
        outstoragePreAuditResult.setOutStorageId(null);
        outstoragePreAuditResult.setAfterAuditOutStorageId(null);
        outstoragePreAuditResult.setOutStorageStatus(0);
        JSONArray jsonArrayProductOut = new JSONArray();
        JSONObject jsonObjectProductOut = new JSONObject();
        jsonObjectProductOut.put("unitPrice", inStorageProduct.getUnitPrice());
        jsonObjectProductOut.put("unit", inStorageProduct.getUnit());
        jsonObjectProductOut.put("productCode", inStorageProduct.getProductCode());
        jsonObjectProductOut.put("totalPrice", inStorageProduct.getUnitPrice().doubleValue() * number);
        jsonObjectProductOut.put("stockStatus", inStorageProduct.getStockStatus());
        jsonObjectProductOut.put("sendNumber", number);
        jsonObjectProductOut.put("stock", outstoragePreAuditResult.getStockGroup());
        jsonObjectProductOut.put("sendableNumber", number);
        jsonObjectProductOut.put("productType", inStorageProduct.getProductType());
        jsonObjectProductOut.put("productName", inStorageProduct.getProductName());
        jsonObjectProductOut.put("batchNumber", inStorageProduct.getBatchNumber());

        jsonArrayProductOut.add(jsonObjectProductOut);
        outstoragePreAuditResult.setOutStorageProductJson(null);
        outstorageDao.insertOutstoragePreAudit(outstoragePreAuditResult);
        //添加预出库商品
        Map mapAddchu = new HashMap();
        List<OutstoragePreAuditProduct> outstoragePreAuditProductList = new ArrayList<>();
        List<OutStorageProduct> outStorageProductList = JSON.parseArray(jsonArrayProductOut.toJSONString(), OutStorageProduct.class);
        for (OutStorageProduct outStorageProduct : outStorageProductList) {
            OutstoragePreAuditProduct outstoragePreAuditProduct = new OutstoragePreAuditProduct();
            outstoragePreAuditProduct.setOutstoragePreAuditId(outstoragePreAuditResult.getOutStorageId());
            outstoragePreAuditProduct.setUnit(outStorageProduct.getUnit());
            outstoragePreAuditProduct.setUnitPrice(outStorageProduct.getUnitPrice());
            outstoragePreAuditProduct.setProductCode(outStorageProduct.getProductCode());
            outstoragePreAuditProduct.setTotalPrice(outStorageProduct.getTotalPrice());
            outstoragePreAuditProduct.setSpecification(outStorageProduct.getSpecification());
            outstoragePreAuditProduct.setStockStatus(outStorageProduct.getStockStatus());
            outstoragePreAuditProduct.setSendNumber(outStorageProduct.getSendNumber());
            outstoragePreAuditProduct.setStock(outStorageProduct.getStock());
            outstoragePreAuditProduct.setSendableNumber(outStorageProduct.getSendableNumber());
            outstoragePreAuditProduct.setProductName(outStorageProduct.getProductName());
            outstoragePreAuditProduct.setProductType(outStorageProduct.getProductType());
            outstoragePreAuditProduct.setBatchNumber(outStorageProduct.getBatchNumber());
            outstoragePreAuditProductList.add(outstoragePreAuditProduct);
        }

        mapAddchu.put("list", outstoragePreAuditProductList);
        outstorageDao.insertPreOutstorageProduct(mapAddchu);


        instoragePreAuditResult.setOutStorageId(outstoragePreAuditResult.getOutStorageId());
        instoragePreAuditResult.setConfirmStatus(null);
        instoragePreAuditResult.setInStorageProductJson(null);
        instorageDao.insertInstoragePreAudit(instoragePreAuditResult);

        //添加预入库商品
        Map mapAdd = new HashMap();
        List<InstoragePreAuditProduct> instoragePreAuditProductList = new ArrayList<>();
        List<InStorageProduct> inStorageProductList = JSON.parseArray(jsonArrayProductIn.toJSONString(), InStorageProduct.class);
        for (InStorageProduct inStorageProductItem : inStorageProductList) {
            InstoragePreAuditProduct instoragePreAuditProduct = new InstoragePreAuditProduct();
            instoragePreAuditProduct.setInstoragePreAuditId(instoragePreAuditResult.getInStorageId());
            instoragePreAuditProduct.setUnitPrice(inStorageProductItem.getUnitPrice());
            instoragePreAuditProduct.setUnit(inStorageProductItem.getUnit());
            instoragePreAuditProduct.setProductCode(inStorageProductItem.getProductCode());
            instoragePreAuditProduct.setTotalPrice(inStorageProductItem.getTotalPrice());
            instoragePreAuditProduct.setStockStatus(inStorageProductItem.getStockStatus());
            instoragePreAuditProduct.setReceivedNumber(inStorageProductItem.getReceivedNumber());
            instoragePreAuditProduct.setStock(inStorageProductItem.getStock());
            instoragePreAuditProduct.setReceivableNumber(inStorageProductItem.getReceivableNumber());
            instoragePreAuditProduct.setProductType(inStorageProductItem.getProductType());
            instoragePreAuditProduct.setProductName(inStorageProductItem.getProductName());
            instoragePreAuditProduct.setBatchNumber(inStorageProductItem.getBatchNumber());
            instoragePreAuditProductList.add(instoragePreAuditProduct);
        }
        mapAdd.put("list", instoragePreAuditProductList);
        instorageDao.insertPreInstorageProduct(mapAdd);


        OutstoragePreAudit outstoragePreAudit = new OutstoragePreAudit();
        outstoragePreAudit.setOutStorageId(outstoragePreAuditResult.getOutStorageId());
        outstoragePreAudit.setInStorageId(instoragePreAuditResult.getInStorageId());
        outstorageDao.updateOutstoragePreAuditById(outstoragePreAudit);
        return ResponseResult.success("退货成功，请通知出库组织审核");
    }

    @Override
    public ResponseResult instoragePreAuditInside(InstoragePreAudit instorage) {
        String inStorageNumber = NumberUtils.getRandomOrderNo();
        instorage.setInStorageNumber("YSH" + inStorageNumber);
        //单据编号，批号从K3获取
        //入库类型（其他入库、采购入库）
        instorage.setInStorageStatus("0");//单据状态,待审核
        String inStorageType = instorage.getInStorageType();
        if (StringUtils.isBlank(inStorageType)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGETYPE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.INSTORAGETYPE_NOT_NULL.getDesc()));
        }
        //todo  验证商品和单位,仓库是否可用,插入K3，获取单号，批号，验证供应商
        OutstoragePreAudit outstoragePreAudit = new OutstoragePreAudit();
        if (inStorageType.equals(InStorageTypeEnum.PURCHASE_INSTORAGE.getDesc())) {
            //采购入库: 单据类型、业务类型、日期、单据状态、收料组织、需求组织
            //采购组织、供应商、结算组织、结算币别、货主类型、货主
            String invoicesType = instorage.getInvoicesType();
            if (StringUtils.isBlank(invoicesType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getDesc()));
            }
            Map<Integer, String> InStorageInvoicesTypeEnum = EnumUtil.getEnumToMap(InStorageInvoicesTypeEnum.class);
            boolean b = InStorageInvoicesTypeEnum.containsValue(invoicesType);
            if (!b) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_ERROR.getDesc()));
            }
            String businessType = instorage.getBusinessType();
            if (StringUtils.isBlank(businessType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.BUSINESSTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.BUSINESSTYPE_NOT_NULL.getDesc()));
            }
            Map<Integer, String> InStorageBusinessTypeEnum = EnumUtil.getEnumToMap(InStorageBusinessTypeEnum.class);
            boolean b1 = InStorageBusinessTypeEnum.containsValue(businessType);
            if (!b1) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.BUSINESSTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.BUSINESSTYPE_ERROR.getDesc()));
            }
            Date inStorageDate = instorage.getInStorageDate();
            if (inStorageDate == null) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getDesc()));
            }
            String inStorageStatus = instorage.getInStorageStatus();
            if (StringUtils.isBlank(inStorageStatus)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGESTATUS_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INSTORAGESTATUS_NOT_NULL.getDesc()));
            }
            String receiveGroup = instorage.getReceiveGroup();
            if (StringUtils.isBlank(receiveGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.RECEIVEGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.RECEIVEGROUP_NOT_NULL.getDesc()));
            }
            String needGroup = instorage.getNeedGroup();
            if (StringUtils.isBlank(needGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.NEEDGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.NEEDGROUP_NOT_NULL.getDesc()));
            }
            String purchaseGroup = instorage.getPurchaseGroup();
            if (StringUtils.isBlank(purchaseGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.PURCHASEGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.PURCHASEGROUP_NOT_NULL.getDesc()));
            }
            String provider = instorage.getProvider();
            if (StringUtils.isBlank(provider)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.PROVIDER_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.PROVIDER_NOT_NULL.getDesc()));
            }
            String settlementGroup = instorage.getSettlementGroup();
            if (StringUtils.isBlank(settlementGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SETTLEMENTGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SETTLEMENTGROUP_NOT_NULL.getDesc()));
            }
            String settlementCurrency = instorage.getSettlementCurrency();
            if (StringUtils.isBlank(settlementCurrency)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SETTLEMENTCURRENCY_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SETTLEMENTCURRENCY_NOT_NULL.getDesc()));
            }
            String shipperType = instorage.getShipperType();
            if (StringUtils.isBlank(shipperType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getDesc()));
            }
            String shipper = instorage.getShipper();
            if (StringUtils.isBlank(shipper)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPER_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPER_NOT_NULL.getDesc()));
            }

            if (StringUtils.isBlank(instorage.getInventoryGroup())) {
                instorage.setInventoryGroup(instorage.getInStorageProductList().get(0).getStock());
            }

            //查询出预入库商品

            List<InStorageProduct> inStorageProductList = JSON.parseArray(instorage.getInStorageProductJson(), InStorageProduct.class);
            if (inStorageProductList != null && inStorageProductList.size() > 0) {
                for (InStorageProduct inStorageProduct : inStorageProductList) {
                    String productCode = inStorageProduct.getProductCode();
                    if (StringUtils.isBlank(productCode)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getDesc()));
                    }
                    String unit = inStorageProduct.getUnit();
                    if (StringUtils.isBlank(unit)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.UNIT_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.UNIT_NOT_NULL.getDesc()));
                    }
                    Integer receivedNumber = inStorageProduct.getReceivedNumber();
                    if (receivedNumber == null) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.RECEIVEDNUMBER_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.RECEIVEDNUMBER_NOT_NULL.getDesc()));
                    }
                    String stock = inStorageProduct.getStock();
                    if (StringUtils.isBlank(stock)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.STOCK_NOT_NULL.getDesc()));
                    }
                    String stockStatus = inStorageProduct.getStockStatus();
                    if (StringUtils.isBlank(stockStatus)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKSTATUS_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.STOCKSTATUS_NOT_NULL.getDesc()));
                    }
                }
            }
            instorage.setInStorageProductJson(null);
            int i = instorageDao.insertInstoragePreAudit(instorage);
            //添加预入库商品
            Map mapAdd = new HashMap();
            List<InstoragePreAuditProduct> instoragePreAuditProductListAdd = new ArrayList<>();
            for (InStorageProduct inStorageProduct : inStorageProductList) {
                InstoragePreAuditProduct instoragePreAuditProduct = new InstoragePreAuditProduct();
                instoragePreAuditProduct.setInstoragePreAuditId(instorage.getInStorageId());
                instoragePreAuditProduct.setUnitPrice(inStorageProduct.getUnitPrice());
                instoragePreAuditProduct.setUnit(inStorageProduct.getUnit());
                instoragePreAuditProduct.setProductCode(inStorageProduct.getProductCode());
                instoragePreAuditProduct.setTotalPrice(inStorageProduct.getTotalPrice());
                instoragePreAuditProduct.setStockStatus(inStorageProduct.getStockStatus());
                instoragePreAuditProduct.setReceivedNumber(inStorageProduct.getReceivedNumber());
                instoragePreAuditProduct.setStock(inStorageProduct.getStock());
                instoragePreAuditProduct.setReceivableNumber(inStorageProduct.getReceivableNumber());
                instoragePreAuditProduct.setProductType(inStorageProduct.getProductType());
                instoragePreAuditProduct.setProductName(inStorageProduct.getProductName());
                instoragePreAuditProduct.setBatchNumber(inStorageProduct.getBatchNumber());
                instoragePreAuditProductListAdd.add(instoragePreAuditProduct);
            }
            mapAdd.put("list", instoragePreAuditProductListAdd);
            instorageDao.insertPreInstorageProduct(mapAdd);


            outstoragePreAudit.setOutStorageType("销售出库");
            outstoragePreAudit.setInvoicesType("标准销售出库");
        }
        if (inStorageType.equals(InStorageTypeEnum.OTHER_INSTORAGE.getDesc())) {
            //其他入库：单据类型、库存组织、库存方向、日期、货主类型、货主、单据状态
            String invoicesType = instorage.getInvoicesType();
            if (StringUtils.isBlank(invoicesType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getDesc()));
            }
            String inventoryGroup = instorage.getInventoryGroup();
            if (StringUtils.isBlank(inventoryGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getDesc()));
            }
            String inventoryWay = instorage.getInventoryWay();
            if (StringUtils.isBlank(inventoryWay)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getDesc()));
            }
            Date inStorageDate = instorage.getInStorageDate();
            if (inStorageDate == null) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getDesc()));
            }
            String shipperType = instorage.getShipperType();
            if (StringUtils.isBlank(shipperType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getDesc()));
            }
            String shipper = instorage.getShipper();
            if (StringUtils.isBlank(shipper)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPER_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPER_NOT_NULL.getDesc()));
            }
            Map<Integer, String> InStorageInvoicesTypeEnum = EnumUtil.getEnumToMap(InStorageInvoicesTypeEnum.class);
            boolean b = InStorageInvoicesTypeEnum.containsValue(invoicesType);
            if (!b) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_ERROR.getDesc()));
            }
            Map<Integer, String> InventoryWayEnum = EnumUtil.getEnumToMap(InventoryWayEnum.class);
            boolean b1 = InventoryWayEnum.containsValue(inventoryWay);
            if (!b1) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYWAY_ERROR.getCode(),
                        ResponseCodeStockEnum.INVENTORYWAY_ERROR.getDesc()));
            }
            if (StringUtils.isBlank(instorage.getInventoryGroup())) {
                instorage.setInventoryGroup(instorage.getInStorageProductList().get(0).getStock());
            }
            //查询出预入库商品
            List<InStorageProduct> inStorageProductList = JSON.parseArray(instorage.getInStorageProductJson(), InStorageProduct.class);
            if (inStorageProductList != null && inStorageProductList.size() > 0) {
                for (InStorageProduct inStorageProduct : inStorageProductList) {
                    String productCode = inStorageProduct.getProductCode();
                    if (StringUtils.isBlank(productCode)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getDesc()));
                    }
                    String unit = inStorageProduct.getUnit();
                    if (StringUtils.isBlank(unit)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.UNIT_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.UNIT_NOT_NULL.getDesc()));
                    }
                    Integer receivedNumber = inStorageProduct.getReceivedNumber();
                    if (receivedNumber == null) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.RECEIVEDNUMBER_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.RECEIVEDNUMBER_NOT_NULL.getDesc()));
                    }
                    String stock = inStorageProduct.getStock();
                    if (StringUtils.isBlank(stock)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.STOCK_NOT_NULL.getDesc()));
                    }
                }
            }
            instorage.setInStorageProductJson(null);
            int i = instorageDao.insertInstoragePreAudit(instorage);
            //添加预入库商品
            Map mapAdd = new HashMap();
            List<InstoragePreAuditProduct> instoragePreAuditProductListAdd = new ArrayList<>();
            for (InStorageProduct inStorageProduct : inStorageProductList) {
                InstoragePreAuditProduct instoragePreAuditProduct = new InstoragePreAuditProduct();
                instoragePreAuditProduct.setInstoragePreAuditId(instorage.getInStorageId());
                instoragePreAuditProduct.setUnitPrice(inStorageProduct.getUnitPrice());
                instoragePreAuditProduct.setUnit(inStorageProduct.getUnit());
                instoragePreAuditProduct.setProductCode(inStorageProduct.getProductCode());
                instoragePreAuditProduct.setTotalPrice(inStorageProduct.getTotalPrice());
                instoragePreAuditProduct.setStockStatus(inStorageProduct.getStockStatus());
                instoragePreAuditProduct.setReceivedNumber(inStorageProduct.getReceivedNumber());
                instoragePreAuditProduct.setStock(inStorageProduct.getStock());
                instoragePreAuditProduct.setReceivableNumber(inStorageProduct.getReceivableNumber());
                instoragePreAuditProduct.setProductType(inStorageProduct.getProductType());
                instoragePreAuditProduct.setProductName(inStorageProduct.getProductName());
                instoragePreAuditProduct.setBatchNumber(inStorageProduct.getBatchNumber());
                instoragePreAuditProductListAdd.add(instoragePreAuditProduct);
            }
            mapAdd.put("list", instoragePreAuditProductListAdd);
            instorageDao.insertPreInstorageProduct(mapAdd);
            outstoragePreAudit.setOutStorageType("其他出库");
            outstoragePreAudit.setInvoicesType("标准其他出库");
        }

        return ResponseResult.success(instorage.getInStorageId());
    }

    @Override
    public ResponseResult cancelInstoragePreAudit(InstoragePreAudit instorage) {
        instorage.setInvalidStatus(0);
        instorage.setInvalidTime(new Date());
        int i = instorageDao.cancelInstoragePreAudit(instorage);

        return ResponseResult.success();
    }

    @Override
    public ResponseResult instorage(InstoragePreAudit instorage) {
        String inStorageNumber = NumberUtils.getRandomOrderNo();
        instorage.setInStorageNumber(inStorageNumber);
        //单据编号，批号从K3获取
        //入库类型（其他入库、采购入库）
        instorage.setInStorageStatus("1");//单据状态,待审核
        String inStorageType = instorage.getInStorageType();
        if (StringUtils.isBlank(inStorageType)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGETYPE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.INSTORAGETYPE_NOT_NULL.getDesc()));
        }
        //todo  验证商品和单位,仓库是否可用,插入K3，获取单号，批号，验证供应商
        if (inStorageType.equals(InStorageTypeEnum.PURCHASE_INSTORAGE.getDesc())) {
            //采购入库: 单据类型、业务类型、日期、单据状态、收料组织、需求组织
            //采购组织、供应商、结算组织、结算币别、货主类型、货主
            String invoicesType = instorage.getInvoicesType();
            if (StringUtils.isBlank(invoicesType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getDesc()));
            }
            Map<Integer, String> InStorageInvoicesTypeEnum = EnumUtil.getEnumToMap(InStorageInvoicesTypeEnum.class);
            boolean b = InStorageInvoicesTypeEnum.containsValue(invoicesType);
            if (!b) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_ERROR.getDesc()));
            }
            String businessType = instorage.getBusinessType();
            if (StringUtils.isBlank(businessType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.BUSINESSTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.BUSINESSTYPE_NOT_NULL.getDesc()));
            }
            Map<Integer, String> InStorageBusinessTypeEnum = EnumUtil.getEnumToMap(InStorageBusinessTypeEnum.class);
            boolean b1 = InStorageBusinessTypeEnum.containsValue(businessType);
            if (!b1) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.BUSINESSTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.BUSINESSTYPE_ERROR.getDesc()));
            }
            Date inStorageDate = instorage.getInStorageDate();
            if (inStorageDate == null) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getDesc()));
            }
            String inStorageStatus = instorage.getInStorageStatus();
            if (StringUtils.isBlank(inStorageStatus)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGESTATUS_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INSTORAGESTATUS_NOT_NULL.getDesc()));
            }
            String receiveGroup = instorage.getReceiveGroup();
            if (StringUtils.isBlank(receiveGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.RECEIVEGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.RECEIVEGROUP_NOT_NULL.getDesc()));
            }
            String needGroup = instorage.getNeedGroup();
            if (StringUtils.isBlank(needGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.NEEDGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.NEEDGROUP_NOT_NULL.getDesc()));
            }
            String purchaseGroup = instorage.getPurchaseGroup();
            if (StringUtils.isBlank(purchaseGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.PURCHASEGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.PURCHASEGROUP_NOT_NULL.getDesc()));
            }
            String provider = instorage.getProvider();
            if (StringUtils.isBlank(provider)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.PROVIDER_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.PROVIDER_NOT_NULL.getDesc()));
            }
            String settlementGroup = instorage.getSettlementGroup();
            if (StringUtils.isBlank(settlementGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SETTLEMENTGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SETTLEMENTGROUP_NOT_NULL.getDesc()));
            }
            String settlementCurrency = instorage.getSettlementCurrency();
            if (StringUtils.isBlank(settlementCurrency)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SETTLEMENTCURRENCY_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SETTLEMENTCURRENCY_NOT_NULL.getDesc()));
            }
            String shipperType = instorage.getShipperType();
            if (StringUtils.isBlank(shipperType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getDesc()));
            }
            String shipper = instorage.getShipper();
            if (StringUtils.isBlank(shipper)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPER_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPER_NOT_NULL.getDesc()));
            }

            if (StringUtils.isBlank(instorage.getInventoryGroup())) {
                instorage.setInventoryGroup(instorage.getInStorageProductList().get(0).getStock());
            }
            instorage.setInStorageId(null);
            int i = instorageDao.insertInstorage(instorage);
            //获取入库单ID
            Long inStorageId = instorage.getInStorageId();
            //入库商品：物料编码、单位、数量、收货仓库、仓库状态
            //查询出预入库商品
            List<InStorageProduct> inStorageProductList = JSON.parseArray(instorage.getInStorageProductJson(), InStorageProduct.class);
            JSONArray jsonArrayProduct = new JSONArray();
            if (inStorageProductList != null && inStorageProductList.size() > 0) {
                for (InStorageProduct inStorageProduct : inStorageProductList) {
                    Product product = iProductDao.selectProductByCode(inStorageProduct.getProductCode());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("productK3Number", product.getK3Number());
                    //根据单位id查看单位k3number
                  /*  HashMap mapUnit = new HashMap();
                    mapUnit.put("unitId",product.getUnitId());
                    Unit unitK3 = unitDao.selectUnit(mapUnit);
                    jsonObject.put("unitK3Number", unitK3.getK3Number());*/
                    jsonObject.put("unitK3Number", "Pcs");
                    jsonObject.put("number", inStorageProduct.getReceivedNumber());
                    jsonObject.put("stockK3Nunber", inStorageProduct.getStock());
                    jsonObject.put("fMaterialDesc", product.getProductName());
                    //根据仓库k3number 查看仓库对应组织的类型
                    Stock stockSelect = new Stock();
                    stockSelect.setStockCode(inStorageProduct.getStock());
                    String companyType = stockDao.selectStockByID(stockSelect).get(0).getCompanyType().toString();

                    if (companyType.equals("3")) {
                        //如果是门店入库  已商品的出库价为入库价
                        jsonObject.put("fPrice", product.getOutstoragePrice());
                    } else {
                        jsonObject.put("fPrice", product.getInstoragePrice());
                    }


                    jsonObject.put("fRequireOrgId", instorage.getOrgK3Number());


                    jsonArrayProduct.add(jsonObject);
                    String productCode = inStorageProduct.getProductCode();
                    if (StringUtils.isBlank(productCode)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getDesc()));
                    }
                    String unit = inStorageProduct.getUnit();
                    if (StringUtils.isBlank(unit)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.UNIT_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.UNIT_NOT_NULL.getDesc()));
                    }
                    Integer receivedNumber = inStorageProduct.getReceivedNumber();
                    if (receivedNumber == null) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.RECEIVEDNUMBER_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.RECEIVEDNUMBER_NOT_NULL.getDesc()));
                    }
                    String stock = inStorageProduct.getStock();
                    if (StringUtils.isBlank(stock)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.STOCK_NOT_NULL.getDesc()));
                    }
                    String stockStatus = inStorageProduct.getStockStatus();
                    if (StringUtils.isBlank(stockStatus)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKSTATUS_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.STOCKSTATUS_NOT_NULL.getDesc()));
                    }
                    inStorageProduct.setInStorageId(inStorageId);
                    inStorageProduct.setInStorageNumber(inStorageNumber);

                    inStorageProduct.setCreateOperator(instorage.getCreateOperator());
                    int i1 = instorageDao.insertInStorageProduct(inStorageProduct);
                    //查询仓库商品数量
                    ResponseResult responseResult = checkProductStockNumbers(stock, productCode, null);
                    Integer result = 0;
                    if (responseResult.isSuccess()) {
                        result = (Integer) responseResult.getResult();
                    }
                    //更新即时库存
                    StockProduct stockProduct = new StockProduct();
                    stockProduct.setStock(stock);
                    stockProduct.setStockPlace(inStorageProduct.getStockPlace());
                    stockProduct.setStockStatus(inStorageProduct.getStockStatus());
                    stockProduct.setBatchNumber(inStorageProduct.getBatchNumber());
                    stockProduct.setProductType(inStorageProduct.getProductType());
                    stockProduct.setProductCode(productCode);
                    stockProduct.setProductName(inStorageProduct.getProductName());
                    stockProduct.setSecondaryAttribute(inStorageProduct.getSecondaryAttribute());
                    stockProduct.setUnit(inStorageProduct.getUnit());
                    stockProduct.setNumber(receivedNumber);
                    stockProduct.setAveailableNumber(inStorageProduct.getReceivableNumber());
                    stockProduct.setInventoryGroup(instorage.getInventoryGroup());
                    stockProduct.setShipperType(instorage.getShipperType());
                    stockProduct.setShipperCode(instorage.getShipperCode());
                    stockProduct.setShipper(instorage.getShipper());
                    stockProduct.setKeeperType(inStorageProduct.getKeeperType());
                    stockProduct.setKeeperCode(inStorageProduct.getKeeperCode());
                    stockProduct.setKeeper(inStorageProduct.getKeeper());
                    stockProduct.setRemark(inStorageProduct.getRemark());
                    stockProductDao.insertStockProduct(stockProduct);
                    //更新商品数量
                    ProductNumber productNumber = new ProductNumber();
                    productNumber.setStock(stock);
                    productNumber.setType(InStorageTypeEnum.PURCHASE_INSTORAGE.getDesc());
                    productNumber.setStorageNumber(inStorageNumber);
                    productNumber.setBatchNumber(inStorageProduct.getBatchNumber());
                    productNumber.setProductType(inStorageProduct.getProductType());
                    productNumber.setProductName(inStorageProduct.getProductName());
                    productNumber.setProductCode(inStorageProduct.getProductCode());
                    productNumber.setUnit(unit);
                    productNumber.setNumber(receivedNumber);
                    productNumber.setAveailableNumber(inStorageProduct.getReceivableNumber());
                    productNumber.setOldNumber(result);
                    productNumber.setTotalNumber(result + inStorageProduct.getReceivedNumber());
                    productNumberDao.insertProductNumber(productNumber);
                }
            }
            //todo 写入k3
            //用户名密码
            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //根据供应商code 查看供应商信息
            Map supplierMap = (Map) (storeApi.selectSupplierByCode(instorage.getProvider()).getResult());
            ResponseResult responseResult = sendToK3PurchaseInstorage("add", dataCentre,
                    userName,
                    password,
                    instorage.getOrgK3Number(),
                    instorage.getOrgK3Number(),
                    supplierMap.get("k3Number").toString(),//供应商
                    sdf.format(inStorageDate),
                    supplierMap.get("address").toString(),//供应商地址
                    null,//联系人
                    jsonArrayProduct.toJSONString(),
                    inStorageId,
                    instorage.getStockId());
            //生成采购订单
            ResponseResult responseResultCaiGouOrder = k3CLOUDApi.savePurchaseOrder(dataCentre,
                    userName,
                    password,
                    instorage.getOrgK3Number(),
                    supplierMap.get("k3Number").toString(),
                    sdf.format(inStorageDate),
                    jsonArrayProduct.toJSONString());
            if (responseResult.getResponseStatusType().getMessage().equals("Success")) {
                return ResponseResult.success(instorage.getInStorageId());
            } else {
                return ResponseResult.success(instorage.getInStorageId());
            }

        }
        if (inStorageType.equals(InStorageTypeEnum.OTHER_INSTORAGE.getDesc())) {
            //其他入库：单据类型、库存组织、库存方向、日期、货主类型、货主、单据状态
            String invoicesType = instorage.getInvoicesType();
            if (StringUtils.isBlank(invoicesType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getDesc()));
            }
            String inventoryGroup = instorage.getInventoryGroup();
            if (StringUtils.isBlank(inventoryGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getDesc()));
            }
            String inventoryWay = instorage.getInventoryWay();
            if (StringUtils.isBlank(inventoryWay)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getDesc()));
            }
            Date inStorageDate = instorage.getInStorageDate();
            if (inStorageDate == null) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getDesc()));
            }
            String shipperType = instorage.getShipperType();
            if (StringUtils.isBlank(shipperType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getDesc()));
            }
            String shipper = instorage.getShipper();
            if (StringUtils.isBlank(shipper)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPER_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPER_NOT_NULL.getDesc()));
            }
            Map<Integer, String> InStorageInvoicesTypeEnum = EnumUtil.getEnumToMap(InStorageInvoicesTypeEnum.class);
            boolean b = InStorageInvoicesTypeEnum.containsValue(invoicesType);
            if (!b) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_ERROR.getDesc()));
            }
            Map<Integer, String> InventoryWayEnum = EnumUtil.getEnumToMap(InventoryWayEnum.class);
            boolean b1 = InventoryWayEnum.containsValue(inventoryWay);
            if (!b1) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYWAY_ERROR.getCode(),
                        ResponseCodeStockEnum.INVENTORYWAY_ERROR.getDesc()));
            }
            if (StringUtils.isBlank(instorage.getInventoryGroup())) {
                instorage.setInventoryGroup(instorage.getInStorageProductList().get(0).getStock());
            }
            instorage.setInStorageId(null);
            int i = instorageDao.insertInstorage(instorage);
            //获取入库单ID
            Long inStorageId = instorage.getInStorageId();
            //入库商品：物料编码、单位、数量、收货仓库
            //查询出预入库商品
            List<InStorageProduct> inStorageProductList = JSON.parseArray(instorage.getInStorageProductJson(), InStorageProduct.class);
            JSONArray jsonArrayProduct = new JSONArray();
            if (inStorageProductList != null && inStorageProductList.size() > 0) {
                for (InStorageProduct inStorageProduct : inStorageProductList) {
                    ServiceProduct serviceProduct = iServiceProductDao.selectProductByCode(inStorageProduct.getProductCode());
                    JSONObject jsonObject = new JSONObject();
                    if (serviceProduct == null) {
                        Product product = iProductDao.selectProductByCode(inStorageProduct.getProductCode());
                        jsonObject.put("productK3Number", product.getK3Number());
                    } else {
                        jsonObject.put("productK3Number", serviceProduct.getK3Number());
                    }


                    //获取单位
                   /* HashMap mapUnit = new HashMap();
                    mapUnit.put("unitId",serviceProduct.getUnitId());
                    Unit unitK3 = unitDao.selectUnit(mapUnit);
                    jsonObject.put("unitK3Number", unitK3.getK3Number());*/
                    jsonObject.put("unitK3Number", "Pcs");
                    jsonObject.put("number", inStorageProduct.getReceivedNumber());

                    jsonArrayProduct.add(jsonObject);


                    String productCode = inStorageProduct.getProductCode();
                    if (StringUtils.isBlank(productCode)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getDesc()));
                    }
                    String unit = inStorageProduct.getUnit();
                    if (StringUtils.isBlank(unit)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.UNIT_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.UNIT_NOT_NULL.getDesc()));
                    }
                    Integer receivedNumber = inStorageProduct.getReceivedNumber();
                    if (receivedNumber == null) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.RECEIVEDNUMBER_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.RECEIVEDNUMBER_NOT_NULL.getDesc()));
                    }
                    String stock = inStorageProduct.getStock();
                    if (StringUtils.isBlank(stock)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.STOCK_NOT_NULL.getDesc()));
                    }
                    inStorageProduct.setInStorageId(inStorageId);
                    inStorageProduct.setInStorageNumber(inStorageNumber);
                    inStorageProduct.setCreateOperator(instorage.getCreateOperator());

                    int i1 = instorageDao.insertInStorageProduct(inStorageProduct);
                    //查询仓库商品数量
                    ResponseResult responseResult = checkProductStockNumbers(stock, productCode, null);
                    Integer result = 0;
                    if (responseResult.isSuccess()) {
                        result = (Integer) responseResult.getResult();
                    }
                    //更新即时库存
                    StockProduct stockProduct = new StockProduct();
                    stockProduct.setStock(stock);
                    stockProduct.setStockPlace(inStorageProduct.getStockPlace());
                    stockProduct.setStockStatus(inStorageProduct.getStockStatus());
                    stockProduct.setBatchNumber(inStorageProduct.getBatchNumber());
                    stockProduct.setProductType(inStorageProduct.getProductType());
                    stockProduct.setProductCode(productCode);
                    stockProduct.setProductName(inStorageProduct.getProductName());
                    stockProduct.setSecondaryAttribute(inStorageProduct.getSecondaryAttribute());
                    stockProduct.setUnit(inStorageProduct.getUnit());
                    stockProduct.setNumber(receivedNumber);
                    stockProduct.setAveailableNumber(receivedNumber);
                    stockProduct.setInventoryGroup(instorage.getInventoryGroup());
                    stockProduct.setShipperType(instorage.getShipperType());
                    stockProduct.setShipperCode(instorage.getShipperCode());
                    stockProduct.setShipper(instorage.getShipper());
                    stockProduct.setKeeperType(inStorageProduct.getKeeperType());
                    stockProduct.setKeeperCode(inStorageProduct.getKeeperCode());
                    stockProduct.setKeeper(inStorageProduct.getKeeper());
                    stockProduct.setRemark(inStorageProduct.getRemark());
                    stockProductDao.insertStockProduct(stockProduct);
                    //更新商品数量
                    ProductNumber productNumber = new ProductNumber();
                    productNumber.setStock(stock);
                    productNumber.setType(InStorageTypeEnum.OTHER_INSTORAGE.getDesc());
                    productNumber.setStorageNumber(inStorageNumber);
                    productNumber.setBatchNumber(inStorageProduct.getBatchNumber());
                    productNumber.setProductType(inStorageProduct.getProductType());
                    productNumber.setProductName(inStorageProduct.getProductName());
                    productNumber.setProductCode(inStorageProduct.getProductCode());
                    productNumber.setUnit(unit);
                    productNumber.setNumber(inStorageProduct.getReceivedNumber());
                    productNumber.setAveailableNumber(inStorageProduct.getReceivableNumber());
                    productNumber.setOldNumber(result);
                    productNumber.setTotalNumber(result + inStorageProduct.getReceivedNumber());
                    productNumberDao.insertProductNumber(productNumber);
                }
            }
            //todo 写入k3
            //用户名密码
            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String supplierK3Number = null;
            if (StringUtils.isNotBlank(instorage.getProvider())) {
                //根据供应商code查看公司k3number
                supplierK3Number = ((Map) (storeApi.selectSupplierByCode(instorage.getProvider()).getResult())).get("k3Number").toString();
            }
            ResponseResult responseResult = sendToK3OtherInstorage("add", dataCentre,
                    userName,
                    password,
                    "QTRKD01_SYS",
                    instorage.getOrgK3Number(),
                    instorage.getInventoryWay(),
                    sdf.format(inStorageDate),
                    instorage.getBranch(),//部门
                    supplierK3Number,//供应商
                    "BD_OwnerOrg",
                    instorage.getOrgK3Number(),
                    "PRE001",
                    jsonArrayProduct.toJSONString(),
                    null,
                    inStorageProductList.get(0).getStock(),
                    "KCZT01_SYS",
                    null,
                    "BD_OwnerOrg",
                    instorage.getOrgK3Number(),
                    "BD_KeeperOrg",
                    instorage.getOrgK3Number(),
                    inStorageId,
                    instorage.getStockId());
            if (responseResult.getResponseStatusType().getMessage().equals("Success")) {
                return ResponseResult.success(instorage.getInStorageId());
            } else {
                return ResponseResult.success(instorage.getInStorageId());
            }

        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGE_INSERT_FAILED.getCode(),
                ResponseCodeStockEnum.INSTORAGE_INSERT_FAILED.getDesc()));
    }

    @Override
    public ResponseResult instorageReturn(InstoragePreAudit instorage) {
        String inStorageNumber = NumberUtils.getRandomOrderNo();
        instorage.setInStorageNumber(inStorageNumber);
        //单据编号，批号从K3获取
        //入库类型（其他入库、采购入库）
        instorage.setInStorageStatus("1");//单据状态,待审核
        String inStorageType = instorage.getInStorageType();
        if (StringUtils.isBlank(inStorageType)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGETYPE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.INSTORAGETYPE_NOT_NULL.getDesc()));
        }
        //其他入库：单据类型、库存组织、库存方向、日期、货主类型、货主、单据状态
        String invoicesType = instorage.getInvoicesType();
        if (StringUtils.isBlank(invoicesType)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getDesc()));
        }
        String inventoryGroup = instorage.getInventoryGroup();
        if (StringUtils.isBlank(inventoryGroup)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getDesc()));
        }
        String inventoryWay = instorage.getInventoryWay();
        if (StringUtils.isBlank(inventoryWay)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getDesc()));
        }
        Date inStorageDate = instorage.getInStorageDate();
        if (inStorageDate == null) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getDesc()));
        }
        String shipperType = instorage.getShipperType();
        if (StringUtils.isBlank(shipperType)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getDesc()));
        }
        String shipper = instorage.getShipper();
        if (StringUtils.isBlank(shipper)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPER_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.SHIPPER_NOT_NULL.getDesc()));
        }
        Map<Integer, String> InStorageInvoicesTypeEnum = EnumUtil.getEnumToMap(InStorageInvoicesTypeEnum.class);
        boolean b = InStorageInvoicesTypeEnum.containsValue(invoicesType);
        if (!b) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_ERROR.getCode(),
                    ResponseCodeStockEnum.INVOICESTYPE_ERROR.getDesc()));
        }
        Map<Integer, String> InventoryWayEnum = EnumUtil.getEnumToMap(InventoryWayEnum.class);
        boolean b1 = InventoryWayEnum.containsValue(inventoryWay);
        if (!b1) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYWAY_ERROR.getCode(),
                    ResponseCodeStockEnum.INVENTORYWAY_ERROR.getDesc()));
        }
        if (StringUtils.isBlank(instorage.getInventoryGroup())) {
            instorage.setInventoryGroup(instorage.getInStorageProductList().get(0).getStock());
        }
        instorage.setInStorageId(null);
        int i = instorageDao.insertInstorage(instorage);
        //获取入库单ID
        Long inStorageId = instorage.getInStorageId();
        //入库商品：物料编码、单位、数量、收货仓库
        //查询出预入库商品
        List<InStorageProduct> inStorageProductList = JSON.parseArray(instorage.getInStorageProductJson(), InStorageProduct.class);
        JSONArray jsonArrayProduct = new JSONArray();
        if (inStorageProductList != null && inStorageProductList.size() > 0) {
            for (InStorageProduct inStorageProduct : inStorageProductList) {
                String stock = inStorageProduct.getStock();
                String productCode = inStorageProduct.getProductCode();
                Product product = iProductDao.selectProductByCode(productCode);
                ServiceProduct serviceProduct = iServiceProductDao.selectProductByCode(productCode);
                JSONObject jsonObject = new JSONObject();
                if (product == null) {
                    jsonObject.put("productK3Number", serviceProduct.getK3Number());
                    jsonObject.put("productName", serviceProduct.getProductName());
                } else {
                    jsonObject.put("productK3Number", product.getK3Number());
                    jsonObject.put("productName", product.getProductName());
                }

                jsonObject.put("unitK3Number", "Pcs");
                jsonObject.put("number", inStorageProduct.getReceivedNumber());
                jsonObject.put("stockK3Nunber", inStorageProduct.getStock());
                jsonObject.put("fOwnerID", instorage.getOrgK3Number());
                jsonArrayProduct.add(jsonObject);


                //查询仓库商品数量
                ResponseResult responseResult = checkProductStockNumbers(stock, productCode, null);
                Integer result = 0;
                if (responseResult.isSuccess()) {
                    result = (Integer) responseResult.getResult();
                }
                //1.查询仓库商品（仓库和商品）
                Map<String, Object> map = new HashMap<>();
                map.put("stock", stock);
                map.put("productCode", productCode);
                StockProduct stockProductSum = stockProductDao.selectStockProductByStockIDAndProductIDSum(map);
                //判断此商品总量 是不是足够
                if (stockProductSum==null){
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                            ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc()+"当前可退："+0));
                }
                if (inStorageProduct.getReceivedNumber()>stockProductSum.getAveailableNumberSum()){
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                            ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc()+"当前可退："+stockProductSum.getAveailableNumberSum()));
                }

                List<StockProduct> stockProducts = stockProductDao.selectStockProductByStockIDAndProductID(map);
                //更新商品数量
                ProductNumber productNumber = new ProductNumber();
                productNumber.setStock(inStorageProductList.get(0).getStock());
                productNumber.setType(OutStorageTypeEnum.MARKET_OUTSTORAGE.getDesc());
                productNumber.setStorageNumber(inStorageNumber);
//                productNumber.setBatchNumber();
                productNumber.setProductType(inStorageProduct.getProductType());
                productNumber.setProductName(inStorageProduct.getProductName());
                productNumber.setProductCode(inStorageProduct.getProductCode());
                productNumber.setUnit(inStorageProduct.getUnit());
                productNumber.setNumber(inStorageProduct.getReceivedNumber());
                productNumber.setAveailableNumber(inStorageProduct.getReceivableNumber());
                productNumber.setOldNumber(result);
                productNumber.setTotalNumber(result - inStorageProduct.getReceivedNumber());
                productNumberDao.insertProductNumber(productNumber);

                Integer sendNumber = inStorageProduct.getReceivedNumber();
                inStorageProduct.setInStorageId(inStorageId);
                inStorageProduct.setInStorageNumber(inStorageNumber);
                Integer residue = sendNumber;
                for (StockProduct stockProduct : stockProducts) {
                    Integer aveailableNumber = stockProduct.getAveailableNumber();
                    if (residue > aveailableNumber) {
                        //2.按批号修改商品数量
                        Integer number = stockProduct.getNumber();
                        stockProduct.setNumber(number - aveailableNumber);
                        stockProduct.setAveailableNumber(0);
                        stockProductDao.updateStockProductByID(stockProduct);
                        //3.按批号出库商品
                        inStorageProduct.setBatchNumber(stockProduct.getBatchNumber());
                        inStorageProduct.setReceivedNumber(aveailableNumber);
                        inStorageProduct.setInStorageProductID(null);
                        instorageDao.insertInStorageProduct(inStorageProduct);
                        residue = residue - aveailableNumber;
                        continue;
                    }
                    //2.按批号修改商品数量
                    Integer number = stockProduct.getNumber();
                    stockProduct.setNumber(number - residue);
                    stockProduct.setAveailableNumber(aveailableNumber - residue);
                    stockProductDao.updateStockProductByID(stockProduct);
                    //3.按批号出库商品
                    inStorageProduct.setBatchNumber(stockProduct.getBatchNumber());
                    inStorageProduct.setReceivedNumber(residue);
                    inStorageProduct.setInStorageProductID(null);
                    instorageDao.insertInStorageProduct(inStorageProduct);
                    residue = 0;
                    break;
                }
                if (residue > 0) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                            ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc()+"当前可退："+stockProducts.get(0).getAveailableNumberSum()));
                }
            }
        }


        //todo 写入k3
        //用户名密码
        HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
        String dataCentre = userNameAndPassword.get("dataCentre");
        String userName = userNameAndPassword.get("userName");
        String password = userNameAndPassword.get("password");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String supplierK3Number = null;
        if (StringUtils.isNotBlank(instorage.getProvider())) {
            //根据供应商code查看公司k3number
            supplierK3Number = ((Map) (storeApi.selectSupplierByCode(instorage.getProvider()).getResult())).get("k3Number").toString();
        }

        ResponseResult responseResult = null;
        if (instorage.getInStorageType().equals("采购入库")) {
            //如果是采购入库 就走采购退料
            PurchaseReturnVO purchaseReturn = new PurchaseReturnVO();
            purchaseReturn.setOrgK3Number(instorage.getOrgK3Number());
            purchaseReturn.setSupplierK3Number(supplierK3Number);
            purchaseReturn.setFDate(sdf.format(new Date()));
            purchaseReturn.setJsonArrayProduct(jsonArrayProduct.toJSONString());
            purchaseReturn.setAcctId(dataCentre);
            purchaseReturn.setDataCentreUserName(userName);
            purchaseReturn.setDataCentrePassword(password);
            responseResult = sendToK3PurchaseReturn("add", purchaseReturn);
        } else if (instorage.getInStorageType().equals("其他入库")) {
            //如果是其他入库  就走方向为退货的其他入库
            responseResult = sendToK3OtherInstorage("add", dataCentre,
                    userName,
                    password,
                    "QTRKD01_SYS",
                    instorage.getOrgK3Number(),
                    instorage.getInventoryWay(),
                    sdf.format(inStorageDate),
                    instorage.getBranch(),//部门
                    supplierK3Number,//供应商
                    "BD_OwnerOrg",
                    instorage.getOrgK3Number(),
                    "PRE001",
                    jsonArrayProduct.toJSONString(),
                    null,
                    inStorageProductList.get(0).getStock(),
                    "KCZT01_SYS",
                    null,
                    "BD_OwnerOrg",
                    instorage.getOrgK3Number(),
                    "BD_KeeperOrg",
                    instorage.getOrgK3Number(),
                    inStorageId,
                    instorage.getStockId());

        }

        if (responseResult.getResponseStatusType().getMessage().equals("Success")) {
            return ResponseResult.success(instorage.getInStorageId());
        } else {
            return ResponseResult.success(instorage.getInStorageId());
        }


    }

    @Override
    public ResponseResult outstoragePreAudit(OutstoragePreAudit outstorage) {
        //出库单号
        String randomOrderNo = NumberUtils.getRandomOrderNo();
        String randomOrderNoBatch = NumberUtils.getRandomOrderNo();
        outstorage.setBatchNumber(randomOrderNoBatch);
        outstorage.setOutStorageNumber("YSH" + randomOrderNo);//自动生成出库单号
        //出库类型（销售出库、其他出库）
        String outStorageType = outstorage.getOutStorageType();
        if (StringUtils.isBlank(outStorageType)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGETYPE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.OUTSTORAGETYPE_NOT_NULL.getDesc()));
        }
        InstoragePreAudit instorage = new InstoragePreAudit();
        //查询预出库商品
        List<OutStorageProduct> outStorageProductList2 = JSON.parseArray(outstorage.getOutStorageProductJson(), OutStorageProduct.class);
        //查看出库组织是否有这些商品
        if (outStorageType.equals(OutStorageTypeEnum.MARKET_OUTSTORAGE.getDesc())) {
            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");

            //根据仓库 查看出库组织的类型
            Stock stockSelect = new Stock();
            stockSelect.setStockId(Long.parseLong(outstorage.getStockId()));
            String companyType = stockDao.selectStockByID(stockSelect).get(0).getCompanyType().toString();
            String number = outstorage.getStaffNumber();
/*            if (companyType.equals("2")) {
                number = "XSY001";
            } else if (companyType.equals("3")) {
                number = "XSY001F";
            }*/

            ResponseResult responseResultEmployees = k3CLOUDApi.employeesView(dataCentre, userName, password, number, null);

            if (responseResultEmployees.isSuccess() && ((Map) ((Map) ((Map) responseResultEmployees.getResult()).get("Result")).get("ResponseStatus")) != null &&
                    ((Boolean) (((Map) ((Map) ((Map) responseResultEmployees.getResult()).get("Result")).get("ResponseStatus"))).get("IsSuccess")) == false) {
                //如果不存在这样的员工
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getCode(),
                        ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getDesc()));
            } else if (((Map) ((Map) ((Map) responseResultEmployees.getResult()).get("Result")).get("ResponseStatus")) == null) {
                List<Map> deptList = (List<Map>) ((Map) ((Map) ((Map) responseResultEmployees.getResult()).get("Result")).get("Result")).get("PostEntity");
                String orgNumber = ((Map) ((Map) ((Map) ((Map) responseResultEmployees.getResult()).get("Result")).get("Result")).get("UseOrgId")).get("Number").toString();
                if (!orgNumber.equals(outstorage.getOrgK3Number())) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getCode(),
                            ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getDesc()));
                }
            }


            //销售出库：单据类型、日期、结算币别、销售组织、客户、发货组织
            Map custMap = (Map) storeApi.selectCustomerByNumber(outstorage.getClient()).getResult();
            if (!custMap.get("customerType").toString().equals("1")) {
                //如果不是默认零售客户 便查看  客户组织是否有商品
                if (custMap.get("relationSubCompanyType").toString().equals("2")) {
                    //如果是出库给子公司 查看子公司有不有这些商品
                    //根据子公司查看总公司
                    String companyId = ((Map) storeApi.selectSubsidiaryByID(Long.parseLong(custMap.get("relationSubCompanyId").toString())).getResult()).get("parentCompany").toString();
                    Map map = new HashMap();
                    map.put("companyId", Long.parseLong(companyId));
                    map.put("subcompanyId", custMap.get("relationSubCompanyId").toString());
                    List<Product> productList = iProductDao.selectProductSubcompany(map);
                    for (int i = 0; i < outStorageProductList2.size(); i++) {
                        boolean flag = true;
                        for (Product product : productList) {
                            if (outStorageProductList2.get(i).getProductCode().equals(product.getProductCode())) {
                                flag = false;
                            }
                        }
                        if (flag) {
                            //如果一个都没匹配上 说明 这个子公司没有这个商品
                            return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGE_ORG_PRODUCT_NULL.getCode(),
                                    ResponseCodeStockEnum.OUTSTORAGE_ORG_PRODUCT_NULL.getDesc()));
                        }
                    }
                } else if (custMap.get("relationSubCompanyType").toString().equals("3")) {
                    //如果是出库给分公司 查看分公司有不有这些商品
                    //根据分公司查看总公司
                    String subCompanyId = ((Map) storeApi.selectStoreById(Long.parseLong(custMap.get("relationSubCompanyId").toString())).getResult()).get("subCompanyId").toString();
                    String companyId = ((Map) storeApi.selectSubsidiaryByID(Long.parseLong(subCompanyId)).getResult()).get("parentCompany").toString();

                    Map map = new HashMap();
                    map.put("companyId", Long.parseLong(companyId));
                    map.put("subcompanyId", custMap.get("relationSubCompanyId").toString());
                    List<Product> productList = iProductDao.selectProductStore(map);
                    for (int i = 0; i < outStorageProductList2.size(); i++) {
                        boolean flag = true;
                        for (Product product : productList) {
                            if (outStorageProductList2.get(i).getProductCode().equals(product.getProductCode())) {
                                flag = false;
                            }
                        }
                        if (flag) {
                            //如果一个都没匹配上 说明 这个分公司没有这个商品
                            return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGE_ORG_PRODUCT_NULL.getCode(),
                                    ResponseCodeStockEnum.OUTSTORAGE_ORG_PRODUCT_NULL.getDesc()));
                        }
                    }
                }
            }


            String invoicesType = outstorage.getInvoicesType();
            if (StringUtils.isBlank(invoicesType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getDesc()));
            }
            Map<Integer, String> OutStorageInvoicesTypeEnum = EnumUtil.getEnumToMap(OutStorageInvoicesTypeEnum.class);
            boolean b = OutStorageInvoicesTypeEnum.containsValue(invoicesType);
            if (!b) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_ERROR.getDesc()));
            }
            Date outStorageDate = outstorage.getOutStorageDate();
            if (outStorageDate == null) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGEDATE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.OUTSTORAGEDATE_NOT_NULL.getDesc()));
            }
            String settlementCurrency = outstorage.getSettlementCurrency();
            if (StringUtils.isBlank(settlementCurrency)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SETTLEMENTCURRENCY_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SETTLEMENTCURRENCY_NOT_NULL.getDesc()));
            }
            String marketGroup = outstorage.getMarketGroup();
            if (StringUtils.isBlank(marketGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.MARKETGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.MARKETGROUP_NOT_NULL.getDesc()));
            }
            String client = outstorage.getClient();
            if (StringUtils.isBlank(client)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.CLIENT_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.CLIENT_NOT_NULL.getDesc()));
            }
            String shipmentGroup = outstorage.getShipmentGroup();
            if (StringUtils.isBlank(shipmentGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPMENTGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPMENTGROUP_NOT_NULL.getDesc()));
            }
            //查询预出库商品
            List<OutStorageProduct> outStorageProductList = JSON.parseArray(outstorage.getOutStorageProductJson(), OutStorageProduct.class);
            if (outStorageProductList == null || outStorageProductList.size() <= 0) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGEPRODUCT_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.OUTSTORAGEPRODUCT_NOT_NULL.getDesc()));
            }

            //出库商品判断
            List<StockProduct> stockProductcheck;
            for (OutStorageProduct outStorageProduct : outStorageProductList2) {
                String productCode = outStorageProduct.getProductCode();
                if (StringUtils.isBlank(productCode)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getDesc()));
                }
                Integer sendNumber = outStorageProduct.getSendNumber();
                if (sendNumber == null) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SENDNUMBER_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.SENDNUMBER_NOT_NULL.getDesc()));
                }
                String unit = outStorageProduct.getUnit();
                if (StringUtils.isBlank(unit)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.UNIT_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.UNIT_NOT_NULL.getDesc()));
                }
                String stock = outStorageProduct.getStock();
                if (StringUtils.isBlank(stock)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.STOCK_NOT_NULL.getDesc()));
                }
                String stockStatus = outStorageProduct.getStockStatus();
                if (StringUtils.isBlank(stockStatus)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKSTATUS_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.STOCKSTATUS_NOT_NULL.getDesc()));
                }
                //1.查询仓库商品（仓库和商品）
                Map<String, Object> map = new HashMap<>();
                map.put("stock", stock);
                map.put("productCode", productCode);
                stockProductcheck = stockProductDao.selectStockProductByStockIDAndProductID(map);
                if (stockProductcheck == null || stockProductcheck.size() <= 0) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT.getCode(),
                            "所选供应商" + ResponseCodeStockEnum.STOCKPRODUCT_NOT.getDesc()));
                }
                Integer numbers = 0;
                for (StockProduct stockProduct : stockProductcheck) {
                    numbers += stockProduct.getAveailableNumber();
                }
                if (sendNumber.compareTo(numbers) > 0) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                            "所选供应商" + ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc()));
                }

                outStorageProduct.setBatchNumber(randomOrderNoBatch);
            }
            if (StringUtils.isBlank(outstorage.getInventoryGroup())) {
                outstorage.setInventoryGroup(outstorage.getOutStorageProductList().get(0).getStock());
            }

            instorage.setInStorageType("采购入库");
            instorage.setInvoicesType("标准采购入库");
            instorage.setBusinessType("标准采购");
        }
        if (outStorageType.equals(OutStorageTypeEnum.OTHER_OUTSTORAGE.getDesc())) {
            //其他出库：单据类型、库存组织、领用组织、库存方向、日期、业务类型、货主、货主类型
            Map custMap = (Map) storeApi.selectCustomerByNumber(outstorage.getClient()).getResult();
            if (!custMap.get("customerType").toString().equals("1")) {
                //如果不是默认零售客户 便查看  客户组织是否有商品
                if (custMap.get("relationSubCompanyType").toString().equals("2")) {
                    //如果是出库给子公司 查看子公司有不有这些商品
                    //根据子公司查看总公司
                    String companyId = ((Map) storeApi.selectSubsidiaryByID(Long.parseLong(custMap.get("relationSubCompanyId").toString())).getResult()).get("parentCompany").toString();
                    Map map = new HashMap();
                    map.put("companyId", Long.parseLong(companyId));
                    map.put("subcompanyId", custMap.get("relationSubCompanyId").toString());
                    List<ServiceProduct> serviceProductList = iServiceProductDao.selectServiceProductSubcompany(map);
                    for (int i = 0; i < outStorageProductList2.size(); i++) {
                        boolean flag = true;
                        for (ServiceProduct serviceProduct : serviceProductList) {
                            if (outStorageProductList2.get(i).getProductCode().equals(serviceProduct.getProductCode())) {
                                flag = false;
                            }
                        }
                        if (flag) {
                            //如果一个都没匹配上 说明 这个子公司没有这个商品
                            return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGE_ORG_PRODUCT_NULL.getCode(),
                                    ResponseCodeStockEnum.OUTSTORAGE_ORG_PRODUCT_NULL.getDesc()));
                        }
                    }
                } else if (custMap.get("relationSubCompanyType").toString().equals("3")) {
                    //如果是出库给分公司 查看分公司有不有这些商品
                    //根据分公司查看总公司
                    String subCompanyId = ((Map) storeApi.selectStoreById(Long.parseLong(custMap.get("relationSubCompanyId").toString())).getResult()).get("subCompanyId").toString();
                    String companyId = ((Map) storeApi.selectSubsidiaryByID(Long.parseLong(subCompanyId)).getResult()).get("parentCompany").toString();

                    Map map = new HashMap();
                    map.put("companyId", Long.parseLong(companyId));
                    map.put("subcompanyId", custMap.get("relationSubCompanyId").toString());
                    List<ServiceProduct> serviceProductList = iServiceProductDao.selectServiceProductStore(map);
                    for (int i = 0; i < outStorageProductList2.size(); i++) {
                        boolean flag = true;
                        for (ServiceProduct serviceProduct : serviceProductList) {
                            if (outStorageProductList2.get(i).getProductCode().equals(serviceProduct.getProductCode())) {
                                flag = false;
                            }
                        }
                        if (flag) {
                            //如果一个都没匹配上 说明 这个分公司没有这个商品
                            return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGE_ORG_PRODUCT_NULL.getCode(),
                                    ResponseCodeStockEnum.OUTSTORAGE_ORG_PRODUCT_NULL.getDesc()));
                        }
                    }
                }
            }


            String invoicesType = outstorage.getInvoicesType();
            if (StringUtils.isBlank(invoicesType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getDesc()));
            }
            Map<Integer, String> OutStorageInvoicesTypeEnum = EnumUtil.getEnumToMap(OutStorageInvoicesTypeEnum.class);
            boolean b = OutStorageInvoicesTypeEnum.containsValue(invoicesType);
            if (!b) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_ERROR.getDesc()));
            }
            String inventoryGroup = outstorage.getInventoryGroup();
            if (StringUtils.isBlank(inventoryGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getDesc()));
            }
            String receiveGroup = outstorage.getReceiveGroup();
            if (StringUtils.isBlank(receiveGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.RECEIVEGROUP1_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.RECEIVEGROUP1_NOT_NULL.getDesc()));
            }
            String inventoryWay = outstorage.getInventoryWay();
            if (StringUtils.isBlank(inventoryWay)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getDesc()));
            }
            Date outStorageDate = outstorage.getOutStorageDate();
            if (outStorageDate == null) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGEDATE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.OUTSTORAGEDATE_NOT_NULL.getDesc()));
            }
            String businessType = outstorage.getBusinessType();
            if (StringUtils.isBlank(businessType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.BUSINESSTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.BUSINESSTYPE_NOT_NULL.getDesc()));
            }
            Map<Integer, String> OutStorageBusinessTypeEnum = EnumUtil.getEnumToMap(OutStorageBusinessTypeEnum.class);
            boolean b1 = OutStorageBusinessTypeEnum.containsValue(businessType);
            if (!b1) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.BUSINESSTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.BUSINESSTYPE_ERROR.getDesc()));
            }
            String shipperType = outstorage.getShipperType();
            if (StringUtils.isBlank(shipperType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getDesc()));
            }
            String shipper = outstorage.getShipper();
            if (StringUtils.isBlank(shipper)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPER_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPER_NOT_NULL.getDesc()));
            }
            List<OutStorageProduct> outStorageProductList = outstorage.getOutStorageProductList();
            if (outStorageProductList == null || outStorageProductList.size() <= 0) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGEPRODUCT_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.OUTSTORAGEPRODUCT_NOT_NULL.getDesc()));
            }
            //判断仓库商品数量
            List<StockProduct> stockProducts;

            for (OutStorageProduct outStorageProduct : outStorageProductList2) {
                String productCode = outStorageProduct.getProductCode();
                if (StringUtils.isBlank(productCode)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getDesc()));
                }
                Integer sendNumber = outStorageProduct.getSendNumber();
                if (sendNumber == null) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SENDNUMBER_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.SENDNUMBER_NOT_NULL.getDesc()));
                }
                String unit = outStorageProduct.getUnit();
                if (StringUtils.isBlank(unit)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.UNIT_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.UNIT_NOT_NULL.getDesc()));
                }
                String stock = outStorageProduct.getStock();
                if (StringUtils.isBlank(stock)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.STOCK_NOT_NULL.getDesc()));
                }
                //1.查询仓库商品（仓库和商品）
                Map<String, Object> map = new HashMap<>();
                map.put("stock", stock);
                map.put("productCode", productCode);
                stockProducts = stockProductDao.selectStockProductByStockIDAndProductID(map);
                if (stockProducts == null || stockProducts.size() <= 0) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT.getCode(),
                            "所选供应商" + ResponseCodeStockEnum.STOCKPRODUCT_NOT.getDesc()));
                }
                Integer numbers = 0;
                for (StockProduct stockProduct : stockProducts) {
                    numbers += stockProduct.getAveailableNumber();
                }
                if (sendNumber.compareTo(numbers) > 0) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                            "所选供应商" + ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc()));
                }
                outStorageProduct.setBatchNumber(randomOrderNoBatch);
            }
            if (StringUtils.isBlank(outstorage.getInventoryGroup())) {
                outstorage.setInventoryGroup(outstorage.getOutStorageProductList().get(0).getStock());
            }

            instorage.setInStorageType("其他入库");
            instorage.setInvoicesType("标准其他入库");
            instorage.setBusinessType("标准采购");

        }

        //同步生成对应客户的入库单
        //根据出库客户查找客户的信息
        Map map = (Map) (storeApi.selectCustomerByNumber(outstorage.getClient()).getResult());
        String relationSubCompanyType = map.get("relationSubCompanyType").toString();
        String relationSubCompanyId = map.get("relationSubCompanyId").toString();
        String caigouOrgName = "";
        String caigouOrgK3Number = "";
        String caigouOrgId = "";
        if (relationSubCompanyType.equals("2")) {
            //如果客户是子公司
            Map mapSubCompany = (Map) (storeApi.selectSubsidiaryByID(Long.parseLong(relationSubCompanyId)).getResult());
            caigouOrgName = mapSubCompany.get("subsidiaryName").toString();
            caigouOrgK3Number = mapSubCompany.get("k3Number").toString();
            caigouOrgId = mapSubCompany.get("subsidiaryId").toString();
        } else if (relationSubCompanyType.equals("3")) {
            //如何客户是分公司
            Map mapStore = (Map) (storeApi.selectStoreById(Long.parseLong(relationSubCompanyId)).getResult());
            caigouOrgName = mapStore.get("name").toString();
            caigouOrgK3Number = mapStore.get("k3Number").toString();
            caigouOrgId = mapStore.get("storeId").toString();
        }
        //查看仓库编号
        Stock stock = new Stock();
        stock.setCompanyId(Long.parseLong(relationSubCompanyId));
        stock.setCompanyType(Long.parseLong(relationSubCompanyType));
        String stockCode = stockDao.selectStockByID(stock).get(0).getStockCode();
        Long stockId = stockDao.selectStockByID(stock).get(0).getStockId();
        //查看供应商信息
        Map mapSupp = (Map) (storeApi.selectSupplierByCondition(outstorage.getCompanyType(), outstorage.getCompanyId()).getResult());
        String supplierCode = mapSupp.get("supplierCode").toString();
        String supplierName = mapSupp.get("supplierName").toString();


        instorage.setBatchNumber(outstorage.getBatchNumber());
        instorage.setInStorageNumber(outstorage.getOutStorageNumber());
        instorage.setInStorageDate(new Date());
        instorage.setInStorageStatus("0");
        instorage.setInventoryGroup(stockCode);
        instorage.setReceiveGroup(caigouOrgName);
        instorage.setStockGroup(stockCode);
        instorage.setNeedGroup(caigouOrgName);
        instorage.setPurchaseGroup(caigouOrgName);
        instorage.setProvider(supplierCode);
        instorage.setProviderName(supplierName);
        instorage.setSettlementGroup(caigouOrgName);
        instorage.setSettlementCurrency("人民币");
        instorage.setShipperType("业务组织");
        instorage.setShipper(caigouOrgName);
        instorage.setStockId(stockId.toString());

        //其他入库的时候
        instorage.setInventoryWay(outstorage.getInventoryWay());
        instorage.setConfirmStatus(0);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(outStorageProductList2));
        JSONArray jsonArrayNew = new JSONArray();


        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("receivableNumber", jsonArray.getJSONObject(i).get("sendableNumber"));
            object.put("receivedNumber", jsonArray.getJSONObject(i).get("sendNumber"));
            object.put("unitPrice", jsonArray.getJSONObject(i).get("unitPrice"));
            object.put("unit", jsonArray.getJSONObject(i).get("unit"));
            object.put("productCode", jsonArray.getJSONObject(i).get("productCode"));
            object.put("totalPrice", jsonArray.getJSONObject(i).get("totalPrice"));
            object.put("specification", jsonArray.getJSONObject(i).get("specification"));
            object.put("stockStatus", jsonArray.getJSONObject(i).get("stockStatus"));
            object.put("stock", stockCode);
            object.put("productType", jsonArray.getJSONObject(i).get("productType"));
            object.put("productName", jsonArray.getJSONObject(i).get("productName"));
            object.put("batchNumber", jsonArray.getJSONObject(i).get("batchNumber"));
            jsonArrayNew.add(object);
        }


        instorage.setInStorageProductJson(jsonArrayNew.toJSONString());
        instorage.setShipperCode(caigouOrgId);
        instorage.setOrgK3Number(caigouOrgK3Number);
        instorage.setOutstorageOrgName(outstorage.getOutstorageOrgName());
        ResponseResult responseResult = this.instoragePreAuditInside(instorage);
        if (responseResult.isSuccess()) {
            outstorage.setInStorageId(Long.parseLong(responseResult.getResult().toString()));
            outstorage.setBatchNumber(randomOrderNoBatch);
            outstorage.setOutStorageProductJson(null);
            outstorage.setInstorageOrgName(outstorage.getClientName());
            outstorageDao.insertOutstoragePreAudit(outstorage);
            //添加预出库商品
            Map mapAddchu = new HashMap();
            List<OutstoragePreAuditProduct> outstoragePreAuditProductListAdd = new ArrayList<>();
            List<OutStorageProduct> outStorageProductList = JSON.parseArray(jsonArray.toJSONString(), OutStorageProduct.class);
            for (OutStorageProduct outStorageProduct : outStorageProductList) {
                OutstoragePreAuditProduct outstoragePreAuditProduct = new OutstoragePreAuditProduct();
                outstoragePreAuditProduct.setOutstoragePreAuditId(outstorage.getOutStorageId());
                outstoragePreAuditProduct.setUnit(outStorageProduct.getUnit());
                outstoragePreAuditProduct.setUnitPrice(outStorageProduct.getUnitPrice());
                outstoragePreAuditProduct.setProductCode(outStorageProduct.getProductCode());
                outstoragePreAuditProduct.setTotalPrice(outStorageProduct.getTotalPrice());
                outstoragePreAuditProduct.setSpecification(outStorageProduct.getSpecification());
                outstoragePreAuditProduct.setStockStatus(outStorageProduct.getStockStatus());
                outstoragePreAuditProduct.setSendNumber(outStorageProduct.getSendNumber());
                outstoragePreAuditProduct.setStock(outStorageProduct.getStock());
                outstoragePreAuditProduct.setSendableNumber(outStorageProduct.getSendableNumber());
                outstoragePreAuditProduct.setProductName(outStorageProduct.getProductName());
                outstoragePreAuditProduct.setProductType(outStorageProduct.getProductType());
                outstoragePreAuditProduct.setBatchNumber(outStorageProduct.getBatchNumber());
                outstoragePreAuditProductListAdd.add(outstoragePreAuditProduct);
            }

            mapAddchu.put("list", outstoragePreAuditProductListAdd);
            outstorageDao.insertPreOutstorageProduct(mapAddchu);
            InstoragePreAudit instoragePreAudit = new InstoragePreAudit();
            instoragePreAudit.setInStorageId(Long.parseLong(responseResult.getResult().toString()));
            instoragePreAudit.setOutStorageId(outstorage.getOutStorageId());
            instorageDao.updateInstoragePreAuditById(instoragePreAudit);
            return ResponseResult.success("预出库成功，请通知入库组织确收");
        } else {
            return responseResult;
        }


    }

    @Override
    public ResponseResult outstoragePreAuditInside(OutstoragePreAudit outstorage) {
        //出库单号
        String randomOrderNo = NumberUtils.getRandomOrderNo();
        outstorage.setOutStorageNumber("YSH" + randomOrderNo);//自动生成出库单号
        //出库类型（销售出库、其他出库）
        outstorage.setOutStorageStatus(0);    //单据待审核
        String outStorageType = outstorage.getOutStorageType();
        if (StringUtils.isBlank(outStorageType)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGETYPE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.OUTSTORAGETYPE_NOT_NULL.getDesc()));
        }
        InstoragePreAudit instorage = new InstoragePreAudit();
        if (outStorageType.equals(OutStorageTypeEnum.MARKET_OUTSTORAGE.getDesc())) {
            //销售出库：单据类型、日期、结算币别、销售组织、客户、发货组织
            String invoicesType = outstorage.getInvoicesType();
            if (StringUtils.isBlank(invoicesType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getDesc()));
            }
            Map<Integer, String> OutStorageInvoicesTypeEnum = EnumUtil.getEnumToMap(OutStorageInvoicesTypeEnum.class);
            boolean b = OutStorageInvoicesTypeEnum.containsValue(invoicesType);
            if (!b) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_ERROR.getDesc()));
            }
            Date outStorageDate = outstorage.getOutStorageDate();
            if (outStorageDate == null) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGEDATE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.OUTSTORAGEDATE_NOT_NULL.getDesc()));
            }
            String settlementCurrency = outstorage.getSettlementCurrency();
            if (StringUtils.isBlank(settlementCurrency)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SETTLEMENTCURRENCY_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SETTLEMENTCURRENCY_NOT_NULL.getDesc()));
            }
            String marketGroup = outstorage.getMarketGroup();
            if (StringUtils.isBlank(marketGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.MARKETGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.MARKETGROUP_NOT_NULL.getDesc()));
            }
            String client = outstorage.getClient();
            if (StringUtils.isBlank(client)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.CLIENT_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.CLIENT_NOT_NULL.getDesc()));
            }
            String shipmentGroup = outstorage.getShipmentGroup();
            if (StringUtils.isBlank(shipmentGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPMENTGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPMENTGROUP_NOT_NULL.getDesc()));
            }
            //查询预出库商品

            List<OutStorageProduct> outStorageProductList = JSON.parseArray(outstorage.getOutStorageProductJson(), OutStorageProduct.class);
            if (outStorageProductList == null || outStorageProductList.size() <= 0) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGEPRODUCT_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.OUTSTORAGEPRODUCT_NOT_NULL.getDesc()));
            }

            //出库商品判断
            List<StockProduct> stockProductcheck = new ArrayList<>();
            List<OutStorageProduct> outStorageProductList2 = JSON.parseArray(outstorage.getOutStorageProductJson(), OutStorageProduct.class);
            for (OutStorageProduct outStorageProduct : outStorageProductList2) {
                String productCode = outStorageProduct.getProductCode();
                if (StringUtils.isBlank(productCode)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getDesc()));
                }
                Integer sendNumber = outStorageProduct.getSendNumber();
                if (sendNumber == null) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SENDNUMBER_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.SENDNUMBER_NOT_NULL.getDesc()));
                }
                String unit = outStorageProduct.getUnit();
                if (StringUtils.isBlank(unit)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.UNIT_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.UNIT_NOT_NULL.getDesc()));
                }
                String stock = outStorageProduct.getStock();
                if (StringUtils.isBlank(stock)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.STOCK_NOT_NULL.getDesc()));
                }
                String stockStatus = outStorageProduct.getStockStatus();
                if (StringUtils.isBlank(stockStatus)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKSTATUS_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.STOCKSTATUS_NOT_NULL.getDesc()));
                }
                //1.查询仓库商品（仓库和商品）
                Map<String, Object> map = new HashMap<>();
                map.put("stock", stock);
                map.put("productCode", productCode);
                stockProductcheck = stockProductDao.selectStockProductByStockIDAndProductID(map);
                if (stockProductcheck == null || stockProductcheck.size() <= 0) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT.getCode(),
                            "所选供应商" + ResponseCodeStockEnum.STOCKPRODUCT_NOT.getDesc()));
                }
                Integer numbers = 0;
                for (StockProduct stockProduct : stockProductcheck) {
                    numbers += stockProduct.getAveailableNumber();
                }
                if (sendNumber.compareTo(numbers) > 0) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                            "所选供应商" + ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc()));
                }
            }
            if (StringUtils.isBlank(outstorage.getInventoryGroup())) {
                outstorage.setInventoryGroup(outstorage.getOutStorageProductList().get(0).getStock());
            }
            outstorage.setOutStorageProductJson(null);
            outstorageDao.insertOutstoragePreAudit(outstorage);
            //添加预出库商品
            Map mapAddchu = new HashMap();
            List<OutstoragePreAuditProduct> outstoragePreAuditProductListAdd = new ArrayList<>();
            for (OutStorageProduct outStorageProduct : outStorageProductList) {
                OutstoragePreAuditProduct outstoragePreAuditProduct = new OutstoragePreAuditProduct();
                outstoragePreAuditProduct.setOutstoragePreAuditId(outstorage.getOutStorageId());
                outstoragePreAuditProduct.setUnit(outStorageProduct.getUnit());
                outstoragePreAuditProduct.setUnitPrice(outStorageProduct.getUnitPrice());
                outstoragePreAuditProduct.setProductCode(outStorageProduct.getProductCode());
                outstoragePreAuditProduct.setTotalPrice(outStorageProduct.getTotalPrice());
                outstoragePreAuditProduct.setSpecification(outStorageProduct.getSpecification());
                outstoragePreAuditProduct.setStockStatus(outStorageProduct.getStockStatus());
                outstoragePreAuditProduct.setSendNumber(outStorageProduct.getSendNumber());
                outstoragePreAuditProduct.setStock(outStorageProduct.getStock());
                outstoragePreAuditProduct.setSendableNumber(outStorageProduct.getSendableNumber());
                outstoragePreAuditProduct.setProductName(outStorageProduct.getProductName());
                outstoragePreAuditProduct.setProductType(outStorageProduct.getProductType());
                outstoragePreAuditProduct.setBatchNumber(outStorageProduct.getBatchNumber());
                outstoragePreAuditProductListAdd.add(outstoragePreAuditProduct);
            }

            mapAddchu.put("list", outstoragePreAuditProductListAdd);
            outstorageDao.insertPreOutstorageProduct(mapAddchu);

        }
        if (outStorageType.equals(OutStorageTypeEnum.OTHER_OUTSTORAGE.getDesc())) {
            //其他出库：单据类型、库存组织、领用组织、库存方向、日期、业务类型、货主、货主类型
            String invoicesType = outstorage.getInvoicesType();
            if (StringUtils.isBlank(invoicesType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getDesc()));
            }
            Map<Integer, String> OutStorageInvoicesTypeEnum = EnumUtil.getEnumToMap(OutStorageInvoicesTypeEnum.class);
            boolean b = OutStorageInvoicesTypeEnum.containsValue(invoicesType);
            if (!b) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_ERROR.getDesc()));
            }
            String inventoryGroup = outstorage.getInventoryGroup();
            if (StringUtils.isBlank(inventoryGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getDesc()));
            }
            String receiveGroup = outstorage.getReceiveGroup();
            if (StringUtils.isBlank(receiveGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.RECEIVEGROUP1_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.RECEIVEGROUP1_NOT_NULL.getDesc()));
            }
            String inventoryWay = outstorage.getInventoryWay();
            if (StringUtils.isBlank(inventoryWay)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getDesc()));
            }
            Date outStorageDate = outstorage.getOutStorageDate();
            if (outStorageDate == null) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGEDATE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.OUTSTORAGEDATE_NOT_NULL.getDesc()));
            }
            String businessType = outstorage.getBusinessType();
            if (StringUtils.isBlank(businessType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.BUSINESSTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.BUSINESSTYPE_NOT_NULL.getDesc()));
            }
            Map<Integer, String> OutStorageBusinessTypeEnum = EnumUtil.getEnumToMap(OutStorageBusinessTypeEnum.class);
            boolean b1 = OutStorageBusinessTypeEnum.containsValue(businessType);
            if (!b1) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.BUSINESSTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.BUSINESSTYPE_ERROR.getDesc()));
            }
            String shipperType = outstorage.getShipperType();
            if (StringUtils.isBlank(shipperType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getDesc()));
            }
            String shipper = outstorage.getShipper();
            if (StringUtils.isBlank(shipper)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPER_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPER_NOT_NULL.getDesc()));
            }
            //查询预出库商品

            List<OutStorageProduct> outStorageProductList2 = JSON.parseArray(outstorage.getOutStorageProductJson(), OutStorageProduct.class);
            if (outStorageProductList2 == null || outStorageProductList2.size() <= 0) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGEPRODUCT_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.OUTSTORAGEPRODUCT_NOT_NULL.getDesc()));
            }
            //判断仓库商品数量
            List<StockProduct> stockProducts = new ArrayList<>();
            JSONArray jsonArrayProduct = new JSONArray();


            for (OutStorageProduct outStorageProduct : outStorageProductList2) {
                String productCode = outStorageProduct.getProductCode();
                if (StringUtils.isBlank(productCode)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getDesc()));
                }
                Integer sendNumber = outStorageProduct.getSendNumber();
                if (sendNumber == null) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SENDNUMBER_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.SENDNUMBER_NOT_NULL.getDesc()));
                }
                String unit = outStorageProduct.getUnit();
                if (StringUtils.isBlank(unit)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.UNIT_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.UNIT_NOT_NULL.getDesc()));
                }
                String stock = outStorageProduct.getStock();
                if (StringUtils.isBlank(stock)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.STOCK_NOT_NULL.getDesc()));
                }
                //1.查询仓库商品（仓库和商品）
                Map<String, Object> map = new HashMap<>();
                map.put("stock", stock);
                map.put("productCode", productCode);
                stockProducts = stockProductDao.selectStockProductByStockIDAndProductID(map);
                if (stockProducts == null || stockProducts.size() <= 0) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT.getCode(),
                            "所选供应商" + ResponseCodeStockEnum.STOCKPRODUCT_NOT.getDesc()));
                }
                Integer numbers = 0;
                for (StockProduct stockProduct : stockProducts) {
                    numbers += stockProduct.getAveailableNumber();
                }
                if (sendNumber.compareTo(numbers) > 0) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                            "所选供应商" + ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc() + "：" + outStorageProduct));
                }
            }
            if (StringUtils.isBlank(outstorage.getInventoryGroup())) {
                outstorage.setInventoryGroup(outstorage.getOutStorageProductList().get(0).getStock());
            }

            outstorage.setOutStorageProductJson(null);
            outstorageDao.insertOutstoragePreAudit(outstorage);
            //添加预出库商品
            Map mapAddchu = new HashMap();
            List<OutstoragePreAuditProduct> outstoragePreAuditProductListAdd = new ArrayList<>();
            for (OutStorageProduct outStorageProduct : outStorageProductList2) {
                OutstoragePreAuditProduct outstoragePreAuditProduct = new OutstoragePreAuditProduct();
                outstoragePreAuditProduct.setOutstoragePreAuditId(outstorage.getOutStorageId());
                outstoragePreAuditProduct.setUnit(outStorageProduct.getUnit());
                outstoragePreAuditProduct.setUnitPrice(outStorageProduct.getUnitPrice());
                outstoragePreAuditProduct.setProductCode(outStorageProduct.getProductCode());
                outstoragePreAuditProduct.setTotalPrice(outStorageProduct.getTotalPrice());
                outstoragePreAuditProduct.setSpecification(outStorageProduct.getSpecification());
                outstoragePreAuditProduct.setStockStatus(outStorageProduct.getStockStatus());
                outstoragePreAuditProduct.setSendNumber(outStorageProduct.getSendNumber());
                outstoragePreAuditProduct.setStock(outStorageProduct.getStock());
                outstoragePreAuditProduct.setSendableNumber(outStorageProduct.getSendableNumber());
                outstoragePreAuditProduct.setProductName(outStorageProduct.getProductName());
                outstoragePreAuditProduct.setProductType(outStorageProduct.getProductType());
                outstoragePreAuditProduct.setBatchNumber(outStorageProduct.getBatchNumber());
                outstoragePreAuditProductListAdd.add(outstoragePreAuditProduct);
            }

            mapAddchu.put("list", outstoragePreAuditProductListAdd);
            outstorageDao.insertPreOutstorageProduct(mapAddchu);
        }
        return ResponseResult.success(outstorage.getOutStorageId());

    }

    @Override
    public ResponseResult outstorage(OutstoragePreAudit outstorage) {
        //出库单号
        String outStorageNumber = NumberUtils.getRandomOrderNo();
        //出库类型（销售出库、其他出库）
        outstorage.setOutStorageStatus(0);    //单据待审核
        String outStorageType = outstorage.getOutStorageType();
        if (StringUtils.isBlank(outStorageType)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGETYPE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.OUTSTORAGETYPE_NOT_NULL.getDesc()));
        }
        if (outStorageType.equals(OutStorageTypeEnum.MARKET_OUTSTORAGE.getDesc())) {

            //销售出库：单据类型、日期、结算币别、销售组织、客户、发货组织
            String invoicesType = outstorage.getInvoicesType();
            if (StringUtils.isBlank(invoicesType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getDesc()));
            }
            Map<Integer, String> OutStorageInvoicesTypeEnum = EnumUtil.getEnumToMap(OutStorageInvoicesTypeEnum.class);
            boolean b = OutStorageInvoicesTypeEnum.containsValue(invoicesType);
            if (!b) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_ERROR.getDesc()));
            }
            Date outStorageDate = outstorage.getOutStorageDate();
            if (outStorageDate == null) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGEDATE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.OUTSTORAGEDATE_NOT_NULL.getDesc()));
            }
            String settlementCurrency = outstorage.getSettlementCurrency();
            if (StringUtils.isBlank(settlementCurrency)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SETTLEMENTCURRENCY_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SETTLEMENTCURRENCY_NOT_NULL.getDesc()));
            }
            String marketGroup = outstorage.getMarketGroup();
            if (StringUtils.isBlank(marketGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.MARKETGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.MARKETGROUP_NOT_NULL.getDesc()));
            }
            String client = outstorage.getClient();
            if (StringUtils.isBlank(client)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.CLIENT_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.CLIENT_NOT_NULL.getDesc()));
            }
            String shipmentGroup = outstorage.getShipmentGroup();
            if (StringUtils.isBlank(shipmentGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPMENTGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPMENTGROUP_NOT_NULL.getDesc()));
            }

            //查询预出库商品


            List<OutStorageProduct> outStorageProductList = JSON.parseArray(outstorage.getOutStorageProductJson(), OutStorageProduct.class);
            if (outStorageProductList == null || outStorageProductList.size() <= 0) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGEPRODUCT_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.OUTSTORAGEPRODUCT_NOT_NULL.getDesc()));
            }

            //出库商品判断
            List<StockProduct> stockProductcheck;
            for (OutStorageProduct outStorageProduct : outStorageProductList) {
                String productCode = outStorageProduct.getProductCode();
                if (StringUtils.isBlank(productCode)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getDesc()));
                }
                Integer sendNumber = outStorageProduct.getSendNumber();
                if (sendNumber == null) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SENDNUMBER_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.SENDNUMBER_NOT_NULL.getDesc()));
                }
                String unit = outStorageProduct.getUnit();
                if (StringUtils.isBlank(unit)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.UNIT_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.UNIT_NOT_NULL.getDesc()));
                }
                String stock = outStorageProduct.getStock();
                if (StringUtils.isBlank(stock)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.STOCK_NOT_NULL.getDesc()));
                }
                String stockStatus = outStorageProduct.getStockStatus();
                if (StringUtils.isBlank(stockStatus)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKSTATUS_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.STOCKSTATUS_NOT_NULL.getDesc()));
                }
                //1.查询仓库商品（仓库和商品）
                Map<String, Object> map = new HashMap<>();
                map.put("stock", stock);
                map.put("productCode", productCode);
                stockProductcheck = stockProductDao.selectStockProductByStockIDAndProductID(map);
                if (stockProductcheck == null || stockProductcheck.size() <= 0) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT.getCode(),
                            ResponseCodeStockEnum.STOCKPRODUCT_NOT.getDesc()));
                }
                Integer numbers = 0;
                for (StockProduct stockProduct : stockProductcheck) {
                    numbers += stockProduct.getAveailableNumber();
                }
                if (sendNumber.compareTo(numbers) > 0) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                            ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc() + "：" + outStorageProduct));
                }
            }
            if (StringUtils.isBlank(outstorage.getInventoryGroup())) {
                outstorage.setInventoryGroup(outstorage.getOutStorageProductList().get(0).getStock());
            }


            outstorage.setOutStorageNumber(outStorageNumber);
            outstorage.setOutStorageId(null);
            outstorageDao.insertOutstorage(outstorage);
            Long outStorageId = outstorage.getOutStorageId();


            JSONArray jsonArrayProduct = new JSONArray();
            //出库商品：物料编码、单位、数量、仓库、仓库状态、批号
            for (OutStorageProduct outStorageProduct : outStorageProductList) {
                String stock = outStorageProduct.getStock();
                String productCode = outStorageProduct.getProductCode();
                Product product = iProductDao.selectProductByCode(productCode);
                ServiceProduct serviceProduct = iServiceProductDao.selectProductByCode(productCode);
                JSONObject jsonObject = new JSONObject();
                if (product == null) {
                    jsonObject.put("productK3Number", serviceProduct.getK3Number());
                } else {
                    jsonObject.put("productK3Number", product.getK3Number());
                }

                //根据单位id查看单位k3number
                /*HashMap mapUnit = new HashMap();
                mapUnit.put("unitId",product.getUnitId());
                Unit unitK3 = unitDao.selectUnit(mapUnit);
                jsonObject.put("unitK3Number", unitK3.getK3Number());*/
                jsonObject.put("unitK3Number", "Pcs");
                jsonObject.put("number", outStorageProduct.getSendNumber());
                jsonObject.put("stockK3Nunber", outStorageProduct.getStock());
                jsonObject.put("fOwnerID", outstorage.getOrgK3Number());
                //根据仓库k3number 查看仓库对应组织的类型
                Stock stockSelect = new Stock();
                stockSelect.setStockCode(outStorageProduct.getStock());
                String companyType = stockDao.selectStockByID(stockSelect).get(0).getCompanyType().toString();
                if (companyType.equals("3")) {
                    //如果是门店出库  已商品的零售价为出库价
                    jsonObject.put("fPrice", product.getRetailPrice());
                } else {
                    jsonObject.put("fPrice", product.getOutstoragePrice());
                }


                jsonArrayProduct.add(jsonObject);


                //查询仓库商品数量
                ResponseResult responseResult = checkProductStockNumbers(stock, productCode, null);
                Integer result = 0;
                if (responseResult.isSuccess()) {
                    result = (Integer) responseResult.getResult();
                }
                //更新商品数量
                ProductNumber productNumber = new ProductNumber();
                productNumber.setStock(outStorageProductList.get(0).getStock());
                productNumber.setType(OutStorageTypeEnum.MARKET_OUTSTORAGE.getDesc());
                productNumber.setStorageNumber(outStorageNumber);
//                productNumber.setBatchNumber();
                productNumber.setProductType(outStorageProduct.getProductType());
                productNumber.setProductName(outStorageProduct.getProductName());
                productNumber.setProductCode(outStorageProduct.getProductCode());
                productNumber.setUnit(outStorageProduct.getUnit());
                productNumber.setNumber(outStorageProduct.getSendNumber());
                productNumber.setAveailableNumber(outStorageProduct.getSendableNumber());
                productNumber.setOldNumber(result);
                productNumber.setTotalNumber(result - outStorageProduct.getSendNumber());
                productNumberDao.insertProductNumber(productNumber);
                //1.查询仓库商品（仓库和商品）
                Map<String, Object> map = new HashMap<>();
                map.put("stock", stock);
                map.put("productCode", productCode);
                List<StockProduct> stockProducts = stockProductDao.selectStockProductByStockIDAndProductID(map);
                Integer sendNumber = outStorageProduct.getSendNumber();
                outStorageProduct.setOutStorageId(outStorageId);
                outStorageProduct.setOutStorageNumber(outStorageNumber);
                Integer residue = sendNumber;
                for (StockProduct stockProduct : stockProducts) {
                    Integer aveailableNumber = stockProduct.getAveailableNumber();
                    if (residue > aveailableNumber) {
                        //2.按批号修改商品数量
                        Integer number = stockProduct.getNumber();
                        stockProduct.setNumber(number - aveailableNumber);
                        stockProduct.setAveailableNumber(0);
                        stockProductDao.updateStockProductByID(stockProduct);
                        //3.按批号出库商品
                        outStorageProduct.setBatchNumber(stockProduct.getBatchNumber());
                        outStorageProduct.setSendNumber(aveailableNumber);
                        outstorageDao.insertOutStorageProduct(outStorageProduct);
                        residue = residue - aveailableNumber;
                        continue;
                    }
                    //2.按批号修改商品数量
                    Integer number = stockProduct.getNumber();
                    stockProduct.setNumber(number - residue);
                    stockProduct.setAveailableNumber(aveailableNumber - residue);
                    stockProductDao.updateStockProductByID(stockProduct);
                    //3.按批号出库商品
                    outStorageProduct.setBatchNumber(stockProduct.getBatchNumber());
                    outStorageProduct.setSendNumber(residue);
                    outstorageDao.insertOutStorageProduct(outStorageProduct);
                    residue = 0;
                    break;
                }
                if (residue > 0) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                            ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc()));
                }
            }
            //todo 写入k3
            //用户名密码
            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");
            MarketOutstorageVO vo = new MarketOutstorageVO();
            vo.setAcctId(dataCentre);
            vo.setDataCentreUserName(userName);
            vo.setDataCentrePassword(password);
            vo.setFNumberCustomer(outstorage.getClient());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            vo.setFDate(sdf.format(outStorageDate));
            vo.setFNumberSaleOrg(outstorage.getOrgK3Number());
            vo.setFNumberSettleOrg(outstorage.getOrgK3Number());
            vo.setFNumberStockOrg(outstorage.getOrgK3Number());

            //查看销售员的所属部门编号
            //根据仓库 查看出库组织的类型
            String number = outstorage.getStaffNumber();
            ResponseResult responseResultEmployees = k3CLOUDApi.employeesView(dataCentre, userName, password, number, null);
            String fSaleDeptId = null;
            if (responseResultEmployees.isSuccess() && ((Map) ((Map) ((Map) responseResultEmployees.getResult()).get("Result")).get("ResponseStatus")) != null &&
                    ((Boolean) (((Map) ((Map) ((Map) responseResultEmployees.getResult()).get("Result")).get("ResponseStatus"))).get("IsSuccess")) == false) {
                //如果不存在这样的员工
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getCode(),
                        ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getDesc()));
            } else if (((Map) ((Map) ((Map) responseResultEmployees.getResult()).get("Result")).get("ResponseStatus")) == null) {
                List<Map> deptList = (List<Map>) ((Map) ((Map) ((Map) responseResultEmployees.getResult()).get("Result")).get("Result")).get("PostEntity");
                fSaleDeptId = ((Map) deptList.get(0).get("PostDept")).get("Number").toString();
                String orgNumber = ((Map) ((Map) ((Map) ((Map) responseResultEmployees.getResult()).get("Result")).get("Result")).get("UseOrgId")).get("Number").toString();
                if (!orgNumber.equals(outstorage.getOrgK3Number())) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getCode(),
                            ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getDesc()));
                }
            }
            ResponseResult responseResult = sendToK3MarketOutstorage("add", outStorageId, vo, jsonArrayProduct.toJSONString());


            ResponseResult responseResultXiaoShouOrder = k3CLOUDApi.marketOutstorageOrderSave(dataCentre,
                    userName,
                    password,
                    outstorage.getOrgK3Number(),
                    sdf.format(outStorageDate),
                    outstorage.getClient(),
                    jsonArrayProduct.toJSONString(),
                    fSaleDeptId,
                    number);


            if (responseResult.getResponseStatusType().getMessage().equals("Success")) {
                return ResponseResult.success(outstorage.getOutStorageId());
            } else {
                return ResponseResult.success(outstorage.getOutStorageId());
            }


        }
        if (outStorageType.equals(OutStorageTypeEnum.OTHER_OUTSTORAGE.getDesc())) {
            //其他出库：单据类型、库存组织、领用组织、库存方向、日期、业务类型、货主、货主类型
            String invoicesType = outstorage.getInvoicesType();
            if (StringUtils.isBlank(invoicesType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getDesc()));
            }
            Map<Integer, String> OutStorageInvoicesTypeEnum = EnumUtil.getEnumToMap(OutStorageInvoicesTypeEnum.class);
            boolean b = OutStorageInvoicesTypeEnum.containsValue(invoicesType);
            if (!b) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_ERROR.getDesc()));
            }
            String inventoryGroup = outstorage.getInventoryGroup();
            if (StringUtils.isBlank(inventoryGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getDesc()));
            }
            String receiveGroup = outstorage.getReceiveGroup();
            if (StringUtils.isBlank(receiveGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.RECEIVEGROUP1_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.RECEIVEGROUP1_NOT_NULL.getDesc()));
            }
            String inventoryWay = outstorage.getInventoryWay();
            if (StringUtils.isBlank(inventoryWay)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getDesc()));
            }
            Date outStorageDate = outstorage.getOutStorageDate();
            if (outStorageDate == null) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGEDATE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.OUTSTORAGEDATE_NOT_NULL.getDesc()));
            }
            String businessType = outstorage.getBusinessType();
            if (StringUtils.isBlank(businessType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.BUSINESSTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.BUSINESSTYPE_NOT_NULL.getDesc()));
            }
            Map<Integer, String> OutStorageBusinessTypeEnum = EnumUtil.getEnumToMap(OutStorageBusinessTypeEnum.class);
            boolean b1 = OutStorageBusinessTypeEnum.containsValue(businessType);
            if (!b1) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.BUSINESSTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.BUSINESSTYPE_ERROR.getDesc()));
            }
            String shipperType = outstorage.getShipperType();
            if (StringUtils.isBlank(shipperType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getDesc()));
            }
            String shipper = outstorage.getShipper();
            if (StringUtils.isBlank(shipper)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPER_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPER_NOT_NULL.getDesc()));
            }
            //查询预出库商品

            List<OutStorageProduct> outStorageProductList = JSON.parseArray(outstorage.getOutStorageProductJson(), OutStorageProduct.class);
            if (outStorageProductList == null || outStorageProductList.size() <= 0) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGEPRODUCT_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.OUTSTORAGEPRODUCT_NOT_NULL.getDesc()));
            }
            //判断仓库商品数量
            List<StockProduct> stockProducts = new ArrayList<>();
            JSONArray jsonArrayProduct = new JSONArray();
            for (OutStorageProduct outStorageProduct : outStorageProductList) {
                String productCode = outStorageProduct.getProductCode();
                ServiceProduct serviceProduct = iServiceProductDao.selectProductByCode(productCode);
                Product product = iProductDao.selectProductByCode(productCode);
                JSONObject jsonObject = new JSONObject();
                if (product == null) {
                    jsonObject.put("productK3Number", serviceProduct.getK3Number());
                } else {
                    jsonObject.put("productK3Number", product.getK3Number());
                }
                //获取单位
                jsonObject.put("unitK3Number", "Pcs");
                jsonObject.put("number", outStorageProduct.getSendNumber());
                jsonObject.put("stockK3Nunber", outStorageProduct.getStock());
                jsonObject.put("fOwnerID", outstorage.getOrgK3Number());
                jsonArrayProduct.add(jsonObject);

                if (StringUtils.isBlank(productCode)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getDesc()));
                }
                Integer sendNumber = outStorageProduct.getSendNumber();
                if (sendNumber == null) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SENDNUMBER_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.SENDNUMBER_NOT_NULL.getDesc()));
                }
                String unit = outStorageProduct.getUnit();
                if (StringUtils.isBlank(unit)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.UNIT_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.UNIT_NOT_NULL.getDesc()));
                }
                String stock = outStorageProduct.getStock();
                if (StringUtils.isBlank(stock)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.STOCK_NOT_NULL.getDesc()));
                }
                //1.查询仓库商品（仓库和商品）
                Map<String, Object> map = new HashMap<>();
                map.put("stock", stock);
                map.put("productCode", productCode);
                stockProducts = stockProductDao.selectStockProductByStockIDAndProductID(map);
                if (stockProducts == null || stockProducts.size() <= 0) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT.getCode(),
                            ResponseCodeStockEnum.STOCKPRODUCT_NOT.getDesc()));
                }
                Integer numbers = 0;
                for (StockProduct stockProduct : stockProducts) {
                    numbers += stockProduct.getAveailableNumber();
                }
                if (sendNumber.compareTo(numbers) > 0) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                            ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc() + "：" + outStorageProduct));
                }
            }


            outstorage.setOutStorageNumber(outStorageNumber);
            outstorage.setOutStorageId(null);
            outstorageDao.insertOutstorage(outstorage);
            Long outStorageId = outstorage.getOutStorageId();
            //出库商品: 物料编码、单位、数量、仓库、批号
            for (OutStorageProduct outStorageProduct : outStorageProductList) {
                //查询仓库商品数量
                ResponseResult responseResult = checkProductStockNumbers(outStorageProduct.getStock(), outStorageProduct.getProductCode(), null);
                Integer result = 0;
                if (responseResult.isSuccess()) {
                    result = (Integer) responseResult.getResult();
                }
                //更新商品数量
                ProductNumber productNumber = new ProductNumber();
                productNumber.setStock(outStorageProductList.get(0).getStock());
                productNumber.setType(OutStorageTypeEnum.MARKET_OUTSTORAGE.getDesc());
                productNumber.setStorageNumber(outStorageNumber);
//                productNumber.setBatchNumber();
                productNumber.setProductType(outStorageProduct.getProductType());
                productNumber.setProductName(outStorageProduct.getProductName());
                productNumber.setProductCode(outStorageProduct.getProductCode());
                productNumber.setUnit(outStorageProduct.getUnit());
                productNumber.setNumber(outStorageProduct.getSendNumber());
                productNumber.setAveailableNumber(outStorageProduct.getSendableNumber());
                productNumber.setOldNumber(result);
                productNumber.setTotalNumber(result - outStorageProduct.getSendNumber());
                productNumberDao.insertProductNumber(productNumber);
                //1.查询仓库商品（仓库和商品）
                Map<String, Object> map = new HashMap<>();
                map.put("stock", outStorageProductList.get(0).getStock());
                map.put("productCode", outStorageProduct.getProductCode());
                stockProducts = stockProductDao.selectStockProductByStockIDAndProductID(map);

                Integer sendNumber = outStorageProduct.getSendNumber();
                outStorageProduct.setOutStorageId(outStorageId);
                outStorageProduct.setOutStorageNumber(outStorageNumber);
                Integer residue = sendNumber;
                for (StockProduct stockProduct : stockProducts) {
                    Integer aveailableNumber = stockProduct.getAveailableNumber();
                    if (residue > aveailableNumber) {
                        //2.按批号修改商品数量
                        Integer number = stockProduct.getNumber();
                        stockProduct.setNumber(number - aveailableNumber);
                        stockProduct.setAveailableNumber(0);
                        stockProductDao.updateStockProductByID(stockProduct);
                        //3.按批号出库商品
                        outStorageProduct.setBatchNumber(stockProduct.getBatchNumber());
                        outStorageProduct.setSendNumber(aveailableNumber);
                        outstorageDao.insertOutStorageProduct(outStorageProduct);
                        residue = residue - aveailableNumber;
                        continue;
                    }
                    //2.按批号修改商品数量
                    Integer number = stockProduct.getNumber();
                    stockProduct.setNumber(number - residue);
                    stockProduct.setAveailableNumber(aveailableNumber - residue);
                    stockProductDao.updateStockProductByID(stockProduct);
                    //3.按批号出库商品
                    outStorageProduct.setBatchNumber(stockProduct.getBatchNumber());
                    outStorageProduct.setSendNumber(residue);
                    outstorageDao.insertOutStorageProduct(outStorageProduct);
                    residue = 0;
                    break;
                }
                if (residue > 0) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                            ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc()));
                }
            }
            OtherOutstorageVO vo = new OtherOutstorageVO();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            vo.setFDate(sdf.format(outstorage.getOutStorageDate()));
            //用户名密码
            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");
            vo.setAcctId(dataCentre);
            vo.setDataCentreUserName(userName);
            vo.setDataCentrePassword(password);
            vo.setFStockOrgId(outstorage.getOrgK3Number());
            vo.setFPickOrgId(outstorage.getOrgK3Number());
            vo.setFCustId(outstorage.getClient());
            vo.setFOwnerIdHead(outstorage.getOrgK3Number());
            vo.setStockId(outstorage.getStockId());
            vo.setFStockDirect("GENERAL");
            ResponseResult responseResult = sendToK3OtherOutstorage("add", outStorageId, vo, jsonArrayProduct.toJSONString());
            if (responseResult.getResponseStatusType().getMessage().equals("Success")) {
                return ResponseResult.success(outstorage.getOutStorageId());
            } else {
                return ResponseResult.success(outstorage.getOutStorageId());
            }


        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGE_INSERT_FAILED.getCode(),
                ResponseCodeStockEnum.OUTSTORAGE_INSERT_FAILED.getDesc()));
    }

    @Override
    public ResponseResult outstorageReturn(OutstoragePreAudit outstorage) {
        String custK3Number = null;
        if (StringUtils.isBlank(outstorage.getClient())) {
            Map resultMapCust = (Map) storeApi.selectDefaultCust(Long.parseLong(outstorage.getStoreId()), 3).getResult();
            if (resultMapCust == null) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.DEFAULT_CUST_IS_NOT_FIND.getCode(),
                        ResponseCodeStockEnum.DEFAULT_CUST_IS_NOT_FIND.getDesc()));
            } else {
                custK3Number = resultMapCust.get("k3Number").toString();
            }
        } else {
            custK3Number = outstorage.getClient();
        }

        String outStorageNumber = NumberUtils.getRandomOrderNo();
        outstorage.setOutStorageNumber(outStorageNumber);
        //单据编号，批号从K3获取
        //入库类型（其他入库、采购入库）
        outstorage.setOutStorageStatus(1);//单据状态,待审核
        String outStorageType = outstorage.getOutStorageType();
        if (StringUtils.isBlank(outStorageType)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGETYPE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.OUTSTORAGETYPE_NOT_NULL.getDesc()));
        }
        // 验证商品和单位,仓库是否可用,插入K3，获取单号，批号，验证供应商
        //采购入库: 单据类型、业务类型、日期、单据状态、收料组织、需求组织
        //采购组织、供应商、结算组织、结算币别、货主类型、货主
        //其他入库：单据类型、库存组织、库存方向、日期、货主类型、货主、单据状态
        String invoicesType = outstorage.getInvoicesType();
        if (StringUtils.isBlank(invoicesType)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getDesc()));
        }
        String inventoryGroup = outstorage.getInventoryGroup();
        if (StringUtils.isBlank(inventoryGroup)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getDesc()));
        }
        String inventoryWay = outstorage.getInventoryWay();
        if (StringUtils.isBlank(inventoryWay)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getDesc()));
        }
        Date inStorageDate = outstorage.getOutStorageDate();
        if (inStorageDate == null) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGEDATE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.OUTSTORAGEDATE_NOT_NULL.getDesc()));
        }

        String shipperType = outstorage.getShipperType();
        if (StringUtils.isBlank(shipperType)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getDesc()));
        }
        String shipper = outstorage.getShipper();
        if (StringUtils.isBlank(shipper)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPER_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.SHIPPER_NOT_NULL.getDesc()));
        }
        Map<Integer, String> OutStorageInvoicesTypeEnum = EnumUtil.getEnumToMap(OutStorageInvoicesTypeEnum.class);
        boolean b = OutStorageInvoicesTypeEnum.containsValue(invoicesType);
        if (!b) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_ERROR.getCode(),
                    ResponseCodeStockEnum.INVOICESTYPE_ERROR.getDesc()));
        }
        Map<Integer, String> InventoryWayEnum = EnumUtil.getEnumToMap(InventoryWayEnum.class);
        boolean b1 = InventoryWayEnum.containsValue(inventoryWay);
        if (!b1) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYWAY_ERROR.getCode(),
                    ResponseCodeStockEnum.INVENTORYWAY_ERROR.getDesc()));
        }
        //查询预出库商品
        //查询预出库商品
        List<OutstoragePreAuditProduct> outstoragePreAuditProductList = outstorageDao.selectPreOutstorageProductByPreAuditId(outstorage.getOutStorageId());
        List<OutStorageProduct> outStorageProductList2 = JSON.parseArray(outstorage.getOutStorageProductJson(), OutStorageProduct.class);
        if (StringUtils.isBlank(outstorage.getInventoryGroup())) {
            outstorage.setInventoryGroup(outStorageProductList2.get(0).getStock());
        }
        outstorage.setStockGroup(outstorage.getInventoryGroup());
        outstorage.setOutStorageId(null);
        int i = outstorageDao.insertOutstorage(outstorage);
        //获取入库单ID
        Long outStorageId = outstorage.getOutStorageId();
        //入库商品：物料编码、单位、数量、收货仓库

        JSONArray jsonArrayProduct = new JSONArray();
        if (outStorageProductList2 != null && outStorageProductList2.size() > 0) {
            for (OutStorageProduct outStorageProduct : outStorageProductList2) {
                ServiceProduct serviceProduct = iServiceProductDao.selectProductByCode(outStorageProduct.getProductCode());
                JSONObject jsonObject = new JSONObject();
                if (serviceProduct == null) {
                    Product product = iProductDao.selectProductByCode(outStorageProduct.getProductCode());
                    jsonObject.put("productK3Number", product.getK3Number());
                } else {
                    jsonObject.put("productK3Number", serviceProduct.getK3Number());
                }
                jsonObject.put("unitK3Number", "Pcs");
                jsonObject.put("number", outStorageProduct.getSendNumber());
                jsonObject.put("stockK3Nunber", outStorageProduct.getStock());
                jsonObject.put("fOwnerID", outstorage.getOrgK3Number());

                jsonObject.put("fUnitID", "Pcs");

                jsonObject.put("fRealQty", outStorageProduct.getSendNumber());
                jsonObject.put("fOwnerId", outstorage.getOrgK3Number());
                jsonObject.put("fStockId", outStorageProduct.getStock());
                jsonObject.put("fSalUnitID", "Pcs");
                jsonObject.put("fSalUnitQty", outStorageProduct.getSendNumber());
                jsonObject.put("fSalBaseQty", outStorageProduct.getSendNumber());
                jsonObject.put("fPriceBaseQty", outStorageProduct.getSendNumber());
                jsonObject.put("fARNOTJOINQTY", outStorageProduct.getSendNumber());


                jsonArrayProduct.add(jsonObject);
                String productCode = outStorageProduct.getProductCode();
                if (StringUtils.isBlank(productCode)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getDesc()));
                }
                String unit = outStorageProduct.getUnit();
                if (StringUtils.isBlank(unit)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.UNIT_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.UNIT_NOT_NULL.getDesc()));
                }
                Integer sendNumber = outStorageProduct.getSendNumber();
                if (sendNumber == null) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SENDNUMBER_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.SENDNUMBER_NOT_NULL.getDesc()));
                }
                String stock = outStorageProduct.getStock();
                if (StringUtils.isBlank(stock)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_NULL.getCode(),
                            ResponseCodeStockEnum.STOCK_NOT_NULL.getDesc()));
                }
                outStorageProduct.setOutStorageId(outStorageId);
                outStorageProduct.setOutStorageNumber(outStorageNumber);
                outStorageProduct.setCreateOperator(outstorage.getCreateOperator());
                outStorageProduct.setOutStorageProductID(null);
                int i1 = outstorageDao.insertOutStorageProduct(outStorageProduct);
                //查询仓库商品数量
                ResponseResult responseResult = checkProductStockNumbers(stock, productCode, null);
                Integer result = 0;
                if (responseResult.isSuccess()) {
                    result = (Integer) responseResult.getResult();
                }
                //更新即时库存
                StockProduct stockProduct = new StockProduct();
                stockProduct.setStock(stock);
                stockProduct.setStockPlace(outStorageProduct.getStockPlace());
                stockProduct.setStockStatus(outStorageProduct.getStockStatus());
                stockProduct.setBatchNumber(outStorageProduct.getBatchNumber());
                stockProduct.setProductType(outStorageProduct.getProductType());
                stockProduct.setProductCode(productCode);
                stockProduct.setProductName(outStorageProduct.getProductName());
                stockProduct.setSecondaryAttribute(outStorageProduct.getSecondaryAttribute());
                stockProduct.setUnit(outStorageProduct.getUnit());
                stockProduct.setNumber(sendNumber);
                stockProduct.setAveailableNumber(sendNumber);
                stockProduct.setInventoryGroup(outstorage.getInventoryGroup());
                stockProduct.setShipperType(outstorage.getShipperType());
                stockProduct.setShipper(outstorage.getShipper());
                stockProduct.setKeeperType(outStorageProduct.getKeeperType());
                stockProduct.setKeeper(outStorageProduct.getKeeper());
                stockProduct.setRemark(outStorageProduct.getRemark());
                stockProductDao.insertStockProduct(stockProduct);
                //更新商品数量
                ProductNumber productNumber = new ProductNumber();
                productNumber.setStock(stock);
                productNumber.setType(InStorageTypeEnum.OTHER_INSTORAGE.getDesc());
                productNumber.setStorageNumber(outStorageNumber);
                productNumber.setBatchNumber(outStorageProduct.getBatchNumber());
                productNumber.setProductType(outStorageProduct.getProductType());
                productNumber.setProductName(outStorageProduct.getProductName());
                productNumber.setProductCode(outStorageProduct.getProductCode());
                productNumber.setUnit(unit);
                productNumber.setNumber(outStorageProduct.getSendNumber());
                productNumber.setAveailableNumber(outStorageProduct.getSendableNumber());
                productNumber.setOldNumber(result);
                productNumber.setTotalNumber(result + outStorageProduct.getSendNumber());
                productNumberDao.insertProductNumber(productNumber);
            }


            //todo 写入k3
            //用户名密码  --出库退货k3
            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (outstorage.getOutStorageType().equals("其他出库")) {
                OtherOutstorageVO vo = new OtherOutstorageVO();
                vo.setAcctId(dataCentre);
                vo.setDataCentreUserName(userName);
                vo.setDataCentrePassword(password);
                vo.setFCustId(custK3Number);
                vo.setFDate(sdf.format(inStorageDate));
                vo.setFStockOrgId(outstorage.getOrgK3Number());
                vo.setFPickOrgId(outstorage.getOrgK3Number());
                vo.setFOwnerIdHead(outstorage.getOrgK3Number());
                vo.setFStockDirect("RETURN");
                vo.setStockId(outstorage.getStockId());
                ResponseResult responseResult2 = sendToK3OtherOutstorage("add", outStorageId, vo, jsonArrayProduct.toJSONString());
                if (responseResult2.getResponseStatusType().getMessage().equals("Success")) {
                    return ResponseResult.success(outstorage.getOutStorageId());
                } else {
                    return ResponseResult.success(outstorage.getOutStorageId());
                }
            } else if (outstorage.getOutStorageType().equals("销售出库")) {
                MarketReturnVO marketReturn = new MarketReturnVO();
                marketReturn.setAcctId(dataCentre);
                marketReturn.setDataCentreUserName(userName);
                marketReturn.setDataCentrePassword(password);
                marketReturn.setFNumberSaleOrg(outstorage.getOrgK3Number());
                marketReturn.setFDate(sdf.format(inStorageDate));
                marketReturn.setFNumberStockOrg(outstorage.getInventoryGroup());
                marketReturn.setFNumberFrecust(outstorage.getClient());
                marketReturn.setFNumberBillType("XSTHD01_SYS");
                marketReturn.setFNumberSettleOrg(outstorage.getOrgK3Number());
                marketReturn.setFNumberSettleCurr("PRE001");
                marketReturn.setEntityJson(jsonArrayProduct.toJSONString());
                ResponseResult responseResult1 = sendToK3MarketReturn("add", outStorageId, marketReturn, outstorage.getStockId());
                if (responseResult1.getResponseStatusType().getMessage().equals("Success")) {
                    return ResponseResult.success(outstorage.getOutStorageId());
                } else {
                    return ResponseResult.success(outstorage.getOutStorageId());
                }
            }


        }

        return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGE_INSERT_FAILED.getCode(),
                ResponseCodeStockEnum.OUTSTORAGE_INSERT_FAILED.getDesc()));
    }

    private void outstorageToStore(OutstoragePreAudit outstorage) {
        String client = outstorage.getClient();         //客户
        String marketGroup = outstorage.getMarketGroup();   //销售组织  销售出库
        String shipper = outstorage.getShipper();   //货主  其他出库
        String marketGroupId = "0"; //销售组织的id
        String shipperId = "0"; //货主的id
        String createOperator = outstorage.getCreateOperator();
        //出库到分公司需要在分公司加一个入库记录
        //1.请求公司和分公司
        List<HashMap<String, Object>> companyList = new ArrayList<>();
        ResponseResult responseResult = storeApi.selectCompanyListNoPage(); //总公司
        if (responseResult != null && responseResult.getResult() != null) {
            List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) responseResult.getResult();
            if (list != null && list.size() > 0) {
                for (HashMap<String, Object> stringObjectHashMap : list) {
                    Integer companyId = (Integer) stringObjectHashMap.get("companyId");
                    String companyName = (String) stringObjectHashMap.get("companyName");
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("companyId", companyId);
                    map.put("companyName", companyName);
                    map.put("companyType", 1);   //总公司
                    companyList.add(map);
                    ResponseResult subCompany = storeApi.selectSubCompanyAndStoreNoPage(companyId.longValue(), 1l);  //子公司和分公司
                    if (subCompany != null && subCompany.getResult() != null) {
                        List<HashMap<String, Object>> subCompanyResult = (List<HashMap<String, Object>>) subCompany.getResult();
                        if (subCompanyResult != null && subCompanyResult.size() > 0) {
                            for (HashMap<String, Object> objectHashMap : subCompanyResult) {
                                Integer subsidiaryId = (Integer) objectHashMap.get("subsidiaryId");
                                String subsidiaryName = (String) objectHashMap.get("subsidiaryName");
                                HashMap<String, Object> map1 = new HashMap<>();
                                map1.put("companyId", subsidiaryId);
                                map1.put("companyName", subsidiaryName);
                                map1.put("companyType", 2);    //分公司
                                companyList.add(map1);
                                List<HashMap<String, Object>> storeVOList = (List<HashMap<String, Object>>) objectHashMap.get("storeVOList");
                                if (storeVOList != null && storeVOList.size() > 0) {
                                    for (HashMap<String, Object> hashMap : storeVOList) {
                                        Integer storeId = (Integer) hashMap.get("storeId");
                                        String name = (String) hashMap.get("name");
                                        HashMap<String, Object> map2 = new HashMap<>();
                                        map2.put("companyId", storeId);
                                        map2.put("companyName", name);
                                        map2.put("companyType", 3);    //分公司
                                        companyList.add(map2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //寻找销售组织和货主
        for (HashMap<String, Object> map : companyList) {
            if (StringUtils.isNotBlank(marketGroup) && marketGroup.equals(String.valueOf(map.get("companyName")))) {
                marketGroupId = String.valueOf((Integer) map.get("companyId"));
            }
            if (StringUtils.isNotBlank(shipper) && shipper.equals(String.valueOf(map.get("companyName")))) {
                shipperId = String.valueOf((Integer) map.get("companyId"));
            }
        }
        //2.判断是否是公司
        for (HashMap<String, Object> map : companyList) {
            if (client.equals(String.valueOf(map.get("companyName")))) {
                //销售的公司ID和客户的ID
                Integer companyId = (Integer) map.get("companyId");
                Integer companyType = (Integer) map.get("companyType");
                //client的仓库
                Stock stock = new Stock();
                stock.setCompanyType(companyType.longValue());
                stock.setCompanyId(companyId.longValue());
                List<Stock> stockList = stockDao.selectStockByID(stock);
                String stockCode = stockList.get(0).getStockCode();

                InstoragePreAudit instorage = new InstoragePreAudit();
                if (outstorage.getOutStorageType().equals(OutStorageTypeEnum.OTHER_OUTSTORAGE.getDesc())) {
                    instorage.setInStorageType(InStorageTypeEnum.OTHER_INSTORAGE.getDesc());
                    //其他入库：单据类型、库存组织、库存方向、日期、货主类型、货主、单据状态
                    instorage.setInvoicesType(InStorageInvoicesTypeEnum.STANDARD_PURCHASE_INSTORAGE.getDesc());
                    instorage.setInStorageDate(outstorage.getOutStorageDate());
                    instorage.setStockGroup(stockCode);
                    instorage.setInventoryGroup(stockCode);
                    instorage.setInventoryWay(InventoryWayEnum.NORMAL.getDesc());
                    instorage.setShipperType("业务组织");
                    instorage.setShipper(shipper);
                    instorage.setShipperCode(shipperId);
                    instorage.setCreateOperator(createOperator);
                    instorage.setRemark("公司之间入库");
//                instorage.setInStorageStatus("0");//单据状态,待审核
//                instorageDao.insertInstorage(instorage);
                    //获取入库单ID
//                    Long inStorageId = instorage.getInStorageId();
//                    String inStorageNumber = instorage.getInStorageNumber();
                    List<OutStorageProduct> outStorageProductList = outstorage.getOutStorageProductList();
                    //入库商品：物料编码、单位、数量、收货仓库
                    List<InStorageProduct> inStorageProductList = new ArrayList<>();
                    if (outStorageProductList != null && outStorageProductList.size() > 0) {
                        for (OutStorageProduct outStorageProduct : outStorageProductList) {
                            InStorageProduct inStorageProduct = new InStorageProduct();
                            inStorageProduct.setProductType(outStorageProduct.getProductType());
                            inStorageProduct.setProductCode(outStorageProduct.getProductCode());
                            inStorageProduct.setProductName(outStorageProduct.getProductName());
                            //商品有效期

                            inStorageProduct.setUnit(outStorageProduct.getUnit());
                            inStorageProduct.setUnitPrice(outStorageProduct.getUnitPrice());
                            inStorageProduct.setTotalPrice(outStorageProduct.getTotalPrice());

                            inStorageProduct.setReceivableNumber(outStorageProduct.getSendableNumber());
                            inStorageProduct.setReceivedNumber(outStorageProduct.getSendNumber());
                            inStorageProduct.setStock(stockCode);
                            inStorageProduct.setStockStatus(outStorageProduct.getStockStatus());
                            inStorageProduct.setKeeperType("业务组织");
                            inStorageProduct.setKeeperCode(String.valueOf(companyId));
                            inStorageProduct.setKeeper(client);
                            inStorageProductList.add(inStorageProduct);
                        }
                    }
                    instorage.setInStorageProductList(inStorageProductList);
                } else {
                    instorage.setInStorageType(InStorageTypeEnum.PURCHASE_INSTORAGE.getDesc());
                    //3.添加入库记录到公司
                    //采购入库: 单据类型、业务类型、日期、单据状态、收料组织、需求组织
                    //采购组织、供应商、结算组织、结算币别、货主类型、货主
                    instorage.setInvoicesType(InStorageInvoicesTypeEnum.STANDARD_PURCHASE_INSTORAGE.getDesc());
                    instorage.setBusinessType(InStorageBusinessTypeEnum.STANDARD_PURCHASE.getDesc());
                    instorage.setInStorageDate(outstorage.getOutStorageDate());
//                instorage.setInStorageStatus("0");//单据状态,待审核
                    instorage.setReceiveGroup(client);
                    instorage.setNeedGroup(client);
                    instorage.setPurchaseGroup(client);
                    instorage.setProviderName(marketGroup);
                    instorage.setProvider(marketGroupId);
                    instorage.setSettlementGroup(client);
                    instorage.setSettlementCurrency(outstorage.getSettlementCurrency());
                    instorage.setShipperType("业务组织");
                    instorage.setShipper(marketGroup);
                    instorage.setShipperCode(marketGroupId);
                    instorage.setCreateOperator(createOperator);
                    instorage.setRemark("公司之间入库");
//                instorageDao.insertInstorage(instorage);
                    //获取入库单ID
//                    Long inStorageId = instorage.getInStorageId();
//                    String inStorageNumber = instorage.getInStorageNumber();
                    List<OutStorageProduct> outStorageProductList = outstorage.getOutStorageProductList();
                    //入库商品：物料编码、单位、数量、收货仓库、仓库状态
                    List<InStorageProduct> inStorageProductList = new ArrayList<>();
                    if (outStorageProductList != null && outStorageProductList.size() > 0) {
                        for (OutStorageProduct outStorageProduct : outStorageProductList) {
                            InStorageProduct inStorageProduct = new InStorageProduct();
                            inStorageProduct.setProductType(outStorageProduct.getProductType());
                            inStorageProduct.setProductCode(outStorageProduct.getProductCode());
                            inStorageProduct.setProductName(outStorageProduct.getProductName());
                            //商品有效期

                            inStorageProduct.setUnit(outStorageProduct.getUnit());
                            inStorageProduct.setUnitPrice(outStorageProduct.getUnitPrice());
                            inStorageProduct.setTotalPrice(outStorageProduct.getTotalPrice());

                            inStorageProduct.setReceivableNumber(outStorageProduct.getSendableNumber());
                            inStorageProduct.setReceivedNumber(outStorageProduct.getSendNumber());
                            inStorageProduct.setStock(stockCode);
                            inStorageProduct.setStockStatus(outStorageProduct.getStockStatus());
                            inStorageProduct.setKeeperType("业务组织");
                            inStorageProduct.setKeeperCode(String.valueOf(companyId));
                            inStorageProduct.setKeeper(client);
                            inStorageProductList.add(inStorageProduct);
                        }
                    }
                    instorage.setInStorageProductList(inStorageProductList);
                }
//                instorage(instorage);
                //公司入库
                instorageCompany(instorage);
            }
        }
    }

    /*入库到公司,只添加入库记录，不加库存*/
    private ResponseResult instorageCompany(InstoragePreAudit instorage) {
        String batch = NumberUtils.getRandomOrderNo();
        instorage.setBatchNumber(batch);
        instorage.setInStorageNumber(batch);
        //单据编号，批号从K3获取
        //入库类型（其他入库、采购入库）
        instorage.setInStorageStatus("0");//单据状态,待审核
        String inStorageType = instorage.getInStorageType();
        if (StringUtils.isBlank(inStorageType)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGETYPE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.INSTORAGETYPE_NOT_NULL.getDesc()));
        }
        //todo  验证商品和单位,仓库是否可用,插入K3，获取单号，批号，验证供应商
        if (inStorageType.equals(InStorageTypeEnum.PURCHASE_INSTORAGE.getDesc())) {
            //采购入库: 单据类型、业务类型、日期、单据状态、收料组织、需求组织
            //采购组织、供应商、结算组织、结算币别、货主类型、货主
            String invoicesType = instorage.getInvoicesType();
            if (StringUtils.isBlank(invoicesType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getDesc()));
            }
            Map<Integer, String> InStorageInvoicesTypeEnum = EnumUtil.getEnumToMap(InStorageInvoicesTypeEnum.class);
            boolean b = InStorageInvoicesTypeEnum.containsValue(invoicesType);
            if (!b) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_ERROR.getDesc()));
            }
            String businessType = instorage.getBusinessType();
            if (StringUtils.isBlank(businessType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.BUSINESSTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.BUSINESSTYPE_NOT_NULL.getDesc()));
            }
            Map<Integer, String> InStorageBusinessTypeEnum = EnumUtil.getEnumToMap(InStorageBusinessTypeEnum.class);
            boolean b1 = InStorageBusinessTypeEnum.containsValue(businessType);
            if (!b1) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.BUSINESSTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.BUSINESSTYPE_ERROR.getDesc()));
            }
            Date inStorageDate = instorage.getInStorageDate();
            if (inStorageDate == null) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getDesc()));
            }
            String inStorageStatus = instorage.getInStorageStatus();
            if (StringUtils.isBlank(inStorageStatus)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGESTATUS_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INSTORAGESTATUS_NOT_NULL.getDesc()));
            }
            String receiveGroup = instorage.getReceiveGroup();
            if (StringUtils.isBlank(receiveGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.RECEIVEGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.RECEIVEGROUP_NOT_NULL.getDesc()));
            }
            String needGroup = instorage.getNeedGroup();
            if (StringUtils.isBlank(needGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.NEEDGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.NEEDGROUP_NOT_NULL.getDesc()));
            }
            String purchaseGroup = instorage.getPurchaseGroup();
            if (StringUtils.isBlank(purchaseGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.PURCHASEGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.PURCHASEGROUP_NOT_NULL.getDesc()));
            }
            String provider = instorage.getProvider();
            if (StringUtils.isBlank(provider)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.PROVIDER_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.PROVIDER_NOT_NULL.getDesc()));
            }
            String settlementGroup = instorage.getSettlementGroup();
            if (StringUtils.isBlank(settlementGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SETTLEMENTGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SETTLEMENTGROUP_NOT_NULL.getDesc()));
            }
            String settlementCurrency = instorage.getSettlementCurrency();
            if (StringUtils.isBlank(settlementCurrency)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SETTLEMENTCURRENCY_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SETTLEMENTCURRENCY_NOT_NULL.getDesc()));
            }
            String shipperType = instorage.getShipperType();
            if (StringUtils.isBlank(shipperType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getDesc()));
            }
            String shipper = instorage.getShipper();
            if (StringUtils.isBlank(shipper)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPER_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPER_NOT_NULL.getDesc()));
            }
            if (StringUtils.isBlank(instorage.getInventoryGroup())) {
                instorage.setInventoryGroup(instorage.getInStorageProductList().get(0).getStock());
            }
            int i = instorageDao.insertInstorage(instorage);
            //获取入库单ID
            Long inStorageId = instorage.getInStorageId();
            String inStorageNumber = instorage.getInStorageNumber();
            //入库商品：物料编码、单位、数量、收货仓库、仓库状态
            List<InStorageProduct> inStorageProductList = instorage.getInStorageProductList();
            if (inStorageProductList != null && inStorageProductList.size() > 0) {
                for (InStorageProduct inStorageProduct : inStorageProductList) {
                    //入库产品
                    String productCode = inStorageProduct.getProductCode();
                    if (StringUtils.isBlank(productCode)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getDesc()));
                    }
                    String unit = inStorageProduct.getUnit();
                    if (StringUtils.isBlank(unit)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.UNIT_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.UNIT_NOT_NULL.getDesc()));
                    }
                    Integer receivedNumber = inStorageProduct.getReceivedNumber();
                    if (receivedNumber == null) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.RECEIVEDNUMBER_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.RECEIVEDNUMBER_NOT_NULL.getDesc()));
                    }
                    String stock = inStorageProduct.getStock();
                    if (StringUtils.isBlank(stock)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.STOCK_NOT_NULL.getDesc()));
                    }
                    String stockStatus = inStorageProduct.getStockStatus();
                    if (StringUtils.isBlank(stockStatus)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKSTATUS_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.STOCKSTATUS_NOT_NULL.getDesc()));
                    }
                    inStorageProduct.setInStorageId(inStorageId);
                    inStorageProduct.setInStorageNumber(inStorageNumber);
                    inStorageProduct.setBatchNumber(batch);
                    inStorageProduct.setCreateOperator(instorage.getCreateOperator());
                    int i1 = instorageDao.insertInStorageProduct(inStorageProduct);
                }
            }
            //todo 写入k3
            PurchaseInstorageVO vo = new PurchaseInstorageVO();
            vo.setFNumberStockOrg("100");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            vo.setDate(sdf.format(instorage.getInStorageDate()));
            vo.setFNumberBillType("1");
            vo.setFOwnerTypeIdHead("1");
            vo.setFNumberOwnerIdHead("1");
            vo.setFNumberPurchaseOrg("100");
            vo.setFNumberSupplierId("1");
            vo.setFNumberSettleType("101");
            vo.setFNumberSettleCurr("PRE001");
            vo.setFPriceTimePoint("");      //2019-11-11 00:00:00
            vo.setEntityJson("[{\"FMaterialId\":\"4447\",\"FUnitID\":\"100156\",\"FPriceUnitID\":\"PRE001\",\"FRemainInStockUnitId\":\"100\"}]");
            //用户名密码
            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");
            vo.setAcctId(dataCentre);
            vo.setDataCentreUserName(userName);
            vo.setDataCentrePassword(password);
            /* sendToK3PurchaseInstorage("add", inStorageId, vo);*/
            return ResponseResult.success(instorage);
        }
        if (inStorageType.equals(InStorageTypeEnum.OTHER_INSTORAGE.getDesc())) {
            //其他入库：单据类型、库存组织、库存方向、日期、货主类型、货主、单据状态
            String invoicesType = instorage.getInvoicesType();
            if (StringUtils.isBlank(invoicesType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_NOT_NULL.getDesc()));
            }
            String inventoryGroup = instorage.getInventoryGroup();
            if (StringUtils.isBlank(inventoryGroup)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVENTORYGROUP_NOT_NULL.getDesc()));
            }
            String inventoryWay = instorage.getInventoryWay();
            if (StringUtils.isBlank(inventoryWay)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INVENTORYWAY_NOT_NULL.getDesc()));
            }
            Date inStorageDate = instorage.getInStorageDate();
            if (inStorageDate == null) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.INSTORAGEDATE_NOT_NULL.getDesc()));
            }
            String shipperType = instorage.getShipperType();
            if (StringUtils.isBlank(shipperType)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPERTYPE_NOT_NULL.getDesc()));
            }
            String shipper = instorage.getShipper();
            if (StringUtils.isBlank(shipper)) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.SHIPPER_NOT_NULL.getCode(),
                        ResponseCodeStockEnum.SHIPPER_NOT_NULL.getDesc()));
            }
            Map<Integer, String> InStorageInvoicesTypeEnum = EnumUtil.getEnumToMap(InStorageInvoicesTypeEnum.class);
            boolean b = InStorageInvoicesTypeEnum.containsValue(invoicesType);
            if (!b) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVOICESTYPE_ERROR.getCode(),
                        ResponseCodeStockEnum.INVOICESTYPE_ERROR.getDesc()));
            }
            Map<Integer, String> InventoryWayEnum = EnumUtil.getEnumToMap(InventoryWayEnum.class);
            boolean b1 = InventoryWayEnum.containsValue(inventoryWay);
            if (!b1) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.INVENTORYWAY_ERROR.getCode(),
                        ResponseCodeStockEnum.INVENTORYWAY_ERROR.getDesc()));
            }
            if (StringUtils.isBlank(instorage.getInventoryGroup())) {
                instorage.setInventoryGroup(instorage.getInStorageProductList().get(0).getStock());
            }
            int i = instorageDao.insertInstorage(instorage);
            //获取入库单ID
            String inStorageNumber = instorage.getInStorageNumber();
            Long inStorageId = instorage.getInStorageId();
            //入库商品：物料编码、单位、数量、收货仓库
            List<InStorageProduct> inStorageProductList = instorage.getInStorageProductList();
            if (inStorageProductList != null && inStorageProductList.size() > 0) {
                for (InStorageProduct inStorageProduct : inStorageProductList) {
                    String productCode = inStorageProduct.getProductCode();
                    if (StringUtils.isBlank(productCode)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getDesc()));
                    }
                    String unit = inStorageProduct.getUnit();
                    if (StringUtils.isBlank(unit)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.UNIT_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.UNIT_NOT_NULL.getDesc()));
                    }
                    Integer receivedNumber = inStorageProduct.getReceivedNumber();
                    if (receivedNumber == null) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.RECEIVEDNUMBER_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.RECEIVEDNUMBER_NOT_NULL.getDesc()));
                    }
                    String stock = inStorageProduct.getStock();
                    if (StringUtils.isBlank(stock)) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_NULL.getCode(),
                                ResponseCodeStockEnum.STOCK_NOT_NULL.getDesc()));
                    }
                    inStorageProduct.setInStorageId(inStorageId);
                    inStorageProduct.setInStorageNumber(inStorageNumber);
                    inStorageProduct.setBatchNumber(batch);
                    inStorageProduct.setCreateOperator(instorage.getCreateOperator());
                    int i1 = instorageDao.insertInStorageProduct(inStorageProduct);
                }
            }
            //todo 写入k3
            OtherInstorageVO vo = new OtherInstorageVO();
            vo.setFNumberStockOrg("100");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            vo.setFDate(sdf.format(instorage.getInStorageDate()));
            vo.setFNumberBillType("1");
            vo.setFOwnerTypeIdHead("1");
            vo.setFNumberOwerIdHead("1");
            vo.setFStockDirect("1");
            vo.setEntityJson("[\n{\"FMATERIALID\":\"4447\",\n\"FSTOCKID\":\"CK001\",\n\"FUnitID\":\"100156\",\n\"FSTOCKSTATUSID\":\"PRE001\",\n" +
                    "\"FOWNERTYPEID\":\"1\",\n\"FOWNERID\":\"100\",\n\"FKEEPERTYPEID\":\"1\",\n\"FKEEPERID\":\"100\"}\n]");
            //用户名密码
            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");
            vo.setAcctId(dataCentre);
            vo.setDataCentreUserName(userName);
            vo.setDataCentrePassword(password);
            /*sendToK3OtherInstorage("add", inStorageId, vo);*/
            return ResponseResult.success(instorage);
        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGE_INSERT_FAILED.getCode(),
                ResponseCodeStockEnum.INSTORAGE_INSERT_FAILED.getDesc()));
    }

    @Override
    public ResponseResult selectEnumByName(String name) {
        Map<Integer, String> enumToMap = new HashMap<>();
//        if(StringUtils.isBlank(name)){
//            enumToMap.putAll(EnumUtil.getEnumToMap(InStorageBusinessTypeEnum.class));
//            enumToMap.putAll(EnumUtil.getEnumToMap(InStorageInvoicesTypeEnum.class));
//            enumToMap.putAll(EnumUtil.getEnumToMap(InStorageTypeEnum.class));
//            enumToMap.putAll(EnumUtil.getEnumToMap(InventoryWayEnum.class));
//            enumToMap.putAll(EnumUtil.getEnumToMap(OutStorageInvoicesTypeEnum.class));
//            enumToMap.putAll(EnumUtil.getEnumToMap(OutStorageTypeEnum.class));
//            return ResponseResult.success(enumToMap);
//        }
        if ("InStorageBusinessTypeEnum".equals(name)) {
            enumToMap = EnumUtil.getEnumToMap(InStorageBusinessTypeEnum.class);
            return ResponseResult.success(enumToMap);
        }
        if ("InStorageInvoicesTypeEnum".equals(name)) {
            enumToMap = EnumUtil.getEnumToMap(InStorageInvoicesTypeEnum.class);
            return ResponseResult.success(enumToMap);
        }
        if ("InStorageTypeEnum".equals(name)) {
            enumToMap = EnumUtil.getEnumToMap(InStorageTypeEnum.class);
            return ResponseResult.success(enumToMap);
        }
        if ("InventoryWayEnum".equals(name)) {
            enumToMap = EnumUtil.getEnumToMap(InventoryWayEnum.class);
            return ResponseResult.success(enumToMap);
        }
        if ("OutStorageInvoicesTypeEnum".equals(name)) {
            enumToMap = EnumUtil.getEnumToMap(OutStorageInvoicesTypeEnum.class);
            return ResponseResult.success(enumToMap);
        }
        if ("OutStorageTypeEnum".equals(name)) {
            enumToMap = EnumUtil.getEnumToMap(OutStorageTypeEnum.class);
            return ResponseResult.success(enumToMap);
        }
        if ("OutStorageBusinessTypeEnum".equals(name)) {
            enumToMap = EnumUtil.getEnumToMap(OutStorageBusinessTypeEnum.class);
            return ResponseResult.success(enumToMap);
        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.NAME_ERROR.getCode(),
                ResponseCodeStockEnum.NAME_ERROR.getDesc()));
    }

    @Override
    public ResponseResult selectStockList(Long stockId, String stockCode, Long companyType, Long companyId, Boolean isSon) {
        Stock stock = new Stock();
        List<Stock> stockList = new ArrayList<>();
        if (stockId != null) {
            stock.setStockId(stockId);
        }
        if (StringUtils.isBlank(stockCode)) {
            stock.setStockCode(stockCode);
        }
        if (companyId != null) {
            stock.setCompanyId(companyId);
        }
        if (companyType != null) {
            stock.setCompanyType(companyType);
        }
        stockList = stockDao.selectStockByID(stock);
        if (companyId != null && companyType != null) {
            if (isSon != null && isSon) { //查询子公司仓库
                if (companyType == 1) { //总公司
                    //公司的map
                    HashMap<Integer, HashMap<Long, Long>> companyMap = new HashMap<>();
                    //查询总公司下的子公司
                    ResponseResult subsidiary = storeApi.selectSubsidiaryByParentID(companyId);
                    List<HashMap<String, Object>> result = (List<HashMap<String, Object>>) subsidiary.getResult();
                    Integer m = 0;
                    for (HashMap<String, Object> objectHashMap : result) {
                        m = companyMap.size();
                        Integer subsidiaryId = (Integer) objectHashMap.get("subsidiaryId");
                        HashMap<Long, Long> mapT = new HashMap<>();
                        mapT.put(2l, subsidiaryId.longValue());
                        companyMap.put(m, mapT);
                        //查询子公司下的分公司
                        ResponseResult store = storeApi.selectStoreListBySubCompanyNoPage(subsidiaryId.longValue());
                        List<HashMap<String, Object>> result1 = (List<HashMap<String, Object>>) store.getResult();
                        if (result1 == null || result1.size() == 0) {
                            continue;
                        }
                        for (HashMap<String, Object> hashMap : result1) {
                            m = companyMap.size();
                            Integer storeId = (Integer) hashMap.get("storeId");
                            HashMap<Long, Long> mapT1 = new HashMap<>();
                            mapT1.put(3l, storeId.longValue());
                            companyMap.put(m, mapT1);
                        }
                    }
                    if (companyMap != null && companyMap.size() > 0) {
                        //根据公司的map查询仓库
                        for (Integer integer : companyMap.keySet()) {
                            HashMap<Long, Long> longLongHashMap = companyMap.get(integer);
                            for (Long aLong : longLongHashMap.keySet()) {
                                Stock stockT = new Stock();
                                stockT.setCompanyType(aLong);
                                stockT.setCompanyId(longLongHashMap.get(aLong));
                                List<Stock> stocks = stockDao.selectStockByID(stockT);
                                if (stocks == null || stocks.size() == 0) {
                                    continue;
                                }
                                stockList.addAll(stocks);
                            }
                        }
                    }
                } else if (companyType == 2) {   //子公司
                    //查询子公司下的分公司
                    ResponseResult store = storeApi.selectStoreListBySubCompanyNoPage(companyId);
                    List<HashMap<String, Object>> result1 = (List<HashMap<String, Object>>) store.getResult();
                    if (result1 != null && result1.size() == 0) {
                        for (HashMap<String, Object> hashMap : result1) {
                            Integer storeId = (Integer) hashMap.get("storeId");
                            Stock stockT = new Stock();
                            stockT.setCompanyType(2l);
                            stockT.setCompanyId(storeId.longValue());
                            List<Stock> stocks = stockDao.selectStockByID(stockT);
                            if (stocks == null || stocks.size() == 0) {
                                continue;
                            }
                            stockList.addAll(stocks);
                        }
                    }
                } else if (companyType == 3) {     //分公司
                    //没有下级
                }
            }
        }
        if (stockList != null && stockList.size() > 0) {
            return ResponseResult.success(stockList);
        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_FIND.getCode(),
                ResponseCodeStockEnum.STOCK_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult auditInstorage(Long inStorageId, String auditor, String inStorageStatus, String remark, String invalid) {
        //处理商品的库存减少
        //查询当前入库批号
        Instorage instorage = instorageDao.selectInstorageByID(inStorageId);
        String batchNumber = instorage.getBatchNumber();
        //查询当前库存是否被出库
        List<OutStorageProduct> outStorageProductList = outstorageDao.selectOutStorageProductByBatchNumber(batchNumber);
        if (outStorageProductList != null && outStorageProductList.size() > 0) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGE_PRODUCT_OUTSTORAGE.getCode(),
                    ResponseCodeStockEnum.INSTORAGE_PRODUCT_OUTSTORAGE.getDesc()));
        }
        String modify = "";
        if (!StringUtils.isBlank(invalid)) {
            //作废
            modify = invalid;
            //将批号的产品即时库存删除
            StockProduct stockProduct = new StockProduct();
            stockProduct.setBatchNumber(batchNumber);
            stockProduct.setModifyOperator(modify);
            stockProductDao.deleteStockProductByID(stockProduct);
            //删除入库商品单据
            InStorageProduct inStorageProduct = new InStorageProduct();
            inStorageProduct.setInStorageId(inStorageId);
            inStorageProduct.setModifyOperator(modify);
            instorageDao.deleteInStorageProductByInStorageID(inStorageProduct);
            //更新入库单状态
            Instorage instorageTemp = new Instorage();
            instorageTemp.setInStorageId(inStorageId);
            instorageTemp.setRemark(remark);
            instorageTemp.setInvalid(invalid);
            instorageTemp.setInvalidTime(new Date());
            instorageTemp.setInvalidStatus(0);
            instorageDao.updateInstorageByID(instorageTemp);
            //todo 作废k3单据
            if (instorage.getInStorageType().equals(InStorageTypeEnum.OTHER_INSTORAGE.getDesc())) {
                OtherInstorageVO vo = new OtherInstorageVO();
                vo.setFID(instorage.getK3Id());
                vo.setFBillNo(instorage.getK3Code());
                //用户名密码
                HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
                String dataCentre = userNameAndPassword.get("dataCentre");
                String userName = userNameAndPassword.get("userName");
                String password = userNameAndPassword.get("password");
                vo.setAcctId(dataCentre);
                vo.setDataCentreUserName(userName);
                vo.setDataCentrePassword(password);
                /*sendToK3OtherInstorage("invalid", inStorageId, vo);*/
            } else if (instorage.getInStorageType().equals(InStorageTypeEnum.PURCHASE_INSTORAGE.getDesc())) {
                PurchaseInstorageVO vo = new PurchaseInstorageVO();
                vo.setFID(instorage.getK3Id());
                vo.setFBillNo(instorage.getK3Code());
                //用户名密码
                HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
                String dataCentre = userNameAndPassword.get("dataCentre");
                String userName = userNameAndPassword.get("userName");
                String password = userNameAndPassword.get("password");
                vo.setAcctId(dataCentre);
                vo.setDataCentreUserName(userName);
                vo.setDataCentrePassword(password);
                /*sendToK3PurchaseInstorage("invalid", inStorageId, vo);*/
            }
            return ResponseResult.success(instorageTemp);
        }
        if (inStorageStatus.equals(2)) {
            //审核不通过
            modify = auditor;
            //将批号的产品即时库存删除
            StockProduct stockProduct = new StockProduct();
            stockProduct.setBatchNumber(batchNumber);
            stockProduct.setModifyOperator(modify);
            stockProductDao.deleteStockProductByID(stockProduct);
            //删除入库商品单据
            InStorageProduct inStorageProduct = new InStorageProduct();
            inStorageProduct.setInStorageId(inStorageId);
            inStorageProduct.setModifyOperator(modify);
            instorageDao.deleteInStorageProductByInStorageID(inStorageProduct);
        }
        //更新入库单状态
        Instorage instorageTemp = new Instorage();
        instorageTemp.setInStorageId(inStorageId);
        instorageTemp.setAuditor(auditor);
        instorageTemp.setAuditorTime(new Date());
        instorageTemp.setInStorageStatus(inStorageStatus);
        instorageTemp.setRemark(remark);
        instorageDao.updateInstorageByID(instorageTemp);
        //todo 审核k3单据
        if (instorage.getInStorageType().equals(InStorageTypeEnum.OTHER_INSTORAGE.getDesc())) {
            OtherInstorageVO vo = new OtherInstorageVO();
            vo.setFID(instorage.getK3Id());
            vo.setFBillNo(instorage.getK3Code());
            //用户名密码
            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");
            vo.setAcctId(dataCentre);
            vo.setDataCentreUserName(userName);
            vo.setDataCentrePassword(password);
         /*   if (inStorageStatus.equals(2)) {
                sendToK3OtherInstorage("unAudit", inStorageId, vo);
            } else {
                sendToK3OtherInstorage("audit", inStorageId, vo);
            }*/
        } else if (instorage.getInStorageType().equals(InStorageTypeEnum.PURCHASE_INSTORAGE.getDesc())) {
            PurchaseInstorageVO vo = new PurchaseInstorageVO();
            vo.setFID(instorage.getK3Id());
            vo.setFBillNo(instorage.getK3Code());
            //用户名密码
            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");
            vo.setAcctId(dataCentre);
            vo.setDataCentreUserName(userName);
            vo.setDataCentrePassword(password);
            if (inStorageStatus.equals(2)) {
                /*sendToK3PurchaseInstorage("unAudit", inStorageId, vo);*/
            } else {
                /*sendToK3PurchaseInstorage("audit", inStorageId, vo);*/
            }
        }

        return ResponseResult.success(instorageTemp);

//        return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGE_AUDITOR_FAILED.getCode(),
//                ResponseCodeStockEnum.INSTORAGE_AUDITOR_FAILED.getDesc()));
    }

    @Override
    public ResponseResult auditOutstorage(Long outStorageId, String auditor, Integer outstorageStatus, String remark, String inventoryWay) {


        OutstoragePreAudit outstoragePreAudit = new OutstoragePreAudit();
        outstoragePreAudit.setOutStorageId(outStorageId);
        outstoragePreAudit.setAuditor(auditor);
        outstoragePreAudit.setOutStorageStatus(outstorageStatus);
        outstoragePreAudit.setAuditorTime(new Date());
        outstoragePreAudit.setRemark(remark);
        outstorageDao.updateOutstoragePreAuditById(outstoragePreAudit);


        OutstoragePreAudit outstoragePreAuditResult = outstorageDao.selectOutstoragePreAuditById(outstoragePreAudit);
        //找到出库id为当前审核出库单id 的入库单
        InstoragePreAudit intstoragePreAudit = new InstoragePreAudit();
        //查询预出库商品
        List<OutstoragePreAuditProduct> outstoragePreAuditProductList = outstorageDao.selectPreOutstorageProductByPreAuditId(outstoragePreAuditResult.getOutStorageId());

        outstoragePreAuditResult.setOutStorageProductJson(JSONArray.parseArray(JSON.toJSONString(outstoragePreAuditProductList)).toJSONString());
        intstoragePreAudit.setOutStorageId(outstoragePreAuditResult.getOutStorageId());
        InstoragePreAudit instoragePreAuditResult = instorageDao.selectInstoragePreAuditById(intstoragePreAudit);
        Long inStorageId = instoragePreAuditResult.getInStorageId();

        //查询出预入库商品
        List<InstoragePreAuditProduct> instoragePreAuditProductList = instorageDao.selectPreInstorageProductByPreAuditId(instoragePreAuditResult.getInStorageId());
        instoragePreAuditResult.setInStorageProductJson(JSONArray.parseArray(JSON.toJSONString(instoragePreAuditProductList)).toJSONString());

        ResponseResult responseResult = null;
        if (inventoryWay.equals("RETURN")) {
            if (outstoragePreAuditResult.getOutStorageType().equals("销售出库")){
                outstoragePreAuditResult.setShipperType("业务组织");
                outstoragePreAuditResult.setShipper(outstoragePreAuditResult.getOrgK3Number());
            }
            responseResult = this.outstorageReturn(outstoragePreAuditResult);
        } else if (inventoryWay.equals("GENERAL")) {
            responseResult = this.outstorage(outstoragePreAuditResult);
        }


        if (responseResult.isSuccess()) {
            OutstoragePreAudit outstoragePreAuditUpdate = new OutstoragePreAudit();
            outstoragePreAuditUpdate.setOutStorageId(outStorageId);
            outstoragePreAuditUpdate.setAfterAuditOutStorageId(Long.parseLong(responseResult.getResult().toString()));
            outstorageDao.updateOutstoragePreAuditById(outstoragePreAuditUpdate);
        }
        ResponseResult responseResult2 = null;
        if (inventoryWay.equals("RETURN")) {
            responseResult2 = this.instorageReturn(instoragePreAuditResult);
        } else if (inventoryWay.equals("GENERAL")) {
            responseResult2 = this.instorage(instoragePreAuditResult);
        }


        if (responseResult2.isSuccess()) {
            InstoragePreAudit instoragePreAuditUpdate = new InstoragePreAudit();
            instoragePreAuditUpdate.setInStorageId(inStorageId);
            instoragePreAuditUpdate.setAfterAuditInStorageId(Long.parseLong(responseResult2.getResult().toString()));
            instorageDao.updateInstoragePreAuditById(instoragePreAuditUpdate);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateStockById(Stock stock) {
        Long aLong = stock.getStockId();
        //修改到数据库
        stockDao.updateStockByID(stock);
        //todo 保存数据到k3
        //查询数据库
        Stock stock1 = new Stock();
        stock1.setStockId(aLong);
        List<Stock> stockList = stockDao.selectStockByID(stock1);
        StockVO vo = new StockVO();
        vo.setFName(stockList.get(0).getCompany());
        vo.setFNumberCreateOrg(String.valueOf(stock.getCompanyId()));
        vo.setFNumerUseOrg(String.valueOf(stockList.get(0).getCompanyId()));
        vo.setFStockProperty(stock.getStockType());
        vo.setFStockStatusType("1");
        vo.setFStockId(stockList.get(0).getId());
        vo.setFNumber(stockList.get(0).getStockCode());
        //用户名密码
        HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
        String dataCentre = userNameAndPassword.get("dataCentre");
        String userName = userNameAndPassword.get("userName");
        String password = userNameAndPassword.get("password");
        vo.setAcctId(dataCentre);
        vo.setDataCentreUserName(userName);
        vo.setDataCentrePassword(password);
        sendToK3Stock("update", stock.getStockId(), vo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult addStock(Stock stock, String acctId, String yewuDataCentreUserName, String yewuDataCentrePassword) {
        //判断仓库是否存在
        Stock stockSelect = new Stock();
        stockSelect.setCompanyType(stock.getCompanyType());
        stockSelect.setCompanyId(stock.getCompanyId());
        List<Stock> stockList = stockDao.selectStockByID(stockSelect);
        if (stockList.size() != 0) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_EXIST.getCode(),
                    ResponseCodeStockEnum.STOCK_EXIST.getDesc()));
        }
        //仓库状态
//        stock.setDataStatus("0");//待审核
        //生成仓库编码
        String stockCode = stockDao.selectLastStockCode();
        if (stockCode == null) {
            stock.setStockCode("CK" + "001");
        } else {
            String substring = stockCode.substring(2, 5);
            int i = Integer.parseInt(substring);
            stock.setStockCode("CK" + String.format("%3d", i + 1).replace(" ", "0"));
        }
        //审核
        stock.setAuditor(stock.getCreateOperator());
        stock.setAuditorTime(new Date());
        stock.setDataStatus("1");
        stockDao.insertStock(stock);
        //todo 仓库保存到k3
        StockVO vo = new StockVO();
        vo.setFName(stock.getCompany());
        vo.setFNumberCreateOrg(String.valueOf(stock.getCompanyId()));
        vo.setFNumerUseOrg(String.valueOf(stock.getCompanyId()));
        vo.setFStockProperty(stock.getStockType());
        vo.setFStockStatusType("0,1,2,3,4,5,6,7,8");
        vo.setK3Number(stock.getK3Number());
        vo.setFNumber(stock.getStockCode());

        vo.setAcctId(acctId);
        vo.setDataCentreUserName(yewuDataCentreUserName);
        vo.setDataCentrePassword(yewuDataCentrePassword);
        String resutlStr = sendToK3Stock("add", stock.getStockId(), vo).getResult().toString();
        if (resutlStr.equals("仓库生成成功，k3保存成功")) {
            return ResponseResult.success("1");
        } else {
            return ResponseResult.success("0");
        }

    }

    @Override
    public ResponseResult selectStockProductListByProductName(int pageNum, int pageSize, String productName) {
        PageHelper.startPage(pageNum, pageSize);
        List<StockProduct> stockProductList = stockProductDao.selectStockProductListByProductName(productName);
        if (stockProductList != null && stockProductList.size() > 0) {
            PageInfo<StockProduct> pageInfo = new PageInfo<>(stockProductList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_FIND.getCode(),
                ResponseCodeStockEnum.STOCKPRODUCT_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult confirmInstorage(Long inStorageId, String confirm, Integer confirmStatus, String remark) {
        InstoragePreAudit instoragePreAudit = new InstoragePreAudit();
        instoragePreAudit.setInStorageId(inStorageId);
        instoragePreAudit.setConfirm(confirm);
        instoragePreAudit.setConfirmStatus(confirmStatus);
        instoragePreAudit.setConfirmTime(new Date());
        instoragePreAudit.setRemark(remark);
        instorageDao.updateInstoragePreAuditById(instoragePreAudit);

        //找到入库id为当前确认收货的入库单的id的出库单

        InstoragePreAudit instoragePreAuditResult = instorageDao.selectInstoragePreAuditById(instoragePreAudit);
        //查询出预入库商品
        List<InstoragePreAuditProduct> instoragePreAuditProductList = instorageDao.selectPreInstorageProductByPreAuditId(instoragePreAuditResult.getInStorageId());
        instoragePreAuditResult.setInStorageProductJson(JSONArray.parseArray(JSON.toJSONString(instoragePreAuditProductList)).toJSONString());
        OutstoragePreAudit outstoragePreAudit = new OutstoragePreAudit();
        outstoragePreAudit.setInStorageId(instoragePreAuditResult.getInStorageId());
        OutstoragePreAudit outstoragePreAuditResult = outstorageDao.selectOutstoragePreAuditById(outstoragePreAudit);

        //查询预出库商品
        List<OutstoragePreAuditProduct> outstoragePreAuditProductList = outstorageDao.selectPreOutstorageProductByPreAuditId(outstoragePreAuditResult.getOutStorageId());
        outstoragePreAuditResult.setOutStorageProductJson(JSONArray.parseArray(JSON.toJSONString(outstoragePreAuditProductList)).toJSONString());

        ResponseResult responseResult = this.outstorage(outstoragePreAuditResult);
        if (responseResult.isSuccess()) {
            OutstoragePreAudit outstoragePreAuditUpdate = new OutstoragePreAudit();
            outstoragePreAuditUpdate.setOutStorageId(outstoragePreAuditResult.getOutStorageId());
            outstoragePreAuditUpdate.setAfterAuditOutStorageId(Long.parseLong(responseResult.getResult().toString()));
            outstorageDao.updateOutstoragePreAuditById(outstoragePreAuditUpdate);
        }
        ResponseResult responseResult2 = this.instorage(instoragePreAuditResult);
        if (responseResult2.isSuccess()) {
            InstoragePreAudit instoragePreAuditUpdate = new InstoragePreAudit();
            instoragePreAuditUpdate.setInStorageId(inStorageId);
            instoragePreAuditUpdate.setAfterAuditInStorageId(Long.parseLong(responseResult2.getResult().toString()));
            instorageDao.updateInstoragePreAuditById(instoragePreAuditUpdate);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult checkProductStockNumbers(String stockCode, String productCode, Integer numbers) {
        //出库商品判断
        //1.查询仓库商品（仓库和商品）
        Map<String, Object> map = new HashMap<>();
        map.put("stock", stockCode);
        map.put("productCode", productCode);
        List<StockProduct> stockProductList = stockProductDao.selectStockProductByStockIDAndProductID(map);
        if (stockProductList == null || stockProductList.size() <= 0 || stockProductList.get(0)==null) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT.getCode(),
                    ResponseCodeStockEnum.STOCKPRODUCT_NOT.getDesc()));
        }
        Integer stockNumber = 0;
        for (StockProduct stockProduct : stockProductList) {
            stockNumber += stockProduct.getAveailableNumber();
        }
        if (numbers != null) {
            if (numbers.compareTo(stockNumber) > 0) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                        ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc() + "," + "商品库存为：" + stockNumber));
            }
        }
        return ResponseResult.success(stockNumber);
    }

    @Override
    public ResponseResult addStockStatus(StockStatus stockStatus) {
        //添加到数据库
//        stockStatus.setDataStatus("0"); //待审核
        stockStatus.setIsUse(true); //启用仓库状态
        if (StringUtils.isBlank(stockStatus.getAuditor())) {
            stockStatus.setAuditor(stockStatus.getCreateOperator());
        }
        stockStatus.setDataStatus("1");
        stockStatus.setAuditorTime(new Date());
        //保存到数据库
        stockStatusDao.insertStockStatus(stockStatus);
        //todo 保存到k3
        StockStatusVO vo = new StockStatusVO();
        vo.setFName(stockStatus.getName());
        vo.setFNumberCreateOrg("100");
        vo.setFNumberUseOrg("100");
        vo.setFType("1");
        //用户名密码
        HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
        String dataCentre = userNameAndPassword.get("dataCentre");
        String userName = userNameAndPassword.get("userName");
        String password = userNameAndPassword.get("password");
        vo.setAcctId(dataCentre);
        vo.setDataCentreUserName(userName);
        vo.setDataCentrePassword(password);
        sendToK3StockStatus("add", stockStatus.getStockStatusID(), vo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteStockStatus(String id, String modifyOperator) {
        Long aLong = Long.parseLong(id);
        //查询数据
        StockStatus stockStatusS = stockStatusDao.selectStockStatusByID(Long.valueOf(id));
        //删除数据库数据
        StockStatus stockStatus = new StockStatus();
        stockStatus.setStockStatusID(aLong);
        stockStatus.setModifyOperator(modifyOperator);
        stockStatusDao.deleteStockStatusByID(stockStatus);
        // todo 删除k3
        StockStatusVO vo = new StockStatusVO();
        vo.setFNumber(stockStatusS.getCode());
        vo.setFStockStatusId(stockStatusS.getId());
        //用户名密码
        HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
        String dataCentre = userNameAndPassword.get("dataCentre");
        String userName = userNameAndPassword.get("userName");
        String password = userNameAndPassword.get("password");
        vo.setAcctId(dataCentre);
        vo.setDataCentreUserName(userName);
        vo.setDataCentrePassword(password);
        sendToK3StockStatus("delete", aLong, vo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult queryStockStatus(String id, String code) {
        if (StringUtils.isBlank(id) && StringUtils.isBlank(code)) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.VIEW_FAILED.getCode(),
                    ResponseCodeStockEnum.VIEW_FAILED.getDesc()));
        }
        return ResponseResult.success();
        /*return k3CLOUDApi.viewStockStatus(code, id);*/
    }

    @Override
    public ResponseResult editStockStatusById(StockStatus stockStatus) {
        //修改到数据库
        //stockStatus.setCode(number);
        //查询数据库
        Long aLong = stockStatus.getStockStatusID();
        StockStatus stockStatusS = stockStatusDao.selectStockStatusByID(aLong);
        stockStatusDao.updateStockStatusByID(stockStatus);
        //todo 调用k3
        StockStatusVO vo = new StockStatusVO();
        vo.setFName(stockStatus.getName());
        vo.setFNumberCreateOrg("100");
        vo.setFType("1");
        vo.setFNumber(stockStatusS.getCode());
        vo.setFStockStatusId(stockStatusS.getId());
        //用户名密码
        HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
        String dataCentre = userNameAndPassword.get("dataCentre");
        String userName = userNameAndPassword.get("userName");
        String password = userNameAndPassword.get("password");
        vo.setAcctId(dataCentre);
        vo.setDataCentreUserName(userName);
        vo.setDataCentrePassword(password);
        sendToK3StockStatus("update", aLong, vo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult delStockById(Long companyId, Long companyType) {
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("companyType", companyType);
        //查询数据
        Stock stock = new Stock();
        stock.setCompanyId(companyId);
        stock.setCompanyType(companyType);
        List<Stock> stocks = stockDao.selectStockByID(stock);
        stockDao.delStockById(map);
        // todo 删除k3
        StockVO vo = new StockVO();
        vo.setFNumber(stocks.get(0).getStockCode());
        vo.setFStockId(stocks.get(0).getId());
        //用户名密码
        HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
        String dataCentre = userNameAndPassword.get("dataCentre");
        String userName = userNameAndPassword.get("userName");
        String password = userNameAndPassword.get("password");
        vo.setAcctId(dataCentre);
        vo.setDataCentreUserName(userName);
        vo.setDataCentrePassword(password);
        sendToK3Stock("delete", stocks.get(0).getStockId(), vo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectProductNumberList(int pageNum, int pageSize, String stock) {
        PageHelper.startPage(pageNum, pageSize);
        Map map1 = new HashMap();
        map1.put("stock", stock);
        List<ProductNumber> productNumberList = productNumberDao.selectProductNumberListByProduct(map1);
        if (productNumberList != null && productNumberList.size() > 0) {
            //下级的数量：下级仓库 仓库商品数量
            Stock stock1 = new Stock();
            stock1.setStockCode(stock);
            List<Stock> stockList = stockDao.selectStockByID(stock1);
            //门店类型 门店id
            Long companyType = stockList.get(0).getCompanyType();
            Long companyId = stockList.get(0).getCompanyId();
            //下级stock
            HashMap<String, String> mapStoreStock = new HashMap();
            if (companyType == 1) {
                //子公司和门店
                ResponseResult responseResult = storeApi.selectSubCompanyAndStoreNoPage(companyId, companyType);
                if (responseResult.isSuccess()) {
                    List<HashMap<String, Object>> mapList = (List<HashMap<String, Object>>) responseResult.getResult();
                    for (HashMap<String, Object> hashMap : mapList) {
                        //子公司
                        Integer subsidiaryId = (Integer) hashMap.get("subsidiaryId");
                        Stock stock2 = new Stock();
                        stock2.setCompanyId(subsidiaryId.longValue());
                        stock2.setCompanyType(2l);
                        //门店的总数量
                        List<Stock> stocks = stockDao.selectStockByID(stock2);
                        if (stocks != null && stocks.size() > 0) {
                            mapStoreStock.put("2_" + companyId, stocks.get(0).getStockCode());
                        }
                        //门店
                        List<HashMap<String, Object>> mapList1 = (List<HashMap<String, Object>>) hashMap.get("storeVOList");
                        for (HashMap<String, Object> objectHashMap : mapList1) {
                            Integer storeId = (Integer) objectHashMap.get("storeId");
                            Stock stock3 = new Stock();
                            stock3.setCompanyId(storeId.longValue());
                            stock3.setCompanyType(3l);
                            List<Stock> stocks1 = stockDao.selectStockByID(stock3);
                            if (stocks1 != null && stocks1.size() > 0) {
                                mapStoreStock.put("3_" + storeId, stocks1.get(0).getStockCode());
                            }
                        }
                    }
                }
            }
            if (companyType == 2) {
                //门店
                ResponseResult responseResult = storeApi.selectStoreListBySubCompanyNoPage(companyId);
                if (responseResult.isSuccess()) {
                    List<HashMap<String, Object>> mapList = (List<HashMap<String, Object>>) responseResult.getResult();
                    Integer allStoreNumber = 0;
                    for (HashMap<String, Object> objectHashMap : mapList) {
                        Integer storeId = (Integer) objectHashMap.get("storeId");
                        Stock stock2 = new Stock();
                        stock2.setCompanyId(storeId.longValue());
                        stock2.setCompanyType(3l);
                        List<Stock> stocks = stockDao.selectStockByID(stock2);
                        if (stocks != null && stocks.size() > 0) {
                            mapStoreStock.put("3_" + storeId, stocks.get(0).getStockCode());
                        }
                    }
                }
            }
            List<Unit> unitList = unitDao.selectUnitList(new HashMap());
            for (ProductNumber productNumber1 : productNumberList) {
                String productCode = productNumber1.getProductCode();
                String productName = productNumber1.getProductName();
                String unit1 = productNumber1.getUnit();
                // 出库单/入库单
                Map map = new HashMap();
                map.put("stock", stock);
                map.put("productCode", productCode);
                List<InStorageProduct> inStorageProductList = instorageDao.selectInStorageProductListByStockIDAndProductID(map);
                List<OutStorageProduct> outStorageProductList = outstorageDao.selectOutStorageProductListByStockIDAndProductID(map);
                List<StockProduct> stockProductList = stockProductDao.selectStockProductByStockIDAndProductID(map);
                //商品入库单
                List<Instorage> instorageList = new ArrayList<>();
                for (InStorageProduct inStorageProduct : inStorageProductList) {
                    Long inStorageId = inStorageProduct.getInStorageId();
                    Instorage instorage = instorageDao.selectInstorageByID(inStorageId);
                    instorageList.add(instorage);
                }
                productNumber1.setInstorageList(instorageList);
                //商品出库单
                List<Outstorage> outstorageList = new ArrayList<>();
                for (OutStorageProduct ou : outStorageProductList) {
                    Long ouInStorageId = ou.getOutStorageId();
                    Outstorage outstorage = outstorageDao.selectOutstorageByID(ouInStorageId);
                    outstorageList.add(outstorage);
                }
                productNumber1.setOutstorageList(outstorageList);
                //库存变化
                productNumber1.setStockProductList(stockProductList);
                //单位
                for (Unit unit : unitList) {
                    if (unit.getUnitId().toString().equals(unit1)) {
                        productNumber1.setUnitName(unit.getUnitName());
                    }
                }
                //门店商品总数量
                Integer allStoreNumber = 0;
                for (String company : mapStoreStock.keySet()) {
                    String stockCode = mapStoreStock.get(company);
                    ResponseResult responseResult1 = checkProductStockNumbers(stockCode, productCode, null);
                    if (responseResult1.isSuccess()) {
                        Integer result = (Integer) responseResult1.getResult();
                        allStoreNumber = allStoreNumber + result;
                        ProductNumberStockVO productNumberStockVO = new ProductNumberStockVO();
                        productNumberStockVO.setStock(stockCode);
                        productNumberStockVO.setProductCode(productNumber1.getProductType());
                        productNumberStockVO.setProductCode(productCode);
                        productNumberStockVO.setProductName(productName);
                        productNumberStockVO.setUnit(unit1);
                        productNumberStockVO.setUnitName(productNumber1.getUnitName());
                        productNumberStockVO.setTotalNumber(result);
                        // 出库单/入库单
                        Map mapT = new HashMap();
                        mapT.put("stock", stockCode);
                        mapT.put("productCode", productCode);
                        List<InStorageProduct> inStorageProductList1 = instorageDao.selectInStorageProductListByStockIDAndProductID(mapT);
                        List<OutStorageProduct> outStorageProductList1 = outstorageDao.selectOutStorageProductListByStockIDAndProductID(mapT);
                        //商品入库单
                        List<Instorage> instorageList1 = new ArrayList<>();
                        for (InStorageProduct inStorageProduct : inStorageProductList1) {
                            Long inStorageId = inStorageProduct.getInStorageId();
                            Instorage instorage = instorageDao.selectInstorageByID(inStorageId);
                            instorageList1.add(instorage);
                        }
                        productNumberStockVO.setInstorageList(instorageList);
                        //商品出库单
                        List<Outstorage> outstorageList1 = new ArrayList<>();
                        for (OutStorageProduct ou : outStorageProductList1) {
                            Long ouInStorageId = ou.getOutStorageId();
                            Outstorage outstorage = outstorageDao.selectOutstorageByID(ouInStorageId);
                            outstorageList1.add(outstorage);
                        }
                        productNumberStockVO.setOutstorageList(outstorageList);
                        //库存变化
                        List<StockProduct> stockProductList1 = stockProductDao.selectStockProductByStockIDAndProductID(mapT);
                        productNumberStockVO.setStockProductList(stockProductList1);
                    }
                }
                productNumber1.setAllStoreNumber(allStoreNumber);
            }

            List<ProductNumber> productNumberListResult = new ArrayList<>();
            for (ProductNumber productNumber : productNumberList) {
                if (productNumber.getStock().equals(stock)) {
                    productNumberListResult.add(productNumber);
                }
            }
            PageInfo<ProductNumber> pageInfo = new PageInfo<>(productNumberListResult);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCT_NUMBER_NOT_FIND.getCode(),
                ResponseCodeStockEnum.PRODUCT_NUMBER_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectProductNumber(String stock, String productCode) {
        ProductNumberVO productNumberVO = new ProductNumberVO();
        productNumberVO.setStock(stock);
        productNumberVO.setProductCode(productCode);
        //查询商品库存数量
        ResponseResult responseResult = checkProductStockNumbers(stock, productCode, null);
        Integer result = (Integer) responseResult.getResult();
        productNumberVO.setTotalNumber(result);
        Map map = new HashMap();
        map.put("stock", stock);
        map.put("productCode", productCode);
        //查询商品入库
        List<InStorageProduct> inStorageProductList = instorageDao.selectInStorageProductListByStockIDAndProductID(map);
        productNumberVO.setInStorageProductList(inStorageProductList);
        //查询商品出库
        List<OutStorageProduct> outStorageProductList = outstorageDao.selectOutStorageProductListByStockIDAndProductID(map);
        productNumberVO.setOutStorageProductList(outStorageProductList);
        //查询商品即使库存
        List<StockProduct> list = stockProductDao.selectStockProductByStockIDAndProductID(map);
        productNumberVO.setStockProductList(list);
        //商品入库单
        List<Instorage> instorageList = new ArrayList<>();
        for (InStorageProduct inStorageProduct : inStorageProductList) {
            Long inStorageId = inStorageProduct.getInStorageId();
            Instorage instorage = instorageDao.selectInstorageByID(inStorageId);
            instorageList.add(instorage);
        }
        productNumberVO.setInstorageList(instorageList);
        //商品出库单
        List<Outstorage> outstorageList = new ArrayList<>();
        for (OutStorageProduct ou : outStorageProductList) {
            Long ouInStorageId = ou.getOutStorageId();
            Outstorage outstorage = outstorageDao.selectOutstorageByID(ouInStorageId);
            outstorageList.add(outstorage);
        }
        productNumberVO.setOutstorageList(outstorageList);
        //商品的入库出库单据
        List<ProductStorageVO> productStorageVOList = new ArrayList<>();
        for (Instorage instorage : instorageList) {
            ProductStorageVO productStorageVO = new ProductStorageVO();
            productStorageVO.setType("入库单");
            productStorageVO.setStorageType(instorage.getInStorageType());
            productStorageVO.setStorageNumber(instorage.getInStorageNumber());
            productStorageVO.setBatchNumber(instorage.getBatchNumber());
            productStorageVO.setStorageDate(instorage.getInStorageDate());
            productStorageVO.setOperator(instorage.getCreateOperator());
            productStorageVOList.add(productStorageVO);
        }
        for (Outstorage outstorage : outstorageList) {
            ProductStorageVO productStorageVO = new ProductStorageVO();
            productStorageVO.setType("出库单");
            productStorageVO.setStorageType(outstorage.getOutStorageType());
            productStorageVO.setStorageNumber(outstorage.getOutStorageNumber());
            productStorageVO.setBatchNumber(outstorage.getBatchNumber());
            productStorageVO.setStorageDate(outstorage.getOutStorageDate());
            productStorageVO.setOperator(outstorage.getCreateOperator());
            productStorageVOList.add(productStorageVO);
        }
        productNumberVO.setProductStorageVOList(productStorageVOList);
        //商品的入库出库商品
        List<ProductStorageProductVO> productStorageProductVOList = new ArrayList<>();
        for (InStorageProduct inStorageProduct : inStorageProductList) {
            ProductStorageProductVO productStorageProductVO = new ProductStorageProductVO();
            productStorageProductVO.setType("入库单");
            productStorageProductVO.setStorageProductId(inStorageProduct.getInStorageProductID());
            productStorageProductVO.setBatchNumber(inStorageProduct.getBatchNumber());
            productStorageProductVO.setProductCode(inStorageProduct.getProductCode());
            productStorageProductVO.setProductName(inStorageProduct.getProductName());
            productStorageProductVO.setNumber(inStorageProduct.getReceivedNumber());
            productStorageProductVO.setUnit(inStorageProduct.getUnit());
            productStorageProductVO.setStock(inStorageProduct.getStock());
            productStorageProductVOList.add(productStorageProductVO);
        }
        for (OutStorageProduct outStorageProduct : outStorageProductList) {
            ProductStorageProductVO productStorageProductVO = new ProductStorageProductVO();
            productStorageProductVO.setType("出库单");
            productStorageProductVO.setStorageProductId(outStorageProduct.getOutStorageProductID());
            productStorageProductVO.setBatchNumber(outStorageProduct.getBatchNumber());
            productStorageProductVO.setProductCode(outStorageProduct.getProductCode());
            productStorageProductVO.setProductName(outStorageProduct.getProductName());
            productStorageProductVO.setNumber(outStorageProduct.getSendNumber());
            productStorageProductVO.setUnit(outStorageProduct.getUnit());
            productStorageProductVO.setStock(outStorageProduct.getStock());
            productStorageProductVOList.add(productStorageProductVO);
        }
        productNumberVO.setProductStorageProductVOList(productStorageProductVOList);
        return ResponseResult.success(productNumberVO);
    }

    @Override
    public ResponseResult checkOutstorageByCust(String custK3Number) {
        List<Outstorage> outstorageList = outstorageDao.selectOutstorageListByCust(custK3Number);
        if (outstorageList != null && outstorageList.size() > 0) {
            return ResponseResult.success(outstorageList);
        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.OUTSTORAGE_NOT_FIND.getCode(), ResponseCodeStockEnum.OUTSTORAGE_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult checkInstorageBySupplierCode(String supplierCode) {
        List<Instorage> instorageList = instorageDao.checkInstorageBySupplierCode(supplierCode);
        if (instorageList != null && instorageList.size() > 0) {
            return ResponseResult.success(instorageList);
        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGE_NOT_FIND.getCode(), ResponseCodeStockEnum.INSTORAGE_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult checkInstorageByDepartment(String departmentK3Number) {
        List<Instorage> instorageList = instorageDao.checkInstorageByDepartment(departmentK3Number);
        if (instorageList != null && instorageList.size() > 0) {
            return ResponseResult.success(instorageList);
        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGE_NOT_FIND.getCode(), ResponseCodeStockEnum.INSTORAGE_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectOutstorageProductById(Long outStorageId) {
        List<OutStorageProduct> outStorageProducts = outstorageDao.selectOutStorageProductByOutStorageID(outStorageId);
        return ResponseResult.success(outStorageProducts);
    }

    @Override
    public ResponseResult selectOutstorageById(Long outStorageId) {
        Outstorage outStorage = outstorageDao.selectOutstorageByID(outStorageId);
        return ResponseResult.success(outStorage);
    }

    //采购入库
    private ResponseResult sendToK3PurchaseInstorage(String type,
                                                     String acctId,
                                                     String dataCentreUserName,
                                                     String dataCentrePassword,
                                                     String orgK3Number,
                                                     String fPurchaseOrgId,
                                                     String fSupplierId,
                                                     String fDate,
                                                     String fSupplyAddress,
                                                     String fCONTACTNUMBER,
                                                     String jsonArrayProduct,
                                                     Long instorageId,
                                                     String stockId) {
        if ("add".equals(type)) { //添加
            ResponseResult save = k3CLOUDApi.savePurchaseInstorage(
                    acctId,
                    dataCentreUserName,
                    dataCentrePassword,
                    orgK3Number,
                    fPurchaseOrgId,
                    fSupplierId,
                    fDate,
                    fSupplyAddress,
                    fCONTACTNUMBER,
                    jsonArrayProduct);
            if (save.getResult().equals("暂未登录")) {
                //保存失败
                //提交操作s
                return ResponseResult.error(new Error(ResponseCodeStockEnum.LOGIN_ERROR.getCode(),
                        ResponseCodeStockEnum.LOGIN_ERROR.getDesc() + save.getResult()));
            }
            if (save.isSuccess()) {
                HashMap<String, Object> objectHashMap = (HashMap<String, Object>) save.getResult();
                HashMap<String, Object> saveresult = (HashMap<String, Object>) objectHashMap.get("Result");
                HashMap<String, Object> saveStatus = (HashMap<String, Object>) saveresult.get("ResponseStatus");
                Boolean isSaveSuccess = (Boolean) saveStatus.get("IsSuccess");
                if (isSaveSuccess) {
                    //获取保存成功后的number和id
                    Integer id = (Integer) saveresult.get("Id");
                    String number = (String) saveresult.get("Number");
                    //更新id和number到数据库
                    Instorage instorage1 = new Instorage();
                    instorage1.setK3Id(String.valueOf(id));
                    instorage1.setK3Code(number);
                    instorage1.setStockId(stockId);
                    instorage1.setInStorageId(instorageId);
                    instorageDao.updateInstorageByID(instorage1);
                    return ResponseResult.success();
                } else {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SAVE_FAILED.getCode(),
                            ResponseCodeStockEnum.SAVE_FAILED.getDesc()));
                }
            }
        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.TYPE_ERROR.getCode(),
                ResponseCodeStockEnum.TYPE_ERROR.getDesc()));
    }

    //其他入库
    private ResponseResult sendToK3OtherInstorage(String type,
                                                  String acctId,
                                                  String userName,
                                                  String password,
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
                                                  String fKEEPERID,
                                                  Long instorageId,
                                                  String stockId) {
        //登录的数据
        if ("add".equals(type)) { //添加
            ResponseResult save = k3CLOUDApi.saveOtherInstorage(
                    acctId,
                    userName,
                    password,
                    fBillTypeID,
                    fStockOrgId,
                    fStockDirect,
                    fDate,
                    fDEPTID,
                    fSUPPLIERID,
                    fOwnerTypeIdHead,
                    fOwnerIdHead,
                    fBaseCurrId,
                    jsonArrayProduct,
                    fUnitID,
                    fSTOCKID,
                    fSTOCKSTATUSID,
                    fQty,
                    fOWNERTYPEID,
                    fOWNERID,
                    fKEEPERTYPEID,
                    fKEEPERID);
            if (save.getResult().equals("暂未登录")) {
                //保存失败
                //提交操作
                return ResponseResult.error(new Error(ResponseCodeStockEnum.LOGIN_ERROR.getCode(),
                        ResponseCodeStockEnum.LOGIN_ERROR.getDesc() + save.getResult()));
            }
            if (save.isSuccess()) {
                HashMap<String, Object> objectHashMap = (HashMap<String, Object>) save.getResult();
                HashMap<String, Object> saveresult = (HashMap<String, Object>) objectHashMap.get("Result");
                HashMap<String, Object> saveStatus = (HashMap<String, Object>) saveresult.get("ResponseStatus");
                Boolean isSaveSuccess = (Boolean) saveStatus.get("IsSuccess");
                if (isSaveSuccess) {
                    //获取保存成功后的number和id
                    Integer id = (Integer) saveresult.get("Id");
                    String number = (String) saveresult.get("Number");
                    //更新id和number到数据库
                    Instorage instorage1 = new Instorage();
                    instorage1.setK3Id(String.valueOf(id));
                    instorage1.setK3Code(number);
                    instorage1.setStockId(stockId);
                    instorage1.setInStorageId(instorageId);
                    instorageDao.updateInstorageByID(instorage1);
                    return ResponseResult.success();
                } else {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SAVE_FAILED.getCode(),
                            ResponseCodeStockEnum.SAVE_FAILED.getDesc()));
                }
            }
        }

        return ResponseResult.error(new Error(ResponseCodeStockEnum.TYPE_ERROR.getCode(),
                ResponseCodeStockEnum.TYPE_ERROR.getDesc()));
    }

    //其他出库
    private ResponseResult sendToK3OtherOutstorage(String type, Long aLong, OtherOutstorageVO outstorage, String jsonArrayProduct) {
        //登录的数据
        String acctId = outstorage.getAcctId();
        String userName = outstorage.getDataCentreUserName();
        String password = outstorage.getDataCentrePassword();
        //保存的数据
        String fStockOrgId = outstorage.getFStockOrgId();
        String fDate = outstorage.getFDate();
        String fPickOrgId = outstorage.getFPickOrgId();
        String fCustId = outstorage.getFCustId();
        String fOwnerIdHead = outstorage.getFOwnerIdHead();
        if ("add".equals(type)) { //添加
            ResponseResult save = k3CLOUDApi.saveOtherOutstorage(
                    acctId,
                    userName,
                    password,
                    fStockOrgId,
                    fPickOrgId,
                    fDate,
                    fCustId,
                    fOwnerIdHead,
                    jsonArrayProduct,
                    outstorage.getFStockDirect());
            if (save.getResult().equals("暂未登录")) {
                //保存失败
                return ResponseResult.error(new Error(ResponseCodeStockEnum.LOGIN_ERROR.getCode(),
                        ResponseCodeStockEnum.LOGIN_ERROR.getDesc() + save.getResult()));
            }
            if (save.isSuccess()) {
                HashMap<String, Object> objectHashMap = (HashMap<String, Object>) save.getResult();
                HashMap<String, Object> saveresult = (HashMap<String, Object>) objectHashMap.get("Result");
                HashMap<String, Object> saveStatus = (HashMap<String, Object>) saveresult.get("ResponseStatus");
                Boolean isSaveSuccess = (Boolean) saveStatus.get("IsSuccess");
                if (isSaveSuccess) {
                    //获取保存成功后的number和id
                    Integer id = (Integer) saveresult.get("Id");
                    String number = (String) saveresult.get("Number");
                    //更新id和number到数据库
                    Outstorage outstorageUpdate = new Outstorage();
                    outstorageUpdate.setOutStorageId(aLong);
                    outstorageUpdate.setK3Id(String.valueOf(id));
                    outstorageUpdate.setStockId(outstorage.getStockId());
                    outstorageUpdate.setK3Code(number);
                    outstorageDao.updateOutstorageByID(outstorageUpdate);
                    return ResponseResult.success();

                } else {
                    //保存失败
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SAVE_FAILED.getCode(),
                            ResponseCodeStockEnum.SAVE_FAILED.getDesc()));
                }
            }
        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGETYPE_NOT_NULL.getCode(), ResponseCodeStockEnum.INSTORAGETYPE_NOT_NULL.getDesc()));
    }


    //销售出库
    private ResponseResult sendToK3MarketOutstorage(String type, Long outStorageId, MarketOutstorageVO outstorage, String jsonArrayProduct) {
        //登录的数据
        String acctId = outstorage.getAcctId();
        String userName = outstorage.getDataCentreUserName();
        String password = outstorage.getDataCentrePassword();
        //保存的数据
        String fNumberSaleOrg = outstorage.getFNumberSaleOrg();
        String fDate = outstorage.getFDate();
        String fNumberStockOrg = outstorage.getFNumberStockOrg();
        String fNumberCustomer = outstorage.getFNumberCustomer();
        String fNumberSettleOrg = outstorage.getFNumberSettleOrg();
        //保存的对象
        String saveJson = JSON.toJSONString(outstorage);
        if ("add".equals(type)) { //添加
            ResponseResult save = k3CLOUDApi.saveMarketOutstorage(
                    acctId,
                    userName,
                    password,
                    fNumberSaleOrg,
                    fDate,
                    fNumberStockOrg,
                    fNumberCustomer,
                    fNumberSettleOrg,
                    jsonArrayProduct);
            if (save.getResult().equals("暂未登录")) {
                //保存失败
                return ResponseResult.error(new Error(ResponseCodeStockEnum.LOGIN_ERROR.getCode(),
                        ResponseCodeStockEnum.LOGIN_ERROR.getDesc() + save.getResult()));
            }
            if (save.isSuccess()) {
                HashMap<String, Object> objectHashMap = (HashMap<String, Object>) save.getResult();
                HashMap<String, Object> saveresult = (HashMap<String, Object>) objectHashMap.get("Result");
                HashMap<String, Object> saveStatus = (HashMap<String, Object>) saveresult.get("ResponseStatus");
                Boolean isSaveSuccess = (Boolean) saveStatus.get("IsSuccess");
                if (isSaveSuccess) {
                    //获取保存成功后的number和id
                    Integer id = (Integer) saveresult.get("Id");
                    String number = (String) saveresult.get("Number");
                    HashMap<String, Object> submitHashMap = (HashMap<String, Object>) save.getResult();
                    HashMap<String, Object> submitResult = (HashMap<String, Object>) submitHashMap.get("Result");
                    HashMap<String, Object> submitStatus = (HashMap<String, Object>) submitResult.get("ResponseStatus");
                    Boolean isSubmitSuccess = (Boolean) submitStatus.get("IsSuccess");
                    if (isSubmitSuccess) {
                        //更新id和number到数据库
                        Outstorage outstorage1 = new Outstorage();
                        outstorage1.setOutStorageId(outStorageId);
                        outstorage1.setK3Id(String.valueOf(id));
                        outstorage1.setK3Code(number);
                        outstorage1.setStockId(outstorage.getStockId());
                        outstorageDao.updateOutstorageByID(outstorage1);
                        return ResponseResult.success();
                    }
                    //保存失败
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SAVE_FAILED.getCode(),
                            ResponseCodeStockEnum.SAVE_FAILED.getDesc() + submitResult));
                }
            }
        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.TYPE_ERROR.getCode(),
                ResponseCodeStockEnum.TYPE_ERROR.getDesc()));
    }

    //采购退料
    private ResponseResult sendToK3PurchaseReturn(String type, PurchaseReturnVO purchaseReturn) {
        //登录的数据
        String acctId = purchaseReturn.getAcctId();
        String userName = purchaseReturn.getDataCentreUserName();
        String password = purchaseReturn.getDataCentrePassword();

        //保存的数据
        String orgK3Number = purchaseReturn.getOrgK3Number();
        String fDate = purchaseReturn.getFDate();
        String supplierK3Number = purchaseReturn.getSupplierK3Number();
        String jsonArrayProduct = purchaseReturn.getJsonArrayProduct();

        if ("add".equals(type)) { //添加
            ResponseResult save = k3CLOUDApi.savePurchaseReturn(acctId,userName,password,orgK3Number,fDate,supplierK3Number,jsonArrayProduct);
            if (save.getResult().equals("暂未登录")) {
                //保存失败
                return ResponseResult.error(new Error(ResponseCodeStockEnum.LOGIN_ERROR.getCode(),
                        ResponseCodeStockEnum.LOGIN_ERROR.getDesc() + save.getResult()));
            }
            if (save.isSuccess()) {
                HashMap<String, Object> objectHashMap = (HashMap<String, Object>) save.getResult();
                HashMap<String, Object> saveresult = (HashMap<String, Object>) objectHashMap.get("Result");
                HashMap<String, Object> saveStatus = (HashMap<String, Object>) saveresult.get("ResponseStatus");
                Boolean isSaveSuccess = (Boolean) saveStatus.get("IsSuccess");
                if (!isSaveSuccess) {
                    //保存失败
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SAVE_FAILED.getCode(),
                            ResponseCodeStockEnum.SAVE_FAILED.getDesc() + objectHashMap));
                }
            }
        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.INSTORAGETYPE_NOT_NULL.getCode(),
                ResponseCodeStockEnum.INSTORAGETYPE_NOT_NULL.getDesc()));
    }

    //销售退货
    private ResponseResult sendToK3MarketReturn(String type, Long outStorageId, MarketReturnVO marketReturn, String stockId) {
        //登录的数据
        String acctId = marketReturn.getAcctId();
        String userName = marketReturn.getDataCentreUserName();
        String password = marketReturn.getDataCentrePassword();
        //保存的数据
        String fDate = marketReturn.getFDate();
        String fNumberStockOrg = marketReturn.getFNumberStockOrg();
        String fNumberCustomer = marketReturn.getFNumberFrecust();
        String fNumberBillType = marketReturn.getFNumberBillType();
        String fNumberSettleOrg = marketReturn.getFNumberSettleOrg();
        String fNumberSettleCurr = marketReturn.getFNumberSettleCurr();
        String entityJson = marketReturn.getEntityJson();
        if ("add".equals(type)) { //添加
            ResponseResult save = k3CLOUDApi.saveMarketReturn(acctId,
                    userName,
                    password,
                    fNumberBillType,
                    fDate,
                    fNumberSettleOrg,
                    fNumberCustomer,
                    "QT",
                    fNumberStockOrg,
                    fNumberCustomer,
                    fNumberCustomer,
                    fNumberCustomer,
                    fNumberSettleCurr,
                    fNumberSettleOrg,
                    entityJson);
            if (save.getResult().equals("暂未登录")) {
                //保存失败
                //提交操作
                return ResponseResult.error(new Error(ResponseCodeStockEnum.LOGIN_ERROR.getCode(),
                        ResponseCodeStockEnum.LOGIN_ERROR.getDesc() + save.getResult()));
            }
            if (save.isSuccess()) {
                HashMap<String, Object> objectHashMap = (HashMap<String, Object>) save.getResult();
                HashMap<String, Object> saveresult = (HashMap<String, Object>) objectHashMap.get("Result");
                HashMap<String, Object> saveStatus = (HashMap<String, Object>) saveresult.get("ResponseStatus");
                Boolean isSaveSuccess = (Boolean) saveStatus.get("IsSuccess");
                if (!isSaveSuccess) {
                    //保存失败
                    //提交操作
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SAVE_FAILED.getCode(),
                            ResponseCodeStockEnum.SAVE_FAILED.getDesc() + objectHashMap));
                } else {
                    //获取保存成功后的number和id
                    Integer id = (Integer) saveresult.get("Id");
                    String number = (String) saveresult.get("Number");
                    //更新id和number到数据库
                    Outstorage outstorage1 = new Outstorage();
                    outstorage1.setOutStorageId(outStorageId);
                    outstorage1.setK3Id(String.valueOf(id));
                    outstorage1.setK3Code(number);
                    outstorage1.setStockId(stockId);
                    outstorageDao.updateOutstorageByID(outstorage1);
                    return ResponseResult.success();
                }

            }
            //提交操作
            return ResponseResult.error(new Error(ResponseCodeStockEnum.SAVE_FAILED.getCode(),
                    ResponseCodeStockEnum.SAVE_FAILED.getDesc() + ":调用k3cloudApi的保存失败！"));
        }

        return ResponseResult.error(new Error(ResponseCodeStockEnum.TYPE_ERROR.getCode(),
                ResponseCodeStockEnum.TYPE_ERROR.getDesc()));
    }

    //仓库K3操作
    private ResponseResult sendToK3Stock(String type, Long aLong, StockVO stock) {
        //登录的数据
        String acctId = stock.getAcctId();
        String userName = stock.getDataCentreUserName();
        String password = stock.getDataCentrePassword();
        //k3数据
        String k3id = stock.getFStockId();
        String k3code = stock.getFNumber();
        //id
        String fName = stock.getFName();
        String fNumberCreateOrg = stock.getK3Number();
        String fNumerUseOrg = stock.getK3Number();
        String fStockProperty = stock.getFStockProperty();
        String fStockStatusType = stock.getFStockStatusType();
        //保存的对象
        String saveJson = JSON.toJSONString(stock);
        //提交的对象 审核的对象
        SubmitVO submitVO = new SubmitVO();
        submitVO.setIds(k3id);
        submitVO.setNumbers(k3code);
        String submitJson = JSON.toJSONString(submitVO);
        if ("add".equals(type)) { //添加
            ResponseResult save = k3CLOUDApi.saveStock(acctId, userName, password, fName, fNumberCreateOrg, fNumerUseOrg, fStockProperty, fStockStatusType, submitVO.getNumbers(), "");
            if (save.getResult().equals("暂未登录")) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.LOGIN_ERROR.getCode(),
                        ResponseCodeStockEnum.LOGIN_ERROR.getDesc() + save.getResult()));
            }
            if (save.isSuccess()) {
                HashMap<String, Object> objectHashMap = (HashMap<String, Object>) save.getResult();
                HashMap<String, Object> saveresult = (HashMap<String, Object>) objectHashMap.get("Result");
                HashMap<String, Object> saveStatus = (HashMap<String, Object>) saveresult.get("ResponseStatus");
                Boolean isSaveSuccess = (Boolean) saveStatus.get("IsSuccess");
                if (isSaveSuccess) {
                    //更新id和number到数据库
                    Integer id = (Integer) saveresult.get("Id");
                    String number = (String) saveresult.get("Number");
                    Stock stock1 = new Stock();
                    stock1.setStockId(aLong);
                    stock1.setId(String.valueOf(id));
                    stock1.setStockCode(number);
                    stockDao.updateStockByID(stock1);
                    return ResponseResult.success("仓库生成成功，k3保存成功");
                } else {
                    //保存失败
                    return ResponseResult.success("仓库生成成功，k3保存失败成功");
                }

            }
        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.TYPE_ERROR.getCode(),
                ResponseCodeStockEnum.TYPE_ERROR.getDesc()));
    }

    //StockStatus k3操作
    private ResponseResult<Object> sendToK3StockStatus(String type, Long aLong, StockStatusVO stockStatus) {
        //登录的数据
        String acctId = stockStatus.getAcctId();
        String userName = stockStatus.getDataCentreUserName();
        String password = stockStatus.getDataCentrePassword();
        //k3数据
        String k3id = stockStatus.getFStockStatusId();
        String k3code = stockStatus.getFNumber();
        //id
//        Long aLong = stockStatus.getStockStatusID();
        String fName = stockStatus.getFName();
        String fNumberCreateOrg = stockStatus.getFNumberCreateOrg();
        String fNumberUseOrg = stockStatus.getFNumberUseOrg();
        String fType = stockStatus.getFType();
        //保存的对象
        String saveJson = JSON.toJSONString(stockStatus);
        //提交的对象 审核的对象
        SubmitVO submitVO = new SubmitVO();
        submitVO.setIds(k3id);
        submitVO.setNumbers(k3code);
        String submitJson = JSON.toJSONString(submitVO);
        //查询的对象
        QueryVO queryVO = new QueryVO();
        if ("add".equals(type)) {     //保存
            ResponseResult save = k3CLOUDApi.saveStockStatus(acctId, userName, password, fName, fNumberCreateOrg,
                    fNumberUseOrg, fType, "", "");
            if (save.getResult().equals("暂未登录")) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.LOGIN_ERROR.getCode(),
                        ResponseCodeStockEnum.LOGIN_ERROR.getDesc() + save.getResult()));
            }
            if (save.isSuccess()) {
                HashMap<String, Object> objectHashMap = (HashMap<String, Object>) save.getResult();
                HashMap<String, Object> saveresult = (HashMap<String, Object>) objectHashMap.get("Result");
                HashMap<String, Object> saveStatus = (HashMap<String, Object>) saveresult.get("ResponseStatus");
                Boolean isSaveSuccess = (Boolean) saveStatus.get("IsSuccess");
                if (isSaveSuccess) {
                    //获取保存成功后的number和id
                    Integer id = (Integer) saveresult.get("Id");
                    String number = (String) saveresult.get("Number");
                    //提交
                    ResponseResult submit = k3CLOUDApi.submitStockStatus(acctId, userName, password, number, String.valueOf(id));
                    //                if(submit.getResult().equals("暂未登录")){
                    //                    return ResponseResult.error(new Error(ResponseCodeStockEnum.LOGIN_ERROR.getCode(),
                    //                            ResponseCodeStockEnum.LOGIN_ERROR.getDesc()+submit.getResult()));
                    //                }
                    if (submit.isSuccess()) {
                        //审核
                        HashMap<String, Object> submitHashMap = (HashMap<String, Object>) submit.getResult();
                        HashMap<String, Object> submitResult = (HashMap<String, Object>) submitHashMap.get("Result");
                        HashMap<String, Object> submitStatus = (HashMap<String, Object>) submitResult.get("ResponseStatus");
                        Boolean isSubmitSuccess = (Boolean) submitStatus.get("IsSuccess");
                        if (isSubmitSuccess) {
                            //审核
                            ResponseResult audit = k3CLOUDApi.auditStockStatus(acctId, userName, password, number, String.valueOf(id));
                            if (audit.getResult().equals("暂未登录")) {
                                return ResponseResult.error(new Error(ResponseCodeStockEnum.LOGIN_ERROR.getCode(),
                                        ResponseCodeStockEnum.LOGIN_ERROR.getDesc() + audit.getResult()));
                            }
                            if (audit.isSuccess()) {
                                HashMap<String, Object> auditResult = (HashMap<String, Object>) audit.getResult();
                                HashMap<String, Object> auditresult = (HashMap<String, Object>) auditResult.get("Result");
                                HashMap<String, Object> auditStatus = (HashMap<String, Object>) auditresult.get("ResponseStatus");
                                Boolean isAuditSuccess = (Boolean) auditStatus.get("IsSuccess");
                                if (isAuditSuccess) {
                                    //更新id和number到数据库
                                    StockStatus stockStatus1 = new StockStatus();
                                    stockStatus1.setStockStatusID(aLong);
                                    stockStatus1.setId(String.valueOf(id));
                                    stockStatus1.setCode(number);
                                    stockStatusDao.updateStockStatusByID(stockStatus1);
                                    //返回数据
                                    stockStatus.setFNumber(number);
                                    stockStatus.setFStockStatusId(String.valueOf(id));
                                    return ResponseResult.success(stockStatus);
                                }
                                return ResponseResult.error(new Error(ResponseCodeStockEnum.AUDIT_FAILED.getCode(),
                                        ResponseCodeStockEnum.AUDIT_FAILED.getDesc() + auditresult));
                            }
                            return ResponseResult.error(new Error(ResponseCodeStockEnum.AUDIT_FAILED.getCode(),
                                    ResponseCodeStockEnum.AUDIT_FAILED.getDesc() + ":调用k3cloudApi的审核失败!"));
                        }
                        //审核
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.SUBMIT_FAILED.getCode(),
                                ResponseCodeStockEnum.SUBMIT_FAILED.getDesc() + submitResult));
                    }
                    //审核
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SUBMIT_FAILED.getCode(),
                            ResponseCodeStockEnum.SUBMIT_FAILED.getDesc() + ":调用k3cloudApi的提交失败！"));
                }
                //保存失败

                return ResponseResult.error(new Error(ResponseCodeStockEnum.SAVE_FAILED.getCode(),
                        ResponseCodeStockEnum.SAVE_FAILED.getDesc() + objectHashMap));
            }
            //保存
            //提交操作
            //审核
            return ResponseResult.error(new Error(ResponseCodeStockEnum.SAVE_FAILED.getCode(),
                    ResponseCodeStockEnum.SAVE_FAILED.getDesc() + ":调用k3cloudApi的保存失败！"));
        }
        if ("update".equals(type)) {  //更新
            if (StringUtils.isBlank(k3code) || StringUtils.isBlank(k3id)) { //未保存
                return ResponseResult.error(new Error(ResponseCodeStockEnum.AUDIT_FAILED.getCode(),
                        ResponseCodeStockEnum.AUDIT_FAILED.getDesc() + ":没有保存数据到k3！"));
            }
            //反审核
            ResponseResult unAudit = k3CLOUDApi.unAuditStockStatus(acctId, userName, password, k3code, k3id);
            if (unAudit.getResult().equals("暂未登录")) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.LOGIN_ERROR.getCode(),
                        ResponseCodeStockEnum.LOGIN_ERROR.getDesc() + unAudit.getResult()));
            }
            if (unAudit.isSuccess()) {
                HashMap<String, Object> objectHashMap = (HashMap<String, Object>) unAudit.getResult();
                HashMap<String, Object> unAuditresult = (HashMap<String, Object>) objectHashMap.get("Result");
                HashMap<String, Object> unAuditStatus = (HashMap<String, Object>) unAuditresult.get("ResponseStatus");
                Boolean isUnAuditSuccess = (Boolean) unAuditStatus.get("IsSuccess");
                if (isUnAuditSuccess) {
                    //保存
                    ResponseResult save = k3CLOUDApi.saveStockStatus(acctId, userName, password, fName, fNumberCreateOrg,
                            fNumberUseOrg, fType, k3code, k3id);
                    HashMap<String, Object> objectHashMap1 = (HashMap<String, Object>) save.getResult();
                    HashMap<String, Object> saveresult = (HashMap<String, Object>) objectHashMap1.get("Result");
                    HashMap<String, Object> saveStatus = (HashMap<String, Object>) saveresult.get("ResponseStatus");
                    Boolean issaveSuccess = (Boolean) saveStatus.get("IsSuccess");
                    if (issaveSuccess) {
                        //提交
                        ResponseResult submit = k3CLOUDApi.submitStockStatus(acctId, userName, password, k3code, k3id);
                        HashMap<String, Object> objectHashMap2 = (HashMap<String, Object>) submit.getResult();
                        HashMap<String, Object> submitresult = (HashMap<String, Object>) objectHashMap2.get("Result");
                        HashMap<String, Object> submitStatus = (HashMap<String, Object>) submitresult.get("ResponseStatus");
                        Boolean issubmitSuccess = (Boolean) submitStatus.get("IsSuccess");
                        if (issubmitSuccess) {
                            //审核k3数据
                            ResponseResult audit = k3CLOUDApi.auditStockStatus(acctId, userName, password, k3code, k3id);
                            HashMap<String, Object> objectHashMap3 = (HashMap<String, Object>) audit.getResult();
                            HashMap<String, Object> auditresult = (HashMap<String, Object>) objectHashMap3.get("Result");
                            HashMap<String, Object> auditStatus = (HashMap<String, Object>) auditresult.get("ResponseStatus");
                            Boolean isauditSuccess = (Boolean) auditStatus.get("IsSuccess");
                            if (isauditSuccess) {
                                return ResponseResult.success();
                            }
                            //审核
                            return ResponseResult.error(new Error(ResponseCodeStockEnum.AUDIT_FAILED.getCode(),
                                    ResponseCodeStockEnum.AUDIT_FAILED.getDesc() + ":调用k3审核失败！"));
                        }
                        //提交
                        //审核
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.SUBMIT_FAILED.getCode(),
                                ResponseCodeStockEnum.SUBMIT_FAILED.getDesc() + ":调用k3提交失败！"));
                    }
                    //保存
                    //提交
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SAVE_FAILED.getCode(),
                            ResponseCodeStockEnum.SAVE_FAILED.getDesc() + ":调用k3保存失败!"));
                }
                //反审核
                //保存
                return ResponseResult.error(new Error(ResponseCodeStockEnum.UNAUDIT_FAILED.getCode(),
                        ResponseCodeStockEnum.UNAUDIT_FAILED.getDesc() + ":调用k3cloudApi的反审核失败!"));
            }
            //反审核
            //保存
            return ResponseResult.error(new Error(ResponseCodeStockEnum.UNAUDIT_FAILED.getCode(),
                    ResponseCodeStockEnum.UNAUDIT_FAILED.getDesc() + ":调用k3cloudApi的反审核失败!"));
        }
        if ("delete".equals(type)) {  //删除
            if (StringUtils.isBlank(k3code) || StringUtils.isBlank(k3id)) { //未保存
                return ResponseResult.error(new Error(ResponseCodeStockEnum.AUDIT_FAILED.getCode(),
                        ResponseCodeStockEnum.AUDIT_FAILED.getDesc() + ":没有保存数据到k3！"));
            }
            //反审核
            ResponseResult unAudit = k3CLOUDApi.unAuditStockStatus(acctId, userName, password, k3code, k3id);
            if (unAudit.getResult().equals("暂未登录")) {
                //反审核
                //删除
                return ResponseResult.error(new Error(ResponseCodeStockEnum.LOGIN_ERROR.getCode(),
                        ResponseCodeStockEnum.LOGIN_ERROR.getDesc() + unAudit.getResult()));
            }
            if (unAudit.isSuccess()) {
                HashMap<String, Object> objectHashMap = (HashMap<String, Object>) unAudit.getResult();
                HashMap<String, Object> unAuditresult = (HashMap<String, Object>) objectHashMap.get("Result");
                HashMap<String, Object> unAuditStatus = (HashMap<String, Object>) unAuditresult.get("ResponseStatus");
                Boolean isUnAuditSuccess = (Boolean) unAuditStatus.get("IsSuccess");
                if (isUnAuditSuccess) {
                    //删除k3数据
                    ResponseResult delete = k3CLOUDApi.deleteStockStatus(acctId, userName, password, k3code, k3id);
//                    if (delete.getResult().equals("暂未登录")) {
//                        return ResponseResult.error(new Error(ResponseCodeStockEnum.LOGIN_ERROR.getCode(),
//                                ResponseCodeStockEnum.LOGIN_ERROR.getDesc() + delete.getResult()));
//                    }
                    if (delete.isSuccess()) {
                        HashMap<String, Object> objectHashMap1 = (HashMap<String, Object>) delete.getResult();
                        HashMap<String, Object> deleteresult = (HashMap<String, Object>) objectHashMap1.get("Result");
                        HashMap<String, Object> deleteStatus = (HashMap<String, Object>) deleteresult.get("ResponseStatus");
                        Boolean isDeleteSuccess = (Boolean) deleteStatus.get("IsSuccess");
                        if (isDeleteSuccess) {
                            return ResponseResult.success(stockStatus);
                        }
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.DELETE_FAILED.getCode(),
                                ResponseCodeStockEnum.DELETE_FAILED.getDesc() + ":调用k3删除失败!"));
                    }
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.DELETE_FAILED.getCode(),
                            ResponseCodeStockEnum.DELETE_FAILED.getDesc() + ":调用k3cloudApi的删除失败!"));
                }
                //反审核
                //删除
                return ResponseResult.error(new Error(ResponseCodeStockEnum.DELETE_FAILED.getCode(),
                        ResponseCodeStockEnum.DELETE_FAILED.getDesc() + ":调用k3反审核失败!"));
            }
            //反审核
            //删除

            return ResponseResult.error(new Error(ResponseCodeStockEnum.DELETE_FAILED.getCode(),
                    ResponseCodeStockEnum.DELETE_FAILED.getDesc() + ":调用k3cloudApi的反审核失败!"));
        }
        if ("query".equals(type)) {  //查询
        }
        return ResponseResult.error(new Error(ResponseCodeStockEnum.TYPE_ERROR.getCode(),
                ResponseCodeStockEnum.TYPE_ERROR.getDesc()));
    }


    //获取数据中心、用户名和密码
    public HashMap<String, String> getUserNameAndPassword(Long companyId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("dataCentre", null);
        map.put("userName", null);
        map.put("password", null);
        ResponseResult responseResult = storeApi.selectCompanyByID(1L);
        if (responseResult.isSuccess()) {
            HashMap<String, Object> result = (HashMap<String, Object>) responseResult.getResult();
            String dataCentre = String.valueOf(result.get("dataCentre"));
            String dataCentreUserName = String.valueOf(result.get("yewuDataCentreUserName"));
            String dataCentrePassword = String.valueOf(result.get("yewuDataCentrePassword"));
            map.put("dataCentre", dataCentre);
            map.put("userName", dataCentreUserName);
            map.put("password", dataCentrePassword);
        }
        return map;
    }

}
