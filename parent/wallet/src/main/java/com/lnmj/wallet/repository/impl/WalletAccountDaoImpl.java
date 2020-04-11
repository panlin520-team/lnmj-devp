package com.lnmj.wallet.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.wallet.entity.RechargeType;
import com.lnmj.wallet.repository.WalletAccountDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class WalletAccountDaoImpl extends BaseDao implements WalletAccountDao {
    @Override
    public List<RechargeType> selectWalletAccountList(Map map) {
        return super.selectList("rechargeType.selectWalletAccountList",map);
    }

    @Override
    public int addWalletAccount(RechargeType rechargeType) {
        return super.insert("rechargeType.addWalletAccount",rechargeType);
    }

    @Override
    public int updateWalletAccount(RechargeType rechargeType) {
        return super.update("rechargeType.updateWalletAccount",rechargeType);
    }

    @Override
    public int deleteWalletAccount(Long accountId) {
        return super.delete("rechargeType.deleteWalletAccount",accountId);
    }

    @Override
    public int checkIsQingKe() {
        return super.select("rechargeType.checkIsQingKe");
    }

    @Override
    public int checkTypeName(Map map) {
        return super.select("rechargeType.checkTypeName",map);
    }
}
