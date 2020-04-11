package com.lnmj.product.entity;

import com.lnmj.product.entity.base.BaseEntity;
import io.swagger.models.auth.In;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class StockProduct extends BaseEntity {
    private Long stockProductId;

    private String stock;

    private String stockPlace;

    private String stockStatus;

    private String batchNumber;

    private String productType;

    private String productCode;

    private String productName;

    private String secondaryAttribute;

    private String unit;

    private Integer number;

    private Integer aveailableNumber;

    private String inventoryGroup;

    private String shipperType;

    private String shipperCode;

    private String shipper;

    private String keeperType;

    private String keeperCode;

    private String keeper;

    private String remark;


    private Integer aveailableNumberSum;
}