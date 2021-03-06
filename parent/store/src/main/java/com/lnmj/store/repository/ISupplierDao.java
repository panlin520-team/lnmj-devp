package com.lnmj.store.repository;

import com.lnmj.store.entity.*;

import java.util.List;
import java.util.Map;

public interface ISupplierDao {
    List<Supplier> selectSupplierList(Map map);

    List<Supplier> selectSupplierListSubCompany(Map map);

    List<Supplier> selectSupplierListStore(Map map);

    Supplier selectSupplierById(Map map);
    int updateSupplier(Supplier supplier);
    int deleteSupplier(Supplier supplier);
    int addSupplier(Supplier supplier);

    List<SupplierCategory> selectSupplierCategoryList(Map map);
    int updateSupplierCategory(SupplierCategory supplierCategory);
    int deleteSupplierCategory(SupplierCategory supplierCategory);
    int addSupplierCategory(SupplierCategory supplierCategory);
    Supplier selectSupplierByCode(String supplierCode);

    List<SupplierSubCompany> checkSupplierSubCompany(Map map);

    List<SupplierStore> checkSupplierStore(Map map);

    int addSupplierSubCompany(Map map);

    int addSupplierStore(Map map);

    int addSupplierSubCompanyBatch(Map map);

    int addSupplierStoreBatch(Map map);

    int deleteSupplierSubcompany(Map map);

    int deleteSupplierStore(Map map);

    int checkSupplierName(Supplier supplier);

    int checkSupplierCategoryName(SupplierCategory supplierCategory);

    Supplier selectSupplierByCondition(Supplier supplier);
    List<SupplierSubCompany> selectSupplierSubCompany(Map map);
    List<SupplierStore> selectSupplierStore(Map map);
}
