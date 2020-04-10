package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.Coupons;
import com.lnmj.product.entity.CouponsGetRecord;
import com.lnmj.product.entity.CouponsType;
import com.lnmj.product.entity.CouponsUseRecord;
import com.lnmj.product.repository.ICouponsDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CouponsDao extends BaseDao implements ICouponsDao {
    @Override
    public Coupons getCouponsById(Long couponsId) {
        return super.select("coupons.getCouponsById", couponsId);
    }

    @Override
    public List<Coupons> couponsList(Map map){
        return super.selectList("coupons.couponsList", map);
    }

    @Override
    public List<CouponsType> couponsTypeList() {
        return super.selectList("coupons.couponsTypeList");
    }

    @Override
    public int addCoupons(Coupons coupons) {
        return super.insert("coupons.addCoupons",coupons);
    }

    @Override
    public int couponsDown(Coupons coupons) {
        return super.update("coupons.updateCoupons",coupons);
    }

    @Override
    public int updateCoupons(Coupons coupons) {
        return super.update("coupons.updateCoupons",coupons);
    }

    @Override
    public int updateEffectiveDate(Coupons coupons) {
        return super.update("coupons.updateEffectiveDate",coupons);
    }

    @Override
    public int updateEffectiveDay(Coupons coupons) {
        return super.update("coupons.updateEffectiveDay",coupons);
    }

    @Override
    public int addCouponsGetRecord(CouponsGetRecord couponsGetRecord) {
        return super.insert("coupons.addCouponsGetRecord",couponsGetRecord);
    }

    @Override
    public List<CouponsGetRecord> checkUserCoupons(Map map) {
        return super.selectList("coupons.checkUserCoupons", map);
    }


    @Override
    public List<CouponsGetRecord> selectCouponsGetRecordList(Map map) {
        return super.selectList("coupons.selectCouponsGetRecordList", map);
    }

    @Override
    public int deleteCouponsGetRecord(Long couponsGetRecordId) {
        return super.delete("coupons.deleteCouponsGetRecord", couponsGetRecordId);
    }

    @Override
    public List<CouponsUseRecord> selectCouponsUseRecordList(Map map) {
        return super.selectList("coupons.selectCouponsUseRecordList", map);
    }

    @Override
    public int deleteCouponsUseRecord(Long couponsUseRecordId) {
        return super.delete("coupons.deleteCouponsUseRecord", couponsUseRecordId);
    }


}
