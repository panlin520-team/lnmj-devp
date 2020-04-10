package com.lnmj.wallet.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.entity.RechargeType;

public interface IWalletAccountService {
    ResponseResult selectWalletAccountList(int pageNum, int pageSize, String keyWord);

    ResponseResult selectWalletAccountListNoPage();

    ResponseResult addWalletAccount(RechargeType account);

    ResponseResult updateWalletAccount(RechargeType account);

    ResponseResult deleteWalletAccount(Long accountId);

    ResponseResult selectWalletAccountBySubClass(String subClassId);
}
