package com.lnmj.store.business;

import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.entity.Experiencecard;
import com.lnmj.store.entity.ExperiencecardOrderDetail;
import com.lnmj.store.entity.ExperiencecardProduct;
import com.lnmj.store.entity.ExperiencecardUseRecord;
import org.springframework.stereotype.Service;

@Service("experienceCardService")
public interface IExperienceCardService {
    ResponseResult selectExperienceCardList(int pageNum, int pageSize, String keyWordCardName, String keyWordCarNum,String storeId);

    ResponseResult selectExperienceCardByCardId(Long cardId);

    ResponseResult selectExpCardProductListByCarNum(int pageNum, int pageSize, String cardNum);

    ResponseResult deleteExpCardByCarNum(String cardNum);

    ResponseResult deleteExpCardProductById(Long cardProductId);

    ResponseResult updateExpCardProductById(ExperiencecardProduct experiencecardProduct);

    ResponseResult updateExpCardById(Experiencecard experiencecard);

    ResponseResult addExpCard(Long subordBuyLimitId, Double account, String cardName, String createOperator, JSONArray productJsonArray, Long achievementPostId,Long achievementId, String logoImage, String moreContent, Integer stockNum, Integer salesVolume, String purchaseDeadline);

    ResponseResult addExpCardOrder(String memoNum,Double account, String cardName, String cardNum, String createOperator, String linkPhone, String purchaserName, Integer storeId, String memberNum,String beauticians,String payTypeAndAmount);

    ResponseResult selectExpCardOrder(int pageNum, int pageSize, String keyWordLinkPhone,
                                      String keyWordLinkName,
                                      String cardName,
                                      String startdate,
                                      String endDate,
                                      String memoNum,
                                      Integer storeId);

    ResponseResult selectExpCardOrderDetail(int pageNum, int pageSize, String cardOrderCode);

    ResponseResult updateExpCardOrderDetail(ExperiencecardOrderDetail experiencecardOrderDetail);

    ResponseResult addUseRecord(ExperiencecardUseRecord experiencecardUseRecord);

    ResponseResult updateUseRecordStatus(ExperiencecardUseRecord experiencecardUseRecord);

    ResponseResult selectUseRecord(int pageNum, int pageSize, Long cardOrderDetailId);

    ResponseResult selectExpCardUser(String memberNum);

    ResponseResult selectExpCardUserProduct(String memberNum,Long experiencecardUserId);

    ResponseResult updateUserExpUseTimes(String staffNumber, Long id, String experiencecardNum, String memberNum, String productCode, Long storeId, String storeName, String productName, String postAndBeautician, String createOperator, String memberName, String memberMobile);

    ResponseResult experiencecardProductUserRefuse(Long id,Integer refuseTimes);

    ResponseResult selectExperiencecardProductUserRefuseList(int pageNum,int pageSize,Long id);

    ResponseResult addUserExpUseTimes(Long id);

    ResponseResult updateExpOrderMemoNum(String cardOrderCode,String memoNum,String modifyOperator);

    ResponseResult checkOrderMemo(String memoNum);

    ResponseResult updateAllExpCardProductOutStockPrice(Long cardId);

    ResponseResult cancleExpOrder(String orderNumbers, Integer orderStatus);

    ResponseResult updateExpOrderStatus(String cardOrderCode, Integer orderStatus);

    ResponseResult checkExpIsUse(String experiencecardNum,String memberCode);

    ResponseResult updateExpCarStockNumUp(String experiencecardNum);

    ResponseResult selectExpOrderCashAll(String memberNumber);
}
