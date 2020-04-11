package com.lnmj.account.serviceProxy;

import com.lnmj.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "lnmj-order",url = "localhost:85")
public interface OrderApi {
    @RequestMapping(value = "/order/selectOrderListByCarNumber",method = RequestMethod.POST)
    ResponseResult selectOrderListByCarNumber(@RequestParam("carNumber") Long carNumber,@RequestParam("orderType")Integer orderType);

    @RequestMapping(value = "/order/selectMemberCashAll",method = RequestMethod.POST)
    ResponseResult selectMemberCashAll(@RequestParam("memberNumber") String memberNumber);

    @RequestMapping(value = "/order/selectCreateTimeByCarNumber",method = RequestMethod.POST)
    ResponseResult selectCreateTimeByCarNumber(@RequestParam("carNumber") String carNumber);

    @RequestMapping(value = "/order/selectOrderListNoPage",method = RequestMethod.POST)
    ResponseResult selectOrderListNoPage();

    @RequestMapping(value = "/order/selectOrderListByCarNumAndDates",method = RequestMethod.POST)
    ResponseResult selectOrderListByCarNumAndDates(@RequestParam("cardNumber") String cardNumber,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate);
}

