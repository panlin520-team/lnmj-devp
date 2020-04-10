package com.lnmj.product.repository;

import com.lnmj.product.entity.Cart;

import java.util.List;

/**
 * @Author: panlin
 * @Date: 2019/5/8 16:15
 * @Description: 商品Dao
 */
public interface ICartDao {
    List<Cart> selectCartByUserId(Long userId);

    int insert(Cart cart);

    int updateByPrimaryKeySelective(Cart cartForQuantity);

    int updateByPrimaryKey(Cart cartForQuantity);

    int deleteByUserIdProductIds(Cart cart);

    int checkedOrUncheckedProduct(Cart cart);

    int selectCartProductCount(Long userId);


    int selectCartProductCheckedStatusByUserId(Long userId);

    Cart selectCartByUserIdProductId(Cart cart);


}
