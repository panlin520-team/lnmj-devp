package com.lnmj.order.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.order.entity.*;
import com.lnmj.order.entity.VO.OrderVO;
import com.lnmj.order.repository.IOrderDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: panlin
 * @Date: 2019/5/31 12:09
 * @Description:
 */
@Repository
public class OrderDaoImpl extends BaseDao implements IOrderDao {
    @Override
    public List<ProductOrder> selectAppointmentByBeauticianAndDate(Map map) {
        return super.selectList("order.selectAppointmentByBeauticianAndDate",map);
    }

    @Override
    public int insertOrder(Order order) {
        return super.insert("order.insertOrder",order);
    }

    @Override
    public int insertProductOrder(Map map) {
        return super.insert("order.insertProductOrder",map);
    }

    @Override
    public List<OrderVO> selectOrderList(Map map) {
        return super.selectList("order.selectOrderList",map);
    }

    @Override
    public List<ProductOrder> selectOrderListByTypeAndStoreId(Map map) {
        return super.selectList("order.selectOrderListByTypeAndStoreId",map);
    }

    @Override
    public List<OrderVO> selectOrderListByCondition(Map map) {
        return super.selectList("order.selectOrderListByCondition",map);
    }

    @Override
    public List<Order> selectOrderByNum(Map map) {
        return super.selectList("order.selectOrderByNum",map);
    }

    @Override
    public List<ProductOrder> selectProductOrderListByNum(Map map) {
        return super.selectList("order.selectProductOrderListByNum",map);
    }
    @Override
    public ProductOrder selectProductOrderByNum(Long orderNumber) {
        return super.select("order.selectProductOrderByNum",orderNumber);
    }

    @Override
    public int cancelOrder(Map map) {
        return super.update("order.cancelOrder",map);
    }

    @Override
    public int deleteOrder(Map map) {
        return super.update("order.deleteOrder",map);
    }

    @Override
    public int deleteProductOrder(Map map) {
        return super.update("order.deleteProductOrder",map);
    }

    @Override
    public int deleteAppointmentOrder(Map map) {
        return super.update("order.deleteAppointmentOrder",map);
    }

    @Override
    public int deleteOrderByRecordId(Map map) {
        return super.update("order.deleteOrderByRecordId",map);
    }

    @Override
    public int deleteProductOrderByRecordId(Map map) {
        return super.update("order.deleteProductOrderByRecordId",map);
    }

    @Override
    public int deleteAppointmentOrderByRecordId(Map map) {
        return super.update("order.deleteAppointmentOrderByRecordId",map);
    }


    @Override
    public int updateOrder(Order order) {
        return super.update("order.updateOrder",order);
    }

    @Override
    public int updateOrderStatus(Map map) {
        return super.update("order.updateOrderStatus",map);

    }

    @Override
    public int updateOrderMemo(Map map) {
        return super.update("order.updateOrderMemo",map);
    }

    @Override
    public List<OrderVO> selectOrderListByCarNumber(HashMap<Object, Object> map) {
        return super.selectList("order.selectOrderListByCarNumber", map);
    }

    @Override
    public List<OrderVO> selectCreateTimeByCarNumber(@Param(value="carNumber") String carNumber) {
        return super.selectList("order.selectCreateTimeByCarNumber", carNumber);
    }


    @Override
    public List<Order> selectOrderListByCarNumAndDates(Map map) {
        return super.selectList("order.selectOrderListByCarNumAndDates", map);
    }

    @Override
    public List<Order> selectOrderListByDateAndStore(HashMap map) {
        return super.selectList("order.selectOrderListByDateAndStore",map);
    }

    @Override
    public List<OrderVO> selectOrderListByToday(Map mapList) {
        return super.selectList("order.selectOrderListByToday",mapList);
    }

    @Override
    public List<Order> selectOrderByMemberNumAndProductType(HashMap map) {
        return super.selectList("order.selectOrderByMemberNumAndProductType",map);
    }

    @Override
    public List<ProductOrder> selectProductOrderListByMember(Map map) {
        return super.selectList("order.selectProductOrderListByMember",map);
    }

    @Override
    public List<Map> selectConsumeTimesTopTen(Map map) {
        return super.selectList("order.selectConsumeTimesTopTen",map);
    }

    @Override
    public List<OrderVO> selectConsumeMoneyTopTen(Map map) {
        return super.selectList("order.selectConsumeMoneyTopTen",map);
    }

    @Override
    public List<AppointmentOrder> selectAppointmentOrderListByDate(Map map) {
        return super.selectList("appointmentOrder.selectAppointmentOrderListByDate",map);
    }

    @Override
    public AppointmentOrder selectAppointmentByNum(Map map) {
        return super.select("appointmentOrder.selectAppointmentByNum",map);
    }

    @Override
    public int insertAppointmentOrder(AppointmentOrder appointmentOrder) {
        return super.insert("appointmentOrder.insertAppointmentOrder",appointmentOrder);
    }

    @Override
    public int updateAppointmentOrder(AppointmentOrder appointmentOrder) {
        return super.insert("appointmentOrder.updateAppointmentOrder",appointmentOrder);
    }

    @Override
    public List<Order> selectOrderByMemoScope(Map map) {
        return super.selectList("order.selectOrderByMemoScope",map);
    }

    @Override
    public int checkOrderMemo(Map map) {
        return super.select("order.checkOrderMemo",map);
    }

    @Override
    public int addCustomProjectUser(Map map) {
        return super.insert("customProjectUser.addCustomProjectUserBatch",map);
    }

    @Override
    public List<CustomProjectUser> selectCustomProjectByMember(Map map) {
        return super.selectList("customProjectUser.selectCustomProjectByMember",map);
    }

    @Override
    public CustomProjectUser selectCustomProjectUserById(Map map) {
        return super.select("customProjectUser.selectCustomProjectUserById",map);
    }

    @Override
    public int updateUserCustomUseTimes(Map map) {
        return super.update("customProjectUser.updateUserCustomUseTimes",map);
    }

    @Override
    public int addUseRecord(CustomProjectUserRecord customProjectUserRecord) {
        return super.insert("customProjectUserRecord.addUseRecord",customProjectUserRecord);
    }

    @Override
    public int updateUseRecord(CustomProjectUserRecord customProjectUserRecord) {
        return super.update("customProjectUserRecord.updateUseRecord",customProjectUserRecord);
    }

    @Override
    public List<CustomProjectUserRecord> selectUseRecordList(Long id) {
        return super.selectList("customProjectUserRecord.selectUseRecordList",id);
    }

    @Override
    public int addUserCustomUseTimes(Long customProjectUserId) {
        return super.update("customProjectUser.addUserCustomUseTimes",customProjectUserId);
    }

    @Override
    public int updateUseRecordStatus(Map map) {
        return super.update("customProjectUserRecord.updateUseRecordStatus",map);
    }

    @Override
    public List<String> selectMemberNumberPayTypeAndAmount(Map map) {
        return super.selectList("order.selectMemberNumberPayTypeAndAmount",map);
    }

    @Override
    public int batchAudit(Map map) {
        return update("order.batchAudit", map);
    }

    @Override
    public CustomProjectUser selectCustomProjectUserAndPayById(CustomProjectUser customProjectUser) {
        return super.select("customProjectUser.selectCustomProjectUserAndPayById",customProjectUser);
    }

    @Override
    public List<CustomProjectUser> selectCustomProjectUserByOrderNum(CustomProjectUser customProjectUser) {
        return super.selectList("customProjectUser.selectCustomProjectUserByOrderNum",customProjectUser);
    }

    @Override
    public int updateUserCustomTotalTimes(Map map) {
        return super.update("customProjectUser.updateUserCustomTotalTimes",map);
    }

    @Override
    public int addCustomProjectUserRefuse(CustomProjectUserRefuse customProjectUserRefuse) {
        return super.insert("customProjectUserRefuse.addCustomProjectUserRefuse",customProjectUserRefuse);
    }

    @Override
    public List<CustomProjectUserRefuse> selectCustomProjectUserRefuseList(Map map) {
        return super.selectList("customProjectUserRefuse.selectCustomProjectUserRefuseList",map);
    }

    @Override
    public int checkDingZhiProjectIsUse(String orderNumber) {
        return super.select("customProjectUser.checkDingZhiProjectIsUse",orderNumber);

    }
}
