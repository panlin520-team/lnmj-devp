package com.lnmj.data.entity.VO;

import lombok.Data;

/**
 * 〈商品访问量统计〉
 *
 * @Author renqingyun
 * @create 2019/6/6
 */
@Data
public class ProductVisitVO {

    private String productName;
    private Integer userVisitNumber;
    private Integer visitNumber;

}
