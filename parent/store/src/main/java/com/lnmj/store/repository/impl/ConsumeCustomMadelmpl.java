package com.lnmj.store.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.store.entity.ConsumeCustomMade;
import com.lnmj.store.entity.VO.ConsumeCustomMadeVO;
import com.lnmj.store.repository.IConsumeCustomMadeDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class ConsumeCustomMadelmpl extends BaseDao implements IConsumeCustomMadeDao {
    @Override
    public List<ConsumeCustomMade> selectConsumeCustomMadeList() {
        return super.selectList("consumeCustomMade.selectConsumeCustomMadeList");
    }

    @Override
    public int insertConsumeCustomMade(ConsumeCustomMade consumeCustomMade) {
        return super.insert("consumeCustomMade.insertConsumeCustomMade",consumeCustomMade);
    }

    @Override
    public List<ConsumeCustomMadeVO> selectUserListById(Long customMadeOrderDetailId) {
        return super.selectList("consumeCustomMade.selectUserListBycustomMadeId", customMadeOrderDetailId);
    }

    @Override
    public List<ConsumeCustomMade> selectCustomByIdsAndTime(HashMap<Object, Object> map) {
        return super.selectList("consumeCustomMade.selectCustomByIdsAndTime", map);
    }
}
