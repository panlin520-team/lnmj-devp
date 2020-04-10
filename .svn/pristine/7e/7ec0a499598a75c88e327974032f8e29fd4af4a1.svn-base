package com.lnmj.account.controller.backend;

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
import java.util.List;

/**
 * @Author: panlin
 * @Date: 2019/9/24 11:34
 * @Description: 会员体系controller
 */
@Api(description = "会员管理")
@RestController
@RequestMapping("/manage/memberUser")
public class MemberUserManageController {
    @Resource
    private IMemberUserService iMemberUserService;

    @ApiOperation(value = "查询会员列表", notes = "查询会员列表")
    @RequestMapping(value = "/listByPage", method = RequestMethod.POST)
    public ResponseResult listByPage(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,String storeId,String mobile, String name,
                                            Integer newOldStatus,Integer isLost) {
        return this.iMemberUserService.selectMemberUser(pageNum,pageSize,storeId,mobile,name,newOldStatus,isLost);
    }

    @ApiOperation(value = "查询会员列表", notes = "查询会员列表")
    @RequestMapping(value = "/listByNoPage", method = RequestMethod.POST)
    public ResponseResult listByPage() {
        return this.iMemberUserService.selectMemberUserNoPage();
    }

    @ApiOperation(value = "根据电话或名称查询", notes = "根据电话或名称查询")
    @RequestMapping(value = "/selectStoreMemberByPhoneOrName", method = RequestMethod.POST)
    public ResponseResult selectStoreMemberByPhoneOrName(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,String memberNum,String storeId,String mobile, String name) {
        return this.iMemberUserService.selectMemberUserByPhoneOrName(pageNum,pageSize,storeId,mobile,name);
    }

    @ApiOperation(value = "修改会员信息", notes = "修改会员信息")
    @RequestMapping(value = "/updateStoreMember", method = RequestMethod.POST)
    public ResponseResult  updateStoreMember(MemberUser memberUser) {
        return this.iMemberUserService.updateMemberUser(memberUser);
    }

    @ApiOperation(value = "查看账户信息", notes = "查看账户信息")
    @RequestMapping(value = "/listMemberAccount", method = RequestMethod.POST)
    public ResponseResult selectStoreMemberAccount(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "0") int pageSize,String memberNum,String subClassIds,Long industry) {
        return this.iMemberUserService.selectMemberUserAccount(pageNum,pageSize,memberNum,subClassIds,industry);
    }

    @ApiOperation(value = "添加会员信息（门店手动添加）", notes = "添加会员信息（门店手动添加）")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult addStoreMember(MemberUser memberUser) {
        return this.iMemberUserService.addMemberUser(memberUser);
    }

    @ApiOperation(value = "批量导入会员信息", notes = "批量导入会员信息")
    @RequestMapping(value = "/batchaddStoreMember", method = RequestMethod.POST)
    public ResponseResult batchaddStoreMember(List<MemberUser> memberUserList) {
        return this.iMemberUserService.batchaddStoreMember(memberUserList);
    }

    @ApiOperation(value = "查询会员进店渠道", notes = "查询会员进店渠道")
    @RequestMapping(value = "/selectEnterChannel", method = RequestMethod.POST)
    public ResponseResult selectEnterChannel() {
        return this.iMemberUserService.selectEnterChannel();
    }

    @ApiOperation(value = "会员密码修改", notes = "会员密码修改")
    @RequestMapping(value = "/resetMemberPassword", method = RequestMethod.POST)
    public ResponseResult resetMemberPassword(String memberNum, String passwordOld, String passwordNew) {
        return this.iMemberUserService.resetMemberPassword(memberNum,passwordOld,passwordNew);
    }
}
