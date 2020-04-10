package com.lnmj.store.business;

import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.entity.*;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("iSubordBuyLimitService")
public interface ISubordBuyLimitService {
    ResponseResult selectSubordBuyLimitList(int pageNum, int pageSize,Integer productType,String keyWordSubordBuyLimitName);
    ResponseResult deleteSubordBuyLimit(Map map);
    ResponseResult selectSubordBuyLimitProductType();
    ResponseResult addSubordBuyLimit(SubordBuyLimit subordBuyLimit);
    ResponseResult updateSubordBuyLimit(SubordBuyLimit subordBuyLimit);
}
