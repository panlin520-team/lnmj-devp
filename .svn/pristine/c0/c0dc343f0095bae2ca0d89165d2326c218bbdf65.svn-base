package com.lnmj.product.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.product.entity.*;
import com.lnmj.product.repository.IUnitDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UnitDaoImpl extends BaseDao implements IUnitDao {

    @Override
    public List<Unit> selectUnitList(Map map) {
        return super.selectList("unit.selectUnitList",map);
    }

    @Override
    public int deleteUnit(Map map) {
        return super.delete("unit.deleteUnit",map);
    }

    @Override
    public int editUnit(Unit unit) {
        return super.update("unit.editUnit",unit);
    }

    @Override
    public int addUnit(Unit unit) {
        return super.insert("unit.addUnit",unit);
    }

    @Override
    public Unit selectUnit(HashMap<Object, Object> map) {
        return super.select("unit.selectUnit",map);
    }
}
