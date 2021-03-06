package com.lnmj.order.serviceProxy;


import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @Author: yilihua
 * @Date: 2019/5/24 17:57
 * @Description:
 */
@FeignClient(name = "lnmj-product")
public interface ProductApi {
    @RequestMapping(value = "/productBrand/selectBrandById", method = RequestMethod.POST)
    ResponseResult selectBrandById(@RequestParam("productBrandId") Long productBrandId);

    //预约：根据ids查看服务
    @RequestMapping(value = "/manage/product/selectServiceListById", method = RequestMethod.POST)
    ResponseResult selectServiceListById(@RequestParam("serviceProductIds") String serviceProductIds);

    @RequestMapping(value = "/manage/product/selectAllProduct", method = RequestMethod.POST)
    ResponseResult selectAllProduct();

    //查看库存
    @RequestMapping(value = "/stock/selectStockList", method = RequestMethod.POST)
    ResponseResult selectStockList(@RequestParam("companyType") Long companyType,
                                   @RequestParam("companyId") Long companyId);

    //查看即使库存
    @RequestMapping(value = "/stock/selectStockProductListNoPage", method = RequestMethod.POST)
    ResponseResult selectStockProductListNoPage(@RequestParam("stock") String stock);

    //出库-销售出库
    @RequestMapping(value = "/stock/outstorage", method = RequestMethod.POST)
    ResponseResult outstorage(
            @RequestParam("staffNumber") String staffNumber
            ,@RequestParam("batchNumber") String batchNumber
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

    //出库-其他出库
    @RequestMapping(value = "/stock/outstorage", method = RequestMethod.POST)
    ResponseResult outstorage(
            @RequestParam("staffNumber") String staffNumber
            , @RequestParam("outStorageProductJson") String outStorageProductJson
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

    //入库
    @RequestMapping(value = "/stock/instorage", method = RequestMethod.POST)
    ResponseResult instorage(
            @RequestParam("orgK3Number") String orgK3Number,
            @RequestParam("branch") String branch,
            @RequestParam("stockId") String stockId,
            @RequestParam("inStorageType") String inStorageType
            , @RequestParam("invoicesType") String invoicesType
            , @RequestParam("inventoryGroup") String inventoryGroup
            , @RequestParam("inventoryWay") String inventoryWay
            , @RequestParam("inStorageDate") String inStorageDate
            , @RequestParam("shipperType") String shipperType
            , @RequestParam("shipper") String shipper
            , @RequestParam("inStorageProductJson") String inStorageProductJson
            , @RequestParam("direction") String direction
            , @RequestParam("createOperator") String createOperator)
    ;

    //查看所有的单位列表
    @RequestMapping(value = "/unit/selectUnitListNoPage", method = RequestMethod.POST)
    ResponseResult selectUnitListNoPage();

    //修改实体商品及护理商品的销量
    @RequestMapping(value = "/manage/product/updateProductSales", method = RequestMethod.POST)
    ResponseResult updateProductSales(@RequestParam("productCode") String productCode, @RequestParam("type") Integer type, @RequestParam("upOrDown") String upOrDown);

    //修改实体商品第三方库存
    @RequestMapping(value = "/manage/product/updateStockQuantity", method = RequestMethod.POST)
    ResponseResult updateStockQuantity(@RequestParam("productCode") String productCode, @RequestParam("upOrDown") String upOrDown);


    //根据实体商品code找到商品
    @RequestMapping(value = "/manage/product/selectProductByCode", method = RequestMethod.POST)
    ResponseResult selectProductByCode(@RequestParam("productCode") String productCode);

    //根据护理商品code找到商品
    @RequestMapping(value = "/manage/product/selectServiceProductByCode", method = RequestMethod.POST)
    ResponseResult selectServiceProductByCode(@RequestParam("productCode") String productCode);

    @ApiOperation(value = "出库退货", notes = "出库退货")
    @RequestMapping(value = "/stock/outstorageReturn", method = RequestMethod.POST)
    ResponseResult outstorageReturn(@RequestParam("client") String client,
                                    @RequestParam("outStorageType") String outStorageType,
                                    @RequestParam("invoicesType") String invoicesType,
                                    @RequestParam("inventoryGroup") String inventoryGroup,
                                    @RequestParam("inventoryWay") String inventoryWay,
                                    @RequestParam("outStorageDate") String outStorageDate,
                                    @RequestParam("shipperType") String shipperType,
                                    @RequestParam("shipper") String shipper,
                                    @RequestParam("outStorageId") String outStorageId,
                                    @RequestParam("outStorageProductJson") String outStorageProductJson,
                                    @RequestParam("orgK3Number") String orgK3Number,
                                    @RequestParam("createOperator") String createOperator,
                                    @RequestParam("batchNumber") String batchNumber,
                                    @RequestParam("stockId") String stockId,
                                    @RequestParam("storeId") String storeId);

    @ApiOperation(value = "根据id查看出库单商品", notes = "根据id查看出库单商品")
    @RequestMapping(value = "/stock/selectOutstorageProductById", method = RequestMethod.POST)
    ResponseResult selectOutstorageProductById(@RequestParam("outStorageId") Long outStorageId);

    @ApiOperation(value = "根据id查看出库单", notes = "根据id查看出库单")
    @RequestMapping(value = "/stock/selectOutstorageById", method = RequestMethod.POST)
    ResponseResult selectOutstorageById(@RequestParam("outStorageId") Long outStorageId);

    @ApiOperation(value = "销售退货", notes = "销售退货")
    @RequestMapping(value = "/stock/saveMarketReturn", method = RequestMethod.POST)
    ResponseResult saveMarketReturn(@RequestParam("client") String client,
                                    @RequestParam("storeId") String storeId,
                                    @RequestParam("outStorageType") String outStorageType,
                                    @RequestParam("invoicesType") String invoicesType,
                                    @RequestParam("inventoryGroup") String inventoryGroup,
                                    @RequestParam("inventoryWay") String inventoryWay,
                                    @RequestParam("outStorageDate") String outStorageDate,
                                    @RequestParam("shipperType") String shipperType,
                                    @RequestParam("shipper") String shipper,
                                    @RequestParam("orgK3Number") String orgK3Number,
                                    @RequestParam("createOperator") String createOperator,
                                    @RequestParam("stockId") String stockId,
                                    @RequestParam("outStorageProductJson") String outStorageProductJson,
                                    @RequestParam("batchNumber") String batchNumber);
}

