package com.lnmj.store.serviceProxy;


import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: yilihua
 * @Date: 2019/5/24 17:57
 * @Description:
 */
@FeignClient(name = "lnmj-product")
public interface ProductApi {
    //预约：修改服务商品
    @RequestMapping(value = "/api/manage/product/updateServiceProduct", method = RequestMethod.POST)
    ResponseResult updateServiceProduct(@RequestParam("serviceProductId") Long serviceProductId,
                                        @RequestParam(value = "productName", required = false) String productName,
                                        @RequestParam(value = "duration", required = false) String duration,
                                        @RequestParam(value = "productSales", required = false) Integer productSales,
                                        @RequestParam("modifyOperator") String modifyOperator);

    //预约：根据ids查看服务
    @RequestMapping(value = "/api/manage/product/selectServiceListById", method = RequestMethod.POST)
    ResponseResult selectServiceListById(@RequestParam("serviceProductIds") String serviceProductIds);

    //查询所有的项目
    @RequestMapping(value = "/api/manage/product/selectServiceVOList", method = RequestMethod.POST)
    ResponseResult selectServiceVOList(@RequestParam(value = "pageNum") int pageNum,
                                       @RequestParam(value = "pageSize") int pageSize, @RequestParam("keyWordProductCode") String keyWordProductCode, @RequestParam("keyWordProductName") String keyWordProductName);

    //查询所有的护理项目
    @RequestMapping(value = "/api/manage/product/selectServiceVOListNoPage", method = RequestMethod.POST)
    ResponseResult selectServiceVOListNoPage(@RequestParam("keyWordProductCode") String keyWordProductCode, @RequestParam("keyWordProductName") String keyWordProductName);

    //查询所有的项目
    @RequestMapping(value = "/api/manage/product/selectServiceVOListNoPage", method = RequestMethod.POST)
    ResponseResult selectAllServiceProduct();

    //查询所有的商品
    @RequestMapping(value = "/api/manage/product/selectAllProduct", method = RequestMethod.POST)
    ResponseResult selectAllProduct();


    //查看库存
    @RequestMapping(value = "/stock/selectStockList", method = RequestMethod.POST)
    ResponseResult selectStockList(@RequestParam("companyType") Long companyType,
                                   @RequestParam("companyId") Long companyId);

    //查看即使库存
    @RequestMapping(value = "/stock/selectStockProductListNoPage", method = RequestMethod.POST)
    ResponseResult selectStockProductListNoPage(@RequestParam("stock") String stock);

    //添加仓库
    @RequestMapping(value = "/stock/addStock", method = RequestMethod.POST)
    ResponseResult addStock(@RequestParam("companyType") Long companyType,
                            @RequestParam("companyId") Long companyId,
                            @RequestParam("company") String company,
                            @RequestParam("k3Number") String k3Number,
                            @RequestParam("createOperator") String createOperator,
                            @RequestParam("acctId") String acctId,
                            @RequestParam("yewuDataCentreUserName") String yewuDataCentreUserName,
                            @RequestParam("yewuDataCentrePassword") String yewuDataCentrePassword);

    //出库-销售出库
    @RequestMapping(value = "/stock/outstorage", method = RequestMethod.POST)
    ResponseResult outstorage(
            @RequestParam("staffNumber") String staffNumber
            , @RequestParam("stockId") String stockId
            , @RequestParam("outStorageProductJson") String outStorageProductJson
            , @RequestParam("outStorageType") String outStorageType
            , @RequestParam("invoicesType") String invoicesType
            , @RequestParam("outStorageDate") String outStorageDate
            , @RequestParam("settlementCurrency") String settlementCurrency
            , @RequestParam("marketGroup") String marketGroup
            , @RequestParam("orgK3Number") String orgK3Number
            , @RequestParam("client") String client
            , @RequestParam("shipmentGroup") String shipmentGroup
            , @RequestParam("createOperator") String createOperator)
    ;
    //查看所有的单位列表
    @RequestMapping(value = "/unit/selectUnitListNoPage", method = RequestMethod.POST)
    ResponseResult selectUnitListNoPage();


    //修改实体商品及护理商品的销量
    @RequestMapping(value = "/manage/product/updateProductSales", method = RequestMethod.POST)
    ResponseResult updateProductSales(@RequestParam("productCode")String productCode,@RequestParam("type")Integer type,@RequestParam("upOrDown")String upOrDown);

    //修改实体商品第三方库存
    @RequestMapping(value = "/manage/product/updateStockQuantity", method = RequestMethod.POST)
    ResponseResult updateStockQuantity(@RequestParam("productCode")String productCode,@RequestParam("upOrDown")String upOrDown);
    //根据实体商品code找到商品
    @RequestMapping(value = "/manage/product/selectProductByCode", method = RequestMethod.POST)
    ResponseResult selectProductByCode(@RequestParam("productCode") String productCode);

    @ApiOperation(value = "查看是否有使用指定客户的出库单", notes = "查看是否有使用指定客户的出库单")
    @RequestMapping(value = "/stock/checkOutstorageByCust", method = RequestMethod.POST)
    ResponseResult checkOutstorageByCust(@RequestParam("custK3Number") String custK3Number);


    @ApiOperation(value = "查看是否有使用指定供应商的采购入库单", notes = "查看是否有使用指定供应商的采购入库单")
    @RequestMapping(value = "/stock/checkInstorageBySupplierCode", method = RequestMethod.POST)
    ResponseResult checkInstorageBySupplierCode(@RequestParam("supplierCode") String supplierCode);

    @ApiOperation(value = "查看是否有使用指定部门的其他入库单", notes = "查看是否有使用指定部门的其他入库单")
    @RequestMapping(value = "/stock/checkInstorageByDepartment", method = RequestMethod.POST)
    ResponseResult checkInstorageByDepartment(@RequestParam("departmentK3Number") String departmentK3Number);

    //出库-其他出库
    @RequestMapping(value = "/stock/outstorage", method = RequestMethod.POST)
    ResponseResult outstorage(
            @RequestParam("outStorageProductJson") String outStorageProductJson
            , @RequestParam("outStorageType") String outStorageType
            , @RequestParam("invoicesType") String invoicesType
            , @RequestParam("outStorageDate") String outStorageDate
            , @RequestParam("inventoryGroup") String inventoryGroup
            , @RequestParam("receiveGroup") String receiveGroup
            , @RequestParam("inventoryWay") String inventoryWay
            , @RequestParam("businessType") String businessType
            , @RequestParam("shipperType") String shipperType
            , @RequestParam("shipper") String shipper
            , @RequestParam("orgK3Number") String orgK3Number
            , @RequestParam("client") String client
            , @RequestParam("stockId") String stockId
    );

}

