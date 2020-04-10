package com.lnmj.system.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.system.entity.BeauticianGrade;
import com.lnmj.system.repository.IBeauticianGradeDao;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: yilihua
 * @Date: 2019/5/29 15:39
 * @Description: 系统参数配置Dao
 */
@Repository
public class BeauticianGradeDaoImpl extends BaseDao implements IBeauticianGradeDao {

    @Override
    public List<BeauticianGrade> selectBeauticianGradeList() {
        return super.selectList("beauticianGrade.selectBeauticianGradeList");
    }

    @Override
    public List<BeauticianGrade> selectBeauticianGradeByWhere(BeauticianGrade beauticianGrade) {
        return super.selectList("beauticianGrade.selectBeauticianGradeByWhere",beauticianGrade);
    }

    @Override
    public int insertBeauticianGrade(BeauticianGrade beauticianGrade) {
        return super.insert("beauticianGrade.insertBeauticianGrade",beauticianGrade);
    }

    @Override
    public int updateBeauticianGrade(BeauticianGrade beauticianGrade) {
        return super.update("beauticianGrade.updateBeauticianGrade",beauticianGrade);
    }

    @Override
    public int deleteBeauticianGrade(BeauticianGrade beauticianGrade) {
        return super.update("beauticianGrade.deleteBeauticianGrade",beauticianGrade);
    }
}
