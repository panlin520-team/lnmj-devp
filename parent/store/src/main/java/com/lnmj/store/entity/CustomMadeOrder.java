package com.lnmj.store.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class CustomMadeOrder extends BaseEntity {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderNumber;

    private Integer orderType;

    private String orderLink;

    private String mobile;

    private String cardNumber;

    private BigDecimal totalPrice;

    private BigDecimal payPrice;

    private String orderSource;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    private Integer payType;

    private Integer orderStatus;

    private List<CustomMadeOrderDetail> customMadeOrderDetailList;

}