package com.lnmj.system.business;

import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.entity.Parameter;
import com.lnmj.system.entity.ParameterType;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/5/29 15:34
 * @Description: 系统参数配置Service接口
 */
@Service("iSystemParameterService")
public interface ISystemParameterService {
    ResponseResult selectParameterList(int pageNum, int pageSize,Long parameterTypeId);
    ResponseResult selectParameterByWhere(Parameter parameter);
    ResponseResult insertParameter(Parameter parameter);
    ResponseResult updateParameter(JSONArray configArray);
    ResponseResult deleteParameter(Long parameterId, String modifyOperator);
    ResponseResult selectParameterTypeList(int pageNum, int pageSize);
    ResponseResult selectParameterTypeByWhere(ParameterType parameterType);
    ResponseResult insertParameterType(ParameterType parameterType);
    ResponseResult updateParameterType(ParameterType parameterType);
    ResponseResult deleteParameterType(Long parameterTypeId, String modifyOperator);
}
