package com.lnmj.product.entity;

import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class Coupons extends BaseEntity {
    private Long couponsId;

    private String couponsName;

    private String couponsSubheading;

    private Long couponsTypeId;

    private BigDecimal fullAmount;

    private BigDecimal reductionAmount;

    private Integer provideAmount;

    private Integer beGetAmount;

    private String instructions;

    private Integer userType;

    private Integer oneLimit;

    private String effectiveDate;

    private Integer effectiveDay;

    private String jumpLink;

    private Integer soldUpDown;

    private Double discount;

    private Double immediatelyDiscount;

    @Transient
    private Integer chooseEffectiveType;
}