/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.bank.account;
import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.base.BaseData;
import lombok.Data;

/**
 * Auto-generated: 2019-11-09 16:19:5
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class BankAccountParam extends BaseData {
    @JSONField(name = "Model")
    private BankAccoutModel Model;
}