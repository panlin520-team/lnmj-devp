package com.lnmj.paySettlement.repository;

import com.lnmj.paySettlement.entity.PayType;

import java.util.List;

public interface IPaySettlementDao {
    List<PayType> selectPayTypeList();
    Integer deletePayType(Long payTypeId);
    Integer insertPayType(PayType payType);

    PayType selectPayTypeById(Long payTypeId);

    PayType selectPayTypeByAccountType(Long accountType);

    int updatePayType(PayType payType);

    List<PayType> selectPayTypeListBySubClass();
}
