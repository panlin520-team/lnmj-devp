package com.lnmj.store.repository;

import com.lnmj.store.entity.Store;
import com.lnmj.store.entity.StoreCategory;
import com.lnmj.store.entity.StoreMemoNum;
import com.lnmj.store.entity.VO.StoreVO;

import java.util.List;
import java.util.Map;


public interface IStoreDao {
    List<StoreVO> selectStoretList(Map map);
    Store selectStoreById(Long storeId);
    List<Store> selectStoreByCodeOrName(Map map);
    List<Store> selectStoresByIds(Map map);
    int insertStore(Store store);
    int checkStoreName(String storeName);
    int checkStoreK3Number(String k3Number);
    int updateStoreByCodeOrId(Store store);
    int deleteStore(Map map);
    List<StoreCategory> storeCategoryList(Map map);
    int updateStoreCategory(StoreCategory storeCategory);
    int insertStoreCategory(StoreCategory storeCategory);
    int deleteStoreCategory(Long storeCategoryId);
    List<Store> selectStoreListByCompanyId(Long companyId);
    List<Store> selectStoreListBySubCompany(Map map);
    int addStoreMemoNum(StoreMemoNum storeMemoNum);
    List<StoreMemoNum> listStoreMemo(StoreMemoNum storeMemoNum);
    int checkStoreCategoryName(StoreCategory storeCategory);
    int checkStoreNumber(String code);
}
