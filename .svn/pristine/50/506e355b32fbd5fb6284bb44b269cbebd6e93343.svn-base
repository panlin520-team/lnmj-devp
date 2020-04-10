package com.lnmj.product.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.entity.VO.ProductSiteVO;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/5/14 16:18
 * @Description: 商品产地service接口
 */
@Service("iProductSiteService")
public interface IProductSiteService {
    ResponseResult selectProductSiteByProductClassifyId(Long productClassifyId);

    ResponseResult selectProductSiteList(int pageNum, int pageSize);

    ResponseResult insertProductSite(ProductSiteVO productSiteVO);

    ResponseResult updateProductSiteById(ProductSiteVO productSiteVO);

    ResponseResult deleteProductSiteById(Long productSiteId, String modifyOperator);

    ResponseResult selectProductSiteById(Long productSiteId);

}
