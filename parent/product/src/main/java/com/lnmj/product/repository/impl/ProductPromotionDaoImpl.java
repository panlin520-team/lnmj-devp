package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.ProductPromotion;
import com.lnmj.product.repository.IProductPromotionDao;
import com.lnmj.product.repository.IProductPromotionTypeDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/5 16:17
 * @Description:
 */
@Repository
public class ProductPromotionDaoImpl extends BaseDao implements IProductPromotionDao {

    @Override
    public List<ProductPromotion> selectProductPromotionList() {
        return super.selectList("productPromotion.selectProductPromotionList", null);
    }

    @Override
    public List<ProductPromotion> selectProductPromotionByKeyWord(String keyword) {
        return super.selectList("productPromotion.selectProductPromotionByKeyWord", keyword);
    }

    @Override
    public int insertProductPromotion(ProductPromotion productPromotion) {
        return super.insert("productPromotion.insertProductPromotion", productPromotion);
    }

    @Override
    public int deleteProductPromotion(Long productPromotionTpyeId) {
        return super.delete("productPromotion.deleteProductPromotion", productPromotionTpyeId);
    }

    @Override
    public int checkPromotionName(String promotionName) {
        return super.select("productPromotion.checkPromotionName", promotionName);
    }

    @Override
    public int updateProductPromotionById(ProductPromotion productPromotion) {
        return super.update("productPromotion.updateProductPromotionById", productPromotion);
    }

    @Override
    public int selectProductPromotionById(Long productPromotionid) {
        return super.select("productPromotion.selectProductPromotionById", productPromotionid);
    }
}
