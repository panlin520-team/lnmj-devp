package com.lnmj.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class OutstoragePreAudit extends BaseEntity {
    private Long outStorageId;

    private String outStorageType;

    private String invoicesType;

    private String outStorageNumber;

    private String batchNumber;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outStorageDate;

    private String inventoryGroup;

    private String receiveGroup;

    private String inventoryWay;

    private String settlementCurrency;

    private String marketGroup;

    private String k3Number;

    private String client;

    private String clientName;

    private String receiveBranch;

    private String receive;

    private String deliveryAddress;

    private String carrier;

    private String transportNumber;

    private String shipmentGroup;

    private String shipmentBranch;

    private String stockGroup;

    private String stockPerson;

    private String businessType;

    private String shipperType;

    private String shipper;

    private Integer outStorageStatus;

    private String auditor;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditorTime;

    private String remark;

    private Long inStorageId;

    private String k3Id;

    private String k3Code;

    private String stockId;

    private String outStorageProductJson;
    private String orgK3Number;
    private Long afterAuditOutStorageId;
    private String instorageOrgName;
    private String outstorageOrgName;

    @Transient
    private String companyType;
    @Transient
    private String companyId;
    @Transient
    private Integer instorageIsComfir;
    @Transient
    private List<OutStorageProduct> outStorageProductList;
    @Transient
    private String afterAuditOutStorageNumber;
    @Transient
    private String storeId;
    @Transient
    private String invalidStatus;
    @Transient
    private String shipperCode;

    private String staffNumber;
}