/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.stock.PurchaseInstorageSaveParam;
import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.stock.*;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2019-11-09 16:19:5
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class FInStockEntry {
    @JSONField(name = "FEntryID")
    private Integer FEntryID;
    @JSONField(name = "FRowType")
    private String FRowType;
    @JSONField(name = "FWWInType")
    private String FWWInType;
    @JSONField(name = "FMaterialId")
    private FMaterialId FMaterialId;    //物料编码
    @JSONField(name = "FUnitID")
    private FUnitID FUnitID;    //库存单位
    @JSONField(name = "FAuxPropId")
    private FAuxPropId FAuxPropId;
    @JSONField(name = "FParentMatId")
    private FParentMatId FParentMatId;
    @JSONField(name = "FRealQty")
    private Integer FRealQty;
    @JSONField(name = "FPriceUnitID")
    private FPriceUnitID FPriceUnitID;  //计价单位
    @JSONField(name = "FPrice")
    private Integer FPrice;
    @JSONField(name = "FTaxCombination")
    private FTaxCombination FTaxCombination;
    @JSONField(name = "FLot")
    private FLot FLot;
    @JSONField(name = "FStockId")
    private FStockId FStockId;
    @JSONField(name = "FDisPriceQty")
    private Integer FDisPriceQty;
    @JSONField(name = "FStockLocId")
    private FStockLocId FStockLocId;
    @JSONField(name = "FStockStatusId")
    private FStockStatusId FStockStatusId;
    @JSONField(name = "FMtoNo")
    private String FMtoNo;
    @JSONField(name = "FGiveAway")
    private String FGiveAway;
    @JSONField(name = "FNote")
    private String FNote;
    @JSONField(name = "FProduceDate")
    private String FProduceDate;
    @JSONField(name = "FExtAuxUnitId")
    private FExtAuxUnitId FExtAuxUnitId;
    @JSONField(name = "FExtAuxUnitQty")
    private Integer FExtAuxUnitQty;
    @JSONField(name = "FCheckInComing")
    private String FCheckInComing;
    @JSONField(name = "FProjectNo")
    private String FProjectNo;
    @JSONField(name = "FIsReceiveUpdateStock")
    private String FIsReceiveUpdateStock;
    @JSONField(name = "FInvoicedJoinQty")
    private Integer FInvoicedJoinQty;
    @JSONField(name = "FPriceBaseQty")
    private Integer FPriceBaseQty;
    @JSONField(name = "FSetPriceUnitID")
    private FSetPriceUnitID FSetPriceUnitID;
    @JSONField(name = "FRemainInStockUnitId")
    private FRemainInStockUnitId FRemainInStockUnitId;  //采购单位
    @JSONField(name = "FBILLINGCLOSE")
    private String FBILLINGCLOSE;
    @JSONField(name = "FRemainInStockQty")
    private Integer FRemainInStockQty;
    @JSONField(name = "FAPNotJoinQty")
    private Integer FAPNotJoinQty;
    @JSONField(name = "FRemainInStockBaseQty")
    private Integer FRemainInStockBaseQty;
    @JSONField(name = "FTaxPrice")
    private Integer FTaxPrice;
    @JSONField(name = "FEntryTaxRate")
    private Integer FEntryTaxRate;
    @JSONField(name = "FDiscountRate")
    private Integer FDiscountRate;
    @JSONField(name = "FCostPrice")
    private Integer FCostPrice;
    @JSONField(name = "FBOMId")
    private FBOMId FBOMId;
    @JSONField(name = "FSupplierLot")
    private String FSupplierLot;
    @JSONField(name = "FExpiryDate")
    private String FExpiryDate;
    @JSONField(name = "FAuxUnitQty")
    private Integer FAuxUnitQty;
    @JSONField(name = "FPriceDiscount")
    private Integer FPriceDiscount;
    @JSONField(name = "FBeforeDisPriceQty")
    private Integer FBeforeDisPriceQty;
    @JSONField(name = "FEntryPruCost")
    private List<FEntryPruCost> FEntryPruCost;
    @JSONField(name = "FTaxDetailSubEntity")
    private List<FTaxDetailSubEntity> FTaxDetailSubEntity;
    @JSONField(name = "FSerialSubEntity")
    private List<FSerialSubEntity> FSerialSubEntity;

}