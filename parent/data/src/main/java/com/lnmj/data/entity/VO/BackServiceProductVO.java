package com.lnmj.data.entity.VO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2020/3/17
 */

@Data
public class BackServiceProductVO {
    private String productName;
    private String K3Id;
    private String K3Number;
    private Long industryId;
    private Long subClassID;
    private BigDecimal productOriginalPrice;
    private BigDecimal retailPrice;
    private BigDecimal activityRetailPrice;
    private Integer IsDiscount;
    private Integer productSales;
    private Integer unitId;
    private BigDecimal instoragePrice;
    private BigDecimal outstoragePrice;
    private BigDecimal outstorageProfit;
    private String moreContent;
}
