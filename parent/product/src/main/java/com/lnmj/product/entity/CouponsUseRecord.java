package com.lnmj.product.entity;

import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;
@Data
public class CouponsUseRecord extends BaseEntity {
    private Long couponsUseRecordId;

    private Long couponsGetRecordId;

    private Long couponsTypeId;

    private String orderNumber;

    private String memberNum;

    private Double discountAmount;


}