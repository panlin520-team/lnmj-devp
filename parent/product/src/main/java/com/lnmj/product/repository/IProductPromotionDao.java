package com.lnmj.product.repository;


import com.lnmj.product.entity.ProductPromotion;

import java.util.List;

public interface IProductPromotionDao {

    List<ProductPromotion> selectProductPromotionList();

    List<ProductPromotion> selectProductPromotionByKeyWord(String keyword);

    int insertProductPromotion(ProductPromotion productPromotionType);

    int deleteProductPromotion(Long productPromotionTpyeId);

    int checkPromotionName(String promotionName);

    int updateProductPromotionById(ProductPromotion productPromotionType);

    int selectProductPromotionById(Long productPromotionid);
}