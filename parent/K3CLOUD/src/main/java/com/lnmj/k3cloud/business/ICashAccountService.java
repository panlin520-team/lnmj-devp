package com.lnmj.k3cloud.business;

import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2020-01-14 11:46
 * @Description:
 */
@Service("ICashAccountService")
public interface ICashAccountService {

    ResponseResult saveCashAccount(String acctId, String dataCentreUserName, String dataCentrePassword,Boolean isautosubmitandaudit,String number,String name,String createOrgId,String useOrgid);

    ResponseResult batchSaveCashAccount(String acctId, String dataCentreUserName, String dataCentrePassword);

    ResponseResult viewCashAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id);

    ResponseResult submitCashAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult auditCashAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unAuditCashAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult deleteCashAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult forbidCashAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult allocateCashAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String pkIds, String orgIds, Boolean isAutoSubmitAndAudit);

    ResponseResult enableCashAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);
}
