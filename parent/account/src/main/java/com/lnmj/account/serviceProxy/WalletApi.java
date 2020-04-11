package com.lnmj.account.serviceProxy;


import com.lnmj.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: panlin
 * @Date: 2019/4/19 15:35
 * @Description:
 */

@FeignClient(name = "lnmj-wallet",url = "localhost:89")
public interface WalletApi {
    @RequestMapping(value = "/wallet/addWallet", method = RequestMethod.POST)
    ResponseResult addWallet(@RequestParam("carNum") String carNum);

    @RequestMapping(value = "/wallet/batchaddWallet", method = RequestMethod.POST)
    ResponseResult batchaddWallet(@RequestParam("carNumList") List<String> carNumList);

    @RequestMapping(value = "/wallet/selectWalletBalanceInfoById", method = RequestMethod.POST)
    ResponseResult selectWalletBalanceInfoById(@RequestParam("userId") Long userId);

    @RequestMapping(value = "/manage/wallet/selectWalletAccountList", method = RequestMethod.POST)
    ResponseResult selectWalletAccountList();

    @RequestMapping(value = "/manage/treat/selectWalletByCarNumber", method = RequestMethod.POST)
    ResponseResult selectWalletByCarNumber(@RequestParam("carNum") String carNum);

    @RequestMapping(value = "/manage/treat/treatAccountInit", method = RequestMethod.POST)
    ResponseResult treatAccountInit(@RequestParam("walletId") Long walletId, @RequestParam("accountTypeId") Long accountTypeId
            , @RequestParam("amount") BigDecimal amount);

    @RequestMapping(value = "/manage/wallet/selectSumAmount", method = RequestMethod.POST)
    ResponseResult selectSumAmount(@RequestParam("userId") Long userId);


    @RequestMapping(value = "/manage/treat/treatAccountRecharge", method = RequestMethod.POST)
    ResponseResult treatAccountRecharge(@RequestParam("walletId") Long walletId, @RequestParam("accountTypeId") Long accountTypeId
            , @RequestParam("amount") BigDecimal amount);

    @RequestMapping(value = "/manage/treat/selectUserWalletConsumeRecord", method = RequestMethod.POST)
    ResponseResult selectUserWalletConsumeRecord(@RequestParam("userId") Long userId,@RequestParam("walletId") Long walletId,
                                                 @RequestParam("accountTypeId") Long accountTypeId);



    @RequestMapping(value = "/wallet/selectWalletByCarNum", method = RequestMethod.POST)
    ResponseResult selectWalletByCarNum(@RequestParam("carNum") String carNum);


    @RequestMapping(value = "/wallet/selectAccountAmountByWalletId", method = RequestMethod.POST)
    ResponseResult selectAccountAmountByWalletId(@RequestParam("walletId") Long walletId);

    @RequestMapping(value = "/manage/wallet/selectSumAmountAll", method = RequestMethod.POST)
    ResponseResult selectSumAmountAll(@RequestParam("cardNumber") String cardNumber);

}

