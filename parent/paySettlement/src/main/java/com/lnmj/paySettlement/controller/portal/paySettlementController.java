package com.lnmj.paySettlement.controller.portal;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.paySettlement.business.IPaySettlementService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author panlin
 * @Date: 2019/5/28 11:49
 * @Description: 店铺管理
 */

@RestController
@RequestMapping("/paySettlement")
public class paySettlementController {
    @Resource(name = "paySettlementService")
    private IPaySettlementService iPaySettlementService;


    /**
    *@Description 支付宝支付
    *@Param [orderNumber] 订单编号
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/6/4
    *@Time 10:28
    */
    @ApiOperation(value = "支付宝支付",notes = "支付宝支付")
    @RequestMapping(value = "/alipay",method = RequestMethod.POST)
    public ResponseResult alipay(Long orderNumber) {
        return iPaySettlementService.alipay(orderNumber);
    }

    /**
     *@Description 支付宝支付
     *@Param [orderNumber] 订单编号
     *@Return com.lnmj.common.response.ResponseResult
     *@Author panlin
     *@Date 2019/6/4
     *@Time 10:28
     */
    @ApiOperation(value = "钱包支付",notes = "钱包支付")
    @RequestMapping(value = "/walletPay",method = RequestMethod.POST)
    public ResponseResult walletPay(Long orderNumber,Long userId) {
        return iPaySettlementService.walletPay(orderNumber,userId);
    }




}
