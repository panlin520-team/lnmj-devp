package com.lnmj.k3cloud.controller.backend.stock;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IStockService;
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
@Api(description = "金蝶K3-库存-仓库状态")
@RestController
@RequestMapping("/k3cloud/stockStatus")
public class StockStatusController {
    @Resource
    private IStockService stockService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult save(String acctId,String dataCentreUserName,String dataCentrePassword,String fName,String fNumberCreateOrg,String fNumberUseOrg,String fType,String fNumber,String fStockStatusId ,String accessToken){
        return stockService.saveStockStatus(acctId,dataCentreUserName,dataCentrePassword,fName,fNumberCreateOrg,fNumberUseOrg,fType,fNumber,fStockStatusId);
    }

    @ApiOperation(value = "批量保存", notes = "批量保存")
    @RequestMapping(value = "/batchSave", method = RequestMethod.POST)
    public ResponseResult batchSave(String accessToken){
        return stockService.batchSaveStockStatus();
    }

    @ApiOperation(value = "查看", notes = "查看")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseResult view(String acctId,String dataCentreUserName,String dataCentrePassword,String number,String id,String accessToken){
        return stockService.viewStockStatus(acctId,dataCentreUserName,dataCentrePassword,number,id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return stockService.submitStockStatus(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult audit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return stockService.auditStockStatus(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "反审核", notes = "反审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return stockService.unAuditStockStatus(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return stockService.deleteStockStatus(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "禁用", notes = "禁用")
    @RequestMapping(value = "/forbid", method = RequestMethod.POST)
    public ResponseResult forbid(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return stockService.forbidStockStatus(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "反禁用", notes = "反禁用")
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public ResponseResult enable(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids,String accessToken){
        return stockService.enableStockStatus(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

}
