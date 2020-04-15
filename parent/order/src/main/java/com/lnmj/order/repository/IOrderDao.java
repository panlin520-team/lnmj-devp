package com.lnmj.order.repository;


import com.lnmj.order.entity.*;
import com.lnmj.order.entity.VO.OrderVO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: panlin
 * @Date: 2019/5/31 12:09
 * @Description:
 */
public interface IOrderDao {
    List<ProductOrder> selectAppointmentByBeauticianAndDate(Map map);
    int insertOrder(Order order);
    int insertProductOrder(Map map);
    List<OrderVO> selectOrderList(Map map);
    List<ProductOrder> selectOrderListByTypeAndStoreId(Map map);
    List<OrderVO> selectOrderListByCondition(Map map);
    List<Order> selectOrderByNum(Map map);
    List<ProductOrder> selectProductOrderListByNum(Map map);
    ProductOrder selectProductOrderByNum(Long orderNumber);
    int cancelOrder(Map map);
    int deleteOrder(Map map);
    int deleteProductOrder(Map map);
    int deleteAppointmentOrder(Map map);

    int deleteOrderByRecordId(Map map);
    int deleteProductOrderByRecordId(Map map);
    int deleteAppointmentOrderByRecordId(Map map);

    int updateOrder(Order order);
    int updateOrderStatus(Map map);
    int updateOrderMemo(Map map);
    List<OrderVO> selectOrderListByCarNumber(HashMap<Object, Object> map);
    List<OrderVO> selectCreateTimeByCarNumber(String carNumber);
    List<Order> selectOrderListByCarNumAndDates(Map map);
    List<Order> selectOrderListByDateAndStore(HashMap map);
    List<OrderVO> selectOrderListByToday(Map mapList);
    List<Order> selectOrderByMemberNumAndProductType(HashMap map);
    List<ProductOrder> selectProductOrderListByMember(Map map);
    List<Map> selectConsumeTimesTopTen(Map map);
    List<OrderVO> selectConsumeMoneyTopTen(Map map);
    List<AppointmentOrder> selectAppointmentOrderListByDate(Map map);
    AppointmentOrder selectAppointmentByNum(Map map);
    int insertAppointmentOrder(AppointmentOrder appointmentOrder);
    int updateAppointmentOrder(AppointmentOrder appointmentOrder);
    List<Order> selectOrderByMemoScope(Map map);
    int checkOrderMemo(Map map);
    int addCustomProjectUser(Map map);
    List<CustomProjectUser> selectCustomProjectByMember(Map map);
    CustomProjectUser selectCustomProjectUserById(Map map);
    int updateUserCustomUseTimes(Map map);
    int addUseRecord(CustomProjectUserRecord customProjectUserRecord);
    int updateUseRecord(CustomProjectUserRecord customProjectUserRecord);
    List<CustomProjectUserRecord> selectUseRecordList(Long id);
    int addUserCustomUseTimes(Long customProjectUserId);
    int updateUseRecordStatus(Map map);

    List<String> selectMemberNumberPayTypeAndAmount(Map map);

    int batchAudit(Map map);

    CustomProjectUser selectCustomProjectUserAndPayById(CustomProjectUser customProjectUser);
    List<CustomProjectUser> selectCustomProjectUserByOrderNum(CustomProjectUser customProjectUser);

    int updateUserCustomTotalTimes(Map map);

    int addCustomProjectUserRefuse(CustomProjectUserRefuse customProjectUserRefuse);

    List<CustomProjectUserRefuse> selectCustomProjectUserRefuseList(Map map);

    int checkDingZhiProjectIsUse(String orderNumber);
}
