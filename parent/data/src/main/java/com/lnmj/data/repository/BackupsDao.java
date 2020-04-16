package com.lnmj.data.repository;

import com.lnmj.data.entity.VO.AmountTyp;
import com.lnmj.data.entity.VO.StoreVO;
import com.lnmj.data.entity.VO.WalletAmount;

import java.util.List;
import java.util.Map;

public interface BackupsDao {
    void saveData(List productList);

    void saveSupplierData(List list);

    void saveServiceProdcutData(List list);

    void saveMember(Map map);

    void saveMemberWallet(Map map2);

    void updateAmount(List list);

    List<AmountTyp> selectAmountType();

    List<StoreVO> selectStores();
}
