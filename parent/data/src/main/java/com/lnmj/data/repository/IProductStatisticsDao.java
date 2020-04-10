package com.lnmj.data.repository;

import com.lnmj.data.entity.VO.ProductStatisticsVO;

import java.util.List;
import java.util.Map;

public interface IProductStatisticsDao {
    List<ProductStatisticsVO> selectByList(Map map);
}
