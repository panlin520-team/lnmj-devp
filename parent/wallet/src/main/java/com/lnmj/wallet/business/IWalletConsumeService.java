package com.lnmj.wallet.business;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.entity.WalletConsumeRecord;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service("iWalletConsumeService")
public interface IWalletConsumeService {

    ResponseResult selectExpenseCalendarById(Long userId);

    ResponseResult selectExpenseCalendarList(int pageNum, int pageSize, String cardNumber);

    ResponseResult deleteExpenseRecord(Long consumeRecordId, String[] ids);

    ResponseResult selectListByUserStatistics();

    ResponseResult deleteCashRecord(Long consumeRecordId, String[] ids);


    ResponseResult selectUserWalletConsumeRecord(WalletConsumeRecord walletConsumeRecord);
}
