package com.lnmj.product.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductVO extends BaseEntity {
    private Long productId;
    private Long CommodityTypeID;
    private Long subClassID;
    private BigDecimal bonus;
    private String productCode;
    ////@NotNull(message = "商品名称不能为空")
    private String productName;
    ////@Max(value = 1,message = "商品所属超出了上限选择")
    ////@Min(value = 0,message = "商品所属低于了下限选择")
    private String productSubordinate;
    ////@Min(value = 0, message = "请输入正确的商品库存")
    private Integer stockQuantity;
    //单位id
    private Long unitId;
    //单位名称
    @Transient
    private String unitName;
    //转发描述
    private String forwardedDescribe;
    ////@NotNull(message = "商品pos类型不能为空")
    ////@NotNull(message = "商品促销不能为空")
    private Long productPromotionType;
    ////@Max(value = 4,message = "配送方式超出了上限选择")
    ////@Min(value = 0,message = "配送方式低于了下限选择")
    private String deliveryMethod;
    ////@NotNull(message = "到店门店不能为空")
    private Long storeName;
    //禁止购买
    private String[] barredBuying;
    //禁止支付方式
    private String[] barredPayMethod;
    ////@Max(value = 1,message = "验证码超出了上限选择")
    ////@Min(value = 0,message = "验证码低于了下限选择")
    private Integer codeBuy;
    ////@Max(value = 1,message = "是否拓客超出了上限选择")
    ////@Min(value = 0,message = "是否拓客低于了下限选择")
    private Integer isExpansionGuest;
    //图片集合
    private String imageList;
    //活动图片集合
    private String activityImageList;
    //商品图片文件
    private MultipartFile[] productFile;
    //活动商品图片
    private MultipartFile[] activityProductFile;
    ////@Max(value = 1,message = "是否创客超出了上限选择")
    ////@Min(value = 0,message = "是否创客低于了下限选择")
    private Integer isCreateGuest;
    ////@Max(value = 1,message = "是否限购超出了上限选择")
    ////@Min(value = 0,message = "是否限购低于了下限选择")
    private Integer isPurchase;
    ////@Min(value = 0, message = "商品限购数量不能为空")
    private Integer purchaseQuantity;
    ////@Max(value = 1,message = "是否显示折扣超出了上限选择")
    ////@Min(value = 0,message = "是否显示折扣低于了下限选择")
    private Integer isDiscount;
    ////@Max(value = 1,message = "是否首页显示超出了上限选择")
    ////@Min(value = 0,message = "是否首页显示低于了下限选择")
    private Integer isHomePage;
    ////@Max(value = 1,message = "固定金额提成是否开启超出了上限选择")
    ////@Min(value = 0,message = "固定金额提成是否开启低于了下限选择")
    private Integer isOpenRoyalty;
    //一级提成
    private BigDecimal oneLevelRoyalty;
    //二级提成
    private BigDecimal twoLevelRoyalty;
    //三级提成
    private BigDecimal threeLevelRoyalty;
//    ////@Min(value = 0, message = "体验项目限制使用数有误")
//    private Integer experienceProjectQuantity;
    //赠送金额
    private BigDecimal giveAmount;
    //定制上级提成
    private BigDecimal superRoyalty;
    //定制美容师提成
    ////@Max(value = 1,message = "是否为赠送商品超出了上限选择")
    ////@Min(value = 0,message = "是否为赠送商品低于了下限选择")
    private Integer isGiveProduct;
    ////@Max(value = 1,message = "是否为拍照返现商品超出了上限选择")
    ////@Min(value = 0,message = "是否为拍照返现商品低于了下限选择")
    private Integer isPhotographCashProduct;
    ////@Max(value = 1,message = "是否为海外购超出了上限选择")
    ////@Min(value = 0,message = "是否为海外购低于了下限选择")
    private Integer isOverseasBuy;
    private Long overseasSite;
    ////@Max(value = 1,message = "是否为虚拟商品超出了上限选择")
    ////@Min(value = 0,message = "是否为虚拟商品低于了下限选择")
    private Integer isVirtualProduct;
    //购买提醒
    private String buyRemind;
    ////@NotNull(message = "所属省不能为空")
    private Long provinceId;
    ////@NotNull(message = "所属市不能为空")
    private Long cityId;
    ////@NotNull(message = "所属县不能为空")
    private Long countyId;
    ////@NotNull(message = "商品种类不能为空")
    private Long productKind;
    ////@NotNull(message = "商品功效不能为空")
    private Long productEffect;
    ////@NotNull(message = "商品品牌不能为空")
    private String productBrand;
    ////@NotNull(message = "商品品类不能为空")
    private Long productCategory;
//    ////@NotNull(message = "商品来源商家不能为空")
    private Long merchantSource;
    private String productSpecification;
    //提成结算金额不能为空
    private BigDecimal commissionSettlement;
    ////@NotNull(message = "商品原价不能为空")
    private BigDecimal productOriginalPrice;
    ////@NotNull(message = "商品零售价不能为空")
    private BigDecimal retailPrice;
    // 商品活动价格不能为空
    private BigDecimal activityRetailPrice;
    ////@Max(value = 1,message = "是否按商品设置积分超出了上限选择")
    ////@Min(value = 0,message = "是否按商品设置积分低于了下限选择")
    private Integer isSeIntegralByProduct;
    ////@NotNull(message = "服务商零售价格不能为空")
    private BigDecimal facilitatorPrice;
    ////@NotNull(message = "服务商积分比例不能为空")
    private Integer facilitatorProportionIntegral;
    ////@NotNull(message = "联盟商零售价格不能为空")
    private BigDecimal unionPrice;
    //@NotNull(message = "联盟商积分比例不能为空")
    private Integer unionProportionIntegral;
    //@NotNull(message = "VIP1零售价格不能为空")
    private BigDecimal retailPriceVip1;
    //@NotNull(message = "VIP1积分比例不能为空")
    private Integer proportionIntegralVip1;
    //@NotNull(message = "VIP2零售价格不能为空")
    private BigDecimal retailPriceVip2;
    //@NotNull(message = "VIP2积分比例不能为空")
    private Integer proportionIntegralVip2;
    //@NotNull(message = "VIP3零售价格不能为空")
    private BigDecimal retailPriceVip3;
    //@NotNull(message = "VIP3积分比例不能为空")
    private Integer proportionIntegralVip3;
    //@NotNull(message = "VIP4零售价格不能为空")
    private BigDecimal retailPriceVip4;
    //@NotNull(message = "VIP4积分比例不能为空")
    private Integer proportionIntegralVip4;
    //@NotNull(message = "VIP5零售价格不能为空")
    private BigDecimal retailPriceVip5;
    //@NotNull(message = "VIP5积分比例不能为空")
    private Integer proportionIntegralVip5;
    private Integer stockDiscount;
    //活动进货折扣
    private Integer activityStockDiscount;
    private Long industryId;
    private Long achievementPostId;
    private Long achievementId;
    //    净含量
    private String netContent;
    private Integer productSales;
    private String productIntroduce;
    private String productDetails;
    private String moreContent;
    private BigDecimal instoragePrice;
    private BigDecimal outstorageProfit;
    private BigDecimal outstoragePrice;
    private String companyId;
    private String subsidiaryId;
    private String storeIds;
    //公司类型
    @Transient
    private Long companyType;


}