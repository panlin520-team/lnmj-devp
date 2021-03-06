package com.lnmj.store.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeCompanyEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeStoreEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.ListPageUntil;
import com.lnmj.common.utils.NumberUtils;
import com.lnmj.common.utils.PhoneUtils;
import com.lnmj.store.business.ICompanyService;
import com.lnmj.store.entity.*;
import com.lnmj.store.entity.VO.StoreVO;
import com.lnmj.store.repository.ICompanyDao;
import com.lnmj.store.repository.ICustomerDao;
import com.lnmj.store.repository.IStoreDao;
import com.lnmj.store.repository.ISupplierDao;
import com.lnmj.store.serviceProxy.AuthApi;
import com.lnmj.store.serviceProxy.K3Api_organization;
import com.lnmj.store.serviceProxy.ProductApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: yilihua
 * @Date: 2019/9/19 18:21
 * @Description: 公司service
 */
@Transactional
@Service("companyService")
public class CompanyService implements ICompanyService {
    @Resource
    private ICompanyDao companyDao;
    @Resource
    private AuthApi authApi;
    @Resource
    private IStoreDao iStoreDao;
    @Resource
    private ISupplierDao iSupplierDao;
    @Resource
    private ICustomerDao iCustomerDao;
    @Resource
    private K3Api_organization k3Api_organization;
    @Resource
    private ProductApi productApi;

    @Override
    public ResponseResult updateSubsidiaryByID(Subsidiary subsidiary) {
        //是否存在subsidiary
        Long subsidiaryId = subsidiary.getSubsidiaryId();
        Subsidiary subsidiaryByID = companyDao.selectSubsidiaryByID(subsidiaryId);
        if (subsidiaryByID == null) {
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.SUBSIDIARY_NOT_FIND.getCode(),
                    ResponseCodeCompanyEnum.SUBSIDIARY_NOT_FIND.getDesc()));
        }
        int i = companyDao.updateSubsidiaryByID(subsidiary);
        Company company = companyDao.selectCompanyByID(subsidiaryByID.getParentCompany());
        if (StringUtils.isNotBlank(subsidiaryByID.getK3Id())) {
            //如果有对应的K3的id
            ResponseResult responseResultEdit = k3Api_organization.save(subsidiaryByID.getK3Id(), company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), subsidiary.getSubsidiaryName(), subsidiaryByID.getK3Number(), "102", true, "2", company.getK3Number(), true, "101,102,103,104", true);
            if (!((Map) (((Map) (((Map) (responseResultEdit.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
                return ResponseResult.success("K3组织修改未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResultEdit.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
            } else {
                return ResponseResult.success("K3组织修改成功");
            }
        }
        if (i > 0) {
            return ResponseResult.success(i);
        }
        return ResponseResult.error(new Error(ResponseCodeCompanyEnum.SUBSIDIARY_UPDATE_FAILD.getCode(),
                ResponseCodeCompanyEnum.SUBSIDIARY_UPDATE_FAILD.getDesc()));
    }

    @Override
    public ResponseResult insertSubsidiary(Subsidiary subsidiary) {
        subsidiary.setPhoneNumber(subsidiary.getPhoneNumber().trim());
        //验证子公司k3number 和名称是否存在subsidiaryCheck
        Subsidiary subsidiaryCheckName = new Subsidiary();
        subsidiaryCheckName.setSubsidiaryName(subsidiary.getSubsidiaryName());
        int resultIntName = companyDao.checkSubCompany(subsidiaryCheckName);
        if (resultIntName > 0) {
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.SUB_COMPANY_NAME_EXIST.getCode(), ResponseCodeCompanyEnum.SUB_COMPANY_NAME_EXIST.getDesc()));
        }

        Subsidiary subsidiaryCheckK3Number = new Subsidiary();
        subsidiaryCheckK3Number.setK3Number(subsidiary.getK3Number());
        int resultIntK3Number = companyDao.checkSubCompany(subsidiaryCheckK3Number);
        if (resultIntK3Number > 0) {
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.SUB_COMPANY_K3NUMBER_EXIST.getCode(), ResponseCodeCompanyEnum.SUB_COMPANY_K3NUMBER_EXIST.getDesc()));
        }

        String phoneNumberRe = subsidiary.getPhoneNumber().replace(" ", "");
        if (!PhoneUtils.isMobileNO(phoneNumberRe) && !PhoneUtils.isFixedLinePhone(phoneNumberRe)) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.MOBILE_ILLEGAL.getCode(), ResponseCodeStoreEnum.MOBILE_ILLEGAL.getDesc()));

        }
        //查看总公司信息
        Company company = companyDao.selectCompanyByID(subsidiary.getParentCompany());
        String parentK3Number = company.getK3Number();
        subsidiary.setDataCentreUserName(company.getDataCentreUserName());
        subsidiary.setDataCentrePassword(company.getDataCentrePassword());
        companyDao.insertSubsidiary(subsidiary);


        //K3添加子公司
        ResponseResult responseResultSave = null;
        if (StringUtils.isNotBlank(subsidiary.getK3Number()) &&
                StringUtils.isNotBlank(subsidiary.getDataCentreUserName()) &&
                StringUtils.isNotBlank(subsidiary.getDataCentrePassword())) {
            //K3添加子公司
            responseResultSave = k3Api_organization.save(null, company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), subsidiary.getSubsidiaryName(), subsidiary.getK3Number(), "102", true, "2", parentK3Number, true, "101,102,103,104,107,108,109,110,111,112,113,114", true);

            if (responseResultSave.getResult().equals("暂未登录")) {
                return ResponseResult.success("K3组织未生成，K3用户密码错误");
            }
        }
        if (!((Map) (((Map) (((Map) (responseResultSave.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("K3组织保存未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResultSave.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        //如果子公司添加成功，保存组织id
        //先查询出保存的组织
        ResponseResult responseResultShow = k3Api_organization.view(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), subsidiary.getK3Number(), null);
        String k3Id = ((Map) (((Map) (((Map) responseResultShow.getResult()).get("Result"))).get("Result"))).get("Id").toString();
        //修改组织的职能
        k3Api_organization.updateOrgFunctions(company.getDataCentreName(), k3Id);

        Subsidiary subsidiaryUpdate = new Subsidiary();
        subsidiaryUpdate.setSubsidiaryId(subsidiary.getSubsidiaryId());
        subsidiaryUpdate.setK3Id(k3Id);
        subsidiaryUpdate.setK3Number(subsidiary.getK3Number());
        companyDao.updateSubsidiaryByID(subsidiaryUpdate);
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
        ResponseResult responseResultbatchSaveLiShu = k3Api_organization.batchSaveLiShu(company.getK3Number(), subsidiary.getK3Number(), company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), null, null, company.getK3Number(), null, null, null, null, null, jsonArray.toJSONString(), type, isDeleteEntry);
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
                    jsonObject1.put("fRelationOrgID", subsidiary.getK3Number());
                    jsonObject1.put("fOrgId", company.getK3Number());

                    jsonObject2.put("fRelationOrgID", company.getK3Number());//受托
                    jsonObject2.put("fOrgId", company.getK3Number());//委托
                } else {
                    jsonObject1.put("fRelationOrgID", company.getK3Number());
                    jsonObject1.put("fOrgId", subsidiary.getK3Number());

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
                    jsonObject.put("fRelationOrgID", subsidiary.getK3Number());
                    jsonObject.put("fOrgId", company.getK3Number());
                } else {
                    jsonObject.put("fRelationOrgID", company.getK3Number());//受托
                    jsonObject.put("fOrgId", subsidiary.getK3Number());//委托
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
        ResponseResult responseResultbatchKongZhi = null;
        if (stringListFPolicyIDs.size() == 0) {
            //如果还没有建立过基础资料先建立
            responseResultbatchKongZhi = k3Api_organization.organizationJiChuKongZhiSave(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), company.getK3Number(), subsidiary.getK3Number());
        } else {
            for (List<String> stringListFPolicyID : stringListFPolicyIDs) {
                if (!stringListFPolicyID.get(1).equals("HR_ORG_HRPOST")) {
                    JSONObject object = new JSONObject();
                    object.put("fpolicyid", stringListFPolicyID.get(0));
                    object.put("ftargetorgid", subsidiary.getK3Number());
                    jsonArrayKongZhi.add(object);
                }
            }
            responseResultbatchKongZhi = k3Api_organization.batchSaveKongZhiCeNue(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), jsonArrayKongZhi.toJSONString());
        }
        k3Api_organization.organizationJiChuKongZhiSaveGW(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), subsidiary.getK3Number());

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
        jsonSubK3Number.put("subK3Number", subsidiary.getK3Number());
        listSubK3NumberArray.add(jsonSubK3Number);
        //在总公司基础上 生成
        ResponseResult responseResultSystem = k3Api_organization.save(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), "1", "100", listSubK3NumberArray.toJSONString(), null, null);

        JSONArray listSubK3NumberArraySelf = new JSONArray();
        JSONObject jsonSubK3NumberSelf = new JSONObject();
        jsonSubK3NumberSelf.put("subK3Number", subsidiary.getK3Number());
        listSubK3NumberArraySelf.add(jsonSubK3NumberSelf);

        //生成自己的核算体系
        ResponseResult responseResultSystemSelf = k3Api_organization.save(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), "0", subsidiary.getK3Number(), listSubK3NumberArraySelf.toJSONString(), subsidiary.getK3Number(), subsidiary.getSubsidiaryName() + "会计核算体系");

        if (!((Map) (((Map) (((Map) (responseResultSystem.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true") &&
                !((Map) (((Map) (((Map) (responseResultSystemSelf.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("K3组织生成成功，隶属关系生成成功，K3组织业务关系保存成功,基础资料控制资料生成成功,会计核算体系生成失败:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResultSystem.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        String systemK3Number = ((Map) (((Map) (((Map) responseResultSystemSelf.getResult()).get("Result"))))).get("Number").toString();
        Subsidiary subsidiaryUpdate2 = new Subsidiary();
        subsidiaryUpdate2.setSubsidiaryId(subsidiary.getSubsidiaryId());
        subsidiaryUpdate2.setSystemK3Number(systemK3Number);
        companyDao.updateSubsidiaryByID(subsidiaryUpdate2);



        /*---------------------------------------------------------------------供应商----------------------------------------------------*/

        //生成名字为子公司的供应商
        //然后生成供应商
        Supplier supplier = new Supplier();
        supplier.setSupplierName(subsidiary.getSubsidiaryName());
        supplier.setSupplierType(1);//组织供应商
        supplier.setAddress(subsidiary.getDetailedAddress());
        supplier.setLinkMan(subsidiary.getSubsidiaryName());
        supplier.setLinkPhone(subsidiary.getPhoneNumber());
        supplier.setRelationSubCompanyType(2);
        supplier.setRelationSubCompanyId(subsidiary.getSubsidiaryId());//关联组织
        supplier.setSupplierCode(NumberUtils.getRandomOrderNoChild());
        supplier.setZhongCompanyId(company.getCompanyId());
        iSupplierDao.addSupplier(supplier);
        //然后接入k3
        JSONArray jsonArraySupp = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("FLocName", supplier.getAddress()); //地址名称


        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("FNUMBER", supplier.getLinkMan());
        jsonObject.put("FLocNewContact", jsonObject1); //联系人

        jsonObject.put("FLocAddress", supplier.getAddress()); //通讯地址
        jsonObject.put("FLocMobile", supplier.getLinkPhone()); //手机
        jsonArraySupp.add(jsonObject);
        ResponseResult responseResultSupplier = k3Api_organization.saveSupplier(company.getDataCentre(), company.getYewuDataCentreUserName(), company.getYewuDataCentrePassword(), supplier.getSupplierName(), company.getK3Number(), company.getK3Number(), "PRE001", jsonArraySupp.toJSONString(), null, null);
        if (!((Map) (((Map) (((Map) (responseResultSupplier.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("k3供应商生成失败:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResultSupplier.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        supplier.setDataCentreUserName(company.getDataCentreUserName());
        supplier.setDataCentrePassword(company.getDataCentrePassword());
        String k3IdSupp = (((Map) (((Map) responseResultSupplier.getResult()).get("Result")))).get("Id").toString();
        String numberSupp = (((Map) (((Map) responseResultSupplier.getResult()).get("Result")))).get("Number").toString();
        //修改供应商k3信息
        Supplier supplierUpdate = new Supplier();
        supplierUpdate.setSupplierId(supplier.getSupplierId());
        supplierUpdate.setK3Id(k3IdSupp);
        supplierUpdate.setK3Number(numberSupp);
        iSupplierDao.updateSupplier(supplierUpdate);


        //查询所有其他子公司
        Map map = new HashMap();
        map.put("selfId", subsidiary.getSubsidiaryId());
        List<Subsidiary> subsidiaryList = companyDao.selectSubsidiaryList(map);
        //查询所有总公司下面的分公司
        List<SupplierSubCompany> supplierSubCompanyList = new ArrayList<>();
        String TOrgIds = "";
        String PkIds = "";
        //然后建立此供应商与其他子公司的关系以及其下分公司的关系-供应商-内部关系
        if (subsidiaryList.size() != 0) {
            for (Subsidiary subsidiaryOther : subsidiaryList) {
                //-----新建组织供应商分配给所有其他子公司
                SupplierSubCompany supplierSubCompany = new SupplierSubCompany();
                supplierSubCompany.setSupplierId(supplier.getSupplierId());
                supplierSubCompany.setSubCompanyId(subsidiaryOther.getSubsidiaryId());
                supplierSubCompanyList.add(supplierSubCompany);
                TOrgIds = TOrgIds + "," + subsidiaryOther.getK3Id();


                //----其他子公司分配给新建组织作为供应商
                SupplierSubCompany supplierSubCompany2 = new SupplierSubCompany();
                supplierSubCompany2.setSupplierId(subsidiaryOther.getSupplierId());
                supplierSubCompany2.setSubCompanyId(subsidiary.getSubsidiaryId());
                supplierSubCompanyList.add(supplierSubCompany2);
                PkIds = PkIds + "," + subsidiaryOther.getSupplierK3Id();

            }
            Map mapSuppSubCompany = new HashMap();
            mapSuppSubCompany.put("list", supplierSubCompanyList);
            iSupplierDao.addSupplierSubCompanyBatch(mapSuppSubCompany);
        }


        //同时分配给所有其他子公司以及当前子公司下面的所有分公司-供应商-k3关系--新建组织供应商分配给所有其他子公司和其下分公司
        if (!TOrgIds.equals("")) {
            k3Api_organization.AllocateSupplier(
                    company.getDataCentre(),
                    company.getYewuDataCentreUserName(),
                    company.getYewuDataCentrePassword(),
                    k3IdSupp,
                    TOrgIds.substring(1),
                    true
            );
        }

        if (!PkIds.equals("")) {
            k3Api_organization.AllocateSupplier(
                    company.getDataCentre(),
                    company.getYewuDataCentreUserName(),
                    company.getYewuDataCentrePassword(),
                    PkIds.substring(1),
                    k3Id,
                    true
            );
        }

        /*---------------------------------------------------------------------供应商----------------------------------------------------*/




        /*---------------------------------------------------------------------客户----------------------------------------------------*/
        //生成为其他子公司和其下分公司的客户
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


        customer.setName(subsidiary.getSubsidiaryName());
        customer.setCustomerType(3);//组织供应商
        customer.setRelationSubCompanyType(2);
        customer.setRelationSubCompanyId(subsidiary.getSubsidiaryId());//关联组织
        customer.setIsDefaultCust(0);
        customer.setZhongCompanyId(company.getCompanyId());
        iCustomerDao.insertCustomer(customer);
        //批量插入k3
        ResponseResult responseResultCustomer = k3Api_organization.customerSave(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), subsidiary.getSubsidiaryName(), k3NumberAdd, subsidiary.getSubsidiaryName(), company.getK3Number(), company.getK3Number(), "KHLB003_SYS", null, null);
        String k3IdCust = (((Map) (((Map) responseResultCustomer.getResult()).get("Result")))).get("Id").toString();
        String numberCust = (((Map) (((Map) responseResultCustomer.getResult()).get("Result")))).get("Number").toString();
        //修改客户k3信息
        Customer customerUpdate = new Customer();
        customerUpdate.setId(customer.getId());
        customerUpdate.setK3Id(k3IdCust);
        customerUpdate.setK3Number(numberCust);
        iCustomerDao.updateCustomer(customerUpdate);


        //同时分配给其他子公司
        List<CustomerSubCompany> customerSubCompanyList = new ArrayList<>();
        String TOrgIdsCust = "";
        String PkIdsCust = "";
        if (subsidiaryList.size() != 0) {
            for (Subsidiary subsidiaryOther : subsidiaryList) {
                //-----新建组织客户分配给所有其他子公司
                CustomerSubCompany customerSubCompany = new CustomerSubCompany();
                customerSubCompany.setCustomerId(customer.getId());
                customerSubCompany.setSubCompanyId(subsidiaryOther.getSubsidiaryId());
                customerSubCompanyList.add(customerSubCompany);
                TOrgIdsCust = TOrgIdsCust + "," + subsidiaryOther.getK3Id();

                //----其他子公司分配给新建组织作为供应商
                CustomerSubCompany customerSubCompany2 = new CustomerSubCompany();
                customerSubCompany2.setCustomerId(subsidiaryOther.getCustomerId());
                customerSubCompany2.setSubCompanyId(subsidiary.getSubsidiaryId());
                customerSubCompanyList.add(customerSubCompany2);
                PkIdsCust = PkIdsCust + "," + subsidiaryOther.getCustomerK3Id();
            }
            Map mapCustSubCompany = new HashMap();
            mapCustSubCompany.put("list", customerSubCompanyList);
            iCustomerDao.addCustomerSubCompanyBatch(mapCustSubCompany);
        }


        //同时分配给所有其他子公司以及当前子公司下面的所有分公司-客户
        if (!TOrgIdsCust.equals("")) {
            k3Api_organization.allocateCustomer(
                    company.getDataCentre(),
                    company.getYewuDataCentreUserName(),
                    company.getYewuDataCentrePassword(),
                    k3IdCust,
                    TOrgIdsCust.substring(1),
                    true
            );
        }

        if (!PkIdsCust.equals("")) {
            k3Api_organization.allocateCustomer(
                    company.getDataCentre(),
                    company.getYewuDataCentreUserName(),
                    company.getYewuDataCentrePassword(),
                    PkIdsCust.substring(1),
                    k3Id,
                    true
            );
        }


        //将零售客户分配给他
        //查询零售客户
        Customer customerLinShou = iCustomerDao.selectLinShouCustomer(new HashMap());
        Map map1 = new HashMap();
        map1.put("customerId", customerLinShou.getId());
        map1.put("subCompanyId", subsidiary.getSubsidiaryId());
        iCustomerDao.addCustomerSubCompany(map1);

        k3Api_organization.allocateCustomer(
                company.getDataCentre(),
                company.getYewuDataCentreUserName(),
                company.getYewuDataCentrePassword(),
                customerLinShou.getK3Id(),
                k3Id,
                true
        );

        //分配到业务账号
        k3Api_organization.orgToUser(company.getDataCentreName(), company.getYewuDataCentreUserId(), k3Id);
        k3Api_organization.saveAdminUserById(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), company.getYewuDataCentreUserId());


        //生成仓库
        productApi.addStock(2l, subsidiary.getSubsidiaryId(), subsidiary.getSubsidiaryName() + "仓库", subsidiary.getK3Number(), subsidiary.getCreateOperator(), company.getDataCentre(), company.getYewuDataCentreUserName(), company.getYewuDataCentrePassword());
        //生成核算范围
        this.insertHeSuanFanWei(subsidiary);
        //生成账簿
        this.insertZhangBu(subsidiary.getSubsidiaryId().toString(), "2");
        //启用仓库
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = sdf.format(new Date());
        k3Api_organization.startStock(company.getDataCentreName(), k3Id, startDate);


        return ResponseResult.success("K3组织生成成功，隶属关系生成成功，业务关系生成成功,基础控制资料生成成功，会计核算体系生成成功, 生成组织供应商成功,生成组织客户成功,分配零售客户成功，分配到业务账号，核算范围生成成功，账簿生成成功，仓库生成成功并已启用");


    }

    @Override
    public ResponseResult insertHeSuanFanWei(Subsidiary subsidiary) {
        Subsidiary subsidiaryResult = companyDao.selectSubsidiaryByID(subsidiary.getSubsidiaryId());

        //查看子公司是否已经生成了核算范围
        Subsidiary subsidiary1 = companyDao.selectSubsidiaryByID(subsidiary.getSubsidiaryId());
        if (StringUtils.isNotBlank(subsidiary1.getHeSuanFanWeiK3Number())) {
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.HESUANFANWEI_IS_EXIST.getCode(),
                    ResponseCodeCompanyEnum.HESUANFANWEI_IS_EXIST.getDesc()));
        }
        //根据子公司查看总公司
        Company company = companyDao.selectCompanyByID(subsidiary.getParentCompany());

        //添加核算范围
        //添加自己的核算范围
        JSONArray jsonArraySelf = new JSONArray();
        JSONObject jsonObjectSelf = new JSONObject();
        jsonObjectSelf.put("subK3Number", subsidiary.getK3Number());
        jsonArraySelf.add(jsonObjectSelf);
        ResponseResult responseResultInventoryAccountSelf = k3Api_organization.saveInventoryAccount(company.getDataCentre(), company.getYewuDataCentreUserName(), company.getYewuDataCentrePassword(), "0", subsidiaryResult.getK3Number(), subsidiaryResult.getSystemK3Number(), subsidiaryResult.getSubsidiaryName() + "核算范围", jsonArraySelf.toJSONString());
        if (!(Boolean) ((Map) (responseResultInventoryAccountSelf.getResult())).get("isSuccess")) {
            return ResponseResult.success("请先将子公司授权到指定业务账号");
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
        jsonObject.put("subK3Number", subsidiary.getK3Number());
        jsonArray.add(jsonObject);
        //添加到上级核算范围
        k3Api_organization.saveInventoryAccount(company.getDataCentre(), company.getYewuDataCentreUserName(), company.getYewuDataCentrePassword(), "1", company.getK3Number(), subsidiaryResult.getSystemK3Number(), null, jsonArray.toJSONString());

        String heSuanFanWeiK3Number = ((Map) (responseResultInventoryAccountSelf.getResult())).get("msg").toString();
        Subsidiary subsidiaryUpdate2 = new Subsidiary();
        subsidiaryUpdate2.setSubsidiaryId(subsidiary.getSubsidiaryId());
        subsidiaryUpdate2.setHeSuanFanWeiK3Number(heSuanFanWeiK3Number);
        companyDao.updateSubsidiaryByID(subsidiaryUpdate2);
        return ResponseResult.success("K3核算范围生成成功");

    }

    @Override
    public ResponseResult insertZhangBu(String companyId, String companyType) {
        //查看子公司是否已经生成了账簿
        Company companyResult = null;
        String fName = null;
        String systemK3Number = null;
        String k3Number = null;
        if (companyType.equals("1")) {
            //如果是总公司
            companyResult = companyDao.selectCompanyByID(Long.parseLong(companyId));
            if (StringUtils.isNotBlank(companyResult.getZhangBuK3Number())) {
                return ResponseResult.error(new Error(ResponseCodeCompanyEnum.ZHANGBU_IS_EXIST.getCode(),
                        ResponseCodeCompanyEnum.ZHANGBU_IS_EXIST.getDesc()));
            }
            fName = companyResult.getCompanyName() + "账簿";
            systemK3Number = "KJHSTX01_SYS";
            k3Number = companyResult.getK3Number();
        } else if (companyType.equals("2")) {
            //如果是子公司
            Subsidiary subsidiaryResult = companyDao.selectSubsidiaryByID(Long.parseLong(companyId));
            if (StringUtils.isNotBlank(subsidiaryResult.getZhangBuK3Number())) {
                return ResponseResult.error(new Error(ResponseCodeCompanyEnum.ZHANGBU_IS_EXIST.getCode(),
                        ResponseCodeCompanyEnum.ZHANGBU_IS_EXIST.getDesc()));
            }
            //根据子公司查看总公司
            companyResult = companyDao.selectCompanyByID(subsidiaryResult.getParentCompany());
            fName = subsidiaryResult.getSubsidiaryName() + "账簿";
            systemK3Number = subsidiaryResult.getSystemK3Number();
            k3Number = subsidiaryResult.getK3Number();
        }


        //添加账簿
        ResponseResult responseResultZB = k3Api_organization.saveZB(companyResult.getDataCentre(), companyResult.getYewuDataCentreUserName(), companyResult.getYewuDataCentrePassword(), fName, systemK3Number, k3Number);
        if (!((Map) (((Map) (((Map) (responseResultZB.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("账簿生成失败:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResultZB.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        String zhangbuK3Number = ((Map) (((Map) (((Map) responseResultZB.getResult()).get("Result"))))).get("Number").toString();

        if (companyType.equals("1")) {
            Company companyUpdate = new Company();
            companyUpdate.setCompanyId(Long.parseLong(companyId));
            companyUpdate.setZhangBuK3Number(zhangbuK3Number);
            companyDao.updateCompanyByID(companyUpdate);
        } else if (companyType.equals("2")) {
            Subsidiary subsidiaryUpdate = new Subsidiary();
            subsidiaryUpdate.setSubsidiaryId(Long.parseLong(companyId));
            subsidiaryUpdate.setZhangBuK3Number(zhangbuK3Number);
            companyDao.updateSubsidiaryByID(subsidiaryUpdate);
        }

        return ResponseResult.success("K3账簿生成成功");
    }

    @Override
    public ResponseResult deleteSubsidiaryByID(Long subsidiaryId, String modifyOperator) {
        Subsidiary subsidiaryByID = companyDao.selectSubsidiaryByID(subsidiaryId);
        if (subsidiaryByID == null) {
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.SUBSIDIARY_NOT_FIND.getCode(),
                    ResponseCodeCompanyEnum.SUBSIDIARY_NOT_FIND.getDesc()));
        }
        Subsidiary subsidiary = new Subsidiary();
        subsidiary.setSubsidiaryId(subsidiaryId);
        subsidiary.setModifyOperator(modifyOperator);
        Company company = companyDao.selectCompanyByID(subsidiary.getParentCompany());
        int i = companyDao.deleteSubsidiaryByID(subsidiary);

        //删除k3子公司
        //k3Api_organization.delete(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), subsidiaryId.toString(), null);

        //删除子公司的所有管理员
        authApi.deleteuserByCompany(subsidiaryId, 2l);
        //删除子公司下面的门店分公司
        Map mapDel = new HashMap();
        Map map = new HashMap();
        map.put("subCompanyId", subsidiaryId);
        List<StoreVO> storeVOList = iStoreDao.selectStoretList(map);
        mapDel.put("subCompanyId", subsidiaryId);
        iStoreDao.deleteStore(mapDel);
        for (StoreVO storeVO : storeVOList) {
            authApi.deleteuserByCompany(storeVO.getStoreId(), 3l);
        }
        if (i > 0) {
            return ResponseResult.success(i);
        }
        return ResponseResult.error(new Error(ResponseCodeCompanyEnum.SUBSIDIARY_DELETE_FAILD.getCode(),
                ResponseCodeCompanyEnum.SUBSIDIARY_DELETE_FAILD.getDesc()));
    }

    @Override
    public ResponseResult selectSubsidiary(String companyType, String companyId, String companyName) {
        List<Company> companyList = null;
        if (companyType.equals("1")) {
            //如果是总公司
            Map map = new HashMap();
            map.put("companyId", companyId);
            map.put("companyNameKeyword", companyName);
            companyList = companyDao.selectCompanyList(map);
        } else if (companyType.equals("0")) {
            companyList = companyDao.selectCompanyList(null);
        } else if (companyType.equals("2")) {
            //如果是子公司
            Long companyIdResult = companyDao.selectSubsidiaryByID(Long.parseLong(companyId)).getParentCompany();
            Map map = new HashMap();
            map.put("companyId", companyIdResult);
            map.put("companyNameKeyword", companyName);
            companyList = companyDao.selectCompanyList(map);
        }

        List<Subsidiary> subsidiaryList = companyDao.selectSubsidiaryList(new HashMap());
        for (Company company : companyList) {
            List<Subsidiary> subsidiaryList1 = new ArrayList<>();
            for (Subsidiary subsidiary : subsidiaryList) {
                if (company.getCompanyId().equals(subsidiary.getParentCompany())) {
                    subsidiaryList1.add(subsidiary);
                }
            }
            company.setSubsidiaryList(subsidiaryList1);
        }

        if (companyList != null && companyList.size() > 0) {
            return ResponseResult.success(companyList);
        }
        return ResponseResult.error(new Error(ResponseCodeCompanyEnum.SUBSIDIARY_NOT_FIND.getCode(),
                ResponseCodeCompanyEnum.SUBSIDIARY_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectSubsidiaryByParentID(Long parentCompany) {
        List<Subsidiary> subsidiaryList = companyDao.selectSubsidiaryByParentID(parentCompany);

        if (subsidiaryList != null && subsidiaryList.size() > 0) {
            return ResponseResult.success(subsidiaryList);
        }
        return ResponseResult.error(new Error(ResponseCodeCompanyEnum.SUBSIDIARY_NOT_FIND.getCode(),
                ResponseCodeCompanyEnum.SUBSIDIARY_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectSubsidiaryByID(Long subsidiaryId) {
        Subsidiary subsidiary = companyDao.selectSubsidiaryByID(subsidiaryId);
        if (subsidiary != null) {
            return ResponseResult.success(subsidiary);
        }
        return ResponseResult.error(new Error(ResponseCodeCompanyEnum.SUBSIDIARY_NOT_FIND.getCode(),
                ResponseCodeCompanyEnum.SUBSIDIARY_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectSubsidiaryList(int pageNum, int pageSize, String keyWordName, String companyId) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("keyWordName", keyWordName);
        map.put("companyId", companyId);
        List<Subsidiary> subsidiaryList = companyDao.selectSubsidiaryList(map);
        //查询所有总公司
        List<Company> companieList = companyDao.selectCompanyList(new HashMap());
        for (Subsidiary subsidiary : subsidiaryList) {
            for (Company company : companieList) {
                if (subsidiary.getParentCompany() == company.getCompanyId()) {
                    subsidiary.setParentCompanyName(company.getCompanyName());
                }
            }
            subsidiary.setOrgId(subsidiary.getSubsidiaryId());
            subsidiary.setOrgK3Number(subsidiary.getK3Number());
            subsidiary.setOrgName(subsidiary.getSubsidiaryName());
        }

        if (subsidiaryList.size() != 0) {
            PageInfo pageInfo = new PageInfo(subsidiaryList);
            return ResponseResult.success(pageInfo);
        }
     /*   Map mapRsultPage = ListPageUntil.listPage(subsidiaryList, pageSize, pageNum);
        Map mapResult = new HashMap();
        if (((List) mapRsultPage.get("list")).size() > 0) {
            mapResult.put("list", mapRsultPage.get("list"));
            mapResult.put("total", mapRsultPage.get("total"));
            return ResponseResult.success(mapResult);
        }*/
        return ResponseResult.error(new Error(ResponseCodeCompanyEnum.SUBSIDIARY_NOT_FIND.getCode(),
                ResponseCodeCompanyEnum.SUBSIDIARY_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult updateCompanyByID(Company company) {
        Long companyId = company.getCompanyId();
        Company companyByID = companyDao.selectCompanyByID(companyId);
        if (companyByID == null) {
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_NOT_FIND.getCode(),
                    ResponseCodeCompanyEnum.COMPANY_NOT_FIND.getDesc()));
        }
        int i = companyDao.updateCompanyByID(company);

        if (i > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_INSERT_FAILD.getCode(),
                ResponseCodeCompanyEnum.COMPANY_INSERT_FAILD.getDesc()));
    }

    @Override
    public ResponseResult insertCompany(Company company) {
        company.setPhoneNumber(company.getPhoneNumber().trim());
        //验证字段是否重复
        //验证数据中心编号
        Company companyCheckDataCentre = new Company();
        companyCheckDataCentre.setDataCentre(company.getDataCentre());
        int resultIntDataCentre = companyDao.checkCompany(companyCheckDataCentre);
        if (resultIntDataCentre > 0) {
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_DATACENTRE_EXIST.getCode(), ResponseCodeCompanyEnum.COMPANY_DATACENTRE_EXIST.getDesc()));
        }
        //验证k3number
        Company companyCheckK3Number = new Company();
        companyCheckK3Number.setK3Number(company.getK3Number());
        int resultIntK3Number = companyDao.checkCompany(companyCheckK3Number);
        if (resultIntK3Number > 0) {
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_K3NUMBER_EXIST.getCode(), ResponseCodeCompanyEnum.COMPANY_K3NUMBER_EXIST.getDesc()));
        }

        //验证总公司名称
        Company companyCheckName = new Company();
        companyCheckName.setCompanyName(company.getCompanyName());
        int resultIntName = companyDao.checkCompany(companyCheckName);
        if (resultIntName > 0) {
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_NAME_EXIST.getCode(), ResponseCodeCompanyEnum.COMPANY_NAME_EXIST.getDesc()));
        }

        String phoneNumberRe = company.getPhoneNumber().replace(" ", "");
        if (!PhoneUtils.isMobileNO(phoneNumberRe) && !PhoneUtils.isFixedLinePhone(phoneNumberRe)) {
            return ResponseResult.error(new Error(ResponseCodeStoreEnum.MOBILE_ILLEGAL.getCode(), ResponseCodeStoreEnum.MOBILE_ILLEGAL.getDesc()));
        }

        //系统用户和业务用户 登录验证
        ResponseResult responseResultAdminLogin = k3Api_organization.k3Login(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), "2052");
        if (((Boolean) ((Map) (responseResultAdminLogin.getResult())).get("isSuccessByAPI")) == false) {
            return ResponseResult.error(new Error(1, "系统管理员连接失败:" + (((Map) (responseResultAdminLogin.getResult())).get("message")).toString()));
        }

        //添加morningstar用户
        ResponseResult responseResultUser = k3Api_organization.saveAdminUser(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), company.getYewuDataCentreUserName(), company.getYewuDataCentreUserName());
        String userK3Id = (((Map) (((Map) responseResultUser.getResult()).get("Result")))).get("Id").toString();


        //添加总公司账簿 k3
        String fName = company.getCompanyName() + "账簿";
        String systemK3Number = "KJHSTX01_SYS";
        String k3Number = company.getK3Number();
        ResponseResult responseResultZB = k3Api_organization.saveZB(company.getDataCentre(), company.getYewuDataCentreUserName(), "888888", fName, systemK3Number, k3Number);
        if (responseResultZB.getResult().equals("暂未登录")) {
            //保存失败
            return ResponseResult.success("总公司生成失败，由于输入的数据中心编号或用户密码有误");
        }

        if (!((Map) (((Map) (((Map) (responseResultZB.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("总公司生成失败，由于k3账簿生成失败:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResultZB.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        String zhangbuK3Number = ((Map) (((Map) (((Map) responseResultZB.getResult()).get("Result"))))).get("Number").toString();

        company.setZhangBuK3Number(zhangbuK3Number);


        //添加零售客户
        Customer customer = new Customer();
        String k3NumberLast = iCustomerDao.selectLastCustomerCode();
        String k3NumberAdd = null;
        if (k3NumberLast == null) {
            customer.setK3Number("CUST" + "0001");
        } else {
            String substring = k3NumberLast.substring(4, 8);
            int j = Integer.parseInt(substring);
            k3NumberAdd = "CUST" + String.format("%4d", j + 1).replace(" ", "0");
            customer.setK3Number(k3NumberAdd);

        }

        customer.setName("零售客户");
        customer.setCustomerType(1);
        customer.setIsDefaultCust(1);
        iCustomerDao.insertCustomer(customer);

        ResponseResult responseResult = k3Api_organization.customerSave(company.getDataCentre(), company.getYewuDataCentreUserName(), "888888", "零售客户", customer.getK3Number(), "零售客户", company.getK3Number(), company.getK3Number(), "KHLB001_SYS", null, null);

        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("客户添加失败,K3客户添加未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }

        String k3Id = (((Map) (((Map) responseResult.getResult()).get("Result")))).get("Id").toString();
        String number = (((Map) (((Map) responseResult.getResult()).get("Result")))).get("Number").toString();

        //修改客户k3信息
        Customer customerUpdate = new Customer();
        customerUpdate.setId(customer.getId());
        customerUpdate.setK3Id(k3Id);
        customerUpdate.setK3Number(number);
        iCustomerDao.updateCustomer(customerUpdate);


        company.setYewuDataCentrePassword("888888");
        company.setYewuDataCentreUserId(userK3Id);
        int i = companyDao.insertCompany(company);

        //生成总公司仓库
        productApi.addStock(1l, company.getCompanyId(), company.getCompanyName() + "仓库", company.getK3Number(), company.getCreateOperator(), company.getDataCentre(), company.getYewuDataCentreUserName(), "888888");
        //启用仓库
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = sdf.format(new Date());
        k3Api_organization.startStock(company.getDataCentreName(), "1", startDate);
        return ResponseResult.success("总公司建立成功,生成账簿及默认零售客户,创建morningstar业务账号,仓库生成成功并已启用");

    }

    @Override
    public ResponseResult deleteCompanyByID(Long companyId, String modifyOperator) {
        Company company = new Company();
        company.setCompanyId(companyId);
        company.setModifyOperator(modifyOperator);
        int i = companyDao.deleteCompanyByID(company);
        Company companySelect = companyDao.selectCompanyByID(companyId);

        //删除总公司的所有管理员
        authApi.deleteuserByCompany(companyId, 1l);
/*
        //删除k3总公司
        k3Api.delete(companySelect.getDataCentre(),companySelect.getDataCentreUserName(), companySelect.getDataCentrePassword(), companySelect.getK3Number(), null);
*/


        //删除子公司
        Subsidiary subsidiarydel = new Subsidiary();
        subsidiarydel.setParentCompany(companyId);
        List<Subsidiary> subsidiaryList = companyDao.selectSubsidiaryByParentID(companyId);
        companyDao.deleteSubsidiaryByID(subsidiarydel);
        //删除子公司权限
        for (Subsidiary subsidiary : subsidiaryList) {
            authApi.deleteuserByCompany(subsidiary.getSubsidiaryId(), 2l);
        }
        //删除子公司下面的门店分公司
        for (Subsidiary subsidiary : subsidiaryList) {
            Map mapDel = new HashMap();
            mapDel.put("subCompanyId", subsidiary.getSubsidiaryId());
            Map map = new HashMap();
            map.put("subCompanyId", subsidiary.getSubsidiaryId());
            List<StoreVO> storeVOList = iStoreDao.selectStoretList(map);
            iStoreDao.deleteStore(mapDel);
            for (StoreVO storeVO : storeVOList) {
                authApi.deleteuserByCompany(storeVO.getStoreId(), 3l);
            }
        }


        if (i > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_DELETE_FAILD.getCode(),
                ResponseCodeCompanyEnum.COMPANY_DELETE_FAILD.getDesc()));
    }

    @Override
    public ResponseResult selectCompanyByID(Long companyId) {
        Company company = companyDao.selectCompanyByID(companyId);
        if (company != null) {
            return ResponseResult.success(company);
        }
        return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_NOT_FIND.getCode(),
                ResponseCodeCompanyEnum.COMPANY_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectCompanyList(int pageNum, int pageSize, String companyNameKeyword) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("companyNameKeyword", companyNameKeyword);
        List<Company> companyList = companyDao.selectCompanyList(map);
        if (companyList != null && companyList.size() > 0) {
            PageInfo<Company> pageInfo = new PageInfo<>(companyList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_NOT_FIND.getCode(),
                ResponseCodeCompanyEnum.COMPANY_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult updatePassword(Long companyId, String password, Integer adminType) {
        Company company = new Company();
        company.setCompanyId(companyId);
        if (adminType == 1) {
            //如果是k3系统管理员
            company.setDataCentrePassword(password);
        } else {
            //如果是k3业务管理员
            company.setYewuDataCentrePassword(password);
        }
        companyDao.updateCompanyByID(company);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectCompanyListNoPage() {
        List<Company> companyList = companyDao.selectCompanyList(new HashMap());
        if (companyList != null && companyList.size() > 0) {
            return ResponseResult.success(companyList);
        }
        return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_NOT_FIND.getCode(),
                ResponseCodeCompanyEnum.COMPANY_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectCompanyAndSubListNoPage(String companyType, String companyId) {
        Map map1 = new HashMap();
        map1.put("companyType", companyType);
        map1.put("companyId", companyId);
        List<Company> companyList = companyDao.selectCompanyList(map1);
        Map map = new HashMap();
        List<Subsidiary> subsidiaryList = companyDao.selectSubsidiaryList(map);
        for (Company company : companyList) {
            List<Subsidiary> subsidiarylist = new ArrayList<>();
            for (Subsidiary subsidiary : subsidiaryList) {
                if (company.getCompanyId().equals(subsidiary.getParentCompany())) {
                    subsidiarylist.add(subsidiary);
                }
            }
            company.setSubsidiaryList(subsidiaryList);
        }
        if (companyList != null && companyList.size() > 0) {
            return ResponseResult.success(companyList);
        }
        return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_NOT_FIND.getCode(),
                ResponseCodeCompanyEnum.COMPANY_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult createJiChuKongZhi(Long companyId) {
        Company company = companyDao.selectCompanyByID(companyId);
        ResponseResult responseResult = k3Api_organization.organizationJiChuKongZhiSave(company.getDataCentre(), company.getDataCentreUserName(), company.getDataCentrePassword(), company.getK3Number(), null);
        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("生成失败，查看组织是否已经生成了基础资料控制策略");
        }
        return ResponseResult.success("生成成功");
    }

    @Override
    public ResponseResult selectBankAccountList(int pageNum, int pageSize, BankAccount bankAccount) {

        List<BankAccount> bankAccountList = companyDao.selectBankAccountList(bankAccount);
        if (bankAccountList != null && bankAccountList.size() != 0) {
            for (BankAccount account : bankAccountList) {
                if (account.getAccountType() == 1) {
                    account.setAccountTypeName("银行账号");
                } else if (account.getAccountType() == 2) {
                    account.setAccountTypeName("现金账号");
                }
            }
            Map mapRsultPage = ListPageUntil.listPage(bankAccountList, pageSize, pageNum);
            Map mapResult = new HashMap();
            mapResult.put("list", mapRsultPage.get("list"));
            mapResult.put("total", mapRsultPage.get("total"));
            return ResponseResult.success(mapResult);
        } else {
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.ACCOUNT_NULL.getCode(),
                    ResponseCodeCompanyEnum.ACCOUNT_NULL.getDesc()));
        }


    }

    @Override
    public ResponseResult addBankAccount(BankAccount bankAccount) {
        //判断是否有重复的名字
        int resultInt1 = companyDao.chekBankAccountName(bankAccount);
        if (resultInt1 > 0) {
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.ACCOUNT_NAME_IS_EXIST.getCode(),
                    ResponseCodeCompanyEnum.ACCOUNT_NAME_IS_EXIST.getDesc()));
        }
        //判断是否有重复的账号号码
        int resultInt2 = companyDao.chekBankAccountNumber(bankAccount);
        if (resultInt2 > 0) {
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.ACCOUNT_NUMBER_IS_EXIST.getCode(),
                    ResponseCodeCompanyEnum.ACCOUNT_NUMBER_IS_EXIST.getDesc()));
        }
        int resultInt = companyDao.addBankAccount(bankAccount);

        //添加k3
        //查看总公司信息
        Company company = companyDao.selectCompanyByID(Long.parseLong(bankAccount.getCompanyId()));
        ResponseResult responseResult = null;
        if (bankAccount.getAccountType() == 1) {
            //如果是银行账户
            responseResult = k3Api_organization.saveBank(company.getDataCentre(), company.getYewuDataCentreUserName(), company.getYewuDataCentrePassword(), true, bankAccount.getAccountName(), bankAccount.getAccountNumber(), company.getK3Number(), company.getK3Number(), bankAccount.getBankId(), "1");
        } else {
            //如果是现金账户
            responseResult = k3Api_organization.saveCash(company.getDataCentre(), company.getYewuDataCentreUserName(), company.getYewuDataCentrePassword(), true, bankAccount.getAccountNumber(), bankAccount.getAccountName(), company.getK3Number(), company.getK3Number());
        }
        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true") &&
                !((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("账号生成成功，k3账号生成失败:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        String bankId = ((Map) (((Map) (((Map) responseResult.getResult()).get("Result"))))).get("Id").toString();
        BankAccount bankAccountUpdate = new BankAccount();
        bankAccountUpdate.setId(bankAccount.getId());
        bankAccountUpdate.setK3Id(bankId);
        companyDao.updateBankAccount(bankAccountUpdate);

        return ResponseResult.success("账号生成成功，k3账号生成成功");
    }

    @Override
    public ResponseResult batchAllocationSubCompany(String companyType, String companyId, String id, String accountType, String subcompanyIdArrayStr) {
        //本系统账号分配
        JSONArray subcompanyIdArray = JSON.parseArray(subcompanyIdArrayStr);
        List<Map> mapList = new ArrayList<>();
        List<BankAccountCompany> bankAccountCompanyArrayListAdd = new ArrayList<>();
        for (int i = 0; i < subcompanyIdArray.size(); i++) {
            BankAccountCompany bankAccountCompany = new BankAccountCompany();
            bankAccountCompany.setBankCashAccountId(Long.parseLong(id));
            bankAccountCompany.setCompanyId(subcompanyIdArray.getJSONObject(i).getString("orgId"));
            bankAccountCompany.setCompanyType(companyType);
            //查看是否已经分配
            Map mapCheck = new HashMap();
            mapCheck.put("bankCashAccountId", id);
            mapCheck.put("companyId", subcompanyIdArray.getJSONObject(i).getLong("orgId"));
            mapCheck.put("companyType", companyType);
            List<BankAccountCompany> bankAccountCompanyList = companyDao.checkBankCashCompany(mapCheck);
            if (bankAccountCompanyList != null && bankAccountCompanyList.size() > 0) {
                Map mapResult = new HashMap();
                mapResult.put("组织名称", subcompanyIdArray.getJSONObject(i).getString("orgName"));
                mapList.add(mapResult);
            } else {
                bankAccountCompanyArrayListAdd.add(bankAccountCompany);
            }
        }
        if (bankAccountCompanyArrayListAdd.size() != 0) {
            Map map = new HashMap();
            map.put("list", bankAccountCompanyArrayListAdd);
            companyDao.insertBankCashCompany(map);
        }


        //k3分配接入
        //查看银行现金账号信息
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(Long.parseLong(id));
        BankAccount bankAccountResult = companyDao.selectBankAccountById(bankAccount);
        String TOrgIds = "";
        String PkIds = bankAccountResult.getK3Id();
        for (int i = 0; i < subcompanyIdArray.size(); i++) {
            TOrgIds = TOrgIds + "," + subcompanyIdArray.getJSONObject(i).getString("k3Id");
        }
        TOrgIds = TOrgIds.substring(1);
        Company company = companyDao.selectCompanyByID(Long.parseLong(companyId));
        ResponseResult responseResult = null;
        if (accountType.equals("1")) {
            //如果是银行账号
            responseResult = k3Api_organization.AllocateBankAccount(
                    company.getDataCentre(),
                    company.getYewuDataCentreUserName(),
                    company.getYewuDataCentrePassword(),
                    PkIds,
                    TOrgIds,
                    true
            );
        } else {
            //如果是现金账号
            responseResult = k3Api_organization.AllocateCashAccount(
                    company.getDataCentre(),
                    company.getYewuDataCentreUserName(),
                    company.getYewuDataCentrePassword(),
                    PkIds,
                    TOrgIds,
                    true
            );
        }


        if (responseResult.getResult().equals("暂未登录")) {
            return ResponseResult.success("账号分配成功,K3账号未分配，K3用户密码错误");
        }
        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("账号分配成功，K3分配未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        if (mapList.size() != 0) {
            return ResponseResult.success("分配成功,k3分配成功,但以下组织已经分配账号" + mapList);
        } else {
            return ResponseResult.success("分配成功,k3分配成功");
        }

    }

    @Override
    public ResponseResult batchAllocationStore(String companyType, String companyId, String id, String accountType, String storeIdArrayStr) {
        //本系统账号分配
        JSONArray storeIdArray = JSON.parseArray(storeIdArrayStr);
        List<Map> mapList = new ArrayList<>();
        List<BankAccountCompany> bankAccountCompanyArrayListAdd = new ArrayList<>();
        for (int i = 0; i < storeIdArray.size(); i++) {
            BankAccountCompany bankAccountCompany = new BankAccountCompany();
            bankAccountCompany.setBankCashAccountId(Long.parseLong(id));
            bankAccountCompany.setCompanyId(storeIdArray.getJSONObject(i).getString("orgId"));
            bankAccountCompany.setCompanyType(companyType);
            //查看是否已经分配
            Map mapCheck = new HashMap();
            mapCheck.put("bankCashAccountId", id);
            mapCheck.put("companyId", storeIdArray.getJSONObject(i).getLong("orgId"));
            mapCheck.put("companyType", companyType);
            List<BankAccountCompany> bankAccountCompanyList = companyDao.checkBankCashCompany(mapCheck);
            if (bankAccountCompanyList != null && bankAccountCompanyList.size() > 0) {
                Map mapResult = new HashMap();
                mapResult.put("组织名称", storeIdArray.getJSONObject(i).getString("orgName"));
                mapList.add(mapResult);
            } else {
                bankAccountCompanyArrayListAdd.add(bankAccountCompany);
            }
        }
        if (bankAccountCompanyArrayListAdd.size() != 0) {
            Map map = new HashMap();
            map.put("list", bankAccountCompanyArrayListAdd);
            companyDao.insertBankCashCompany(map);
        }


        //k3分配接入
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(Long.parseLong(id));
        BankAccount bankAccountResult = companyDao.selectBankAccountById(bankAccount);
        String TOrgIds = "";
        String PkIds = bankAccountResult.getK3Id();
        for (int i = 0; i < storeIdArray.size(); i++) {
            TOrgIds = TOrgIds + "," + storeIdArray.getJSONObject(i).getString("k3Id");
        }
        TOrgIds = TOrgIds.substring(1);
        Company company = companyDao.selectCompanyByID(Long.parseLong(companyId));
        ResponseResult responseResult = null;
        if (accountType.equals("1")) {
            //如果是银行账号
            responseResult = k3Api_organization.AllocateBankAccount(
                    company.getDataCentre(),
                    company.getYewuDataCentreUserName(),
                    company.getYewuDataCentrePassword(),
                    PkIds,
                    TOrgIds,
                    true
            );
        } else {
            //如果是现金账号
            responseResult = k3Api_organization.AllocateCashAccount(
                    company.getDataCentre(),
                    company.getYewuDataCentreUserName(),
                    company.getYewuDataCentrePassword(),
                    PkIds,
                    TOrgIds,
                    true
            );
        }
        if (responseResult.getResult().equals("暂未登录")) {
            return ResponseResult.success("账号分配成功,K3账号分配未生成，K3用户密码错误");
        }
        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("账号分配成功，K3分配未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        if (mapList.size() != 0) {
            return ResponseResult.success("分配成功,k3分配成功,但以下账号已经分配组织" + mapList);
        } else {
            return ResponseResult.success("分配成功,k3分配成功");
        }
    }

    @Override
    public ResponseResult batchCancelAllocationSubCompany(String companyType, String companyId, String id, String subcompanyIdArrayStr) {
        //本系统商品取消分配
        JSONArray subcompanyIdArray = JSON.parseArray(subcompanyIdArrayStr);
        for (int i = 0; i < subcompanyIdArray.size(); i++) {
            Map map = new HashMap();
            map.put("bankCashAccountId", id);
            map.put("companyId", subcompanyIdArray.getJSONObject(i).getLong("orgId"));
            map.put("companyType", companyType);
            companyDao.deleteBankCashCompany(map);
        }
        return ResponseResult.success("取消分配成功,请登陆k3系统进行手动组织取消分配");
    }

    @Override
    public ResponseResult batchCancelAllocationStore(String companyType, String companyId, String id, String storeIdArrayStr) {
        //本系统商品取消分配
        JSONArray storeIdArray = JSON.parseArray(storeIdArrayStr);
        for (int i = 0; i < storeIdArray.size(); i++) {
            Map map = new HashMap();
            map.put("bankCashAccountId", id);
            map.put("companyId", storeIdArray.getJSONObject(i).getLong("orgId"));
            map.put("companyType", companyType);
            companyDao.deleteBankCashCompany(map);
        }
        return ResponseResult.success("取消分配成功,请登陆k3系统进行手动组织取消分配");
    }
}
