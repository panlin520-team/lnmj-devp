package com.lnmj.product.controller.portal;

import com.lnmj.common.Const;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IProductCartService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther: panlin
 * @Date: 2019/5/8 16:11
 * @Description: 购物车管理
 */
@Api(description = "购物车管理")
@RestController
@RequestMapping("/productCart")
public class ProductCartController {
    @Resource
    private IProductCartService iProductCartService;

    /**
     * @Description 显示购物车列表
     * @Param [userId] 用户id
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/5/8
     * @Time 15:14
     */
    @RequestMapping("list")
    public ResponseResult list(Long userId) {
        return iProductCartService.list(userId);
    }


    /**
     * @Description 添加商品至购物车列表
     * @Param [userId, productId, count] 用户ID 商品ID  商品数量
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/5/8
     * @Time 15:14
     */
    @PostMapping("/insertProductToCart")
    public ResponseResult insertGoodsToCart(Long userId, Long productId, Integer count) {
        return iProductCartService.insertGoodsToCart(userId, productId, count);
    }

    /**
     * @Description 修改购物车商品数量
     * @Param [userId, productId, count] 用户ID 商品ID 商品数量
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/5/8
     * @Time 15:14
     */
    @PostMapping("/update")
    public ResponseResult update(Long userId, Long productId, Integer count) {
        return iProductCartService.update(userId, productId, count);
    }

    /**
     * @Description 修改购物车商品数量
     * @Param [userId, productIds] 用户ID 商品ID集合
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/5/8
     * @Time 15:15
     */
    @RequestMapping("deleteProduct") //真删除
    public ResponseResult deleteProduct(Long userId, String productIds) {
        return iProductCartService.deleteProduct(userId, productIds);
    }

    /**
     * @Description 全选
     * @Param [userId] 用户ID
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/5/8
     * @Time 15:26
     */
    @RequestMapping("selectAll")
    public ResponseResult selectAll(Long userId) {
        return iProductCartService.selectOrUnSelect(userId, null, Const.Cart.CHECKED);
    }

    /**
     * @Description 全不选
     * @Param [userId] 用户ID
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/5/8
     * @Time 15:26
     */
    @RequestMapping("unSelectAll")
    public ResponseResult unSelectAll(Long userId) {
        return iProductCartService.selectOrUnSelect(userId, null, Const.Cart.UN_CHECKED);
    }

    /**
     * @Description 选定指定商品
     * @Param [userId, productId] 用户ID 商品ID
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/5/8
     * @Time 15:29
     */
    @RequestMapping("select")
    public ResponseResult select(Long userId, Long productId) {
        return iProductCartService.selectOrUnSelect(userId, productId, Const.Cart.CHECKED);
    }

    /**
     * @Description 不选定指定商品
     * @Param [userId, productId] 用户ID 商品ID
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/5/8
     * @Time 15:29
     */
    @RequestMapping("unSelect")
    public ResponseResult unSelect(Long userId, Long productId) {
        return iProductCartService.selectOrUnSelect(userId, productId, Const.Cart.UN_CHECKED);
    }

    /**
     * @Description 查询当前用户的购物车里面的产品数量, 如果一个产品有10个, 那么数量就是10.
     * @Param [userId] 用户ID
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author panlin
     * @Date 2019/5/8
     * @Time 15:30
     */
    @RequestMapping("selectCartProductCount")
    public ResponseResult selectCartProductCount(Long userId) {
        return iProductCartService.getCartProductCount(userId);
    }


}

