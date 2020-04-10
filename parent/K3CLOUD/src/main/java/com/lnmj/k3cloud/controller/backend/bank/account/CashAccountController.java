package com.lnmj.k3cloud.controller.backend.bank.account;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.ICashAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yilihua
 * @Date: 2019-11-11 11:39
 * @Description:
 */
@Api(description = "金蝶K3-现金账号")
@RestController
@RequestMapping("/k3cloud/cashAccount")
public class CashAccountController {
    @Resource
    private ICashAccountService cashAccountService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult saveCash(String acctId,String dataCentreUserName,String dataCentrePassword,Boolean isautosubmitandaudit,String number,String name,String createOrgId,String useOrgid,String accessToken){
        return cashAccountService.saveCashAccount(acctId,dataCentreUserName,dataCentrePassword,isautosubmitandaudit,number,name,createOrgId,useOrgid);
    }

    @ApiOperation(value = "批量保存", notes = "批量保存")
    @RequestMapping(value = "/batchSave", method = RequestMethod.POST)
    public ResponseResult batchSave(String acctId,String dataCentreUserName,String dataCentrePassword,String accessToken){
        return cashAccountService.batchSaveCashAccount(acctId,dataCentreUserName,dataCentrePassword);
    }

    @ApiOperation(value = "查看", notes = "查看")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseResult view(String acctId,String dataCentreUserName,String dataCentrePassword,String number,String id,String accessToken){
        return cashAccountService.viewCashAccount(acctId,dataCentreUserName,dataCentrePassword,number,id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return cashAccountService.submitCashAccount(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult audit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return cashAccountService.auditCashAccount(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "反审核", notes = "反审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return cashAccountService.unAuditCashAccount(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return cashAccountService.deleteCashAccount(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "禁用", notes = "禁用")
    @RequestMapping(value = "/forbid", method = RequestMethod.POST)
    public ResponseResult forbid(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return cashAccountService.forbidCashAccount(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "反禁用", notes = "反禁用")
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public ResponseResult enable(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return cashAccountService.enableCashAccount(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "分配", notes = "分配")
    @RequestMapping(value = "/allocateCashAccount", method = RequestMethod.POST)
    public ResponseResult AllocateCashAccount(String acctId,String dataCentreUserName,String dataCentrePassword,String pkIds,String orgIds,Boolean isAutoSubmitAndAudit) {
        return cashAccountService.allocateCashAccount(acctId,dataCentreUserName,dataCentrePassword,pkIds,orgIds,isAutoSubmitAndAudit);
    }

}
