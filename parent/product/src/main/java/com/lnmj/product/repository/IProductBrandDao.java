package com.lnmj.product.repository;


import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.ProductBrand;
import com.lnmj.product.entity.ServiceProduct;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 10:25
 * @Description:  商品品牌dao
 */
public interface IProductBrandDao{

    int insertProductBrand(ProductBrand productBrand);

    int updateProductBrandById(ProductBrand productBrand);

    int  deleteProductBrand(Long productBrandId);

    List<Product> selectProductByProductBrandId(Long productBrandId);

    List<ServiceProduct> selectServiceByProductBrandId(Long productBrandId);

    List<ProductBrand> selectProductBrandList();

    List<ProductBrand> selectProductBrandListByKeyWord(String keyWord);

    ProductBrand selectProductBrandByProductBrandId(Long productBrandId);

    ProductBrand selectBrandById(Long productBrandId);
}
