package com.lnmj.common.generator.custom_project_user_refuse;

import com.lnmj.common.generator.custom_project_user_refuse.CustomProjectUserRefuse;
import com.lnmj.common.generator.custom_project_user_refuse.CustomProjectUserRefuseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomProjectUserRefuseMapper {
    int countByExample(CustomProjectUserRefuseExample example);

    int deleteByExample(CustomProjectUserRefuseExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustomProjectUserRefuse record);

    int insertSelective(CustomProjectUserRefuse record);

    List<CustomProjectUserRefuse> selectByExample(CustomProjectUserRefuseExample example);

    CustomProjectUserRefuse selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustomProjectUserRefuse record, @Param("example") CustomProjectUserRefuseExample example);

    int updateByExample(@Param("record") CustomProjectUserRefuse record, @Param("example") CustomProjectUserRefuseExample example);

    int updateByPrimaryKeySelective(CustomProjectUserRefuse record);

    int updateByPrimaryKey(CustomProjectUserRefuse record);
}