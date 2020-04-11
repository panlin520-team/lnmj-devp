package com.lnmj.data.business;

import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;


@Service("iProductBrandStatisticsService")
public interface IProductBrandStatisticsService {
    ResponseResult selecyByList(int pageNum, int pageSize, String keyWord, String times, String saleStatus, String amountStatus);
}
