package com.lnmj.product.entity.VO.K3VO;

import lombok.Data;

@Data
public class ViewVO {

    private String number;
    private String id;
    private String acctId;  //数据中心
    private String dataCentreUserName;  //用户名
    private String dataCentrePassword;  //密码
}
