package com.lnmj.store.controller.backend;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.ICustomerService;
import com.lnmj.store.business.IDepartmentService;
import com.lnmj.store.entity.Customer;
import com.lnmj.store.entity.Department;
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
@Api(description = "客户管理")
@RestController
@RequestMapping("/manage/customer")
public class CustomerController {
    @Resource
    private ICustomerService iCustomerService;
    

    @ApiOperation(value = "查看客户列表",notes = "查看客户列表")
    @RequestMapping(value = "/listCustomer",method = RequestMethod.POST)
    public ResponseResult selectCustomerList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,String keyWordName,String companyType,String companyId){
        return iCustomerService.selectCustomerList(pageNum,pageSize,keyWordName,companyType,companyId);
    }

    @ApiOperation(value = "查看客户列表不分页",notes = "查看客户列表不分页")
    @RequestMapping(value = "/listCustomerNoPage",method = RequestMethod.POST)
    public ResponseResult selectCustomerListNoPage(String companyType,String companyId){
        return iCustomerService.selectCustomerListNoPage(companyType,companyId);
    }


    @ApiOperation(value = "根据客户k3编号查看客户",notes = "根据客户k3编号查看客户")
    @RequestMapping(value = "/selectCustomerByNumber",method = RequestMethod.POST)
    public ResponseResult selectCustomerByNumber(String k3Number){
        return iCustomerService.selectCustomerByNumber(k3Number);
    }

    @ApiOperation(value = "根据条件查看客户",notes = "根据条件查看客户")
    @RequestMapping(value = "/selectCustomerByConditon",method = RequestMethod.POST)
    public ResponseResult selectCustomerByConditon(Customer customer){
        return iCustomerService.selectCustomerByConditon(customer);
    }

    @ApiOperation(value = "添加客户",notes = "添加客户")
    @RequestMapping(value = "/insertCustomer",method = RequestMethod.POST)
    public ResponseResult insertCustomer(Customer customer){
        return iCustomerService.insertCustomer(customer);
    }

    @ApiOperation(value = "删除客户",notes = "删除客户")
    @RequestMapping(value = "/deleteCustomer",method = RequestMethod.POST)
    public ResponseResult deleteCustomer(Customer customer){
        return iCustomerService.deleteCustomer(customer);
    }

    @ApiOperation(value = "修改客户",notes = "修改客户")
    @RequestMapping(value = "/updateCustomer",method = RequestMethod.POST)
    public ResponseResult updateCustomer(Customer customer){
        return iCustomerService.updateCustomer(customer);
    }

    @ApiOperation(value = "查看组织默认客户",notes = "查看组织默认客户")
    @RequestMapping(value = "/selectDefaultCust",method = RequestMethod.POST)
    public ResponseResult selectDefaultCust(Customer customer){
        return iCustomerService.selectDefaultCust(customer);
    }
}
