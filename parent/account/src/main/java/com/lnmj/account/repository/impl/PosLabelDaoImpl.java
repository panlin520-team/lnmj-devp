package com.lnmj.account.repository.impl;

import com.lnmj.account.entity.Poslabel;
import com.lnmj.account.repository.IPosLabelDao;
import com.lnmj.common.baseDao.impl.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class PosLabelDaoImpl extends BaseDao implements IPosLabelDao {

    @Override
    public List<Poslabel> selectList(Map map) {
        return super.selectList("poslabel.selectList",map);
    }

    @Override
    public int insertPosLabel(Poslabel poslabel) {
        return super.insert("poslabel.insertPostLabel",poslabel);
    }

    @Override
    public int updatePosLabel(Poslabel poslabel) {
        return super.update("poslabel.updatePostLabel",poslabel);
    }

    @Override
    public int deletePosLabel(Map map) {
        return super.update("poslabel.deletePostLabel",map);
    }
}
