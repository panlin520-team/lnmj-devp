package com.lnmj.wallet.entity.VO;

import com.lnmj.wallet.entity.Wallet;
import lombok.Data;

import java.util.Map;

/**
 * @Author panlin
 * @Date: 2019/7/18 16:34
 * @Description: 钱包及余额
 */

@Data
public class WalletAccountVO {
    private Wallet wallet;

    private Map walletAmountsMap;


}