package com.lnmj.paySettlement.serviceProxy;


import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @Author: panlin
 * @Date: 2019/6/4 17:57
 * @Description: 
 */
@FeignClient(name = "lnmj-wallet")
public interface WalletApi {
    //修改指定用户钱包充值金额
    @RequestMapping(value = "/wallet/updateRechargeAmount", method = RequestMethod.POST)
    ResponseResult updateRechargeAmount(@RequestParam("userId") Long userId,@RequestParam("updateRechargeAmount") BigDecimal updateRechargeAmount);
    @ApiOperation(value = "通过会员号找钱包", notes = "通过会员号找钱包")
    @RequestMapping(value = "/wallet/selectWalletByCarNum", method = RequestMethod.POST)
     ResponseResult selectWalletByCarNum(@RequestParam("carNum")String carNum);

    @ApiOperation(value = "根据钱包id查看钱包账户余额", notes = "根据钱包id查看钱包账户余额")
    @RequestMapping(value = "/wallet/selectAccountAmountByWalletId", method = RequestMethod.POST)
     ResponseResult selectAccountAmountByWalletId(@RequestParam("walletId")Long walletId) ;

}

