package com.lnmj.wallet.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.wallet.entity.WalletRechargeRecord;
import com.lnmj.wallet.entity.WalletrechargerecordRefuse;
import com.lnmj.wallet.repository.WalletRechargeRecordDao;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Repository
public class WalletRechargeRecordDaoImpl extends BaseDao implements WalletRechargeRecordDao {
    @Override
    public int addWalletRechargeRecord(WalletRechargeRecord walletRechargeRecord) {
        return insert("walletRechargeRecord.insertWalletRechargeRecord", walletRechargeRecord);
    }

    @Override
    public int updateWalletRechargeRecord(WalletRechargeRecord walletRechargeRecord) {
        return update("walletRechargeRecord.updateWalletRechargeRecord", walletRechargeRecord);
    }

    @Override
    public List<WalletRechargeRecord> selectRechargeList(Map map) {
        return selectList("walletRechargeRecord.selectRechargeList",map);
    }

    @Override
    public List<WalletRechargeRecord> selectRechargeById(Map map) {
        return selectList("walletRechargeRecord.selectRechargeById", map);
    }

    @Override
    public int BackgroundOnlineByCardNumber(Map map) {
        return update("walletRechargeRecord.BackgroundOnline", map);
    }

    @Override
    public int deleteRechargeRecordById(Long rechargeRecordId) {
        return update("walletRechargeRecord.deleteRechargeRecordById", rechargeRecordId);
    }

    @Override
    public int audit(WalletRechargeRecord walletRechargeRecord) {
        return update("walletRechargeRecord.audit", walletRechargeRecord);
    }

    @Override
    public int batchAudit(Map map) {
        return update("walletRechargeRecord.batchAudit", map);
    }

    @Override
    public int selectSumAmount(Map<Object, Object> map) {
        return select("walletRechargeRecord.selectSumAmount", map);
    }

    @Override
    public Double selectSumAmountAll(Map<Object, Object> map) {
        return super.select("walletRechargeRecord.selectSumAmountAll",map);
    }

    @Override
    public Double selectSumAmountAllRefuse(Map<Object, Object> map) {
        return super.select("walletRechargeRecord.selectSumAmountAllRefuse",map);
    }

    @Override
    public Double selectSumAmountMax(Map<Object, Object> map) {
        return super.select("walletRechargeRecord.selectSumAmountMax",map);
    }

    @Override
    public int selectConsumerAmount(String cardNumber) {
        return select("walletRechargeRecord.selectConsumerAmount",cardNumber);
    }

    @Override
    public int selectRechargeOrderList(Map mapList) {
        return select("walletRechargeRecord.selectRechargeOrderList",mapList);
    }

    @Override
    public int countRechargeOrderSum(Date date) {
        return select("walletRechargeRecord.countRechargeOrderSum",date);
    }

    @Override
    public List<WalletRechargeRecord> selectRechargeByOrderNum(Map map) {
        return selectList("walletRechargeRecord.selectRechargeByOrderNum",map);
    }

    @Override
    public List<WalletRechargeRecord> selectRechargeOrderListTopTen(Map map) {
        return selectList("walletRechargeRecord.selectRechargeOrderListTopTen",map);
    }

    @Override
    public int insertRechargeRefuse(WalletrechargerecordRefuse walletrechargerecordRefuse) {
        return insert("walletRechargeRecord.insertRechargeRefuse",walletrechargerecordRefuse);
    }

    @Override
    public List<WalletrechargerecordRefuse> selectRechargeRefuseList(WalletrechargerecordRefuse walletrechargerecordRefuse) {
        return selectList("walletRechargeRecord.selectRechargeRefuseList",walletrechargerecordRefuse);
    }

    @Override
    public WalletRechargeRecord selectRechargeByOrderNumber(Map map) {
        return select("walletRechargeRecord.selectRechargeByOrderNumber",map);
    }

    @Override
    public int batchAuditAmount(Map map) {
        return update("walletRechargeRecord.batchAuditAmount", map);
    }

    @Override
    public List<WalletRechargeRecord> selectTodayAmount(Map map) {
        return selectList("walletRechargeRecord.selectTodayAmount",map);
    }

    @Override
    public List<Map> selectTodayAmountRefuse(Map map) {
        return selectList("walletRechargeRecord.selectTodayAmountRefuse",map);
    }
}
