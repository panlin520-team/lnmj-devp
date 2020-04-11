package com.lnmj.product.serviceProxy;

import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "lnmj-k3cloud")
public interface K3CLOUDApi {
    /*---------------------------------物料操作--------------------------------------*/
    @RequestMapping(value = "/k3cloud/product/product/save", method = RequestMethod.POST)
    ResponseResult saveProduct(@RequestParam("acctId") String acctId,
                               @RequestParam("dataCentreUserName") String dataCentreUserName,
                               @RequestParam("dataCentrePassword") String dataCentrePassword,
                               @RequestParam("fname") String fname,
                               @RequestParam("id") Integer id,
                               @RequestParam("isautosubmitandaudit") Boolean isautosubmitandaudit
//                               @RequestParam("fCreateOrgId") String fCreateOrgId,
//                               @RequestParam("fUseOrgId") String fUseOrgId,
//                               @RequestParam("fbarcodeentityCmkListStr") String fbarcodeentityCmkListStr,
//                               @RequestParam("fErpClsID") String fErpClsID,
//                               @RequestParam("fBaseUnitId") String fBaseUnitId,
//                               @RequestParam("fCategoryID") String fCategoryID,
//                               @RequestParam("fSuite") String fSuite,
//                               @RequestParam("fStoreUnitID") String fStoreUnitID,
//                               @RequestParam("fCurrencyId") String fCurrencyId,
//                               @RequestParam("fUnitConvertDir") String fUnitConvertDir,
//                               @RequestParam("fSNGenerateTime") String fSNGenerateTime,
//                               @RequestParam("fSNManageType") String fSNManageType,
//                               @RequestParam("fSalePriceUnitId") String fSalePriceUnitId,
//                               @RequestParam("fSaleUnitId") String fSaleUnitId,
//                               @RequestParam("fPurchaseUnitId") String fPurchaseUnitId,
//                               @RequestParam("fPurchasePriceUnitId") String fPurchasePriceUnitId,
//                               @RequestParam("fQuotaType") String fQuotaType,
//                               @RequestParam("fPlanningStrategy") String fPlanningStrategy,
//                               @RequestParam("fOrderPolicy") String fOrderPolicy,
//                               @RequestParam("fFixLeadTimeType") String fFixLeadTimeType,
//                               @RequestParam("fVarLeadTimeType") String fVarLeadTimeType,
//                               @RequestParam("fCheckLeadTimeType") String fCheckLeadTimeType,
//                               @RequestParam("fOrderIntervalTimeType") String fOrderIntervalTimeType,
//                               @RequestParam("fReserveType") String fReserveType,
//                               @RequestParam("fPlanOffsetTimeType") String fPlanOffsetTimeType,
//                               @RequestParam("fIssueType") String fIssueType,
//                               @RequestParam("fOverControlMode") String fOverControlMode,
//                               @RequestParam("fMinIssueUnitId") String fMinIssueUnitId,
//                               @RequestParam("fStandHourUnitId") String fStandHourUnitId,
//                               @RequestParam("fBackFlushType") String fBackFlushType,
//                               @RequestParam("fEntityInvPtyListStr") String fEntityInvPtyListStr
    );

    @RequestMapping(value = "/k3cloud/product/product/view", method = RequestMethod.POST)
    ResponseResult viewProduct(@RequestParam("acctId") String acctId,
                               @RequestParam("dataCentreUserName") String dataCentreUserName,
                               @RequestParam("dataCentrePassword") String dataCentrePassword,
                               @RequestParam("number") String number, @RequestParam("id") String id);

    @RequestMapping(value = "/k3cloud/product/product/submit", method = RequestMethod.POST)
    ResponseResult submitProduct(@RequestParam("acctId") String acctId,
                                 @RequestParam("dataCentreUserName") String dataCentreUserName,
                                 @RequestParam("dataCentrePassword") String dataCentrePassword,
                                 @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/product/product/audit", method = RequestMethod.POST)
    ResponseResult auditProduct(@RequestParam("acctId") String acctId,
                                @RequestParam("dataCentreUserName") String dataCentreUserName,
                                @RequestParam("dataCentrePassword") String dataCentrePassword,
                                @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/product/product/unAudit", method = RequestMethod.POST)
    ResponseResult unAuditProduct(@RequestParam("acctId") String acctId,
                                  @RequestParam("dataCentreUserName") String dataCentreUserName,
                                  @RequestParam("dataCentrePassword") String dataCentrePassword,
                                  @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/product/product/delete", method = RequestMethod.POST)
    ResponseResult deleteProduct(@RequestParam("acctId") String acctId,
                                 @RequestParam("dataCentreUserName") String dataCentreUserName,
                                 @RequestParam("dataCentrePassword") String dataCentrePassword,
                                 @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/product/product/allocate", method = RequestMethod.POST)
    ResponseResult allocate(@RequestParam("acctId") String acctId,
                            @RequestParam("dataCentreUserName") String dataCentreUserName,
                            @RequestParam("dataCentrePassword") String dataCentrePassword,
                            @RequestParam("PkIds") String PkIds,
                            @RequestParam("TOrgIds") String TOrgIds);


    /*---------------------------------单位操作--------------------------------------*/
    @RequestMapping(value = "/k3cloud/product/unit/save", method = RequestMethod.POST)
    ResponseResult saveUnit(@RequestParam("acctId") String acctId,
                            @RequestParam("dataCentreUserName") String dataCentreUserName,
                            @RequestParam("dataCentrePassword") String dataCentrePassword,
                            @RequestParam("fname") String fname,
                            @RequestParam("groupId") String groupId,
                            @RequestParam("froundtype") String froundtype,
                            @RequestParam("id") String id,
                            @RequestParam("isautosubmitandaudit") Boolean isautosubmitandaudit);

//    @RequestMapping(value = "/k3cloud/product/unit/submit", method = RequestMethod.POST)
//    ResponseResult submitUnit(String numbers,String ids);
//
    @RequestMapping(value = "/k3cloud/product/unit/unAudit", method = RequestMethod.POST)
    ResponseResult unAuditUnit(@RequestParam("acctId") String acctId,
                           @RequestParam("dataCentreUserName") String dataCentreUserName,
                           @RequestParam("dataCentrePassword") String dataCentrePassword,
                           @RequestParam("numbers") String numbers,
                           @RequestParam("ids") String ids);

    //=========================仓库状态==========================


    @RequestMapping(value = "/k3cloud/login", method = RequestMethod.POST)
    ResponseResult login(@RequestParam("acctId") String acctId,
                         @RequestParam("dataCentreUserName") String dataCentreUserName,
                         @RequestParam("dataCentrePassword") String dataCentrePassword,
                         @RequestParam("acctID") String acctID, @RequestParam("userName") String userName,
                         @RequestParam("password") String password, @RequestParam("lcid") String lcid);

    @RequestMapping(value = "/k3cloud/stockStatus/save", method = RequestMethod.POST)
    ResponseResult saveStockStatus(@RequestParam("acctId") String acctId,
                                   @RequestParam("dataCentreUserName") String dataCentreUserName,
                                   @RequestParam("dataCentrePassword") String dataCentrePassword,
                                   @RequestParam("fName") String fName, @RequestParam("fNumberCreateOrg") String fNumberCreateOrg,
                                   @RequestParam("fNumberUseOrg") String fNumberUseOrg, @RequestParam("fType") String fType,
                                   @RequestParam("fNumber") String fNumber, @RequestParam("fStockStatusId") String fStockStatusId);

    @RequestMapping(value = "/k3cloud/stockStatus/view", method = RequestMethod.POST)
    ResponseResult viewStockStatus(@RequestParam("acctId") String acctId,
                                   @RequestParam("dataCentreUserName") String dataCentreUserName,
                                   @RequestParam("dataCentrePassword") String dataCentrePassword,
                                   @RequestParam("number") String number, @RequestParam("id") String id);

    @RequestMapping(value = "/k3cloud/stockStatus/submit", method = RequestMethod.POST)
    ResponseResult submitStockStatus(@RequestParam("acctId") String acctId,
                                     @RequestParam("dataCentreUserName") String dataCentreUserName,
                                     @RequestParam("dataCentrePassword") String dataCentrePassword,
                                     @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stockStatus/audit", method = RequestMethod.POST)
    ResponseResult auditStockStatus(@RequestParam("acctId") String acctId,
                                    @RequestParam("dataCentreUserName") String dataCentreUserName,
                                    @RequestParam("dataCentrePassword") String dataCentrePassword,
                                    @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stockStatus/unAudit", method = RequestMethod.POST)
    ResponseResult unAuditStockStatus(@RequestParam("acctId") String acctId,
                                      @RequestParam("dataCentreUserName") String dataCentreUserName,
                                      @RequestParam("dataCentrePassword") String dataCentrePassword,
                                      @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stockStatus/delete", method = RequestMethod.POST)
    ResponseResult deleteStockStatus(@RequestParam("acctId") String acctId,
                                     @RequestParam("dataCentreUserName") String dataCentreUserName,
                                     @RequestParam("dataCentrePassword") String dataCentrePassword,
                                     @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stockStatus/forbid", method = RequestMethod.POST)
    ResponseResult forbidStockStatus(@RequestParam("acctId") String acctId,
                                     @RequestParam("dataCentreUserName") String dataCentreUserName,
                                     @RequestParam("dataCentrePassword") String dataCentrePassword,
                                     @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stockStatus/enable", method = RequestMethod.POST)
    ResponseResult enableStockStatus(@RequestParam("acctId") String acctId,
                                     @RequestParam("dataCentreUserName") String dataCentreUserName,
                                     @RequestParam("dataCentrePassword") String dataCentrePassword,
                                     @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    //=========================即时库存==========================
    @RequestMapping(value = "/k3cloud/inventory/save", method = RequestMethod.POST)
    ResponseResult saveInventory(@RequestParam("acctId") String acctId,
                                 @RequestParam("dataCentreUserName") String dataCentreUserName,
                                 @RequestParam("dataCentrePassword") String dataCentrePassword,
                                 @RequestParam("Model") String Model);

    @RequestMapping(value = "/k3cloud/inventory/query", method = RequestMethod.POST)
    ResponseResult queryInventory(@RequestParam("acctId") String acctId,
                                  @RequestParam("dataCentreUserName") String dataCentreUserName,
                                  @RequestParam("dataCentrePassword") String dataCentrePassword,
                                  @RequestParam("formId") String formId, @RequestParam("fieldKeys") String fieldKeys);

    //============================仓库============================
    @RequestMapping(value = "/k3cloud/stock/save", method = RequestMethod.POST)
    ResponseResult saveStock(@RequestParam("acctId") String acctId,
                             @RequestParam("dataCentreUserName") String dataCentreUserName,
                             @RequestParam("dataCentrePassword") String dataCentrePassword,
                             @RequestParam("fName") String fName,
                             @RequestParam("fNumberCreateOrg") String fNumberCreateOrg,
                             @RequestParam("fNumerUseOrg") String fNumerUseOrg,
                             @RequestParam("fStockProperty") String fStockProperty,
                             @RequestParam("fStockStatusType") String fStockStatusType,
                             @RequestParam("fNumber") String fNumber,
                             @RequestParam("fStockId") String fStockId);

    @RequestMapping(value = "/k3cloud/stock/view", method = RequestMethod.POST)
    ResponseResult viewStock(@RequestParam("acctId") String acctId,
                             @RequestParam("dataCentreUserName") String dataCentreUserName,
                             @RequestParam("dataCentrePassword") String dataCentrePassword,
                             @RequestParam("number") String number, @RequestParam("id") String id);

    @RequestMapping(value = "/k3cloud/stock/submit", method = RequestMethod.POST)
    ResponseResult submitStock(@RequestParam("acctId") String acctId,
                               @RequestParam("dataCentreUserName") String dataCentreUserName,
                               @RequestParam("dataCentrePassword") String dataCentrePassword,
                               @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/audit", method = RequestMethod.POST)
    ResponseResult auditStock(@RequestParam("acctId") String acctId,
                              @RequestParam("dataCentreUserName") String dataCentreUserName,
                              @RequestParam("dataCentrePassword") String dataCentrePassword,
                              @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/unAudit", method = RequestMethod.POST)
    ResponseResult unAuditStock(@RequestParam("acctId") String acctId,
                                @RequestParam("dataCentreUserName") String dataCentreUserName,
                                @RequestParam("dataCentrePassword") String dataCentrePassword,
                                @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/delete", method = RequestMethod.POST)
    ResponseResult deleteStock(@RequestParam("acctId") String acctId,
                               @RequestParam("dataCentreUserName") String dataCentreUserName,
                               @RequestParam("dataCentrePassword") String dataCentrePassword,
                               @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/forbid", method = RequestMethod.POST)
    ResponseResult forbidStock(@RequestParam("acctId") String acctId,
                               @RequestParam("dataCentreUserName") String dataCentreUserName,
                               @RequestParam("dataCentrePassword") String dataCentrePassword,
                               @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/enable", method = RequestMethod.POST)
    ResponseResult enableStock(@RequestParam("acctId") String acctId,
                               @RequestParam("dataCentreUserName") String dataCentreUserName,
                               @RequestParam("dataCentrePassword") String dataCentrePassword,
                               @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    //============================销售出库============================
    @RequestMapping(value = "/k3cloud/stock/marketOutstorage/save", method = RequestMethod.POST)
    ResponseResult saveMarketOutstorage(@RequestParam("acctId") String acctId,
                                        @RequestParam("dataCentreUserName") String dataCentreUserName,
                                        @RequestParam("dataCentrePassword") String dataCentrePassword,
                                        @RequestParam("fNumberSaleOrg") String fNumberSaleOrg,
                                        @RequestParam("fDate") String fDate,
                                        @RequestParam("fNumberStockOrg") String fNumberStockOrg,
                                        @RequestParam("fNumberCustomer") String fNumberCustomer,
                                        @RequestParam("fNumberSettleOrg") String fNumberSettleOrg,
                                        @RequestParam("jsonArrayProduct") String jsonArrayProduct);

    @RequestMapping(value = "/k3cloud/stock/marketOutstorage/view", method = RequestMethod.POST)
    ResponseResult viewMarketOutstorage(@RequestParam("acctId") String acctId,
                                        @RequestParam("dataCentreUserName") String dataCentreUserName,
                                        @RequestParam("dataCentrePassword") String dataCentrePassword,
                                        @RequestParam("number") String number, @RequestParam("id") String id);

    @RequestMapping(value = "/k3cloud/stock/marketOutstorage/submit", method = RequestMethod.POST)
    ResponseResult submitMarketOutstorage(@RequestParam("acctId") String acctId,
                                          @RequestParam("dataCentreUserName") String dataCentreUserName,
                                          @RequestParam("dataCentrePassword") String dataCentrePassword,
                                          @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/marketOutstorage/audit", method = RequestMethod.POST)
    ResponseResult auditMarketOutstorage(@RequestParam("acctId") String acctId,
                                         @RequestParam("dataCentreUserName") String dataCentreUserName,
                                         @RequestParam("dataCentrePassword") String dataCentrePassword,
                                         @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/marketOutstorage/unAudit", method = RequestMethod.POST)
    ResponseResult unAuditMarketOutstorage(@RequestParam("acctId") String acctId,
                                           @RequestParam("dataCentreUserName") String dataCentreUserName,
                                           @RequestParam("dataCentrePassword") String dataCentrePassword,
                                           @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/marketOutstorage/delete", method = RequestMethod.POST)
    ResponseResult deleteMarketOutstorage(@RequestParam("acctId") String acctId,
                                          @RequestParam("dataCentreUserName") String dataCentreUserName,
                                          @RequestParam("dataCentrePassword") String dataCentrePassword,
                                          @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/marketOutstorage/cancel", method = RequestMethod.POST)
    ResponseResult cancelMarketOutstorage(@RequestParam("acctId") String acctId,
                                          @RequestParam("dataCentreUserName") String dataCentreUserName,
                                          @RequestParam("dataCentrePassword") String dataCentrePassword,
                                          @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/marketOutstorage/unCancel", method = RequestMethod.POST)
    ResponseResult unCancelMarketOutstorage(@RequestParam("acctId") String acctId,
                                            @RequestParam("dataCentreUserName") String dataCentreUserName,
                                            @RequestParam("dataCentrePassword") String dataCentrePassword,
                                            @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    //============================销售退货============================
    @RequestMapping(value = "/k3cloud/stock/marketReturn/saveMarketReturn", method = RequestMethod.POST)
    ResponseResult saveMarketReturn(@RequestParam("acctId") String acctId,
                                    @RequestParam("dataCentreUserName") String dataCentreUserName,
                                    @RequestParam("dataCentrePassword") String dataCentrePassword,
                                    @RequestParam("fBillTypeID") String fBillTypeID,
                                    @RequestParam("fDate") String fDate,
                                    @RequestParam("fSaleOrgId") String fSaleOrgId,
                                    @RequestParam("fRetcustId") String fRetcustId,
                                    @RequestParam("fReturnReason") String fReturnReason,
                                    @RequestParam("fStockOrgId") String fStockOrgId,
                                    @RequestParam("fReceiveCustId") String fReceiveCustId,
                                    @RequestParam("fSettleCustId") String fSettleCustId,
                                    @RequestParam("fPayCustId") String fPayCustId,
                                    @RequestParam("fSettleCurrId") String fSettleCurrId,
                                    @RequestParam("fSettleOrgId") String fSettleOrgId,
                                    @RequestParam("jsonArrayProduct") String jsonArrayProduct);

    @RequestMapping(value = "/k3cloud/stock/marketReturn/view", method = RequestMethod.POST)
    ResponseResult viewMarketReturn(@RequestParam("acctId") String acctId,
                                    @RequestParam("dataCentreUserName") String dataCentreUserName,
                                    @RequestParam("dataCentrePassword") String dataCentrePassword,
                                    @RequestParam("number") String number, @RequestParam("id") String id);

    @RequestMapping(value = "/k3cloud/stock/marketReturn/submit", method = RequestMethod.POST)
    ResponseResult submitMarketReturn(@RequestParam("acctId") String acctId,
                                      @RequestParam("dataCentreUserName") String dataCentreUserName,
                                      @RequestParam("dataCentrePassword") String dataCentrePassword,
                                      @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/marketReturn/audit", method = RequestMethod.POST)
    ResponseResult auditMarketReturn(@RequestParam("acctId") String acctId,
                                     @RequestParam("dataCentreUserName") String dataCentreUserName,
                                     @RequestParam("dataCentrePassword") String dataCentrePassword,
                                     @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/marketReturn/unAudit", method = RequestMethod.POST)
    ResponseResult unAuditMarketReturn(@RequestParam("acctId") String acctId,
                                       @RequestParam("dataCentreUserName") String dataCentreUserName,
                                       @RequestParam("dataCentrePassword") String dataCentrePassword,
                                       @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/marketReturn/delete", method = RequestMethod.POST)
    ResponseResult deleteMarketReturn(@RequestParam("acctId") String acctId,
                                      @RequestParam("dataCentreUserName") String dataCentreUserName,
                                      @RequestParam("dataCentrePassword") String dataCentrePassword,
                                      @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/marketReturn/cancel", method = RequestMethod.POST)
    ResponseResult cancelMarketReturn(@RequestParam("acctId") String acctId,
                                      @RequestParam("dataCentreUserName") String dataCentreUserName,
                                      @RequestParam("dataCentrePassword") String dataCentrePassword,
                                      @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/marketReturn/unCancel", method = RequestMethod.POST)
    ResponseResult unCancelMarketReturn(@RequestParam("acctId") String acctId,
                                        @RequestParam("dataCentreUserName") String dataCentreUserName,
                                        @RequestParam("dataCentrePassword") String dataCentrePassword,
                                        @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/marketReturn/manageUnAudit", method = RequestMethod.POST)
    ResponseResult manageUnAuditMarketReturn(@RequestParam("acctId") String acctId,
                                             @RequestParam("dataCentreUserName") String dataCentreUserName,
                                             @RequestParam("dataCentrePassword") String dataCentrePassword,
                                             @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    //============================其他入库============================
    @RequestMapping(value = "/k3cloud/stock/otherInstorage/save", method = RequestMethod.POST)
    ResponseResult saveOtherInstorage(@RequestParam("acctId") String acctId,
                                      @RequestParam("dataCentreUserName") String dataCentreUserName,
                                      @RequestParam("dataCentrePassword") String dataCentrePassword,
                                      @RequestParam("fBillTypeID") String fBillTypeID,
                                      @RequestParam("fStockOrgId") String fStockOrgId,
                                      @RequestParam("fStockDirect") String fStockDirect,
                                      @RequestParam("fDate") String fDate,
                                      @RequestParam("fDEPTID") String fDEPTID,
                                      @RequestParam("fSUPPLIERID") String fSUPPLIERID,
                                      @RequestParam("fOwnerTypeIdHead") String fOwnerTypeIdHead,
                                      @RequestParam("fOwnerIdHead") String fOwnerIdHead,
                                      @RequestParam("fBaseCurrId") String fBaseCurrId,
                                      @RequestParam("jsonArrayProduct") String jsonArrayProduct,
                                      @RequestParam("fUnitID") String fUnitID,
                                      @RequestParam("fSTOCKID") String fSTOCKID,
                                      @RequestParam("fSTOCKSTATUSID") String fSTOCKSTATUSID,
                                      @RequestParam("fQty") String fQty,
                                      @RequestParam("fOWNERTYPEID") String fOWNERTYPEID,
                                      @RequestParam("fOWNERID") String fOWNERID,
                                      @RequestParam("fKEEPERTYPEID") String fKEEPERTYPEID,
                                      @RequestParam("fKEEPERID") String fKEEPERID);

    @RequestMapping(value = "/k3cloud/stock/otherInstorage/view", method = RequestMethod.POST)
    ResponseResult viewOtherInstorage(@RequestParam("acctId") String acctId,
                                      @RequestParam("dataCentreUserName") String dataCentreUserName,
                                      @RequestParam("dataCentrePassword") String dataCentrePassword,
                                      @RequestParam("number") String number, @RequestParam("id") String id);

    @RequestMapping(value = "/k3cloud/stock/otherInstorage/submit", method = RequestMethod.POST)
    ResponseResult submitOtherInstorage(@RequestParam("acctId") String acctId,
                                        @RequestParam("dataCentreUserName") String dataCentreUserName,
                                        @RequestParam("dataCentrePassword") String dataCentrePassword,
                                        @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/otherInstorage/audit", method = RequestMethod.POST)
    ResponseResult auditOtherInstorage(@RequestParam("acctId") String acctId,
                                       @RequestParam("dataCentreUserName") String dataCentreUserName,
                                       @RequestParam("dataCentrePassword") String dataCentrePassword,
                                       @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/otherInstorage/unAudit", method = RequestMethod.POST)
    ResponseResult unAuditOtherInstorage(@RequestParam("acctId") String acctId,
                                         @RequestParam("dataCentreUserName") String dataCentreUserName,
                                         @RequestParam("dataCentrePassword") String dataCentrePassword,
                                         @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/otherInstorage/delete", method = RequestMethod.POST)
    ResponseResult deleteOtherInstorage(@RequestParam("acctId") String acctId,
                                        @RequestParam("dataCentreUserName") String dataCentreUserName,
                                        @RequestParam("dataCentrePassword") String dataCentrePassword,
                                        @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/otherInstorage/cancel", method = RequestMethod.POST)
    ResponseResult cancelOtherInstorage(@RequestParam("acctId") String acctId,
                                        @RequestParam("dataCentreUserName") String dataCentreUserName,
                                        @RequestParam("dataCentrePassword") String dataCentrePassword,
                                        @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/otherInstorage/unCancel", method = RequestMethod.POST)
    ResponseResult unCancelOtherInstorage(@RequestParam("acctId") String acctId,
                                          @RequestParam("dataCentreUserName") String dataCentreUserName,
                                          @RequestParam("dataCentrePassword") String dataCentrePassword,
                                          @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    //============================其他出库============================
    @RequestMapping(value = "/k3cloud/stock/otherOutstorage/save", method = RequestMethod.POST)
    ResponseResult saveOtherOutstorage(@RequestParam("acctId") String acctId,
                                       @RequestParam("dataCentreUserName") String dataCentreUserName,
                                       @RequestParam("dataCentrePassword") String dataCentrePassword,
                                       @RequestParam("fStockOrgId") String fStockOrgId,
                                       @RequestParam("fPickOrgId") String fPickOrgId,
                                       @RequestParam("fDate") String fDate,
                                       @RequestParam("fCustId") String fCustId,
                                       @RequestParam("fOwnerIdHead") String fOwnerIdHead,
                                       @RequestParam("jsonArrayProduct") String jsonArrayProduct,
                                       @RequestParam("fStockDirect") String fStockDirect);

    @RequestMapping(value = "/k3cloud/stock/otherOutstorage/view", method = RequestMethod.POST)
    ResponseResult viewOtherOutstorage(@RequestParam("acctId") String acctId,
                                       @RequestParam("dataCentreUserName") String dataCentreUserName,
                                       @RequestParam("dataCentrePassword") String dataCentrePassword,
                                       @RequestParam("number") String number, @RequestParam("id") String id);

    @RequestMapping(value = "/k3cloud/stock/otherOutstorage/submit", method = RequestMethod.POST)
    ResponseResult submitOtherOutstorage(@RequestParam("acctId") String acctId,
                                         @RequestParam("dataCentreUserName") String dataCentreUserName,
                                         @RequestParam("dataCentrePassword") String dataCentrePassword,
                                         @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/otherOutstorage/audit", method = RequestMethod.POST)
    ResponseResult auditOtherOutstorage(@RequestParam("acctId") String acctId,
                                        @RequestParam("dataCentreUserName") String dataCentreUserName,
                                        @RequestParam("dataCentrePassword") String dataCentrePassword,
                                        @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/otherOutstorage/unAudit", method = RequestMethod.POST)
    ResponseResult unAuditOtherOutstorage(@RequestParam("acctId") String acctId,
                                          @RequestParam("dataCentreUserName") String dataCentreUserName,
                                          @RequestParam("dataCentrePassword") String dataCentrePassword,
                                          @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/otherOutstorage/delete", method = RequestMethod.POST)
    ResponseResult deleteOtherOutstorage(@RequestParam("acctId") String acctId,
                                         @RequestParam("dataCentreUserName") String dataCentreUserName,
                                         @RequestParam("dataCentrePassword") String dataCentrePassword,
                                         @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/otherOutstorage/cancel", method = RequestMethod.POST)
    ResponseResult cancelOtherOutstorage(@RequestParam("acctId") String acctId,
                                         @RequestParam("dataCentreUserName") String dataCentreUserName,
                                         @RequestParam("dataCentrePassword") String dataCentrePassword,
                                         @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/otherOutstorage/unCancel", method = RequestMethod.POST)
    ResponseResult unCancelOtherOutstorage(@RequestParam("acctId") String acctId,
                                           @RequestParam("dataCentreUserName") String dataCentreUserName,
                                           @RequestParam("dataCentrePassword") String dataCentrePassword,
                                           @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    //============================采购入库============================
    @RequestMapping(value = "/k3cloud/stock/purchaseInstorage/save", method = RequestMethod.POST)
    ResponseResult savePurchaseInstorage(@RequestParam("acctId") String acctId,
                                         @RequestParam("dataCentreUserName") String dataCentreUserName,
                                         @RequestParam("dataCentrePassword") String dataCentrePassword,
                                         @RequestParam("orgK3Number") String orgK3Number,
                                         @RequestParam("fPurchaseOrgId") String fPurchaseOrgId,
                                         @RequestParam("fSupplierId") String fSupplierId,
                                         @RequestParam("fDate") String fDate,
                                         @RequestParam("fSupplyAddress") String fSupplyAddress,
                                         @RequestParam("fCONTACTNUMBER") String fCONTACTNUMBER,
                                         @RequestParam("jsonArrayProduct") String jsonArrayProduct);

    @RequestMapping(value = "/k3cloud/stock/purchaseInstorage/view", method = RequestMethod.POST)
    ResponseResult viewPurchaseInstorage(@RequestParam("acctId") String acctId,
                                         @RequestParam("dataCentreUserName") String dataCentreUserName,
                                         @RequestParam("dataCentrePassword") String dataCentrePassword,
                                         @RequestParam("number") String number, @RequestParam("id") String id);

    @RequestMapping(value = "/k3cloud/stock/purchaseInstorage/submit", method = RequestMethod.POST)
    ResponseResult submitPurchaseInstorage(@RequestParam("acctId") String acctId,
                                           @RequestParam("dataCentreUserName") String dataCentreUserName,
                                           @RequestParam("dataCentrePassword") String dataCentrePassword,
                                           @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/purchaseInstorage/audit", method = RequestMethod.POST)
    ResponseResult auditPurchaseInstorage(@RequestParam("acctId") String acctId,
                                          @RequestParam("dataCentreUserName") String dataCentreUserName,
                                          @RequestParam("dataCentrePassword") String dataCentrePassword,
                                          @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/purchaseInstorage/unAudit", method = RequestMethod.POST)
    ResponseResult unAuditPurchaseInstorage(@RequestParam("acctId") String acctId,
                                            @RequestParam("dataCentreUserName") String dataCentreUserName,
                                            @RequestParam("dataCentrePassword") String dataCentrePassword,
                                            @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/purchaseInstorage/delete", method = RequestMethod.POST)
    ResponseResult deletePurchaseInstorage(@RequestParam("acctId") String acctId,
                                           @RequestParam("dataCentreUserName") String dataCentreUserName,
                                           @RequestParam("dataCentrePassword") String dataCentrePassword,
                                           @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/purchaseInstorage/cancel", method = RequestMethod.POST)
    ResponseResult cancelPurchaseInstorage(@RequestParam("acctId") String acctId,
                                           @RequestParam("dataCentreUserName") String dataCentreUserName,
                                           @RequestParam("dataCentrePassword") String dataCentrePassword,
                                           @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/purchaseInstorage/unCancel", method = RequestMethod.POST)
    ResponseResult unCancelPurchaseInstorage(@RequestParam("acctId") String acctId,
                                             @RequestParam("dataCentreUserName") String dataCentreUserName,
                                             @RequestParam("dataCentrePassword") String dataCentrePassword,
                                             @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    //============================采购退料============================
    @ApiOperation(value = "采购退料保存", notes = "采购退料保存")
    @RequestMapping(value = "/k3cloud/stock/purchaseReturn/savePurchaseReturn", method = RequestMethod.POST)
    public ResponseResult savePurchaseReturn(@RequestParam("acctId") String acctId,
                                             @RequestParam("dataCentreUserName") String dataCentreUserName,
                                             @RequestParam("dataCentrePassword") String dataCentrePassword,
                                             @RequestParam("fStockOrgId") String fStockOrgId,
                                             @RequestParam("fDate") String fDate,
                                             @RequestParam("fSupplierID") String fSupplierID,
                                             @RequestParam("jsonArrayProduct") String jsonArrayProduct);




    @RequestMapping(value = "/k3cloud/stock/purchaseReturn/view", method = RequestMethod.POST)
    ResponseResult viewPurchaseReturn(@RequestParam("acctId") String acctId,
                                      @RequestParam("dataCentreUserName") String dataCentreUserName,
                                      @RequestParam("dataCentrePassword") String dataCentrePassword,
                                      @RequestParam("number") String number, @RequestParam("id") String id);

    @RequestMapping(value = "/k3cloud/stock/purchaseReturn/submit", method = RequestMethod.POST)
    ResponseResult submitPurchaseReturn(@RequestParam("acctId") String acctId,
                                        @RequestParam("dataCentreUserName") String dataCentreUserName,
                                        @RequestParam("dataCentrePassword") String dataCentrePassword,
                                        @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/purchaseReturn/audit", method = RequestMethod.POST)
    ResponseResult auditPurchaseReturn(@RequestParam("acctId") String acctId,
                                       @RequestParam("dataCentreUserName") String dataCentreUserName,
                                       @RequestParam("dataCentrePassword") String dataCentrePassword,
                                       @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/purchaseReturn/unAudit", method = RequestMethod.POST)
    ResponseResult unAuditPurchaseReturn(@RequestParam("acctId") String acctId,
                                         @RequestParam("dataCentreUserName") String dataCentreUserName,
                                         @RequestParam("dataCentrePassword") String dataCentrePassword,
                                         @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/purchaseReturn/delete", method = RequestMethod.POST)
    ResponseResult deletePurchaseReturn(@RequestParam("acctId") String acctId,
                                        @RequestParam("dataCentreUserName") String dataCentreUserName,
                                        @RequestParam("dataCentrePassword") String dataCentrePassword,
                                        @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/purchaseReturn/cancel", method = RequestMethod.POST)
    ResponseResult cancelPurchaseReturn(@RequestParam("acctId") String acctId,
                                        @RequestParam("dataCentreUserName") String dataCentreUserName,
                                        @RequestParam("dataCentrePassword") String dataCentrePassword,
                                        @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/purchaseReturn/unCancel", method = RequestMethod.POST)
    ResponseResult unCancelPurchaseReturn(@RequestParam("acctId") String acctId,
                                          @RequestParam("dataCentreUserName") String dataCentreUserName,
                                          @RequestParam("dataCentrePassword") String dataCentrePassword,
                                          @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/stock/purchaseReturn/manageUnAudit", method = RequestMethod.POST)
    ResponseResult manageUnAuditPurchaseReturn(@RequestParam("acctId") String acctId,
                                               @RequestParam("dataCentreUserName") String dataCentreUserName,
                                               @RequestParam("dataCentrePassword") String dataCentrePassword,
                                               @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);



    @ApiOperation(value = "保存采购订单", notes = "保存采购订单")
    @RequestMapping(value = "/k3cloud/stock/purchaseInstorage/savePurchaseOrder", method = RequestMethod.POST)
    ResponseResult savePurchaseOrder(@RequestParam("acctId") String acctId,
                                     @RequestParam("dataCentreUserName") String dataCentreUserName,
                                     @RequestParam("dataCentrePassword") String dataCentrePassword,
                                     @RequestParam("fPurchaseOrgId") String fPurchaseOrgId,
                                     @RequestParam("fSupplierId") String fSupplierId,
                                     @RequestParam("fDate") String fDate,
                                     @RequestParam("jsonArrayProduct") String jsonArrayProduct);

    @ApiOperation(value = "销售订单保存", notes = "销售订单保存")
    @RequestMapping(value = "/k3cloud/stock/marketOutstorage/marketOutstorageOrderSave", method = RequestMethod.POST)
    public ResponseResult marketOutstorageOrderSave(@RequestParam("acctId") String acctId,
                                                    @RequestParam("dataCentreUserName") String dataCentreUserName,
                                                    @RequestParam("dataCentrePassword") String dataCentrePassword,
                                                    @RequestParam("fNumberSaleOrg") String fNumberSaleOrg,
                                                    @RequestParam("fDate") String fDate,
                                                    @RequestParam("fNumberCustomer") String fNumberCustomer,
                                                    @RequestParam("jsonArrayProduct") String jsonArrayProduct,
                                                    @RequestParam("fSaleDeptId") String fSaleDeptId,
                                                    @RequestParam("fSalerId") String fSalerId);

    @ApiOperation(value = "员工查看", notes = "员工查看")
    @RequestMapping(value = "/k3cloud/employees/employees/employeesView", method = RequestMethod.POST)
    public ResponseResult employeesView(@RequestParam("acctId") String acctId,
                                        @RequestParam("dataCentreUserName") String dataCentreUserName,
                                        @RequestParam("dataCentrePassword") String dataCentrePassword,
                                        @RequestParam("number") String number,
                                        @RequestParam("id") String id);




}


