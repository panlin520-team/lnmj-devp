package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;

@Data
public class Supplier extends BaseEntity {
    private Long supplierId;

    private String supplierCode;

    private String supplierName;

    private String shortName;

    private Long supplierCategoryId;

    private String bankDeposit;

    private String creditCardNum;

    private String linkMan;

    private String linkPhone;

    private String address;

    private String dataCentre;

    private String k3Id;

    private String k3Number;

    private String dataCentreUserName;

    private String dataCentrePassword;

    private Integer supplierType;
    private Integer relationSubCompanyType;
    private Long relationSubCompanyId;
    private Long zhongCompanyId;
    @Transient
    private String supplierCategoryName;
}