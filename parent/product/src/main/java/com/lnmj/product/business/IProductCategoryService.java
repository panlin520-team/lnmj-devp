package com.lnmj.product.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.entity.VO.ProductCategoryVO;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 10:24
 * @Description: 商品分类品类service接口
 */
@Service("iProductCategoryService")
public interface IProductCategoryService {

    ResponseResult selectProductCategoryList(int pageNum, int pageSize, String keyWord);

    ResponseResult selectProductCategoryListNoPage();

    ResponseResult insertProductCategory(ProductCategoryVO productCategoryVO);

    ResponseResult updateProductCategoryById(ProductCategoryVO productCategoryVO);

    ResponseResult deleteProductCategoryById(Long productTypeCategoryId, String modifyOperator);

    ResponseResult selectProductCategoryById(Long productTypeCategoryId);
}
