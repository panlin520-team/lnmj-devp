package com.lnmj.data.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.data.entity.Statistic;
import com.lnmj.data.entity.UserEvaluating;
import com.lnmj.data.repository.IStatisticDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/9/6 10:52
 * @Description: 统计
 */

@Repository
public class StatisticDaoImpl extends BaseDao implements IStatisticDao {

    @Override
    public List<Statistic> selectStatisticsList() {
        return super.selectList("statistics.selectStatisticsList");
    }

    @Override
    public int addStatistics(Statistic statistic) {
        return super.insert("statistics.addStatistics",statistic);
    }

    @Override
    public int updateStatistics(Statistic statistic) {
        return super.update("statistics.updateStatistics",statistic);
    }

    @Override
    public List<Statistic> selectStatisticsBySalesman(HashMap<String,Object> map) {
        return super.selectList("statistics.selectStatisticsBySalesman",map);
    }

    @Override
    public List<UserEvaluating> selectUserEvaluatingList(UserEvaluating userEvaluating) {
        return super.selectList("userEvaluating.selectUserEvaluatingList",userEvaluating);
    }

    @Override
    public int addUserEvaluating(UserEvaluating userEvaluating) {
        return super.insert("userEvaluating.addUserEvaluating",userEvaluating);
    }

    @Override
    public List<UserEvaluating> selectUserEvaluatingByUser(HashMap map) {
        return super.selectList("userEvaluating.selectUserEvaluatingByUser",map);
    }

    @Override
    public int updateUserEvaluating(UserEvaluating userEvaluating) {
        return super.update("userEvaluating.updateUserEvaluating",userEvaluating);
    }
}
