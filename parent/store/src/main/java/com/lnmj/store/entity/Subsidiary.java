package com.lnmj.store.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.store.entity.VO.StoreVO;
import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Subsidiary extends BaseEntity {
    private Long subsidiaryId;

    private String subsidiaryName;

    private String abbreviation;

    private String formerName;

    private String englishName;

    private Long parentCompany;

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

    private String bankNumber;

    private String cashNumber;

    private String k3Id;

    private String k3Number;

    private String systemK3Number;

    private String heSuanFanWeiK3Number;

    private String zhangBuK3Number;

    private String dataCentreUserName;

    private String dataCentrePassword;

    @Transient
    private String parentCompanyName;
    @Transient
    private List<StoreVO> storeVOList;
    @Transient
    private Long orgId;
    @Transient
    private String orgK3Number;
    @Transient
    private String orgName;
    @Transient
    private Long supplierId;
    @Transient
    private String supplierK3Id;
    @Transient
    private Long customerId;
    @Transient
    private String customerK3Id;
}