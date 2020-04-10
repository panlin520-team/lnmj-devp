package com.lnmj.wallet.controller.backend;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.business.IWalletAccountService;
import com.lnmj.wallet.entity.RechargeType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "账户管理接口")
@RestController
@RequestMapping("/manage/wallet")
public class AccountTypeManagerController {
    @Resource
    private IWalletAccountService iWalletAccountService;



    /**
    *@Description 账户管理查询
    *@Param [pageNum, pageSize, keyWord, accessToken]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/7/17
    *@Time 
    */
    @ApiOperation(value = "账户管理查询", notes = "账户管理查询")
    @RequestMapping(value = "/selectWalletAccountList", method = RequestMethod.POST)
    public ResponseResult selectWalletAccountList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,String keyWord, String accessToken) {
        return this.iWalletAccountService.selectWalletAccountList(pageNum, pageSize, keyWord);
    }

    /**
     *@Description 账户管理查询
     *@Param [pageNum, pageSize, keyWord, accessToken]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author Mr.Ren
     *@Date 2019/7/17
     *@Time
     */
    @ApiOperation(value = "账户管理查询", notes = "账户管理查询")
    @RequestMapping(value = "/selectWalletAccountListNoPage", method = RequestMethod.POST)
    public ResponseResult selectWalletAccountListNoPage() {
        return this.iWalletAccountService.selectWalletAccountListNoPage();
    }



    /**
    *@Description 添加账户
    *@Param [rechargeRecordId, ids, accessToken]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/7/17
    *@Time 
    */
    @ApiOperation(value = "添加账户", notes = "添加账户")
    @RequestMapping(value = "/addWalletAccount", method = RequestMethod.POST)
    public ResponseResult addWalletAccount(RechargeType account, String accessToken) {
        return iWalletAccountService.addWalletAccount(account);
    }


    /**
    *@Description 修改账户
    *@Param [pageNum, pageSize, cardNumber, accessToken]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/7/17
    *@Time 
    */
    @ApiOperation(value = "修改账户", notes = "修改账户")
    @RequestMapping(value = "/updateWalletAccount", method = RequestMethod.POST)
    public ResponseResult updateWalletAccount(RechargeType account, String accessToken) {
        return this.iWalletAccountService.updateWalletAccount(account);
    }

    /**
    *@Description 删除账户
    *@Param [consumeRecordId, ids, accessToken]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/7/17
    *@Time
    */
    @ApiOperation(value = "删除账户", notes = "删除账户")
    @RequestMapping(value = "/deleteWalletAccount", method = RequestMethod.POST)
    public ResponseResult deleteWalletAccount(Long accountId, String accessToken) {
        return iWalletAccountService.deleteWalletAccount(accountId);
    }

    /**
    *@Description 查询小类所允许的账户支付类型
    *@Param [subClassId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019-11-12
    *@Time 10:42
    */
    @ApiOperation(value = "查询小类所允许的账户支付类型", notes = "查询小类所允许的账户支付类型")
    @RequestMapping(value = "/selectWallietAccountBySubClass", method = RequestMethod.POST)
    public ResponseResult selectWalletAccountBySubClass(String subClassId) {
        return iWalletAccountService.selectWalletAccountBySubClass(subClassId);
    }


}
