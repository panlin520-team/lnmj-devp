package com.lnmj.product.controller.portal;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductCartService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @Auther: panlin
 * @Date: 2019/5/6 16:11
 * @Description: 购物车管理
 */
@Api(description = "购物车管理")
@RestController
@RequestMapping("/productCookieCart")
public class ProductCartCookieController {
    @Resource
    private IProductCartService iProductCartService;

    /**
     * 添加商品至购物车列表
     *
     * @param productId 商品ID
     * @param request
     * @param response
     * @throws UnsupportedEncodingException 异常抛出
     */
    @RequestMapping("/insertProductToCookieCart/{productId}")
    public ResponseResult insertGoodsToCartCookie(@PathVariable("productId") Long productId, HttpServletRequest request,
                                                  HttpServletResponse response) throws UnsupportedEncodingException {

        return iProductCartService.insertGoodsToCookieCart(productId, request, response);
    }

    /**
     * 根据ID删除删除购物车内的商品
     *
     * @param productId 商品ID
     * @param request
     * @param response
     * @return 成功与否
     * @throws UnsupportedEncodingException 抛出异常
     */
    @RequestMapping("/deleteByProductIdCookie/{productId}")
    public ResponseResult deleteByGoodsIdCookie(@PathVariable("productId") Long productId, HttpServletRequest request,
                                                HttpServletResponse response) throws UnsupportedEncodingException {
        return iProductCartService.deleteByGoodsIdCookie(productId, request, response);
    }

    /**
     * 清空购物车
     *
     * @param response
     * @param request
     * @return 成功与否
     */
    @RequestMapping("/deleteAllCookie")
    public ResponseResult deleteCookie(HttpServletResponse response, HttpServletRequest request) {
        return iProductCartService.deleteCookie(response, request);
    }

    /**
     * 获取购物车列表
     *
     * @param request
     * @param response
     * @return 购物车列表
     * @throws UnsupportedEncodingException 抛出异常
     */
    @RequestMapping("/getCookieCart")
    public ResponseResult getCookieCart(HttpServletRequest request, HttpServletResponse response) throws
            UnsupportedEncodingException {
        return iProductCartService.getCookieCart(response, request);
    }
}

