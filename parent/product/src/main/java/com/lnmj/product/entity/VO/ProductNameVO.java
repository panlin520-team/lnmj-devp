package com.lnmj.product.entity.VO;

import lombok.Data;

import javax.persistence.Transient;

/**
 * 〈服务商品名称视图对象〉
 *
 * @Author renqingyun
 * @create 2019/7/4
 */

@Data
public class ProductNameVO {
    private Long ProductId;
    private String ProductName;
    private String ProductCode;
//    @Transient
//    private Integer isChecked;
}
