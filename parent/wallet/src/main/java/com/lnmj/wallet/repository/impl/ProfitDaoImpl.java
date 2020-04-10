package com.lnmj.wallet.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.wallet.entity.*;
import com.lnmj.wallet.repository.IProfitDao;
import com.lnmj.wallet.repository.WalletDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class ProfitDaoImpl extends BaseDao implements IProfitDao {

    @Override
    public int addProfit(Profit profit) {
        return super.insert("profit.addProfit",profit);
    }
}
