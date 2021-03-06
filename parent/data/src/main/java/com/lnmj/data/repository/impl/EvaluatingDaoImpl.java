package com.lnmj.data.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.data.entity.Evaluating;
import com.lnmj.data.entity.EvaluatingDetailed;
import com.lnmj.data.entity.EvaluatingLevel;
import com.lnmj.data.repository.IEvaluatingDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/10/12 16:17
 * @Description: 客户评测
 */
@Repository
public class EvaluatingDaoImpl extends BaseDao implements IEvaluatingDao {

    @Override
    public List<Evaluating> selectEvaluatingList(Map map) {
        return super.selectList("evaluating.selectEvaluatingList",map);
    }

    @Override
    public List<Evaluating> selectEvaluatingByCondition(Evaluating evaluating) {
        return super.selectList("evaluating.selectEvaluatingByCondition",evaluating);
    }

    @Override
    public Evaluating selectEvaluatingByID(Long evaluatingID) {
        return super.select("evaluating.selectEvaluatingByID",evaluatingID);
    }

    @Override
    public int deleteEvaluatingByID(Evaluating evaluating) {
        return super.update("evaluating.deleteEvaluatingByID",evaluating);
    }

    @Override
    public int insertEvaluating(Evaluating evaluating) {
        return super.insert("evaluating.insertEvaluating",evaluating);
    }

    @Override
    public int updateEvaluatingByID(Evaluating evaluating) {
        return super.update("evaluating.updateEvaluatingByID",evaluating);
    }

    @Override
    public Evaluating selectEvaluatingByType(HashMap<String,Object> selectMap) {
        return super.select("evaluating.selectEvaluatingByType",selectMap);
    }

    @Override
    public List<Long> insertEvaluatingLevelList(List<EvaluatingLevel> evaluatingLevelList) {
        return super.insertList("evaluatingLevel.insertEvaluatingLevelList",evaluatingLevelList);
    }

    @Override
    public int deleteEvaluatingLevelByEvaluatingID(EvaluatingLevel evaluatingLevel) {
        return super.update("evaluatingLevel.deleteEvaluatingLevelByEvaluatingID",evaluatingLevel);
    }

    @Override
    public List<Long> updateEvaluatingLevelList(List<EvaluatingLevel> evaluatingLevelList) {
        return super.updateList("evaluatingLevel.updateEvaluatingLevelList",evaluatingLevelList);
    }

    @Override
    public List<EvaluatingLevel> selectEvaluatingLevelList() {
        return super.selectList("evaluatingLevel.selectEvaluatingLevelList");
    }

    @Override
    public List<EvaluatingLevel> selectEvaluatingLevelByCondition(EvaluatingLevel evaluatingLevel) {
        return super.selectList("evaluatingLevel.selectEvaluatingLevelByCondition",evaluatingLevel);
    }

    @Override
    public EvaluatingLevel selectEvaluatingLevelByID(Long evaluatingLevelID) {
        return super.select("evaluatingLevel.selectEvaluatingLevelByID",evaluatingLevelID);
    }

    @Override
    public int deleteEvaluatingLevelByID(EvaluatingLevel evaluatingLevel) {
        return super.update("evaluatingLevel.deleteEvaluatingLevelByID",evaluatingLevel);
    }

    @Override
    public int insertEvaluatingLevel(EvaluatingLevel evaluatingLevel) {
        return super.insert("evaluatingLevel.insertEvaluatingLevel",evaluatingLevel);
    }

    @Override
    public int updateEvaluatingLevelByID(EvaluatingLevel evaluatingLevel) {
        return super.update("evaluatingLevel.updateEvaluatingLevelByID",evaluatingLevel);
    }

    @Override
    public List<EvaluatingDetailed> selectEvaluatingDetailedList() {
        return super.selectList("evaluatingDetailed.selectEvaluatingDetailedList");
    }

    @Override
    public List<EvaluatingDetailed> selectEvaluatingDetailedByCondition(EvaluatingDetailed evaluatingDetailed) {
        return super.selectList("evaluatingDetailed.selectEvaluatingDetailedByCondition",evaluatingDetailed);
    }

    @Override
    public EvaluatingDetailed selectEvaluatingDetailedByID(Long evaluatingDetailedID) {
        return super.select("evaluatingDetailed.selectEvaluatingDetailedByID",evaluatingDetailedID);
    }

    @Override
    public int deleteEvaluatingDetailedByID(EvaluatingDetailed evaluatingDetailed) {
        return super.update("evaluatingDetailed.deleteEvaluatingDetailedByID",evaluatingDetailed);
    }

    @Override
    public int insertEvaluatingDetailed(EvaluatingDetailed evaluatingDetailed) {
        return super.insert("evaluatingDetailed.insertEvaluatingDetailed",evaluatingDetailed);
    }

    @Override
    public int updateEvaluatingDetailedByID(EvaluatingDetailed evaluatingDetailed) {
        return super.update("evaluatingDetailed.updateEvaluatingDetailedByID",evaluatingDetailed);
    }

    @Override
    public List<EvaluatingDetailed> selectEvaluatingDetailedByUserIdAndDate(HashMap<String, Object> map) {
        return super.selectList("evaluatingDetailed.selectEvaluatingDetailedByUserIdAndDate",map);
    }
}
