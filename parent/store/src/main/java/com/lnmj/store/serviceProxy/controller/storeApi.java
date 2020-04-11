package com.lnmj.store.serviceProxy.controller;


import com.lnmj.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: yilihua
 * @Date: 2019/5/24 17:57
 * @Description: 
 */
@FeignClient(name = "lnmj-store")
public interface storeApi {


    @RequestMapping(value = "/manage/payment/selectPayTypeById", method = RequestMethod.POST)
     ResponseResult selectCompanyByID(@RequestParam("payTypeId") Long payTypeId) ;

}

