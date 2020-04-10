package com.lnmj.product.entity.VO;

import com.lnmj.product.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * @Author: yilihua
 * @Date: 2019/5/8 14:28
 * @Description: ProductSiteVO
 */
@Data
public class ProductSiteVO extends BaseEntity {
    private Long productSiteId;       // Id
    private String productSiteName;   // 名
    private String productSiteOrder;  // 排序（A-Z）
    private Long productTypeDisplayId; // 显示
    private Long productClassifyId; // 商品类型
    private String imageType;     // 图片类型
    private MultipartFile multipartFile;    //商品分类图标

}
