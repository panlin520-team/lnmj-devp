package com.lnmj.data.serviceProxy.controller;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.business.ICommodityTypeService;
import com.lnmj.data.business.IPerformanceService;
import com.lnmj.data.entity.Subclass;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yilihua
 * @Date: 2019/9/4 15:17
 * @Description: 商品大类、商品小类
 */
@Api(description = "商品大类、商品小类api")
@RestController
@RequestMapping("/api/commodityType")
public class CommodityTypeManageService {
    @Resource
    private ICommodityTypeService commodityTypeService;
    @Resource
    private IPerformanceService iPerformanceService;

    @ApiOperation(value = "条件查询商品小类",notes = "条件查询商品小类")
    @RequestMapping(value = "/selectSubclassByCondition",method = RequestMethod.POST)
    public ResponseResult selectSubclassByCondition(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                                    Subclass subclass,Long industryID ,String access_token,Long storeId) {
        return commodityTypeService.selectSubclassByCondition(pageNum,pageSize,subclass,industryID,storeId);
    }

    @ApiOperation(value = "查询商品小类",notes = "查询商品小类")
    @RequestMapping(value = "/selectSubclassList",method = RequestMethod.POST)
    public ResponseResult selectSubclassList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                             @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,String commodityTypeID, String searchWord) {
        return commodityTypeService.selectSubclassList(pageNum,pageSize,commodityTypeID,searchWord);
    }

    @ApiOperation(value = "查询商品小类",notes = "查询商品小类")
    @RequestMapping(value = "/selectSubclassListAll",method = RequestMethod.POST)
    public ResponseResult selectSubclassListAll() {
        return commodityTypeService.selectSubclassListAll();
    }

    @ApiOperation(value = "条件查询商品小类分页",notes = "条件查询商品小类分页")
    @RequestMapping(value = "/selectSubclassByConditionNoPage",method = RequestMethod.POST)
    public ResponseResult selectSubclassByConditionNoPage(Subclass subclass,Long industryID) {
        return commodityTypeService.selectSubclassByConditionNoPage(subclass,industryID);
    }

    @ApiOperation(value = "新增业绩明细", notes = "新增业绩明细")
    @RequestMapping(value = "/insertLadderDetailed", method = RequestMethod.POST)
    public ResponseResult insertLadderDetailed(Integer productType,Long storeId, String beauticianId, String orderNum, String payTypeAndAmount,Integer productNum,String productCode,Double price,String industryId) {
        return iPerformanceService.insertLadderDetailed(productType,storeId, beauticianId, orderNum, payTypeAndAmount,productNum,productCode,price,industryId);
    }

    @ApiOperation(value = "根据储值订单号查看业绩明显", notes = "根据储值订单号查看业绩明显")
    @RequestMapping(value = "/selectLadderDetailedByRechargeOrderNum", method = RequestMethod.POST)
    public ResponseResult selectLadderDetailedByRechargeOrderNum(Long orderNumber) {
        return iPerformanceService.selectLadderDetailedByRechargeOrderNum(orderNumber);
    }

    @ApiOperation(value = "根据业绩id查询业绩", notes = "根据业绩id查询业绩")
    @RequestMapping(value = "/selectPerformanceById", method = RequestMethod.POST)
    public ResponseResult selectPerformanceById(Long id) {
        return iPerformanceService.selectPerformanceById(id);
    }

    @ApiOperation(value = "根据业绩id查询业绩具体", notes = "根据业绩id查询业绩具体")
    @RequestMapping(value = "/selectPerformancePostById", method = RequestMethod.POST)
    public ResponseResult selectPerformancePostById(Long id) {
        return iPerformanceService.selectPerformancePostById(id);
    }
}
