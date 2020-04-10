package com.lnmj.product.controller.backend;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductPromotionTypeService;
import com.lnmj.product.entity.ProductPromotionType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author renqingyun
 * @Date: 2019/5/7 16:28
 * @Description:商品促销类型
 */
@Api(description = "商品促销类型")
@RestController
@RequestMapping("/manage/productPromotionTpye")
public class ProductPromotionTypeController {
    @Resource(name = "productPromotionTypeService")
    private IProductPromotionTypeService productPromotionTypeService;

    /**
     * @Description 商品促销类型分页显示(包含关键字查询)
     * @Param [pageNum, pageSize, keyword, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/7
     * @Time
     */
    @ApiOperation(value = "商品促销类型分页显示", notes = "商品促销类型分页显示")
    @RequestMapping(value = "/selectProductPromotionList", method = RequestMethod.POST)
    public ResponseResult selectProductPromotionList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String keyword, String access_token) {
        return productPromotionTypeService.selectProductPromotionList(pageNum, pageSize, keyword);
    }


    /**
     * @Description 商品促销类型添加
     * @Param [productPromotionType, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/7
     * @Time
     */
    @ApiOperation(value = "商品促销类型添加", notes = "商品促销类型添加")
    @RequestMapping(value = "/insertProductPromotion", method = RequestMethod.POST)
    public ResponseResult insertProductPromotion(ProductPromotionType productPromotionType, String access_token) {
        if (StringUtils.isNotBlank(productPromotionType.getPromotionName())) {
            return productPromotionTypeService.insertProductPromotion(productPromotionType);
        }
        return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTPROMOTIONNAME_ISNULL.getCode(), ResponseCodeProductEnum.PRODUCTPROMOTIONNAME_ISNULL.getDesc()));
    }


    /**
     * @Description 商品促销类型删除
     * @Param [productPromotion, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/7
     * @Time
     */
    @ApiOperation(value = "商品促销类型删除", notes = "商品促销类型删除")
    @RequestMapping(value = "/deleteProductPromotion", method = RequestMethod.POST)
    public ResponseResult deleteProductPromotion(Long productPromotionTpyeId, String access_token) {
        if (productPromotionTpyeId != null) {
            return productPromotionTypeService.deleteProductPromotion(productPromotionTpyeId);
        }
        return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTPROMOTIONID_ISNULL.getCode(), ResponseCodeProductEnum.PRODUCTPROMOTIONID_ISNULL.getDesc()));
    }


    /**
     * @Description 商品促销类型更新
     * @Param [productPromotionType, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/7
     * @Time
     */
    @ApiOperation(value = "商品促销类型更新", notes = "商品促销类型更新")
    @RequestMapping(value = "/updateProductPromotion", method = RequestMethod.POST)
    public ResponseResult updateProductPromotion(ProductPromotionType productPromotionType, String access_token) {
        return productPromotionTypeService.updateProductPromotionById(productPromotionType);
    }




}
