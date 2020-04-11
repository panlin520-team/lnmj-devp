package com.lnmj.product.entity;

import com.alibaba.druid.support.monitor.annotation.MTable;
import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:01
 * @Description: 商品种类表
 */
@Data
@MTable(name = "p_productkind")
public class ProductKind extends BaseEntity {
    private Long productKindId;       // Id
    private String productKindName;   // 名
    private String productKindOrder;  // 排序（A-Z）
    private Long productTypeDisplayId; // 显示
    private Long productClassifyId; // 商品类型
    private String imageType;     // 图片类型
    private String imageUrl;        // 图片存储地址
    private Integer ImageHeight;     // 图片长
    private Integer imageWidth;    // 图片宽
    private BigDecimal integralratioService;  // 积分比例(服务商)
    private BigDecimal integralratioUnion;   // 积分比例(联盟商)
    private BigDecimal retailpriceVIP1;     // 积分比例VIP1
    private BigDecimal retailpriceVIP2;     // 积分比例VIP2
    private BigDecimal retailpriceVIP3;     // 积分比例VIP3
    private BigDecimal retailpriceVIP4;     // 积分比例VIP4
    private BigDecimal retailpriceVIP5;     // 积分比例VIP5

}
