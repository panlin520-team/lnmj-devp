package com.lnmj.k3cloud.controller.backend.product;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IInventoryTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "存货类别")
@RestController
@RequestMapping("/k3cloud/product/inventoryType")
public class InventoryTypeController {
    @Resource
    private IInventoryTypeService iInventoryTypeService;


    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult save(String acctId ,String dataCentreUserName,String dataCentrePassword,String FName,String FNumber,String FNFCreateOrgId,String FUseOrgId) {
        return iInventoryTypeService.save(acctId ,dataCentreUserName,dataCentrePassword,FName,FNumber,FNFCreateOrgId,FUseOrgId);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(String acctId ,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        return iInventoryTypeService.delete(acctId ,dataCentreUserName,dataCentrePassword,numbers, ids);
    }

    @ApiOperation(value = "查看", notes = "查看")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseResult view(String acctId ,String dataCentreUserName,String dataCentrePassword,String number, String id) {
        return iInventoryTypeService.view(acctId ,dataCentreUserName,dataCentrePassword,number, id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submit(String acctId ,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        return iInventoryTypeService.submit(acctId ,dataCentreUserName,dataCentrePassword,numbers, ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult audit(String acctId ,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        return iInventoryTypeService.audit(acctId ,dataCentreUserName,dataCentrePassword,numbers, ids);
    }

    @ApiOperation(value = "反审核", notes = "审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAudit(String acctId ,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        return iInventoryTypeService.unAudit(acctId ,dataCentreUserName,dataCentrePassword,numbers, ids);
    }

    @ApiOperation(value = "分配", notes = "分配")
    @RequestMapping(value = "/allocate", method = RequestMethod.POST)
    public ResponseResult allocate(String acctId ,String dataCentreUserName,String dataCentrePassword,String PkIds, String TOrgIds) {
        return iInventoryTypeService.allocate(acctId ,dataCentreUserName,dataCentrePassword,PkIds, TOrgIds);
    }
}
