package com.lnmj.k3cloud.controller.backend.organization;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3-组织结构-组织结构")
@RestController
@RequestMapping("/k3cloud/organization/organizationJiChuKongZhi")
public class OrganizationJiChuKongZhiController {
    @Resource
    private IOrganizationService iOrganizationService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/organizationJiChuKongZhiSave", method = RequestMethod.POST)
    public ResponseResult organizationJiChuKongZhiSave(String acctId,String dataCentreUserName, String dataCentrePassword,String orgK3Number,String fTargetOrgId) {
        return iOrganizationService.organizationJiChuKongZhiSave(acctId,dataCentreUserName,dataCentrePassword,orgK3Number,fTargetOrgId);
    }

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/organizationJiChuKongZhiSaveGW", method = RequestMethod.POST)
    public ResponseResult organizationJiChuKongZhiSaveGW(String acctId,String dataCentreUserName, String dataCentrePassword,String orgK3Number) {
        return iOrganizationService.organizationJiChuKongZhiSaveGW(acctId,dataCentreUserName,dataCentrePassword,orgK3Number);
    }

}
