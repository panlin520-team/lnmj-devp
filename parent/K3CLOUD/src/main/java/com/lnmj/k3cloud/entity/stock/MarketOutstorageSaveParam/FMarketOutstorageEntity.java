/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.stock.MarketOutstorageSaveParam;
import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.stock.*;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2019-11-11 17:28:51
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class FMarketOutstorageEntity {
    @JSONField(name = "FENTRYID")
    private Integer FENTRYID;
    @JSONField(name = "FRowType")
    private String FRowType;
    @JSONField(name = "FCustMatID")
    private FCustMatID FCustMatID;
    @JSONField(name = "FMaterialID")
    private FMaterialId FMaterialID;
    @JSONField(name = "FAuxPropId")
    private FAuxPropId FAuxPropId;
    @JSONField(name = "FUnitID")
    private FUnitID FUnitID;
    @JSONField(name = "FInventoryQty")
    private Integer FInventoryQty;
    @JSONField(name = "FParentMatId")
    private FParentMatId FParentMatId;
    @JSONField(name = "FRealQty")
    private Integer FRealQty;
    @JSONField(name = "FDisPriceQty")
    private Integer FDisPriceQty;
    @JSONField(name = "FPrice")
    private Integer FPrice;
    @JSONField(name = "FTaxPrice")
    private Integer FTaxPrice;
    @JSONField(name = "FIsFree")
    private String FIsFree;
    @JSONField(name = "FBomID")
    private FBOMId FBomID;
    @JSONField(name = "FProduceDate")
    private String FProduceDate;
    @JSONField(name = "FOwnerTypeID")
    private String FOwnerTypeID;
    @JSONField(name = "FOwnerID")
    private FOwnerId FOwnerID;
    @JSONField(name = "FLot")
    private FLot FLot;
    @JSONField(name = "FExpiryDate")
    private String FExpiryDate;
    @JSONField(name = "FTaxCombination")
    private FTaxCombination FTaxCombination;
    @JSONField(name = "FEntryTaxRate")
    private Integer FEntryTaxRate;
    @JSONField(name = "FAuxUnitQty")
    private Integer FAuxUnitQty;
    @JSONField(name = "FExtAuxUnitId")
    private FExtAuxUnitId FExtAuxUnitId;
    @JSONField(name = "FExtAuxUnitQty")
    private Integer FExtAuxUnitQty;
    @JSONField(name = "FStockID")
    private FStockId FStockID;
    @JSONField(name = "FStockLocID")
    private FStockLocId FStockLocID;
    @JSONField(name = "FStockStatusID")
    private FStockStatusId FStockStatusID;
    @JSONField(name = "FQualifyType")
    private String FQualifyType;
    @JSONField(name = "FMtoNo")
    private String FMtoNo;
    @JSONField(name = "FEntrynote")
    private String FEntrynote;
    @JSONField(name = "FDiscountRate")
    private Integer FDiscountRate;
    @JSONField(name = "FPriceDiscount")
    private Integer FPriceDiscount;
    @JSONField(name = "FActQty")
    private Integer FActQty;
    @JSONField(name = "FSalUnitID")
    private FSalUnitID FSalUnitID;
    @JSONField(name = "FSALUNITQTY")
    private Integer FSALUNITQTY;
    @JSONField(name = "FSALBASEQTY")
    private Integer FSALBASEQTY;
    @JSONField(name = "FPRICEBASEQTY")
    private Integer FPRICEBASEQTY;
    @JSONField(name = "FProjectNo")
    private String FProjectNo;
    @JSONField(name = "FOUTCONTROL")
    private Boolean FOUTCONTROL;
    @JSONField(name = "FRepairQty")
    private Integer FRepairQty;
    @JSONField(name = "FIsCreateProDoc")
    private String FIsCreateProDoc;
    @JSONField(name = "FEOwnerSupplierId")
    private FEOwnerSupplierId FEOwnerSupplierId;
    @JSONField(name = "FIsOverLegalOrg")
    private Boolean FIsOverLegalOrg;
    @JSONField(name = "FESettleCustomerId")
    private FESettleCustomerId FESettleCustomerId;
    @JSONField(name = "FPriceListEntry")
    private FPriceListEntry FPriceListEntry;
    @JSONField(name = "FARNOTJOINQTY")
    private Integer FARNOTJOINQTY;
    @JSONField(name = "FQmEntryID")
    private Integer FQmEntryID;
    @JSONField(name = "FConvertEntryID")
    private Integer FConvertEntryID;
    @JSONField(name = "FSOEntryId")
    private Integer FSOEntryId;
    @JSONField(name = "FThirdEntryId")
    private String FThirdEntryId;
    @JSONField(name = "FBeforeDisPriceQty")
    private Integer FBeforeDisPriceQty;
    @JSONField(name = "FSignQty")
    private Integer FSignQty;
    @JSONField(name = "FCheckDelivery")
    private Boolean FCheckDelivery;
    @JSONField(name = "FETHIRDBILLID")
    private String FETHIRDBILLID;
    @JSONField(name = "FETHIRDBILLNO")
    private String FETHIRDBILLNO;
    @JSONField(name = "FTaxDetailSubEntity")
    private List<FTaxDetailSubEntity> FTaxDetailSubEntity;
    @JSONField(name = "FSerialSubEntity")
    private List<FSerialSubEntity> FSerialSubEntity;
}