package com.lnmj.account.entity;

import com.lnmj.account.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class MMemberaccount extends BaseEntity {
    private Long memberAccountId;

    private Long memberLevel;

    private Long accountTypeId;

    private String accountType;

    private BigDecimal accountAmount;

    private String accountAmountScope;

    private Boolean isUse;

    private Integer amountMethod;

    private BigDecimal oneMonthAmount;

    private Boolean isReset;

    private Boolean isContinue;

    private String remark;

}