package com.lnmj.account.serviceProxy;


import com.lnmj.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: panlin
 * @Date: 2019/4/19 15:35
 * @Description:
 */

@FeignClient(name = "lnmj-authority")
public interface AuthorityApi {
    @RequestMapping(value = "/manager/authority/selectTMenu", method = RequestMethod.GET)
    ResponseResult selectTMenu(@RequestParam("pId")  Integer pId);

}

