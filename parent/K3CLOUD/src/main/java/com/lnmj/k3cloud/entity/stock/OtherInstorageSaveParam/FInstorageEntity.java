/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.stock.OtherInstorageSaveParam;
import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.organization.organizationForm.addSendParam.Fmodifierid;
import com.lnmj.k3cloud.entity.stock.*;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2019-11-10 11:32:48
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class FInstorageEntity {
    @JSONField(name = "FEntryID")
    private Integer FEntryID;
    @JSONField(name = "FMATERIALID")
    private FMaterialId FMATERIALID;
    @JSONField(name = "FAuxPropId")
    private FAuxPropId FAuxPropId;
    @JSONField(name = "FUnitID")
    private FUnitID FUnitID;
    @JSONField(name = "FSTOCKID")
    private FStockId FSTOCKID;
    @JSONField(name = "FStockLocId")
    private FStockLocId FStockLocId;
    @JSONField(name = "FSTOCKSTATUSID")
    private FStockStatusId FSTOCKSTATUSID;
    @JSONField(name = "FLOT")
    private FLot FLOT;
    @JSONField(name = "FQty")
    private Integer FQty;
    @JSONField(name = "FEntryNote")
    private String FEntryNote;
    @JSONField(name = "FBOMID")
    private FBOMId FBOMID;
    @JSONField(name = "FPRODUCEDATE")
    private String FPRODUCEDATE;
    @JSONField(name = "FMTONO")
    private String FMTONO;
    @JSONField(name = "FExtAuxUnitId")
    private FExtAuxUnitId FExtAuxUnitId;
    @JSONField(name = "FExtAuxUnitQty")
    private Integer FExtAuxUnitQty;
    @JSONField(name = "FProjectNo")
    private String FProjectNo;
    @JSONField(name = "FOWNERTYPEID")
    private String FOWNERTYPEID;
    @JSONField(name = "FOWNERID")
    private FOwnerId FOWNERID;
    @JSONField(name = "FKEEPERTYPEID")
    private String FKEEPERTYPEID;
    @JSONField(name = "FKEEPERID")
    private FKeeperId FKEEPERID;
    @JSONField(name = "FBASEQTY")
    private Integer FBASEQTY;
    @JSONField(name = "FSerialSubEntity")
    private List<FSerialSubEntity> FSerialSubEntity;
}