package com.lnmj.product.entity.VO;

import com.lnmj.product.entity.ProductActivityImage;
import com.lnmj.product.entity.ProductImage;
import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class AllProductVO extends BaseEntity {
    public AllProductVO() {
    }

    private BigDecimal retailPrice;
    private String productCode;
    private String productName;
    private Integer stockNum;
    private Integer productType;
    private BigDecimal instoragePrice;
    private BigDecimal outstoragePrice;
    private Long unitId;
    private String productSpecification;
    private Long subClassId;
    private BigDecimal outstorageProfit;
    private String productSubordinate;

}