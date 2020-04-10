package com.lnmj.wallet.entity.VO;

import lombok.Data;


/**
 * @Author renqingyun
 * @Date: 2019/5/30 16:34
 * @Description: 钱包提现VO
 */

@Data
public class CashVO {
    private String name;

    private String BankName;

    private String BankCode;

    private String Mobile;

    private String CardNumber;

    private Integer CashStatus;//提现状态

}