package com.lnmj.product.entity;


import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;

@Data
public class ProductImage extends BaseEntity {

    private Long productImageId;
    private String imageType;
    private String imageUrl;
    private Integer imageHeight;
    private Integer imageWidth;
}
