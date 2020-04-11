package com.lnmj.store.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonArray;
import com.lnmj.common.Enum.OrderTypeEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeCustomMadeEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.NumberUtils;
import com.lnmj.store.business.ICustomMadeOrderService;
import com.lnmj.store.entity.ConsumeCustomMade;
import com.lnmj.store.entity.CustomMadeOrder;
import com.lnmj.store.entity.CustomMadeOrderDetail;
import com.lnmj.store.entity.VO.ConsumeCustomMadeVO;
import com.lnmj.store.entity.VO.CustomMadeOrderVO;
import com.lnmj.store.repository.IConsumeCustomMadeDao;
import com.lnmj.store.repository.ICustomMadeOrderDao;
import com.lnmj.store.repository.ICustomMadeOrderDetailDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
@Transactional
@Service("customMadeOrderService")
public class CustomMadeOrderService implements ICustomMadeOrderService {

    @Resource
    private ICustomMadeOrderDetailDao customMadeOrderDetailDao;
    @Resource
    private IConsumeCustomMadeDao consumeCustomMadeDao;
    @Resource
    private ICustomMadeOrderDao customMadeOrderDao;

    @Override
    public ResponseResult insertCustomMadeOrder(String orderLink, String mobile, String cardNumber, JSONArray jsonArray) {
        if (jsonArray.size() == 0) {
            return ResponseResult.error(new Error(ResponseCodeCustomMadeEnum.CUSTOMMADEORDERDETAIL_INFO_IS_NULL.getCode(), ResponseCodeCustomMadeEnum.CUSTOMMADEORDERDETAIL_INFO_IS_NULL.getDesc()));
        }
        //订单号
        String randomOrderNo = OrderTypeEnum.CUSTOM_ORDER.getCode()+NumberUtils.getRandomOrderNo();
        //一个订单可以有多个定制订单
        int totailPrice = 0;
        CustomMadeOrderDetail customMadeOrderDetail = new CustomMadeOrderDetail();
        for (int i = 0; i < jsonArray.size(); i++) {
            customMadeOrderDetail.setServiceProductId(Long.parseLong(jsonArray.getJSONObject(i).getString("serviceProductId")));
            customMadeOrderDetail.setProjectName(jsonArray.getJSONObject(i).getString("projectName"));
            customMadeOrderDetail.setCustomMadeCount(Integer.parseInt(jsonArray.getJSONObject(i).getString("customMadeCount")));
            customMadeOrderDetail.setPrice(BigDecimal.valueOf(Long.valueOf(jsonArray.getJSONObject(i).getString("price"))));
            customMadeOrderDetail.setOriginalPrice(BigDecimal.valueOf(Long.valueOf(jsonArray.getJSONObject(i).getString("originalPrice"))));
            customMadeOrderDetail.setIntegral(Integer.parseInt(jsonArray.getJSONObject(i).getString("integral")));
            customMadeOrderDetail.setManualCost(BigDecimal.valueOf(Long.valueOf(jsonArray.getJSONObject(i).getString("manualCost"))));
            customMadeOrderDetail.setValiditytime(jsonArray.getJSONObject(i).getString("validitytime"));
            customMadeOrderDetail.setRoyaltyAmount(BigDecimal.valueOf(Long.valueOf(jsonArray.getJSONObject(i).getString("royaltyAmount"))));
            customMadeOrderDetail.setAttribute(jsonArray.getJSONObject(i).getString("attribute"));
            customMadeOrderDetail.setOrderNumber(Long.parseLong(randomOrderNo));
            //新增定制订单详情
            int result = customMadeOrderDetailDao.insertCustomMadeOrderDetail(customMadeOrderDetail);
            if (result <= 0) {
                return ResponseResult.error(new Error(ResponseCodeCustomMadeEnum.INSERT_CUSTOMMADEORDERDETAIL_FAIL.getCode(), ResponseCodeCustomMadeEnum.INSERT_CUSTOMMADEORDERDETAIL_FAIL.getDesc()));
            }
            totailPrice += Integer.parseInt(jsonArray.getJSONObject(i).getString("price"));
        }
        CustomMadeOrder customMadeOrder = new CustomMadeOrder();
        //新增定制订单
        customMadeOrder.setOrderNumber(Long.parseLong(randomOrderNo));
        customMadeOrder.setOrderType(OrderTypeEnum.CUSTOM_ORDER.getCode());
        customMadeOrder.setOrderLink(orderLink);
        customMadeOrder.setMobile(mobile);
        customMadeOrder.setCardNumber(cardNumber);
        customMadeOrder.setTotalPrice(new BigDecimal(totailPrice));
        customMadeOrder.setPayPrice(new BigDecimal(totailPrice));
        int inserOrderResult = customMadeOrderDao.insertOrder(customMadeOrder);
        if (inserOrderResult <= 0) {
            return ResponseResult.error(new Error(ResponseCodeCustomMadeEnum.INSERT_ORDER_FAIL.getCode(), ResponseCodeCustomMadeEnum.INSERT_ORDER_FAIL.getDesc()));
        }
        return  ResponseResult.success();
    }

    @Override
    public ResponseResult selectCustomMadeList(CustomMadeOrder customMadeOrder, int pageNum, int pageSize, String keyWord) {
        PageHelper.startPage(pageNum,pageSize);
        Map map = new HashMap<>();
        if (StringUtils.isNotBlank(keyWord)) {
            map.put("keyWord", keyWord);
        }
        List<CustomMadeOrder> customMadeList = customMadeOrderDao.selectCustomMadeOrderList(map);
        if (customMadeList.size() !=0) {
            PageInfo pageInfo = new PageInfo(customMadeList);
            return ResponseResult.success(pageInfo);
        }
            return ResponseResult.error(new Error(ResponseCodeCustomMadeEnum.CUSTOMMADE_IS_NULL.getCode(),ResponseCodeCustomMadeEnum.CUSTOMMADE_IS_NULL.getDesc()));
    }



    @Override
    public ResponseResult selectCustomMadeOrderDetailById(Long orderNumber) {
        CustomMadeOrder customMadeOrder = customMadeOrderDao.selectCustomMadeOrderDetailById(orderNumber);
        if (customMadeOrder == null) {
            return ResponseResult.error(new Error(ResponseCodeCustomMadeEnum.CUSTOMMADE_IS_NOT_FOUND.getCode(), ResponseCodeCustomMadeEnum.CUSTOMMADE_IS_NOT_FOUND.getDesc()));
        }
        return ResponseResult.success(customMadeOrder);
    }


    //根据订单号查询定制订单详情和订单
    @Override
    public ResponseResult selectOrderDetailByCondition(int pageNum, int pageSize, Long orderNumber) {
        PageHelper.startPage(pageNum,pageSize);
        List<CustomMadeOrderVO> list = customMadeOrderDetailDao.selectOrderDetailByCondition(orderNumber);
        PageInfo<CustomMadeOrderVO> pageInfo = new PageInfo(list);
        return ResponseResult.success(pageInfo);
    }

    //使用定制订单项目
    @Override
    public ResponseResult UseCustomMade(ConsumeCustomMade consumeCustomMade, String beauticianStrList) {
//        //美容师赋值
//        JSONArray beauticianArr = JSONArray.parseArray(String.valueOf(consumeCustomMade.getBeautician()));
//        consumeCustomMade.setBeautician(getArrParams(beauticianArr));
//        //美容师id赋值
//        JSONArray beauticianIdArr = JSONArray.parseArray(String.valueOf(consumeCustomMade.getBeauticianId()));
//        consumeCustomMade.setBeauticianId(getArrParams(beauticianIdArr));
//        //业绩占比赋值
//        JSONArray  achievementArr =JSONArray.parseArray(String.valueOf(consumeCustomMade.getAchievementProportion()));
//        consumeCustomMade.setAchievementProportion(getArrParams(achievementArr));
        //处理美容师json信息
        JSONArray jsonArray = JSON.parseArray(beauticianStrList);

        consumeCustomMade.setBookingBeauticianIds(beauticianStrList);
        //添加使用定制项目数据
        int result = consumeCustomMadeDao.insertConsumeCustomMade(consumeCustomMade);
//       //更新定制项目使用次数
        int countResult = customMadeOrderDetailDao.updateCustommade(consumeCustomMade.getCustomMadeOrderDetailId());
        if (result <= 0 && countResult <= 0 ) {
            return ResponseResult.error(new Error(ResponseCodeCustomMadeEnum.USR_CUSTOMMADE_FAIL.getCode(), ResponseCodeCustomMadeEnum.USR_CUSTOMMADE_FAIL.getDesc()));
        }
        return ResponseResult.success();
    }


    //根据详情id查询订单使用列表
    @Override
    public ResponseResult selectUserListById(int pageNum, int pageSize, Long customMadeOrderDetailId) {
        PageHelper.startPage(pageNum,pageSize);
        List<ConsumeCustomMadeVO> consumeCustomMadeList = consumeCustomMadeDao.selectUserListById(customMadeOrderDetailId);
        if (consumeCustomMadeList.size() !=0) {
            PageInfo pageInfo = new PageInfo(consumeCustomMadeList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeCustomMadeEnum.CONSUMECUSTOMMADE_IS_NULL.getCode(),ResponseCodeCustomMadeEnum.CONSUMECUSTOMMADE_IS_NULL.getDesc()));
    }


    //根据美容id和护理时间查询定制订单信息
    @Override
    public ResponseResult selectCustomByIdsAndTime(String beauticianId, String nurseTime,Long storeId) {
        HashMap<Object, Object> map = new HashMap<>();
        List<ConsumeCustomMade> orderlist = new ArrayList<>();
        List<ConsumeCustomMade> consumeCustomMadeList = consumeCustomMadeDao.selectConsumeCustomMadeList();
        consumeCustomMadeList.forEach(consumeCustomMade -> {
            String bookingBeauticianIds = consumeCustomMade.getBookingBeauticianIds();
            JSONArray jsonArray = JSONArray.parseArray(bookingBeauticianIds);
//            Date date = consumeCustomMade.getNurseTime();
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String formatDate = format.format(date);
//            if (nurseTime.equals(formatDate)) {
//                orderlist.add(consumeCustomMade);
//            }
            for (int i = 0; i < jsonArray.size(); i++) {
                String id = jsonArray.getJSONObject(i).getString("beauticianId");
                if (beauticianId.equals(id)) {
                    orderlist.add(consumeCustomMade);
                }
            }
        });
        map.put("list", orderlist);
        return ResponseResult.success(map);

//        if (StringUtils.isNotBlank(beauticianId)) {
//            map.put("beauticianId", beauticianId);
//        }
//        if (storeId != null) {
//            map.put("storeId", storeId);
//        }
//        if (StringUtils.isNotBlank(nurseTime)) {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            try {
//                Date date = format.parse(nurseTime);
//                map.put("nurseTime", date);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//        List<ConsumeCustomMade> list = consumeCustomMadeDao.selectCustomByIdsAndTime(map);
//        if (list.size() !=0 && list != null) {
//            return ResponseResult.success(list);
//        }
//        return ResponseResult.error(new Error(ResponseCodeCustomMadeEnum.MEIRONGSHIANDTIME_CONSUMECUSTOMMADE_INFO_IS_NULL.getCode(),ResponseCodeCustomMadeEnum.MEIRONGSHIANDTIME_CONSUMECUSTOMMADE_INFO_IS_NULL.getDesc()));
    }



    public String getArrParams(JSONArray param) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < param.size(); i++) {
            if (param.size()-1 == i) {
                buffer.append(param.get(i));
            } else {
                buffer.append(param.get(i)+",");
            }
        }
        return buffer.toString();
    }
}
