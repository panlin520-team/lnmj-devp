package com.lnmj.store.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.entity.Industry;
import org.springframework.stereotype.Service;

@Service("iIndustryService")
public interface IIndustryService {
    ResponseResult selectList(int pageNum, int pageSize,String industryNameKeyword);

    ResponseResult selectListIndustryNoPage();

    ResponseResult selectListIndustryById(Long industryID);

    ResponseResult addIndustry(Industry industry);

    ResponseResult updateIndustry(Industry industry);

    ResponseResult deleteIndustry(Industry industry);
}
