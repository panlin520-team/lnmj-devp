package com.lnmj.account.business;


import com.lnmj.account.entity.Label;
import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;


@Service("ilabelService")
public interface ILabelService {

    ResponseResult selectById(String labelId);

    ResponseResult selectLabelList(int pageNum, int pageSize, String keyWord, Long labelCategoryId);

    ResponseResult addLabel(Label label);

    ResponseResult updateLabel(Label label);

    ResponseResult deleteLabelById(String labelId, String modifyOperator);

    ResponseResult deleteLabelByIds(String[] ids, String modifyOperator);

}
