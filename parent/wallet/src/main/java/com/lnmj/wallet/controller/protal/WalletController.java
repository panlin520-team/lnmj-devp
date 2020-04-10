package com.lnmj.wallet.controller.protal;


import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAccountEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeWalletEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.business.IWalletConsumeService;
import com.lnmj.wallet.business.IWalletRechargeService;
import com.lnmj.wallet.business.IWalletService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;


@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Resource
    private IWalletService IWalletService;
    @Resource
    private IWalletConsumeService walletConsumeRecordService;
    @Resource
    private IWalletRechargeService IWalletRechargeService;

    /**
     * @Description 开通钱包
     * @Param [userId, rechargeAmount, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/4/28
     * @Time
     */
    @ApiOperation(value = "开通钱包", notes = "开通钱包")
    @RequestMapping(value = "/addWallet" ,method = RequestMethod.POST)
    public ResponseResult addWallet(@RequestParam("carNum")String carNum) {
        if (carNum == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.MEMBER_CODE_NULL.getCode(), ResponseCodeAccountEnum.MEMBER_CODE_NULL.getDesc()));
        }
        return this.IWalletService.addWallet(carNum);
    }

    /**
     * @Description 充值记录查询
     * @Param [userId, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/4/28
     * @Time
     */
    @ApiOperation(value = "充值记录查询", notes = "充值记录查询")
    @RequestMapping(value = "/selectRechargeById", method = RequestMethod.POST)
    public ResponseResult selectRechargeById(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,String cardNumber, Integer accountTypeId,String date) {
        if (StringUtils.isBlank(cardNumber)) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.USER_ID_NULL.getCode(), ResponseCodeWalletEnum.USER_ID_NULL.getDesc()));
        }
        return this.IWalletRechargeService.selectRechargeById(pageNum,pageSize,cardNumber,accountTypeId,date);
    }

    /**
     * @Description 用户钱包余额查询
     * @Param [userId, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/4/28
     * @Time
     */
    @ApiOperation(value = "用户钱包余额查询", notes = "用户钱包余额查询")
    @RequestMapping(value = "/selectWalletBalanceInfoById", method = RequestMethod.POST)
    public ResponseResult selectWalletBalanceInfoById(Long userId,Integer rechargeType, String accessToken) {
        if (userId == null) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.USER_ID_NULL.getCode(), ResponseCodeWalletEnum.USER_ID_NULL.getDesc()));
        }
        return this.IWalletService.selectWalletBalanceInfoById(userId,rechargeType);
    }

    /**
     * @Description 用户消费记录查询
     * @Param [userId, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/4/28
     * @Time
     */
    @ApiOperation(value = "根据用户id查询消费记录查询", notes = "用户消费记录查询")
    @RequestMapping(value = "/selectExpenseCalendarById", method = RequestMethod.POST)
    public ResponseResult selectExpenseCalendarById(Long userId, String accessToken) {
        if (userId == null) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.USER_ID_NULL.getCode(), ResponseCodeWalletEnum.USER_ID_NULL.getDesc()));
        }
        return this.walletConsumeRecordService.selectExpenseCalendarById(userId);
    }

    /**
     *@Description 修改充值金额
     *@Param [userId, updateRechargeAmount] 用户id 修改数额
     *@Return com.lnmj.common.response.ResponseResult
     *@Author panlin
     *@Date 2019/6/4
     *@Time 17:25
     */
    @ApiOperation(value = "修改充值金额", notes = "修改充值金额")
    @RequestMapping(value = "/updateRechargeAmount", method = RequestMethod.POST)
    public ResponseResult updateRechargeAmount(Long userId,BigDecimal updateRechargeAmount) {
        return this.IWalletService.updateRechargeAmount(userId,updateRechargeAmount);
    }


    /**
    *@Description 收益转余额
    *@Param [userId, transferAmount, accountType]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/8/19
    *@Time
    */
    @ApiOperation(value = "收益转余额", notes = "收益转余额")
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public ResponseResult transfer(Long userId,BigDecimal transferAmount,Long accountType) {
        return this.IWalletService.transfer(userId,transferAmount,accountType);
    }



    @ApiOperation(value = "支付后结算收益", notes = "支付后结算收益")
    @RequestMapping(value = "/statisticsProfit", method = RequestMethod.POST)
    public ResponseResult statisticsProfit(String memberNum,String productIds,String serviceIds) {
        return this.IWalletService.statisticsProfit(memberNum,productIds,serviceIds);
    }

    @ApiOperation(value = "通过会员号找钱包", notes = "通过会员号找钱包")
    @RequestMapping(value = "/selectWalletByCarNum", method = RequestMethod.POST)
    public ResponseResult selectWalletByCarNum(String carNum) {
        return this.IWalletService.selectWalletByCarNum(carNum);
    }

    @ApiOperation(value = "根据钱包id查看钱包账户余额", notes = "根据钱包id查看钱包账户余额")
    @RequestMapping(value = "/selectAccountAmountByWalletId", method = RequestMethod.POST)
    public ResponseResult selectAccountAmountByWalletId(Long walletId) {
        return this.IWalletService.selectAccountAmountByWalletId(walletId);
    }
}
