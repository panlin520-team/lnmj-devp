package com.lnmj.store.serviceProxy;


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
@FeignClient(name = "lnmj-authority")
public interface AuthApi {
    @RequestMapping(value = "/admin/user/deleteuserByCompany", method = RequestMethod.POST)
    ResponseResult deleteuserByCompany(@RequestParam("companyId") Long companyId,@RequestParam("companyType") Long companyType);
}

