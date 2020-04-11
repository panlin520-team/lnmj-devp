package com.lnmj.wallet.serviceProxy;

import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: yilihua
 * @Date: 2019/9/6 17:21
 * @Description:
 */
@FeignClient(value = "lnmj-order")
public interface OrderApi {
    @ApiOperation(value = "储值订单退款并扣除业绩", notes = "储值订单退款并扣除业绩")
    @RequestMapping(value = "/order/rechargeOrderRefund", method = RequestMethod.POST)
     ResponseResult rechargeOrderRefund(@RequestParam("orderNumber") String orderNumber,
                                        @RequestParam("refundAmount") Double refundAmount,
                                        @RequestParam("name") String name,
                                        @RequestParam("mobile") String mobile,
                                        @RequestParam("memberNum") String memberNum,
                                        @RequestParam("accountTypeId") Long accountTypeId,
                                        @RequestParam("isAudit") String isAudit);


}
