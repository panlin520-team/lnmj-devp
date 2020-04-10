package com.lnmj.order.serviceProxy;


import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: yilihua
 * @Date: 2019/5/24 17:57
 * @Description:
 */
@FeignClient(name = "lnmj-data")
public interface DataApi {
    @RequestMapping(value = "/api/commodityType/selectSubclassByConditionNoPage", method = RequestMethod.POST)
    ResponseResult selectSubclassByConditionNoPage(@RequestParam("industryID") Long industryID);

    @RequestMapping(value = "/api/commodityType/insertLadderDetailed", method = RequestMethod.POST)
    ResponseResult insertLadderDetailed(@RequestParam("productType") Integer productType,
                                        @RequestParam("storeId") Long storeId,
                                        @RequestParam("beauticianId") String beauticianId,
                                        @RequestParam("orderNum") String orderNum,
                                        @RequestParam("payTypeAndAmount") String payTypeAndAmount,
                                        @RequestParam("productNum") Integer productNum,
                                        @RequestParam("productCode") String productCode,
                                        @RequestParam("price") Double price,
                                        @RequestParam("industryId") String industryId);

    @RequestMapping(value = "/api/commodityType/selectLadderDetailedByRechargeOrderNum", method = RequestMethod.POST)
    ResponseResult selectLadderDetailedByRechargeOrderNum(@RequestParam("orderNumber") Long orderNumber);

    @RequestMapping(value = "/api/commodityType/selectPerformanceById", method = RequestMethod.POST)
    ResponseResult selectPerformanceById(@RequestParam("id") Long id);

    @RequestMapping(value = "/api/commodityType/selectPerformancePostById", method = RequestMethod.POST)
    ResponseResult selectPerformancePostById(@RequestParam("id") Long id);

    @RequestMapping(value = "/evaluating/insertEvaluatingDetailed", method = RequestMethod.POST)
    ResponseResult insertEvaluatingDetailed(@RequestParam("orderNum") String orderNum,
                                            @RequestParam("memberNum") String memberNum,
                                            @RequestParam("subclass") Long subclass,
                                            @RequestParam("price") Double price,
                                            @RequestParam("orderType") Integer orderType,
                                            @RequestParam("productTypeId") Long productTypeId);
    @RequestMapping(value = "/performance/deleteLadderDetailedByOrder", method = RequestMethod.POST)
    ResponseResult deleteLadderDetailedByOrder(@RequestParam("orderNum") String  orderNum,@RequestParam("modifyOperator") String  modifyOperator);
    @RequestMapping(value = "/api/commodityType/selectSubclassListAll", method = RequestMethod.POST)
    ResponseResult selectSubclassListAll();
    @ApiOperation(value = "修改业绩明细", notes = "修改业绩明细")
    @RequestMapping(value = "/performance/updateLadderDetailed", method = RequestMethod.POST)
    ResponseResult updateLadderDetailed(@RequestParam("ladderDetailedOrderId") String ladderDetailedOrderId,@RequestParam("ladderDetailedAmount") Double ladderDetailedAmount);
    @ApiOperation(value = "删除业绩明细", notes = "删除业绩明细")
    @RequestMapping(value = "/performance/deleteLadderDetailedByCondition", method = RequestMethod.POST)
    ResponseResult deleteLadderDetailedByCondition(@RequestParam("ladderDetailedOrderId") String ladderDetailedOrderId);
}

