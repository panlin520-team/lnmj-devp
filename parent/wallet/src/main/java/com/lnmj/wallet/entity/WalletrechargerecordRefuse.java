package com.lnmj.wallet.entity;

import com.lnmj.wallet.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

@Data
public class WalletrechargerecordRefuse extends BaseEntity {
    private Long id;

    private String orderNo;

    private String name;

    private String mobile;

    private String amount;

    @Transient
    private String startdate;
    @Transient
    private String endDate;
}