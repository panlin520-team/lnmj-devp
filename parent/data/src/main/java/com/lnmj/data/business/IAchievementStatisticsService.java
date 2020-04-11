package com.lnmj.data.business;

import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

@Service("iAchievementStatisticsService")
public interface IAchievementStatisticsService {
    ResponseResult selectAchievementByList(int pageNum, int pageSize, String store, String times);
}
