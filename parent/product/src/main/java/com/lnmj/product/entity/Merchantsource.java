package com.lnmj.product.entity;

import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;

@Data
public class Merchantsource extends BaseEntity {

    private Long merchantSourceId;
    private String merchantSourceName;
}