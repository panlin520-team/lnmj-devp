package com.lnmj.product.controller.backend;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductDivisionService;
import com.lnmj.product.entity.VO.ProductDivisionVO;
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
 * @Date: 2019/8/14 12:12
 * @Description: 商品专区
 */
@Api(description = "商品专区")
@RestController
@RequestMapping("/productDivision")
public class ProductDivisionController {
    @Resource
    private IProductDivisionService productDivisionService;

    /**
    *@Description 查询商品专区
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/14
    *@Time 12:14
    */
    @ApiOperation(value = "查询商品专区", notes = "查询商品专区")
    @RequestMapping(value = "/selectProductDivisionList", method = RequestMethod.POST)
    public ResponseResult selectProductDivisionList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String access_token) {
        return productDivisionService.selectProductDivisionList(pageNum, pageSize);
    }

    /**
    *@Description 新增商品专区
    *@Param [productDivisionVo, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/14
    *@Time 14:07
    */
    @ApiOperation(value = "新增商品专区", notes = "新增商品专区")
    @RequestMapping(value = "/insertProductDivision", method = RequestMethod.POST)
    public ResponseResult insertProductDivision(ProductDivisionVO productDivisionVo, String access_token) {
        //商品功效名字
        if (StringUtils.isBlank(productDivisionVo.getProductDivisionName())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_NAME_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_NAME_NULL.getDesc()));
        }
        //商品分类显示
        if (productDivisionVo.getProductTypeDisplayId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
        }
        //图片
        if(StringUtils.isBlank(productDivisionVo.getImageType())){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getDesc()));
        }
        if (productDivisionVo.getMultipartFile()==null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_PICCTURE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_PICCTURE.getDesc()));
        }
        //创建人
        if (StringUtils.isBlank(productDivisionVo.getCreateOperator())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_CREATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_CREATE_OPPERATOR.getDesc()));
        }
        return productDivisionService.insertProductDivision(productDivisionVo);
    }

    /**
    *@Description 修改商品专区
    *@Param [productDivisionVO, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/14
    *@Time 14:16
    */
    @ApiOperation(value = "修改商品专区", notes = "修改商品专区")
    @RequestMapping(value = "/updateProductDivision", method = RequestMethod.POST)
    public ResponseResult updateProductDivision(ProductDivisionVO productDivisionVO, String access_token) {
        if (productDivisionVO.getProductDivisionId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getDesc()));
        }
        if (StringUtils.isBlank(productDivisionVO.getModifyOperator())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return productDivisionService.updateProductDivision(productDivisionVO);
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
    @RequestMapping(value = "/deleteProductDivision", method = RequestMethod.POST)
    public ResponseResult deleteProductDivision(Long productDivisionId, String modifyOperator, String access_token) {
        if (productDivisionId == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return productDivisionService.deleteProductDivision(productDivisionId,modifyOperator);
    }
    
    /**
    *@Description 根据商品类型查询商品专区
    *@Param [productClassifyId, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/14
    *@Time 17:03
    */
    @ApiOperation(value = "根据商品类型查询商品专区", notes = "根据商品类型查询商品专区")
    @RequestMapping(value = "/selectProductDivisionByProductClassifyId", method = RequestMethod.POST)
    public ResponseResult selectProductDivisionByProductClassifyId(Long productClassifyId, String access_token) {
        if(productClassifyId==null){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_ID.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_ID.getDesc()));
        }
        return productDivisionService.selectProductDivisionByProductClassifyId(productClassifyId);
    }

    @ApiOperation(value = "根据专区ID查看专区", notes = "根据专区ID查看专区")
    @RequestMapping(value = "/selectProductDivisionById", method = RequestMethod.POST)
    public ResponseResult selectProductDivisionById(Long productDivisionId) {
        return productDivisionService.selectProductDivisionById(productDivisionId);
    }

}
