package com.lnmj.system.controller.backend;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeMakerEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.business.IMakerOrderService;
import com.lnmj.system.entity.MakerOrder;
import com.lnmj.system.entity.MakerOrderUse;
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
 * @Date: 2019/8/24 15:24
 * @Description:  创客订单
 */
@Api(description = "创客订单")
@RestController
@RequestMapping("/manage/maker/order")
public class MakerOrderController {
    @Resource
    private IMakerOrderService makerOrderService;

    /**
    *@Description 新增订单使用
    *@Param [makerOrderUse, access_token]
    *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
    *@Date 2019/8/28
    *@Time 17:11
    */
    @ApiOperation(value = "新增订单使用",notes = "新增订单使用")
    @RequestMapping(value = "/insertMakerOrderUse",method = RequestMethod.POST)
    public ResponseResult insertMakerOrderUse(MakerOrderUse makerOrderUse, String access_token) {
        return makerOrderService.insertMakerOrderUse(makerOrderUse);
    }

    /**
    *@Description 条件查询订单商品的使用
    *@Param [pageNum, pageSize, makerOrderUse, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/28
    *@Time 15:50
    */
    @ApiOperation(value = "条件查询订单商品的使用",notes = "条件查询订单商品的使用")
    @RequestMapping(value = "/selectMakerOrderUseByCondition",method = RequestMethod.POST)
    public ResponseResult selectMakerOrderUseByCondition(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                         @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                                         MakerOrderUse makerOrderUse, String access_token) {
        return makerOrderService.selectMakerOrderUseByCondition(pageNum,pageSize,makerOrderUse);
    }

    /**
    *@Description 查询订单状态
    *@Param [name]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/26
    *@Time 16:41
    */
    @ApiOperation(value = "查询订单状态",notes = "查询订单状态")
    @RequestMapping(value = "/selectOrderStatusEnum",method = RequestMethod.POST)
    public ResponseResult selectOrderStatusEnum(String name, String access_token) {
        if (StringUtils.isBlank(name) || name == "") {
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.ENUM_NAME_NULL.getCode(), ResponseCodeMakerEnum.ENUM_NAME_NULL.getDesc()));
        }
        if ("OrderStatusEnum".equals(name)||"ProductStatusEnum".equals(name) || "DeliveryMethodEnum".equals(name) ||
                "MakerProductUseStatusEnum".equals(name) || "OrderTypeEnum".equals(name) || "PayTypeEnum".equals(name)) {
            return makerOrderService.selectOrderStatusEnum(name);
        } else {      //ENUM字段名字错误
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.ENUM_NAME_ERROR.getCode(), ResponseCodeMakerEnum.ENUM_NAME_ERROR.getDesc()));
        }
    }


    /**
    *@Description 条件查询创客订单
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/26
    *@Time 9:48
    */
    @ApiOperation(value = "条件查询创客订单",notes = "条件查询创客订单")
    @RequestMapping(value = "/selectMakerOrderByCondition",method = RequestMethod.POST)
    public ResponseResult selectMakerOrderByCondition(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                      @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                                      MakerOrder makerOrder, String access_token) {
        return makerOrderService.selectMakerOrderByCondition(pageNum,pageSize,makerOrder);
    }

    /**
    *@Description 修改创客订单
    *@Param [makerOrder, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/26
    *@Time 9:55
    */
    @ApiOperation(value = "修改创客订单",notes = "修改创客订单")
    @RequestMapping(value = "/updateMakerOrder",method = RequestMethod.POST)
    public ResponseResult updateMakerOrder(MakerOrder makerOrder, String access_token) {
        if(makerOrder.getMakerOrderId()==null){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_ORDER_ID_NULL.getCode(),
                    ResponseCodeMakerEnum.MAKER_ORDER_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(makerOrder.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return makerOrderService.updateMakerOrder(makerOrder);
    }

    /**
    *@Description 删除创客订单
    *@Param [makerOrderId, modifyOperator, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/26
    *@Time 9:57
    */
    /*@ApiOperation(value = "删除创客订单",notes = "删除创客订单")
    @RequestMapping(value = "/deleteMakerOrder",method = RequestMethod.POST)
    public ResponseResult deleteMakerOrder(Long makerOrderId,String modifyOperator, String access_token) {
        if(makerOrderId==null){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_ORDER_ID_NULL.getCode(),
                    ResponseCodeMakerEnum.MAKER_ORDER_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return makerOrderService.deleteMakerOrder(makerOrderId,modifyOperator);
    }*/

    /**
     *@Description 查询创客订单
     *@Param [pageNum, pageSize, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/8/26
     *@Time 9:48
     */
    /*@ApiOperation(value = "查询创客订单",notes = "查询创客订单")
    @RequestMapping(value = "/selectMakerOrderList",method = RequestMethod.POST)
    public ResponseResult selectMakerOrderList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, String access_token) {
        return makerOrderService.selectMakerOrderList(pageNum,pageSize);
    }*/

    /**
    *@Description 新增创客订单
    *@Param [makerOrder, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/26
    *@Time 9:55
    */
    /*@ApiOperation(value = "新增创客订单",notes = "条件查询创客订单")
    @RequestMapping(value = "/insertMakerOrder",method = RequestMethod.POST)
    public ResponseResult insertMakerOrder(MakerOrder makerOrder, String access_token) {
        return makerOrderService.insertMakerOrder(makerOrder);
    }*/

}
