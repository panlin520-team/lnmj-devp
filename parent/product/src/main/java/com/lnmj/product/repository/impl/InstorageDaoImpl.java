package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.InStorageProduct;
import com.lnmj.product.entity.Instorage;
import com.lnmj.product.entity.InstoragePreAudit;
import com.lnmj.product.entity.InstoragePreAuditProduct;
import com.lnmj.product.repository.IInstorageDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/23 16:17
 * @Description: 入库
 */
@Repository
public class InstorageDaoImpl extends BaseDao implements IInstorageDao {

    @Override
    public List<Instorage> selectInstorageListByStorageNumber(Map map) {
        return super.selectList("Instorage.selectInstorageListByStorageNumber",map);
    }

    @Override
    public List<Instorage> selectInstorageList() {
        return super.selectList("Instorage.selectInstorageList");
    }

    @Override
    public List<Instorage> checkInstorageBySupplierCode(String supplierCode) {
        return super.selectList("Instorage.checkInstorageBySupplierCode",supplierCode);
    }

    @Override
    public List<Instorage> checkInstorageByDepartment(String departmentK3Number) {
        return super.selectList("Instorage.checkInstorageByDepartment",departmentK3Number);
    }

    @Override
    public Instorage selectInstorageByID(Long inStorageId) {
        return super.select("Instorage.selectInstorageByID",inStorageId);
    }

    @Override
    public int deleteInstorageByID(Instorage instorage) {
        return super.update("Instorage.deleteInstorageByID",instorage);
    }

    @Override
    public int insertInstoragePreAudit(InstoragePreAudit instorage) {
        return super.insert("Instorage.insertInstoragePreAudit",instorage);
    }

    @Override
    public int insertInstorage(InstoragePreAudit instorage) {
        return super.insert("Instorage.insertInstorage",instorage);
    }

    @Override
    public int updateInstorageByID(Instorage instorage) {
        return super.update("Instorage.updateInstorageByID",instorage);
    }
    @Override
    public List<InStorageProduct> selectInStorageProductList() {
        return super.selectList("InStorageProduct.selectInStorageProductList");
    }

    @Override
    public List<InstoragePreAuditProduct> selectPreInstorageProductByPreAuditId(Long instoragePreAuditProduct) {
        return super.selectList("InStorageProduct.selectPreInstorageProductByPreAuditId",instoragePreAuditProduct);
    }

    @Override
    public InStorageProduct selectInStorageProductByID(Long inStorageProductID) {
        return super.select("InStorageProduct.selectInStorageProductByID",inStorageProductID);
    }

    @Override
    public List<InStorageProduct> selectInStorageProductByInStorageID(Long inStorageId) {
        return super.selectList("InStorageProduct.selectInStorageProductByInStorageID",inStorageId);
    }

    @Override
    public int deleteInStorageProductByID(InStorageProduct inStorageProduct) {
        return super.update("InStorageProduct.deleteInStorageProductByID",inStorageProduct);

    }

    @Override
    public int deleteInStorageProductByInStorageID(InStorageProduct inStorageProduct) {
        return super.update("InStorageProduct.deleteInStorageProductByInStorageID",inStorageProduct);

    }

    @Override
    public int insertInStorageProduct(InStorageProduct inStorageProduct) {
        return super.insert("InStorageProduct.insertInStorageProduct",inStorageProduct);

    }

    @Override
    public int insertPreInstorageProduct(Map map) {
        return super.insert("InStorageProduct.insertPreInstorageProduct",map);
    }

    @Override
    public int cancelInstoragePreAudit(InstoragePreAudit inStorageProduct) {
        return super.update("Instorage.updateInstoragePreAuditById",inStorageProduct);
    }

    @Override
    public int updateInStorageProductByID(InStorageProduct inStorageProduct) {
        return super.update("InStorageProduct.updateInStorageProductByID",inStorageProduct);
    }

    @Override
    public List<InStorageProduct> selectInStorageProductListByStockIDAndProductID(Map map) {
        return super.selectList("InStorageProduct.selectInStorageProductListByStockIDAndProductID",map);
    }

    @Override
    public InstoragePreAudit selectInstoragePreAuditById(InstoragePreAudit instoragePreAudit) {
        return super.select("Instorage.selectInstoragePreAuditById",instoragePreAudit);
    }

    @Override
    public int updateInstoragePreAuditById(InstoragePreAudit instoragePreAudit) {
        return super.update("Instorage.updateInstoragePreAuditById",instoragePreAudit);
    }



    @Override
    public List<InstoragePreAudit> selectInstorageByTypeAndDate(Map<String, Object> map) {
        return super.selectList("Instorage.selectInstorageByTypeAndDate",map);
    }
}
