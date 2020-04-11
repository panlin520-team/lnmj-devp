package com.lnmj.product.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ProductTypeDistinguishEnum;
import com.lnmj.common.Enum.ProductTypeEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAccountEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeCouponsEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.baseController.ExportController;
import com.lnmj.common.baseController.HttpServletRequestWarpper;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.MemberUtil;
import com.lnmj.common.utils.NumberUtils;
import com.lnmj.product.business.*;
import com.lnmj.product.entity.*;
import com.lnmj.product.entity.VO.*;
import com.lnmj.product.repository.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;


/**
 * @Author: panlin
 * @Date: 2019/8/21 10:25
 * @Description: 优惠券
 */
@Transactional
@Service("couponsService")
public class CouponsService implements ICouponsService {

    @Resource
    private ICouponsDao iCouponsDao;


    @Override
    public ResponseResult couponsList(int pageNum, int pageSize,Long keyWordCouponsType,String keyWordCouponsName) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("keyWordCouponsType",keyWordCouponsType);
        map.put("keyWordCouponsName",keyWordCouponsName);
        map.put("soldUpDown",1);
        List<Coupons> couponsList = iCouponsDao.couponsList(map);
        PageInfo<Coupons> pageInfo = new PageInfo(couponsList);
        return ResponseResult.success(pageInfo);
    }

    @Override
    public ResponseResult couponsTypeList() {
       List<CouponsType> couponsTypeList = iCouponsDao.couponsTypeList();
        return ResponseResult.success(couponsTypeList);
    }

    @Override
    public ResponseResult addCoupons(Coupons coupons) {
        if (coupons.getChooseEffectiveType()==1){
            coupons.setEffectiveDay(null);
        }else if (coupons.getChooseEffectiveType()==2){
            coupons.setEffectiveDate(null);
        }
        iCouponsDao.addCoupons(coupons);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult couponsDown(Coupons coupons) {
        iCouponsDao.couponsDown(coupons);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateCoupons(Coupons coupons) {
        if (coupons.getChooseEffectiveType()==1){
            coupons.setEffectiveDay(null);
            iCouponsDao.updateEffectiveDay(coupons);
        }else if (coupons.getChooseEffectiveType()==2){
            coupons.setEffectiveDate(null);
            iCouponsDao.updateEffectiveDate(coupons);
        }
        iCouponsDao.updateCoupons(coupons);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult addCouponsGetRecord(CouponsGetRecord couponsGetRecord) {
        //查询此优惠券是否领取完
        Coupons couponsResult = iCouponsDao.getCouponsById(couponsGetRecord.getCouponsId());
        if (couponsResult.getBeGetAmount()==couponsResult.getProvideAmount()){
            return ResponseResult.error(new Error(ResponseCodeCouponsEnum.COUPONS_GET_OVER.getCode(), ResponseCodeCouponsEnum.COUPONS_GET_OVER.getDesc()));

        }
        //查看用户是否有优惠券
        Map map = new HashMap();
        map.put("memberNum",couponsGetRecord.getMemberNum());
        map.put("couponsId",couponsGetRecord.getCouponsId());
        List<CouponsGetRecord> couponsGetRecordResultList = iCouponsDao.checkUserCoupons(map);
        if (couponsGetRecordResultList.size()==0){
            //如果没有领取过，直接添加
            couponsGetRecord.setCouponsCode(NumberUtils.getRandomCouponsNo());
            couponsGetRecord.setOneLimit(couponsResult.getOneLimit());
            iCouponsDao.addCouponsGetRecord(couponsGetRecord);
        }else{
            //如果领取了
            if (couponsGetRecordResultList.size()==couponsGetRecordResultList.get(0).getOneLimit()){
                return ResponseResult.error(new Error(ResponseCodeCouponsEnum.ONELIMIT_CANNOT_GET.getCode(), ResponseCodeCouponsEnum.ONELIMIT_CANNOT_GET.getDesc()));
            }
            couponsGetRecord.setCouponsCode(NumberUtils.getRandomCouponsNo());
            couponsGetRecord.setOneLimit(couponsResult.getOneLimit());
            iCouponsDao.addCouponsGetRecord(couponsGetRecord);
        }
        //将此优惠券领取数量+1
        Coupons couponsUpdate = new Coupons();
        couponsUpdate.setCouponsId(couponsGetRecord.getCouponsId());
        couponsUpdate.setBeGetAmount(couponsResult.getBeGetAmount()+1);
        iCouponsDao.updateCoupons(couponsUpdate);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectCouponsGetRecordList(int pageNum, int pageSize, Long keyWordCouponsType, String keyWordCouponsName) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("keyWordCouponsType",keyWordCouponsType);
        map.put("keyWordCouponsName",keyWordCouponsName);
        List<CouponsGetRecord> couponsGetRecordList = iCouponsDao.selectCouponsGetRecordList(map);
        PageInfo<CouponsGetRecord> pageInfo = new PageInfo(couponsGetRecordList);
        return ResponseResult.success(pageInfo);
    }

    @Override
    public ResponseResult deleteCouponsGetRecord(Long couponsGetRecordId) {
        iCouponsDao.deleteCouponsGetRecord(couponsGetRecordId);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectCouponsUseRecordList(int pageNum, int pageSize, Long keyWordOrderNum, Long keyWordUserId,Long keyWordCouponsType) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("keyWordOrderNum",keyWordOrderNum);
        map.put("keyWordUserId",keyWordUserId);
        map.put("keyWordCouponsType",keyWordCouponsType);
        List<CouponsUseRecord> couponsUseRecordList = iCouponsDao.selectCouponsUseRecordList(map);
        PageInfo<CouponsUseRecord> pageInfo = new PageInfo(couponsUseRecordList);
        return ResponseResult.success(pageInfo);
    }

    @Override
    public ResponseResult deleteCouponsUseRecord(Long couponsUseRecordId) {
        iCouponsDao.deleteCouponsUseRecord(couponsUseRecordId);
        return ResponseResult.success();
    }
}
