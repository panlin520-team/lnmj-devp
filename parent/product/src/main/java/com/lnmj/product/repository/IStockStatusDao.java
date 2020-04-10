package com.lnmj.product.repository;

import com.lnmj.product.entity.StockStatus;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/23 16:08
 * @Description: 仓库状态管理
 */
public interface IStockStatusDao {
    List<StockStatus> selectStockStatusList();

    StockStatus selectStockStatusByID(Long stockStatusID);

    int deleteStockStatusByID(StockStatus stockStatus);

    int insertStockStatus(StockStatus stockStatus);

    int updateStockStatusByID(StockStatus stockStatus);


}
