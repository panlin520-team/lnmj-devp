package com.lnmj.store.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.entity.Department;
import com.lnmj.store.entity.Supplier;
import com.lnmj.store.entity.SupplierCategory;
import org.springframework.stereotype.Service;


@Service("iDepartmentService")
public interface IDepartmentService {
    ResponseResult selectDepartmentList(int pageNum, int pageSize, String keyWordName, String companyType, String companyId,String searchCompanyType,String searchCompanyId);
    ResponseResult selectDepartmentListNoPage(String companyType, String companyId);
    ResponseResult insertDepartment(Department department);
    ResponseResult deleteDepartment(Department department);
    ResponseResult updateDepartment(Department department);
    ResponseResult selectDefaultDept(Department department);
}
