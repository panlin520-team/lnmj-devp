package com.lnmj.wallet.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.wallet.entity.*;
import com.lnmj.wallet.repository.CapitalPoolDao;
import com.lnmj.wallet.repository.WalletDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class CapitalPoolDaoImpl extends BaseDao implements CapitalPoolDao {
    @Override
    public int addCapitalPool(CapitalPool capitalPool) {
        return super.insert("capitalPool.addCapitalPool", capitalPool);
    }

    @Override
    public int updateCapitalPool(CapitalPool capitalPool) {
        return super.update("capitalPool.updateCapitalPool", capitalPool);
    }


    @Override
    public List<CapitalPool> selectCapitalPoolList(CapitalPool capitalPool) {
        return super.selectList("capitalPool.selectCapitalPoolList",capitalPool);
    }

    @Override
    public List<Transfer> selectTransfer(Map map) {
        return super.selectList("capitalPool.selectTransfer",map);
    }

    @Override
    public int reduceForwardSaleByCondition(CapitalPool capitalPool) {
        return super.update("capitalPool.reduceForwardSaleByCondition",capitalPool);
    }

    @Override
    public int deleteCapPoolByOrderNumber(CapitalPool capitalPool) {
        return super.delete("capitalPool.deleteCapPoolByOrderNumber",capitalPool);
    }

    @Override
    public int addTransfer(Transfer transfer) {
        return super.insert("capitalPool.addTransfer", transfer);
    }


}
