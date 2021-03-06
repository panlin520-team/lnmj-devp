package com.lnmj.k3cloud.controller.backend.customer;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.ICustomerService;
import com.lnmj.k3cloud.business.IEmployeesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3-客户")
@RestController
@RequestMapping("/k3cloud/customer/customer")
public class CustomerController {
    @Resource
    private ICustomerService icustomerService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/customerSave", method = RequestMethod.POST)
    public ResponseResult customerSave(
            String acctId,
            String dataCentreUserName,
            String dataCentrePassword,
            String fName,
            String fNumber,
            String fINVOICETITLE,
            String fCreateOrgId,
            String fUseOrgId,
            String fCustTypeId,
            String needUpDateField,
            Integer fCUSTID
    ) {
        return icustomerService.customerSave(
                acctId,
                dataCentreUserName,
                dataCentrePassword,
                fName,
                fNumber,
                fINVOICETITLE,
                fCreateOrgId,
                fUseOrgId,
                fCustTypeId,
                needUpDateField,
                fCUSTID);
    }

    @ApiOperation(value = "批量保存", notes = "批量保存")
    @RequestMapping(value = "/customerSaveBatch", method = RequestMethod.POST)
    public ResponseResult customerSaveBatch(String acctId, String dataCentreUserName, String dataCentrePassword, String jsonArrayCust) {
        return icustomerService.customerSaveBatch(acctId, dataCentreUserName, dataCentrePassword, jsonArrayCust);
    }


    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/customerDelete", method = RequestMethod.POST)
    public ResponseResult customerDelete(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        return icustomerService.customerDelete(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }

    @ApiOperation(value = "查看", notes = "查看")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseResult view(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id) {
        return icustomerService.customerView(acctId, dataCentreUserName, dataCentrePassword, number, id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        return icustomerService.customerSubimt(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult audit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        return icustomerService.customerAudit(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }

    @ApiOperation(value = "反审核", notes = "审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids) {
        return icustomerService.customerUnAudit(acctId, dataCentreUserName, dataCentrePassword, numbers, ids);
    }

    @ApiOperation(value = "分配", notes = "分配")
    @RequestMapping(value = "/allocateCustomer", method = RequestMethod.POST)
    public ResponseResult allocateCustomer(String acctId, String dataCentreUserName, String dataCentrePassword, String PkIds, String TOrgIds, Boolean isautosubmitandaudit) {
        return icustomerService.customerAllocate(acctId, dataCentreUserName, dataCentrePassword, PkIds, TOrgIds, isautosubmitandaudit);
    }
}
