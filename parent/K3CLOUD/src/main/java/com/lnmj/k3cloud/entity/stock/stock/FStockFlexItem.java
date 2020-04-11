/**
  * Copyright 2019 bejson.com 
  */
package com.lnmj.k3cloud.entity.stock.stock;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2019-11-11 12:12:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class FStockFlexItem {
    @JSONField(name = "FEntryID")
    private Integer FEntryID;
    @JSONField(name = "FFlexId")
    private FFlexId FFlexId;
    @JSONField(name = "FIsMustInput")
    private String FIsMustInput;
    @JSONField(name = "FStockFlexDetail")
    private List<FStockFlexDetail> FStockFlexDetail;
}