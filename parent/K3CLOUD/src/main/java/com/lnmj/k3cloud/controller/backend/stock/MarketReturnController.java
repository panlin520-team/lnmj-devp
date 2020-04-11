package com.lnmj.k3cloud.controller.backend.stock;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3-库存-销售退货")
@RestController
@RequestMapping("/k3cloud/stock/marketReturn")
public class MarketReturnController {
    @Resource
    private IStockService stockService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/saveMarketReturn", method = RequestMethod.POST)
    public ResponseResult saveMarketReturn(String acctId,
                                           String dataCentreUserName,
                                           String dataCentrePassword,
                                           String fBillTypeID,
                                           String fDate,
                                           String fSaleOrgId,
                                           String fRetcustId,
                                           String fReturnReason,
                                           String fStockOrgId,
                                           String fReceiveCustId,
                                           String fSettleCustId,
                                           String fPayCustId,
                                           String fSettleCurrId,
                                           String fSettleOrgId,
                                           String jsonArrayProduct){
        return stockService.saveMarketReturn(acctId,dataCentreUserName,dataCentrePassword,fBillTypeID,fDate,fSaleOrgId,fRetcustId,fReturnReason,fStockOrgId,
                fReceiveCustId,fSettleCustId,fPayCustId,fSettleCurrId,fSettleOrgId,jsonArrayProduct);
    }

    @ApiOperation(value = "批量保存", notes = "批量保存")
    @RequestMapping(value = "/batchSave", method = RequestMethod.POST)
    public ResponseResult batchSave(String accessToken){
        return stockService.batchSaveMarketReturn();
    }

    @ApiOperation(value = "查看", notes = "查看")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseResult view(String acctId,String dataCentreUserName,String dataCentrePassword,String number,String id,String accessToken){
        return stockService.viewMarketReturn(acctId,dataCentreUserName,dataCentrePassword,number,id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return stockService.submitMarketReturn(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult audit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return stockService.auditMarketReturn(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "反审核", notes = "反审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return stockService.unAuditMarketReturn(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return stockService.deleteMarketReturn(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "作废", notes = "作废")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ResponseResult cancel(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return stockService.cancelMarketReturn(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "反作废", notes = "反作废")
    @RequestMapping(value = "/unCancel", method = RequestMethod.POST)
    public ResponseResult unCancel(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return stockService.unCancelMarketReturn(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }
    @ApiOperation(value = "后台反审核", notes = "后台反审核")
    @RequestMapping(value = "/manageUnAudit", method = RequestMethod.POST)
    public ResponseResult manageUnAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return stockService.manageUnAuditMarketReturn(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

}
