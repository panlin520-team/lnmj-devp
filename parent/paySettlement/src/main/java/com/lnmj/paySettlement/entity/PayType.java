package com.lnmj.paySettlement.entity;


import com.lnmj.paySettlement.entity.base.BaseEntity;
import lombok.Data;

@Data
public class PayType extends BaseEntity {

  private Long payTypeId;
  private Long payTypeCategory;
  private String payTypeName;
  private String subclass;
  private String industry;
  private Double paymentRatioOriginal;
  private Integer discount;
  private Long accountType;
  private Double accountTypeAmount;
}
