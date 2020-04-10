package com.lnmj.account.entity.VO;


import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: yilihua
 * @Date: 2019/4/24 10:04
 * @Description: 充值升级请求vo
 */
@Data
public class WalletRechargeRecordRequestVO {

    private String memberNum;//用户id
    private String rechargeAmount;//充值金额/消费金额


    public BigDecimal getRechargeAmount() {
        BigDecimal bigDecimal = new BigDecimal(rechargeAmount);
        bigDecimal = bigDecimal.setScale(2);
        return bigDecimal;
    }



}
