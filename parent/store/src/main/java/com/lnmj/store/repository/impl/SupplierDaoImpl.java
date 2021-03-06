package com.lnmj.store.repository.impl;


import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.store.entity.*;
import com.lnmj.store.repository.ICompanyDao;
import com.lnmj.store.repository.ISupplierDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/19 18:23
 * @Description:
 */
@Repository
public class SupplierDaoImpl extends BaseDao implements ISupplierDao {


    @Override
    public List<Supplier> selectSupplierList(Map map) {
        return super.selectList("supplier.selectSupplierList", map);
    }

    @Override
    public List<Supplier> selectSupplierListSubCompany(Map map) {
        return super.selectList("supplier.selectSupplierListSubCompany", map);
    }

    @Override
    public List<Supplier> selectSupplierListStore(Map map) {
        return super.selectList("supplier.selectSupplierListStore", map);
    }

    @Override
    public Supplier selectSupplierById(Map map) {
        return super.select("supplier.selectSupplierById", map);
    }

    @Override
    public int updateSupplier(Supplier supplier) {
        return super.update("supplier.updateSupplier", supplier);
    }

    @Override
    public int deleteSupplier(Supplier supplier) {
        return super.delete("supplier.deleteSupplier", supplier);
    }

    @Override
    public int addSupplier(Supplier supplier) {
        return super.insert("supplier.addSupplier", supplier);
    }


    @Override
    public List<SupplierCategory> selectSupplierCategoryList(Map map) {
        return super.selectList("supplier.selectSupplierCategoryList", map);
    }

    @Override
    public int updateSupplierCategory(SupplierCategory supplierCategory) {
        return super.update("supplier.updateSupplierCategory", supplierCategory);
    }

    @Override
    public int deleteSupplierCategory(SupplierCategory supplierCategory) {
        return super.update("supplier.deleteSupplierCategory", supplierCategory);
    }

    @Override
    public int addSupplierCategory(SupplierCategory supplierCategory) {
        return super.insert("supplier.addSupplierCategory", supplierCategory);
    }

    @Override
    public Supplier selectSupplierByCode(String supplierCode) {
        return super.select("supplier.selectSupplierByCode", supplierCode);
    }

    @Override
    public List<SupplierSubCompany> checkSupplierSubCompany(Map map) {
        return super.selectList("supplier.checkSupplierSubCompany", map);
    }

    @Override
    public List<SupplierStore> checkSupplierStore(Map map) {
        return super.selectList("supplier.checkSupplierStore", map);
    }

    @Override
    public int addSupplierSubCompany(Map map) {
        return super.insert("supplier.addSupplierSubCompany", map);
    }

    @Override
    public int addSupplierStore(Map map) {
        return super.insert("supplier.addSupplierStore", map);
    }

    @Override
    public int addSupplierSubCompanyBatch(Map map) {
        return super.insert("supplier.addSupplierSubCompanyBatch", map);
    }

    @Override
    public int addSupplierStoreBatch(Map map) {
        return super.insert("supplier.addSupplierStoreBatch", map);
    }

    @Override
    public int deleteSupplierSubcompany(Map map) {
        return super.delete("supplier.deleteSupplierSubcompany", map);
    }

    @Override
    public int deleteSupplierStore(Map map) {
        return super.delete("supplier.deleteSupplierStore", map);
    }

    @Override
    public int checkSupplierName(Supplier supplier) {
        return super.select("supplier.checkSupplierName", supplier);
    }

    @Override
    public int checkSupplierCategoryName(SupplierCategory supplierCategory) {
        return super.select("supplier.checkSupplierCategoryName", supplierCategory);
    }

    @Override
    public Supplier selectSupplierByCondition(Supplier supplier) {
        return super.select("supplier.selectSupplierByCondition", supplier);
    }

    @Override
    public List<SupplierSubCompany> selectSupplierSubCompany(Map map) {
        return super.selectList("supplier.selectSupplierSubCompany", map);
    }

    @Override
    public List<SupplierStore> selectSupplierStore(Map map) {
        return super.selectList("supplier.selectSupplierStore", map);
    }
}
