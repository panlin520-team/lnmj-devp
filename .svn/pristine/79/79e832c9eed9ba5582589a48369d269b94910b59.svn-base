package com.lnmj.account.business;


import com.lnmj.account.entity.LabelCategory;
import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

@Service("iLabelCategoryService")
public interface ILabelCategoryService {

    ResponseResult addCategory(LabelCategory labelCategory);

    ResponseResult selectCategoryList(int pageNum, int pageSize, String keyWord);

    ResponseResult selectLabelCategoryById(String labelCategoryId);

    ResponseResult deleteLabelCategoryById(String labelCategoryId);

    ResponseResult updateLabelCategory(LabelCategory labelCategory);

    boolean checkCategoryName(String categoryName);

    ResponseResult deleteLabelCategoryByIds(String[] labelCategoryIds);

//    ResponseResult selectLabelCategoryParent();
}
