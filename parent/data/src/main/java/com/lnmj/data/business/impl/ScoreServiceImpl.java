package com.lnmj.data.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.IndustryEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ScoreEnum;
import com.lnmj.common.Enum.ScoreModeEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.EnumUtil;
import com.lnmj.data.business.IScoreService;
import com.lnmj.data.entity.Performance;
import com.lnmj.data.entity.PerformancePost;
import com.lnmj.data.entity.Score;
import com.lnmj.data.repository.IPerformanceDao;
import com.lnmj.data.repository.IScoreDao;
import com.lnmj.data.serviceProxy.StoreApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2019/9/4
 */

@Service("scoreServiceImpl")
public class ScoreServiceImpl implements IScoreService {

    @Resource
    private IScoreDao scoreDao;
    @Resource
    private StoreApi storeApi;
    @Resource
    private IPerformanceDao performanceDao;

    @Override
    public ResponseResult selectList(int pageNum, int pageSize, String storeId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Score> list = scoreDao.selectList();
        //查询所有的职务
        ResponseResult postCategoryList = storeApi.selectPostCategoryNoPage();
        List<Map<String, Object>> result = (List<Map<String, Object>>) postCategoryList.getResult();
        //查询所有业绩
        String[] storeIdArray = storeId.replaceAll("]", "").replace("[", "").split(",");
        Map mapList = new HashMap();
        mapList.put("list", storeIdArray);
        List<PerformancePost> performancePostList = performanceDao.selectPerformancePostList(mapList);
        for (Score score : list) {
            if (result != null) {
                for (Map<String, Object> map : result) {
                    if (score.getScorePostID().toString().equals(map.get("postCategoryId").toString())) {
                        score.setPostName(map.get("name").toString());
                    }
                }
            }
            for (PerformancePost performancePost : performancePostList) {
                if (performancePost.getId().toString().equals(score.getScoreAchievementID().toString())) {
                    score.setAchievementName(performancePost.getAchievementPostName());
                }
            }
        }
        if (list.size() > 0) {
            PageInfo pageInfo = new PageInfo(list);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ScoreEnum.LIST_INFOMATION_ISNULL.getCode(), ScoreEnum.LIST_INFOMATION_ISNULL.getDesc()));
    }

    @Override
    public ResponseResult selectScoreById(Long scoreID) {
        Score score = scoreDao.selectScoreById(scoreID);
        return ResponseResult.success(score);
    }

    @Override
    public ResponseResult addScore(Score score) {
        int result = scoreDao.addScore(score);
        if (result > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ScoreEnum.ADD_SCORE_FAIL.getCode(), ScoreEnum.ADD_SCORE_FAIL.getDesc()));
    }

    @Override
    public ResponseResult updateScore(Score score) {
        int result = scoreDao.updateScore(score);
        if (result > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ScoreEnum.UPDATE_SCORE_FAIL.getCode(), ScoreEnum.UPDATE_SCORE_FAIL.getDesc()));
    }

    @Override
    public ResponseResult deleteScore(Long scoreID, String modifyOperator) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("scoreID", scoreID);
        map.put("modifyOperator", modifyOperator);
        int result = scoreDao.deleteScore(map);
        if (result > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ScoreEnum.DELETE_SCORE_FAIL.getCode(), ScoreEnum.DELETE_SCORE_FAIL.getDesc()));
    }

    @Override
    public ResponseResult selectScoreMode() {
        Map<Integer, String> map = EnumUtil.getEnumToMap(ScoreModeEnum.class);
        return ResponseResult.success(map);
    }
}
