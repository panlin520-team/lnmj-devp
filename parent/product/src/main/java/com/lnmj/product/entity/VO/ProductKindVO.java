package com.lnmj.product.entity.VO;

import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * @Author: yilihua
 * @Date: 2019/5/8 14:28
 * @Description: ProductKindVO
 */
@Data
public class ProductKindVO extends BaseEntity {
    private Long productKindId;       // Id
    private String productKindName;   // 名
    private String productKindOrder;  // 排序（A-Z）
    private Long productTypeDisplayId; // 显示
    private Long productClassifyId; // 商品类型
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
