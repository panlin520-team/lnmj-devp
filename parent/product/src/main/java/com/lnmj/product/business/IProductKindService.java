package com.lnmj.product.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.entity.VO.ProductKindVO;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 10:24
 * @Description: 商品种类service接口
 */
@Service("iProductKindService")
public interface IProductKindService {
    ResponseResult selectProductKindList(int pageNum, int pageSize, String keyWord);

    ResponseResult insertProductKind(ProductKindVO pProductKind);

    ResponseResult updateProductKindById(ProductKindVO pProductKind);

    ResponseResult deleteProductKindById(Long productKindId, String modifyOperator);

    ResponseResult selectProductCategoryByProductKindId(Long productKindId);

    ResponseResult selectProductEffectByProductKindId(Long productKindId);

    ResponseResult selectProductBrandByProductKindId(Long productKindId);

    ResponseResult deleteProductBrandByProductKindId(Long productKindId);

    ResponseResult deleteProductCategoryByProductKindId(Long productKindId);

    ResponseResult deleteProductEffectByProductKindId(Long productKindId);

    ResponseResult selectProductKindByProductClassifyId(Long productClassifyId);

    ResponseResult selectProductTypeByProductKindId(Long productKindId);

    ResponseResult deleteProductTypeByProductKindId(Long productKindId);

    ResponseResult selectProductKind();

    ResponseResult selectProductKindById(Long productKindId);
}
