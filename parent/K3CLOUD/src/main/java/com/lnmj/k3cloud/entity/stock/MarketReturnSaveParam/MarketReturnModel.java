package com.lnmj.k3cloud.entity.stock.MarketReturnSaveParam;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.stock.*;
import lombok.Data;
/**
 * Auto-generated: 2019-11-13 10:5:38
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class MarketReturnModel {

    @JSONField(name ="FID")
    private Integer FID;
    @JSONField(name ="FBillTypeID")
    private FBillTypeID FBillTypeID;
    @JSONField(name ="FBillNo")
    private String FBillNo;
    @JSONField(name ="FDate")
    private String FDate;
    @JSONField(name ="FSaleOrgId")
    private FSaleOrgId FSaleOrgId;
    @JSONField(name ="FRetcustId")
    private FRetcustId FRetcustId;
    @JSONField(name ="FSaledeptid")
    private FSaleDeptID FSaledeptid;
    @JSONField(name ="FReturnReason")
    private FReturnType FReturnReason;
    @JSONField(name ="FHeadLocId")
    private FHeadLocId FHeadLocId;
    @JSONField(name ="FCorrespondOrgId")
    private FCorrespondOrgId FCorrespondOrgId;
    @JSONField(name ="FTransferBizType")
    private FTransferBizType FTransferBizType;
    @JSONField(name ="FSaleGroupId")
    private FSaleGroupId FSaleGroupId;
    @JSONField(name ="FSalesManId")
    private FSalesManID FSalesManId;
    @JSONField(name ="FStockOrgId")
    private FStockOrgId FStockOrgId;
    @JSONField(name ="FStockDeptId")
    private FStockDeptId FStockDeptId;
    @JSONField(name ="FStockerGroupId")
    private FStockerGroupId FStockerGroupId;
    @JSONField(name ="FStockerId")
    private FStockerId FStockerId;
    @JSONField(name ="FHeadNote")
    private String FHeadNote;
    @JSONField(name ="FReceiveCustId")
    private FReceiveCustId FReceiveCustId;
    @JSONField(name ="FReceiveAddress")
    private String FReceiveAddress;
    @JSONField(name ="FSettleCustId")
    private FSettleCustId FSettleCustId;
    @JSONField(name ="FReceiveCusContact")
    private FReceiveCusContact FReceiveCusContact;
    @JSONField(name ="FPayCustId")
    private FPayCustId FPayCustId;
    @JSONField(name ="FOwnerTypeIdHead")
    private String FOwnerTypeIdHead;
    @JSONField(name ="FOwnerIdHead")
    private FOwnerIdHead FOwnerIdHead;
    @JSONField(name ="FScanBox")
    private String FScanBox;
    @JSONField(name ="FCDateOffsetUnit")
    private String FCDateOffsetUnit;
    @JSONField(name ="FCDateOffsetValue")
    private Integer FCDateOffsetValue;
    @JSONField(name ="FIsTotalServiceOrCost")
    private String FIsTotalServiceOrCost;
    @JSONField(name ="FSHOPNUMBER")
    private String FSHOPNUMBER;
    @JSONField(name ="SubHeadEntity")
    private Subheadentity SubHeadEntity;
    @JSONField(name ="FEntity")
    private List<FEntity> FEntity;

}