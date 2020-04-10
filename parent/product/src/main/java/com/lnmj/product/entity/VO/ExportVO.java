package com.lnmj.product.entity.VO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 〈导出对象〉
 *
 * @Author renqingyun
 * @create 2019/5/28
 */
@Data
public class ExportVO {
    private String ProductCode;
    private String ProductName;
    private String ProductKindName;
    private String ProductSpecification;
    private String NetContent;
    private BigDecimal ProductOriginalPrice;
    private BigDecimal RetailPrice;
    private String ProductDetails;
}
