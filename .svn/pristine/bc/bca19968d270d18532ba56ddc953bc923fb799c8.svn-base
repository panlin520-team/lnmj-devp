package com.lnmj.data.serviceProxy;

import com.lnmj.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/6 17:21
 * @Description:
 */
@FeignClient(value = "lnmj-order")
public interface OrderApi {
    @RequestMapping(value = "/order/selectOrderByNum", method = RequestMethod.POST)
    ResponseResult selectOrderByNum(@RequestParam("orderNumbers") String orderNumbers);

    @RequestMapping(value = "/order/selectOrderProductByOrderNum", method = RequestMethod.POST)
    ResponseResult selectOrderProductByOrderNum(@RequestParam("orderNumber") Long orderNumber);

    @RequestMapping(value = "/order/selectOrderList",method = RequestMethod.POST)
    ResponseResult selectOrderList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,@RequestParam("storeId")Long storeId,
                                          @RequestParam("keyWordOrderNum")String keyWordOrderNum, @RequestParam("keyWordMobile")String keyWordMobile,
                                          @RequestParam("orderType")Integer orderType,@RequestParam("payStatus")Integer payStatus);

    @RequestMapping(value = "/manage/pos/order/selectOrderListByToday", method = RequestMethod.POST)
    ResponseResult selectOrderListByToday(@RequestParam("storeId") String storeId,@RequestParam("date") String date);

}
