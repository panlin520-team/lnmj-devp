package com.lnmj.store.serviceProxy;


import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author: panlin
 * @Date: 2019/5/24 17:57
 * @Description:
 */
@FeignClient(name = "lnmj-order")
public interface OrderApi {
    @ApiOperation(value = "根据时间查看预约订单", notes = "根据时间查看预约订单")
    @RequestMapping(value = "/manage/pos/order/selectAppointmentOrderListByDate", method = RequestMethod.POST)
    ResponseResult selectAppointmentOrderListByDate(@RequestParam("storeId") Long storeId);

    @ApiOperation(value = "根据水单号范围查询订单列表", notes = "根据水单号范围查询订单列表")
    @RequestMapping(value = "/manage/pos/order/selectOrderByMemoScope", method = RequestMethod.POST)
    ResponseResult selectOrderByMemoScope(@RequestParam("storeId") Long storeId,
                                          @RequestParam("memoNumStart") Integer memoNumStart,
                                          @RequestParam("memoNumEnd") Integer memoNumEnd);

    @ApiOperation(value = "查看水单号是否重复", notes = "查看水单号是否重复")
    @RequestMapping(value = "/manage/pos/order/checkOrderMemo", method = RequestMethod.POST)
    ResponseResult checkOrderMemo(@RequestParam("memoNum") String memoNum);

    @ApiOperation(value = "插入主订单", notes = "插入主订单")
    @RequestMapping(value = "/manage/pos/order/insertOrder", method = RequestMethod.POST)
    ResponseResult insertOrder(@RequestParam("orderType") Integer orderType,
                               @RequestParam("channel") Integer channel,
                               @RequestParam("orderLink") String orderLink,
                               @RequestParam("mobile") String mobile,
                               @RequestParam("cardNumber") String cardNumber,
                               @RequestParam("nurseStore") Long nurseStore,
                               @RequestParam("orderStatus") Integer orderStatus,
                               @RequestParam("orderNumber") String orderNumber,
                               @RequestParam("recordId") String recordId);

    @ApiOperation(value = "插入子订单", notes = "插入子订单")
    @RequestMapping(value = "/manage/pos/order/insertProductOrder", method = RequestMethod.POST)
    ResponseResult insertProductOrder(@RequestParam("orderId") String orderId,
                                      @RequestParam("orderNumber") String orderNumber,
                                      @RequestParam("productCode") String productCode,
                                      @RequestParam("productName") String productName,
                                      @RequestParam("productNum") Integer productNum,
                                      @RequestParam("bookingBeauticianIds") String bookingBeauticianIds,
                                      @RequestParam("productTypeId") Integer productTypeId,
                                      @RequestParam("storeId") Long storeId,
                                      @RequestParam("subclassID") Long subclassID,
                                      @RequestParam("recordId") String recordId);

    @ApiOperation(value = "插入预约辅订单", notes = "插入预约辅订单")
    @RequestMapping(value = "/manage/pos/order/insertAppointmentOrder", method = RequestMethod.POST)
    ResponseResult insertAppointmentOrder(@RequestParam("orderNumber") String orderNumber,
                                          @RequestParam("appointmentType") Integer appointmentType,
                                          @RequestParam("appointmentStatus") Integer appointmentStatus,
                                          @RequestParam("storeId") Long storeId,
                                          @RequestParam("source") Integer source,
                                          @RequestParam("recordId") String recordId);

}

