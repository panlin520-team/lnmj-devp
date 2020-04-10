package com.lnmj.product.repository;

import com.lnmj.product.entity.StockProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/23 16:15
 * @Description: 库存商品
 */
public interface IStockProductDao {
    List<StockProduct> selectStockProductList();

    StockProduct selectStockProductByID(Long stockProductId);

    List<StockProduct> selectStockProductByStockID(String stockId);

    int deleteStockProductByID(StockProduct stockProduct);

    int insertStockProduct(StockProduct stockProduct);

    int updateStockProductByID(StockProduct stockProduct);

    List<StockProduct> selectStockProductByProductID(String productCode);

    List<StockProduct> selectStockProductByStockIDAndProductID(Map<String, Object> map);

    StockProduct selectStockProductByStockIDAndProductIDSum(Map<String, Object> map);

    List<StockProduct> selectStockProductByBatchNumber(String batchNumber);

    int updateStockProductByStockAndProductAndBatchNumber(HashMap<String, Object> map);

    List<StockProduct> selectStockProductListByProductName(String productName);
}
