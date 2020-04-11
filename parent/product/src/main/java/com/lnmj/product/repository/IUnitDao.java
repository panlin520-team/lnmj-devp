package com.lnmj.product.repository;

import com.lnmj.product.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IUnitDao {

    List<Unit> selectUnitList(Map map);

    int deleteUnit(Map map);

    int editUnit(Unit unit);

    int addUnit(Unit unit);

    Unit selectUnit(HashMap<Object, Object> map);
}