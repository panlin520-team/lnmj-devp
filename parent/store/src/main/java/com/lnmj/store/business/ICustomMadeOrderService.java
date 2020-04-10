package com.lnmj.store.business;

import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.entity.ConsumeCustomMade;
import com.lnmj.store.entity.CustomMadeOrder;
import org.springframework.stereotype.Service;

@Service("iCustomMadeOrderService")
public interface ICustomMadeOrderService {

    ResponseResult insertCustomMadeOrder(String orderLink, String mobile, String cardNumber, JSONArray jsonArray);

    ResponseResult selectCustomMadeList(CustomMadeOrder customMadeOrder, int pageNum, int pageSize, String keyWord);

    ResponseResult selectCustomMadeOrderDetailById(Long orderNumber);

    //根据订单号查询定制订单详情和订单
    ResponseResult selectOrderDetailByCondition(int pageNum, int pageSize, Long orderNumber);

    //使用定制订单项目
    ResponseResult UseCustomMade(ConsumeCustomMade consumeCustomMade, String beauticianStrList);

    //根据详情id查询订单使用列表
    ResponseResult selectUserListById(int pageNum, int pageSize, Long customMadeOrderDetailId);

    //根据美容id和护理时间查询定制订单信息
    ResponseResult selectCustomByIdsAndTime(String beauticianId, String nurseTime,Long storeId);

}
