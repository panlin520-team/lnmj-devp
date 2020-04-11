package com.lnmj.data.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.entity.VO.ProductStatisticsVO;
import com.lnmj.data.repository.IProductStatisticsDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @Author: panlin
 * @Date: 2019/5/8 16:17
 * @Description:
 */
@Repository
public class ProductStatisticsDaolmpl extends BaseDao implements IProductStatisticsDao {

    @Override
    public List<ProductStatisticsVO> selectByList(Map map) {
        return super.selectList("productStatistics.selectByList",map);
    }
}
