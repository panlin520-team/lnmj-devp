package com.lnmj.store.repository;

import com.lnmj.store.entity.Department;
import com.lnmj.store.entity.Supplier;
import com.lnmj.store.entity.SupplierCategory;

import java.util.List;
import java.util.Map;

public interface IDepartmentDao {
    List<Department> selectDepartmentList(Map map);
    int insertDepartment(Department department);
    int deleteDepartment(Department department);
    int updateDepartment(Department department);
    Department selectDefaultDept(Department department);
    int checkDefault(Department department);
    int checkDeptName(Department department);
}
