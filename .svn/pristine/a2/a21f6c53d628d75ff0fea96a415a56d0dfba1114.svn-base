package com.lnmj.store.business;

import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("orderworkService")
public interface IOrderworkService {
    ResponseResult addOrderwork(Long beauticianId, Date orderworkDate,String allTimeNodes, String busyTimeNodes, String restTimeNodes);
    ResponseResult selectOrderwork(Long beauticianId, Date orderworkDate);
    ResponseResult updateOrderwork(Long beauticianId, Date orderworkDate,String leisureTimeNodes, String busyTimeNodes, String restTimeNodes);
    ResponseResult deleteOrderwork(Long beauticianId, Date orderworkDate);
    ResponseResult selectOrderworkAll(Long storeId);
}
