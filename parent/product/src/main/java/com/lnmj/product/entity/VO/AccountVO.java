package com.lnmj.product.entity.VO;

import lombok.Data;

@Data
public class AccountVO {
    private String product;
    private String mobile;
    private String name;
    private String cardNumber;
    private Integer registerType;
    private String registrationChannel;

}
