package com.lnmj.k3cloud.controller.backend.account;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.AccountBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3-帐薄")
@RestController
@RequestMapping("/k3cloud/account/accountBook")
public class AccountBookController {
    @Resource
    private AccountBookService accountBookService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult saveZB(
            String acctId,
            String dataCentreUserName,
            String dataCentrePassword,
            String fName,
            String heSuanTiXiK3Number,
            String orgK3Number
    ) {
        return accountBookService.saveAccountBook(acctId, dataCentreUserName, dataCentrePassword, fName, heSuanTiXiK3Number, orgK3Number);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(String acctId, String numbers, String ids, String dataCentreUserName, String dataCentrePassword) {
        return accountBookService.deleteAccountBook(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }

    @ApiOperation(value = "查看", notes = "查看")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseResult view(String acctId, String numbers, String id, String dataCentreUserName, String dataCentrePassword) {
        return accountBookService.viewAccountBook(acctId, dataCentreUserName, dataCentrePassword, numbers, id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submit(String acctId, String numbers, String ids, String dataCentreUserName, String dataCentrePassword) {
        return accountBookService.subimtAccountBook(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult audit(String acctId, String numbers, String ids, String dataCentreUserName, String dataCentrePassword) {
        return accountBookService.auditAccountBook(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }

    @ApiOperation(value = "反审核", notes = "审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAudit(String acctId, String numbers, String ids, String dataCentreUserName, String dataCentrePassword) {
        return accountBookService.unAuditAccountBook(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }
}
