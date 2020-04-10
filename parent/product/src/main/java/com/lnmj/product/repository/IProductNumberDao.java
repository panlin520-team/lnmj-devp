package com.lnmj.product.repository;

import com.lnmj.product.entity.ProductNumber;

import java.util.List;
import java.util.Map;

public interface IProductNumberDao {

    List<ProductNumber> selectProductNumberList();

    List<ProductNumber> selectProductNumberByCondition(ProductNumber productNumber);

    List<ProductNumber> selectProductNumberListByProduct(Map map);

    int insertProductNumber(ProductNumber productNumber);

    int updateProductNumberByID(ProductNumber productNumber);

    int deleteProductNumberByID(ProductNumber productNumber);
}