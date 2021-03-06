package com.lnmj.k3cloud.business;


import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Author: panlin
 * @Date: 2019-11-08 15:09
 * @Description:  金蝶service
 */
@Service("iEmployeesService")
public interface IEmployeesService {
    ResponseResult employeesSave(String acctId, String dataCentreUserName, String dataCentrePassword, String FName, String FCreateOrgId, String FUseOrgId, String FStaffNumber, String deptName, String postName);
    ResponseResult employeesDelete(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);
    ResponseResult employeesView(String acctId,String dataCentreUserName,String dataCentrePassword,String number, String id);
    ResponseResult employeesSubimt(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);
    ResponseResult employeesAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);
    ResponseResult employeesUnAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);

    ResponseResult saveYeWuYuan( String acctId,
                                 String dataCentreUserName,
                                 String dataCentrePassword,
                                 String fBizOrgId,
                                 String fStaffId);
    ResponseResult delYeWuYuan( String acctId,
                                 String dataCentreUserName,
                                 String dataCentrePassword,
                                 String ids);
}
