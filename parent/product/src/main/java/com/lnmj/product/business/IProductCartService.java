package com.lnmj.product.business;

import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @Auther: panlin
 * @Date: 2019/5/8 17:38
 * @Description:
 */
@Service("iProductCartService")
public interface IProductCartService {
    ResponseResult insertGoodsToCookieCart(Long productId, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException;

    ResponseResult deleteByGoodsIdCookie(Long productId, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException;

    ResponseResult deleteCookie(HttpServletResponse response, HttpServletRequest request);

    ResponseResult getCookieCart(HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException;

    ResponseResult list(Long userId);

    ResponseResult insertGoodsToCart(Long userId, Long productId, Integer count);

    ResponseResult update(Long userId, Long productId, Integer count);

    ResponseResult deleteProduct(Long userId, String productIds);

    ResponseResult selectOrUnSelect(Long userId, Long productId, Integer checked);

    ResponseResult getCartProductCount(Long userId);

}
