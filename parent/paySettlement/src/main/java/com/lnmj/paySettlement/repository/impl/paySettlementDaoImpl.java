package com.lnmj.paySettlement.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.paySettlement.entity.PayType;
import com.lnmj.paySettlement.repository.IPaySettlementDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 〈〉
 *
 * @Author panlin
 * @create 2019/5/8
 */

@Repository
public class paySettlementDaoImpl extends BaseDao implements IPaySettlementDao {


    @Override
    public List<PayType> selectPayTypeList() {
        return super.selectList("pay.selectPayTypeList");
    }

    @Override
    public Integer deletePayType(Long payTypeId) {
        return super.delete("pay.deletePayType",payTypeId);
    }



    @Override
    public Integer insertPayType(PayType payType) {
        return insert("pay.insertPayType",payType);
    }

    @Override
    public PayType selectPayTypeById(Long payTypeId) {
        return select("pay.selectPayTypeById",payTypeId);
    }

    @Override
    public PayType selectPayTypeByAccountType(Long accountType) {
        return select("pay.selectPayTypeByAccountType",accountType);
    }

    @Override
    public int updatePayType(PayType payType) {
        return update("pay.updatePayType",payType);
    }

    @Override
    public List<PayType> selectPayTypeListBySubClass() {
        return selectList("pay.selectPayTypeListBySubClass");
    }
}
