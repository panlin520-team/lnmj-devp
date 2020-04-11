package com.lnmj.store.repository;

import com.lnmj.store.entity.CustomMadeOrder;

import java.util.List;
import java.util.Map;


public interface ICustomMadeOrderDao {
    int insertOrder(CustomMadeOrder customMadeOrder);

    List<CustomMadeOrder> selectCustomMadeOrderList(Map map);

    CustomMadeOrder selectCustomMadeOrderDetailById(Long cardNumber);
}
