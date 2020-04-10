package com.lnmj.product.controller.backend;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductSiteService;
import com.lnmj.product.entity.VO.ProductSiteVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:35
 * @Description: 商品产地controller
 */
@Api(description = "商品产地")
@RestController
@RequestMapping("/productSite")
public class ProductSiteController {
    @Resource
    private IProductSiteService productSiteService;

    /**
     * @Description 根据商品类型查询商品产地
     * @Param [pageNum, pageSize, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:24
     */
    @ApiOperation(value = "根据商品类型查询商品产地", notes = "根据商品类型查询商品产地")
    @RequestMapping(value = "/selectProductSiteByProductClassifyId", method = RequestMethod.POST)
    public ResponseResult selectProductSiteByProductClassifyId(Long productClassifyId, String access_token) {
        if(productClassifyId==null){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_ID.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_ID.getDesc()));
        }
        return productSiteService.selectProductSiteByProductClassifyId(productClassifyId);
    }

    /**
     * @Description 查询商品产地
     * @Param [pageNum, pageSize, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:24
     */
    @ApiOperation(value = "查询商品产地", notes = "查询商品产地")
    @RequestMapping(value = "/selectProductSiteList", method = RequestMethod.POST)
    public ResponseResult selectProductSiteList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String access_token) {
        return productSiteService.selectProductSiteList(pageNum, pageSize);
    }

    /**
     * @Description 新增商品产地
     * @Param [pProductSite, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:27
     */
    @ApiOperation(value = "新增商品产地", notes = "新增商品产地")
    @RequestMapping(value = "/insertProductSite", method = RequestMethod.POST)
    public ResponseResult insertProductSite(ProductSiteVO productSiteVO, String access_token) {
        //商品产地名字
        if (StringUtils.isBlank(productSiteVO.getProductSiteName())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_NAME_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_NAME_NULL.getDesc()));
        }
        //商品类型
        if(productSiteVO.getProductClassifyId()==null){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getDesc()));
        }
        //商品分类显示
        if (productSiteVO.getProductTypeDisplayId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
        }
        //图片
        if(StringUtils.isBlank(productSiteVO.getImageType())){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getDesc()));
        }
        if (productSiteVO.getMultipartFile()==null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_PICCTURE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_PICCTURE.getDesc()));
        }
        //创建人
        if (StringUtils.isBlank(productSiteVO.getCreateOperator())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_CREATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_CREATE_OPPERATOR.getDesc()));
        }
        return productSiteService.insertProductSite(productSiteVO);
    }

    /**
     * @Description 修改商品产地
     * @Param [pProductSite, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:31
     */
    @ApiOperation(value = "修改商品产地", notes = "修改商品产地")
    @RequestMapping(value = "/updateProductSiteById", method = RequestMethod.POST)
    public ResponseResult updateProductSiteById(ProductSiteVO productSiteVO, String access_token) {
        if (productSiteVO.getProductSiteId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getDesc()));
        }
        if (productSiteVO.getModifyOperator() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return productSiteService.updateProductSiteById(productSiteVO);
    }

    /**
     * @Description 删除商品产地
     * @Param [productSiteId, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:31
     */
    @ApiOperation(value = "删除商品产地", notes = "删除商品产地")
    @RequestMapping(value = "/deleteProductSiteById", method = RequestMethod.POST)
    public ResponseResult deleteProductSiteById(Long productSiteId, String modifyOperator, String access_token) {
        if (productSiteId == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return productSiteService.deleteProductSiteById(productSiteId,modifyOperator);
    }

    @ApiOperation(value = "根据产地id查看产地", notes = "根据产地id查看产地")
    @RequestMapping(value = "/selectProductSiteById", method = RequestMethod.POST)
    public ResponseResult selectProductSiteById(Long productSiteId) {
        return productSiteService.selectProductSiteById(productSiteId);
    }

}
