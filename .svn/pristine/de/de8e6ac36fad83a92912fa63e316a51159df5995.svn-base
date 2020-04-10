package com.lnmj.product.controller.backend;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductEffectService;
import com.lnmj.product.entity.VO.ProductEffectVO;
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
 * @Description: 商品功效controller
 */
@Api(description = "商品功效")
@RestController
@RequestMapping("/productEffect")
public class ProductEffectController {
    @Resource
    private IProductEffectService productEffectService;

    /**
     * @Description 查询商品功效
     * @Param [pageNum, pageSize, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:24
     */
    @ApiOperation(value = "查询商品功效", notes = "查询商品功效")
    @RequestMapping(value = "/selectProductEffectList", method = RequestMethod.POST)
    public ResponseResult selectProductEffectList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String keyWord) {
        return productEffectService.selectProductEffectList(pageNum, pageSize,keyWord);
    }

    @ApiOperation(value = "查询商品功效", notes = "查询商品功效")
    @RequestMapping(value = "/selectProductEffectListNoPage", method = RequestMethod.POST)
    public ResponseResult selectProductEffectListNoPage() {
        return productEffectService.selectProductEffectListNoPage();
    }

    /**
     * @Description 新增商品功效
     * @Param [pProductEffect, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:27
     */
    @ApiOperation(value = "新增商品功效", notes = "新增商品功效")
    @RequestMapping(value = "/insertProductEffect", method = RequestMethod.POST)
    public ResponseResult insertProductEffect(ProductEffectVO productEffectVO, String access_token) {
        //商品功效名字
        if (StringUtils.isBlank(productEffectVO.getProductEffectName())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_NAME_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_NAME_NULL.getDesc()));
        }
        //商品种类
        if (productEffectVO.getProductKindId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getDesc()));
        }
        //商品分类显示
        if (productEffectVO.getProductTypeDisplayId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
        }
        //图片
        if(StringUtils.isBlank(productEffectVO.getImageType())){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getDesc()));
        }
        if (productEffectVO.getMultipartFile()==null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_PICCTURE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_PICCTURE.getDesc()));
        }
        //创建人
        if (StringUtils.isBlank(productEffectVO.getCreateOperator())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_CREATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_CREATE_OPPERATOR.getDesc()));
        }
        return productEffectService.insertProductEffect(productEffectVO);
    }

    /**
     * @Description 修改商品功效
     * @Param [pProductEffect, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:31
     */
    @ApiOperation(value = "修改商品功效", notes = "修改商品功效")
    @RequestMapping(value = "/updateProductEffectById", method = RequestMethod.POST)
    public ResponseResult updateProductEffectById(ProductEffectVO productEffectVO, String access_token) {
        if (productEffectVO.getProductEffectId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getDesc()));
        }
        if (StringUtils.isBlank(productEffectVO.getModifyOperator())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return productEffectService.updateProductEffectById(productEffectVO);
    }

    /**
     * @Description 删除商品功效
     * @Param [productEffectId,modifyOperator, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:31
     */
    @ApiOperation(value = "删除商品功效", notes = "删除商品功效")
    @RequestMapping(value = "/deleteProductEffectById", method = RequestMethod.POST)
    public ResponseResult deleteProductEffectById(Long productEffectId, String modifyOperator, String access_token) {
        if (productEffectId == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return productEffectService.deleteProductEffectById(productEffectId,modifyOperator);
    }

    @ApiOperation(value = "根据功效id查看功效", notes = "根据功效id查看功效")
    @RequestMapping(value = "/selectProductEffectById", method = RequestMethod.POST)
    public ResponseResult selectProductEffectById(Long productEffectId) {
        return productEffectService.selectProductEffectById(productEffectId);
    }

}
