package com.lnmj.store.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.store.entity.Industry;
import com.lnmj.store.repository.IIndustryDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class IndustryDaoImpl extends BaseDao implements IIndustryDao {

    @Override
    public List<Industry> selectList(Map map) {
        return super.selectList("industry.selectList",map);
    }

    @Override
    public Industry selectListIndustryById(Long industryID) {
        return super.select("industry.selectListIndustryById",industryID);
    }

    @Override
    public int addIndustry(Industry industry) {
        return super.insert("industry.addIndustry",industry);
    }

    @Override
    public int updateIndustry(Industry industry) {
        return super.update("industry.updateIndustry",industry);
    }

    @Override
    public int deleteIndustry(Industry industry) {
        return super.update("industry.deleteIndustry",industry);
    }

    @Override
    public int checkIndustryName(Industry industry) {
        return super.select("industry.checkIndustryName",industry);
    }
}
