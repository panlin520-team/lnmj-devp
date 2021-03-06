package com.lnmj.store.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.store.entity.*;
import com.lnmj.store.entity.VO.ExperiencecardVO;
import com.lnmj.store.repository.IExperienceCardDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @Author panlin
 * @create 2019/5/28
 */

@Repository
public class ExperienceCardDaoImpl extends BaseDao implements IExperienceCardDao {
    @Override
    public List<Experiencecard> selectExperienceCardList(Map map) {
        return super.selectList("experienceCard.selectExperienceCardList",map);
    }

    @Override
    public Experiencecard selectExperienceCardByCardNum(String cardNum) {
        return super.select("experienceCard.selectExperienceCardByCardNum",cardNum);
    }

    @Override
    public Experiencecard selectExperienceCardByCardId(Map map) {
        return super.select("experienceCard.selectExperienceCardByCardId",map);
    }

    @Override
    public List<ExperiencecardProduct> selectExpCardProductListByCarNum(String cardNum) {
        return super.selectList("experiencecardProduct.selectExpCardProductListByCarNum",cardNum);
    }

    @Override
    public int deleteExpCardByCarNum(String cardNum) {
        return super.delete("experienceCard.deleteExpCardByCarNum",cardNum);
    }

    @Override
    public int deleteExpCardProductByCarNum(String cardNum) {
        return super.delete("experiencecardProduct.deleteExpCardProductByCarNum",cardNum);
    }

    @Override
    public int deleteExpCardProductById(Long cardProductId) {
        return super.delete("experiencecardProduct.deleteExpCardProductById",cardProductId);
    }

    @Override
    public int updateExpCardProductById(ExperiencecardProduct experiencecardProduct) {
        return super.update("experiencecardProduct.updateExpCardProductById",experiencecardProduct);
    }

    @Override
    public int updateExpCardById(Experiencecard experiencecard) {
        return super.update("experienceCard.updateExpCardById",experiencecard);
    }

    @Override
    public int updateExpAddSalesVolumeReduceStock(Map map) {
        return super.update("experienceCard.updateExpAddSalesVolumeReduceStock",map);
    }


    @Override
    public int addExpCard(Experiencecard experiencecard) {
        return super.insert("experienceCard.addExpCard",experiencecard);
    }

    @Override
    public int addExpCardProduct(Map map) {
        return super.insert("experiencecardProduct.addExpCardProduct",map);
    }

    @Override
    public int addExpCardOrder(ExperiencecardOrder experiencecardOrder) {
        return super.insert("experiencecardOrder.addExpCardOrder",experiencecardOrder);
    }

    @Override
    public int addExpCardOrderDetail(Map map) {
        return super.insert("experiencecardOrder.addExpCardOrderDetail",map);
    }


    @Override
    public List<ExperiencecardOrder> selectExpCardOrder(Map map) {
        return super.selectList("experiencecardOrder.selectExpCardOrder",map);
    }

    @Override
    public List<ExperiencecardOrderDetail> selectExpCardOrderDetail(String cardOrderCode) {
        return super.selectList("experiencecardOrder.selectExpCardOrderDetail",cardOrderCode);
    }

    @Override
    public int updateExpCardOrderDetail(ExperiencecardOrderDetail experiencecardOrderDetail) {
        return super.update("experiencecardOrder.updateExpCardOrderDetail",experiencecardOrderDetail);
    }

    @Override
    public ExperiencecardOrderDetail selectExpCardOrderDetailById(Long cardOrderDetailId) {
        return super.select("experiencecardOrder.selectExpCardOrderDetailById",cardOrderDetailId);
    }

    @Override
    public int addUseRecord(ExperiencecardUseRecord experiencecardUseRecord) {
        return super.insert("experiencecardOrder.addUseRecord",experiencecardUseRecord);
    }

    @Override
    public int updateUseRecordStatus(ExperiencecardUseRecord experiencecardUseRecord) {
        return super.update("experiencecardOrder.updateUseRecordStatus",experiencecardUseRecord);
    }

    @Override
    public int updateUseRecord(ExperiencecardUseRecord experiencecardUseRecord) {
        return super.update("experiencecardOrder.updateUseRecord",experiencecardUseRecord);
    }

    @Override
    public List<ExperiencecardUseRecord> selectUseRecord(Long cardOrderDetailId) {
        return super.selectList("experiencecardOrder.selectUseRecord",cardOrderDetailId);

    }

    @Override
    public List<ExperiencecardUser> selectExperienceCardUserList() {
        return super.selectList("experiencecardUser.selectExpCardUserList");
    }

    @Override
    public List<ExperiencecardProductUser> selectExperienceCardProductUserList(Map map) {
        return super.selectList("experiencecardUser.selectExperienceCardProductUserList",map);
    }

    @Override
    public List<ExperiencecardProductUser> selectExpCardUserProduct(Map map) {
        return super.selectList("experiencecardUser.selectExpCardUserProduct",map);
    }

    @Override
    public int updateUserExpUseTimes(Map map) {
        return super.update("experiencecardUser.updateUserExpUseTimes",map);
    }

    @Override
    public int addUserExpUseTimes(Long id) {
        return super.update("experiencecardUser.addUserExpUseTimes",id);
    }

    @Override
    public int updateExpOrderMemoNum(Map map) {
        return super.update("experiencecardOrder.updateExpOrderMemoNum",map);
    }

    @Override
    public int checkOrderMemo(Map map) {
        return super.select("experiencecardOrder.checkOrderMemo",map);
    }


    @Override
    public List<ExperiencecardVO> selectExperienceCardVoUserList(Map map) {
        return super.selectList("experiencecardUser.selectExperienceCardVoUserList",map);
    }

    @Override
    public ExperiencecardProduct selectExperienceCardProductById(Map map) {
        return super.select("experiencecardProduct.selectExperienceCardProductById",map);
    }

    @Override
    public int updateExpCardProductBatch(Map map) {
        return super.update("experiencecardProduct.updateExpCardProductBatch",map);
    }

    @Override
    public int cancleExpOrder(Map map) {
        return super.update("experienceCard.cancleExpOrder",map);
    }

    @Override
    public int updateExpOrderStatus(Map map) {
        return super.update("experienceCard.updateExpOrderStatus",map);
    }

    @Override
    public int checkExpIsUse(Map map) {
        return super.select("experiencecardUser.checkExpIsUse",map);
    }

    @Override
    public int updateExpCarStockNumUp(String experiencecardNum) {
        return super.update("experienceCard.updateExpCarStockNumUp",experiencecardNum);
    }

    @Override
    public ExperiencecardProductUser selectExperienceCardProductUserById(ExperiencecardProductUser experiencecardProductUser) {
        return super.select("experiencecardUser.selectExperienceCardProductUserById",experiencecardProductUser);
    }

    @Override
    public int updateExperienceCardProductUserTotalTimesById(Map map) {
        return super.update("experiencecardUser.updateExperienceCardProductUserTotalTimesById",map);
    }

    @Override
    public int addExperiencecardProductUserRefuse(ExperiencecardProductUserRefuse experiencecardProductUserRefuse) {
        return super.insert("experiencecardProductUserRefuse.addExperiencecardProductUserRefuse",experiencecardProductUserRefuse);
    }

    @Override
    public List<ExperiencecardProductUserRefuse> selectExperiencecardProductUserRefuseList(Map map) {
        return super.selectList("experiencecardProductUserRefuse.selectExperiencecardProductUserRefuseList",map);
    }

    @Override
    public List<String> selectMemberNumberPayTypeAndAmount(Map map) {
        return super.selectList("experiencecardOrder.selectMemberNumberPayTypeAndAmount",map);
    }


    @Override
    public int addExpCardUser(ExperiencecardUser experiencecardUser) {
        return super.insert("experiencecardUser.addExpCardUser",experiencecardUser);
    }

    @Override
    public int addExpCardUserProduct(Map map) {
        return super.insert("experiencecardUser.addExpCardUserProduct",map);
    }

}
