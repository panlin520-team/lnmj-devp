package com.lnmj.system.entity.VO;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.system.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: yilihua
 * @Date: 2019/6/4 12:03
 * @Description: 创客商品详情
 */
@Data
public class MakerProductDetailVO extends BaseEntity {

  private Long makerProductDetailId;
  private String productCode;
  private String product;
  private String productName;
  private String useLimit;  //时间
  private Long useTotalTimes;
  private Long useTime;
  private Long efficientCondition;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date efficientDate;
  private String link;
  private BigDecimal totalSales;
  private BigDecimal totalCommission;
  private String forwardTitle;
  private String forwardDescribe;
  private String imageType;
  private MultipartFile multipartFile;

}
