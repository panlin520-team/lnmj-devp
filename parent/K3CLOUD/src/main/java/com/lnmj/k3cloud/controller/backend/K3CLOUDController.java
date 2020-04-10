package com.lnmj.k3cloud.controller.backend;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IK3CLOUDService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3")
@RestController
@RequestMapping("/k3cloud")
public class K3CLOUDController {
    @Resource
    private IK3CLOUDService k3CLOUDService;

    /**
    *@Description 登录
    *@Param [acctID, userName, password, lcid, accessToken]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019-11-09
    *@Time 11:38
    */
    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult k3Login(String acctID,String userName,String password,String lcid, String accessToken) {
        return k3CLOUDService.k3Login(acctID,userName,password,lcid);
    }


}
