package com.lnmj.product.serviceProxy;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeCompanyEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "lnmj-store")
public interface StoreApi {
    @RequestMapping(value = "/manage/store/selectStoreById", method = RequestMethod.POST)
    ResponseResult selectStoreById(@RequestParam("storeId") Long storeId);

    @RequestMapping(value = "/manage/beautician/selectProjectNoPage", method = RequestMethod.POST)
    ResponseResult selectProjectNoPage(@RequestParam("postId") Long postId, @RequestParam("postLevel") Integer postLevel, @RequestParam("keyWordProductCode") String keyWordProductCode, @RequestParam("keyWordProductName") String keyWordProductName);

    @RequestMapping(value = "/api/manage/company/selectSubsidiaryByID", method = RequestMethod.POST)
    ResponseResult selectSubsidiaryByID(@RequestParam("subsidiaryId") Long subsidiaryId);

    //总公司下的子公司
    @RequestMapping(value = "/api/manage/company/selectSubsidiaryByParentID", method = RequestMethod.POST)
    ResponseResult selectSubsidiaryByParentID(@RequestParam("parentCompany") Long parentCompany);

    //子公司下的分公司
    @RequestMapping(value = "/api/manage/store/selectStoreListBySubCompanyNoPage", method = RequestMethod.POST)
    ResponseResult selectStoreListBySubCompanyNoPage(@RequestParam("subsidiaryId") Long subsidiaryId);

    //查看行业根据id
    @RequestMapping(value = "/manage/industry/selectListIndustryById", method = RequestMethod.POST)
    ResponseResult selectListIndustryById(@RequestParam("industryID") Long industryID);

    //查询所有行业
    @RequestMapping(value = "/manage/industry/selectListIndustryNoPage", method = RequestMethod.POST)
    ResponseResult selectListIndustryNoPage();

    //查询所有的子公司和分公司
    @RequestMapping(value = "/manage/store/selectSubCompanyAndStoreNoPage", method = RequestMethod.POST)
    ResponseResult selectSubCompanyAndStoreNoPage(@RequestParam("companyId") Long companyId, @RequestParam("companyType") Long companyType);

    //查询所有的总公司
    @RequestMapping(value = "/manage/company/selectCompanyListNoPage", method = RequestMethod.POST)
    ResponseResult selectCompanyListNoPage();

    //根据ID查询总公司（获取用户名和密码）
    @RequestMapping(value = "/manage/company/selectCompanyByID", method = RequestMethod.POST)
    ResponseResult selectCompanyByID(@RequestParam("companyId") Long companyId);

    //根据Code查询供应商
    @RequestMapping(value = "/manage/supplier/selectSupplierByCode", method = RequestMethod.POST)
    ResponseResult selectSupplierByCode(@RequestParam("supplierCode") String supplierCode);

    @ApiOperation(value = "查看组织默认客户", notes = "查看组织默认客户")
    @RequestMapping(value = "/manage/customer/selectDefaultCust", method = RequestMethod.POST)
    ResponseResult selectDefaultCust(@RequestParam("companyId") Long companyId, @RequestParam("companyType") Integer companyType);

    @ApiOperation(value = "根据客户k3编号查看客户", notes = "根据客户k3编号查看客户")
    @RequestMapping(value = "/manage/customer/selectCustomerByNumber", method = RequestMethod.POST)
    ResponseResult selectCustomerByNumber(@RequestParam("k3Number") String k3Number);

    @ApiOperation(value = "根据条件查询供应商", notes = "根据条件查询供应商")
    @RequestMapping(value = "/manage/supplier/selectSupplierByCondition", method = RequestMethod.POST)
    ResponseResult selectSupplierByCondition(@RequestParam("relationSubCompanyType") String relationSubCompanyType, @RequestParam("relationSubCompanyId") String relationSubCompanyId);

    @ApiOperation(value = "根据条件查看客户",notes = "根据条件查看客户")
    @RequestMapping(value = "/manage/customer/selectCustomerByConditon",method = RequestMethod.POST)
    ResponseResult selectCustomerByConditon(@RequestParam("relationSubCompanyType") String relationSubCompanyType, @RequestParam("relationSubCompanyId") String relationSubCompanyId);
}

