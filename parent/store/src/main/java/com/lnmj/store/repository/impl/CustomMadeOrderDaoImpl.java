package com.lnmj.store.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.store.entity.CustomMadeOrder;
import com.lnmj.store.repository.ICustomMadeOrderDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @Author panlin
 * @create 2019/5/28
 */

@Repository
public class CustomMadeOrderDaoImpl extends BaseDao implements ICustomMadeOrderDao {

    @Override
    public int insertOrder(CustomMadeOrder customMadeOrder) {
        return super.insert("customMadeOrder.insertOrder",customMadeOrder);
    }

    @Override
    public List<CustomMadeOrder> selectCustomMadeOrderList(Map map) {
        return super.selectList("customMadeOrder.selectCustomMadeOrderList",map);
    }

    @Override
    public CustomMadeOrder selectCustomMadeOrderDetailById(Long OrderNumber) {
        return super.select("customMadeOrder.selectCustomMadeOrderDetailById",OrderNumber);
    }
}
