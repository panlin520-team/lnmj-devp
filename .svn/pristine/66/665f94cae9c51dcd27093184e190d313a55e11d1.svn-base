package com.lnmj.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Stock extends BaseEntity {

    private Long stockId;

    private String id;

    private String stockCode;

    private Long companyType;

    private Long companyId;

    private String company;

    private String responsiblePerson;

    private String stockType;

    private Boolean allowableNegativeInventory;

    private Boolean isUse;

    private Boolean isStockPlace;

    private BigDecimal integralPercent;

    private String provinceId;

    private String cityId;

    private String countyId;

    private String stockAddress;

    private String phone;

    private String dataStatus;

    private String auditor;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditorTime;

    private String remark;

    @Transient
    private String k3Number;

}