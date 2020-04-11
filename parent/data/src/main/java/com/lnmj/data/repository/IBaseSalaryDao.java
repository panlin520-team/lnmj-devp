package com.lnmj.data.repository;

import com.lnmj.data.entity.Basesalary;

import java.util.HashMap;
import java.util.List;

public interface IBaseSalaryDao {
    List<Basesalary> selectList();

    int addBaseSalary(Basesalary basesalary);

    int updateBaseSalary(Basesalary basesalary);

    int deleteBaseSalary(HashMap<Object, Object> map);

    List<Basesalary> selectByCondition(Basesalary basesalary);
}
