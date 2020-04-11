package com.lnmj.k3cloud.controller.backend.organization;


import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3-组织结构-组织隶属关系")
@RestController
@RequestMapping("/k3cloud/organization/OrganizationLiShuGuanXi")
public class OrganizationLiShuGuanXiController {
    @Resource
    private IOrganizationService iOrganizationService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult saveLiShu(String fParentOrgId ,String fOrgId ,String acctId,String dataCentreUserName, String dataCentrePassword,String fname,String fNumber,String fRootOrgID,String fType,String fEndDate,String fStartDate,String fBackupOrg,String fAFFILIATIONID ) {
        return iOrganizationService.organizationLiShuGuanXiSave(fParentOrgId,fOrgId,acctId,dataCentreUserName,dataCentrePassword,fname,fNumber,fRootOrgID,fType,fEndDate,fStartDate,fBackupOrg,fAFFILIATIONID);
    }

    @ApiOperation(value = "批量保存", notes = "批量保存")
    @RequestMapping(value = "/batchSave", method = RequestMethod.POST)
    public ResponseResult batchSaveLiShu(String fParentOrgId , String fOrgId , String acctId, String dataCentreUserName, String dataCentrePassword, String fname, String fNumber, String fRootOrgID, String fType, String fEndDate, String fStartDate, String fBackupOrg, String fAFFILIATIONID , String jsonArray,Integer type, Boolean isDeleteEntry) {
        return iOrganizationService.organizationLiShuGuanXiBatchSave(fParentOrgId,fOrgId,acctId,dataCentreUserName,dataCentrePassword,fname,fNumber,fRootOrgID,fType,fEndDate,fStartDate,fBackupOrg,fAFFILIATIONID,jsonArray,type,isDeleteEntry);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult deleteLiShu(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids) {
        return iOrganizationService.organizationLiShuGuanXiDelete(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "查看", notes = "查看")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseResult viewLiShu(String acctId,String dataCentreUserName, String dataCentrePassword,String number,String id) {
        return iOrganizationService.organizationLiShuGuanXiView(acctId,dataCentreUserName,dataCentrePassword,number,id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submitLiShu(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids) {
        return iOrganizationService.organizationLiShuGuanXiSubimt(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult auditLiShu(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids) {
        return iOrganizationService.organizationLiShuGuanXiAudit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "反审核", notes = "审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAuditLiShu(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids) {
        return iOrganizationService.organizationLiShuGuanXiUnAudit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "查询业务组织（根据字段）", notes = "查询业务组织（根据字段）")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseResult queryOrganizationLiShu(String acctId,String dataCentreUserName,String dataCentrePassword,String formId,String fieldKeys){
        return iOrganizationService.queryOrganizationLiShu(acctId,dataCentreUserName,dataCentrePassword,formId,fieldKeys);
    }
}
