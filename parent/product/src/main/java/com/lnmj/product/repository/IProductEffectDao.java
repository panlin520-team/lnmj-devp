package com.lnmj.product.repository;

import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.ProductEffect;
import com.lnmj.product.entity.ServiceProduct;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:29
 * @Description: 商品功效dao
 */
public interface IProductEffectDao {

    List<ProductEffect> selectProductEffectList();

    int insertProductEffect(ProductEffect productEffect);

    int updateProductEffectById(ProductEffect productEffect);

    int deleteProductEffect(Long productEffectId);

    List<Product> selectProductByProductEffectId(Long productEffectId);

    List<ServiceProduct> selectServiceByProductEffectId(Long productEffectId);

    List<ProductEffect> selectProductEffectListByKeyWord(String keyWord);

    ProductEffect selectProductEffectByProductEffectId(Long productEffectId);
}
