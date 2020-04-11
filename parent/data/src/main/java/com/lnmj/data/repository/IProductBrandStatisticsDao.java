package com.lnmj.data.repository;

import com.lnmj.data.entity.VO.ProductBrandStatisticsVO;

import java.util.List;
import java.util.Map;

public interface IProductBrandStatisticsDao {
    List<ProductBrandStatisticsVO> selecyByList(Map map);
}
