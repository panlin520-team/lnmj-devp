package com.lnmj.data.entity.VO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2020/4/15
 */
@Data
public class WalletAmount {
    private Long walletId;
    private Long accountTypeId;
    private BigDecimal amount;
}
