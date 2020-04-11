package com.lnmj.data.serviceProxy;

import com.lnmj.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "lnmj-system")
public interface SystemApi {
    @RequestMapping(value = "/manage/system/selectParameterByWhere", method = RequestMethod.POST)
    ResponseResult selectParameterByWhere(@RequestParam("parameterId") Long parameterId);


}

