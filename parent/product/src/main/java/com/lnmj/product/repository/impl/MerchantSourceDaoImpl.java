package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.Merchantsource;
import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.ProductBrand;
import com.lnmj.product.entity.ServiceProduct;
import com.lnmj.product.repository.IMerchantSourceDao;
import com.lnmj.product.repository.IProductBrandDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:30
 * @Description: 商品种类dao实现类
 */
@Repository
public class MerchantSourceDaoImpl extends BaseDao implements IMerchantSourceDao {


    @Override
    public List<Merchantsource> selectMerchantSourceList() {
        return super.selectList("merchantsource.selectMerchantSourceList");
    }
}
