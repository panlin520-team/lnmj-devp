package com.lnmj.k3cloud.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.pojo.Incident;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019-11-27 19:51
 * @Description:
 */
@Service("iEmployeesService")
public interface IIncidentService {

     ResponseResult incidentHandle(String acctId,String dataCentreUserName,String dataCentrePassword,Long incidentId, String modifyOperator);

     ResponseResult selectIncidentList(String acctId,String dataCentreUserName,String dataCentrePassword,String orderByClause);

     ResponseResult selectIncidentByID(String acctId,String dataCentreUserName,String dataCentrePassword,Long incidentId);

     ResponseResult deleteIncidentByID(String acctId,String dataCentreUserName,String dataCentrePassword,Long incidentId, String modifyOperator) ;

     ResponseResult updateIncidentByID(String acctId,String dataCentreUserName,String dataCentrePassword,Incident incident) ;

     ResponseResult insertIncident(String acctId,String dataCentreUserName,String dataCentrePassword,Incident incident) ;
}
