package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.ProductNumber;
import com.lnmj.product.repository.IProductNumberDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/23 16:31
 * @Description: 商品入库后数量变化
 */
@Repository
public class ProductNumberDaoImpl extends BaseDao implements IProductNumberDao {


    @Override
    public List<ProductNumber> selectProductNumberList() {
        return super.selectList("ProductNumber.selectProductNumberList");
    }

    @Override
    public List<ProductNumber> selectProductNumberByCondition(ProductNumber productNumber) {
        return super.selectList("ProductNumber.selectProductNumberByCondition",productNumber);
    }

    @Override
    public List<ProductNumber> selectProductNumberListByProduct(Map map) {
        return super.selectList("ProductNumber.selectProductNumberListByProduct",map);
    }

    @Override
    public int insertProductNumber(ProductNumber productNumber) {
        return super.insert("ProductNumber.insertProductNumber",productNumber);
    }

    @Override
    public int updateProductNumberByID(ProductNumber productNumber) {
        return super.update("ProductNumber.updateProductNumberByID",productNumber);
    }

    @Override
    public int deleteProductNumberByID(ProductNumber productNumber) {
        return super.delete("ProductNumber.deleteProductNumberByID",productNumber);
    }
}
