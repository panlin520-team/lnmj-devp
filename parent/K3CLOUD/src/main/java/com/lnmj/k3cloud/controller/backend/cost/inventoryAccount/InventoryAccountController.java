package com.lnmj.k3cloud.controller.backend.cost.inventoryAccount;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IInventoryAccountService;
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
@Api(description = "金蝶K3-存货核算-核算范围")
@RestController
@RequestMapping("/k3cloud/inventoryAccount")
public class InventoryAccountController {
    @Resource
    private IInventoryAccountService inventoryAccountService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult saveInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword,String fACCTGRANGEID,String fACCTGORGID, String fACCTGSYSTEMID,String fName,String jsonArraySubOrg){
        return inventoryAccountService.saveInventoryAccount(acctId,dataCentreUserName,dataCentrePassword,fACCTGRANGEID,fACCTGORGID,fACCTGSYSTEMID,fName,jsonArraySubOrg);
    }

    @ApiOperation(value = "批量保存", notes = "批量保存")
    @RequestMapping(value = "/batchSave", method = RequestMethod.POST)
    public ResponseResult batchSave(String acctId,String dataCentreUserName,String dataCentrePassword,String accessToken){
        return inventoryAccountService.batchSaveInventoryAccount(acctId,dataCentreUserName,dataCentrePassword);
    }

    @ApiOperation(value = "查看", notes = "查看")
    @RequestMapping(value = "/viewInventoryAccount", method = RequestMethod.POST)
    public ResponseResult viewInventoryAccount(String acctId,String dataCentreUserName,String dataCentrePassword,String number,String id){
        return inventoryAccountService.viewInventoryAccount(acctId,dataCentreUserName,dataCentrePassword,number,id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return inventoryAccountService.submitInventoryAccount(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult audit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return inventoryAccountService.auditInventoryAccount(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "反审核", notes = "反审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return inventoryAccountService.unAuditInventoryAccount(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return inventoryAccountService.deleteInventoryAccount(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "禁用", notes = "禁用")
    @RequestMapping(value = "/forbid", method = RequestMethod.POST)
    public ResponseResult forbid(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return inventoryAccountService.forbidInventoryAccount(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "反禁用", notes = "反禁用")
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public ResponseResult enable(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return inventoryAccountService.enableInventoryAccount(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }
}
