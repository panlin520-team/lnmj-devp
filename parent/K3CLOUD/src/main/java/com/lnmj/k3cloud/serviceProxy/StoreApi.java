package com.lnmj.k3cloud.serviceProxy;


import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
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
public interface StoreApi {
    @ApiOperation(value = "查询总公司不分页",notes = "查询总公司不分页")
    @RequestMapping(value = "/manage/company/selectCompanyListNoPage",method = RequestMethod.POST)
     ResponseResult selectCompanyListNoPage();
}

