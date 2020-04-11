package com.lnmj.product.repository;

import com.lnmj.product.entity.ProducStore;
import com.lnmj.product.entity.ProducSubcompany;
import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.VO.ExportVO;
import com.lnmj.product.entity.VO.ProductAndServiceNameVO;
import com.lnmj.product.entity.VO.ProductNameVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IProductDao {
    List<Product> selectProductList(Map map);

    Product selectById(Long productId);

    Product selectProductByCode(String productCode);

    int insertProduct(Product product);

    int updateProduct(Product product);

    int updateProductByStatus(Product product);

    int deleteProduct(Map map);

    Product checkByCondition(Map map);

    List<ExportVO> selectProductListByExport(Map map);

    List<ExportVO> selectServiceListByExport(Map map);

    List<ProductNameVO> selectProductNameList(Map map);

    List<ProductAndServiceNameVO> selectProductAndServiceNameList();

    List<Product> selectProductBySubClass(Map<Object, Object> map);

    int addProductSubcompany(Map map);

    List<Product> selectProductSubcompany(Map map);

    List<Product> selectProductStore(Map map);

    int addProductStore(Map map);

    int onDownProductBySubcompany(ProducSubcompany producSubcompany);

    int onDownProductByStore(ProducStore producStore);

    int updateProductSales(Map map);

    int updateStockQuantity(Map map);

    int checkProductSubCompany(Map map);

    int checkProductStore(Map map);

    int deleteProductSubcompany(Map map);

    int deleteProductStore(Map map);

    int calculation(HashMap<Object, Object> map);
}