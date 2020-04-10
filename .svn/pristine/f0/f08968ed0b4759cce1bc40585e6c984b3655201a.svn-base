package com.lnmj.k3cloud.business;

import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2020-01-14 14:59
 * @Description:
 */
@Service("IInventoryAccountService")
public interface IInventoryAccountService {

    ResponseResult enableInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult forbidInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult deleteInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unAuditInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult auditInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult submitInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult batchSaveInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword);

    ResponseResult viewInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id);

    ResponseResult saveInventoryAccount(String acctId, String dataCentreUserName, String dataCentrePassword,String fACCTGRANGEID,String fACCTGORGID, String fACCTGSYSTEMID,String fName,String jsonArraySubOrg);
}
