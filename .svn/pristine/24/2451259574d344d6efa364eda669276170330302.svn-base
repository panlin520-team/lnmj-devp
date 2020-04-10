package com.lnmj.data.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.data.entity.CustomMadeStatistics;
import com.lnmj.data.repository.ICustomMadeStatisticsDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class ICustomMadeStatisticsDaolmpl extends BaseDao implements ICustomMadeStatisticsDao {

    @Override
    public List<CustomMadeStatistics> selectCustomMadeByList(Map map) {
        return super.selectList("customMadeStatistics.selectCustomMadeByList",map);
    }
}
