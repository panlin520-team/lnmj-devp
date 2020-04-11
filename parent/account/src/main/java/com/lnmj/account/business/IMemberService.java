package com.lnmj.account.business;


import com.lnmj.account.entity.MMemberamountrecord;
import com.lnmj.account.entity.MMembershipLevel;
import com.lnmj.account.entity.MMembershipLevelRecords;
import com.lnmj.account.entity.VO.UpdateAccountMemberShipLevelRequestVO;
import com.lnmj.account.entity.VO.WalletRechargeRecordRequestVO;
import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/4/22 10:02
 * @Description: 会员体系业务处理接口类
 */
@Service("iMemberService")
public interface IMemberService {
    ResponseResult selectAllMemberList(int pageNum, int pageSize);

    ResponseResult selectAllNormalMemberList(int pageNum, int pageSize);

    ResponseResult selectAllNormalMemberListNoPage();

    ResponseResult insertMemberShipLevel(MMembershipLevel mMembershipLevel);

    ResponseResult selectMemberShipLevelList(int pageNum, int pageSize, MMembershipLevel mMembershipLevel);

    ResponseResult deleteMemberShipLevelById(MMembershipLevel mMembershipLevel);

    ResponseResult updateMemberShipLevelById(MMembershipLevel mMembershipLevel);

    ResponseResult updateWalletAccountMemberShipLevelById(UpdateAccountMemberShipLevelRequestVO updateWalletAccountMemberShipLevelRequestVO);

    ResponseResult selectMemberShipLevelUpgradeList(int pageNum, int pageSize, MMembershipLevelRecords mMembershipLevelRecords);

    ResponseResult userRechargeUpgrade(WalletRechargeRecordRequestVO walletRechargeRecordVO);

    ResponseResult consumptionUpgrade(WalletRechargeRecordRequestVO walletRechargeRecordVO);

    ResponseResult selectAllErpName(String name);

    ResponseResult selectMemberShipLevelListByKeyWord(int pageNum, int pageSize, String keyWord);

    ResponseResult fansUpgrade(String memberNum);

    ResponseResult returnLevel(WalletRechargeRecordRequestVO walletRechargeRecordVO);

    ResponseResult selectUserByMember(int pageNum, int pageSize, Long membershipLevelId);

    ResponseResult selectMemberAmountRecordByCondition(int pageNum, int pageSize, MMemberamountrecord memberamountrecord);

    ResponseResult insertMemberAmountRecord(MMemberamountrecord memberamountrecord);

    ResponseResult userRechargeUpgradeAdd(WalletRechargeRecordRequestVO walletRechargeRecordVO);

    ResponseResult<MMembershipLevelRecords> memberUserUpdate(String memberNumber);
}
