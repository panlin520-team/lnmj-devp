package com.lnmj.data.controller.backend;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeOrderEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodePerformanceEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.business.IPerformanceService;
import com.lnmj.data.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: yilihua
 * @Date: 2019/9/4 18:07
 * @Description: 业绩(业绩 ， 业绩阶梯 ， 业绩明细，订单提成)
 */
@Api(description = "业绩")
@RestController
@RequestMapping("/performance")
public class PerformanceController {
    @Resource
    private IPerformanceService performanceService;

    //================================================订单提成==========================================
    @ApiOperation(value = "查询订单提成", notes = "查询订单提成")
    @RequestMapping(value = "/selectOrderTichengList", method = RequestMethod.POST)
    public ResponseResult selectOrderTichengList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return performanceService.selectOrderTichengList(pageNum, pageSize);
    }
    @ApiOperation(value = "条件查询订单提成", notes = "条件查询订单提成")
    @RequestMapping(value = "/selectOrderTichengByCondition", method = RequestMethod.POST)
    public ResponseResult selectOrderTichengByCondition(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, OrderTicheng orderTicheng) {
        return performanceService.selectOrderTichengByCondition(pageNum,pageSize,orderTicheng);
    }
    @ApiOperation(value = "按id查询订单提成", notes = "按id查询订单提成")
    @RequestMapping(value = "/selectOrderTichengByID", method = RequestMethod.POST)
    public ResponseResult selectOrderTichengByID(Long id) {
        return performanceService.selectOrderTichengByID(id);
    }
    @ApiOperation(value = "按id删除订单提成", notes = "按id删除订单提成")
    @RequestMapping(value = "/deleteOrderTichengByID", method = RequestMethod.POST)
    public ResponseResult deleteOrderTichengByID(Long id,String modifyOperator) {
        return performanceService.deleteOrderTichengByID(id,modifyOperator);
    }
    @ApiOperation(value = "插入订单提成", notes = "插入订单提成")
    @RequestMapping(value = "/insertOrderTicheng", method = RequestMethod.POST)
    public ResponseResult insertOrderTicheng(OrderTicheng orderTicheng) {
        return performanceService.insertOrderTicheng(orderTicheng);
    }
    @ApiOperation(value = "更新订单提成", notes = "更新订单提成")
    @RequestMapping(value = "/updateOrderTichengByID", method = RequestMethod.POST)
    public ResponseResult updateOrderTichengByID(OrderTicheng orderTicheng) {
        return performanceService.updateOrderTichengByID(orderTicheng);
    }

    //================================================业绩===============================================
    /**
     * @Description 查询业绩
     * @Param [pageNum, pageSize, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5
     * @Time 16:01
     */
    @ApiOperation(value = "查询业绩", notes = "查询业绩")
    @RequestMapping(value = "/selectPerformanceList", method = RequestMethod.POST)
    public ResponseResult selectPerformanceList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String storeId,Integer achievementType,Long achievementIndustryID,String keyWordAchievementName) {
        return performanceService.selectPerformanceList(pageNum, pageSize, storeId,achievementType,achievementIndustryID,keyWordAchievementName);
    }

    @ApiOperation(value = "查询业绩", notes = "查询业绩")
    @RequestMapping(value = "/selectPerformanceListNoPage", method = RequestMethod.POST)
    public ResponseResult selectPerformanceListNoPage(String storeId,Integer achievementType,Long achievementIndustryID) {
        return performanceService.selectPerformanceListNoPage(storeId,achievementType,achievementIndustryID);
    }

    @ApiOperation(value = "查询业绩职位", notes = "查询业绩")
    @RequestMapping(value = "/selectPerformancePostList", method = RequestMethod.POST)
    public ResponseResult selectPerformancePostList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String achievementID,String keyWord) {
        return performanceService.selectPerformancePostList(pageNum, pageSize, achievementID,keyWord);
    }

    @ApiOperation(value = "查询业绩职位所有", notes = "查询业绩职位所有")
    @RequestMapping(value = "/selectPerformancePostListAll", method = RequestMethod.POST)
    public ResponseResult selectPerformancePostListAll() {
        return performanceService.selectPerformancePostListAll();
    }

    /**
     * @Description 条件查询业绩
     * @Param [pageNum, pageSize, performance, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5
     * @Time 16:03
     */
    @ApiOperation(value = "条件查询业绩", notes = "条件查询业绩")
    @RequestMapping(value = "/selectPerformanceByCondition", method = RequestMethod.POST)
    public ResponseResult selectPerformanceByCondition(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                       Performance performance, String access_token) {
        return performanceService.selectPerformanceByCondition(pageNum, pageSize, performance);
    }

    /**
     * @Description 删除业绩
     * @Param [achievementID, modifyOperator, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5
     * @Time 16:06
     */
    @ApiOperation(value = "删除业绩", notes = "删除业绩")
    @RequestMapping(value = "/deletePerformanceByID", method = RequestMethod.POST)
    public ResponseResult deletePerformanceByID(Long id, String modifyOperator) {
        if (id == null) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.PERFORMANCE_ID_NOT_NULL.getCode(),
                    ResponseCodePerformanceEnum.PERFORMANCE_ID_NOT_NULL.getDesc()));
        }

        return performanceService.deletePerformanceByID(id, modifyOperator);
    }

    /**
     * @Description 删除业绩职位
     * @Param [achievementID, modifyOperator, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5
     * @Time 16:06
     */
    @ApiOperation(value = "删除业绩职位", notes = "删除业绩职位")
    @RequestMapping(value = "/deletePerformancePostByID", method = RequestMethod.POST)
    public ResponseResult deletePerformancePostByID(Long performanceId, Long performancePostId, String modifyOperator) {
        if (performancePostId == null) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.PERFORMANCE_ID_NOT_NULL.getCode(),
                    ResponseCodePerformanceEnum.PERFORMANCE_ID_NOT_NULL.getDesc()));
        }

        return performanceService.deletePerformancePostByID(performanceId, performancePostId, modifyOperator);
    }


    /**
     * @Description 新增业绩
     * @Param [performance, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5
     * @Time 16:35
     */
    @ApiOperation(value = "新增业绩", notes = "新增业绩")
    @RequestMapping(value = "/insertPerformance", method = RequestMethod.POST)
    public ResponseResult insertPerformance(Performance performance) {
        return performanceService.insertPerformance(performance);
    }

    @ApiOperation(value = "新增业绩职位", notes = "新增业绩职位")
    @RequestMapping(value = "/insertPerformancePost", method = RequestMethod.POST)
    public ResponseResult insertPerformancePost(PerformancePost performancePost) {
        return performanceService.insertPerformancePost(performancePost);
    }

    /**
     * @Description 修改业绩
     * @Param [performance, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5
     * @Time 16:36
     */
    @ApiOperation(value = "修改业绩", notes = "修改业绩")
    @RequestMapping(value = "/updatePerformance", method = RequestMethod.POST)
    public ResponseResult updatePerformance(Performance performance) {
        if (performance.getId() == null) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.PERFORMANCE_ID_NOT_NULL.getCode(),
                    ResponseCodePerformanceEnum.PERFORMANCE_ID_NOT_NULL.getDesc()));
        }

        return performanceService.updatePerformance(performance);
    }

    @ApiOperation(value = "修改业绩职位", notes = "修改业绩职位")
    @RequestMapping(value = "/updatePerformancePost", method = RequestMethod.POST)
    public ResponseResult updatePerformancePost(PerformancePost performancePost) {
        if (performancePost.getId() == null) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.PERFORMANCE_ID_NOT_NULL.getCode(),
                    ResponseCodePerformanceEnum.PERFORMANCE_ID_NOT_NULL.getDesc()));
        }

        return performanceService.updatePerformancePost(performancePost);
    }

    //================================================业绩阶梯==========================================
    /**
     * @Description 查询业绩阶梯
     * @Param [pageNum, pageSize, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5
     * @Time 16:01
     */
    @ApiOperation(value = "查询业绩阶梯", notes = "查询业绩阶梯")
    @RequestMapping(value = "/selectLadderList", method = RequestMethod.POST)
    public ResponseResult selectLadderList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String access_token) {
        return performanceService.selectLadderList(pageNum, pageSize);
    }

    /**
     * @Description 条件查询业绩阶梯
     * @Param [pageNum, pageSize, ladder, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5
     * @Time 16:03
     */
    @ApiOperation(value = "条件查询业绩阶梯", notes = "条件查询业绩阶梯")
    @RequestMapping(value = "/selectLadderByCondition", method = RequestMethod.POST)
    public ResponseResult selectLadderByCondition(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                  Ladder ladder, String access_token) {
        return performanceService.selectLadderByCondition(pageNum, pageSize, ladder);
    }

    /**
     * @Description 删除业绩阶梯
     * @Param [ladderID, modifyOperator, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5
     * @Time 16:06
     */
    @ApiOperation(value = "删除业绩阶梯", notes = "删除业绩阶梯")
    @RequestMapping(value = "/deleteLadderByID", method = RequestMethod.POST)
    public ResponseResult deleteLadderByID(Long ladderID, String modifyOperator, String access_token) {
        if (ladderID == null) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.LADDER_ID_NOT_NULL.getCode(),
                    ResponseCodePerformanceEnum.LADDER_ID_NOT_NULL.getDesc()));
        }
        if (StringUtils.isBlank(modifyOperator)) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodePerformanceEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return performanceService.deleteLadderByID(ladderID, modifyOperator);
    }

    /**
     * @Description 新增业绩阶梯
     * @Param [performance, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5
     * @Time 16:35
     */
    @ApiOperation(value = "新增业绩阶梯", notes = "新增业绩阶梯")
    @RequestMapping(value = "/insertLadder", method = RequestMethod.POST)
    public ResponseResult insertLadder(Ladder ladder) {
        return performanceService.insertLadder(ladder);
    }

    /**
     * @Description 修改业绩阶梯
     * @Param [ladder, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5
     * @Time 16:36
     */
    @ApiOperation(value = "修改业绩阶梯", notes = "修改业绩阶梯")
    @RequestMapping(value = "/updateLadder", method = RequestMethod.POST)
    public ResponseResult updateLadder(Ladder ladder) {
        if (ladder.getLadderID() == null) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.LADDER_ID_NOT_NULL.getCode(),
                    ResponseCodePerformanceEnum.LADDER_ID_NOT_NULL.getDesc()));
        }
      /*  if (StringUtils.isBlank(ladder.getModifyOperator())) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodePerformanceEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }*/
        return performanceService.updateLadder(ladder);
    }

    //================================================业绩明细==========================================
    /**
     * @Description 查询业绩明细
     * @Param [pageNum, pageSize, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5
     * @Time 16:01
     */
    @ApiOperation(value = "查询业绩明细", notes = "查询业绩明细")
    @RequestMapping(value = "/selectLadderDetailedList", method = RequestMethod.POST)
    public ResponseResult selectLadderDetailedList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String access_token, Long storeId) {
        return performanceService.selectLadderDetailedList(pageNum, pageSize, storeId);
    }

    /**
     * @Description 查询业绩明细
     * @Param [pageNum, pageSize, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5
     * @Time 16:01
     */
    @ApiOperation(value = "查询业绩明细(柱状图)", notes = "查询业绩明细(柱状图)")
    @RequestMapping(value = "/selectLadderDetailedListColumnar", method = RequestMethod.POST)
    public ResponseResult selectLadderDetailedListColumnar(String storeId) {
        return performanceService.selectLadderDetailedListColumnar(storeId);
    }

    @ApiOperation(value = "查询业绩明细-个数(柱状图)", notes = "查询业绩明细(柱状图)")
    @RequestMapping(value = "/selectLadderDetailedListColumnarForNumber", method = RequestMethod.POST)
    public ResponseResult selectLadderDetailedListColumnarForNumber(String storeId) {
        return performanceService.selectLadderDetailedListColumnarForNumber(storeId);
    }

   /**
   *@Description 根据员工id查看业绩详情(柱状图)
   *@Param [storeId]
   *@Return com.lnmj.common.response.ResponseResult
   *@Author panlin
   *@Date 2019/10/30
   *@Time 16:18
   */
    @ApiOperation(value = "根据员工id查看业绩详情(柱状图)", notes = "根据员工id查看业绩详情(柱状图)")
    @RequestMapping(value = "/selectLadderDetaiColumnarByBeauId", method = RequestMethod.POST)
    public ResponseResult selectLadderDetaiColumnarByBeauId(Long beauticianId,Integer isNumber) {
        return performanceService.selectLadderDetaiColumnarByBeauId(beauticianId,isNumber);
    }

    /**
     * @Description 条件查询业绩明细
     * @Param [pageNum, pageSize, ladderDetailed, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5
     * @Time 16:03
     */
    @ApiOperation(value = "条件查询业绩明细", notes = "条件查询业绩明细")
    @RequestMapping(value = "/selectLadderDetailedByCondition", method = RequestMethod.POST)
    public ResponseResult selectLadderDetailedByCondition(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                          LadderDetailed ladderDetailed, String access_token) {
        return performanceService.selectLadderDetailedByCondition(pageNum, pageSize, ladderDetailed);
    }

    /**
     * @Description 删除业绩明细
     * @Param [ladderDetailedID, modifyOperator, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5
     * @Time 16:06
     */
    @ApiOperation(value = "删除业绩明细", notes = "删除业绩明细")
    @RequestMapping(value = "/deleteLadderDetailedByID", method = RequestMethod.POST)
    public ResponseResult deleteLadderDetailedByID(Long ladderDetailedID ,String modifyOperator, String access_token) {
        if (ladderDetailedID == null) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.LADDERDETAILED_ID_NOT_NULL.getCode(),
                    ResponseCodePerformanceEnum.LADDERDETAILED_ID_NOT_NULL.getDesc()));
        }
        if (StringUtils.isBlank(modifyOperator)) {
            return ResponseResult.error(new Error(ResponseCodePerformanceEnum.MODIFYOPERATOR_NOT_NULL.getCode(),
                    ResponseCodePerformanceEnum.MODIFYOPERATOR_NOT_NULL.getDesc()));
        }
        return performanceService.deleteLadderDetailedByID(ladderDetailedID, modifyOperator);
    }

    @ApiOperation(value = "删除订单对应业绩", notes = "删除订单对应业绩")
    @RequestMapping(value = "/deleteLadderDetailedByOrder", method = RequestMethod.POST)
    public ResponseResult deleteLadderDetailedByOrder(String orderNum ,String modifyOperator, String access_token) {
        if (orderNum == null) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_NUMBER_NULL.getCode(),
                    ResponseCodeOrderEnum.ORDER_NUMBER_NULL.getDesc()));
        }
        return performanceService.deleteLadderDetailedByOrder(orderNum, modifyOperator);
    }

    /**
     * @Description 新增业绩明细
     * @Param [ladderDetailed, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5null
     * @Time 16:35
     */
    @ApiOperation(value = "新增业绩明细", notes = "新增业绩明细")
    @RequestMapping(value = "/insertLadderDetailed", method = RequestMethod.POST)
    public ResponseResult insertLadderDetailed(Integer productType,Long storeId, String beauticianId, String orderNum, String payTypeAndAmount,Integer productNum,String productCode,Double price,String industryId) {
        return performanceService.insertLadderDetailed(productType,storeId,beauticianId,orderNum,payTypeAndAmount,productNum,productCode,price,industryId);
    }

    /**
     * @Description 修改业绩明细
     * @Param [ladderDetailed, access_token]
     * @Return com.lnmj.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/9/5
     * @Time 16:36
     */
    @ApiOperation(value = "修改业绩明细", notes = "修改业绩明细")
    @RequestMapping(value = "/updateLadderDetailed", method = RequestMethod.POST)
    public ResponseResult updateLadderDetailed(LadderDetailed ladderDetailed) {
        return performanceService.updateLadderDetailed(ladderDetailed);
    }

    @ApiOperation(value = "删除业绩明细", notes = "删除业绩明细")
    @RequestMapping(value = "/deleteLadderDetailedByCondition", method = RequestMethod.POST)
    public ResponseResult deleteLadderDetailedByCondition(LadderDetailed ladderDetailed) {
        return performanceService.deleteLadderDetailedByCondition(ladderDetailed);
    }

    /**
    *@Description  查看业绩提成方式
    *@Param [storeId, subclassID, orderAmount, beauticianId, orderType, orderNum, payTypeId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/10/12
    *@Time 18:15
    */
    @ApiOperation(value = "查看业绩提成方式", notes = "查看业绩提成方式")
    @RequestMapping(value = "/listAchievementMethods", method = RequestMethod.POST)
    public ResponseResult listAchievementMethods() {
        return performanceService.listAchievementMethods();
    }

    @ApiOperation(value = "查看业绩类型枚举", notes = "查看业绩类型枚举")
    @RequestMapping(value = "/listAchievementTypeEnum", method = RequestMethod.POST)
    public ResponseResult listAchievementTypeEnum() {
        return performanceService.listAchievementTypeEnum();
    }

}
