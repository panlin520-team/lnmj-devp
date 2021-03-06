package com.lnmj.data.entity.VO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BackProductVO {

    private String productName;
    private String K3Id;
    private String K3Number;
    private String productCode;
    private String productSubordinate;
    //所属大类
    private Long CommodityTypeID;
    //所属小类
    private Long subClassID;
    private Long achievementId;
    //禁止购买
    private String barredBuying;
    //禁止支付方式
    private String barredPayMethod;
    private Long provinceId;
    private Long cityId;
    private Long countyId;
    private Long productKind;
    private Long productEffect;
    private Long productBrand;
    private Long productCategory;
    private String productSpecification;
    private BigDecimal productOriginalPrice;
    private BigDecimal retailPrice;
    private BigDecimal activityRetailPrice;
    private Integer IsDiscount;
    //    净含量
    private String netContent;
    //    @NotNull(message = "商品销量不能为空")
    private Integer productSales;
    private Integer unitId;
    private BigDecimal instoragePrice;
    private BigDecimal outstoragePrice;
    private BigDecimal outstorageProfit;
    //    @NotNull(message = "商品简介不能为空")
    private String productIntroduce;
    //    @NotNull(message = "商品更多信息不能为空")
    private String moreContent;
}
