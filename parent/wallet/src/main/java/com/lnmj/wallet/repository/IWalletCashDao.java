package com.lnmj.wallet.repository;

import com.lnmj.wallet.entity.WalletBonudDetail;
import com.lnmj.wallet.entity.WalletCashRecord;
import com.lnmj.wallet.entity.WalletCashRecordDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface IWalletCashDao {

    List<WalletCashRecord> selectCashRecordList(HashMap<Object, Object> map);

    List<WalletBonudDetail> selectCashRecordDetailList(Map map);

    int addCashDetail(WalletCashRecordDetail detail);

    int addCash(WalletCashRecord walletCashRecord);

    int examine(Long id);

    int updateCashDetail(WalletCashRecordDetail detail);
}
