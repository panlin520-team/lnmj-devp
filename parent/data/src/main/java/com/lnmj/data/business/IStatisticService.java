package com.lnmj.data.business;

import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.entity.Statistic;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author: yilihua
 * @Date: 2019/9/6 10:52
 * @Description: 统计
 */
@Service("iStatisticService")
public interface IStatisticService {

    //统计底薪

    ResponseResult selectStatisticsList(int pageNum, int pageSize);

    ResponseResult insertStatistic(Statistic statistic);

    ResponseResult selectSalaryList(String storeId);


    ResponseResult statisticEvaluating(Long userId, Date start, Date end);

    ResponseResult selectUserEvaluatingList(int pageNum, int pageSize,Long evaluatingLevelID,Long evaluatingID);

    ResponseResult selectYeJiSumByDate(Statistic statistic,Long salesmanID, String startDate,String endDate);
    ResponseResult selectScore(Long salesmanID, HashMap<String,Object> map, Statistic statistic);
    ResponseResult selectBasicSalary(Long salesmanID, BigDecimal score, Statistic statistic);
    ResponseResult responseResultTiCheng(HashMap<String,Object> map,Statistic statistic);
}
