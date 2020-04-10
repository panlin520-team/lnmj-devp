package com.lnmj.account.serviceProxy;


import com.lnmj.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @Auther: panlin
 * @Date: 2019/4/19 15:35
 * @Description:
 */

@FeignClient(value = "lnmj-paySettlement")
public interface PaymentApi {
    @RequestMapping(value = "/manage/payment/selectPayTypeByAccountType", method = RequestMethod.POST)
    ResponseResult selectPayTypeByAccountType(@RequestParam("accountType") Long accountType);



}

