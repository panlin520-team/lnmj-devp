package com.lnmj.wallet.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.wallet.entity.WalletBonudDetail;
import com.lnmj.wallet.entity.WalletCashRecord;
import com.lnmj.wallet.entity.WalletCashRecordDetail;
import com.lnmj.wallet.repository.IWalletCashDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class WalletCashDaoImpl extends BaseDao implements IWalletCashDao {

    @Override
    public List<WalletCashRecord> selectCashRecordList(HashMap<Object, Object> map) {
        return super.selectList("walletcashrecord.selectCashrecordList", map);
    }

    @Override
    public List<WalletBonudDetail> selectCashRecordDetailList(Map map) {
        return super.selectList("walletBonudDetail.selectCashRecordDetailList",map);
    }

    @Override
    public int addCashDetail(WalletCashRecordDetail detail) {
        return super.insert("walletcashrecord.addCashDetail", detail);
    }

    @Override
    public int examine(Long id) {
        return super.update("walletcashrecord.examine", id);
    }

    @Override
    public int addCash(WalletCashRecord walletCashRecord) {
        return super.insert("walletcashrecord.addCash", walletCashRecord);
    }

    @Override
    public int updateCashDetail(WalletCashRecordDetail detail) {
        return super.update("walletBonudDetail.updateCashDetail", detail);
    }
}
