package com.lnmj.wallet.entity;


import com.lnmj.wallet.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class IndustryAccount extends BaseEntity {

      private Long industryAccountId;
      private Long accountUseRuleId;
      private Long industryId;      //行业
      private String industry;      //行业
      private Integer useSum;       //使用总次数
      private BigDecimal useMax;    //金额
      private BigDecimal unitPrice; //单价
      private String remark;

}
