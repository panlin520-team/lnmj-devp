package com.lnmj.data.entity.VO;

import lombok.Data;
import org.apache.ibatis.annotations.Param;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2020/3/17
 */

@Data
public class BackWalletVO {
    private Long walletId;
    private String cardNumber;
    //储值总余额
    private String rechargeBalance;
    //返利总余额
    private String rebateBalance;
    //赠送总余额
    private String giveBalance;
    //拓客金余额
    private String tokerBalance;
    //账户类型
    private Long amountType;
}
