package com.lnmj.k3cloud.controller.backend.product;


import com.lnmj.common.response.ResponseResult;
import com.lnmj.k3cloud.business.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "金蝶K3-物资-物资")
@RestController
@RequestMapping("/k3cloud/product/product")
public class ProductController {
    @Resource
    private IProductService iProductService;

    @ApiOperation(value = "保存", notes = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult save(String acctId,String dataCentreUserName,String dataCentrePassword,String fname,Integer id,Boolean isautosubmitandaudit
            ) {
        return iProductService.productSave(acctId,dataCentreUserName,dataCentrePassword,fname,id,isautosubmitandaudit);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        return iProductService.productDelete(acctId,dataCentreUserName,dataCentrePassword,numbers, ids);
    }

    @ApiOperation(value = "查看", notes = "查看")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseResult view(String acctId,String dataCentreUserName,String dataCentrePassword,String number, String id) {
        return iProductService.productView(acctId,dataCentreUserName,dataCentrePassword,number, id);
    }

    @ApiOperation(value = "提交", notes = "提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseResult submit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        return iProductService.productSubimt(acctId,dataCentreUserName,dataCentrePassword,numbers, ids);
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseResult audit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        return iProductService.productAudit(acctId,dataCentreUserName,dataCentrePassword,numbers, ids);
    }

    @ApiOperation(value = "反审核", notes = "审核")
    @RequestMapping(value = "/unAudit", method = RequestMethod.POST)
    public ResponseResult unAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids) {
        return iProductService.productUnAudit(acctId,dataCentreUserName,dataCentrePassword,numbers, ids);
    }

    @ApiOperation(value = "分配", notes = "分配")
    @RequestMapping(value = "/allocate", method = RequestMethod.POST)
    public ResponseResult allocate(String acctId,String dataCentreUserName,String dataCentrePassword,String PkIds, String TOrgIds) {
        return iProductService.productAllocate(acctId,dataCentreUserName,dataCentrePassword, PkIds,TOrgIds);
    }

    @ApiOperation(value = "分组", notes = "分组")
    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public ResponseResult group(String acctId,String dataCentreUserName,String dataCentrePassword,String PkIds, String TOrgIds) {
        return iProductService.productAllocate(acctId,dataCentreUserName,dataCentrePassword, PkIds,TOrgIds);
    }


    @ApiOperation(value = "批量添加", notes = "批量添加")
    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    public ResponseResult batchAdd(String acctId,String dataCentreUserName,String dataCentrePassword,String productArr) {
        return iProductService.batchAdd(acctId,dataCentreUserName,dataCentrePassword,productArr);
    }
}
