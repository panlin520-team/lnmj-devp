package com.lnmj.k3cloud.controller.backend.organization;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3-组织结构-组织结构")
@RestController
@RequestMapping("/k3cloud/organization/organization")
public class OrganizationController {
    @Resource
    private IOrganizationService iOrganizationService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult save(String id,String acctId,String dataCentreUserName, String dataCentrePassword,String fname,String fNumber,String fOrgFormID,Boolean fIsAccountOrg,String fAcctOrgType,String fParentID,Boolean fIsBusinessOrg,String fOrgFunctions,Boolean fStockBox) {
        return iOrganizationService.organizationSave(id,acctId,dataCentreUserName,dataCentrePassword,fname,fNumber,fOrgFormID,fIsAccountOrg,fAcctOrgType,fParentID,fIsBusinessOrg,fOrgFunctions,fStockBox);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids) {
        return iOrganizationService.organizationDelete(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "查看", notes = "查看")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseResult view(String acctId,String dataCentreUserName, String dataCentrePassword,String number,String id) {
        return iOrganizationService.organizationView(acctId,dataCentreUserName,dataCentrePassword,number,id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submit(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids) {
        return iOrganizationService.organizationSubimt(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult audit(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids) {
        return iOrganizationService.organizationAudit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "反审核", notes = "审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAudit(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids) {
        return iOrganizationService.organizationUnAudit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "修改组织的职能", notes = "修改组织的职能")
    @RequestMapping(value = "/updateOrgFunctions", method = RequestMethod.POST)
    public ResponseResult updateOrgFunctions(String dataCentreName,String orgId) {
        return iOrganizationService.updateOrgFunctions(dataCentreName,orgId);
    }
}
