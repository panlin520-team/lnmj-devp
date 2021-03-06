package com.lnmj.k3cloud.controller.backend.employees;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IEmployeesService;
import com.lnmj.k3cloud.business.ISupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3-员工-员工")
@RestController
@RequestMapping("/k3cloud/employees/employees")
public class EmployeesController {
    @Resource
    private IEmployeesService iEmployeesService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult save(
            String acctId,
            String dataCentreUserName,String dataCentrePassword,
            String FName,
            String FCreateOrgId,
            String FUseOrgId,
            String FStaffNumber,
            @RequestParam(required = false) String deptName,
            @RequestParam(required = false) String postName
    )
    {
        return iEmployeesService.employeesSave(acctId,dataCentreUserName,dataCentrePassword,FName,FCreateOrgId,FUseOrgId,FStaffNumber,deptName,postName);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids) {
        return iEmployeesService.employeesDelete(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "查看", notes = "查看")
    @RequestMapping(value = "/employeesView", method = RequestMethod.POST)
    public ResponseResult employeesView(String acctId,String dataCentreUserName,String dataCentrePassword,String number,String id) {
        return iEmployeesService.employeesView(acctId,dataCentreUserName,dataCentrePassword,number,id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids) {
        return iEmployeesService.employeesSubimt(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult audit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids) {
        return iEmployeesService.employeesAudit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "反审核", notes = "审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids) {
        return iEmployeesService.employeesUnAudit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }


    @ApiOperation(value = "添加业务员", notes = "添加业务员")
    @RequestMapping(value = "/saveYeWuYuan", method = RequestMethod.POST)
    public ResponseResult saveYeWuYuan(
            String acctId,
            String dataCentreUserName,
            String dataCentrePassword,
            String fBizOrgId,
            String fStaffId
    ) {
        return iEmployeesService.saveYeWuYuan(acctId,dataCentreUserName,dataCentrePassword,fBizOrgId,fStaffId);
    }

    @ApiOperation(value = "删除业务员", notes = "删除业务员")
    @RequestMapping(value = "/delYeWuYuan", method = RequestMethod.POST)
    public ResponseResult delYeWuYuan(
            String acctId,
            String dataCentreUserName,
            String dataCentrePassword,
            String ids
    ) {
        return iEmployeesService.delYeWuYuan(acctId,dataCentreUserName,dataCentrePassword,ids);
    }
}
