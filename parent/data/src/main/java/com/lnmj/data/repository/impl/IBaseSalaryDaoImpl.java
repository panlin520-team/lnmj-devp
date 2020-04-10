package com.lnmj.data.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.data.entity.Basesalary;
import com.lnmj.data.entity.Score;
import com.lnmj.data.repository.IBaseSalaryDao;
import com.lnmj.data.repository.IScoreDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2019/9/4
 */

@Repository
public class IBaseSalaryDaoImpl extends BaseDao implements IBaseSalaryDao {
    @Override
    public List<Basesalary> selectList() {
        return super.selectList("basesalary.selectList");
    }

    @Override
    public int addBaseSalary(Basesalary basesalary) {
        return super.insert("basesalary.addBasesalary",basesalary);
    }

    @Override
    public int updateBaseSalary(Basesalary basesalary) {
        return super.update("basesalary.updateBasesalary",basesalary);
    }

    @Override
    public int deleteBaseSalary(HashMap<Object, Object> map) {
        return super.update("basesalary.deleteBasesalary",map);
    }

    @Override
    public List<Basesalary> selectByCondition(Basesalary basesalary) {
        return super.selectList("basesalary.selectByCondition",basesalary);
    }
}
