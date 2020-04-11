package com.lnmj.wallet.controller.backend;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeWalletEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.business.IWalletCashService;
import com.lnmj.wallet.business.IWalletConsumeService;
import com.lnmj.wallet.business.IWalletRechargeService;
import com.lnmj.wallet.business.IWalletService;
import com.lnmj.wallet.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Api(description = "商城钱包接口")
@RestController
@RequestMapping("/manage/wallet")
public class WalletManagerController {
    @Resource
    private IWalletService walletService;
    @Resource
    private IWalletConsumeService walletConsumeService;
    @Resource
    private IWalletRechargeService walletRechargeService;
    @Resource
    private IWalletCashService walletCashService;


    /**
     * @Description 充值记录查询
     * @Param [pageNum, pageSize, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/4/28
     * @Time
     */
    @ApiOperation(value = "充值记录查询", notes = "充值记录查询")
    @RequestMapping(value = "/selectRechargeList", method = RequestMethod.POST)
    public ResponseResult selectRechargeList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,String cardNumber,String keyWord,String storeId,
                                             Long accountTypeId,Integer auditStatus) {
        return this.walletRechargeService.selectRechargeList(pageNum, pageSize, keyWord,storeId,accountTypeId,auditStatus);
    }


    /**
    *@Description 根据会员卡号计算储值总金额和最大充值金额
    *@Param [cardNumber, storeId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/10/10
    *@Time
    */
    @ApiOperation(value = "根据会员卡号计算储值总金额和最大充值金额", notes = "根据会员卡号计算储值总金额和最大充值金额")
    @RequestMapping(value = "/selectSumAmountAll", method = RequestMethod.POST)
    public ResponseResult selectSumAmountAll(String cardNumber) {
        return this.walletRechargeService.selectSumAmountAll(cardNumber);
    }

    /**
    *@Description 根据会员卡号或者门店id计算储值总金额(当天)
    *@Param [cardNumber, storeId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/10/10
    *@Time
    */
    @ApiOperation(value = "根据会员卡号或者门店id(当天)计算储值总金额", notes = "根据会员卡号或者门店id计算储值总金额(当天)")
    @RequestMapping(value = "/selectSumAmount", method = RequestMethod.POST)
    public ResponseResult selectSumAmount(String date,String cardNumber,String storeId) {
        return this.walletRechargeService.selectSumAmount(date,cardNumber,storeId);
    }



    /**
    *@Description 计算消费总金额
    *@Param [userId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/9/20
    *@Time
    */
    @ApiOperation(value = "计算消费总金额", notes = "计算消费总金额")
    @RequestMapping(value = "/selectConsumerAmount", method = RequestMethod.POST)
    public ResponseResult selectConsumerAmount(String cardNumber) {
        return this.walletRechargeService.selectConsumerAmount(cardNumber);
    }



    /**
    *@Description 删除充值记录
    *@Param [consumeRecordId, ids, accessToken]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/5/30
    *@Time
    */
    @ApiOperation(value = "删除充值记录", notes = "删除充值记录")
    @RequestMapping(value = "/deleteRechargeRecord", method = RequestMethod.POST)
    public ResponseResult deleteRechargeRecord(Long rechargeRecordId,String[] ids) {
        return walletRechargeService.deleteRechargeRecord(rechargeRecordId,ids);
    }


    /**
     * @Description 消费记录查询
     * @Param [accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/4/28
     * @Time
     */
    @ApiOperation(value = "消费记录查询", notes = "消费记录查询")
    @RequestMapping(value = "/selectExpenseCalendarList", method = RequestMethod.POST)
    public ResponseResult selectExpenseCalendarList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,String cardNumber) {
        return this.walletConsumeService.selectExpenseCalendarList(pageNum, pageSize,cardNumber);
    }

    /**
     * @Description 删除消费记录
     * @Param [userId, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/4/28
     * @Time
     */
    @ApiOperation(value = "删除消费记录", notes = "删除消费记录")
    @RequestMapping(value = "/deleteExpenseRecordById", method = RequestMethod.POST)
    public ResponseResult deleteExpenseRecordById(Long consumeRecordId,String[] ids) {
        return walletConsumeService.deleteExpenseRecord(consumeRecordId,ids);
    }



    /**
     *@Description 提现记录查询
     *@Param [pageNum, pageSize, cardNumber, accessToken]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author Mr.Ren
     *@Date 2019/5/30
     *@Time
     */
    @ApiOperation(value = "提现记录查询", notes = "提现记录查询")
    @RequestMapping(value = "/selectCashRecordList", method = RequestMethod.POST)
    public ResponseResult selectCashRecordList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, Integer status,String cashTime,String accessToken) {
        return this.walletCashService.selectCashRecordList(pageNum, pageSize,status,cashTime);
    }



    /**
    *@Description 删除提现记录
    *@Param [consumeRecordId, ids, accessToken]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/6/3
    *@Time
    */
    @ApiOperation(value = "删除提现记录", notes = "删除提现记录")
    @RequestMapping(value = "/deleteCashRecord", method = RequestMethod.POST)
    public ResponseResult deleteCashRecord(Long consumeRecordId,String[] ids) {
        return walletConsumeService.deleteCashRecord(consumeRecordId,ids);
    }



    /**
    *@Description 提现详情查询
    *@Param [pageNum, pageSize, name, accessToken]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/6/3
    *@Time
    */
    @ApiOperation(value = "提现详情查询", notes = "提现详情查询")
    @RequestMapping(value = "/selectCashRecordDetailList", method = RequestMethod.POST)
    public ResponseResult selectCashRecordDetailList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,String bonusTypeId,String cashRecordId) {
        return this.walletCashService.selectCashRecordDetailList(pageNum, pageSize,bonusTypeId,cashRecordId);
    }


    /**
    *@Description 审核提现
    *@Param [pageNum, pageSize, bonusTypeId, cashRecordId, accessToken]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/8/19
    *@Time
    */
    @ApiOperation(value = "审核提现", notes = "审核提现")
    @RequestMapping(value = "/examine", method = RequestMethod.POST)
    public ResponseResult examine(String cashRecordId) {
        return this.walletCashService.examine(cashRecordId);
    }


    /**
    *@Description 发起提现
    *@Param [pageNum, pageSize, times, accessToken]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/6/3
    *@Time
    */
    @ApiOperation(value = "发起提现", notes = "发起提现")
    @RequestMapping(value = "/addCash", method = RequestMethod.POST)
    public ResponseResult addCash(WalletCashRecord walletCashRecord, List<WalletCashRecordDetail> detailList) {
        return this.walletCashService.addCash(walletCashRecord,detailList);
    }



    /**
     * @Description 后台充值
     * @Param [walletRechargeRecord, cardNumber, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/4/28
     * @Time
     */
    @ApiOperation(value = "后台充值", notes = "后台充值")
    @RequestMapping(value = "/backgroundOnline", method = RequestMethod.POST)
    public ResponseResult BackgroundOnline(WalletAmount walletAmount) {

        return this.walletService.recharge(walletAmount);
    }



    /**
    *@Description 添加门店充值审核记录
    *@Param [walletRechargeRecord, accessToken]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/7/17
    *@Time
    */
    @ApiOperation(value = "添加门店充值审核记录", notes = "添加门店充值审核记录")
    @RequestMapping(value = "/insertStoreRechargeAuditRecord", method = RequestMethod.POST)
    public ResponseResult insertStoreRechargeAuditRecord(WalletRechargeRecord walletRechargeRecord) {
        if (walletRechargeRecord.getAccountTypeId()==null) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.ACCOUNTTYPE_ISNULL.getCode(), ResponseCodeWalletEnum.ACCOUNTTYPE_ISNULL.getDesc()));
        }
        if (StringUtils.isBlank(walletRechargeRecord.getCardNumber())) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.VIPCARTNUMBER_NULL.getCode(), ResponseCodeWalletEnum.VIPCARTNUMBER_NULL.getDesc()));
        }
        if (BigDecimal.ZERO.equals(walletRechargeRecord.getAmount())) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.RECHARGEAMOUNT_IS_NULL.getCode(), ResponseCodeWalletEnum.RECHARGEAMOUNT_IS_NULL.getDesc()));
        }

        if (StringUtils.isBlank(walletRechargeRecord.getPayTypeAndAmount()) ) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.PAYTYPE_IS_ERROR.getCode(), ResponseCodeWalletEnum.PAYTYPE_IS_ERROR.getDesc()));
        }
        if (StringUtils.isBlank(walletRechargeRecord.getBeauticianId())) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.BEAUTICIAN_IS_NULL.getCode(), ResponseCodeWalletEnum.BEAUTICIAN_IS_NULL.getDesc()));
        }

        if (StringUtils.isBlank(walletRechargeRecord.getName())) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.NAMEIS_NULL.getCode(), ResponseCodeWalletEnum.NAMEIS_NULL.getDesc()));
        }
        if (StringUtils.isBlank(walletRechargeRecord.getMobile())) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.MOBILE_NULL.getCode(), ResponseCodeWalletEnum.MOBILE_NULL.getDesc()));
        }
        if (walletRechargeRecord.getRechargeChannel()==null) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.RECHARGECHANNEL_NULL.getCode(), ResponseCodeWalletEnum.RECHARGECHANNEL_NULL.getDesc()));
        }
        if (walletRechargeRecord.getPayStatus()==null) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.PAY_STATUS_NULL.getCode(), ResponseCodeWalletEnum.PAY_STATUS_NULL.getDesc()));
        }
        return this.walletService.insertStoreRechargeAuditRecord(walletRechargeRecord);
    }

    /**
     *@Description 添加门店充值审核记录
     *@Param [walletRechargeRecord, accessToken]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author Mr.Ren
     *@Date 2019/7/17
     *@Time
     */
    @ApiOperation(value = "修改门店充值审核记录", notes = "修改门店充值审核记录")
    @RequestMapping(value = "/updateStoreRechargeAuditRecord", method = RequestMethod.POST)
    public ResponseResult updateStoreRechargeAuditRecord(WalletRechargeRecord walletRechargeRecord) {

        return this.walletService.updateStoreRechargeAuditRecord(walletRechargeRecord);
    }

    /**
    *@Description 审核充值记录
    *@Param [walletAmount, accessToken]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/7/17
    *@Time
    */
    @ApiOperation(value = "审核充值记录", notes = "审核充值记录")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult audit(WalletRechargeRecord walletRechargeRecord) {
        return this.walletRechargeService.audit(walletRechargeRecord);
    }


    @ApiOperation(value = "批量审核充值记录", notes = "审核充值记录")
    @RequestMapping(value = "/batchAudit", method = RequestMethod.POST)
    public ResponseResult batchAudit(String walletRechargeRecordList,Integer auditStatus) {
        return this.walletRechargeService.batchAudit(walletRechargeRecordList,auditStatus);
    }


    /**
    *@Description 执行充值
    *@Param [walletAmount, accessToken]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/7/17
    *@Time
    */
    @ApiOperation(value = "执行充值", notes = "执行充值")
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    public ResponseResult recharge(WalletAmount walletAmount) {
        return this.walletService.recharge(walletAmount);
    }



    @ApiOperation(value = "用户钱包数据统计", notes = "用户钱包数据统计")
    @RequestMapping(value = "/selectListByUserStatistics", method = RequestMethod.POST)
    public ResponseResult selectListByUserStatistics() {
        return this.walletConsumeService.selectListByUserStatistics();
    }


    /**
    *@Description
    *@Param [accessToken]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/8/23
    *@Time
    */
    @ApiOperation(value = "返利审核分页查询", notes = "返利审核分页查询")
    @RequestMapping(value = "/selectCashBackList", method = RequestMethod.POST)
    public ResponseResult selectCashBackList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,String keyWord) {
        return this.walletService.selectCashBackList(pageNum,pageSize,keyWord);
    }


    /**
    *@Description 返利审核
    *@Param [cashBackId, cashbackAmount, modifyOperator, accessToken]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/8/27
    *@Time
    */
    @ApiOperation(value = "返利审核", notes = "返利审核")
    @RequestMapping(value = "/rebateExamine", method = RequestMethod.POST)
    public ResponseResult rebateExamine(Long cashBackId,BigDecimal cashbackAmount,String modifyOperator) {
        return this.walletService.rebateExamine(cashBackId,modifyOperator);
    }


    /**
     *@Description 退货
     *@Param [cashBackId, orderNumber, cashbackAmount, modifyOperator, accessToken]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author Mr.Ren
     *@Date 2019/8/26
     *@Time
     */
    @ApiOperation(value = "退货", notes = "退货")
    @RequestMapping(value = "/returngoods", method = RequestMethod.POST)
    public ResponseResult returnGoods(Long cashBackId,String orderNumber,String mobile,String cashbackAmount,String modifyOperator,String accessToken) {
        return this.walletService.returnGoods(cashBackId,orderNumber,cashbackAmount,mobile,modifyOperator);
    }



    /**
    *@Description 统计当天充值订单数
    *@Param [accessToken]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/9/26
    *@Time
    */
    @ApiOperation(value = "统计当天充值订单数", notes = "统计当天充值订单数")
    @RequestMapping(value = "/selectRechargeOrderList", method = RequestMethod.POST)
    public ResponseResult selectRechargeOrderList(String storeId,String date,String accessToken) {
        return this.walletRechargeService.selectRechargeOrderList(storeId,date);
    }

    @ApiOperation(value = "查询会员最近10笔充值记录", notes = "查询会员最近10笔充值记录")
    @RequestMapping(value = "/selectRechargeTopTen", method = RequestMethod.POST)
    public ResponseResult selectRechargeTopTen(Long storeId,String memberNum) {
        return this.walletRechargeService.selectRechargeTopTen(storeId,memberNum);
    }

    @ApiOperation(value = "添加充值退款附表", notes = "添加充值退款附表")
    @RequestMapping(value = "/insertRechargeRefuse", method = RequestMethod.POST)
    public ResponseResult insertRechargeRefuse(WalletrechargerecordRefuse walletrechargerecordRefuse) {
        return this.walletRechargeService.insertRechargeRefuse(walletrechargerecordRefuse);
    }

    @ApiOperation(value = "查看充值退款列表", notes = "查看充值退款列表")
    @RequestMapping(value = "/selectRechargeRefuseList", method = RequestMethod.POST)
    public ResponseResult selectRechargeRefuseList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,WalletrechargerecordRefuse walletrechargerecordRefuse) {
        return this.walletRechargeService.selectRechargeRefuseList(pageNum,pageSize,walletrechargerecordRefuse);
    }

    @ApiOperation(value = "查看充值退款列表不分页", notes = "查看充值退款列表不分页")
    @RequestMapping(value = "/selectRechargeRefuseListNoPage", method = RequestMethod.POST)
    public ResponseResult selectRechargeRefuseListNoPage(WalletrechargerecordRefuse walletrechargerecordRefuse) {
        return this.walletRechargeService.selectRechargeRefuseListNoPage(walletrechargerecordRefuse);
    }

    @ApiOperation(value = "根据充值订单号查看充值订单", notes = "根据充值订单号查看充值订单")
    @RequestMapping(value = "/selectRechargeByOrderNumber", method = RequestMethod.POST)
    public ResponseResult selectRechargeByOrderNumber(String orderNo) {
        return this.walletRechargeService.selectRechargeByOrderNumber(orderNo);
    }

    @ApiOperation(value = "审核订单金额", notes = "审核订单金额")
    @RequestMapping(value = "/auditOrderAmount", method = RequestMethod.POST)
    public ResponseResult auditOrderAmount(String orderList,Integer auditStatus) {
        return walletRechargeService.auditOrderAmount(orderList,auditStatus);
    }
}
