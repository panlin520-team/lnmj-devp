package com.lnmj.account.repository.impl;

import com.lnmj.account.entity.LabelCategory;
import com.lnmj.account.repository.ILabelCategoryDao;
import com.lnmj.common.baseDao.impl.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class LabelCategoryDaoImpl extends BaseDao implements ILabelCategoryDao {

    @Override
    public boolean checkCategoryName(String categoryName) {
        return super.select("LabelCategory.checkCategoryName");
    }

    @Override
    public List<LabelCategory> selectCategoryList(Map map) {
        return super.selectList("LabelCategory.selectCategoryList",map);
    }


    @Override
    public int addCategory(LabelCategory labelCategory) {
        return super.insert("LabelCategory.insertSelective", labelCategory);
    }

    @Override
    public LabelCategory getCategoryByParentId(Long parentId) {
        return super.select("LabelCategory.getCategoryByParentId", parentId);
    }

    @Override
    public LabelCategory selectLabelCategoryById(String labelCategoryId) {
        return super.select("LabelCategory.selectLabelCategoryById", labelCategoryId);
    }

    @Override
    public int updateLabelCategory(LabelCategory labelCategory) {
        return super.update("LabelCategory.updateByPrimaryKeySelective", labelCategory);
    }

    @Override
    public int deleteLabelCategoryById(String labelCategoryId) {
        return super.update("LabelCategory.deleteByPrimaryKey", labelCategoryId);
    }

//    @Override
//    public List<LabelCategory> selectLabelCategoryParent() {
//        return super.selectList("LabelCategory.selectLabelCategoryParent");
//    }
}
