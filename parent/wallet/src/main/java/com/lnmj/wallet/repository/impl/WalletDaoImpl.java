package com.lnmj.wallet.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.wallet.entity.CashBack;
import com.lnmj.wallet.entity.RechargeType;
import com.lnmj.wallet.entity.Wallet;
import com.lnmj.wallet.entity.WalletAmount;
import com.lnmj.wallet.repository.WalletDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class WalletDaoImpl extends BaseDao implements WalletDao {
    @Override
    public int addWallet(@Param(value = "wallet") Wallet wallet) {
        return super.insert("wallet.insertSelective", wallet);
    }

    @Override
    public Wallet selectWalletInfoById(Long userId) {
        return super.select("wallet.selectWalletInfoById", userId);
    }

    @Override
    public List<WalletAmount> selectBalanceInfoById(Map map) {
        return super.selectList("wallet.selectBalanceInfoById", map);
    }

    @Override
    public int checkWallet(String carNum) {
        return super.select("wallet.checkWallet", carNum);
    }

    @Override
    public Long selectWalletIdByCardNumber(String cardNumber) {
        return super.select("wallet.selectWalletIdByCardNumber", cardNumber);
    }

    @Override
    public Wallet selectWalletById(Long walletId) {
        return super.select("wallet.selectWalletById", walletId);
    }


    @Override
    public int recharge(WalletAmount walletAmount) {
        return super.update("walletAmount.recharge", walletAmount);
    }

    @Override
    public int checkUserWallet(Long userId) {
        return super.select("wallet.checkUserWallet",userId);
    }

    @Override
    public int updateRechargeAmount(Map map) {
        return super.update("wallet.updateRechargeAmount", map);
    }

    @Override
    public List<RechargeType> selectRechargeType() {
        return selectList("wallet.selectRechargeType");
    }

    @Override
    public Long selectWalletIdByUserId(Long userId) {
        return super.select("wallet.selectWalletIdByUserId", userId);
    }

    @Override
    public int updateWallet(Wallet wallet) {
        return super.update("wallet.updateWallet", wallet);
    }

    @Override
    public int updateWalletAmount(WalletAmount walletAmount) {
        return super.update("wallet.updateWalletAmount", walletAmount);
    }

    @Override
    public List<CashBack> selectCashBackList(HashMap<Object, Object> map) {
        return selectList("cashback.selectCashBackList", map);
    }

    @Override
    public int rebateExamine(CashBack cashBack) {
        return super.update("cashback.update", cashBack);
    }

    @Override
    public int updateWalletAccount(Map map) {
        return super.update("wallet.updateWalletAccount", map);
    }

    @Override
    public int updateWalletAccountDown(Map map) {
        return super.update("wallet.updateWalletAccountDown", map);
    }


    @Override
    public int updateCashBack(CashBack cashBack) {
        return super.update("cashback.update", cashBack);
    }

    @Override
    public List<WalletAmount> selectAccountAmountByWalletId(Long walletId) {
        return super.selectList("wallet.selectAccountAmountByWalletId", walletId);
    }
}
