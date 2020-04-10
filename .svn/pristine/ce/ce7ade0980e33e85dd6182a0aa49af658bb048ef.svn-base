/*
package com.lnmj.account.controller.portal.mall;


import com.lnmj.account.entity.Account;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAccountEnum;
import com.lnmj.common.cache.CacheForSet;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.MailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

*/
/**
 * @Auther: panlin
 * @Date: 2019/4/18 11:34
 * @Description:用户信息管理
 *//*

@Api(description = "用户接口")
@RestController
@RequestMapping("/account")
public class MallAccountController {
    @Resource
    private IAccountService accountService;
    @Resource
    private MailUtil mailUtil;
    @Resource
    private IAccountDao accountDao;


    @ApiOperation(value = "用户注册", notes = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseResult register(Account account, @RequestParam Integer registerType, String validCode) {
        return this.accountService.register(account, registerType, validCode);
    }


    @ApiOperation(value = "自动注册", notes = "自动注册")
    @RequestMapping(value = "/selfRegister", method = RequestMethod.POST)
    public ResponseResult selfRegister(@RequestParam String mobile,@RequestParam String password,@RequestParam Integer registerType, @RequestParam String validCode) {
        Account account = new Account();
        account.setMobile(mobile);
        account.setPassword(password);
        return this.accountService.register(account, registerType, validCode);
    }


    @ApiOperation(value = "用户登录", notes = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult login(String validCode, @RequestParam("username") String username, @RequestParam("password") String password, Integer loginType) {
        return this.accountService.login(validCode, username, password, loginType);
    }

    @ApiOperation(value = "退出登录", notes = "用户登录")
    @RequestMapping(value = "/loginout", method = RequestMethod.POST)
    public ResponseResult loginout(String access_token, String refresh_token) {
        boolean isMmeber = CacheForSet.isMember("access_token", access_token);
        if (!isMmeber) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.TOKEN_NULL.getCode(), ResponseCodeAccountEnum.TOKEN_NULL.getDesc()));
        }
        CacheForSet.remove("access_token", access_token);
        CacheForSet.remove("refresh_token", refresh_token);
        return ResponseResult.success();
    }


    @ApiOperation(value = "获取修改密码的token", notes = "获取修改密码的token")
    @RequestMapping(value = "/checkValid", method = RequestMethod.POST)
    public ResponseResult checkValid(String validCode, String mobileOrEmail) {
        return this.accountService.checkValid(validCode, mobileOrEmail);
    }

    @ApiOperation(value = "忘记密码", notes = "忘记密码")
    @RequestMapping(value = "/forgetResetPassword", method = RequestMethod.POST)
    public ResponseResult forgetResetPassword(String forgetToken, String account, String newPassword) {
        return this.accountService.forgetResetPassword(forgetToken, account, newPassword);
    }

    @ApiOperation(value = "重置密码", notes = "重置密码")
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ResponseResult resetPassword(Long userId, String passwordOld, String passwordNew,String modifyOperator) {

        return this.accountService.resetPassword(userId, passwordOld, passwordNew,modifyOperator);
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    @RequestMapping(value = "updateInformation", method = RequestMethod.POST)
    public ResponseResult<Account> updateInformation(String access_token, Account account) {
        if (StringUtils.isBlank(access_token)) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.NEED_LOGIN.getCode(), ResponseCodeAccountEnum.NEED_LOGIN.getDesc()));
        }
        return accountService.updateInformation(account);
    }

    @ApiOperation(value = "获取用户微信二维码", notes = "获取用户微信二维码")
    @RequestMapping(value = "selectAccountQrcode", method = RequestMethod.POST)
    public ResponseResult selectAccountQrcode(String account) {
        ResponseResult response = accountService.selectAccountQrcode(account);
        return response;
    }


    @ApiOperation(value = "发送邮箱验证码", notes = "发送邮箱验证码")
    @RequestMapping(value = "sendEmailAuthentication", method = RequestMethod.POST)
    public ResponseResult sendEmailAuthentication(String email) {
        return accountService.sendEmailAuthentication(email);
    }


    @ApiOperation(value = "邮箱验证码验证", notes = "邮箱验证码验证")
    @RequestMapping(value = "emailAuthentication", method = RequestMethod.POST)
    public ResponseResult emailAuthentication(String code, String email, Long userId) {
        return accountService.emailAuthentication(code, email, userId);
    }


    @ApiOperation(value = "发送手机验证码", notes = "发送手机验证码")
    @RequestMapping(value = "sendMobileCode", method = RequestMethod.POST)
    public ResponseResult sendMobileCode(String mobile) {
        return accountService.sendMobileCode(mobile);
    }


    @ApiOperation(value = "刷新token", notes = "刷新token")
    @RequestMapping(value = "refreshToken", method = RequestMethod.POST)
    public ResponseResult refreshToken(String mobile) {
        return null;
    }


}
*/
