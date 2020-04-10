package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.ProductBrand;
import com.lnmj.product.entity.ServiceProduct;
import com.lnmj.product.repository.IProductBrandDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:30
 * @Description: 商品种类dao实现类
 */
@Repository
public class ProductBrandDaoImpl extends BaseDao implements IProductBrandDao {


    @Override
    public int insertProductBrand(ProductBrand productBrand) {
        return super.insert("productBrand.insertProductBrand",productBrand);
    }

    @Override
    public int updateProductBrandById(ProductBrand productBrand) {
        return super.update("productBrand.updateProductBrand",productBrand);
    }

    @Override
    public int deleteProductBrand(Long productBrandId) {
        return super.delete("productBrand.deleteProductBrand",productBrandId);
    }

    @Override
    public List<Product> selectProductByProductBrandId(Long productBrandId) {
        return super.selectList("productBrand.selectProductByProductBrandId",productBrandId);
    }
    @Override
    public List<ServiceProduct> selectServiceByProductBrandId(Long productBrandId) {
        return super.selectList("productBrand.selectServiceByProductBrandId",productBrandId);
    }

    @Override
    public List<ProductBrand> selectProductBrandList() {
        return super.selectList("productBrand.selectProductBrandList");
    }

    @Override
    public List<ProductBrand> selectProductBrandListByKeyWord(String keyWord) {
        return super.selectList("productBrand.selectProductBrandByKeyWord",keyWord);
    }

    @Override
    public ProductBrand selectProductBrandByProductBrandId(Long productBrandId) {
        return super.select("productBrand.selectProductBrandByProductBrandId",productBrandId);
    }

    @Override
    public ProductBrand selectBrandById(Long productBrandId) {
        return super.select("productBrand.selectBrandById",productBrandId);
    }


}
