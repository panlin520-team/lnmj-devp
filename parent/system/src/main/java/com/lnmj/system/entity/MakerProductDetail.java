package com.lnmj.system.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.system.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: yilihua
 * @Date: 2019/6/4 12:03
 * @Description: 创客商品详情
 */
@Data
public class MakerProductDetail extends BaseEntity {

  private Long makerProductDetailId;
  private String productCode;  //创客商品code
  private String product;     //商品
  private String productName;   //商品
  private String useLimit;
  private Integer useTotalTimes;
  private Integer useTime;
  private Integer efficientCondition;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date efficientDate;
  private String link;
  private BigDecimal totalSales;
  private BigDecimal totalCommission;
  private String forwardTitle;
  private String forwardDescribe;
  private String imageType;
  private String imageUrl;
  private Integer imageHeight;
  private Integer imageWidth;

}
