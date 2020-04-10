package com.lnmj.product.serviceProxy;

import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "lnmj-data")
public interface DataApi {
    @RequestMapping(value = "/commodityType/selectCommodityTypeListNoPage", method = RequestMethod.POST)
    ResponseResult selectCommodityTypeListNoPage();
    @RequestMapping(value = "/commodityType/selectSubclassListNoPage",method = RequestMethod.POST)
     ResponseResult selectSubclassListNoPage();

    @RequestMapping(value = "/performance/selectPerformancePostListAll", method = RequestMethod.POST)
     ResponseResult selectPerformancePostListAll();



}

