package com.lnmj.wallet.serviceProxy;


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

    @RequestMapping(value = "/evaluating/insertEvaluatingDetailed", method = RequestMethod.POST)
    ResponseResult insertEvaluatingDetailed(@RequestParam("orderNum") String orderNum,
                                            @RequestParam("memberNum") String memberNum,
                                            @RequestParam("subclass") Long subclass,
                                            @RequestParam("price") Double price,
                                            @RequestParam("orderType") Integer orderType);
    @RequestMapping(value = "/commodityType/selectSubclassListNoPage",method = RequestMethod.POST)
    ResponseResult selectSubclassListNoPage();

}

