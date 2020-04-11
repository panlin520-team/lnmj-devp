package com.lnmj.product.entity;

import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;

@Data
public class ProductActivityImage extends BaseEntity {

  private Long activityImageId;
  private String imageType;
  private String imageUrl;
  private Integer imageHeight;
  private Integer imageWidth;
}
