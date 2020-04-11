package com.lnmj.account.repository.impl;

import com.lnmj.account.entity.Label;
import com.lnmj.account.repository.ILabelDao;
import com.lnmj.common.baseDao.impl.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class LabelDaoImpl extends BaseDao implements ILabelDao {

    @Override
    public List<Label> selectAll(Map map) {
        return super.selectList("Label.selectAll",map);
    }

    @Override
    public Label selectByPrimaryKey(String labelId) {
        return super.select("Label.selectByPrimaryKey", labelId);
    }

    @Override
    public int insertSelective(Label label) {
        return super.insert("Label.insert", label);
    }

    @Override
    public int updateLabel(Label label) {
        return super.update("Label.updateByPrimaryKeySelective", label);
    }

    @Override
    public int deleteLabelById(Label label) {
        return super.update("Label.deleteByPrimaryKey", label);
    }

    @Override
    public int checkLabelName(String labelName) {
        return select("Label.checkLabelName", labelName);
    }

    @Override
    public List<Label> selectLabelList() {
        return super.selectList("Label.selectLabelList");
    }
}
