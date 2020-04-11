package com.lnmj.product.entity.VO.K3VO;

import lombok.Data;

@Data
public class PurchaseReturnVO {


    private String acctId;  //数据中心
    private String dataCentreUserName;  //用户名
    private String dataCentrePassword;  //密码


    private String orgK3Number;
    private String fDate;
    private String supplierK3Number;
    private String jsonArrayProduct;
}
