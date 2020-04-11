package com.lnmj.store.controller.backend;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.IOrderworkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Auther: panlin
 * @Date: 2019/5/28 11:32
 * @Description:
 */
@Api(description = "排班")
@RestController
@RequestMapping("/manage/orderwork")
public class OrderWorkController {
    @Resource(name = "orderworkService")
    private IOrderworkService orderworkService;
    /**
    *@Description 添加排班
    *@Param [beauticianId, orderWorkDate, busyTimeNodes, restTimeNodes]  美容师id 排班日期 忙碌时间节点 休息时间节点
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/5/30
    *@Time 11:46
    */
    @ApiOperation(value = "添加排班",notes = "添加排班")
    @RequestMapping(value = "/addOrderwork",method = RequestMethod.POST)
    public ResponseResult addOrderwork(Long beauticianId, @DateTimeFormat(pattern="yyyy-MM-dd") Date orderworkDate,String allTimeNodes,String busyTimeNodes,String restTimeNodes) {
        return orderworkService.addOrderwork(beauticianId,orderworkDate,allTimeNodes,busyTimeNodes,restTimeNodes);
    }

    /**
    *@Description 查看排班
    *@Param [beauticianId, orderworkDate] 美容师id 排班时间
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/5/30
    *@Time 12:37
    */
    @ApiOperation(value = "查看排班",notes = "查看排班")
    @RequestMapping(value = "/selectOrderwork",method = RequestMethod.POST)
    public ResponseResult selectOrderwork(Long beauticianId, @DateTimeFormat(pattern="yyyy-MM-dd")Date orderworkDate) {
        return orderworkService.selectOrderwork(beauticianId,orderworkDate);
    }

    /**
    *@Description 修改排班
    *@Param [beauticianId, orderworkDate, busyTimeNodes, restTimeNodes] 美容师id 排班日期 忙碌时间节点 休息时间节点
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/5/30
    *@Time 14:59
    */
    @ApiOperation(value = "修改排班",notes = "修改排班")
    @RequestMapping(value = "/updateOrderwork",method = RequestMethod.POST)
    public ResponseResult updateOrderwork(Long beauticianId, @DateTimeFormat(pattern="yyyy-MM-dd") Date orderworkDate,String leisureTimeNodes,String busyTimeNodes,String restTimeNodes) {
        return orderworkService.updateOrderwork(beauticianId,orderworkDate,leisureTimeNodes,busyTimeNodes,restTimeNodes);
    }

    /**
    *@Description 删除排班
    *@Param [beauticianId, orderworkDate] 美容师id 排班时间
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/5/30
    *@Time 15:13
    */
    @ApiOperation(value = "删除排班",notes = "修改排班")
    @RequestMapping(value = "/deleteOrderwork",method = RequestMethod.POST)
    public ResponseResult deleteOrderwork(Long beauticianId, @DateTimeFormat(pattern="yyyy-MM-dd") Date orderworkDate) {
        return orderworkService.deleteOrderwork(beauticianId,orderworkDate);
    }

    /**
    *@Description 查看所有美容师的排班情况
    *@Param [storeId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/7/18
    *@Time 18:36
    */
    @ApiOperation(value = "查看所有美容师的排班情况",notes = "查看所有美容师的排班情况")
    @RequestMapping(value = "/selectOrderworkAll",method = RequestMethod.POST)
    public ResponseResult selectOrderworkAll(Long storeId) {
        return orderworkService.selectOrderworkAll(storeId);
    }
}
