package com.lnmj.wallet.entity;

import com.lnmj.wallet.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Profit extends BaseEntity {
    private Long id;

    private BigDecimal amount;

    private String reason;

    private int consumptionStatus;

}