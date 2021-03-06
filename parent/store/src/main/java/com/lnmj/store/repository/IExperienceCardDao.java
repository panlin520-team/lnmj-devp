package com.lnmj.store.repository;

import com.lnmj.store.entity.*;
import com.lnmj.store.entity.VO.ExperiencecardVO;

import java.util.List;
import java.util.Map;


public interface IExperienceCardDao {
    List<Experiencecard> selectExperienceCardList(Map map);
    Experiencecard selectExperienceCardByCardNum(String cardNum);
    Experiencecard selectExperienceCardByCardId(Map map);
    List<ExperiencecardProduct> selectExpCardProductListByCarNum(String cardNum);
    int deleteExpCardByCarNum(String cardNum);
    int deleteExpCardProductByCarNum(String cardNum);
    int deleteExpCardProductById(Long cardProductId);
    int updateExpCardProductById(ExperiencecardProduct experiencecardProduct);
    int updateExpCardById(Experiencecard experiencecard);
    int addExpCard(Experiencecard experiencecard);
    int addExpCardProduct(Map map);
    int addExpCardOrder(ExperiencecardOrder experiencecardOrder);
    int addExpCardOrderDetail(Map map);
    int addExpCardUser(ExperiencecardUser experiencecardUser);
    int addExpCardUserProduct(Map map);
    List<ExperiencecardOrder> selectExpCardOrder(Map map);
    List<ExperiencecardOrderDetail> selectExpCardOrderDetail(String cardOrderCode);
    int updateExpCardOrderDetail(ExperiencecardOrderDetail experiencecardOrderDetail);
    ExperiencecardOrderDetail selectExpCardOrderDetailById(Long cardOrderDetailId);
    int addUseRecord(ExperiencecardUseRecord experiencecardUseRecord);
    int updateUseRecordStatus(ExperiencecardUseRecord experiencecardUseRecord);
    int updateUseRecord(ExperiencecardUseRecord experiencecardUseRecord);
    List<ExperiencecardUseRecord> selectUseRecord(Long cardOrderDetailId);
    List<ExperiencecardUser> selectExperienceCardUserList();
    List<ExperiencecardProductUser> selectExperienceCardProductUserList(Map map);
    List<ExperiencecardProductUser> selectExpCardUserProduct(Map map);
    int updateUserExpUseTimes(Map map);
    int addUserExpUseTimes(Long id);
    int updateExpOrderMemoNum(Map map);
    int checkOrderMemo(Map map);
    int updateExpAddSalesVolumeReduceStock(Map map);

    List<ExperiencecardVO> selectExperienceCardVoUserList(Map map);

    ExperiencecardProduct selectExperienceCardProductById(Map map);

    int updateExpCardProductBatch(Map map);

    int cancleExpOrder(Map map);

    int updateExpOrderStatus(Map map);

    int checkExpIsUse(Map map);

    int updateExpCarStockNumUp(String experiencecardNum);

    ExperiencecardProductUser selectExperienceCardProductUserById(ExperiencecardProductUser experiencecardProductUser);
    int updateExperienceCardProductUserTotalTimesById(Map map);
    int addExperiencecardProductUserRefuse(ExperiencecardProductUserRefuse experiencecardProductUserRefuse);
    List<ExperiencecardProductUserRefuse> selectExperiencecardProductUserRefuseList(Map map);
    List<String> selectMemberNumberPayTypeAndAmount(Map map);
}
