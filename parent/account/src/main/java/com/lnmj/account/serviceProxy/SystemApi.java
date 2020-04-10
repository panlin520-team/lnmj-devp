package com.lnmj.account.serviceProxy;

import com.lnmj.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "lnmj-system")
public interface SystemApi {
    @RequestMapping(value = "/manage/system/selectParameterList", method = RequestMethod.POST)
    ResponseResult selectParameterList(@RequestParam("parameterTypeId") Long parameterTypeId);

    @RequestMapping(value = "/manage/system/selectParameterByWhere", method = RequestMethod.POST)
    ResponseResult selectParameterByWhere(@RequestParam("parameterTypeId") Long parameterTypeId);


}

