package com.lnmj.store.repository;

import com.lnmj.store.entity.CustomMadeOrderDetail;
import com.lnmj.store.entity.VO.CustomMadeOrderVO;

import java.util.List;
import java.util.Map;

public interface ICustomMadeOrderDetailDao {
    int insertCustomMadeOrderDetail(CustomMadeOrderDetail CustomMadeOrderDetail);

    List<CustomMadeOrderDetail> selectCustomMadeList(Map map);

    int updateCustommade(Long customMadeOrderDetailId);

    CustomMadeOrderDetail selectCustomMadeById(Long customMadeId);

    List<CustomMadeOrderVO> selectOrderDetailByCondition(Long orderNumber);
}
