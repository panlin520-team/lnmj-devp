package com.lnmj.system.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.entity.BackupRestore;
import com.lnmj.system.entity.BeauticianGrade;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/6/11 11:04
 * @Description: 美容师等级service接口
 */
@Service("iBeauticianGradeService")
public interface IBeauticianGradeService {

    ResponseResult selectBeauticianGradeList(int pageNum, int pageSize);

    ResponseResult selectBeauticianGradeByWhere(BeauticianGrade beauticianGrade);

    ResponseResult insertBeauticianGrade(BeauticianGrade beauticianGrade);

    ResponseResult updateBeauticianGrade(BeauticianGrade beauticianGrade);

    ResponseResult deleteBeauticianGrade(Long beauticianGradeId, String modifyOperator);


}
