package com.lnmj.product.entity.VO.K3VO;

import lombok.Data;
import org.springframework.data.annotation.Transient;

@Data
public class OtherOutstorageVO {

    private String fNumberStockOrg; //库存组织
    private String fDate;   // 日期
    private String fNumberBillType; //单据类型
    private String fOwnerTypeIdHead;    //货主类型
    private String fNumberOwerIdHead;   //货主
    private String fStockDirect;        //库存方向
    /* 明细信息 [{"FMaterialId":"4447","FStockId":"CK001","FUnitID":"100156","FStockStatusId":"PRE001","FOwnerTypeId":"1",
        "FOwnerId":"100","FKeeperTypeId":"1","FKeeperId":"100","FBaseUnitId":"100156"}]
         物料编码：FMaterialId  (必填项)
	     发货仓库：FStockId  (必填项)
	     库存状态：FStockStatusId  (必填项)
         货主类型：FOwnerTypeId  (必填项)
         货主：FOwnerId  (必填项)
         保管者类型：FKeeperTypeId  (必填项)
         保管者：FKeeperId  (必填项)
         基本单位：FBaseUnitId  (必填项)
     */
    private String entityJson;
    private String fID;         //实体主键
    private String fBillNo;     //单据编号
    private String acctId;  //数据中心
    private String dataCentreUserName;  //用户名
    private String dataCentrePassword;  //密码

    @Transient
    private String fStockOrgId;
    @Transient
    private String fPickOrgId;
    @Transient
    private String fCustId;
    @Transient
    private String fOwnerIdHead;
    @Transient
    private String stockId;
}
