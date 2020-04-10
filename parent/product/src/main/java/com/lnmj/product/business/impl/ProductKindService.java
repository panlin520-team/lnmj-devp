package com.lnmj.product.business.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ProductTypeDistinguishEnum;
import com.lnmj.common.Enum.ProductTypeEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.baseController.FtpFileUploadController;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductKindService;
import com.lnmj.product.entity.*;
import com.lnmj.product.entity.VO.ProductKindVO;
import com.lnmj.product.entity.VO.ProductType;
import com.lnmj.product.repository.IProductKindDao;
import com.lnmj.product.repository.IProductTypeDisplayDao;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:28
 * @Description: 商品种类service
 */
@Service("productKindService")
public class ProductKindService implements IProductKindService {
    @Resource
    private IProductKindDao productKindDao;
    @Resource
    private FtpFileUploadController ftpFileUploadController;
    @Resource
    private ProductTypeService productTypeService;
    @Resource
    private IProductTypeDisplayDao productTypeDisplayDao;

    /**
     *@Description 根据商品类型查询商品种类
     *@Param [productClassifyId]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/5/17
     *@Time 15:04
     */
    @Override
    public ResponseResult selectProductKindByProductClassifyId(Long productClassifyId) {
        //判断商品类型
        if(productClassifyId.intValue() == ProductTypeEnum.PRODUCT.getCode()|| productClassifyId.intValue()== ProductTypeEnum.SERVICE.getCode()){
            List<ProductKind> productKindList = productKindDao.selectProductKindByProductClassifyId(productClassifyId);
            if(productKindList.size()!=0){
                return ResponseResult.success(productKindList);
            }
        }else{
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getDesc()+productClassifyId));
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_NOT_FIND.getCode(),
                ResponseCodeProductTypeEnum.PRODUCT_KIND_NOT_FIND.getDesc()));
    }
    /**
    *@Description 根据商品种类id查询商品品牌，商品品类，商品功效
    *@Param [productKindId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/21
    *@Time 11:27
    */
    @Override
    public ResponseResult selectProductTypeByProductKindId(Long productKindId){
        ResponseResult responseResultB = selectProductBrandByProductKindId(productKindId);
        ResponseResult responseResultK = selectProductCategoryByProductKindId(productKindId);
        ResponseResult responseResultE = selectProductEffectByProductKindId(productKindId);
        ArrayList<Object> result = new ArrayList<>();
        result.add(responseResultB.getResult());
        result.add(responseResultE.getResult());
        result.add(responseResultK.getResult());
        return ResponseResult.success(result);
    }

    /**
     *@Description 根据商品种类id删除商品品牌，商品品类，商品功效
     *@Param [productKindId]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/5/21
     *@Time 11:27
     */
    @Override
    public ResponseResult deleteProductTypeByProductKindId(Long productKindId){
        deleteProductBrandByProductKindId(productKindId);
        deleteProductCategoryByProductKindId(productKindId);
        deleteProductEffectByProductKindId(productKindId);
        return ResponseResult.success();
    }

    /**
    *@Description 根据商品种类id查询商品品类
    *@Param [productKindId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/17
    *@Time 16:17
    */
    @Override
    public ResponseResult selectProductCategoryByProductKindId(Long productKindId){
        List<ProductCategory> productCategoryList = productKindDao.selectProductCategoryByProductKindId(productKindId);
        if(productCategoryList.size()!=0){
            return ResponseResult.success(productCategoryList);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_CATEGORY_NOT_FIND.getCode(),
                ResponseCodeProductTypeEnum.PRODUCT_CATEGORY_NOT_FIND.getDesc()));
    }
    /**
    *@Description 根据商品种类id查询商品功效
    *@Param [productKindId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/17
    *@Time 16:17
    */
    @Override
    public ResponseResult selectProductEffectByProductKindId(Long productKindId){
        List<ProductEffect> productEffectList = productKindDao.selectProductEffectByProductKindId(productKindId);
        if(productEffectList.size()!=0){
            return ResponseResult.success(productEffectList);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_EFFECT_NOT_FIND.getCode(),
                ResponseCodeProductTypeEnum.PRODUCT_EFFECT_NOT_FIND.getDesc()));
    }
    /**
    *@Description 根据商品种类id查询商品品牌
    *@Param [productKindId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/17
    *@Time 16:17
    */
    @Override
    public ResponseResult selectProductBrandByProductKindId(Long productKindId){
        List<ProductBrand> productBrandList = productKindDao.selectProductBrandByProductKindId(productKindId);
        if(productBrandList.size()!=0){
            return ResponseResult.success(productBrandList);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_BRAND_NOT_FIND.getCode(),
                ResponseCodeProductTypeEnum.PRODUCT_BRAND_NOT_FIND.getDesc()));
    }
    /**
    *@Description  根据商品种类id删除商品品牌
    *@Param [productKindId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/17
    *@Time 16:08
    */
    @Override
    public ResponseResult deleteProductBrandByProductKindId(Long productKindId){
        //根据种类id查询到所有的品牌数据
        List<ProductBrand> productBrandList = productKindDao.selectProductBrandByProductKindId(productKindId);
        if(productBrandList==null){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_BRAND.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_BRAND.getDesc()));
        }
        //删除商品品牌图片
        for(int i=0;i<productBrandList.size();i++){
            //图片是否存在
            if(!StringUtils.isBlank(productBrandList.get(i).getImageUrl())){
                String  imageName = productBrandList.get(i).getImageUrl().split("\\/")[2];
                try {
                    ftpFileUploadController.deleteImg(imageName,productBrandList.get(i).getImageType());
                } catch (Exception e) {
                    return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getCode(),
                            ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getDesc()));
                }
            }
        }
        return ResponseResult.success(productKindDao.deleteProductBrandByProductKindId(productKindId));
    }
    /**
    *@Description 根据商品种类Id删除商品品类
    *@Param [productKindId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/17
    *@Time 16:18
    */
    @Override
    public ResponseResult deleteProductCategoryByProductKindId(Long productKindId){
        //根据种类id查询到所有的品类数据
        List<ProductCategory> productCategoryList = productKindDao.selectProductCategoryByProductKindId(productKindId);
        if(productCategoryList==null){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_BRAND.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_BRAND.getDesc()));
        }
        //删除商品品牌图片
        for(int i=0;i<productCategoryList.size();i++){
            if(StringUtils.isBlank(productCategoryList.get(i).getImageUrl())){
                String  imageName = productCategoryList.get(i).getImageUrl().split("\\/")[2];
                try {
                    ftpFileUploadController.deleteImg(imageName,productCategoryList.get(i).getImageType());
                } catch (Exception e) {
                    return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getCode(),
                            ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getDesc()));
                }
            }
        }
        return ResponseResult.success(productKindDao.deleteProductCategoryByProductKindId(productKindId));
    }
    /**
    *@Description 根据商品种类id删除商品功效
    *@Param [productKindId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/17
    *@Time 16:18
    */
    @Override
    public ResponseResult deleteProductEffectByProductKindId(Long productKindId){
        //根据种类id查询到所有的功效数据
        List<ProductEffect> productEffectList = productKindDao.selectProductEffectByProductKindId(productKindId);
        if(productEffectList==null){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_BRAND.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_BRAND.getDesc()));
        }
        //删除商品品牌图片
        for(int i=0;i<productEffectList.size();i++){
            if(StringUtils.isBlank(productEffectList.get(i).getImageUrl())){
                String  imageName = productEffectList.get(i).getImageUrl().split("\\/")[2];
                try {
                    ftpFileUploadController.deleteImg(imageName,productEffectList.get(i).getImageType());
                } catch (Exception e) {
                    return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getCode(),
                            ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getDesc()));
                }
            }
        }
        return ResponseResult.success(productKindDao.deleteProductEffectByProductKindId(productKindId));
    }
    /***
    *@Description 查询商品种类
    *@Param []
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/7/11
    *@Time 11:48
    */
    @Transactional
    @Override
    public ResponseResult selectProductKind() {
        List<ProductType> productTypeList  = productTypeService.selectProductType(
                ProductTypeDistinguishEnum.PRODUCT_KIND.getCode().longValue(), null);
        if (productTypeList.size()!=0) {
            return ResponseResult.success(productTypeList);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_NOT_FIND.getCode(),
                ResponseCodeProductTypeEnum.PRODUCT_KIND_NOT_FIND.getDesc()));
    }

    /**
     * @Description 商品种类分页显示
     * @Param [pageNum, pageSize]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:48
     */
    @Transactional
    @Override
    public ResponseResult selectProductKindList(int pageNum, int pageSize, String keyWord) {
        PageHelper.startPage(pageNum, pageSize);

        List<ProductType> productTypeList  = productTypeService.selectProductType(
                ProductTypeDistinguishEnum.PRODUCT_KIND.getCode().longValue(), keyWord);
        if (productTypeList.size()!=0) {
            PageInfo<ProductType> pageInfo = new PageInfo(productTypeList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_NOT_FIND.getCode(),
                ResponseCodeProductTypeEnum.PRODUCT_KIND_NOT_FIND.getDesc()));
    }

    /**
     * @Description 新增商品种类
     * @Param [productKindVO]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:47
     */
    @Transactional
    @Override
    public ResponseResult insertProductKind(ProductKindVO productKindVO) {
        //判断商品类型是否正常
        Long productClassifyId = productKindVO.getProductClassifyId();
        Boolean flag = false;   //标志商品类型是否正常
        for(ProductTypeEnum productTypeEnum : ProductTypeEnum.values()){
            if(productClassifyId.intValue() == productTypeEnum.getCode()){
                flag = true;
            }
        }
        if(!flag){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getDesc()+productClassifyId));
        }
        //判断商品分类显示是否正常
        Long productTypeDisplayId = productKindVO.getProductTypeDisplayId();
        if(productTypeDisplayId!=null){
            int j = productTypeDisplayDao.selectProductTypeDisplayById(productTypeDisplayId);
            if (j != 1) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
            }
        }
        //修改人
        String createOperator = productKindVO.getCreateOperator();
        if (StringUtils.isBlank(productKindVO.getModifyOperator())){
            productKindVO.setModifyOperator(createOperator);
        }
        ProductKind productKind = new ProductKind();
        try {
            BeanUtils.copyProperties(productKind,productKindVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //图片
        if(productKindVO.getMultipartFile()!=null && !productKindVO.getMultipartFile().isEmpty()){
            try {
                ResponseResult responseResult = ftpFileUploadController.uploadImg(
                        productKindVO.getMultipartFile(), productKindVO.getImageType());
                String imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                Integer imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                Integer imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                productKind.setImageHeight(imageHeight);
                productKind.setImageUrl(imageUrl);
                productKind.setImageWidth(imageWidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productKindDao.insertProductKind(productKind);
        return ResponseResult.success(productKind);
    }

    /**
     * @Description 修改商品种类
     * @Param [productKindVO]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:47
     */
    @Transactional
    @Override
    public ResponseResult updateProductKindById(ProductKindVO productKindVO) {
        //判断修改的商品种类信息是否合理
        //判断商品类型是否正常
        Long productClassifyId = productKindVO.getProductClassifyId();
        if(productClassifyId!=null){
            Boolean flag = false;   //标志商品类型是否正常
            for(ProductTypeEnum productTypeEnum : ProductTypeEnum.values()){
                if(productClassifyId.intValue() == productTypeEnum.getCode()){
                    flag = true;
                }
            }
            if(!flag){
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getDesc()+productClassifyId));
            }
        }
        //判断商品分类显示是否正常
        Long productTypeDisplayId = productKindVO.getProductTypeDisplayId();
        if(productTypeDisplayId!=null){
            int j = productTypeDisplayDao.selectProductTypeDisplayById(productTypeDisplayId);
            if (j != 1) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
            }
        }
        //判断商品种类是否已经存在
        Long productKindId = productKindVO.getProductKindId();
        ProductKind productKindtemp = productKindDao.selectProductKindByProductKindId(productKindId);//根据id查询，有且仅有一个
        if (productKindtemp==null) {    //不存在返回错误
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getDesc()));
        }
        //存在即修改
        ProductKind productKind = new ProductKind();
        try {
            BeanUtils.copyProperties(productKind,productKindVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //状态为删除
        if (productKind.getStatus() != null && 0 == productKind.getStatus().intValue()) {
           return deleteProductKindById(productKind.getProductKindId(),productKind.getModifyOperator());
        }
        //如果有图片
        if(productKindVO.getMultipartFile()!=null && !productKindVO.getMultipartFile().isEmpty()){
            try {
                //判断图片类型是否正确
                if(StringUtils.isBlank(productKindVO.getImageType())){
                    return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getCode(),
                            ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getDesc()));
                }
                //图片是否存在
                if(!StringUtils.isBlank(productKindtemp.getImageUrl())){
                    //先删除图片
                    String  imageName = productKindtemp.getImageUrl().split("\\/")[2];
                    ftpFileUploadController.deleteImg(imageName,productKindtemp.getImageType());
                }
                //存储图片
                ResponseResult responseResult = ftpFileUploadController.uploadImg(
                        productKindVO.getMultipartFile(), productKindVO.getImageType());
                String imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                Integer imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                Integer imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                productKind.setImageHeight(imageHeight);
                productKind.setImageUrl(imageUrl);
                productKind.setImageWidth(imageWidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productKindDao.updateProductKindById(productKind);
        return ResponseResult.success(productKind);
    }

    /**
     * @Description 删除商品种类
     * @Param [productKindId]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:46
     */
    @Transactional
    @Override
    public ResponseResult deleteProductKindById(Long productKindId, String modifyOperator) {
        //判断商品种类是否已经存在
        ProductKind productKindtemp = productKindDao.selectProductKindByProductKindId(productKindId);//根据id查询，有且仅有一个
        if (productKindtemp==null) {    //不存在返回错误
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getDesc()));
        }
        //判断此商品种类是否在使用
        List<Product> products = productKindDao.selectProductByProductKindId(productKindId);
        if (products.size()!=0) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_IS_USE.getCode(),
                    productKindId + " " + ResponseCodeProductTypeEnum.PRODUCT_KIND_IS_USE.getDesc()+
                            ",商品如下："+products));
        }
        List<ServiceProduct> serviceProducts = productKindDao.selectServiceByProductKindId(productKindId);
        if (serviceProducts.size()!=0) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_IS_USE.getCode(),
                    productKindId + " " + ResponseCodeProductTypeEnum.PRODUCT_KIND_IS_USE.getDesc()+
                            ",服务如下："+serviceProducts));
        }
        List<ProductBrand> productBrandList = productKindDao.selectProductBrandByProductKindId(productKindId);
        if (productBrandList.size()!=0) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_IS_USE.getCode(),
                    productKindId + " " + ResponseCodeProductTypeEnum.PRODUCT_KIND_IS_USE.getDesc()+
                            ",商品品牌如下:"+productBrandList));
        }
        List<ProductCategory> productCategoryList = productKindDao.selectProductCategoryByProductKindId(productKindId);
        if (productCategoryList.size()!=0) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_IS_USE.getCode(),
                    productKindId + " " + ResponseCodeProductTypeEnum.PRODUCT_KIND_IS_USE.getDesc()+
                            ",商品品类如下："+productCategoryList));
        }
        List<ProductEffect> productEffectList = productKindDao.selectProductEffectByProductKindId(productKindId);
        if (productEffectList.size()!=0) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_IS_USE.getCode(),
                    productKindId + " " + ResponseCodeProductTypeEnum.PRODUCT_KIND_IS_USE.getDesc()+
                    ",商品功效如下:"+productEffectList));
        }
        //图片是否存在
        if(!StringUtils.isBlank(productKindtemp.getImageUrl())){
            //删除商品种类图片
            String  imageName = productKindtemp.getImageUrl().split("\\/")[2];
            try {
                ftpFileUploadController.deleteImg(imageName,productKindtemp.getImageType());
            } catch (Exception e) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getCode(),
                        ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getDesc()));
            }
        }
        return ResponseResult.success(productKindDao.deleteProductKind(productKindId));
    }

    @Override
    public ResponseResult selectProductKindById(Long productKindId) {
        if (productKindId==null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.KIND_ID_NULL.getCode(), ResponseCodeProductTypeEnum.KIND_ID_NULL.getDesc()));
        }
        return ResponseResult.success(productKindDao.selectProductKindByProductKindId(productKindId));
    }

}
