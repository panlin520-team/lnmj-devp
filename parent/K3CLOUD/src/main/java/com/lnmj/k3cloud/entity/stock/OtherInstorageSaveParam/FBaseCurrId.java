/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.stock.OtherInstorageSaveParam;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Auto-generated: 2019-11-10 11:32:48
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class FBaseCurrId {
    @JSONField(name = "FNumber")
    private String FNumber;

    public FBaseCurrId(String FNumber) {
        this.FNumber = FNumber;
    }
}