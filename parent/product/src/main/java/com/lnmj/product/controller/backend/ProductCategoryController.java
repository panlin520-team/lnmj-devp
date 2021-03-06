package com.lnmj.product.controller.backend;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductCategoryService;
import com.lnmj.product.entity.VO.ProductCategoryVO;
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
 * @Description: 商品品类controller
 */
@Api(description = "商品品类")
@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {
    @Resource
    private IProductCategoryService productCategoryService;

    /**
     * @Description 查询商品品类
     * @Param [pageNum, pageSize, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:24
     */
    @ApiOperation(value = "查询商品品类", notes = "查询商品品类")
    @RequestMapping(value = "/selectProductCategoryList", method = RequestMethod.POST)
    public ResponseResult selectProductCategoryList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String keyWord) {
        return productCategoryService.selectProductCategoryList(pageNum, pageSize,keyWord);
    }

    @ApiOperation(value = "查询商品品类", notes = "查询商品品类")
    @RequestMapping(value = "/selectProductCategoryListNoPage", method = RequestMethod.POST)
    public ResponseResult selectProductCategoryListNoPage() {
        return productCategoryService.selectProductCategoryListNoPage();
    }


    /**
     * @Description 新增商品品类
     * @Param [pProductCategory, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:27
     */
    @ApiOperation(value = "新增商品品类", notes = "新增商品品类")
    @RequestMapping(value = "/insertProductCategory", method = RequestMethod.POST)
    public ResponseResult insertProductCategory(ProductCategoryVO productCategoryVO, String access_token) {
        //商品品类名字
        if (StringUtils.isBlank(productCategoryVO.getProductCategoryName())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_NAME_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_NAME_NULL.getDesc()));
        }
        //商品种类
        if (productCategoryVO.getProductKindId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getDesc()));
        }
        //商品分类显示
        if (productCategoryVO.getProductTypeDisplayId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
        }
        //图片
        if(StringUtils.isBlank(productCategoryVO.getImageType())){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getDesc()));
        }
        if (productCategoryVO.getMultipartFile()==null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_PICCTURE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_PICCTURE.getDesc()));
        }
        //创建人
        if (StringUtils.isBlank(productCategoryVO.getCreateOperator())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_CREATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_CREATE_OPPERATOR.getDesc()));
        }
        return productCategoryService.insertProductCategory(productCategoryVO);
    }

    /**
     * @Description 修改商品品类
     * @Param [pProductCategory, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:31
     */
    @ApiOperation(value = "修改商品品类", notes = "修改商品品类")
    @RequestMapping(value = "/updateProductCategoryById", method = RequestMethod.POST)
    public ResponseResult updateProductCategoryById(ProductCategoryVO productCategoryVO, String access_token) {
        if (productCategoryVO.getProductCategoryId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getDesc()));
        }
        if (StringUtils.isBlank(productCategoryVO.getModifyOperator())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return productCategoryService.updateProductCategoryById(productCategoryVO);
    }

    /**
     * @Description 删除商品品类
     * @Param [productCategoryId,modifyOperator, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:31
     */
    @ApiOperation(value = "删除商品品类", notes = "删除商品品类")
    @RequestMapping(value = "/deleteProductCategoryById", method = RequestMethod.POST)
    public ResponseResult deleteProductCategoryById(Long productCategoryId, String modifyOperator, String access_token) {
        if (productCategoryId == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return productCategoryService.deleteProductCategoryById(productCategoryId,modifyOperator);
    }

    @ApiOperation(value = "根据品类id查看品类", notes = "根据品类id查看品类")
    @RequestMapping(value = "/selectProductCategoryById", method = RequestMethod.POST)
    public ResponseResult selectProductCategoryById(Long productCategoryId) {
        return productCategoryService.selectProductCategoryById(productCategoryId);
    }

}
