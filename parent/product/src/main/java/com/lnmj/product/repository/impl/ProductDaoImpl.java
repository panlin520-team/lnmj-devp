package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.ProducStore;
import com.lnmj.product.entity.ProducSubcompany;
import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.VO.ExportVO;
import com.lnmj.product.entity.VO.ProductAndServiceNameVO;
import com.lnmj.product.entity.VO.ProductNameVO;
import com.lnmj.product.repository.IProductDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/5/5 16:17
 * @Description:
 */
@Repository
public class ProductDaoImpl extends BaseDao implements IProductDao {

    @Override
    public List<Product> selectProductList(Map map) {
        return super.selectList("product.selectProductList", map);
    }

    @Override
    public Product selectById(Long productId) {
        return super.select("product.selectById", productId);
    }

    @Override
    public Product selectProductByCode(String productCode) {
        return super.select("product.selectProductByCode", productCode);
    }

    @Override
    public int insertProduct(Product product) {
        return super.insert("product.insertProduct", product);
    }

    @Override
    public int updateProduct(Product product) {
        return super.update("product.updateProduct", product);
    }

    @Override
    public int updateProductByStatus(Product product) {
        return super.update("product.updateProductByStatus",product);
    }

    @Override
    public int deleteProduct(Map map) {
        return super.delete("product.deleteProductById",map);
    }

    @Override
    public Product checkByCondition(Map map) {
        return super.select("product.checkByCondition", map);
    }

    @Override
    public List<ExportVO> selectProductListByExport(Map map) {
        return super.selectList("product.selectProductListByExport",map);
    }

    @Override
    public List<ExportVO> selectServiceListByExport(Map map) {
        return super.selectList("product.selectServiceListByExport",map);
    }

    @Override
    public List<ProductNameVO> selectProductNameList(Map map) {
        return super.selectList("product.selectProductNameList",map);
    }

    @Override
    public List<ProductAndServiceNameVO> selectProductAndServiceNameList() {
        return super.selectList("product.selectProductAndServiceNameList");
    }

    @Override
    public List<Product> selectProductBySubClass(Map<Object, Object> map) {
        return super.selectList("product.selectProductBySubClass");
    }

    @Override
    public int addProductSubcompany(Map map) {
        return super.insert("product.addProductSubcompany",map);
    }

    @Override
    public List<Product> selectProductSubcompany(Map map) {
        return super.selectList("product.selectProductSubcompany",map);
    }

    @Override
    public List<Product> selectProductStore(Map map) {
        return super.selectList("product.selectProductStore",map);
    }

    @Override
    public int addProductStore(Map map) {
        return super.insert("product.addProductStore",map);
    }

    @Override
    public int onDownProductBySubcompany(ProducSubcompany producSubcompany) {
        return super.update("product.onDownProductBySubcompany",producSubcompany);
    }

    @Override
    public int onDownProductByStore(ProducStore producStore) {
        return super.update("product.onDownProductByStore",producStore);
    }

    @Override
    public int updateProductSales(Map map) {
        return super.update("product.updateProductSales",map);
    }

    @Override
    public int updateStockQuantity(Map map) {
        return super.update("product.updateStockQuantity",map);
    }

    @Override
    public int checkProductSubCompany(Map map) {
        return super.select("product.checkProductSubCompany",map);
    }

    @Override
    public int checkProductStore(Map map) {
        return super.select("product.checkProductStore",map);
    }

    @Override
    public int deleteProductSubcompany(Map map) {
        return super.delete("product.deleteProductSubcompany",map);
    }

    @Override
    public int deleteProductStore(Map map) {
        return super.delete("product.deleteProductStore",map);
    }

    @Override
    public int calculation(HashMap<Object, Object> map) {
        return super.update("product.calculation",map);
    }
}
