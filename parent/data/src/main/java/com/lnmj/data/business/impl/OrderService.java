package com.lnmj.data.business.impl;

import com.lnmj.common.Enum.OrderTypeEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeOrderEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.business.IOrderService;
import com.lnmj.data.entity.VO.OrderNum;
import com.lnmj.data.serviceProxy.AccountApi;
import com.lnmj.data.serviceProxy.OrderApi;
import com.lnmj.data.serviceProxy.WalletApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2019/9/26
 */

@Service("orderService")
public class OrderService implements IOrderService {

    @Autowired
    private OrderApi orderApi;
    @Autowired
    private WalletApi walletApi;
    @Autowired
    private AccountApi accountApi;

    @Override
    public ResponseResult selectList(String storeId,String date) {
        //查询服务和产品订单
        List<Map<String, String>> result = (List<Map<String, String>>) orderApi.selectOrderListByToday(storeId,date).getResult();
        int productOrderNum = 0;
        int serviceOrderNum = 0;
        int customOrderNum = 0;
        int appointmentOrderNum = 0;
        int isNotVIP = 0;
        for (Map<String, String> map : result) {
            Object orderType = map.get("orderType");
            String cardNumber = (String) map.get("cardNumber");
            if ((int) orderType == OrderTypeEnum.PRODUCT_ORDER.getCode().intValue()) {
                //商品订单
                productOrderNum++;
            }
            if ((int) orderType == OrderTypeEnum.SERVICE_ORDER.getCode().intValue()) {
                //服务订单
                serviceOrderNum++;
            }
            if ((int) orderType == OrderTypeEnum.CUSTOM_ORDER.getCode().intValue()) {
                //定制订单
                customOrderNum++;
            }
            if ((int) orderType == OrderTypeEnum.APPOINTMENT_ORDER.getCode().intValue()) {
                //预约订单
                appointmentOrderNum++;
            }
            if (StringUtils.isBlank(cardNumber)) {
                //散客会员数
                isNotVIP++;
            }
        }
        OrderNum orderNum = new OrderNum();
        //查询充值订单数
        Object recharge = walletApi.selectRechargeOrderList(storeId,date).getResult();
        if (recharge == null) {
            orderNum.setRechargeOrder(0);
        } else {
            int rechargeNum = (int) recharge;
            orderNum.setRechargeOrder(rechargeNum);
        }
        //查询当日新建会员数
        int vipNum = (int) accountApi.countMemberByDay(date, storeId).getResult();
        orderNum.setProductOrder(productOrderNum);
        orderNum.setServiceOrder(serviceOrderNum);
        orderNum.setCustomOrder(customOrderNum);
        orderNum.setAppointmentOrder(appointmentOrderNum);
        orderNum.setIsNotVIP(isNotVIP);
        orderNum.setNewVIP(vipNum);
        return ResponseResult.success(orderNum);
    }
}
