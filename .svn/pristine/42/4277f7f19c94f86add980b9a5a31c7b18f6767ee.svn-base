package com.lnmj.account.repository;


import com.lnmj.account.entity.*;
import com.lnmj.account.entity.VO.UpdateAccountMemberShipLevelVO;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/4/29 9:49
 * @Description:
 */
public interface IMemberDao {
    /*
    等级股东
     */
    List<Long> updatememberDivisionList(List<MMemberdivision> memberDivisionList);

    List<Long> insertmemberDivisionList(List<MMemberdivision> memberDivisionList);

    int deleteMemberDivisionByMemberID(MMemberdivision memberdivision);

    List<MMemberdivision> selectMemberDivisionList();

    List<MMemberdivision> selectMemberDivisionByMemberID(Long memberLevel);

    MMemberdivision selectMemberDivisionByID(Long memberDivisionID);

    int insertMemberDivision(MMemberdivision memberdivision);

    int updateMemberDivision(MMemberdivision memberdivision);

    int deleteMemberDivisionByID(Long memberDivisionID);

    /*
    等级金额记录
     */
    List<MMemberamountrecord> selectMemberAmountRecordList();

    List<MMemberamountrecord> selectMemberAmountRecordByCondition(MMemberamountrecord memberamountrecord);

    int insertMemberAmountRecord(MMemberamountrecord memberamountrecord);

     /*
    等级账户
     */
    List<Long> updateMemberAccountList(List<MMemberaccount> memberAccountList);

    List<Long> insertmemberAccountList(List<MMemberaccount> memberAccountList);

    int deleteMemberAccountByMemberID(MMemberaccount memberaccount);

    List<MMemberaccount> selectMemberAccountByMemberID(Long memberLevelId);

    List<MMemberaccount> selectmemberAccountList();

    MMemberaccount selectMemberAccountByID(Long memberAccountId);

    int deleteMemberAccount(MMemberaccount memberaccount);

    int insertMemberAccount(MMemberaccount memberaccount);

    int updateMemberAccount(MMemberaccount memberaccount);

    /*
    等级
     */
    List<MMembershipLevel> selectAllNormalMemberList();

    List<MMembershipLevel> selectMemberShipLevelList(MMembershipLevel mMembershipLevel);

    int insertMemberShipLevel(MMembershipLevel mMembershipLevel);

    int deleteMemberShipLevelById(MMembershipLevel mMembershipLevel);

    int updateMemberShipLevelById(MMembershipLevel mMembershipLevel);

    int updateWalletAccountMemberShipLevelById(UpdateAccountMemberShipLevelVO updateWalletAccountMemberShipLevelVO);

    List<MMembershipLevelRecords> selectMemberShipLevelUpgradeList(MMembershipLevelRecords mMembershipLevelRecords);

    /*
   等级升级记录
    */
    int insertMembershipLevelRecords(MMembershipLevelRecords mMembershipLevelRecords);

    String selectUserNameByUserId(Long userId);

    Long selectMemberShipLevelByUserId(Long userId);

    List<String> selectUsersFromUserByMembershipLevelId(Long membershipLevelId);

    Integer selectParentIdsByMemberNum(String memberNum);

    Integer selectUserTypeByUserId(Long userId);

    List<MMembershipLevel> selectMemberShipLevelListByKeyWord(String keyWord);

    List<MemberUser> selectUserByMember(Long membershipLevelId);
}
