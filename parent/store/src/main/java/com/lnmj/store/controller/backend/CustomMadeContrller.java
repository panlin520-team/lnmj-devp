package com.lnmj.store.controller.backend;

import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeCustomMadeEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.ICustomMadeOrderService;
import com.lnmj.store.entity.ConsumeCustomMade;
import com.lnmj.store.entity.CustomMadeOrder;
import com.lnmj.store.serviceProxy.ProductApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 〈项目定制控制器〉
 *
 * @Author renqingyun
 * @create 2019/5/22
 */
@Api(description = "项目定制")
@RestController
@RequestMapping("/store")
public class CustomMadeContrller {

    @Resource
    private ICustomMadeOrderService customMadeOrderService;//订单
    @Resource
    private ProductApi productApi;


    @ApiOperation(value = "新增定制订单定制", notes = "新增定制订单定制")
    @PostMapping("/insertCustomMade")
    public ResponseResult insertCustomMade(String orderLink,String mobile,String cardNumber,String customMadeOrderDetailList, String access_token) {
        JSONArray jsonArray = JSONArray.parseArray(customMadeOrderDetailList);
                if (StringUtils.isBlank(orderLink)) {
                    return ResponseResult.error(new Error(ResponseCodeCustomMadeEnum.NAMAE_IS_NULL.getCode(), ResponseCodeCustomMadeEnum.NAMAE_IS_NULL.getDesc()));
                }
                if (StringUtils.isBlank(mobile)) {
                    return ResponseResult.error(new Error(ResponseCodeCustomMadeEnum.MOBILE_IS_NULL.getCode(), ResponseCodeCustomMadeEnum.MOBILE_IS_NULL.getDesc()));
                }
        if (StringUtils.isBlank(cardNumber)) {
            return ResponseResult.error(new Error(ResponseCodeCustomMadeEnum.MOBILE_IS_NULL.getCode(), ResponseCodeCustomMadeEnum.MOBILE_IS_NULL.getDesc()));
        }
            return customMadeOrderService.insertCustomMadeOrder(orderLink,mobile,cardNumber,jsonArray);
    }


    @ApiOperation(value = "定制订单分页查询", notes = "定制订单分页查询")
    @PostMapping("/selectCustomMadeList")
    public ResponseResult selectCustomMadeList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, CustomMadeOrder customMadeOrder, String keyWord, String access_token) {
        return customMadeOrderService.selectCustomMadeList(customMadeOrder, pageNum, pageSize, keyWord);
    }


    @ApiOperation(value = "根据订单号查询定制订单详情和订单", notes = "根据订单号查询定制订单详情和订单")
    @PostMapping("/selectOrderDetailByCondition")
    public ResponseResult selectOrderDetailByCondition(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,Long orderNumber, String access_token) {
        return customMadeOrderService.selectOrderDetailByCondition(pageNum,pageSize,orderNumber);
    }


    @ApiOperation(value = "根据服务id查询服务护理时长", notes = "根据服务id查询服务护理时长")
    @PostMapping("/selectDurationById")
    public ResponseResult selectDurationById(String serviceProductIds, String access_token) {
        return productApi.selectServiceListById(serviceProductIds);
    }


    @ApiOperation(value = "使用定制定制", notes = "使用定制定制")
    @PostMapping("/useCustomMade")
    public ResponseResult UseCustomMade(ConsumeCustomMade consumeCustomMade,String beauticianStrList, String access_token) {
//        if (consumeCustomMade.getCustomMadeOrderDetailId() == null) {
//            return ResponseResult.error(new Error(ResponseCodeCustomMadeEnum.CUSTOMMADEID_IS_NULL.getCode(), ResponseCodeCustomMadeEnum.CUSTOMMADEID_IS_NULL.getDesc()));
//        }
//        if (consumeCustomMade.getNurseTime() == null) {
//            return ResponseResult.error(new Error(ResponseCodeCustomMadeEnum.NURSETIME_IS_NULL.getCode(), ResponseCodeCustomMadeEnum.NURSETIME_IS_NULL.getDesc()));
//        }
//        if (consumeCustomMade.getStoreId() == null) {
//            return ResponseResult.error(new Error(ResponseCodeCustomMadeEnum.NURSESTORE_IS_NULL.getCode(), ResponseCodeCustomMadeEnum.NURSESTORE_IS_NULL.getDesc()));
//        }
//        if (StringUtils.isBlank(consumeCustomMade.getNurseStore())) {
//            return ResponseResult.error(new Error(ResponseCodeCustomMadeEnum.NURSESTORE_IS_NULL.getCode(), ResponseCodeCustomMadeEnum.NURSESTORE_IS_NULL.getDesc()));
//        }
//        if (StringUtils.isBlank(consumeCustomMade.getBeauticianId())) {
//            return ResponseResult.error(new Error(ResponseCodeCustomMadeEnum.BEAUTICIAN_IS_NULL.getCode(), ResponseCodeCustomMadeEnum.BEAUTICIAN_IS_NULL.getDesc()));
//        }
//        if (StringUtils.isBlank(consumeCustomMade.getBeautician())) {
//            return ResponseResult.error(new Error(ResponseCodeCustomMadeEnum.BEAUTICIANID_IS_NULL.getCode(), ResponseCodeCustomMadeEnum.BEAUTICIANID_IS_NULL.getDesc()));
//        }
        return customMadeOrderService.UseCustomMade(consumeCustomMade,beauticianStrList);
    }


    @ApiOperation(value = "根据详情id查询订单使用列表", notes = "根据详情id查询订单使用列表")
    @PostMapping("/selectUserListById")
    public ResponseResult selectUserListById(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, Long customMadeOrderDetailId, String access_token) {
        return customMadeOrderService.selectUserListById(pageNum, pageSize, customMadeOrderDetailId);
    }




    @ApiOperation(value = "根据美容id和护理时间查询定制订单信息", notes = "根据美容id和护理时间查询定制订单信息")
    @RequestMapping(value = "/selectCustomByIdsAndTime", method = RequestMethod.POST)
    public ResponseResult selectCustomByIdsAndTime(String beauticianId, String nurseTime, Long storeId) {
        return customMadeOrderService.selectCustomByIdsAndTime(beauticianId, nurseTime, storeId);
    }


    public static ResponseResult CheckParams(@Validated Object object, BindingResult bindingResult) {
        int count = bindingResult.getErrorCount();
        HashMap<Object, Object> map = new HashMap<>();
        if (count > 0) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (int i = 0; i < count; i++) {
                FieldError error = fieldErrors.get(i);
                map.put(error.getField(), error.getDefaultMessage());
            }
        }
        return ResponseResult.error(new Error(1, map));
    }
}
