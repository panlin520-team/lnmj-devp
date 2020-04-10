package com.lnmj.k3cloud.entity.supplier.supplier.batchAdd;

import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.base.BaseData;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2019-11-10 18:5:43
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class SupplierBatchAddJsonsRootBean extends BaseData {
    @JSONField(name="Model")
    private List<SupplierBatchAdd> model;


}