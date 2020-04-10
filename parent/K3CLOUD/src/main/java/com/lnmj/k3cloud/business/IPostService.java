package com.lnmj.k3cloud.business;


import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Author: panlin
 * @Date: 2019-11-08 15:09
 * @Description:  金蝶service
 */
@Service("iPostService")
public interface IPostService {

    ResponseResult postSave(String acctId, String dataCentreUserName, String dataCentrePassword, String fName, String fCreateOrgId, String fUseOrgId, String fDept);
    ResponseResult postDelete(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);
    ResponseResult postView(String acctId,String dataCentreUserName,String dataCentrePassword,String number, String id);
    ResponseResult postSubimt(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);
    ResponseResult postAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);
    ResponseResult postUnAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);


}
