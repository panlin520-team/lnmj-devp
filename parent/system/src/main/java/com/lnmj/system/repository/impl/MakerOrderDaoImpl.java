package com.lnmj.system.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.system.entity.MakerOrder;
import com.lnmj.system.entity.MakerOrderUse;
import com.lnmj.system.repository.IMakerOrderDao;
import com.lnmj.system.repository.IMakerOrderDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/8/24 16:15
 * @Description: 创客订单
 */
@Repository
public class MakerOrderDaoImpl extends BaseDao implements IMakerOrderDao {
    @Override
    public List<MakerOrderUse> selectMakerOrderUseList() {
        return super.selectList("makerOrder.selectMakerOrderUseList");
    }

    @Override
    public List<MakerOrderUse> selectMakerOrderUseByCondition(MakerOrderUse makerOrderUse) {
        return super.selectList("makerOrder.selectMakerOrderUseByCondition",makerOrderUse);
    }

    @Override
    public int insertMakerOrderUse(MakerOrderUse makerOrderUse) {
        return super.insert("makerOrder.insertMakerOrderUse",makerOrderUse);
    }

    @Override
    public int updateMakerOrderUse(MakerOrderUse makerOrderUse) {
        return super.update("makerOrder.updateMakerOrderUse",makerOrderUse);
    }

    @Override
    public int deleteMakerOrderUse(MakerOrderUse makerOrderUse) {
        return super.update("makerOrder.deleteMakerOrderUse",makerOrderUse);
    }

    @Override
    public List<MakerOrder> selectMakerOrderList() {
        return super.selectList("makerOrder.selectMakerOrderList");
    }

    @Override
    public List<MakerOrder> selectMakerOrderByCondition(MakerOrder makerOrder) {
        return super.selectList("makerOrder.selectMakerOrderByCondition",makerOrder);
    }

    @Override
    public int insertMakerOrder(MakerOrder makerOrder) {
        return super.insert("makerOrder.insertMakerOrder",makerOrder);
    }

    @Override
    public int updateMakerOrder(MakerOrder makerOrder) {
        return super.update("makerOrder.updateMakerOrder",makerOrder);
    }

    @Override
    public int deleteMakerOrder(MakerOrder makerOrder) {
        return super.update("makerOrder.deleteMakerOrder",makerOrder);
    }
}
