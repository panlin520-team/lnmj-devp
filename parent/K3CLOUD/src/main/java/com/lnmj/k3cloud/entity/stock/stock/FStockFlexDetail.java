/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.stock.stock;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Auto-generated: 2019-11-11 12:12:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class FStockFlexDetail {
    @JSONField(name = "FDetailID")
    private Integer FDetailID;
    @JSONField(name = "FFlexEntryId")
    private FFlexEntryId FFlexEntryId;
    @JSONField(name = "FIsRepeat")
    private String FIsRepeat;
}