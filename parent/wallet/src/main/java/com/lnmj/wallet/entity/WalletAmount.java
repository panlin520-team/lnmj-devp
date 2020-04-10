package com.lnmj.wallet.entity;


import com.lnmj.wallet.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletAmount extends BaseEntity {
  private long walletId;
  private long accountTypeId;
  private BigDecimal amount;

}
