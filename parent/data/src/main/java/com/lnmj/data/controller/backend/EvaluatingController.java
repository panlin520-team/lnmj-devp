package com.lnmj.data.controller.backend;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeEvaluatingEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodePerformanceEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.business.IEvaluatingService;
import com.lnmj.data.entity.Evaluating;
import com.lnmj.data.entity.EvaluatingDetailed;
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
 * @Date: 2019/10/12 14:57
 * @Description: 客户评测
 */
@Api(description = "客户评测")
@RestController
@RequestMapping("/evaluating")
public class EvaluatingController {
    @Resource
    private IEvaluatingService evaluatingService;

    /*----------------------客户评测明细------------------------*/
    /**
    *@Description 查询客户评测明细
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/12
    *@Time 18:17
    */
    @ApiOperation(value = "查询客户评测明细", notes = "查询客户评测明细")
    @RequestMapping(value = "/selectEvaluatingDetailedList", method = RequestMethod.POST)
    public ResponseResult selectEvaluatingDetailedList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String access_token) {
        return evaluatingService.selectEvaluatingDetailedList(pageNum, pageSize);
    }

    /**
    *@Description 条件查询客户评测明细
    *@Param [pageNum, pageSize, evaluatingDetailed, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/12
    *@Time 18:18
    */
    @ApiOperation(value = "条件查询客户评测明细", notes = "条件查询客户评测明细")
    @RequestMapping(value = "/selectEvaluatingDetailedByCondition", method = RequestMethod.POST)
    public ResponseResult selectEvaluatingDetailedByCondition(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                                EvaluatingDetailed evaluatingDetailed, String access_token) {
        return evaluatingService.selectEvaluatingDetailedByCondition(pageNum, pageSize,evaluatingDetailed);
    }

    /**
    *@Description 根据ID查询客户评测明细
    *@Param [pageNum, pageSize, evaluatingDetailedID, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/12
    *@Time 18:20
    */
    @ApiOperation(value = "根据ID查询客户评测明细", notes = "根据ID查询客户评测明细")
    @RequestMapping(value = "/selectEvaluatingDetailedByID", method = RequestMethod.POST)
    public ResponseResult selectEvaluatingDetailedByID(Long evaluatingDetailedID, String access_token) {
        if(evaluatingDetailedID==null){
            return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.EVALUATING_DETAILED_ID_NOT_NULL.getCode(),
                    ResponseCodeEvaluatingEnum.EVALUATING_DETAILED_ID_NOT_NULL.getDesc()));
        }
        return evaluatingService.selectEvaluatingDetailedByID(evaluatingDetailedID);
    }

    /**
    *@Description 删除客户评测明细
    *@Param [evaluatingDetailed, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/12
    *@Time 18:21
    */
    @ApiOperation(value = "删除客户评测明细", notes = "删除客户评测明细")
    @RequestMapping(value = "/deleteEvaluatingDetailedByID", method = RequestMethod.POST)
    public ResponseResult deleteEvaluatingDetailedByID(Long evaluatingDetailedID,String modifyOperator, String access_token) {
        if(evaluatingDetailedID==null){
            return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.EVALUATING_DETAILED_ID_NOT_NULL.getCode(),
                    ResponseCodeEvaluatingEnum.EVALUATING_DETAILED_ID_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeEvaluatingEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return evaluatingService.deleteEvaluatingDetailedByID(evaluatingDetailedID,modifyOperator);
    }

    /**
    *@Description 新增客户评测明细
    *@Param [evaluatingDetailed, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/12
    *@Time 18:21
    */
    @ApiOperation(value = "新增客户评测明细", notes = "新增客户评测明细")
    @RequestMapping(value = "/insertEvaluatingDetailed", method = RequestMethod.POST)
    public ResponseResult insertEvaluatingDetailed(String orderNum,
                                                   String memberNum,
                                                   Long subclass,
                                                   Double price,
                                                   Integer orderType,
                                                    Long productTypeId) {
        return evaluatingService.insertEvaluatingDetailed(orderNum,memberNum,subclass,price,orderType,productTypeId);
    }

    /**
    *@Description 更新客户评测明细
    *@Param [evaluatingDetailed, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/12
    *@Time 18:22
    */
    @ApiOperation(value = "更新客户评测明细", notes = "更新客户评测明细")
    @RequestMapping(value = "/updateEvaluatingDetailedByID", method = RequestMethod.POST)
    public ResponseResult updateEvaluatingDetailedByID(EvaluatingDetailed evaluatingDetailed, String access_token) {
        if(evaluatingDetailed.getEvaluatingDetailedID()==null){
            return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.EVALUATING_DETAILED_ID_NOT_NULL.getCode(),
                    ResponseCodeEvaluatingEnum.EVALUATING_DETAILED_ID_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(evaluatingDetailed.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeEvaluatingEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return evaluatingService.updateEvaluatingDetailedByID(evaluatingDetailed);
    }


    /*----------------------客户评测---------------------------*/
    /*----------------------客户评测等级------------------------*/
    /**
    *@Description 查询客户评测（包含客户评测级别）
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/12
    *@Time 18:11
    */
    @ApiOperation(value = "查询客户评测", notes = "查询客户评测")
    @RequestMapping(value = "/selectEvaluatingList", method = RequestMethod.POST)
    public ResponseResult selectEvaluatingList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String keyWord,String evaluatingIndustryID) {
        return evaluatingService.selectEvaluatingList(pageNum, pageSize,keyWord,evaluatingIndustryID);
    }
    
    /**
    *@Description 条件查询客户评测（包含客户评测级别）
    *@Param [pageNum, pageSize, evaluating, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/12
    *@Time 18:12
    */
    @ApiOperation(value = "条件查询客户评测", notes = "条件查询客户评测")
    @RequestMapping(value = "/selectEvaluatingByCondition", method = RequestMethod.POST)
    public ResponseResult selectEvaluatingByCondition(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                               Evaluating evaluating,String access_token) {
        return evaluatingService.selectEvaluatingByCondition(pageNum, pageSize,evaluating);
    }

    /**
    *@Description 根据ID查询客户评测（包含客户评测级别）
    *@Param [evaluatingID, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/12
    *@Time 18:13
    */
    @ApiOperation(value = "根据ID查询客户评测", notes = "根据ID查询客户评测")
    @RequestMapping(value = "/selectEvaluatingByID", method = RequestMethod.POST)
    public ResponseResult selectEvaluatingByID(Long evaluatingID,String access_token) {
        if(evaluatingID==null){
            return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.EVALUATING_ID_NOT_NULL.getCode(),
                    ResponseCodeEvaluatingEnum.EVALUATING_ID_NOT_NULL.getDesc()));
        }
        return evaluatingService.selectEvaluatingByID(evaluatingID);
    }

    /**
    *@Description 根据ID删除客户评测(同时删除客户评测级别)
    *@Param [evaluating, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/12
    *@Time 18:14
    */
    @ApiOperation(value = "根据ID删除客户评测", notes = "根据ID删除客户评测")
    @RequestMapping(value = "/deleteEvaluatingByID", method = RequestMethod.POST)
    public ResponseResult deleteEvaluatingByID(Long evaluatingID,String modifyOperator,String access_token) {
        if(evaluatingID==null){
            return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.EVALUATING_ID_NOT_NULL.getCode(),
                    ResponseCodeEvaluatingEnum.EVALUATING_ID_NOT_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodeEvaluatingEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return evaluatingService.deleteEvaluatingByID(evaluatingID,modifyOperator);
    }

    /**
    *@Description 新增客户评测(同时新增客户评测级别)
    *@Param [evaluating, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/12
    *@Time 18:15
    */
    @ApiOperation(value = "新增客户评测", notes = "新增客户评测")
    @RequestMapping(value = "/insertEvaluating", method = RequestMethod.POST)
    public ResponseResult insertEvaluating(Evaluating evaluating,String access_token) {
        return evaluatingService.insertEvaluating(evaluating);
    }

    /**
    *@Description 根据ID更新客户评测(同时更新客户评测级别)
    *@Param [evaluating, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/10/12
    *@Time 18:15
    */
    @ApiOperation(value = "根据ID更新客户评测", notes = "根据ID更新客户评测")
    @RequestMapping(value = "/updateEvaluatingByID", method = RequestMethod.POST)
    public ResponseResult updateEvaluatingByID(Evaluating evaluating,String access_token) {
        if(evaluating.getEvaluatingID()==null){
            return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.EVALUATING_ID_NOT_NULL.getCode(),
                    ResponseCodeEvaluatingEnum.EVALUATING_ID_NOT_NULL.getDesc()));
        }

        return evaluatingService.updateEvaluatingByID(evaluating);
    }

}
