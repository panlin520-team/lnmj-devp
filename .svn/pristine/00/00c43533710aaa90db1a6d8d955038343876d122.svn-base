package com.lnmj.k3cloud.controller.backend.account;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.AccountPolicyService;
import com.lnmj.k3cloud.business.ICustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3-会计政策")
@RestController
@RequestMapping("/k3cloud/account/accountPolicy")
public class AccountPolicyController {
    @Resource
    private AccountPolicyService accountPolicyService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult save(
            String acctId ,
            String dataCentreUserName,
            String dataCentrePassword,
            String model,
            Boolean isautosubmitandaudit
            )
    {
        return accountPolicyService.saveAccountPolicy(acctId,dataCentreUserName,dataCentrePassword,model,isautosubmitandaudit);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(String acctId ,String numbers,String ids,String dataCentreUserName,String dataCentrePassword) {
        return accountPolicyService.deleteAccountPolicy(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "查看", notes = "查看")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseResult view(String acctId ,String numbers,String id,String dataCentreUserName,String dataCentrePassword) {
        return accountPolicyService.viewAccountPolicy(acctId,dataCentreUserName,dataCentrePassword,numbers,id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submit(String acctId ,String numbers,String ids,String dataCentreUserName,String dataCentrePassword) {
        return accountPolicyService.subimtAccountPolicy(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult audit(String acctId ,String numbers,String ids,String dataCentreUserName,String dataCentrePassword) {
        return accountPolicyService.auditAccountPolicy(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "反审核", notes = "审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAudit(String acctId ,String numbers,String ids,String dataCentreUserName,String dataCentrePassword) {
        return accountPolicyService.unAuditAccountPolicy(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "分配", notes = "分配")
    @RequestMapping(value = "/allocate", method = RequestMethod.POST)
    public ResponseResult allocate(String acctId,String dataCentreUserName,String dataCentrePassword,String PkIds, String TOrgIds) {
        return accountPolicyService.accountPolicyAllocate(acctId,dataCentreUserName,dataCentrePassword,PkIds, TOrgIds);
    }
}
