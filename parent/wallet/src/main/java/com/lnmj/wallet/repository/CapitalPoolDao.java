package com.lnmj.wallet.repository;


import com.lnmj.wallet.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CapitalPoolDao {
    int addCapitalPool(CapitalPool capitalPool);
    int updateCapitalPool(CapitalPool capitalPool);
    int addTransfer(Transfer transfer);
    List<CapitalPool>  selectCapitalPoolList(CapitalPool capitalPool);
    List<Transfer>  selectTransfer(Map map);
    int  reduceForwardSaleByCondition(CapitalPool capitalPool);
    int  deleteCapPoolByOrderNumber(CapitalPool capitalPool);
}
