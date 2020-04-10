package com.lnmj.system.controller.backend;


import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeSystemEnum;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.response.Error;
import com.lnmj.system.business.ISystemParameterService;
import com.lnmj.system.entity.Parameter;
import com.lnmj.system.entity.ParameterType;
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
 * @Date: 2019/5/29 15:30
 * @Description:  系统参数配置Controller
 */
@Api(description = "系统参数配置")
@RestController
@RequestMapping("/manage/system")
public class SystemParameterManageController {
    @Resource
    private ISystemParameterService systemParameterService;

    /**
    *@Description 系统参数类型分页显示
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/10
    *@Time 14:31
    */
    @ApiOperation(value = "系统参数类型分页显示",notes = "系统参数类型分页显示")
    @RequestMapping(value = "/selectParameterTypeList",method = RequestMethod.POST)
    public ResponseResult selectParameterTypeList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, String access_token) {
        return systemParameterService.selectParameterTypeList(pageNum,pageSize);
    }

    /**
    *@Description 修改系统参数类型
    *@Param [parameter, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/10
    *@Time 14:42
    */
    @ApiOperation(value = "修改系统参数类型",notes = "修改系统参数类型")
    @RequestMapping(value = "/updateParameterType",method = RequestMethod.POST)
    public ResponseResult updateParameterType(ParameterType parameterType, String access_token) {
        if (parameterType.getParameterTypeId() == null) {
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.PARAMETER_TYPE_ID_NULL.getCode(),
                    ResponseCodeSystemEnum.PARAMETER_TYPE_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(parameterType.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeSystemEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return systemParameterService.updateParameterType(parameterType);
    }

    /**
    *@Description 删除系统参数类型
    *@Param [parameterId, modifyOperator, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/10
    *@Time 14:56
    */
    @ApiOperation(value = "删除系统参数类型",notes = "删除系统参数类型")
    @RequestMapping(value = "/deleteParameterType",method = RequestMethod.POST)
    public ResponseResult deleteParameterType(Long parameterTypeId,String modifyOperator, String access_token) {
        if (parameterTypeId == null) {
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.PARAMETER_TYPE_ID_NULL.getCode(),
                    ResponseCodeSystemEnum.PARAMETER_TYPE_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeSystemEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return systemParameterService.deleteParameterType(parameterTypeId,modifyOperator);
    }

    /**
    *@Description 系统参数分页显示
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/10
    *@Time 14:31
    */
    @ApiOperation(value = "系统参数分页显示",notes = "系统参数分页显示")
    @RequestMapping(value = "/selectParameterList",method = RequestMethod.POST)
    public ResponseResult selectParameterList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,Long parameterTypeId, String access_token) {
        return systemParameterService.selectParameterList(pageNum,pageSize,parameterTypeId);
    }

    /**
    *@Description 修改系统参数
    *@Param [parameter, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/10
    *@Time 14:42
    */
    @ApiOperation(value = "修改系统参数",notes = "修改系统参数")
    @RequestMapping(value = "/updateParameter",method = RequestMethod.POST)
    public ResponseResult updateParameter(String parameterArray, String access_token) {

        JSONArray configArray = JSONArray.parseArray(parameterArray);
/*        if (parameter.getParameterId() == null) {
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.PARAMETER_ID_NULL.getCode(),
                    ResponseCodeSystemEnum.PARAMETER_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(parameter.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeSystemEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }*/
        return systemParameterService.updateParameter(configArray);
    }

    /**
    *@Description 删除系统参数
    *@Param [parameterId, modifyOperator, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/10
    *@Time 14:56
    */
    @ApiOperation(value = "删除系统参数",notes = "删除系统参数")
    @RequestMapping(value = "/deleteParameter",method = RequestMethod.POST)
    public ResponseResult deleteParameter(Long parameterId,String modifyOperator, String access_token) {
        if (parameterId == null) {
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.PARAMETER_ID_NULL.getCode(),
                    ResponseCodeSystemEnum.PARAMETER_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeSystemEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return systemParameterService.deleteParameter(parameterId,modifyOperator);
    }

    /**
     *@Description 查询系统参数（按系统参数类型查询系统参数）
     *@Param [parameter, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/6/10
     *@Time 15:16
     */
    @ApiOperation(value = "查询系统参数",notes = "查询系统参数")
    @RequestMapping(value = "/selectParameterByWhere",method = RequestMethod.POST)
    public ResponseResult selectParameterByWhere(Parameter parameter, String access_token) {
        return systemParameterService.selectParameterByWhere(parameter);
    }

    /**
     *@Description 新增系统参数类型
     *@Param [parameter, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/6/10
     *@Time 14:31
     */
    @ApiOperation(value = "新增系统参数类型",notes = "新增系统参数类型")
    @RequestMapping(value = "/insertParameterType",method = RequestMethod.POST)
    public ResponseResult insertParameterType(ParameterType parameterType, String access_token) {
        if (StringUtils.isBlank(parameterType.getParameterType())) {
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.PARAMETER_TYPE_NAME_NULL.getCode(),
                    ResponseCodeSystemEnum.PARAMETER_TYPE_NAME_NULL.getDesc()));
        }
//        if (parameterType.getParentId()==null) {
//            return ResponseResult.error(new Error(ResponseCodeSystemEnum.PARAMETER_TYPE_PARENTID_NULL.getCode(),
//                    ResponseCodeSystemEnum.PARAMETER_TYPE_PARENTID_NULL.getDesc()));
//        }
        if(StringUtils.isBlank(parameterType.getCreateOperator())){
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.NOT_CREATE_OPPERATOR.getCode(),
                    ResponseCodeSystemEnum.NOT_CREATE_OPPERATOR.getDesc()));
        }
        return systemParameterService.insertParameterType(parameterType);
    }


    /**
     *@Description 新增系统参数
     *@Param [parameter, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/6/10
     *@Time 14:31
     */
    @ApiOperation(value = "新增系统参数",notes = "新增系统参数")
    @RequestMapping(value = "/insertParameter",method = RequestMethod.POST)
    public ResponseResult insertParameter(Parameter parameter, String access_token) {
        if (parameter.getParameterTypeId()==null) {
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.PARAMETER_TYPE_NULL.getCode(),
                    ResponseCodeSystemEnum.PARAMETER_TYPE_NULL.getDesc()));
        }
        if (StringUtils.isBlank(parameter.getParameterName())) {
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.PARAMETER_NAME_NULL.getCode(),
                    ResponseCodeSystemEnum.PARAMETER_NAME_NULL.getDesc()));
        }
        if (StringUtils.isBlank(parameter.getParameterValue())) {
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.PARAMETER_VALUE_NULL.getCode(),
                    ResponseCodeSystemEnum.PARAMETER_VALUE_NULL.getDesc()));
        }
        if (parameter.getParameterSort()==null) {
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.PARAMETER_SORT_NULL.getCode(),
                    ResponseCodeSystemEnum.PARAMETER_SORT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(parameter.getCreateOperator())){
            return ResponseResult.error(new Error(ResponseCodeSystemEnum.NOT_CREATE_OPPERATOR.getCode(),
                    ResponseCodeSystemEnum.NOT_CREATE_OPPERATOR.getDesc()));
        }
        return systemParameterService.insertParameter(parameter);
    }

    /**
     *@Description 查询系统参数类型(按parentid查询系统参数)
     *@Param [parameterType, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/6/10
     *@Time 15:38
     */
    /*@ApiOperation(value = "查询系统参数类型",notes = "查询系统参数类型")
    @RequestMapping(value = "/selectParameterTypeByWhere",method = RequestMethod.POST)
    public ResponseResult selectParameterTypeByWhere(ParameterType parameterType, String access_token) {
        return systemParameterService.selectParameterTypeByWhere(parameterType);
    }*/

}
