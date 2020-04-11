package com.lnmj.store.entity;

import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;

@Data
public class SubordBuyLimit extends BaseEntity {
    private Long subordBuyLimitId;

    private String subordBuyLimitName;

    private Integer subordBuyLimitNumber;

    private Integer productType;




}