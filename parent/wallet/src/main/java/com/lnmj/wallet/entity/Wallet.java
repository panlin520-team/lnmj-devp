package com.lnmj.wallet.entity;

import com.lnmj.wallet.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Wallet extends BaseEntity {
    private Long walletId;

    private String cardNumber;

    private BigDecimal bonus;

    private Integer integral;

}