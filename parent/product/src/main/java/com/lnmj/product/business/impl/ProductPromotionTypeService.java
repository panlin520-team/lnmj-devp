package com.lnmj.product.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductPromotionTypeService;
import com.lnmj.product.entity.ProductPromotionType;
import com.lnmj.product.repository.IProductPromotionTypeDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Transactional
@Service("productPromotionTypeService")
public class ProductPromotionTypeService implements IProductPromotionTypeService {

    @Resource
    private IProductPromotionTypeDao productPromotionDao;

    @Override
    public ResponseResult selectProductPromotionList(int pageNum, int pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductPromotionType> result;
        //keyword不为空就调根据关键字查询
        if (StringUtils.isNotBlank(keyword)) {
            result = productPromotionDao.selectProductPromotionByKeyWord(keyword);
        } else {
            result = productPromotionDao.selectProductPromotionList();
        }
        if (result != null && result.size() > 0) {
            PageInfo<ProductPromotionType> pageInfo = new PageInfo<>(result);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTPROMOTIONINFO_NOT_FOUND.getCode(), ResponseCodeProductEnum.PRODUCTPROMOTIONINFO_NOT_FOUND.getDesc()));
    }

    @Override
    public ResponseResult insertProductPromotion(ProductPromotionType productPromotionType) {
        String promotionName = productPromotionType.getPromotionName();
        int count = productPromotionDao.checkPromotionName(promotionName);
        if (count > 0) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTPROMOTIONNAME_ISEXIST.getCode(), ResponseCodeProductEnum.PRODUCTPROMOTIONNAME_ISEXIST.getDesc()));
        }
        return ResponseResult.success(productPromotionDao.insertProductPromotion(productPromotionType));
    }

    @Override
    public ResponseResult deleteProductPromotion(Long productPromotionTpyeId) {
        return ResponseResult.success(productPromotionDao.deleteProductPromotion(productPromotionTpyeId));
    }

    @Override
    public ResponseResult updateProductPromotionById(ProductPromotionType productPromotionType) {
        if (StringUtils.isBlank(productPromotionType.getPromotionName())) {
            //非空判断
            return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTNAME_ISNULL.getCode(), ResponseCodeProductEnum.PRODUCTNAME_ISNULL.getDesc()));
        }

        int count = productPromotionDao.checkPromotionName(productPromotionType.getPromotionName());
        if (count > 0) {
            //是否存在判断
            return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTPROMOTIONNAME_ISEXIST.getCode(), ResponseCodeProductEnum.PRODUCTPROMOTIONNAME_ISEXIST.getDesc()));
        }

        return ResponseResult.success(productPromotionDao.updateProductPromotionById(productPromotionType));
    }
}
