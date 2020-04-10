package com.lnmj.account.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.account.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class MMemberamountrecord extends BaseEntity {
    private Long memberAmountRecordID;

    private String memberNum;

    private String cardNumber;

    private Long memberLevelID;

    private String memberAmountType;

    private Long memberAmountSource;

    private BigDecimal totalAmount;

    private BigDecimal banlance;

    private BigDecimal amount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date amountTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date amountEndTime;

    private String remark;



}