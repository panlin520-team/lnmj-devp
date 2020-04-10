package com.lnmj.product.entity.VO.K3VO;

import lombok.Data;

@Data
public class OtherInstorageVO {

    private String fNumberStockOrg;     //库存组织
    private String fDate;               // 日期
    private String fNumberBillType;     //单据类型
    private String fOwnerTypeIdHead;    //货主类型
    private String fNumberOwerIdHead;   //货主
    private String fStockDirect;        //库存方向
    /* [{"FMATERIALID":"4447","FSTOCKID":"CK001","FUnitID":"100156","FSTOCKSTATUSID":"PRE001","FOWNERTYPEID":"1",
        "FOWNERID":"100","FKEEPERTYPEID":"1","FKEEPERID":"100"}]
    明细信息
        物料编码：FMATERIALID  (必填项)
	    收货仓库：FSTOCKID  (必填项)
	    单位：FUnitID  (必填项)
	    库存状态：FSTOCKSTATUSID  (必填项)
	    货主类型：FOWNERTYPEID  (必填项)
	    货主：FOWNERID  (必填项)
	    保管者类型：FKEEPERTYPEID  (必填项)
	    保管者：FKEEPERID  (必填项)
    */
    private String entityJson;
    private String fID;     //实体主键
    private String fBillNo; //单据编号
    private String acctId;  //数据中心
    private String dataCentreUserName;  //用户名
    private String dataCentrePassword;  //密码

}
