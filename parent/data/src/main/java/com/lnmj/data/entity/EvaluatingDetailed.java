package com.lnmj.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.data.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EvaluatingDetailed extends BaseEntity {
    private Long evaluatingDetailedID;

    private Long evaluatingDetailedEvaluatingID;

    private BigDecimal evaluatingDetailedAmount;

    private Long evaluatingDetailedRechargeOrderID;

    private Long evaluatingDetailedOrderID;

    private Long evaluatingDetailedRelationshipID;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date evaluatingDetailedDatetime;

    private Long userId;

    private String remark;

}