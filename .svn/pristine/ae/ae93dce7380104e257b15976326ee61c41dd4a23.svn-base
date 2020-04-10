package com.lnmj.order.serviceProxy;


import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/24 17:57
 * @Description:
 */
@FeignClient(name = "lnmj-wallet")
public interface WalletApi {
    @RequestMapping(value = "/api/manage/wallet/selectRechargeByOrderNum", method = RequestMethod.POST)
    ResponseResult selectRechargeByOrderNum(@RequestParam("date") String date, @RequestParam("orderNo") String orderNo, @RequestParam("storeId") String storeId);

    @RequestMapping(value = "/api/manage/wallet/insertStoreRechargeAuditRecord", method = RequestMethod.POST)
    ResponseResult insertStoreRechargeAuditRecord(@RequestParam("isAbatementLadderDetailed") Boolean isAbatementLadderDetailed,
                                                  @RequestParam("name") String name,
                                                  @RequestParam("mobile") String mobile,
                                                  @RequestParam("cardNumber") String cardNumber,
                                                  @RequestParam("storeId") Long storeId,
                                                  @RequestParam("orderNo") String orderNo,
                                                  @RequestParam("beauticianId") Long beauticianId,
                                                  @RequestParam("amount") BigDecimal amount,
                                                  @RequestParam("accountTypeId") Long accountTypeId);

    @RequestMapping(value = "/api/manage/wallet/selectWalletByCarNum", method = RequestMethod.POST)
    ResponseResult selectWalletByCarNum(@RequestParam("carNum") String carNum);

    @RequestMapping(value = "/api/manage/wallet/selectAccountAmountByWalletId", method = RequestMethod.POST)
    ResponseResult selectAccountAmountByWalletId(@RequestParam("walletId") Long walletId);

    @RequestMapping(value = "/api/manage/wallet/updateWalletAccount", method = RequestMethod.POST)
    ResponseResult updateWalletAccount(@RequestParam("walletId") Long walletId,
                                       @RequestParam("accountTypeId") Long accountTypeId,
                                       @RequestParam("amount") Double amount);


    @RequestMapping(value = "/api/manage/wallet/updateWalletAccountDown", method = RequestMethod.POST)
    ResponseResult updateWalletAccountDown(@RequestParam("walletId") Long walletId,
                                           @RequestParam("accountTypeId") Long accountTypeId,
                                           @RequestParam("amount") Double amount);


    @RequestMapping(value = "/manage/wallet/insertRechargeRefuse", method = RequestMethod.POST)
    ResponseResult insertRechargeRefuse(@RequestParam("orderNo") String orderNo,
                                        @RequestParam("name") String name,
                                        @RequestParam("mobile") String mobile,
                                        @RequestParam("amount") Double amount);

    @RequestMapping(value = "/manage/wallet/selectWalletAccountListNoPage", method = RequestMethod.POST)
    ResponseResult selectWalletAccountListNoPage();

    @RequestMapping(value = "/manage/wallet/auditOrderAmount", method = RequestMethod.POST)
    ResponseResult auditOrderAmount(@RequestParam("orderList") String orderList, @RequestParam("auditStatus") Integer auditStatus);


    @RequestMapping(value = "/manage/wallet/addCapPool", method = RequestMethod.POST)
    ResponseResult addCapPool(@RequestParam("payType") Integer payType,
                              @RequestParam("type") Integer type,
                              @RequestParam("amount") Double amount,
                              @RequestParam("storeId") Long storeId,
                              @RequestParam("advancesReceivedAccount") Long advancesReceivedAccount,
                              @RequestParam("orderNumber") String orderNumber);

    @RequestMapping(value = "/manage/wallet/addTransfer", method = RequestMethod.POST)
     ResponseResult addTransfer(@RequestParam("capitalPoolId")Long capitalPoolId,
                                @RequestParam("payAmount")Double payAmount,
                                @RequestParam("payType")Integer payType,
                                @RequestParam("toStoreId")Long toStoreId);

    @ApiOperation(value = "查看充值退款列表不分页", notes = "查看充值退款列表不分页")
    @RequestMapping(value = "/manage/wallet/selectRechargeRefuseListNoPage", method = RequestMethod.POST)
     ResponseResult selectRechargeRefuseListNoPage(@RequestParam("orderNo") String orderNo) ;

    @ApiOperation(value = "根据充值订单号查看充值订单", notes = "根据充值订单号查看充值订单")
    @RequestMapping(value = "/manage/wallet/selectRechargeByOrderNumber", method = RequestMethod.POST)
     ResponseResult selectRechargeByOrderNumber(@RequestParam("orderNo") String orderNo) ;

    @ApiOperation(value = "扣除指定条件的预收", notes = "扣除指定条件的预收")
    @RequestMapping(value = "/manage/wallet/reduceForwardSaleByCondition", method = RequestMethod.POST)
     ResponseResult reduceForwardSaleByCondition(@RequestParam("type")Integer type,
                                                 @RequestParam("advancesReceivedAccount")Long advancesReceivedAccount,
                                                 @RequestParam("storeId")Long storeId,
                                                 @RequestParam("amount")Double amount);
    @ApiOperation(value = "修改门店充值审核记录", notes = "修改门店充值审核记录")
    @RequestMapping(value = "/manage/wallet/updateStoreRechargeAuditRecord", method = RequestMethod.POST)
     ResponseResult updateStoreRechargeAuditRecord(@RequestParam("orderNo") String orderNo,@RequestParam("auditAmountStatus") Integer auditAmountStatus);


    @ApiOperation(value = "删除指定订单号的资金收入", notes = "删除指定订单号的资金收入")
    @RequestMapping(value = "/manage/wallet/deleteCapPoolByOrderNumber", method = RequestMethod.POST)
     ResponseResult deleteCapPoolByOrderNumber(@RequestParam("orderNumber")String orderNumber) ;
}

