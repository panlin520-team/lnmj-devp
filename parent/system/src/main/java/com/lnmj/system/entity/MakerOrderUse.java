package com.lnmj.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.system.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: yilihua
 * @Date: 2019/8/24 9:51
 * @Description: 创客订单使用
 */
@Data
public class MakerOrderUse extends BaseEntity {

  private Long makerProductUseId;
  private String makerOrderCode;    //创客订单号
  private Long makerUserId;
  private String mobile;
  private Long product;         //商品（项目id）
  private String productCode;   //创客商品code
  private String receiverAddress;
  private Integer deliveryWay;
  private String deliveryNumber;
  private String deliveryOrganization;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date bookingTime;
  private String bookingBeauticianIds;
  private String bookingBeauticianName;
  private Long storeId;
  private String storeName;
  private String performanceRatio;
  private Integer productUseStatus;

}
