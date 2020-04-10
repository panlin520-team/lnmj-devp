package com.lnmj.order.serviceProxy.Controller;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.order.business.IOrderService;
import com.lnmj.order.entity.AppointmentOrder;
import com.lnmj.order.entity.Order;
import com.lnmj.order.entity.ProductOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Auther: panlin
 * @Date: 2019/4/23 13:29
 * @Description: 后台订单接口api
 */
@Api(description = "后台订单接口api")
@RestController
@RequestMapping("/manage/pos/order")
@CrossOrigin
public class orderManageService {
    @Resource
    private IOrderService iOrderService;

    /**
     *@Description 查询当天的订单
     *@Param [cardNumber, startDate, endDate]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author Mr.Ren
     *@Date 2019/10/10
     *@Time
     */
    @ApiOperation(value = "查询当天的订单",notes = "查询当天的订单")
    @RequestMapping(value = "/selectOrderListByToday",method = RequestMethod.POST)
    public ResponseResult selectOrderListByToday(String storeId,String date) {
        return iOrderService.selectOrderListByToday(storeId,date);
    }

    @ApiOperation(value = "根据时间查看预约订单",notes = "根据时间查看预约订单")
    @RequestMapping(value = "/selectAppointmentOrderListByDate",method = RequestMethod.POST)
    public ResponseResult selectAppointmentOrderListByDate(Long storeId) {
        return iOrderService.selectAppointmentOrderListByDate(storeId);
    }

    @ApiOperation(value = "根据水单号范围查询订单列表",notes = "根据水单号范围查询订单列表")
    @RequestMapping(value = "/selectOrderByMemoScope",method = RequestMethod.POST)
    public ResponseResult selectOrderByMemoScope(Long storeId,Integer memoNumStart,Integer memoNumEnd) {
        return iOrderService.selectOrderByMemoScope(storeId,memoNumStart,memoNumEnd);
    }

    @ApiOperation(value = "查看水单号是否重复",notes = "查看水单号是否重复")
    @RequestMapping(value = "/checkOrderMemo",method = RequestMethod.POST)
    public ResponseResult checkOrderMemo(String memoNum) {
        return iOrderService.checkOrderMemo(memoNum);
    }



    @ApiOperation(value = "插入主订单",notes = "插入主订单")
    @RequestMapping(value = "/insertOrder",method = RequestMethod.POST)
    public ResponseResult insertOrder(Order order) {
        return iOrderService.insertOrder(order);
    }

    @ApiOperation(value = "插入子订单",notes = "插入子订单")
    @RequestMapping(value = "/insertProductOrder",method = RequestMethod.POST)
    public ResponseResult insertProductOrder(ProductOrder productOrder) {
        return iOrderService.insertProductOrder(productOrder);
    }

    @ApiOperation(value = "插入预约辅订单",notes = "插入预约辅订单")
    @RequestMapping(value = "/insertAppointmentOrder",method = RequestMethod.POST)
    public ResponseResult insertAppointmentOrder(AppointmentOrder appointmentOrder) {
        return iOrderService.insertAppointmentOrder(appointmentOrder);
    }
}
