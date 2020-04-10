package com.lnmj.data.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.data.entity.EvaluatingLevel;
import com.lnmj.data.repository.IEvaluatingLevelDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/10/12 16:17
 * @Description: 客户评测
 */
@Repository
public class EvaluatingLevelDaoImpl extends BaseDao implements IEvaluatingLevelDao {

    @Override
    public List<EvaluatingLevel> selectEvaluatingLeveldList() {
        return super.selectList("evaluatingLevel.selectEvaluatingLevelList");
    }

    @Override
    public int addEvaluatingLeveld(EvaluatingLevel evaluatingLevel) {
        return super.insert("evaluatingLevel.insertEvaluatingLevel",evaluatingLevel);
    }

    @Override
    public int updateEvaluatingLeveldList(EvaluatingLevel evaluatingLevel) {
        return super.update("evaluatingLevel.updateEvaluatingLevelByID",evaluatingLevel);
    }

    @Override
    public int deleteEvaluatingLeveldList(EvaluatingLevel evaluatingLevel) {
        return super.insert("evaluatingLevel.deleteEvaluatingLevelByID", evaluatingLevel);
    }
}
