package com.lnmj.account.repository;


import com.lnmj.account.entity.Label;

import java.util.List;
import java.util.Map;

public interface ILabelDao {
    Label selectByPrimaryKey(String labelId);

    List<Label> selectAll(Map map);

    int insertSelective(Label label);

    int checkLabelName(String labelName);

    int updateLabel(Label label);

    int deleteLabelById(Label label);

    List<Label> selectLabelList();
}
