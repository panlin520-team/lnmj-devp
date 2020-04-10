package com.lnmj.system.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.entity.MakerProduct;
import com.lnmj.system.entity.MakerProductDetail;
import com.lnmj.system.entity.VO.MakerProductVO;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/8/26 10:17
 * @Description: 创客商品
 */
@Service("iMakerProductService")
public interface IMakerProductService {

     ResponseResult insertMakerProduct(MakerProductVO makerProduct);

     ResponseResult updateMakerProduct(MakerProductVO makerProduct);

     ResponseResult deleteMakerProduct(Long makerProductId, String modifyOperator);

     ResponseResult selectMakerProductByCondition(int pageNum, int pageSize, MakerProduct makerProduct);

     ResponseResult selectMakerProductList(int pageNum, int pageSize);

    ResponseResult selectMakerProductDetailByCondition(int pageNum, int pageSize, MakerProductDetail makerProductDetail);

    ResponseResult deleteMakerProductDetail(Long makerProductDetailId, String modifyOperator);
}
