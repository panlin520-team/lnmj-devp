package com.lnmj.product.controller.backend;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductKindService;
import com.lnmj.product.entity.VO.ProductKindVO;
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
 * @Description: 商品种类controller
 */
@Api(description = "商品种类")
@RestController
@RequestMapping("/productKind")
public class ProductKindController {
    @Resource
    private IProductKindService productKindService;

    /**
     * @Description 根据商品类型查询商品种类
     * @Param [productClassifyId,access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/6
     * @Time 16:38
     */
    @ApiOperation(value = "根据商品类型查询商品种类", notes = "根据商品类型查询商品种类")
    @RequestMapping(value = "/selectProductKindByProductClassifyId", method = RequestMethod.POST)
    public ResponseResult selectProductKindByProductClassifyId(Long productClassifyId, String access_token) {
        if(productClassifyId==null){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_ID.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_ID.getDesc()));
        }
        return productKindService.selectProductKindByProductClassifyId(productClassifyId);
    }

    /**
     * @Description 根据商品种类id查询商品品牌，商品品类，商品功效
     * @Param [productClassifyId,access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/6
     * @Time 16:38
     */
    @ApiOperation(value = "根据商品种类id查询商品品牌，商品品类，商品功效", notes = "根据商品种类id查询商品品牌，商品品类，商品功效")
    @RequestMapping(value = "/selectProductTypeByProductKindId", method = RequestMethod.POST)
    public ResponseResult selectProductTypeByProductKindId(Long productKindId, String access_token) {
        if(productKindId==null){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.KIND_ID_NULL.getDesc()));
        }
        return productKindService.selectProductTypeByProductKindId(productKindId);
    }

    /**
     * @Description 根据商品种类id删除商品品牌，商品品类，商品功效
     * @Param [productClassifyId,access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/6
     * @Time 16:38
     */
    @ApiOperation(value = "根据商品种类id删除商品品牌，商品品类，商品功效", notes = "根据商品种类id删除商品品牌，商品品类，商品功效")
    @RequestMapping(value = "/deleteProductTypeByProductKindId", method = RequestMethod.POST)
    public ResponseResult deleteProductTypeByProductKindId(Long productKindId, String access_token) {
        if(productKindId==null){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.KIND_ID_NULL.getDesc()));
        }
        return productKindService.deleteProductTypeByProductKindId(productKindId);
    }

    /**
     *@Description 根据商品种类id删除商品功效
     *@Param [productKindId, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/5/17
     *@Time 15:08
     */
    @ApiOperation(value = "根据商品种类id删除商品功效", notes = "根据商品种类id删除商品功效")
    @RequestMapping(value = "/deleteProductEffectByProductKindId", method = RequestMethod.POST)
    public ResponseResult deleteProductEffectByProductKindId(Long productKindId, String access_token) {
        if(productKindId==null){
            return  ResponseResult.error(new Error(ResponseCodeProductTypeEnum.KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.KIND_ID_NULL.getDesc()));
        }
        return productKindService.deleteProductEffectByProductKindId(productKindId);
    }


    /**
     *@Description 根据商品种类id删除商品品牌
     *@Param [productKindId, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/5/17
     *@Time 15:08
     */
    @ApiOperation(value = "根据商品种类id删除商品品牌", notes = "根据商品种类id删除商品品牌")
    @RequestMapping(value = "/deleteProductBrandByProductKindId", method = RequestMethod.POST)
    public ResponseResult deleteProductBrandByProductKindId(Long productKindId, String access_token) {
        if(productKindId==null){
            return  ResponseResult.error(new Error(ResponseCodeProductTypeEnum.KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.KIND_ID_NULL.getDesc()));
        }
        return productKindService.deleteProductBrandByProductKindId(productKindId);
    }

    /**
     *@Description 根据商品种类id删除商品品类
     *@Param [productKindId, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/5/17
     *@Time 15:08
     */
    @ApiOperation(value = "根据商品种类id删除商品品类", notes = "根据商品种类id删除商品品类")
    @RequestMapping(value = "/deleteProductCategoryByProductKindId", method = RequestMethod.POST)
    public ResponseResult deleteProductCategoryByProductKindId(Long productKindId, String access_token) {
        if(productKindId==null){
            return  ResponseResult.error(new Error(ResponseCodeProductTypeEnum.KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.KIND_ID_NULL.getDesc()));
        }
        return productKindService.deleteProductCategoryByProductKindId(productKindId);
    }

    /**
     *@Description 根据商品种类id查询商品功效
     *@Param [productKindId, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/5/17
     *@Time 15:08
     */
    @ApiOperation(value = "根据商品种类id查询商品功效", notes = "根据商品种类id查询商品功效")
    @RequestMapping(value = "/selectProductEffectByProductKindId", method = RequestMethod.POST)
    public ResponseResult selectProductEffectByProductKindId(Long productKindId, String access_token) {
        if(productKindId==null){
            return  ResponseResult.error(new Error(ResponseCodeProductTypeEnum.KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.KIND_ID_NULL.getDesc()));
        }
        return productKindService.selectProductEffectByProductKindId(productKindId);
    }


    /**
     *@Description 根据商品种类id查询商品品牌
     *@Param [productKindId, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/5/17
     *@Time 15:08
     */
    @ApiOperation(value = "根据商品种类id查询商品品牌", notes = "根据商品种类id查询商品品牌")
    @RequestMapping(value = "/selectProductBrandByProductKindId", method = RequestMethod.POST)
    public ResponseResult selectProductBrandByProductKindId(Long productKindId, String access_token) {
        if(productKindId==null){
            return  ResponseResult.error(new Error(ResponseCodeProductTypeEnum.KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.KIND_ID_NULL.getDesc()));
        }
        return productKindService.selectProductBrandByProductKindId(productKindId);
    }

    /**
    *@Description 根据商品种类id查询商品品类
    *@Param [productKindId, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/17
    *@Time 15:08
    */
    @ApiOperation(value = "根据商品种类id查询商品品类", notes = "根据商品种类id查询商品品类")
    @RequestMapping(value = "/selectProductCategoryByProductKindId", method = RequestMethod.POST)
    public ResponseResult selectProductCategoryByProductKindId(Long productKindId, String access_token) {
        if(productKindId==null){
            return  ResponseResult.error(new Error(ResponseCodeProductTypeEnum.KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.KIND_ID_NULL.getDesc()));
        }
        return productKindService.selectProductCategoryByProductKindId(productKindId);
    }

    /**
     * @Description 查询商品种类
     * @Param [pageNum, pageSize, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:24
     */
    @ApiOperation(value = "查询商品种类", notes = "查询商品种类")
    @RequestMapping(value = "/selectProductKind", method = RequestMethod.POST)
    public ResponseResult selectProductKindList(String access_token) {
        return productKindService.selectProductKind();
    }

    /**
     * @Description 商品种类分页显示
     * @Param [pageNum, pageSize, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:24
     */
    @ApiOperation(value = "商品种类分页显示", notes = "商品种类分页显示")
    @RequestMapping(value = "/selectProductKindList", method = RequestMethod.POST)
    public ResponseResult selectProductKindList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String keyWord) {
        return productKindService.selectProductKindList(pageNum, pageSize,keyWord);
    }

    /**
     * @Description 新增商品种类
     * @Param [pProductKind, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:27
     */
    @ApiOperation(value = "新增商品种类", notes = "新增商品种类")
    @RequestMapping(value = "/insertProductKind", method = RequestMethod.POST)
    public ResponseResult insertProductKind(ProductKindVO productKindVO, String access_token) {
        //商品种类名字
        if (StringUtils.isBlank(productKindVO.getProductKindName())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_NAME_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_NAME_NULL.getDesc()));
        }
        //商品类型
        if(productKindVO.getProductClassifyId()==null){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getDesc()));
        }
        //商品分类显示
        if (productKindVO.getProductTypeDisplayId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_DISPLAY.getDesc()));
        }
        //图片
        if(StringUtils.isBlank(productKindVO.getImageType())){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_IMAGE_TYPE.getDesc()));
        }
        if (productKindVO.getMultipartFile()==null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_PICCTURE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE_PICCTURE.getDesc()));
        }
        //创建人
        if (StringUtils.isBlank(productKindVO.getCreateOperator())) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_CREATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_CREATE_OPPERATOR.getDesc()));
        }
        return productKindService.insertProductKind(productKindVO);
    }

    /**
     * @Description 修改商品种类
     * @Param [pProductKind, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:31
     */
    @ApiOperation(value = "修改商品种类", notes = "修改商品种类")
    @RequestMapping(value = "/updateProductKindById", method = RequestMethod.POST)
    public ResponseResult updateProductKindById(ProductKindVO productKindVO, String access_token) {
        if (productKindVO.getProductKindId() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getDesc()));
        }
        if (productKindVO.getModifyOperator() == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return productKindService.updateProductKindById(productKindVO);
    }

    /**
     * @Description 删除商品种类
     * @Param [productKindId, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/7
     * @Time 16:31
     */
    @ApiOperation(value = "删除商品种类", notes = "删除商品种类")
    @RequestMapping(value = "/deleteProductKindById", method = RequestMethod.POST)
    public ResponseResult deleteProductKindById(Long productKindId, String modifyOperator, String access_token) {
        if (productKindId == null) {
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getCode(),
                    ResponseCodeProductTypeEnum.PRODUCT_KIND_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeProductTypeEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return productKindService.deleteProductKindById(productKindId,modifyOperator);
    }

    @ApiOperation(value = "根据种类id查看种类", notes = "根据种类id查看种类")
    @RequestMapping(value = "/selectProductKindById", method = RequestMethod.POST)
    public ResponseResult selectProductKindById(Long productKindId) {
        return productKindService.selectProductKindById(productKindId);
    }

}
