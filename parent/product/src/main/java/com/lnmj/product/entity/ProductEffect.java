package com.lnmj.product.entity;

import com.alibaba.druid.support.monitor.annotation.MTable;
import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 14:52
 * @Description: 商品功效表
 */
@Data
@MTable(name = "p_producteffect")
public class ProductEffect extends BaseEntity {
    private Long productEffectId;       // Id
    private String productEffectName;   // 名
    private Long productKindId;       // 商品种类
    private String productEffectOrder;  // 排序（A-Z）
    private Long productTypeDisplayId; // 显示
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
