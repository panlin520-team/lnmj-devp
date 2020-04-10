package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.ProductActivityImage;
import com.lnmj.product.entity.VO.ProductVO;
import com.lnmj.product.repository.IProductActivityImageDao;
import com.lnmj.product.repository.IProductDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/5/5 16:17
 * @Description:
 */
@Repository
public class ProductActivityImageDaoImpl extends BaseDao implements IProductActivityImageDao {

    @Override
    public int insertProductActivity(ProductActivityImage productActivityImage) {
        return super.insert("productActivityImage.insertProductActivityImage",productActivityImage);
    }

    @Override
    public List<ProductActivityImage> selectProductActivityImageByIds(Map ids) {
        return super.selectList("productActivityImage.selectActivityProductImageByIds",ids);
    }
}
