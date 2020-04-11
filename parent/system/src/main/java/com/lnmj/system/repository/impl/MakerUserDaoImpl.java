package com.lnmj.system.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.system.entity.MakerMemberLevel;
import com.lnmj.system.entity.MakerProduct;
import com.lnmj.system.entity.MakerUser;
import com.lnmj.system.repository.IMakerMemberDao;
import com.lnmj.system.repository.IMakerUserDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/8/24 16:15
 * @Description: 创客用户
 */
@Repository
public class MakerUserDaoImpl extends BaseDao implements IMakerUserDao {

    @Override
    public List<MakerUser> selectMakerUserList() {
        return super.selectList("makerUser.selectMakerUserList");
    }

    @Override
    public List<MakerUser> selectMakerUserByCondition(MakerUser makerUser) {
        return super.selectList("makerUser.selectMakerUserByCondition",makerUser);
    }

    @Override
    public int insertMakerUser(MakerUser makerUser) {
        return super.insert("makerUser.insertMakerUser",makerUser);
    }

    @Override
    public int updateMakerUser(MakerUser makerUser) {
        return super.update("makerUser.updateMakerUser",makerUser);
    }

    @Override
    public int deleteMakerUser(MakerUser makerUser) {
        return super.update("makerMember.deleteMakerUser",makerUser);
    }
}
