package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.ProductEffect;
import com.lnmj.product.entity.ServiceProduct;
import com.lnmj.product.repository.IProductEffectDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:30
 * @Description: 商品种类dao实现类
 */
@Repository
public class ProductEffectDaoImpl extends BaseDao implements IProductEffectDao {


    @Override
    public List<ProductEffect> selectProductEffectList() {
        return super.selectList("productEffect.selectProductEffectList");
    }

    @Override
    public int insertProductEffect(ProductEffect productEffect) {
        return super.insert("productEffect.insertProductEffect",productEffect);
    }

    @Override
    public int updateProductEffectById(ProductEffect productEffect) {
        return super.update("productEffect.updateProductEffect",productEffect);
    }

    @Override
    public int deleteProductEffect(Long productEffectId) {
        return super.delete("productEffect.deleteProductEffect",productEffectId);
    }

    @Override
    public List<Product> selectProductByProductEffectId(Long productEffectId) {
        return super.selectList("productEffect.selectProductByProductEffectId",productEffectId);
    }
    @Override
    public List<ServiceProduct> selectServiceByProductEffectId(Long productEffectId) {
        return super.selectList("productEffect.selectServiceByProductEffectId",productEffectId);
    }

    @Override
    public List<ProductEffect> selectProductEffectListByKeyWord(String keyWord) {
        return super.selectList("productEffect.selectProductEffectByKeyWord",keyWord);
    }

    @Override
    public ProductEffect selectProductEffectByProductEffectId(Long productEffectId) {
        return super.select("productEffect.selectProductEffectByProductEffectId",productEffectId);
    }
}
