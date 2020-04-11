package com.lnmj.wallet.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.entity.WalletCashRecord;
import com.lnmj.wallet.entity.WalletCashRecordDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iWalletCashService")
public interface IWalletCashService {
    ResponseResult selectCashRecordList(int pageNum, int pageSize, Integer status, String cashTime);

    ResponseResult selectCashRecordDetailList(int pageNum, int pageSize, String bonusTypeId, String cashRecordId);

    ResponseResult addCash(WalletCashRecord walletCashRecord, List<WalletCashRecordDetail> detailList);

    ResponseResult examine(String cashRecordId);
}
