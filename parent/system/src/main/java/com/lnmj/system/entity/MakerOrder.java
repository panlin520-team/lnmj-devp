package com.lnmj.system.entity;


import com.lnmj.system.entity.base.BaseEntity;
import io.swagger.models.auth.In;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: yilihua
 * @Date: 2019/8/24 9:51
 * @Description: 创客订单
 */
@Data
public class MakerOrder extends BaseEntity {

  private Long makerOrderId;
  private Integer orderType;  //订单类型（创客订单）
  private String makerOrderCode;  //创客订单号
  private Long makerUserId;
  private String purchaserName;
  private String mobile;
  private Long makerProduct;  //创客商品
  private String productCode;//创客商品
  private Integer num;
  private BigDecimal totalAmount;
  private Integer payType;
  private Integer deliveryWay;
  private String receiverAddress;
  private String orderSource;
  private Integer orderStatus;

}
