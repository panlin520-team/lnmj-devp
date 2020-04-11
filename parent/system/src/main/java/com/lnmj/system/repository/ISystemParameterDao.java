package com.lnmj.system.repository;

import com.lnmj.system.entity.Parameter;
import com.lnmj.system.entity.ParameterType;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/5/29 15:38
 * @Description: 系统参数配置Dao接口
 */
public interface ISystemParameterDao {
    List<Parameter> selectParameterList(Long parameterTypeId);
    List<Parameter> selectParameterByWhere(Parameter parameter);
    int insertParameter(Parameter parameter);
    int updateParameter(Map map);
    int deleteParameter(Parameter parameter);
    List<ParameterType> selectParameterTypeList();
    List<ParameterType> selectParameterTypeByWhere(ParameterType parameterType);
    int insertParameterType(ParameterType parameterType);
    int updateParameterType(ParameterType parameterType);
    int deleteParameterType(ParameterType parameterType);
}
