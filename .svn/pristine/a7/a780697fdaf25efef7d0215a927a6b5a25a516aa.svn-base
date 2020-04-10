package com.lnmj.wallet.controller.backend;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.business.ITreatService;
import com.lnmj.wallet.entity.WalletAmount;
import com.lnmj.wallet.entity.WalletConsumeRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yilihua
 * @Date: 2019/8/19 14:52
 * @Description: 请客
 */
@Api(description = "请客")
@RestController
@RequestMapping("/manage/treat")
public class TreatController {
    @Resource
    private ITreatService treatService;
    
    /**
    *@Description 我要请客
    *@Param [userId, orderLink, mobile, cartNumber, productType, productId, productName, contacts, phone, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/26
    *@Time 16:43
    */
    @ApiOperation(value = "我要请客", notes = "我要请客")
    @RequestMapping(value = "/userTreat", method = RequestMethod.POST)
    public ResponseResult userTreat(Long userId, String orderLink, String mobile, String cartNumber, Long productType, Long productId, String productName,
                                    String contacts, String phone, String access_token) {
        return treatService.userTreat(userId,orderLink,mobile,cartNumber,productType,productId,productName,contacts,phone);
    }

    /**
     *@Description 根据用户号查询钱包Id
     *@Param [userId, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/8/19
     *@Time 14:50
     */
    @ApiOperation(value = "根据用户号查询钱包Id", notes = "根据用户号查询钱包Id")
    @RequestMapping(value = "/selectWalletByCarNumber", method = RequestMethod.POST)
    public ResponseResult selectWalletByCarNumber(String carNum, String access_token) {
        return treatService.selectWalletByCarNumber(carNum);
    }

    /**
     *@Description 初始化请客钱包余额
     *@Param [walletAmount, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/8/19
     *@Time 11:45
     */
    @ApiOperation(value = "初始化请客钱包余额", notes = "初始化请客钱包余额")
    @RequestMapping(value = "/treatAccountInit", method = RequestMethod.POST)
    public ResponseResult treatAccountInit(WalletAmount walletAmount, String access_token) {
        return treatService.treatAccountInit(walletAmount);
    }

    /**
    *@Description 充值请客账户
    *@Param [walletAmount, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/17
    *@Time 10:06
    */
    @ApiOperation(value = "充值请客账户", notes = "充值请客账户")
    @RequestMapping(value = "/treatAccountRecharge", method = RequestMethod.POST)
    public ResponseResult treatAccountRecharge(WalletAmount walletAmount, String access_token) {
        return treatService.treatAccountRecharge(walletAmount);
    }

    @ApiOperation(value = "查询钱包消费记录", notes = "查询钱包消费记录")
    @RequestMapping(value = "/selectUserWalletConsumeRecord", method = RequestMethod.POST)
    public ResponseResult selectUserWalletConsumeRecord(WalletConsumeRecord walletConsumeRecord, String access_token) {
        return treatService.selectUserWalletConsumeRecord(walletConsumeRecord);
    }

}
