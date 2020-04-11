package com.lnmj.product.repository;

import com.lnmj.product.entity.*;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:29
 * @Description: 商品种类dao
 */
public interface IProductKindDao {
    int updateProductKindById(ProductKind pProductKind);
    List<ProductKind> selectProductKindList();
    int insertProductKind(ProductKind pProductKind);
    int deleteProductKind(Long productKindId);
    List<ProductKind> selectProductKindListByKeyWord(String keyWord);
    ProductKind selectProductKindByProductKindId(Long productKindId);
    List<ProductEffect> selectProductEffectByProductKindId(Long productKindId);
    List<ProductBrand> selectProductBrandByProductKindId(Long productKindId);
    List<ProductCategory> selectProductCategoryByProductKindId(Long productKindId);
    int deleteProductEffectByProductKindId(Long productKindId);
    int deleteProductBrandByProductKindId(Long productKindId);
    int deleteProductCategoryByProductKindId(Long productKindId);
    List<Product> selectProductByProductKindId(Long productKindId);
    List<ServiceProduct> selectServiceByProductKindId(Long productKindId);
    List<ProductKind> selectProductKindByProductClassifyId(Long productClassifyId);
}
