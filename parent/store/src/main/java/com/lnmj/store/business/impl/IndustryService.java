package com.lnmj.store.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.IndustryEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeExperiencecardEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.IIndustryService;
import com.lnmj.store.entity.Industry;
import com.lnmj.store.entity.PostCategory;
import com.lnmj.store.repository.IBeauticianDao;
import com.lnmj.store.repository.IIndustryDao;
import com.lnmj.store.serviceProxy.DataApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2019/9/4
 */
@Transactional
@Service("industryService")
public class IndustryService implements IIndustryService {
    @Autowired
    private IIndustryDao industryDao;
    @Autowired
    private IBeauticianDao iBeauticianDao;
    @Autowired
    private DataApi dataApi;

    @Override
    public ResponseResult selectList(int pageNum, int pageSize, String industryNameKeyword) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("industryNameKeyword", industryNameKeyword);
        List<Industry> list = industryDao.selectList(map);
        if (list.size() > 0) {
            PageInfo pageInfo = new PageInfo(list);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(IndustryEnum.LIST_INFOMATION_ISNULL.getCode(), IndustryEnum.LIST_INFOMATION_ISNULL.getDesc()));
    }

    @Override
    public ResponseResult selectListIndustryNoPage() {
        List<Industry> list = industryDao.selectList(new HashMap());
        //查询所有职位分类
        List<PostCategory> postCategories = iBeauticianDao.selectPostCategoryList(new HashMap());
        //查询所有的业绩分类
        List<Map> performanceList = (List<Map>) dataApi.selectPerformanceListNoPage().getResult();
        for (Industry industry : list) {
            List<Map> performanceAdd = new ArrayList<>();
            if (performanceList != null) {
                for (Map map : performanceList) {
                    if (industry.getIndustryID().toString().equals(map.get("achievementIndustryID").toString())) {
                        performanceAdd.add(map);
                    }
                }
            }
            industry.setPerformanceList(performanceAdd);
        }


        for (Industry industry : list) {
            List<PostCategory> postCategoriesAdd = new ArrayList<>();
            for (PostCategory postCategory : postCategories) {
                if (industry.getIndustryID().equals(postCategory.getIndustryID())) {
                    postCategoriesAdd.add(postCategory);
                }
            }
            industry.setPostCategoryList(postCategoriesAdd);
        }

        if (list.size() > 0) {
            return ResponseResult.success(list);
        }
        return ResponseResult.error(new Error(IndustryEnum.LIST_INFOMATION_ISNULL.getCode(), IndustryEnum.LIST_INFOMATION_ISNULL.getDesc()));
    }

    @Override
    public ResponseResult selectListIndustryById(Long industryID) {
        Industry industry = industryDao.selectListIndustryById(industryID);
        return ResponseResult.success(industry);
    }

    @Override
    public ResponseResult addIndustry(Industry industry) {
        //查看行业名称是否存在
        int resultInt = industryDao.checkIndustryName(industry);
        if (resultInt > 0) {
            return ResponseResult.error(new Error(IndustryEnum.INDUSTRY_NAME_IS_EXIST.getCode(), IndustryEnum.INDUSTRY_NAME_IS_EXIST.getDesc()));
        }

        int result = industryDao.addIndustry(industry);
        if (result <= 0) {
            return ResponseResult.error(new Error(IndustryEnum.ADD_INDUSTRY_FAIL.getCode(), IndustryEnum.ADD_INDUSTRY_FAIL.getDesc()));
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("-------延迟5000毫秒，每1000毫秒执行一次--------");
            }
        }, 500000000, 1000);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateIndustry(Industry industry) {
        int result = industryDao.updateIndustry(industry);
        if (result <= 0) {
            return ResponseResult.error(new Error(IndustryEnum.UPDATE_INDUSTRY_FAIL.getCode(), IndustryEnum.UPDATE_INDUSTRY_FAIL.getDesc()));
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteIndustry(Industry industry) {
        int result = industryDao.deleteIndustry(industry);
        if (result <= 0) {
            return ResponseResult.error(new Error(IndustryEnum.DELETE_INDUSTRY_FAIL.getCode(), IndustryEnum.DELETE_INDUSTRY_FAIL.getDesc()));
        }
        return ResponseResult.success();
    }
}
