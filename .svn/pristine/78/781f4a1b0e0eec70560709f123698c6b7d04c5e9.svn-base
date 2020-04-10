package com.lnmj.data.serviceProxy;

import com.lnmj.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: yilihua
 * @Date: 2019/9/6 17:21
 * @Description:
 */
@FeignClient(value = "lnmj-paySettlement")
public interface PayApi {
    @RequestMapping(value = "/manage/payment/selectPayTypeById", method = RequestMethod.POST)
    ResponseResult  selectPayTypeById(@RequestParam("payTypeId")Long payTypeId);
    @RequestMapping(value = "/manage/payment/selectPayTypeListAll", method = RequestMethod.POST)
    ResponseResult  selectPayTypeListAll();
}
