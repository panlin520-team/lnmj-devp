package com.lnmj.data.business.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.business.IAchievementStatisticsService;
import com.lnmj.data.entity.Achievement;
import com.lnmj.data.repository.IAchievementStatisticsDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author renqingyun
 * @Date: 2019/6/10 10:07
 * @Description:
 */
@Service("achievementStatisticsServicelmpl")
public class AchievementStatisticsStatisticsServicelmpl implements IAchievementStatisticsService {
    @Resource
    private IAchievementStatisticsDao achievementStatisticsDao;

    @Override
    public ResponseResult selectAchievementByList(int pageNum, int pageSize, String store, String times) {
        Map map = new HashMap<>();
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNotBlank(store)) {
            map.put("store", store);
        }
        if (StringUtils.isNotBlank(store)) {
            String[] timesArr = times.split(",");
            map.put("startTime", timesArr[0]);
            map.put("endTime", timesArr[1]);
        }
        List<Achievement> list =  achievementStatisticsDao.selectAchievementByList(map);
        PageInfo pageInfo = new PageInfo(list);
        return ResponseResult.success(pageInfo);
    }
}
