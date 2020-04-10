package com.lnmj.data.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeEvaluatingEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.business.IEvaluatingLevelService;
import com.lnmj.data.entity.Evaluating;
import com.lnmj.data.entity.EvaluatingDetailed;
import com.lnmj.data.entity.EvaluatingLevel;
import com.lnmj.data.repository.IEvaluatingDao;
import com.lnmj.data.repository.IEvaluatingLevelDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/10/12 16:32
 * @Description: 客户评测
 */
@Service("evaluatingLevelService")
public class EvaluatingLevelService implements IEvaluatingLevelService {
    @Resource
    private IEvaluatingLevelDao evaluatingLevelDao;
    @Resource
    private IEvaluatingDao evaluatingDao;

    @Override
    public ResponseResult selectEvaluatingLeveldList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<EvaluatingLevel> evaluatingLevelList = evaluatingLevelDao.selectEvaluatingLeveldList();
        if(evaluatingLevelList!=null && evaluatingLevelList.size()>0){
            for (EvaluatingLevel evaluatingLevel : evaluatingLevelList) {
                evaluatingLevel.setEvaluatingName("");
                Long evaluatingID = evaluatingLevel.getEvaluatingLevelEvaluatingID();
                if(evaluatingID !=null){
                    Evaluating evaluating = evaluatingDao.selectEvaluatingByID(evaluatingID);
                    evaluatingLevel.setEvaluatingName(evaluating.getEvaluatingName());
                }
            }
            PageInfo<EvaluatingLevel> pageInfo = new PageInfo<>(evaluatingLevelList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.LEVEL_INFO_ISNULL.getCode(),
                ResponseCodeEvaluatingEnum.LEVEL_INFO_ISNULL.getDesc()));
    }

    @Override
    public ResponseResult addEvaluatingLeveld(EvaluatingLevel evaluatingLevel) {
        int result = evaluatingLevelDao.addEvaluatingLeveld(evaluatingLevel);
        if (result > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.ADD_LEVELINFO_FAIL.getCode(),
                ResponseCodeEvaluatingEnum.ADD_LEVELINFO_FAIL.getDesc()));
    }

    @Override
    public ResponseResult updateEvaluatingLeveldList(EvaluatingLevel evaluatingLevel) {
        int result = evaluatingLevelDao.updateEvaluatingLeveldList(evaluatingLevel);
        if (result > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.UPDATE_LEVELINFO_FAIL.getCode(),
                ResponseCodeEvaluatingEnum.UPDATE_LEVELINFO_FAIL.getDesc()));
    }

    @Override
    public ResponseResult deleteEvaluatingLeveldList(EvaluatingLevel evaluatingLevel) {
        int result = evaluatingLevelDao.deleteEvaluatingLeveldList(evaluatingLevel);
        if (result > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ResponseCodeEvaluatingEnum.DELETE_LEVELINFO_FAIL.getCode(),
                ResponseCodeEvaluatingEnum.DELETE_LEVELINFO_FAIL.getDesc()));
    }
}
