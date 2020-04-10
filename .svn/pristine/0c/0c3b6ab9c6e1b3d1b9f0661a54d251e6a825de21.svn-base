package com.lnmj.product.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.entity.*;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/9/23 17:39
 * @Description: 仓库管理
 */
@Service("iStockService")
public interface IStockService {

    ResponseResult selectStockStatusList();

    ResponseResult selectStockProductList(int pageNum, int pageSize, String stock, String productCode,String productName);

    ResponseResult selectStockProductListNoPage(String stock, String productCode);

    ResponseResult selectInstorageList(int pageNum, int pageSize,String inStorageType, String inStorageDate,String stockGroup, String inventoryGroup,String stockId,String provider);

    ResponseResult selectOutstorageList(int pageNum, int pageSize,String outStorageType, String outStorageDate,String stockGroup,String inventoryGroup,String client);

    ResponseResult instoragePreAudit(InstoragePreAudit instorage);

    ResponseResult instoragePreAuditReturn(Long inStorageId,Integer number,Long inStorageProductID);

    ResponseResult instoragePreAuditInside(InstoragePreAudit instorage);

    ResponseResult cancelInstoragePreAudit(InstoragePreAudit instorage);

    ResponseResult instorage(InstoragePreAudit instorage);

    ResponseResult instorageReturn(InstoragePreAudit instorage);

    ResponseResult outstoragePreAudit(OutstoragePreAudit outstorage);

    ResponseResult outstoragePreAuditInside(OutstoragePreAudit outstorage);

    ResponseResult outstorage(OutstoragePreAudit outstorage);

    ResponseResult outstorageReturn(OutstoragePreAudit outstorage);

    ResponseResult selectEnumByName(String name);

    ResponseResult selectStockList(Long stockId, String stockCode, Long companyType, Long companyId,Boolean isSon);

    ResponseResult auditInstorage(Long inStorageId, String auditor, String inStorageStatus,String remark,String invalid);

    ResponseResult auditOutstorage(Long outStorageId, String auditor, Integer outstorageStatus,String remark,String inventoryWay);

    ResponseResult updateStockById(Stock stock);

    ResponseResult addStock(Stock stock, String acctId, String yewuDataCentreUserName,String yewuDataCentrePassword);

    ResponseResult selectStockProductListByProductName(int pageNum, int pageSize, String productName);

    ResponseResult confirmInstorage(Long inStorageId, String confirm, Integer confirmStatus, String remark);

    ResponseResult checkProductStockNumbers(String stockCode, String productCode, Integer numbers);

    ResponseResult addStockStatus(StockStatus stockStatus);

    ResponseResult deleteStockStatus(String id, String modifyOperator);

    ResponseResult queryStockStatus(String id, String code);

    ResponseResult editStockStatusById(StockStatus stockStatus);

    ResponseResult delStockById(Long companyId,Long companyType);

    ResponseResult selectProductNumberList(int pageNum, int pageSize, String stock);

    ResponseResult selectProductNumber(String stock, String productCode);

    ResponseResult checkOutstorageByCust(String custK3Number);

    ResponseResult checkInstorageBySupplierCode(String supplierCode);

    ResponseResult checkInstorageByDepartment(String departmentK3Number);

    ResponseResult selectOutstorageProductById(Long outStorageId);

    ResponseResult selectOutstorageById(Long outStorageId);
}
