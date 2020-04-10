package com.lnmj.product.entity;


import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class ProductNursing extends BaseEntity {
  private Long productNursingId;
  @NotNull(message = "商品id不能为空")
  private Long serviceProductId;
  @NotBlank(message = "商品护理方式名称不能为空")
  private String nursingName;
  @Min(value = 0,message = "商品护理方式次数小于0")
  private Integer frequency;
  @NotBlank(message = "商品护理方式折扣不能为空")
  private BigDecimal discount;
}
