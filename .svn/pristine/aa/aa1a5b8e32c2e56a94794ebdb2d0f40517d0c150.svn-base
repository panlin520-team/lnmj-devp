package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.Stock;
import com.lnmj.product.entity.StockProduct;
import com.lnmj.product.repository.IStockDao;
import com.lnmj.product.repository.IStockProductDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/23 16:31
 * @Description: 仓库
 */
@Repository
public class StockDaoImpl extends BaseDao implements IStockDao {

    @Override
    public List<Stock> selectStockList() {
        return super.selectList("stock.selectStockList");
    }

    @Override
    public List<Stock> selectStockByID(Stock stock) {
        return super.selectList("stock.selectStockByID",stock);
    }

    @Override
    public int insertStock(Stock stock) {
        return super.insert("stock.insertStock",stock);
    }

    @Override
    public int updateStockByID(Stock stock) {
        return super.update("stock.updateStockByID",stock);
    }

    @Override
    public String selectLastStockCode() {
        return super.select("stock.selectLastStockCode");
    }

    @Override
    public int delStockById(Map map) {
        return super.update("stock.delStockById",map);
    }

    @Override
    public int checkStock(Stock stock) {
        return super.select("stock.checkStock",stock);
    }
}
