package com.lnmj.data.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.data.entity.OrderTicheng;
import com.lnmj.data.entity.Statistic;
import com.lnmj.data.entity.UserEvaluating;
import com.lnmj.data.repository.IOrderTichengDao;
import com.lnmj.data.repository.IStatisticDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/9/6 10:52
 * @Description: 统计
 */

@Repository
public class OrderTichengDaoImpl extends BaseDao implements IOrderTichengDao {


    @Override
    public List<OrderTicheng> selectOrderTichengList() {
        return super.selectList("OrderTicheng.selectOrderTichengList");
    }

    @Override
    public List<OrderTicheng> selectOrderTichengByCondition(OrderTicheng orderTicheng) {
        return super.selectList("OrderTicheng.selectOrderTichengByCondition",orderTicheng);
    }

    @Override
    public OrderTicheng selectOrderTichengByID(Long ID) {
        return super.select("OrderTicheng.selectOrderTichengByID",ID);
    }

    @Override
    public int deleteOrderTichengByID(OrderTicheng orderTicheng) {
        return super.update("OrderTicheng.updateOrderTichengByID",orderTicheng);
    }

    @Override
    public int insertOrderTicheng(OrderTicheng orderTicheng) {
        return super.insert("OrderTicheng.insertOrderTicheng",orderTicheng);
    }

    @Override
    public int updateOrderTichengByID(OrderTicheng orderTicheng) {
        return super.update("OrderTicheng.updateOrderTichengByID",orderTicheng);
    }
}
