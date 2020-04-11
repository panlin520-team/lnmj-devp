package com.lnmj.product.repository;

import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.ServiceProduct;
import com.lnmj.product.entity.VO.ProductServiceVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IServiceProductDao {
    List<ServiceProduct> selectProductList(Map map);

    int insertServiceProduct(ServiceProduct serviceProduct);

    int deleteServiceProduct(Map map);

    ServiceProduct checkByCondition(Map map);

    int updateServiceProduct(ServiceProduct serviceProduct);

    List<ProductServiceVO> selectServiceVOList(Map map);

    List<ServiceProduct> selectServiceListById(Map map);

    List<Product> selectProductListById(Map map);

    int updateProductByStatus(ServiceProduct serviceProduct);

    List<ServiceProduct> selectServiceProductSubcompany(Map map);

    List<ServiceProduct> selectServiceProductStore(Map map);

    int updateProductSales(Map map);

    ServiceProduct selectProductByCode(String productCode);

    int calculation(HashMap<Object, Object> map);
}
