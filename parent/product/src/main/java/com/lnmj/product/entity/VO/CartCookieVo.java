package com.lnmj.product.entity.VO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: panlin
 * @Date: 2019/5/6 16:21
 * @Description:
 */
@Data
public class CartCookieVo {

    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private int num;
    private String pictureUrl;

}
