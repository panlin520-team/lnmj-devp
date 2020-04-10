package com.lnmj.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class StockStatus extends BaseEntity {
    private Long stockStatusID;

    private String code;

    private String id;

    private String type;

    private String name;

    private Boolean isUse;

    private Boolean isMarket;

    private Boolean isReceive;

    private Boolean isLock;

    private Boolean isMRP;

    private String dataStatus;

    private String auditor;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditorTime;

    private String remark;

}