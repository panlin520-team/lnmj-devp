package com.lnmj.product.entity.VO;

import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author: yilihua
 * @Date: 2019/5/9 10:22
 * @Description:
 */
@Data
public class ProductTypeDisplayVO extends BaseEntity {
    private Long productTypeDisplayId;     // "分类显示Id"
    private String productTypeDisplayName; // "分类显示名"
    private String productTypeDisplayDesc;// "分类显示描述"
}
