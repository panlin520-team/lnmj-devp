package com.lnmj.product.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.entity.ProductPromotion;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/5/5 16:10
 * @Description: 产品Service类
 */
@Service("iProductPromotionService")
public interface IProductPromotionService {

    ResponseResult selectProductPromotionList(int pageNum, int pageSize, String keyword);

    /*ResponseResult insertProductPromotion(ProductPromotion productPromotion);

    ResponseResult deleteProductPromotion(Long productPromotionId);

    ResponseResult updateProductPromotionById(ProductPromotion productPromotion);*/

}
