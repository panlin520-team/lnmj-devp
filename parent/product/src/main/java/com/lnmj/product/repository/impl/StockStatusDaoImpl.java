package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.StockStatus;
import com.lnmj.product.repository.IStockStatusDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/23 16:27
 * @Description: 仓库状态管理
 */
@Repository
public class StockStatusDaoImpl extends BaseDao implements IStockStatusDao {


    @Override
    public List<StockStatus> selectStockStatusList() {
        return super.selectList("StockStatus.selectStockStatusList");
    }

    @Override
    public StockStatus selectStockStatusByID(Long stockStatusID) {
        return super.select("StockStatus.selectStockStatusByID",stockStatusID);
    }

    @Override
    public int deleteStockStatusByID(StockStatus stockStatus) {
        return super.update("StockStatus.deleteStockStatusByID",stockStatus);
    }

    @Override
    public int insertStockStatus(StockStatus stockStatus) {
        return super.insert("StockStatus.insertStockStatus",stockStatus);
    }

    @Override
    public int updateStockStatusByID(StockStatus stockStatus) {
        return super.update("StockStatus.updateStockStatusByID",stockStatus);
    }


}
