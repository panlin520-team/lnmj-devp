package com.lnmj.product.repository;

import com.lnmj.product.entity.Stock;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/23 16:15
 * @Description: 仓库
 */
public interface IStockDao {

    List<Stock> selectStockList();

    List<Stock> selectStockByID(Stock stock);

    int insertStock(Stock stock);

    int updateStockByID(Stock stock);


    String selectLastStockCode();

    int delStockById(Map map);

    int checkStock(Stock stock);
}
