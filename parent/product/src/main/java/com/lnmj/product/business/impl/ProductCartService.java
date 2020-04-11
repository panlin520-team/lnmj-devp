package com.lnmj.product.business.impl;

import com.google.common.base.Splitter;
import com.lnmj.common.Const;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeCartEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.BigDecimalUtil;
import com.lnmj.product.business.IProductCartService;
import com.lnmj.product.entity.Cart;
import com.lnmj.product.entity.Product;
import com.lnmj.product.entity.VO.CartCookieVo;
import com.lnmj.product.entity.VO.CartProductVo;
import com.lnmj.product.entity.VO.CartVo;
import com.lnmj.product.repository.ICartDao;
import com.lnmj.product.repository.IProductDao;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: panlin
 * @Date: 2019/5/8 17:38
 * @Description:
 */
@Service("productCartService")
public class ProductCartService implements IProductCartService {
    @Resource
    ICartDao iCartDao;
    @Resource
    IProductDao iProductDao;

    @Override
    public ResponseResult insertGoodsToCookieCart(Long productId, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        // 从cookie中获取购物车列表
        List<CartCookieVo> cartVos = getCartInCookie(response, request);
        Cookie cookie_2st;
        // 如果购物车列表为空
        if (cartVos.size() <= 0) {
            //TODO 根据商品ID获取商品信息
            CartCookieVo cartVo = new CartCookieVo(); // 测试用，实际应当根据id获取
            cartVo.setNum(1);//数量
            cartVo.setProductId(productId);//商品id
            cartVo.setProductPrice(BigDecimal.valueOf(1));//商品价格
            cartVo.setProductName("雷园");//商品名称
            cartVo.setPictureUrl("url");//商品图片链接
            // 将当前传来的商品添加到购物车列表
            cartVos.add(cartVo);
            if (getCookie(request) == null) {
                // 制作购物车cookie数据
                cookie_2st = new Cookie("cart", URLEncoder.encode(makeCookieValue(cartVos), "utf-8"));
                cookie_2st.setPath("/");//设置在该项目下都可以访问该cookie
                cookie_2st.setMaxAge(60 * 30);//设置cookie有效时间为30分钟
                response.addCookie(cookie_2st);//添加cookie
            } else {
                cookie_2st = getCookie(request);
                cookie_2st.setPath("/");//设置在该项目下都可以访问该cookie
                cookie_2st.setMaxAge(60 * 30);//设置cookie有效时间为30分钟
                cookie_2st.setValue(URLEncoder.encode(makeCookieValue(cartVos)));
                response.addCookie(cookie_2st);//添加cookie
            }
        }
        // 当获取的购物车列表不为空时
        else {
            int bj = 0;
            for (CartCookieVo cart : cartVos) {
                // 如果购物车中存在该商品则数量+1
                if (cart.getProductId() == productId) {
                    cart.setNum(cart.getNum() + 1);
                    bj = 1;
                    break;
                }
            }
            if (bj == 0) {
                //TODO 根据商品ID获取商品信息
                CartCookieVo cartVo = new CartCookieVo(); // 测试用，实际应当根据id获取
                cartVo.setNum(1);
                cartVo.setProductId(productId);
                cartVo.setProductPrice(BigDecimal.valueOf(1));
                cartVo.setProductName("雷园");
                // 将当前传来的商品添加到购物车列表
                cartVos.add(cartVo);
            }
            // 获取名为"cart"的cookie
            cookie_2st = getCookie(request);
            cookie_2st.setPath("/");//设置在该项目下都可以访问该cookie
            cookie_2st.setMaxAge(60 * 30);//设置cookie有效时间为30分钟
            cookie_2st.setValue(URLEncoder.encode(makeCookieValue(cartVos))); // 设置value
            response.addCookie(cookie_2st);//添加cookie
        }

        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteByGoodsIdCookie(Long productId, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        // 获取cookie中购物车列表
        List<CartCookieVo> cartVos = getCartInCookie(response, request);
        CartCookieVo deleteCart = null;
        // 判断购物车列表是否为空
        if (cartVos.size() > 0) {
            // 循环购物车列表寻找相同ID的商品
            for (CartCookieVo c : cartVos) {
                if (c.getProductId() == productId) {
                    deleteCart = c;
                    break;
                }
            }
            // 判断是否找到相同ID的商品
            if (deleteCart != null) {
                // 判断购物车中商品的数量
                if (deleteCart.getNum() > 1) {
                    // 数量大于1增让数量-1
                    deleteCart.setNum(deleteCart.getNum() - 1);
                    cartVos.remove(deleteCart);
                    cartVos.add(deleteCart);
                } else {
                    // 否则直接删除该商品在购物车中的信息
                    cartVos.remove(deleteCart);
                }
                // 获取名为"cart"的cookie
                Cookie cookie = getCookie(request);
                // 为cookie设置value
                cookie.setValue(URLEncoder.encode(makeCookieValue(cartVos), "utf-8"));
                // 设置寿命
                cookie.setMaxAge(60 * 10);
                // 设置路径
                cookie.setPath("/");
                // 更新cookie
                response.addCookie(cookie);
            }
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteCookie(HttpServletResponse response, HttpServletRequest request) {
        // 获取名为"cart"的cookie
        Cookie cookie = getCookie(request);
        // 设置寿命为0秒
        cookie.setMaxAge(0);
        // 设置路径
        cookie.setPath("/");
        // 设置cookie的value为null
        cookie.setValue(null);
        // 更新cookie
        response.addCookie(cookie);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult getCookieCart(HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        List<CartCookieVo> cartVos = getCartInCookie(response, request);
        return ResponseResult.success(cartVos);
    }


    @Override
    public ResponseResult<CartVo> list(Long userId) {
        CartVo cartVo = this.getCartVoLimit(userId);
        return ResponseResult.success(cartVo);
    }

    @Override
    public ResponseResult insertGoodsToCart(Long userId, Long productId, Integer count) {
        if (productId == null || count == null) {
            return ResponseResult.error(new Error(ResponseCodeCartEnum.PARAMETER_ILLEGAL.getCode(), ResponseCodeCartEnum.PARAMETER_ILLEGAL.getDesc()));
        }
        Cart cartForSelect = new Cart();
        cartForSelect.setUserId(userId);
        cartForSelect.setProductId(productId);
        Cart cart = iCartDao.selectCartByUserIdProductId(cartForSelect);
        if (cart == null) {
            //这个产品不在这个购物车里,需要新增一个这个产品的记录
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
            iCartDao.insert(cartItem);
        } else {
            //这个产品已经在购物车里了.
            //如果产品已存在,数量相加
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            iCartDao.updateByPrimaryKeySelective(cart);
        }
        return this.list(userId);
    }

    @Override
    public ResponseResult<CartVo> update(Long userId, Long productId, Integer count) {
        if (productId == null || count == null) {
            return ResponseResult.error(new Error(ResponseCodeCartEnum.PARAMETER_ILLEGAL.getCode(), ResponseCodeCartEnum.PARAMETER_ILLEGAL.getDesc()));
        }
        Cart cartForSelect = new Cart();
        cartForSelect.setUserId(userId);
        cartForSelect.setProductId(productId);
        Cart cart = iCartDao.selectCartByUserIdProductId(cartForSelect);
        if (cart != null) {
            cart.setQuantity(count);
        }
        iCartDao.updateByPrimaryKey(cart);
        return this.list(userId);
    }

    @Override
    public ResponseResult deleteProduct(Long userId, String productIds) {
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(productList)) {
            return ResponseResult.error(new Error(ResponseCodeCartEnum.PARAMETER_ILLEGAL.getCode(), ResponseCodeCartEnum.PARAMETER_ILLEGAL.getDesc()));
        }
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductIdList(productList);
        iCartDao.deleteByUserIdProductIds(cart);
        return this.list(userId);
    }

    public ResponseResult selectOrUnSelect(Long userId, Long productId, Integer checked) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductId(productId);
        cart.setChecked(checked);
        iCartDao.checkedOrUncheckedProduct(cart);
        return this.list(userId);
    }

    @Override
    public ResponseResult getCartProductCount(Long userId) {
        if (userId == null) {
            return ResponseResult.success(0);
        }
        return ResponseResult.success(iCartDao.selectCartProductCount(userId));
    }


    /**
     * 获取名为"cart"的cookie
     *
     * @param request
     * @return cookie
     */
    public Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie cart_cookie = null;
        for (Cookie cookie : cookies) {
            if ("cart".equals(cookie.getName())) { //获取购物车cookie
                cart_cookie = cookie;
            }
        }
        return cart_cookie;
    }


    /**
     * 获取cookie中的购物车列表
     *
     * @param response
     * @param request
     * @return 购物车列表
     * @throws UnsupportedEncodingException 抛出异常
     */
    public List<CartCookieVo> getCartInCookie(HttpServletResponse response, HttpServletRequest request) throws
            UnsupportedEncodingException {
        // 定义空的购物车列表
        List<CartCookieVo> items = new ArrayList<CartCookieVo>();
        String value_1st = "";
        // 购物cookie
        Cookie cart_cookie = getCookie(request);
        // 判断cookie是否为空
        if (cart_cookie != null) {
            // 获取cookie中String类型的value
            value_1st = URLDecoder.decode(cart_cookie.getValue(), "utf-8");//从cookie获取购物车
            // 判断value是否为空或者""字符串
            if (value_1st != null && !"".equals(value_1st)) {
                // 解析字符串中的数据为对象并封装至list中返回给上一级
                String[] arr_1st = value_1st.split("==");
                for (String value_2st : arr_1st) {
                    String[] arr_2st = value_2st.split("=");
                    CartCookieVo item = new CartCookieVo();
                    item.setProductId(Long.parseLong(arr_2st[0])); //商品id
                    item.setProductName(arr_2st[1]); //商品名称
                    item.setProductPrice(new BigDecimal(arr_2st[2])); //商品价格
                    item.setNum(Integer.parseInt(arr_2st[3])); //商品购物车数量
                    item.setPictureUrl(arr_2st[4]); //商品购物车数量
                    items.add(item);
                }
            }
        }
        return items;
    }

    /**
     * 制作cookie所需value
     *
     * @param cartVos 购物车列表
     * @return 解析为字符串的购物车列表，属性间使用"="相隔，对象间使用"=="相隔
     */
    public String makeCookieValue(List<CartCookieVo> cartVos) {
        StringBuffer buffer_2st = new StringBuffer();
        if (cartVos.size() == 0) {
            return buffer_2st.toString();
        }
        for (CartCookieVo item : cartVos) {
            buffer_2st.append(item.getProductId() + "=" + item.getProductName() + "="
                    + item.getProductPrice() + "=" + item.getNum() + "=" + item.getPictureUrl() + "==");
        }
        return buffer_2st.toString().substring(0, buffer_2st.toString().length() - 2);
    }


    private CartVo getCartVoLimit(Long userId) {
        CartVo cartVo = new CartVo();
        List<Cart> cartList = iCartDao.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        BigDecimal cartTotalPrice = new BigDecimal("0");
        if (CollectionUtils.isNotEmpty(cartList)) {
            for (Cart cartItem : cartList) {
                //循环用户购物车，加入到购物-商品VO
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(userId);
                cartProductVo.setProductId(cartItem.getProductId());

                Product product = iProductDao.selectById(cartItem.getProductId());
                if (product != null) {
                    cartProductVo.setProductMainImage(product.getImageList());//TODO 获取商品第一张首图
                    cartProductVo.setProductName(product.getProductName());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getRetailPrice());
                    cartProductVo.setProductStock(product.getStockQuantity());
                    //判断库存
                    int buyLimitCount = 0;
                    if (product.getStockQuantity() >= cartItem.getQuantity()) {
                        //库存充足的时候
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        //库存不充足的时候
                        buyLimitCount = product.getStockQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        iCartDao.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getRetailPrice().doubleValue(), cartProductVo.getQuantity()));
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }

                if (cartItem.getChecked() == Const.Cart.CHECKED) {
                    //如果已经勾选,增加到整个的购物车总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        cartVo.setImageHost("123");//TODO 获取图片地址host
        return cartVo;
    }


    private boolean getAllCheckedStatus(Long userId) {
        if (userId == null) {
            return false;
        }
        return iCartDao.selectCartProductCheckedStatusByUserId(userId) == 0;

    }
}
