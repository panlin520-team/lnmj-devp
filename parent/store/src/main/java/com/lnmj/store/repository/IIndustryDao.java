package com.lnmj.store.repository;

import com.lnmj.store.entity.Industry;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


public interface IIndustryDao {
    List<Industry> selectList(Map map);

    Industry selectListIndustryById(Long industryID);

    int addIndustry(Industry industry);

    int updateIndustry(Industry industry);

    int deleteIndustry(Industry industry);

    int checkIndustryName(Industry industry);

}
