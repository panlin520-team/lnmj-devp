package com.lnmj.system.business.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ProductStatusEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeMakerEnum;
import com.lnmj.common.baseController.FtpFileUploadController;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.NumberUtils;
import com.lnmj.system.business.IMakerProductService;
import com.lnmj.system.entity.MakerProduct;
import com.lnmj.system.entity.MakerProductDetail;
import com.lnmj.system.entity.VO.MakerProductDetailVO;
import com.lnmj.system.entity.VO.MakerProductVO;
import com.lnmj.system.repository.IMakerProductDao;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/8/26 10:04
 * @Description: 创客产品
 */
@Service("makerProductService")
public class MakerPoductService implements IMakerProductService {
    @Resource
    IMakerProductDao makerProductDao;
    @Resource
    private FtpFileUploadController ftpFileUploadController;

    @Override
    public ResponseResult deleteMakerProduct(Long makerProductId, String modifyOperator) {
        //删除创客产品
        MakerProduct makerProduct = new MakerProduct();
        makerProduct.setMakerProductId(makerProductId);
        List<MakerProduct> makerProductList = makerProductDao.selectMakerProductByCondition(makerProduct);
        if(makerProductList.size()<=0){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_PRODUCT_NOT_FIND.getCode(),
                    ResponseCodeMakerEnum.MAKER_PRODUCT_NOT_FIND.getDesc()));
        }
        //删除创客产品detail
        MakerProductDetail makerProductDetail = new MakerProductDetail();
        makerProductDetail.setProductCode(makerProductList.get(0).getProductCode());
        makerProductDetail.setModifyOperator(modifyOperator);
        makerProductDao.deleteMakerProductDetailByCode(makerProductDetail);
        //删除创客产品
        makerProduct.setModifyOperator(modifyOperator);
        makerProductDao.deleteMakerProduct(makerProduct);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateMakerProduct(MakerProductVO makerProductVO) {
        //产品是否存在
        Long makerProductId = makerProductVO.getMakerProductId();
        MakerProduct makerProductTemp = new MakerProduct();
        makerProductTemp.setMakerProductId(makerProductId);
        List<MakerProduct> makerProducts = makerProductDao.selectMakerProductByCondition(makerProductTemp);
        if(makerProducts.size()<=0){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_PRODUCT_NOT_FIND.getCode(),
                    ResponseCodeMakerEnum.MAKER_PRODUCT_NOT_FIND.getDesc()));
        }
        makerProductTemp = makerProducts.get(0);
        //更新产品项目
        List<MakerProductDetailVO> makerProductDetailList = makerProductVO.getMakerProductDetailList();
        if(makerProductDetailList!=null && makerProductDetailList.size()>0){
            for(MakerProductDetailVO detailVO:makerProductDetailList){
                Long makerProductDetailId = detailVO.getMakerProductDetailId();
                MakerProductDetail makerProductDetail = new MakerProductDetail();
                try {
                    BeanUtils.copyProperties(makerProductDetail,detailVO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(makerProductDetailId==null){ //新增
                    //如果有图片
                    if(detailVO.getMultipartFile()!=null){
                        try {
                            //判断图片类型是否正确
                            if(StringUtils.isBlank(detailVO.getImageType())){
                                return ResponseResult.error(new Error(ResponseCodeMakerEnum.NOT_IMAGE_TYPE.getCode(),
                                        ResponseCodeMakerEnum.NOT_IMAGE_TYPE.getDesc()));
                            }
                            //存储图片
                            ResponseResult responseResult = ftpFileUploadController.uploadImg(
                                    detailVO.getMultipartFile(), detailVO.getImageType());
                            String imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                            Integer imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                            Integer imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                            makerProductDetail.setImageHeight(imageHeight);
                            makerProductDetail.setImageUrl(imageUrl);
                            makerProductDetail.setImageWidth(imageWidth);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    makerProductDao.insertMakerProductDetail(makerProductDetail);
                }else { //修改
                    //是否存在商品项目
                    MakerProductDetail temp = new MakerProductDetail();
                    temp.setMakerProductDetailId(makerProductDetailId);
                    List<MakerProductDetail> makerProductDetailTemps = makerProductDao.selectMakerProductDetailByCondition(temp);
                    if(makerProductDetailTemps==null|| makerProductDetailTemps.size()<=0){
                        continue;
                    }
                    temp = makerProductDetailTemps.get(0);
                    //如果有图片
                    if (detailVO.getMultipartFile() != null) {
                        try {
                            //判断图片类型是否正确
                            if (StringUtils.isBlank(detailVO.getImageType())) {
                                return ResponseResult.error(new Error(ResponseCodeMakerEnum.NOT_IMAGE_TYPE.getCode(),
                                        ResponseCodeMakerEnum.NOT_IMAGE_TYPE.getDesc()));
                            }
                            //先删除图片
                            String imageName = temp.getImageUrl().split("\\/")[2];
                            ftpFileUploadController.deleteImg(imageName, detailVO.getImageType());
                            //存储图片
                            ResponseResult responseResult = ftpFileUploadController.uploadImg(
                                    detailVO.getMultipartFile(), detailVO.getImageType());
                            String imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                            Integer imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                            Integer imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                            makerProductDetail.setImageHeight(imageHeight);
                            makerProductDetail.setImageUrl(imageUrl);
                            makerProductDetail.setImageWidth(imageWidth);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    makerProductDao.updateMakerProductDetail(makerProductDetail);
                }
            }
        }
        //更新产品
        MakerProduct makerProduct = new MakerProduct();
        try {
            BeanUtils.copyProperties(makerProduct,makerProductVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //状态为删除
//        if (makerProductVO.getStatus()!=null && 0 == makerProductVO.getStatus().intValue() ) {
//            return deleteMakerProduct(makerProductId,makerProductVO.getModifyOperator());
//        }
        //如果有图片
        if(makerProductVO.getMultipartFile()!=null){
            try {
                //判断图片类型是否正确
                if(StringUtils.isBlank(makerProductVO.getImageType())){
                    return ResponseResult.error(new Error(ResponseCodeMakerEnum.NOT_IMAGE_TYPE.getCode(),
                            ResponseCodeMakerEnum.NOT_IMAGE_TYPE.getDesc()));
                }
                //先删除图片
                String  imageName = makerProductTemp.getImageUrl().split("\\/")[2];
                ftpFileUploadController.deleteImg(imageName,makerProductTemp.getImageType());
                //存储图片
                ResponseResult responseResult = ftpFileUploadController.uploadImg(
                        makerProductVO.getMultipartFile(), makerProductTemp.getImageType());
                String imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                Integer imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                Integer imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                makerProduct.setImageHeight(imageHeight);
                makerProduct.setImageUrl(imageUrl);
                makerProduct.setImageWidth(imageWidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        makerProductDao.updateMakerProduct(makerProduct);
        return ResponseResult.success(makerProduct);
    }

    @Override
    public ResponseResult insertMakerProduct(MakerProductVO makerProductVO) {
        //商品代码
        makerProductVO.setProductCode(NumberUtils.getRandomCouponsNo());
        //默认下架
        makerProductVO.setProductStatus(ProductStatusEnum.DOWN.getCode().longValue());
        //商品详情
        List<MakerProductDetailVO> makerProductDetailList = makerProductVO.getMakerProductDetailList();
        if(makerProductDetailList!=null && makerProductDetailList.size()>0){
            for(MakerProductDetailVO detailVO:makerProductDetailList) {
                detailVO.setProductCode(makerProductVO.getProductCode());
                Long makerProductDetailId = detailVO.getMakerProductDetailId();
                MakerProductDetail makerProductDetail = new MakerProductDetail();
                try {
                    BeanUtils.copyProperties(makerProductDetail, detailVO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //如果有图片
                if (detailVO.getMultipartFile() != null) {
                    try {
                        //判断图片类型是否正确
                        if (StringUtils.isBlank(detailVO.getImageType())) {
                            return ResponseResult.error(new Error(ResponseCodeMakerEnum.NOT_IMAGE_TYPE.getCode(),
                                    ResponseCodeMakerEnum.NOT_IMAGE_TYPE.getDesc()));
                        }
                        //存储图片
                        ResponseResult responseResult = ftpFileUploadController.uploadImg(
                                detailVO.getMultipartFile(), detailVO.getImageType());
                        String imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                        Integer imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                        Integer imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                        makerProductDetail.setImageHeight(imageHeight);
                        makerProductDetail.setImageUrl(imageUrl);
                        makerProductDetail.setImageWidth(imageWidth);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                makerProductDao.insertMakerProductDetail(makerProductDetail);
            }
        }
        //新增商品
        //修改人
        String createOperator = makerProductVO.getCreateOperator();
        if (StringUtils.isBlank(makerProductVO.getModifyOperator())){
            makerProductVO.setModifyOperator(createOperator);
        }
        MakerProduct makerProduct = new MakerProduct();
        try {
            BeanUtils.copyProperties(makerProduct,makerProductVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //如果有图片
        if(makerProductVO.getMultipartFile()!=null) {
            try {
                ResponseResult responseResult = ftpFileUploadController.uploadImg(
                        makerProductVO.getMultipartFile(), makerProductVO.getImageType());
                String imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                Integer imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                Integer imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                makerProduct.setImageHeight(imageHeight);
                makerProduct.setImageUrl(imageUrl);
                makerProduct.setImageWidth(imageWidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        makerProductDao.insertMakerProduct(makerProduct);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectMakerProductByCondition(int pageNum, int pageSize, MakerProduct makerProduct) {
        PageHelper.startPage(pageNum,pageSize);
        List<MakerProduct> makerProductList = makerProductDao.selectMakerProductByCondition(makerProduct);
        if(makerProductList.size()>0){
            PageInfo<MakerProduct> pageInfo = new PageInfo<>(makerProductList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_PRODUCT_NULL.getCode(),
                ResponseCodeMakerEnum.MAKER_PRODUCT_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectMakerProductList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<MakerProduct> makerProductList = makerProductDao.selectMakerProductList();
        if(makerProductList.size()>0){
            PageInfo<MakerProduct> pageInfo = new PageInfo<>(makerProductList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_PRODUCT_NULL.getCode(),
                ResponseCodeMakerEnum.MAKER_PRODUCT_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectMakerProductDetailByCondition(int pageNum, int pageSize, MakerProductDetail makerProductDetail) {
        PageHelper.startPage(pageNum,pageSize);
        List<MakerProductDetail> productDetailList = makerProductDao.selectMakerProductDetailByCondition(makerProductDetail);
        if(productDetailList.size()>0){
            PageInfo<MakerProductDetail> pageInfo = new PageInfo<>(productDetailList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_PRODUCT_DETAIL_NULL.getCode(),
                ResponseCodeMakerEnum.MAKER_PRODUCT_DETAIL_NULL.getDesc()));
    }

    @Override
    public ResponseResult deleteMakerProductDetail(Long makerProductDetailId, String modifyOperator) {
        MakerProductDetail makerProductDetail = new MakerProductDetail();
        makerProductDetail.setMakerProductDetailId(makerProductDetailId);
        makerProductDetail.setModifyOperator(modifyOperator);
        return ResponseResult.success(makerProductDao.deleteMakerProductDetail(makerProductDetail));
    }

}
