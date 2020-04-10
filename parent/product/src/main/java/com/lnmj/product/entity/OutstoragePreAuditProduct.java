package com.lnmj.product.entity;

import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class OutstoragePreAuditProduct extends BaseEntity {
    private Long id;

    private Long outstoragePreAuditId;

    private BigDecimal unitPrice;

    private String unit;

    private String productCode;

    private BigDecimal totalPrice;

    private String specification;

    private String stockStatus;

    private Integer sendNumber;

    private String stock;

    private Integer sendableNumber;

    private String productName;

    private String productType;

    private String batchNumber;

}