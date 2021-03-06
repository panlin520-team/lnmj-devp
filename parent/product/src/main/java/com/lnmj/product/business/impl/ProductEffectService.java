package com.lnmj.product.business.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ProductTypeDistinguishEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.baseController.FtpFileUploadController;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductEffectService;
import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.ProductEffect;
import com.lnmj.product.entity.ProductKind;
import com.lnmj.product.entity.ServiceProduct;
import com.lnmj.product.entity.VO.ProductEffectVO;
import com.lnmj.product.entity.VO.ProductType;
import com.lnmj.product.repository.IProductEffectDao;
import com.lnmj.product.repository.IProductKindDao;
import com.lnmj.product.repository.IProductTypeDisplayDao;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:28
 * @Description: 商品功效service
 */
@Service("productEffectService")
public class ProductEffectService implements IProductEffectService {
    @Resource
    private IProductEffectDao productEffectDao;
    @Resource
    private FtpFileUploadController ftpFileUploadController;
    @Resource
    private ProductTypeService productTypeService;
    @Resource
    private IProductTypeDisplayDao productTypeDisplayDao;
    @Resource
    private IProductKindDao productKindDao;
    /**
     * @Description 查询商品功效
     * @Param [pageNum, pageSize]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:48
     */
    @Transactional
    @Override
    public ResponseResult selectProductEffectList(int pageNum, int pageSize, String keyWord) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductType> productTypeList  = productTypeService.selectProductType(
                ProductTypeDistinguishEnum.PRODUCT_EFFECT.getCode().longValue(), keyWord);
        if (productTypeList.size()!=0) {
            PageInfo<ProductType> pageInfo = new PageInfo(productTypeList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_EFFECT_NOT_FIND.getCode(),
                ResponseCodeProductTypeEnum.PRODUCT_EFFECT_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectProductEffectListNoPage() {
        List<ProductType> productTypeList  = productTypeService.selectProductType(
                ProductTypeDistinguishEnum.PRODUCT_EFFECT.getCode().longValue(), null);
        if (productTypeList.size()!=0) {
            return ResponseResult.success(productTypeList);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_EFFECT_NOT_FIND.getCode(),
                ResponseCodeProductTypeEnum.PRODUCT_EFFECT_NOT_FIND.getDesc()));
    }

    /**
     * @Description 新增商品功效
     * @Param [productEffectVO]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:47
     */
    @Transactional
    @Override
    public ResponseResult insertProductEffect(ProductEffectVO productEffectVO) {
        //判断商品分类显示是否正常
        Long productTypeDisplayId = productEffectVO.getProductTypeDisplayId();
        if(productTypeDisplayId!=null){
            int j = productTypeDisplayDao.selectProductTypeDisplayById(productTypeDisplayId);
            if (j != 1) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
            }
        }
        //判断商品种类是否正常
        Long productKindId = productEffectVO.getProductKindId();
        if(productKindId!=null){
            ProductKind productKind = productKindDao.selectProductKindByProductKindId(productKindId);//根据id查询，有且仅有一个
            if (productKind==null) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getDesc()));
            }
        }
        //修改人
        String createOperator = productEffectVO.getCreateOperator();
        if (StringUtils.isBlank(productEffectVO.getModifyOperator())){
            productEffectVO.setModifyOperator(createOperator);
        }
        ProductEffect productEffect = new ProductEffect();
        try {
            BeanUtils.copyProperties(productEffect,productEffectVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //图片
        if(productEffectVO.getMultipartFile()!=null && !productEffectVO.getMultipartFile().isEmpty()){
            try {
                ResponseResult responseResult = ftpFileUploadController.uploadImg(
                        productEffectVO.getMultipartFile(), productEffectVO.getImageType());
                String imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                Integer imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                Integer imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                productEffect.setImageHeight(imageHeight);
                productEffect.setImageUrl(imageUrl);
                productEffect.setImageWidth(imageWidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productEffectDao.insertProductEffect(productEffect);
        return ResponseResult.success(productEffect);
    }

    /**
     * @Description 修改商品功效
     * @Param [productEffectVO]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:47
     */
    @Transactional
    @Override
    public ResponseResult updateProductEffectById(ProductEffectVO productEffectVO) {
        //判断修改的商品品牌信息是否合理
        //判断商品分类显示是否正常
        Long productTypeDisplayId = productEffectVO.getProductTypeDisplayId();
        if(productTypeDisplayId!=null){
            int j = productTypeDisplayDao.selectProductTypeDisplayById(productTypeDisplayId);
            if (j != 1) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
            }
        }
        //判断商品种类是否正常
        Long productKindId = productEffectVO.getProductKindId();
        if(productKindId!=null){
            ProductKind productKind = productKindDao.selectProductKindByProductKindId(productKindId);//根据id查询，有且仅有一个
            if (productKind==null) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getDesc()));
            }
        }
        //判断商品功效是否存在
        Long productEffectId = productEffectVO.getProductEffectId();
        ProductEffect productEffecttemp = productEffectDao.selectProductEffectByProductEffectId(productEffectId);//根据id查询，有且仅有一个
        if (productEffecttemp==null) {    //不存在返回错误
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_EFFFECT.getCode(),
                    productEffectId+" " + ResponseCodeProductTypeEnum.NOT_PRODUCT_EFFFECT.getDesc()));
        }
        ProductEffect productEffect = new ProductEffect();
        try {
            BeanUtils.copyProperties(productEffect,productEffectVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //状态为删除
        if (productEffect.getStatus()!=null && 0 == productEffect.getStatus().intValue()) {
            return deleteProductEffectById(productEffect.getProductEffectId(),productEffect.getModifyOperator());
        }
        //如果有图片
        if(productEffectVO.getMultipartFile()!=null && !productEffectVO.getMultipartFile().isEmpty()){
            try {
                //判断图片类型是否正确
                if(StringUtils.isBlank(productEffectVO.getImageType())){
                    return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getCode(),
                            ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getDesc()));
                }
                //先删除图片
                if(!StringUtils.isBlank(productEffecttemp.getImageUrl())) {
                    String imageName = productEffecttemp.getImageUrl().split("\\/")[2];
                    ftpFileUploadController.deleteImg(imageName, productEffecttemp.getImageType());
                }
                //存储图片
                ResponseResult responseResult = ftpFileUploadController.uploadImg(
                        productEffectVO.getMultipartFile(), productEffectVO.getImageType());
                String imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                Integer imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                Integer imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                productEffect.setImageHeight(imageHeight);
                productEffect.setImageUrl(imageUrl);
                productEffect.setImageWidth(imageWidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productEffectDao.updateProductEffectById(productEffect);
        return ResponseResult.success(productEffect);
    }

    /**
     * @Description 删除商品功效
     * @Param [productEffectId]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:46
     */
    @Transactional
    @Override
    public ResponseResult deleteProductEffectById(Long productEffectId, String modifyOperator) {
        //判断商品功效是否存在
        ProductEffect productEffecttemp = productEffectDao.selectProductEffectByProductEffectId(productEffectId);//根据id查询，有且仅有一个
        if (productEffecttemp==null) {    //不存在返回错误
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_EFFFECT.getCode(),
                    productEffectId+" " + ResponseCodeProductTypeEnum.NOT_PRODUCT_EFFFECT.getDesc()));
        }
        //判断此商品功效是否在使用
        List<Product> product = productEffectDao.selectProductByProductEffectId(productEffectId);
        if (product.size()!=0) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_EFFECT_IS_USE.getCode(),
                    productEffectId + " " + ResponseCodeProductTypeEnum.PRODUCT_EFFECT_IS_USE.getDesc()+",商品如下："+product));
        }
        List<ServiceProduct> serviceProduct = productEffectDao.selectServiceByProductEffectId(productEffectId);
        if (serviceProduct.size()!=0) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_EFFECT_IS_USE.getCode(),
                    productEffectId + " " + ResponseCodeProductTypeEnum.PRODUCT_EFFECT_IS_USE.getDesc()+",服务如下："+serviceProduct));
        }
        //先删除图片
        if(!StringUtils.isBlank(productEffecttemp.getImageUrl())){
            String  imageName = productEffecttemp.getImageUrl().split("\\/")[2];
            try {
                ftpFileUploadController.deleteImg(imageName,productEffecttemp.getImageType());
            } catch (Exception e) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getCode(),
                        ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getDesc()));
            }
        }
        return ResponseResult.success(productEffectDao.deleteProductEffect(productEffectId));
    }

    @Override
    public ResponseResult selectProductEffectById(Long productEffectId) {
        if (productEffectId==null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.EFFFECT_ID_NULL.getCode(), ResponseCodeProductTypeEnum.EFFFECT_ID_NULL.getDesc()));
        }
        return ResponseResult.success(productEffectDao.selectProductEffectByProductEffectId(productEffectId));
    }

}
