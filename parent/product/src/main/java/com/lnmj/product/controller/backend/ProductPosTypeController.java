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
 * @Description:商品模块
 */
@Api(description = "商品模块")
@RestController
@RequestMapping("/manage/product")
public class ProductPosTypeController {
    @Resource(name = "productService")
    private IProductService productService;


    @ApiOperation(value = "查看商品类别枚举", notes = "查看商品类别枚举")
    @RequestMapping(value = "/selectProductTypeEnum", method = RequestMethod.POST)
    public ResponseResult selectProductTypeEnum() {
        return productService.selectProductTypeEnum();
    }
    
}
