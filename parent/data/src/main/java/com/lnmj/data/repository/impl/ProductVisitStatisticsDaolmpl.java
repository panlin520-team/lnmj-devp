package com.lnmj.data.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.data.entity.VO.ProductVisitVO;
import com.lnmj.data.repository.IProductVisitStatisticsDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2019/6/6
 */
@Repository
public class ProductVisitStatisticsDaolmpl extends BaseDao implements IProductVisitStatisticsDao {
    @Override
    public List<ProductVisitVO> selectProductVisitByList(Map map) {
        return super.selectList("productVisitStatistics.selectProductVisitByList",map);
    }
}
