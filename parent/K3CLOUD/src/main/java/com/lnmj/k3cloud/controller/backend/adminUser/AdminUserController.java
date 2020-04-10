package com.lnmj.k3cloud.controller.backend.adminUser;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.AccountBookService;
import com.lnmj.k3cloud.business.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3-管理员")
@RestController
@RequestMapping("/k3cloud/adminUser/adminUser")
public class AdminUserController {
    @Resource
    private AdminUserService adminUserService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/saveAdminUser", method = RequestMethod.POST)
    public ResponseResult saveAdminUser(
            String acctId,
            String dataCentreUserName,
            String dataCentrePassword,
            String fUserAccount,
            String fName
    ) {
        return adminUserService.saveAdminUser(acctId, dataCentreUserName, dataCentrePassword, fUserAccount, fName);
    }

    @ApiOperation(value = "通过id保存user", notes = "通过id保存user")
    @RequestMapping(value = "/saveAdminUserById", method = RequestMethod.POST)
    public ResponseResult saveAdminUserById(
            String acctId,
            String dataCentreUserName,
            String dataCentrePassword,
            String fUserID
    ) {
        return adminUserService.saveAdminUserById(acctId, dataCentreUserName, dataCentrePassword, fUserID);
    }

    @ApiOperation(value = "将组织分配给用户", notes = "将组织分配给用户")
    @RequestMapping(value = "/orgToUser", method = RequestMethod.POST)
    public ResponseResult orgToUser(String dataCentreName,String userId,String orgId) {
        return adminUserService.orgToUser(dataCentreName,userId,orgId);
    }

}
