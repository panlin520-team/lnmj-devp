package com.lnmj.product.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.entity.VO.ProductEffectVO;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/5/14 16:23
 * @Description: 商品功效分类service接口
 */
@Service("iProductEffectService")
public interface IProductEffectService {

    ResponseResult selectProductEffectList(int pageNum, int pageSize, String keyWord);

    ResponseResult selectProductEffectListNoPage();

    ResponseResult insertProductEffect(ProductEffectVO productEffectVO);

    ResponseResult updateProductEffectById(ProductEffectVO productEffectVO);

    ResponseResult deleteProductEffectById(Long productEffectId, String modifyOperator);

    ResponseResult selectProductEffectById(Long productEffectId);
}
