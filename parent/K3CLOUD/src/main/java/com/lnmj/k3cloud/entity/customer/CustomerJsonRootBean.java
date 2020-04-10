/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.customer;
import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.base.BaseData;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2019-11-11 14:30:30
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class CustomerJsonRootBean extends BaseData {

    @JSONField(name ="Model")
    private CustomerModel Model;
}