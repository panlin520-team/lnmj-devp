/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.stock;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Auto-generated: 2019-11-11 15:26:8
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class FDeptId {
    @JSONField(name = "FNumber")
    private String FNumber;

    public FDeptId(String FNumber) {
        this.FNumber = FNumber;
    }
}