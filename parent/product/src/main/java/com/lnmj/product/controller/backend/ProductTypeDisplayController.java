package com.lnmj.product.controller.backend;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductTypeDisplayService;
import com.lnmj.product.entity.VO.ProductTypeDisplayVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:35
 * @Description: 分类显示controller
 */
@Api(description = "分类显示")
@RestController
@RequestMapping("/productTypeDisplay")
public class ProductTypeDisplayController {
    @Resource(name = "productTypeDisplayService")
    private IProductTypeDisplayService productTypeDisplayService;

    /**
     * @Description 查询分类显示
     * @Param [access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 17:49
     */
    @ApiOperation(value = "查询分类显示", notes = "查询分类显示")
    @RequestMapping(value = "/selectProductTypeDisplayList", method = RequestMethod.POST)
    public ResponseResult selectProductTypeDisplayList(String access_token) {
        return productTypeDisplayService.selectProductTypeDisplayList();
    }

    /**
     * @Description 新增分类显示
     * @Param [pProductTypeDisplay, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 17:51
     */
    @ApiOperation(value = "新增分类显示", notes = "新增分类显示")
    @RequestMapping(value = "/insertProductTypeDisplay", method = RequestMethod.POST)
    public ResponseResult insertProductTypeDisplay(ProductTypeDisplayVO productTypeDisplayVO, String access_token) {
        if (StringUtils.isBlank(productTypeDisplayVO.getProductTypeDisplayName())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_TYPE_DISPLAY_NAME_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_TYPE_DISPLAY_NAME_NULL.getDesc()));
        }
        if (StringUtils.isBlank(productTypeDisplayVO.getCreateOperator())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_CREATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_CREATE_OPPERATOR.getDesc()));
        }
        return productTypeDisplayService.insertProductTypeDisplay(productTypeDisplayVO);
    }

    /**
     * @Description 修改分类显示
     * @Param [pProductTypeDisplay, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 17:51
     */
    @ApiOperation(value = "修改分类显示", notes = "修改分类显示")
    @RequestMapping(value = "/updateProductTypeDisplayById", method = RequestMethod.POST)
    public ResponseResult updateProductTypeDisplayById(ProductTypeDisplayVO productTypeDisplayVO, String access_token) {
        if (productTypeDisplayVO.getProductTypeDisplayId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_TYPE_DISPLAY_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_TYPE_DISPLAY_ID_NULL.getDesc()));
        }
        if (productTypeDisplayVO.getModifyOperator() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return productTypeDisplayService.updateProductTypeDisplayById(productTypeDisplayVO);
    }

    /**
     * @Description 删除分类显示
     * @Param [productTypeDisplayId, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 17:51
     */
    @ApiOperation(value = "删除分类显示", notes = "删除分类显示")
    @RequestMapping(value = "/deleteProductTypeDisplayById", method = RequestMethod.POST)
    public ResponseResult deleteProductTypeDisplayById(Long productTypeDisplayId, String access_token) {
        if (productTypeDisplayId == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_TYPE_DISPLAY_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_TYPE_DISPLAY_ID_NULL.getDesc()));
        }
        return productTypeDisplayService.deleteProductTypeDisplayById(productTypeDisplayId);
    }

}
