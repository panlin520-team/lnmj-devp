package com.lnmj.product.business.impl;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ProductTypeDistinguishEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.baseController.FtpFileUploadController;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductCategoryService;
import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.ProductCategory;
import com.lnmj.product.entity.ProductKind;
import com.lnmj.product.entity.ServiceProduct;
import com.lnmj.product.entity.VO.ProductCategoryVO;
import com.lnmj.product.entity.VO.ProductType;
import com.lnmj.product.repository.IProductCategoryDao;
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
 * @Description: 商品品类service
 */
@Service("productCategoryService")
public class ProductCategoryService implements IProductCategoryService {
    @Resource
    private IProductCategoryDao productCategoryDao;
    @Resource
    private FtpFileUploadController ftpFileUploadController;
    @Resource
    private ProductTypeService productTypeService;
    @Resource
    private IProductTypeDisplayDao productTypeDisplayDao;
    @Resource
    private IProductKindDao productKindDao;
    /**
     * @Description 查询商品品类
     * @Param [pageNum, pageSize]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:48
     */
    @Transactional
    @Override
    public ResponseResult selectProductCategoryList(int pageNum, int pageSize, String keyWord) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductType> productTypeList  = productTypeService.selectProductType(
                ProductTypeDistinguishEnum.PRODUCT_CATEGORY.getCode().longValue(), keyWord);
        if (productTypeList.size()!=0) {
            PageInfo<ProductType> pageInfo = new PageInfo(productTypeList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_CATEGORY_NOT_FIND.getCode(),
                ResponseCodeProductTypeEnum.PRODUCT_CATEGORY_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectProductCategoryListNoPage() {
        List<ProductType> productTypeList  = productTypeService.selectProductType(
                ProductTypeDistinguishEnum.PRODUCT_CATEGORY.getCode().longValue(), null);
        if (productTypeList.size()!=0) {
            return ResponseResult.success(productTypeList);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_CATEGORY_NOT_FIND.getCode(),
                ResponseCodeProductTypeEnum.PRODUCT_CATEGORY_NOT_FIND.getDesc()));
    }

    /**
     * @Description 新增商品品类
     * @Param [productCategoryVO]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:47
     */
    @Transactional
    @Override
    public ResponseResult insertProductCategory(ProductCategoryVO productCategoryVO) {
        //判断商品分类显示是否正常
        Long productTypeDisplayId = productCategoryVO.getProductTypeDisplayId();
        if(productTypeDisplayId!=null){
            int j = productTypeDisplayDao.selectProductTypeDisplayById(productTypeDisplayId);
            if (j != 1) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
            }
        }
        //判断商品种类是否正常
        Long productKindId = productCategoryVO.getProductKindId();
        if(productKindId!=null){
            ProductKind productKind = productKindDao.selectProductKindByProductKindId(productKindId);//根据id查询，有且仅有一个
            if (productKind==null) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getDesc()));
            }
        }
        //修改人
        String createOperator = productCategoryVO.getCreateOperator();
        if (StringUtils.isBlank(productCategoryVO.getModifyOperator())){
            productCategoryVO.setModifyOperator(createOperator);
        }
        ProductCategory productCategory = new ProductCategory();
        try {
            BeanUtils.copyProperties(productCategory,productCategoryVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //图片
        if(productCategoryVO.getMultipartFile()!=null && !productCategoryVO.getMultipartFile().isEmpty()){
            try {
                ResponseResult responseResult = ftpFileUploadController.uploadImg(
                        productCategoryVO.getMultipartFile(), productCategoryVO.getImageType());
                String imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                Integer imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                Integer imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                productCategory.setImageHeight(imageHeight);
                productCategory.setImageUrl(imageUrl);
                productCategory.setImageWidth(imageWidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productCategoryDao.insertProductCategory(productCategory);
        return ResponseResult.success(productCategory);
    }

    /**
     * @Description 修改商品品类
     * @Param [productCategoryVO]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:47
     */
    @Transactional
    @Override
    public ResponseResult updateProductCategoryById(ProductCategoryVO productCategoryVO) {
        //判断修改的商品品牌信息是否合理
        //判断商品分类显示是否正常
        Long productTypeDisplayId = productCategoryVO.getProductTypeDisplayId();
        if(productTypeDisplayId!=null){
            int j = productTypeDisplayDao.selectProductTypeDisplayById(productTypeDisplayId);
            if (j != 1) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
            }
        }
        //判断商品种类是否正常
        Long productKindId = productCategoryVO.getProductKindId();
        if(productKindId!=null){
            ProductKind productKind = productKindDao.selectProductKindByProductKindId(productKindId);//根据id查询，有且仅有一个
            if (productKind==null) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getDesc()));
            }
        }
        //判断商品品类是否存在
        Long productCategoryId = productCategoryVO.getProductCategoryId();
        ProductCategory productCategoryTemp = productCategoryDao.selectProductCategoryByProductCategoryId(productCategoryId);//根据id查询，有且仅有一个
        if (productCategoryTemp==null) {    //不存在返回错误
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_CATEGORY.getCode(),
                    productCategoryId+" " + ResponseCodeProductTypeEnum.NOT_PRODUCT_CATEGORY.getDesc()));
        }
        ProductCategory productCategory = new ProductCategory();
        try {
            BeanUtils.copyProperties(productCategory,productCategoryVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //状态为删除
        if (productCategory.getStatus()!=null && 0 == productCategory.getStatus().intValue()) {
            return deleteProductCategoryById(productCategory.getProductCategoryId(),productCategory.getModifyOperator());
        }
        //如果有图片
        if(productCategoryVO.getMultipartFile()!=null && !productCategoryVO.getMultipartFile().isEmpty()){
            try {
                //判断图片类型是否正确
                if(StringUtils.isBlank(productCategoryVO.getImageType())){
                    return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getCode(),
                            ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getDesc()));
                }
                //先删除图片
                if(!StringUtils.isBlank(productCategoryTemp.getImageUrl())){
                    String imageName = productCategoryTemp.getImageUrl().split("\\/")[2];
                    ftpFileUploadController.deleteImg(imageName, productCategoryTemp.getImageType());
                }
                //存储图片
                ResponseResult responseResult = ftpFileUploadController.uploadImg(
                        productCategoryVO.getMultipartFile(), productCategoryVO.getImageType());
                String imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                Integer imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                Integer imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                productCategory.setImageHeight(imageHeight);
                productCategory.setImageUrl(imageUrl);
                productCategory.setImageWidth(imageWidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productCategoryDao.updateProductCategoryById(productCategory);
        return ResponseResult.success(productCategory);
    }

    /**
     * @Description 删除商品品类
     * @Param [productCategoryId]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:46
     */
    @Transactional
    @Override
    public ResponseResult deleteProductCategoryById(Long productCategoryId, String modifyOperator) {
        //判断商品品类是否存在
        ProductCategory productCategoryTemp = productCategoryDao.selectProductCategoryByProductCategoryId(productCategoryId);//根据id查询，有且仅有一个
        if (productCategoryTemp==null) {    //不存在返回错误
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_CATEGORY.getCode(),
                    productCategoryId+" " + ResponseCodeProductTypeEnum.NOT_PRODUCT_CATEGORY.getDesc()));
        }
        //判断此商品品类是否在使用
        List<Product> product = productCategoryDao.selectProductByProductCategoryId(productCategoryId);
        if (product.size()!=0) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_CATEGORY_IS_USE.getCode(),
                    productCategoryId + " " + ResponseCodeProductTypeEnum.PRODUCT_CATEGORY_IS_USE.getDesc()+",商品如下:"+product));
        }
        List<ServiceProduct> serviceProduct = productCategoryDao.selectServiceByProductCategoryId(productCategoryId);
        if (serviceProduct.size()!=0) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_CATEGORY_IS_USE.getCode(),
                    productCategoryId + " " + ResponseCodeProductTypeEnum.PRODUCT_CATEGORY_IS_USE.getDesc()+",服务如下:"+serviceProduct));
        }
        //删除商品品牌图片
        if(!StringUtils.isBlank(productCategoryTemp.getImageUrl())){
            String  imageName = productCategoryTemp.getImageUrl().split("\\/")[2];
            try {
                ftpFileUploadController.deleteImg(imageName,productCategoryTemp.getImageType());
            } catch (Exception e) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getCode(),
                        ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getDesc()));
            }
        }
        return ResponseResult.success(productCategoryDao.deleteProductCategory(productCategoryId));
    }

    @Override
    public ResponseResult selectProductCategoryById(Long productTypeCategoryId) {
        if (productTypeCategoryId==null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.CATEGORY_ID_NULL.getCode(), ResponseCodeProductTypeEnum.CATEGORY_ID_NULL.getDesc()));
        }
        return ResponseResult.success(productCategoryDao.selectProductCategoryByProductCategoryId(productTypeCategoryId));
    }
}
