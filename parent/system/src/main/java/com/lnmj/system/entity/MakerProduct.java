package com.lnmj.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.system.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: yilihua
 * @Date: 2019/8/24 9:51
 * @Description: 创客商品
 */
@Data
public class MakerProduct extends BaseEntity{

  private Long makerProductId;
  private Integer makerType;
  private String makerProduct;  //商品名
  private String productCode;   //商品编号
  private BigDecimal originalPrice;
  private BigDecimal retailPrice;
  private BigDecimal discount;
  private String integral;
  private String imageType;
  private String imageUrl;
  private Integer imageHeight;
  private Integer imageWidth;
  private String barredPayMethod;
  private Long storeId;
  private String storeName;
  private String moreContent;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date startTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date endTime;
  private Integer productStatus;

}
