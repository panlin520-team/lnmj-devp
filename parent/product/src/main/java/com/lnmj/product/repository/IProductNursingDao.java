package com.lnmj.product.repository;

import com.lnmj.product.entity.ProductNursing;

public interface IProductNursingDao {
    int insertProductNursing(ProductNursing productNursing);

    int deleteProductNursing(Long productNursingId);
}
