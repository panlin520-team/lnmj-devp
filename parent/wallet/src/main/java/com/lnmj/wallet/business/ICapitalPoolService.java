package com.lnmj.wallet.business;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.entity.CapitalPool;
import com.lnmj.wallet.entity.WalletAmount;
import com.lnmj.wallet.entity.WalletRechargeRecord;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service("iCapitalPoolService")
public interface ICapitalPoolService {

    ResponseResult selectCapitalPool(int pageNum,int pageSize, Long storeId);

    ResponseResult addCapPool(Integer payType, Integer type, String amount, Long storeId, Long advancesReceivedAccount,String orderNumber);

    ResponseResult addTransfer(Long capitalPoolId,Double payAmount,Long payType,Long toStoreId);

    ResponseResult selectTransferIn(int pageNum,int pageSize,Long capitalPoolId,Long storeId);

    ResponseResult selectTransferOut(int pageNum,int pageSize,Long capitalPoolId,Long storeId);

    ResponseResult reduceForwardSaleByCondition(CapitalPool capitalPool);

    ResponseResult deleteCapPoolByOrderNumber(CapitalPool capitalPool);
}
