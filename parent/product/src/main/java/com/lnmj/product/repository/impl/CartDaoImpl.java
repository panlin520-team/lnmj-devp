package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.Cart;
import com.lnmj.product.repository.ICartDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: panlin
 * @Date: 2019/5/8 16:17
 * @Description:
 */
@Repository
public class CartDaoImpl extends BaseDao implements ICartDao {
    @Override
    public int insert(Cart cart) {
        return super.insert("cart.insert", cart);
    }

    @Override
    public List<Cart> selectCartByUserId(Long userId) {
        return super.selectList("cart.selectCartByUserId", userId);
    }

    @Override
    public int updateByPrimaryKeySelective(Cart cartForQuantity) {
        return super.update("cart.updateByPrimaryKeySelective", cartForQuantity);
    }

    @Override
    public int updateByPrimaryKey(Cart cartForQuantity) {
        return super.update("cart.updateByPrimaryKey", cartForQuantity);
    }

    @Override
    public int deleteByUserIdProductIds(Cart cart) {
        return super.delete("cart.deleteByUserIdProductIds", cart);
    }

    @Override
    public int checkedOrUncheckedProduct(Cart cart) {
        return super.delete("cart.checkedOrUncheckedProduct", cart);
    }

    @Override
    public int selectCartProductCount(Long userId) {
        return super.select("cart.selectCartProductCount", userId);
    }


    @Override
    public int selectCartProductCheckedStatusByUserId(Long userId) {
        return super.select("cart.selectCartProductCheckedStatusByUserId", userId);
    }

    @Override
    public Cart selectCartByUserIdProductId(Cart cart) {
        return super.select("cart.selectCartByUserIdProductId", cart);
    }


}
