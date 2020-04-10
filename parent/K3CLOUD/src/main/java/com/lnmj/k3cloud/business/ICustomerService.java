package com.lnmj.k3cloud.business;

import com.lnmj.common.response.ResponseResult;

public interface ICustomerService {
    ResponseResult customerSave(String acctId,
                                String dataCentreUserName,
                                String dataCentrePassword,
                                String fName,
                                String fNumber,
                                String fINVOICETITLE,
                                String fCreateOrgId,
                                String fUseOrgId,
                                String fCustTypeId,
                                String needUpDateField,
                                Integer fCUSTID);


    ResponseResult customerSaveBatch(String acctId, String dataCentreUserName, String dataCentrePassword,String jsonArrayCust);

    ResponseResult customerDelete(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);

    ResponseResult customerView(String acctId,String dataCentreUserName,String dataCentrePassword,String number, String id);

    ResponseResult customerSubimt(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);

    ResponseResult customerAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);

    ResponseResult customerUnAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);

    ResponseResult customerAllocate(String acctId, String dataCentreUserName, String dataCentrePassword, String PkIds, String TOrgIds, Boolean isautosubmitandaudit);
}
