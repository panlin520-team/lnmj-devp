package com.lnmj.wallet.repository;


import com.lnmj.wallet.entity.WalletRechargeRecord;
import com.lnmj.wallet.entity.WalletrechargerecordRefuse;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WalletRechargeRecordDao {

    int addWalletRechargeRecord(WalletRechargeRecord walletRechargeRecord);

    int updateWalletRechargeRecord(WalletRechargeRecord walletRechargeRecord);

    List<WalletRechargeRecord> selectRechargeList(Map map);

    List<WalletRechargeRecord> selectRechargeById(Map map);

    int BackgroundOnlineByCardNumber(Map map);

    int deleteRechargeRecordById(Long rechargeRecordId);

    int audit(WalletRechargeRecord walletRechargeRecord);

    int batchAudit(Map map);

    int selectSumAmount(Map<Object, Object> map);

    Double selectSumAmountAll(Map<Object, Object> map);

    Double selectSumAmountAllRefuse(Map<Object, Object> map);

    Double selectSumAmountMax(Map<Object, Object> map);

    List<WalletRechargeRecord> selectTodayAmount(Map map);

    List<Map> selectTodayAmountRefuse(Map map);

    int selectConsumerAmount(String cardNumber);

    int selectRechargeOrderList(Map mapList);

    int countRechargeOrderSum(Date date);

    List<WalletRechargeRecord> selectRechargeByOrderNum(Map map);

    List<WalletRechargeRecord> selectRechargeOrderListTopTen(Map map);

    int insertRechargeRefuse(WalletrechargerecordRefuse walletrechargerecordRefuse);

    List<WalletrechargerecordRefuse> selectRechargeRefuseList(WalletrechargerecordRefuse walletrechargerecordRefuse);

    WalletRechargeRecord selectRechargeByOrderNumber(Map map);

    int  batchAuditAmount(Map map);
}
