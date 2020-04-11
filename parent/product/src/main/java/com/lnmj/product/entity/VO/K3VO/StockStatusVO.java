package com.lnmj.product.entity.VO.K3VO;

import lombok.Data;

@Data
public class StockStatusVO {

    private String fName;
    private String fNumberCreateOrg;
    private String fNumberUseOrg;
    private String fType;
    private String fNumber;
    private String fStockStatusId;
    private String acctId;  //数据中心
    private String dataCentreUserName;  //用户名
    private String dataCentrePassword;  //密码

}
