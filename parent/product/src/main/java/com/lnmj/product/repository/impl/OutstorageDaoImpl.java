package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.OutStorageProduct;
import com.lnmj.product.entity.Outstorage;
import com.lnmj.product.entity.OutstoragePreAudit;
import com.lnmj.product.entity.OutstoragePreAuditProduct;
import com.lnmj.product.repository.IOutstorageDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/23 16:17
 * @Description: 出库
 */
@Repository
public class OutstorageDaoImpl extends BaseDao implements IOutstorageDao {

    @Override
    public List<Outstorage> selectOutstorageList() {
        return super.selectList("Outstorage.selectOutstorageList");
    }

    @Override
    public List<Outstorage> selectOutstorageListByCust(String custK3Number) {
        return super.selectList("Outstorage.selectOutstorageListByCust",custK3Number);
    }

    @Override
    public List<Outstorage> selectOutstorageListByStorageNumber(Map map) {
        return super.selectList("Outstorage.selectOutstorageListByStorageNumber",map);
    }

    @Override
    public Outstorage selectOutstorageByID(Long outStorageId) {
        return super.select("Outstorage.selectOutstorageByID",outStorageId);
    }

    @Override
    public int deleteOutstorageByID(Outstorage outstorage) {
        return super.update("Outstorage.deleteOutstorageByID",outstorage);
    }

    @Override
    public int insertOutstorage(OutstoragePreAudit outstorage) {
        return super.insert("Outstorage.insertOutstorage",outstorage);
    }

    @Override
    public int updateOutstorageByID(Outstorage outstorage) {
        return super.update("Outstorage.updateOutstorageByID",outstorage);
    }

    @Override
    public List<OutStorageProduct> selectOutStorageProductList() {
        return super.selectList("OutStorageProduct.selectOutStorageProductList");
    }

    @Override
    public List<OutStorageProduct> selectOutStorageProductByOutStorageID(Long outStorageId) {
        return super.selectList("OutStorageProduct.selectOutStorageProductByOutStorageID",outStorageId);
    }

    @Override
    public OutStorageProduct selectOutStorageProductByID(Long outStorageProductID) {
        return super.select("OutStorageProduct.selectOutStorageProductByID",outStorageProductID);
    }

    @Override
    public int deleteOutStorageProductByID(OutStorageProduct outStorageProduct) {
        return super.update("OutStorageProduct.deleteOutStorageProductByID",outStorageProduct);
    }

    @Override
    public int deleteOutStorageProductByOutStorageID(OutStorageProduct outStorageProduct) {
        return super.update("OutStorageProduct.deleteOutStorageProductByOutStorageID",outStorageProduct);
    }

    @Override
    public int insertOutStorageProduct(OutStorageProduct outStorageProduct) {
        return super.insert("OutStorageProduct.insertOutStorageProduct",outStorageProduct);
    }

    @Override
    public int insertPreOutstorageProduct(Map map) {
        return super.insert("OutStorageProduct.insertPreOutstorageProduct",map);
    }

    @Override
    public List<OutstoragePreAuditProduct> selectPreOutstorageProductByPreAuditId(Long outstoragePreAuditId) {
        return super.selectList("OutStorageProduct.selectPreOutstorageProductByPreAuditId",outstoragePreAuditId);
    }

    @Override
    public int updateOutStorageProductByID(OutStorageProduct outStorageProduct) {
        return super.update("OutStorageProduct.updateOutStorageProductByID",outStorageProduct);
    }

    @Override
    public List<OutStorageProduct> selectOutStorageProductByBatchNumber(String batchNumber) {
        return super.selectList("OutStorageProduct.selectOutStorageProductByBatchNumber",batchNumber);
    }

    @Override
    public List<OutStorageProduct> selectOutStorageProductListByStockIDAndProductID(Map map) {
        return super.selectList("OutStorageProduct.selectOutStorageProductListByStockIDAndProductID",map);
    }

    @Override
    public int insertOutstoragePreAudit(OutstoragePreAudit outstorage) {
        return super.insert("Outstorage.insertOutstoragePreAudit",outstorage);
    }

    @Override
    public OutstoragePreAudit selectOutstoragePreAuditById(OutstoragePreAudit outstorage) {
        return super.select("Outstorage.selectOutstoragePreAuditById",outstorage);
    }

    @Override
    public int updateOutstoragePreAuditById(OutstoragePreAudit outstorage) {
        return super.update("Outstorage.updateOutstoragePreAuditById",outstorage);
    }

    @Override
    public List<OutstoragePreAudit> selectOutstorageByTypeAndDate(Map<String, Object> map) {
        return super.selectList("Outstorage.selectOutstorageByTypeAndDate",map);
    }

}
