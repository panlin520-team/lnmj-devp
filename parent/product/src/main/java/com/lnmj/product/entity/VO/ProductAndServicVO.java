package com.lnmj.product.entity.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 〈服务商品名称视图对象〉
 *
 * @Author renqingyun
 * @create 2019/7/4
 */

@Data
public class ProductAndServicVO {
    private String productCode;
    private String productName;
    private BigDecimal retailPrice;
    private Long subClassID;
    private Integer productType;
    private String storeIds;
    private Long unitId;
    private Integer productSubordinate;
}
