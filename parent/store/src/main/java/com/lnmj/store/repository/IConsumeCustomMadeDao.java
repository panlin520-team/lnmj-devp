package com.lnmj.store.repository;

import com.lnmj.store.entity.ConsumeCustomMade;
import com.lnmj.store.entity.VO.ConsumeCustomMadeVO;

import java.util.HashMap;
import java.util.List;

public interface IConsumeCustomMadeDao {
    List<ConsumeCustomMade> selectConsumeCustomMadeList();

    int insertConsumeCustomMade(ConsumeCustomMade consumeCustomMade);

    List<ConsumeCustomMadeVO> selectUserListById(Long customMadeOrderDetailId);

    List<ConsumeCustomMade> selectCustomByIdsAndTime(HashMap<Object, Object> map);
}
