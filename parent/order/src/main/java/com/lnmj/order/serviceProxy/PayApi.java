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
@FeignClient(name = "lnmj-paySettlement")
public interface PayApi {
    @RequestMapping(value = "/manage/payment/selectPayTypeList", method = RequestMethod.POST)
    ResponseResult selectPayTypeList();

    @RequestMapping(value = "/manage/payment/selectPayTypeById", method = RequestMethod.POST)
     ResponseResult selectPayTypeById(@RequestParam("payTypeId") Long payTypeId) ;

}

