package com.lnmj.product.entity;


import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductPromotion extends BaseEntity {

  private Long productPromotionId;
  private Long promotionId;
  private Long promotionNumber;
  private BigDecimal promotionPrict;
  private String promotionDate;

}
