package com.lnmj.k3cloud.business;

import com.lnmj.common.response.ResponseResult;

public interface AccountPolicyService {

    ResponseResult saveAccountPolicy(String acctId, String dataCentreUserName, String dataCentrePassword, String model, Boolean isautosubmitandaudit);

    ResponseResult deleteAccountPolicy(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult viewAccountPolicy(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String id);

    ResponseResult subimtAccountPolicy(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult auditAccountPolicy(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unAuditAccountPolicy(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult accountPolicyAllocate(String acctId, String dataCentreUserName, String dataCentrePassword, String pkIds, String tOrgIds);
}
