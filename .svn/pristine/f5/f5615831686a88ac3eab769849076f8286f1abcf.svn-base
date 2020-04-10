package com.lnmj.data.business.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeCommodityTypeEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.business.ICommodityTypeService;
import com.lnmj.data.entity.CommodityType;
import com.lnmj.data.entity.Evaluating;
import com.lnmj.data.entity.Subclass;
import com.lnmj.data.entity.VO.PostCategoryVO;
import com.lnmj.data.repository.ICommodityTypeDao;
import com.lnmj.data.repository.IEvaluatingDao;
import com.lnmj.data.serviceProxy.StoreApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/9/4 15:27
 * @Description: 商品大类、商品小类
 */
@Service("commodityTypeService")
public class CommodityTypeService implements ICommodityTypeService {
    @Resource
    private ICommodityTypeDao commodityTypeDao;
    @Resource
    private StoreApi storeApi;
    @Resource
    private IEvaluatingDao iEvaluatingDao;

    @Override
    public ResponseResult selectCommodityTypeList(int pageNum, int pageSize, Integer isDingzhi, Long commodityTypeIndustryID, String type, String searchWord) {
        PageHelper.startPage(pageNum, pageSize);
        Map map1 = new HashMap();
        map1.put("commodityTypeIndustryID", commodityTypeIndustryID);
        map1.put("searchWord", searchWord);
        if (null != type && type != "") {
            if (type.equals("1")) {
                map1.put("commodityProductType", "1");
            } else {
                map1.put("commodityProductType", "2");
            }
        }
        List<CommodityType> commodityTypeListResult = commodityTypeDao.selectCommodityTypeList(map1);
        List<CommodityType> commodityTypeList = new ArrayList<>();
        if (isDingzhi != null) {
            if (isDingzhi == 1) {
                for (CommodityType commodityType : commodityTypeListResult) {
                    /*  if (commodityType.getCommodityTypeName().equals("商品") || commodityType.getCommodityTypeName().equals("项目")) {*/
                    commodityTypeList.add(commodityType);
                    /* }*/
                }
            } else {
                commodityTypeList.addAll(commodityTypeListResult);
            }
            if (commodityTypeList != null && commodityTypeList.size() > 0) {
                PageInfo<CommodityType> pageInfo = new PageInfo<>(commodityTypeList);
                return ResponseResult.success(pageInfo);
            }
        } else {
            if (commodityTypeListResult != null && commodityTypeListResult.size() > 0) {
                PageInfo<CommodityType> pageInfo = new PageInfo<>(commodityTypeListResult);
                return ResponseResult.success(pageInfo);
            }
        }
        return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.COMMODITY_TYPE_NULL.getCode(),
                ResponseCodeCommodityTypeEnum.COMMODITY_TYPE_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectCommodityTypeListNoPage(Integer isDingzhi, Long commodityTypeIndustryID, String type) {
        Map map1 = new HashMap();
        map1.put("commodityTypeIndustryID", commodityTypeIndustryID);
        if (null != type && type != "") {
            if (type.equals("1")) {
                map1.put("commodityProductType", "1");
            } else {
                map1.put("commodityProductType", "2");
            }
        }
        List<CommodityType> commodityTypeListResult = commodityTypeDao.selectCommodityTypeList(map1);
        List<CommodityType> commodityTypeList = new ArrayList<>();
        if (isDingzhi != null) {
            if (isDingzhi == 1) {
                for (CommodityType commodityType : commodityTypeListResult) {
                    /*  if (commodityType.getCommodityTypeName().equals("商品") || commodityType.getCommodityTypeName().equals("项目")) {*/
                    commodityTypeList.add(commodityType);
                    /* }*/
                }
            } else {
                commodityTypeList.addAll(commodityTypeListResult);
            }
            if (commodityTypeList != null && commodityTypeList.size() > 0) {
                PageInfo<CommodityType> pageInfo = new PageInfo<>(commodityTypeList);
                return ResponseResult.success(pageInfo);
            }
        } else {
            if (commodityTypeListResult != null && commodityTypeListResult.size() > 0) {
                return ResponseResult.success(commodityTypeListResult);
            }
        }
        return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.COMMODITY_TYPE_NULL.getCode(),
                ResponseCodeCommodityTypeEnum.COMMODITY_TYPE_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectCommodityTypeByCondition(int pageNum, int pageSize, CommodityType commodityType) {
        PageHelper.startPage(pageNum, pageSize);
        List<CommodityType> commodityTypeList = commodityTypeDao.selectCommodityTypeByCondition(commodityType);
        List<Map> industryList = (List<Map>) storeApi.selectListIndustryNoPage().getResult();
        for (CommodityType type : commodityTypeList) {
            if (industryList != null) {
                for (Map map : industryList) {
                    if (type.getCommodityTypeIndustryID() != null && type.getCommodityTypeIndustryID().toString().equals(map.get("industryID").toString())) {
                        type.setCommodityTypeIndustryName(map.get("industryName").toString());
                    }
                }
            }
        }


        if (commodityTypeList != null && commodityTypeList.size() > 0) {
            PageInfo<CommodityType> pageInfo = new PageInfo<>(commodityTypeList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.COMMODITY_TYPE_NULL.getCode(),
                ResponseCodeCommodityTypeEnum.COMMODITY_TYPE_NULL.getDesc()));
    }

    @Override
    public ResponseResult insertCommodityType(CommodityType commodityType) {
        commodityTypeDao.insertCommodityType(commodityType);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateCommodityType(CommodityType commodityType) {
        //是否存在
        CommodityType commodityTypeByID = commodityTypeDao.selectCommodityTypeByID(commodityType.getCommodityTypeID());
        if (commodityTypeByID == null) {
            return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.NOT_FIND_COMMODITY_TYPE.getCode(),
                    ResponseCodeCommodityTypeEnum.NOT_FIND_COMMODITY_TYPE.getDesc()));
        }
        commodityTypeDao.updateCommodityType(commodityType);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteCommodityTypeByID(Long commodityTypeId, String modifyOperator) {
        //是否存在
        CommodityType commodityTypeByID = commodityTypeDao.selectCommodityTypeByID(commodityTypeId);
        if (commodityTypeByID == null) {
            return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.NOT_FIND_COMMODITY_TYPE.getCode(),
                    ResponseCodeCommodityTypeEnum.NOT_FIND_COMMODITY_TYPE.getDesc()));
        }
        CommodityType commodityType = new CommodityType();
        commodityType.setCommodityTypeID(commodityTypeId);
        commodityType.setModifyOperator(modifyOperator);
        commodityTypeDao.deleteCommodityTypeByID(commodityType);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectSubclassList(int pageNum, int pageSize, String commodityTypeID, String searchWord) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap<>();
        map.put("commodityTypeID", commodityTypeID);
        map.put("searchWord", searchWord);
        List<Subclass> subclassList = commodityTypeDao.selectSubclassList(map);
        if (subclassList != null && subclassList.size() > 0) {
            //解析postCategoryIds
            for (Subclass subclass : subclassList) {
                String postCategoryIds = subclass.getPostCategoryIds();
                if (postCategoryIds != null) {
                    JSONArray array = JSONArray.parseArray(postCategoryIds);
                    List<PostCategoryVO> postCategoryVOList = array.toJavaList(PostCategoryVO.class);
                    subclass.setPostCategoryVOList(postCategoryVOList);
                }
            }
            PageInfo<Subclass> pageInfo = new PageInfo<>(subclassList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.SUBCLASS_NULL.getCode(),
                ResponseCodeCommodityTypeEnum.SUBCLASS_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectSubclassListAll() {
        Map map = new HashMap<>();
        List<Subclass> subclassList = commodityTypeDao.selectSubclassList(map);
        if (subclassList != null && subclassList.size() > 0) {
            //解析postCategoryIds
            for (Subclass subclass : subclassList) {
                String postCategoryIds = subclass.getPostCategoryIds();
                if (postCategoryIds != null) {
                    JSONArray array = JSONArray.parseArray(postCategoryIds);
                    List<PostCategoryVO> postCategoryVOList = array.toJavaList(PostCategoryVO.class);
                    subclass.setPostCategoryVOList(postCategoryVOList);
                }
            }
            return ResponseResult.success(subclassList);
        }
        return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.SUBCLASS_NULL.getCode(),
                ResponseCodeCommodityTypeEnum.SUBCLASS_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectSubclassListNoPage(String commodityTypeID) {
        Map map = new HashMap<>();
        map.put("commodityTypeID", commodityTypeID);
        List<Subclass> subclassList = commodityTypeDao.selectSubclassList(map);
        if (subclassList != null && subclassList.size() > 0) {
            //解析postCategoryIds
            for (Subclass subclass : subclassList) {
                String postCategoryIds = subclass.getPostCategoryIds();
                if (postCategoryIds != null) {
                    JSONArray array = JSONArray.parseArray(postCategoryIds);
                    List<PostCategoryVO> postCategoryVOList = array.toJavaList(PostCategoryVO.class);
                    subclass.setPostCategoryVOList(postCategoryVOList);
                }
            }
            return ResponseResult.success(subclassList);
        }
        return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.SUBCLASS_NULL.getCode(),
                ResponseCodeCommodityTypeEnum.SUBCLASS_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectSubclassByCondition(int pageNum, int pageSize, Subclass subclass, Long industryID, Long storeId) {
        PageHelper.startPage(pageNum, pageSize);
        if (industryID != null) {
            //根据行业找项目大类
            CommodityType commodityType = new CommodityType();
            commodityType.setCommodityTypeIndustryID(industryID);
            List<CommodityType> commodityTypeList = commodityTypeDao.selectCommodityTypeByCondition(commodityType);
            Long commodityTypeID = null;
            for (CommodityType commodityTypeItem : commodityTypeList) {
                if (commodityTypeItem.getCommodityTypeName().equals("项目")) {
                    commodityTypeID = commodityTypeItem.getCommodityTypeID();
                }
            }
            subclass.setCommodityTypeID(commodityTypeID);
        }
        List<Subclass> subclassList = commodityTypeDao.selectSubclassByCondition(subclass);
        //查询所有的职位
        List<Map> postMap = (List<Map>) storeApi.selectPostNoPage().getResult();
        //查询所有的美容师
        List<Map> beauticianMap = (List<Map>) storeApi.selectBeauticianListNoPage().getResult();
        //查询所有的职位等级
        List<Map> postLevel = (List<Map>) storeApi.selectPostLevelNoPage().getResult();
        if (subclassList != null && subclassList.size() > 0) {
            //解析postCategoryIds
            for (Subclass temp : subclassList) {
                if (temp.getPostCategoryIds() != null) {
                    String postCategoryIds = temp.getPostCategoryIds();
                    JSONArray array = JSONArray.parseArray(postCategoryIds);
                    List<PostCategoryVO> postCategoryVOList = array.toJavaList(PostCategoryVO.class);
                    for (PostCategoryVO postCategoryVO : postCategoryVOList) {
                        List<Map> mapAddListBea = new ArrayList<>();
                        List<Map> mapAddListPost = new ArrayList<>();
                        if (beauticianMap != null) {
                            for (Map map : beauticianMap) {
                                if (storeId != null && map.get("companyId").toString().equals(storeId.toString()) && map.get("companyType").toString().equals("3") &&
                                        (map.get("postCategoryId").toString().equals(postCategoryVO.getPostCategoryId())) || (map.get("partTimePostCategoryId").toString().equals(postCategoryVO.getPostCategoryId()))) {
                                    Map mapAdd = new HashMap();
                                    mapAdd.put("beauticianId", map.get("beauticianId"));
                                    mapAdd.put("name", map.get("name"));
                                    mapAdd.put("postId", map.get("postId"));
                                    mapAdd.put("postCategoryId", map.get("postCategoryId"));
                                    mapAdd.put("staffNumber", map.get("staffNumber"));
                                    if (postMap != null) {
                                        for (Map map1 : postMap) {
                                            if (map1.get("postId").toString().equals(map.get("postId").toString())) {
                                                mapAdd.put("postLevel", map1.get("postLevel"));
                                            }
                                        }
                                    }
                                    if (postLevel != null) {
                                        for (Map map1 : postLevel) {
                                            if (mapAdd.get("postLevel") != null && map1.get("postLevelId").toString().equals(mapAdd.get("postLevel").toString())) {
                                                mapAdd.put("postLevelName", map1.get("postLevelName"));
                                            }
                                        }
                                    }
                                    mapAddListBea.add(mapAdd);
                                }
                            }
                            postCategoryVO.setBeauticianList(mapAddListBea);
                        }
                        if (postMap != null) {
                            for (Map map : postMap) {
                                if (map.get("postCategoryId").toString().equals(postCategoryVO.getPostCategoryId())) {
                                    Map mapAdd = new HashMap();
                                    mapAdd.put("postId", map.get("postId"));
                                    mapAdd.put("name", map.get("name"));
                                    mapAdd.put("postCategoryId", map.get("postCategoryId"));
                                    mapAdd.put("postLevel", map.get("postLevel"));
                                    mapAddListPost.add(mapAdd);
                                }
                            }
                        }
                        postCategoryVO.setPostList(mapAddListPost);
                    }

                    if (postLevel != null) {
                        for (PostCategoryVO postCategoryVO : postCategoryVOList) {
                            List<Map> mapList = postCategoryVO.getPostList();
                            for (Map map : mapList) {
                                for (Map map1 : postLevel) {
                                    if (map.get("postLevel") != null && map1.get("postLevelId").toString().equals(map.get("postLevel").toString())) {
                                        map.put("postLevelName", map1.get("postLevelName"));
                                    }
                                }
                            }
                        }
                    }

                    temp.setPostCategoryVOList(postCategoryVOList);

                }
            }

            List<Evaluating> evaluatingList = iEvaluatingDao.selectEvaluatingList(new HashMap());
            List<CommodityType> commodityTypeList = commodityTypeDao.selectCommodityTypeList(new HashMap());
            for (Subclass subclass1 : subclassList) {
                for (Evaluating evaluating : evaluatingList) {
                    if (subclass1.getSubclassEvaluatingID() != null && subclass1.getSubclassEvaluatingID().toString().equals(evaluating.getEvaluatingID().toString())) {
                        subclass1.setSubclassEvaluatingName(evaluating.getEvaluatingName());
                    }

                }
                for (CommodityType commodityType : commodityTypeList) {
                    if (subclass1.getCommodityTypeID() != null && subclass1.getCommodityTypeID().toString().equals(commodityType.getCommodityTypeID().toString())) {
                        subclass1.setCommodityTypeName(commodityType.getCommodityTypeName());
                    }

                }
            }

            PageInfo<Subclass> pageInfo = new PageInfo<>(subclassList);
            return ResponseResult.success(pageInfo);
        }


        return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.SUBCLASS_NULL.getCode(),
                ResponseCodeCommodityTypeEnum.SUBCLASS_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectSubclassByConditionNoPage(Subclass subclass, Long industryID) {
        if (industryID != null) {
            //根据行业找项目大类
            CommodityType commodityType = new CommodityType();
            commodityType.setCommodityTypeIndustryID(industryID);
            List<CommodityType> commodityTypeList = commodityTypeDao.selectCommodityTypeByCondition(commodityType);
            Long commodityTypeID = null;
            for (CommodityType commodityTypeItem : commodityTypeList) {
                if (commodityTypeItem.getCommodityTypeName().equals("项目")) {
                    commodityTypeID = commodityTypeItem.getCommodityTypeID();
                }
                ;
            }
            subclass.setCommodityTypeID(commodityTypeID);
        }
        List<Subclass> subclassList = commodityTypeDao.selectSubclassByCondition(subclass);
        if (subclassList != null && subclassList.size() > 0) {
            return ResponseResult.success(subclassList);
        }
        return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.SUBCLASS_NULL.getCode(),
                ResponseCodeCommodityTypeEnum.SUBCLASS_NULL.getDesc()));
    }

    @Override
    public ResponseResult deleteSubclass(Long subclassID, String modifyOperator) {
        //是否存在
        Subclass subclassByID = commodityTypeDao.selectSubclassByID(subclassID);
        if (subclassByID == null) {
            return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.NOT_FIND_SUBCLASS.getCode(),
                    ResponseCodeCommodityTypeEnum.NOT_FIND_SUBCLASS.getDesc()));
        }
        Subclass subclass = new Subclass();
        subclass.setSubclassID(subclassID);
        subclass.setModifyOperator(modifyOperator);
        commodityTypeDao.deleteSubclassByID(subclass);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult insertSubclass(Subclass subclass) {
        commodityTypeDao.insertSubclass(subclass);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateSubclass(Subclass subclass) {
        //是否存在
        Subclass subclassByID = commodityTypeDao.selectSubclassByID(subclass.getSubclassID());
        if (subclassByID == null) {
            return ResponseResult.error(new Error(ResponseCodeCommodityTypeEnum.NOT_FIND_SUBCLASS.getCode(),
                    ResponseCodeCommodityTypeEnum.NOT_FIND_SUBCLASS.getDesc()));
        }
        commodityTypeDao.updateSubclass(subclass);
        return ResponseResult.success();
    }
}
