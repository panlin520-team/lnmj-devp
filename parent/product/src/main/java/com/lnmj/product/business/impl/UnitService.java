package com.lnmj.product.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeStockEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.UnitEnum;
import com.lnmj.common.Enum.k3Enum.RoundingTypeEnum;
import com.lnmj.common.Enum.k3Enum.UnitGroupTypeEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.EnumUtil;
import com.lnmj.product.business.IUnitService;
import com.lnmj.product.entity.ServiceProduct;
import com.lnmj.product.entity.Unit;
import com.lnmj.product.entity.VO.ProductVO;
import com.lnmj.product.entity.VO.ServiceProductVO;
import com.lnmj.product.repository.IUnitDao;
import com.lnmj.product.serviceProxy.K3CLOUDApi;
import com.lnmj.product.serviceProxy.StoreApi;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: panlin
 * @Date: 2019/8/21 10:25
 * @Description: 优惠券
 */
@Transactional
@Service("unitService")
public class UnitService implements IUnitService {

    @Resource
    private IUnitDao unitDao;
    @Resource
    private K3CLOUDApi k3CLOUDApi;
    @Resource
    private StoreApi storeApi;

    @Override
    public ResponseResult selectUnitList(int pageNum, int pageSize,String keyWord) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("keyWord",keyWord);
        List<Unit> unitList = unitDao.selectUnitList(map);
        if (unitList != null && unitList.size() > 0) {
            PageInfo<Unit> pageInfo = new PageInfo<>(unitList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(UnitEnum.LIST_INFOMATION_ISNULL.getCode(),
                UnitEnum.LIST_INFOMATION_ISNULL.getDesc()));
    }

    @Override
    public ResponseResult selectUnitListNoPage() {
        List<Unit> unitList = unitDao.selectUnitList(new HashMap());
        if (unitList != null && unitList.size() > 0) {
            return ResponseResult.success(unitList);
        }
        return ResponseResult.error(new Error(UnitEnum.LIST_INFOMATION_ISNULL.getCode(),
                UnitEnum.LIST_INFOMATION_ISNULL.getDesc()));
    }

    @Override
    public ResponseResult addUnit(Unit unit) {
        int result = unitDao.addUnit(unit);
        if (result > 0) {
            //k3操作
            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");
            String unitName = unit.getUnitName();
            String unitGroupId = unit.getUnitGroupId();
            String s = unitGroupId.toString();
            ResponseResult saveUnit = k3CLOUDApi.saveUnit(dataCentre, userName, password, unitName, s, unit.getFroundtypeId(), null, true);

            HashMap<String, Object> resultHashMap;
            HashMap<String, Object> resulut;
            HashMap<String, Object> resultStatus;
            Boolean isSuccess;
            String id = null;
            String number;
            Long temp = unit.getUnitId();

            //登录成功----》进行判断反交审核是否成功
            if (saveUnit.isSuccess()) {
                resultHashMap = (HashMap<String, Object>) saveUnit.getResult();
                resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                isSuccess = (Boolean) resultStatus.get("IsSuccess");
                if (isSuccess) {
                    //获取保存成功后的number和id
                    id = resulut.get("Id").toString();
                    number = (String) resulut.get("Number");
                    //成功后将k3的id和number保存在数据库
                    unit = new Unit();
                    unit.setK3Id(id);
                    unit.setK3Number(number);
                    unit.setUnitId(temp);
                    unitDao.editUnit(unit);
                    return ResponseResult.success("单位添加成功，k3数据插入成功");
                }
            }
        }
        return ResponseResult.success("单位添加成功，k3数据未生成");
    }

    @Override
    public ResponseResult editUnit(Unit unit) {
        int result = unitDao.editUnit(unit);
        if (result > 0) {
            String temp = String.valueOf(unit.getUnitId());
            HashMap<Object, Object> map = new HashMap<>();
            map.put("unitId", temp);
            unit = unitDao.selectUnit(map);
            String k3Id = unit.getK3Id();
            String k3Number = unit.getK3Number();
            HashMap<String, Object> resultHashMap;
            HashMap<String, Object> resulut;
            HashMap<String, Object> resultStatus;
            Boolean isSuccess;


            //k3操作
            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");
            String unitName = unit.getUnitName();
            ResponseResult unAuditUnit = k3CLOUDApi.unAuditUnit(dataCentre, userName, password, k3Number, k3Id);
            resultHashMap = (HashMap<String, Object>) unAuditUnit.getResult();
            resulut = (HashMap<String, Object>) resultHashMap.get("Result");
            resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
            isSuccess = (Boolean) resultStatus.get("IsSuccess");
            if (isSuccess) {
                ResponseResult saveUnit = k3CLOUDApi.saveUnit(dataCentre, userName, password, unitName, null, null, k3Id, true);
                if (saveUnit.isSuccess()) {
                    resultHashMap = (HashMap<String, Object>) saveUnit.getResult();
                    resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                    resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                    isSuccess = (Boolean) resultStatus.get("IsSuccess");
                    if (isSuccess) {
                        return ResponseResult.success();
                    }
                }
            }
        }
        return ResponseResult.error(new Error(UnitEnum.UPDATE_UNIT_FAIL.getCode(),
                UnitEnum.UPDATE_UNIT_FAIL.getDesc()));
    }

    @Override
    public ResponseResult deleteUnit(String unitId, String modifyOperator) {
        Map map = new HashMap<>();
        map.put("unitId", unitId);
        map.put("modifyOperator", modifyOperator);
        int result = unitDao.deleteUnit(map);
        if (result > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(UnitEnum.DELETE_UNIT_FAIL.getCode(),
                UnitEnum.DELETE_UNIT_FAIL.getDesc()));
    }

    @Override
    public ResponseResult selectUnitGroup() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put(UnitGroupTypeEnum.CAPACITY, UnitGroupTypeEnum.CAPACITY.getDesc());
        map.put(UnitGroupTypeEnum.TIME, UnitGroupTypeEnum.TIME.getDesc());
        map.put(UnitGroupTypeEnum.QUANTITY, UnitGroupTypeEnum.QUANTITY.getDesc());
        map.put(UnitGroupTypeEnum.LENGTH, UnitGroupTypeEnum.LENGTH.getDesc());
        map.put(UnitGroupTypeEnum.WEIGHT, UnitGroupTypeEnum.WEIGHT.getDesc());
        map.put(UnitGroupTypeEnum.ELECTRONIC, UnitGroupTypeEnum.ELECTRONIC.getDesc());
        map.put(UnitGroupTypeEnum.BUILDING, UnitGroupTypeEnum.BUILDING.getDesc());
        map.put(UnitGroupTypeEnum.MACHINE, UnitGroupTypeEnum.MACHINE.getDesc());
        map.put(UnitGroupTypeEnum.TRANSPORT, UnitGroupTypeEnum.TRANSPORT.getDesc());
        return ResponseResult.success(map);
    }

    @Override
    public ResponseResult selectFroundType() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put(RoundingTypeEnum.ROUNDING.getCode(), RoundingTypeEnum.ROUNDING.getDesc());
        map.put(RoundingTypeEnum.ROUNDING_OFF.getCode(), RoundingTypeEnum.ROUNDING_OFF.getDesc());
        map.put(RoundingTypeEnum.ROUNDING_CARRY.getCode(), RoundingTypeEnum.ROUNDING_CARRY.getDesc());
        return ResponseResult.success(map);
    }

    //获取数据中心、用户名和密码
    public HashMap<String, String> getUserNameAndPassword(Long companyId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("dataCentre", null);
        map.put("userName", null);
        map.put("password", null);
        ResponseResult responseResult = storeApi.selectCompanyByID(1L);
        if (responseResult.isSuccess()) {
            HashMap<String, Object> result = (HashMap<String, Object>) responseResult.getResult();
            String dataCentre = String.valueOf(result.get("dataCentre"));
            String dataCentreUserName = String.valueOf(result.get("yewuDataCentreUserName"));
            String dataCentrePassword = String.valueOf(result.get("yewuDataCentrePassword"));
            map.put("dataCentre", dataCentre);
            map.put("userName", dataCentreUserName);
            map.put("password", dataCentrePassword);
        }
        return map;
    }
}
