package com.lnmj.wallet.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.wallet.entity.RechargeType;
import com.lnmj.wallet.entity.WalletAmount;
import com.lnmj.wallet.repository.WalletAccountDao;
import com.lnmj.wallet.repository.WalletAmountDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class WalletAmountDaoImpl extends BaseDao implements WalletAmountDao {
    @Override
    public int updateAmount(WalletAmount walletAmount) {
        return super.update("walletAmount.updateAmount",walletAmount);
    }

    @Override
    public int checkAmountByWalletIdAndTypeId(Map map) {
        return super.select("walletAmount.checkAmountByWalletIdAndTypeId",map);
    }

    @Override
    public int insertAmount(WalletAmount walletAmount) {
        return super.insert("walletAmount.insertSelective",walletAmount);
    }
}
