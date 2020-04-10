package com.lnmj.product.controller.backend;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductPromotionService;
import com.lnmj.product.business.IProductPromotionTypeService;
import com.lnmj.product.entity.ProductPromotion;
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
 * @Description:商品促销
 */
@Api(description = "商品促销")
@RestController
@RequestMapping("/manage/productPromotion")
public class ProductPromotionController {
    @Resource(name = "productPromotionService")
    private IProductPromotionService productPromotionService;

    /**
     * @Description 商品促销分页显示(包含关键字查询)
     * @Param [pageNum, pageSize, keyword, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/7
     * @Time
     */
    @ApiOperation(value = "商品促销分页显示", notes = "商品促销分页显示")
    @RequestMapping(value = "/selectProductPromotionList", method = RequestMethod.POST)
    public ResponseResult selectProductPromotionList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String keyword, String access_token) {
        return productPromotionService.selectProductPromotionList(pageNum, pageSize, keyword);
    }


    /**
     * @Description 商品促销添加
     * @Param [productPromotion, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/7
     * @Time
     */
    /*@ApiOperation(value = "商品促销添加", notes = "商品促销添加")
    @RequestMapping(value = "/insertProductPromotion", method = RequestMethod.POST)
    public ResponseResult insertProductPromotion(ProductPromotion productPromotion, String access_token) {
        if (StringUtils.isNotBlank(productPromotion.getPromotionName())) {
            return productPromotionService.insertProductPromotion(productPromotion);
        }
        return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTPROMOTIONNAME_ISNULL.getCode(), ResponseCodeProductEnum.PRODUCTPROMOTIONNAME_ISNULL.getDesc()));
    }*/


    /**
     * @Description 商品促销删除
     * @Param [productPromotion, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/7
     * @Time
     */
    /*@ApiOperation(value = "商品促销删除", notes = "商品促销删除")
    @RequestMapping(value = "/deleteProductPromotion", method = RequestMethod.POST)
    public ResponseResult deleteProductPromotion(Long productPromotionTpyeId, String access_token) {
        if (productPromotionTpyeId != null) {
            return productPromotionService.deleteProductPromotion(productPromotionTpyeId);
        }
        return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTPROMOTIONID_ISNULL.getCode(), ResponseCodeProductEnum.PRODUCTPROMOTIONID_ISNULL.getDesc()));
    }
*/

    /**
     * @Description 商品促销更新
     * @Param [productPromotion, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/7
     * @Time
     */
    /*@ApiOperation(value = "商品促销更新", notes = "商品促销更新")
    @RequestMapping(value = "/updateProductPromotion", method = RequestMethod.POST)
    public ResponseResult updateProductPromotion(ProductPromotion productPromotion, String access_token) {
        return productPromotionService.updateProductPromotionById(productPromotion);
    }*/




}
