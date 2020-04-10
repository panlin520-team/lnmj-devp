package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.ProductCategory;
import com.lnmj.product.entity.ServiceProduct;
import com.lnmj.product.repository.IProductCategoryDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:30
 * @Description: 商品种类dao实现类
 */
@Repository
public class ProductCategoryDaoImpl extends BaseDao implements IProductCategoryDao {


    @Override
    public List<ProductCategory> selectProductCategoryList() {
        return super.selectList("productCategory.selectProductCategoryList");
    }

    @Override
    public int insertProductCategory(ProductCategory productCategory) {
        return super.insert("productCategory.insertProductCategory",productCategory);
    }

    @Override
    public int updateProductCategoryById(ProductCategory productCategory) {
        return super.update("productCategory.updateProductCategory",productCategory);
    }

    @Override
    public int deleteProductCategory(Long productCategoryId) {
        return super.delete("productCategory.deleteProductCategory",productCategoryId);
    }

    @Override
    public List<Product> selectProductByProductCategoryId(Long productCategoryId) {
        return super.selectList("productCategory.selectProductByProductCategoryId",productCategoryId);
    }
    @Override
    public List<ServiceProduct> selectServiceByProductCategoryId(Long productCategoryId) {
        return super.selectList("productCategory.selectServiceByProductCategoryId",productCategoryId);
    }

    @Override
    public List<ProductCategory> selectProductCategoryListByKeyWord(String keyWord) {
        return super.selectList("productCategory.selectProductCategoryByKeyWord",keyWord);
    }

    @Override
    public ProductCategory selectProductCategoryByProductCategoryId(Long productCategoryId) {
        return super.select("productCategory.selectProductCategoryByProductCategoryId",productCategoryId);
    }
}
