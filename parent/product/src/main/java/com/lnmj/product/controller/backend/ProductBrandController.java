package com.lnmj.product.controller.backend;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductBrandService;
import com.lnmj.product.entity.VO.ProductBrandVO;
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
 * @Description: 商品品牌controller
 */
@Api(description = "商品品牌")
@RestController
@RequestMapping("/productBrand")
public class ProductBrandController {
    @Resource
    private IProductBrandService productBrandService;

    /**
     * @Description 查询商品品牌
     * @Param [pageNum, pageSize, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:24
     */
    @ApiOperation(value = "查询商品品牌", notes = "查询商品品牌")
    @RequestMapping(value = "/selectProductBrandList", method = RequestMethod.POST)
    public ResponseResult selectProductBrandList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String keyWord) {
        return productBrandService.selectProductBrandList(pageNum, pageSize,keyWord);
    }

    @ApiOperation(value = "查询商品品牌", notes = "查询商品品牌")
    @RequestMapping(value = "/selectProductBrandListNoPage", method = RequestMethod.POST)
    public ResponseResult selectProductBrandListNoPage() {
        return productBrandService.selectProductBrandListNoPage();
    }

    /**
     * @Description 新增商品品牌
     * @Param [pProductBrand, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:27
     */
    @ApiOperation(value = "新增商品品牌", notes = "新增商品品牌")
    @RequestMapping(value = "/insertProductBrand", method = RequestMethod.POST)
    public ResponseResult insertProductBrand(ProductBrandVO productBrandVO, String access_token) {
        //商品品牌名字
        if (StringUtils.isBlank(productBrandVO.getProductBrandName())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_NAME_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_NAME_NULL.getDesc()));
        }
        //商品种类
        if (productBrandVO.getProductKindId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getDesc()));
        }
        //商品分类显示
        if (productBrandVO.getProductTypeDisplayId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
        }
        //图片
        if(StringUtils.isBlank(productBrandVO.getImageType())){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getDesc()));
        }
        if (productBrandVO.getMultipartFile()==null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_PICCTURE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_PICCTURE.getDesc()));
        }
        //创建人
        if (StringUtils.isBlank(productBrandVO.getCreateOperator())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_CREATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_CREATE_OPPERATOR.getDesc()));
        }
        return productBrandService.insertProductBrand(productBrandVO);
    }

    /**
     * @Description 修改商品品牌
     * @Param [pProductBrand, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:31
     */
    @ApiOperation(value = "修改商品品牌", notes = "修改商品品牌")
    @RequestMapping(value = "/updateProductBrandById", method = RequestMethod.POST)
    public ResponseResult updateProductBrandById(ProductBrandVO productBrandVO, String access_token) {
        if (productBrandVO.getProductBrandId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getDesc()));
        }
        if (StringUtils.isBlank(productBrandVO.getModifyOperator())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return productBrandService.updateProductBrandById(productBrandVO);
    }

    /**
     * @Description 删除商品品牌
     * @Param [productBrandId,modifyOperator, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:31
     */
    @ApiOperation(value = "删除商品品牌", notes = "删除商品品牌")
    @RequestMapping(value = "/deleteProductBrandById", method = RequestMethod.POST)
    public ResponseResult deleteProductBrandById(Long productBrandId, String modifyOperator, String access_token) {
        if (productBrandId == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return productBrandService.deleteProductBrandById(productBrandId,modifyOperator);
    }

    @ApiOperation(value = "根据品牌id查看品牌", notes = "根据品牌id查看品牌")
    @RequestMapping(value = "/selectBrandById", method = RequestMethod.POST)
    public ResponseResult selectBrandById(Long productBrandId) {
        return productBrandService.selectBrandById(productBrandId);
    }
}
