package com.lnmj.k3cloud.business;


import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;


@Service("iInventoryTypeService")
public interface IInventoryTypeService {

    ResponseResult save(String acctId, String dataCentreUserName, String dataCentrePassword, String fName, String fNumber, String fnfCreateOrgId, String fUseOrgId);

    ResponseResult delete(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult view(String acctId, String dataCentreUserName, String dataCentrePassword, String number, String id);

    ResponseResult submit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult audit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult unAudit(String acctId, String dataCentreUserName, String dataCentrePassword, String numbers, String ids);

    ResponseResult allocate(String acctId, String dataCentreUserName, String dataCentrePassword, String pkIds, String tOrgIds);
}
