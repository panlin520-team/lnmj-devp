package com.lnmj.account.controller.backend;

import com.github.pagehelper.PageInfo;
import com.lnmj.account.business.IRecipientAddressService;
import com.lnmj.account.entity.RecipientAddress;
import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther: panlin
 * @Date: 2019/4/23 14:15
 * @Description: 商城用户收货地址管理
 */
@Api(description = "会员收货地址接口")
@RestController
@RequestMapping("/manage/address")
public class MemberAddressManageController {
    @Resource
    private IRecipientAddressService recipientAddressService;

    @ApiOperation(value = "获取用户收货地址列表(分页)", notes = "获取用户收货地址列表(分页)")
    @RequestMapping(value = "/listByPage", method = RequestMethod.POST)
    public ResponseResult<PageInfo> listByPage(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                               String memberNum) {
        return recipientAddressService.listByPage(memberNum, pageNum, pageSize);
    }


    @ApiOperation(value = "设置默认地址", notes = "设置默认地址")
    @RequestMapping(value = "/setDefault", method = RequestMethod.POST)
    public ResponseResult<PageInfo> setDefault(String memberNum, Long recipientAddressId, String modifyOperator) {
        return recipientAddressService.setDefault(memberNum, recipientAddressId, modifyOperator);
    }

    @ApiOperation(value = "修改用户收货地址", notes = "删除用户收货地址")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(String memberNum, RecipientAddress recipientAddress) {
        return recipientAddressService.update(memberNum, recipientAddress);
    }

    @ApiOperation(value = "添加用户收货地址", notes = "添加用户收货地址")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(String memberNum, RecipientAddress recipientAddress) {
        return recipientAddressService.add(memberNum, recipientAddress);
    }


    @ApiOperation(value = "删除用户收货地址", notes = "删除用户收货地址")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(String memberNum, Long recipientAddressId) {
        return recipientAddressService.delete(memberNum, recipientAddressId);
    }


}
