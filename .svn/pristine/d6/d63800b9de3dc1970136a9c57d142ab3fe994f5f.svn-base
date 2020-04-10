package com.lnmj.wallet.entity;

import com.lnmj.wallet.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class CapitalPool extends BaseEntity {
    private Long id;

    private Integer type;

    private Integer payType;

    private Long storeId;

    private BigDecimal amount;

    private BigDecimal residueAmount;

    private String remarks;

    private String orderNumber;

    private Long advancesReceivedAccount;

    @Transient
    private String typeName;
    @Transient
    private String payTypeName;
    @Transient
    private String storeName;
    @Transient
    private String advancesReceivedAccountName;
    @Transient
    private Long notStoreId;
    @Transient
    private Integer residueAmountUpOrDown;
}