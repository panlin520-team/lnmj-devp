package com.lnmj.order.entity;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnmj.order.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;


import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
public class Order extends BaseEntity {
  @JsonSerialize(using = ToStringSerializer.class)
  private String orderNumber;
  private Integer orderType;
  private Integer channel;
  @NotNull(message = "订单联系人不能为空")
  private String orderLink;
  @NotNull(message = "订单联系人电话不能为空")
  private String mobile;
  @NotNull(message = "会员卡号不能为空")
  private String cardNumber;
  private BigDecimal totalPrice;
  private BigDecimal totalDiscount;
  private BigDecimal payPrice;
  private Integer deliveryWay;
  private String orderSource;
  private String payTypeAndAmount;
  private Long NurseStore;
  private String receivingNetwork;
  private Long bookingUserId;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date bookingTime;
  private Integer orderStatus;
  private Integer userStatus;
  private Integer userPayStatus;
  private Integer platformStatus;
  private Integer platformPayStatus;
  private Integer proxyStatus;
  private Integer proxyPayStatus;
  private Integer supplierStatus;
  private Integer supplierPayStatus;
  private String remark;
  private String receiverAddress;
  private String deleteReason;
  private String memoNum;
  private String recordId;
  private Integer auditAmountStatus;
  private String outStorageIdQiTa;
  private String outStorageIdXiaoShou;
  @Transient
  private String storeName;
  @Transient
  private String bookingBeauticianIds;
  @Transient
  private String bookingBeauticianName;
  @Transient
  private String orderTypeName;
  @Transient
  private List<ProductOrder> productOrderList;
  @Transient
  private AppointmentOrder appointmentOrder;
  @Transient
  private JSONArray payTypeAndAmountArray;
}
