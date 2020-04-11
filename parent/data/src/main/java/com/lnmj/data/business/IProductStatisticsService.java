package com.lnmj.data.business;

import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

@Service("iProductStatisticsService")
public interface IProductStatisticsService {
    ResponseResult selectByList(int pageNum, int pageSize, String keyWord, String time, String saleStatus, String amountStatus);
}
