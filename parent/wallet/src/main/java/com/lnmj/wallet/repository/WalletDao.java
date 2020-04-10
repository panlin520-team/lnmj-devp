package com.lnmj.wallet.repository;


import com.lnmj.wallet.entity.CashBack;
import com.lnmj.wallet.entity.RechargeType;
import com.lnmj.wallet.entity.Wallet;
import com.lnmj.wallet.entity.WalletAmount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WalletDao {
    int addWallet(Wallet wallet);

    Wallet selectWalletInfoById(Long userId);

    List<WalletAmount> selectBalanceInfoById(Map map);

    int checkWallet(String carNum);

    Long selectWalletIdByCardNumber(String cardNumber);

    Wallet selectWalletById(Long walletId);


    int recharge(WalletAmount walletAmount);

    int checkUserWallet(Long userId);

    int updateRechargeAmount(Map map);

    List<RechargeType> selectRechargeType();

    //审核通过后通过用户id拿到钱包id
    Long selectWalletIdByUserId(Long userId);

    //更新钱包
    int updateWallet(Wallet wallet);

    //更新钱包余额
    int updateWalletAmount(WalletAmount walletAmount);

    List<CashBack> selectCashBackList(HashMap<Object, Object> map);

    int rebateExamine(CashBack cashBack);

    int updateWalletAccount(Map map);

    int updateWalletAccountDown(Map map);

    //更新返利金额
    int updateCashBack(CashBack cashBack);

    List<WalletAmount> selectAccountAmountByWalletId(Long walletId);


}
