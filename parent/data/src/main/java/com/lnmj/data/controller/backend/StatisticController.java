package com.lnmj.data.controller.backend;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeEvaluatingEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodePerformanceEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.ListPageUntil;
import com.lnmj.data.business.IStatisticService;
import com.lnmj.data.entity.LadderDetailed;
import com.lnmj.data.entity.PerformancePost;
import com.lnmj.data.entity.Score;
import com.lnmj.data.entity.Statistic;
import com.lnmj.data.repository.IPerformanceDao;
import com.lnmj.data.repository.IScoreDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: yilihua
 * @Date: 2019/9/6 10:54
 * @Description: 统计(业绩, 底薪, 评分)
 */
@Api(description = "统计")
@RestController
@RequestMapping("/statistic")
public class StatisticController {
    @Resource
    private IStatisticService statisticService;
    @Resource
    private IPerformanceDao iPerformanceDao;

    /**
     * @Description 客户等级分页显示
     * @Param [pageNum, pageSize, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/10/25
     * @Time 19:06
     */
    @ApiOperation(value = "客户等级分页显示", notes = "客户等级分页显示")
    @RequestMapping(value = "/selectUserEvaluatingList", method = RequestMethod.POST)
    public ResponseResult selectUserEvaluatingList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, Long evaluatingLevelID, Long evaluatingID, String access_token) {
        return statisticService.selectUserEvaluatingList(pageNum, pageSize, evaluatingLevelID, evaluatingID);
    }

    /**
     * @Description 客户评测等级
     * @Param [userId, startDate, endDate]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/10/25
     * @Time 10:41
     */
    @ApiOperation(value = "客户评测等级", notes = "客户评测等级")
    @RequestMapping(value = "/statisticEvaluating", method = RequestMethod.POST)
    public ResponseResult statisticEvaluating(Long userId, String startDate, String endDate) {
        //当前时间的等级（在多少天内消费了好多达到等级）
        if (userId == null) {
            return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.USER_ID_NULL.getCode(),
                    ResponseCodeEvaluatingEnum.USER_ID_NULL.getDesc()));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = null;
        Date end = new Date();
        if (!StringUtils.isBlank(startDate)) {
            try {
                start = sdf.parse(startDate);
            } catch (ParseException e) {
                return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.STATISTICDATE_EROOR.getCode(),
                        ResponseCodeEvaluatingEnum.STATISTICDATE_EROOR.getDesc()));
            }
        }
        if (!StringUtils.isBlank(endDate)) {
            try {
                end = sdf.parse(endDate);
            } catch (ParseException e) {
                return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.STATISTICDATE_EROOR.getCode(),
                        ResponseCodeEvaluatingEnum.STATISTICDATE_EROOR.getDesc()));
            }
        }
        return statisticService.statisticEvaluating(userId, start, end);
    }

    /**
     * @Description 工资分页显示
     * @Param [pageNum, pageSize, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/10
     * @Time 10:22
     */
    @ApiOperation(value = "工资分页显示", notes = "工资分页显示")
    @RequestMapping(value = "/selectStatisticsList", method = RequestMethod.POST)
    public ResponseResult selectStatisticsList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String access_token) {
        return statisticService.selectStatisticsList(pageNum, pageSize);
    }

    /**
     * @Description 个人工资统计
     * @Param [salesmanID, date]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/10
     * @Time 10:22
     */
    @ApiOperation(value = "个人工资统计", notes = "个人工资统计")
    @RequestMapping(value = "/salesmanStatistic", method = RequestMethod.POST)
    public ResponseResult salesmanStatistic(Long salesmanID, String startDate, String endDate) {
        if (salesmanID == null) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.SALESMAN_ID_NULL.getCode(),
                    ResponseCodePerformanceEnum.SALESMAN_ID_NULL.getDesc()));
        }
        Statistic statistic = new Statistic();
        //查看时间范围内每种业绩 对于此员工 的业绩总和
        ResponseResult responseResultYeJi = statisticService.selectYeJiSumByDate(statistic, salesmanID, startDate, endDate);
        if (!responseResultYeJi.isSuccess()) {
            return responseResultYeJi;
        }
        //查看此员工的所得分数
        ResponseResult responseResultScore = statisticService.selectScore(salesmanID, ((Statistic) responseResultYeJi.getResult()).getPerformanceMap(), statistic);
        //根据得分计算员工的底薪
        ResponseResult responseResultBasicSalary = statisticService.selectBasicSalary(salesmanID, ((Statistic) responseResultScore.getResult()).getScore(), (Statistic) responseResultScore.getResult());
        //根据业绩查看业绩指标
        ResponseResult responseResultTiCheng = statisticService.responseResultTiCheng(((Statistic) responseResultYeJi.getResult()).getPerformanceMap(), (Statistic) responseResultBasicSalary.getResult());
        //计算总工资
        Statistic statisticResult = (Statistic) responseResultTiCheng.getResult();
        BigDecimal basicSalary = statisticResult.getBaseSalary();
        if (statisticResult.getBaseSalary() == null) {
            basicSalary = new BigDecimal(0);
            statisticResult.setBaseSalary(new BigDecimal(0));
        }
        BigDecimal tiCheng = statisticResult.getTakePercentage();
        BigDecimal salary = basicSalary.add(tiCheng);
        statisticResult.setSalary(salary);

        if (!responseResultScore.isSuccess()) {
            return responseResultScore;
        }

        return ResponseResult.success(statisticResult);
    }

    @ApiOperation(value = "个人业绩汇总统计", notes = "个人业绩汇总统计")
    @RequestMapping(value = "/yejiHuiZongStatistic", method = RequestMethod.POST)
    public ResponseResult yejiHuiZongStatistic(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                               Long salesmanID,
                                               String startDate,
                                               String endDate) {
        if (salesmanID == null) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.SALESMAN_ID_NULL.getCode(),
                    ResponseCodePerformanceEnum.SALESMAN_ID_NULL.getDesc()));
        }
        Statistic statistic = new Statistic();
        //查看时间范围内每种业绩 对于此员工 的业绩总和
        statisticService.selectYeJiSumByDate(statistic, salesmanID, startDate, endDate);
        HashMap<String, Object> performanceMap = statistic.getPerformanceMap();
        List<PerformancePost> performancePostList = iPerformanceDao.selectPerformancePostListAll();
        List<Map> mapList = new ArrayList<>();
        for (Map.Entry entry : performanceMap.entrySet()) {
            Map map = new HashMap();
            for (PerformancePost performancePost : performancePostList) {
                if (entry.getKey().toString().equals(performancePost.getId().toString())) {
                    map.put("achievementPostName", performancePost.getAchievementPostName());
                    map.put("amountOrNumber", entry.getValue());
                    mapList.add(map);
                }
            }
        }


        Map map1 = new HashMap();
        Map mapRsultPage = ListPageUntil.listPage(mapList, pageSize, pageNum);
        map1.put("list", mapRsultPage.get("list"));
        map1.put("total", mapRsultPage.get("total"));

        return ResponseResult.success(map1);
    }

    @ApiOperation(value = "个人业绩明细查询统计", notes = "个人业绩明细查询统计")
    @RequestMapping(value = "/yejiMingXiStatistic", method = RequestMethod.POST)
    public ResponseResult yejiMingXiStatistic(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                              Long salesmanID,
                                              String startDate,
                                              String endDate) {
        if (salesmanID == null) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.SALESMAN_ID_NULL.getCode(),
                    ResponseCodePerformanceEnum.SALESMAN_ID_NULL.getDesc()));
        }
        Statistic statistic = new Statistic();
        //查看时间范围内每种业绩 对于此员工 的业绩总和
        statisticService.selectYeJiSumByDate(statistic, salesmanID, startDate, endDate);
        String ladderDetailedListStr = statistic.getLadderDetailed();
        String[] ladderDetailedList = ladderDetailedListStr.replaceAll("]", "").replace("[", "").split(",");
        List<PerformancePost> performancePostList = iPerformanceDao.selectPerformancePostListAll();
        List<LadderDetailed> ladderDetailedListReuslt = iPerformanceDao.selectLadderDetailedList(new HashMap());
        List<Map> mapList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (String ladderDetailedId : ladderDetailedList) {
            Map map = new HashMap();
            for (LadderDetailed ladderDetailed : ladderDetailedListReuslt) {
                if (ladderDetailedId.equals(ladderDetailed.getLadderDetailedID().toString())) {
                    map.put("id", ladderDetailed.getLadderDetailedID());
                    map.put("ladderDetailedAmount", ladderDetailed.getLadderDetailedAmount());
                    map.put("ladderDetailedNumber", ladderDetailed.getLadderDetailedNumber());
                    map.put("ladderDetailedDatetime", simpleDateFormat.format(ladderDetailed.getLadderDetailedDatetime()));
                    map.put("ladderDetailedAchievementID",ladderDetailed.getLadderDetailedAchievementID());
                    mapList.add(map);
                }
            }
        }
        for (Map map : mapList) {
            for (PerformancePost performancePost : performancePostList) {
                if (performancePost.getId().toString().equals(map.get("ladderDetailedAchievementID").toString())){
                    map.put("achievementPostName",performancePost.getAchievementPostName());
                }
            }
        }


        Map map1 = new HashMap();
        Map mapRsultPage = ListPageUntil.listPage(mapList, pageSize, pageNum);
        map1.put("list", mapRsultPage.get("list"));
        map1.put("total", mapRsultPage.get("total"));

        return ResponseResult.success(map1);
    }

    /**
     * @Description
     * @Param [storeId]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/10/15
     * @Time
     */
    @ApiOperation(value = "统计工资首页柱状显示", notes = "统计工资首页柱状显示")
    @RequestMapping(value = "/selectSalaryList", method = RequestMethod.POST)
    public ResponseResult selectSalaryList(String storeId) {
        return statisticService.selectSalaryList(storeId);
    }


}
