package com.lnmj.product.controller.backend;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IStockService;
import com.lnmj.product.entity.StockStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yilihua
 * @Date: 2019-11-19 11:10
 * @Description: 仓库状态
 */
@Api(description = "仓库状态")
@RestController
@RequestMapping("/stockStatus")
public class StockStatusController {
    @Resource
    private IStockService stockService;

    /**
     *@Description 查询仓库状态
     *@Param [access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/10/28
     *@Time 21:07
     */
    @ApiOperation(value = "查询仓库状态", notes = "查询仓库状态")
    @RequestMapping(value = "/selectStockStatusList", method = RequestMethod.POST)
    public ResponseResult selectStockStatusList(String access_token) {
        return stockService.selectStockStatusList();
    }

    //仓库状态的增删改查，同时调用K3
    /**
    *@Description 添加仓库状态
    *@Param [fName, fNumberCreateOrg, fNumberUseOrg, fType, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019-11-19
    *@Time 18:06
    */
    @ApiOperation(value = "添加仓库状态", notes = "添加仓库状态")
    @RequestMapping(value = "/addStockStatus", method = RequestMethod.POST)
    public ResponseResult addStockStatus(StockStatus stockStatus,String access_token) {
        return stockService.addStockStatus(stockStatus);
    }
    /**
    *@Description 修改仓库状态
    *@Param [fName, fNumberCreateOrg, fNumberUseOrg, fType, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019-11-19
    *@Time 18:06
    */
    @ApiOperation(value = "修改仓库状态", notes = "修改仓库状态")
    @RequestMapping(value = "/editStockStatus", method = RequestMethod.POST)
    public ResponseResult editStockStatus(StockStatus stockStatus, String access_token) {
        return stockService.editStockStatusById(stockStatus);
    }
    /**
    *@Description 删除仓库状态
    *@Param [id, code, modifyOperator, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019-11-19
    *@Time 18:25
    */
    @ApiOperation(value = "删除仓库状态", notes = "删除仓库状态")
    @RequestMapping(value = "/deleteStockStatus", method = RequestMethod.POST)
    public ResponseResult deleteStockStatus(String id,String modifyOperator,String access_token) {
        return stockService.deleteStockStatus(id,modifyOperator);
    }

    @ApiOperation(value = "查询仓库状态", notes = "查询仓库状态")
    @RequestMapping(value = "/queryStockStatus", method = RequestMethod.POST)
    public ResponseResult queryStockStatus(String id,String code,String access_token) {
        return stockService.queryStockStatus(id,code);
    }

}
















