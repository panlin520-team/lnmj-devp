package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.*;
import com.lnmj.product.repository.IProductKindDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:30
 * @Description: 商品种类dao实现类
 */
@Repository
public class ProductKindDaoImpl extends BaseDao implements IProductKindDao {

    @Override
    public int updateProductKindById(ProductKind pProductKind) {
        return super.update("productKind.updateProductKind",pProductKind);
    }

    @Override
    public List<ProductKind> selectProductKindList() {
        return super.selectList("productKind.selectProductKindList");
    }

    @Override
    public int insertProductKind(ProductKind pProductKind) {
        return super.insert("productKind.insertProductKind",pProductKind);
    }

    @Override
    public int deleteProductKind(Long productKindId) {
        return super.delete("productKind.deleteProductKind",productKindId);
    }

    @Override
    public List<ProductBrand> selectProductBrandByProductKindId(Long productKindId) {
        return super.selectList("productKind.selectProductBrandByProductKindId",productKindId);
    }

    @Override
    public List<ProductCategory> selectProductCategoryByProductKindId(Long productKindId) {
        return super.selectList("productKind.selectProductCategoryByProductKindId",productKindId);
    }

    @Override
    public int deleteProductEffectByProductKindId(Long productKindId) {
        return super.delete("productKind.deleteProductEffectByProductKindId",productKindId);
    }

    @Override
    public int deleteProductBrandByProductKindId(Long productKindId) {
        return super.delete("productKind.deleteProductBrandByProductKindId",productKindId);
    }

    @Override
    public int deleteProductCategoryByProductKindId(Long productKindId) {
        return super.delete("productKind.deleteProductCategoryByProductKindId",productKindId);
    }

    @Override
    public List<ProductEffect> selectProductEffectByProductKindId(Long productKindId) {
        return super.selectList("productKind.selectProductEffectByProductKindId",productKindId);
    }

    @Override
    public List<Product> selectProductByProductKindId(Long productKindId) {
        return super.selectList("productKind.selectProductByProductKindId",productKindId);
    }
    @Override
    public List<ServiceProduct> selectServiceByProductKindId(Long productKindId) {
        return super.selectList("productKind.selectServiceByProductKindId",productKindId);
    }

    @Override
    public List<ProductKind> selectProductKindByProductClassifyId(Long productClassifyId) {
        return super.selectList("productKind.selectProductKindByProductClassifyId",productClassifyId);
    }

    @Override
    public List<ProductKind> selectProductKindListByKeyWord(String keyWord) {
        return super.selectList("productKind.selectProductKindListByKeyWord",keyWord);
    }

    @Override
    public ProductKind selectProductKindByProductKindId(Long productKindId) {
        return super.select("productKind.selectProductKindByProductKindId",productKindId);
    }
}
