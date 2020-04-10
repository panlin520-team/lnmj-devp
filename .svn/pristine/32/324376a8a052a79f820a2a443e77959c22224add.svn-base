package com.lnmj.k3cloud.controller.backend.product;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IStockService;
import com.lnmj.k3cloud.business.IUnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3-物资-单位")
@RestController
@RequestMapping("/k3cloud/product/unit")
public class UnitController {
    @Resource
    private IUnitService unitService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult save(String acctId,String dataCentreUserName,String dataCentrePassword,String fname,String groupId,String froundtype,Integer id,Boolean isautosubmitandaudit){
        return unitService.saveUnit(acctId,dataCentreUserName,dataCentrePassword,fname,groupId,froundtype,id,isautosubmitandaudit);
    }

    @ApiOperation(value = "查看", notes = "查看")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseResult view(String acctId,String dataCentreUserName,String dataCentrePassword,String number,String id){
        return unitService.viewUnit(acctId,dataCentreUserName,dataCentrePassword,number,id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids){
        return unitService.submitUnit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult audit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids){
        return unitService.auditUnit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "反审核", notes = "反审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids){
        return unitService.unAuditUnit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids){
        return unitService.deleteUnit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "禁用", notes = "禁用")
    @RequestMapping(value = "/forbid", method = RequestMethod.POST)
    public ResponseResult forbid(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids){
        return unitService.forbidUnit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "反禁用", notes = "反禁用")
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public ResponseResult enable(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids){
        return unitService.enableUnit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

}
