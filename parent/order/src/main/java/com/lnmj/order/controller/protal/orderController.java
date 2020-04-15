package com.lnmj.order.controller.protal;

import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAppointmentEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeOrderEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.order.business.IOrderService;
import com.lnmj.order.entity.CustomProjectUser;
import com.lnmj.order.entity.CustomProjectUserRecord;
import com.lnmj.order.entity.Order;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

/**
 * @Auther: panlin
 * @Date: 2019/5/31 12:04
 * @Description:
 */
@RestController
@RequestMapping("/order")
public class orderController {
    @Resource
    private IOrderService iOrderService;

    @ApiOperation(value = "按美容师和日期查询预约", notes = "按美容师和日期查询预约")
    @RequestMapping(value = "/selectAppointmentByBeauticianAndDate", method = RequestMethod.POST)
    public ResponseResult selectAppointmentByBeauticianAndDate(String beauticianId, String nurseTime, String access_token) {
        return iOrderService.selectAppointmentByBeauticianAndDate(beauticianId, nurseTime);
    }

    @ApiOperation(value = "查询会员现金消费总金额", notes = "查询会员现金消费总金额")
    @RequestMapping(value = "/selectMemberCashAll", method = RequestMethod.POST)
    public ResponseResult selectMemberCashAll(String memberNumber, String access_token) {
        return iOrderService.selectMemberCashAll(memberNumber);
    }

    @ApiOperation(value = "添加订单", notes = "添加订单")
    @RequestMapping(value = "/createServiceOrder", method = RequestMethod.POST)
    public ResponseResult createServiceOrder(Integer orderType,
                                             Integer channel,
                                             Long storeId,
                                             String productIds,
                                             String cardNum,
                                             String orderLink,
                                             String mobile,
                                             Long industryID,
                                             String postCategoryIds,
                                             Long appointmentId,
                                             String memoNum,
                                             Double totalPrice,
                                             String remark, String nurseDate) {
        return iOrderService.createServiceOrder(orderType, channel, storeId, productIds, cardNum, orderLink, mobile, industryID, postCategoryIds, appointmentId, memoNum, totalPrice, remark, nurseDate);
    }

    @ApiOperation(value = "查看订单列表", notes = "查看订单列表")
    @RequestMapping(value = "/selectOrderList", method = RequestMethod.POST)
    public ResponseResult selectOrderList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String storeId,
                                          String keyWordOrderNum, String keyWordMobile, Integer orderType, Integer payStatus, Integer orderStatus, String date,Integer orderChannel) {
        return iOrderService.selectOrderList(pageNum, pageSize, storeId, keyWordOrderNum, keyWordMobile, orderType, payStatus, orderStatus, date,orderChannel);
    }

    @ApiOperation(value = "查看订单列表不分页", notes = "查看订单列表")
    @RequestMapping(value = "/selectOrderListNoPage", method = RequestMethod.POST)
    public ResponseResult selectOrderListNoPage() {
        return iOrderService.selectOrderListNoPage();
    }

    @ApiOperation(value = "根据创建时间及会员号查询订单", notes = "查看订单列表")
    @RequestMapping(value = "/selectOrderListByCarNumAndDates", method = RequestMethod.POST)
    public ResponseResult selectOrderListByCarNumAndDates(String cardNumber, String startDate, String endDate) {
        return iOrderService.selectOrderListByCarNumAndDates(cardNumber, startDate, endDate);
    }

    @ApiOperation(value = "查看订单详情", notes = "查看订单详情")
    @RequestMapping(value = "/selectOrderByNum", method = RequestMethod.POST)
    public ResponseResult selectOrderByNum(String orderNumbers) {
        return iOrderService.selectOrderByNum(orderNumbers);
    }

    @ApiOperation(value = "取消订单", notes = "取消订单")
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    public ResponseResult cancelOrder(String orderNumbers, Integer orderStatus) {
        return iOrderService.cancelOrder(orderNumbers, orderStatus);
    }

    @ApiOperation(value = "删除订单", notes = "删除订单")
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    public ResponseResult deleteOrder(String orderNumbers, String deleteReason) {
        return iOrderService.deleteOrder(orderNumbers, deleteReason);
    }

    @ApiOperation(value = "修改订单", notes = "修改订单")
    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    public ResponseResult updateOrder(String productIds, Order order) {
        return iOrderService.updateOrder(productIds, order);
    }

    @ApiOperation(value = "导出订单", notes = "修改订单")
    @RequestMapping(value = "/exportOrder", method = RequestMethod.POST)
    public ResponseResult exportOrder(HttpServletRequest req, HttpServletResponse resp, Long storeId) {
        return iOrderService.exportOrder(req, resp, storeId);
    }

    @ApiOperation(value = "修改订单状态", notes = "修改订单状态")
    @RequestMapping(value = "/updateOrderStatus", method = RequestMethod.POST)
    public ResponseResult updateOrderStatus(Long orderNumber, Integer status, Integer orderStatusOld) {
        return iOrderService.updateOrderStatus(orderNumber, status, orderStatusOld);
    }

    @ApiOperation(value = "修改订单水单号", notes = "修改订单水单号")
    @RequestMapping(value = "/updateOrderMemoNum", method = RequestMethod.POST)
    public ResponseResult updateOrderMemo(Long orderNumber, String memoNum) {
        return iOrderService.updateOrderMemo(orderNumber, memoNum);
    }

    @ApiOperation(value = "查看指定用户订单", notes = "查看指定用户订单")
    @RequestMapping(value = "/selectOrderListByCarNumber", method = RequestMethod.POST)
    public ResponseResult selectOrderListByCarNumber(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, Long carNumber, Integer orderType) {
        return iOrderService.selectOrderListByCarNumber(pageNum, pageSize, carNumber, orderType);
    }

    @ApiOperation(value = "根据会员卡查用户订单创建时间", notes = "根据会员卡查用户订单创建时间")
    @RequestMapping(value = "/selectCreateTimeByCarNumber", method = RequestMethod.POST)
    public ResponseResult selectCreateTimeByCarNumber(String carNumber) {
        return iOrderService.selectCreateTimeByCarNumber(carNumber);
    }

    @ApiOperation(value = "根据订单编号查看子订单", notes = "根据订单编号查看子订单")
    @RequestMapping(value = "/selectOrderProductByOrderNum", method = RequestMethod.POST)
    public ResponseResult selectOrderProductByOrderNum(Long orderNumber) {
        return iOrderService.selectOrderProductByOrderNum(orderNumber);
    }

    @ApiOperation(value = "查询当日总收入和大类的收入明细", notes = "查询当日总收入和大类的收入明细")
    @RequestMapping(value = "/selectTodayReceipt", method = RequestMethod.POST)
    public ResponseResult selectTodayReceipt(String date, String storeId, String access_token) {
        return iOrderService.selectTodayReceipt(date, storeId);
    }

    @ApiOperation(value = "查询当日现金收入和大类的收入明细", notes = "查询当日总收入和大类的收入明细")
    @RequestMapping(value = "/selectTodayCashReceipt", method = RequestMethod.POST)
    public ResponseResult selectTodayCashReceipt(String date, String storeId, String access_token) {
        return iOrderService.selectTodayCashReceipt(date, storeId);
    }

    @ApiOperation(value = "查看订单类型", notes = "查看订单类型")
    @RequestMapping(value = "/selectOrderTypeList", method = RequestMethod.POST)
    public ResponseResult selectOrderTypeList() {
        return iOrderService.selectOrderTypeList();
    }

    @ApiOperation(value = "查看订单类型", notes = "查看订单类型")
    @RequestMapping(value = "/selectOrderTypeListForYeji", method = RequestMethod.POST)
    public ResponseResult selectOrderTypeListForYeji() {
        return iOrderService.selectOrderTypeListForYeji();
    }

    @ApiOperation(value = "查看订单状态", notes = "查看订单状态")
    @RequestMapping(value = "/selectOrderStatusList", method = RequestMethod.POST)
    public ResponseResult selectOrderStatusList() {
        return iOrderService.selectOrderStatusList();
    }

    @ApiOperation(value = "查看订单来源渠道", notes = "查看订单来源渠道")
    @RequestMapping(value = "/selectOrderChannelList", method = RequestMethod.POST)
    public ResponseResult selectOrderChannelList() {
        return iOrderService.selectOrderChannelList();
    }

    @ApiOperation(value = "储值订单退款并扣除业绩", notes = "储值订单退款并扣除业绩")
    @RequestMapping(value = "/rechargeOrderRefund", method = RequestMethod.POST)
    public ResponseResult rechargeOrderRefund(String orderNumber, Double refundAmount,String name,String mobile,String memberNum,Long accountTypeId,String isAudit) {
        return iOrderService.rechargeOrderRefund(orderNumber, refundAmount,name,mobile,memberNum,accountTypeId,isAudit);
    }

    @ApiOperation(value = "支付订单退款并扣除业绩", notes = "储值订单退款并扣除业绩")
    @RequestMapping(value = "/payOrderRefund", method = RequestMethod.POST)
    public ResponseResult payOrderRefund(String outStorageIdQiTa,String outStorageIdXiaoShou,Integer isTiYanKaOrDingzhi, Integer isTiYanKa, Long expUseRecordId,Integer isHuaKa,String payTypeAndAmount, String memberNum, Long experiencecardProductUserId, String orderNumber, String expCardNum, String modifyOperator,String storeId) {
        return iOrderService.payOrderRefund(outStorageIdQiTa,outStorageIdXiaoShou,isTiYanKaOrDingzhi,isTiYanKa,expUseRecordId,isHuaKa,payTypeAndAmount,memberNum,experiencecardProductUserId,orderNumber,expCardNum,modifyOperator, storeId);
    }

    @ApiOperation(value = "定制订单退货(次数)", notes = "定制订单退货(次数)")
    @RequestMapping(value = "/customProjectUserRefuse", method = RequestMethod.POST)
    public ResponseResult customProjectUserRefuse(Long id,Integer refuseTimes) {
        return iOrderService.customProjectUserRefuse(id,refuseTimes);
    }

    @ApiOperation(value = "定制订单退货次数详情", notes = "定制订单退货次数详情")
    @RequestMapping(value = "/selectCustomProjectUserRefuseList", method = RequestMethod.POST)
    public ResponseResult selectCustomProjectUserRefuseList(Long id) {
        return iOrderService.selectCustomProjectUserRefuseList(id);
    }

    @ApiOperation(value = "查看现金收入明细", notes = "储值订单退款并扣除业绩")
    @RequestMapping(value = "/selectOrderByCash", method = RequestMethod.POST)
    public ResponseResult selectOrderByCash(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "0") int pageSize, String storeId, String date, Integer cash) {
        return iOrderService.selectOrderByCash(pageNum, pageSize, storeId, date, cash);
    }

    @ApiOperation(value = "根据会员编号及商品类型查看订单", notes = "根据会员编号及商品类型查看订单")
    @RequestMapping(value = "/selectOrderByMemberNumAndProductType", method = RequestMethod.POST)
    public ResponseResult selectOrderByMemberNumAndProductType(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String storeId, String memberNum, Integer orderType, String date, String keywordOrderNum) {
        return iOrderService.selectOrderByMemberNumAndProductType(pageNum, pageSize, storeId, memberNum, orderType, date, keywordOrderNum);
    }

    @ApiOperation(value = "根据会员编号查看会员购买产品记录", notes = "根据会员编号查看会员购买产品记录")
    @RequestMapping(value = "/selectOrderProductByMemberNum", method = RequestMethod.POST)
    public ResponseResult selectOrderProductByMemberNum(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                        Long storeId,
                                                        String memberNum,
                                                        String date) {
        return iOrderService.selectOrderProductByMemberNum(pageNum, pageSize, storeId, memberNum, date);
    }


    @ApiOperation(value = "查询用户最近10笔消费", notes = "查询用户最近10笔消费")
    @RequestMapping(value = "/selectConsumeTopTen", method = RequestMethod.POST)
    public ResponseResult selectConsumeTopTen(String memberNum, Long storeId) {
        return iOrderService.selectConsumeTopTen(memberNum, storeId);
    }

    @ApiOperation(value = "查询用户消费次数top10", notes = "查询用户消费次数top10")
    @RequestMapping(value = "/selectConsumeTimesTopTen", method = RequestMethod.POST)
    public ResponseResult selectConsumeTimesTopTen(String memberNum, Long storeId) {
        return iOrderService.selectConsumeTimesTopTen(memberNum, storeId);
    }

    @ApiOperation(value = "查询用户消费金额top10", notes = "查询用户消费金额top10")
    @RequestMapping(value = "/selectConsumeMoneyTopTen", method = RequestMethod.POST)
    public ResponseResult selectConsumeMoneyTopTen(String memberNum, Long storeId) {
        return iOrderService.selectConsumeMoneyTopTen(memberNum, storeId);
    }

    @ApiOperation(value = "支付订单", notes = "支付订单")
    @RequestMapping(value = "/payOrder", method = RequestMethod.POST)
    public ResponseResult payOrder(String staffNumber,String orderNumber, String payTypeAndAmount, String productIds, Double payPrice, Double number, String createOperator, String useLimit, Integer totalTimes) {
        return iOrderService.payOrder(staffNumber,orderNumber, payTypeAndAmount, productIds, payPrice, number, createOperator, useLimit, totalTimes);
    }

    @ApiOperation(value = "取消预约订单", notes = "取消预约订单")
    @RequestMapping(value = "/cancelAppointMent", method = RequestMethod.POST)
    public ResponseResult cancelAppointMent(Long appointmentId, String modifyOperator) {
        return iOrderService.cancelAppointMent(appointmentId, modifyOperator);
    }

    @ApiOperation(value = "查看所有订单", notes = "查看所有订单")
    @RequestMapping(value = "/selectAllOrder", method = RequestMethod.POST)
    public ResponseResult selectAllOrder(String storeId, String date, Integer payType) {
        return iOrderService.selectAllOrder(storeId, date, payType);
    }


    @ApiOperation(value = "根据会员号查看定制项目", notes = "根据会员号查看定制项目")
    @RequestMapping(value = "/selectCustomProjectByMember", method = RequestMethod.POST)
    public ResponseResult selectCustomProjectByMember(String memberNum) {
        return iOrderService.selectCustomProjectByMember(memberNum);
    }

    @ApiOperation(value = "项目定制划卡", notes = "项目定制划卡")
    @RequestMapping(value = "/updateCustomProjectTimes", method = RequestMethod.POST)
    public ResponseResult updateCustomProjectTimes(String staffNumber,Long id, String memberNum, String productCode, Long storeId, String storeName, String productName, String postAndBeautician, String createOperator, String memberName, String memberMobile) {
        return iOrderService.updateCustomProjectTimes(staffNumber,id, memberNum, productCode, storeId, storeName, productName, postAndBeautician, createOperator, memberName, memberMobile);
    }

    @ApiOperation(value = " 查询使用记录列表", notes = "查询使用记录列表")
    @RequestMapping(value = "/selectUseRecordList", method = RequestMethod.POST)
    public ResponseResult selectUseRecordList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, Long id) {
        return iOrderService.selectUseRecordList(pageNum, pageSize, id);
    }
    @ApiOperation(value = "审核订单金额", notes = "审核订单金额")
    @RequestMapping(value = "/auditOrderAmount", method = RequestMethod.POST)
    public ResponseResult auditOrderAmount(String orderList,Integer auditStatus) {
        return iOrderService.auditOrderAmount(orderList,auditStatus);
    }
    @ApiOperation(value = "查看指定员工，指定时间预约是否存在", notes = "查看指定员工，指定时间预约是否存在")
    @RequestMapping(value = "/checkAppointmentOrder", method = RequestMethod.POST)
    public ResponseResult checkAppointmentOrder(String stffNumberList,String storeId) {
        return iOrderService.checkAppointmentOrder(stffNumberList, storeId);
    }


}
