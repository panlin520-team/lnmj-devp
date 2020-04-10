package com.lnmj.k3cloud.controller.backend.supplier;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IOrganizationService;
import com.lnmj.k3cloud.business.ISupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3-供应商-供应商")
@RestController
@RequestMapping("/k3cloud/supplier/supplier")
public class SupplierController {
    @Resource
    private ISupplierService iSupplierService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult saveSupplier(String acctId,String dataCentreUserName,String dataCentrePassword,String fName,String fCreateOrgId,String fUseOrgId,String fPayCurrencyId,String fLocationInfoListStr,String needUpDateField,Integer fSupplierId) {
        return iSupplierService.supplierSave(acctId,dataCentreUserName,dataCentrePassword,fName,fCreateOrgId,fUseOrgId,fPayCurrencyId,fLocationInfoListStr,needUpDateField,fSupplierId);
    }

    @ApiOperation(value = "批量保存", notes = "批量保存")
    @RequestMapping(value = "/batchsave", method = RequestMethod.POST)
    public ResponseResult batchsave(String acctId,String dataCentreUserName,String dataCentrePassword,String supplierArr/*,String fName,String fCreateOrgId,String fUseOrgId,String fPayCurrencyId,String fLocationInfoListStr*/) {
        return iSupplierService.batchsave(acctId,dataCentreUserName,dataCentrePassword,supplierArr/*,fName,fCreateOrgId,fUseOrgId,fPayCurrencyId,fLocationInfoListStr*/);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult deleteSupplier(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids) {
        return iSupplierService.supplierDelete(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "查看", notes = "查看")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseResult viewSupplier(String acctId,String dataCentreUserName,String dataCentrePassword,String number,String id) {
        return iSupplierService.supplierView(acctId,dataCentreUserName,dataCentrePassword,number,id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submitSupplier(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids) {
        return iSupplierService.supplierSubimt(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult auditSupplier(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids) {
        return iSupplierService.supplierAudit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "反审核", notes = "审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAuditSupplier(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids) {
        return iSupplierService.supplierUnAudit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "分配", notes = "分配")
    @RequestMapping(value = "/AllocateSupplier", method = RequestMethod.POST)
    public ResponseResult AllocateSupplier(String acctId,String dataCentreUserName,String dataCentrePassword,String pkIds,String orgIds,Boolean isAutoSubmitAndAudit) {
        return iSupplierService.supplierAllocate(acctId,dataCentreUserName,dataCentrePassword,pkIds,orgIds,isAutoSubmitAndAudit);
    }
}
