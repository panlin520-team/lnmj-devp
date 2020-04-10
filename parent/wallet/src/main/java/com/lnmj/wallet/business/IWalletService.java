package com.lnmj.wallet.business;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.entity.WalletAmount;
import com.lnmj.wallet.entity.WalletRechargeRecord;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service("iWalletService")
public interface IWalletService {

    ResponseResult addWallet(String carNum);

    ResponseResult selectWalletBalanceInfoById(Long userId,Integer rechargeType);

    ResponseResult recharge(WalletAmount walletAmount);

    ResponseResult insertStoreRechargeAuditRecord(WalletRechargeRecord walletRechargeRecord);

    ResponseResult updateStoreRechargeAuditRecord(WalletRechargeRecord walletRechargeRecord);

    ResponseResult updateRechargeAmount(Long userId, BigDecimal updateRechargeAmount);

    ResponseResult transfer(Long userId, BigDecimal transferAmount, Long accountType);

    ResponseResult selectCashBackList(int pageNum, int pageSize, String keyWord);

    ResponseResult rebateExamine(Long cashBackId, String modifyOperator);

    ResponseResult returnGoods(Long cashBackId, String orderNumber, String cashbackAmount, String mobile, String modifyOperator);

    ResponseResult statisticsProfit(String memberNum, String productIds, String serviceIds);

    ResponseResult selectWalletByCarNum(String carNum);

    ResponseResult selectAccountAmountByWalletId(Long walletId);

    ResponseResult updateWalletAccount(Long walletId,Long accountTypeId,Double amount);

    ResponseResult updateWalletAccountDown(Long walletId,Long accountTypeId,Double amount);


}
