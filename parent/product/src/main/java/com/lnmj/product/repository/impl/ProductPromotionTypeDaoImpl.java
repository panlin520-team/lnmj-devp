package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.ProductPromotionType;
import com.lnmj.product.repository.IProductPromotionTypeDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/5 16:17
 * @Description:
 */
@Repository
public class ProductPromotionTypeDaoImpl extends BaseDao implements IProductPromotionTypeDao {

    @Override
    public List<ProductPromotionType> selectProductPromotionList() {
        return super.selectList("productPromotionType.selectProductPromotionList", null);
    }

    @Override
    public List<ProductPromotionType> selectProductPromotionByKeyWord(String keyword) {
        return super.selectList("productPromotionType.selectProductPromotionByKeyWord", keyword);
    }

    @Override
    public int insertProductPromotion(ProductPromotionType productPromotionType) {
        return super.insert("productPromotionType.insertProductPromotion", productPromotionType);
    }

    @Override
    public int deleteProductPromotion(Long productPromotionTpyeId) {
        return super.delete("productPromotionType.deleteProductPromotion", productPromotionTpyeId);
    }

    @Override
    public int checkPromotionName(String promotionName) {
        return super.select("productPromotionType.checkPromotionName", promotionName);
    }

    @Override
    public int updateProductPromotionById(ProductPromotionType productPromotionType) {
        return super.update("productPromotionType.updateProductPromotionById", productPromotionType);
    }

    @Override
    public int selectProductPromotionById(Long productPromotionTypeId) {
        return super.select("productPromotionType.selectProductPromotionById", productPromotionTypeId);
    }
}
