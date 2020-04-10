package com.lnmj.system.repository;

import com.lnmj.system.entity.MakerOrder;
import com.lnmj.system.entity.MakerOrderUse;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/8/24 15:57
 * @Description:  创客订单
 */
public interface IMakerOrderDao {

    List<MakerOrder> selectMakerOrderList();

    List<MakerOrder> selectMakerOrderByCondition(MakerOrder makerOrder);

    int insertMakerOrder(MakerOrder makerOrder);

    int updateMakerOrder(MakerOrder makerOrder);

    int deleteMakerOrder(MakerOrder makerOrder);

    List<MakerOrderUse> selectMakerOrderUseList();

    List<MakerOrderUse> selectMakerOrderUseByCondition(MakerOrderUse makerOrderUse);

    int insertMakerOrderUse(MakerOrderUse makerOrderUse);

    int updateMakerOrderUse(MakerOrderUse makerOrderUse);

    int deleteMakerOrderUse(MakerOrderUse makerOrderUse);
}
