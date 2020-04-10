package com.lnmj.system.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.system.entity.MakerProduct;
import com.lnmj.system.entity.MakerProductDetail;
import com.lnmj.system.repository.IMakerProductDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/8/24 16:15
 * @Description: 创客产品
 */
@Repository
public class MakerProductDaoImpl extends BaseDao implements IMakerProductDao {


    @Override
    public List<MakerProductDetail> selectMakerProductDetail() {
        return super.selectList("makerProduct.selectMakerProductDetail");
    }

    @Override
    public List<MakerProductDetail> selectMakerProductDetailByCondition(MakerProductDetail makerProductDetail) {
        return super.selectList("makerProduct.selectMakerProductDetailByCondition",makerProductDetail);
    }

    @Override
    public int insertMakerProductDetail(MakerProductDetail makerProductDetail) {
        return super.insert("makerProduct.insertMakerProductDetail",makerProductDetail);
    }

    @Override
    public int updateMakerProductDetail(MakerProductDetail makerProductDetail) {
        return super.update("makerProduct.updateMakerProductDetail",makerProductDetail);
    }

    @Override
    public int deleteMakerProductDetail(MakerProductDetail makerProductDetail) {
        return super.update("makerProduct.deleteMakerProductDetail",makerProductDetail);
    }

    @Override
    public List<MakerProduct> selectMakerProductList() {
        return super.selectList("makerProduct.selectMakerProductList");
    }

    @Override
    public List<MakerProduct> selectMakerProductByCondition(MakerProduct makerProduct) {
        return super.selectList("makerProduct.selectMakerProductByCondition",makerProduct);
    }

    @Override
    public int insertMakerProduct(MakerProduct makerProduct) {
        return super.insert("makerProduct.insertMakerProduct",makerProduct);
    }

    @Override
    public int updateMakerProduct(MakerProduct makerProduct) {
        return super.update("makerProduct.updateMakerProduct",makerProduct);
    }

    @Override
    public int deleteMakerProduct(MakerProduct makerProduct) {
        return super.update("makerProduct.deleteMakerProduct",makerProduct);
    }

    @Override
    public int deleteMakerProductDetailByCode(MakerProductDetail makerProductDetail) {
        return super.update("makerProduct.deleteMakerProductDetailByCode",makerProductDetail);
    }
}
