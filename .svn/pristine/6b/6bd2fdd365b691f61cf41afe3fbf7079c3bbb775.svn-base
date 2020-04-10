package com.lnmj.account.repository;


import com.lnmj.account.entity.LabelCategory;

import java.util.List;
import java.util.Map;

public interface ILabelCategoryDao {
    List<LabelCategory> selectCategoryList(Map map);

    LabelCategory selectLabelCategoryById(String labelCategoryId);

    int addCategory(LabelCategory labelCategory);

    int deleteLabelCategoryById(String labelCategoryId);

    int updateLabelCategory(LabelCategory labelCategory);

    boolean checkCategoryName(String categoryName);

    LabelCategory getCategoryByParentId(Long parentId);

//    List<LabelCategory> selectLabelCategoryParent();
}
