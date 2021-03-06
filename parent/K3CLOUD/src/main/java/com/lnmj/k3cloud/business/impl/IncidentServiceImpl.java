package com.lnmj.k3cloud.business.impl;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeIncidentEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.*;
import com.lnmj.k3cloud.pojo.Incident;
import com.lnmj.k3cloud.repository.IIncidentDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019-11-27 13:55
 * @Description:
 */
@Service
public class IncidentServiceImpl implements IIncidentService {
    @Resource
    private IIncidentDao incidentDao;

    public ResponseResult selectIncidentList(String acctId,String dataCentreUserName,String dataCentrePassword,String orderByClause) {
        return ResponseResult.success(incidentDao.selectIncidentList(orderByClause));
    }

    public ResponseResult selectIncidentByID(String acctId,String dataCentreUserName,String dataCentrePassword,Long incidentId) {
        return ResponseResult.success(incidentDao.selectIncidentByID(incidentId));
    }

    public ResponseResult deleteIncidentByID(String acctId,String dataCentreUserName,String dataCentrePassword,Long incidentId, String modifyOperator) {
        Incident incident = new Incident();
        incident.setIncidentId(incidentId);
        incident.setModifyOperator(modifyOperator);
        incidentDao.deleteIncidentByID(incident);
        return ResponseResult.success(incident);
    }

    public ResponseResult insertIncident(String acctId,String dataCentreUserName,String dataCentrePassword,Incident incident) {
        incidentDao.insertIncident(incident);
        return ResponseResult.success(incident);
    }

    public ResponseResult updateIncidentByID(String acctId,String dataCentreUserName,String dataCentrePassword,Incident incident) {
        incidentDao.updateIncidentByID(incident);
        return ResponseResult.success(incident);
    }

    public ResponseResult incidentHandle(String acctId,String dataCentreUserName,String dataCentrePassword,Long incidentId, String modifyOperator) {
        if(incidentId==null){
            //全部事件处理
            List<Incident> incidentList = incidentDao.selectIncidentList(null);
            if(incidentList==null || incidentList.size()>0){
                return ResponseResult.error(new Error(ResponseCodeIncidentEnum.LIST_INFOMATION_ISNULL.getCode(),
                        ResponseCodeIncidentEnum.LIST_INFOMATION_ISNULL.getDesc()));
            }
            for (Incident incident : incidentList) {

            }
            return  ResponseResult.success();
        }else{
            //只处理incidentId的事件
            Incident incident = incidentDao.selectIncidentByID(incidentId);
            if(incident==null){
                return ResponseResult.error(new Error(ResponseCodeIncidentEnum.LIST_INFOMATION_ISNULL.getCode(),
                        ResponseCodeIncidentEnum.LIST_INFOMATION_ISNULL.getDesc()));
            }
            String excuteIncidentProject = incident.getExcuteIncidentProject(); //k3
            String incidentClass = incident.getIncidentClass(); //controller
            String excuteIncidentName = incident.getExcuteIncidentName();   //methodName
            String excuteIncidentJSON = incident.getExcuteIncidentJSON();
            //从controller到method
            String classN = "com.lnmj.k3cloud.controller"+incidentClass;
            Class<?> aClass = null;
            try {
                aClass = Class.forName(classN);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                String remark = e.getMessage();
                //删除事件
                Incident incident1 = new Incident();
                incident1.setIncidentId(incidentId);
                incident1.setRemark(remark);
                incident1.setStatus(0);
                incident1.setModifyOperator(modifyOperator);
                incidentDao.updateIncidentByID(incident1);
                return ResponseResult.error(new Error(ResponseCodeIncidentEnum.CLASS_NOT_FOUND.getCode(),
                        ResponseCodeIncidentEnum.CLASS_NOT_FOUND.getDesc()));
            }
            Method[] methods = aClass.getMethods();
            boolean a = false;
            for (Method method : methods) {
                if(excuteIncidentName.equals(method.getName())){
                    a = true;
                    //重新调用k3
                    // 保存，需要保存、提交、审核。
                    // 审核，审核k3。
                    // 修改，需要修改k3的数据。
                    // 查看，需要核对数据库和k3的数据。
                    // 删除，需要删除k3。需要验证k3是否能删除
                    Object obj = null;
                    try {
                         obj = aClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ResponseResult.error(new Error(ResponseCodeIncidentEnum.CLASS_NOT_INSTANCE.getCode(),
                                ResponseCodeIncidentEnum.CLASS_NOT_INSTANCE.getDesc()));
                    }
                    try {
                        method.invoke(obj, excuteIncidentJSON);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ResponseResult.error(new Error(ResponseCodeIncidentEnum.METHOD_EXCUTE_FAILED.getCode(),
                                ResponseCodeIncidentEnum.METHOD_EXCUTE_FAILED.getDesc()));
                    }
                    //更新原数据
                    // 保存，需要更新k3id。
                    // 审核，需要修改数据库的状态。 只需要核对
                    // 删除，删除数据库数据。  只需要核对

                    //更新事件，删除事件
                    return ResponseResult.success();
                }
            }
            if(!a){
                String remark = "需执行的事件所在的类没有所需的方法";
                //删除事件
                Incident incident1 = new Incident();
                incident1.setIncidentId(incidentId);
                incident1.setRemark(remark);
                incident1.setStatus(0);
                incident1.setModifyOperator(modifyOperator);
                incidentDao.updateIncidentByID(incident1);
                return ResponseResult.error(new Error(ResponseCodeIncidentEnum.METHOD_NOT_FOUND.getCode(),
                        ResponseCodeIncidentEnum.METHOD_NOT_FOUND.getDesc()));
            }
            return ResponseResult.success();
        }
    }
}
