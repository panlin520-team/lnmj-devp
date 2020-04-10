package com.lnmj.account.repository.impl;

import com.lnmj.account.entity.*;
import com.lnmj.account.entity.VO.UpdateAccountMemberShipLevelVO;
import com.lnmj.account.repository.IMemberDao;
import com.lnmj.common.baseDao.impl.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: yilihua
 * @Date: 2019/4/22 10:07
 * @Description: 会员接口实现
 */
@Repository("memberDaoImpl")
public class MemberDaoImpl extends BaseDao implements IMemberDao {
    @Override
    public List<MMembershipLevel> selectMemberShipLevelListByKeyWord(String keyWord) {
        return super.selectList("member.selectMemberShipLevelListByKeyWord", keyWord);
    }

    @Override
    public List<MemberUser> selectUserByMember(Long membershipLevelId) {
        return super.selectList("member.selectUserByMember",membershipLevelId);
    }

    @Override
    public Integer selectUserTypeByUserId(Long userId) {
        return super.select("member.selectUserTypeByUserId", userId);
    }

    @Override
    public Integer selectParentIdsByMemberNum(String memberNum) {
        return super.select("member.selectParentIdsByUserId", memberNum);
    }

    @Override
    public List<String> selectUsersFromUserByMembershipLevelId(Long membershipLevelId) {
        return super.selectList("member.selectUsersFromUserByMembershipLevelId", membershipLevelId);
    }

    @Override
    public Long selectMemberShipLevelByUserId(Long userId) {
        return super.select("member.selectMemberShipLevelByUserId", userId);
    }

    @Override
    public String selectUserNameByUserId(Long userId) {
        return super.select("member.selectUserNameByUserId", userId);
    }

    @Override
    public int insertMembershipLevelRecords(MMembershipLevelRecords mMembershipLevelRecords) {
        return super.insert("member.insertMembershipLevelRecords", mMembershipLevelRecords);
    }

    @Override
    public List<Long> updatememberDivisionList(List<MMemberdivision> memberDivisionList) {
        return super.updateList("MemberDivision.updatememberDivisionList",memberDivisionList);
    }

    @Override
    public List<Long> insertmemberDivisionList(List<MMemberdivision> memberDivisionList) {
        return super.insertList("MemberDivision.insertmemberDivisionList",memberDivisionList);
    }

    @Override
    public int deleteMemberDivisionByMemberID(MMemberdivision memberdivision) {
        return super.update("MemberDivision.deleteMemberDivisionByMemberID",memberdivision);
    }

    @Override
    public List<MMemberdivision> selectMemberDivisionList() {
        return super.selectList("MemberDivision.selectMemberDivisionList");
    }

    @Override
    public List<MMemberdivision> selectMemberDivisionByMemberID(Long memberLevel) {
        return super.selectList("MemberDivision.selectMemberDivisionByMemberID",memberLevel);
    }

    @Override
    public MMemberdivision selectMemberDivisionByID(Long memberDivisionID) {
        return super.select("MemberDivision.selectMemberDivisionByID",memberDivisionID);
    }

    @Override
    public int insertMemberDivision(MMemberdivision memberdivision) {
        return super.insert("MemberDivision.insertMemberDivision",memberdivision);
    }

    @Override
    public int updateMemberDivision(MMemberdivision memberdivision) {
        return super.update("MemberDivision.updateMemberDivision",memberdivision);
    }

    @Override
    public int deleteMemberDivisionByID(Long memberDivisionID) {
        return super.update("MemberDivision.deleteMemberDivisionByID",memberDivisionID);
    }

    @Override
    public List<MMemberamountrecord> selectMemberAmountRecordList() {
        return super.selectList("MemberAmountRecord.selectMemberAmountRecordList");
    }

    @Override
    public List<MMemberamountrecord> selectMemberAmountRecordByCondition(MMemberamountrecord memberamountrecord) {
        return super.selectList("MemberAmountRecord.selectMemberAmountRecordByCondition",memberamountrecord);
    }

    @Override
    public int insertMemberAmountRecord(MMemberamountrecord memberamountrecord) {
        return super.insert("MemberAmountRecord.insertMemberAmountRecord",memberamountrecord);
    }

    @Override
    public List<Long> updateMemberAccountList(List<MMemberaccount> memberAccountList) {
        return super.updateList("MemberAccount.updateMemberAccountList",memberAccountList);
    }

    @Override
    public List<Long> insertmemberAccountList(List<MMemberaccount> memberAccountList) {
        return super.insertList("MemberAccount.insertmemberAccountList",memberAccountList);
    }

    @Override
    public int deleteMemberAccountByMemberID(MMemberaccount mMemberaccount) {
        return super.update("MemberAccount.deleteMemberAccountByMemberID",mMemberaccount);
    }

    @Override
    public List<MMemberaccount> selectMemberAccountByMemberID(Long memberLevelId) {
        return super.selectList("MemberAccount.selectMemberAccountByMemberID",memberLevelId);
    }

    @Override
    public List<MMemberaccount> selectmemberAccountList() {
        return super.selectList("MemberAccount.selectmemberAccountList");
    }

    @Override
    public MMemberaccount selectMemberAccountByID(Long memberAccountId) {
        return super.select("MemberAccount.selectMemberAccountByID",memberAccountId);
    }

    @Override
    public int deleteMemberAccount(MMemberaccount memberaccount) {
        return super.update("MemberAccount.deleteMemberAccount",memberaccount);
    }

    @Override
    public int insertMemberAccount(MMemberaccount memberaccount) {
        return super.insert("MemberAccount.insertMemberAccount",memberaccount);
    }

    @Override
    public int updateMemberAccount(MMemberaccount memberaccount) {
        return super.update("MemberAccount.updateMemberAccount",memberaccount);
    }

    @Override
    public List<MMembershipLevel> selectAllNormalMemberList() {
        return super.selectList("member.selectAllNormalMemberList");
    }

    @Override
    public List<MMembershipLevel> selectMemberShipLevelList(MMembershipLevel mMembershipLevel) {
        return super.selectList("member.selectMemberShipLevelList", mMembershipLevel);
    }

    @Override
    public int insertMemberShipLevel(MMembershipLevel mMembershipLevel) {
        return super.insert("member.insertMemberShipLevel", mMembershipLevel);
    }

    @Override
    public int deleteMemberShipLevelById(MMembershipLevel mMembershipLevel) {
        return super.update("member.deleteMemberShipLevelById", mMembershipLevel);
    }

    @Override
    public int updateMemberShipLevelById(MMembershipLevel mMembershipLevel) {
        return super.update("member.updateMemberShipLevelById", mMembershipLevel);
    }

    @Override
    public int updateWalletAccountMemberShipLevelById(UpdateAccountMemberShipLevelVO updateWalletAccountMemberShipLevelVO) {
        return super.update("member.updateWalletAccountMemberShipLevelById", updateWalletAccountMemberShipLevelVO);
    }

    @Override
    public List<MMembershipLevelRecords> selectMemberShipLevelUpgradeList(MMembershipLevelRecords mMembershipLevelRecords) {
        return super.selectList("member.selectMemberShipLevelUpgradeList", mMembershipLevelRecords);
    }
}
