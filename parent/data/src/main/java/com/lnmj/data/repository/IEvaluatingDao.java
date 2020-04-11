package com.lnmj.data.repository;

import com.lnmj.data.entity.Evaluating;
import com.lnmj.data.entity.EvaluatingDetailed;
import com.lnmj.data.entity.EvaluatingLevel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/4 15:30
 * @Description: 客户评测
 */
public interface IEvaluatingDao {
    /**
     * 客户评测
     */
    List<Evaluating> selectEvaluatingList(Map map);

    List<Evaluating> selectEvaluatingByCondition(Evaluating evaluating);

    Evaluating selectEvaluatingByID(Long evaluatingID);

    int deleteEvaluatingByID(Evaluating evaluating);

    int insertEvaluating(Evaluating evaluating);

    int updateEvaluatingByID(Evaluating evaluating);

    Evaluating selectEvaluatingByType(HashMap<String,Object> selectMap);
    /**
     * 客户评测级别
     */
    List<Long> insertEvaluatingLevelList(List<EvaluatingLevel> evaluatingLevelList);

    int deleteEvaluatingLevelByEvaluatingID(EvaluatingLevel evaluatingLevel);

    List<Long> updateEvaluatingLevelList(List<EvaluatingLevel> evaluatingLevelList);

    List<EvaluatingLevel> selectEvaluatingLevelList();

    List<EvaluatingLevel> selectEvaluatingLevelByCondition(EvaluatingLevel evaluatingLevel);

    EvaluatingLevel selectEvaluatingLevelByID(Long evaluatingLevelID);

    int deleteEvaluatingLevelByID(EvaluatingLevel evaluatingLevel);

    int insertEvaluatingLevel(EvaluatingLevel evaluatingLevel);

    int updateEvaluatingLevelByID(EvaluatingLevel evaluatingLevel);

    /**
     * 客户评测明细
     */
    List<EvaluatingDetailed> selectEvaluatingDetailedList();

    List<EvaluatingDetailed> selectEvaluatingDetailedByCondition(EvaluatingDetailed evaluatingDetailed);

    EvaluatingDetailed selectEvaluatingDetailedByID(Long evaluatingDetailedID);

    int deleteEvaluatingDetailedByID(EvaluatingDetailed evaluatingDetailed);

    int insertEvaluatingDetailed(EvaluatingDetailed evaluatingDetailed);

    int updateEvaluatingDetailedByID(EvaluatingDetailed evaluatingDetailed);

    List<EvaluatingDetailed> selectEvaluatingDetailedByUserIdAndDate(HashMap<String, Object> map);
}
