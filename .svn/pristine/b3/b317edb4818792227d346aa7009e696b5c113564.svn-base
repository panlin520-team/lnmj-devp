package com.lnmj.paySettlement.serviceProxy;


import com.lnmj.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: panlin
 * @Date: 2019/6/4 17:57
 * @Description: 
 */
@FeignClient(name = "lnmj-order")
public interface OrderApi {
    //修改订单状态
    @RequestMapping(value = "/order/updateOrderStatus", method = RequestMethod.POST)
    ResponseResult updateOrderStatus(@RequestParam("orderNumber") Long orderNumber, @RequestParam("status") Integer status);


}

