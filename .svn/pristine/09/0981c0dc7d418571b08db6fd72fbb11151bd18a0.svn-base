package com.lnmj.product.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.entity.Coupons;
import com.lnmj.product.entity.CouponsGetRecord;
import com.lnmj.product.entity.Unit;
import org.springframework.stereotype.Service;

/*
    单位service
 */
@Service("iUnitService")
public interface IUnitService {

    ResponseResult selectUnitList(int pageNum, int pageSize,String keyWord);

    ResponseResult selectUnitListNoPage();

    ResponseResult addUnit(Unit unit);

    ResponseResult editUnit(Unit unit);

    ResponseResult deleteUnit(String unitId, String modifyOperator);

    ResponseResult selectUnitGroup();

    ResponseResult selectFroundType();
}
