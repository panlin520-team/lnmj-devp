package com.lnmj.data.repository;

import com.lnmj.data.entity.CommodityType;
import com.lnmj.data.entity.Subclass;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/4 15:30
 * @Description: 商品大类、商品小类
 */
public interface ICommodityTypeDao {
    List<CommodityType> selectCommodityTypeList(Map map);

    List<CommodityType> selectCommodityTypeByCondition(CommodityType commodityType);

    CommodityType selectCommodityTypeByID(Long commodityTypeID);

    int deleteCommodityTypeByID(CommodityType commodityType);

    int insertCommodityType(CommodityType commodityType);

    int updateCommodityType(CommodityType commodityType);

    //商品小类

    List<Subclass> selectSubclassList(Map map);

    List<Subclass> selectSubclassByCondition(Subclass subclass);

    Subclass selectSubclassByID(Long subclassID);


    int deleteSubclassByID(Subclass subclass);

    int insertSubclass(Subclass subclass);

    int updateSubclass(Subclass subclass);
}
