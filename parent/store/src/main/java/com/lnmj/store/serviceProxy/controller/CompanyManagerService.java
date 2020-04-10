package com.lnmj.store.serviceProxy.controller;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeCompanyEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.ICompanyService;
import com.lnmj.store.business.IStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author panlin
 * @Date: 2019/5/28 11:49
 * @Description: 店铺管理
 */
@Api(description = "公司管理")
@RestController
@RequestMapping("/api/manage/company")
public class CompanyManagerService {
    @Resource
    private ICompanyService companyService;


    @ApiOperation(value = "根据ID查询公司",notes = "根据ID查询公司")
    @RequestMapping(value = "/selectCompanyByID",method = RequestMethod.POST)
    public ResponseResult selectCompanyByID(Long companyId){
        if(companyId==null){
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.COMPANY_ID_NULL.getCode(),
                    ResponseCodeCompanyEnum.COMPANY_ID_NULL.getDesc()));
        }
        return companyService.selectCompanyByID(companyId);
    }

    @ApiOperation(value = "根据总公司ID查询子公司",notes = "根据ID查询公司")
    @RequestMapping(value = "/selectSubsidiaryByParentID",method = RequestMethod.POST)
    public ResponseResult selectSubsidiaryByParentID(Long parentCompany){
        return companyService.selectSubsidiaryByParentID(parentCompany);
    }



    @ApiOperation(value = "根据ID查询子公司",notes = "根据ID查询子公司")
    @RequestMapping(value = "/selectSubsidiaryByID",method = RequestMethod.POST)
    public ResponseResult selectSubsidiaryByID(Long subsidiaryId){
        if(subsidiaryId==null){
            return ResponseResult.error(new Error(ResponseCodeCompanyEnum.SUBSIDIARY_ID_NULL.getCode(),
                    ResponseCodeCompanyEnum.SUBSIDIARY_ID_NULL.getDesc()));
        }
        return companyService.selectSubsidiaryByID(subsidiaryId);
    }
}
