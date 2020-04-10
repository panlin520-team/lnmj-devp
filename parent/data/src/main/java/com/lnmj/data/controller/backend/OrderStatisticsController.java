package com.lnmj.data.controller.backend;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.business.IOrderService;
import com.lnmj.data.business.IScoreService;
import com.lnmj.data.entity.Score;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 〈订单统计〉
 *
 * @Author renqingyun
 * @create 2019/9/4
 */

@Api(description = "订单")
@RequestMapping("/manage/order")
@RestController
public class OrderStatisticsController {

    @Resource
    private IOrderService orderService;

    @ApiOperation(value = "查询当日订单量", notes = "查询当日订单量")
    @RequestMapping(value = "/selectTodayOrderList",method = RequestMethod.POST)
    public ResponseResult selectList(String storeId,String date) {
        return orderService.selectList(storeId,date);
    }



}
