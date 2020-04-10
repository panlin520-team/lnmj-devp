package com.lnmj.product.entity.VO;

import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ServiceProductVO {
    private Long serviceProductId;
    private Long subClassID;
    private String productCode;
    //@NotNull(message = "商品名称不能为空")
    private String productName;
    //转发描述
    private String forwardedDescribe;
    private String[] postType;
    //@NotNull(message = "商品促销不能为空")
    private Long productPromotionType;
    //禁止购买
    private String[] barredBuying;
    //禁止支付方式
    private String[] barredPayMethod;
    //@Max(value = 1,message = "验证码超出了上限选择")
    //@Min(value = 0,message = "验证码低于了下限选择")
    private Integer codeBuy;
    //@Max(value = 1,message = "是否拓客超出了上限选择")
    //@Min(value = 0,message = "是否拓客低于了下限选择")
    private Integer isExpansionGuest;
    private Long unitId;
    //服务图片集合
    private String imageList;
    //活动服务图片集合
    private String activityImageList;
    //@Max(value = 1,message = "是否创客超出了上限选择")
    //@Min(value = 0,message = "是否创客低于了下限选择")
    private Integer isCreateGuest;
    //@Max(value = 1,message = "是否限购超出了上限选择")
    //@Min(value = 0,message = "是否限购低于了下限选择")
    private Integer isPurchase;
    //@Min(value = 0, message = "限购数量有误")
    private Integer purchaseQuantity;
    //@Max(value = 1,message = "是否显示折扣超出了上限选择")
    //@Min(value = 0,message = "是否显示折扣低于了下限选择")
    private Integer isDiscount;
    private Long industryId;
    private Long CommodityTypeID;
    //@Max(value = 1,message = "是否首页显示超出了上限选择")
    //@Min(value = 0,message = "是否首页显示低于了下限选择")
    private Integer isHomePage;
    //@Max(value = 1,message = "是否为体验卡超出了上限选择")
    //@Min(value = 0,message = "是否为体验卡低于了下限选择")
    private Integer isExperienceCart;

    private Integer isOpenRoyalty;
    //一级提成
    private BigDecimal oneLevelRoyalty;
    //二级提成
    private BigDecimal twoLevelRoyalty;
    //三级提成
    private BigDecimal threeLevelRoyalty;
    //体验项目限制使用个数
    private Integer experienceProjectQuantity;
    //定制上级提成
    private BigDecimal superRoyalty;
    //定制美容师提成
    private BigDecimal cosmetologistRoyalty;
    //@Max(value = 1,message = "是否为赠送商品超出了上限选择")
    //@Min(value = 0,message = "是否为赠送商品低于了下限选择")
    private Integer isGiveProduct;
    //@Max(value = 1,message = "是否为拍照返现商品超出了上限选择")
    //@Min(value = 0,message = "是否为拍照返现商品低于了下限选择")
    private Integer isPhotographCashProduct;
    //@Max(value = 1,message = "是否为虚拟商品超出了上限选择")
    //@Min(value = 0,message = "是否为虚拟商品低于了下限选择")
    private Integer isVirtualProduct;
    //购买提醒
    private String buyRemind;
    //@NotNull(message = "所属省不能为空")
    private Long provinceId;
    //@NotNull(message = "所属市不能为空")
    private Long cityId;
    //@NotNull(message = "所属县不能为空")
    private Long countyId;
    //@NotNull(message = "商品种类不能为空")
    private Long productKind;
    //@NotNull(message = "商品功效不能为空")
    private Long productEffect;
    //@NotNull(message = "商品品牌不能为空")
    private Long productBrand;
    //@NotNull(message = "商品品类不能为空")
    private Long productCategory;
//    //@NotNull(message = "护理时长不能为空")
    private String duration;//护理时长
    //护理购买方式
    private Long nurseProductBuyMeans;
    //提成结算金额
    private BigDecimal commissionSettlement;
//    //@NotNull(message = "商品原价不能为空")
    private BigDecimal productOriginalPrice;
//    //@NotNull(message = "商品零售价不能为空")
    private BigDecimal retailPrice;
    // 商品活动价格不能为空
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
    private String companyId;
//    private Integer subsidiaryId;
    private String subsidiaryId;
    //公司类型
    @Transient
    private Long companyType;
    private String status;

    private String createOperator;

    private Date createTime;

    private String modifyOperator;

    private Date dataChangeLastTime;
    private String storeIds;
    //文件处理
    private MultipartFile[] serviceProductFile;
    private MultipartFile[] activityServiceProductFile;
    //@Transient
    private String fCreateOrgId;
    //@Transient
    private String fUseOrgId;
    //@Transient
    private String fbarcodeentityCmkListStr;
    //@Transient
    private String fErpClsID;
    //@Transient
    private String fBaseUnitId;
    //@Transient
    private String fCategoryID;
    //@Transient
    private String fSuite;
    //@Transient
    private String fStoreUnitID;
    //@Transient
    private String fCurrencyId;
    //@Transient
    private String fUnitConvertDir;
    //@Transient
    private String fSNGenerateTime;
    //@Transient
    private String fSNManageType;
    //@Transient
    private String fSalePriceUnitId;
    //@Transient
    private String fSaleUnitId;
    //@Transient
    private String fPurchaseUnitId;
    //@Transient
    private String fPurchasePriceUnitId;
    //@Transient
    private String fQuotaType;
    //@Transient
    private String fPlanningStrategy;
    //@Transient
    private String fOrderPolicy;
    //@Transient
    private String fFixLeadTimeType;
    //@Transient
    private String fVarLeadTimeType;
    //@Transient
    private String fCheckLeadTimeType;
    //@Transient
    private String fOrderIntervalTimeType;
    //@Transient
    private String fReserveType;
    //@Transient
    private String fPlanOffsetTimeType;
    //@Transient
    private String fIssueType;
    //@Transient
    private String fOverControlMode;
    //@Transient
    private String fMinIssueUnitId;
    //@Transient
    private String fStandHourUnitId;
    //@Transient
    private String fBackFlushType;
    //@Transient
    private String fEntityInvPtyListStr;


}