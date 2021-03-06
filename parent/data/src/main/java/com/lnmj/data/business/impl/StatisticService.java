package com.lnmj.data.business.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.AchievementMethodEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeBeauticianEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeEvaluatingEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodePerformanceEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.Enum.AchievementStatusEnum;
import com.lnmj.common.Enum.AchievementStoreEnum;
import com.lnmj.data.business.IStatisticService;
import com.lnmj.data.entity.*;
import com.lnmj.data.repository.*;
import com.lnmj.data.serviceProxy.StoreApi;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Author: yilihua
 * @Date: 2019/9/6 10:53
 * @Description: 统计
 */
@Service("statisticService")
public class StatisticService implements IStatisticService {
    @Resource
    private IStatisticDao statisticDao;
    @Resource
    private StoreApi storeApi;
    @Resource
    private IScoreDao scoreDao;
    @Resource
    private IBaseSalaryDao baseSalaryDao;
    @Resource
    private IPerformanceDao performanceDao;
    @Resource
    private IEvaluatingDao evaluatingDao;

    @Override
    public ResponseResult selectStatisticsList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Statistic> list = statisticDao.selectStatisticsList();
        if (list.size() > 0 && list != null) {
            PageInfo<Statistic> pageInfo = new PageInfo(list);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodePerformanceEnum.LIST_IS_NULL.getCode(),
                ResponseCodePerformanceEnum.LIST_IS_NULL.getDesc()));
    }

    @Override
    public ResponseResult insertStatistic(Statistic statistic) {
        statisticDao.addStatistics(statistic);
        return ResponseResult.success(statistic);
    }


    //计算业绩和提成
    private void setPerformance(List<LadderDetailed> ladderDetailedList, List<PerformancePost> performancePostList,
                                Statistic statistic, List<Ladder> ladderList, HashMap<String, Object> performanceMap, Long postCategory, Integer isBasicSalary) {
        if (ladderDetailedList != null && ladderDetailedList.size() > 0) {
            //业绩明细ID
            List<Long> detailedIdList = new ArrayList<>();
            //单个提成
            HashMap<Long, BigDecimal> onePerformanceMap = new HashMap<>();
            //总业绩
            HashMap<Long, BigDecimal> allPerformanceMap = new HashMap<>();
            //总业绩(业绩的阶梯提成金额)
            HashMap<Long, BigDecimal> allPerformanceLadderMap = new HashMap();
            //总个数（业绩的阶梯提成个数）
            HashMap<Long, BigDecimal> allNumberLadderMap = new HashMap();
            for (int i = 0; i < ladderDetailedList.size(); i++) {
                LadderDetailed detailed = ladderDetailedList.get(i);
                Long ladderDetailedID = detailed.getLadderDetailedID();
                detailedIdList.add(ladderDetailedID);
                //业绩postID
                Long achievementPostID = detailed.getLadderDetailedAchievementID();
                if (achievementPostID == null) {
                    continue;
                }
                //业绩post
                PerformancePost performancePost = null;
                for (PerformancePost performancePost1 : performancePostList) {
                    if (achievementPostID.equals(performancePost1.getId())) {
                        performancePost = performancePost1;
                    }
                }
                if (performancePost == null) {    //人员职位分类
                    continue;
                }
                if (!(postCategory.equals(performancePost.getAchievementPostCategoryID().longValue()))) {    //人员职位分类
                    continue;
                }
                if (!(isBasicSalary.equals(performancePost.getIsBasicSalary()))) {    //职位分类是否有底薪
                    continue;
                }
                //业绩明细的有效期（储值业绩有效期）
                Date ladderDetailedDatetime = ladderDetailedList.get(i).getLadderDetailedDatetime();
                Integer interval = performancePost.getAchievementInterval();    //业绩有效期（天）
                Date now = new Date();
                int days = (int) ((now.getTime() - ladderDetailedDatetime.getTime()) / (1000 * 3600 * 24));
                if (interval != null && interval > 0) {  //如果是有有效期
                    if (days > interval) {  //相隔天数比有效期天数大，则过期了不统计业绩
                        continue;
                    }
                }
                //业绩提成方式
                Integer achievementMethods = performancePost.getAchievementMethods();
                //单个提成
                if (achievementMethods.equals(AchievementMethodEnum.NUMBER_ROYALTY.getCode())) {
                    BigDecimal ladderDetailedNumber = detailed.getLadderDetailedNumber();
                    BigDecimal add = ladderDetailedNumber;
                    if (onePerformanceMap.get(achievementPostID) != null) {
                        add = ladderDetailedNumber.add(onePerformanceMap.get(achievementPostID));
                    }
                    onePerformanceMap.put(achievementPostID, add);
                }
                //总业绩提成
                if (achievementMethods.equals(AchievementMethodEnum.ALL_ACHIEVEMENT_ROYALTY.getCode())) {
                    BigDecimal ladderDetailedAmount = detailed.getLadderDetailedAmount();
                    BigDecimal add = ladderDetailedAmount;
                    if (onePerformanceMap.get(achievementPostID) != null) {
                        add = ladderDetailedAmount.add(onePerformanceMap.get(achievementPostID));
                    }
                    allPerformanceMap.put(achievementPostID, add);
                }
                //总业绩阶梯提成
                if (achievementMethods.equals(AchievementMethodEnum.ALL_ACHIEVEMENT_LADDER_ROYALTY.getCode())) {
                    BigDecimal ladderDetailedAmount = detailed.getLadderDetailedAmount();
                    BigDecimal add = ladderDetailedAmount;
                    if (onePerformanceMap.get(achievementPostID) != null) {
                        add = ladderDetailedAmount.add(onePerformanceMap.get(achievementPostID));
                    }
                    allPerformanceLadderMap.put(achievementPostID, add);
                }
                //个数阶梯提成
                if (achievementMethods.equals(AchievementMethodEnum.NUMBER_LADDER_ROYALTY.getCode())) {
                    BigDecimal ladderDetailedNumber = detailed.getLadderDetailedNumber();
                    BigDecimal add = ladderDetailedNumber;
                    if (onePerformanceMap.get(achievementPostID) != null) {
                        add = ladderDetailedNumber.add(onePerformanceMap.get(achievementPostID));
                    }
                    allNumberLadderMap.put(achievementPostID, add);
                }
            }
            //业绩
            //个数提成(个数)
            BigDecimal performance1 = getPerformance(onePerformanceMap, performanceMap);
            //个数阶梯
            BigDecimal performance2 = getPerformance(allNumberLadderMap, performanceMap);
            //总业绩提成（金额）
            BigDecimal performance3 = getPerformance(allPerformanceMap, performanceMap);
            //总业绩阶梯
            BigDecimal performance4 = getPerformance(allPerformanceLadderMap, performanceMap);
            if (performance1 != null) {
                statistic.setNumberPerformance(performance1);
            }
            if (performance2 != null) {
                statistic.setNumberPerformance(performance2.add(statistic.getNumberPerformance()));
            }
            if (performance3 != null) {
                statistic.setAmountPerformance(performance3);
            }
            if (performance4 != null) {
                statistic.setAmountPerformance(performance4.add(statistic.getAmountPerformance()));
            }
            //提成
            BigDecimal takePercentage1 = getTakePercentage(onePerformanceMap, ladderList, AchievementMethodEnum.NUMBER_ROYALTY.getCode());
            BigDecimal takePercentage2 = getTakePercentage(allPerformanceMap, ladderList, AchievementMethodEnum.ALL_ACHIEVEMENT_ROYALTY.getCode());
            BigDecimal takePercentage3 = getTakePercentage(allPerformanceLadderMap, ladderList, AchievementMethodEnum.ALL_ACHIEVEMENT_LADDER_ROYALTY.getCode());
            BigDecimal takePercentage4 = getTakePercentage(allNumberLadderMap, ladderList, AchievementMethodEnum.NUMBER_LADDER_ROYALTY.getCode());
            if (takePercentage1 != null) {
                statistic.setTakePercentage(takePercentage1);
            }
            if (takePercentage2 != null) {
                if (statistic.getTakePercentage() != null) {
                    statistic.setTakePercentage(takePercentage2.add(statistic.getTakePercentage()));
                } else {
                    statistic.setTakePercentage(takePercentage2);
                }
            }
            if (takePercentage3 != null) {
                if (statistic.getTakePercentage() != null) {
                    statistic.setTakePercentage(takePercentage3.add(statistic.getTakePercentage()));
                } else {
                    statistic.setTakePercentage(takePercentage3);
                }
            }
            if (takePercentage4 != null) {
                if (statistic.getTakePercentage() != null) {
                    statistic.setTakePercentage(takePercentage4.add(statistic.getTakePercentage()));
                } else {
                    statistic.setTakePercentage(takePercentage4);
                }
            }
            statistic.setLadderDetailed(detailedIdList.toString());
        } else {
            performanceMap.put("flag", 3);   //没有业绩统计
        }
        statistic.setPerformanceMap(performanceMap);
    }

    //提成
    private BigDecimal getTakePercentage(HashMap<Long, BigDecimal> map, List<Ladder> ladderList, Integer method) {
        BigDecimal result = BigDecimal.ZERO;
        //单个提成
        if (method.equals(AchievementMethodEnum.NUMBER_ROYALTY.getCode())) {
            //遍历业绩的map
            Set<Long> set = map.keySet();
            for (Long aId : set) {
                //寻找阶梯中业绩的提成方式
                for (Ladder ladder : ladderList) {
                    if (ladder.getLadderAchievementPostID().equals(aId)) {
                        BigDecimal number = map.get(aId);
                        result = result.add(number.multiply(ladder.getLadderBonus()));
                        break;
                    }
                }
            }
        }
        //总业绩提成
        if (method.equals(AchievementMethodEnum.ALL_ACHIEVEMENT_ROYALTY.getCode())) {
            //遍历业绩的map
            Set<Long> set = map.keySet();
            for (Long aId : set) {
                //寻找阶梯中业绩的提成方式
                for (Ladder ladder : ladderList) {
                    if (ladder.getLadderAchievementPostID().equals(aId)) {
                        BigDecimal number = map.get(aId);
                        result = result.add(number.multiply(ladder.getLadderProportion()));
                        break;
                    }
                }
            }
        }
        //总业绩阶梯提成
        if (method.equals(AchievementMethodEnum.ALL_ACHIEVEMENT_LADDER_ROYALTY.getCode())) {
            Set<Long> set = map.keySet();
            for (Long aId : set) {
                //筛选业绩阶梯
                List<Ladder> ladders = new ArrayList<>();
                for (Ladder ladder : ladderList) {
                    if (aId.equals(ladder.getLadderAchievementPostID())) {
                        ladders.add(ladder);
                    }
                }
                //排序(升序)
                Collections.sort(ladders, new Comparator<Ladder>() {
                    @Override
                    public int compare(Ladder o1, Ladder o2) {
                        return o1.getLadderLower().compareTo(o2.getLadderLower());
                    }
                });
                //金额
                BigDecimal value = map.get(aId);
                Ladder ladder = ladders.get(0); //赋值为最小的
                for (int j = 0; j < ladders.size() - 1; j++) {
                    int c1 = value.compareTo(new BigDecimal(ladders.get(j).getLadderLower()));
                    int c2 = value.compareTo(new BigDecimal(ladders.get(j + 1).getLadderLower()));
                    //比最小的小
                    if (c1 < 0) {
                        continue;
                    }
                    //中间
                    if (c1 > 0 && c2 < 0) {
                        ladder = ladders.get(j);
                    }
                    //比最大的大
                    if (c1 > 0 && c2 > 0) {
                        ladder = ladders.get(j + 1);
                    }
                }
                //金额
                result = value.multiply(ladder.getLadderProportion());
            }
        }
        //个数阶梯提成
        if (method.equals(AchievementMethodEnum.NUMBER_LADDER_ROYALTY.getCode())) {
            Set<Long> set = map.keySet();
            for (Long aId : set) {
                //筛选业绩阶梯
                List<Ladder> ladders = new ArrayList<>();
                for (Ladder ladder : ladderList) {
                    if (aId.equals(ladder.getLadderAchievementPostID())) {
                        ladders.add(ladder);
                    }
                }
                //排序(升序)
                Collections.sort(ladders, new Comparator<Ladder>() {
                    @Override
                    public int compare(Ladder o1, Ladder o2) {
                        return o1.getLadderLower().compareTo(o2.getLadderLower());
                    }
                });
                //个数
                BigDecimal value = map.get(aId);
                Ladder ladder = ladders.get(0); //赋值为最小的
                for (int j = 0; j < ladders.size() - 1; j++) {
                    int c1 = value.compareTo(new BigDecimal(ladders.get(j).getLadderLower()));
                    int c2 = value.compareTo(new BigDecimal(ladders.get(j + 1).getLadderLower()));
                    //比最小的小
                    if (c1 < 0) {
                        continue;
                    }
                    //中间
                    if (c1 > 0 && c2 < 0) {
                        ladder = ladders.get(j);
                    }
                    //比最大的大
                    if (c1 > 0 && c2 > 0) {
                        ladder = ladders.get(j + 1);
                    }
                }
                //个数
                result = value.multiply(ladder.getLadderBonus());
            }
        }
        return result;
    }

    //业绩
    private BigDecimal getPerformance(Map<Long, BigDecimal> map, HashMap<String, Object> performanceMap) {
        BigDecimal performance = BigDecimal.ZERO;
        //遍历业绩的map
        Set<Long> set = map.keySet();
        for (Long aId : set) {
            BigDecimal value = map.get(aId);
            performanceMap.put(Long.toString(aId), value);
            //个数相加
            performance = performance.add(value);
        }
        return performance;
    }

    //检查业绩是否被统计
    private void checkSalesmanPerformance(Long salesmanID, Date start, Date end, List<LadderDetailed> ladderDetailedList
            , HashMap<String, Object> performanceMap) {
        //查询员工已经被统计的记录(统计开始时间从这次统计开始时间开始被统计的业绩)
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("salesmanID", salesmanID);
        map1.put("start", start);
        List<Statistic> statisticList = statisticDao.selectStatisticsBySalesman(map1);
        List<Long> ladderDetailedIdList = new ArrayList<>();
        if (statisticList == null || statisticList.size() == 0) {
            performanceMap.put("flag", 1);
            return;
        }
        for (Statistic statistic : statisticList) {
            Date statisticDateEnd = statistic.getStatisticDateEnd();
            if (end.compareTo(statisticDateEnd) <= 0) {
                ladderDetailedList.clear();
                performanceMap.put("flag", 2);
                return;
            }
            String ladderDetailed = statistic.getLadderDetailed();
            if (StringUtils.isBlank(ladderDetailed)) {
                continue;
            }
            JSONArray array = JSONObject.parseArray(ladderDetailed);
            List<Long> longList = array.toJavaList(Long.class);
            ladderDetailedIdList.addAll(longList);
        }
        //去重
        Set set = new HashSet();
        set.addAll(ladderDetailedList);
        ladderDetailedList.clear();
        ladderDetailedList.addAll(set);
        //ladderDetailedList 去掉被统计的业绩
        Iterator<LadderDetailed> iterator = ladderDetailedList.iterator();
        while (iterator.hasNext()) {
            LadderDetailed detailed = iterator.next();
            if (ladderDetailedIdList.contains(detailed.getLadderDetailedID())) {
                iterator.remove();
            }
        }
        performanceMap.put("flag", 1);
        return;
    }


    @Override
    public ResponseResult selectSalaryList(String storeId) {
        List<Long> id = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<BigDecimal> dixin = new ArrayList<>();
        List<BigDecimal> ticheng = new ArrayList<>();
        List<BigDecimal> total = new ArrayList<>();
        HashMap<Object, Object> map = new HashMap<>();
        ResponseResult result = storeApi.selectBeauticianByStoreIdArray(storeId);
        List<Map<Object, Object>> beauticians = (List<Map<Object, Object>>) result.getResult();
        if (beauticians == null || beauticians.size() == 0) {
            map.put("idList", id);
            map.put("nameList", name);
            map.put("dixinList", dixin);
            map.put("tichengList", ticheng);
            map.put("totalList", total);
            return ResponseResult.success(map);
        }
        List<Statistic> statisticList = statisticDao.selectStatisticsList();
        for (int j = 0; j < beauticians.size(); j++) {
//            SalaryVO salaryVO = new SalaryVO();
            Map<Object, Object> objectObjectMap = beauticians.get(j);
            Integer i = (Integer) objectObjectMap.get("beauticianId");
            long beauticianId = i.longValue();
            id.add(beauticianId);
            name.add(objectObjectMap.get("name").toString());
            dixin.add(null);
            ticheng.add(null);
            total.add(null);
            for (Statistic statistic : statisticList) {
                if (statistic.getSalesmanID().equals(beauticianId)) {
                    dixin.set(j, statistic.getBaseSalary());
                    ticheng.set(j, statistic.getTakePercentage());
                    total.set(j, statistic.getSalary());
                }
            }
        }
        map.put("idList", id);
        map.put("nameList", name);
        map.put("dixinList", dixin);
        map.put("tichengList", ticheng);
        map.put("totalList", total);
        return ResponseResult.success(map);
    }


    @Override
    public ResponseResult statisticEvaluating(Long userId, Date start, Date end) {
        //所有测评
        List<Evaluating> evaluatingList = evaluatingDao.selectEvaluatingList(new HashMap());
        if (evaluatingList == null || evaluatingList.size() == 0) {
            return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.EVALUATING_NULL.getCode(),
                    ResponseCodeEvaluatingEnum.EVALUATING_NULL.getDesc()));
        }
        List<UserEvaluating> userEvaluatingList = new ArrayList<>();
        //测评明细ID
        List<Long> longList = new ArrayList<>();
        //测评金额或个数
        BigDecimal evaluatingAmount = BigDecimal.ZERO;
        Long levelId = new Long(0);
        String LevelName = "";
        //测评统计
        for (Evaluating evaluating : evaluatingList) {
            Long evaluatingID = evaluating.getEvaluatingID();
            //客户测评统计表
            UserEvaluating userEvaluating = new UserEvaluating();
            userEvaluating.setUserId(userId);
            userEvaluating.setEvaluatingID(evaluatingID);
            userEvaluating.setEvaluatingName(evaluating.getEvaluatingName());
            userEvaluating.setEvaluatingDateEnd(end);
            //查询测评等级（按lower顺序排列）
            EvaluatingLevel evaluatingLevel = new EvaluatingLevel();
            evaluatingLevel.setEvaluatingLevelEvaluatingID(evaluatingID);
            List<EvaluatingLevel> evaluatingLevelList = evaluatingDao.selectEvaluatingLevelByCondition(evaluatingLevel);
            if (evaluatingLevelList == null || evaluatingLevelList.size() == 0) {
                //没有测评等级不做如下操作
                continue;
            }
            if (start == null) {
                //如果没有传开始时间，直接根据测评ID去获得天数获得开始时间
                for (EvaluatingLevel level : evaluatingLevelList) {
                    Integer evaluatingLevelDate = level.getEvaluatingLevelDate();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(end);
                    calendar.add(Calendar.DATE, -(evaluatingLevelDate.intValue()));
                    Date start1 = calendar.getTime();
                    //获取员工的测评明细
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("userId", userId);
                    map.put("evaluatingID", evaluatingID);
                    map.put("start", start1);
                    map.put("end", end);
                    List<EvaluatingDetailed> evaluatingDetailedList = evaluatingDao.selectEvaluatingDetailedByUserIdAndDate(map);
                    if (evaluatingDetailedList == null || evaluatingDetailedList.size() == 0) {
                        //没有测评明细无测评等级
//                        userEvaluating.setEvaluatingDetailed(null);
//                        userEvaluating.setEvaluatingNumber(null);
//                        userEvaluating.setEvaluatingAmount(null);
//                        userEvaluating.setEvaluatingLevelID(null);
//                        userEvaluating.setEvaluatingLevelName(null);
//                        statisticDao.addUserEvaluating(userEvaluating);
//                        userEvaluatingList.add(userEvaluating);
                        continue;
                    }
                    //计算现在的测评金额或个数
                    List<Long> longList1 = new ArrayList<>();
                    BigDecimal evaluatingAmount1 = BigDecimal.ZERO;
                    for (EvaluatingDetailed evaluatingDetailed : evaluatingDetailedList) {
                        longList1.add(evaluatingDetailed.getEvaluatingDetailedID());
                        BigDecimal evaluatingDetailedAmount = evaluatingDetailed.getEvaluatingDetailedAmount();
                        if (evaluatingDetailedAmount.compareTo(BigDecimal.ZERO) > 0) {
                            evaluatingAmount1 = evaluatingDetailedAmount.add(evaluatingAmount1);
                        }
                    }
                    //测评金额或等级
                    BigDecimal lower = BigDecimal.valueOf(level.getEvaluatingLevelLower().doubleValue());
                    int i = evaluatingAmount1.compareTo(lower);
                    if (i < 0) {
                        continue;
                    }
                    start = start1;
                    longList = longList1;
                    evaluatingAmount = evaluatingAmount1;
                    levelId = level.getEvaluatingLevelID();
                    LevelName = level.getEvaluatingLevelName();
                }
            } else {
                //获取员工的测评明细
                HashMap<String, Object> map = new HashMap<>();
                map.put("userId", userId);
                map.put("evaluatingID", evaluatingID);
                map.put("start", start);
                map.put("end", end);
                List<EvaluatingDetailed> evaluatingDetailedList = evaluatingDao.selectEvaluatingDetailedByUserIdAndDate(map);
                if (evaluatingDetailedList == null || evaluatingDetailedList.size() == 0) {
                    //没有测评明细无测评等级
                    continue;
                }
                //计算现在的测评金额或个数
                for (EvaluatingDetailed evaluatingDetailed : evaluatingDetailedList) {
                    longList.add(evaluatingDetailed.getEvaluatingDetailedID());
                    BigDecimal evaluatingDetailedAmount = evaluatingDetailed.getEvaluatingDetailedAmount();
                    if (evaluatingDetailedAmount.compareTo(BigDecimal.ZERO) > 0) {
                        evaluatingAmount = evaluatingDetailedAmount.add(evaluatingAmount);
                    }
                }
                levelId = evaluatingLevelList.get(0).getEvaluatingLevelID();
                LevelName = evaluatingLevelList.get(0).getEvaluatingLevelName();
                for (EvaluatingLevel level : evaluatingLevelList) {
                    BigDecimal lower = BigDecimal.valueOf(level.getEvaluatingLevelLower().doubleValue());
                    int i = evaluatingAmount.compareTo(lower);
                    if (i < 0) {
                        break;
                    }
                    levelId = level.getEvaluatingLevelID();
                    LevelName = level.getEvaluatingLevelName();
                }
            }
            userEvaluating.setEvaluatingDateStart(start);
            userEvaluating.setEvaluatingDetailed(longList.toString());
            if (evaluating.getEvaluatingMethods().equals(AchievementMethodEnum.NUMBER_ROYALTY.getCode())) {
                userEvaluating.setEvaluatingNumber(evaluatingAmount);
            } else {
                userEvaluating.setEvaluatingAmount(evaluatingAmount);
            }
            userEvaluating.setEvaluatingLevelID(levelId);
            userEvaluating.setEvaluatingLevelName(LevelName);
            //查询用户该测评是否有数据
            HashMap<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            map.put("evaluatingID", evaluatingID);
            List<UserEvaluating> userEvaluatingList1 = statisticDao.selectUserEvaluatingByUser(map);
            if (userEvaluatingList1 == null || userEvaluatingList1.size() == 0) {
                //将数据插入到数据库
                statisticDao.addUserEvaluating(userEvaluating);
            } else {
                //更新数据库
                statisticDao.updateUserEvaluating(userEvaluating);
            }
            userEvaluatingList.add(userEvaluating);
        }
        return ResponseResult.success(userEvaluatingList);
    }

    @Override
    public ResponseResult selectUserEvaluatingList(int pageNum, int pageSize, Long evaluatingLevelID, Long evaluatingID) {
        PageHelper.startPage(pageNum, pageSize);
        UserEvaluating userEvaluating = new UserEvaluating();
        userEvaluating.setEvaluatingLevelID(evaluatingLevelID);
        userEvaluating.setEvaluatingID(evaluatingID);
        List<UserEvaluating> userEvaluatingList = statisticDao.selectUserEvaluatingList(userEvaluating);
        if (userEvaluatingList != null && userEvaluatingList.size() > 0) {
            PageInfo<UserEvaluating> pageInfo = new PageInfo<>(userEvaluatingList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.LIST_IS_NULL.getCode(),
                ResponseCodeEvaluatingEnum.LIST_IS_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectYeJiSumByDateAllStore(Statistic statistic, Long salesmanID, String startDate, String endDate) {
        //查看所有业绩方式
        List<PerformancePost> performancePostList = performanceDao.selectPerformancePostList(new HashMap());
        Map mapStaff = (Map) (storeApi.selectBeauticianByCode(salesmanID.toString()).getResult());
        //查询时间范围内当前员工所在门店所有的业绩明细
        Map mapLadderDetailedSelect = new HashMap();
        mapLadderDetailedSelect.put("ladderDetailedStoreId", mapStaff.get("companyId"));
        mapLadderDetailedSelect.put("noLadderDetailedBeauticianId", salesmanID);
        mapLadderDetailedSelect.put("startDate", startDate);
        mapLadderDetailedSelect.put("endDate", endDate);
        List<LadderDetailed> ladderDetailedList = performanceDao.selectLadderDetailedList(mapLadderDetailedSelect);
        HashMap map = new HashMap();
        Double yejiAllSum = 0.00;
        Integer geshuAllSum = 0;
        List<Long> stringList = new ArrayList<>();
        for (PerformancePost performancePost : performancePostList) {
            Double yejiSum = 0.00;
            Integer geshuSum = 0;
            for (LadderDetailed ladderDetailed : ladderDetailedList) {
                if (performancePost.getId().toString().equals(ladderDetailed.getLadderDetailedAchievementID().toString())) {
                    yejiSum = yejiSum + ladderDetailed.getLadderDetailedAmount().doubleValue();
                    geshuSum = geshuSum + ladderDetailed.getLadderDetailedNumber().intValue();
                    stringList.add(ladderDetailed.getLadderDetailedID());
                }
            }
            if (performancePost.getAchievementMethods() == AchievementMethodEnum.NUMBER_ROYALTY.getCode() |
                    performancePost.getAchievementMethods() == AchievementMethodEnum.NUMBER_LADDER_ROYALTY.getCode()) {
                if (geshuSum != 0) {
                    map.put(performancePost.getId().toString(), geshuSum);
                }
            } else {
                if (yejiSum != 0) {
                    map.put(performancePost.getId().toString(), yejiSum);
                }
            }


            yejiAllSum = yejiAllSum + yejiSum;
            geshuAllSum = geshuAllSum + geshuSum;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        statistic.setPerformanceMapAllStore(map);
        try {
            statistic.setStatisticDateStart(sdf.parse(startDate));
            statistic.setStatisticDateEnd(sdf.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        statistic.setAmountPerformanceAllStore(new BigDecimal(yejiAllSum));
        statistic.setNumberPerformanceAllStore(new BigDecimal(geshuAllSum));
        statistic.setLadderDetailedAllStore(stringList.toString().replaceAll(" ", ""));
        statistic.setSalesmanID(salesmanID);

        statistic.setAmountPerformanceGroup(new BigDecimal(0));
        statistic.setNumberPerformanceGroup(new BigDecimal(0));
        return ResponseResult.success(statistic);
    }

    @Override
    public ResponseResult selectYeJiSumByDateGroup(Statistic statistic, Long salesmanID, String startDate, String endDate) {
        //查看所有业绩方式
        List<PerformancePost> performancePostList = performanceDao.selectPerformancePostList(new HashMap());
        Map mapStaff = (Map) (storeApi.selectBeauticianByCode(salesmanID.toString()).getResult());
        //获取组id
        Long groupId = Long.parseLong(mapStaff.get("groupId").toString());
        Long beauticianId = Long.parseLong(mapStaff.get("beauticianId").toString());
        //查看组员
        List<Map> groupMemberListMap = (List<Map>) storeApi.selectGroupMember(groupId, beauticianId).getResult();
        List<String> listStaffNumber = new ArrayList<>();
        for (Map map : groupMemberListMap) {
            listStaffNumber.add(map.get("staffNumber").toString());
        }

        //查询时间范围内当前员工所在门店当前分组业绩明细
        Map mapLadderDetailedSelect = new HashMap();
        mapLadderDetailedSelect.put("ladderDetailedStoreId", mapStaff.get("companyId"));
        mapLadderDetailedSelect.put("noLadderDetailedBeauticianId", salesmanID);
        mapLadderDetailedSelect.put("listBeauticianId", listStaffNumber);
        mapLadderDetailedSelect.put("startDate", startDate);
        mapLadderDetailedSelect.put("endDate", endDate);
        List<LadderDetailed> ladderDetailedList = new ArrayList<>();
        if (listStaffNumber.size() != 0) {
            //如果有组员
            ladderDetailedList = performanceDao.selectLadderDetailedList(mapLadderDetailedSelect);
        }
        HashMap map = new HashMap();
        Double yejiAllSum = 0.00;
        Integer geshuAllSum = 0;
        List<Long> stringList = new ArrayList<>();
        for (PerformancePost performancePost : performancePostList) {
            Double yejiSum = 0.00;
            Integer geshuSum = 0;
            for (LadderDetailed ladderDetailed : ladderDetailedList) {
                if (performancePost.getId().toString().equals(ladderDetailed.getLadderDetailedAchievementID().toString())) {
                    yejiSum = yejiSum + ladderDetailed.getLadderDetailedAmount().doubleValue();
                    geshuSum = geshuSum + ladderDetailed.getLadderDetailedNumber().intValue();
                    stringList.add(ladderDetailed.getLadderDetailedID());
                }
            }
            if (performancePost.getAchievementMethods() == AchievementMethodEnum.NUMBER_ROYALTY.getCode() |
                    performancePost.getAchievementMethods() == AchievementMethodEnum.NUMBER_LADDER_ROYALTY.getCode()) {
                if (geshuSum != 0) {
                    map.put(performancePost.getId().toString(), geshuSum);
                }
            } else {
                if (yejiSum != 0) {
                    map.put(performancePost.getId().toString(), yejiSum);
                }
            }


            yejiAllSum = yejiAllSum + yejiSum;
            geshuAllSum = geshuAllSum + geshuSum;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        statistic.setPerformanceMapGroup(map);
        try {
            statistic.setStatisticDateStart(sdf.parse(startDate));
            statistic.setStatisticDateEnd(sdf.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        statistic.setAmountPerformanceGroup(new BigDecimal(yejiAllSum));
        statistic.setNumberPerformanceGroup(new BigDecimal(geshuAllSum));
        statistic.setLadderDetailedGroup(stringList.toString().replaceAll(" ", ""));
        statistic.setSalesmanID(salesmanID);

        statistic.setAmountPerformanceAllStore(new BigDecimal(0));
        statistic.setNumberPerformanceAllStore(new BigDecimal(0));
        return ResponseResult.success(statistic);
    }

    @Override
    public ResponseResult selectYeJiSumByDatePerson(Statistic statistic, Long salesmanID, String startDate, String endDate) {
        //查看所有业绩方式
        List<PerformancePost> performancePostList = performanceDao.selectPerformancePostList(new HashMap());
        //查询时间范围内当前员工的业绩明细
        Map mapLadderDetailedSelect = new HashMap();
        mapLadderDetailedSelect.put("ladderDetailedBeauticianId", salesmanID);
        mapLadderDetailedSelect.put("startDate", startDate);
        mapLadderDetailedSelect.put("endDate", endDate);

        List<LadderDetailed> ladderDetailedList = performanceDao.selectLadderDetailedList(mapLadderDetailedSelect);

        HashMap map = new HashMap();
        Double yejiAllSum = 0.00;
        Integer geshuAllSum = 0;
        List<Long> stringList = new ArrayList<>();
        for (PerformancePost performancePost : performancePostList) {
            Double yejiSum = 0.00;
            Integer geshuSum = 0;
            for (LadderDetailed ladderDetailed : ladderDetailedList) {
                if (performancePost.getId().toString().equals(ladderDetailed.getLadderDetailedAchievementID().toString())) {
                    yejiSum = yejiSum + ladderDetailed.getLadderDetailedAmount().doubleValue();
                    geshuSum = geshuSum + ladderDetailed.getLadderDetailedNumber().intValue();
                    stringList.add(ladderDetailed.getLadderDetailedID());
                }
            }
            if (performancePost.getAchievementMethods() == AchievementMethodEnum.NUMBER_ROYALTY.getCode() |
                    performancePost.getAchievementMethods() == AchievementMethodEnum.NUMBER_LADDER_ROYALTY.getCode()) {
                if (geshuSum != 0) {
                    map.put(performancePost.getId().toString(), geshuSum);
                }
            } else {
                if (yejiSum != 0) {
                    map.put(performancePost.getId().toString(), yejiSum);
                }
            }


            yejiAllSum = yejiAllSum + yejiSum;
            geshuAllSum = geshuAllSum + geshuSum;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        statistic.setPerformanceMap(map);
        try {
            statistic.setStatisticDateStart(sdf.parse(startDate));
            statistic.setStatisticDateEnd(sdf.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        statistic.setAmountPerformance(new BigDecimal(yejiAllSum));
        statistic.setNumberPerformance(new BigDecimal(geshuAllSum));
        statistic.setLadderDetailed(stringList.toString().replaceAll(" ", ""));
        statistic.setSalesmanID(salesmanID);
        return ResponseResult.success(statistic);
    }

    @Override
    public ResponseResult selectScorePerson(Long salesmanID, HashMap<String, Object> map, Statistic statistic) {
        if (map != null) {

            //查看此员工的职位分类
            Map mapStaff = (Map) storeApi.selectBeauticianByCode(salesmanID.toString()).getResult();
            if (mapStaff == null) {
                return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.BEAUTICIAN_NULL.getCode(),
                        ResponseCodeBeauticianEnum.BEAUTICIAN_NULL.getDesc()));
            }
            statistic.setStoreId(Long.parseLong(mapStaff.get("companyId").toString()));
            String postCategoryId = mapStaff.get("postCategoryId").toString();
            String partTimePostCategoryId = mapStaff.get("partTimePostCategoryId").toString();
            List<String> stringList = new ArrayList<>();
            stringList.add(postCategoryId);
            stringList.add(partTimePostCategoryId);
            //根据职位分类 查看积分规则
            Map map1 = new HashMap();
            map1.put("list", stringList);
            List<Score> scoreList = scoreDao.selectScoreByPostId(map1);
            BigDecimal totalScore = new BigDecimal(0);
            if (scoreList.size() == 0) {
                totalScore = new BigDecimal(0);
            } else {
                //总分数
                Set<String> performanceIds = map.keySet();
                for (String performanceId : performanceIds) {
                    PerformancePost performancePost = performanceDao.selectPerformancePostById(Long.parseLong(performanceId));
                    if (performancePost.getIsBasicSalary() == 0) {
                        //如果不计算底薪
                        totalScore = totalScore.add(BigDecimal.ZERO);
                        continue;
                    }
                    for (Score score : scoreList) {
                        if (score.getScoreMode() == 1) {
                            //手动评分
                            continue;
                        }
                        if (score.getScoreAchievementID().equals(Long.valueOf(performanceId)) || score.getScoreAchievementID().toString().equals("0")) {
                            //传来的业绩id和评分标准的业绩id一致再计算评分
                            //每条业绩对应的金额
                            BigDecimal amount = null;
                            if (map.get(performanceId) instanceof Double) {
                                amount = new BigDecimal((Double) map.get(performanceId));
                            } else if (map.get(performanceId) instanceof Integer) {
                                amount = new BigDecimal((Integer) map.get(performanceId));
                            }
                            if (amount.compareTo(BigDecimal.ZERO) != 0) {
                                //计算业绩金额评分 金额业绩/业绩基数*分数基数
                                BigDecimal performanceScore = amount.subtract(score.getScoreProportion()).divide(score.getScoreBase(), BigDecimal.ROUND_UP).add(score.getScoreDefault());
                                if (performanceScore.doubleValue() != 0 && performanceScore.compareTo(score.getScoreLow()) == -1) {
                                    //如果评分小于最低分取最低分
                                    performanceScore = score.getScoreLow();
                                } else if (performanceScore.compareTo(score.getScoreHigh()) == 1) {
                                    //高于了最高分数 就取最高分
                                    performanceScore = score.getScoreHigh();
                                } else if (amount.doubleValue() < score.getScoreProportion().doubleValue()) {
                                    //如果业绩小于业绩基数按照默认业绩
                                    performanceScore = score.getScoreDefault();
                                }
                                //累加每条业绩的评分
                                totalScore = totalScore.add(performanceScore);
                            }
                        }
                    }
                }
            }
            statistic.setScore(totalScore.setScale(0, BigDecimal.ROUND_HALF_UP));

        }
        return ResponseResult.success(statistic);
    }

    @Override
    public ResponseResult selectScoreAllStore(Long salesmanID, HashMap<String, Object> map, Statistic statistic) {
        if (map != null) {
            //查看此员工的职位分类
            Map mapStaff = (Map) storeApi.selectBeauticianByCode(salesmanID.toString()).getResult();
            if (mapStaff == null) {
                return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.BEAUTICIAN_NULL.getCode(),
                        ResponseCodeBeauticianEnum.BEAUTICIAN_NULL.getDesc()));
            }
            statistic.setStoreId(Long.parseLong(mapStaff.get("companyId").toString()));
            String postCategoryId = mapStaff.get("postCategoryId").toString();
            String partTimePostCategoryId = mapStaff.get("partTimePostCategoryId").toString();
            List<String> stringList = new ArrayList<>();
            stringList.add(postCategoryId);
            stringList.add(partTimePostCategoryId);
            //根据职位分类 查看积分规则
            Map map1 = new HashMap();
            map1.put("list", stringList);
            List<Score> scoreList = scoreDao.selectScoreByPostId(map1);
            BigDecimal totalScore = new BigDecimal(0);
            if (scoreList.size() == 0) {
                totalScore = new BigDecimal(0);
            } else {
                //总分数
                Set<String> performanceIds = map.keySet();
                for (String performanceId : performanceIds) {
                    PerformancePost performancePost = performanceDao.selectPerformancePostById(Long.parseLong(performanceId));
                    if (performancePost.getIsBasicSalary() == 0) {
                        //如果不计算底薪
                        totalScore = totalScore.add(BigDecimal.ZERO);
                        continue;
                    }
                    for (Score score : scoreList) {
                        if (score.getScoreMode() == 1) {
                            //手动评分
                            continue;
                        }
                        if (score.getScoreAchievementID().equals(Long.valueOf(performanceId))) {
                            //传来的业绩id和评分标准的业绩id一致再计算评分
                            //每条业绩对应的金额
                            BigDecimal amount = null;
                            if (map.get(performanceId) instanceof Double) {
                                amount = new BigDecimal((Double) map.get(performanceId));
                            } else if (map.get(performanceId) instanceof Integer) {
                                amount = new BigDecimal((Integer) map.get(performanceId));
                            }
                            if (amount.compareTo(BigDecimal.ZERO) != 0) {
                                //计算业绩金额评分 金额业绩/业绩基数*分数基数
                                BigDecimal performanceScore = amount.subtract(score.getScoreProportion()).divide(score.getScoreBase(), BigDecimal.ROUND_UP).add(score.getScoreDefault());
                                if (performanceScore.doubleValue() != 0 && performanceScore.compareTo(score.getScoreLow()) == -1) {
                                    //如果评分小于最低分取最低分
                                    performanceScore = score.getScoreLow();
                                } else if (performanceScore.compareTo(score.getScoreHigh()) == 1) {
                                    //高于了最高分数 就取最高分
                                    performanceScore = score.getScoreHigh();
                                } else if (amount.doubleValue() < score.getScoreProportion().doubleValue()) {
                                    //如果业绩小于业绩基数按照默认业绩
                                    performanceScore = score.getScoreDefault();
                                }
                                //累加每条业绩的评分
                                totalScore = totalScore.add(performanceScore);
                            }
                        }
                    }
                }
            }
            statistic.setScoreAllStore(totalScore.setScale(0, BigDecimal.ROUND_HALF_UP));
        }

        return ResponseResult.success(statistic);
    }

    @Override
    public ResponseResult selectScoreGroup(Long salesmanID, HashMap<String, Object> map, Statistic statistic) {
        if (map != null) {
            //查看此员工的职位分类
            Map mapStaff = (Map) storeApi.selectBeauticianByCode(salesmanID.toString()).getResult();
            if (mapStaff == null) {
                return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.BEAUTICIAN_NULL.getCode(),
                        ResponseCodeBeauticianEnum.BEAUTICIAN_NULL.getDesc()));
            }
            statistic.setStoreId(Long.parseLong(mapStaff.get("companyId").toString()));
            String postCategoryId = mapStaff.get("postCategoryId").toString();
            String partTimePostCategoryId = mapStaff.get("partTimePostCategoryId").toString();
            List<String> stringList = new ArrayList<>();
            stringList.add(postCategoryId);
            stringList.add(partTimePostCategoryId);
            //根据职位分类 查看积分规则
            Map map1 = new HashMap();
            map1.put("list", stringList);
            List<Score> scoreList = scoreDao.selectScoreByPostId(map1);
            BigDecimal totalScore = new BigDecimal(0);
            if (scoreList.size() == 0) {
                totalScore = new BigDecimal(0);
            } else {
                //总分数
                Set<String> performanceIds = map.keySet();
                for (String performanceId : performanceIds) {
                    PerformancePost performancePost = performanceDao.selectPerformancePostById(Long.parseLong(performanceId));
                    if (performancePost.getIsBasicSalary() == 0) {
                        //如果不计算底薪
                        totalScore = totalScore.add(BigDecimal.ZERO);
                        continue;
                    }
                    for (Score score : scoreList) {
                        if (score.getScoreMode() == 1) {
                            //手动评分
                            continue;
                        }
                        if (score.getScoreAchievementID().equals(Long.valueOf(performanceId))) {
                            //传来的业绩id和评分标准的业绩id一致再计算评分
                            //每条业绩对应的金额
                            BigDecimal amount = null;
                            if (map.get(performanceId) instanceof Double) {
                                amount = new BigDecimal((Double) map.get(performanceId));
                            } else if (map.get(performanceId) instanceof Integer) {
                                amount = new BigDecimal((Integer) map.get(performanceId));
                            }
                            if (amount.compareTo(BigDecimal.ZERO) != 0) {
                                //计算业绩金额评分 金额业绩/业绩基数*分数基数
                                BigDecimal performanceScore = amount.subtract(score.getScoreProportion()).divide(score.getScoreBase(), BigDecimal.ROUND_UP).add(score.getScoreDefault());
                                if (performanceScore.doubleValue() != 0 && performanceScore.compareTo(score.getScoreLow()) == -1) {
                                    //如果评分小于最低分取最低分
                                    performanceScore = score.getScoreLow();
                                } else if (performanceScore.compareTo(score.getScoreHigh()) == 1) {
                                    //高于了最高分数 就取最高分
                                    performanceScore = score.getScoreHigh();
                                } else if (amount.doubleValue() < score.getScoreProportion().doubleValue()) {
                                    //如果业绩小于业绩基数按照默认业绩
                                    performanceScore = score.getScoreDefault();
                                }
                                //累加每条业绩的评分
                                totalScore = totalScore.add(performanceScore);
                            }
                        }
                    }
                }
            }
            statistic.setScoreGroup(totalScore.setScale(0, BigDecimal.ROUND_HALF_UP));
        }
        return ResponseResult.success(statistic);
    }

    @Override
    public ResponseResult selectBasicSalary(Long salesmanID, BigDecimal totalScore, Statistic statistic) {
        //根据员工的职位，门店，行业  查看底薪
        Map mapStaff = (Map) storeApi.selectBeauticianByCode(salesmanID.toString()).getResult();
        Map mapStore = (Map) storeApi.selectStoreById(Long.parseLong(mapStaff.get("companyId").toString())).getResult();

        Basesalary basesalary = new Basesalary();
        basesalary.setBaseSalaryPostID(Long.parseLong(mapStaff.get("postCategoryId").toString()));
        basesalary.setBaseSalaryStoreId(Long.parseLong(mapStaff.get("storeId").toString()));
        List<Basesalary> basesalaryList = baseSalaryDao.selectByCondition(basesalary);
        if (basesalaryList.size() == 0) {
            //如果门店没找到底薪  查看行业标准
            basesalary.setBaseSalaryStoreId(null);
            basesalary.setBaseSalaryPostID(Long.parseLong(mapStaff.get("postCategoryId").toString()));
            basesalary.setBaseSalaryIndustryID(Long.parseLong(mapStore.get("industryID").toString()));
            basesalaryList = baseSalaryDao.selectByCondition(basesalary);
        }

        if (basesalaryList.size() == 1) {
            //如果只有一个规则
            if (totalScore.doubleValue() >= basesalaryList.get(0).getBaseSalaryLow().doubleValue()) {
                statistic.setBaseSalary(basesalaryList.get(0).getBaseSalaryAmount());
                return ResponseResult.success(statistic);
            } else {
                statistic.setBaseSalary(new BigDecimal(0));
                return ResponseResult.success(statistic);
            }
        }

        if (basesalaryList.size() == 0) {
            //如果门店标准  行业标准 都没有底薪规则
            return ResponseResult.success(statistic);
        } else {
            //比对分数  获取底薪
            BigDecimal baseSalaryAmount = null;
            if (totalScore.doubleValue() == new BigDecimal(0).doubleValue()) {
                //如果分数为0，就直接等于  0分的对应底薪
                for (int i = 0; i < basesalaryList.size() - 1; i++) {
                    if (basesalaryList.get(i).getBaseSalaryLow().doubleValue() == 0) {
                        baseSalaryAmount = basesalaryList.get(i).getBaseSalaryAmount();
                    }
                }
            }

            for (int i = 0; i < basesalaryList.size() - 1; i++) {
                //如果有多个分数标准拿到阶梯分数标准
                int r1 = totalScore.compareTo(basesalaryList.get(i).getBaseSalaryLow());
                int r2 = totalScore.compareTo(basesalaryList.get(i + 1).getBaseSalaryLow());
                if (r1 < 0) {
                    //比最小的还小
                    continue;
                } else if (r1 > 0 && r2 < 0) {
                    //中间
                    baseSalaryAmount = basesalaryList.get(i).getBaseSalaryAmount();
                } else if (r1 > 0 && r2 > 0) {
                    //比最大的还大
                    baseSalaryAmount = basesalaryList.get(i + 1).getBaseSalaryAmount();
                }
            }
            if (baseSalaryAmount == null) {
                baseSalaryAmount = new BigDecimal(0);
            }
            statistic.setBaseSalary(baseSalaryAmount);
        }

        //判断员工是否算底薪
        if (mapStaff.get("isBasicSalary").toString().equals("0")) {
            //如果不算底薪
            statistic.setBaseSalary(new BigDecimal(0));
        }

        return ResponseResult.success(statistic);
    }

    @Override
    public ResponseResult responseResultTiCheng(List<HashMap<String, Object>> mapList, Statistic statistic) {
        //查看所有的业绩阶梯
        List<Ladder> ladderList = performanceDao.selectLadderList();
        //查看每种业绩的业绩提成阶梯
        Double takePercentage = 0.00;
        for (HashMap<String, Object> stringObjectHashMap : mapList) {

            for (String yejiId : stringObjectHashMap.keySet()) {
                List<Ladder> ladderListResult = new ArrayList<>();
                for (Ladder ladder : ladderList) {
                    if (ladder.getLadderAchievementPostID().toString().equals(yejiId)) {
                        ladderListResult.add(ladder);
                    }
                }


                Double ladderProportion = 0.00;
                if (ladderListResult.size() == 1) {
                    //如果只有一个规则
                    if (Double.parseDouble(stringObjectHashMap.get(yejiId).toString()) >= ladderListResult.get(0).getLadderLower().doubleValue()) {
                        takePercentage = takePercentage + ladderListResult.get(0).getLadderProportion().doubleValue() * Double.parseDouble(stringObjectHashMap.get(yejiId).toString());
                    } else {
                        takePercentage = takePercentage + 0;
                    }
                }
                for (int i = 0; i < ladderListResult.size() - 1; i++) {
                    //如果有多个分数标准拿到阶梯分数标准
                    int r1 = new BigDecimal(Double.parseDouble(stringObjectHashMap.get(yejiId).toString())).compareTo(new BigDecimal(ladderListResult.get(i).getLadderLower()));
                    int r2 = new BigDecimal(Double.parseDouble(stringObjectHashMap.get(yejiId).toString())).compareTo(new BigDecimal(ladderListResult.get(i + 1).getLadderLower()));
                    if (r1 < 0) {
                        //比最小的还小
                        continue;
                    } else if (r1 > 0 && r2 < 0) {
                        //中间
                        ladderProportion = ladderListResult.get(i).getLadderProportion().doubleValue();
                    } else if (r1 > 0 && r2 > 0) {
                        //比最大的还大
                        ladderProportion = ladderListResult.get(i + 1).getLadderProportion().doubleValue();
                    }
                }

                takePercentage = takePercentage + Double.parseDouble(stringObjectHashMap.get(yejiId).toString()) * ladderProportion;
            }
        }

        statistic.setTakePercentage(new BigDecimal(takePercentage));


        return ResponseResult.success(statistic);
    }


}
