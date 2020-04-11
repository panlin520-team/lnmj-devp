/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.stock.inventory;
import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.stock.*;
import lombok.Data;

/**
 * Auto-generated: 2019-11-11 13:51:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class InventoryModel {
    @JSONField(name = "FID")
    private String FID;
    @JSONField(name = "FMtoNo")
    private String FMtoNo;
    @JSONField(name = "FProjectNo")
    private String FProjectNo;
    @JSONField(name = "FOwnerTypeId")
    private String FOwnerTypeId;
    @JSONField(name = "FSecQty")
    private Integer FSecQty;
    @JSONField(name = "FExpiryDate")
    private String FExpiryDate;
    @JSONField(name = "FStockStatusId")
    private FStockStatusId FStockStatusId;
    @JSONField(name = "FProduceDate")
    private String FProduceDate;
    @JSONField(name = "FAuxPropId")
    private FAuxPropId FAuxPropId;
    @JSONField(name = "FSecAVBQty")
    private Integer FSecAVBQty;
    @JSONField(name = "FExpiryDays")
    private Integer FExpiryDays;
    @JSONField(name = "FBaseAVBQty")
    private Integer FBaseAVBQty;
    @JSONField(name = "FBomId")
    private FBOMId FBomId;
    @JSONField(name = "FAVBQty")
    private Integer FAVBQty;
    @JSONField(name = "FBaseLockQty")
    private Integer FBaseLockQty;
    @JSONField(name = "FSecUnitId")
    private FSecUnitId FSecUnitId;
    @JSONField(name = "FStockUnitId")
    private FStockUnitId FStockUnitId;
    @JSONField(name = "FBaseUnitId")
    private FBaseUnitId FBaseUnitId;
    @JSONField(name = "FKeeperTypeId")
    private String FKeeperTypeId;
    @JSONField(name = "FStockOrgId")
    private FStockOrgId FStockOrgId;
    @JSONField(name = "FBaseQty")
    private Integer FBaseQty;
    @JSONField(name = "FQty")
    private Integer FQty;
    @JSONField(name = "FSecLockQty")
    private Integer FSecLockQty;
    @JSONField(name = "FLot")
    private FLot FLot;
    @JSONField(name = "FLockQty")
    private Integer FLockQty;
    @JSONField(name = "FKeeperId")
    private FKeeperId FKeeperId;
    @JSONField(name = "FOwnerId")
    private FOwnerId FOwnerId;
    @JSONField(name = "FStockId")
    private FStockId FStockId;
    @JSONField(name = "FMaterialId")
    private FMaterialId FMaterialId;
    @JSONField(name = "FStockLocId")
    private FStockLocId FStockLocId;
}