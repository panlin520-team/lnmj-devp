package com.lnmj.product.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.entity.VO.ProductBrandVO;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/5/14 16:22
 * @Description:  商品品牌service接口
 */
@Service("iProductBrandService")
public interface IProductBrandService {
    ResponseResult selectProductBrandList(int pageNum, int pageSize, String keyWord);
    ResponseResult selectProductBrandListNoPage();
    ResponseResult insertProductBrand(ProductBrandVO productBrandVO);
    ResponseResult updateProductBrandById(ProductBrandVO productBrandVO);
    ResponseResult deleteProductBrandById(Long productBrandId, String modifyOperator);
    ResponseResult selectBrandById(Long productBrandId);
}
