package com.lnmj.order.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.order.entity.AppointmentOrder;
import com.lnmj.order.entity.Order;
import com.lnmj.order.entity.ProductOrder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: panlin
 * @Date: 2019/5/31 12:05
 * @Description:
 */
@Service("orderService")
public interface IOrderService {
    ResponseResult selectAppointmentByBeauticianAndDate(String beauticianId, String nurseTime);

    ResponseResult createServiceOrder(Integer orderType,
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
                                      String remark, String nurseDate);

    ResponseResult selectOrderList(int pageNum, int pageSize, String storeId, String keyWordOrderNum, String keyWordMobile, Integer orderType, Integer payStatus, Integer orderStatus, String date,Integer orderChannel);

    ResponseResult selectOrderListNoPage();

    ResponseResult selectOrderByNum(String orderNumbers);

    ResponseResult cancelOrder(String orderNumbers, Integer orderStatus);

    ResponseResult deleteOrder(String orderNumbers, String deleteReason);

    ResponseResult updateOrder(String productIds, Order order);

    ResponseResult exportOrder(HttpServletRequest req, HttpServletResponse resp, Long storeId);

    ResponseResult updateOrderStatus(Long orderNumber, Integer status, Integer orderStatusOld);

    ResponseResult updateOrderMemo(Long orderNumber, String memoNum);

    ResponseResult selectOrderListByCarNumber(int pageNum, int pageSize, Long carNumber, Integer orderType);

    ResponseResult selectOrderProductByOrderNum(Long orderNumber);

    ResponseResult selectCreateTimeByCarNumber(String carNumber);

    ResponseResult selectOrderListByCarNumAndDates(String cardNumber, String startDate, String endDate);

    ResponseResult selectTodayReceipt(String date, String storeId);

    ResponseResult selectTodayCashReceipt(String date, String storeId);

    ResponseResult selectOrderTypeList();

    ResponseResult selectOrderTypeListForYeji();

    ResponseResult selectOrderStatusList();

    ResponseResult selectOrderChannelList();

    ResponseResult selectOrderListByToday(String storeId,String date);

    ResponseResult rechargeOrderRefund(String orderNumber, Double refundAmount,String name,String mobile,String memberNum,Long accountTypeId,String isAudit);

    ResponseResult payOrderRefund(String outStorageIdQiTa,String outStorageIdXiaoShou,Integer isTiYanKaOrDingzhi, Integer isTiYanKa, Long expUseRecordId,Integer isHuaKa,String payTypeAndAmount, String memberNum, Long experiencecardProductUserId, String orderNumber, String expCardNum, String modifyOperator,String storeId);

    ResponseResult selectOrderByCash(int pageNum, int pageSize, String storeId, String date, Integer cash);

    ResponseResult selectOrderByMemberNumAndProductType(int pageNum, int pageSize, String storeId, String memberNum, Integer orderType, String date, String keywordOrderNum);

    ResponseResult selectOrderProductByMemberNum(int pageNum, int pageSize, Long storeId, String memberNum, String date);

    ResponseResult selectConsumeTopTen(String memberNum, Long storeId);

    ResponseResult selectConsumeTimesTopTen(String memberNum, Long storeId);

    ResponseResult selectConsumeMoneyTopTen(String memberNum, Long storeId);

    ResponseResult selectAppointmentOrderListByDate(/*String nursingTime,*/Long storeId);

    ResponseResult payOrder(String staffNumber, String orderNumber, String payTypeAndAmount, String productIds, Double payPrice, Double number, String createOperator, String useLimit, Integer totalTimes);

    ResponseResult cancelAppointMent(Long appointmentId, String modifyOperator);

    ResponseResult selectOrderByMemoScope(Long storeId, Integer memoNumStart, Integer memoNumEnd);

    ResponseResult checkOrderMemo(String memoNum);

    ResponseResult selectAllOrder(String storeId, String date, Integer payType);

    ResponseResult selectCustomProjectByMember(String memberNum);

    ResponseResult updateCustomProjectTimes(String staffNumber,Long id, String memberNum, String productCode, Long storeId, String storeName, String productName, String postAndBeautician, String createOperator, String memberName, String memberMobile);

    ResponseResult selectUseRecordList(int pageNum, int pageSize, Long id);

    ResponseResult insertOrder(Order order);

    ResponseResult insertProductOrder(ProductOrder productOrder);

    ResponseResult insertAppointmentOrder(AppointmentOrder appointmentOrder);

    ResponseResult selectMemberCashAll(String memberNumber);

    ResponseResult auditOrderAmount(String orderList,Integer auditStatus);

    ResponseResult customProjectUserRefuse(Long id,Integer refuseTimes);

    ResponseResult selectCustomProjectUserRefuseList(Long id);

    ResponseResult checkAppointmentOrder(String stffNumberList,String storeId);
}
