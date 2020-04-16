package com.lnmj.data.business;

import com.lnmj.data.entity.VO.AmountTyp;
import com.lnmj.data.entity.VO.WalletAmount;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: panlin
 * @Date: 2019/8/21 10:25
 * @Description: 优惠券
 */
@Service("dBService")
public interface BackupsService {
    void saveProductData(List list);

    void saveServiceProductData(List list);

    void saveSupplierData(List list);

    void saveMember(Map map);

    void saveMemberWallet(Map map2);

    List<WalletAmount> selectWalletAmount(Long walletId);

     void updateAmount(List list);

    List<AmountTyp> selectAmountType();
}
