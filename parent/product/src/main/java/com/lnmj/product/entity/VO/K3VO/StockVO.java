package com.lnmj.product.entity.VO.K3VO;

import lombok.Data;
import org.springframework.data.annotation.Transient;

@Data
public class StockVO {

    private String fName;
    private String fNumberCreateOrg;
    private String fNumerUseOrg;
    private String fStockProperty;
    private String fStockStatusType;
    private String fNumber;
    private String fStockId;
    private String acctId;  //数据中心
    private String dataCentreUserName;  //用户名
    private String dataCentrePassword;  //密码

    @Transient
    private String k3Number;
}
