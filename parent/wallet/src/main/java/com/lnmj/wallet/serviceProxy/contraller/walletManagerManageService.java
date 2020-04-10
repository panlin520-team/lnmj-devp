package com.lnmj.wallet.serviceProxy.contraller;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeWalletEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.business.IWalletCashService;
import com.lnmj.wallet.business.IWalletConsumeService;
import com.lnmj.wallet.business.IWalletRechargeService;
import com.lnmj.wallet.business.IWalletService;
import com.lnmj.wallet.entity.WalletRechargeRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Api(description = "商城钱包接口")
@RestController
@RequestMapping("/api/manage/wallet")
public class walletManagerManageService {
    @Resource
    private IWalletService walletService;
    @Resource
    private IWalletRechargeService walletRechargeService;

    @ApiOperation(value = "充值记录查询通过订单号", notes = "充值记录查询通过订单号")
    @RequestMapping(value = "/selectRechargeByOrderNum", method = RequestMethod.POST)
    public ResponseResult selectRechargeByOrderNum(String date,String orderNo,String storeId) {
        return this.walletRechargeService.selectRechargeByOrderNum(date,orderNo,storeId);
    }

    @ApiOperation(value = "添加门店充值审核记录", notes = "添加门店充值审核记录")
    @RequestMapping(value = "/insertStoreRechargeAuditRecord", method = RequestMethod.POST)
    public ResponseResult insertStoreRechargeAuditRecord(WalletRechargeRecord walletRechargeRecord) {
        return this.walletService.insertStoreRechargeAuditRecord(walletRechargeRecord);
    }

    @ApiOperation(value = "通过会员号找钱包", notes = "通过会员号找钱包")
    @RequestMapping(value = "/selectWalletByCarNum", method = RequestMethod.POST)
    public ResponseResult selectWalletByCarNum(String carNum) {
        return this.walletService.selectWalletByCarNum(carNum);
    }

    @ApiOperation(value = "根据钱包id查看钱包账户余额", notes = "根据钱包id查看钱包账户余额")
    @RequestMapping(value = "/selectAccountAmountByWalletId", method = RequestMethod.POST)
    public ResponseResult selectAccountAmountByWalletId(Long walletId) {
        return this.walletService.selectAccountAmountByWalletId(walletId);
    }

    @ApiOperation(value = "修改对应钱包账户金额", notes = "修改对应钱包账户金额")
    @RequestMapping(value = "/updateWalletAccount", method = RequestMethod.POST)
    public ResponseResult updateWalletAccount(Long walletId,Long accountTypeId,Double amount) {
        return this.walletService.updateWalletAccount(walletId,accountTypeId,amount);
    }

    @ApiOperation(value = "修改对应钱包账户金额", notes = "修改对应钱包账户金额")
    @RequestMapping(value = "/updateWalletAccountDown", method = RequestMethod.POST)
    public ResponseResult updateWalletAccountDown(Long walletId,Long accountTypeId,Double amount) {
        return this.walletService.updateWalletAccountDown(walletId,accountTypeId,amount);
    }




}
