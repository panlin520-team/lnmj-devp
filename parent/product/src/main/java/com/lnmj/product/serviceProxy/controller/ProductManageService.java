package com.lnmj.product.serviceProxy.controller;


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
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * @Author renqingyun
 * @Date: 2019/5/7 11:49
 * @Description: 商品模块
 */
@Api(description = "商品模块api")
@RestController
@RequestMapping("/api/manage/product")
public class ProductManageService {
    @Resource(name = "productService")
    private IProductService productService;

    @ApiOperation(value = "根据id数组查看商品（实体商品）", notes = "根据id数组查看商品（实体商品）")
    @RequestMapping(value = "/selectPorductListById", method = RequestMethod.POST)
    public ResponseResult selectPorductListById(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String productIds) {
        return productService.selectPorductListById(pageNum, pageSize, productIds);
    }

    @ApiOperation(value = "根据id数组或者id查看商品（服务商品）", notes = "根据id数组或者id查看商品（服务商品）")
    @RequestMapping(value = "/selectServiceListById", method = RequestMethod.POST)
    public ResponseResult selectServiceListById(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String serviceProductIds) {
        return productService.selectServiceListById(pageNum, pageSize, serviceProductIds);
    }

    @ApiOperation(value = "查询所有商品VO（服务商品）", notes = "查询所有商品VO（服务商品）")
    @RequestMapping(value = "/selectServiceVOList", method = RequestMethod.POST)
    public ResponseResult selectServiceVOList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String keyWordProductCode, String keyWordProductName) {
        return productService.selectServiceVOList(pageNum, pageSize, keyWordProductCode, keyWordProductName);
    }

    @ApiOperation(value = "查询所有商品VO（服务商品）", notes = "查询所有商品VO（服务商品）")
    @RequestMapping(value = "/selectServiceVOListNoPage", method = RequestMethod.POST)
    public ResponseResult selectServiceVOListNoPage(String keyWordProductCode, String keyWordProductName) {
        return productService.selectServiceVOListNoPage(keyWordProductCode, keyWordProductName);
    }

    @ApiOperation(value = "查询商品VO（护理及实体商品）", notes = "查询所有商品VO（护理及实体商品）")
    @RequestMapping(value = "/selectAllProduct", method = RequestMethod.POST)
    public ResponseResult selectAllProduct() {
        return productService.selectAllProduct();
    }

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
