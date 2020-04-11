package com.lnmj.store.serviceProxy.controller;

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
@Api(description = "赠送后台api")
@RestController
@RequestMapping("/api/manage/presentation")
public class PresentationManageService {
    @Resource
    private IPresentationService presentationService;

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

}
