package com.lnmj.account.repository;


import com.lnmj.account.entity.Poslabel;
import com.lnmj.account.entity.Poslabel;

import java.util.List;
import java.util.Map;

public interface IPosLabelDao {
    List<Poslabel> selectList(Map map);

    int insertPosLabel(Poslabel poslabel);

    int updatePosLabel(Poslabel poslabel);

    int deletePosLabel(Map map);

}
