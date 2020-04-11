package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class ExperiencecardProduct extends BaseEntity {
    private Long cardProductId;

    private String cardNum;

    private String productCode;

    private String productKind;

    private String productName;

    private String useLimit;

    private Integer useTotalTimes;

    private Integer efficientCondition;

    private Date efficientDate;

    private String link;

    private Long totalSales;

    private Long totalCommission;

    private String forwardTitle;

    private String forwardDescribe;

    private String logoImge;

    private Double outStockPrice;


}