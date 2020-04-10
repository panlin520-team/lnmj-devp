package com.lnmj.wallet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.wallet.entity.VO.WalletUserVo;
import com.lnmj.wallet.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @Author renqingyun
 * @Date: 2019/5/30 16:34
 * @Description: 钱包提现
 */
@Data
public class WalletCashRecord extends BaseEntity {
    private Long cashRecordId;

    private Long walletId;

    private Long bankCardId;

    private String transactionNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp cashTime;

    private BigDecimal amount;

    private Integer cashChannel;

    private Integer cashStatus;

    private String failureCause;

    private String remarks;

    private String account;

    private String mobile;

    private String cardNumber;

    private String name;

    private String bankName;

    private String bankCode;
}