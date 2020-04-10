package com.lnmj.product.business.impl;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductImageService;
import com.lnmj.product.entity.ProductImage;
import com.lnmj.product.repository.IProductImageDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @Author renqingyun
 * @Date: 2019/5/9 18:12
 * @Description:
 */
@Transactional
@Service("productImageService")
public class ProductImageService implements IProductImageService {

    @Resource
    private IProductImageDao productImageDao;


    @Override
    public ResponseResult insertProductImage(ProductImage productImage) {
        return ResponseResult.success(productImageDao.insertProductImage(productImage));
    }
}
