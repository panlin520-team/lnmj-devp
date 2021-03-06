package com.lnmj.product.business.impl;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ProductTypeDistinguishEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.baseController.FtpFileUploadController;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductBrandService;
import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.ProductBrand;
import com.lnmj.product.entity.ProductKind;
import com.lnmj.product.entity.ServiceProduct;
import com.lnmj.product.entity.VO.ProductBrandVO;
import com.lnmj.product.entity.VO.ProductType;
import com.lnmj.product.repository.IProductBrandDao;
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
 * @Description: 商品品牌service
 */
@Service("productBrandService")
public class ProductBrandService implements IProductBrandService {
    @Resource
    private IProductBrandDao productBrandDao;
    @Resource
    private FtpFileUploadController ftpFileUploadController;
    @Resource
    private ProductTypeService productTypeService;
    @Resource
    private IProductTypeDisplayDao productTypeDisplayDao;
    @Resource
    private IProductKindDao productKindDao;

    /**
     * @Description 查询商品品牌
     * @Param [pageNum, pageSize]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:48
     */
    @Transactional
    @Override
    public ResponseResult selectProductBrandList(int pageNum, int pageSize, String keyWord) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductType> productTypeList = productTypeService.selectProductType(
                ProductTypeDistinguishEnum.PRODUCT_BRAND.getCode().longValue(), keyWord);
        if (productTypeList.size()!=0) {
            PageInfo<ProductType> pageInfo = new PageInfo(productTypeList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_BRAND_NOT_FIND.getCode(),
                ResponseCodeProductTypeEnum.PRODUCT_BRAND_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectProductBrandListNoPage() {
        List<ProductType> productTypeList = productTypeService.selectProductType(
                ProductTypeDistinguishEnum.PRODUCT_BRAND.getCode().longValue(), null);
        if (productTypeList.size()!=0) {
            return ResponseResult.success(productTypeList);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_BRAND_NOT_FIND.getCode(),
                ResponseCodeProductTypeEnum.PRODUCT_BRAND_NOT_FIND.getDesc()));
    }

    /**
     * @Description 新增商品品牌
     * @Param [productBrandVO]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:47
     */
    @Transactional
    @Override
    public ResponseResult insertProductBrand(ProductBrandVO productBrandVO) {
        //判断商品分类显示是否正常
        Long productTypeDisplayId = productBrandVO.getProductTypeDisplayId();
        if(productTypeDisplayId!=null){
            int j = productTypeDisplayDao.selectProductTypeDisplayById(productTypeDisplayId);
            if (j != 1) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
            }
        }
        //判断商品种类是否正常
        Long productKindId = productBrandVO.getProductKindId();
        if(productKindId!=null){
            ProductKind productKind = productKindDao.selectProductKindByProductKindId(productKindId);//根据id查询，有且仅有一个
            if (productKind==null) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getDesc()));
            }
        }
        //修改人
        String createOperator = productBrandVO.getCreateOperator();
        if (StringUtils.isBlank(productBrandVO.getModifyOperator())){
            productBrandVO.setModifyOperator(createOperator);
        }
        ProductBrand productBrand = new ProductBrand();
        try {
            BeanUtils.copyProperties(productBrand,productBrandVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //图片
        if(productBrandVO.getMultipartFile()!=null && !productBrandVO.getMultipartFile().isEmpty()){
            try {
                ResponseResult responseResult = ftpFileUploadController.uploadImg(
                        productBrandVO.getMultipartFile(), productBrandVO.getImageType());
                String imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                Integer imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                Integer imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                productBrand.setImageHeight(imageHeight);
                productBrand.setImageUrl(imageUrl);
                productBrand.setImageWidth(imageWidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productBrandDao.insertProductBrand(productBrand);
        return ResponseResult.success(productBrand);
    }

    /**
     * @Description 修改商品品牌
     * @Param [productBrandVO]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:47
     */
    @Transactional
    @Override
    public ResponseResult updateProductBrandById(ProductBrandVO productBrandVO) {
        //判断修改的商品品牌信息是否合理
        //判断商品分类显示是否正常
        Long productTypeDisplayId = productBrandVO.getProductTypeDisplayId();
        if(productTypeDisplayId!=null){
            int j = productTypeDisplayDao.selectProductTypeDisplayById(productTypeDisplayId);
            if (j != 1) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
            }
        }
        //判断商品种类是否正常
        Long productKindId = productBrandVO.getProductKindId();
        if(productKindId!=null){
            ProductKind productKind = productKindDao.selectProductKindByProductKindId(productKindId);//根据id查询，有且仅有一个
            if (productKind==null) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getDesc()));
            }
        }
        //判断商品品牌是否存在
        Long productBrandId = productBrandVO.getProductBrandId();
        ProductBrand productBrandTemp = productBrandDao.selectProductBrandByProductBrandId(productBrandId);//根据id查询，有且仅有一个
        if (productBrandTemp==null) {    //不存在返回错误
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_BRAND.getCode(),
                    productBrandId+" " + ResponseCodeProductTypeEnum.NOT_PRODUCT_BRAND.getDesc()));
        }
        ProductBrand productBrand = new ProductBrand();
        try {
            BeanUtils.copyProperties(productBrand,productBrandVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //状态为删除
        if (productBrand.getStatus()!=null && 0 == productBrand.getStatus().intValue() ) {
            return deleteProductBrandById(productBrand.getProductBrandId(),productBrand.getModifyOperator());
        }
        //如果有图片
        if(productBrandVO.getMultipartFile()!=null && !productBrandVO.getMultipartFile().isEmpty()){
            try {
                //判断图片类型是否正确
                if(StringUtils.isBlank(productBrandVO.getImageType())){
                    return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getCode(),
                            ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getDesc()));
                }
                //先删除图片
                if(!StringUtils.isBlank(productBrandTemp.getImageUrl())) {
                    String imageName = productBrandTemp.getImageUrl().split("\\/")[2];
                    ftpFileUploadController.deleteImg(imageName, productBrandTemp.getImageType());
                }
                //存储图片
                ResponseResult responseResult = ftpFileUploadController.uploadImg(
                        productBrandVO.getMultipartFile(), productBrandVO.getImageType());
                String imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                Integer imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                Integer imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                productBrand.setImageHeight(imageHeight);
                productBrand.setImageUrl(imageUrl);
                productBrand.setImageWidth(imageWidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productBrandDao.updateProductBrandById(productBrand);
        return ResponseResult.success(productBrand);
    }

    /**
     * @Description 删除商品品牌
     * @Param [productBrandId]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:46
     */
    @Transactional
    @Override
    public ResponseResult deleteProductBrandById(Long productBrandId, String modifyOperator) {
        //商品品牌是否存在
        ProductBrand productBrandTemp = productBrandDao.selectProductBrandByProductBrandId(productBrandId);//根据id查询，有且仅有一个
        if (productBrandTemp==null) {    //不存在返回错误
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_BRAND.getCode(),
                    productBrandId+" " + ResponseCodeProductTypeEnum.NOT_PRODUCT_BRAND.getDesc()));
        }
        //判断此商品品牌是否在使用
        List<Product> product = productBrandDao.selectProductByProductBrandId(productBrandId);
        if (product.size()!=0) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_BRAND_IS_USE.getCode(),
                    productBrandId + "" + ResponseCodeProductTypeEnum.PRODUCT_BRAND_IS_USE.getDesc()+",商品如下:"+product));
        }
        List<ServiceProduct> serviceProduct = productBrandDao.selectServiceByProductBrandId(productBrandId);
        if(serviceProduct.size()!=0){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_BRAND_IS_USE.getCode(),
                    productBrandId + "" + ResponseCodeProductTypeEnum.PRODUCT_BRAND_IS_USE.getDesc()+",服务如下:"+serviceProduct));
        }
        //删除商品品牌图片
        if(!StringUtils.isBlank(productBrandTemp.getImageUrl())) {
            String imageName = productBrandTemp.getImageUrl().split("\\/")[2];
            try {
                ftpFileUploadController.deleteImg(imageName, productBrandTemp.getImageType());
            } catch (Exception e) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getCode(),
                        ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getDesc()));
            }
        }
        return ResponseResult.success(productBrandDao.deleteProductBrand(productBrandId));
    }

    @Override
    public ResponseResult selectBrandById(Long productBrandId) {
        if (productBrandId==null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.BRAND_ID_NULL.getCode(), ResponseCodeProductTypeEnum.BRAND_ID_NULL.getDesc()));
        }
        return ResponseResult.success(productBrandDao.selectBrandById(productBrandId));
    }
}
