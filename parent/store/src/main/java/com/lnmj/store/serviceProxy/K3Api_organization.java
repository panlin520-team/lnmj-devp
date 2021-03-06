package com.lnmj.store.serviceProxy;


import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: yilihua
 * @Date: 2019/5/24 17:57
 * @Description:
 */
@FeignClient(name = "lnmj-k3cloud")
public interface K3Api_organization {
    @RequestMapping(value = "/k3cloud/organization/organization/save", method = RequestMethod.POST)
    ResponseResult save(@RequestParam("id") String id,
                        @RequestParam("acctId") String acctId,
                        @RequestParam("dataCentreUserName") String dataCentreUserName,
                        @RequestParam("dataCentrePassword") String dataCentrePassword,
                        @RequestParam("fname") String fname,
                        @RequestParam("fNumber") String fNumber,
                        @RequestParam("fOrgFormID") String fOrgFormID,
                        @RequestParam("fIsAccountOrg") Boolean fIsAccountOrg,
                        @RequestParam("fAcctOrgType") String fAcctOrgType,
                        @RequestParam("fParentID") String fParentID,
                        @RequestParam("fIsBusinessOrg") Boolean fIsBusinessOrg,
                        @RequestParam("fOrgFunctions") String fOrgFunctions,
                        @RequestParam("fStockBox") Boolean fStockBox);

    @RequestMapping(value = "/k3cloud/organization/organization/delete", method = RequestMethod.POST)
    ResponseResult delete(@RequestParam("acctId") String acctId,
                          @RequestParam("dataCentreUserName") String dataCentreUserName,
                          @RequestParam("dataCentrePassword") String dataCentrePassword,
                          @RequestParam("numbers") String numbers,
                          @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/organization/organization/submit", method = RequestMethod.POST)
    ResponseResult submit(@RequestParam("acctId") String acctId,
                          @RequestParam("dataCentreUserName") String dataCentreUserName,
                          @RequestParam("dataCentrePassword") String dataCentrePassword,
                          @RequestParam("numbers") String numbers,
                          @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/organization/organization/audit", method = RequestMethod.POST)
    ResponseResult audit(@RequestParam("acctId") String acctId,
                         @RequestParam("dataCentreUserName") String dataCentreUserName,
                         @RequestParam("dataCentrePassword") String dataCentrePassword,
                         @RequestParam("numbers") String numbers,
                         @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/organization/organization/view", method = RequestMethod.POST)
    ResponseResult view(@RequestParam("acctId") String acctId,
                        @RequestParam("dataCentreUserName") String dataCentreUserName,
                        @RequestParam("dataCentrePassword") String dataCentrePassword,
                        @RequestParam("number") String number,
                        @RequestParam("id") String id);

    @RequestMapping(value = "/k3cloud/organization/OrganizationLiShuGuanXi/save", method = RequestMethod.POST)
    ResponseResult saveLishu(@RequestParam("fParentOrgId") String fParentOrgId,
                             @RequestParam("fOrgId") String fOrgId,
                             @RequestParam("acctId") String acctId,
                             @RequestParam("dataCentreUserName") String dataCentreUserName,
                             @RequestParam("dataCentrePassword") String dataCentrePassword,
                             @RequestParam("fname") String fname,
                             @RequestParam("fNumber") String fNumber,
                             @RequestParam("fRootOrgID") String fRootOrgID,
                             @RequestParam("fType") String fType,
                             @RequestParam("fEndDate") String fEndDate,
                             @RequestParam("fStartDate") String fStartDate,
                             @RequestParam("fBackupOrg") String fBackupOrg,
                             @RequestParam("fAFFILIATIONID") String fAFFILIATIONID);

    @RequestMapping(value = "/k3cloud/organization/OrganizationLiShuGuanXi/batchSave", method = RequestMethod.POST)
    ResponseResult batchSaveLiShu(@RequestParam("fParentOrgId") String fParentOrgId,
                                  @RequestParam("fOrgId") String fOrgId,
                                  @RequestParam("acctId") String acctId,
                                  @RequestParam("dataCentreUserName") String dataCentreUserName,
                                  @RequestParam("dataCentrePassword") String dataCentrePassword,
                                  @RequestParam("fname") String fname,
                                  @RequestParam("fNumber") String fNumber,
                                  @RequestParam("fRootOrgID") String fRootOrgID,
                                  @RequestParam("fType") String fType,
                                  @RequestParam("fEndDate") String fEndDate,
                                  @RequestParam("fStartDate") String fStartDate,
                                  @RequestParam("fBackupOrg") String fBackupOrg,
                                  @RequestParam("fAFFILIATIONID") String fAFFILIATIONID,
                                  @RequestParam("jsonArray") String jsonArray,
                                  @RequestParam("type") Integer type,
                                  @RequestParam("isDeleteEntry") Boolean isDeleteEntry);

    @RequestMapping(value = "/k3cloud/organization/OrganizationLiShuGuanXi/view", method = RequestMethod.POST)
    ResponseResult viewLishu(@RequestParam("id") String id,
                             @RequestParam("number") String number,
                             @RequestParam("acctId") String acctId,
                             @RequestParam("dataCentreUserName") String dataCentreUserName,
                             @RequestParam("dataCentrePassword") String dataCentrePassword);


    @RequestMapping(value = "/k3cloud/organization/OrganizationYeWuZuZhi/query", method = RequestMethod.POST)
    ResponseResult queryOrganizationYeWuZuZhi(@RequestParam("acctId") String acctId,
                                              @RequestParam("dataCentreUserName") String dataCentreUserName,
                                              @RequestParam("dataCentrePassword") String dataCentrePassword,
                                              @RequestParam("formId") String formId,
                                              @RequestParam("fieldKeys") String fieldKeys);

    @RequestMapping(value = "/k3cloud/organization/OrganizationLiShuGuanXi/query", method = RequestMethod.POST)
    ResponseResult queryOrganizationLiShu(
            @RequestParam("acctId") String acctId,
            @RequestParam("dataCentreUserName") String dataCentreUserName,
            @RequestParam("dataCentrePassword") String dataCentrePassword,
            @RequestParam("formId") String formId,
            @RequestParam("fieldKeys") String fieldKeys);


    @RequestMapping(value = "/k3cloud/organization/OrganizationYeWuZuZhi/batchSave", method = RequestMethod.POST)
    ResponseResult batchSaveYeWuZuZhi(@RequestParam("acctId") String acctId,
                                      @RequestParam("dataCentreUserName") String dataCentreUserName,
                                      @RequestParam("dataCentrePassword") String dataCentrePassword,
                                      @RequestParam("jsonArray") String jsonArray);


    @RequestMapping(value = "/k3cloud/organization/OrganizationKongZhiCeNue/batchSave", method = RequestMethod.POST)
    ResponseResult batchSaveKongZhiCeNue(@RequestParam("acctId") String acctId,
                                         @RequestParam("dataCentreUserName") String dataCentreUserName,
                                         @RequestParam("dataCentrePassword") String dataCentrePassword,
                                         @RequestParam("jsonArray") String jsonArray);

    @RequestMapping(value = "/k3cloud/organization/OrganizationKongZhiCeNue/query", method = RequestMethod.POST)
    ResponseResult queryOrganizationKongZhiCeNue(@RequestParam("acctId") String acctId,
                                                 @RequestParam("dataCentreUserName") String dataCentreUserName,
                                                 @RequestParam("dataCentrePassword") String dataCentrePassword,
                                                 @RequestParam("formId") String formId,
                                                 @RequestParam("fieldKeys") String fieldKeys);

    @RequestMapping(value = "/k3cloud/supplier/supplier/save", method = RequestMethod.POST)
    ResponseResult saveSupplier(@RequestParam("acctId") String acctId,
                                @RequestParam("dataCentreUserName") String dataCentreUserName,
                                @RequestParam("dataCentrePassword") String dataCentrePassword,
                                @RequestParam("fName") String fName,
                                @RequestParam("fCreateOrgId") String fCreateOrgId,
                                @RequestParam("fUseOrgId") String fUseOrgId,
                                @RequestParam("fPayCurrencyId") String fPayCurrencyId,
                                @RequestParam("fLocationInfoListStr") String fLocationInfoListStr,
                                @RequestParam("needUpDateField") String needUpDateField,
                                @RequestParam("fSupplierId") Integer fSupplierId);

    @ApiOperation(value = "反审核", notes = "反审核")
    @RequestMapping(value = "/k3cloud/supplier/supplier/unAudit", method = RequestMethod.POST)
    ResponseResult unAuditSupplier(@RequestParam("acctId") String acctId,
                                   @RequestParam("dataCentreUserName") String dataCentreUserName,
                                   @RequestParam("dataCentrePassword") String dataCentrePassword,
                                   @RequestParam("numbers") String numbers,
                                   @RequestParam("ids") String ids);

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/k3cloud/supplier/supplier/delete", method = RequestMethod.POST)
    ResponseResult deleteSupplier(@RequestParam("acctId") String acctId,
                                  @RequestParam("dataCentreUserName") String dataCentreUserName,
                                  @RequestParam("dataCentrePassword") String dataCentrePassword,
                                  @RequestParam("numbers") String numbers,
                                  @RequestParam("ids") String ids);


    @ApiOperation(value = "添加银行账户", notes = "添加银行账户")
    @RequestMapping(value = "/k3cloud/bankAccount/save", method = RequestMethod.POST)
    ResponseResult saveBank(@RequestParam("acctId") String acctId,
                            @RequestParam("dataCentreUserName") String dataCentreUserName,
                            @RequestParam("dataCentrePassword") String dataCentrePassword,
                            @RequestParam("isautosubmitandaudit") Boolean isautosubmitandaudit,
                            @RequestParam("name") String name,
                            @RequestParam("number") String number,
                            @RequestParam("createOrgId") String createOrgId,
                            @RequestParam("useOrgId") String useOrgId,
                            @RequestParam("bankId") String bankId,
                            @RequestParam("upType") String upType);

    @ApiOperation(value = "添加现金账户", notes = "添加现金账户")
    @RequestMapping(value = "/k3cloud/cashAccount/save", method = RequestMethod.POST)
    ResponseResult saveCash(@RequestParam("acctId") String acctId,
                            @RequestParam("dataCentreUserName") String dataCentreUserName,
                            @RequestParam("dataCentrePassword") String dataCentrePassword,
                            @RequestParam("isautosubmitandaudit") Boolean isautosubmitandaudit,
                            @RequestParam("number") String number,
                            @RequestParam("name") String name,
                            @RequestParam("createOrgId") String createOrgId,
                            @RequestParam("useOrgid") String useOrgid);


    @ApiOperation(value = "保存会计核算体系", notes = "保存会计核算体系")
    @RequestMapping(value = "/k3cloud/account/accountingSystem/save", method = RequestMethod.POST)
    ResponseResult save(
            @RequestParam("acctId") String acctId,
            @RequestParam("dataCentreUserName") String dataCentreUserName,
            @RequestParam("dataCentrePassword") String dataCentrePassword,
            @RequestParam("fACCTSYSTEMID") String fACCTSYSTEMID,
            @RequestParam("fMAINORGID") String fMAINORGID,
            @RequestParam("jsonArraySubOrg") String jsonArraySubOrg,
            @RequestParam("fNumber") String fNumber,
            @RequestParam("fName") String fName);


    @ApiOperation(value = "查看会计核算体系", notes = "查看会计核算体系")
    @RequestMapping(value = "/k3cloud/account/accountingSystem/viewAccountingSystem", method = RequestMethod.POST)
    ResponseResult viewAccountingSystem(@RequestParam("acctId") String acctId,
                                        @RequestParam("dataCentreUserName") String dataCentreUserName,
                                        @RequestParam("dataCentrePassword") String dataCentrePassword,
                                        @RequestParam("numbers") String numbers,
                                        @RequestParam("id") String id
    );

    @ApiOperation(value = "保存核算范围", notes = "保存核算范围")
    @RequestMapping(value = "/k3cloud/inventoryAccount/save", method = RequestMethod.POST)
    ResponseResult saveInventoryAccount(@RequestParam("acctId") String acctId,
                                        @RequestParam("dataCentreUserName") String dataCentreUserName,
                                        @RequestParam("dataCentrePassword") String dataCentrePassword,
                                        @RequestParam("fACCTGRANGEID") String fACCTGRANGEID,
                                        @RequestParam("fACCTGORGID") String fACCTGORGID,
                                        @RequestParam("fACCTGSYSTEMID") String fACCTGSYSTEMID,
                                        @RequestParam("fName") String fName,
                                        @RequestParam("jsonArraySubOrg") String jsonArraySubOrg);

    @ApiOperation(value = "查看核算范围", notes = "查看核算范围")
    @RequestMapping(value = "/k3cloud/inventoryAccount/viewInventoryAccount", method = RequestMethod.POST)
    ResponseResult viewInventoryAccount(@RequestParam("acctId") String acctId,
                                        @RequestParam("dataCentreUserName") String dataCentreUserName,
                                        @RequestParam("dataCentrePassword") String dataCentrePassword,
                                        @RequestParam("number") String number,
                                        @RequestParam("id") String id);


    @ApiOperation(value = "保存账簿", notes = "保存账簿")
    @RequestMapping(value = "/k3cloud/account/accountBook/save", method = RequestMethod.POST)
    ResponseResult saveZB(@RequestParam("acctId") String acctId,
                          @RequestParam("dataCentreUserName") String dataCentreUserName,
                          @RequestParam("dataCentrePassword") String dataCentrePassword,
                          @RequestParam("fName") String fName,
                          @RequestParam("heSuanTiXiK3Number") String heSuanTiXiK3Number,
                          @RequestParam("orgK3Number") String orgK3Number);

    @ApiOperation(value = "保存部门", notes = "保存部门")
    @RequestMapping(value = "/k3cloud/department/department/departmentSave", method = RequestMethod.POST)
    ResponseResult departmentSave(@RequestParam("acctId") String acctId,
                                  @RequestParam("dataCentreUserName") String dataCentreUserName,
                                  @RequestParam("dataCentrePassword") String dataCentrePassword,
                                  @RequestParam("fName") String fName,
                                  @RequestParam("fCreateOrgId") String fCreateOrgId,
                                  @RequestParam("fUseOrgId") String fUseOrgId,
                                  @RequestParam("updateField") String updateField,
                                  @RequestParam("fDEPTID") String fDEPTID);

    @ApiOperation(value = "删除部门", notes = "删除部门")
    @RequestMapping(value = "/k3cloud/department/department/departmentDelete", method = RequestMethod.POST)
    ResponseResult departmentDelete(@RequestParam("acctId") String acctId,
                                    @RequestParam("dataCentreUserName") String dataCentreUserName,
                                    @RequestParam("dataCentrePassword") String dataCentrePassword,
                                    @RequestParam("numbers") String numbers,
                                    @RequestParam("ids") String ids);

    @ApiOperation(value = "保存客户", notes = "保存客户")
    @RequestMapping(value = "/k3cloud/customer/customer/customerSave", method = RequestMethod.POST)
    ResponseResult customerSave(@RequestParam("acctId") String acctId,
                                @RequestParam("dataCentreUserName") String dataCentreUserName,
                                @RequestParam("dataCentrePassword") String dataCentrePassword,
                                @RequestParam("fName") String fName,
                                @RequestParam("fNumber") String fNumber,
                                @RequestParam("fINVOICETITLE") String fINVOICETITLE,
                                @RequestParam("fCreateOrgId") String fCreateOrgId,
                                @RequestParam("fUseOrgId") String fUseOrgId,
                                @RequestParam("fCustTypeId") String fCustTypeId,
                                @RequestParam("needUpDateField") String needUpDateField,
                                @RequestParam("fCUSTID") Integer fCUSTID);

    @ApiOperation(value = "删除客户", notes = "删除客户")
    @RequestMapping(value = "/k3cloud/customer/customer/customerDelete", method = RequestMethod.POST)
    ResponseResult customerDelete(@RequestParam("acctId") String acctId,
                                  @RequestParam("dataCentreUserName") String dataCentreUserName,
                                  @RequestParam("dataCentrePassword") String dataCentrePassword,
                                  @RequestParam("numbers") String numbers,
                                  @RequestParam("ids") String ids);

    @ApiOperation(value = "分配供应商", notes = "分配供应商")
    @RequestMapping(value = "/k3cloud/supplier/supplier/AllocateSupplier", method = RequestMethod.POST)
    ResponseResult AllocateSupplier(@RequestParam("acctId") String acctId,
                                    @RequestParam("dataCentreUserName") String dataCentreUserName,
                                    @RequestParam("dataCentrePassword") String dataCentrePassword,
                                    @RequestParam("pkIds") String pkIds,
                                    @RequestParam("orgIds") String orgIds,
                                    @RequestParam("isAutoSubmitAndAudit") Boolean isAutoSubmitAndAudit);

    @ApiOperation(value = "加入组织用户维护", notes = "加入组织用户维护")
    @RequestMapping(value = "/k3cloud/organization/organizationUserWeiHu/save", method = RequestMethod.POST)
    ResponseResult organizationUserWeiHuSave(@RequestParam("acctId") String acctId,
                                             @RequestParam("dataCentreUserName") String dataCentreUserName,
                                             @RequestParam("dataCentrePassword") String dataCentrePassword,
                                             @RequestParam("fOrgBaseId") String fOrgBaseId,
                                             @RequestParam("fUSERACCOUNT") String fUSERACCOUNT);

    @ApiOperation(value = "生成基础资料控制策略", notes = "生成基础资料控制策略")
    @RequestMapping(value = "/k3cloud/organization/organizationJiChuKongZhi/organizationJiChuKongZhiSave", method = RequestMethod.POST)
    ResponseResult organizationJiChuKongZhiSave(@RequestParam("acctId") String acctId,
                                                @RequestParam("dataCentreUserName") String dataCentreUserName,
                                                @RequestParam("dataCentrePassword") String dataCentrePassword,
                                                @RequestParam("orgK3Number") String orgK3Number,
                                                @RequestParam("fTargetOrgId") String fTargetOrgId);

    @ApiOperation(value = "生成基础资料控制策略-岗位", notes = "生成基础资料控制策略-岗位")
    @RequestMapping(value = "/k3cloud/organization/organizationJiChuKongZhi/organizationJiChuKongZhiSaveGW", method = RequestMethod.POST)
    public ResponseResult organizationJiChuKongZhiSaveGW(@RequestParam("acctId") String acctId,
                                                         @RequestParam("dataCentreUserName") String dataCentreUserName,
                                                         @RequestParam("dataCentrePassword") String dataCentrePassword,
                                                         @RequestParam("orgK3Number") String orgK3Number);


    @ApiOperation(value = "查看组织业务关系", notes = "查看组织业务关系")
    @RequestMapping(value = "/k3cloud/organization/OrganizationYeWuZuZhi/view", method = RequestMethod.POST)
    ResponseResult viewYeWuZuZhi(@RequestParam("acctId") String acctId,
                                 @RequestParam("dataCentreUserName") String dataCentreUserName,
                                 @RequestParam("dataCentrePassword") String dataCentrePassword,
                                 @RequestParam("number") String number,
                                 @RequestParam("id") String id);

    @ApiOperation(value = "银行账号分配", notes = "银行账号分配")
    @RequestMapping(value = "/k3cloud/bankAccount/allocateBankAccount", method = RequestMethod.POST)
    ResponseResult AllocateBankAccount(@RequestParam("acctId") String acctId,
                                       @RequestParam("dataCentreUserName") String dataCentreUserName,
                                       @RequestParam("dataCentrePassword") String dataCentrePassword,
                                       @RequestParam("pkIds") String pkIds,
                                       @RequestParam("orgIds") String orgIds,
                                       @RequestParam("isAutoSubmitAndAudit") Boolean isAutoSubmitAndAudit);

    @ApiOperation(value = "现金账号分配", notes = "现金账号分配")
    @RequestMapping(value = "/k3cloud/cashAccount/allocateCashAccount", method = RequestMethod.POST)
    ResponseResult AllocateCashAccount(@RequestParam("acctId") String acctId,
                                       @RequestParam("dataCentreUserName") String dataCentreUserName,
                                       @RequestParam("dataCentrePassword") String dataCentrePassword,
                                       @RequestParam("pkIds") String pkIds,
                                       @RequestParam("orgIds") String orgIds,
                                       @RequestParam("isAutoSubmitAndAudit") Boolean isAutoSubmitAndAudit);

    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping(value = "/k3cloud/login", method = RequestMethod.POST)
    ResponseResult k3Login(@RequestParam("acctID") String acctID,
                           @RequestParam("userName") String userName,
                           @RequestParam("password") String password,
                           @RequestParam("lcid") String lcid);


    @ApiOperation(value = "批量保存", notes = "批量保存")
    @RequestMapping(value = "/k3cloud/customer/customer/customerSaveBatch", method = RequestMethod.POST)
    ResponseResult customerSaveBatch(@RequestParam("acctId") String acctId,
                                     @RequestParam("dataCentreUserName") String dataCentreUserName,
                                     @RequestParam("dataCentrePassword") String dataCentrePassword,
                                     @RequestParam("jsonArrayCust") String jsonArrayCust);

    @ApiOperation(value = "分配客户", notes = "分配客户")
    @RequestMapping(value = "/k3cloud/customer/customer/allocateCustomer", method = RequestMethod.POST)
    ResponseResult allocateCustomer(@RequestParam("acctId") String acctId,
                                    @RequestParam("dataCentreUserName") String dataCentreUserName,
                                    @RequestParam("dataCentrePassword") String dataCentrePassword,
                                    @RequestParam("PkIds") String PkIds,
                                    @RequestParam("TOrgIds") String TOrgIds,
                                    @RequestParam("isautosubmitandaudit") Boolean isautosubmitandaudit);

    @ApiOperation(value = "保存管理员", notes = "保存管理员")
    @RequestMapping(value = "/k3cloud/adminUser/adminUser/saveAdminUser", method = RequestMethod.POST)
    ResponseResult saveAdminUser(
            @RequestParam("acctId") String acctId,
            @RequestParam("dataCentreUserName") String dataCentreUserName,
            @RequestParam("dataCentrePassword") String dataCentrePassword,
            @RequestParam("fUserAccount") String fUserAccount,
            @RequestParam("fName") String fName
    );

    @ApiOperation(value = "将组织分配给用户", notes = "将组织分配给用户")
    @RequestMapping(value = "/k3cloud/adminUser/adminUser/orgToUser", method = RequestMethod.POST)
    ResponseResult orgToUser(@RequestParam("dataCentreName") String dataCentreName,
                             @RequestParam("userId") String userId,
                             @RequestParam("orgId") String orgId);

    @ApiOperation(value = "通过id保存user", notes = "通过id保存user")
    @RequestMapping(value = "/k3cloud/adminUser/adminUser/saveAdminUserById", method = RequestMethod.POST)
    ResponseResult saveAdminUserById(
            @RequestParam("acctId") String acctId,
            @RequestParam("dataCentreUserName") String dataCentreUserName,
            @RequestParam("dataCentrePassword") String dataCentrePassword,
            @RequestParam("fUserID") String fUserID
    );

    @ApiOperation(value = "修改组织的职能", notes = "修改组织的职能")
    @RequestMapping(value = "/k3cloud/organization/organization/updateOrgFunctions", method = RequestMethod.POST)
    ResponseResult updateOrgFunctions(@RequestParam("dataCentreName") String dataCentreName, @RequestParam("orgId") String orgId);


    @ApiOperation(value = "仓库启用", notes = "仓库启用")
    @RequestMapping(value = "/k3cloud/stock/startStock", method = RequestMethod.POST)
    ResponseResult startStock(@RequestParam("dataCentreName") String dataCentreName,
                              @RequestParam("orgId") String orgId,
                              @RequestParam("startDate") String startDate);

    /*-----------------------------------职位---------------------------------------------*/
    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/k3cloud/post/post/save", method = RequestMethod.POST)
    public ResponseResult savePost(
            @RequestParam("acctId") String acctId,
            @RequestParam("dataCentreUserName") String dataCentreUserName,
            @RequestParam("dataCentrePassword") String dataCentrePassword,
            @RequestParam("fName") String fName,
            @RequestParam("fCreateOrgId") String fCreateOrgId,
            @RequestParam("fUseOrgId") String fUseOrgId,
            @RequestParam("fDept") String fDept
    );


    /*-----------------------------------员工---------------------------------------------*/
    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/k3cloud/employees/employees/save", method = RequestMethod.POST)
    ResponseResult saveEmployees(
            @RequestParam("acctId") String acctId,
            @RequestParam("dataCentreUserName") String dataCentreUserName,
            @RequestParam("dataCentrePassword") String dataCentrePassword,
            @RequestParam("FName") String FName,
            @RequestParam("FCreateOrgId") String FCreateOrgId,
            @RequestParam("FUseOrgId") String FUseOrgId,
            @RequestParam("FStaffNumber") String FStaffNumber,
            @RequestParam(value = "deptName", required = false) String deptName,
            @RequestParam(value = "postName", required = false) String postName
    );

    @ApiOperation(value = "添加业务员", notes = "添加业务员")
    @RequestMapping(value = "/k3cloud/employees/employees/saveYeWuYuan", method = RequestMethod.POST)
    ResponseResult saveYeWuYuan(
            @RequestParam("acctId") String acctId,
            @RequestParam("dataCentreUserName") String dataCentreUserName,
            @RequestParam("dataCentrePassword") String dataCentrePassword,
            @RequestParam("fBizOrgId") String fBizOrgId,
            @RequestParam("fStaffId") String fStaffId
    );


    @ApiOperation(value = "删除业务员", notes = "删除业务员")
    @RequestMapping(value = "/k3cloud/employees/employees/delYeWuYuan", method = RequestMethod.POST)
    ResponseResult delYeWuYuan(
            @RequestParam("acctId") String acctId,
            @RequestParam("dataCentreUserName") String dataCentreUserName,
            @RequestParam("dataCentrePassword") String dataCentrePassword,
            @RequestParam("ids") String ids
    );
}

