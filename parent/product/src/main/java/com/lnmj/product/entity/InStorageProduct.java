package com.lnmj.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InStorageProduct extends BaseEntity {
    private Long inStorageProductID;

    private Long inStorageId;

    private String inStorageNumber;

    private String productType;

    private String productCode;

    private String productName;

    private String barCode;

    private String specification;

    private String secondaryAttribute;

    private String batchNumber;

    private String providerBatchNumber;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date productionDate;

    private String expiration;

    private String expirationUnit;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    private Date inShelfDate;

    private Integer receivableNumber;

    private Integer receivedNumber;

    private String unit;

    private BigDecimal unitPrice;

    private BigDecimal discountPrice;

    private BigDecimal totalPrice;

    private String stock;

    private String stockPlace;

    private String stockStatus;

    private String keeperType;

    private String keeper;

    private Boolean isSend;

    private BigDecimal discountPercent;

    private String assistUnit;

    private Integer receivedNumberAssistUnit;

    private String demandTrackingNumber;

    private String planTrackingNumber;

    private String sourceInvoicesType;

    private String sourceInvoices;

    private String orderNumber;

    private Integer returnNumber;

    private Integer demageNumber;

    private String businessProcess;

    private String remark;

    @Transient
    private String keeperCode;
    @Transient
    private String unitName;
    @Transient
    private String k3Number;
}