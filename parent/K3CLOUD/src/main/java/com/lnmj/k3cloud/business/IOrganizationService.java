package com.lnmj.k3cloud.business;


import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Author: panlin
 * @Date: 2019-11-08 15:09
 * @Description:  金蝶service
 */
@Service("iOrganizationService")
public interface IOrganizationService {
    //组织形态
    ResponseResult formSave(String acctId,String dataCentreUserName, String dataCentrePassword,String fname,String FNumber);
    ResponseResult formDelete(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult formView(String acctId,String dataCentreUserName, String dataCentrePassword,String number,String id);
    ResponseResult formSubimt(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult formAudit(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult formUnAudit(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    //组织机构
    ResponseResult organizationSave(String fOrgID,String acctId,String dataCentreUserName, String dataCentrePassword,String fname,String fNumber,String fOrgFormID,Boolean fIsAccountOrg,String fAcctOrgType,String fParentID,Boolean fIsBusinessOrg,String fOrgFunctions,Boolean fStockBox);
    ResponseResult organizationDelete(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult organizationView(String acctId,String dataCentreUserName, String dataCentrePassword,String number,String id);
    ResponseResult organizationSubimt(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult organizationAudit(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult organizationUnAudit(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult updateOrgFunctions(String dataCentreName,String orgId);
    //组织隶属关系
    ResponseResult organizationLiShuGuanXiSave(String fParentOrgId ,String fOrgId,String acctId,String dataCentreUserName, String dataCentrePassword,String fname,String fNumber,String fRootOrgID,String fType,String fEndDate,String fStartDate,String fBackupOrg,String fAFFILIATIONID);
    ResponseResult organizationLiShuGuanXiBatchSave(String fParentOrgId , String fOrgId, String acctId, String dataCentreUserName, String dataCentrePassword, String fname, String fNumber, String fRootOrgID, String fType, String fEndDate, String fStartDate, String fBackupOrg, String fAFFILIATIONID, String jsonArray,Integer type,Boolean isDeleteEntry );
    ResponseResult organizationLiShuGuanXiDelete(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult organizationLiShuGuanXiView(String acctId,String dataCentreUserName, String dataCentrePassword,String number,String id);
    ResponseResult organizationLiShuGuanXiSubimt(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult organizationLiShuGuanXiAudit(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult organizationLiShuGuanXiUnAudit(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult queryOrganizationLiShu(String acctId,String dataCentreUserName,String dataCentrePassword,String formId,String fieldKeys);
    //业务关系
    ResponseResult organizationYeWuZuZhiSave(String fBRTypeId,String fRelationOrgID,String fOrgId,String acctId,String dataCentreUserName, String dataCentrePassword);
    ResponseResult organizationYeWuZuZhiBatchSave(String acctId, String dataCentreUserName, String dataCentrePassword,String jsonArray);
    ResponseResult organizationYeWuZuZhiDelete(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult organizationYeWuZuZhiView(String acctId,String dataCentreUserName, String dataCentrePassword,String number,String id);
    ResponseResult organizationYeWuZuZhiSubimt(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult organizationYeWuZuZhiAudit(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult organizationYeWuZuZhiUnAudit(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult queryOrganizationYeWuZuZhi(String acctId,String dataCentreUserName,String dataCentrePassword,String formId,String fieldKeys);
    //基础资料控制策略
    ResponseResult organizationKongZhiCeNueSave(String fBaseDataTypeId,String fCreateOrgId,String fControlTypeId,String acctId,String dataCentreUserName, String dataCentrePassword);
    ResponseResult organizationKongZhiCeNueBatchSave(String acctId, String dataCentreUserName, String dataCentrePassword,String jsonArray);
    ResponseResult organizationKongZhiCeNueDelete(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult organizationKongZhiCeNueView(String acctId,String dataCentreUserName, String dataCentrePassword,String number,String id);
    ResponseResult organizationKongZhiCeNueSubimt(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult organizationKongZhiCeNueAudit(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult organizationKongZhiCeNueUnAudit(String acctId,String dataCentreUserName, String dataCentrePassword,String numbers,String ids);
    ResponseResult queryOrganizationKongZhiCeNue(String acctId,String dataCentreUserName,String dataCentrePassword,String formId,String fieldKeys);
    //组织用户维护
    ResponseResult organizationUserWeiHuSave(String acctId, String dataCentreUserName, String dataCentrePassword, String fOrgBaseId,String fUSERACCOUNT);
    //基础资料控制策略
    ResponseResult organizationJiChuKongZhiSave(String acctId,String dataCentreUserName, String dataCentrePassword,String orgK3Number,String fTargetOrgId);
    //基础资料控制策略-岗位
    ResponseResult organizationJiChuKongZhiSaveGW(String acctId,String dataCentreUserName, String dataCentrePassword,String orgK3Number);

}
