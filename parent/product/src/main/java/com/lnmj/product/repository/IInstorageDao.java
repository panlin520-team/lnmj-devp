package com.lnmj.product.repository;

import com.lnmj.product.entity.InStorageProduct;
import com.lnmj.product.entity.Instorage;
import com.lnmj.product.entity.InstoragePreAudit;
import com.lnmj.product.entity.InstoragePreAuditProduct;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/23 16:11
 * @Description: 入库
 */
public interface IInstorageDao {
    List<Instorage> selectInstorageListByStorageNumber(Map map);

    List<Instorage> selectInstorageList();

    List<Instorage> checkInstorageBySupplierCode(String supplierCode);

    List<Instorage> checkInstorageByDepartment(String departmentK3Number);

    Instorage selectInstorageByID(Long inStorageId);


    int deleteInstorageByID(Instorage instorage);

    int insertInstoragePreAudit(InstoragePreAudit instorage);

    int insertInstorage(InstoragePreAudit instorage);

    int updateInstorageByID(Instorage instorage);

    List<InstoragePreAudit> selectInstorageByTypeAndDate(Map<String, Object> map);

    //入库商品
    List<InStorageProduct> selectInStorageProductList();

    List<InstoragePreAuditProduct> selectPreInstorageProductByPreAuditId(Long instoragePreAuditProduct);

    InStorageProduct selectInStorageProductByID(Long inStorageProductID);

    List<InStorageProduct> selectInStorageProductByInStorageID(Long inStorageId);

    int deleteInStorageProductByID(InStorageProduct inStorageProduct);

    int deleteInStorageProductByInStorageID(InStorageProduct inStorageProduct);

    int insertInStorageProduct(InStorageProduct inStorageProduct);

    int insertPreInstorageProduct(Map map);

    int cancelInstoragePreAudit(InstoragePreAudit instorage);

    int updateInStorageProductByID(InStorageProduct inStorageProduct);

    List<InStorageProduct> selectInStorageProductListByStockIDAndProductID(Map map);

    InstoragePreAudit selectInstoragePreAuditById(InstoragePreAudit instoragePreAudit);

    int updateInstoragePreAuditById(InstoragePreAudit instoragePreAudit);


}
