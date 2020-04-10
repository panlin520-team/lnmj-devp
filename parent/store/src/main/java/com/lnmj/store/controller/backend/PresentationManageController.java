package com.lnmj.store.controller.backend;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodePresentationEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.IPresentationService;
import com.lnmj.store.entity.Presentation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yilihua
 * @Date: 2019/5/22 11:29
 * @Description: 赠送后台controller
 */
@Api(description = "赠送后台")
@RestController
@RequestMapping("/manage/presentation")
public class PresentationManageController {
    @Resource
    private IPresentationService presentationService;

    /**
     * @Description 显示所有enum内容
     * @Param [name]
     * @Return com.lnmj.account.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/4/25
     * @Time 12:30
     */
    @PreAuthorize("hasAuthority('sys:menu:show')")
    @ApiOperation(value = "显示所有enum内容", notes = "显示所有enum内容")
    @RequestMapping(value = "/selectPresentationEnumName", method = RequestMethod.POST)
    public ResponseResult selectPresentationEnumName(String name, String access_token) {
        //ENUM字段名字不能为空
        if (StringUtils.isBlank(name) || name == "") {
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.ENUM_NAME_NULL.getCode(), ResponseCodePresentationEnum.ENUM_NAME_NULL.getDesc()));
        }
        if ("OrderTypeEnum".equals(name) || "PresentationStatusEnum".equals(name)) {
            return this.presentationService.selectPresentationEnumName(name);
        } else {      //ENUM字段名字错误
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.ENUM_NAME_ERROR.getCode(), ResponseCodePresentationEnum.ENUM_NAME_ERROR.getDesc()));
        }
    }

    /**
    *@Description 赠送分页显示
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/22
    *@Time 11:32
    */
    @ApiOperation(value = "赠送分页显示",notes = "赠送分页显示")
    @RequestMapping(value = "/selectPresentationList",method = RequestMethod.POST)
    public ResponseResult selectPresentationList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                 @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, String access_token) {
        return presentationService.selectPresentationList(pageNum,pageSize);
    }
    /**
    *@Description 查询赠送(关键字查询)
    *@Param [pageNum, pageSize, presentation, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/22
    *@Time 11:35
    */
    @ApiOperation(value = "查询赠送",notes = "查询赠送")
    @RequestMapping(value = "/selectPresentationByWhere",method = RequestMethod.POST)
    public ResponseResult selectPresentationByWhere(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                                    Presentation presentation, String access_token) {
        return presentationService.selectPresentationByWhere(pageNum,pageSize,presentation);
    }
    /**
    *@Description 删除赠送
    *@Param [presentationId, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/22
    *@Time 11:36
    */
    @ApiOperation(value = "删除赠送",notes = "删除赠送")
    @RequestMapping(value = "/deletePresentation",method = RequestMethod.POST)
    public ResponseResult deletePresentation(Long presentationId, String modifyOperator, String access_token) {
        if(presentationId==null){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.PRESENTATIONID_NULL.getCode(),
                    ResponseCodePresentationEnum.PRESENTATIONID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return presentationService.deletePresentation(presentationId,modifyOperator);
    }
    /**
    *@Description 添加赠送
    *@Param [presentation, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/22
    *@Time 11:36
    */
    @ApiOperation(value = "添加赠送",notes = "添加赠送")
    @RequestMapping(value = "/insertPresentation",method = RequestMethod.POST)
    public ResponseResult insertPresentation(Presentation presentation, String access_token) {
        //赠送的字段
        if(presentation.getOrderType() == null){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.ORDERTYPE_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.ORDERTYPE_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(presentation.getOrderNumber())){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.ORDERNUMBER_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.ORDERNUMBER_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(presentation.getOrderLink())){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.ORDERLINK_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.ORDERLINK_NOT_NULL.getDesc()));
        }
        if(presentation.getProductId() == null){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.PRODUCTID_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.PRODUCTID_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(presentation.getProductName())){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.PRODUCTNAME_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.PRODUCTNAME_NOT_NULL.getDesc()));
        }
        if(presentation.getPresentationEndTime() == null){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.PRESENTATION_END_TIME_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.PRESENTATION_END_TIME_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(presentation.getContacts())){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.CONTACTS_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.CONTACTS_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(presentation.getPhone())){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.PHONE_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.PHONE_NOT_NULL.getDesc()));
        }
        return presentationService.insertPresentation(presentation);
    }
    /**
    *@Description 预约
    *@Param [presentation, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/22
    *@Time 11:36
    */
    @ApiOperation(value = "预约",notes = "预约")
    @RequestMapping(value = "/appointmentPresentation",method = RequestMethod.POST)
    public ResponseResult appointmentPresentation(Presentation presentation, String access_token) {
        //预约的赠送
        if(presentation.getPresentationId()==null){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.PRESENTATIONID_NULL.getCode(),
                    ResponseCodePresentationEnum.PRESENTATIONID_NULL.getDesc()));
        }
        //预约的美容师
        if(StringUtils.isBlank(presentation.getBeautician())){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.BEAUTICIAN_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.BEAUTICIAN_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(presentation.getBeauticianId())){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.BEAUTICIANID_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.BEAUTICIANID_NOT_NULL.getDesc()));
        }
        //预约门店
        if(presentation.getStoreId()==null){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.STOREID_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.STOREID_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(presentation.getNurseStore())){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.NURSESTORE_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.NURSESTORE_NOT_NULL.getDesc()));
        }
        //预约的护理时间
        if(presentation.getNurseTime()==null){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.NURSETIME_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.NURSETIME_NOT_NULL.getDesc()));
        }
        //需要传入修改人
        if(StringUtils.isBlank(presentation.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return presentationService.appointmentPresentation(presentation);
    }
    //退货，确认，取消只能操作一次
    /**
    *@Description 退货
    *@Param [presentation, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/22
    *@Time 11:36
    */
    @ApiOperation(value = "退货",notes = "退货")
    @RequestMapping(value = "/returnPresentation",method = RequestMethod.POST)
    public ResponseResult returnPresentation(Presentation presentation, String access_token) {
        //退货必须在预约之后
        //预约的赠送
        if(presentation.getPresentationId()==null){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.PRESENTATIONID_NULL.getCode(),
                    ResponseCodePresentationEnum.PRESENTATIONID_NULL.getDesc()));
        }
        //需要传入修改人
        if(StringUtils.isBlank(presentation.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return presentationService.returnPresentation(presentation);
    }
    /**
    *@Description 确认（只修改状态）
    *@Param [presentation, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/22
    *@Time 11:36
    */
    @ApiOperation(value = "确认",notes = "确认")
    @RequestMapping(value = "/confirmPresentation",method = RequestMethod.POST)
    public ResponseResult confirmPresentation(Presentation presentation, String access_token) {
        //确认必须在预约之后
        //确认的赠送
        if(presentation.getPresentationId()==null){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.PRESENTATIONID_NULL.getCode(),
                    ResponseCodePresentationEnum.PRESENTATIONID_NULL.getDesc()));
        }
        //需要传入修改人
        if(StringUtils.isBlank(presentation.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return presentationService.confirmPresentation(presentation);
    }
    /**
    *@Description 取消（只修改状态）
    *@Param [presentation, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/22
    *@Time 11:36
    */
    @ApiOperation(value = "取消",notes = "取消")
    @RequestMapping(value = "/cancelPresentation",method = RequestMethod.POST)
    public ResponseResult cancelPresentation(Presentation presentation, String access_token) {
        //取消必须在预约之后
        //取消的赠送
        if(presentation.getPresentationId()==null){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.PRESENTATIONID_NULL.getCode(),
                    ResponseCodePresentationEnum.PRESENTATIONID_NULL.getDesc()));
        }
        //需要传入修改人
        if(StringUtils.isBlank(presentation.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodePresentationEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return presentationService.cancelPresentation(presentation);
    }

}
