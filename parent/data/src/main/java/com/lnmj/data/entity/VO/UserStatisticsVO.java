package com.lnmj.data.entity.VO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserStatisticsVO {
    private String name;
    private String nickName;
    private String superName;//上级姓名
    private String mobile;
    private String cardNumber;
    private String membershipLevelName;
    private BigDecimal amount;
    private BigDecimal accountBalance;
}
