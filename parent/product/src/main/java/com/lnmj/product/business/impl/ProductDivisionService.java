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
import com.lnmj.product.business.IProductDivisionService;
import com.lnmj.product.entity.ProductDivision;
import com.lnmj.product.entity.ProductKind;
import com.lnmj.product.entity.VO.ProductDivisionVO;
import com.lnmj.product.entity.VO.ProductType;
import com.lnmj.product.repository.IProductDivisionDao;
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
 * @Date: 2019/8/14 14:22
 * @Description: 商品分类专区
 */
@Service("productDivisionService")
public class ProductDivisionService  implements IProductDivisionService {
    @Resource
    private IProductDivisionDao productDivisionDao;
    @Resource
    private FtpFileUploadController ftpFileUploadController;
    @Resource
    private ProductTypeService productTypeService;
    @Resource
    private IProductTypeDisplayDao productTypeDisplayDao;
    @Resource
    private IProductKindDao productKindDao;

    @Transactional
    @Override
    public ResponseResult selectProductDivisionList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductType> productTypeList  = productTypeService.selectProductType(
                ProductTypeDistinguishEnum.PRODUCT_DIVISION.getCode().longValue(), null);
        if (productTypeList.size()!=0) {
            PageInfo<ProductType> pageInfo = new PageInfo(productTypeList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_DIVISION_NOT_FIND.getCode(),
                ResponseCodeProductTypeEnum.PRODUCT_DIVISION_NOT_FIND.getDesc()));
    }

    @Transactional
    @Override
    public ResponseResult insertProductDivision(ProductDivisionVO productDivisionVo) {
        //判断商品类型是否正常
        Long productClassifyId = productDivisionVo.getProductClassifyId();
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
        //商品分类显示是否正常
        Long productTypeDisplayId = productDivisionVo.getProductTypeDisplayId();
        if(productTypeDisplayId!=null){
            int j = productTypeDisplayDao.selectProductTypeDisplayById(productTypeDisplayId);
            if (j != 1) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
            }
        }
        //修改人
        String createOperator = productDivisionVo.getCreateOperator();
        if (StringUtils.isBlank(productDivisionVo.getModifyOperator())){
            productDivisionVo.setModifyOperator(createOperator);
        }
        ProductDivision productDivision = new ProductDivision();
        try {
            BeanUtils.copyProperties(productDivision,productDivisionVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //图片
        if(productDivisionVo.getMultipartFile()!=null && !productDivisionVo.getMultipartFile().isEmpty()){
            try {
                ResponseResult responseResult = ftpFileUploadController.uploadImg(
                        productDivisionVo.getMultipartFile(), productDivision.getImageType());
                String imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                Integer imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                Integer imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                productDivision.setImageHeight(imageHeight);
                productDivision.setImageUrl(imageUrl);
                productDivision.setImageWidth(imageWidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productDivisionDao.insertProductDivision(productDivision);
        return ResponseResult.success(productDivision);
    }

    @Transactional
    @Override
    public ResponseResult updateProductDivision(ProductDivisionVO productDivisionVO) {
        //判断商品类型是否正常
        Long productClassifyId = productDivisionVO.getProductClassifyId();
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
        Long productTypeDisplayId = productDivisionVO.getProductTypeDisplayId();
        if(productTypeDisplayId!=null){
            int j = productTypeDisplayDao.selectProductTypeDisplayById(productTypeDisplayId);
            if (j != 1) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                        ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
            }
        }
        //判断商品专区是否已经存在
        Long productDivisionId = productDivisionVO.getProductDivisionId();
        ProductDivision productDivisionTemp = productDivisionDao.selectProductDivisionByProductDivisionId(productDivisionId);//根据id查询，有且仅有一个
        if (productDivisionTemp==null) {    //不存在返回错误
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_DIVISION.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_DIVISION.getDesc()));
        }
        //存在即修改
        ProductDivision productDivision = new ProductDivision();
        try {
            BeanUtils.copyProperties(productDivision,productDivisionVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //状态为删除
        if (productDivision.getStatus() != null && 0 == productDivision.getStatus().intValue()) {
            return deleteProductDivision(productDivision.getProductDivisionId(),productDivision.getModifyOperator());
        }
        //如果有图片
        if(productDivisionVO.getMultipartFile()!=null && !productDivisionVO.getMultipartFile().isEmpty()){
            try {
                //判断图片类型是否正确
                if(StringUtils.isBlank(productDivisionVO.getImageType())){
                    return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getCode(),
                            ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getDesc()));
                }
                //先删除图片
                if(!StringUtils.isBlank(productDivisionTemp.getImageUrl())) {
                    String imageName = productDivisionTemp.getImageUrl().split("\\/")[2];
                    ftpFileUploadController.deleteImg(imageName, productDivisionTemp.getImageType());
                }
                //存储图片
                ResponseResult responseResult = ftpFileUploadController.uploadImg(
                        productDivisionVO.getMultipartFile(), productDivisionVO.getImageType());
                String imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                Integer imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                Integer imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                productDivision.setImageHeight(imageHeight);
                productDivision.setImageUrl(imageUrl);
                productDivision.setImageWidth(imageWidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productDivisionDao.updateProductDivision(productDivision);
        return ResponseResult.success(productDivision);
    }

    @Transactional
    @Override
    public ResponseResult deleteProductDivision(Long productDivisionId, String modifyOperator) {
        //判断商品专区是否已经存在
        ProductDivision productDivisionTemp = productDivisionDao.selectProductDivisionByProductDivisionId(productDivisionId);//根据id查询，有且仅有一个
        if (productDivisionTemp==null) {    //不存在返回错误
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_DIVISION.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_DIVISION.getDesc()));
        }
        //判断此商品种类是否在使用 TODO

        //删除商品专区图片
        if(!StringUtils.isBlank(productDivisionTemp.getImageUrl())){
            String  imageName = productDivisionTemp.getImageUrl().split("\\/")[2];
            try {
                ftpFileUploadController.deleteImg(imageName,productDivisionTemp.getImageType());
            } catch (Exception e) {
                return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getCode(),
                        ResponseCodeProductTypeEnum.UNSUPPORTED_PICTURE_TYPE.getDesc()));
            }
        }
        return ResponseResult.success(productDivisionDao.deleteProductDivision(productDivisionId));
    }
    @Transactional
    @Override
    public ResponseResult selectProductDivisionByProductClassifyId(Long productClassifyId) {
        if(productClassifyId==null){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_ID.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_ID.getDesc()));
        }
        return ResponseResult.success(productDivisionDao.selectProductDivisionByProductClassifyId(productClassifyId));
    }
    @Transactional
    @Override
    public ResponseResult selectProductDivisionById(Long productDivisionId) {
        if (productDivisionId==null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.DIVISION_ID_NULL.getCode(), ResponseCodeProductTypeEnum.DIVISION_ID_NULL.getDesc()));
        }
        return ResponseResult.success(productDivisionDao.selectProductDivisionByProductDivisionId(productDivisionId));
    }
}
