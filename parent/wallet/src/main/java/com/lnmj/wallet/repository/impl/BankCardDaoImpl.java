package com.lnmj.wallet.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.wallet.repository.BankCardDao;
import org.springframework.stereotype.Repository;


@Repository
public class BankCardDaoImpl extends BaseDao implements BankCardDao {

    @Override
    public Long selectBankCardId(String cardNumber) {
        return super.select("bankCard.selectBankCardId");
    }
}
