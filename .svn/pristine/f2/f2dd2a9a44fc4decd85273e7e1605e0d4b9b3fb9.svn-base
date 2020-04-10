package com.lnmj.k3cloud.controller.backend.stock;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3-库存-采购退料")
@RestController
@RequestMapping("/k3cloud/stock/purchaseReturn")
public class PurchaseReturnController {
    @Resource
    private IStockService stockService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/savePurchaseReturn", method = RequestMethod.POST)
    public ResponseResult savePurchaseReturn(String acctId,
                               String dataCentreUserName,
                               String dataCentrePassword,
                               String fStockOrgId,
                               String fDate,
                               String fSupplierID,
                               String jsonArrayProduct) {
        return stockService.savePurchaseReturn(acctId,
                dataCentreUserName,
                dataCentrePassword,
                fStockOrgId,
                fDate,
                fSupplierID,
                jsonArrayProduct);
    }

    @ApiOperation(value = "批量保存", notes = "批量保存")
    @RequestMapping(value = "/batchSave", method = RequestMethod.POST)
    public ResponseResult batchSave(String accessToken) {
        return stockService.batchSavePurchaseReturn();
    }

    @ApiOperation(value = "查看", notes = "查看")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseResult view(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id, String accessToken) {
        return stockService.viewPurchaseReturn(acctId, dataCentreUserName, dataCentrePassword, number, id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids, String accessToken) {
        return stockService.submitPurchaseReturn(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult audit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids, String accessToken) {
        return stockService.auditPurchaseReturn(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }

    @ApiOperation(value = "反审核", notes = "反审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids, String accessToken) {
        return stockService.unAuditPurchaseReturn(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids, String accessToken) {
        return stockService.deletePurchaseReturn(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }

    @ApiOperation(value = "作废", notes = "作废")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ResponseResult cancel(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids, String accessToken) {
        return stockService.cancelPurchaseReturn(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }

    @ApiOperation(value = "反作废", notes = "反作废")
    @RequestMapping(value = "/unCancel", method = RequestMethod.POST)
    public ResponseResult unCancel(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids, String accessToken) {
        return stockService.unCancelPurchaseReturn(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }

    @ApiOperation(value = "后台反审核", notes = "后台反审核")
    @RequestMapping(value = "/manageUnAudit", method = RequestMethod.POST)
    public ResponseResult manageUnAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids, String accessToken) {
        return stockService.manageUnAuditPurchaseReturn(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }

}
