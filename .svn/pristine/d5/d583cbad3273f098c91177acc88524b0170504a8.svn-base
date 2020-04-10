package com.lnmj.store.controller.backend;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.IDepartmentService;
import com.lnmj.store.business.ISupplierService;
import com.lnmj.store.entity.Customer;
import com.lnmj.store.entity.Department;
import com.lnmj.store.entity.Supplier;
import com.lnmj.store.entity.SupplierCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: panlin
 * @Date: 2020/2/25 18:23
 * @Description:
 */
@Api(description = "部门管理")
@RestController
@RequestMapping("/manage/department")
public class DepartmentController {
    @Resource
    private IDepartmentService iDepartmentService;
    

    @ApiOperation(value = "查看部门列表",notes = "查看部门列表")
    @RequestMapping(value = "/listDepartment",method = RequestMethod.POST)
    public ResponseResult selectDepartmentList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,String keyWordName,String companyType,String companyId,String searchCompanyType,String searchCompanyId){
        return iDepartmentService.selectDepartmentList(pageNum,pageSize,keyWordName,companyType,companyId,searchCompanyType,searchCompanyId);
    }

    @ApiOperation(value = "查看部门列表不分页",notes = "查看部门列表不分页")
    @RequestMapping(value = "/listDepartmentNoPage",method = RequestMethod.POST)
    public ResponseResult selectDepartmentListNoPage(String companyType,String companyId){
        return iDepartmentService.selectDepartmentListNoPage(companyType,companyId);
    }


    @ApiOperation(value = "添加部门",notes = "添加部门")
    @RequestMapping(value = "/insertDepartment",method = RequestMethod.POST)
    public ResponseResult insertDepartment(Department department){
        return iDepartmentService.insertDepartment(department);
    }

    @ApiOperation(value = "删除部门",notes = "删除部门")
    @RequestMapping(value = "/deleteDepartment",method = RequestMethod.POST)
    public ResponseResult deleteDepartment(Department department){
        return iDepartmentService.deleteDepartment(department);
    }

    @ApiOperation(value = "修改部门",notes = "修改部门")
    @RequestMapping(value = "/updateDepartment",method = RequestMethod.POST)
    public ResponseResult updateDepartment(Department department){
        return iDepartmentService.updateDepartment(department);
    }

    @ApiOperation(value = "查看组织默认部门",notes = "查看组织默认部门")
    @RequestMapping(value = "/selectDefaultDept",method = RequestMethod.POST)
    public ResponseResult selectDefaultDept(Department department){
        return iDepartmentService.selectDefaultDept(department);
    }
}
