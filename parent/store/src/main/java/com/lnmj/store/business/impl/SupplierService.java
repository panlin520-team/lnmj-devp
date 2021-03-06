package com.lnmj.store.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeSupplierEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseSupplierEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.EnumUtil;
import com.lnmj.common.utils.NumberUtils;
import com.lnmj.store.business.ISupplierService;
import com.lnmj.store.entity.*;
import com.lnmj.store.repository.ICompanyDao;
import com.lnmj.store.repository.IStoreDao;
import com.lnmj.store.repository.ISupplierDao;
import com.lnmj.store.serviceProxy.K3Api_organization;
import com.lnmj.store.serviceProxy.ProductApi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/19 18:23
 * @Description:
 */
@Transactional
@Service("supplierService")
public class SupplierService implements ISupplierService {
    @Resource
    private ISupplierDao iSupplierDao;
    @Resource
    private K3Api_organization k3Api_organization;
    @Resource
    private ICompanyDao iCompanyDao;
    @Resource
    private IStoreDao iStoreDao;
    @Resource
    private ProductApi productApi;

    @Override
    public ResponseResult selectSupplierList(int pageNum, int pageSize, String keyWordName, String keyWordPhone, String supplierCategoryId, String supplierType, String companyType, String companyId) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("keyWordName", keyWordName);
        map.put("keyWordPhone", keyWordPhone);
        map.put("supplierCategoryId", supplierCategoryId);
        map.put("supplierType", supplierType);
        map.put("relationSubCompanyType", companyType);
        map.put("relationSubCompanyId", companyId);

        map.put("companyId", companyId);
        map.put("companyType", companyType);
        List<Supplier> supplierList = new ArrayList<>();
        if (companyType.equals("2")){
            supplierList = iSupplierDao.selectSupplierListSubCompany(map);
        }else if (companyType.equals("3")){
            supplierList = iSupplierDao.selectSupplierListStore(map);
        }else if (companyType.equals("1")){
            map.clear();
            map.put("zhongCompanyId",companyId);
            supplierList = iSupplierDao.selectSupplierList(map);
        }

        List<SupplierCategory> supplierCategoryList = iSupplierDao.selectSupplierCategoryList(new HashMap());
        for (Supplier supplier : supplierList) {
            for (SupplierCategory supplierCategory : supplierCategoryList) {
                if (supplier.getSupplierCategoryId() != null && supplier.getSupplierCategoryId().equals(supplierCategory.getSupplierCategoryId())) {
                    supplier.setSupplierCategoryName(supplierCategory.getSupplierCategoryName());
                }
            }
        }
        if (supplierList.size() != 0) {
            PageInfo pageInfo = new PageInfo(supplierList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseSupplierEnum.SUPPLIER_NULL.getCode(),
                ResponseSupplierEnum.SUPPLIER_NULL.getDesc()));

    }

    @Override
    public ResponseResult selectSupplierByCondition(Supplier supplier) {
        Supplier supplierRsult = iSupplierDao.selectSupplierByCondition(supplier);
        return ResponseResult.success(supplierRsult);
    }

    @Override
    public ResponseResult selectSupplierListNoPage(String keyWordName, String keyWordPhone, String supplierCategoryId, String supplierType, String companyType, String companyId) {
        Map map = new HashMap();
        map.put("keyWordName", keyWordName);
        map.put("keyWordPhone", keyWordPhone);
        map.put("supplierCategoryId", supplierCategoryId);
        map.put("supplierType", supplierType);
        if (supplierType != null && supplierType.equals("2")) {
            map.put("relationSubCompanyType", null);
            map.put("relationSubCompanyId", null);
        } else if (supplierType != null && supplierType.equals("1")) {
            map.put("relationSubCompanyType", companyType);
            map.put("relationSubCompanyId", companyId);
        }



        List<Supplier> supplierList = iSupplierDao.selectSupplierList(map);
        map.clear();
        List<SupplierCategory> supplierCategoryList = iSupplierDao.selectSupplierCategoryList(map);

        for (Supplier supplier : supplierList) {
            for (SupplierCategory supplierCategory : supplierCategoryList) {
                if (supplier.getSupplierCategoryId() != null && supplier.getSupplierCategoryId().equals(supplierCategory.getSupplierCategoryId())) {
                    supplier.setSupplierCategoryName(supplierCategory.getSupplierCategoryName());
                }
            }
        }


        List<Supplier> supplierListResult = new ArrayList<>();
        map.clear();
        map.put("companyId",companyId);
        if (companyType != null) {
            if (companyType.equals("2")) {
                List<SupplierSubCompany> supplierSubCompanyList = iSupplierDao.selectSupplierSubCompany(map);
                for (SupplierSubCompany supplierSubCompany : supplierSubCompanyList) {
                    for (Supplier supplier : supplierList) {
                        if (supplierSubCompany.getSupplierId().toString().equals(supplier.getSupplierId().toString())) {
                            supplierListResult.add(supplier);
                        }
                    }

                }
            } else if (companyType.equals("3")) {
                List<SupplierStore> supplierStoreList = iSupplierDao.selectSupplierStore(map);
                for (SupplierStore supplierStore : supplierStoreList) {
                    for (Supplier supplier : supplierList) {
                        if (supplierStore.getSupplierId().toString().equals(supplier.getSupplierId().toString())) {
                            supplierListResult.add(supplier);
                        }
                    }

                }
            }
        }


        if (companyType == null) {
            if (supplierList.size() > 0) {
                return ResponseResult.success(supplierList);
            }
        } else {
            if (supplierListResult.size() > 0) {
                return ResponseResult.success(supplierListResult);
            }
        }
        return ResponseResult.error(new Error(ResponseSupplierEnum.SUPPLIER_NULL.getCode(),
                ResponseSupplierEnum.SUPPLIER_NULL.getDesc()));
    }

    @Override
    public ResponseResult updateSupplier(Supplier supplier) {
        int resultInt = iSupplierDao.updateSupplier(supplier);

        //k3修改供应商
        Map mapSelect = new HashMap();
        mapSelect.put("supplierId", supplier.getSupplierId());
        Supplier supplierSelect = iSupplierDao.selectSupplierById(mapSelect);
        ResponseResult responseResult = k3Api_organization.saveSupplier(
                supplierSelect.getDataCentre(),
                supplierSelect.getDataCentreUserName(),
                supplierSelect.getDataCentrePassword(),
                supplier.getSupplierName(),
                "",
                "",
                "",
                "",
                "FName",
                Integer.parseInt(supplierSelect.getK3Id()));
        HashMap<String, Object> objectHashMap = (HashMap<String, Object>) responseResult.getResult();
        HashMap<String, Object> saveresult = (HashMap<String, Object>) objectHashMap.get("Result");
        HashMap<String, Object> saveStatus = (HashMap<String, Object>) saveresult.get("ResponseStatus");
        Boolean isSaveSuccess = (Boolean) saveStatus.get("IsSuccess");
        if (isSaveSuccess) {
            return ResponseResult.success("供应商修改成功，k3数据更新成功");
        }
        if (resultInt > 0) {
            return ResponseResult.success("供应商修改成功，k3数据更新失败");
        }
        return ResponseResult.error(new Error(ResponseCodeSupplierEnum.SUPPLIER_UPDATE_FAIL.getCode(), ResponseCodeSupplierEnum.SUPPLIER_UPDATE_FAIL.getDesc()));

    }

    @Override
    public ResponseResult deleteSupplier(Supplier supplier) {
        //判断供应商是否被采购入库单使用
        List<Map> mapList = (List<Map>) (productApi.checkInstorageBySupplierCode(supplier.getSupplierCode()).getResult());
        if (mapList != null && mapList.size() > 0) {
            return ResponseResult.error(new Error(ResponseSupplierEnum.SUPPLIER_CAIGOU_INTSTORAGE_USE.getCode(),
                    ResponseSupplierEnum.SUPPLIER_CAIGOU_INTSTORAGE_USE.getDesc()));
        }


        Map map = new HashMap();
        map.put("supplierId", supplier.getSupplierId());
        Supplier supplierResult = iSupplierDao.selectSupplierById(map);
        iSupplierDao.deleteSupplier(supplier);
        //判断供应商是否有与子公司的分配
        Map map1 = new HashMap();
        map1.put("supplierId", supplier.getSupplierId());
        List<SupplierSubCompany> supplierSubCompanyList = iSupplierDao.checkSupplierSubCompany(map1);
        if (supplierSubCompanyList != null && supplierSubCompanyList.size() > 0) {
            //如果存在分配到子公司 也要删除
            for (SupplierSubCompany supplierSubCompany : supplierSubCompanyList) {
                Map map2 = new HashMap();
                map2.put("supplierId", supplierSubCompany.getSupplierId());
                iSupplierDao.deleteSupplierSubcompany(map2);
            }
        }

        //判断供应商是否有与分公司的分配
        List<SupplierStore> supplierStoreList = iSupplierDao.checkSupplierStore(map1);
        if (supplierSubCompanyList != null && supplierSubCompanyList.size() > 0) {
            //如果存在分配到子公司 也要删除
            for (SupplierStore supplierStore : supplierStoreList) {
                Map map2 = new HashMap();
                map2.put("supplierId", supplierStore.getSupplierId());
                iSupplierDao.deleteSupplierStore(map2);
            }
        }

        //先查询出供应商信息
        k3Api_organization.unAuditSupplier(supplierResult.getDataCentre(), supplierResult.getDataCentreUserName(), supplierResult.getDataCentrePassword(), supplierResult.getK3Number(), null);
        ResponseResult responseResultDelete = k3Api_organization.deleteSupplier(supplierResult.getDataCentre(), supplierResult.getDataCentreUserName(), supplierResult.getDataCentrePassword(), supplierResult.getK3Number(), null);
        if (responseResultDelete.getResult().equals("暂未登录")) {
            return ResponseResult.success("k3供应商删除失败:" + "k3未登录");

        }
        if (!((Map) (((Map) (((Map) (responseResultDelete.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("k3供应商删除失败:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResultDelete.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        return ResponseResult.success("k3供应商删除成功");
    }

    @Override
    public ResponseResult addSupplier(Supplier supplier, String companyID, String companyType, String orgK3Number) {
        //查看供应商名字是否重复
        int resultIntName = iSupplierDao.checkSupplierName(supplier);
        if (resultIntName>0) {
            return ResponseResult.error(new Error(ResponseSupplierEnum.SUPPLIER_NAME_EXIST.getCode(),
                    ResponseSupplierEnum.SUPPLIER_NAME_EXIST.getDesc()));
        }

        if (supplier.getSupplierCategoryId() == null) {
            return ResponseResult.error(new Error(ResponseSupplierEnum.SUPPLIER_CATEGORY_ID_NULL.getCode(),
                    ResponseSupplierEnum.SUPPLIER_CATEGORY_ID_NULL.getDesc()));
        }
        supplier.setSupplierCode(NumberUtils.getRandomOrderNoChild());
        supplier.setSupplierType(2);
        //加入k3供应商
        Company company = null;
        Subsidiary subsidiary = null;
        if (companyType.equals("1")) {
            company = iCompanyDao.selectCompanyByID(Long.parseLong(companyID));
        } else if (companyType.equals("2")) {
            subsidiary = iCompanyDao.selectSubsidiaryByID(Long.parseLong(companyID));
            company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("FLocName", supplier.getAddress()); //地址名称

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("FNUMBER", supplier.getLinkMan());
        jsonObject.put("FLocNewContact", jsonObject1); //联系人

        jsonObject.put("FLocAddress", supplier.getAddress()); //通讯地址
        jsonObject.put("FLocMobile", supplier.getLinkPhone()); //手机
        jsonArray.add(jsonObject);
        ResponseResult responseResultSupplier = k3Api_organization.saveSupplier(company.getDataCentre(), company.getYewuDataCentreUserName(), company.getYewuDataCentrePassword(), supplier.getSupplierName(), company.getK3Number(), company.getK3Number(), "PRE001", jsonArray.toJSONString(), null, null);
        if (!((Map) (((Map) (((Map) (responseResultSupplier.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("k3供应商生成失败:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResultSupplier.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        supplier.setDataCentreUserName(company.getDataCentreUserName());
        supplier.setDataCentrePassword(company.getDataCentrePassword());
        String k3Id = (((Map) (((Map) responseResultSupplier.getResult()).get("Result")))).get("Id").toString();
        String number = (((Map) (((Map) responseResultSupplier.getResult()).get("Result")))).get("Number").toString();
        supplier.setK3Id(k3Id);
        supplier.setK3Number(number);
        supplier.setDataCentre(company.getDataCentre());
        iSupplierDao.addSupplier(supplier);


        if (companyType.equals("2")) {
            //添加供应商和当前组织的对应关系
            List<SupplierSubCompany> supplierSubCompanyList = new ArrayList<>();

            Map map = new HashMap();
            SupplierSubCompany supplierSubCompany = new SupplierSubCompany();
            supplierSubCompany.setSupplierId(supplier.getSupplierId());
            supplierSubCompany.setSubCompanyId(Long.parseLong(companyID));
            supplierSubCompanyList.add(supplierSubCompany);
            map.put("list", supplierSubCompanyList);
            iSupplierDao.addSupplierSubCompany(map);

            //同时分配给当前子公司
            ResponseResult responseResult = k3Api_organization.AllocateSupplier(
                    company.getDataCentre(),
                    company.getYewuDataCentreUserName(),
                    company.getYewuDataCentrePassword(),
                    k3Id,
                    subsidiary.getK3Id(),
                    true
            );
            return ResponseResult.success("k3供应商生成成功,并分配给当前组织");
        }

        return ResponseResult.success("k3供应商生成成功");
    }


    @Override
    public ResponseResult selectSupplierCategoryList(int pageNum, int pageSize, String keyWordName, String companyType, String companyId) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("keyWordName", keyWordName);
        map.put("companyType", companyType);
        map.put("companyId", companyId);
        List<SupplierCategory> supplierCategoryList = iSupplierDao.selectSupplierCategoryList(map);
        if (supplierCategoryList.size() != 0) {
            PageInfo pageInfo = new PageInfo(supplierCategoryList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseSupplierEnum.SUPPLIER_CATEGORY_NULL.getCode(),
                ResponseSupplierEnum.SUPPLIER_CATEGORY_NULL.getDesc()));

    }

    @Override
    public ResponseResult listSupplierCategoryNoPage(String companyType, String companyId) {
        if (companyType.equals("3")) {
            //如果是门店
            Store store = iStoreDao.selectStoreById(Long.parseLong(companyId));
            Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(store.getSubCompanyId());
            companyId = subsidiary.getSubsidiaryId().toString();
            companyType = "2";
        }
        Map map = new HashMap();
        map.put("companyType", companyType);
        map.put("companyId", companyId);
        List<SupplierCategory> supplierCategoryList = iSupplierDao.selectSupplierCategoryList(map);
        //查看分类下面的具体供应商

        List<Supplier> supplierList = iSupplierDao.selectSupplierList(new HashMap());
        for (SupplierCategory supplierCategory : supplierCategoryList) {
            List<Supplier> supplierListAdd = new ArrayList<>();
            for (Supplier supplier : supplierList) {
                if (supplierCategory.getSupplierCategoryId() == supplier.getSupplierCategoryId()) {
                    supplierListAdd.add(supplier);
                }
            }
            supplierCategory.setSupplierList(supplierListAdd);
        }


        if (supplierCategoryList.size() != 0) {
            return ResponseResult.success(supplierCategoryList);
        }
        return ResponseResult.error(new Error(ResponseSupplierEnum.SUPPLIER_CATEGORY_NULL.getCode(),
                ResponseSupplierEnum.SUPPLIER_CATEGORY_NULL.getDesc()));
    }

    @Override
    public ResponseResult updateSupplierCategory(SupplierCategory supplierCategory) {
        iSupplierDao.updateSupplierCategory(supplierCategory);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteSupplierCategory(SupplierCategory supplierCategory) {
        iSupplierDao.deleteSupplierCategory(supplierCategory);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult addSupplierCategory(SupplierCategory supplierCategory) {
        //查看供应商分类名称是否存在
        int resultIntName = iSupplierDao.checkSupplierCategoryName(supplierCategory);
        if (resultIntName>0) {
            return ResponseResult.error(new Error(ResponseSupplierEnum.SUPPLIER_CATEGORY_NAME_EXIST.getCode(),
                    ResponseSupplierEnum.SUPPLIER_CATEGORY_NAME_EXIST.getDesc()));
        }

        iSupplierDao.addSupplierCategory(supplierCategory);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectSupplierByCode(String supplierCode) {
        Supplier supplier = iSupplierDao.selectSupplierByCode(supplierCode);
        return ResponseResult.success(supplier);
    }

    @Override
    public ResponseResult batchAllocationSubCompany(String companyId, String companyType, String supplierArrayStr, String subcompanyIdArrayStr) {
        //本系统供应商分配
        JSONArray supplierArrary = JSON.parseArray(supplierArrayStr);
        JSONArray subcompanyIdArray = JSON.parseArray(subcompanyIdArrayStr);
        List<Map> mapList = new ArrayList<>();
        for (int i = 0; i < subcompanyIdArray.size(); i++) {
            SupplierSubCompany supplierSubCompany = new SupplierSubCompany();
            for (int j = 0; j < supplierArrary.size(); j++) {
                List<SupplierSubCompany> supplierSubCompanylistAdd = new ArrayList<>();
                supplierSubCompany.setSupplierId(supplierArrary.getJSONObject(j).getLong("supplierId"));
                supplierSubCompany.setSubCompanyId(subcompanyIdArray.getJSONObject(i).getLong("orgId"));
                //查看是否已经分配
                Map mapCheck = new HashMap();
                mapCheck.put("supplierId", supplierArrary.getJSONObject(j).getString("supplierId"));
                mapCheck.put("subCompanyId", subcompanyIdArray.getJSONObject(i).getLong("orgId"));
                List<SupplierSubCompany> supplierSubCompanyList = iSupplierDao.checkSupplierSubCompany(mapCheck);
                if (supplierSubCompanyList != null && supplierSubCompanyList.size() > 0) {
                    Map mapResult = new HashMap();
                    mapResult.put("供应商名称", supplierArrary.getJSONObject(j).getString("supplierName"));
                    mapResult.put("组织名称", subcompanyIdArray.getJSONObject(i).getString("orgName"));
                    mapList.add(mapResult);
                } else {
                    supplierSubCompanylistAdd.add(supplierSubCompany);
                    Map map = new HashMap();
                    map.put("list", supplierSubCompanylistAdd);
                    iSupplierDao.addSupplierSubCompany(map);
                }
            }
        }


        //k3分配接入
        String PkIds = "";
        String TOrgIds = "";
        for (int i = 0; i < supplierArrary.size(); i++) {
            PkIds = PkIds + "," + supplierArrary.getJSONObject(i).getString("supplierK3Id");
        }
        for (int i = 0; i < subcompanyIdArray.size(); i++) {
            TOrgIds = TOrgIds + "," + subcompanyIdArray.getJSONObject(i).getString("k3Id");
        }
        PkIds = PkIds.substring(1);
        TOrgIds = TOrgIds.substring(1);
        Company company = null;
        if (companyType.equals("1")) {
            //如果是总公司
            company = iCompanyDao.selectCompanyByID(Long.parseLong(companyId));
        } else if (companyType.equals("2")) {
            //如果是子公司
            Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(Long.parseLong(companyId));
            company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
        }


        ResponseResult responseResult = k3Api_organization.AllocateSupplier(
                company.getDataCentre(),
                company.getYewuDataCentreUserName(),
                company.getYewuDataCentrePassword(),
                PkIds,
                TOrgIds,
                true
        );
        if (responseResult.getResult().equals("暂未登录")) {
            return ResponseResult.success("供应商分配成功,K3供应商未分配，K3用户密码错误");
        }
        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("供应商分配成功，K3分配未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        if (mapList.size() != 0) {
            return ResponseResult.success("分配成功,k3分配成功,但以下供应商已经分配组织" + mapList);
        } else {
            return ResponseResult.success("分配成功,k3分配成功");
        }

    }

    @Override
    public ResponseResult batchAllocationStore(String companyId, String companyType, String supplierArrayStr, String storeIdArrayStr) {
        //本系统供应商分配
        JSONArray supplierArrary = JSON.parseArray(supplierArrayStr);
        JSONArray storeIdArray = JSON.parseArray(storeIdArrayStr);

        List<Map> mapList = new ArrayList<>();
        for (int i = 0; i < storeIdArray.size(); i++) {
            SupplierStore supplierStore = new SupplierStore();
            for (int j = 0; j < supplierArrary.size(); j++) {
                List<SupplierStore> supplierStorelistAdd = new ArrayList<>();
                supplierStore.setSupplierId(supplierArrary.getJSONObject(j).getLong("supplierId"));
                supplierStore.setStoreId(storeIdArray.getJSONObject(i).getLong("orgId"));
                //查看是否已经分配
                Map mapCheck = new HashMap();
                mapCheck.put("supplierId", supplierArrary.getJSONObject(j).getString("supplierId"));
                mapCheck.put("storeId", storeIdArray.getJSONObject(i).getLong("orgId"));
                List<SupplierStore> supplierStoreList = iSupplierDao.checkSupplierStore(mapCheck);
                if (supplierStoreList != null && supplierStoreList.size() > 0) {
                    Map mapResult = new HashMap();
                    mapResult.put("供应商名称", supplierArrary.getJSONObject(j).getString("supplierName"));
                    mapResult.put("组织名称", storeIdArray.getJSONObject(i).getString("orgName"));
                    mapList.add(mapResult);
                } else {
                    supplierStorelistAdd.add(supplierStore);
                    Map map = new HashMap();
                    map.put("list", supplierStorelistAdd);
                    iSupplierDao.addSupplierStore(map);
                }
            }
        }
        //k3分配接入
        String PkIds = "";
        String TOrgIds = "";
        for (int i = 0; i < supplierArrary.size(); i++) {
            PkIds = PkIds + "," + supplierArrary.getJSONObject(i).getString("supplierK3Id");
        }
        for (int i = 0; i < storeIdArray.size(); i++) {
            TOrgIds = TOrgIds + "," + storeIdArray.getJSONObject(i).getString("k3Id");
        }
        PkIds = PkIds.substring(1);
        TOrgIds = TOrgIds.substring(1);
        Company company = null;
        if (companyType.equals("1")) {
            //如果是总公司
            company = iCompanyDao.selectCompanyByID(Long.parseLong(companyId));
        } else if (companyType.equals("2")) {
            //如果是子公司
            Subsidiary subsidiary = iCompanyDao.selectSubsidiaryByID(Long.parseLong(companyId));
            company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
        }


        ResponseResult responseResult = k3Api_organization.AllocateSupplier(
                company.getDataCentre(),
                company.getYewuDataCentreUserName(),
                company.getYewuDataCentrePassword(),
                PkIds,
                TOrgIds,
                true
        );
        if (responseResult.getResult().equals("暂未登录")) {
            return ResponseResult.success("供应商分配成功,K3供应商分配未生成，K3用户密码错误");
        }
        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("供应商分配成功，K3分配未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        if (mapList.size() != 0) {
            return ResponseResult.success("分配成功,k3分配成功,但以下供应商已经分配组织" + mapList);
        } else {
            return ResponseResult.success("分配成功,k3分配成功");
        }
    }

    @Override
    public ResponseResult batchCancelAllocationSubCompany(String companyId, String companyType, String supplierArrayStr, String subcompanyIdArrayStr) {
        //本系统商品取消分配
        JSONArray supplierArrary = JSON.parseArray(supplierArrayStr);
        JSONArray subcompanyIdArray = JSON.parseArray(subcompanyIdArrayStr);
        for (int i = 0; i < subcompanyIdArray.size(); i++) {
            for (int j = 0; j < supplierArrary.size(); j++) {
                Map map = new HashMap();
                map.put("supplierId", supplierArrary.getJSONObject(j).getString("supplierId"));
                map.put("subCompanyId", subcompanyIdArray.getJSONObject(i).getLong("orgId"));
                iSupplierDao.deleteSupplierSubcompany(map);
            }
        }
        return ResponseResult.success("取消分配成功,请登陆k3系统进行手动供应商取消分配");
    }

    @Override
    public ResponseResult batchCancelAllocationStore(String companyId, String companyType, String supplierArrayStr, String storeIdArrayStr) {
        //本系统商品取消分配
        JSONArray supplierArrary = JSON.parseArray(supplierArrayStr);
        JSONArray storeIdArray = JSON.parseArray(storeIdArrayStr);
        for (int i = 0; i < storeIdArray.size(); i++) {
            for (int j = 0; j < supplierArrary.size(); j++) {
                Map map = new HashMap();
                map.put("supplierId", supplierArrary.getJSONObject(j).getString("supplierId"));
                map.put("storeId", storeIdArray.getJSONObject(i).getLong("orgId"));
                iSupplierDao.deleteSupplierStore(map);
            }
        }
        return ResponseResult.success("取消分配成功,请登陆k3系统进行手动供应商取消分配");
    }

    @Override
    public ResponseResult selectSuppTypeList() {
        Map<Integer, String> supplierTypeEnum = EnumUtil.getEnumToMap(com.lnmj.common.Enum.SupplierTypeEnum.class);
        return ResponseResult.success(supplierTypeEnum);
    }
}
