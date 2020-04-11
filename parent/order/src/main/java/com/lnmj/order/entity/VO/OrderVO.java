package com.lnmj.order.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnmj.common.Enum.base.BaseEnum;
import com.lnmj.order.entity.ProductOrder;
import com.lnmj.order.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
public class OrderVO  extends BaseEntity {

  @JsonSerialize(using = ToStringSerializer.class)
  private String orderNumber;
  private String orderLink;
  private String mobile;
  private String cardNumber;
  private String payTypeAndAmount;
  private Integer orderType;
  private BigDecimal payPrice;
  private BigDecimal totalPrice;
  private Integer orderStatus;
  private String recordId;
  private Integer auditAmountStatus;
  private String outStorageIdQiTa;
  private String outStorageIdXiaoShou;
  private Long nurseStore;

  @Transient
  private String orderStatueValue;

  @Transient
  private String orderTypeValue;
  @Transient
  private String payTypeAndAmountStr;
  @Transient
  private List<ProductOrder> productOrderList;
}
