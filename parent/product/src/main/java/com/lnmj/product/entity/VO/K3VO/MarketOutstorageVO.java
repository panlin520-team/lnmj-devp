package com.lnmj.product.entity.VO.K3VO;

import lombok.Data;

@Data
public class MarketOutstorageVO {

    private String fNumberSaleOrg;  //销售组织
    private String fDate;//日期
    private String fNumberStockOrg;//发货组织
    private String fNumberCustomer;//客户
    private String fNumberBillType;//单据类型
    private String fNumberSettleOrg;//结算组织
    private String fNumberSettleCurr;//结算币别
    /* 明细信息 [{"FUnitID":"100156","FOwnerID":"100"}]
        库存单位 FUnitID  (必填项)
        货主：FOwnerID  (必填项)
     */
    private String entityJson;
    /* 物流跟踪明细 [{"FCarryBillNo":"4447"}]
         物流单号：FCarryBillNo  (必填项)
     */
    private String outStockTraceJson;//销售组织
    private String fID;         //实体主键
    private String fBillNo;     //单据编号
    private String acctId;  //数据中心
    private String dataCentreUserName;  //用户名
    private String dataCentrePassword;  //密码
    private String stockId;  //仓库id
}
