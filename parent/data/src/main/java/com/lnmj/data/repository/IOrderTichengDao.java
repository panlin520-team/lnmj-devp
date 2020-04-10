package com.lnmj.data.repository;

import com.lnmj.data.entity.OrderTicheng;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019-12-16 10:39
 * @Description: 订单提成
 */
public interface IOrderTichengDao {
    List<OrderTicheng> selectOrderTichengList();

    List<OrderTicheng> selectOrderTichengByCondition(OrderTicheng orderTicheng);

    OrderTicheng selectOrderTichengByID(Long ID);

    int deleteOrderTichengByID(OrderTicheng orderTicheng);

    int insertOrderTicheng(OrderTicheng orderTicheng);

    int updateOrderTichengByID(OrderTicheng orderTicheng);
}
