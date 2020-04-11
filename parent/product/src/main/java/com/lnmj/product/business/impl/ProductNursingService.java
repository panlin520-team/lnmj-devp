package com.lnmj.product.business.impl;

import com.alibaba.fastjson.JSONObject;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductNursingService;
import com.lnmj.product.entity.ProductNursing;
import com.lnmj.product.repository.IProductNursingDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: renqingyun
 * @Date: 2019/5/5 16:13
 * @Description: 商品Service实现类
 */

@Transactional
@Service("productNursingService")
public class ProductNursingService implements IProductNursingService {

    @Resource
    private IProductNursingDao productNursingDao;

    @Override
    public ResponseResult insertProductNursing(ProductNursing productNursing) {
        int i = productNursingDao.insertProductNursing(productNursing);
        if (i == 0) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.ADD_PRODUCTNURSING_FAIL.getCode(), ResponseCodeProductEnum.ADD_PRODUCTNURSING_FAIL.getDesc()));
        }
        JSONObject object = new JSONObject();
        object.put("productNursingId",productNursing.getProductNursingId());
        return ResponseResult.success(object);
    }

    @Override
    public ResponseResult deleteProductNursing(Long productNursingId) {
        int i = productNursingDao.deleteProductNursing(productNursingId);
        if (i == 0) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.ADD_PRODUCTNURSING_FAIL.getCode(), ResponseCodeProductEnum.ADD_PRODUCTNURSING_FAIL.getDesc()));
        }
        return ResponseResult.success();
    }
}
