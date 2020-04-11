package com.lnmj.product.repository;

import com.lnmj.product.entity.OutStorageProduct;
import com.lnmj.product.entity.Outstorage;
import com.lnmj.product.entity.OutstoragePreAudit;
import com.lnmj.product.entity.OutstoragePreAuditProduct;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/23 16:13
 * @Description: 出库
 */
public interface IOutstorageDao {
    List<Outstorage> selectOutstorageList();

    List<Outstorage> selectOutstorageListByCust(String custK3Number);

    List<Outstorage> selectOutstorageListByStorageNumber(Map map);

    Outstorage selectOutstorageByID(Long outStorageId);

    int deleteOutstorageByID(Outstorage outstorage);

    int insertOutstorage(OutstoragePreAudit outstorage);

    int updateOutstorageByID(Outstorage outstorage);

    List<OutstoragePreAudit> selectOutstorageByTypeAndDate(Map<String, Object> map);

    //出库商品
    List<OutStorageProduct> selectOutStorageProductList();

    List<OutStorageProduct> selectOutStorageProductByOutStorageID(Long outStorageId);

    OutStorageProduct selectOutStorageProductByID(Long outStorageProductID);

    int deleteOutStorageProductByID(OutStorageProduct outStorageProduct);

    int deleteOutStorageProductByOutStorageID(OutStorageProduct outStorageProduct);

    int insertOutStorageProduct(OutStorageProduct outStorageProduct);

    int insertPreOutstorageProduct(Map map);

    List<OutstoragePreAuditProduct>  selectPreOutstorageProductByPreAuditId(Long outstoragePreAuditId);

    int updateOutStorageProductByID(OutStorageProduct outStorageProduct);

    List<OutStorageProduct> selectOutStorageProductByBatchNumber(String batchNumber);

    List<OutStorageProduct> selectOutStorageProductListByStockIDAndProductID(Map map);

    //预审核出库
    int insertOutstoragePreAudit(OutstoragePreAudit outstorage);
    OutstoragePreAudit selectOutstoragePreAuditById(OutstoragePreAudit outstorage);
    int updateOutstoragePreAuditById(OutstoragePreAudit outstorage);
}
