package com.lnmj.product.controller.backend;


import com.lnmj.common.Enum.ProductTypeEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductService;
import com.lnmj.product.entity.ProductNursing;
import com.lnmj.product.entity.VO.ProductVO;
import com.lnmj.product.entity.VO.ServiceProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author renqingyun
 * @Date: 2019/5/7 11:49
 * @Description:商品模块
 */
@Api(description = "商品模块")
@RestController
@RequestMapping("/manage/product")
public class ProductManageController {
    @Resource(name = "productService")
    private IProductService productService;


    //------------------------------------------服务商品--------------------------

    /**
     * @Description 根据小类id以及职位和职位等级查询商品（服务商品）
     * @Param [subClassID, postId, postLevel, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/9/29
     * @Time 15:03
     */
    @ApiOperation(value = "根据小类id以及职位和职位等级查询商品（服务商品）", notes = "根据小类id以及职位和职位等级查询商品（服务商品）")
    @RequestMapping(value = "/selectProductByCondition", method = RequestMethod.POST)
    public ResponseResult selectProductByCondition(Long storeId,Long subClassID, Long postId, Integer postLevel) {
        return productService.selectProductByCondition(storeId,subClassID, postId, postLevel);
    }


    /**
     * @Description 新增商品（服务商品）
     * @Param [productvo, bindingResult, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/17
     * @Time
     */
    @ApiOperation(value = "新增商品（服务商品）", notes = "新增商品（服务商品）")
    @RequestMapping(value = "/insertServiceProduct", method = RequestMethod.POST)
    public ResponseResult insertServiceProduct(@Validated ServiceProductVO serviceProductVO, ProductNursing
            productNursing, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException {
        if (bindingResult.getErrorCount() > 0) {
            return ProductCheck(serviceProductVO, bindingResult);
        }
        return productService.insertServiceProduct(serviceProductVO, productNursing);
    }

    /**
     * @Description 更新商品（服务商品）
     * @Param [productvo, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/20
     * @Time
     */
    @ApiOperation(value = "更新商品（服务商品）", notes = "更新商品（服务商品）")
    @RequestMapping(value = "/updateServiceProduct", method = RequestMethod.POST)
    public ResponseResult updateServiceProduct(@Validated ServiceProductVO serviceProductVO, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException {
        if (bindingResult.getErrorCount() > 0) {
            return ProductCheck(serviceProductVO, bindingResult);
        }
        return productService.updateServiceProduct(serviceProductVO);
    }

    @ApiOperation(value = "更新商品所属门店（服务商品）", notes = "更新商品（服务商品）")
    @RequestMapping(value = "/updateServiceProductStoreIds", method = RequestMethod.POST)
    public ResponseResult updateServiceProductStoreIds(ServiceProductVO serviceProductVO) {
        return productService.updateServiceProductStoreIds(serviceProductVO);
    }

    /**
     * @Description 删除商品（服务商品）
     * @Param [productId, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/20
     * @Time
     */
    @ApiOperation(value = "删除商品（服务商品）", notes = "删除商品（服务商品）")
    @RequestMapping(value = "/deleteServiceProduct", method = RequestMethod.POST)
    public ResponseResult deleteServiceProduct(Long serviceProductId, String modifyOperator) {
        if (serviceProductId == null) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.SERVICE_PRODUCTID_ISNULL.getCode(), ResponseCodeProductEnum.SERVICE_PRODUCTID_ISNULL.getDesc()));
        }
        if (StringUtils.isBlank(modifyOperator)) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.MODIFYOPERATOR_ISNULL.getCode(), ResponseCodeProductEnum.MODIFYOPERATOR_ISNULL.getDesc()));
        }
        return productService.deleteServiceProduct(serviceProductId, modifyOperator);
    }


    /**
     * @Description 查询所有商品VO（服务商品）
     * @Param [pageNum, pageSize, keyWordProductCode, keyWordProductName, type]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/7/23
     * @Time
     */
    @ApiOperation(value = "查询所有商品VO（服务商品）", notes = "查询所有商品VO（服务商品）")
    @RequestMapping(value = "/selectServiceVOList", method = RequestMethod.POST)
    public ResponseResult selectServiceVOList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String keyWordProductCode, String keyWordProductName) {
        return productService.selectServiceVOList(pageNum, pageSize, keyWordProductCode, keyWordProductName);
    }


    /**
     * @Description 根据id数组或者id查看商品（服务商品）
     * @Param [serviceProductIds]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/5/30
     * @Time 17:44
     */
    @ApiOperation(value = "根据id数组或者id查看商品（服务商品）", notes = "根据id数组或者id查看商品（服务商品）")
    @RequestMapping(value = "/selectServiceListById", method = RequestMethod.POST)
    public ResponseResult selectServiceListById(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String serviceProductIds) {
        return productService.selectServiceListById(pageNum, pageSize, serviceProductIds);
    }

    @ApiOperation(value = "根据Code查询商品（服务商品）", notes = "根据Code查询商品（服务商品）")
    @RequestMapping(value = "/selectServiceProductByCode", method = RequestMethod.POST)
    public ResponseResult selectServiceProductByCode(@RequestParam("productCode") String productCode) {
        if (productCode == null) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTCODE_ISNULL.getCode(), ResponseCodeProductEnum.PRODUCTCODE_ISNULL.getDesc()));
        }
        return productService.selectServiceProductByCode(productCode);
    }

    //------------------------------------------------实体商品--------------------------------------

    /**
     * @Description 根据id查询商品（实体商品）
     * @Param [pageNum, pageSize, keyWord, status, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/8
     * @Time
     */
    @ApiOperation(value = "根据id查询商品（实体商品）", notes = "根据id查询商品（实体商品）")
    @RequestMapping(value = "/selectProductById", method = RequestMethod.POST)
    public ResponseResult selectProductById(@RequestParam("productId") Long productId) {
        if (productId == null) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTID_ISNULL.getCode(), ResponseCodeProductEnum.PRODUCTID_ISNULL.getDesc()));
        }
        return productService.selectProductById(productId);
    }

    @ApiOperation(value = "根据Code查询商品（实体商品）", notes = "根据Code查询商品（实体商品）")
    @RequestMapping(value = "/selectProductByCode", method = RequestMethod.POST)
    public ResponseResult selectProductByCode(@RequestParam("productCode") String productCode) {
        if (productCode == null) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTCODE_ISNULL.getCode(), ResponseCodeProductEnum.PRODUCTCODE_ISNULL.getDesc()));
        }
        return productService.selectProductByCode(productCode);
    }

    /**
     * @Description 新增商品（实体商品）
     * @Param [pageNum, pageSize, keyWord, status, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/8
     * @Time
     */
    @ApiOperation(value = "新增商品（实体商品）", notes = "新增商品（实体商品）")
    @RequestMapping(value = "/insertProduct", method = RequestMethod.POST)
    public ResponseResult insertProduct(@Validated ProductVO productvo, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException {
        if (bindingResult.getErrorCount() > 0) {
            return ProductCheck(productvo, bindingResult);
        }
        return productService.insertProduct(productvo);
    }


    /**
    *@Description
    *@Param [productvo, bindingResult]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/11/28
    *@Time
    */
    @ApiOperation(value = "上传图片", notes = "上传图片")
    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
    public ResponseResult uploadPic(MultipartFile[] productFiles,MultipartFile[] activityProductFiles,MultipartFile file,String type) {
        return productService.uploadPic(productFiles,activityProductFiles,file,type);
    }



    /**
    *@Description
    *@Param [productFiles, activityProductFiles, file, type]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/12/16
    *@Time
    */
    @ApiOperation(value = "上传富文本图片", notes = "上传富文本图片")
    @RequestMapping(value = "/uploadFuwenbenPic", method = RequestMethod.POST)
    public Map uploadFuwenbenPic(MultipartFile file) {
        return productService.uploadFuwenbenPic(file);
    }


    /**
     * @Description 根据小类id以及职位和职位等级查询产品
     * @Param [subClassID, postId, postLevel]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/10/28
     * @Time
     */
    @ApiOperation(value = "根据小类id以及职位和职位等级查询产品", notes = "根据小类id以及职位和职位等级查询产品")
    @RequestMapping(value = "/selectProductBySubClass", method = RequestMethod.POST)
    public ResponseResult selectProductBySubClass(Long subClassID) {
        return productService.selectProductBySubClass(subClassID);
    }


    @ApiOperation(value = "查询配送方式", notes = "查询配送方式")
    @RequestMapping(value = "/selectDeliveryMethod", method = RequestMethod.POST)
    public ResponseResult selectDeliveryMethod() {
        return productService.selectDeliveryMethod();
    }


    /**
     * @Description 更新商品（实体商品）
     * @Param [productvo, bindingResult, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/10
     * @Time
     */
    @ApiOperation(value = "更新商品（实体商品）", notes = "更新商品（实体商品）")
    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    public ResponseResult updateProduct(@Validated ProductVO productvo, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException {
        if (bindingResult.getErrorCount() > 0) {
            return ProductCheck(productvo, bindingResult);
        }

        return productService.updateProduct(productvo);
    }

    @ApiOperation(value = "更新商品所属门店（实体商品）", notes = "更新商品所属门店（实体商品）")
    @RequestMapping(value = "/updateProductStoreIds", method = RequestMethod.POST)
    public ResponseResult updateProductStoreIds(ProductVO productvo) {
        if (StringUtils.isBlank(productvo.getModifyOperator())) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.MODIFYOPERATOR_ISNULL.getCode(), ResponseCodeProductEnum.MODIFYOPERATOR_ISNULL.getDesc()));
        }
        return productService.updateProductStoreIds(productvo);
    }

    /**
     * @Description 删除商品（实体商品）
     * @Param [productId, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/13
     * @Time
     */
    @ApiOperation(value = "删除商品（实体商品）", notes = "删除商品（实体商品）")
    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    public ResponseResult deleteProduct(String productCode, String modifyOperator, Integer type) throws InvocationTargetException, IllegalAccessException {
        if (ProductTypeEnum.PRODUCT.getCode() == type) {
            //删除产品
//            if (id == null) {
//                return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTID_ISNULL.getCode(), ResponseCodeProductEnum.PRODUCTID_ISNULL.getDesc()));
//            }
        }
        if (ProductTypeEnum.SERVICE.getCode() == type) {
            //删除服务产品
//            if (id == null) {
//                return ResponseResult.error(new Error(ResponseCodeProductEnum.SERVICE_PRODUCTID_ISNULL.getCode(), ResponseCodeProductEnum.SERVICE_PRODUCTID_ISNULL.getDesc()));
//            }
        }
        if (StringUtils.isBlank(modifyOperator)) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.MODIFYOPERATOR_ISNULL.getCode(), ResponseCodeProductEnum.MODIFYOPERATOR_ISNULL.getDesc()));
        }
        return productService.deleteProduct(productCode, modifyOperator, type);
    }


    /**
     * @Description 根据id数组查看商品（实体商品）
     * @Param [pageNum, pageSize, productIds]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/7/29
     * @Time 12:38
     */
    @ApiOperation(value = "根据id数组查看商品（实体商品）", notes = "根据id数组查看商品（实体商品）")
    @RequestMapping(value = "/selectPorductListById", method = RequestMethod.POST)
    public ResponseResult selectPorductListById(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String productIds) {
        return productService.selectPorductListById(pageNum, pageSize, productIds);
    }




    @ApiOperation(value = "根据id数组查看商品（实体商品）", notes = "根据id数组查看商品（实体商品）")
    @RequestMapping(value = "/selectPorductListByIdNoPage", method = RequestMethod.POST)
    public ResponseResult selectPorductListByIdNoPage(String productIds) {
        return productService.selectPorductListByIdNoPage(productIds);
    }

    //-------------------------------------------------------护理及实体商品--------------------------------------------------------

    /**
     * @Description 商品分页查询(护理及实体商品)
     * @Param [pageNum, pageSize, keyWord, status, onOffSale, type, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/7/9
     * @Time
     */
    @ApiOperation(value = "商品分页查询(护理及实体商品)", notes = "商品分页查询(护理及实体商品)")
    @RequestMapping(value = "/selectProductList", method = RequestMethod.POST)
    public ResponseResult selectProductList(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "0") int pageSize,
                                            String postType, String keyWord,
                                            String productStatus,
                                            String type,
                                            Integer status,
                                            Integer kindId,
                                            Integer brandId,
                                            Integer effectId,
                                            String companyId,
                                            String subsidiaryId,
                                            String checkedProductCode,
                                            String storeId,
                                            Long subClassId,Long companyType,Integer isHoutai) {
        return productService.selectProductAndServiceList(pageNum, pageSize, postType, keyWord, type, productStatus, status, kindId, brandId, effectId, companyId, subsidiaryId, checkedProductCode, storeId, subClassId,companyType,isHoutai);
    }

    /**
     * @Description 商品查询不分页(护理及实体商品)
     * @Param [pageNum, pageSize, postType, keyWord, productStatus, type, status, kindId, brandId, effectId, checkedProductCode]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/9/29
     * @Time 15:32
     */
    @ApiOperation(value = "商品查询不分页(护理及实体商品)", notes = "商品查询不分页(护理及实体商品)")
    @RequestMapping(value = "/selectProductListNoPage", method = RequestMethod.POST)
    public ResponseResult selectProductListNoPage(
            String postType,
            String keyWord,
            String productStatus,
            String type,
            Integer status,
            Integer kindId,
            Integer brandId,
            Integer effectId,
            String checkedProductCode,
            Long companyId,Long companyType) {
        if (StringUtils.isBlank(type)) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCT_TYPE_ISNULL.getCode(), ResponseCodeProductEnum.PRODUCT_TYPE_ISNULL.getDesc()));
        }
        return productService.selectProductAndServiceListNoPage(postType, keyWord, type, productStatus, status, kindId, brandId, effectId, checkedProductCode,companyId,companyType);
    }


    /**
     * @Description 商品上下架（护理及实体商品）
     * @Param [productvo, bindingResult, access_token]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/13
     * @Time
     */
    @ApiOperation(value = "商品上下架（护理及实体商品）", notes = "商品上下架（护理及实体商品）")
    @RequestMapping(value = "/onOffSale", method = RequestMethod.POST)
    public ResponseResult onOffSale(String productCode, int productStatus, Integer type,Long companyType,Long companyId) {
        if (StringUtils.isEmpty(productCode)) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTID_CODE_NULL.getCode(), ResponseCodeProductEnum.PRODUCTID_CODE_NULL.getDesc()));
        }
        return productService.putOffOrOnProduct(productCode, productStatus, type,companyType,companyId);
    }

    /**
     * @Description 商品导出（护理及实体商品）
     * @Param [ids, productStatus, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/5/15
     * @Time
     */
    @ApiOperation(value = "商品导出（护理及实体商品）", notes = "商品导出（护理及实体商品）")
    @RequestMapping(value = "/exportProduct", method = RequestMethod.POST)
    public ResponseResult exportProduct(HttpServletRequest req, HttpServletResponse resp,
                                        @RequestParam(value = "status", defaultValue = "1") Integer status,
                                        @RequestParam(value = "productStatus", defaultValue = "1") Integer
                                                productStatus, @RequestParam(name = "type") Integer type) {
        return productService.exportProduct(req, resp, status, productStatus, type);
    }


    /**
     * @Description 查询所有商品VO（护理及实体商品）
     * @Param [pageNum, pageSize, keyWordProductCode, keyWordProductName, type]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/7/23
     * @Time
     */
    @ApiOperation(value = "查询商品VO（护理及实体商品）", notes = "查询所有商品VO（护理及实体商品）")
    @RequestMapping(value = "/selectProductAndServiceNameList", method = RequestMethod.POST)
    public ResponseResult selectProductAndServiceNameList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return productService.selectProductAndServiceNameList(pageNum, pageSize);
    }

    @ApiOperation(value = "查询商品VO（护理及实体商品）", notes = "查询所有商品VO（护理及实体商品）")
    @RequestMapping(value = "/selectAllProduct", method = RequestMethod.POST)
    public ResponseResult selectAllProduct() {
        return productService.selectAllProduct();
    }


    @ApiOperation(value = "插入商品子公司中间表（授权）", notes = "插入商品子公司中间表（授权）")
    @RequestMapping(value = "/addProductSubcompany", method = RequestMethod.POST)
    public ResponseResult addProductSubcompany(String productCode,Long subCompanyId,Integer type) throws InvocationTargetException, IllegalAccessException {
        return productService.addProductSubcompany(productCode,subCompanyId,type);
    }

    @ApiOperation(value = "插入商品分公司中间表（授权）", notes = "插入商品子公司中间表（授权）")
    @RequestMapping(value = "/addProductStore", method = RequestMethod.POST)
    public ResponseResult addProductStore(String productCode,Long storeId,Integer type) {
        return productService.addProductStore(productCode,storeId,type);
    }


    @ApiOperation(value = "修改实体商品及护理商品的销量", notes = "修改实体商品及护理商品的销量")
    @RequestMapping(value = "/updateProductSales", method = RequestMethod.POST)
    public ResponseResult updateProductSales(String productCode,Integer type,String upOrDown) {
        return productService.updateProductSales(productCode,type,upOrDown);
    }

    @ApiOperation(value = "修改实体商品第三方库存", notes = "修改实体商品第三方库存")
    @RequestMapping(value = "/updateStockQuantity", method = RequestMethod.POST)
    public ResponseResult updateStockQuantity(String productCode,String upOrDown) {
        return productService.updateStockQuantity(productCode,upOrDown);
    }

    @ApiOperation(value = "批量分配商品-子公司", notes = "批量分配商品-子公司")
    @RequestMapping(value = "/batchAllocationSubCompany", method = RequestMethod.POST)
    public ResponseResult batchAllocationSubCompany(String companyId,String companyType,String productArrayStr,String subcompanyIdArrayStr) {
        return productService.batchAllocationSubCompany(companyId,companyType,productArrayStr,subcompanyIdArrayStr);
    }

    @ApiOperation(value = "批量分配商品-分公司", notes = "批量分配商品-分公司")
    @RequestMapping(value = "/batchAllocationStore", method = RequestMethod.POST)
    public ResponseResult batchAllocationStore(String companyId,String companyType,String productArrayStr,String storeIdArrayStr) {
        return productService.batchAllocationStore(companyId,companyType,productArrayStr,storeIdArrayStr);
    }

    @ApiOperation(value = "批量取消分配商品-子公司", notes = "批量分配商品-子公司")
    @RequestMapping(value = "/batchCancelAllocationSubCompany", method = RequestMethod.POST)
    public ResponseResult batchCancelAllocationSubCompany(String companyId,String companyType,String productArrayStr,String subcompanyIdArrayStr) {
        return productService.batchCancelAllocationSubCompany(companyId,companyType,productArrayStr,subcompanyIdArrayStr);
    }

    @ApiOperation(value = "批量取消分配商品-分公司", notes = "批量取消分配商品-分公司")
    @RequestMapping(value = "/batchCancelAllocationStore", method = RequestMethod.POST)
    public ResponseResult batchCancelAllocationStore(String companyId,String companyType,String productArrayStr,String storeIdArrayStr) {
        return productService.batchCancelAllocationStore(companyId,companyType,productArrayStr,storeIdArrayStr);
    }


    @ApiOperation(value = "计算出入库价格以及利润", notes = "计算出入库价格以及利润")
    @RequestMapping(value = "/calculation", method = RequestMethod.POST)
    public ResponseResult calculation(String instoragePrice,String outstoragePrice,String outstorageProfit,String productCode,String type) {
        //outstorageProfit  传小数
        return productService.calculation(instoragePrice,outstoragePrice,outstorageProfit,type,productCode);
    }

/*
    @ApiOperation(value = "子公司上下架商品", notes = "子公司上下架商品")
    @RequestMapping(value = "/onDownProductBySubcompany", method = RequestMethod.POST)
    public ResponseResult onDownProductBySubcompany(String productCode, Long subCompanyId, Integer onOrUpStatus) {
        return productService.onDownProductBySubcompany(productCode,subCompanyId,onOrUpStatus);
    }
*/

    public static ResponseResult ProductCheck(@Validated Object object, BindingResult bindingResult) {
        int count = bindingResult.getErrorCount();
        HashMap<Object, Object> map = new HashMap<>();
        if (count > 0) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (int i = 0; i < count; i++) {
                FieldError error = fieldErrors.get(i);
                map.put(error.getField(), error.getDefaultMessage());
            }
        }
        return ResponseResult.error(new Error(1, map));
    }

}
