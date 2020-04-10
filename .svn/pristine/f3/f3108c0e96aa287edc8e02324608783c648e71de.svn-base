package com.lnmj.authority.serviceProxy;

import com.lnmj.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "lnmj-product")
public interface ProductApi {
    @RequestMapping(value = "/stock/selectStockList",method = RequestMethod.POST)
    ResponseResult selectStockList(@RequestParam("companyType") Long companyType,@RequestParam("companyId") Long companyId);

}

