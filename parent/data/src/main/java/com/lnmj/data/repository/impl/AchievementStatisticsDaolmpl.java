package com.lnmj.data.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.data.entity.Achievement;
import com.lnmj.data.repository.IAchievementStatisticsDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;



@Repository
public class AchievementStatisticsDaolmpl extends BaseDao implements IAchievementStatisticsDao {

    @Override
    public List<Achievement> selectAchievementByList(Map map) {
        return super.selectList("achievementStatistics.selectAchievementByList",map);
    }
}
