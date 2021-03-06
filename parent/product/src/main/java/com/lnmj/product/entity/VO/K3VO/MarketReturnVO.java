package com.lnmj.product.entity.VO.K3VO;

import lombok.Data;
import org.springframework.data.annotation.Transient;

@Data
public class MarketReturnVO {

    private String fNumberSaleOrg;
    private String fDate;
    private String fNumberStockOrg;
    private String fNumberFrecust;
    private String fNumberBillType;
    private String fNumberSettleOrg;
    private String fNumberSettleCurr;
    private String entityJson;
    private String fID;
    private String fBillNo;
    private String acctId;  //数据中心
    private String dataCentreUserName;  //用户名
    private String dataCentrePassword;  //密码

}
