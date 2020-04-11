package com.lnmj.data.business;

import com.lnmj.common.response.ResponseResult;


public interface IOrderService {
    ResponseResult selectList(String storeId,String date);
}
