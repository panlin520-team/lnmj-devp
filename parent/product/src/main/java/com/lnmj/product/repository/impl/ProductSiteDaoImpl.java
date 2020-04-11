package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.ProductSite;
import com.lnmj.product.repository.IProductSiteDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:30
 * @Description: 商品种类dao实现类
 */
@Repository
public class ProductSiteDaoImpl extends BaseDao implements IProductSiteDao {


    @Override
    public List<ProductSite> selectProductSiteList() {
        return super.selectList("productSite.selectProductSiteList");
    }

    @Override
    public int insertProductSite(ProductSite productSite) {
        return super.insert("productSite.insertProductSite",productSite);
    }

    @Override
    public int updateProductSiteById(ProductSite productSite) {
        return super.update("productSite.updateProductSite",productSite);
    }

    @Override
    public int deleteProductSite(Long productSiteId) {
        return super.delete("productSite.deleteProductSite",productSiteId);
    }

    @Override
    public List<Product> selectProductByProductSiteId(Long productSiteId) {
        return super.selectList("productSite.selectProductByProductSiteId",productSiteId);
    }

    @Override
    public List<ProductSite> selectProductSiteListByKeyWord(String keyWord) {
        return super.selectList("productSite.selectProductSiteByKeyWord",keyWord);
    }

    @Override
    public ProductSite selectProductSiteByProductSiteId(Long productSiteId) {
        return super.select("productSite.selectProductSiteByProductSiteId",productSiteId);
    }

    @Override
    public List<ProductSite> selectProductSiteByProductClassifyId(Long productClassifyId) {
        return super.selectList("productSite.selectProductSiteByProductClassifyId",productClassifyId);
    }
}
