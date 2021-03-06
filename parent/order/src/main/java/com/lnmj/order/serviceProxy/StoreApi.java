package com.lnmj.order.serviceProxy;


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
@FeignClient(name = "lnmj-store")
public interface StoreApi {
    @RequestMapping(value = "/manage/appointment/updateAppointment", method = RequestMethod.POST)
    ResponseResult updateAppointment(@RequestParam("appointmentId") Long appointmentId,@RequestParam("orderNumber") Long orderNumber,@RequestParam("appointmentStatus") Integer appointmentStatus);
    @RequestMapping(value = "/manage/store/selectStoreById", method = RequestMethod.POST)
    ResponseResult selectStoreById(@RequestParam("storeId") Long storeId);

    @RequestMapping(value = "/manage/experienceCard/checkOrderMemo",method = RequestMethod.POST)
    ResponseResult checkOrderMemo(@RequestParam("memoNum") String memoNum);
    @RequestMapping(value = "/manage/experienceCard/selectExpOrderListByCondition",method = RequestMethod.POST)
    ResponseResult selectExpOrderListByCondition(@RequestParam("list") String[] list,
                                                 @RequestParam("keyWordOrderNum") String keyWordOrderNum,
                                                 @RequestParam("keyWordMobile") String keyWordMobile,
                                                 @RequestParam("orderType") Integer orderType,
                                                 @RequestParam("payStatus") Integer payStatus,
                                                 @RequestParam("date") String date,
                                                 @RequestParam("orderStatus") Integer orderStatus);
    @RequestMapping(value = "/manage/experienceCard/updateUseRecordStatus",method = RequestMethod.POST)
    ResponseResult updateUseRecordStatus(@RequestParam("recordStatus") Integer recordStatus,@RequestParam("recordId") Long recordId);
    @RequestMapping(value = "/manage/experienceCard/addUserExpUseTimes",method = RequestMethod.POST)
    ResponseResult addUserExpUseTimes(@RequestParam("id") Long id);

    @RequestMapping(value = "/manage/experienceCard/updateExpOrderStatus",method = RequestMethod.POST)
    ResponseResult updateExpOrderStatus(@RequestParam("cardOrderCode") String cardOrderCode,@RequestParam("orderStatus") Integer orderStatus);

    @RequestMapping(value = "/manage/experienceCard/checkExpIsUse",method = RequestMethod.POST)
    ResponseResult checkExpIsUse(@RequestParam("experiencecardNum") String experiencecardNum,@RequestParam("memberCode") String memberCode);


    @RequestMapping(value = "/manage/experienceCard/updateExpCarStockNumUp",method = RequestMethod.POST)
    ResponseResult updateExpCarStockNumUp(@RequestParam("experiencecardNum") String experiencecardNum);


    @ApiOperation(value = "查看组织默认客户",notes = "查看组织默认客户")
    @RequestMapping(value = "/manage/customer/selectDefaultCust",method = RequestMethod.POST)
     ResponseResult selectDefaultCust(@RequestParam("companyId") Long companyId,@RequestParam("companyType") Integer companyType);

    @ApiOperation(value = "查看组织默认部门",notes = "查看组织默认部门")
    @RequestMapping(value = "/manage/department/selectDefaultDept",method = RequestMethod.POST)
     ResponseResult selectDefaultDept(@RequestParam("companyId") Long companyId,@RequestParam("companyType") Integer companyType);


    //根据ID查询总公司（获取用户名和密码）
    @RequestMapping(value = "/manage/company/selectCompanyByID", method = RequestMethod.POST)
    ResponseResult selectCompanyByID(@RequestParam("companyId") Long companyId);

}

