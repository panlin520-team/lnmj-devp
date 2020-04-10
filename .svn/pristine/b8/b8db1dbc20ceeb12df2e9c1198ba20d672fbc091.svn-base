package com.lnmj.k3cloud.controller.backend.stock;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IStockService;
import com.lnmj.k3cloud.entity.stock.inventory.InventorySaveParam;
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
@Api(description = "金蝶K3-库存-即时库存")
@RestController
@RequestMapping("/k3cloud/inventory")
public class InventoryController {
    @Resource
    private IStockService stockService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult save(String acctId,String dataCentreUserName,String dataCentrePassword,InventorySaveParam inventorySaveParam,String accessToken){
        return stockService.saveInventory(acctId,dataCentreUserName , dataCentrePassword,inventorySaveParam);
    }

    @ApiOperation(value = "批量保存", notes = "批量保存")
    @RequestMapping(value = "/batchSave", method = RequestMethod.POST)
    public ResponseResult batchSave(String accessToken){
        return stockService.batchSaveInventory();
    }

    @ApiOperation(value = "查询及时库存", notes = "查询及时库存")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseResult query(String acctId,String dataCentreUserName,String dataCentrePassword,String formId,String fieldKeys,String accessToken){
        return stockService.queryInventory(acctId,dataCentreUserName,dataCentrePassword,formId,fieldKeys);
    }

//    @ApiOperation(value = "初始库存导入", notes = "初始库存导入")
//    @RequestMapping(value = "/query", method = RequestMethod.POST)
//    public ResponseResult query(String formId,String fieldKeys,String accessToken){
//        return stockService.queryInventory(formId,fieldKeys);
//    }

}
