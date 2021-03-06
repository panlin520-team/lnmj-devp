package com.lnmj.store.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.entity.Company;
import com.lnmj.store.entity.Subsidiary;
import com.lnmj.store.entity.Supplier;
import com.lnmj.store.entity.SupplierCategory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: yilihua
 * @Date: 2019/9/19 18:23
 * @Description:
 */
@Service("iSupplierService")
public interface ISupplierService {
    ResponseResult selectSupplierList(int pageNum, int pageSize,String keyWordName,String keyWordPhone,String supplierCategoryId,String supplierType,String companyType,String companyId);
    ResponseResult selectSupplierByCondition(Supplier supplier);
    ResponseResult selectSupplierListNoPage(String keyWordName,String keyWordPhone,String supplierCategoryId,String supplierType,String companyType,String companyId);
    ResponseResult updateSupplier(Supplier supplier);
    ResponseResult deleteSupplier(Supplier supplier);
    ResponseResult addSupplier(Supplier supplier,String companyID,String companyType,String orgK3Number);

    ResponseResult selectSupplierCategoryList(int pageNum, int pageSize,String keyWordName,String companyType,String companyId);
    ResponseResult listSupplierCategoryNoPage(String companyType,String companyId);
    ResponseResult updateSupplierCategory(SupplierCategory supplierCategory);
    ResponseResult deleteSupplierCategory(SupplierCategory supplierCategory);
    ResponseResult addSupplierCategory(SupplierCategory supplierCategory);
    ResponseResult selectSupplierByCode(String supplierCode);

    ResponseResult batchAllocationSubCompany(String companyId,String companyType,String supplierArrayStr,String subcompanyIdArrayStr);

    ResponseResult batchAllocationStore(String companyId,String companyType,String supplierArrayStr,String storeIdArrayStr);

    ResponseResult batchCancelAllocationSubCompany(String companyId,String companyType,String supplierArrayStr,String subcompanyIdArrayStr);

    ResponseResult batchCancelAllocationStore(String companyId,String companyType,String supplierArrayStr,String storeIdArrayStr);

    ResponseResult selectSuppTypeList();
}
