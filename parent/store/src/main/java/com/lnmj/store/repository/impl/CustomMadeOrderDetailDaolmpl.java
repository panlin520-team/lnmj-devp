package com.lnmj.store.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.store.entity.CustomMadeOrderDetail;
import com.lnmj.store.entity.VO.CustomMadeOrderVO;
import com.lnmj.store.repository.ICustomMadeOrderDetailDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CustomMadeOrderDetailDaolmpl extends BaseDao implements ICustomMadeOrderDetailDao {
    @Override
    public int insertCustomMadeOrderDetail(CustomMadeOrderDetail customMadeOrderDetail) {
        return super.insert("customMadeorderDetail.insertCustomMadeOrderDetail", customMadeOrderDetail);
    }

    @Override
    public List<CustomMadeOrderDetail> selectCustomMadeList(Map map) {
        return super.selectList("customMadeorderDetail.selectCustomMadeList", map);
    }

    @Override
    public int updateCustommade(Long customMadeOrderDetailId) {
        return super.update("customMadeorderDetail.updateCustomMadeOrder",customMadeOrderDetailId);
    }

    @Override
    public CustomMadeOrderDetail selectCustomMadeById(Long customMadeId) {
        return super.select("customMadeorderDetail.selectCustomMadeById",customMadeId);
    }

    @Override
    public List<CustomMadeOrderVO> selectOrderDetailByCondition(Long orderNumber) {
        return super.selectList("customMadeorderDetail.selectOrderDetailByCondition",orderNumber);
    }
}
