package com.lnmj.data.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.*;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodePerformanceEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.DateUtil;
import com.lnmj.common.utils.EnumUtil;
import com.lnmj.common.utils.NumberUtils;
import com.lnmj.data.business.IPerformanceService;
import com.lnmj.data.entity.*;
import com.lnmj.data.entity.VO.OrderNum;
import com.lnmj.data.repository.ICommodityTypeDao;
import com.lnmj.data.repository.IOrderTichengDao;
import com.lnmj.data.repository.IPerformanceDao;
import com.lnmj.data.serviceProxy.*;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.internal.util.unsafe.MpmcArrayQueue;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: yilihua
 * @Date: 2019/9/5 16:50
 * @Description: 业绩
 */
@Service("performanceService")
@Transactional
public class PerformanceService implements IPerformanceService {
    @Resource
    private IPerformanceDao performanceDao;
    @Resource
    private IOrderTichengDao orderTichengDao;
    @Resource
    private ICommodityTypeDao commodityTypeDao;
    @Resource
    private PayApi payApi;
    @Resource
    private WalletApi walletApi;
    @Resource
    private StoreApi storeApi;
    @Resource
    private IPerformanceDao iPerformanceDao;
    @Resource
    private OrderApi orderApi;
    @Resource
    private ProductApi productApi;
    @Resource
    private SystemApi systemApi;

    @Override
    public ResponseResult selectPerformanceList(int pageNum, int pageSize, String storeId, Integer achievementType, Long achievementIndustryID, String keyWordAchievementName) {
        PageHelper.startPage(pageNum, pageSize);
        String[] storeIdArray = null;
        if (storeId != null) {
            storeIdArray = storeId.replaceAll("]", "").replace("[", "").split(",");
        }
        Map mapList = new HashMap();
        mapList.put("list", storeIdArray);
        mapList.put("achievementType", achievementType);
        mapList.put("achievementIndustryID", achievementIndustryID);
        mapList.put("keyWordAchievementName", keyWordAchievementName);
        List<Performance> performanceList = performanceDao.selectPerformanceList(mapList);
        if (achievementIndustryID != null && (performanceList == null || performanceList.size() == 0)) {
            mapList.put("achievementIndustryID", 0);
            performanceList = performanceDao.selectPerformanceList(mapList);
        }
        //查看所有行业列表
        ResponseResult responseResultIndustryList = storeApi.selectListIndustryNoPage();
        List<Map> IndustryMapList = (List<Map>) responseResultIndustryList.getResult();
        //查看所有门店列表
        ResponseResult responseResultStoreList = storeApi.selectStoreListNoPage();
        List<Map> StoreMapList = (List<Map>) responseResultStoreList.getResult();
        //查看所有订单类型列表
        Map<Integer, String> responseResultOrderTypeMap = EnumUtil.getEnumToMap(OrderTypeEnum.class);
        for (Map.Entry entry : responseResultOrderTypeMap.entrySet()) {
            if (entry.getKey().equals(2) || entry.getKey().equals(3)) {
                entry.setValue("护理订单");
            }
        }
        Set<Map.Entry<Integer, String>> keyListOrderType = responseResultOrderTypeMap.entrySet();


        for (Performance performanceItem : performanceList) {
            for (Map Industry : IndustryMapList) {
                if (performanceItem.getAchievementIndustryID() == Long.parseLong(Industry.get("industryID").toString())) {
                    performanceItem.setAchievementIndustryName(Industry.get("industryName").toString());
                }
            }
            for (Map.Entry<Integer, String> mapOrderType : keyListOrderType) {
                if (performanceItem.getAchievementType() == Long.parseLong(String.valueOf(mapOrderType.getKey()))) {
                    performanceItem.setAchievementOrderTypeName(mapOrderType.getValue());
                }
            }

        }


        //查看子业绩列表
        List<PerformancePost> performancePostList = performanceDao.selectPerformancePostListAll();
        //查看所有职位分类列表
        ResponseResult responseResultPostList = storeApi.selectPostCategoryNoPage();
        List<Map> PostCategoryMapList = (List<Map>) responseResultPostList.getResult();
        //查看所有业绩提成方式列表
        Map responseResultAchievementMethodMap = EnumUtil.getEnumToMap(AchievementMethodEnum.class);
        Set<Map.Entry<String, String>> keyListAchievementMethod = responseResultAchievementMethodMap.entrySet();
        for (Performance performance : performanceList) {
            List<PerformancePost> performancePostListA = new ArrayList<>();
            for (PerformancePost performancePost : performancePostList) {
                if (performancePost.getAchievementID().toString().equals(performance.getAchievementID().toString())/*&&
                        (performancePost.getAchievementMethods()==AchievementMethodEnum.ALL_ACHIEVEMENT_LADDER_ROYALTY.getCode()||
                                performancePost.getAchievementMethods()==AchievementMethodEnum.NUMBER_LADDER_ROYALTY.getCode())*/) {
                    //查询职位名称
                    if (PostCategoryMapList != null) {
                        for (Map PostCategory : PostCategoryMapList) {
                            if (performancePost.getAchievementPostCategoryID() == Long.parseLong(PostCategory.get("postCategoryId").toString())) {
                                performancePost.setAchievementPostCategoryName(PostCategory.get("name").toString());
                            }
                        }
                    }
                    //查询提成方式名称
                    for (Map.Entry<String, String> mapAchievementMethod : keyListAchievementMethod) {
                        if (performancePost.getAchievementMethods() == Long.parseLong(String.valueOf(mapAchievementMethod.getKey()))) {
                            performancePost.setAchievementMethodsName(mapAchievementMethod.getValue());
                        }
                    }
                    //是否为个人业绩
                    if (performancePost.getAchievementStore() == 1) {
                        performancePost.setAchievementStoreName("全店业绩");
                    } else {
                        performancePost.setAchievementStoreName("个人业绩");
                    }
                    performancePostListA.add(performancePost);
                }
            }
            performance.setPerformancePostList(performancePostListA);

            if (performance.getAchievementIndustryID() == 0) {
                performance.setAchievementIndustryName("所有行业");
            }
        }


        if (performanceList != null && performanceList.size() > 0) {
            PageInfo<Performance> pageInfo = new PageInfo<>(performanceList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodePerformanceEnum.PERFORMANCE_NULL.getCode(),
                ResponseCodePerformanceEnum.PERFORMANCE_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectPerformanceListNoPage(String storeId, Integer achievementType, Long achievementIndustryID) {
        String[] storeIdArray = null;
        if (storeId != null) {
            storeIdArray = storeId.replaceAll("]", "").replace("[", "").split(",");
        }
        Map mapList = new HashMap();
        mapList.put("list", storeIdArray);
        mapList.put("achievementType", achievementType);
        mapList.put("achievementIndustryID", achievementIndustryID);
        List<Performance> performanceList = performanceDao.selectPerformanceList(mapList);
        //查看所有行业列表
/*        ResponseResult responseResultIndustryList = storeApi.selectListIndustryNoPage();
        List<Map> IndustryMapList = (List<Map>) responseResultIndustryList.getResult();*/
        //查看所有门店列表
        ResponseResult responseResultStoreList = storeApi.selectStoreListNoPage();
        List<Map> StoreMapList = (List<Map>) responseResultStoreList.getResult();
        //查看所有订单类型列表
        Map<Integer, String> responseResultOrderTypeMap = EnumUtil.getEnumToMap(OrderTypeEnum.class);
        for (Map.Entry entry : responseResultOrderTypeMap.entrySet()) {
            if (entry.getKey().equals(2) || entry.getKey().equals(3)) {
                entry.setValue("护理订单");
            }
        }
        Set<Map.Entry<Integer, String>> keyListOrderType = responseResultOrderTypeMap.entrySet();


        for (Performance performanceItem : performanceList) {
         /*   for (Map Industry : IndustryMapList) {
                if (performanceItem.getAchievementIndustryID() == Long.parseLong(Industry.get("industryID").toString())) {
                    performanceItem.setAchievementIndustryName(Industry.get("industryName").toString());
                }
            }*/
            /*for (Map Store : StoreMapList) {
                if (performanceItem.getAchievementStoreId() == Long.parseLong(Store.get("storeId").toString())) {
                    performanceItem.setAchievementStoreName(Store.get("name").toString());
                }
            }*/
            for (Map.Entry<Integer, String> mapOrderType : keyListOrderType) {
                if (performanceItem.getAchievementType() == Long.parseLong(String.valueOf(mapOrderType.getKey()))) {
                    performanceItem.setAchievementOrderTypeName(mapOrderType.getValue());
                }
            }

        }


        //查看子业绩列表
        List<PerformancePost> performancePostList = performanceDao.selectPerformancePostListAll();
        //查看所有职位分类列表
        ResponseResult responseResultPostList = storeApi.selectPostCategoryNoPage();
        List<Map> PostCategoryMapList = (List<Map>) responseResultPostList.getResult();
        //查看所有业绩提成方式列表
        Map responseResultAchievementMethodMap = EnumUtil.getEnumToMap(AchievementMethodEnum.class);
        Set<Map.Entry<String, String>> keyListAchievementMethod = responseResultAchievementMethodMap.entrySet();
        for (Performance performance : performanceList) {
            List<PerformancePost> performancePostListA = new ArrayList<>();
            for (PerformancePost performancePost : performancePostList) {
                if (performancePost.getAchievementID().toString().equals(performance.getAchievementID().toString())/*&&
                        (performancePost.getAchievementMethods()==AchievementMethodEnum.ALL_ACHIEVEMENT_LADDER_ROYALTY.getCode()||
                                performancePost.getAchievementMethods()==AchievementMethodEnum.NUMBER_LADDER_ROYALTY.getCode())*/) {
                    //查询职位名称
                    if (PostCategoryMapList != null) {
                        for (Map PostCategory : PostCategoryMapList) {
                            if (performancePost.getAchievementPostCategoryID() == Long.parseLong(PostCategory.get("postCategoryId").toString())) {
                                performancePost.setAchievementPostCategoryName(PostCategory.get("name").toString());
                            }
                        }
                    }
                    //查询提成方式名称
                    if (keyListAchievementMethod != null) {
                        for (Map.Entry<String, String> mapAchievementMethod : keyListAchievementMethod) {
                            if (performancePost.getAchievementMethods() == Long.parseLong(String.valueOf(mapAchievementMethod.getKey()))) {
                                performancePost.setAchievementMethodsName(mapAchievementMethod.getValue());
                            }
                        }
                    }
                    //是否为个人业绩
                    if (performancePost.getAchievementStore() == 1) {
                        performancePost.setAchievementStoreName("全店业绩");
                    } else {
                        performancePost.setAchievementStoreName("个人业绩");
                    }
                    performancePostListA.add(performancePost);
                }
            }
            performance.setPerformancePostList(performancePostListA);

            if (performance.getAchievementIndustryID() == 0) {
                performance.setAchievementIndustryName("所有行业");
            }
        }


        if (performanceList != null && performanceList.size() > 0) {
            return ResponseResult.success(performanceList);
        }
        return ResponseResult.error(new Error(ResponseCodePerformanceEnum.PERFORMANCE_NULL.getCode(),
                ResponseCodePerformanceEnum.PERFORMANCE_NULL.getDesc()));
    }


    @Override
    public ResponseResult selectPerformancePostList(int pageNum, int pageSize, String achievementID, String keyWord) {
        if (achievementID != null && achievementID.equals("0")) {
            achievementID = null;
        }
        Page p = PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("achievementID", achievementID);
        map.put("keyWord", keyWord);
        List<PerformancePost> performancePostList = performanceDao.selectPerformancePostList(map);
        //查看所有职位分类列表
        ResponseResult responseResultPostList = storeApi.selectPostCategoryNoPage();
        //查询业绩分类
        List<Performance> performanceList = performanceDao.selectPerformanceList(new HashMap());
        //查询所有门店
        List<Map> mapListStore = (List<Map>) storeApi.selectStoreListNoPage().getResult();


        List<Map> PostCategoryMapList = (List<Map>) responseResultPostList.getResult();
        //查看所有业绩提成方式列表
        Map responseResultAchievementMethodMap = EnumUtil.getEnumToMap(AchievementMethodEnum.class);
        Set<Map.Entry<String, String>> keyListAchievementMethod = responseResultAchievementMethodMap.entrySet();
        for (PerformancePost performancePostItem : performancePostList) {
            //查询职位名称
            if (PostCategoryMapList != null) {
                for (Map PostCategory : PostCategoryMapList) {
                    if (performancePostItem.getAchievementPostCategoryID() == Long.parseLong(PostCategory.get("postCategoryId").toString())) {
                        performancePostItem.setAchievementPostCategoryName(PostCategory.get("name").toString());
                    }
                }
            }
            //查询业绩分类名称
            for (Performance performance : performanceList) {
                if (performancePostItem.getAchievementID() != null && performancePostItem.getAchievementID().toString().equals(performance.getAchievementID().toString())) {
                    performancePostItem.setAchievementCategoryName(performance.getAchievementName());
                }
            }

            //查询所属门店名称
            for (Map store : mapListStore) {
                if (performancePostItem.getAchievementStoreId() != null && performancePostItem.getAchievementStoreId().toString().equals(store.get("storeId").toString())) {
                    performancePostItem.setAchievementStoreIdName(store.get("name").toString());
                }
            }

            //查询提成方式名称
            for (Map.Entry<String, String> mapAchievementMethod : keyListAchievementMethod) {
                if (performancePostItem.getAchievementMethods() == Long.parseLong(String.valueOf(mapAchievementMethod.getKey()))) {
                    performancePostItem.setAchievementMethodsName(mapAchievementMethod.getValue());
                }
            }
            //是否为个人业绩
            if (performancePostItem.getAchievementStore() == 1) {
                performancePostItem.setAchievementStoreName("全店业绩");
            } else if (performancePostItem.getAchievementStore() == 0) {
                performancePostItem.setAchievementStoreName("个人业绩");
            } else if (performancePostItem.getAchievementStore() == 2) {
                performancePostItem.setAchievementStoreName("分组业绩");
            }
        }

        //如果是阶梯查看阶梯提成数值
        List<Ladder> ladderList = performanceDao.selectLadderList();
        for (PerformancePost performancePost : performancePostList) {
            for (Ladder ladder : ladderList) {
                if (performancePost.getAchievementMethods().equals(AchievementMethodEnum.NUMBER_ROYALTY.getCode()) && performancePost.getId().equals(ladder.getLadderAchievementPostID())) {
                    performancePost.setLadderValue(ladder.getLadderBonus().doubleValue());
                } else if (performancePost.getAchievementMethods().equals(AchievementMethodEnum.ALL_ACHIEVEMENT_ROYALTY.getCode()) && performancePost.getId().equals(ladder.getLadderAchievementPostID())) {
                    performancePost.setLadderValue(ladder.getLadderProportion().doubleValue());
                }
            }
        }


        if (performancePostList != null && performancePostList.size() > 0) {
            PageInfo<PerformancePost> pageInfo = new PageInfo<>(p.getResult());
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodePerformanceEnum.PERFORMANCE_NULL.getCode(),
                ResponseCodePerformanceEnum.PERFORMANCE_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectPerformancePostListAll() {
        List<PerformancePost> performancePostList = performanceDao.selectPerformancePostListAll();
        //查看所有职位分类列表
        ResponseResult responseResultPostList = storeApi.selectPostCategoryNoPage();
        List<Map> PostCategoryMapList = (List<Map>) responseResultPostList.getResult();
        //查看所有业绩提成方式列表
        Map responseResultAchievementMethodMap = EnumUtil.getEnumToMap(AchievementMethodEnum.class);
        Set<Map.Entry<String, String>> keyListAchievementMethod = responseResultAchievementMethodMap.entrySet();
        for (PerformancePost performancePostItem : performancePostList) {
            //查询职位名称
            if (PostCategoryMapList != null) {
                for (Map PostCategory : PostCategoryMapList) {
                    if (performancePostItem.getAchievementPostCategoryID() == Long.parseLong(PostCategory.get("postCategoryId").toString())) {
                        performancePostItem.setAchievementPostCategoryName(PostCategory.get("name").toString());
                    }
                }
            }
            //查询提成方式名称
            for (Map.Entry<String, String> mapAchievementMethod : keyListAchievementMethod) {
                if (performancePostItem.getAchievementMethods() == Long.parseLong(String.valueOf(mapAchievementMethod.getKey()))) {
                    performancePostItem.setAchievementMethodsName(mapAchievementMethod.getValue());
                }
            }
            //是否为个人业绩
            if (performancePostItem.getAchievementStore() == 1) {
                performancePostItem.setAchievementStoreName("全店业绩");
            } else {
                performancePostItem.setAchievementStoreName("个人业绩");
            }
        }

        return ResponseResult.success(performancePostList);
    }


    @Override
    public ResponseResult selectPerformanceByCondition(int pageNum, int pageSize, Performance performance) {
        PageHelper.startPage(pageNum, pageSize);
        List<Performance> performanceList = performanceDao.selectPerformanceByCondition(performance);
        if (performanceList != null && performanceList.size() > 0) {
            PageInfo<Performance> pageInfo = new PageInfo<>(performanceList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodePerformanceEnum.PERFORMANCE_NULL.getCode(),
                ResponseCodePerformanceEnum.PERFORMANCE_NULL.getDesc()));
    }

    @Override
    public ResponseResult deletePerformanceByID(Long id, String modifyOperator) {
        //是否存在
        Performance performanceByID = performanceDao.selectPerformanceByID(id);
        if (performanceByID == null) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.NOT_FIND_PERFORMANCE.getCode(),
                    ResponseCodePerformanceEnum.NOT_FIND_PERFORMANCE.getDesc()));
        }
        //删除对应的业绩阶梯
        Ladder ladder = new Ladder();
        ladder.setLadderAchievementID(performanceByID.getAchievementID());
        ladder.setModifyOperator(modifyOperator);
        performanceDao.deleteLadderByAchievementID(ladder);
        //删除业绩
        Performance performance = new Performance();
        performance.setId(id);
        performance.setModifyOperator(modifyOperator);
        performanceDao.deletePerformanceByID(performance);
        //删除对应的职位业绩
        PerformancePost performancePost = new PerformancePost();
        performancePost.setAchievementID(performanceByID.getAchievementID());
        performanceDao.deletePerformancePostByID(performancePost);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deletePerformancePostByID(Long performanceId, Long performancePostId, String modifyOperator) {
        //删除对应的业绩阶梯
        Ladder ladder = new Ladder();
        ladder.setLadderAchievementPostID(performancePostId);
        ladder.setLadderAchievementID(performanceId);
        ladder.setModifyOperator(modifyOperator);
        performanceDao.deleteLadderByAchievementID(ladder);
        //删除业绩职位
        PerformancePost performancePost = new PerformancePost();
        performancePost.setId(performancePostId);
        performancePost.setModifyOperator(modifyOperator);
        performanceDao.deletePerformancePostByID(performancePost);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult insertPerformance(Performance performance) {
        if (performance.getAchievementID() == 0) {
            performance.setAchievementID(Long.parseLong(NumberUtils.getRandomOrderNo()));
        }
        performanceDao.insertPerformance(performance);
        return ResponseResult.success(performance);
    }

    @Override
    public ResponseResult insertPerformancePost(PerformancePost performancePost) {
        if (performancePost.getAchievementID() == 0) {
            performancePost.setAchievementID(Long.parseLong(NumberUtils.getRandomOrderNo()));
        }
        performanceDao.insertPerformancePost(performancePost);
        return ResponseResult.success(performancePost.getId());
    }

    @Override
    public ResponseResult updatePerformance(Performance performance) {
        HashMap<String, Object> result = new HashMap<>();
        //是否存在
        Performance performanceByID = performanceDao.selectPerformanceByID(performance.getId());
        if (performanceByID == null) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.NOT_FIND_PERFORMANCE.getCode(),
                    ResponseCodePerformanceEnum.NOT_FIND_PERFORMANCE.getDesc()));
        }
        //更新业绩
        int i = performanceDao.updatePerformance(performance);
        result.put("业绩更新成功", performance);
        return ResponseResult.success(result);
    }

    @Override
    public ResponseResult updatePerformancePost(PerformancePost performancePost) {
        //更新业绩
        if (performancePost.getAchievementStoreId() == null) {
            performancePost.setAchievementStoreId(0l);
        }
        if (performancePost.getAchievementPostCategoryID() == null) {
            performancePost.setAchievementPostCategoryID(0l);
        }
        performanceDao.updatePerformancePost(performancePost);
        //如果是固定修改为阶梯，要先删除固定
        if (performancePost.getAchievementMethods() == AchievementMethodEnum.NUMBER_ROYALTY.getCode()) {//如果修改为单个固定
            //先删除原有的阶梯
            Ladder ladderDel = new Ladder();
            ladderDel.setLadderAchievementPostID(performancePost.getId());
            performanceDao.deleteLadderByAchievementIDLongTime(ladderDel);
            //添加阶梯
            Ladder ladderAdd = new Ladder();
            ladderAdd.setLadderAchievementPostID(performancePost.getId());
            ladderAdd.setLadderAchievementID(performancePost.getAchievementID());
            ladderAdd.setLadderName("总个数固定提成");
            if (performancePost.getSinglePrize() != null) {
                ladderAdd.setLadderBonus(new BigDecimal(performancePost.getSinglePrize()));
            }
            performanceDao.insertLadder(ladderAdd);

        } else if (performancePost.getAchievementMethods() == AchievementMethodEnum.ALL_ACHIEVEMENT_ROYALTY.getCode()) {//如果修改为总业绩固定
            //先删除原有的阶梯
            Ladder ladderDel = new Ladder();
            ladderDel.setLadderAchievementPostID(performancePost.getId());
            performanceDao.deleteLadderByAchievementIDLongTime(ladderDel);
            //添加阶梯
            Ladder ladderAdd = new Ladder();
            ladderAdd.setLadderAchievementPostID(performancePost.getId());
            ladderAdd.setLadderAchievementID(performancePost.getAchievementID());
            ladderAdd.setLadderName("总业绩固定提成");
            ladderAdd.setLadderProportion(new BigDecimal(performancePost.getZhongyejiRatio() / 100));
            performanceDao.insertLadder(ladderAdd);
        } else {
            Ladder ladderDel = new Ladder();
            ladderDel.setLadderAchievementPostID(performancePost.getId());
            performanceDao.deleteLadderByAchievementIDLongTime(ladderDel);
        }

        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectLadderDetailedList(int pageNum, int pageSize, Long storeId) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("storeId", storeId);
        List<LadderDetailed> ladderDetailedList = performanceDao.selectLadderDetailedList(map);
        if (ladderDetailedList != null && ladderDetailedList.size() > 0) {
            PageInfo<LadderDetailed> pageInfo = new PageInfo<>(ladderDetailedList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodePerformanceEnum.LADDERDETAILED_NULL.getCode(),
                ResponseCodePerformanceEnum.LADDERDETAILED_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectLadderDetailedListColumnar(String storeId) {
        Map mapDate = DateUtil.getDateTodayAndYesToday();
        String[] storeIdArray = storeId.replaceAll("]", "").replace("[", "").split(",");
        //查询所有此门店员工
        ResponseResult responseResult = storeApi.selectBeauticianByStoreIdArray(storeId);
        Map map = new HashMap();
        map.put("list", storeIdArray);
        //查询所有此门店业绩-今日
        map.put("startDate", mapDate.get("today"));
        map.put("endDate", mapDate.get("tomorrow"));
        map.put("orderType", 2);//充值订单
        List<LadderDetailed> ladderDetailedListTodayRecharge = performanceDao.selectLadderDetailedList(map);
        map.put("orderType", 1);//支付订单
        List<LadderDetailed> ladderDetailedListTodayPay = performanceDao.selectLadderDetailedList(map);
        List<Map> AllStorebeauticianList = (List<Map>) responseResult.getResult();
        List<Double> amountListRechargeToday = new ArrayList<>();
        List<Double> amountListPayToday = new ArrayList<>();
        List<String> beauticianName = new ArrayList<>();
        List<Long> beauticianId = new ArrayList<>();
        if (AllStorebeauticianList == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("allStorebeauticianList", beauticianName);
            jsonObject.put("allStorebeauticianIdList", beauticianId);
            jsonObject.put("amountListTodayRecharge", amountListRechargeToday);
            jsonObject.put("amountListTodayPay", amountListPayToday);
            return ResponseResult.success(jsonObject);
        }
        //员工业绩数组-支付业绩-今日
        for (Map beauticianItem : AllStorebeauticianList) {
            double payAmount = 0.00;
            for (LadderDetailed ladderDetailedItem : ladderDetailedListTodayPay) {
                if (ladderDetailedItem.getLadderDetailedBeauticianId() == Long.parseLong(beauticianItem.get("staffNumber").toString())) {
                    payAmount = payAmount + ladderDetailedItem.getLadderDetailedAmount().doubleValue();
                }
            }
            beauticianName.add(beauticianItem.get("name").toString());
        }

        for (Map beauticianItem : AllStorebeauticianList) {
            double payAmount = 0.00;
            for (LadderDetailed ladderDetailedItem : ladderDetailedListTodayPay) {
                if (ladderDetailedItem.getLadderDetailedBeauticianId() == Long.parseLong(beauticianItem.get("staffNumber").toString())) {
                    payAmount = payAmount + ladderDetailedItem.getLadderDetailedAmount().doubleValue();
                }
            }
            amountListPayToday.add(payAmount);
            beauticianId.add(Long.parseLong(beauticianItem.get("beauticianId").toString()));
        }

        //员工业绩数组-充值业绩-今日
        for (Map beauticianItem : AllStorebeauticianList) {
            double rechargAmount = 0.00;
            for (LadderDetailed ladderDetailedItem : ladderDetailedListTodayRecharge) {
                if (ladderDetailedItem.getLadderDetailedBeauticianId() == Long.parseLong(beauticianItem.get("beauticianId").toString())) {
                    rechargAmount = rechargAmount + ladderDetailedItem.getLadderDetailedAmount().doubleValue();
                }
            }
            amountListRechargeToday.add(rechargAmount);
        }

        for (int i = 0; i < amountListRechargeToday.size(); i++) {
            if (amountListRechargeToday.get(i) == 0) {
                amountListRechargeToday.set(i, 0.0);
            }
        }

        for (int i = 0; i < amountListPayToday.size(); i++) {
            if (amountListPayToday.get(i) == 0) {
                amountListPayToday.set(i, 0.0);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("allStorebeauticianList", beauticianName);
        jsonObject.put("allStorebeauticianIdList", beauticianId);
        jsonObject.put("amountListTodayRecharge", amountListRechargeToday);
        jsonObject.put("amountListTodayPay", amountListPayToday);
        return ResponseResult.success(jsonObject);
    }

    @Override
    public ResponseResult selectLadderDetailedListColumnarForNumber(String storeId) {
        Map mapDate = DateUtil.getDateTodayAndYesToday();
        String[] storeIdArray = storeId.replaceAll("]", "").replace("[", "").split(",");
        //查询所有此门店员工
        ResponseResult responseResult = storeApi.selectBeauticianByStoreIdArray(storeId);
        Map map = new HashMap();
        map.put("list", storeIdArray);
        //查询所有此门店业绩-今日
        map.put("startDate", mapDate.get("today"));
        map.put("endDate", mapDate.get("tomorrow"));
        map.put("orderType", 1);//支付订单
        map.put("forNumber", "forNumber");
        List<LadderDetailed> ladderDetailedListTodayPay = performanceDao.selectLadderDetailedList(map);
        List<Map> AllStorebeauticianList = (List<Map>) responseResult.getResult();

        List<Double> allNumberList = new ArrayList<>();
        List<String> beauticianName = new ArrayList<>();
        List<Long> beauticianId = new ArrayList<>();

        if (AllStorebeauticianList == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("allNumberList", allNumberList);
            jsonObject.put("allStorebeauticianList", beauticianName);
            jsonObject.put("allStorebeauticianIdList", beauticianId);
            return ResponseResult.success(jsonObject);
        }
        //获取员工数据
        for (Map beauticianItem : AllStorebeauticianList) {
            beauticianName.add(beauticianItem.get("name").toString());
            beauticianId.add(Long.parseLong(beauticianItem.get("beauticianId").toString()));
        }


        //员工业绩数组-支付业绩中-个数
        for (Map beauticianItem : AllStorebeauticianList) {
            double number = 0.00;
            for (LadderDetailed ladderDetailedItem : ladderDetailedListTodayPay) {
                if (ladderDetailedItem.getLadderDetailedBeauticianId() == Long.parseLong(beauticianItem.get("staffNumber").toString())) {
                    number = number + ladderDetailedItem.getLadderDetailedNumber().doubleValue();
                }
            }
            allNumberList.add(number);
        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("allStorebeauticianList", beauticianName);
        jsonObject.put("allStorebeauticianIdList", beauticianId);
        jsonObject.put("amountListTodayRecharge", new ArrayList<>());
        jsonObject.put("allNumberList", allNumberList);
        return ResponseResult.success(jsonObject);
    }


    @Override
    public ResponseResult selectLadderDetaiColumnarByBeauId(Long beauticianId, Integer isNumber) {
        //根据美容师查询业绩明细
        LadderDetailed ladderDetailed = new LadderDetailed();
        ladderDetailed.setLadderDetailedBeauticianId(beauticianId);
        List<LadderDetailed> result = performanceDao.selectLadderDetailedByCondition(ladderDetailed);
        Double productLadderDetai = 0.0;//商品业绩
        Double serviceLadderDetai = 0.0;//护理业绩
        Double rechargeLadderDetai = 0.0;//充值业绩
        Double cosmeticLadderDetai = 0.0;//医美业
        for (LadderDetailed ladderDetailedItem : result) {
            if (ladderDetailedItem.getLadderDetailedType() == LadderdetailedTypeEnum.PRODUCT_LADDERDETAILED.getCode()) {
                //商品业绩
                if (isNumber == 1) {
                    productLadderDetai = productLadderDetai + ladderDetailedItem.getLadderDetailedNumber().doubleValue();
                } else {
                    productLadderDetai = productLadderDetai + ladderDetailedItem.getLadderDetailedAmount().doubleValue();
                }
            }
            if (ladderDetailedItem.getLadderDetailedType() == LadderdetailedTypeEnum.SERVICE_LADDERDETAILED.getCode()) {
                //服务业绩
                if (isNumber == 1) {
                    serviceLadderDetai = productLadderDetai + ladderDetailedItem.getLadderDetailedNumber().doubleValue();
                } else {
                    serviceLadderDetai = productLadderDetai + ladderDetailedItem.getLadderDetailedAmount().doubleValue();
                }

            }
            if (ladderDetailedItem.getLadderDetailedType() == LadderdetailedTypeEnum.RECHARGE_LADDERDETAILED.getCode()) {
                //充值业绩
                if (isNumber == 1) {
                    rechargeLadderDetai = productLadderDetai + ladderDetailedItem.getLadderDetailedNumber().doubleValue();
                } else {
                    rechargeLadderDetai = productLadderDetai + ladderDetailedItem.getLadderDetailedAmount().doubleValue();
                }

            }
            if (ladderDetailedItem.getLadderDetailedType() == LadderdetailedTypeEnum.COSMETIC_LADDERDETAILED.getCode()) {
                //医美业绩
                if (isNumber == 1) {
                    cosmeticLadderDetai = productLadderDetai + ladderDetailedItem.getLadderDetailedNumber().doubleValue();
                } else {
                    cosmeticLadderDetai = productLadderDetai + ladderDetailedItem.getLadderDetailedAmount().doubleValue();
                }

            }
        }
        Map map = new HashMap();
        map.put("productLadderDetai", productLadderDetai);
        map.put("serviceLadderDetai", serviceLadderDetai);
        map.put("rechargeLadderDetai", rechargeLadderDetai);
        map.put("cosmeticLadderDetai", cosmeticLadderDetai);
        return ResponseResult.success(map);
    }


    @Override
    public ResponseResult selectLadderDetailedByCondition(int pageNum, int pageSize, LadderDetailed ladderDetailed) {
        String[] storeIdArray = ladderDetailed.getStoreIdList().replaceAll("]", "").replace("[", "").split(",");
        if (storeIdArray[0].equals("")) {
            storeIdArray = null;
        }
        ladderDetailed.setList(storeIdArray);
        PageHelper.startPage(pageNum, pageSize);
        List<LadderDetailed> ladderDetailedList = performanceDao.selectLadderDetailedByCondition(ladderDetailed);
        //查询所有的业绩
        List<PerformancePost> performanceList = performanceDao.selectPerformancePostList(new HashMap());
        Map<Integer, String> achievementMethod = EnumUtil.getEnumToMap(AchievementMethodEnum.class);
        //查看所有职位分类列表
        ResponseResult responseResultPostList = storeApi.selectPostCategoryNoPage();
        List<Map> PostCategoryMapList = (List<Map>) responseResultPostList.getResult();
        for (PerformancePost performancePost : performanceList) {
            for (Map.Entry chievementMethodItem : achievementMethod.entrySet()) {
                if (performancePost.getAchievementMethods().toString().equals(chievementMethodItem.getKey().toString())) {
                    performancePost.setAchievementMethodsName(chievementMethodItem.getValue().toString());
                }
            }
            for (Map map : PostCategoryMapList) {
                if (performancePost.getAchievementPostCategoryID().toString().equals(map.get("postCategoryId").toString())) {
                    performancePost.setAchievementPostCategoryName(map.get("name").toString());
                }
            }
        }

        //查询所有的员工
        List<Map> mapListBeautician = (List<Map>) storeApi.selectBeauticianByStoreIdArray(ladderDetailed.getStoreIdList()).getResult();
        //查询所有门店
        List<Map> mapListStore = (List<Map>) storeApi.selectStoreListNoPage().getResult();
        //查询所有订单类型
        Map<Integer, String> orderMasp = EnumUtil.getEnumToMap(OrderTypeEnum.class);
        for (LadderDetailed detailed : ladderDetailedList) {
            for (PerformancePost performance1 : performanceList) {
                if (detailed.getLadderDetailedAchievementID().toString().equals(performance1.getId().toString())) {
                    String isBasicSalaryName = "";
                    if (performance1.getIsBasicSalary() == 0) {
                        isBasicSalaryName = "（无底薪）";
                    } else {
                        isBasicSalaryName = "（有底薪）";
                    }
                    detailed.setAchievementName((performance1.getAchievementPostCategoryName() == null ? "所有职位" : performance1.getAchievementPostCategoryName()) + performance1.getAchievementMethodsName() + isBasicSalaryName);
                }
            }

            for (Map.Entry orderTypeItem : orderMasp.entrySet()) {
                if (detailed.getOrderType().toString().equals(orderTypeItem.getKey().toString())) {
                    detailed.setOrderTypeName(orderTypeItem.getValue().toString());
                }
            }

            if (mapListBeautician == null || mapListBeautician.size() == 0) {
                PageInfo<LadderDetailed> pageInfo = new PageInfo<>(new ArrayList<>());
                return ResponseResult.success(pageInfo);
            }

            for (Map mapItem : mapListBeautician) {
                if (detailed.getLadderDetailedBeauticianId() == Long.parseLong(mapItem.get("staffNumber").toString())) {
                    detailed.setBeauticianName(mapItem.get("name").toString());
                }
            }

            for (Map mapItem : mapListStore) {
                if (detailed.getLadderDetailedStoreId() == Long.parseLong(mapItem.get("storeId").toString())) {
                    detailed.setStoreName(mapItem.get("name").toString());
                }
            }
        }
        if (ladderDetailedList != null && ladderDetailedList.size() > 0) {
            PageInfo<LadderDetailed> pageInfo = new PageInfo<>(ladderDetailedList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodePerformanceEnum.LADDERDETAILED_NULL.getCode(),
                ResponseCodePerformanceEnum.LADDERDETAILED_NULL.getDesc()));
    }

    @Override
    public ResponseResult deleteLadderDetailedByID(Long ladderDetailedID, String modifyOperator) {
        //是否存在
        LadderDetailed ladderDetailedByID = performanceDao.selectLadderDetailedByID(ladderDetailedID);
        if (ladderDetailedByID == null) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.NOT_FIND_LADDERDETAILED.getCode(),
                    ResponseCodePerformanceEnum.NOT_FIND_LADDERDETAILED.getDesc()));
        }
        Map map = new HashMap();
        map.put("ladderDetailedByID", ladderDetailedByID);
        map.put("modifyOperator", modifyOperator);
        performanceDao.deleteLadderDetailedByID(map);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteLadderDetailedByOrder(String orderNum, String modifyOperator) {
        Map map = new HashMap();
        map.put("orderNum", orderNum);
        map.put("modifyOperator", modifyOperator);
        performanceDao.deleteLadderDetailedByOrder(map);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult insertLadderDetailed(Integer productType, Long storeId, String beauticianId, String orderNum, String payTypeAndAmount, Integer productNum, String productCode, Double price, String industryId) {
        //根据支付方式，计算支付业绩比例计算业绩
        Double sum = 0.00;
        if (StringUtils.isNotBlank(payTypeAndAmount)) {
            JSONArray jsonArrayPay = JSON.parseArray(payTypeAndAmount);
            List<Map> payTypeList = (List<Map>) payApi.selectPayTypeListAll().getResult();
            for (int i = 0; i < jsonArrayPay.size(); i++) {
                for (Map map : payTypeList) {
                    if (jsonArrayPay.getJSONObject(i).getInteger("payType") == Integer.parseInt(map.get("payTypeId").toString())) {
                        Double paymentRatioOriginal = Double.parseDouble(map.get("paymentRatioOriginal").toString());
                        sum = sum + jsonArrayPay.getJSONObject(i).getDouble("amount") * paymentRatioOriginal;
                    }
                }
            }
        }


        //根据员工及其职位分类查看业绩规则
        JSONArray jsonArrayStaff = JSON.parseArray(beauticianId);
        Map mapResult = new HashMap();
        for (int i = 0; i < jsonArrayStaff.size(); i++) {
            Map beautician = (Map) storeApi.selectBeauticianById(jsonArrayStaff.getJSONObject(i).getLong("beauticianId")).getResult();
            jsonArrayStaff.getJSONObject(i).put("beauticianName", beautician.get("name").toString());
            //根据职位分类去找业绩
            LadderDetailed ladderDetailedForAdd = new LadderDetailed();
            if (productType == ProductTypeEnum.PRODUCT.getCode()) {//如果子订单是商品类产品
                String postId = beautician.get("postId").toString();//职位
                Map mapPost = (Map) storeApi.selectPostById(Long.parseLong(postId)).getResult();
                int isQuandian = Integer.parseInt(mapPost.get("postAchievement").toString());

                Map mapProduct = (Map) productApi.selectProductByCode(productCode).getResult();
                //根据业绩postid找到业绩
                PerformancePost performancePost = null;
                String achievementPostId = null;
                if (!mapProduct.get("achievementPostId").toString().equals("0")) {
                    //如果购买的体验卡指定的业绩，直接找指定业绩
                    Map map1 = new HashMap();
                    map1.put("id", Long.parseLong(mapProduct.get("achievementPostId").toString()));
                    map1.put("achievementPostCategoryID", beautician.get("postCategoryId").toString());
                    performancePost = performanceDao.selectPerformancePosByCondition(map1);
                    if (performancePost != null) {
                        achievementPostId = performancePost.getId().toString();
                    }else{
                        //判断是否有业绩规则
                        //如果没有找到具体的业绩规则 获取此员工的职位  根据职位及商品的业绩分类 去找具体的业绩规则
                        String postCategoryIdResult = beautician.get("postCategoryId").toString();
                        String achievementId = mapProduct.get("achievementId").toString();
                        String industryIdResult = mapProduct.get("industryId").toString();
                        Map map = new HashMap();
                        map.put("achievementPostCategoryID", postCategoryIdResult);
                        map.put("industryId", industryIdResult);
                        map.put("achievementId", achievementId);
                        map.put("isBasicSalary", beautician.get("isBasicSalary"));

                        performancePost = performanceDao.selectPerformancePosByCondition(map);
                        achievementPostId = performancePost.getId().toString();
                        if (performancePost == null) {
                            map.clear();
                            map.put("achievementPostCategoryID", 0);
                            map.put("industryId", industryIdResult);
                            map.put("achievementId", achievementId);
                            map.put("isBasicSalary", beautician.get("isBasicSalary"));
                            performancePost = performanceDao.selectPerformancePosByCondition(map);
                            achievementPostId = performancePost.getId().toString();
                        }
                        if (performancePost == null) {
                            mapResult.put("产品或医美业绩：" + jsonArrayStaff.getJSONObject(i).getString("beauticianName"), "业绩生成失败，未找到对应业绩规则");
                            continue;
                        }
                    }
                } else {
                    //判断是否有业绩规则
                    //如果没有找到具体的业绩规则 获取此员工的职位  根据职位及商品的业绩分类 去找具体的业绩规则
                    String postCategoryIdResult = beautician.get("postCategoryId").toString();
                    String achievementId = mapProduct.get("achievementId").toString();
                    String industryIdResult = mapProduct.get("industryId").toString();
                    Map map = new HashMap();
                    map.put("achievementPostCategoryID", postCategoryIdResult);
                    map.put("industryId", industryIdResult);
                    map.put("achievementId", achievementId);
                    map.put("isBasicSalary", beautician.get("isBasicSalary"));

                    performancePost = performanceDao.selectPerformancePosByCondition(map);
                    achievementPostId = performancePost.getId().toString();
                    if (performancePost == null) {
                        map.clear();
                        map.put("achievementPostCategoryID", 0);
                        map.put("industryId", industryIdResult);
                        map.put("achievementId", achievementId);
                        map.put("isBasicSalary", beautician.get("isBasicSalary"));
                        performancePost = performanceDao.selectPerformancePosByCondition(map);
                        achievementPostId = performancePost.getId().toString();
                    }
                    if (performancePost == null) {
                        mapResult.put("产品或医美业绩：" + jsonArrayStaff.getJSONObject(i).getString("beauticianName"), "业绩生成失败，未找到对应业绩规则");
                        continue;
                    }
                }
                //查看阶梯
                ladderDetailedForAdd.setOrderType(OrderTypeEnum.PRODUCT_ORDER.getCode());//订单类型
                ladderDetailedForAdd.setLadderDetailedOrderId(orderNum);//订单号
                ladderDetailedForAdd.setLadderDetailedAchievementID(Long.parseLong(achievementPostId));//所属业绩
                ladderDetailedForAdd.setLadderDetailedStoreId(storeId);//所属门店
                if (performancePost.getAchievementMethods() == 1 || performancePost.getAchievementMethods() == 4) {
                    //如果是按个数算
                    ladderDetailedForAdd.setLadderDetailedNumber(new BigDecimal(productNum * jsonArrayStaff.getJSONObject(i).getDouble("ratio") / 100));//业绩金额
                } else {
                    //如果是按金额算
                    ladderDetailedForAdd.setLadderDetailedAmount(new BigDecimal(sum * jsonArrayStaff.getJSONObject(i).getDouble("ratio") / 100));//业绩金额
                }
                ladderDetailedForAdd.setLadderDetailedBeauticianId(jsonArrayStaff.getJSONObject(i).getLong("beauticianId"));
                mapResult.put("产品或医美业绩：" + jsonArrayStaff.getJSONObject(i).getString("beauticianName"), "业绩生成成功");
                performanceDao.insertLadderDetailed(ladderDetailedForAdd);
            } else if (productType == ProductTypeEnum.EXPERIENCECARD.getCode()) {//如果是体验卡
                String postId = beautician.get("postId").toString();//职位
                Map mapPost = (Map) storeApi.selectPostById(Long.parseLong(postId)).getResult();
                int isQuandian = Integer.parseInt(mapPost.get("postAchievement").toString());

                Map mapProductList = (Map) storeApi.selectExperienceCardByCardId(Long.parseLong(productCode)).getResult();
                //根据业绩postid找到业绩
                PerformancePost performancePost = null;
                String achievementPostId = null;
                if (!mapProductList.get("achievementPostId").toString().equals("0")) {
                    //如果购买的体验卡指定的业绩，直接找指定业绩
                    Map map1 = new HashMap();
                    map1.put("id", Long.parseLong(mapProductList.get("achievementPostId").toString()));
                    map1.put("achievementPostCategoryID", beautician.get("postCategoryId").toString());
                    performancePost = performanceDao.selectPerformancePosByCondition(map1);
                    if (performancePost != null) {
                        achievementPostId = performancePost.getId().toString();
                    } else {
                        //如果购买的体验卡指定了业绩，但是不是当前职位的 ，就去根据职位分类 和 行业 和 业绩分类 和是否为计算底薪 查找业绩
                        String postCategoryIdResult = beautician.get("postCategoryId").toString();
                        String achievementId = mapProductList.get("achievementId").toString();
                        Map map = new HashMap();
                        map.put("achievementPostCategoryID", postCategoryIdResult);
                        map.put("industryId", industryId);
                        map.put("achievementId", achievementId);
                        map.put("isBasicSalary", beautician.get("isBasicSalary"));

                        performancePost = performanceDao.selectPerformancePosByCondition(map);
                        achievementPostId = performancePost.getId().toString();
                        if (performancePost == null) {
                            //如果还是没找到  就去根据所有职位 和 行业 和 业绩分类 和是否为计算底薪 查找业绩
                            map.clear();
                            map.put("achievementPostCategoryID", 0);
                            map.put("industryId", industryId);
                            map.put("achievementId", achievementId);
                            map.put("isBasicSalary", beautician.get("isBasicSalary"));
                            performancePost = performanceDao.selectPerformancePosByCondition(map);
                            achievementPostId = performancePost.getId().toString();
                        }
                        if (performancePost == null) {
                            mapResult.put("体验卡业绩：" + jsonArrayStaff.getJSONObject(i).getString("beauticianName"), "业绩生成失败，未找到对应业绩规则");
                            continue;
                        }
                    }

                } else {
                    //如果购买的体验卡没有指定业绩，就去根据职位分类 和 行业 和 业绩分类 和是否为计算底薪 查找业绩
                    String postCategoryIdResult = beautician.get("postCategoryId").toString();
                    String achievementId = mapProductList.get("achievementId").toString();
                    Map map = new HashMap();
                    map.put("achievementPostCategoryID", postCategoryIdResult);
                    map.put("industryId", industryId);
                    map.put("achievementId", achievementId);
                    map.put("isBasicSalary", beautician.get("isBasicSalary"));

                    performancePost = performanceDao.selectPerformancePosByCondition(map);
                    achievementPostId = performancePost.getId().toString();
                    if (performancePost == null) {
                        //如果还是没找到  就去根据所有职位 和 行业 和 业绩分类 和是否为计算底薪 查找业绩
                        map.clear();
                        map.put("achievementPostCategoryID", 0);
                        map.put("industryId", industryId);
                        map.put("achievementId", achievementId);
                        map.put("isBasicSalary", beautician.get("isBasicSalary"));
                        performancePost = performanceDao.selectPerformancePosByCondition(map);
                        achievementPostId = performancePost.getId().toString();
                    }
                    if (performancePost == null) {
                        mapResult.put("体验卡业绩：" + jsonArrayStaff.getJSONObject(i).getString("beauticianName"), "业绩生成失败，未找到对应业绩规则");
                        continue;
                    }
                }


                //查看阶梯
                Ladder ladder = new Ladder();
                ladder.setLadderAchievementPostID(performancePost.getId());
                Ladder ladder1 = performanceDao.selectLadderByCondition(ladder).get(0);
                performancePost.setSinglePrize(ladder1.getLadderBonus().doubleValue());
                ladderDetailedForAdd.setOrderType(OrderTypeEnum.EXPCARD_ORDER.getCode());//商品订单业绩
                ladderDetailedForAdd.setLadderDetailedOrderId(orderNum);//订单号
                ladderDetailedForAdd.setLadderDetailedAchievementID(Long.parseLong(achievementPostId));//所属业绩
                ladderDetailedForAdd.setLadderDetailedStoreId(storeId);//所属门店

                if (performancePost.getAchievementMethods() == 1 || performancePost.getAchievementMethods() == 4) {
                    //如果是按个数算
                    ladderDetailedForAdd.setLadderDetailedNumber(new BigDecimal(productNum));//业绩金额
                } else {
                    //如果是按金额算
                    ladderDetailedForAdd.setLadderDetailedAmount(new BigDecimal(sum));//业绩金额
                }

                ladderDetailedForAdd.setLadderDetailedBeauticianId(jsonArrayStaff.getJSONObject(i).getLong("beauticianId"));
                mapResult.put("体验卡业绩：" + jsonArrayStaff.getJSONObject(i).getString("beauticianName"), "业绩生成成功");
                performanceDao.insertLadderDetailed(ladderDetailedForAdd);
            } else if (productType == ProductTypeEnum.SERVICE.getCode() || productType == ProductTypeEnum.COSMETIC.getCode()) {//如果子订单是服务类产品
                //根据职位查看业绩id
                String postCategoryId = beautician.get("postCategoryId").toString();//职位分类
                String isBasicSalary = beautician.get("isBasicSalary").toString();//是否计算底薪
                String isPartTime = beautician.get("isPartTime").toString();//是否为兼职
                String postId = beautician.get("postId").toString();//职位
                //查看职位是全店业绩还是个人业绩
                Map mapPost = (Map) storeApi.selectPostById(Long.parseLong(postId)).getResult();
                int isQuandian = Integer.parseInt(mapPost.get("postAchievement").toString());

                //查看员工是否有兼职职位
                String partTimePostId = beautician.get("partTimePostId").toString();
                String partTimePostCategoryId = beautician.get("partTimePostCategoryId").toString();

                Map map = new HashMap();
                if (isPartTime.equals("1")) {
                    //如果这个员工是兼职的 那么要加上兼职这个条件
                    map.put("isPartTime", isPartTime);
                }
                map.put("achievementPostCategoryID", postCategoryId);
                map.put("isBasicSalary", isBasicSalary);
                map.put("achievementStoreId", storeId);
                map.put("industryId", industryId);
                map.put("achievementType", OrderTypeEnum.SERVICE_ORDER.getCode());
                map.put("achievementStore", isQuandian);

                PerformancePost performancePost = iPerformanceDao.selectPerformancePosByCondition(map);

                if (performancePost == null) {
                    //匹配所有行业
                    map.put("industryId", 0);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }
                if (performancePost == null) {
                    //所有行业，所有门店的
                    map.put("industryId", 0);
                    map.put("achievementStoreId", 0);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }
                if (performancePost == null) {
                    //匹配所有行业，所有门店，所有职位
                    map.put("industryId", 0);
                    map.put("achievementStoreId", 0);
                    map.put("achievementPostCategoryID", 0);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }

                if (performancePost == null) {
                    //匹配指定行业，所有门店，所有职位
                    map.put("industryId", industryId);
                    map.put("achievementStoreId", 0);
                    map.put("achievementPostCategoryID", 0);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }

                if (performancePost == null) {
                    //匹配所有行业，指定门店，所有职位
                    map.put("industryId", 0);
                    map.put("achievementStoreId", storeId);
                    map.put("achievementPostCategoryID", 0);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }

                if (performancePost == null) {
                    //匹配所有行业，所有门店，指定职位
                    map.put("industryId", 0);
                    map.put("achievementStoreId", 0);
                    map.put("achievementPostCategoryID", postCategoryId);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }
                if (performancePost == null) {
                    //匹配指定行业，所有门店，指定职位
                    map.put("industryId", industryId);
                    map.put("achievementStoreId", 0);
                    map.put("achievementPostCategoryID", postCategoryId);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }
                if (performancePost == null) {
                    //匹配指定行业，所有门店，指定职位
                    map.put("industryId", industryId);
                    map.put("achievementStoreId", storeId);
                    map.put("achievementPostCategoryID", 0);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }
                if (performancePost == null) {
                    //匹配指定行业，所有门店，指定职位
                    map.put("industryId", 0);
                    map.put("achievementStoreId", storeId);
                    map.put("achievementPostCategoryID", postCategoryId);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }
                if (performancePost == null) {
                    //判断是否有业绩规则
                    mapResult.put("护理业绩：" + jsonArrayStaff.getJSONObject(i).getString("beauticianName"), "业绩生成失败，未找到对应业绩规则");
                    if (partTimePostId.equals("0")) {
                        continue;
                    }

/*                    return ResponseResult.error(new Error(ResponseCodePerformanceEnum.PERFORMANCE_NULL.getCode(),
                            ResponseCodePerformanceEnum.PERFORMANCE_NULL.getDesc()));*/
                } else {
                    mapResult.put("护理业绩：" + jsonArrayStaff.getJSONObject(i).getString("beauticianName"), "业绩生成成功");
                }
                if (performancePost != null) {
                    if (performancePost.getAchievementMethods() == AchievementMethodEnum.NUMBER_LADDER_ROYALTY.getCode() ||
                            performancePost.getAchievementMethods() == AchievementMethodEnum.NUMBER_ROYALTY.getCode()) {
                        if (jsonArrayStaff.getJSONObject(i).getDouble("number") == null) {
                            ladderDetailedForAdd.setLadderDetailedNumber(new BigDecimal(1));
                        } else {
                            ladderDetailedForAdd.setLadderDetailedNumber(new BigDecimal(jsonArrayStaff.getJSONObject(i).getDouble("number")));
                        }

                    } else {
                        if (orderNum.contains("HK") && systemApi.selectParameterByWhere(1l).getResult() != null) {
                            //如果是划卡
                            //查找划卡计算比例
                            List<Map> mapList = (List<Map>) systemApi.selectParameterByWhere(1l).getResult();
                            Double reosi;
                            if (mapList != null) {
                                reosi = Double.parseDouble((mapList.get(0).get("parameterValue")).toString());
                            } else {
                                reosi = 1.00;
                            }
                            ladderDetailedForAdd.setLadderDetailedAmount(new BigDecimal(price * reosi));
                        } else {
                            ladderDetailedForAdd.setLadderDetailedAmount(new BigDecimal(sum));
                        }
                    }
                    ladderDetailedForAdd.setOrderType(OrderTypeEnum.SERVICE_ORDER.getCode());
                    ladderDetailedForAdd.setLadderDetailedOrderId(orderNum);
                    ladderDetailedForAdd.setLadderDetailedAchievementID(performancePost.getId());
                    ladderDetailedForAdd.setLadderDetailedStoreId(storeId);
                    ladderDetailedForAdd.setLadderDetailedBeauticianId(jsonArrayStaff.getJSONObject(i).getLong("beauticianId"));
                    performanceDao.insertLadderDetailed(ladderDetailedForAdd);
                }


                if (!partTimePostId.equals("0")) {
                    ladderDetailedForAdd = new LadderDetailed();
                    //如果这个员工有兼职岗位
                    Map mapPartTime = new HashMap();
                    if (isPartTime.equals("1")) {
                        //如果这个员工是兼职的 那么要加上兼职这个条件
                        mapPartTime.put("isPartTime", isPartTime);
                    }
                    mapPartTime.put("achievementPostCategoryID", partTimePostCategoryId);
                    mapPartTime.put("isBasicSalary", isBasicSalary);
                    mapPartTime.put("achievementStoreId", storeId);
                    mapPartTime.put("industryId", industryId);
                    mapPartTime.put("achievementType", OrderTypeEnum.SERVICE_ORDER.getCode());
                    mapPartTime.put("achievementStore", isQuandian);

                    PerformancePost performancePostPartTime = iPerformanceDao.selectPerformancePosByCondition(mapPartTime);

                    if (performancePostPartTime == null) {
                        //匹配所有行业
                        mapPartTime.put("industryId", 0);
                        performancePostPartTime = iPerformanceDao.selectPerformancePosByCondition(mapPartTime);
                    }
                    if (performancePostPartTime == null) {
                        //所有行业，所有门店的
                        mapPartTime.put("industryId", 0);
                        mapPartTime.put("achievementStoreId", 0);
                        performancePostPartTime = iPerformanceDao.selectPerformancePosByCondition(mapPartTime);
                    }
                    if (performancePostPartTime == null) {
                        //匹配所有行业，所有门店，所有职位
                        mapPartTime.put("industryId", 0);
                        mapPartTime.put("achievementStoreId", 0);
                        mapPartTime.put("achievementPostCategoryID", 0);
                        performancePostPartTime = iPerformanceDao.selectPerformancePosByCondition(mapPartTime);
                    }

                    if (performancePostPartTime == null) {
                        //匹配指定行业，所有门店，所有职位
                        mapPartTime.put("industryId", industryId);
                        mapPartTime.put("achievementStoreId", 0);
                        mapPartTime.put("achievementPostCategoryID", 0);
                        performancePostPartTime = iPerformanceDao.selectPerformancePosByCondition(mapPartTime);
                    }

                    if (performancePostPartTime == null) {
                        //匹配所有行业，指定门店，所有职位
                        mapPartTime.put("industryId", 0);
                        mapPartTime.put("achievementStoreId", storeId);
                        mapPartTime.put("achievementPostCategoryID", 0);
                        performancePostPartTime = iPerformanceDao.selectPerformancePosByCondition(mapPartTime);
                    }

                    if (performancePostPartTime == null) {
                        //匹配所有行业，所有门店，指定职位
                        mapPartTime.put("industryId", 0);
                        mapPartTime.put("achievementStoreId", 0);
                        mapPartTime.put("achievementPostCategoryID", postCategoryId);
                        performancePostPartTime = iPerformanceDao.selectPerformancePosByCondition(mapPartTime);
                    }
                    if (performancePostPartTime == null) {
                        //匹配指定行业，所有门店，指定职位
                        mapPartTime.put("industryId", industryId);
                        mapPartTime.put("achievementStoreId", 0);
                        mapPartTime.put("achievementPostCategoryID", postCategoryId);
                        performancePostPartTime = iPerformanceDao.selectPerformancePosByCondition(mapPartTime);
                    }
                    if (performancePostPartTime == null) {
                        //匹配指定行业，所有门店，指定职位
                        mapPartTime.put("industryId", industryId);
                        mapPartTime.put("achievementStoreId", storeId);
                        mapPartTime.put("achievementPostCategoryID", 0);
                        performancePostPartTime = iPerformanceDao.selectPerformancePosByCondition(mapPartTime);
                    }
                    if (performancePostPartTime == null) {
                        //匹配指定行业，所有门店，指定职位
                        mapPartTime.put("industryId", 0);
                        mapPartTime.put("achievementStoreId", storeId);
                        mapPartTime.put("achievementPostCategoryID", postCategoryId);
                        performancePostPartTime = iPerformanceDao.selectPerformancePosByCondition(mapPartTime);
                    }
                    if (performancePostPartTime == null) {
                        //判断是否有业绩规则
                        mapResult.put("兼职护理业绩：" + jsonArrayStaff.getJSONObject(i).getString("beauticianName"), "业绩生成失败，未找到对应业绩规则");
                        continue;
                    } else {
                        mapResult.put("兼职护理业绩：" + jsonArrayStaff.getJSONObject(i).getString("beauticianName"), "业绩生成成功");
                    }
                    if (performancePostPartTime.getAchievementMethods() == AchievementMethodEnum.NUMBER_LADDER_ROYALTY.getCode() ||
                            performancePostPartTime.getAchievementMethods() == AchievementMethodEnum.NUMBER_ROYALTY.getCode()) {
                        if (jsonArrayStaff.getJSONObject(i).getDouble("number") == null) {
                            ladderDetailedForAdd.setLadderDetailedNumber(new BigDecimal(1));
                        } else {
                            ladderDetailedForAdd.setLadderDetailedNumber(new BigDecimal(jsonArrayStaff.getJSONObject(i).getDouble("number")));
                        }

                    } else {
                        if (orderNum.contains("HK") && systemApi.selectParameterByWhere(1l).getResult() != null) {
                            //如果是划卡
                            //查找划卡计算比例
                            List<Map> mapList = (List<Map>) systemApi.selectParameterByWhere(1l).getResult();
                            Double reosi;
                            if (mapList != null) {
                                reosi = Double.parseDouble((mapList.get(0).get("parameterValue")).toString());
                            } else {
                                reosi = 1.00;
                            }
                            ladderDetailedForAdd.setLadderDetailedAmount(new BigDecimal(price * reosi));
                        } else {
                            ladderDetailedForAdd.setLadderDetailedAmount(new BigDecimal(sum));
                        }

                    }
                    ladderDetailedForAdd.setOrderType(OrderTypeEnum.SERVICE_ORDER.getCode());
                    ladderDetailedForAdd.setLadderDetailedOrderId(orderNum);
                    ladderDetailedForAdd.setLadderDetailedAchievementID(performancePostPartTime.getId());
                    ladderDetailedForAdd.setLadderDetailedStoreId(storeId);
                    ladderDetailedForAdd.setLadderDetailedBeauticianId(jsonArrayStaff.getJSONObject(i).getLong("beauticianId"));
                    performanceDao.insertLadderDetailed(ladderDetailedForAdd);

                }
            } else if (productType == 0) {//充值
                String postCategoryId = beautician.get("postCategoryId").toString();//职位分类
                String isBasicSalary = beautician.get("isBasicSalary").toString();//是否计算底薪
                String postId = beautician.get("postId").toString();//职位
                //查看职位是全店业绩还是个人业绩
                Map mapPost = (Map) storeApi.selectPostById(Long.parseLong(postId)).getResult();
                int isQuandian = Integer.parseInt(mapPost.get("postAchievement").toString());


                Map map = new HashMap();
                map.put("achievementPostCategoryID", postCategoryId);
                map.put("isBasicSalary", isBasicSalary);
                map.put("achievementStoreId", storeId);
                map.put("industryId", industryId);
                map.put("achievementType", OrderTypeEnum.RECHARGE_ORDER.getCode());
                map.put("achievementStore", isQuandian);
                PerformancePost performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                if (performancePost == null) {
                    //匹配所有行业
                    map.put("industryId", 0);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }
                if (performancePost == null) {
                    //所有行业，所有门店的
                    map.put("industryId", 0);
                    map.put("achievementStoreId", 0);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }
                if (performancePost == null) {
                    //匹配所有行业，所有门店，所有职位
                    map.put("industryId", 0);
                    map.put("achievementStoreId", 0);
                    map.put("achievementPostCategoryID", 0);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }

                if (performancePost == null) {
                    //匹配指定行业，所有门店，所有职位
                    map.put("industryId", industryId);
                    map.put("achievementStoreId", 0);
                    map.put("achievementPostCategoryID", 0);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }

                if (performancePost == null) {
                    //匹配所有行业，指定门店，所有职位
                    map.put("industryId", 0);
                    map.put("achievementStoreId", storeId);
                    map.put("achievementPostCategoryID", 0);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }

                if (performancePost == null) {
                    //匹配所有行业，所有门店，指定职位
                    map.put("industryId", 0);
                    map.put("achievementStoreId", 0);
                    map.put("achievementPostCategoryID", postCategoryId);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }
                if (performancePost == null) {
                    //匹配指定行业，所有门店，指定职位
                    map.put("industryId", industryId);
                    map.put("achievementStoreId", 0);
                    map.put("achievementPostCategoryID", postCategoryId);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }
                if (performancePost == null) {
                    //匹配指定行业，所有门店，指定职位
                    map.put("industryId", industryId);
                    map.put("achievementStoreId", storeId);
                    map.put("achievementPostCategoryID", 0);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }
                if (performancePost == null) {
                    //匹配指定行业，所有门店，指定职位
                    map.put("industryId", 0);
                    map.put("achievementStoreId", storeId);
                    map.put("achievementPostCategoryID", postCategoryId);
                    performancePost = iPerformanceDao.selectPerformancePosByCondition(map);
                }
                if (performancePost == null) {
                    //判断是否有业绩规则
                  /*  return ResponseResult.error(new Error(ResponseCodePerformanceEnum.PERFORMANCE_NULL.getCode(),
                            ResponseCodePerformanceEnum.PERFORMANCE_NULL.getDesc()));*/
                    mapResult.put("充值业绩：" + jsonArrayStaff.getJSONObject(i).getString("beauticianName"), "业绩生成失败，未找到对应业绩规则");
                    continue;
                } else {
                    mapResult.put("充值业绩：" + jsonArrayStaff.getJSONObject(i).getString("beauticianName"), "业绩生成成功");
                }
                ladderDetailedForAdd.setOrderType(OrderTypeEnum.RECHARGE_ORDER.getCode());//商品订单业绩
                ladderDetailedForAdd.setLadderDetailedOrderId(orderNum);//订单号
                ladderDetailedForAdd.setLadderDetailedAchievementID(performancePost.getId());//所属业绩
                ladderDetailedForAdd.setLadderDetailedStoreId(storeId);//所属门店
                ladderDetailedForAdd.setLadderDetailedAmount(new BigDecimal(sum));//业绩金额
                ladderDetailedForAdd.setLadderDetailedBeauticianId(jsonArrayStaff.getJSONObject(i).getLong("beauticianId"));
                performanceDao.insertLadderDetailed(ladderDetailedForAdd);
            }

        }

        /*  mapResult.put("业绩:", "业绩生成成功");*/
        return ResponseResult.success(mapResult);
    }

    @Override
    public ResponseResult updateLadderDetailed(LadderDetailed ladderDetailed) {
        int resultInt = performanceDao.updateLadderDetailed(ladderDetailed);
        return ResponseResult.success(resultInt);
    }

    @Override
    public ResponseResult deleteLadderDetailedByCondition(LadderDetailed ladderDetailed) {
        int resultInt = performanceDao.deleteLadderDetailedByCondition(ladderDetailed);
        return ResponseResult.success(resultInt);
    }

    @Override
    public ResponseResult updateLadder(Ladder ladder) {
        //是否存在
        Ladder ladderByID = performanceDao.selectLadderByID(ladder.getLadderID());
        if (ladderByID == null) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.NOT_FIND_LADDER.getCode(),
                    ResponseCodePerformanceEnum.NOT_FIND_LADDER.getDesc()));
        }
        Long accId = performanceDao.selectPerformancePostById(ladder.getLadderAchievementPostID()).getAchievementID();
        ladder.setLadderAchievementID(accId);
        ladder.setLadderProportion(new BigDecimal(ladder.getLadderProportion().doubleValue() / 100));
        performanceDao.updateLadder(ladder);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectLadderByCondition(int pageNum, int pageSize, Ladder ladder) {
        PageHelper.startPage(pageNum, pageSize);
        List<Ladder> ladderList = performanceDao.selectLadderByCondition(ladder);
        //判定阶梯是否有对应的固定个数业绩或总业绩比例业绩
        List<PerformancePost> performancePostList = performanceDao.selectPerformancePostList(new HashMap());
        for (Ladder ladder1 : ladderList) {
            boolean flag = false;
            for (PerformancePost performancePost : performancePostList) {
                if (ladder1.getLadderAchievementPostID().equals(performancePost.getId()) &&
                        (performancePost.getAchievementMethods().equals(AchievementMethodEnum.NUMBER_ROYALTY.getCode()) ||
                                performancePost.getAchievementMethods().equals(AchievementMethodEnum.ALL_ACHIEVEMENT_ROYALTY.getCode()))) {
                    flag = true;
                    break;
                } else {
                    flag = false;
                }
            }
            if (flag) {
                ladder1.setIshaveParentAch(1);
            } else {
                ladder1.setIshaveParentAch(0);
            }
        }


        if (ladderList != null && ladderList.size() > 0) {
            PageInfo<Ladder> pageInfo = new PageInfo<>(ladderList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodePerformanceEnum.LADDER_NULL.getCode(),
                ResponseCodePerformanceEnum.LADDER_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectLadderList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Ladder> ladderList = performanceDao.selectLadderList();
        if (ladderList != null && ladderList.size() > 0) {
            PageInfo<Ladder> pageInfo = new PageInfo<>(ladderList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodePerformanceEnum.LADDER_NULL.getCode(),
                ResponseCodePerformanceEnum.LADDER_NULL.getDesc()));
    }

    @Override
    public ResponseResult deleteLadderByID(Long ladderID, String modifyOperator) {
        //是否存在
        Ladder ladderByID = performanceDao.selectLadderByID(ladderID);
        if (ladderByID == null) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.NOT_FIND_LADDER.getCode(),
                    ResponseCodePerformanceEnum.NOT_FIND_LADDER.getDesc()));
        }
        Ladder ladder = new Ladder();
        ladder.setLadderID(ladderID);
        ladder.setModifyOperator(modifyOperator);
        performanceDao.deleteLadderByID(ladder);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult insertLadder(Ladder ladder) {
        //获取业绩职位所对应的业绩
        Long accId = performanceDao.selectPerformancePostById(ladder.getLadderAchievementPostID()).getAchievementID();
        ladder.setLadderAchievementID(accId);
        if (ladder.getLadderProportion() != null) {
            ladder.setLadderProportion(new BigDecimal(ladder.getLadderProportion().doubleValue() / 100));
        } else if (ladder.getLadderBonus() != null) {
            ladder.setLadderBonus(ladder.getLadderBonus());
        }

        performanceDao.insertLadder(ladder);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult listAchievementMethods() {
        Map map = EnumUtil.getEnumToMap(AchievementMethodEnum.class);
        return ResponseResult.success(map);
    }

    @Override
    public ResponseResult selectLadderDetailedByRechargeOrderNum(Long orderNumber) {
        LadderDetailed ladderDetailed = performanceDao.selectLadderDetailedByRechargeOrderNum(orderNumber);
        return ResponseResult.success(ladderDetailed);
    }

    @Override
    public ResponseResult selectPerformanceById(Long id) {
        Performance performance = performanceDao.selectPerformanceByID(id);
        return ResponseResult.success(performance);
    }

    @Override
    public ResponseResult selectPerformancePostById(Long id) {
        PerformancePost performancePost = performanceDao.selectPerformancePostById(id);
        return ResponseResult.success(performancePost);
    }

    @Override
    public ResponseResult listAchievementTypeEnum() {
        Map map = EnumUtil.getEnumToMap(AchievementTypeEnum.class);
        return ResponseResult.success(map);
    }

    @Override
    public ResponseResult insertOrderTicheng(OrderTicheng orderTicheng) {
        int i = orderTichengDao.insertOrderTicheng(orderTicheng);
        return ResponseResult.success(orderTicheng);
    }

    @Override
    public ResponseResult updateOrderTichengByID(OrderTicheng orderTicheng) {
        int i = orderTichengDao.updateOrderTichengByID(orderTicheng);
        return ResponseResult.success(orderTicheng);
    }

    @Override
    public ResponseResult deleteOrderTichengByID(Long id, String modifyOperator) {
        OrderTicheng orderTicheng = new OrderTicheng();
        orderTicheng.setID(id);
        orderTicheng.setModifyOperator(modifyOperator);
        int i = orderTichengDao.deleteOrderTichengByID(orderTicheng);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectOrderTichengByID(Long id) {
        OrderTicheng orderTicheng = orderTichengDao.selectOrderTichengByID(id);
        if (orderTicheng != null) {
            return ResponseResult.success(orderTicheng);
        }
        return ResponseResult.error(new Error(ResponseCodePerformanceEnum.NOT_FIND_ORDERTICHENG.getCode(),
                ResponseCodePerformanceEnum.NOT_FIND_ORDERTICHENG.getDesc()));
    }

    @Override
    public ResponseResult selectOrderTichengByCondition(int pageNum, int pageSize, OrderTicheng orderTicheng) {
        PageHelper.startPage(pageNum, pageSize);
        List<OrderTicheng> orderTichengList = orderTichengDao.selectOrderTichengByCondition(orderTicheng);
        if (orderTichengList != null && orderTichengList.size() > 0) {
            PageInfo<OrderTicheng> orderTichengPageInfo = new PageInfo<>(orderTichengList);
            return ResponseResult.success(orderTichengPageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodePerformanceEnum.NOT_FIND_ORDERTICHENG.getCode(),
                ResponseCodePerformanceEnum.NOT_FIND_ORDERTICHENG.getDesc()));
    }

    @Override
    public ResponseResult selectOrderTichengList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OrderTicheng> orderTichengList = orderTichengDao.selectOrderTichengList();
        if (orderTichengList != null && orderTichengList.size() > 0) {
            PageInfo<OrderTicheng> orderTichengPageInfo = new PageInfo<>(orderTichengList);
            return ResponseResult.success(orderTichengPageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodePerformanceEnum.NOT_FIND_ORDERTICHENG.getCode(),
                ResponseCodePerformanceEnum.NOT_FIND_ORDERTICHENG.getDesc()));
    }


}
