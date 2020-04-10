package com.lnmj.data.serviceProxy;

import com.lnmj.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "lnmj-wallet")
public interface WalletApi {
    @RequestMapping(value = "/manage/wallet/selectListByUserStatistics", method = RequestMethod.POST)
    ResponseResult selectListByUserStatistics();

    @RequestMapping(value = "/manage/wallet/selectRechargeOrderList", method = RequestMethod.POST)
    ResponseResult selectRechargeOrderList(@RequestParam("storeId") String storeId,@RequestParam("date") String date);


    @RequestMapping(value = "/manage/wallet/selectWalletAccountListNoPage", method = RequestMethod.POST)
    ResponseResult selectWalletAccountListNoPage();
}

