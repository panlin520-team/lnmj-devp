package com.lnmj.k3cloud.entity.stock.PurchaseReturnSaveParam;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.stock.*;
import lombok.Data;
/**
 * Auto-generated: 2019-11-13 10:18:40
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class FPURMRBENTRY {

    @JSONField(name ="FEntryID")
    private Integer FEntryID;
    @JSONField(name ="FRowType")
    private String FRowType;
    @JSONField(name ="FMATERIALID")
    private FMaterialId FMATERIALID;
    @JSONField(name ="FMaterialDesc")
    private String FMaterialDesc;
    @JSONField(name ="FParentMatId")
    private FParentMatId FParentMatId;
    @JSONField(name ="FProjectNo")
    private String FProjectNo;
    @JSONField(name ="FUnitID")
    private FUnitID FUnitID;
    @JSONField(name ="FRMREALQTY")
    private Integer FRMREALQTY;
    @JSONField(name ="FREPLENISHQTY")
    private Integer FREPLENISHQTY;
    @JSONField(name ="FKEAPAMTQTY")
    private Integer FKEAPAMTQTY;
    @JSONField(name ="FPRICEUNITID")
    private FPriceUnitID FPRICEUNITID;
    @JSONField(name ="FSTOCKID")
    private FStockId FSTOCKID;
    @JSONField(name ="FSTOCKLOCID")
    private FSTOCKLOCID__FF100001 FSTOCKLOCID;
    @JSONField(name ="FStockStatusId")
    private FStockStatusId FStockStatusId;
    @JSONField(name ="FNOTE")
    private String FNOTE;
    @JSONField(name ="FTaxCombination")
    private FTaxCombination FTaxCombination;
    @JSONField(name ="FPrice")
    private Integer FPrice;
    @JSONField(name ="FExtAuxUnitId")
    private FExtAuxUnitId FExtAuxUnitId;
    @JSONField(name ="FExtAuxUnitQty")
    private Integer FExtAuxUnitQty;
    @JSONField(name ="FREQTRACENO")
    private String FREQTRACENO;
    @JSONField(name ="FIsReceiveUpdateStock")
    private String FIsReceiveUpdateStock;
    @JSONField(name ="FInvoicedJoinQty")
    private Integer FInvoicedJoinQty;
    @JSONField(name ="FGiveAway")
    private String FGiveAway;
    @JSONField(name ="FPriceBaseQty")
    private Integer FPriceBaseQty;
    @JSONField(name ="FSetPriceUnitID")
    private FSetPriceUnitID FSetPriceUnitID;
    @JSONField(name ="FCarryUnitId")
    private FCarryUnitId FCarryUnitId;
    @JSONField(name ="FCarryQty")
    private Integer FCarryQty;
    @JSONField(name ="FCarryBaseQty")
    private Integer FCarryBaseQty;
    @JSONField(name ="FPOORDERENTRYID")
    private Integer FPOORDERENTRYID;
    @JSONField(name ="FBILLINGCLOSE")
    private String FBILLINGCLOSE;
    @JSONField(name ="FPriceListEntry")
    private FPriceListEntry FPriceListEntry;
    @JSONField(name ="FAuxPropID")
    private FAuxPropId FAuxPropID;
    @JSONField(name ="FRMMUSTQTY")
    private Integer FRMMUSTQTY;
    @JSONField(name ="FBOMID")
    private FBOMId FBOMID;
    @JSONField(name ="FSupplierLot")
    private String FSupplierLot;
    @JSONField(name ="FLot")
    private FLot FLot;
    @JSONField(name ="FProduceDate")
    private String FProduceDate;
    @JSONField(name ="FEXPIRYDATE")
    private String FEXPIRYDATE;
    @JSONField(name ="FAUXUNITQTY")
    private Integer FAUXUNITQTY;
    @JSONField(name ="FENTRYTAXRATE")
    private Integer FENTRYTAXRATE;
    @JSONField(name ="FDISCOUNTRATE")
    private Integer FDISCOUNTRATE;
    @JSONField(name ="FTAXPRICE")
    private Integer FTAXPRICE;
    @JSONField(name ="FPriceDiscount")
    private Integer FPriceDiscount;
    @JSONField(name ="FIsStock")
    private String FIsStock;
    @JSONField(name ="FChargeProjectID")
    private FChargeProjectID FChargeProjectID;
    @JSONField(name ="FMtoNo")
    private String FMtoNo;
    @JSONField(name ="FEntryPruCost")
    private List<FEntryPruCost> FEntryPruCost;
    @JSONField(name ="FTaxDetailSubEntity")
    private List<FTaxDetailSubEntity> FTaxDetailSubEntity;
    @JSONField(name ="FSerialSubEntity")
    private List<FSerialSubEntity> FSerialSubEntity;
}