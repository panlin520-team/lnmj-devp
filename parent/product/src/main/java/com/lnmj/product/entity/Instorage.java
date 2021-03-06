package com.lnmj.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Instorage  extends BaseEntity {
    private Long inStorageId;

    private String inStorageType;

    private String invoicesType;

    private String businessType;

    private String batchNumber;

    private String inStorageNumber;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inStorageDate;

    private String inStorageStatus;

    private String inventoryGroup;

    private String inventoryWay;

    private String receiveGroup;

    private String receiveBranch;

    private String stockGroup;

    private String stockPerson;

    private String needGroup;

    private String purchaseGroup;

    private String purchaseBranch;

    private String purchaseLevel;

    private String purchasePerson;

    private String provider;

    private String providerName;

    private String branch;

    private String inspector;

    private String supply;

    private String supplyName;

    private String supplyLinkPerson;

    private String supplyAddress;

    private String settlement;

    private String gathering;

    private String settlementGroup;

    private String settlementCurrency;

    private String settlementType;

    private String currency;

    private String terms;

    private String exchangeRate;

    private String shipperType;

    private String shipper;

    private String auditor;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditorTime;

    private String invalid;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date invalidTime;

    private Integer invalidStatus;

    private String confirm;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;

    private Integer confirmStatus;

    private String remark;

    private String k3Id;

    private String k3Code;

    private String stockId;

    @Transient
    private List<InStorageProduct> inStorageProductList;
    @Transient
    private String inStorageProductJson;
    @Transient
    private String shipperCode;
    @Transient
    private String orgK3Number;
    @Transient
    private String direction;
    @Transient
    private String storeId;
}