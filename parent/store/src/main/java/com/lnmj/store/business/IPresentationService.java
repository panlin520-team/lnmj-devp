package com.lnmj.store.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.entity.Presentation;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/5/22 11:29
 * @Description: 赠送service接口
 */
@Service("iPresentationService")
public interface IPresentationService {

    ResponseResult selectPresentationList(int pageNum, int pageSize);

    ResponseResult selectPresentationByWhere(int pageNum, int pageSize, Presentation presentation);

    ResponseResult deletePresentation(Long presentationId, String modifyOperator);

    ResponseResult insertPresentation(Presentation presentation);

    ResponseResult appointmentPresentation(Presentation presentation);

    ResponseResult returnPresentation(Presentation presentation);

    ResponseResult confirmPresentation(Presentation presentation);

    ResponseResult cancelPresentation(Presentation presentation);

    ResponseResult selectPresentationEnumName(String name);
}
