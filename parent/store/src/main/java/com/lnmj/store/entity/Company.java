package com.lnmj.store.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Company extends BaseEntity {
    private Long companyId;

    private String companyName;

    private String abbreviation;

    private String formerName;

    private String englishName;

    private String corporate;

    private Long firstGovernor;

    private Long secondGovernor;

    private Integer isThisStock;

    private String stock;

    private String provinceId;

    private String cityId;

    private String countyId;

    private String companyDetailedAddress;

    private String detailedAddress;

    private Long baiduLongitude;

    private Long baiduLatitude;

    private Long gaodeLongitude;

    private Long gaodeLatitude;

    private Long tencentLongitude;

    private String phoneNumber;

    private String chainCode;

    private String weChatOfficialAccount;

    private String weChatOfficialAccountName;

    private String companyWebSite;

    private String businessScope;

    private BigDecimal registeredCapital;

    private BigDecimal paidCapital;

    private String businessStatus;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registerDate;

    private String creditCode;

    private String registrationNumber;

    private String registrID;

    private String organizationCode;

    private String companyType;

    private String industry;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date approvedDate;

    private String registrationAuthority;

    private String staffNumber;

    private String termOperation;

    private String remark;

    private String dataCentre;

    private String dataCentreName;

    private String k3Number;

    private String dataCentreUserName;

    private String dataCentrePassword;

    private String yewuDataCentreUserId;

    private String yewuDataCentreUserName;

    private String yewuDataCentrePassword;

    private String zhangBuK3Number;
    @Transient
    private List<Subsidiary> subsidiaryList;
}