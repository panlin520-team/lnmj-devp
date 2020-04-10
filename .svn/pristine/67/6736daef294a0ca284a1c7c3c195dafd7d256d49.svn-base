package com.lnmj.k3cloud.controller.backend.account;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.AccountingSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3-会计核算体系")
@RestController
@RequestMapping("/k3cloud/account/accountingSystem")
public class AccountingSystemController {
    @Resource
    private AccountingSystemService accountingSystemService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult save(
            String acctId,
            String dataCentreUserName,
            String dataCentrePassword,
            String fACCTSYSTEMID,
            String fMAINORGID,
            String jsonArraySubOrg,
            String fNumber,
            String fName) {
        return accountingSystemService.saveAccountingSystem(acctId,
                dataCentreUserName,
                dataCentrePassword,
                fACCTSYSTEMID,
                fMAINORGID,
                jsonArraySubOrg,
                 fNumber,
                 fName);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(String acctId, String numbers, String ids, String dataCentreUserName, String dataCentrePassword) {
        return accountingSystemService.deleteAccountingSystem(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }

    @ApiOperation(value = "查看会计核算体系", notes = "查看会计核算体系")
    @RequestMapping(value = "/viewAccountingSystem", method = RequestMethod.POST)
    public ResponseResult viewAccountingSystem(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String id) {
        return accountingSystemService.viewAccountingSystem(acctId, dataCentreUserName, dataCentrePassword, numbers, id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submit(String acctId, String numbers, String ids, String dataCentreUserName, String dataCentrePassword) {
        return accountingSystemService.subimtAccountingSystem(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult audit(String acctId, String numbers, String ids, String dataCentreUserName, String dataCentrePassword) {
        return accountingSystemService.auditAccountingSystem(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }

    @ApiOperation(value = "反审核", notes = "审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAudit(String acctId, String numbers, String ids, String dataCentreUserName, String dataCentrePassword) {
        return accountingSystemService.unAuditAccountingSystem(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }
}
