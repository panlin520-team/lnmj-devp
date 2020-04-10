package com.lnmj.data.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.entity.Evaluating;
import com.lnmj.data.entity.EvaluatingDetailed;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/10/12 16:30
 * @Description: 客户评测
 */
@Service("iEvaluatingService")
public interface IEvaluatingService {

    ResponseResult updateEvaluatingByID(Evaluating evaluating);

    ResponseResult insertEvaluating(Evaluating evaluating);

    ResponseResult deleteEvaluatingByID(Long evaluatingID, String modifyOperator);

    ResponseResult selectEvaluatingByID(Long evaluatingID);

    ResponseResult selectEvaluatingByCondition(int pageNum, int pageSize, Evaluating evaluating);

    ResponseResult selectEvaluatingList(int pageNum, int pageSize, String keyWord,String evaluatingIndustryID);

    ResponseResult updateEvaluatingDetailedByID(EvaluatingDetailed evaluatingDetailed);

    ResponseResult insertEvaluatingDetailed(String orderNum,
                                            String memberNum,
                                            Long subclass,
                                            Double price,
                                            Integer orderType,
                                            Long productTypeId);

    ResponseResult deleteEvaluatingDetailedByID(Long evaluatingDetailedID, String modifyOperator);

    ResponseResult selectEvaluatingDetailedByID(Long evaluatingDetailedID);

    ResponseResult selectEvaluatingDetailedByCondition(int pageNum, int pageSize, EvaluatingDetailed evaluatingDetailed);

    ResponseResult selectEvaluatingDetailedList(int pageNum, int pageSize);

}
