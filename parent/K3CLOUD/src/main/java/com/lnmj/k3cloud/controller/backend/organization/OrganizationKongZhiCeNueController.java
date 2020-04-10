package com.lnmj.k3cloud.controller.backend.organization;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3-组织结构-资料控制策略")
@RestController
@RequestMapping("/k3cloud/organization/OrganizationKongZhiCeNue")
public class OrganizationKongZhiCeNueController {
    @Resource
    private IOrganizationService iOrganizationService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult saveKongZhiCeNue(String fBRTypeId,String fRelationOrgID,String fOrgId,String acctId,String dataCentreUserName, String dataCentrePassword) {
        return iOrganizationService.organizationKongZhiCeNueSave(fBRTypeId,fRelationOrgID,fOrgId,acctId,dataCentreUserName,dataCentrePassword);
    }

    @ApiOperation(value = "批量保存", notes = "批量保存")
    @RequestMapping(value = "/batchSave", method = RequestMethod.POST)
    public ResponseResult batchSaveKongZhiCeNue(String acctId, String dataCentreUserName, String dataCentrePassword,String jsonArray) {
        return iOrganizationService.organizationKongZhiCeNueBatchSave(acctId,dataCentreUserName,dataCentrePassword,jsonArray);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult deleteKongZhiCeNue(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids) {
        return iOrganizationService.organizationKongZhiCeNueDelete(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "查看", notes = "查看")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseResult viewKongZhiCeNue(String acctId,String dataCentreUserName, String dataCentrePassword,String number,String id) {
        return iOrganizationService.organizationKongZhiCeNueView(acctId,dataCentreUserName,dataCentrePassword,number,id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submitKongZhiCeNue(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids) {
        return iOrganizationService.organizationKongZhiCeNueSubimt(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult auditKongZhiCeNue(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids) {
        return iOrganizationService.organizationKongZhiCeNueAudit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "反审核", notes = "审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAuditKongZhiCeNue(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids) {
        return iOrganizationService.organizationKongZhiCeNueUnAudit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "查询业务组织（根据字段）", notes = "查询业务组织（根据字段）")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseResult queryOrganizationKongZhiCeNue(String acctId,String dataCentreUserName,String dataCentrePassword,String formId,String fieldKeys){
        return iOrganizationService.queryOrganizationKongZhiCeNue(acctId,dataCentreUserName,dataCentrePassword,formId,fieldKeys);
    }
}
