package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.ProductDivision;
import com.lnmj.product.entity.ProductKind;
import com.lnmj.product.repository.IProductDivisionDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/8/14 15:16
 * @Description: 商品专区dao
 */
@Repository
public class ProductDivisionDaoImpl extends BaseDao implements IProductDivisionDao {

    @Override
    public int insertProductDivision(ProductDivision productDivision) {
        return super.insert("productDivision.insertProductDivision",productDivision);
    }

    @Override
    public int updateProductDivision(ProductDivision productDivision) {
        return super.update("productDivision.updateProductDivision",productDivision);
    }

    @Override
    public int deleteProductDivision(Long productDivisionId) {
        return super.delete("productDivision.deleteProductDivision",productDivisionId);
    }

    @Override
    public ProductDivision selectProductDivisionByProductDivisionId(Long productDivisionId) {
        return super.select("productDivision.selectProductDivisionByProductDivisionId",productDivisionId);
    }

    @Override
    public List<ProductDivision> selectProductDivisionList() {
        return super.selectList("productDivision.selectProductDivisionList");
    }

    @Override
    public List<ProductDivision> selectProductDivisionListByKeyWord(String keyWord) {
        return super.selectList("productDivision.selectProductDivisionListByKeyWord",keyWord);
    }

    @Override
    public List<ProductDivision> selectProductDivisionByProductClassifyId(Long productClassifyId) {
        return super.selectList("productDivision.selectProductDivisionByProductClassifyId",productClassifyId);
    }
}
