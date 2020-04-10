package com.lnmj.wallet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.wallet.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class CashBack extends BaseEntity {
    private Long cashBackId;

    private String imageUrl;

    private Integer imageHeight;

    private Integer imageWidth;

    private String productName;

    private String orderNumber;

    private String name;

    private String mobile;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date photographTime;

    private Long cashBackType;

    private BigDecimal cashbackAmount;

    private Integer auditsCount;

    private Integer auditsStatus;

}