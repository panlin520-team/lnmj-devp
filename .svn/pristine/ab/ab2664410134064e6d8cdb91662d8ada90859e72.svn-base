package com.lnmj.data.repository;

import com.lnmj.data.entity.Statistic;
import com.lnmj.data.entity.UserEvaluating;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/9/6 10:51
 * @Description:  统计
 */
public interface IStatisticDao {

    List<Statistic> selectStatisticsList();
    int addStatistics(Statistic statistic);
    int updateStatistics(Statistic statistic);

    List<Statistic> selectStatisticsBySalesman(HashMap<String,Object> map);

    List<UserEvaluating> selectUserEvaluatingList(UserEvaluating userEvaluating);
    int addUserEvaluating(UserEvaluating userEvaluating);
    List<UserEvaluating> selectUserEvaluatingByUser(HashMap map);

    int updateUserEvaluating(UserEvaluating userEvaluating);
}
