package com.lnmj.system.controller.backend;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeSystemEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.business.IBeauticianGradeService;
import com.lnmj.system.entity.BeauticianGrade;
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
 * @Date: 2019/6/11 10:48
 * @Description:  美容师等级controller
 */
@Api(description = "美容师等级")
@RestController
@RequestMapping("/manage/beauticianGrade")
public class BeauticianGradeManageController {
    @Resource
    private IBeauticianGradeService beauticianGradeService;


    /**
    *@Description 美容师等级分页显示
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/11
    *@Time 10:50
    */
    @ApiOperation(value = "美容师等级分页显示",notes = "美容师等级分页显示")
    @RequestMapping(value = "/selectBeauticianGradeList",method = RequestMethod.POST)
    public ResponseResult selectBeauticianGradeList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, String access_token) {
        return beauticianGradeService.selectBeauticianGradeList(pageNum,pageSize);
    }
    /**
    *@Description
    *@Param [beauticianGrade, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/11
    *@Time 10:51
    */
    @ApiOperation(value = "查询美容师等级",notes = "查询美容师等级")
    @RequestMapping(value = "/selectBeauticianGradeByWhere",method = RequestMethod.POST)
    public ResponseResult selectBeauticianGradeByWhere(BeauticianGrade beauticianGrade, String access_token) {
        return beauticianGradeService.selectBeauticianGradeByWhere(beauticianGrade);
    }
    /**
    *@Description 新增美容师等级
    *@Param [beauticianGrade, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/11
    *@Time 10:52
    */
    @ApiOperation(value = "新增美容师等级",notes = "新增美容师等级")
    @RequestMapping(value = "/insertBeauticianGrade",method = RequestMethod.POST)
    public ResponseResult insertBeauticianGrade(BeauticianGrade beauticianGrade, String access_token) {
        if (beauticianGrade.getBeauticianGrade()==null) {
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.BEAUTICIAN_GRADE_NULL.getCode(),
                    ResponseCodeSystemEnum.BEAUTICIAN_GRADE_NULL.getDesc()));
        }
        if (StringUtils.isBlank(beauticianGrade.getMemberShipLevel())) {
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.MEMBERSHIPLEVEL_NULL.getCode(),
                    ResponseCodeSystemEnum.MEMBERSHIPLEVEL_NULL.getDesc()));
        }
        if(StringUtils.isBlank(beauticianGrade.getCreateOperator())){
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.NOT_CREATE_OPPERATOR.getCode(),
                    ResponseCodeSystemEnum.NOT_CREATE_OPPERATOR.getDesc()));
        }
        return beauticianGradeService.insertBeauticianGrade(beauticianGrade);
    }
    /**
    *@Description 修改美容师等级
    *@Param [beauticianGrade, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/11
    *@Time 11:00
    */
    @ApiOperation(value = "修改美容师等级",notes = "修改美容师等级")
    @RequestMapping(value = "/updateBeauticianGrade",method = RequestMethod.POST)
    public ResponseResult updateBeauticianGrade(BeauticianGrade beauticianGrade, String access_token) {
        if (beauticianGrade.getBeauticianGradeId() == null) {
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.BEAUTICIAN_GRADE_ID_NULL.getCode(),
                    ResponseCodeSystemEnum.BEAUTICIAN_GRADE_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(beauticianGrade.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeSystemEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return beauticianGradeService.updateBeauticianGrade(beauticianGrade);
    }
    /**
    *@Description 删除美容师等级
    *@Param [beauticianGradeId, modifyOperator, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/11
    *@Time 11:04
    */
    @ApiOperation(value = "删除美容师等级",notes = "删除美容师等级")
    @RequestMapping(value = "/deleteBeauticianGrade",method = RequestMethod.POST)
    public ResponseResult deleteBeauticianGrade(Long beauticianGradeId,String modifyOperator, String access_token) {
        if (beauticianGradeId == null) {
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.BEAUTICIAN_GRADE_ID_NULL.getCode(),
                    ResponseCodeSystemEnum.BEAUTICIAN_GRADE_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeSystemEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return beauticianGradeService.deleteBeauticianGrade(beauticianGradeId,modifyOperator);
    }

}
