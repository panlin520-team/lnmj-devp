package com.lnmj.account.serviceProxy.controller;


import com.lnmj.account.business.IMemberUserService;
import com.lnmj.account.entity.MemberUser;
import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: panlin
 * @Date: 2019/9/24 11:34
 * @Description: 会员体系controller
 */
@Api(description = "门店用户会员体系api")
@RestController
@RequestMapping("/api/manage/member")
public class MemberManageService {
    @Resource
    private IMemberUserService iMemberUserService;

    @ApiOperation(value = "通过时间或者门店查看会员账户信息", notes = "通过时间或者门店查看会员账户信息")
    @RequestMapping(value = "/countMemberByDay", method = RequestMethod.POST)
    public ResponseResult  countMemberByDay(String time,String storeId) {
        return this.iMemberUserService.CountMemberByDay(time,storeId);
    }

    @ApiOperation(value = "修改门店会员信息", notes = "修改门店会员信息")
    @RequestMapping(value = "/updateStoreMember", method = RequestMethod.POST)
    public ResponseResult  updateStoreMember(MemberUser storeMember) {
        return this.iMemberUserService.updateMemberUser(storeMember);
    }

    @ApiOperation(value = "根据会员编号查看会员", notes = "根据会员编号查看会员")
    @RequestMapping(value = "/selectMemberByNum", method = RequestMethod.POST)
    public ResponseResult  selectMemberByNum(String memberNum) {
        return this.iMemberUserService.selectMemberByNum(memberNum);
    }


    @ApiOperation(value = "查看账户信息", notes = "查看账户信息")
    @RequestMapping(value = "/listMemberAccountNoPage", method = RequestMethod.POST)
    public ResponseResult selectStoreMemberAccountNoPage(String memberNum,String subClassIds) {
        return this.iMemberUserService.selectStoreMemberAccountNoPage(memberNum,subClassIds);
    }
}
