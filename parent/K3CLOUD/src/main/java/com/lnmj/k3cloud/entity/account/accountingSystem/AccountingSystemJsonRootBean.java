/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.account.accountingSystem;
import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.base.BaseData;
import lombok.Data;


@Data
public class AccountingSystemJsonRootBean extends BaseData {

    @JSONField(name ="Model")
    private AccountingSystemModel Model;
}