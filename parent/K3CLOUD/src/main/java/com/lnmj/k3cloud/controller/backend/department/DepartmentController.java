package com.lnmj.k3cloud.controller.backend.department;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.ICustomerService;
import com.lnmj.k3cloud.business.IDepartmentService;
import com.lnmj.k3cloud.business.IEmployeesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3-部门-部门")
@RestController
@RequestMapping("/k3cloud/department/department")
public class DepartmentController {
    @Resource
    private IDepartmentService iDepartmentService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/departmentSave", method = RequestMethod.POST)
    public ResponseResult departmentSave(
            String acctId,
            String dataCentreUserName,String dataCentrePassword,
            String fName,
            String fCreateOrgId,
            String fUseOrgId,
            String updateField,
            Integer fDEPTID)
    {
        return iDepartmentService.Save(acctId,dataCentreUserName,dataCentrePassword,fName,fCreateOrgId,fUseOrgId,updateField,fDEPTID);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/departmentDelete", method = RequestMethod.POST)
    public ResponseResult departmentDelete(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids) {
        return iDepartmentService.Delete(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "查看", notes = "查看")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseResult view(String acctId,String dataCentreUserName,String dataCentrePassword,String number,String id) {
        return iDepartmentService.View(acctId,dataCentreUserName,dataCentrePassword,number,id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids) {
        return iDepartmentService.Subimt(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult audit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids) {
        return iDepartmentService.Audit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }

    @ApiOperation(value = "反审核", notes = "审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers,String ids) {
        return iDepartmentService.UnAudit(acctId,dataCentreUserName,dataCentrePassword,numbers,ids);
    }
}
