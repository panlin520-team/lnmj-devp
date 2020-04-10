package com.lnmj.data.controller.backend;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.business.IProductBrandStatisticsService;
import com.lnmj.data.business.IScoreService;
import com.lnmj.data.entity.Score;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 〈评分〉
 *
 * @Author renqingyun
 * @create 2019/9/4
 */

@Api(description = "评分")
@RequestMapping("/manage/score")
@RestController
public class ScoreController {

    @Resource
    private IScoreService scoreService;

    @ApiOperation(value = "查询评分", notes = "查询评分")
    @RequestMapping(value = "/selectList",method = RequestMethod.POST)
    public ResponseResult selectList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,String storeId) {
        return scoreService.selectList(pageNum,pageSize,storeId);
    }


    /**
    *@Description 根据评分id查询评分
    *@Param []
    *@Return com.lnmj.common.response.ResponseResult
    *@Author Mr.Ren
    *@Date 2019/10/16
    *@Time 
    */
    @ApiOperation(value = "根据评分id查询评分", notes = "根据评分id查询评分")
    @RequestMapping(value = "/selectScoreById",method = RequestMethod.POST)
    public ResponseResult selectScoreById(Long scoreID) {
        return scoreService.selectScoreById(scoreID);
    }


    @ApiOperation(value = "添加评分", notes = "添加评分")
    @RequestMapping(value = "/addScore",method = RequestMethod.POST)
    public ResponseResult addScore(Score score, String access_token) {
        return scoreService.addScore(score);
    }


    @ApiOperation(value = "更新评分", notes = "更新评分")
    @RequestMapping(value = "/updateScore",method = RequestMethod.POST)
    public ResponseResult updateScore(Score score,String access_token) {
        return scoreService.updateScore(score);
    }


    @ApiOperation(value = "删除评分", notes = "删除评分")
    @RequestMapping(value = "/deleteScore",method = RequestMethod.POST)
    public ResponseResult deleteScore(Long scoreID,String modifyOperator,String access_token) {
        return scoreService.deleteScore(scoreID,modifyOperator);
    }


    @ApiOperation(value = "获取计分方式", notes = "获取计分方式")
    @RequestMapping(value = "/selectScoreMode",method = RequestMethod.POST)
    public ResponseResult selectScoreMode() {
        return scoreService.selectScoreMode();
    }
}
