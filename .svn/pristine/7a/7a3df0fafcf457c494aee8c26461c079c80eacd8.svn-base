package com.lnmj.store.repository.impl;


import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.store.entity.Department;
import com.lnmj.store.entity.Supplier;
import com.lnmj.store.entity.SupplierCategory;
import com.lnmj.store.repository.IDepartmentDao;
import com.lnmj.store.repository.ISupplierDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class DepartmentDaoImpl extends BaseDao implements IDepartmentDao {


    @Override
    public List<Department> selectDepartmentList(Map map) {
        return super.selectList("department.selectDepartmentList",map);
    }

    @Override
    public int insertDepartment(Department department) {
        return super.insert("department.insertDepartment",department);
    }

    @Override
    public int deleteDepartment(Department department) {
        return super.delete("department.deleteDepartment",department);
    }

    @Override
    public int updateDepartment(Department department) {
        return super.update("department.updateDepartment",department);
    }

    @Override
    public Department selectDefaultDept(Department department) {
        return super.select("department.selectDefaultDept",department);
    }

    @Override
    public int checkDefault(Department department) {
        return super.select("department.checkDefault",department);
    }

    @Override
    public int checkDeptName(Department department) {
        return super.select("department.checkDeptName",department);
    }


}
