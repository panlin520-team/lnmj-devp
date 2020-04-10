package com.lnmj.wallet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.wallet.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WalletBonudDetail extends BaseEntity {

    private Long walletId;

    private Long bonusTypeId;

    private Integer orderNumber;

    private BigDecimal orderPrice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    private BigDecimal amount;

    private String name;

    private String membershipLevel;

    private String bonusStatus;
}