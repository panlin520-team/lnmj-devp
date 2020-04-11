package com.lnmj.store.business.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.OrderStatusEnum;
import com.lnmj.common.Enum.OrderTypeEnum;
import com.lnmj.common.Enum.ProductTypeDistinguishEnum;
import com.lnmj.common.Enum.ProductTypeEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeExperiencecardEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeSubordBuyLimitEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.MemberUtil;
import com.lnmj.common.utils.NumberUtils;
import com.lnmj.store.business.IExperienceCardService;
import com.lnmj.store.business.ISubordBuyLimitService;
import com.lnmj.store.entity.*;
import com.lnmj.store.repository.IExperienceCardDao;
import com.lnmj.store.repository.ISubordBuyLimitDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @Author panlin
 * @create 2019/8/25
 */
@Transactional
@Service("subordBuyLimitService")
public class SubordBuyLimitService implements ISubordBuyLimitService {
    @Resource
    private ISubordBuyLimitDao iSubordBuyLimitDao;


    @Override
    public ResponseResult selectSubordBuyLimitList(int pageNum, int pageSize,Integer productType,String keyWordSubordBuyLimitName) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("keyWordSubordBuyLimitName",keyWordSubordBuyLimitName);
        map.put("productType",productType);
        List<SubordBuyLimit> subordBuyLimitList = iSubordBuyLimitDao.selectSubordBuyLimitList(map);
        if (subordBuyLimitList.size() > 0) {
            PageInfo pageInfo = new PageInfo(subordBuyLimitList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeSubordBuyLimitEnum.SUBORD_BUY_LIMIT_LIST_NULL.getCode(), ResponseCodeSubordBuyLimitEnum.SUBORD_BUY_LIMIT_LIST_NULL.getDesc()));
    }

    @Override
    public ResponseResult deleteSubordBuyLimit(Map map) {
        iSubordBuyLimitDao.deleteSubordBuyLimit(map);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectSubordBuyLimitProductType() {
        return ResponseResult.success(MemberUtil.getEnumToMap(ProductTypeEnum.class));
    }

    @Override
    public ResponseResult addSubordBuyLimit(SubordBuyLimit subordBuyLimit) {
        if (subordBuyLimit.getSubordBuyLimitNumber()==0){
            return ResponseResult.error(new Error(ResponseCodeSubordBuyLimitEnum.SUBORD_BUY_LIMIT_NUM_ZERO.getCode(), ResponseCodeSubordBuyLimitEnum.SUBORD_BUY_LIMIT_NUM_ZERO.getDesc()));
        }
        iSubordBuyLimitDao.addSubordBuyLimit(subordBuyLimit);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateSubordBuyLimit(SubordBuyLimit subordBuyLimit) {
        iSubordBuyLimitDao.updateSubordBuyLimit(subordBuyLimit);
        return ResponseResult.success();
    }


}
