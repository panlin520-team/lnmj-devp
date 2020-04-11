package com.lnmj.system.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.*;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeMakerEnum;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.EnumUtil;
import com.lnmj.common.utils.NumberUtils;
import com.lnmj.system.business.IMakerOrderService;
import com.lnmj.system.entity.MakerOrder;
import com.lnmj.system.entity.MakerOrderUse;
import com.lnmj.system.repository.IMakerOrderDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.lnmj.common.response.Error;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/8/26 10:04
 * @Description:
 */
@Service("makerOrderService")
public class MakerOrderService implements IMakerOrderService {
    @Resource
    private IMakerOrderDao makerOrderDao;

    @Override
    public ResponseResult selectMakerOrderList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<MakerOrder> makerOrderList = makerOrderDao.selectMakerOrderList();
        if(makerOrderList.size()>0){
            PageInfo<MakerOrder> pageInfo = new PageInfo<>(makerOrderList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_ORDER_NULL.getCode(),
                ResponseCodeMakerEnum.MAKER_ORDER_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectMakerOrderByCondition(int pageNum, int pageSize, MakerOrder makerOrder) {
        PageHelper.startPage(pageNum,pageSize);
        List<MakerOrder> makerOrderList = makerOrderDao.selectMakerOrderByCondition(makerOrder);
        if(makerOrderList.size()>0){
            PageInfo<MakerOrder> pageInfo = new PageInfo<>(makerOrderList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_ORDER_NULL.getCode(),
                ResponseCodeMakerEnum.MAKER_ORDER_NULL.getDesc()));
    }

    @Override
    public ResponseResult insertMakerOrder(MakerOrder makerOrder) {
        //订单号
        makerOrder.setMakerOrderCode(NumberUtils.getRandomOrderNo());
        //修改人
        if(StringUtils.isBlank(makerOrder.getModifyOperator())){
            makerOrder.setModifyOperator(makerOrder.getCreateOperator());
        }
        //订单状态
        if(makerOrder.getOrderStatus()==null){
            makerOrder.setOrderStatus(OrderStatusEnum.UNPAID.getCode());
        }
        makerOrderDao.insertMakerOrder(makerOrder);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateMakerOrder(MakerOrder makerOrder) {
        makerOrderDao.updateMakerOrder(makerOrder);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteMakerOrder(Long makerOrderId, String modifyOperator) {
        MakerOrder makerOrder = new MakerOrder();
        makerOrder.setMakerOrderId(makerOrderId);
        makerOrder.setModifyOperator(modifyOperator);
        makerOrderDao.deleteMakerOrder(makerOrder);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectOrderStatusEnum(String name) {
        if("OrderStatusEnum".equals(name)){
            return ResponseResult.success(EnumUtil.getEnumToMap(OrderStatusEnum.class));
        }
        if("ProductStatusEnum".equals(name)){
            return ResponseResult.success(EnumUtil.getEnumToMap(ProductStatusEnum.class));
        }
        if("DeliveryMethodEnum".equals(name)){
            return ResponseResult.success(EnumUtil.getEnumToMap(DeliveryMethodEnum.class));
        }
        if("MakerProductUseStatusEnum".equals(name)){
            return ResponseResult.success(EnumUtil.getEnumToMap(MakerProductUseStatusEnum.class));
        }
        if("OrderTypeEnum".equals(name)){
            return ResponseResult.success(EnumUtil.getEnumToMap(OrderTypeEnum.class));
        }
        if("PayTypeEnum".equals(name)){
            return ResponseResult.success(EnumUtil.getEnumToMap(PayTypeEnum.class));
        }
        return ResponseResult.error(new Error(ResponseCodeMakerEnum.ENUM_NAME_ERROR.getCode(),
                ResponseCodeMakerEnum.ENUM_NAME_ERROR.getDesc()));
    }

    @Override
    public ResponseResult selectMakerOrderUseByCondition(int pageNum, int pageSize, MakerOrderUse makerOrderUse) {
        PageHelper.startPage(pageNum,pageSize);
        List<MakerOrderUse> makerOrderUseList = makerOrderDao.selectMakerOrderUseByCondition(makerOrderUse);
        if(makerOrderUseList.size()>0){
            PageInfo<MakerOrderUse> pageInfo = new PageInfo<>(makerOrderUseList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_ORDER_USE_NULL.getCode(),
                ResponseCodeMakerEnum.MAKER_ORDER_USE_NULL.getDesc()));
    }

    @Override
    public ResponseResult insertMakerOrderUse(MakerOrderUse makerOrderUse) {
        if(makerOrderUse.getProductUseStatus() == null){
            if(makerOrderUse.getBookingTime()!=null ||
                    makerOrderUse.getBookingBeauticianIds() != null ){
                //使用状态(预约状态)
                makerOrderUse.setProductUseStatus(MakerProductUseStatusEnum.APPOINTMENT.getCode());
            }else if( !StringUtils.isBlank(makerOrderUse.getReceiverAddress()) && StringUtils.isBlank(makerOrderUse.getDeliveryNumber())){
                //使用状态(物流状态)
                makerOrderUse.setProductUseStatus(MakerProductUseStatusEnum.TO_SEND.getCode());
            }else if( !StringUtils.isBlank(makerOrderUse.getReceiverAddress()) && !StringUtils.isBlank(makerOrderUse.getDeliveryNumber())){
                //使用状态(物流状态)
                makerOrderUse.setProductUseStatus(MakerProductUseStatusEnum.SEND.getCode());
            }
        }
        makerOrderDao.insertMakerOrderUse(makerOrderUse);
        return ResponseResult.success();
    }
}
