package com.lnmj.data.serviceProxy;

import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "lnmj-k3cloud")
public interface K3CLOUDApi {

    /*---------------------------------物料操作--------------------------------------*/
    @RequestMapping(value = "/k3cloud/product/product/save", method = RequestMethod.POST)
    ResponseResult saveProduct(@RequestParam("acctId") String acctId,
                               @RequestParam("dataCentreUserName") String dataCentreUserName,
                               @RequestParam("dataCentrePassword") String dataCentrePassword,
                               @RequestParam("model") String model,
                               @RequestParam("isautosubmitandaudit") Boolean isautosubmitandaudit);


    @ApiOperation(value = "批量添加", notes = "批量添加")
    @RequestMapping(value = "/k3cloud/product/product/batchAdd", method = RequestMethod.POST)
    ResponseResult batchAdd(@RequestParam(value = "acctId",required = false) String acctId,
                            @RequestParam(value = "dataCentreUserName",required = false) String dataCentreUserName,
                            @RequestParam(value = "dataCentrePassword",required = false) String dataCentrePassword,
                            @RequestParam(value = "productArr",required = false) String productArr);

    @RequestMapping(value = "/k3cloud/product/product/view", method = RequestMethod.POST)
    ResponseResult viewProduct(@RequestParam("acctId") String acctId,
                               @RequestParam("dataCentreUserName") String dataCentreUserName,
                               @RequestParam("dataCentrePassword") String dataCentrePassword,
                               @RequestParam("number") String number, @RequestParam("id") String id);

    @RequestMapping(value = "/k3cloud/product/product/submit", method = RequestMethod.POST)
    ResponseResult submitProduct(@RequestParam("acctId") String acctId,
                                 @RequestParam("dataCentreUserName") String dataCentreUserName,
                                 @RequestParam("dataCentrePassword") String dataCentrePassword,
                                 @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/product/product/audit", method = RequestMethod.POST)
    ResponseResult auditProduct(@RequestParam("acctId") String acctId,
                                @RequestParam("dataCentreUserName") String dataCentreUserName,
                                @RequestParam("dataCentrePassword") String dataCentrePassword,
                                @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/product/product/unAudit", method = RequestMethod.POST)
    ResponseResult unAuditProduct(@RequestParam("acctId") String acctId,
                                  @RequestParam("dataCentreUserName") String dataCentreUserName,
                                  @RequestParam("dataCentrePassword") String dataCentrePassword,
                                  @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/product/product/delete", method = RequestMethod.POST)
    ResponseResult deleteProduct(@RequestParam("acctId") String acctId,
                                 @RequestParam("dataCentreUserName") String dataCentreUserName,
                                 @RequestParam("dataCentrePassword") String dataCentrePassword,
                                 @RequestParam("numbers") String numbers, @RequestParam("ids") String ids);

    @RequestMapping(value = "/k3cloud/product/product/allocate", method = RequestMethod.POST)
    ResponseResult allocate(@RequestParam("PkIds") String PkIds, @RequestParam("TOrgIds") String TOrgIds);



    /*---------------------供应商------------------------*/
    @ApiOperation(value = "批量保存", notes = "批量保存")
    @RequestMapping(value = "/k3cloud/supplier/supplier/batchsave", method = RequestMethod.POST)
    public ResponseResult batchsaveSupplier(
            @RequestParam("acctId") String acctId,
            @RequestParam("dataCentreUserName") String dataCentreUserName,
            @RequestParam("dataCentrePassword") String dataCentrePassword,
            @RequestParam("supplierArr") String supplierArr
           /* @RequestParam("fName") String fName,
            @RequestParam("fCreateOrgId") String fCreateOrgId,
            @RequestParam("fUseOrgId") String fUseOrgId,
            @RequestParam("fPayCurrencyId") String fPayCurrencyId,
            @RequestParam("fLocationInfoListStr") String fLocationInfoListStr*/);
}


