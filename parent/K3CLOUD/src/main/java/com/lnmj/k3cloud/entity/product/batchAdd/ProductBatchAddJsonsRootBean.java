package com.lnmj.k3cloud.entity.product.batchAdd;

import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.base.BaseData;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2019-11-10 11:56:8
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class ProductBatchAddJsonsRootBean extends BaseData {
    @JSONField(name="Model")
    private List<BatchAdd> model;
    @JSONField(name="BatchCount")
    private int BatchCount;
    @JSONField(name="NeedReturnFields")
    private String[] NeedReturnFields;


}