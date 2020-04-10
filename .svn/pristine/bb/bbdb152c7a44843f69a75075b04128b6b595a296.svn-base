package com.lnmj.store.controller.backend;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAppointmentEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.IAppointmentService;
import com.lnmj.store.entity.Option;
import com.lnmj.store.entity.OptionValue;
import com.lnmj.store.entity.VO.AppointmentVO;
import com.lnmj.store.serviceProxy.ProductApi;
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
 * @Date: 2019/5/23 11:25
 * @Description: 预约后台controller
 */
@Api(description = "预约后台")
@RestController
@RequestMapping("/manage/appointment")
public class AppointmentManageController {
    @Resource
    private IAppointmentService appointmentService;
    @Resource
    private ProductApi productApi;


    //待完成：推送，销量

    //后台管理页面：预约管理
    //预约商品（商品名称/商品规格/商品价格/产品销量）分页显示（服务商品已做）
    //预约商品（商品名称,护理时长,产品销量）修改（服务商品已做）
    //实际销量

    //门店页面：预约状态
    //美容师列表（全部美容师）
    //预约状态（根据护理时间的日期查询，休息，空闲，忙碌，过期，选中，重叠）
    //美容师的预约顾客显示（顾客，项目，业绩，销售，来源，美容师，门店,时间（9:00-10:30）） 根据护理时间的日期查询
    //美容师的预约顾客修改（美容师，时间,门店）
    //全部预约顾客显示（顾客，项目，业绩，销售，来源，美容师，门店,时间（9:00-10:30）） 根据护理时间的日期查询
    //全部预约顾客推送(给顾客发消息,推送类型:预约提醒/支付提醒)
    //全部预约顾客取消
    //到店人数，项目个数，计划金额，提成金额，销售金额

    //门店页面：用户
    //用户详情（用户接口）
    //消费详情
    //预约项目（选择时间段，查询是否有购买项目，购买项目则直接预约，美容师，时间，门店）
    
    /**
    *@Description 配置值是否存在
    *@Param [userId, optionId, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/7/31
    *@Time 18:54
    */
    @ApiOperation(value = "配置值是否存在",notes = "配置值是否存在")
    @RequestMapping(value = "/checkOptionValue",method = RequestMethod.POST)
    public ResponseResult checkOptionValue(Long userId, Long optionId, String access_token){
        return appointmentService.checkOptionValue(userId,optionId);
    }

    /**
    *@Description 查询配置值
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/7/31
    *@Time 17:15
    */
    @ApiOperation(value = "查询配置值",notes = "查询配置值")
    @RequestMapping(value = "/selectOptionValueList",method = RequestMethod.POST)
    public ResponseResult selectOptionValueList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, String access_token){
        return appointmentService.selectOptionValueList(pageNum,pageSize);
    }

    /**
     *@Description 新增配置
     *@Param [option, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/7/24
     *@Time 11:02
     */
    @ApiOperation(value = "新增配置",notes = "新增配置")
    @RequestMapping(value = "/insertOptionValue",method = RequestMethod.POST)
    public ResponseResult insertOptionValue(OptionValue optionValue, String access_token){
        /*if(optionValue.getUserId()==null){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.USERID_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.USERID_NOT_NULL.getDesc()));
        }
        if(optionValue.getOptionId()==null){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.OPTION_ID_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.OPTION_ID_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(optionValue.getCardNumber())){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.CARDNUMBER_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.CARDNUMBER_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(optionValue.getUserValue())){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.USERVALUE_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.USERVALUE_NOT_NULL.getDesc()));
        }*/
        return appointmentService.insertOptionValue(optionValue);
    }

    /**
    *@Description 修改配置值
    *@Param [optionValue, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/7/31
    *@Time 17:13
    */
    @ApiOperation(value = "修改配置值",notes = "修改配置值")
    @RequestMapping(value = "/updateOptionValue",method = RequestMethod.POST)
    public ResponseResult updateOptionValue(OptionValue optionValue, String access_token){
        if(optionValue.getUserOptionId() == null){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.USEROPTIONID_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.USEROPTIONID_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(optionValue.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return appointmentService.updateOptionValue(optionValue);
    }

    /**
    *@Description 删除配置值
    *@Param [userOptionId, modifyOperator, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/7/31
    *@Time 17:12
    */
    @ApiOperation(value = "删除配置值",notes = "删除配置值")
    @RequestMapping(value = "/deleteOptionValue",method = RequestMethod.POST)
    public ResponseResult deleteOptionValue(Long userOptionId, String modifyOperator, String access_token){
        if(userOptionId == null){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.USEROPTIONID_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.USEROPTIONID_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return appointmentService.deleteOptionValue(userOptionId,modifyOperator);
    }

    /**
    *@Description 查询配置
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/7/24
    *@Time 11:00
    */
    @ApiOperation(value = "查询配置",notes = "查询配置")
    @RequestMapping(value = "/selectOptionList",method = RequestMethod.POST)
    public ResponseResult selectOptionList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                           @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, String access_token){
        return appointmentService.selectOptionList(pageNum,pageSize);
    }
    
    /**
    *@Description 新增配置
    *@Param [option, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/7/24
    *@Time 11:02
    */
    @ApiOperation(value = "新增配置",notes = "新增配置")
    @RequestMapping(value = "/insertOption",method = RequestMethod.POST)
    public ResponseResult insertOption(Option option, String access_token){
        /*if(StringUtils.isBlank(option.getOptionTitle())){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.OPTIONTITLE_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.OPTIONTITLE_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(option.getOptionSort())){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.OPTIONSORT_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.OPTIONSORT_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(option.getOptionValue())){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.OPTIONVALUE_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.OPTIONVALUE_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(option.getCreateOperator())){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.CREATEOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.CREATEOPERATOR_NOT_NULL.getDesc()));
        }*/
        return appointmentService.insertOption(option);
    }

    /**
     *@Description 修改配置
     *@Param [option, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/7/24
     *@Time 11:02
     */
    @ApiOperation(value = "修改配置",notes = "修改配置")
    @RequestMapping(value = "/updateOption",method = RequestMethod.POST)
    public ResponseResult updateOption(Option option, String access_token){
        if(option.getOptionId() == null){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.OPTIONID_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.OPTIONID_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(option.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return appointmentService.updateOption(option);
    }

    /**
    *@Description  删除配置
    *@Param [optionId, modifyOperator, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/7/24
    *@Time 11:14
    */
    @ApiOperation(value = "删除配置",notes = "删除配置")
    @RequestMapping(value = "/deleteOption",method = RequestMethod.POST)
    public ResponseResult deleteOption(Long optionId, String modifyOperator, String access_token){
        if(optionId == null){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.OPTIONID_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.OPTIONID_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return appointmentService.deleteOption(optionId,modifyOperator);
    }


    /**
    *@Description 修改服务商品
    *@Param [serviceProductId, productName, duration, productSales, modifyOperator]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/24
    *@Time 17:53
    */
    @ApiOperation(value = "修改服务商品",notes = "修改服务商品")
    @RequestMapping(value = "/updateServiceProduct",method = RequestMethod.POST)
    public ResponseResult updateServiceProduct(@RequestParam("serviceProductId")Long serviceProductId,
                                               @RequestParam(value = "productName",required = false)String productName,
                                               @RequestParam(value = "duration",required = false) String duration,
                                               @RequestParam(value = "productSales",required = false)Integer productSales,
                                               @RequestParam("modifyOperator")String modifyOperator, String access_token){
        return productApi.updateServiceProduct(serviceProductId,productName,duration,productSales,modifyOperator);
    }

    /**
     *@Description 按日期查询预约
     *@Param [nursingTime,access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/5/23
     *@Time 16:31
     */
    @ApiOperation(value = "按日期查询预约",notes = "按日期查询预约")
    @RequestMapping(value = "/selectAppointmentByDate",method = RequestMethod.POST)
    public ResponseResult selectAppointmentByDate(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                  @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                                  String nursingDate, String access_token){
        if(StringUtils.isBlank(nursingDate)){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.DATE_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.DATE_NOT_NULL.getDesc()));
        }
        return appointmentService.selectAppointmentByDate(nursingDate,pageNum,pageSize);
    }

    /**
    *@Description 全部预约顾客
    *@Param [access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/23
    *@Time 16:31
    */
    @ApiOperation(value = "全部预约顾客",notes = "全部预约顾客")
    @RequestMapping(value = "/selectAppointmentList",method = RequestMethod.POST)
    public ResponseResult selectAppointmentList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, String access_token){
        return appointmentService.selectAppointmentList(pageNum,pageSize);
    }

    /**
     *@Description 按日期、员工、状态查询当前时间之后的预约时间
     *@Param [nursingTime,access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/5/23
     *@Time 16:31
     */
    @ApiOperation(value = "按日期、员工、状态查询当前时间之后的预约时间",notes = "按日期、员工、状态查询当前时间之后的预约时间")
    @RequestMapping(value = "/selectAppointmentTimeByDateAndEmployeeAndStatus",method = RequestMethod.POST)
    public ResponseResult selectAppointmentTimeByDateAndEmployeeAndStatus(String employeeCode,String nursingDate,Integer appointmentStatus, String access_token){
        if(StringUtils.isBlank(nursingDate)){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.DATE_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.DATE_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(employeeCode)){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.BEAUTICIAN_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.BEAUTICIAN_NOT_NULL.getDesc()));
        }
        if(appointmentStatus == null){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.APPOINTMENTSTATUS_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.APPOINTMENTSTATUS_NOT_NULL.getDesc()));
        }
        return appointmentService.selectAppointmentTimeByDateAndEmployeeAndStatus(employeeCode,nursingDate,appointmentStatus);
    }

    /**
     *@Description 按美容师查询预约
     *@Param [appointment,access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/5/23
     *@Time 16:31
     */
    @ApiOperation(value = "按美容师查询预约",notes = "按美容师查询预约")
    @RequestMapping(value = "/selectAppointmentByBeautician",method = RequestMethod.POST)
    public ResponseResult selectAppointmentByBeautician(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                        @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                                        String employeeCode, String access_token){
        if(StringUtils.isBlank(employeeCode)){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.BEAUTICIAN_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.BEAUTICIAN_NOT_NULL.getDesc()));
        }
        return appointmentService.selectAppointmentByBeautician(employeeCode,pageNum,pageSize);
    }
    /**
    *@Description 按美容师和日期查询预约
    *@Param [employeeCode, nursingTime, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/6
    *@Time 15:48
    */
    @ApiOperation(value = "按美容师和日期查询预约",notes = "按美容师和日期查询预约")
    @RequestMapping(value = "/selectAppointmentByBeauticianAndDate",method = RequestMethod.POST)
    public ResponseResult selectAppointmentByBeauticianAndDate(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                               @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                                               String employeeCode, String nursingDate, String access_token){
        if(StringUtils.isBlank(employeeCode)){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.BEAUTICIAN_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.BEAUTICIAN_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(nursingDate)){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.DATE_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.DATE_NOT_NULL.getDesc()));
        }
        return appointmentService.selectAppointmentByBeauticianAndDate(employeeCode,nursingDate,pageNum,pageSize);
    }

    /**
     *@Description 预约条件查询
     *@Param [appointment,access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/5/23
     *@Time 16:31
     */
    @ApiOperation(value = "预约条件查询",notes = "预约条件查询")
    @RequestMapping(value = "/selectAppointmentByWhere",method = RequestMethod.POST)
    public ResponseResult selectAppointmentByWhere(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                                   AppointmentVO appointmentVO, String access_token){
        return appointmentService.selectAppointmentByWhere(appointmentVO,pageNum,pageSize);
    }

    /**
     *@Description 新增预约
     *@Param [appointment,access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/5/23
     *@Time 16:31
     */
    @ApiOperation(value = "新增预约",notes = "新增预约")
    @RequestMapping(value = "/insertAppointment",method = RequestMethod.POST)
    public ResponseResult insertAppointment(AppointmentVO appointmentVO, String access_token){
        if(appointmentVO.getConsumerId()==null){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.NAME_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.NAME_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(appointmentVO.getAppointmentType())){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.APPOINTMENTTYPE_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.APPOINTMENTTYPE_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(appointmentVO.getProduct())){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.PRODUCTNAME_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.PRODUCTNAME_NOT_NULL.getDesc()));
        }
/*        if(appointmentVO.getPerformance()==null){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.PERFORMANCE_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.PERFORMANCE_NOT_NULL.getDesc()));
        }
        if(appointmentVO.getSelling()==null){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.SELLING_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.SELLING_NOT_NULL.getDesc()));
        }*/
        if(StringUtils.isBlank(appointmentVO.getSource())){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.SOURCE_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.SOURCE_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(appointmentVO.getDuration())){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.DURATION_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.DURATION_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(appointmentVO.getStoreName())){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.STORENAME_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.STORENAME_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(appointmentVO.getEmployeeCode())){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.BEAUTICIAN_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.BEAUTICIAN_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(appointmentVO.getNursingDate())){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.NURSINGTIME_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.NURSINGTIME_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(appointmentVO.getDuration())){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.DURATION_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.DURATION_NOT_NULL.getDesc()));
        }
        if(appointmentVO.getStoreNumber()==null){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.STORENUMBER_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.STORENUMBER_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(appointmentVO.getCreateOperator())){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.CREATEOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.CREATEOPERATOR_NOT_NULL.getDesc()));
        }
        if(appointmentVO.getStoreId()==null){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.STORENAME_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.STORENAME_NOT_NULL.getDesc()));
        }
        return appointmentService.insertAppointment(appointmentVO);
    }
    /**
     *@Description 修改预约(目前只修改门店，护理人员，护理时间)
     *@Param [appointment,access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/5/23
     *@Time 16:31
     */
    @ApiOperation(value = "修改预约",notes = "修改预约")
    @RequestMapping(value = "/updateAppointment",method = RequestMethod.POST)
    public ResponseResult updateAppointment(AppointmentVO appointmentVO){
        if(appointmentVO.getAppointmentId()==null){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.APPOINTMENTID_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.APPOINTMENTID_NOT_NULL.getDesc()));
        }
//        if(StringUtils.isBlank(appointmentVO.getStoreName())){
//            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.STORENAME_NOT_NULL.getCode(),
//                    ResponseCodeAppointmentEnum.STORENAME_NOT_NULL.getDesc()));
//        }
//        if(StringUtils.isBlank(appointmentVO.getEmployeeCode())){
//            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.BEAUTICIAN_NOT_NULL.getCode(),
//                    ResponseCodeAppointmentEnum.BEAUTICIAN_NOT_NULL.getDesc()));
//        }
//        if(StringUtils.isBlank(appointmentVO.getNursingDate())){
//            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.NURSINGTIME_NOT_NULL.getCode(),
//                    ResponseCodeAppointmentEnum.NURSINGTIME_NOT_NULL.getDesc()));
//        }
//        if(StringUtils.isBlank(appointmentVO.getModifyOperator())){
//            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
//                    ResponseCodeAppointmentEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
//        }
        return appointmentService.updateAppointment(appointmentVO);
    }
    /**
    *@Description 推送预约（需要发送消息）
    *@Param [appointmentStatus, modifyOperator, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/23
    *@Time 17:48
    */
    @ApiOperation(value = "推送预约",notes = "推送预约")
    @RequestMapping(value = "/pushAppointment",method = RequestMethod.POST)
    public ResponseResult pushAppointment(Long appointmentId, Integer appointmentStatus, String modifyOperator, String access_token){
        if(appointmentId==null){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.APPOINTMENTID_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.APPOINTMENTID_NOT_NULL.getDesc()));
        }
        if(appointmentStatus==null){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.APPOINTMENTSTATUS_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.APPOINTMENTSTATUS_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return appointmentService.pushAppointment(appointmentId,appointmentStatus,modifyOperator);
    }
    /**
    *@Description 取消预约（只做了修改状态）
    *@Param [appointmentId, modifyOperator, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/23
    *@Time 17:48
    */
    @ApiOperation(value = "取消预约",notes = "取消预约")
    @RequestMapping(value = "/cancelAppointment",method = RequestMethod.POST)
    public ResponseResult cancelAppointment(Long appointmentId, String modifyOperator, String access_token){
        if(appointmentId==null){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.APPOINTMENTID_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.APPOINTMENTID_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return appointmentService.cancelAppointment(appointmentId,modifyOperator);
    }

    /**
     *@Description 删除预约
     *@Param [appointment,access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/5/23
     *@Time 16:31
     */
    @ApiOperation(value = "删除预约",notes = "删除预约")
    @RequestMapping(value = "/deleteAppointment",method = RequestMethod.POST)
    public ResponseResult deleteAppointment(Long appointmentId, String modifyOperator, String access_token){
        if(appointmentId==null){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.APPOINTMENTID_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.APPOINTMENTID_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeAppointmentEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return appointmentService.deleteAppointment(appointmentId,modifyOperator);
    }




}
