package com.lnmj.wallet.entity;


import com.lnmj.wallet.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WalletConsumeRecord extends BaseEntity {

    private Long consumeRecordId;
    private Long userId;
    private String cardNumber;
    private Long walletId;
    private String transactionNo;
    private String merchantOrderNumber;
    private String transactionCode;
    private Date transactionTime;
    private String transactionName;
    private Long accountTypeId;
    private BigDecimal amount;
    private BigDecimal accountBalance;
    private Long transactionType;
    private Long transactionStatus;
    private String failureCause;
    private Date payTime;
    private String remarks;


}
