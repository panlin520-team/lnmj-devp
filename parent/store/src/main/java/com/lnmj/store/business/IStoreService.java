package com.lnmj.store.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.entity.Store;
import com.lnmj.store.entity.StoreCategory;
import com.lnmj.store.entity.StoreMemoNum;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("storeService")
public interface IStoreService {
    ResponseResult selectStoretList(int pageNum, int pageSize, String keyWordPhone, String keyWordName,Long storeCategoryId,Long companyType,Long companyId,Long industryID);
    ResponseResult selectStoreListNoPage(String companyId,String subCompanyId,String storeId,String productCode,String experienceCardNum,Long companyType);
    ResponseResult selectStoreById(Long storeId);
    ResponseResult selectStoreByCodeOrName(Long storeId,String code,String name);
    ResponseResult selectStoresByIds(Map map);
    ResponseResult insertStore(Store store);
    ResponseResult insertHeSuanFanWei(Store store);
    ResponseResult insertZhangBu(String companyId, String companyType);
    ResponseResult updateStoreByCodeOrId(Store store);
    ResponseResult updateStoreLatById(Store store);
    ResponseResult deleteStore(Long storeId,String modifyOperator);
    ResponseResult storeEnableOrDisEnable(Long storeId);
    ResponseResult storeCategoryList(int pageNum,int pageSize,String keyWordStoreCategoryName);
    ResponseResult storeCategoryListNoPage();
    ResponseResult updateStoreCategory(StoreCategory storeCategory);
    ResponseResult insertStoreCategory(StoreCategory storeCategory);
    ResponseResult deleteStoreCategory(Long storeCategoryId);
    ResponseResult selectStoreListByCompanyId(Long companyId);
    ResponseResult selectSubCompanyAndStoreNoPage(Long companyId,Integer companyType);
    ResponseResult selectStoreListBySubCompany(int pageNum, int pageSize, Long subsidiaryId, String keyWord);
    ResponseResult selectStoreListBySubCompanyNoPage(Long subsidiaryId);
    ResponseResult addStoreMemoNum(StoreMemoNum storeMemoNum);
    ResponseResult listStoreMemo(int pageNum,int pageSize,StoreMemoNum storeMemoNum);
    ResponseResult checkMenoContinuous(StoreMemoNum storeMemoNum);

    ResponseResult selectCompanyAndStoreNoPage(Long companyId, Integer companyType);
    ResponseResult selectStoreAll();
}
