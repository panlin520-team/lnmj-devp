package com.lnmj.store.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.store.entity.Store;
import com.lnmj.store.entity.StoreCategory;
import com.lnmj.store.entity.StoreMemoNum;
import com.lnmj.store.entity.VO.StoreVO;
import com.lnmj.store.repository.IStoreDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2019/5/8
 */

@Repository
public class StoreDaoImpl extends BaseDao implements IStoreDao {
    @Override
    public List<StoreVO> selectStoretList(Map map) {
        return super.selectList("store.selectStoretList",map);
    }

    @Override
    public Store selectStoreById(Long storeId) {
        return super.select("store.selectStoreById",storeId);
    }

    @Override
    public List<Store> selectStoreByCodeOrName(Map map) {
        return super.selectList("store.selectStoreByCodeOrName",map);
    }

    @Override
    public List<Store> selectStoresByIds(Map map) {
        return super.selectList("store.selectStoresByIds",map);
    }

    @Override
    public int insertStore(Store store) {
        return super.insert("store.insertSelective",store);
    }

    @Override
    public int checkStoreName(String storeName) {
        return super.select("store.checkStoreName",storeName);
    }

    @Override
    public int checkStoreK3Number(String k3Number) {
        return super.select("store.checkStoreK3Number",k3Number);
    }

    @Override
    public int updateStoreByCodeOrId(Store store) {
        return super.update("store.updateStoreByCodeOrId",store);
    }

    @Override
    public int deleteStore(Map map) {
        return super.update("store.deleteStore",map);
    }

    @Override
    public List<StoreCategory> storeCategoryList(Map map) {
        return super.selectList("store.storeCategoryList",map);
    }

    @Override
    public int updateStoreCategory(StoreCategory storeCategory) {
        return super.update("store.updateStoreCategory",storeCategory);
    }

    @Override
    public int insertStoreCategory(StoreCategory storeCategory) {
        return super.insert("store.insertStoreCategory",storeCategory);
    }

    @Override
    public int deleteStoreCategory(Long storeCategoryId) {
        return super.delete("store.deleteStoreCategory",storeCategoryId);
    }

    @Override
    public List<Store> selectStoreListByCompanyId(Long companyId) {
        return super.selectList("store.selectStoreListByCompanyId",companyId);
    }

    @Override
    public List<Store> selectStoreListBySubCompany(Map map) {
        return super.selectList("store.selectStoreListBySubCompany", map);
    }

    @Override
    public int addStoreMemoNum(StoreMemoNum storeMemoNum) {
        return super.insert("store.addStoreMemoNum",storeMemoNum);
    }

    @Override
    public List<StoreMemoNum> listStoreMemo(StoreMemoNum storeMemoNum) {
        return super.selectList("store.listStoreMemo",storeMemoNum);
    }

    @Override
    public int checkStoreCategoryName(StoreCategory storeCategory) {
        return super.select("store.checkStoreCategoryName",storeCategory);
    }

    @Override
    public int checkStoreNumber(String code) {
        return super.select("store.checkStoreNumber",code);
    }
}
