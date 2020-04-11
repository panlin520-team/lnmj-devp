package com.lnmj.product.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
public class ServiceProduct extends BaseEntity {
    private Long serviceProductId;

    private Long subClassID;

    private String productCode;

    private String productName;

    private Integer Frequency;

    private BigDecimal Discount;

    private String forwardedDescribe;

    private String postType;

    private Long productPromotionType;

    private String barredBuying;

    private String barredPayMethod;

    private Integer codeBuy;

    private Long unitId;

    private Integer isExpansionGuest;

    private String imageList;

    private String activityImageList;

    private Integer isCreateGuest;

    private Integer isPurchase;

    private Integer purchaseQuantity;

    private Integer isDiscount;
    private Long industryId;
    @Transient
    private String industryName;
    private Long CommodityTypeID;
    private Integer isHomePage;

    private Integer isExperienceCart;

    private Integer isOpenRoyalty;

    private BigDecimal oneLevelRoyalty;

    private BigDecimal twoLevelRoyalty;

    private BigDecimal threeLevelRoyalty;

    private Integer experienceProjectQuantity;

    private BigDecimal superRoyalty;

    private BigDecimal cosmetologistRoyalty;

    private Integer isGiveProduct;

    private Integer isPhotographCashProduct;

    private Integer isVirtualProduct;

    private String buyRemind;

    private Long provinceId;

    private Long cityId;

    private Long countyId;

    private Long productKind;

    private Long productEffect;

    private Long productBrand;

    private Long productCategory;

//    private Long merchantSource;

    private String duration;//护理时长

    private Long nurseProductBuyMeans;

    private BigDecimal commissionSettlement;

    private BigDecimal productOriginalPrice;

    private BigDecimal retailPrice;

    private BigDecimal activityRetailPrice;

    private Integer isSeIntegralByProduct;

    private BigDecimal facilitatorPrice;

    private Integer facilitatorProportionIntegral;

    private BigDecimal unionPrice;

    private Integer unionProportionIntegral;

    private BigDecimal retailPriceVip1;

    private Integer proportionIntegralVip1;

    private BigDecimal retailPriceVip2;

    private Integer proportionIntegralVip2;

    private BigDecimal retailPriceVip3;

    private Integer proportionIntegralVip3;

    private BigDecimal retailPriceVip4;

    private Integer proportionIntegralVip4;

    private BigDecimal retailPriceVip5;

    private Integer proportionIntegralVip5;

    private BigDecimal retailPriceVip6;

    private Integer proportionIntegralVip6;

    private Integer productSales;

    private String productIntroduce;

    private String productDetails;

    private String moreContent;

    private Integer onOffSale;
    private Integer productStatus;
    private BigDecimal instoragePrice;
    private BigDecimal outstorageProfit;
    private BigDecimal outstoragePrice;
    //所属总公司
    private String companyId;
    //授权子公司
    private String subsidiaryId;
    //授权门店
    private String storeIds;
    private String newCreateCompanyId;
    private String newCreateCompanyType;

    private String k3Id;

    private String k3Number;


    //图片url
    @Transient
    private List<ProductImage> serviceProductImageList;
    @Transient
    private List<ProductActivityImage> serviceProductActivityImageList;
    @Transient
    private Integer isChecked=0;
    @Transient
    private Integer stockNum=0;
    @Transient
    private Integer productType;

    @Transient
    @JsonProperty("LAY_CHECKED")
    private String LAY_CHECKED;


    @Transient
    private String productKindName;
    @Transient
    private String productBrandName;
    @Transient
    private String productEffectName;
    @Transient
    private String productCategoryName;
    @Transient
    private String productIndustryName;
    @Transient
    private String productCommoditytypeName;
    @Transient
    private String productSubclassName;
    @Transient
    private String productUnitName;


}