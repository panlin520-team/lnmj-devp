package com.lnmj.data.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.data.entity.CommodityType;
import com.lnmj.data.entity.Subclass;
import com.lnmj.data.repository.ICommodityTypeDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/4 15:34
 * @Description: 商品大类、商品小类
 */
@Repository
public class CommodityTypeDaoImpl extends BaseDao implements ICommodityTypeDao {

    @Override
    public List<CommodityType> selectCommodityTypeList(Map map) {
        return super.selectList("commodityType.selectCommodityTypeList",map);
    }

    @Override
    public List<CommodityType> selectCommodityTypeByCondition(CommodityType commodityType) {
        return super.selectList("commodityType.selectCommodityTypeByCondition",commodityType);
    }

    @Override
    public CommodityType selectCommodityTypeByID(Long commodityTypeID) {
        return super.select("commodityType.selectCommodityTypeByID",commodityTypeID);
    }

    @Override
    public int deleteCommodityTypeByID(CommodityType commodityType) {
        return super.update("commodityType.deleteCommodityTypeByID",commodityType);
    }

    @Override
    public int insertCommodityType(CommodityType commodityType) {
        return super.insert("commodityType.insertCommodityType",commodityType);
    }

    @Override
    public int updateCommodityType(CommodityType commodityType) {
        return super.update("commodityType.updateCommodityType",commodityType);
    }

    @Override
    public List<Subclass> selectSubclassList(Map map) {
        return super.selectList("subclass.selectSubclassList",map);
    }

    @Override
    public List<Subclass> selectSubclassByCondition(Subclass subclass) {
        return super.selectList("subclass.selectSubclassByCondition",subclass);
    }

    @Override
    public Subclass selectSubclassByID(Long subclassID) {
        return super.select("subclass.selectSubclassByID",subclassID);
    }


    @Override
    public int deleteSubclassByID(Subclass subclass) {
        return super.update("subclass.deleteSubclassByID",subclass);
    }

    @Override
    public int insertSubclass(Subclass subclass) {
        return super.insert("subclass.insertSubclass",subclass);
    }

    @Override
    public int updateSubclass(Subclass subclass) {
        return super.insert("subclass.updateSubclass",subclass);
    }


}
