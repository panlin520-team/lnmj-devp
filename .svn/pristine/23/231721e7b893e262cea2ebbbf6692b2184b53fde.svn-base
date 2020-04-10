package com.lnmj.system.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.entity.MakerOrder;
import com.lnmj.system.entity.MakerOrderUse;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/8/26 09:58
 * @Description: 创客订单
 */
@Service("iMakerOrderService")
public interface IMakerOrderService {

    ResponseResult selectMakerOrderList(int pageNum, int pageSize);

    ResponseResult selectMakerOrderByCondition(int pageNum, int pageSize, MakerOrder makerOrder);

    ResponseResult insertMakerOrder(MakerOrder makerOrder);

    ResponseResult updateMakerOrder(MakerOrder makerOrder);

    ResponseResult deleteMakerOrder(Long makerOrderId, String modifyOperator);

    ResponseResult selectOrderStatusEnum(String name);

    ResponseResult selectMakerOrderUseByCondition(int pageNum, int pageSize, MakerOrderUse makerOrderUse);

    ResponseResult insertMakerOrderUse(MakerOrderUse makerOrderUse);
}
