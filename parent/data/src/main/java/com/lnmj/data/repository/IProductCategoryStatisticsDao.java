package com.lnmj.data.repository;

import com.lnmj.data.entity.VO.ProductBrandStatisticsVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2019/6/4
 */

@Repository
public interface IProductCategoryStatisticsDao {

    List<ProductBrandStatisticsVO> selecyByList(Map map);
}
