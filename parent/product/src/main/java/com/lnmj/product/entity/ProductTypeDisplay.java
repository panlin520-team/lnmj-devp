package com.lnmj.product.entity;

import com.alibaba.druid.support.monitor.annotation.MTable;
import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:10
 * @Description: 商品显示表
 */
@Data
@MTable(name = "p_producttypedisplay")
public class ProductTypeDisplay extends BaseEntity {
    private Long productTypeDisplayId;     // "分类显示Id"
    private String productTypeDisplayName; // "分类显示名"
    private String productTypeDisplayDesc;// "分类显示描述"
}
