package com.lnmj.data.business;

import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;


@Service("iProductVisitStatisticsService")
public interface IProductVisitStatisticsService {
    ResponseResult selectProductVisitByList(int pageNum, int pageSize, String keyWord, String userVisitStatus, String visitStatus);
}
