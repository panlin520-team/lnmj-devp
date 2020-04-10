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
public class PurchaseInstorageModel {
    @JSONField(name = "FID")
    private Integer FID;
    @JSONField(name = "FBillTypeID")
    private FBillTypeID FBillTypeID;    //单据类型必录
    @JSONField(name = "FBillNo")
    private String FBillNo;
    @JSONField(name = "FDate")
    private String FDate; //入库日期必录
    @JSONField(name = "FStockOrgId")
    private FStockOrgId FStockOrgId;    //收料组织必录
    @JSONField(name = "FStockDeptId")
    private FStockDeptId FStockDeptId;
    @JSONField(name = "FStockerGroupId")
    private FStockerGroupId FStockerGroupId;
    @JSONField(name = "FStockerId")
    private FStockId FStockerId;
    @JSONField(name = "FDemandOrgId")
    private FDemandOrgId FDemandOrgId;
    @JSONField(name = "FCorrespondOrgId")
    private FCorrespondOrgId FCorrespondOrgId;
    @JSONField(name = "FPurchaseOrgId")
    private FPurchaseOrgId FPurchaseOrgId;  //采购组织必录
    @JSONField(name = "FPurchaseDeptId")
    private FPurchaseDeptId FPurchaseDeptId;
    @JSONField(name = "FPurchaserGroupId")
    private FPurchaserGroupId FPurchaserGroupId;
    @JSONField(name = "FPurchaserId")
    private FPurchaserId FPurchaserId;
    @JSONField(name = "FSupplierId")
    private FSupplierId FSupplierId;    //供应商必录
    @JSONField(name = "FSupplyId")
    private FSupplyId FSupplyId;
    @JSONField(name = "FSupplyAddress")
    private String FSupplyAddress;
    @JSONField(name = "FSettleId")
    private FSettleID FSettleId;
    @JSONField(name = "FChargeId")
    private FChargeId FChargeId;
    @JSONField(name = "FOwnerTypeIdHead")
    private String FOwnerTypeIdHead;    //货主类型必录
    @JSONField(name = "FOwnerIdHead")
    private FOwnerIdHead FOwnerIdHead;  //货主必录
    @JSONField(name = "FConfirmerId")
    private FConfirmerId FConfirmerId;
    @JSONField(name = "FConfirmDate")
    private String FConfirmDate;
    @JSONField(name = "FScanBox")
    private String FScanBox;
    @JSONField(name = "FCDateOffsetUnit")
    private String FCDateOffsetUnit;
    @JSONField(name = "FFCDateOffsetValueNumber")
    private Integer FCDateOffsetValue;
    @JSONField(name = "FProviderContactID")
    private FProviderContactID FProviderContactID;
    @JSONField(name = "FInStockFin")
    private FInStockFin FInStockFin;
    @JSONField(name = "FInStockEntry")
    private List<FInStockEntry> FInStockEntry;

}