package com.lnmj.product.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.entity.ProductPromotionType;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/5/5 16:10
 * @Description: 产品Service类
 */
@Service("iProductPromotionTypeService")
public interface IProductPromotionTypeService {

    ResponseResult selectProductPromotionList(int pageNum, int pageSize, String keyword);

    ResponseResult insertProductPromotion(ProductPromotionType productPromotionType);

    ResponseResult deleteProductPromotion(Long productPromotionTpyeId);

    ResponseResult updateProductPromotionById(ProductPromotionType productPromotionType);

}
