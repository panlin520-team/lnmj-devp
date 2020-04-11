package com.lnmj.k3cloud.controller.backend.organization;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3-组织结构-组织用户维护")
@RestController
@RequestMapping("/k3cloud/organization/organizationUserWeiHu")
public class OrganizationUserWeiHuController {
    @Resource
    private IOrganizationService iOrganizationService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult organizationUserWeiHuSave(String acctId, String dataCentreUserName, String dataCentrePassword, String fOrgBaseId,String fUSERACCOUNT) {
        return iOrganizationService.organizationUserWeiHuSave(acctId,dataCentreUserName,dataCentrePassword,fOrgBaseId,fUSERACCOUNT);
    }

}
