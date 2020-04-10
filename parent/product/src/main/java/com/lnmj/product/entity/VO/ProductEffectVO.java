package com.lnmj.product.entity.VO;

import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * @Author: yilihua
 * @Date: 2019/5/8 14:28
 * @Description: ProductEffectVO
 */
@Data
public class ProductEffectVO extends BaseEntity {
    private Long productEffectId;       // Id
    private String productEffectName;   // 名
    private Long productKindId;       // 商品种类
    private String productEffectOrder;  // 排序（A-Z）
    private Long productTypeDisplayId; // 显示
    private String imageType;     // 图片类型
    private MultipartFile multipartFile;    //商品分类图标
    private BigDecimal integralratioService;  // 积分比例(服务商)
    private BigDecimal integralratioUnion;   // 积分比例(联盟商)
    private BigDecimal retailpriceVIP1;     // 积分比例VIP1
    private BigDecimal retailpriceVIP2;     // 积分比例VIP2
    private BigDecimal retailpriceVIP3;     // 积分比例VIP3
    private BigDecimal retailpriceVIP4;     // 积分比例VIP4
    private BigDecimal retailpriceVIP5;     // 积分比例VIP5
}
