package com.lnmj.wallet.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.wallet.entity.VO.WalletConsumeRecordVO;
import com.lnmj.wallet.entity.WalletConsumeRecord;
import com.lnmj.wallet.repository.WalletConsumeRecordDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class WalletConsumeRecordDaolmpl extends BaseDao implements WalletConsumeRecordDao {

    @Override
    public List<WalletConsumeRecord> selectExpenseCalendarById(Long userId) {
        return super.selectList("walletConsumeRecord.selectExpenseCalendarById", userId);
    }

    @Override
    public List<WalletConsumeRecord> selectExpenseCalendarList(Map map) {
        return super.selectList("walletConsumeRecord.selectExpenseCalendarList",map);
    }

    @Override
    public int deleteExpenseRecordById(Long userId) {
        return super.update("walletConsumeRecord.deleteExpenseRecordById", userId);
    }

    @Override
    public List<WalletConsumeRecordVO> selectListByUserStatistics() {
        return super.selectList("walletConsumeRecord.selectListByUserStatistics");
    }

    @Override
    public int deleteCashRecord(Long consumeRecordId) {
        return super.update("walletConsumeRecord.deleteCashRecord", consumeRecordId);
    }

    @Override
    public List<WalletConsumeRecord> selectUserWalletConsumeRecord(WalletConsumeRecord walletConsumeRecord) {
        return super.select("walletConsumeRecord.selectUserWalletConsumeRecord",walletConsumeRecord);
    }


}
