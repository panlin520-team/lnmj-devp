package com.lnmj.product.entity;


import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;

@Data
public class ProductSubordinateprovince extends BaseEntity {

    private long subordinateId;
    private String subordinateName;

}
