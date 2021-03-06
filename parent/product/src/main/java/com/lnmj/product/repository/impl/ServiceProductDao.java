package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.ServiceProduct;
import com.lnmj.product.entity.VO.ProductServiceVO;
import com.lnmj.product.repository.IServiceProductDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ServiceProductDao extends BaseDao implements IServiceProductDao {
    @Override
    public List<ServiceProduct> selectProductList(Map map){
        return super.selectList("serviceProduct.selectProductList", map);
    }

    @Override
    public int insertServiceProduct(ServiceProduct serviceProduct) {
        return super.insert("serviceProduct.insertServiceProduct", serviceProduct);
    }

    @Override
    public int deleteServiceProduct(Map map) {
        return super.delete("serviceProduct.deleteServiceProduct", map);
    }

    @Override
    public ServiceProduct checkByCondition(Map map) {
        return super.select("serviceProduct.checkByCondition", map);
    }

    @Override
    public int updateServiceProduct(ServiceProduct serviceProduct) {
        return super.update("serviceProduct.updateServiceProduct", serviceProduct);
    }

    @Override
    public List<ProductServiceVO> selectServiceVOList(Map map) {
        return super.selectList("serviceProduct.selectServiceVOList",map);
    }

    @Override
    public List<ServiceProduct> selectServiceListById(Map map) {
        return super.selectList("serviceProduct.selectServiceListById", map);
    }

    @Override
    public List<Product> selectProductListById(Map map) {
        return super.selectList("product.selectProductListById", map);
    }

    @Override
    public int updateProductByStatus(ServiceProduct serviceProduct) {
        return super.update("serviceProduct.updateProductByStatus", serviceProduct);
    }

    @Override
    public List<ServiceProduct> selectServiceProductSubcompany(Map map) {
        return super.selectList("serviceProduct.selectServiceProductSubcompany",map);
    }

    @Override
    public List<ServiceProduct> selectServiceProductStore(Map map) {
        return super.selectList("serviceProduct.selectServiceProductStore",map);
    }

    @Override
    public int updateProductSales(Map map) {
        return super.update("serviceProduct.updateProductSales", map);
    }

    @Override
    public ServiceProduct selectProductByCode(String productCode) {
        return super.select("serviceProduct.selectProductByCode", productCode);
    }

    @Override
    public int calculation(HashMap<Object, Object> map) {
        return super.update("serviceProduct.calculation", map);
    }
}
