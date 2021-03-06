package com.lnmj.k3cloud.business;


import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.entity.stock.inventory.InventorySaveParam;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019-11-08 15:09
 * @Description: 仓库service
 */
@Service("IStockService")
public interface IStockService {

    ResponseResult savePurchaseInstorage(String acctId,
                                         String dataCentreUserName,
                                         String dataCentrePassword,
                                         String orgK3Number,
                                         String fPurchaseOrgId,
                                         String fSupplierId,
                                         String fDate,
                                         String fSupplyAddress,
                                         String fCONTACTNUMBER,
                                         String jsonArrayProduct);

    ResponseResult savePurchaseOrder(String acctId,
                                     String dataCentreUserName,
                                     String dataCentrePassword,
                                     String fPurchaseOrgId,
                                     String fSupplierId,
                                     String fDate,
                                     String jsonArrayProduct);

    ResponseResult deletePurchaseInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unAuditPurchaseInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult auditPurchaseInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult submitPurchaseInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult viewPurchaseInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id);

    ResponseResult cancelPurchaseInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unCancelPurchaseInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult batchSavePurchaseInstorage();

    ResponseResult saveOtherInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String fBillTypeID,
                                      String fStockOrgId,
                                      String fStockDirect,
                                      String fDate,
                                      String fDEPTID,
                                      String fSUPPLIERID,
                                      String fOwnerTypeIdHead,
                                      String fOwnerIdHead,
                                      String fBaseCurrId,
                                      String jsonArrayProduct,
                                      String fUnitID,
                                      String fSTOCKID,
                                      String fSTOCKSTATUSID,
                                      String fQty,
                                      String fOWNERTYPEID,
                                      String fOWNERID,
                                      String fKEEPERTYPEID,
                                      String fKEEPERID);

    ResponseResult viewOtherInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id);

    ResponseResult submitOtherInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult auditOtherInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unAuditOtherInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult batchSaveOtherInstorage();

    ResponseResult deleteOtherInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unCancelOtherInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult cancelOtherInstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult batchSaveStockStatus();

    ResponseResult viewStockStatus(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id);

    ResponseResult submitStockStatus(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult auditStockStatus(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unAuditStockStatus(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult deleteStockStatus(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult saveStockStatus(String acctId, String dataCentreUserName, String dataCentrePassword, String fName, String fNumberCreateOrg, String fNumberUseOrg, String fType, String fNumber, String fStockStatusId);

    ResponseResult forbidStockStatus(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult enableStockStatus(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult enableStock(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult forbidStock(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult deleteStock(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unAuditStock(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult auditStock(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult submitStock(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult viewStock(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id);

    ResponseResult batchSaveStock();

    ResponseResult saveStock(String acctId, String dataCentreUserName, String dataCentrePassword, String fName, String fNumberCreateOrg, String fNumerUseOrg,
                             String fStockProperty, String fStockStatusType, String fNumber, String fStockId);

    ResponseResult saveInventory(String acctId, String dataCentreUserName, String dataCentrePassword, InventorySaveParam inventorySaveParam);

    ResponseResult batchSaveInventory();

    ResponseResult queryInventory(String acctId, String dataCentreUserName, String dataCentrePassword, String formId, String fieldKeys);

    ResponseResult unCancelOtherOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult cancelOtherOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult deleteOtherOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unAuditOtherOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult auditOtherOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult submitOtherOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult viewOtherOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id);

    ResponseResult batchSaveOtherOutstorage();

    ResponseResult saveOtherOutstorage(String acctId,
                                       String dataCentreUserName,
                                       String dataCentrePassword,
                                       String fStockOrgId,
                                       String fPickOrgId,
                                       String fDate,
                                       String fCustId,
                                       String fOwnerIdHead,
                                       String jsonArrayProduct,
                                       String fStockDirect);

    ResponseResult saveMarketOutstorage(String acctId,
                                        String dataCentreUserName,
                                        String dataCentrePassword,
                                        String fNumberSaleOrg,
                                        String fDate,
                                        String fNumberStockOrg,
                                        String fNumberCustomer,
                                        String fNumberSettleOrg,
                                        String jsonArrayProduct);

    ResponseResult marketOutstorageOrderSave(String acctId,
                                             String dataCentreUserName,
                                             String dataCentrePassword,
                                             String fNumberSaleOrg,
                                             String fDate,
                                             String fNumberCustomer,
                                             String jsonArrayProduct,
                                             String fSaleDeptId,
                                             String fSalerId);

    ResponseResult batchSaveMarketOutstorage();

    ResponseResult viewMarketOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id);

    ResponseResult submitMarketOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult auditMarketOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unAuditMarketOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult deleteMarketOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult cancelMarketOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unCancelMarketOutstorage(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult saveMarketReturn(String acctId,
                                    String dataCentreUserName,
                                    String dataCentrePassword,
                                    String fBillTypeID,
                                    String fDate,
                                    String fSaleOrgId,
                                    String fRetcustId,
                                    String fReturnReason,
                                    String fStockOrgId,
                                    String fReceiveCustId,
                                    String fSettleCustId,
                                    String fPayCustId,
                                    String fSettleCurrId,
                                    String fSettleOrgId,
                                    String jsonArrayProduct);

    ResponseResult batchSaveMarketReturn();

    ResponseResult viewMarketReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id);

    ResponseResult submitMarketReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult auditMarketReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unAuditMarketReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult deleteMarketReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult cancelMarketReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unCancelMarketReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult manageUnAuditMarketReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);


    ResponseResult savePurchaseReturn(String acctId,
                                      String dataCentreUserName,
                                      String dataCentrePassword,
                                      String fStockOrgId,
                                      String fDate,
                                      String fSupplierID,
                                      String jsonArrayProduct);

    ResponseResult batchSavePurchaseReturn();

    ResponseResult viewPurchaseReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id);

    ResponseResult auditPurchaseReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult submitPurchaseReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unAuditPurchaseReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult deletePurchaseReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult cancelPurchaseReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unCancelPurchaseReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult manageUnAuditPurchaseReturn(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult startStock(String dataCentreName, String orgId, String startDate);

}
