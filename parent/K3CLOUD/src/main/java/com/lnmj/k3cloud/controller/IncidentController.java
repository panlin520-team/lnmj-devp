package com.lnmj.k3cloud.controller;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeIncidentEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IIncidentService;
import com.lnmj.k3cloud.pojo.Incident;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yilihua
 * @Date: 2019-11-27 11:00
 * @Description:    K3事件
 */
@Api(description = "K3事件")
@RestController
@RequestMapping("/incident")
public class IncidentController {
    @Resource
    private IIncidentService incidentService;

    @ApiOperation(value = "K3事件处理", notes = "K3事件处理")
    @RequestMapping(value="/incidentHandle", method = RequestMethod.POST)
    public ResponseResult incidentHandle(String acctId,String dataCentreUserName,String dataCentrePassword,Long incidentId,String modifyOperator){
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeIncidentEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeIncidentEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return incidentService.incidentHandle(acctId,dataCentreUserName,dataCentrePassword,incidentId,modifyOperator);
    }

    @ApiOperation(value = "K3事件查询", notes = "K3事件查询")
    @RequestMapping(value="/selectIncidentList", method = RequestMethod.POST)
    public ResponseResult selectIncidentList(String acctId,String dataCentreUserName,String dataCentrePassword,String orderByClause){
        return incidentService.selectIncidentList(acctId,dataCentreUserName,dataCentrePassword,orderByClause);
    }

    @ApiOperation(value = "K3事件查询", notes = "K3事件查询")
    @RequestMapping(value="/selectIncidentByID", method = RequestMethod.POST)
    public ResponseResult selectIncidentByID(String acctId,String dataCentreUserName,String dataCentrePassword,Long incidentId){
        if(incidentId==null){
            return ResponseResult.error(new Error(ResponseCodeIncidentEnum.INCIDENT_ID_NUT_NULL.getCode(),
                    ResponseCodeIncidentEnum.INCIDENT_ID_NUT_NULL.getDesc()));
        }
        return incidentService.selectIncidentByID(acctId,dataCentreUserName,dataCentrePassword,incidentId);
    }

    @ApiOperation(value = "删除K3事件", notes = "删除K3事件")
    @RequestMapping(value="/deleteIncidentByID", method = RequestMethod.POST)
    public ResponseResult deleteIncidentByID(String acctId,String dataCentreUserName,String dataCentrePassword,Long incidentId,String modifyOperator){
        if(incidentId==null){
            return ResponseResult.error(new Error(ResponseCodeIncidentEnum.INCIDENT_ID_NUT_NULL.getCode(),
                    ResponseCodeIncidentEnum.INCIDENT_ID_NUT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeIncidentEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeIncidentEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return incidentService.deleteIncidentByID(acctId,dataCentreUserName,dataCentrePassword,incidentId,modifyOperator);
    }

    @ApiOperation(value = "新增K3事件", notes = "新增K3事件")
    @RequestMapping(value="/insertIncident", method = RequestMethod.POST)
    public ResponseResult insertIncident(String acctId,String dataCentreUserName,String dataCentrePassword,Incident incident){
        return incidentService.insertIncident(acctId,dataCentreUserName,dataCentrePassword,incident);
    }

    @ApiOperation(value = "更新K3事件", notes = "更新K3事件")
    @RequestMapping(value="/updateIncidentByID", method = RequestMethod.POST)
    public ResponseResult updateIncidentByID(String acctId,String dataCentreUserName,String dataCentrePassword,Incident incident){
        if(incident.getIncidentId()==null){
            return ResponseResult.error(new Error(ResponseCodeIncidentEnum.INCIDENT_ID_NUT_NULL.getCode(),
                    ResponseCodeIncidentEnum.INCIDENT_ID_NUT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(incident.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodeIncidentEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeIncidentEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return incidentService.updateIncidentByID(acctId,dataCentreUserName,dataCentrePassword,incident);
    }

}
