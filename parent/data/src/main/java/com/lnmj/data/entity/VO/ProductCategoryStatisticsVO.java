package com.lnmj.data.entity.VO;

import lombok.Data;

/**
 * 〈商品统计vo〉
 *
 * @Author renqingyun
 * @create 2019/6/3
 */
@Data
public class ProductCategoryStatisticsVO {
    private String ProductCategoryName;
    private String ProductSales;
    private String saleAmount;//销售金额
}
