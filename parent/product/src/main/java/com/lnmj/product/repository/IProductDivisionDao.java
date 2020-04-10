package com.lnmj.product.repository;

import com.lnmj.product.entity.ProductDivision;
import com.lnmj.product.entity.ProductKind;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/8/14 15:15
 * @Description:  商品专区dao 接口
 */
public interface IProductDivisionDao {

    int insertProductDivision(ProductDivision productDivision);

    int updateProductDivision(ProductDivision productDivision);

    int deleteProductDivision(Long productDivisionId);

    ProductDivision selectProductDivisionByProductDivisionId(Long productDivisionId);

    List<ProductDivision> selectProductDivisionList();

    List<ProductDivision> selectProductDivisionListByKeyWord(String keyWord);

    List<ProductDivision> selectProductDivisionByProductClassifyId(Long productClassifyId);
}
