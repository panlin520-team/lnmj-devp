package com.lnmj.store.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.store.entity.*;
import com.lnmj.store.repository.IExperienceCardDao;
import com.lnmj.store.repository.ISubordBuyLimitDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @Author panlin
 * @create 2019/5/28
 */

@Repository
public class SubordBuyLimitDaoImpl extends BaseDao implements ISubordBuyLimitDao {
    @Override
    public List<SubordBuyLimit> selectSubordBuyLimitList(Map map) {
        return super.selectList("subordBuyLimit.selectSubordBuyLimitList",map);
    }

    @Override
    public int deleteSubordBuyLimit(Map map) {
        return super.delete("subordBuyLimit.deleteSubordBuyLimit",map);
    }

    @Override
    public int addSubordBuyLimit(SubordBuyLimit subordBuyLimit) {
        return super.insert("subordBuyLimit.addSubordBuyLimit",subordBuyLimit);
    }

    @Override
    public int updateSubordBuyLimit(SubordBuyLimit subordBuyLimit) {
        return super.update("subordBuyLimit.updateSubordBuyLimit",subordBuyLimit);
    }

    @Override
    public SubordBuyLimit selectSubordBuyLimitById(Long subordBuyLimitId) {
        return super.select("subordBuyLimit.selectSubordBuyLimitById",subordBuyLimitId);
    }


}
