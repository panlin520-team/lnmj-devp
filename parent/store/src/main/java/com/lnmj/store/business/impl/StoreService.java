package com.lnmj.store.business.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeCompanyEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeStoreEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.ListPageUntil;
import com.lnmj.common.utils.MemoContinuousCheckUtil;
import com.lnmj.common.utils.PhoneUtils;
import com.lnmj.store.business.IStoreService;
import com.lnmj.store.entity.*;
import com.lnmj.store.entity.VO.CompanyVO;
import com.lnmj.store.entity.VO.StoreVO;
import com.lnmj.store.repository.*;
import com.lnmj.store.serviceProxy.AuthApi;
import com.lnmj.store.serviceProxy.K3Api_organization;
import com.lnmj.store.serviceProxy.OrderApi;
import com.lnmj.store.serviceProxy.ProductApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2019/5/8
 */
@Transactional
@Service("storeService")
public class StoreService implements IStoreService {
    @Resource
    private IStoreDao storeDao;
    @Resource
    private IIndustryDao industryDao;
    @Resource
    private ICompanyDao companyDao;
    @Resource
    private ProductApi productApi;
    @Resource
    private IExperienceCardDao experienceCardDao;
    @Resource
    private OrderApi orderApi;
    @Resource
    private AuthApi authApi;
    @Resource
    private K3Api_organization k3Api_organization;
    @Resource
    private ICustomerDao iCustomerDao;
    @Resource
    private ISupplierDao iSupplierDao;

    @Override
    public ResponseResult selectStoretList(int pageNum, int pageSize, String keyWordPhone, String keyWordName, Long storeCategoryId, Long companyType, Long companyId, Long industryID) {
        /* PageHelper.startPage(pageNum, pageSize);*/
        Map map = new HashMap();
        map.put("keyWordPhone", keyWordPhone);
        map.put("keyWordName", keyWordName);
        map.put("storeCategoryId", storeCategoryId);
        map.put("industryID", industryID);
        List<StoreVO> storeList = storeDao.selectStoretList(map);
        List<StoreVO> storeListResult = new ArrayList<>();
        //查询行业
        List<Industry> industryList = industryDao.selectList(map);
        //查询门店分类
        List<StoreCategory> storeCategoryList = storeDao.storeCategoryList(map);
        if (companyType == 0) {
            //如果是系统
        } else if (companyType == 1) {
            //如果是总公司
            //所有子公司
            List<Subsidiary> subsidiaryList = companyDao.selectSubsidiaryByParentID(companyId);
            List<Long> longList = new ArrayList<>();
            for (Subsidiary subsidiary : subsidiaryList) {
                longList.add(subsidiary.getSubsidiaryId());
            }

            for (StoreVO storeVO : storeList) {
                if (longList.indexOf(storeVO.getSubCompanyId()) != -1) {
                    storeListResult.add(storeVO);
                }
                storeVO.setOrgId(storeVO.getStoreId());
                storeVO.setOrgK3Number(storeVO.getK3Number());
                storeVO.setOrgName(storeVO.getName());
            }


            map.put("list", longList);
        } else if (companyType == 2) {
            //如果是子公司
            map.put("subCompanyId", companyId);
            for (StoreVO storeVO : storeList) {
                storeVO.setOrgId(storeVO.getStoreId());
                storeVO.setOrgK3Number(storeVO.getK3Number());
                storeVO.setOrgName(storeVO.getName());
                if (companyId == storeVO.getSubCompanyId()) {
                    storeListResult.add(storeVO);
                }
            }
        }


        //查看子公司列表信息
        Map mapCompany = new HashMap();
        List<Subsidiary> subsidiaryList = companyDao.selectSubsidiaryList(mapCompany);
        for (StoreVO storeVO : storeListResult) {
            for (Subsidiary subsidiary : subsidiaryList) {
                if (storeVO.getSubCompanyId().equals(subsidiary.getSubsidiaryId())) {
                    storeVO.setSubCompanyName(subsidiary.getSubsidiaryName());
                }
            }

            //循环匹配门店分类的名称
            for (StoreCategory storeCategory : storeCategoryList) {
                if (storeVO.getStoreCategoryId().equals(storeCategory.getStoreCategoryId())) {
                    storeVO.setStoreCategoryName(storeCategory.getStoreCategoryName());
                }
            }

            //循环匹配门店行业的名称
            for (Industry industry : industryList) {
                if (storeVO.getIndustryID().equals(industry.getIndustryID())) {
                    storeVO.setIndustryName(industry.getIndustryName());
                }
            }
        }

        Map mapRsultPage = ListPageUntil.listPage(storeListResult, pageSize, pageNum);
        Map mapResult = new HashMap();
        if (((List) mapRsultPage.get("list")).size() > 0) {
            mapResult.put("list", mapRsultPage.get("list"));
            mapResult.put("total", mapRsultPage.get("total"));
            return ResponseResult.success(mapResult);
        }
        return ResponseResult.error(new Error(ResponseCodeStoreEnum.STORELIST_ISNOTEXICT.getCode(), ResponseCodeStoreEnum.STORELIST_ISNOTEXICT.getDesc()));
    }

    @Override
    public ResponseResult selectStoreListNoPage(String companyId, String subCompanyId, String storeId, String productCode, String experienceCardNum, Long companyType) {
        String companyIds = "";
        if (subCompanyId != null) {//如果是子公司查看
            companyIds = subCompanyId;
        } else if (companyId != null) {//如果是总公司查看
            //根据总公司查看子公司
            List<Subsidiary> subsidiaryList = companyDao.selectSubsidiaryByParentID(Long.parseLong(companyId));
            for (Subsidiary subsidiary : subsidiaryList) {
                companyIds = companyIds + "," + subsidiary.getSubsidiaryId();
            }
        }


        Map map = new HashMap();
        /* map.put("subCompanyId", subCompanyId);*/
        map.put("storeId", storeId);
        List<StoreVO> storeListResult = storeDao.selectStoretList(map);
        List<StoreVO> storeList = new ArrayList<>();
        if (companyIds != "") {
            for (StoreVO storeVO : storeListResult) {
                if (companyIds.indexOf(storeVO.getSubCompanyId().toString()) != -1) {
                    storeList.add(storeVO);
                }
            }
        } else {
            storeList.addAll(storeListResult);
        }

        if (productCode != null && !productCode.equals("")) {
            List<Map> mapList = (List<Map>) productApi.selectAllProduct().getResult();
            for (StoreVO storeVO : storeList) {
                for (Map mapItem : mapList) {
                    if (mapItem.get("productCode").toString().equals(productCode) && mapItem.get("storeIds").toString().indexOf(storeVO.getStoreId().toString()) != -1) {
                        storeVO.setIsChecked(1);
                    }
                }
            }
        }
        if (experienceCardNum != null && !experienceCardNum.equals("")) {
            List<Experiencecard> mapList = experienceCardDao.selectExperienceCardList(new HashMap());
            for (StoreVO storeVO : storeList) {
                for (Experiencecard mapItem : mapList) {
                    if (mapItem.getCardNum().equals(experienceCardNum) && mapItem.getStoreId().indexOf(storeVO.getStoreId().toString()) != -1) {
                        storeVO.setIsChecked(1);
                    }
                }
            }
        }

        return ResponseResult.success(storeList);
    }

    @Override
    public ResponseResult selectStoreById(Long storeId) {
        if (storeId == null) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.STOREID_ISNOTNULL.getCode(), ResponseCodeStoreEnum.STOREID_ISNOTNULL.getDesc()));
        }
        return ResponseResult.success(storeDao.selectStoreById(storeId));
    }

    @Override
    public ResponseResult selectStoreByCodeOrName(Long storeId, String code, String name) {
        /*if (StringUtils.isBlank(code) && StringUtils.isBlank(name)) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.STORE_QUERY_CONDITION_NULL.getCode(), ResponseCodeStoreEnum.STORE_QUERY_CONDITION_NULL.getDesc()));
        }*/
        Map map = new HashMap();
        map.put("code", code);
        map.put("name", name);
        map.put("storeId", storeId);
        List<Store> storeList = storeDao.selectStoreByCodeOrName(map);
        if (storeList.size() != 0) {
            return ResponseResult.success(storeList);
        } else {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.STORELIST_ISNOTEXICT.getCode(), ResponseCodeStoreEnum.STORELIST_ISNOTEXICT.getDesc()));
        }

    }

    @Override
    public ResponseResult selectStoresByIds(Map map) {
        List<Store> result = storeDao.selectStoresByIds(map);

        return ResponseResult.success(result);
    }

    @Override
    public ResponseResult insertStore(Store store) {
        store.setPhoneNumber(store.getPhoneNumber().trim());
        //判断门店k3number是否重复
        int resultCheckIntK3Number = storeDao.checkStoreK3Number(store.getK3Number());
        if (resultCheckIntK3Number > 0) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.STORE_K3NUMBER_EXIT.getCode(), ResponseCodeStoreEnum.STORE_K3NUMBER_EXIT.getDesc()));
        }

        //判断门店编号是否重复
        int resultCode = storeDao.checkStoreNumber(store.getCode());
        if (resultCode > 0) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.STORE_CODE_EXIT.getCode(), ResponseCodeStoreEnum.STORE_CODE_EXIT.getDesc()));
        }


        //判断名字是否重复
        int resultCheckInt = storeDao.checkStoreName(store.getName());
        if (resultCheckInt > 0) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.STORE_NAME_EXIT.getCode(), ResponseCodeStoreEnum.STORE_NAME_EXIT.getDesc()));
        }
        if (!PhoneUtils.isMobileNO(store.getPhoneNumber()) && !PhoneUtils.isFixedLinePhone(store.getPhoneNumber())) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.MOBILE_ILLEGAL.getCode(), ResponseCodeStoreEnum.MOBILE_ILLEGAL.getDesc()));

        }
        /*store.setCode(NumberUtils.getRandomOrderNoChild());*/
        //查看子公司信息
        Subsidiary subsidiary = companyDao.selectSubsidiaryByID(store.getSubCompanyId());
        //查看总公司信息
        Company company = companyDao.selectCompanyByID(subsidiary.getParentCompany());
        String parentK3Number = company.getK3Number();
        store.setDataCentreUserName(subsidiary.getDataCentreUserName());
        store.setDataCentrePassword(subsidiary.getDataCentrePassword());
        storeDao.insertStore(store);
        //K3添加分公司
        ResponseResult responseResultSave = null;

        if (StringUtils.isNotBlank(subsidiary.getK3Number()) &&
                StringUtils.isNotBlank(subsidiary.getDataCentreUserName()) &&
                StringUtils.isNotBlank(subsidiary.getDataCentrePassword())) {
            responseResultSave = k3Api_organization.save(null, company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), store.getName(), store.getK3Number(), "105", true, "2", parentK3Number, true, "101,102,103,104", true);
            if (responseResultSave.getResult().equals("暂未登录")) {
                return ResponseResult.success("K3组织未生成，K3用户密码错误");
            }
        }
        if (!((Map) (((Map) (((Map) (responseResultSave.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("K3组织保存未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResultSave.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        ResponseResult responseResultShow = k3Api_organization.view(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), store.getK3Number(), null);
        String k3Id = ((Map) (((Map) (((Map) responseResultShow.getResult()).get("Result"))).get("Result"))).get("Id").toString();
        //修改组织的职能
        k3Api_organization.updateOrgFunctions(company.getDataCentreName(),k3Id);
        Store storeUpdate = new Store();
        storeUpdate.setStoreId(store.getStoreId());
        storeUpdate.setK3Number(store.getK3Number());
        storeUpdate.setK3Id(k3Id);
        storeDao.updateStoreByCodeOrId(storeUpdate);
        //组织生成成功后，建立组织隶属管理
        //首先查询每种编号的隶属关系方案的id
        ResponseResult responseResultLiShuId = k3Api_organization.queryOrganizationLiShu(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), "ORG_Affiliation", "FAFFILIATIONID");
        List resultList = (List) (responseResultLiShuId.getResult());

        List<Integer> stringListIds = new ArrayList<>();
        List<String> fTypeListStr = new ArrayList<>();
        fTypeListStr.add("001");
        fTypeListStr.add("002");
        fTypeListStr.add("003");
        fTypeListStr.add("004");
        fTypeListStr.add("007");
        fTypeListStr.add("008");
        fTypeListStr.add("009");
        fTypeListStr.add("010");
        fTypeListStr.add("011");
        fTypeListStr.add("012");
        fTypeListStr.add("013");
        fTypeListStr.add("014");

        for (int m = 0; m < resultList.size(); m++) {
            stringListIds.add(Integer.parseInt(((List) (resultList.get(m))).get(0).toString()));
        }
        JSONArray jsonArray = new JSONArray();
        Integer type = null;
        Boolean isDeleteEntry = null;
        if (resultList.size() == 0) {
            type = 0;
            isDeleteEntry = true;
            //如果还没添加隶属关系-要添加上级
            for (String stringListId : fTypeListStr) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fType", stringListId);
                jsonArray.add(jsonObject);
            }
        } else {
            type = 1;
            isDeleteEntry = false;
            //如果已经有了就直接添加在上级下面
            for (Integer stringListId : stringListIds) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", stringListId);
                jsonArray.add(jsonObject);
            }
        }
        ResponseResult responseResultbatchSaveLiShu = k3Api_organization.batchSaveLiShu(company.getK3Number(), store.getK3Number(), company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), null, null, company.getK3Number(), null, null, null, null, null, jsonArray.toJSONString(), type, isDeleteEntry);
        if (!((Map) (((Map) (((Map) (responseResultbatchSaveLiShu.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("K3组织生成成功，K3组织隶属关系保存未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResultSave.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }

        //组织生成成功后，建立业务关系
        //首先查询每种编号的业务关系的id
        List<String> stringList = new ArrayList<>();
        stringList.add("YWGXLX01");
        stringList.add("YWGXLX04");
        stringList.add("YWGXLX05");
        stringList.add("YWGXLX06");
        stringList.add("YWGXLX08");
        stringList.add("YWGXLX09");
        stringList.add("YWGXLX10");
        stringList.add("YWGXLX12");
        //查看k3是否已经有业务组织关系
        ResponseResult responseResultbatchYeWuKeys = k3Api_organization.queryOrganizationYeWuZuZhi(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), "ORG_BizRelation", "FBIZRELATIONID,FBRTypeId");
        List<List<String>> stringListfBIZRELATIONIDS = (List) (responseResultbatchYeWuKeys.getResult());
        JSONArray jsonArrayYeWu = new JSONArray();
        if (stringListfBIZRELATIONIDS.size() == 0) {
            //如果不存在
            for (String s : stringList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fBIZRELATIONID", 0);
                jsonObject.put("fBRTypeIdNumber", s);
                JSONArray jsonArray1 = new JSONArray();
                JSONObject jsonObject1 = new JSONObject();
                JSONObject jsonObject2 = new JSONObject();
                if (s.equals("YWGXLX08") ||
                        s.equals("YWGXLX01") ||
                        s.equals("YWGXLX09")) {
                    jsonObject1.put("fRelationOrgID", store.getK3Number());
                    jsonObject1.put("fOrgId", company.getK3Number());

                    jsonObject2.put("fRelationOrgID", company.getK3Number());//受托
                    jsonObject2.put("fOrgId", company.getK3Number());//委托
                } else {
                    jsonObject1.put("fRelationOrgID", company.getK3Number());
                    jsonObject1.put("fOrgId", store.getK3Number());

                    jsonObject2.put("fRelationOrgID", company.getK3Number());//受托
                    jsonObject2.put("fOrgId", company.getK3Number());//委托
                }
                jsonArray1.add(jsonObject1);
                jsonArray1.add(jsonObject2);
                jsonObject.put("fBizrelationEntryArray", jsonArray1.toJSONString());
                jsonArrayYeWu.add(jsonObject);
            }
        } else {

            for (List<String> s : stringListfBIZRELATIONIDS) {
                //查询出所有编号id对应的信息
                ResponseResult responseResult = k3Api_organization.viewYeWuZuZhi(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), null, String.valueOf(s.get(0)));
                List<Map> OrgBizRelationEntryList = (List<Map>) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("Result"))).get("OrgBizRelationEntry"));

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fBIZRELATIONID", s.get(0));
                jsonObject.put("fBRTypeIdNumber", null);
                JSONArray jsonArray1 = new JSONArray();

                for (Map map : OrgBizRelationEntryList) {
                    JSONObject jsonObjectOld = new JSONObject();
                    jsonObjectOld.put("fOrgId", ((Map) (map.get("OrgId"))).get("Number"));//委托
                    jsonObjectOld.put("fRelationOrgID", ((Map) (map.get("RelationOrgId"))).get("Number"));//受托
                    jsonArray1.add(jsonObjectOld);
                }

                if (String.valueOf(s.get(1)).equals("112") ||
                        String.valueOf(s.get(1)).equals("101") ||
                        String.valueOf(s.get(1)).equals("107")) {
                    jsonObject.put("fRelationOrgID", store.getK3Number());
                    jsonObject.put("fOrgId", company.getK3Number());
                } else {
                    jsonObject.put("fRelationOrgID", company.getK3Number());//受托
                    jsonObject.put("fOrgId", store.getK3Number());//委托
                }
                jsonArray1.add(jsonObject);

                jsonObject.put("fBizrelationEntryArray", jsonArray1.toJSONString());
                jsonArrayYeWu.add(jsonObject);
            }

        }


        ResponseResult responseResultbatchSaveYeWu = k3Api_organization.batchSaveYeWuZuZhi(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), jsonArrayYeWu.toJSONString());
        if (!((Map) (((Map) (((Map) (responseResultbatchSaveYeWu.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("K3组织生成成功，隶属关系生成成功，K3组织业务关系保存未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResultbatchSaveYeWu.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }

        //基础资料控制策略
        ResponseResult responseResultbatchKongZhiKeys = k3Api_organization.queryOrganizationKongZhiCeNue(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), "ORG_BaseDataControlPolicy", "FPolicyID,FBaseDataTypeId");
        JSONArray jsonArrayKongZhi = new JSONArray();
        List<List<String>> stringListFPolicyIDs = (List) (responseResultbatchKongZhiKeys.getResult());
        for (List<String> stringListFPolicyID : stringListFPolicyIDs) {
            if (!stringListFPolicyID.get(1).equals("HR_ORG_HRPOST")) {
                JSONObject object = new JSONObject();
                object.put("fpolicyid", stringListFPolicyID.get(0));
                object.put("ftargetorgid", store.getK3Number());
                jsonArrayKongZhi.add(object);
            }
        }

        ResponseResult responseResultbatchKongZhi = k3Api_organization.batchSaveKongZhiCeNue(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), jsonArrayKongZhi.toJSONString());

        k3Api_organization.organizationJiChuKongZhiSaveGW(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(),store.getK3Number());

        if (!((Map) (((Map) (((Map) (responseResultbatchKongZhi.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("K3组织生成成功，隶属关系生成成功，K3组织业务关系保存成功,基础资料控制资料生成失败:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResultbatchKongZhi.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }


        //添加会计核算体系
        //先查询出总公司核算体系已经有的下级组织k3number
        ResponseResult responseResultSystemView = k3Api_organization.viewAccountingSystem(company.getDataCentre(), company.getYewuDataCentreUserName(), company.getYewuDataCentrePassword(), null, "1");
        if (responseResultSystemView.getResult().equals("暂未登录")) {
            return ResponseResult.success("K3组织生成成功，隶属关系生成成功，K3组织业务关系保存成功,基础资料控制资料生成成功,会计核算体系生成失败:morningstar用户未建立，请前往k3建立");
        }
        List<Map> mapListResult = ((List<Map>) (((List<Map>) (((Map) (((Map) (((Map) (responseResultSystemView.getResult())).get("Result"))).get("Result"))).get("AcctSysEntry"))).get(0).get("ORG_ACCTSYSDETAIL")));
        JSONArray listSubK3NumberArray = new JSONArray();
        for (Map map : mapListResult) {
            JSONObject jsonSubK3Number = new JSONObject();
            String subK3Number = ((Map) (map.get("SUBORGID"))).get("Number").toString();
            jsonSubK3Number.put("subK3Number", subK3Number);
            listSubK3NumberArray.add(jsonSubK3Number);
        }
        JSONObject jsonSubK3Number = new JSONObject();
        jsonSubK3Number.put("subK3Number", store.getK3Number());
        listSubK3NumberArray.add(jsonSubK3Number);
        //在总公司基础上 生成
        ResponseResult responseResultSystem = k3Api_organization.save(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), "1", "100", listSubK3NumberArray.toJSONString(), null, null);

        JSONArray listSubK3NumberArraySelf = new JSONArray();
        JSONObject jsonSubK3NumberSelf = new JSONObject();
        jsonSubK3NumberSelf.put("subK3Number", store.getK3Number());
        listSubK3NumberArraySelf.add(jsonSubK3NumberSelf);
        //生成自己的核算体系
        ResponseResult responseResultSystemSelf = k3Api_organization.save(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), "0", store.getK3Number(), listSubK3NumberArraySelf.toJSONString(), store.getK3Number(), store.getName() + "会计核算体系");

        if (!((Map) (((Map) (((Map) (responseResultSystem.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true") &&
                !((Map) (((Map) (((Map) (responseResultSystemSelf.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("K3组织生成成功，隶属关系生成成功，K3组织业务关系保存成功,基础资料控制资料生成成功,银行账号生成成功,现金账号生成成功,会计核算体系生成失败:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResultSystemSelf.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        String systemK3Number = ((Map) (((Map) (((Map) responseResultSystemSelf.getResult()).get("Result"))))).get("Number").toString();
        Store storeUpdate2 = new Store();
        storeUpdate2.setStoreId(store.getStoreId());
        storeUpdate2.setSystemK3Number(systemK3Number);
        storeDao.updateStoreByCodeOrId(storeUpdate2);


        //生成为上级子公司的客户
        Customer customer = new Customer();
        String k3NumberLast = iCustomerDao.selectLastCustomerCode();
        String k3NumberAdd = null;
        if (k3NumberLast == null) {
            customer.setK3Number("CUST" + "0001");
        } else {
            String substring = k3NumberLast.substring(4, 8);
            int i = Integer.parseInt(substring);
            k3NumberAdd = "CUST" + String.format("%4d", i + 1).replace(" ", "0");
            customer.setK3Number(k3NumberAdd);
        }


        customer.setName(store.getName());
        customer.setCustomerType(3);//组织供应商
        customer.setRelationSubCompanyType(3);
        customer.setRelationSubCompanyId(store.getStoreId());//关联组织
        customer.setIsDefaultCust(0);
        customer.setZhongCompanyId(company.getCompanyId());
        iCustomerDao.insertCustomer(customer);
        //批量插入k3
        ResponseResult responseResultCustomer = k3Api_organization.customerSave(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), store.getName(), k3NumberAdd, subsidiary.getSubsidiaryName(), company.getK3Number(), company.getK3Number(), "KHLB001_SYS", null, null);
        String k3IdCust = (((Map) (((Map) responseResultCustomer.getResult()).get("Result")))).get("Id").toString();
        String numberCust = (((Map) (((Map) responseResultCustomer.getResult()).get("Result")))).get("Number").toString();
        //修改客户k3信息
        Customer customerUpdate = new Customer();
        customerUpdate.setId(customer.getId());
        customerUpdate.setK3Id(k3IdCust);
        customerUpdate.setK3Number(numberCust);
        iCustomerDao.updateCustomer(customerUpdate);
        //同时分配给子公司
        Subsidiary subsidiary1 = companyDao.selectSubsidiaryByID(store.getSubCompanyId());
        Map map = new HashMap();
        map.put("customerId", customer.getId());
        map.put("subCompanyId", subsidiary1.getSubsidiaryId());
        iCustomerDao.addCustomerSubCompany(map);

        //同时分配给子公司
        k3Api_organization.allocateCustomer(
                company.getDataCentre(),
                company.getYewuDataCentreUserName(),
                company.getYewuDataCentrePassword(),
                k3IdCust,
                subsidiary1.getK3Id(),
                true
        );

        //同时给此新建门店分配他的直接上级子公司作为供应商

        Map map1 = new HashMap();
        List<SupplierStore> supplierStoreList = new ArrayList<>();
        SupplierStore supplierStore = new SupplierStore();
        supplierStore.setSupplierId(subsidiary1.getSupplierId());
        supplierStore.setStoreId(store.getStoreId());
        supplierStoreList.add(supplierStore);
        map1.put("list", supplierStoreList);
        iSupplierDao.addSupplierStore(map1);
        k3Api_organization.AllocateSupplier(
                company.getDataCentre(),
                company.getYewuDataCentreUserName(),
                company.getYewuDataCentrePassword(),
                subsidiary1.getSupplierK3Id(),
                k3Id,
                true
        );


        //将零售客户分配给他
        //查询零售客户
        Customer customerLinShou = iCustomerDao.selectLinShouCustomer(new HashMap());
        k3Api_organization.allocateCustomer(
                company.getDataCentre(),
                company.getYewuDataCentreUserName(),
                company.getYewuDataCentrePassword(),
                customerLinShou.getK3Id(),
                k3Id,
                true
        );

        //分配到业务账号
        k3Api_organization.orgToUser(company.getDataCentreName(),company.getYewuDataCentreUserId(),k3Id);
        k3Api_organization.saveAdminUserById(company.getDataCentre(),company.getDataCentreUserName(),company.getDataCentrePassword(),company.getYewuDataCentreUserId());

        //生成仓库
        productApi.addStock(3l,store.getStoreId(),store.getName()+"仓库",store.getK3Number(),store.getCreateOperator(),company.getDataCentre(),company.getYewuDataCentreUserName(),company.getYewuDataCentrePassword());
        //生成核算范围
        this.insertHeSuanFanWei(store);
        //生成账簿
        this.insertZhangBu(store.getStoreId().toString(),"3");
        //启用仓库
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = sdf.format(new Date());
        k3Api_organization.startStock(company.getDataCentreName(),k3Id,startDate);
        return ResponseResult.success("K3组织生成成功，隶属关系生成成功，业务关系生成成功,基础控制资料生成成功，会计核算体系生成成功,生成组织客户成功，分配零售客户成功，分配到业务账号，核算范围生成成功，账簿生成成功，仓库生成成功并已启用");
    }


    @Override
    public ResponseResult insertHeSuanFanWei(Store store) {

        Store storeResult = storeDao.selectStoreById(store.getStoreId());
        if (StringUtils.isNotBlank(storeResult.getHeSuanFanWeiK3Number())) {
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.HESUANFANWEI_IS_EXIST.getCode(),
                    ResponseCodeCompanyEnum.HESUANFANWEI_IS_EXIST.getDesc()));
        }
        //根据分公司查看子公司
        Subsidiary subsidiary = companyDao.selectSubsidiaryByID(store.getSubCompanyId());
        //根据子公司找总公司
        Company company = companyDao.selectCompanyByID(subsidiary.getParentCompany());

        //添加核算范围
        //添加自己的核算范围
        JSONArray jsonArraySelf = new JSONArray();
        JSONObject jsonObjectSelf = new JSONObject();
        jsonObjectSelf.put("subK3Number", store.getK3Number());
        jsonArraySelf.add(jsonObjectSelf);
        ResponseResult responseResultInventoryAccountSelf = k3Api_organization.saveInventoryAccount(company.getDataCentre(), company.getYewuDataCentreUserName(), company.getYewuDataCentrePassword(), "0", storeResult.getK3Number(), storeResult.getSystemK3Number(), store.getName() + "核算范围", jsonArraySelf.toJSONString());
        if (!(Boolean) ((Map) (responseResultInventoryAccountSelf.getResult())).get("isSuccess")) {
            return ResponseResult.success("请先将分公司授权到指定业务账号");
        }

        //查看总公司核算范围的所有下级
        ResponseResult responseResult = k3Api_organization.viewInventoryAccount(company.getDataCentre(), company.getYewuDataCentreUserName(), company.getYewuDataCentrePassword(), null, "1");
        List<Map> mapList = (List<Map>) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("Result"))).get("HS_ACCTGRANGEENTRY"));
        JSONArray jsonArray = new JSONArray();
        for (Map map : mapList) {
            JSONObject jsonObject = new JSONObject();
            String subK3Number = ((Map) (map.get("Owner"))).get("Number").toString();
            jsonObject.put("subK3Number", subK3Number);
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("subK3Number", store.getK3Number());
        jsonArray.add(jsonObject);
        //添加到上级核算范围
        k3Api_organization.saveInventoryAccount(company.getDataCentre(), company.getYewuDataCentreUserName(), company.getYewuDataCentrePassword(), "1", company.getK3Number(), storeResult.getSystemK3Number(), null, jsonArray.toJSONString());

        String heSuanFanWeiK3Number = ((Map) (responseResultInventoryAccountSelf.getResult())).get("msg").toString();
        Store storeUpdate2 = new Store();
        storeUpdate2.setStoreId(store.getStoreId());
        storeUpdate2.setHeSuanFanWeiK3Number(heSuanFanWeiK3Number);
        storeDao.updateStoreByCodeOrId(storeUpdate2);
        return ResponseResult.success("K3核算范围生成成功");

    }

    @Override
    public ResponseResult insertZhangBu(String companyId, String companyType) {
        Store storeResult = storeDao.selectStoreById(Long.parseLong(companyId));
        if (StringUtils.isNotBlank(storeResult.getZhangBuK3Number())) {
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.ZHANGBU_IS_EXIST.getCode(),
                    ResponseCodeCompanyEnum.ZHANGBU_IS_EXIST.getDesc()));
        }
        //根据分公司查看子公司
        Subsidiary subsidiary = companyDao.selectSubsidiaryByID(storeResult.getSubCompanyId());
        //根据子公司找总公司
        Company company = companyDao.selectCompanyByID(subsidiary.getParentCompany());
        //添加账簿
        ResponseResult responseResultZB = k3Api_organization.saveZB(company.getDataCentre(), company.getYewuDataCentreUserName(), company.getYewuDataCentrePassword(), storeResult.getName() + "账簿", storeResult.getSystemK3Number(), storeResult.getK3Number());
        if (!((Map) (((Map) (((Map) (responseResultZB.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            //  return ResponseResult.success("账簿生成失败:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResultZB.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
            return ResponseResult.success("请先将分公司授权到指定业务账号");
        }
        String zhangbuK3Number = ((Map) (((Map) (((Map) responseResultZB.getResult()).get("Result"))))).get("Number").toString();
        Store storeUpdate2 = new Store();
        storeUpdate2.setStoreId(storeResult.getStoreId());
        storeUpdate2.setZhangBuK3Number(zhangbuK3Number);
        storeDao.updateStoreByCodeOrId(storeUpdate2);
        return ResponseResult.success("K3账簿生成成功");
    }

    @Override
    public ResponseResult updateStoreByCodeOrId(Store store) {
        if (store.getCode() == null && store.getStoreId() == null) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.STORECODE_AND_ID_ISNOTNULL.getCode(), ResponseCodeStoreEnum.STORECODE_AND_ID_ISNOTNULL.getDesc()));
        }
        if (!PhoneUtils.isMobileNO(store.getPhoneNumber()) && !PhoneUtils.isFixedLinePhone(store.getPhoneNumber())) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.MOBILE_ILLEGAL.getCode(), ResponseCodeStoreEnum.MOBILE_ILLEGAL.getDesc()));
        }
        int resultInt = storeDao.updateStoreByCodeOrId(store);
        if (resultInt <= 0) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.STORE_UPDATE_FAIL.getCode(), ResponseCodeStoreEnum.STORE_UPDATE_FAIL.getDesc()));
        }
        return ResponseResult.success((Store) this.selectStoreById(store.getStoreId()).getResult());
    }

    @Override
    public ResponseResult updateStoreLatById(Store store) {
        if (store.getStoreId()==null){
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.STOREID_ISNOTNULL.getCode(), ResponseCodeStoreEnum.STOREID_ISNOTNULL.getDesc()));
        }
        int resultInt = storeDao.updateStoreByCodeOrId(store);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteStore(Long storeId, String modifyOperator) {
        if (storeId == null) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.STOREID_ISNOTNULL.getCode(), ResponseCodeStoreEnum.STOREID_ISNOTNULL.getDesc()));
        }
        Map map = new HashMap();
        map.put("storeId", storeId);
        map.put("modifyOperator", modifyOperator);
        int resultInt = storeDao.deleteStore(map);
        //同时删除此门店所有的权限
        authApi.deleteuserByCompany(storeId, 3l);
        if (resultInt <= 0) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.DELETE_STORE_FAIL.getCode(), ResponseCodeStoreEnum.DELETE_STORE_FAIL.getDesc()));
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult storeEnableOrDisEnable(Long storeId) {
        if (storeId == null) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.STOREID_ISNOTNULL.getCode(), ResponseCodeStoreEnum.STOREID_ISNOTNULL.getDesc()));
        }
        Integer isEnable = ((Store) this.selectStoreById(storeId).getResult()).getIsEnableAppointment();
        if (isEnable == 0) {
            Store storeForUpdate = new Store();
            storeForUpdate.setStoreId(storeId);
            storeForUpdate.setIsEnableAppointment(1);
            return this.updateStoreByCodeOrId(storeForUpdate);
        } else {
            Store storeForUpdate = new Store();
            storeForUpdate.setStoreId(storeId);
            storeForUpdate.setIsEnableAppointment(0);
            return this.updateStoreByCodeOrId(storeForUpdate);
        }
    }

    @Override
    public ResponseResult storeCategoryList(int pageNum, int pageSize, String keyWordStoreCategoryName) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("keyWordStoreCategoryName", keyWordStoreCategoryName);
        List<StoreCategory> storeCategoryList = storeDao.storeCategoryList(map);
        if (storeCategoryList.size() > 0) {
            PageInfo pageInfo = new PageInfo(storeCategoryList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeStoreEnum.STORECATEGORYLIST_ISNOTEXICT.getCode(), ResponseCodeStoreEnum.STORECATEGORYLIST_ISNOTEXICT.getDesc()));
    }

    @Override
    public ResponseResult storeCategoryListNoPage() {
        Map map = new HashMap();
        List<StoreCategory> storeCategoryList = storeDao.storeCategoryList(map);
        return ResponseResult.success(storeCategoryList);
    }

    @Override
    public ResponseResult updateStoreCategory(StoreCategory storeCategory) {
        storeDao.updateStoreCategory(storeCategory);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult insertStoreCategory(StoreCategory storeCategory) {
        //查看门店分类名称是否存在
        int resultInt = storeDao.checkStoreCategoryName(storeCategory);
        if (resultInt>0){
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.STORE_STORECATEGORY_NAME_EXIST.getCode(),
                    ResponseCodeStoreEnum.STORE_STORECATEGORY_NAME_EXIST.getDesc()));
        }

        storeDao.insertStoreCategory(storeCategory);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteStoreCategory(Long storeCategoryId) {
        storeDao.deleteStoreCategory(storeCategoryId);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectStoreListByCompanyId(Long companyId) {
        List<Store> storeList = storeDao.selectStoreListByCompanyId(companyId);
        return ResponseResult.success(storeList);
    }

    @Override
    public ResponseResult selectSubCompanyAndStoreNoPage(Long companyId, Integer companyType) {
        List<Subsidiary> subsidiaryList = null;
        if (companyType.equals(1)) {
            //如果是总公司
            subsidiaryList = companyDao.selectSubsidiaryByParentID(companyId);
        } else if (companyType.equals(2)) {
            //如果是子公司
            Map map = new HashMap();
            map.put("subsidiaryId", companyId);
            subsidiaryList = companyDao.selectSubsidiaryList(map);
        } else if (companyType.equals(0)) {
            subsidiaryList = companyDao.selectSubsidiaryByParentID(null);
        }
        Map map = new HashMap();
        List<StoreVO> storeVOList = storeDao.selectStoretList(map);
        for (Subsidiary subsidiary : subsidiaryList) {
            List<StoreVO> storeList = new ArrayList<>();
            for (StoreVO storeVO : storeVOList) {
                if (subsidiary.getSubsidiaryId().equals(storeVO.getSubCompanyId())) {
                    storeList.add(storeVO);
                }
            }
            subsidiary.setStoreVOList(storeList);
        }
        if (subsidiaryList != null && subsidiaryList.size() > 0) {
            return ResponseResult.success(subsidiaryList);
        }
        return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_NOT_FIND.getCode(),
                ResponseCodeCompanyEnum.COMPANY_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectStoreListBySubCompany(int pageNum, int pageSize, Long subsidiaryId, String keyWord) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap<>();
        map.put("subsidiaryId", subsidiaryId);
        map.put("keyWord", keyWord);
        List<Store> storeList = storeDao.selectStoreListBySubCompany(map);
        if (storeList != null && storeList.size() > 0) {
            PageInfo pageInfo = new PageInfo(storeList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeStoreEnum.STORELIST_ISNOTEXICT.getCode(), ResponseCodeStoreEnum.STORELIST_ISNOTEXICT.getDesc()));
    }

    @Override
    public ResponseResult selectStoreListBySubCompanyNoPage(Long subsidiaryId) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("subsidiaryId", subsidiaryId);
        List<Store> storeList = storeDao.selectStoreListBySubCompany(map);
        if (storeList != null && storeList.size() > 0) {
            return ResponseResult.success(storeList);
        }
        return ResponseResult.error(new Error(ResponseCodeStoreEnum.STORELIST_ISNOTEXICT.getCode(), ResponseCodeStoreEnum.STORELIST_ISNOTEXICT.getDesc()));
    }

    @Override
    public ResponseResult addStoreMemoNum(StoreMemoNum storeMemoNum) {
        if (storeMemoNum.getStoreId() == null) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.STORELIST_ISNOTEXICT.getCode(), ResponseCodeStoreEnum.STORELIST_ISNOTEXICT.getDesc()));
        }
        if (storeMemoNum.getMemoNumStart() == null) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.MEMO_START_NUM.getCode(), ResponseCodeStoreEnum.MEMO_START_NUM.getDesc()));
        }
        if (storeMemoNum.getMemoNumEnd() == null) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.MEMO_END_NUM.getCode(), ResponseCodeStoreEnum.MEMO_END_NUM.getDesc()));
        }
        storeDao.addStoreMemoNum(storeMemoNum);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult listStoreMemo(int pageNum, int pageSize, StoreMemoNum storeMemoNum) {
        PageHelper.startPage(pageNum, pageSize);
        if (storeMemoNum.getStoreId() == null) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.STORELIST_ISNOTEXICT.getCode(), ResponseCodeStoreEnum.STORELIST_ISNOTEXICT.getDesc()));
        }

        List<StoreMemoNum> storeMemoNumList = storeDao.listStoreMemo(storeMemoNum);
        if (storeMemoNumList != null && storeMemoNumList.size() > 0) {
            PageInfo pageInfo = new PageInfo(storeMemoNumList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeStoreEnum.MEMO_NUM_NULL.getCode(), ResponseCodeStoreEnum.MEMO_NUM_NULL.getDesc()));

    }

    @Override
    public ResponseResult checkMenoContinuous(StoreMemoNum storeMemoNum) {
        //查询指定水单号范围内的订单
        List<Map> mapList = (List<Map>) orderApi.selectOrderByMemoScope(storeMemoNum.getStoreId(), storeMemoNum.getMemoNumStart(), storeMemoNum.getMemoNumEnd()).getResult();
        List<ExperiencecardOrder> experiencecardOrderList = experienceCardDao.selectExpCardOrder(new HashMap());


        if (mapList.size() == 0) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.MEMO_SCOPE_ORDER_NOT_EXIST.getCode(), ResponseCodeStoreEnum.MEMO_SCOPE_ORDER_NOT_EXIST.getDesc()));
        }
        int[] memoNumArray = new int[mapList.size()+experiencecardOrderList.size()];
        for (int i = 0; i < mapList.size(); i++) {
            memoNumArray[i] = Integer.parseInt(mapList.get(i).get("memoNum").toString());
        }
        for (int i = 0; i < experiencecardOrderList.size(); i++) {
            memoNumArray[i] = Integer.parseInt(experiencecardOrderList.get(i).getMemoNum());
        }

        boolean isContinuous = MemoContinuousCheckUtil.IsContinous(memoNumArray);
        Map mapListOrder = new HashMap();
        mapListOrder.put("orderList", mapList);
        if (isContinuous) {
            //相邻连续
            mapListOrder.put("isContinuous", 1);
        } else {
            //不相邻连续
            mapListOrder.put("isContinuous", 0);
        }
        return ResponseResult.success(mapListOrder);
    }

    @Override
    public ResponseResult selectCompanyAndStoreNoPage(Long companyId, Integer companyType) {
        List<CompanyVO> companyVOList = new ArrayList<>();
        if (companyType.equals(1)) {
            //平级查所有
            Map map = new HashMap();
            List<Company> companyList = companyDao.selectCompanyList(map);
            if (companyList != null && companyList.size() > 0) {
                for (Company company : companyList) {
                    CompanyVO voC = new CompanyVO();
                    voC.setCompanyId(company.getCompanyId());
                    voC.setCompanyName(company.getCompanyName());
                    voC.setCompanyType(1);
                    voC.setParentId(0l);
                    companyVOList.add(voC);
                }
            }
            //自己的下级
            List<Subsidiary> subsidiaryList = companyDao.selectSubsidiaryByParentID(companyId);
            if (subsidiaryList != null && subsidiaryList.size() > 0) {
                for (Subsidiary subsidiary : subsidiaryList) {
                    CompanyVO voS = new CompanyVO();
                    Long subsidiaryId = subsidiary.getSubsidiaryId();
                    voS.setCompanyId(subsidiaryId);
                    voS.setCompanyName(subsidiary.getSubsidiaryName());
                    voS.setCompanyType(2);
                    voS.setParentId(subsidiary.getParentCompany());
                    companyVOList.add(voS);
                    //门店
                    HashMap<Object, Object> map1 = new HashMap<>();
                    map.put("subsidiaryId", subsidiaryId);
                    List<Store> storeList = storeDao.selectStoreListBySubCompany(map1);
                    if (storeList != null && storeList.size() > 0) {
                        for (Store store : storeList) {
                            CompanyVO voC = new CompanyVO();
                            voC.setCompanyId(store.getStoreId());
                            voC.setCompanyName(store.getName());
                            voC.setCompanyType(3);
                            voC.setParentId(store.getSubCompanyId());
                            companyVOList.add(voC);
                        }
                    }
                }
            }
        } else if (companyType.equals(2)) {
            //如果是子公司
            //子公司上级
            Subsidiary subsidiary1 = companyDao.selectSubsidiaryByID(companyId);
            Long parentCompany = subsidiary1.getParentCompany();
            //同总公司下的子公司
            Map map = new HashMap();
            map.put("parentCompany", parentCompany);
            List<Subsidiary> subsidiaryList = companyDao.selectSubsidiaryList(map);
            if (subsidiaryList != null && subsidiaryList.size() > 0) {
                for (Subsidiary subsidiary : subsidiaryList) {
                    CompanyVO voS = new CompanyVO();
                    Long subsidiaryId = subsidiary.getSubsidiaryId();
                    voS.setCompanyId(subsidiaryId);
                    voS.setCompanyName(subsidiary.getSubsidiaryName());
                    voS.setCompanyType(2);
                    voS.setParentId(subsidiary.getParentCompany());
                    companyVOList.add(voS);
                    //门店
                    HashMap<Object, Object> map1 = new HashMap<>();
                    map.put("subsidiaryId", subsidiaryId);
                    List<Store> storeList = storeDao.selectStoreListBySubCompany(map1);
                    if (storeList != null && storeList.size() > 0) {
                        for (Store store : storeList) {
                            CompanyVO voC = new CompanyVO();
                            voC.setCompanyId(store.getStoreId());
                            voC.setCompanyName(store.getName());
                            voC.setCompanyType(3);
                            voC.setParentId(store.getSubCompanyId());
                            companyVOList.add(voC);
                        }
                    }
                }
            }
        } else if (companyType.equals(3)) {
            //如果是门店
            Map map = new HashMap();
            List<StoreVO> storeList = storeDao.selectStoretList(map);
            if (storeList != null && storeList.size() > 0) {
                for (StoreVO store : storeList) {
                    CompanyVO voC = new CompanyVO();
                    voC.setCompanyId(store.getStoreId());
                    voC.setCompanyName(store.getName());
                    voC.setCompanyType(3);
                    voC.setParentId(store.getSubCompanyId());
                    companyVOList.add(voC);
                }
            }
        }
        if (companyVOList != null && companyVOList.size() > 0) {
            return ResponseResult.success(companyVOList);
        }
        return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_NOT_FIND.getCode(),
                ResponseCodeCompanyEnum.COMPANY_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectStoreAll() {
        List<StoreVO> storeVOS = storeDao.selectStoretList(new HashMap());
        return ResponseResult.success(storeVOS);
    }
}
