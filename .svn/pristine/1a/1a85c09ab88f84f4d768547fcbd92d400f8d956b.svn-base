package com.lnmj.wallet.serviceProxy;

import com.lnmj.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: panlin
 * @Date: 2019/4/19 15:35
 * @Description:
 */
@FeignClient(value = "lnmj-product")
public interface ProductApi {
    @RequestMapping(value = "/api/manage/product/selectPorductListById", method = RequestMethod.POST)
    ResponseResult selectPorductListById(@RequestParam("productIds") String productIds);
    @RequestMapping(value = "/api/manage/product/selectServiceListById", method = RequestMethod.POST)
    ResponseResult selectServiceListById(@RequestParam("serviceProductIds") String serviceProductIds);

}

