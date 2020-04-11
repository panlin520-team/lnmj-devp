package com.lnmj.product.repository;

import com.lnmj.product.entity.ProductPromotionType;

import java.util.List;

public interface IProductPromotionTypeDao {

    List<ProductPromotionType> selectProductPromotionList();

    List<ProductPromotionType> selectProductPromotionByKeyWord(String keyword);

    int insertProductPromotion(ProductPromotionType productPromotionType);

    int deleteProductPromotion(Long productPromotionTpyeId);

    int checkPromotionName(String promotionName);

    int updateProductPromotionById(ProductPromotionType productPromotionType);

    int selectProductPromotionById(Long productPromotionTypeId);
}