package com.lnmj.authority.serviceProxy;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeStoreEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "lnmj-store")
public interface CompanyAndStoreApi {
    @RequestMapping(value = "/api/manage/company/selectCompanyByID",method = RequestMethod.POST)
    ResponseResult selectCompanyByID(@RequestParam("companyId") Long companyId);
    @RequestMapping(value = "/api/manage/company/selectSubsidiaryByID",method = RequestMethod.POST)
    ResponseResult selectSubsidiaryByID(@RequestParam("subsidiaryId") Long subsidiaryId);
    @RequestMapping(value = "/api/manage/store/selectStoreListByCompanyId",method = RequestMethod.POST)
    ResponseResult selectStoreListByCompanyId(@RequestParam("companyId") Long companyId);


    @RequestMapping(value = "/api/manage/store/selectStoreListBySubCompanyNoPage",method = RequestMethod.POST)
    ResponseResult selectStoreListBySubCompanyNoPage(@RequestParam("subsidiaryId") Long subsidiaryId);

    @RequestMapping(value = "/api/manage/company/selectSubsidiaryByParentID",method = RequestMethod.POST)
    ResponseResult selectSubsidiaryByParentID(@RequestParam("parentCompany") Long parentCompany);
    @RequestMapping(value = "/manage/store/selectStoreById",method = RequestMethod.POST)
    public ResponseResult selectStoreById(@RequestParam("storeId")Long storeId);
    @RequestMapping(value = "/manage/store/selectStoreByCodeOrName",method = RequestMethod.POST)
    public ResponseResult selectStoreByCodeOrName(@RequestParam("code")String code);
}

