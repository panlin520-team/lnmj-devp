package com.lnmj.account.repository.impl;

import com.lnmj.account.entity.MemberUser;
import com.lnmj.account.repository.IMemberUserDao;
import com.lnmj.common.baseDao.impl.BaseDao;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;


/**
 * @Author: yilihua
 * @Date: 2019/4/22 10:07
 * @Description: 会员接口实现
 */
@Repository("MemberUserDaoImpl")
public class MemberUserDaoImpl extends BaseDao implements IMemberUserDao {

    @Override
    public List<MemberUser> selectMemberUser(Map map) {
        return super.selectList("memberUser.selectMemberUser",map);
    }

    @Override
    public List<MemberUser> selectMemberUserByPhoneOrName(Map map) {
        return super.selectList("memberUser.selectMemberUserByPhoneOrName",map);
    }

    @Override
    public int addMemberUser(MemberUser storeMember) {
        return super.insert("memberUser.addMemberUser",storeMember);
    }

    @Override
    public int updateMemberUserToOld(Map map) {
        return super.update("memberUser.updateMemberUserToOld",map);
    }

    @Override
    public int updateMemberUserToLost(Map map) {
        return super.update("memberUser.updateMemberUserToLost",map);
    }

    @Override
    public int updateMemberUser(MemberUser storeMember) {
        return super.update("memberUser.updateMemberUser",storeMember);
    }

    @Override
    public String selectPasswordByNum(String memberNum) {
        return super.select("memberUser.selectPasswordByNum",memberNum);
    }


    @Override
    public int CountMemberUserByDay(Map<Object, Object> map) {
        return super.select("memberUser.CountMemberUserByDay", map);
    }

    @Override
    public MemberUser selectMemberUserByMemberNum(Map map) {
        return super.select("memberUser.selectMemberUserByMemberNum",map);
    }

    @Override
    public List<String> batchaddStoreMember(List<MemberUser> memberUserList) {
        return super.select("memberUser.batchaddStoreMember",memberUserList);
    }
}
