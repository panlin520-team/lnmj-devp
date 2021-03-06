package com.lnmj.store.controller.backend;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeCompanyEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.ICompanyService;
import com.lnmj.store.business.ISupplierService;
import com.lnmj.store.entity.Company;
import com.lnmj.store.entity.Subsidiary;
import com.lnmj.store.entity.Supplier;
import com.lnmj.store.entity.SupplierCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yilihua
 * @Date: 2019/9/19 18:23
 * @Description:
 */
@Api(description = "供应商管理")
@RestController
@RequestMapping("/manage/supplier")
public class SupplierController {
    @Resource
    private ISupplierService iSupplierService;
    

    @ApiOperation(value = "查询供应商列表",notes = "查询供应商列表")
    @RequestMapping(value = "/listSupplier",method = RequestMethod.POST)
    public ResponseResult selectSupplierList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,String keyWordName,String keyWordPhone,String supplierCategoryId,String supplierType,String companyType,String companyId){
        return iSupplierService.selectSupplierList(pageNum,pageSize,keyWordName,keyWordPhone,supplierCategoryId,supplierType,companyType,companyId);
    }

    @ApiOperation(value = "查询供应商列表不分页",notes = "查询供应商列表不分页")
    @RequestMapping(value = "/listSupplierNoPage",method = RequestMethod.POST)
    public ResponseResult selectSupplierListNoPage(String keyWordName,String keyWordPhone,String supplierCategoryId,String supplierType,String companyType,String companyId){
        return iSupplierService.selectSupplierListNoPage(keyWordName,keyWordPhone,supplierCategoryId,supplierType,companyType,companyId);
    }


    @ApiOperation(value = "根据条件查询供应商",notes = "根据条件查询供应商")
    @RequestMapping(value = "/selectSupplierByCondition",method = RequestMethod.POST)
    public ResponseResult selectSupplierByCondition(Supplier supplier){
        return iSupplierService.selectSupplierByCondition(supplier);
    }

    @ApiOperation(value = "修改供应商",notes = "修改供应商")
    @RequestMapping(value = "/updateSupplier",method = RequestMethod.POST)
    public ResponseResult updateSupplier(Supplier supplier){
        return iSupplierService.updateSupplier(supplier);
    }

    @ApiOperation(value = "删除供应商",notes = "修改供应商")
    @RequestMapping(value = "/deleteSupplier",method = RequestMethod.POST)
    public ResponseResult deleteSupplier(Supplier supplier){
        return iSupplierService.deleteSupplier(supplier);
    }

    @ApiOperation(value = "添加供应商",notes = "修改供应商")
    @RequestMapping(value = "/addSupplier",method = RequestMethod.POST)
    public ResponseResult addSupplier(Supplier supplier,String companyId,String companyType,String orgK3Number){
        return iSupplierService.addSupplier(supplier,companyId,companyType,orgK3Number);
    }

    @ApiOperation(value = "查询供应商分类",notes = "查询供应商分类")
    @RequestMapping(value = "/listSupplierCategory",method = RequestMethod.POST)
    public ResponseResult listSupplierCategory(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                             @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,String keyWordName,String companyType,String companyId){
        return iSupplierService.selectSupplierCategoryList(pageNum,pageSize,keyWordName,companyType,companyId);
    }

    @ApiOperation(value = "查询供应商分类不分页",notes = "查询供应商分类不分页")
    @RequestMapping(value = "/listSupplierCategoryNoPage",method = RequestMethod.POST)
    public ResponseResult listSupplierCategoryNoPage(String companyType,String companyId){
        return iSupplierService.listSupplierCategoryNoPage(companyType,companyId);
    }

    @ApiOperation(value = "修改供应商",notes = "修改供应商")
    @RequestMapping(value = "/updateSupplierCategory",method = RequestMethod.POST)
    public ResponseResult updateSupplierCategory(SupplierCategory supplierCategory){
        return iSupplierService.updateSupplierCategory(supplierCategory);
    }

    @ApiOperation(value = "删除供应商",notes = "修改供应商")
    @RequestMapping(value = "/deleteSupplierCategory",method = RequestMethod.POST)
    public ResponseResult deleteSupplierCategory(SupplierCategory supplierCategory){
        return iSupplierService.deleteSupplierCategory(supplierCategory);
    }

    @ApiOperation(value = "添加供应商分类",notes = "修改供应商分类")
    @RequestMapping(value = "/addSupplierCategory",method = RequestMethod.POST)
    public ResponseResult addSupplierCategory(SupplierCategory supplierCategory){
        return iSupplierService.addSupplierCategory(supplierCategory);
    }

    @ApiOperation(value = "根据供应商code查看供应商",notes = "根据供应商code查看供应商")
    @RequestMapping(value = "/selectSupplierByCode",method = RequestMethod.POST)
    public ResponseResult selectSupplierByCode(String supplierCode){
        return iSupplierService.selectSupplierByCode(supplierCode);
    }

    @ApiOperation(value = "批量分配供应商-子公司", notes = "批量分配供应商-子公司")
    @RequestMapping(value = "/batchAllocationSubCompany", method = RequestMethod.POST)
    public ResponseResult batchAllocationSubCompany(String companyId,String companyType,String supplierArrayStr,String subcompanyIdArrayStr) {
        return iSupplierService.batchAllocationSubCompany(companyId,companyType,supplierArrayStr,subcompanyIdArrayStr);
    }

    @ApiOperation(value = "批量分配供应商-分公司", notes = "批量分配供应商-分公司")
    @RequestMapping(value = "/batchAllocationStore", method = RequestMethod.POST)
    public ResponseResult batchAllocationStore(String companyId,String companyType,String supplierArrayStr,String storeIdArrayStr) {
        return iSupplierService.batchAllocationStore(companyId,companyType,supplierArrayStr,storeIdArrayStr);
    }

    @ApiOperation(value = "批量取消分配供应商-子公司", notes = "批量取消分配供应商-子公司")
    @RequestMapping(value = "/batchCancelAllocationSubCompany", method = RequestMethod.POST)
    public ResponseResult batchCancelAllocationSubCompany(String companyId,String companyType,String supplierArrayStr,String subcompanyIdArrayStr) {
        return iSupplierService.batchCancelAllocationSubCompany(companyId,companyType,supplierArrayStr,subcompanyIdArrayStr);
    }

    @ApiOperation(value = "批量取消分配供应商-分公司", notes = "批量取消分配供应商-分公司")
    @RequestMapping(value = "/batchCancelAllocationStore", method = RequestMethod.POST)
    public ResponseResult batchCancelAllocationStore(String companyId,String companyType,String supplierArrayStr,String storeIdArrayStr) {
        return iSupplierService.batchCancelAllocationStore(companyId,companyType,supplierArrayStr,storeIdArrayStr);
    }

    @ApiOperation(value = "查看供应商类别列表", notes = "查看供应商类别列表")
    @RequestMapping(value = "/selectSuppTypeList", method = RequestMethod.POST)
    public ResponseResult selectSuppTypeList() {
        return iSupplierService.selectSuppTypeList();
    }
}
