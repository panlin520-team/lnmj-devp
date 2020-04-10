package com.lnmj.account.business;


import com.lnmj.account.entity.Poslabel;
import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;


@Service("iPosLabelService")
public interface IPosLabelService {

    ResponseResult selectLabelList(int pageNum, int pageSize, String keyWord);

    ResponseResult addLabel(Poslabel label);

    ResponseResult updateLabel(Poslabel label);

    ResponseResult deleteLabelById(String labelId, String modifyOperator);

}
