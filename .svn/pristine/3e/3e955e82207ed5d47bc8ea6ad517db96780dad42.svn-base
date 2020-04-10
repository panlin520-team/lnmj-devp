package com.lnmj.data.controller.backend;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.business.IBaseSalaryService;
import com.lnmj.data.business.IScoreService;
import com.lnmj.data.entity.Basesalary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 〈底薪〉
 *
 * @Author renqingyun
 * @create 2019/9/4
 */

@Api(description = "底薪")
@RequestMapping("/manage/baseSalary")
@RestController
public class BaseSalaryController {

    @Resource
    private IBaseSalaryService baseSalaryService;

    @ApiOperation(value = "查询底薪", notes = "查询底薪")
    @RequestMapping(value = "/selectList",method = RequestMethod.POST)
    public ResponseResult selectList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,String access_token) {
        return baseSalaryService.selectList(pageNum,pageSize);
    }


    @ApiOperation(value = "添加底薪", notes = "添加底薪")
    @RequestMapping(value = "/addBaseSalary",method = RequestMethod.POST)
    public ResponseResult addBaseSalary(Basesalary basesalary, String access_token) {
        return baseSalaryService.addBaseSalary(basesalary);
    }


    @ApiOperation(value = "更新底薪", notes = "更新底薪")
    @RequestMapping(value = "/updateBaseSalary",method = RequestMethod.POST)
    public ResponseResult updateBaseSalary(Basesalary basesalary,String access_token) {
        return baseSalaryService.updateBaseSalary(basesalary);
    }


    @ApiOperation(value = "删除底薪", notes = "删除底薪")
    @RequestMapping(value = "/deleteBaseSalary",method = RequestMethod.POST)
    public ResponseResult deleteBaseSalary(Long baseSalaryScoreID,String modifyOperator,String access_token) {
        return baseSalaryService.deleteBaseSalary(baseSalaryScoreID,modifyOperator);
    }
}
