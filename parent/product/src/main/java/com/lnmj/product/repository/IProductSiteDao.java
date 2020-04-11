package com.lnmj.product.repository;

import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.ProductSite;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:29
 * @Description: 商品产地dao
 */
public interface IProductSiteDao {

    List<ProductSite> selectProductSiteList();

    int insertProductSite(ProductSite productSite);

    int updateProductSiteById(ProductSite productSite);

    int deleteProductSite(Long productSiteId);

    List<Product> selectProductByProductSiteId(Long productSiteId);

    List<ProductSite> selectProductSiteListByKeyWord(String keyWord);

    ProductSite selectProductSiteByProductSiteId(Long productSiteId);

    List<ProductSite> selectProductSiteByProductClassifyId(Long productClassifyId);
}
