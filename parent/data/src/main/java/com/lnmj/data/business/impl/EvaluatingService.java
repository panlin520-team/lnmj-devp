package com.lnmj.data.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.OrderTypeEnum;
import com.lnmj.common.Enum.ProductTypeEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeEvaluatingEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.MemberUtil;
import com.lnmj.data.business.IEvaluatingService;
import com.lnmj.data.entity.Evaluating;
import com.lnmj.data.entity.EvaluatingDetailed;
import com.lnmj.data.entity.EvaluatingLevel;
import com.lnmj.data.entity.Subclass;
import com.lnmj.data.repository.ICommodityTypeDao;
import com.lnmj.data.repository.IEvaluatingDao;
import com.lnmj.data.serviceProxy.StoreApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/10/12 16:32
 * @Description: 客户评测
 */
@Service("evaluatingService")
public class EvaluatingService implements IEvaluatingService {
    @Resource
    private IEvaluatingDao evaluatingDao;
    @Resource
    private ICommodityTypeDao iCommodityTypeDao;
    @Resource
    private StoreApi storeApi;

    @Override
    public ResponseResult updateEvaluatingByID(Evaluating evaluating) {
        HashMap<String, Object> result = new HashMap<>();
        //是否存在
        Evaluating evaluatingTemp = evaluatingDao.selectEvaluatingByID(evaluating.getEvaluatingID());
        if (evaluatingTemp == null) {
            return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.EVALUATING_NULL.getCode(),
                    ResponseCodeEvaluatingEnum.EVALUATING_NULL.getDesc()));
        }
        //更新或新建等级
        List<EvaluatingLevel> evaluatingLevelList = evaluating.getEvaluatingLevelList();
        List<EvaluatingLevel> evaluatingLevelListUpdate = new ArrayList<>();
        List<EvaluatingLevel> evaluatingLevelListInsert = new ArrayList<>();
        if (evaluatingLevelList != null && evaluatingLevelList.size() > 0) {
            for (EvaluatingLevel level : evaluatingLevelList) {
                if (level.getEvaluatingLevelID() == null) {
                    evaluatingLevelListInsert.add(level);
                } else {
                    evaluatingLevelListUpdate.add(level);
                }
            }
            if (evaluatingLevelListInsert != null && evaluatingLevelListInsert.size() > 0) {
                List<Long> insertList = evaluatingDao.insertEvaluatingLevelList(evaluatingLevelListInsert);
                if (insertList != null && insertList.size() > 0) {
                    result.put("客户评测级别插入成功", evaluatingLevelListInsert);
                }
            }
            if (evaluatingLevelListUpdate != null && evaluatingLevelListUpdate.size() > 0) {
                List<Long> updateList = evaluatingDao.updateEvaluatingLevelList(evaluatingLevelListUpdate);
                if (updateList != null && updateList.size() > 0) {
                    result.put("客户评测级别更新成功", evaluatingLevelListUpdate);
                }
            }
        }
        int i = evaluatingDao.updateEvaluatingByID(evaluating);
        if (i > 0) {
            result.put("客户评测更新成功", evaluating);
            return ResponseResult.success(result);
        }
        return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.UPDATE_FAILED.getCode(),
                ResponseCodeEvaluatingEnum.UPDATE_FAILED.getDesc()));
    }

    @Override
    public ResponseResult insertEvaluating(Evaluating evaluating) {
        if (OrderTypeEnum.RECHARGE_ORDER.getCode().equals(evaluating.getEvaluatingType())) {
            //如果为充值测评 判断是否已经有
            HashMap<String, Object> selectMap = new HashMap<>();
            selectMap.put("evaluatingType", OrderTypeEnum.RECHARGE_ORDER.getCode());
            selectMap.put("evaluatingIndustryID", evaluating.getEvaluatingIndustryID());
            Evaluating evaluatingResult = evaluatingDao.selectEvaluatingByType(selectMap);
            if (evaluatingResult != null) {
                return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.RECHARGE_TYPE_EVALUATING_EXIST.getCode(),
                        ResponseCodeEvaluatingEnum.RECHARGE_TYPE_EVALUATING_EXIST.getDesc()));
            }
        }
        int i = evaluatingDao.insertEvaluating(evaluating);
        Long evaluatingID = evaluating.getEvaluatingID();
        List<EvaluatingLevel> evaluatingLevelList = evaluating.getEvaluatingLevelList();
        if (evaluatingLevelList != null && evaluatingLevelList.size() > 0) {
            for (EvaluatingLevel evaluatingLevel : evaluatingLevelList) {
                evaluatingLevel.setEvaluatingLevelEvaluatingID(evaluatingID);
            }
            evaluatingDao.insertEvaluatingLevelList(evaluatingLevelList);
        }
        if (i > 0) {
            return ResponseResult.success(evaluating);
        }
        return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.INSERT_FAILED.getCode(),
                ResponseCodeEvaluatingEnum.INSERT_FAILED.getDesc()));
    }

    @Override
    public ResponseResult deleteEvaluatingByID(Long evaluatingID, String modifyOperator) {
        //是否存在
        Evaluating evaluatingTemp = evaluatingDao.selectEvaluatingByID(evaluatingID);
        if (evaluatingTemp == null) {
            return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.EVALUATING_NULL.getCode(),
                    ResponseCodeEvaluatingEnum.EVALUATING_NULL.getDesc()));
        }
        //删除客户评测等级
        EvaluatingLevel evaluatingLevel = new EvaluatingLevel();
        evaluatingLevel.setEvaluatingLevelEvaluatingID(evaluatingID);
        evaluatingLevel.setModifyOperator(modifyOperator);
        evaluatingDao.deleteEvaluatingLevelByEvaluatingID(evaluatingLevel);
        //删除客户评测
        Evaluating evaluating = new Evaluating();
        evaluating.setEvaluatingID(evaluatingID);
        evaluating.setModifyOperator(modifyOperator);
        int i = evaluatingDao.deleteEvaluatingByID(evaluating);
        if (i > 0) {
            return ResponseResult.success(evaluatingID);
        }
        return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.DELETE_FAILED.getCode(),
                ResponseCodeEvaluatingEnum.DELETE_FAILED.getDesc()));
    }

    @Override
    public ResponseResult selectEvaluatingByID(Long evaluatingID) {
        Evaluating evaluating = evaluatingDao.selectEvaluatingByID(evaluatingID);
        if (evaluating == null) {
            return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.EVALUATING_NULL.getCode(),
                    ResponseCodeEvaluatingEnum.EVALUATING_NULL.getDesc()));
        }
        //所有行业
        ResponseResult responseResult = storeApi.selectListIndustryNoPage();
        List<HashMap<String, Object>> result = (List<HashMap<String, Object>>) responseResult.getResult();
        evaluating.setIndustryName("");
        Long industryID = evaluating.getEvaluatingIndustryID();
        if (industryID != null) {
            for (HashMap<String, Object> map : result) {
                Integer industryID1 = (Integer) map.get("industryID");
                if (industryID1.equals(industryID.intValue())) {
                    evaluating.setIndustryName((String) map.get("industryName"));
                }
            }
        }
        return ResponseResult.success(evaluating);
    }

    @Override
    public ResponseResult selectEvaluatingByCondition(int pageNum, int pageSize, Evaluating evaluating) {
        PageHelper.startPage(pageNum, pageSize);
        List<Evaluating> evaluatingList = evaluatingDao.selectEvaluatingByCondition(evaluating);
        if (evaluatingList != null && evaluatingList.size() > 0) {
            //所有行业
            ResponseResult responseResult = storeApi.selectListIndustryNoPage();
            List<HashMap<String, Object>> result = (List<HashMap<String, Object>>) responseResult.getResult();
            for (Evaluating evaluating1 : evaluatingList) {
                evaluating1.setIndustryName("");
                Long industryID = evaluating1.getEvaluatingIndustryID();
                if (industryID != null) {
                    for (HashMap<String, Object> map : result) {
                        Integer industryID1 = (Integer) map.get("industryID");
                        if (industryID1.equals(industryID.intValue())) {
                            evaluating1.setIndustryName((String) map.get("industryName"));
                        }
                    }
                }
            }
            PageInfo<Evaluating> pageInfo = new PageInfo<>(evaluatingList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.EVALUATING_NULL.getCode(),
                ResponseCodeEvaluatingEnum.EVALUATING_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectEvaluatingList(int pageNum, int pageSize, String keyWord, String evaluatingIndustryID) {
        PageHelper.startPage(pageNum, pageSize);
        Map map1 = new HashMap();
        map1.put("keyWord", keyWord);
        map1.put("evaluatingIndustryID", evaluatingIndustryID);
        List<Evaluating> evaluatingList = evaluatingDao.selectEvaluatingList(map1);
        if (evaluatingList != null && evaluatingList.size() > 0) {
            //所有行业
            ResponseResult responseResult = storeApi.selectListIndustryNoPage();
            List<HashMap<String, Object>> result = (List<HashMap<String, Object>>) responseResult.getResult();
            for (Evaluating evaluating1 : evaluatingList) {
                evaluating1.setIndustryName("");
                Long industryID = evaluating1.getEvaluatingIndustryID();
                if (industryID != null) {
                    for (HashMap<String, Object> map : result) {
                        Integer industryID1 = (Integer) map.get("industryID");
                        if (industryID1.equals(industryID.intValue())) {
                            evaluating1.setIndustryName((String) map.get("industryName"));
                        }
                    }
                }
            }
            PageInfo<Evaluating> pageInfo = new PageInfo<>(evaluatingList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.EVALUATING_NULL.getCode(),
                ResponseCodeEvaluatingEnum.EVALUATING_NULL.getDesc()));
    }

    @Override
    public ResponseResult updateEvaluatingDetailedByID(EvaluatingDetailed evaluatingDetailed) {
        //是否存在
        EvaluatingDetailed evaluatingDetailedTemp = evaluatingDao.selectEvaluatingDetailedByID(evaluatingDetailed.getEvaluatingDetailedID());
        if (evaluatingDetailedTemp == null) {
            return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.EVALUATING_DETAILED_NULL.getCode(),
                    ResponseCodeEvaluatingEnum.EVALUATING_DETAILED_NULL.getDesc()));
        }
        //更新
        int i = evaluatingDao.updateEvaluatingDetailedByID(evaluatingDetailed);
        if (i > 0) {
            return ResponseResult.success(evaluatingDetailed);
        }
        return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.UPDATE_FAILED.getCode(),
                ResponseCodeEvaluatingEnum.UPDATE_FAILED.getDesc()));
    }

    @Override
    public ResponseResult insertEvaluatingDetailed(String orderNum,
                                                   String memberNum,
                                                   Long subclass,
                                                   Double price,
                                                   Integer orderType,
                                                   Long productTypeId) {

        //orderType:(2支付订单)1为商品订单，2为护理订单，5为充值订单，6体验卡订单
        EvaluatingDetailed evaluatingDetailed = new EvaluatingDetailed();
        if (orderType == 5 || orderType == 6) {
            //充值订单  体验卡订单
            List<Evaluating> evaluatingList = evaluatingDao.selectEvaluatingList(new HashMap());
            for (Evaluating evaluating : evaluatingList) {
                if (evaluating.getEvaluatingType().equals(orderType)) {
                    if (orderType == 5) {
                        evaluatingDetailed.setEvaluatingDetailedRechargeOrderID(Long.parseLong(orderNum));
                    }
                    if (orderType == 6) {
                        evaluatingDetailed.setEvaluatingDetailedOrderID(Long.parseLong(orderNum));
                    }
                    evaluatingDetailed.setEvaluatingDetailedAmount(new BigDecimal(price));
                    evaluatingDetailed.setEvaluatingDetailedEvaluatingID(evaluating.getEvaluatingID());
                    evaluatingDetailed.setUserId(Long.parseLong(memberNum));
                    int i = evaluatingDao.insertEvaluatingDetailed(evaluatingDetailed);
                    break;
                }
            }
        } else if (orderType == 2) {
            //支付订单
//            if (productTypeId.toString().equals(ProductTypeEnum.PRODUCT.getCode().toString())||productTypeId.toString().equals(ProductTypeEnum.EXPERIENCECARD.getCode().toString())){
            if (productTypeId.toString().equals(ProductTypeEnum.PRODUCT.getCode().toString())) {
                evaluatingDetailed.setEvaluatingDetailedAmount(new BigDecimal(price));
                evaluatingDetailed.setUserId(Long.parseLong(memberNum));
                //支付订单
                evaluatingDetailed.setEvaluatingDetailedOrderID(Long.parseLong(orderNum));
                //根据小类找到测评方式
                Subclass subclassResult = iCommodityTypeDao.selectSubclassByID(subclass);
                Long subclassEvaluatingID = subclassResult.getSubclassEvaluatingID();
                Evaluating evaluating = evaluatingDao.selectEvaluatingByID(subclassEvaluatingID);
                if (evaluating != null) {
                    evaluatingDetailed.setEvaluatingDetailedEvaluatingID(evaluating.getEvaluatingID());
                }
                int i = evaluatingDao.insertEvaluatingDetailed(evaluatingDetailed);
            } else {
                //根据小类找到测评方式
                Subclass subclassResult = iCommodityTypeDao.selectSubclassByID(subclass);
                Long subclassEvaluatingID = subclassResult.getSubclassEvaluatingID();
                //根据测评方式id找到测评方式
                Evaluating evaluating = evaluatingDao.selectEvaluatingByID(subclassEvaluatingID);
                if (evaluating == null) {
                    return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.INSERT_FAILED.getCode(),
                            ResponseCodeEvaluatingEnum.INSERT_FAILED.getDesc()));
                }
                evaluatingDetailed.setEvaluatingDetailedAmount(new BigDecimal(price));
                evaluatingDetailed.setEvaluatingDetailedEvaluatingID(evaluating.getEvaluatingID());
                evaluatingDetailed.setUserId(Long.parseLong(memberNum));
                //支付订单
                evaluatingDetailed.setEvaluatingDetailedOrderID(Long.parseLong(orderNum));
                int i = evaluatingDao.insertEvaluatingDetailed(evaluatingDetailed);
            }
        } else if (orderType == 3) {
            //拓客订单
        }

        return ResponseResult.success();


    }

    @Override
    public ResponseResult deleteEvaluatingDetailedByID(Long evaluatingDetailedID, String modifyOperator) {
        EvaluatingDetailed evaluatingDetailed = new EvaluatingDetailed();
        evaluatingDetailed.setEvaluatingDetailedID(evaluatingDetailedID);
        evaluatingDetailed.setModifyOperator(modifyOperator);
        int i = evaluatingDao.deleteEvaluatingDetailedByID(evaluatingDetailed);
        if (i > 0) {
            return ResponseResult.success(evaluatingDetailedID);
        }
        return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.DELETE_FAILED.getCode(),
                ResponseCodeEvaluatingEnum.DELETE_FAILED.getDesc()));
    }

    @Override
    public ResponseResult selectEvaluatingDetailedByID(Long evaluatingDetailedID) {
        EvaluatingDetailed evaluatingDetailed = evaluatingDao.selectEvaluatingDetailedByID(evaluatingDetailedID);
        if (evaluatingDetailed != null) {
            return ResponseResult.success(evaluatingDetailed);
        }
        return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.EVALUATING_DETAILED_NULL.getCode(),
                ResponseCodeEvaluatingEnum.EVALUATING_DETAILED_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectEvaluatingDetailedByCondition(int pageNum, int pageSize, EvaluatingDetailed evaluatingDetailed) {
        PageHelper.startPage(pageNum, pageSize);
        List<EvaluatingDetailed> evaluatingDetailedList = evaluatingDao.selectEvaluatingDetailedByCondition(evaluatingDetailed);
        if (evaluatingDetailedList != null && evaluatingDetailedList.size() > 0) {
            PageInfo<EvaluatingDetailed> pageInfo = new PageInfo<>(evaluatingDetailedList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.EVALUATING_DETAILED_NULL.getCode(),
                ResponseCodeEvaluatingEnum.EVALUATING_DETAILED_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectEvaluatingDetailedList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<EvaluatingDetailed> evaluatingDetailedList = evaluatingDao.selectEvaluatingDetailedList();
        List<Evaluating> evaluatingList = evaluatingDao.selectEvaluatingList(new HashMap());
        for (int i = 0; i < evaluatingDetailedList.size(); i++) {
            for (int i1 = 0; i1 < evaluatingList.size(); i1++) {
                if (evaluatingDetailedList.get(i).getEvaluatingDetailedEvaluatingID().toString().equals(evaluatingList.get(i1).getEvaluatingID().toString())) {
                    evaluatingDetailedList.get(i).setEvaluatingDetailedEvaluatingName(evaluatingList.get(i1).getEvaluatingName());
                }
            }
        }
        if (evaluatingDetailedList != null && evaluatingDetailedList.size() > 0) {
            PageInfo<EvaluatingDetailed> pageInfo = new PageInfo<>(evaluatingDetailedList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.EVALUATING_DETAILED_NULL.getCode(),
                ResponseCodeEvaluatingEnum.EVALUATING_DETAILED_NULL.getDesc()));
    }

}
