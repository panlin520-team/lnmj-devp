package com.lnmj.k3cloud.business;

import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019-11-08 15:09
 * @Description:  单位service
 */
@Service("IUnitService")
public interface IUnitService {

    ResponseResult saveUnit(String acctId, String dataCentreUserName, String dataCentrePassword, String fname, String groupId, String froundtype, Integer id, Boolean isautosubmitandaudit);

    ResponseResult enableUnit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);

    ResponseResult forbidUnit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);

    ResponseResult deleteUnit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);

    ResponseResult unAuditUnit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);

    ResponseResult auditUnit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);

    ResponseResult submitUnit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);

    ResponseResult viewUnit(String acctId,String dataCentreUserName,String dataCentrePassword,String number, String id);

}
