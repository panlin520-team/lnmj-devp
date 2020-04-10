package com.lnmj.product.business.impl;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductTypeDisplayService;
import com.lnmj.product.entity.*;
import com.lnmj.product.entity.VO.ProductTypeDisplayVO;
import com.lnmj.product.repository.IProductTypeDisplayDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:28
 * @Description: 商品分类显示service
 */
@Service("productTypeDisplayService")
public class ProductTypeDisplayService implements IProductTypeDisplayService {
    @Resource(name = "productTypeDisplayDaoImpl")
    private IProductTypeDisplayDao productTypeDisplayDao;

    /**
     * @Description 查询分类显示
     * @Param []
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 17:45
     */
    @Transactional
    @Override
    public ResponseResult selectProductTypeDisplayList() {
        List<ProductTypeDisplay> productTypeDisplayList = productTypeDisplayDao.selectProductTypeDisplayList();
        if (productTypeDisplayList.size()!=0) {
            return ResponseResult.success(productTypeDisplayList);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
    }

    /**
     * @Description 新增分类显示
     * @Param [productTypeDisplayVO]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 17:44
     */
    @Transactional
    @Override
    public ResponseResult insertProductTypeDisplay(ProductTypeDisplayVO productTypeDisplayVO) {
        ProductTypeDisplay productTypeDisplay = new ProductTypeDisplay();
        productTypeDisplay.setProductTypeDisplayName(productTypeDisplayVO.getProductTypeDisplayName());
        //描述
        if (!StringUtils.isBlank(productTypeDisplayVO.getProductTypeDisplayDesc())) {
            productTypeDisplay.setProductTypeDisplayDesc(productTypeDisplayVO.getProductTypeDisplayDesc());
        }
        //状态
        if (productTypeDisplayVO.getStatus() != null) {
            productTypeDisplay.setStatus(productTypeDisplayVO.getStatus());
        }
        //创建人
        productTypeDisplay.setCreateOperator(productTypeDisplayVO.getCreateOperator());
        //修改人
        if (productTypeDisplayVO.getModifyOperator() == null) {
            productTypeDisplay.setModifyOperator(productTypeDisplayVO.getCreateOperator());
        }
        return ResponseResult.success(productTypeDisplayDao.insertProductTypeDisplay(productTypeDisplay));
    }

    /**
     * @Description 修改分类显示
     * @Param [productTypeDisplayVO]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 17:44
     */
    @Transactional
    @Override
    public ResponseResult updateProductTypeDisplayById(ProductTypeDisplayVO productTypeDisplayVO) {
        ProductTypeDisplay productTypeDisplay = new ProductTypeDisplay();
        productTypeDisplay.setProductTypeDisplayId(productTypeDisplayVO.getProductTypeDisplayId());
        //名字
        if (!StringUtils.isBlank(productTypeDisplayVO.getProductTypeDisplayName())) {
            productTypeDisplay.setProductTypeDisplayName(productTypeDisplayVO.getProductTypeDisplayName());
        }
        //描述
        if (!StringUtils.isBlank(productTypeDisplayVO.getProductTypeDisplayDesc())) {
            productTypeDisplay.setProductTypeDisplayDesc(productTypeDisplayVO.getProductTypeDisplayDesc());
        }
        //状态
        if (productTypeDisplayVO.getStatus() != null ) {
            productTypeDisplay.setStatus(productTypeDisplayVO.getStatus());
        }
        //修改人
        productTypeDisplay.setModifyOperator(productTypeDisplayVO.getModifyOperator());
        //修改时间
        if (productTypeDisplayVO.getDataChangeLastTime() != null) {
            productTypeDisplay.setDataChangeLastTime(productTypeDisplayVO.getDataChangeLastTime());
        }
        //状态为删除
        if (productTypeDisplay.getStatus()!=null && 0 == productTypeDisplay.getStatus().intValue()) {
            return deleteProductTypeDisplayById(productTypeDisplayVO.getProductTypeDisplayId());
        }
        return ResponseResult.success(productTypeDisplayDao.updateProductTypeDisplay(productTypeDisplay));
    }

    /**
     * @Description 删除分类显示
     * @Param [productTypeDisplayId]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 17:44
     */
    @Transactional
    @Override
    public ResponseResult deleteProductTypeDisplayById(Long productTypeDisplayId) {
        ProductTypeDisplay productTypeDisplay = new ProductTypeDisplay();
        productTypeDisplay.setProductTypeDisplayId(productTypeDisplayId);
        productTypeDisplay.setStatus(0);//设置状态为删除
        //判断此商品显示是否在使用
        List<ProductBrand> productBrandList = productTypeDisplayDao.selectProductBrandByProductTypeDisplayId(productTypeDisplayId);
        List<ProductCategory> productCategoryList = productTypeDisplayDao.selectProductCategoryByProductTypeDisplayId(productTypeDisplayId);
        List<ProductEffect> productEffectList = productTypeDisplayDao.selectProductEffectByProductTypeDisplayId(productTypeDisplayId);
        List<ProductKind> productKindList = productTypeDisplayDao.selectProductKindByProductTypeDisplayId(productTypeDisplayId);
        List<ProductSite> productSiteList = productTypeDisplayDao.selectProductSiteByProductTypeDisplayId(productTypeDisplayId);
        List<ProductDivision> productDivisionList = productTypeDisplayDao.selectProductDivisionByProductTypeDisplayId(productTypeDisplayId);
        if (productBrandList.size()!=0 || productEffectList.size()!=0 || productCategoryList.size()!=0||
            productKindList.size()!=0 || productSiteList.size()!=0 || productDivisionList.size()!=0) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_TYPE_DIAPLAY_IS_USE.getCode(),
                    productTypeDisplayId + " " + ResponseCodeProductTypeEnum.PRODUCT_TYPE_DIAPLAY_IS_USE.getDesc()));
        }
        return ResponseResult.success(productTypeDisplayDao.updateProductTypeDisplay(productTypeDisplay));
    }

}
