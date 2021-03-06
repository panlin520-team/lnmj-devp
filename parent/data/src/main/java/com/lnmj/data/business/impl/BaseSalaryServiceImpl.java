package com.lnmj.data.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.BaseSalaryEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.business.IBaseSalaryService;
import com.lnmj.data.entity.Basesalary;
import com.lnmj.data.repository.IBaseSalaryDao;
import com.lnmj.data.serviceProxy.StoreApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2019/9/4
 */

@Service("baseSalaryServiceImpl")
public class BaseSalaryServiceImpl implements IBaseSalaryService {

    @Resource
    private IBaseSalaryDao baseSalaryDao;
    @Resource
    private StoreApi storeApi;

    @Override
    public ResponseResult selectList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Basesalary> basesalaryListlist = baseSalaryDao.selectList();
        //查询所有行业信息
        ResponseResult industryNoPage = storeApi.selectListIndustryNoPage();
        List<Map<String,Object>> industryList = (List<Map<String, Object>>) industryNoPage.getResult();
        //查询所有门店信息
        ResponseResult storeListNoPage = storeApi.selectStoreListNoPage();
        List<Map<String,Object>> storeList = (List<Map<String, Object>>) storeListNoPage.getResult();
        //查询所有职务信息
        ResponseResult postList = storeApi.selectPostCategoryNoPage();
        List<Map<String,Object>> result = (List<Map<String, Object>>) postList.getResult();
        List<Map<String,Object>> postCategoryListMap = new ArrayList<>();
        if (result!=null){
            postCategoryListMap =  result;
        }


        for (Basesalary basesalary : basesalaryListlist) {
            for (Map<String, Object> industry : industryList) {
                if (industry.get("industryID").toString().equals(basesalary.getBaseSalaryIndustryID().toString())) {
                    basesalary.setIndustryName((String) industry.get("industryName"));
                }
            }
            for (Map<String, Object> store : storeList) {
                if (store.get("storeId").toString().equals(basesalary.getBaseSalaryStoreId().toString())) {
                    basesalary.setStoreName((String) store.get("name"));
                }
            }
            for (Map<String, Object> postCategory : postCategoryListMap) {
                if (postCategory.get("postCategoryId").toString().equals(basesalary.getBaseSalaryPostID().toString())) {
                    basesalary.setPostName((String) postCategory.get("name"));
                }
            }
        }
        if (basesalaryListlist.size() > 0) {
            PageInfo pageInfo = new PageInfo(basesalaryListlist);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(BaseSalaryEnum.LIST_INFOMATION_ISNULL.getCode(), BaseSalaryEnum.LIST_INFOMATION_ISNULL.getDesc()));
    }

    @Override
    public ResponseResult addBaseSalary(Basesalary basesalary) {
        int result = baseSalaryDao.addBaseSalary(basesalary);
        if (result > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(BaseSalaryEnum.ADD_BASESALARY_FAIL.getCode(), BaseSalaryEnum.ADD_BASESALARY_FAIL.getDesc()));
    }

    @Override
    public ResponseResult updateBaseSalary(Basesalary basesalary) {
        int result = baseSalaryDao.updateBaseSalary(basesalary);
        if (result > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(BaseSalaryEnum.UPDATE_BASESALARY_FAIL.getCode(), BaseSalaryEnum.UPDATE_BASESALARY_FAIL.getDesc()));
    }

    @Override
    public ResponseResult deleteBaseSalary(Long baseSalaryScoreID, String modifyOperator) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("baseSalaryScoreID",baseSalaryScoreID);
        map.put("modifyOperator",modifyOperator);
        int result = baseSalaryDao.deleteBaseSalary(map);
        if (result > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(BaseSalaryEnum.DELETE_BASESALARY_FAIL.getCode(), BaseSalaryEnum.DELETE_BASESALARY_FAIL.getDesc()));
    }
}
