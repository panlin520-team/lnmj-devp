package com.lnmj.account.repository;


import com.lnmj.account.entity.*;
import com.lnmj.account.entity.VO.StoreMemberVO;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;

/**
 * @Author: panlin
 * @Date: 2019/9/24 9:49
 * @Description:
 */
public interface IMemberUserDao {
    //门店会员信息
    List<MemberUser> selectMemberUser(Map map);
    List<MemberUser> selectMemberUserByPhoneOrName(Map map);
    int addMemberUser(MemberUser memberUser);
    int updateMemberUserToOld(Map map);
    int updateMemberUserToLost(Map map);
    int updateMemberUser(MemberUser memberUser);
    String selectPasswordByNum(String memberNum);

    //门店会员账户信息
    int CountMemberUserByDay(Map<Object, Object> time);
    MemberUser selectMemberUserByMemberNum(Map map);

    List<String> batchaddStoreMember(List<MemberUser> memberUserList);
}
