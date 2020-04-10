package com.lnmj.k3cloud.business;

import com.lnmj.common.response.ResponseResult;

public interface AccountingSystemService {
    ResponseResult saveAccountingSystem(String acctId, String dataCentreUserName, String dataCentrePassword,String fACCTSYSTEMID,String fMAINORGID ,String jsonArraySubOrg, String fNumber, String fName);

    ResponseResult deleteAccountingSystem(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult viewAccountingSystem(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String id);

    ResponseResult subimtAccountingSystem(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult auditAccountingSystem(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unAuditAccountingSystem(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);
}
