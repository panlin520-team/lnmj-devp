/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.account.accountPolicy;
import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.base.BaseData;
import lombok.Data;


/**
    会计政策实体
*/
@Data
public class AccountPolicyJsonRootBean extends BaseData {

    @JSONField(name ="Model")
    private AccountPolicyModel Model;
}