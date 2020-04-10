package com.lnmj.k3cloud.business;

import com.lnmj.common.response.ResponseResult;

public interface IDepartmentService {

    ResponseResult Save(String acctId,String dataCentreUserName,String dataCentrePassword,String fName, String fCreateOrgId, String fUseOrgId,String updateField,Integer fDEPTID);

    ResponseResult Delete(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);

    ResponseResult View(String acctId,String dataCentreUserName,String dataCentrePassword,String number, String id);

    ResponseResult Subimt(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);

    ResponseResult Audit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);

    ResponseResult UnAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);
}
