package com.lnmj.system.business.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeSystemEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.business.ISystemParameterService;
import com.lnmj.system.entity.Parameter;
import com.lnmj.system.entity.ParameterType;
import com.lnmj.system.repository.ISystemParameterDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/5/29 15:29
 * @Description: 系统参数配置Service
 */
@Service("systemParameterService")
public class SystemParameterService implements ISystemParameterService {
    @Resource
    private ISystemParameterDao systemParameterDao;


    @Override
    public ResponseResult selectParameterList(int pageNum, int pageSize,Long parameterTypeId) {
        PageHelper.startPage(pageNum,pageSize);
        List<Parameter> parameterList = systemParameterDao.selectParameterList(parameterTypeId);
        if(parameterList!=null && parameterList.size()!=0){
            PageInfo pageInfo = new PageInfo(parameterList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeSystemEnum.PARAMETER_NOT_FIND.getCode(),
                ResponseCodeSystemEnum.PARAMETER_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectParameterByWhere(Parameter parameter) {
        List<Parameter> parameterList = systemParameterDao.selectParameterByWhere(parameter);
        if(parameterList!=null && parameterList.size()!=0){
            return ResponseResult.success(parameterList);
        }
        return ResponseResult.error(new Error(ResponseCodeSystemEnum.PARAMETER_NOT_FIND.getCode(),
                ResponseCodeSystemEnum.PARAMETER_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult insertParameter(Parameter parameter) {
        //判断是否存在参数类型
        Parameter parameterTemp = new Parameter();
        parameter.setParameterId(parameter.getParameterId());
        List<Parameter> parameterList = systemParameterDao.selectParameterByWhere(parameterTemp);
        if(parameterList==null && parameterList.size()==0){
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.PARAMETER_TYPE_NOT_FIND.getCode(),
                    ResponseCodeSystemEnum.PARAMETER_TYPE_NOT_FIND.getDesc()));
        }
        //修改人
        if(StringUtils.isBlank(parameter.getModifyOperator())){
            parameter.setModifyOperator(parameter.getCreateOperator());
        }
        //插入参数
        systemParameterDao.insertParameter(parameter);
        return ResponseResult.success(parameter);
    }

    @Override
    public ResponseResult updateParameter(JSONArray configArray) {
        List<Parameter> parameterList = new ArrayList<>();
        if(configArray==null || configArray.isEmpty() || configArray.size()<=0){
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.PARAMETER_ID_NULL.getCode(),
                    ResponseCodeSystemEnum.PARAMETER_ID_NULL.getDesc()));
        }
        for (Object item:configArray) {
            Parameter parameter = new Parameter();
            JSONObject a = (JSONObject)item;
            Long parameterId = a.getLong("parameterId");
            Long parameterTypeId = a.getLong("parameterTypeId");
            String parameterName = a.getString("parameterName");
            String parameterValue = a.getString("parameterValue");
            Integer parameterSort = a.getInteger("parameterSort");
            String modifyOperator = a.getString("modifyOperator");
            if(parameterId!=null){
                parameter.setParameterId(parameterId);
            }
            if(parameterTypeId != null){
                parameter.setParameterTypeId(parameterTypeId);
            }
            if(!StringUtils.isBlank(parameterName)){
                parameter.setParameterName(parameterName);
            }
            if(!StringUtils.isBlank(parameterValue)){
                parameter.setParameterValue(parameterValue);
            }
            if(parameterSort!=null){
                parameter.setParameterSort(parameterSort);
            }
            if(!StringUtils.isBlank(modifyOperator)){
                parameter.setModifyOperator(modifyOperator);
            }
            parameterList.add(parameter);
        }
        Map map = new HashMap();
        map.put("list",parameterList);
        int i = systemParameterDao.updateParameter(map);
        return ResponseResult.success(i);
    }

    @Override
    public ResponseResult deleteParameter(Long parameterId, String modifyOperator) {
        Parameter parameter = new Parameter();
        parameter.setParameterId(parameterId);
        parameter.setModifyOperator(modifyOperator);
        int i = systemParameterDao.deleteParameter(parameter);
        return ResponseResult.success(i);
    }

    @Override
    public ResponseResult selectParameterTypeList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ParameterType> parameterList = systemParameterDao.selectParameterTypeList();
        if(parameterList!=null && parameterList.size()!=0){
            PageInfo pageInfo = new PageInfo(parameterList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeSystemEnum.PARAMETER_TYPE_NOT_FIND.getCode(),
                ResponseCodeSystemEnum.PARAMETER_TYPE_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectParameterTypeByWhere(ParameterType parameterType) {
        List<ParameterType> parameterList = systemParameterDao.selectParameterTypeByWhere(parameterType);
        if(parameterList!=null && parameterList.size()!=0){
            return ResponseResult.success(parameterList);
        }
        return ResponseResult.error(new Error(ResponseCodeSystemEnum.PARAMETER_TYPE_NOT_FIND.getCode(),
                ResponseCodeSystemEnum.PARAMETER_TYPE_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult insertParameterType(ParameterType parameterType) {
        //修改人
        if(StringUtils.isBlank(parameterType.getModifyOperator())){
            parameterType.setModifyOperator(parameterType.getCreateOperator());
        }
        //插入参数
        systemParameterDao.insertParameterType(parameterType);
        return ResponseResult.success(parameterType);
    }

    @Override
    public ResponseResult updateParameterType(ParameterType parameterType) {
        int i = systemParameterDao.updateParameterType(parameterType);
        return ResponseResult.success(i);
    }

    @Override
    public ResponseResult deleteParameterType(Long parameterTypeId, String modifyOperator) {
        ParameterType parameterType = new ParameterType();
        parameterType.setParameterTypeId(parameterTypeId);
        parameterType.setModifyOperator(modifyOperator);
        int i = systemParameterDao.deleteParameterType(parameterType);
        return ResponseResult.success(i);
    }
}
