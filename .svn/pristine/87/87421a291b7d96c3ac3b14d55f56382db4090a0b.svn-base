package com.lnmj.product.repository;


import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.ProductCategory;
import com.lnmj.product.entity.ServiceProduct;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:29
 * @Description: 商品品类dao
 */
public interface IProductCategoryDao {


    List<ProductCategory> selectProductCategoryList();

    int insertProductCategory(ProductCategory productCategory);

    int updateProductCategoryById(ProductCategory productCategory);

    int deleteProductCategory(Long productCategoryId);

    List<Product> selectProductByProductCategoryId(Long productCategoryId);

    List<ServiceProduct> selectServiceByProductCategoryId(Long productCategoryId);

    List<ProductCategory> selectProductCategoryListByKeyWord(String keyWord);

    ProductCategory selectProductCategoryByProductCategoryId(Long productCategoryId);
}
