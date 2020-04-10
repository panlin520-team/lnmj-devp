package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.ProductNursing;
import com.lnmj.product.repository.IProductNursingDao;
import org.springframework.stereotype.Repository;

/**
 * @Author: yilihua
 * @Date: 2019/5/5 16:17
 * @Description:
 */
@Repository
public class ProductNursingDaoImpl extends BaseDao implements IProductNursingDao {

    @Override
    public int insertProductNursing(ProductNursing productNursing) {
        return super.insert("productNursing.insertProductNursing",productNursing);
    }

    @Override
    public int deleteProductNursing(Long productNursingId) {
        return super.delete("productNursing.deleteProductNursing",productNursingId);
    }
}
