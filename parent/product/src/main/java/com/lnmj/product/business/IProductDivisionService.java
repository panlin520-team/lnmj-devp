package com.lnmj.product.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.entity.VO.ProductDivisionVO;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/8/14 14:21
 * @Description:  商品分类专区接口
 */
@Service("iProductDivisionService")
public interface IProductDivisionService {

    ResponseResult selectProductDivisionList(int pageNum, int pageSize);

    ResponseResult insertProductDivision(ProductDivisionVO productDivisionVo);

    ResponseResult updateProductDivision(ProductDivisionVO productDivisionVO);

    ResponseResult deleteProductDivision(Long productDivisionId, String modifyOperator);

    ResponseResult selectProductDivisionByProductClassifyId(Long productClassifyId);

    ResponseResult selectProductDivisionById(Long productDivisionId);
}
