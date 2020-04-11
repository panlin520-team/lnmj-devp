package com.lnmj.wallet.business;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.entity.WalletRechargeRecord;
import com.lnmj.wallet.entity.WalletrechargerecordRefuse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Service("iWalletRechargeService")
public interface IWalletRechargeService {

    ResponseResult selectRechargeList(int pageNum, int pageSize, String keyWord,String storeId,Long accountTypeId,Integer auditStatus);

    ResponseResult selectRechargeById(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,String cardNumber,Integer accountTypeId,String date);

    ResponseResult deleteRechargeRecord(Long rechargeRecordId, String[] ids);

    //审核
    ResponseResult audit(WalletRechargeRecord walletRechargeRecord);

    ResponseResult batchAudit(String walletRechargeRecordList,Integer auditStatus);

    ResponseResult selectSumAmount(String date,String cardNumber, String storeId);

    ResponseResult selectSumAmountAll(String cardNumber);

    ResponseResult selectConsumerAmount(String cardNumber);

    ResponseResult selectRechargeOrderList(String storeId,String date);

    ResponseResult countRechargeOrderSum(String today);

    ResponseResult selectRechargeByOrderNum(String date,String orderNo,String storeId);

    ResponseResult selectRechargeTopTen(Long storeId,String memberNum);

    ResponseResult insertRechargeRefuse(WalletrechargerecordRefuse walletrechargerecordRefuse);


    ResponseResult selectRechargeRefuseList(int pageNum,int pageSize,WalletrechargerecordRefuse walletrechargerecordRefuse);

    ResponseResult selectRechargeRefuseListNoPage(WalletrechargerecordRefuse walletrechargerecordRefuse);

    ResponseResult selectRechargeByOrderNumber(String orderNo);

    ResponseResult auditOrderAmount(String orderList, Integer auditStatus);
}
