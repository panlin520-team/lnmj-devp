package com.lnmj.store.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.store.entity.Orderwork;
import com.lnmj.store.repository.IOrderworkDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @Author panlin
 * @create 2019/5/28
 */

@Repository
public class OrderworkDaoImpl extends BaseDao implements IOrderworkDao {

    @Override
    public int addOrderwork(Orderwork orderwork) {
        return super.insert("orderwork.addOrderwork",orderwork);
    }

    @Override
    public Orderwork selectOrderwork(Orderwork orderwork) {
        return super.select("orderwork.selectOrderwork",orderwork);
    }

    @Override
    public int checkOrderwork(Orderwork orderwork) {
        return super.select("orderwork.checkOrderwork",orderwork);
    }

    @Override
    public int deleteOrderwork(Orderwork orderwork) {
        return super.delete("orderwork.deleteOrderwork",orderwork);
    }

    @Override
    public List<Orderwork> selectOrderworkAll(Map map) {
        return super.selectList("orderwork.selectOrderworkAll",map);
    }


}
