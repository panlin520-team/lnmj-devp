package com.lnmj.store.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.store.entity.Presentation;
import com.lnmj.store.repository.IPresentationDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/22 09:49
 * @Description: 赠送dao实现
 */
@Repository
public class PresentationDaoImpl extends BaseDao implements IPresentationDao {

    @Override
    public List<Presentation> selectPresentationList() {
        return super.selectList("presentation.selectPresentationList");
    }

    @Override
    public List<Presentation> selectPresentationByWhere(Presentation presentation) {
        return super.selectList("presentation.selectPresentationByWhere",presentation);
    }

    @Override
    public int insertPresentation(Presentation presentation) {
        return super.insert("presentation.insertPresentation",presentation);
    }

    @Override
    public int updatePresentation(Presentation presentation) {
        return super.update("presentation.updatePresentation",presentation);
    }

    @Override
    public int deletePresentation(Presentation presentation) {
        return super.update("presentation.deletePresentation",presentation);
    }

    @Override
    public int returnPresentation(Presentation presentation) {
        return super.update("presentation.returnPresentation",presentation);
    }

}
