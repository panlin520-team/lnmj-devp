package com.lnmj.product.entity;

import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;
@Data
public class ProducStore extends BaseEntity {
    private String productCode;

    private Long storeId;

    private Integer upOrDown;


}