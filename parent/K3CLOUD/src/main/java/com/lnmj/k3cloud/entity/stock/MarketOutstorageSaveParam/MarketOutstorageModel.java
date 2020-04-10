/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.stock.MarketOutstorageSaveParam;
import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.stock.*;
import com.lnmj.k3cloud.entity.stock.FStockOrgId;
import com.lnmj.k3cloud.entity.stock.stock.FCustomerId;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2019-11-11 17:28:51
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class MarketOutstorageModel {
    @JSONField(name = "FID")
    private Integer FID;
    @JSONField(name = "FBillTypeID")
    private FBillTypeID FBillTypeID;
    @JSONField(name = "FBillNo")
    private String FBillNo;
    @JSONField(name = "FDate")
    private String FDate;
    @JSONField(name = "FSaleOrgId")
    private FSaleOrgId FSaleOrgId;
    @JSONField(name = "FCustomerID")
    private FCustomerId FCustomerID;
    @JSONField(name = "FSaleDeptID")
    private FSaleDeptID FSaleDeptID;
    @JSONField(name = "FHeadLocationId")
    private FHeadLocationId FHeadLocationId;
    @JSONField(name = "FCorrespondOrgId")
    private FCorrespondOrgId FCorrespondOrgId;
    @JSONField(name = "FCarrierID")
    private FCarrierID FCarrierID;
    @JSONField(name = "FCarriageNO")
    private String FCarriageNO;
    @JSONField(name = "FSalesGroupID")
    private FSalesGroupID FSalesGroupID;
    @JSONField(name = "FSalesManID")
    private FSalesManID FSalesManID;
    @JSONField(name = "FStockOrgId")
    private FStockOrgId FStockOrgId;
    @JSONField(name = "FDeliveryDeptID")
    private FDeliveryDeptID FDeliveryDeptID;
    @JSONField(name = "FLinkMan")
    private String FLinkMan;
    @JSONField(name = "FLinkPhone")
    private String FLinkPhone;
    @JSONField(name = "FStockerGroupID")
    private FStockerGroupId FStockerGroupID;
    @JSONField(name = "FStockerID")
    private FStockerId FStockerID;
    @JSONField(name = "FNote")
    private String FNote;
    @JSONField(name = "FReceiverID")
    private FReceiverID FReceiverID;
    @JSONField(name = "FReceiveAddress")
    private String FReceiveAddress;
    @JSONField(name = "FSettleID")
    private FSettleID FSettleID;
    @JSONField(name = "FReceiverContactID")
    private FReceiverContactID FReceiverContactID;
    @JSONField(name = "FPayerID")
    private FPayerID FPayerID;
    @JSONField(name = "FOwnerTypeIdHead")
    private String FOwnerTypeIdHead;
    @JSONField(name = "FOwnerIdHead")
    private FOwnerIdHead FOwnerIdHead;
    @JSONField(name = "FScanBox")
    private String FScanBox;
    @JSONField(name = "FCDateOffsetUnit")
    private String FCDateOffsetUnit;
    @JSONField(name = "FCDateOffsetValue")
    private Integer FCDateOffsetValue;
    @JSONField(name = "FPlanRecAddress")
    private String FPlanRecAddress;
    @JSONField(name = "FIsTotalServiceOrCost")
    private String FIsTotalServiceOrCost;
    @JSONField(name = "FSHOPNUMBER")
    private String FSHOPNUMBER;
    @JSONField(name = "SubHeadEntity")
    private SubHeadEntity SubHeadEntity;
    @JSONField(name = "FEntity")
    private List<FMarketOutstorageEntity> FEntity;
    @JSONField(name = "FOutStockTrace")
    private List<FOutStockTrace> FOutStockTrace;
}