package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.ProductActivityImage;
import com.lnmj.product.entity.ProductImage;
import com.lnmj.product.repository.IProductImageDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/5/5 16:17
 * @Description:
 */
@Repository
public class ProductImageDaoImpl extends BaseDao implements IProductImageDao {

    @Override
    public int insertProductImage(ProductImage productImage) {
        return super.insert("productImage.insertProductImage",productImage);
    }

    @Override
    public List<ProductImage> selectProductImageByIds(Map map) {
        return super.selectList("productImage.selectProductImageById",map);
    }

    @Override
    public List<ProductActivityImage> selectProductActivityImageByIds(Map ids) {
        return super.selectList("productImage.selectProductActivityImageByIds",ids);
    }
}
