package com.lnmj.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OutStorageProduct extends BaseEntity {
    private Long outStorageProductID;

    private Long outStorageId;

    private String outStorageNumber;

    private String batchNumber;

    private String productType;

    private String productCode;

    private String productName;

    private String barCode;

    private String specification;

    private String secondaryAttribute;

    private String shipperType;

    private String shipper;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date productionDate;

    private String expiration;

    private String expirationUnit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    private String unit;

    private Integer sendableNumber;

    private Integer sendNumber;

    private BigDecimal unitPrice;

    private BigDecimal taxIncludedUnitPrice;

    private String assistUnit;

    private Integer sendNumberAssistUnit;

    private BigDecimal totalPrice;

    private String stock;

    private String stockPlace;

    private String stockStatus;

    private String keeperType;

    private String keeper;

    private BigDecimal discountPercent;

    private String planTrackingNumber;

    private String productGroup;

    private String sourceInvoicesType;

    private String sourceInvoices;

    private String remark;

    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outStoregeDate;
    @Transient
    private String unitName;
    @Transient
    private String unitK3Number;
    @Transient
    private String productK3Number;


    @Transient
    private String fRealQty;
    @Transient
    private String fOwnerId;
    @Transient
    private String fStockId;
    @Transient
    private String fSalUnitID;
    @Transient
    private String fSalUnitQty;
    @Transient
    private String fSalBaseQty;
    @Transient
    private String fPriceBaseQty;
    @Transient
    private String fARNOTJOINQTY;


}