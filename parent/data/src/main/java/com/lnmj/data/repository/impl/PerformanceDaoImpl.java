package com.lnmj.data.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.data.entity.Ladder;
import com.lnmj.data.entity.LadderDetailed;
import com.lnmj.data.entity.Performance;
import com.lnmj.data.entity.PerformancePost;
import com.lnmj.data.repository.IPerformanceDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/5 15:10
 * @Description: 业绩
 */
@Repository
public class PerformanceDaoImpl extends BaseDao implements IPerformanceDao {

    @Override
    public List<Performance> selectPerformanceList(Map mapList) {
        return super.selectList("performance.selectPerformanceList",mapList);
    }

    @Override
    public List<PerformancePost> selectPerformancePostList(Map mapList) {
        return super.selectList("performance.selectPerformancePostList",mapList);
    }

    @Override
    public List<PerformancePost> selectPerformancePostListAll() {
        return super.selectList("performance.selectPerformancePostListAll");
    }

    @Override
    public PerformancePost selectPerformancePostById(Long id) {
        return super.select("performance.selectPerformancePostById",id);
    }

    @Override
    public PerformancePost selectPerformancePosByCondition(Map map) {
        return super.select("performance.selectPerformancePosByCondition",map);
    }

    @Override
    public PerformancePost selectPerformancePosByConditionNoWhere(Map map) {
        return super.select("performance.selectPerformancePosByConditionNoWhere",map);
    }

    @Override
    public List<Performance> selectPerformanceByCondition(Performance performance) {
        return super.selectList("performance.selectPerformanceByCondition",performance);
    }

    @Override
    public Performance selectPerformanceByID(Long achievementID) {
        return super.select("performance.selectPerformanceByID",achievementID);
    }



    @Override
    public int deletePerformanceByID(Performance performance) {
        return super.update("performance.deletePerformanceByID",performance);
    }

    @Override
    public int deletePerformancePostByID(PerformancePost performancePost) {
        return super.update("performance.deletePerformancePostByID",performancePost);
    }

    @Override
    public int insertPerformance(Performance performance) {
        return super.insert("performance.insertPerformance",performance);
    }

    @Override
    public int insertPerformancePost(PerformancePost performancePost) {
        return super.insert("performance.insertPerformancePost",performancePost);
    }

    @Override
    public int updatePerformance(Performance performance) {
        return super.update("performance.updatePerformance",performance);
    }

    @Override
    public int updatePerformancePost(PerformancePost performancePost) {
        return super.update("performance.updatePerformancePost",performancePost);
    }

    @Override
    public List<Long> insertLadderList(List<Ladder> ladderList) {
        return super.insertList("ladder.insertLadderList",ladderList);
    }

    @Override
    public int deleteLadderByAchievementID(Ladder ladder) {
        return super.update("ladder.deleteLadderByAchievementID",ladder);
    }

    @Override
    public int deleteLadderByAchievementIDLongTime(Ladder ladder) {
        return super.delete("ladder.deleteLadderByAchievementIDLongTime",ladder);
    }

    @Override
    public List<Long> updateLadderList(List<Ladder> ladderList) {
        return super.updateList("ladder.updateLadderList",ladderList);
    }

    @Override
    public List<Ladder> selectLadderList() {
        return super.selectList("ladder.selectLadderList");
    }

    @Override
    public List<Ladder> selectLadderByCondition(Ladder ladder) {
        return super.selectList("ladder.selectLadderByCondition",ladder);
    }

    @Override
    public Ladder selectLadderByID(Long ladderID) {
        return super.select("ladder.selectLadderByID",ladderID);
    }

    @Override
    public int deleteLadderByID(Ladder ladder) {
        return super.update("ladder.deleteLadderByID",ladder);
    }

    @Override
    public int insertLadder(Ladder ladder) {
        return super.insert("ladder.insertLadder",ladder);
    }

    @Override
    public int updateLadder(Ladder ladder) {
        return super.update("ladder.updateLadder",ladder);
    }

    @Override
    public List<LadderDetailed> selectLadderDetailedList(Map map) {
        return super.selectList("ladderDetailed.selectLadderDetailedList",map);
    }

    @Override
    public List<LadderDetailed> selectLadderDetailedByCondition(LadderDetailed ladderDetailed) {
        return super.selectList("ladderDetailed.selectLadderDetailedByCondition",ladderDetailed);
    }

    @Override
    public LadderDetailed selectLadderDetailedByID(Long ladderDetailedID) {
        return super.select("ladderDetailed.selectLadderDetailedByID",ladderDetailedID);
    }

    @Override
    public int deleteLadderDetailedByID(Map map) {
        return super.update("ladderDetailed.deleteLadderDetailedByID",map);
    }

    @Override
    public int deleteLadderDetailedByOrder(Map map) {
        return super.update("ladderDetailed.deleteLadderDetailedByOrder",map);
    }

    @Override
    public int insertLadderDetailed(LadderDetailed ladderDetailed) {
        return super.insert("ladderDetailed.insertLadderDetailed",ladderDetailed);
    }

    @Override
    public int updateLadderDetailed(LadderDetailed ladderDetailed) {
        return super.update("ladderDetailed.updateLadderDetailed",ladderDetailed);
    }

    @Override
    public int deleteLadderDetailedByCondition(LadderDetailed ladderDetailed) {
        return super.delete("ladderDetailed.deleteLadderDetailedByCondition",ladderDetailed);
    }

    @Override
    public List<LadderDetailed> statisticPerformance(HashMap<String, Object> map) {
        return super.selectList("ladderDetailed.statisticPerformance",map);
    }

    @Override
    public LadderDetailed selectLadderDetailedByRechargeOrderNum(Long orderNumber) {
        return select("ladderDetailed.selectLadderDetailedByRechargeOrderNum",orderNumber);
    }
}
