package com.lnmj.data.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.data.entity.VO.ProductBrandStatisticsVO;
import com.lnmj.data.repository.IProductBrandStatisticsDao;
import com.lnmj.data.repository.IProductCategoryStatisticsDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @Author: panlin
 * @Date: 2019/5/8 16:17
 * @Description:
 */
@Repository
public class ProductCategoryStatisticsDaolmpl extends BaseDao implements IProductCategoryStatisticsDao {

    @Override
    public List<ProductBrandStatisticsVO> selecyByList(Map map) {
        return super.selectList("productCategoryStatistics.selecyByList",map);
    }
}
