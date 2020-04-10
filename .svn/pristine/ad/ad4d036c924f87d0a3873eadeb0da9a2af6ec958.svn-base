package com.lnmj.system.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeSystemEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.business.IBeauticianGradeService;
import com.lnmj.system.entity.BeauticianGrade;
import com.lnmj.system.repository.IBeauticianGradeDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/6/11 11:08
 * @Description: 美容师等级service
 */
@Service("beauticianGradeService")
public class BeauticianGradeService implements IBeauticianGradeService {
    @Resource
    private IBeauticianGradeDao beauticianGradeDao;


    @Override
    public ResponseResult selectBeauticianGradeList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<BeauticianGrade> beauticianGradeList = beauticianGradeDao.selectBeauticianGradeList();
        if(beauticianGradeList!=null && beauticianGradeList.size()!=0){
            PageInfo pageInfo = new PageInfo(beauticianGradeList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeSystemEnum.BEAUTICIAN_GRADE_NOT_FIND.getCode(),
                ResponseCodeSystemEnum.BEAUTICIAN_GRADE_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult selectBeauticianGradeByWhere(BeauticianGrade beauticianGrade) {
        List<BeauticianGrade> beauticianGradeList = beauticianGradeDao.selectBeauticianGradeByWhere(beauticianGrade);
        if(beauticianGradeList!=null && beauticianGradeList.size()!=0){
            return ResponseResult.success(beauticianGradeList);
        }
        return ResponseResult.error(new Error(ResponseCodeSystemEnum.BEAUTICIAN_GRADE_NOT_FIND.getCode(),
                ResponseCodeSystemEnum.BEAUTICIAN_GRADE_NOT_FIND.getDesc()));
    }

    @Override
    public ResponseResult insertBeauticianGrade(BeauticianGrade beauticianGrade) {
        //修改人
        if(StringUtils.isBlank(beauticianGrade.getModifyOperator())){
            beauticianGrade.setModifyOperator(beauticianGrade.getCreateOperator());
        }
        //插入参数
        beauticianGradeDao.insertBeauticianGrade(beauticianGrade);
        return ResponseResult.success(beauticianGrade);
    }

    @Override
    public ResponseResult updateBeauticianGrade(BeauticianGrade beauticianGrade) {
        int i = beauticianGradeDao.updateBeauticianGrade(beauticianGrade);
        return ResponseResult.success(i);
    }

    @Override
    public ResponseResult deleteBeauticianGrade(Long beauticianGradeId, String modifyOperator) {
        BeauticianGrade beauticianGrade = new BeauticianGrade();
        beauticianGrade.setBeauticianGradeId(beauticianGradeId);
        beauticianGrade.setModifyOperator(modifyOperator);
        int i = beauticianGradeDao.deleteBeauticianGrade(beauticianGrade);
        return ResponseResult.success(i);
    }


}
