package com.lnmj.paySettlement.controller.backend;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAccountEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeWalletEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponsePayEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.paySettlement.business.IPaySettlementService;
import com.lnmj.paySettlement.entity.PayType;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author panlin
 * @Date: 2019/5/28 11:49
 * @Description: 店铺管理
 */

@RestController
@RequestMapping("/manage/payment")
public class payManageController {
    @Resource(name = "paySettlementService")
    private IPaySettlementService iPaySettlementService;


    /**
     * @Description 查询支付类型
     * @Param [orderNumber] 订单编号
     * @Return com.lnmj.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/6/4
     * @Time 10:28
     */
    @ApiOperation(value = "查询支付类型列表", notes = "查询支付类型列表")
    @RequestMapping(value = "/selectPayTypeList", method = RequestMethod.POST)
    public ResponseResult selectPayTypeList(String memberNum,String subClassId,String industryId,String showAll) {
        return iPaySettlementService.selectPayTypeList(memberNum,subClassId,industryId,showAll);
    }

    @ApiOperation(value = "查询支付类型列表", notes = "查询支付类型列表")
    @RequestMapping(value = "/selectPayTypeListAll", method = RequestMethod.POST)
    public ResponseResult selectPayTypeListAll() {
        return iPaySettlementService.selectPayTypeListAll();
    }

    /**
     * @Description 查询小类查询支付类型列表
     * @Param []selectPayTypeListBySubClass
     * @Return com.lnmj.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/12/13
     * @Time
     */
    @ApiOperation(value = "查询小类查询支付类型列表", notes = "查询小类查询支付类型列表")
    @RequestMapping(value = "/selectPayTypeListBySubClass", method = RequestMethod.POST)
    public ResponseResult selectPayTypeListBySubClass(String subclass,String industry) {
        return iPaySettlementService.selectPayTypeListBySubClass(subclass,industry);
    }

    /**
     * @Description 根据id查看支付类型
     * @Param []
     * @Return com.lnmj.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/9/9
     * @Time 16:19
     */
    @ApiOperation(value = "根据id查看支付类型", notes = "根据id查看支付类型")
    @RequestMapping(value = "/selectPayTypeById", method = RequestMethod.POST)
    public ResponseResult selectPayTypeById(Long payTypeId) {
        return iPaySettlementService.selectPayTypeById(payTypeId);
    }

    @ApiOperation(value = "根据id查看支付类型", notes = "根据id查看支付类型")
    @RequestMapping(value = "/selectPayTypeByAccountType", method = RequestMethod.POST)
    public ResponseResult selectPayTypeByAccountType(Long accountType) {
        return iPaySettlementService.selectPayTypeByAccountType(accountType);
    }


    /**
     * @Description 删除支付类型
     * @Param [payTypeId]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/7/17
     * @Time 12:06
     */
    @ApiOperation(value = "删除支付类型", notes = "删除支付类型")
    @RequestMapping(value = "/deletePayType", method = RequestMethod.POST)
    public ResponseResult deletePayType(@RequestParam(value = "payTypeId") Long payTypeId) {
        return iPaySettlementService.deletePayType(payTypeId);
    }

    /**
     * @Description 添加支付类型
     * @Param [payType]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/7/17
     * @Time 12:11
     */
    @ApiOperation(value = "添加支付类型", notes = "添加支付类型")
    @RequestMapping(value = "/insertPayType", method = RequestMethod.POST)
    public ResponseResult insertPayType(PayType payType) {
        if (payType.getPayTypeCategory() == 2 && payType.getAccountType() == null) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.ACCOUNTTYPE_ISNULL.getCode(),
                    ResponseCodeWalletEnum.ACCOUNTTYPE_ISNULL.getDesc()));
        }
        return iPaySettlementService.insertPayType(payType);
    }

    /**
     * @Description 修改支付类型
     * @Param [payType]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5
     * @Time 18:38
     */
    @ApiOperation(value = "修改支付类型", notes = "修改支付类型")
    @RequestMapping(value = "/updatePayType", method = RequestMethod.POST)
    public ResponseResult updatePayType(PayType payType) {
        if (payType.getPayTypeCategory() == 2 && payType.getAccountType() == null) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.ACCOUNTTYPE_ISNULL.getCode(),
                    ResponseCodeWalletEnum.ACCOUNTTYPE_ISNULL.getDesc()));
        }


        if (payType.getPayTypeId() == null) {
            return ResponseResult.error(new Error(ResponsePayEnum.PAY_TYPE_ID_NULL.getCode(), ResponsePayEnum.PAY_TYPE_ID_NULL.getDesc()));
        }
        payType.setPaymentRatioOriginal(payType.getPaymentRatioOriginal() / 100);
        return iPaySettlementService.updatePayType(payType);
    }

    /**
     * @Description 当面付接口接入
     * @Param [payTypeName]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/8/2
     * @Time 10:23
     */
    @ApiOperation(value = "支付宝当面付接口接入", notes = "支付宝当面付接口接入")
    @RequestMapping(value = "/alipayFacePay", method = RequestMethod.POST)
    public ResponseResult alipayFacePay(Long orderNumber) {
        return iPaySettlementService.alipayFacePay(orderNumber);
    }

    /**
     * @Description 当面付接口接入
     * @Param [payTypeName]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/8/2
     * @Time 10:23
     */
    @ApiOperation(value = "微信当面付接口接入", notes = "微信当面付接口接入")
    @RequestMapping(value = "/wxFacePay", method = RequestMethod.POST)
    public ResponseResult wxFacePay(Long orderNumber) throws Exception {
        return iPaySettlementService.wxFacePay(orderNumber);
    }

}
