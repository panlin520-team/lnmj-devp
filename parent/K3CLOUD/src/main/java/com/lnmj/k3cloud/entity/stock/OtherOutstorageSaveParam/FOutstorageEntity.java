/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.stock.OtherOutstorageSaveParam;
import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.stock.*;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2019-11-11 15:26:8
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class FOutstorageEntity {
    @JSONField(name = "FEntryID")
    private Integer FEntryID;
    @JSONField(name = "FMaterialId")
    private FMaterialId FMaterialId;
    @JSONField(name = "FAuxPropId")
    private FAuxPropId FAuxPropId;
    @JSONField(name = "FUnitID")
    private FUnitID FUnitID;
    @JSONField(name = "FQty")
    private Integer FQty;
    @JSONField(name = "FBaseUnitId")
    private FBaseUnitId FBaseUnitId;
    @JSONField(name = "FStockId")
    private FStockId FStockId;
    @JSONField(name = "FStockLocId")
    private FStockLocId FStockLocId;
    @JSONField(name = "FLot")
    private FLot FLot;
    @JSONField(name = "FPRODUCTGROUPID")
    private FPRODUCTGROUPID FPRODUCTGROUPID;
    @JSONField(name = "FOwnerTypeId")
    private String FOwnerTypeId;
    @JSONField(name = "FOwnerId")
    private FOwnerId FOwnerId;
    @JSONField(name = "FEntryNote")
    private String FEntryNote;
    @JSONField(name = "FBomId")
    private FBOMId FBomId;
    @JSONField(name = "FProjectNo")
    private String FProjectNo;
    @JSONField(name = "FProduceDate")
    private String FProduceDate;
    @JSONField(name = "FServiceContext")
    private String FServiceContext;
    @JSONField(name = "FStockStatusId")
    private FStockStatusId FStockStatusId;
    @JSONField(name = "FMtoNo")
    private String FMtoNo;
    @JSONField(name = "FCostItem")
    private FCostItem FCostItem;
    @JSONField(name = "FKeeperTypeId")
    private String FKeeperTypeId;
    @JSONField(name = "FDistribution")
    private String FDistribution;
    @JSONField(name = "FKeeperId")
    private FKeeperId FKeeperId;
    @JSONField(name = "FExtAuxUnitId")
    private FExtAuxUnitId FExtAuxUnitId;
    @JSONField(name = "FExtAuxUnitQty")
    private Integer FExtAuxUnitQty;
    @JSONField(name = "FSerialSubEntity")
    private List<FSerialSubEntity> FSerialSubEntity;
}