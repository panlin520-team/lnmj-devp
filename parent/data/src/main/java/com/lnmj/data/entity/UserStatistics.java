package com.lnmj.data.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 〈数据统计〉
 *
 * @Author renqingyun
 * @create 2019/5/31
 */
@Data
public class UserStatistics {
    private String name;
    private String nickName;
    private String Mobile;
    private String CardNumber;
    private BigDecimal Amount;
    private BigDecimal AccountBalance;
}
