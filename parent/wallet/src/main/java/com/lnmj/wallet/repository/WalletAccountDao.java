package com.lnmj.wallet.repository;

import com.lnmj.wallet.entity.RechargeType;

import java.util.List;
import java.util.Map;

public interface WalletAccountDao {
    List<RechargeType> selectWalletAccountList(Map map);

    int addWalletAccount(RechargeType rechargeType);

    int updateWalletAccount(RechargeType account);

    int deleteWalletAccount(Long accountId);

    int checkIsQingKe();
    int checkTypeName(Map map);
}
