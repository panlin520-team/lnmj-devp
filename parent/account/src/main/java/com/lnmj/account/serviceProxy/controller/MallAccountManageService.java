/*
package com.lnmj.account.serviceProxy.controller;

import com.github.pagehelper.PageInfo;
import com.lnmj.account.business.IAccountService;
import com.lnmj.account.entity.Account;
import com.lnmj.account.entity.VO.AccountVO;
import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

*/
/**
 * @Auther: panlin
 * @Date: 2019/4/23 13:29
 * @Description: 商城用户会员信息后台管理
 *//*

@Api(description = "后台商城用户管理接口api")
@RestController
@RequestMapping("/api/manage/mall/account")
@CrossOrigin
public class MallAccountManageService {
    @Resource
    private IAccountService accountService;

    @ApiOperation(value = "根据id查询用户", notes = "根据id查询用户")
    @RequestMapping(value = "/selectAccountById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<PageInfo> selectAccountList(Long userId) {
        ResponseResult<PageInfo> response = accountService.selectAccountById(userId);
        return response;
    }

    @ApiOperation(value = "根据条件查看用户", notes = "根据条件查看用户")
    @RequestMapping(value = "/selectAccountByCondition", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<PageInfo> selectAccountByCondition(String mobile,String account,String name, String cardNumber) {
        ResponseResult<PageInfo> response = accountService.selectAccountByCondition(mobile,account,name,cardNumber);
        return response;
    }
}
*/
