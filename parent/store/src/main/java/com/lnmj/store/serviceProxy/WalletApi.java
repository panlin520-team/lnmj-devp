package com.lnmj.store.serviceProxy;


import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @Author: yilihua
 * @Date: 2019/5/24 17:57
 * @Description:
 */
@FeignClient(name = "lnmj-wallet")
public interface WalletApi {
    @RequestMapping(value = "/manage/wallet/addCapPool", method = RequestMethod.POST)
    ResponseResult addCapPool(@RequestParam("payType") Integer payType,
                              @RequestParam("type") Integer type,
                              @RequestParam("amount") Double amount,
                              @RequestParam("storeId") Long storeId,
                              @RequestParam("advancesReceivedAccount") Long advancesReceivedAccount,
                              @RequestParam("orderNumber") String orderNumber);
    @RequestMapping(value = "/manage/wallet/addTransfer", method = RequestMethod.POST)
    ResponseResult addTransfer(@RequestParam("capitalPoolId")Long capitalPoolId,
                               @RequestParam("payAmount")Double payAmount,
                               @RequestParam("payType")Integer payType,
                               @RequestParam("toStoreId")Long toStoreId);
}

