package com.lnmj.data.business;


import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

@Service("iCustomMadeStatisticsService")
public interface ICustomMadeStatisticsService {
    ResponseResult selectCustomMadeByList(int pageNum, int pageSize, String store, String times);
}
