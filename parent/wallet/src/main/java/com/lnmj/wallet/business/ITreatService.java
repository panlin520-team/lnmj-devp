package com.lnmj.wallet.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.entity.WalletAmount;
import com.lnmj.wallet.entity.WalletConsumeRecord;
import org.springframework.stereotype.Service;

@Service("iTreatService")
public interface ITreatService {

    ResponseResult treatAccountInit(WalletAmount walletAmount);

    ResponseResult selectWalletByCarNumber(String carNum);

    ResponseResult userTreat(Long userId, String orderLink, String mobile, String cartNumber, Long productType,
                             Long productId, String productName, String contacts, String phone);

    ResponseResult treatAccountRecharge(WalletAmount walletAmount);

    ResponseResult selectUserWalletConsumeRecord(WalletConsumeRecord walletConsumeRecord);
}
