package com.lnmj.wallet.repository;


import com.lnmj.wallet.entity.VO.WalletConsumeRecordVO;
import com.lnmj.wallet.entity.WalletConsumeRecord;

import java.util.List;
import java.util.Map;

public interface WalletConsumeRecordDao {
    List<WalletConsumeRecord> selectExpenseCalendarById(Long userId);

    List<WalletConsumeRecord> selectExpenseCalendarList(Map map);

    int deleteExpenseRecordById(Long userId);

    List<WalletConsumeRecordVO> selectListByUserStatistics();

    int deleteCashRecord(Long consumeRecordId);


    List<WalletConsumeRecord> selectUserWalletConsumeRecord(WalletConsumeRecord walletConsumeRecord);
}
