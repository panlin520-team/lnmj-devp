package com.lnmj.account.business;


import com.lnmj.account.entity.*;
import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/4/22 10:02
 * @Description: 会员用户管理
 */
@Service("iMemberUserService")
public interface IMemberUserService {
    ResponseResult selectMemberUser(int pageNum,int pageSize,String storeId,String mobile, String name,Integer newOldStatus,Integer isLost);
    ResponseResult selectMemberUserNoPage();
    ResponseResult selectMemberUserByPhoneOrName(int pageNum, int pageSize, String storeId, String mobile, String name);
    ResponseResult addMemberUser(MemberUser storeMember);
    ResponseResult selectMemberUserAccount(int pageNum,int pageSize,String memberNum,String subClassIds,Long industry);
    ResponseResult selectStoreMemberAccountNoPage(String memberNum,String subClassIds);
    ResponseResult selectMemberByNum(String memberNum);

    ResponseResult CountMemberByDay(String time, String storeId);

    ResponseResult updateMemberUserToOld(List<MemberUser> storeMemberInfoList);
    ResponseResult updateMemberUserToLost(List<MemberUser> storeMemberInfoList);
    ResponseResult updateMemberUser(MemberUser storeMember);
    ResponseResult selectEnterChannel();
    ResponseResult resetMemberPassword(String memberNum, String passwordOld, String passwordNew);

    ResponseResult batchaddStoreMember(List<MemberUser> memberUserList);
}
