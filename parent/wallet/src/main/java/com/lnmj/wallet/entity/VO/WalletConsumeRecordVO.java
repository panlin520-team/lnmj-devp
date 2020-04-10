package com.lnmj.wallet.entity.VO;


import com.lnmj.wallet.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WalletConsumeRecordVO extends BaseEntity {
    private BigDecimal amount;//自身消费
    private BigDecimal accountBalance;//余额
}
