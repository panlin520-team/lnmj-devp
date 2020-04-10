package com.lnmj.data.repository;

import com.lnmj.data.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/4 15:30
 * @Description: 业绩
 */
public interface IPerformanceDao {
    /**
     * 业绩
     */
    List<Performance> selectPerformanceList(Map mapList);

    List<PerformancePost> selectPerformancePostList(Map mapList);

    List<PerformancePost> selectPerformancePostListAll();

    PerformancePost selectPerformancePostById(Long id);

    PerformancePost selectPerformancePosByCondition(Map map);

    PerformancePost selectPerformancePosByConditionNoWhere(Map map);

    List<Performance> selectPerformanceByCondition(Performance performance);

    Performance selectPerformanceByID(Long achievementID);


    int deletePerformanceByID(Performance performance);

    int deletePerformancePostByID(PerformancePost performancePost);

    int insertPerformance(Performance performance);

    int insertPerformancePost(PerformancePost performancePost);

    int updatePerformance(Performance performance);

    int updatePerformancePost(PerformancePost performancePost);

    /**
     * 业绩阶梯
     */
    List<Long> insertLadderList(List<Ladder> ladderList);

    int deleteLadderByAchievementID(Ladder ladder);

    int deleteLadderByAchievementIDLongTime(Ladder ladder);

    List<Long> updateLadderList(List<Ladder> ladderList);

    List<Ladder> selectLadderList();

    List<Ladder> selectLadderByCondition(Ladder ladder);

    Ladder selectLadderByID(Long ladderID);

    int deleteLadderByID(Ladder ladder);

    int insertLadder(Ladder ladder);

    int updateLadder(Ladder ladder);

    /**
     * 业绩明细
     */
    List<LadderDetailed> selectLadderDetailedList(Map map);

    List<LadderDetailed> selectLadderDetailedByCondition(LadderDetailed ladderDetailed);

    LadderDetailed selectLadderDetailedByID(Long ladderDetailedID);

    int deleteLadderDetailedByID(Map map);

    int deleteLadderDetailedByOrder(Map map);

    int insertLadderDetailed(LadderDetailed ladderDetailed);

    int updateLadderDetailed(LadderDetailed ladderDetailed);

    int deleteLadderDetailedByCondition(LadderDetailed ladderDetailed);

    List<LadderDetailed> statisticPerformance(HashMap<String, Object> map);

    LadderDetailed selectLadderDetailedByRechargeOrderNum(Long orderNumber);
}
