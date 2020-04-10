package com.lnmj.data.serviceProxy;

import com.lnmj.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: yilihua
 * @Date: 2019/9/6 17:21
 * @Description:
 */
@FeignClient(value = "lnmj-product")
public interface ProductApi {
    @RequestMapping(value = "/manage/product/selectServiceListById", method = RequestMethod.POST)
    ResponseResult selectServiceListById(@RequestParam("serviceProductIds") String serviceProductIds);

    @RequestMapping(value = "/manage/product/selectPorductListById", method = RequestMethod.POST)
    ResponseResult selectPorductListById(@RequestParam("serviceProductIds") String productIds);

    @RequestMapping(value = "/manage/product/selectPorductListByIdNoPage", method = RequestMethod.POST)
    ResponseResult selectPorductListByIdNoPage(@RequestParam("productIds") String productIds);

    @RequestMapping(value = "/manage/product/selectProductByCode", method = RequestMethod.POST)
    ResponseResult selectProductByCode(@RequestParam("productCode") String productCode);
}
