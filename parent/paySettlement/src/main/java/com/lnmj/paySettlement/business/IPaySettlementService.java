package com.lnmj.paySettlement.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.paySettlement.entity.PayType;
import org.springframework.stereotype.Service;

@Service("paySettlementService")
public interface IPaySettlementService {
    ResponseResult alipay(Long orderNumber);
    ResponseResult walletPay(Long orderNumber,Long userId);
    ResponseResult selectPayTypeList(String memberNum,String subClassId,String industryId,String showAll);
    ResponseResult selectPayTypeListAll();
    ResponseResult selectPayTypeById(Long payTypeId);
    ResponseResult selectPayTypeByAccountType(Long accountType);
    ResponseResult deletePayType(Long payTypeId);
    ResponseResult insertPayType(PayType payType);
    ResponseResult alipayFacePay(Long orderNumber);
    ResponseResult wxFacePay(Long orderNumber) throws Exception;

    ResponseResult updatePayType(PayType payType);

    ResponseResult selectPayTypeListBySubClass(String subclass, String industry);
}
