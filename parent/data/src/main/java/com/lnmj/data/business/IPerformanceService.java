package com.lnmj.data.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.entity.*;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/9/5 16:43
 * @Description: 业绩
 */
@Service("iPerformanceService")
public interface IPerformanceService {

    ResponseResult selectPerformanceList(int pageNum, int pageSize,String storeId,Integer achievementType,Long achievementIndustryID,String keyWordAchievementName);

    ResponseResult selectPerformanceListNoPage(String storeId,Integer achievementType,Long achievementIndustryID);

    ResponseResult selectPerformancePostList(int pageNum, int pageSize,String achievementID,String keyWord);

    ResponseResult selectPerformancePostListAll();

    ResponseResult selectPerformanceByCondition(int pageNum, int pageSize, Performance performance);

    ResponseResult deletePerformanceByID(Long id, String modifyOperator);

    ResponseResult deletePerformancePostByID(Long performanceId,  Long performancePostId,String modifyOperator);

    ResponseResult insertPerformance(Performance performance);

    ResponseResult insertPerformancePost(PerformancePost performancePost);

    ResponseResult updatePerformance(Performance performance);

    ResponseResult updatePerformancePost(PerformancePost performancePost);

    ResponseResult selectLadderDetailedList(int pageNum, int pageSize,Long storeId);

    ResponseResult selectLadderDetailedListColumnar(String storeId);

    ResponseResult selectLadderDetailedListColumnarForNumber(String storeId);

    ResponseResult selectLadderDetaiColumnarByBeauId(Long beauticianId,Integer isNumber);

    ResponseResult selectLadderDetailedByCondition(int pageNum, int pageSize, LadderDetailed ladderDetailed);

    ResponseResult deleteLadderDetailedByID(Long ladderDetailedID, String modifyOperator);

    ResponseResult deleteLadderDetailedByOrder(String orderNum, String modifyOperator);

    ResponseResult insertLadderDetailed(Integer productType,Long storeId, String beauticianId, String orderNum, String payTypeAndAmount,Integer productNum,String productCode,Double price,String industryId);

    ResponseResult updateLadderDetailed(LadderDetailed ladderDetailed);

    ResponseResult deleteLadderDetailedByCondition(LadderDetailed ladderDetailed);

    ResponseResult updateLadder(Ladder ladder);

    ResponseResult selectLadderByCondition(int pageNum, int pageSize, Ladder ladder);

    ResponseResult selectLadderList(int pageNum, int pageSize);

    ResponseResult deleteLadderByID(Long ladderID, String modifyOperator);

    ResponseResult insertLadder(Ladder ladder);

    ResponseResult listAchievementMethods();

    ResponseResult selectLadderDetailedByRechargeOrderNum(Long orderNumber);

    ResponseResult selectPerformanceById(Long id);

    ResponseResult selectPerformancePostById(Long id);

    ResponseResult listAchievementTypeEnum();

    ResponseResult insertOrderTicheng(OrderTicheng orderTicheng);

    ResponseResult updateOrderTichengByID(OrderTicheng orderTicheng);

    ResponseResult deleteOrderTichengByID(Long id, String modifyOperator);

    ResponseResult selectOrderTichengByID(Long id);

    ResponseResult selectOrderTichengByCondition(int pageNum, int pageSize, OrderTicheng orderTicheng);

    ResponseResult selectOrderTichengList(int pageNum, int pageSize);
}
