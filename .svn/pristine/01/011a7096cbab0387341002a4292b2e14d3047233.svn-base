package com.lnmj.k3cloud.business;

import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2020-01-13 11:10
 * @Description:
 */
@Service("IBankAccountService")
public interface IBankAccountService {

    ResponseResult saveBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword,Boolean isautosubmitandaudit,
                                   String name, String number, String createOrgId, String useOrgId, String bankId,String upType);

    ResponseResult batchSaveBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword);

    ResponseResult viewBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id);

    ResponseResult submitBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult auditBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unAuditBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult deleteBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult forbidBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult allocateBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String pkIds, String orgIds, Boolean isAutoSubmitAndAudit);

    ResponseResult enableBankAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);
}
