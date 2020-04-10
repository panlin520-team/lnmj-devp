package com.lnmj.data.repository;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BackupsDao {
    void saveData(List productList);

    void saveSupplierData(List list);

    void saveServiceProdcutData(List list);

    void saveMember(Map map);

    void saveMemberWallet(Map map2);
}
