package com.lnmj.product.entity;


import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;

@Data
public class ProductPromotionType extends BaseEntity {
    private Long productPromotionTypeId;
    private String promotionName;
}
