package com.lnmj.system.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.system.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/8/24 9:51
 * @Description: 创客商品
 */
@Data
public class MakerProductVO extends BaseEntity{

  private Long makerProductId;
  private Long makerType;
  private String makerProduct;
  private String productCode;
  private BigDecimal originalPrice;
  private BigDecimal retailPrice;
  private BigDecimal discount;
  private String integral;
  private String imageType;
  private MultipartFile multipartFile;
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
  private Long productStatus;
  private List<MakerProductDetailVO> makerProductDetailList;

}
