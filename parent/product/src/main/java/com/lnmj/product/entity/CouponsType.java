package com.lnmj.product.entity;

import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;

import java.util.Date;
@Data
public class CouponsType extends BaseEntity {
    private Long couponsTypeId;

    private String couponsTypeName;


}