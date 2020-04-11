package com.lnmj.data.repository;

import com.lnmj.data.entity.EvaluatingLevel;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/4 15:30
 * @Description: 客户评测
 */
public interface IEvaluatingLevelDao {
    /**
     * 客户评测等级
     */
    List<EvaluatingLevel> selectEvaluatingLeveldList();

    int addEvaluatingLeveld(EvaluatingLevel evaluatingLevel);

    int updateEvaluatingLeveldList(EvaluatingLevel evaluatingLevel);

    int deleteEvaluatingLeveldList(EvaluatingLevel evaluatingLevel);
}
