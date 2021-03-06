package com.lnmj.k3cloud.business;


import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Author: panlin
 * @Date: 2019-11-08 15:09
 * @Description:  金蝶service
 */
@Service("iSupplierService")
public interface ISupplierService {
    ResponseResult supplierSave(String acctId,String dataCentreUserName,String dataCentrePassword,String fName,String fCreateOrgId,String fUseOrgId,String fPayCurrencyId,String fLocationInfoListStr,String needUpDateField,Integer fSupplierId);
    ResponseResult supplierDelete(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);
    ResponseResult supplierView(String acctId,String dataCentreUserName,String dataCentrePassword,String number, String id);
    ResponseResult supplierSubimt(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);
    ResponseResult supplierAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);
    ResponseResult supplierUnAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);


    ResponseResult supplierAllocate(String acctId,String dataCentreUserName,String dataCentrePassword,String pkIds, String orgIds, Boolean isAutoSubmitAndAudit);

    ResponseResult batchsave(String acctId, String dataCentreUserName, String dataCentrePassword,String supplierArr/*, String fName, String fCreateOrgId, String fUseOrgId, String fPayCurrencyId, String fLocationInfoListStr*/);
}
