package com.lnmj.system.entity;

import com.lnmj.system.entity.base.BaseEntity;
import lombok.Data;
import java.math.BigDecimal;

/**
 * @Author: yilihua
 * @Date: 2019/8/24 9:48
 * @Description: 创客参数
 */
@Data
public class MakerMemberLevel extends BaseEntity {

  private Long makerMemberLevelId;
  private String makerMemberLevel;
  private Integer makerType;
  private BigDecimal levelUpdatePrice;
  private Integer salesNumber;
  private BigDecimal salesPrice;
  private Long makerProduct;
  private BigDecimal productPrice;
  private BigDecimal bonus1;
  private BigDecimal bonus2;

}
