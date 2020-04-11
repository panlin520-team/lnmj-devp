package com.lnmj.product.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.entity.VO.ProductTypeVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 10:24
 * @Description: 商品分类service接口
 */
@Service("iProductTypeService")
public interface IProductTypeService {
    ResponseResult selectProductTypeList(int pageNum, int pageSize);

    ResponseResult selectProductTypeByKeyWord(int pageNum, int pageSize, String keyWord);

    ResponseResult selectProductTypeByType(int pageNum, int pageSize, Long productTypeCategory);

    ResponseResult insertProductType(ProductTypeVO productTypeVO);

    ResponseResult deleteProductType(Long typeId, Long productTypeId, String modifyOperator);

    ResponseResult updateProductType(ProductTypeVO productTypeVO);

    ResponseResult exportProductType(HttpServletRequest req, HttpServletResponse resp);

    ResponseResult selectProductTypeByProductClassifyId(Long productClassifyId);

    ResponseResult selectProductTypeList();

    ResponseResult selectProductTypeKind();
}
