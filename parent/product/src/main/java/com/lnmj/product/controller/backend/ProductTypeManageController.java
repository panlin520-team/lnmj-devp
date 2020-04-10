package com.lnmj.product.controller.backend;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.impl.ProductTypeService;
import com.lnmj.product.entity.VO.ProductTypeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 14:47
 * @Description: 商品分类后台controller
 */
@Api(description = "商品分类后台")
@RestController
@RequestMapping("/manage/productType")
public class ProductTypeManageController {
    @Resource(name = "productTypeService")
    private ProductTypeService productTypeService;

    @ApiOperation(value = "查询商品分类的种类", notes = "查询商品分类的种类")
    @RequestMapping(value = "/selectProductTypeKind", method = RequestMethod.POST)
    public ResponseResult selectProductTypeKind(String access_token) {
        return productTypeService.selectProductTypeKind();
    }

    //商品种类商品产地表添加外键商品类型
    //判断商品产地表和商品种类表的商品类型是否合理
    //商品产地表去掉积分字段
    //商品分类按商品类型查询，商品种类、商品产地、
    //商品分类(商品品牌、商品功效、商品品类)按商品种类删除
    //商品分类(商品品牌、商品功效、商品品类)按商品种类查询
    //删除商品种类，需判断商品是否使用了商品种类，并列出商品
    //删除商品种类,判断商品品类，商品功效,商品品牌和商品

    /**
     * @Description 根据商品类型查询商品种类，商品产地，商品专区
     * @Param [productClassifyId,access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/6
     * @Time 16:38
     */
    @ApiOperation(value = "根据商品类型查询商品种类，商品产地，商品专区", notes = "根据商品类型查询商品种类，商品产地，商品专区")
    @RequestMapping(value = "/selectProductKindByProductClassifyId", method = RequestMethod.POST)
    public ResponseResult selectProductKindByProductClassifyId(Long productClassifyId, String access_token) {
        if(productClassifyId==null){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_ID.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_ID.getDesc()));
        }
        return productTypeService.selectProductTypeByProductClassifyId(productClassifyId);
    }

    /**
     * @Description 商品分类所有数据
     * @Param []
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/6
     * @Time 16:38
     */
    @ApiOperation(value = "商品分类所有数据", notes = "商品分类所有数据")
    @RequestMapping(value = "/selectProductType", method = RequestMethod.POST)
    public ResponseResult selectProductType(String access_token) {
        return productTypeService.selectProductTypeList();
    }

    /**
     * @Description 商品分类分页显示
     * @Param [pageNum, pageSize, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/6
     * @Time 16:38
     */
    @ApiOperation(value = "商品分类分页显示", notes = "商品分类分页显示")
    @RequestMapping(value = "/selectProductTypeList", method = RequestMethod.POST)
    public ResponseResult selectProductTypeList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String access_token) {
        return productTypeService.selectProductTypeList(pageNum, pageSize);
    }

    /**
     * @Description 商品分类关键字查询
     * @Param [pageNum, pageSize, keyWord, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/6
     * @Time 16:40
     */
    @ApiOperation(value = "商品分类关键字查询", notes = "商品分类关键字查询")
    @RequestMapping(value = "/selectProductTypeByKeyWord", method = RequestMethod.POST)
    public ResponseResult selectProductTypeByKeyWord(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                     String keyWord, String access_token) {
        return productTypeService.selectProductTypeByKeyWord(pageNum, pageSize, keyWord);
    }

    /**
     * @Description 商品分类按分类查询
     * @Param [pageNum, pageSize, productTypeCategory, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/10
     * @Time 15:03
     */
    @ApiOperation(value = "商品分类按分类查询", notes = "商品分类按分类查询")
    @RequestMapping(value = "/selectProductTypeByType", method = RequestMethod.POST)
    public ResponseResult selectProductTypeByType(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                  Long productTypeDistinguishId, String access_token) {
        return productTypeService.selectProductTypeByType(pageNum, pageSize, productTypeDistinguishId);
    }

    /**
     * @Description 新增商品分类
     * @Param [productTypeVO, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/8
     * @Time 14:37
     */
    @ApiOperation(value = "新增商品分类", notes = "新增商品分类")
    @RequestMapping(value = "/insertProductType", method = RequestMethod.POST)
    public ResponseResult insertProductType(ProductTypeVO productTypeVO, String access_token) {
        //判断传入的参数是否合理
        if (StringUtils.isBlank(productTypeVO.getProductTypeName())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_NAME.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_NAME.getDesc()));
        }
        if (productTypeVO.getProductKindId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_KIND.getDesc()));
        }
        if (StringUtils.isBlank(productTypeVO.getProductTypeOrder())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_ORDER.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_ORDER.getDesc()));
        }
        if (productTypeVO.getProductTypeDisplayId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
        }
        if(productTypeVO.getProductClassifyId()==null){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getDesc()));
        }
        if(StringUtils.isBlank(productTypeVO.getImageType())){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getDesc()));
        }
//        if (productTypeVO.getMultipartFile()==null) {
//            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_PICCTURE.getCode(),
//                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_PICCTURE.getDesc()));
//        }
        if (productTypeVO.getIntegralratioService() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_INTEGRALRATIO_SERVICE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_INTEGRALRATIO_SERVICE.getDesc()));
        }
        if (productTypeVO.getIntegralratioUnion() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_INTEGRALRATIO_UNION.getCode(),
                    ResponseCodeProductTypeEnum.NOT_INTEGRALRATIO_UNION.getDesc()));
        }
        if (productTypeVO.getRetailpriceVIP1() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_RETAILPRICE_VIP1.getCode(),
                    ResponseCodeProductTypeEnum.NOT_RETAILPRICE_VIP1.getDesc()));
        }
        if (productTypeVO.getRetailpriceVIP2() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_RETAILPRICE_VIP2.getCode(),
                    ResponseCodeProductTypeEnum.NOT_RETAILPRICE_VIP2.getDesc()));
        }
        if (productTypeVO.getRetailpriceVIP3() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_RETAILPRICE_VIP3.getCode(),
                    ResponseCodeProductTypeEnum.NOT_RETAILPRICE_VIP3.getDesc()));
        }
        if (productTypeVO.getRetailpriceVIP4() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_RETAILPRICE_VIP4.getCode(),
                    ResponseCodeProductTypeEnum.NOT_RETAILPRICE_VIP4.getDesc()));
        }
        if (productTypeVO.getRetailpriceVIP5() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_RETAILPRICE_VIP5.getCode(),
                    ResponseCodeProductTypeEnum.NOT_RETAILPRICE_VIP5.getDesc()));
        }
        if (StringUtils.isBlank(productTypeVO.getCreateOperator())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_CREATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_CREATE_OPPERATOR.getDesc()));
        }
        if(productTypeVO.getProductTypeDistinguishId()==null){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_DISTINGUISH_ID.getCode(),
                    ResponseCodeProductTypeEnum.NOT_DISTINGUISH_ID.getDesc()));
        }
        return productTypeService.insertProductType(productTypeVO);
    }

    /**
     * @Description 根据id删除商品分类
     * @Param [pageNum, pageSize, keyWord, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/9
     * @Time 19:27
     */
    @ApiOperation(value = "根据id删除商品分类", notes = "根据id删除商品分类")
    @RequestMapping(value = "/deleteProductType", method = RequestMethod.POST)
    public ResponseResult deleteProductType(Long productTypeId, Long productTypeDistinguishId, String modifyOperator, String access_token) {
        if (productTypeId == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_TYPE_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_TYPE_ID_NULL.getDesc()));
        }
        if(productTypeDistinguishId==null){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_DISTINGUISH_ID.getCode(),
                    ResponseCodeProductTypeEnum.NOT_DISTINGUISH_ID.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return productTypeService.deleteProductType(productTypeId,productTypeDistinguishId,modifyOperator);
    }

    /**
     * @Description 根据id修改商品分类
     * @Param [productTypeId, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/10
     * @Time 11:12
     */
    @ApiOperation(value = "根据id修改商品分类", notes = "根据id修改商品分类")
    @RequestMapping(value = "/updateProductType", method = RequestMethod.POST)
    public ResponseResult updateProductType(ProductTypeVO productTypeVO, String access_token) {
        //修改的商品分类Id不能为空
        if (productTypeVO.getProductTypeId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_TYPE_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_TYPE_ID_NULL.getDesc()));
        }
        //修改的商品分类区分id不能为空
        if(productTypeVO.getProductTypeDistinguishId()==null){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_DISTINGUISH_ID.getCode(),
                    ResponseCodeProductTypeEnum.NOT_DISTINGUISH_ID.getDesc()));
        }
        //修改人不能为空
        if (StringUtils.isBlank(productTypeVO.getModifyOperator())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return productTypeService.updateProductType(productTypeVO);
    }

    /**
     * @Description 导出商品分类
     * @Param [productTypeVO, access_token]
     * @Return com.lnm  j.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/10
     * @Time 15:58
     */
    @ApiOperation(value = "导出商品分类", notes = "导出商品分类")
    @RequestMapping(value = "/exportProductType", method = RequestMethod.POST)
    public ResponseResult exportProductType(HttpServletRequest req, HttpServletResponse resp, String access_token) {
        return productTypeService.exportProductType(req, resp);
    }

}
