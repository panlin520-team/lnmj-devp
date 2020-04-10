package com.lnmj.data.entity;

import com.lnmj.data.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;


@Data
public class Basesalary extends BaseEntity {
    private Long baseSalaryScoreID;

    private String baseSalaryName;

    private Long baseSalaryIndustryID;

    private Long baseSalaryStoreId;

    private Long baseSalaryPostID;

    private BigDecimal baseSalaryLow;

    private BigDecimal baseSalaryAmount;

    @Transient
    private String IndustryName;
    @Transient
    private String StoreName;
    @Transient
    private String PostName;
}