package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.StockProduct;
import com.lnmj.product.repository.IStockProductDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/23 16:31
 * @Description: 仓库商品
 */
@Repository
public class StockProductDaoImpl extends BaseDao implements IStockProductDao {

    @Override
    public List<StockProduct> selectStockProductList() {
        return super.selectList("StockProduct.selectStockProductList");
    }

    @Override
    public StockProduct selectStockProductByID(Long stockProductId) {
        return super.select("StockProduct.selectStockProductByID",stockProductId);
    }

    @Override
    public List<StockProduct> selectStockProductByStockID(String stockId) {
        return super.selectList("StockProduct.selectStockProductByStockID",stockId);
    }

    @Override
    public int deleteStockProductByID(StockProduct stockProduct) {
        return super.update("StockProduct.deleteStockProductByID",stockProduct);
    }

    @Override
    public int insertStockProduct(StockProduct stockProduct) {
        return super.insert("StockProduct.insertStockProduct",stockProduct);
    }

    @Override
    public int updateStockProductByID(StockProduct stockProduct) {
        return super.update("StockProduct.updateStockProductByID",stockProduct);
    }

    @Override
    public List<StockProduct> selectStockProductByProductID(String productCode) {
        return super.selectList("StockProduct.selectStockProductByProductID",productCode);
    }

    @Override
    public List<StockProduct> selectStockProductByStockIDAndProductID(Map<String, Object> map) {
        return super.selectList("StockProduct.selectStockProductByStockIDAndProductID",map);
    }

    @Override
    public StockProduct selectStockProductByStockIDAndProductIDSum(Map<String, Object> map) {
        return super.select("StockProduct.selectStockProductByStockIDAndProductIDSum",map);
    }

    @Override
    public List<StockProduct> selectStockProductByBatchNumber(String batchNumber) {
        return super.selectList("StockProduct.selectStockProductByBatchNumber",batchNumber);
    }

    @Override
    public int updateStockProductByStockAndProductAndBatchNumber(HashMap<String, Object> map) {
        return super.update("StockProduct.updateStockProductByStockAndProductAndBatchNumber",map);
    }

    @Override
    public List<StockProduct> selectStockProductListByProductName(String productName) {
        return super.selectList("StockProduct.selectStockProductListByProductName",productName);
    }
}
