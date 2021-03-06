package com.lnmj.product.controller.backend;

import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeStockEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IStockService;
import com.lnmj.product.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yilihua
 * @Date: 2019/9/23 16:38
 * @Description: 仓库管理
 */
@Api(description = "仓库管理")
@RestController
@RequestMapping("/stock")
public class StockController {
    @Resource
    private IStockService stockService;

    /**
     *@Description 分页查询入库的数量变化（即时库存，入库，出库）
     *@Param [pageNum, pageSize, stock, productCode, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/10/28
     *@Time 21:20
     */
    @ApiOperation(value = "分页查询入库的数量变化", notes = "分页查询入库的数量变化")
    @RequestMapping(value = "/selectProductNumberList", method = RequestMethod.POST)
    public ResponseResult selectProductNumberList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                  String stock,String access_token) {
        return stockService.selectProductNumberList(pageNum,pageSize,stock);
    }
    /**
     *@Description 商品的数量（即时库存，入库，出库）
     *@Param [stock, productCode, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/10/28
     *@Time 21:20
     */
    @ApiOperation(value = "分页查询入库的数量变化", notes = "分页查询入库的数量变化")
    @RequestMapping(value = "/selectProductNumber", method = RequestMethod.POST)
    public ResponseResult selectProductNumber(String stock,String productCode,String access_token) {
        if(StringUtils.isBlank(productCode)){
            return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(stock)){
            return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.STOCK_NOT_NULL.getDesc()));
        }
        return stockService.selectProductNumber(stock,productCode);
    }

    /**
    *@Description 商品库存验证
    *@Param [stock, productCode, numbers, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019-11-14
    *@Time 17:59
    */
    @ApiOperation(value = "商品库存验证", notes = "商品库存验证")
    @RequestMapping(value = "/checkProductStockNumber", method = RequestMethod.POST)
    public ResponseResult checkProductStockNumbers(String stockCode,String productCode, Integer numbers, String access_token) {
        if(StringUtils.isBlank(productCode)){
            return ResponseResult.error(new Error(ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.PRODUCTCODE_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(stockCode)){
            return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.STOCK_NOT_NULL.getDesc()));
        }
        return stockService.checkProductStockNumbers(stockCode,productCode,numbers);
    }

    /**
    *@Description 确认收货
    *@Param [inStorageId, confirm, confirmStatus, remark, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019-11-14
    *@Time 17:56
    */
    @ApiOperation(value = "确认收货", notes = "确认收货")
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public ResponseResult confirm(Long inStorageId,String confirm, Integer confirmStatus,String remark, String access_token) {
        return stockService.confirmInstorage(inStorageId,confirm,confirmStatus,remark);
    }
    
    /**
    *@Description 查询仓库
    *@Param [stockId, stockCode, companyType, companyId, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/29
    *@Time 18:59
    */
    @ApiOperation(value = "查询仓库", notes = "查询仓库")
    @RequestMapping(value = "/selectStockList", method = RequestMethod.POST)
    public ResponseResult selectStockList(Long stockId,String stockCode,Long companyType,Long companyId,Boolean isSon) {
        return stockService.selectStockList(stockId,stockCode,companyType,companyId,isSon);
    }

    /**
    *@Description 更新仓库
    *@Param [stock, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/30
    *@Time 9:51
    */
    @ApiOperation(value = "更新仓库", notes = "更新仓库")
    @RequestMapping(value = "/updateStockById", method = RequestMethod.POST)
    public ResponseResult updateStockById(Stock stock, String access_token) {
        if(stock.getStockId()==null){
            return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_ID_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.STOCK_ID_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(stock.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodeStockEnum.MODIFY_OPERATOR_NULL.getCode(),
                    ResponseCodeStockEnum.MODIFY_OPERATOR_NULL.getDesc()));
        }
        return stockService.updateStockById(stock);
    }
    /**
    *@Description addStock 审核一起
    *@Param [stock, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/30
    *@Time 10:25
    */
    @ApiOperation(value = "添加仓库", notes = "添加仓库")
    @RequestMapping(value = "/addStock", method = RequestMethod.POST)
    public ResponseResult addStock(Stock stock, String acctId, String yewuDataCentreUserName,String yewuDataCentrePassword) {
        if(stock.getCompanyType()==null){
            return ResponseResult.error(new Error(ResponseCodeStockEnum.COMPANY_TYPE_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.COMPANY_TYPE_NOT_NULL.getDesc()));
        }
        if(stock.getCompanyId()==null){
            return ResponseResult.error(new Error(ResponseCodeStockEnum.COMPANY_NOT_NULL.getCode(),
                    ResponseCodeStockEnum.COMPANY_NOT_NULL.getDesc()));
        }
       /* if(StringUtils.isBlank(stock.getCreateOperator())){
            return ResponseResult.error(new Error(ResponseCodeStockEnum.CREATE_OPERATOR_NULL.getCode(),
                    ResponseCodeStockEnum.CREATE_OPERATOR_NULL.getDesc()));
        }*/
        return stockService.addStock(stock,acctId,yewuDataCentreUserName,yewuDataCentrePassword);
    }

    /**
    *@Description 查询枚举
    *@Param [name, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/29
    *@Time 18:56
    */
    @ApiOperation(value = "查询枚举", notes = "查询枚举")
    @RequestMapping(value = "/selectEnumByName", method = RequestMethod.POST)
    public ResponseResult selectEnumByName(String name,String access_token) {
        return stockService.selectEnumByName(name);
    }

    /**
    *@Description 分页查询即时库存
    *@Param [pageNum, pageSize, stock, productCode, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/28
    *@Time 21:20
    */
    @ApiOperation(value = "分页查询即时库存", notes = "分页查询即时库存")
    @RequestMapping(value = "/selectStockProductList", method = RequestMethod.POST)
    public ResponseResult selectStockProductList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                 String stock, String productCode,String productName,String access_token) {
        return stockService.selectStockProductList(pageNum,pageSize,stock,productCode,productName);
    }

    /**
     *@Description 分页查询即时库存
     *@Param [pageNum, pageSize, stock, productCode, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/10/28
     *@Time 21:20
     */
    @ApiOperation(value = "分页查询即时库存不分页", notes = "分页查询即时库存不分页")
    @RequestMapping(value = "/selectStockProductListNoPage", method = RequestMethod.POST)
    public ResponseResult selectStockProductListNoPage(String stock, String productCode,String access_token) {
        return stockService.selectStockProductListNoPage(stock,productCode);
    }

    /**
    *@Description 根据商品名称分页模糊查询即时库存
    *@Param [pageNum, pageSize, stock, productCode, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/28
    *@Time 21:20
    */
    @ApiOperation(value = "根据商品名称分页模糊查询即时库存", notes = "根据商品名称分页模糊查询即时库存")
    @RequestMapping(value = "/selectStockProductListByProductName", method = RequestMethod.POST)
    public ResponseResult selectStockProductListByProductName(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                 String productName,String access_token) {
        return stockService.selectStockProductListByProductName(pageNum,pageSize,productName);
    }

    /**
    *@Description 分页查询入库单
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/28
    *@Time 21:22
    */
    @ApiOperation(value = "分页查询入库单", notes = "分页查询入库单")
    @RequestMapping(value = "/selectInstorageList", method = RequestMethod.POST)
    public ResponseResult selectInstorageList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                              String inStorageType, String inStorageDate,String stockGroup, String inventoryGroup,String stockId,String provider) {
        return stockService.selectInstorageList(pageNum,pageSize,inStorageType,inStorageDate,stockGroup,inventoryGroup,stockId,provider);
    }

    /**
    *@Description 分页查询出库单
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/28
    *@Time 21:22
    */
    @ApiOperation(value = "分页查询出库单", notes = "分页查询出库单")
    @RequestMapping(value = "/selectOutstorageList", method = RequestMethod.POST)
    public ResponseResult selectOutstorageList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                               String outStorageType, String outStorageDate,String stockGroup,String inventoryGroup,String client,String access_token) {
        return stockService.selectOutstorageList(pageNum,pageSize,outStorageType,outStorageDate,stockGroup,inventoryGroup,client);
    }


    /**
     *@Description 出库：出库、出库商品、更新即时库存
     *@Param [instorage, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/10/28
     *@Time 21:34
     */
    @ApiOperation(value = "出库-并减库存", notes = "出库-并减库存")
    @RequestMapping(value = "/outstorage", method = RequestMethod.POST)
    public ResponseResult outstorage(OutstoragePreAudit outstorage, String access_token) {
        if(!StringUtils.isBlank(outstorage.getOutStorageProductJson())){
            JSONArray jsonArray = JSONArray.parseArray(outstorage.getOutStorageProductJson());
            outstorage.setOutStorageProductList(jsonArray.toJavaList(OutStorageProduct.class));
        }
        return stockService.outstorage(outstorage);
    }

    @ApiOperation(value = "入库", notes = "入库")
    @RequestMapping(value = "/instorage", method = RequestMethod.POST)
    public ResponseResult instorage(InstoragePreAudit instorage, String access_token) {
        if(!StringUtils.isBlank(instorage.getInStorageProductJson())){
            JSONArray jsonArray = JSONArray.parseArray(instorage.getInStorageProductJson());
            instorage.setInStorageProductList(jsonArray.toJavaList(InStorageProduct.class));
        }
        return stockService.instorage(instorage);
    }

    @ApiOperation(value = "出库预审核", notes = "出库预审核")
    @RequestMapping(value = "/outstoragePreAudit", method = RequestMethod.POST)
    public ResponseResult outstoragePreAudit(OutstoragePreAudit outstorage, String access_token) {
        if(!StringUtils.isBlank(outstorage.getOutStorageProductJson())){
            JSONArray jsonArray = JSONArray.parseArray(outstorage.getOutStorageProductJson());
            outstorage.setOutStorageProductList(jsonArray.toJavaList(OutStorageProduct.class));
        }
        return stockService.outstoragePreAudit(outstorage);
    }



    /**
     *@Description 入库: 入库、入库商品、更新即时库存
     *@Param [instorage, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/10/28
     *@Time 21:34outstoragePreAudit
     */
    @ApiOperation(value = "入库预审核", notes = "入库出库预审核")
    @RequestMapping(value = "/instoragePreAudit", method = RequestMethod.POST)
    public ResponseResult instoragePreAudit(InstoragePreAudit instorage, String access_token) {
        if(!StringUtils.isBlank(instorage.getInStorageProductJson())){
            JSONArray jsonArray = JSONArray.parseArray(instorage.getInStorageProductJson());
            instorage.setInStorageProductList(jsonArray.toJavaList(InStorageProduct.class));
        }
        return stockService.instoragePreAudit(instorage);
    }


    /**
     *@Description 入库: 入库、入库商品、更新即时库存
     *@Param [instorage, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/10/28
     *@Time 21:34outstoragePreAudit
     */
    @ApiOperation(value = "入库预审核-退货（发起退货）", notes = "入库出库预审核-退货（发起退货）")
    @RequestMapping(value = "/instoragePreAuditReturn", method = RequestMethod.POST)
    public ResponseResult instoragePreAuditReturn(Long inStorageId,Integer number,Long inStorageProductID) {
        return stockService.instoragePreAuditReturn(inStorageId,number,inStorageProductID);
    }

    @ApiOperation(value = "作废入库", notes = "作废入库")
    @RequestMapping(value = "/cancelInstoragePreAudit", method = RequestMethod.POST)
    public ResponseResult cancelInstoragePreAudit(InstoragePreAudit instorage) {
        return stockService.cancelInstoragePreAudit(instorage);
    }

    @ApiOperation(value = "其他出库退货", notes = "其他出库退货")
    @RequestMapping(value = "/outstorageReturn", method = RequestMethod.POST)
    public ResponseResult outstorageReturn(OutstoragePreAudit outstorage) {

        return stockService.outstorageReturn(outstorage);
    }

    @ApiOperation(value = "销售退货", notes = "其他出库退货")
    @RequestMapping(value = "/saveMarketReturn", method = RequestMethod.POST)
    public ResponseResult saveMarketReturn(OutstoragePreAudit outstorage) {
        return stockService.outstorageReturn(outstorage);
    }

    /**
    *@Description 审核入库单
    *@Param [inStorageId, auditor, inStorageStatus, remark, invalid, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019-11-05
    *@Time 16:29
    */
    @ApiOperation(value = "审核入库单", notes = "审核入库单")
    @RequestMapping(value = "/auditInstorage", method = RequestMethod.POST)
    public ResponseResult auditInstorage(Long inStorageId,String auditor,
            String inStorageStatus,String remark,String invalid, String access_token) {
        return stockService.auditInstorage(inStorageId,auditor,inStorageStatus,remark,invalid);
    }

    /**
    *@Description 审核出库单
    *@Param [outStorageId, auditor, outstorageStatus, remark, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019-11-05
    *@Time 16:27
    */
    @ApiOperation(value = "审核出库单", notes = "审核出库单")
    @RequestMapping(value = "/auditOutstorage", method = RequestMethod.POST)
    public ResponseResult auditOutstorage(Long outStorageId,String auditor,
                                          Integer outstorageStatus,String remark,String inventoryWay,String access_token) {
        return stockService.auditOutstorage(outStorageId,auditor,outstorageStatus,remark,inventoryWay);
    }

    @ApiOperation(value = "删除仓库", notes = "审核出库单")
    @RequestMapping(value = "/delStockById", method = RequestMethod.POST)
    public ResponseResult delStockById(Long companyId,Long companyType) {
        return stockService.delStockById(companyId,companyType);
    }

    @ApiOperation(value = "查看是否有使用指定客户的出库单", notes = "查看是否有使用指定客户的出库单")
    @RequestMapping(value = "/checkOutstorageByCust", method = RequestMethod.POST)
    public ResponseResult checkOutstorageByCust(String custK3Number) {
        return stockService.checkOutstorageByCust(custK3Number);
    }


    @ApiOperation(value = "查看是否有使用指定供应商的采购入库单", notes = "查看是否有使用指定供应商的采购入库单")
    @RequestMapping(value = "/checkInstorageBySupplierCode", method = RequestMethod.POST)
    public ResponseResult checkInstorageBySupplierCode(String supplierCode) {
        return stockService.checkInstorageBySupplierCode(supplierCode);
    }

    @ApiOperation(value = "查看是否有使用指定部门的其他入库单", notes = "查看是否有使用指定部门的其他入库单")
    @RequestMapping(value = "/checkInstorageByDepartment", method = RequestMethod.POST)
    public ResponseResult checkInstorageByDepartment(String departmentK3Number) {
        return stockService.checkInstorageByDepartment(departmentK3Number);
    }

    @ApiOperation(value = "根据id查看出库单商品", notes = "根据id查看出库单商品")
    @RequestMapping(value = "/selectOutstorageProductById", method = RequestMethod.POST)
    public ResponseResult selectOutstorageProductById(Long outStorageId) {
        return stockService.selectOutstorageProductById(outStorageId);
    }

    @ApiOperation(value = "根据id查看出库单", notes = "根据id查看出库单")
    @RequestMapping(value = "/selectOutstorageById", method = RequestMethod.POST)
    public ResponseResult selectOutstorageById(Long outStorageId) {
        return stockService.selectOutstorageById(outStorageId);
    }


}
















