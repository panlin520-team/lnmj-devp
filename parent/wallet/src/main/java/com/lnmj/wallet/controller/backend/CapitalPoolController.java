package com.lnmj.wallet.controller.backend;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeWalletEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.business.*;
import com.lnmj.wallet.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
public class CapitalPoolController {
    @Resource
    private ICapitalPoolService iCapitalPoolService;

    /**
     * @Description 充值记录查询
     * @Param [pageNum, pageSize, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/4/28
     * @Time
     */
    @ApiOperation(value = "添加资金池", notes = "添加资金池")
    @RequestMapping(value = "/addCapPool", method = RequestMethod.POST)
    public ResponseResult addCapPool(Integer payType, Integer type, String amount, Long storeId, Long advancesReceivedAccount,String orderNumber) {
        return this.iCapitalPoolService.addCapPool(payType, type, amount,storeId,advancesReceivedAccount,orderNumber);
    }

    /**
     * @Description 查看资金池列表
     * @Param [pageNum, pageSize, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2020/2/7
     * @Time
     */
    @ApiOperation(value = "查看资金池列表", notes = "查看资金池列表")
    @RequestMapping(value = "/listCapPool", method = RequestMethod.POST)
    public ResponseResult listCapPool(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                      Long storeId) {
        return this.iCapitalPoolService.selectCapitalPool(pageNum, pageSize, storeId);
    }

    /**
     * @Description 插入划拨
     * @Param [pageNum, pageSize, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2020/2/7
     * @Time
     */
    @ApiOperation(value = "插入划拨", notes = "插入划拨")
    @RequestMapping(value = "/addTransfer", method = RequestMethod.POST)
    public ResponseResult addTransfer(Long capitalPoolId,Double payAmount,Long payType,Long toStoreId) {
        return this.iCapitalPoolService.addTransfer(capitalPoolId,payAmount,payType,toStoreId);
    }

    /**
     * @Description 查看指定账户实收划入情况
     * @Param [pageNum, pageSize, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2020/2/7
     * @Time
     */
    @ApiOperation(value = "查看指定账户实收划入情况", notes = "查看指定账户实收划入情况")
    @RequestMapping(value = "/selectTransferIn", method = RequestMethod.POST)
    public ResponseResult selectTransferIn(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,Long capitalPoolId,Long storeId) {
        return this.iCapitalPoolService.selectTransferIn(pageNum,pageSize,capitalPoolId,storeId);
    }

    /**
     * @Description 查看指定账户实收划出情况
     * @Param [pageNum, pageSize, accessToken]
     * @Return com.lnmj.wallet.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2020/2/7
     * @Time
     */
    @ApiOperation(value = "查看指定账户实收划出情况", notes = "查看指定账户实收划出情况")
    @RequestMapping(value = "/selectTransferOut", method = RequestMethod.POST)
    public ResponseResult selectTransferOut(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,Long capitalPoolId,Long storeId) {
        return this.iCapitalPoolService.selectTransferOut(pageNum,pageSize,capitalPoolId,storeId);
    }

    @ApiOperation(value = "扣除指定条件的预收", notes = "扣除指定条件的预收")
    @RequestMapping(value = "/reduceForwardSaleByCondition", method = RequestMethod.POST)
    public ResponseResult reduceForwardSaleByCondition(CapitalPool capitalPool) {
        return this.iCapitalPoolService.reduceForwardSaleByCondition(capitalPool);
    }

    @ApiOperation(value = "删除指定订单号的资金收入", notes = "删除指定订单号的资金收入")
    @RequestMapping(value = "/deleteCapPoolByOrderNumber", method = RequestMethod.POST)
    public ResponseResult deleteCapPoolByOrderNumber(CapitalPool capitalPool) {
        return this.iCapitalPoolService.deleteCapPoolByOrderNumber(capitalPool);
    }
}
