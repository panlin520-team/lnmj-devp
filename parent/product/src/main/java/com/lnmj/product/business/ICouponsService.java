package com.lnmj.product.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.entity.Coupons;
import com.lnmj.product.entity.CouponsGetRecord;
import com.lnmj.product.entity.VO.ProductTypeVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: panlin
 * @Date: 2019/8/21 10:25
 * @Description: 优惠券
 */
@Service("iCouponsService")
public interface ICouponsService {
    //优惠券
    ResponseResult couponsList(int pageNum, int pageSize,Long keyWordCouponsType,String keyWordCouponsName);
    ResponseResult couponsTypeList();
    ResponseResult addCoupons(Coupons coupons);
    ResponseResult couponsDown(Coupons coupons);
    ResponseResult updateCoupons(Coupons coupons);
    //优惠券领取记录
    ResponseResult addCouponsGetRecord(CouponsGetRecord couponsGetRecord);
    ResponseResult selectCouponsGetRecordList(int pageNum,int pageSize,Long keyWordCouponsType,String keyWordCouponsName);
    ResponseResult deleteCouponsGetRecord(Long couponsGetRecordId);
    //优惠券使用记录
    ResponseResult selectCouponsUseRecordList(int pageNum,int pageSize,Long keyWordOrderNum,Long keyWordUserId,Long keyWordCouponsType);
    ResponseResult deleteCouponsUseRecord(Long couponsUseRecordId);
}
