package com.lnmj.system.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.system.entity.Parameter;
import com.lnmj.system.entity.ParameterType;
import com.lnmj.system.repository.ISystemParameterDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @Author: yilihua
 * @Date: 2019/5/29 15:39
 * @Description: 系统参数配置Dao
 */
@Repository
public class SystemParameterDaoImpl extends BaseDao implements ISystemParameterDao {

    @Override
    public List<Parameter> selectParameterList(Long parameterTypeId) {
        return super.selectList("systemParameter.selectParameterList",parameterTypeId);
    }

    @Override
    public List<Parameter> selectParameterByWhere(Parameter parameter) {
        return super.selectList("systemParameter.selectParameterByWhere",parameter);
    }

    @Override
    public int insertParameter(Parameter parameter) {
        return super.insert("systemParameter.insertParameter",parameter);
    }

    @Override
    public int updateParameter(Map map) {
        return super.update("systemParameter.updateParameter",map);
    }

    @Override
    public int deleteParameter(Parameter parameter) {
        return super.update("systemParameter.deleteParameter",parameter);
    }

    @Override
    public List<ParameterType> selectParameterTypeList() {
        return super.selectList("systemParameterType.selectParameterTypeList");
    }

    @Override
    public List<ParameterType> selectParameterTypeByWhere(ParameterType parameterType) {
        return super.selectList("systemParameterType.selectParameterTypeByWhere",parameterType);
    }

    @Override
    public int insertParameterType(ParameterType parameterType) {
        return super.insert("systemParameterType.insertParameterType",parameterType);
    }

    @Override
    public int updateParameterType(ParameterType parameterType) {
        return super.update("systemParameterType.updateParameterType",parameterType);
    }

    @Override
    public int deleteParameterType(ParameterType parameterType) {
        return super.update("systemParameterType.deleteParameterType",parameterType);
    }
}
