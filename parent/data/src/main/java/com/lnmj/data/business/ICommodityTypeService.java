package com.lnmj.data.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.entity.CommodityType;
import com.lnmj.data.entity.Subclass;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/9/4 15:26
 * @Description: 商品大类、商品小类
 */
@Service("iCommodityTypeService")
public interface ICommodityTypeService {

    ResponseResult selectCommodityTypeList(int pageNum, int pageSize, Integer isDingzhi, Long commodityTypeIndustryID, String type,String searchWord);

    ResponseResult selectCommodityTypeListNoPage(Integer isDingzhi,Long commodityTypeIndustryID,String type);

    ResponseResult selectCommodityTypeByCondition(int pageNum, int pageSize, CommodityType commodityType);

    ResponseResult insertCommodityType(CommodityType commodityType);

    ResponseResult updateCommodityType(CommodityType commodityType);

    ResponseResult deleteCommodityTypeByID(Long commodityTypeId, String modifyOperator);

    ResponseResult selectSubclassList(int pageNum, int pageSize, String commodityTypeID,String searchWord);

    ResponseResult selectSubclassListAll();

    ResponseResult selectSubclassListNoPage(String commodityTypeID);

    ResponseResult selectSubclassByCondition(int pageNum, int pageSize, Subclass subclass,Long industryID,Long storeId);

    ResponseResult selectSubclassByConditionNoPage(Subclass subclass,Long industryID);

    ResponseResult deleteSubclass(Long subclassID, String modifyOperator);

    ResponseResult insertSubclass(Subclass subclass);

    ResponseResult updateSubclass(Subclass subclass);

    ResponseResult selectCommodityTypeById(Long commodityTypeID);
}
