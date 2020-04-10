package com.lnmj.account.serviceProxy;

import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @Auther: panlin
 * @Date: 2019/4/19 15:35
 * @Description:
 */
@FeignClient(value = "lnmj-store")
public interface StoreApi {
    @ApiOperation(value = "查看体验卡订单里面已支付的订单的现金支付的总额",notes = "查看体验卡订单里面已支付的订单的现金支付的总额")
    @RequestMapping(value = "/manage/experienceCard/selectExpOrderCashAll",method = RequestMethod.POST)
    ResponseResult selectExpOrderCashAll(@RequestParam("memberNumber") String memberNumber);
}

