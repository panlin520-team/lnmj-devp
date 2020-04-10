package com.lnmj.store.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CustomMadeOrderDetail extends BaseEntity {
    private Long customMadeOrderDetailId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderNumber;

    private Long serviceProductId;

    private String projectName;

    private Integer customMadeCount;

    private Integer userCount;

    private BigDecimal price;

    private BigDecimal originalPrice;

    private Integer integral;

    private BigDecimal manualCost;

    private String validitytime;

    private BigDecimal royaltyAmount;

    private String attribute;

    private Integer customMadeStatus;

}