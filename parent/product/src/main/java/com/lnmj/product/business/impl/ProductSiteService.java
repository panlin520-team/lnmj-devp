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
import com.lnmj.product.business.IProductSiteService;
import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.ProductSite;
import com.lnmj.product.entity.VO.ProductSiteVO;
import com.lnmj.product.entity.VO.ProductType;
import com.lnmj.product.repository.IProductSiteDao;
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
 * @Description: 商品产地service
 */
@Service("productSiteService")
public class ProductSiteService implements IProductSiteService {
    @Resource
    private IProductSiteDao productSiteDao;
    @Resource
    private FtpFileUploadController ftpFileUploadController;
    @Resource
    private ProductTypeService productTypeService;
    @Resource
    private IProductTypeDisplayDao productTypeDisplayDao;

    /**
     * @Description 根据商品类型查询商品产地
     * @Param [pageNum, pageSize]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:48
     */
    @Transactional
    @Override
    public ResponseResult selectProductSiteByProductClassifyId(Long productClassifyId) {
        if(productClassifyId.intValue() == ProductTypeEnum.PRODUCT.getCode()) {
            List<ProductSite> productSiteList = productSiteDao.selectProductSiteByProductClassifyId(productClassifyId);
            if (productSiteList != null) {
                return ResponseResult.success(productSiteList);
            }
        }else{
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getDesc()+productClassifyId));
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_SITE_NOT_FIND.getCode(),
                ResponseCodeProductTypeEnum.PRODUCT_SITE_NOT_FIND.getDesc()));
    }

    /**
     * @Description 查询商品产地
     * @Param [pageNum, pageSize]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:48
     */
    @Transactional
    @Override
    public ResponseResult selectProductSiteList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductType> productTypeList  = productTypeService.selectProductType(
                ProductTypeDistinguishEnum.PRODUCT_SITE.getCode().longValue(), null);
        if (productTypeList.size()!=0) {
            PageInfo<ProductType> pageInfo = new PageInfo(productTypeList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_SITE_NOT_FIND.getCode(),
                ResponseCodeProductTypeEnum.PRODUCT_SITE_NOT_FIND.getDesc()));
    }

    /**
     * @Description 新增商品产地
     * @Param [productSiteVO]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:47
     */
    @Transactional
    @Override
    public ResponseResult insertProductSite(ProductSiteVO productSiteVO) {
        //判断商品类型是否正常
        Long productClassifyId = productSiteVO.getProductClassifyId();
        if(productClassifyId.intValue()!= ProductTypeEnum.PRODUCT.getCode()){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getDesc()+productClassifyId));
        }
        //判断商品分类显示是否正常
        Long productTypeDisplayId = productSiteVO.getProductTypeDisplayId();
        if(productTypeDisplayId!=null){
            int j = productTypeDisplayDao.selectProductTypeDisplayById(productTypeDisplayId);
            if (j != 1) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
            }
        }
        //添加修改人
        String createOperator = productSiteVO.getCreateOperator();
        if (StringUtils.isBlank(productSiteVO.getModifyOperator())){
            productSiteVO.setModifyOperator(createOperator);
        }
        ProductSite productSite = new ProductSite();
        try {
            BeanUtils.copyProperties(productSite,productSiteVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //图片
        if(productSiteVO.getMultipartFile()!=null && !productSiteVO.getMultipartFile().isEmpty()){
            try {
                ResponseResult responseResult = ftpFileUploadController.uploadImg(
                        productSiteVO.getMultipartFile(), productSiteVO.getImageType());
                String imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                Integer imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                Integer imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                productSite.setImageHeight(imageHeight);
                productSite.setImageUrl(imageUrl);
                productSite.setImageWidth(imageWidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productSiteDao.insertProductSite(productSite);
        return ResponseResult.success(productSite);
    }

    /**
     * @Description 修改商品产地
     * @Param [productSiteVO]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:47
     */
    @Transactional
    @Override
    public ResponseResult updateProductSiteById(ProductSiteVO productSiteVO) {
        //判断修改的商品产地信息是否合理
        //判断商品类型是否正常
        Long productClassifyId = productSiteVO.getProductClassifyId();
        if(productClassifyId!=null){
            if(productClassifyId.intValue()!= ProductTypeEnum.PRODUCT.getCode()){
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getDesc()+productClassifyId));
            }
        }
        //判断商品分类显示是否正常
        Long productTypeDisplayId = productSiteVO.getProductTypeDisplayId();
        if(productTypeDisplayId!=null){
            int j = productTypeDisplayDao.selectProductTypeDisplayById(productTypeDisplayId);
            if (j != 1) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
            }
        }
        //判断商品产地是否存在
        Long productSiteId = productSiteVO.getProductSiteId();
        ProductSite productSiteTemp = productSiteDao.selectProductSiteByProductSiteId(productSiteId);//根据id查询，有且仅有一个
        if (productSiteTemp==null) {    //不存在返回错误
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_SITE.getCode(),
                    productSiteId+" " + ResponseCodeProductTypeEnum.NOT_PRODUCT_SITE.getDesc()));
        }
        ProductSite productSite = new ProductSite();
        try {
            BeanUtils.copyProperties(productSite,productSiteVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //状态为删除
        if (productSite.getStatus()!=null && 0 == productSite.getStatus().intValue()) {
            return deleteProductSiteById(productSite.getProductSiteId(),productSite.getModifyOperator());
        }
        //如果有图片
        if(productSiteVO.getMultipartFile()!=null && !productSiteVO.getMultipartFile().isEmpty()){
            try {
                //判断图片类型是否正确
                if(StringUtils.isBlank(productSiteVO.getImageType())){
                    return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getCode(),
                            ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getDesc()));
                }
                //先删除图片
                if(!StringUtils.isBlank(productSiteTemp.getImageUrl())) {
                    String imageName = productSiteTemp.getImageUrl().split("\\/")[2];
                    ftpFileUploadController.deleteImg(imageName, productSiteTemp.getImageType());
                }
                //存储图片
                ResponseResult responseResult = ftpFileUploadController.uploadImg(
                        productSiteVO.getMultipartFile(), productSiteVO.getImageType());
                String imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                Integer imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                Integer imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                productSite.setImageHeight(imageHeight);
                productSite.setImageUrl(imageUrl);
                productSite.setImageWidth(imageWidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productSiteDao.updateProductSiteById(productSite);
        return ResponseResult.success(productSite);
    }

    /**
     * @Description 删除商品产地
     * @Param [productSiteId]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:46
     */
    @Transactional
    @Override
    public ResponseResult deleteProductSiteById(Long productSiteId, String modifyOperator) {
        //判断商品产地是否存在
        ProductSite productSiteTemp = productSiteDao.selectProductSiteByProductSiteId(productSiteId);//根据id查询，有且仅有一个
        if (productSiteTemp==null) {    //不存在返回错误
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_SITE.getCode(),
                    productSiteId+" " + ResponseCodeProductTypeEnum.NOT_PRODUCT_SITE.getDesc()));
        }
        //判断此商品产地是否在使用
        List<Product> product = productSiteDao.selectProductByProductSiteId(productSiteId);
        if (product.size()!=0) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_SITE_IS_USE.getCode(),
                    productSiteId + " " + ResponseCodeProductTypeEnum.PRODUCT_SITE_IS_USE.getDesc()+",商品如下:"+product));
        }
        //删除商品产地图片
        if(!StringUtils.isBlank(productSiteTemp.getImageUrl())){
            String  imageName = productSiteTemp.getImageUrl().split("\\/")[2];
            try {
                ftpFileUploadController.deleteImg(imageName,productSiteTemp.getImageType());
            } catch (Exception e) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getCode(),
                        ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getDesc()));
            }
        }
        return ResponseResult.success(productSiteDao.deleteProductSite(productSiteId));
    }

    @Override
    public ResponseResult selectProductSiteById(Long productSiteId) {
        if(productSiteId==null){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.SITE_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.SITE_ID_NULL.getDesc()));
        }
        return ResponseResult.success(productSiteDao.selectProductSiteByProductSiteId(productSiteId));
    }

}
