package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.*;
import com.lnmj.product.repository.IProductTypeDisplayDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:30
 * @Description:  商品显示dao实现类
 */
@Repository("productTypeDisplayDaoImpl")
public class ProductTypeDisplayDaoImpl extends BaseDao implements IProductTypeDisplayDao {


    @Override
    public int updateProductTypeDisplay(ProductTypeDisplay pProductTypeDisplay) {
        return super.update("productTypeDisplay.updateProductTypeDisplay",pProductTypeDisplay);
    }

    @Override
    public List<ProductTypeDisplay> selectProductTypeDisplayList() {
        return super.selectList("productTypeDisplay.selectProductTypeDisplayList");
    }

    @Override
    public int insertProductTypeDisplay(ProductTypeDisplay pProductTypeDisplay) {
        return super.insert("productTypeDisplay.insertProductTypeDisplay",pProductTypeDisplay);
    }

    @Override
    public int selectProductTypeDisplayById(Long productTypeDisplayId) {
        return super.select("productTypeDisplay.selectProductTypeDisplayById",productTypeDisplayId);
    }

    @Override
    public List<ProductBrand> selectProductBrandByProductTypeDisplayId(Long productTypeDisplayId) {
        return super.selectList("productTypeDisplay.selectProductBrandByProductTypeDisplayId",productTypeDisplayId);
    }

    @Override
    public List<ProductCategory> selectProductCategoryByProductTypeDisplayId(Long productTypeDisplayId) {
        return super.selectList("productTypeDisplay.selectProductCategoryByProductTypeDisplayId",productTypeDisplayId);
    }

    @Override
    public List<ProductEffect> selectProductEffectByProductTypeDisplayId(Long productTypeDisplayId) {
        return super.selectList("productTypeDisplay.selectProductEffectByProductTypeDisplayId",productTypeDisplayId);
    }

    @Override
    public List<ProductSite> selectProductSiteByProductTypeDisplayId(Long productTypeDisplayId) {
        return super.selectList("productTypeDisplay.selectProductSiteByProductTypeDisplayId",productTypeDisplayId);
    }

    @Override
    public List<ProductKind> selectProductKindByProductTypeDisplayId(Long productTypeDisplayId) {
        return super.selectList("productTypeDisplay.selectProductKindByProductTypeDisplayId",productTypeDisplayId);
    }

    @Override
    public List<ProductDivision> selectProductDivisionByProductTypeDisplayId(Long productTypeDisplayId) {
        return super.selectList("productTypeDisplay.selectProductDivisionByProductTypeDisplayId",productTypeDisplayId);
    }

}
