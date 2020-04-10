package com.lnmj.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.data.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderTicheng  extends BaseEntity {
    private Long ID;

    private String orderNumber;

    private String product;

    private Integer number;

    private BigDecimal productCommission;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date statisticDate;

    private Long storeId;

    private Long salesmanID;

    private BigDecimal takePercentage;

    private Boolean salesmanConfirm;

    private Boolean managementConfirm;

}