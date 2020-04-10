package com.lnmj.data.controller.backend;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeEvaluatingEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.business.IEvaluatingLevelService;
import com.lnmj.data.business.IEvaluatingService;
import com.lnmj.data.entity.Evaluating;
import com.lnmj.data.entity.EvaluatingDetailed;
import com.lnmj.data.entity.EvaluatingLevel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(description = "客户评测等级标准")
@RestController
@RequestMapping("/evaluatingLevel")
public class EvaluatingLevelController {
    @Resource
    private IEvaluatingLevelService evaluatingLevelService;

    /**
    *@Description 查询客户评测等级
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/10/29
    *@Time
    */
    @ApiOperation(value = "查询客户评测等级", notes = "查询客户评测等级")
    @RequestMapping(value = "/selectEvaluatingLeveldList", method = RequestMethod.POST)
    public ResponseResult selectEvaluatingLeveldList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String access_token) {
        return evaluatingLevelService.selectEvaluatingLeveldList(pageNum, pageSize);
    }


    @ApiOperation(value = "新增客户评测等级", notes = "新增客户评测等级")
    @RequestMapping(value = "/addEvaluatingLeveld", method = RequestMethod.POST)
    public ResponseResult addEvaluatingLeveld(EvaluatingLevel evaluatingLevel) {
        return evaluatingLevelService.addEvaluatingLeveld(evaluatingLevel);
    }

    @ApiOperation(value = "修改客户评测等级", notes = "修改客户评测等级")
    @RequestMapping(value = "/updateEvaluatingLeveld", method = RequestMethod.POST)
    public ResponseResult updateEvaluatingLeveldList(EvaluatingLevel evaluatingLevel) {
        return evaluatingLevelService.updateEvaluatingLeveldList(evaluatingLevel);
    }


    @ApiOperation(value = "删除客户评测等级", notes = "删除客户评测等级")
    @RequestMapping(value = "/deleteEvaluatingLeveld", method = RequestMethod.POST)
    public ResponseResult deleteEvaluatingLeveldList(EvaluatingLevel evaluatingLevel) {
        return evaluatingLevelService.deleteEvaluatingLeveldList(evaluatingLevel);
    }


}
