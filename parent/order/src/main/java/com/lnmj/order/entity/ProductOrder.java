package com.lnmj.order.entity;


import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnmj.order.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProductOrder extends BaseEntity {

    private String orderId;
    @JsonSerialize(using = ToStringSerializer.class)
    private String orderNumber;
    private String productCode;
    @JsonSerialize(using = ToStringSerializer.class)
    private String productName;
    private Integer productNum;
    private BigDecimal originalPrice;
    private Double discount;
    private BigDecimal discountPrice;
    private String productSpec;
    private String productSource;
    private String deliveryNumber;
    private String deliveryOrganization;
    private Integer deliveryWay;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bookingTime;
    private String bookingBeauticianIds;
    private String performanceRatio;
    private Integer userStatus;
    private Integer userPayStatus;
    private Integer platformStatus;
    private Integer platformPayStatus;
    private Integer proxyStatus;
    private Integer proxyPayStatus;
    private Integer supplierStatus;
    private Integer supplierPayStatus;
    private String orderSource;
    private String payTypeAndAmount;
    private String receivingNetwork;
    private Long productTypeId;
    private String productTypeName;
    private Long productBrandId;
    private String productBrandName;
    private Long productCategoryId;
    private String productCategoryName;
    private Long storeId;
    private String storeName;
    private Long supplierId;
    private String supplierName;
    private String useLimit;
    private String recordId;
    private String k3Number;
    @Transient
    private Double price;
    private Long subclassID;
    @Transient
    private JSONArray bookingBeauticianIdsArray;

}
