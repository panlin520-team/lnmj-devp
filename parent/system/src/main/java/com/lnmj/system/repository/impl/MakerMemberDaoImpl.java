package com.lnmj.system.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.system.entity.MakerMemberLevel;
import com.lnmj.system.repository.IMakerMemberDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/8/24 16:15
 * @Description: 创客等级
 */
@Repository
public class MakerMemberDaoImpl extends BaseDao implements IMakerMemberDao {

    @Override
    public List<MakerMemberLevel> selectMakerMemberList() {
        return super.selectList("makerMember.selectMakerMemberList");
    }

    @Override
    public List<MakerMemberLevel> selectMakerMemberByCondition(MakerMemberLevel makerMemberLevel) {
        return super.selectList("makerMember.selectMakerMemberByCondition",makerMemberLevel);
    }

    @Override
    public int insertMakerMember(MakerMemberLevel makerMemberLevel) {
        return super.insert("makerMember.insertMakerMember",makerMemberLevel);
    }

    @Override
    public int updateMakerMember(MakerMemberLevel makerMemberLevel) {
        return super.update("makerMember.updateMakerMember",makerMemberLevel);
    }

    @Override
    public int deleteMakerMember(MakerMemberLevel makerMemberLevel) {
        return super.update("makerMember.deleteMakerMember",makerMemberLevel);
    }
}
