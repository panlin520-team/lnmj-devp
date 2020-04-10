package com.lnmj.product.repository;

import com.lnmj.product.entity.*;

import java.util.List;
import java.util.Map;

public interface ICouponsDao {

    Coupons getCouponsById(Long couponsId);

    List<Coupons> couponsList(Map map);

    List<CouponsType> couponsTypeList();

    int addCoupons(Coupons coupons);

    int couponsDown(Coupons coupons);

    int updateCoupons(Coupons coupons);

    int updateEffectiveDate(Coupons coupons);

    int updateEffectiveDay(Coupons coupons);

    int addCouponsGetRecord(CouponsGetRecord couponsGetRecord);

    List<CouponsGetRecord> checkUserCoupons(Map map);

    List<CouponsGetRecord> selectCouponsGetRecordList(Map map);

    int deleteCouponsGetRecord(Long couponsGetRecordId);

    List<CouponsUseRecord> selectCouponsUseRecordList(Map map);

    int deleteCouponsUseRecord(Long couponsUseRecordId);

}