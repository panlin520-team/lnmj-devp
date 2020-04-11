package com.lnmj.product.entity.VO;

import com.lnmj.product.entity.ProductImage;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductRoughVO {
    //商品code
    private String productCode;
    private String productName;
    //商品种类名称
    private String productKindName;
    //商品功效名称
    private String productEffectName;
    //商品规格
    private String productSpecification;
    //净重量
    private String netContent;
    //图片url
    private List<ProductImage> ProductImageList;
    //原价
    private BigDecimal productOriginalPrice;
    //零售价
    private BigDecimal RetailPrice;
    //折扣
    private Integer ProductSales;
}