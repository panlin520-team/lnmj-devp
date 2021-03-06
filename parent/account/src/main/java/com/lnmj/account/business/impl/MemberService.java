package com.lnmj.account.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.account.business.IMemberService;
import com.lnmj.account.entity.*;
import com.lnmj.account.entity.VO.UpdateAccountMemberShipLevelRequestVO;
import com.lnmj.account.entity.VO.UpdateAccountMemberShipLevelVO;
import com.lnmj.account.entity.VO.WalletRechargeRecordRequestVO;
import com.lnmj.account.repository.IMemberDao;
import com.lnmj.account.repository.IMemberUserDao;
import com.lnmj.account.serviceProxy.OrderApi;
import com.lnmj.account.serviceProxy.StoreApi;
import com.lnmj.account.serviceProxy.WalletApi;
import com.lnmj.common.Enum.*;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAccountEnum;
import com.lnmj.common.baseDao.IBaseDao;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.MemberUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: yilihua
 * @Date: 2019/4/22 10:01
 * @Description: 会员体系业务处理实现类
 */
@Transactional
@Service("memberService")
public class MemberService implements IMemberService {

    /**
     * @Autowired
     * @Qualifier("BaseDao") 或者
     * @Resource(name="BaseDao")
     */
    @Resource(name = "memberDaoImpl")
    private IMemberDao iMemberDao;
    @Resource(name = "BaseDaoImpl")
    private IBaseDao iBaseDao;
    @Resource
    private WalletApi walletApi;
    @Resource
    private OrderApi orderApi;
    @Resource
    private IMemberUserDao iMemberUserDao;
    @Resource
    private StoreApi storeApi;
    /**
    *@Description 根据会员等级查询会员
    *@Param [pageNum, pageSize, membershipLevelId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/16
    *@Time 17:32
    */
    @Override
    public ResponseResult selectUserByMember(int pageNum, int pageSize, Long membershipLevelId){
        PageHelper.startPage(pageNum,pageSize);
        List<MemberUser> memberUserList = iMemberDao.selectUserByMember(membershipLevelId);
        if(memberUserList.size()>0){
            PageInfo pageInfo = new PageInfo(memberUserList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.ACCOUNT_NOT_FOUND.getCode(),
                ResponseCodeAccountEnum.ACCOUNT_NOT_FOUND.getDesc()));
    }

    /**
     * @Description 关键字查询
     * @Param [pageNum, pageSize, keyWord]
     * @Return com.lnmj.account.common.response.ResponseResult<com.lnmj.account.entity.MMembershipLevel>
     * @Author yilihua
     * @Date 2019/4/26
     * @Time 17:15
     */
    @Transactional
    @Override
    public ResponseResult selectMemberShipLevelListByKeyWord(int pageNum, int pageSize, String keyWord) {
        PageHelper.startPage(pageNum, pageSize);
        List<MMembershipLevel> mMembershipLevelList = iMemberDao.selectMemberShipLevelListByKeyWord(keyWord);
        if (mMembershipLevelList!=null && !mMembershipLevelList.isEmpty()) {
            //添加等级账户数据,添加等级股东数据
            for(MMembershipLevel membershipLevel:mMembershipLevelList){
                Long membershipLevelId = membershipLevel.getMembershipLevelId();
                List<MMemberaccount> memberaccountList = iMemberDao.selectMemberAccountByMemberID(membershipLevelId);
                membershipLevel.setMemberAccountList(memberaccountList);
                List<MMemberdivision> memberdivisionList = iMemberDao.selectMemberDivisionByMemberID(membershipLevelId);
                membershipLevel.setMemberDivisionList(memberdivisionList);
            }
            PageInfo<MMembershipLevel> pageInfo = new PageInfo(mMembershipLevelList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getCode(),
                ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getDesc()));
    }

    /**
     * @Description 查询枚举数据
     * @Param [name]
     * @Return com.lnmj.account.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/4/26
     * @Time 17:14
     */
    @Transactional
    @Override
    public ResponseResult selectAllErpName(String name) {
        if ("ErpNameEnum".equals(name)) {
            return ResponseResult.success(MemberUtil.getEnumToMap(ErpNameEnum.class));
        }
        if ("UpgradingWaysEnum".equals(name)) {
            return ResponseResult.success(MemberUtil.getEnumToMap(UpgradingWaysEnum.class));
        }
        if ("MemberLevelTypeEnum".equals(name)) {
            return ResponseResult.success(MemberUtil.getEnumToMap(MemberLevelUpgringTypeEnum.class));
        }
        if("ScopeOfUseEnum".equals(name)){
            return ResponseResult.success(MemberUtil.getEnumToMap(ScopeOfUseEnum.class));
        }
        if("AmountMethodEnum".equals(name)){
            return ResponseResult.success(MemberUtil.getEnumToMap(AmountMethodEnum.class));
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.ENUM_NAME_ERROR.getCode(), ResponseCodeAccountEnum.ENUM_NAME_ERROR.getDesc()));
    }

    /**
     * @Description 新增等级
     * @Param [mMembershipLevel]
     * @Return com.lnmj.account.common.response.ResponseResult;
     * @Author yilihua
     * @Date 2019/4/22
     * @Time 11:08
     */
    @Transactional
    @Override
    public ResponseResult insertMemberShipLevel(MMembershipLevel mMembershipLevel) {
        /*
            会员生效时间和使用范围(默认所有用户生效)
            新用户生效，新增加会员等级（老用户保存了会员等级id，不能直接修改，且会员等级信息需要保存）
            老用户生效，新增加会员等级（老用户用之前的会员等级，新用户用之后的会员等级）
            所有用户生效，则直接修改会员等级
        */
        //新增时创建人和修改人一致
        if (StringUtils.isBlank(mMembershipLevel.getModifyOperator())) {
            mMembershipLevel.setModifyOperator(mMembershipLevel.getCreateOperator());
        }
        iMemberDao.insertMemberShipLevel(mMembershipLevel);
        Long membershipLevelId = mMembershipLevel.getMembershipLevelId();
        //等级账户
        List<MMemberaccount> memberAccountList = mMembershipLevel.getMemberAccountList();
        if(memberAccountList!=null && !memberAccountList.isEmpty()){
            for(MMemberaccount memberaccount:memberAccountList){
                memberaccount.setMemberLevel(membershipLevelId);
                if(StringUtils.isBlank(memberaccount.getCreateOperator())){
                    memberaccount.setCreateOperator(mMembershipLevel.getCreateOperator());
                }
                if (StringUtils.isBlank(memberaccount.getModifyOperator())){
                    memberaccount.setModifyOperator(mMembershipLevel.getModifyOperator());
                }
            }
            iMemberDao.insertmemberAccountList(memberAccountList);
        }
        //等级股东
        if(mMembershipLevel.getIsDivision()){
            List<MMemberdivision> memberDivisionList = mMembershipLevel.getMemberDivisionList();
            if(memberDivisionList!=null && !memberDivisionList.isEmpty()){
                for(MMemberdivision memberdivision:memberDivisionList){
                    memberdivision.setMemberLevel(membershipLevelId);
                    if(StringUtils.isBlank(memberdivision.getCreateOperator())){
                        memberdivision.setCreateOperator(mMembershipLevel.getCreateOperator());
                    }
                    if (StringUtils.isBlank(memberdivision.getModifyOperator())){
                        memberdivision.setModifyOperator(mMembershipLevel.getModifyOperator());
                    }
                }
                iMemberDao.insertmemberDivisionList(memberDivisionList);
            }
        }
        return ResponseResult.success();
    }


    /**
     * @Description 显示所有正常会员等级
     * @Param [pageNum,pageSize]
     * @Return com.lnmj.account.common.response.ResponseResult;
     * @Author yilihua
     * @Date 2019/4/22
     * @Time 11:09
     */
    @Transactional
    @Override
    public ResponseResult selectAllNormalMemberList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MMembershipLevel> mMembershipLevelList = iMemberDao.selectAllNormalMemberList();
        if (mMembershipLevelList!=null && !mMembershipLevelList.isEmpty()) {
            //添加等级账户数据,添加等级股东数据
            for(MMembershipLevel membershipLevel:mMembershipLevelList){
                Long membershipLevelId = membershipLevel.getMembershipLevelId();
                List<MMemberaccount> memberaccountList = iMemberDao.selectMemberAccountByMemberID(membershipLevelId);
                membershipLevel.setMemberAccountList(memberaccountList);
                List<MMemberdivision> memberdivisionList = iMemberDao.selectMemberDivisionByMemberID(membershipLevelId);
                membershipLevel.setMemberDivisionList(memberdivisionList);
            }
            PageInfo pageInfo = new PageInfo(mMembershipLevelList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getCode(),
                ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getDesc()));
    }

    @Override
    public ResponseResult selectAllNormalMemberListNoPage() {
        List<MMembershipLevel> mMembershipLevelList = iMemberDao.selectAllNormalMemberList();
        if (mMembershipLevelList!=null && !mMembershipLevelList.isEmpty()) {
            //添加等级账户数据,添加等级股东数据
            for(MMembershipLevel membershipLevel:mMembershipLevelList){
                Long membershipLevelId = membershipLevel.getMembershipLevelId();
                List<MMemberaccount> memberaccountList = iMemberDao.selectMemberAccountByMemberID(membershipLevelId);
                membershipLevel.setMemberAccountList(memberaccountList);
                List<MMemberdivision> memberdivisionList = iMemberDao.selectMemberDivisionByMemberID(membershipLevelId);
                membershipLevel.setMemberDivisionList(memberdivisionList);
            }
            return ResponseResult.success(mMembershipLevelList);
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getCode(),
                ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getDesc()));
    }

    /**
     * @Description 显示所有会员等级
     * @Param [pageNum,pageSize]
     * @Return com.lnmj.account.common.response.ResponseResult;
     * @Author yilihua
     * @Date 2019/4/22
     * @Time 11:09
     */
    @Transactional
    @Override
    public ResponseResult selectAllMemberList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MMembershipLevel> mMembershipLevelList = iBaseDao.selectList("member.selectAllMemberList");
        if (mMembershipLevelList!=null && !mMembershipLevelList.isEmpty()) {
            //添加等级账户数据,添加等级股东数据
            for(MMembershipLevel membershipLevel:mMembershipLevelList){
                Long membershipLevelId = membershipLevel.getMembershipLevelId();
                List<MMemberaccount> memberaccountList = iMemberDao.selectMemberAccountByMemberID(membershipLevelId);
                membershipLevel.setMemberAccountList(memberaccountList);
                List<MMemberdivision> memberdivisionList = iMemberDao.selectMemberDivisionByMemberID(membershipLevelId);
                membershipLevel.setMemberDivisionList(memberdivisionList);
            }
            PageInfo pageInfo = new PageInfo(mMembershipLevelList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getCode(),
                ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getDesc()));
    }

    /**
     * @Description 按条件查询会员等级
     * @Param [mMembershipLevel]
     * @Return com.lnmj.account.common.response.ResponseResult;
     * @Author yilihua
     * @Date 2019/4/22
     * @Time 11:10
     */
    @Transactional
    @Override
    public ResponseResult selectMemberShipLevelList(int pageNum, int pageSize, MMembershipLevel mMembershipLevel) {
        PageHelper.startPage(pageNum, pageSize);
        List<MMembershipLevel> mMembershipLevelList = iMemberDao.selectMemberShipLevelList(mMembershipLevel);
        if (mMembershipLevelList!=null && !mMembershipLevelList.isEmpty()) {
            //添加等级账户数据,添加等级股东数据
            for(MMembershipLevel membershipLevel:mMembershipLevelList){
                Long membershipLevelId = membershipLevel.getMembershipLevelId();
                List<MMemberaccount> memberaccountList = iMemberDao.selectMemberAccountByMemberID(membershipLevelId);
                membershipLevel.setMemberAccountList(memberaccountList);
                List<MMemberdivision> memberdivisionList = iMemberDao.selectMemberDivisionByMemberID(membershipLevelId);
                membershipLevel.setMemberDivisionList(memberdivisionList);
            }
            PageInfo pageInfo = new PageInfo(mMembershipLevelList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getCode(),
                ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getDesc()));
    }

    /**
     * @Description 删除会员等级
     * @Param [mMembershipLevel]
     * @Return com.lnmj.account.common.response.ResponseResult;
     * @Author yilihua
     * @Date 2019/4/22
     * @Time 11:10
     */
    @Transactional
    @Override
    public ResponseResult deleteMemberShipLevelById(MMembershipLevel mMembershipLevel) {
        //是否是管理员，是否有修改权限
        /*Integer userType = iMemberDao.selectUserTypeByUserId(modifyOperatorId);
        if(userType.equals(UserTypeEnum.NORMAL_USER_ONLINE.getCode())){
            return ResponseResult.error(new Error(ResponseCodeEnum.NORMAL_USER_ONLINE_CAN_NOT_DO.getCode(),
                    ResponseCodeEnum. NORMAL_USER_ONLINE_CAN_NOT_DO.getDesc()+":删除会员等级"));
        }
        if(userType.equals(UserTypeEnum.NORMAL_USER_OFFLINE.getCode())){
            return ResponseResult.error(new Error(ResponseCodeEnum.NORMAL_USER_OFFLINE_CAN_NOT_DO.getCode(),
                    ResponseCodeEnum. NORMAL_USER_OFFLINE_CAN_NOT_DO.getDesc()+":删除会员等级"));
        }*/
        //需要判断要删除的等级下是否有用户，如果有用户则不能删除
        Long membershipLevelId = mMembershipLevel.getMembershipLevelId();
        List<String> userNames = iMemberDao.selectUsersFromUserByMembershipLevelId(membershipLevelId);
        if (!userNames.isEmpty()) {
            String result = "";
            for (int i = 0; i < userNames.size(); i++) {
                result = result + " " + i + ":" + userNames.get(i) + "";
            }
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.CAN_NOT_DELETE_MEMBER_SHIP.getCode(),
                    ResponseCodeAccountEnum.CAN_NOT_DELETE_MEMBER_SHIP.getDesc() + ":" + membershipLevelId + "里面有"
                            + userNames.size() + "个用户不能删除：" + result));
        }
        MMemberaccount memberaccount = new MMemberaccount();
        memberaccount.setMemberLevel(membershipLevelId);
        memberaccount.setModifyOperator(mMembershipLevel.getModifyOperator());
        iMemberDao.deleteMemberAccountByMemberID(memberaccount);
        MMemberdivision memberdivision = new MMemberdivision();
        memberdivision.setMemberLevel(membershipLevelId);
        memberdivision.setModifyOperator(mMembershipLevel.getModifyOperator());
        iMemberDao.deleteMemberDivisionByMemberID(memberdivision);
        return ResponseResult.success(iMemberDao.deleteMemberShipLevelById(mMembershipLevel));
    }

    /**
     * @Description 修改会员等级信息
     * @Param [mMembershipLevel]
     * @Return com.lnmj.account.common.response.ResponseResult;
     * @Author yilihua
     * @Date 2019/4/22
     * @Time 11:11
     */
    @Transactional
    @Override
    public ResponseResult updateMemberShipLevelById(MMembershipLevel mMembershipLevel) {
        //修改的会员等级是否存在,根据id判断
        Long membershipLevelId = mMembershipLevel.getMembershipLevelId();
        MMembershipLevel mMembershipLevel1 = new MMembershipLevel();
        mMembershipLevel1.setMembershipLevelId(membershipLevelId);
        List<MMembershipLevel> mMembershipLevels = iMemberDao.selectMemberShipLevelList(mMembershipLevel1);
        if (mMembershipLevels.size() <= 0) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getCode(),
                    ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getDesc() + ":" + membershipLevelId));
        }
        Integer status = mMembershipLevel.getStatus();
        //如果修改的会员等级是Status字段，则调用删除方法
        if (status != null) {
            if (status.equals(0)) {
                deleteMemberShipLevelById(mMembershipLevel);
            }
        }
        //修改会员等级
        Integer i = iMemberDao.updateMemberShipLevelById(mMembershipLevel);
        if(i != null && i>0){
            return ResponseResult.success(mMembershipLevel);
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.UPDATE_FAILUER.getCode(),
                ResponseCodeAccountEnum.UPDATE_FAILUER.getDesc()));
        //会员生效时间和使用范围(默认所有用户生效)
        //新用户生效，新增加会员等级（老用户保存了会员等级id，不能直接修改，且会员等级信息需要保存）
        //老用户生效，新增加会员等级（老用户用之前的会员等级，新用户用之后的会员等级）
        //所有用户生效，则直接修改会员等级
//        if(mMembershipLevel.getScopeOfUse()==null){
//            mMembershipLevel.setScopeOfUse(ScopeOfUseEnum.SCOPE_OF_USE_ALL.getCode());
//        }
//        MMembershipLevel membershipLevel = mMembershipLevels.get(0);
//        if(mMembershipLevel.getScopeOfUse().equals(ScopeOfUseEnum.SCOPE_OF_USE_ALL.getCode())){
//            //会员等级名称是否重复
//            String membershipLevelName = mMembershipLevel.getMembershipLevelName();
//            Long membershipLevelIdForSelect = mMembershipLevel.getMembershipLevelId();
//            if (!StringUtils.isBlank(membershipLevelName)) {
//                MMembershipLevel mMembershipLevelTemp = new MMembershipLevel();
//                mMembershipLevelTemp.setMembershipLevelName(membershipLevelName);
//                mMembershipLevelTemp.setMembershipLevelId(membershipLevelIdForSelect);
//                List<MMembershipLevel> lists = iMemberDao.selectMemberShipLevelList(mMembershipLevelTemp);
//                if (lists.size() > 0) {
//                    return ResponseResult.error(new Error(ResponseCodeAccountEnum.MEMBER_SHIP_LEVEL_NAME_REPEAT.getCode(),
//                            ResponseCodeAccountEnum.MEMBER_SHIP_LEVEL_NAME_REPEAT.getDesc() + ":" + mMembershipLevel.getMembershipLevelName()));
//                }
//            }
//            iMemberDao.updateMemberShipLevelById(mMembershipLevel);
//            return ResponseResult.success();
//        }
//        if( mMembershipLevel.getScopeOfUse().equals(ScopeOfUseEnum.SCOPE_OF_USE_NEW.getCode()) ||
//                mMembershipLevel.getScopeOfUse().equals(ScopeOfUseEnum.SCOPE_OF_USE_OLD.getCode())){
//            try {
//                BeanUtils.copyProperties(membershipLevel,mMembershipLevel);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//            membershipLevel.setMembershipLevelId(null);
//            membershipLevel.setCreateOperator(mMembershipLevel.getModifyOperator());
//            membershipLevel.setCreateTime(new Date());
//            membershipLevel.setModifyOperator(mMembershipLevel.getModifyOperator());
//            membershipLevel.setDataChangeLastTime(new Date());
//            iMemberDao.insertMemberShipLevel(membershipLevel);
//        }

    }

    /**
     * @Description 更新指定用户的会员等级
     * @Param [updateWalletAccountMemberShipLevelRequestVO]
     * @Return com.lnmj.account.common.response.ResponseResult;
     * @Author yilihua
     * @Date 2019/4/22
     * @Time 11:11
     */
    @Transactional
    @Override
    public ResponseResult updateWalletAccountMemberShipLevelById(UpdateAccountMemberShipLevelRequestVO updateWalletAccountMemberShipLevelRequestVO) {
        UpdateAccountMemberShipLevelVO updateWalletAccountMemberShipLevelVO = new UpdateAccountMemberShipLevelVO();
        String memberNum = updateWalletAccountMemberShipLevelRequestVO.getMemberNum();
        Long membershipLevelId = updateWalletAccountMemberShipLevelRequestVO.getMembershipLevelId();
        String modifyOperator = updateWalletAccountMemberShipLevelRequestVO.getModifyOperator();
        String operateDesc = MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_2.getDesc();        //操作描述
        if (!StringUtils.isBlank(updateWalletAccountMemberShipLevelRequestVO.getOperateDesc())) {
            operateDesc = updateWalletAccountMemberShipLevelRequestVO.getOperateDesc();
        }
        MMembershipLevel mMembershipLevel = new MMembershipLevel();
        mMembershipLevel.setMembershipLevelId(membershipLevelId);
        List<MMembershipLevel> mMembershipLevels = iMemberDao.selectMemberShipLevelList(mMembershipLevel);
        if (mMembershipLevels.isEmpty()) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getCode(),
                    ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getDesc() + "当前没有会员等级id为" + membershipLevelId + "的会员等级"));
        }
        if (mMembershipLevels.size() > 1) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.MEMBER_SHIP_REPEAT.getCode(),
                    ResponseCodeAccountEnum.MEMBER_SHIP_REPEAT.getDesc() + "当前会员等级id" + membershipLevelId + "重复"));
        }
        updateWalletAccountMemberShipLevelVO.setMemberNum(memberNum);
        //是否是管理员，是否有修改权限
        /*Integer userType = iMemberDao.selectUserTypeByUserId(modifyOperatorId);
        if(userType.equals(UserTypeEnum.NORMAL_USER_ONLINE.getCode())){
            return ResponseResult.error(new Error(ResponseCodeEnum.NORMAL_USER_ONLINE_CAN_NOT_DO.getCode(),
                    ResponseCodeEnum. NORMAL_USER_ONLINE_CAN_NOT_DO.getDesc()+":更新指定用户会员等级"));
        }
        if(userType.equals(UserTypeEnum.NORMAL_USER_OFFLINE.getCode())){
            return ResponseResult.error(new Error(ResponseCodeEnum.NORMAL_USER_OFFLINE_CAN_NOT_DO.getCode(),
                    ResponseCodeEnum. NORMAL_USER_OFFLINE_CAN_NOT_DO.getDesc()+":更新指定用户会员等级"));
        }*/
        updateWalletAccountMemberShipLevelVO.setModifyOperator(modifyOperator);
        //等级Id
        updateWalletAccountMemberShipLevelVO.setMembershipLevelId(membershipLevelId);
        //等级名称
        String membershipLevelName = mMembershipLevels.get(0).getMembershipLevelName();
        updateWalletAccountMemberShipLevelVO.setMembershipLevelName(membershipLevelName);
        //用户等级更新记录：
        MMembershipLevelRecords mMembershipLevelRecords = new MMembershipLevelRecords();
        mMembershipLevelRecords.setMemberNum(memberNum);
        //根据userId查询等级记录
        List<MMembershipLevelRecords>  mMembershipLevelRecordsList= iMemberDao.selectMemberShipLevelUpgradeList(mMembershipLevelRecords);
        // 用户当前等级,从用户表查询
        Map map = new HashMap();
        map.put("memberNum",memberNum);
        MemberUser memberUser = iMemberUserDao.selectMemberUserByMemberNum(map);
//        Long afterMemberShipLevel = iMemberDao.selectMemberShipLevelByUserId(userId);
        Long afterMemberShipLevel;
        if (memberUser.getMembershipLevelId() == null) { //如果查询到当前用户等级为空
            afterMemberShipLevel = new Long(0);
        }else {
            afterMemberShipLevel = memberUser.getMembershipLevelId();
        }
        mMembershipLevelRecords.setAfterMembershipLevelId(afterMemberShipLevel);
        //用户名，用户卡号
        String memberName = memberUser.getName();
        String cardNumber = memberUser.getMemberNum();
        mMembershipLevelRecords.setName(memberName);
        mMembershipLevelRecords.setMemberNum(memberNum);

        mMembershipLevelRecords.setLaterMembershipLevelId(membershipLevelId);
        mMembershipLevelRecords.setCreateOperator(modifyOperator);
        mMembershipLevelRecords.setMembershiplevelrecordsType(MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_2.getCode());   //管理员升级操作
        mMembershipLevelRecords.setOperateDesc(operateDesc);
        //金额(没有升级金额)
        mMembershipLevelRecords.setAmount(BigDecimal.ZERO);
        if( mMembershipLevelRecordsList == null||mMembershipLevelRecordsList.size()==0){
            mMembershipLevelRecords.setTotalAmount(BigDecimal.ZERO);
        }else{
            for(int i=0;i<mMembershipLevelRecordsList.size();i++){
                if(mMembershipLevelRecordsList.get(i).getLaterMembershipLevelId().equals(afterMemberShipLevel)){
                    mMembershipLevelRecords.setTotalAmount(mMembershipLevelRecordsList.get(i).getTotalAmount());
                }
            }
        }
        updateWalletAccountMemberShipLevelRecods(mMembershipLevelRecords);
        //最后更新用户等级
        return ResponseResult.success(iMemberDao.updateWalletAccountMemberShipLevelById(updateWalletAccountMemberShipLevelVO));
    }

    /**
     * @Description 用户等级更新记录
     * @Param [mMembershipLevelRecords]
     * @Return int
     * @Author yilihua
     * @Date 2019/4/22
     * @Time 15:07
     */
    private int updateWalletAccountMemberShipLevelRecods(MMembershipLevelRecords mMembershipLevelRecords) {
        //用户升级记录表插入数据
        int insertMembershipLevelRecords = iMemberDao.insertMembershipLevelRecords(mMembershipLevelRecords);
        return insertMembershipLevelRecords;
    }

    /**
     * @Description 充值后的等级（一次性充值达到标准才能升级）
     * @Param [walletRechargeRecordVO]
     * @Return com.lnmj.account.common.response.ResponseResult;
     * @Author yilihua
     * @Date 2019/4/23
     * @Time 18:03
     */
    @Transactional
    @Override
    public ResponseResult userRechargeUpgrade(WalletRechargeRecordRequestVO walletRechargeRecordVO) {
        //判断充值参数条件是否满足  （直接根据充值总金额判断用户是否可以升级）
        // userId;//用户id
        // rechargeAmount;//充值金额
        String memberNum = walletRechargeRecordVO.getMemberNum();
        BigDecimal rechargeAmount = walletRechargeRecordVO.getRechargeAmount();
        if(rechargeAmount.compareTo(BigDecimal.ZERO) <= 0){
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.AMOUNT_NOT_UP_STANDAR.getCode(),
                    ResponseCodeAccountEnum.AMOUNT_NOT_UP_STANDAR.getDesc()));
        }
        //用户当前等级信息
        Map map = new HashMap();
        map.put("memberNum",memberNum);
        MemberUser memberUser = iMemberUserDao.selectMemberUserByMemberNum(map);
        Long memberUserShipLevelId;
        if (memberUser.getMembershipLevelId() == null) { //如果查询到当前用户等级为空
            memberUserShipLevelId = new Long(0);
        }else {
            memberUserShipLevelId = memberUser.getMembershipLevelId();
        }
        String userName = memberUser.getName();
        String cardNumber = memberUser.getMemberNum();
/*      BigDecimal rechargeAmount = amount;
        总金额
        MMembershipLevelRecords membershipLevelRecords = new MMembershipLevelRecords();
        membershipLevelRecords.setUserId(userId);
        会员最后的充值升级记录
        membershipLevelRecords.setMembershiplevelrecordsType(MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_1.getCode());
        List<MMembershipLevelRecords> recordsList = iMemberDao.selectMemberShipLevelUpgradeList(membershipLevelRecords);
        if(recordsList.size()!=0){
            Collections.sort(recordsList, new Comparator<MMembershipLevelRecords>() {
                @Override
                public int compare(MMembershipLevelRecords o1, MMembershipLevelRecords o2) {
                    return o1.getCreateTime().compareTo(o2.getCreateTime());
                }
            });
            rechargeAmount = recordsList.get(recordsList.size()-1).getTotalAmount().add(amount);
        }
        当前所有等级信息
        List<MMembershipLevel> mMembershipLevelListTemp = iMemberDao.selectAllNormalMemberList();
        2.自动升级，且升级方式为金钱和粉丝
        List<MMembershipLevel> mMembershipLevelList2 = new ArrayList<>();
        //当前所有等级信息筛选（自动升级,粉丝和金钱）
        for(MMembershipLevel membershipLevel:mMembershipLevelListTemp){
            if(membershipLevel.getAutoUpgrade() && membershipLevel.getUpgradingWays().equals(UpgradingWaysEnum.UPGRADING_WAYS_1.getCode())){
                mMembershipLevelList2.add(membershipLevel);
            }
        }
        //3.自动升级，升级方式为粉丝
        List<MMembershipLevel> mMembershipLevelList3 = new ArrayList<>();
        //当前所有等级信息筛选（自动升级,粉丝升级）
        for(MMembershipLevel membershipLevel:mMembershipLevelListTemp){
            if(membershipLevel.getAutoUpgrade() && membershipLevel.getUpgradingWays().equals(UpgradingWaysEnum.UPGRADING_WAYS_2.getCode())){
                mMembershipLevelList3.add(membershipLevel);
            }
        }
        1.自动升级且升级方式对粉丝没有要求
        List<MMembershipLevel> mMembershipLevelList = new ArrayList<>();
        //当前所有等级信息筛选（自动升级,充值升级）
        for(MMembershipLevel membershipLevel:mMembershipLevelListTemp){
            if(membershipLevel.getAutoUpgrade() && membershipLevel.getUpgradingWays().equals(UpgradingWaysEnum.UPGRADING_WAYS_3.getCode())){
                mMembershipLevelList.add(membershipLevel);
            }
        }
        */
        //查询符合要求的等级
        MMembershipLevel membershipLevel = new MMembershipLevel();
        membershipLevel.setAutoUpgrade(true);
        membershipLevel.setUpgradingWays(String.valueOf(UpgradingWaysEnum.UPGRADING_WAYS_2.getCode()));
        List<MMembershipLevel> mMembershipLevelList = iMemberDao.selectMemberShipLevelList(membershipLevel);
        if(mMembershipLevelList==null||mMembershipLevelList.size()==0){
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getCode(),
                    ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getDesc()+"符合条件"));
        }
        //等级按照StandardAmount金额排序
        Collections.sort(mMembershipLevelList, new Comparator<MMembershipLevel>() {
            @Override
            public int compare(MMembershipLevel o1, MMembershipLevel o2) {
                //升序
                return o1.getBestieIntroduceAmount().compareTo(o2.getBestieIntroduceAmount());
            }
        });
        String operateDesc = MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_1.getDesc();        //操作描述
        //用户更新：
        UpdateAccountMemberShipLevelVO updateWalletAccountMemberShipLevelVO = new UpdateAccountMemberShipLevelVO();
        //设置用户信息
        updateWalletAccountMemberShipLevelVO.setMemberNum(memberNum);
        updateWalletAccountMemberShipLevelVO.setModifyOperator(userName);
        //用户等级更新记录：
        MMembershipLevelRecords mMembershipLevelRecords = new MMembershipLevelRecords();
        mMembershipLevelRecords.setMemberNum(memberNum);
        mMembershipLevelRecords.setName(userName);
        // 用户当前等级,从用户表查询
        mMembershipLevelRecords.setAfterMembershipLevelId(memberUserShipLevelId);
        mMembershipLevelRecords.setCreateOperator(userName);
        mMembershipLevelRecords.setMembershiplevelrecordsType(MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_1.getCode());
        mMembershipLevelRecords.setOperateDesc(operateDesc);
        mMembershipLevelRecords.setAmount(rechargeAmount);
        mMembershipLevelRecords.setTotalAmount(rechargeAmount);
        //用户当前等级的消费升级金额
        BigDecimal upgradeAmount = null;
        for(int i=0;i<mMembershipLevelList.size();i++){
            if(mMembershipLevelList.get(i).getMembershipLevelId().equals(memberUserShipLevelId)){
                upgradeAmount = mMembershipLevelList.get(i).getBestieIntroduceAmount();
            }
        }
        //用户可以升级到的等级
        Long id = null;
        String memberName = null;
        BigDecimal upgradeAmountN = null;
        //遍历每个符合条件的等级（从用户当前等级的后一个等级开始遍历）
        for(int i=0;i<mMembershipLevelList.size()-1;i++){
            int r = rechargeAmount.compareTo(mMembershipLevelList.get(i).getBestieIntroduceAmount());
            int r1 = rechargeAmount.compareTo(mMembershipLevelList.get(i+1).getBestieIntroduceAmount());

            //比最大的等级的钱还多
            if(r>0 && r1>=0){
                id = mMembershipLevelList.get(i+1).getMembershipLevelId();
                memberName = mMembershipLevelList.get(i+1).getMembershipLevelName();
                upgradeAmountN = mMembershipLevelList.get(i+1).getBestieIntroduceAmount();
            }
            //处于两个等级的中间
            if(r>0 && r1<0 ){
                id = mMembershipLevelList.get(i).getMembershipLevelId();
                memberName = mMembershipLevelList.get(i).getMembershipLevelName();
                upgradeAmountN = mMembershipLevelList.get(i).getBestieIntroduceAmount();
            }
        }
        if(upgradeAmount != null && upgradeAmountN != null && id != null && memberName !=null){
            //用户现在的金额要比升级的小
            if(upgradeAmount.compareTo(upgradeAmountN) < 0){
                updateWalletAccountMemberShipLevelVO.setMembershipLevelId(id);
                updateWalletAccountMemberShipLevelVO.setMembershipLevelName(memberName);

                mMembershipLevelRecords.setLaterMembershipLevelId(id);
                updateWalletAccountMemberShipLevelRecods(mMembershipLevelRecords);
                iMemberDao.updateWalletAccountMemberShipLevelById(updateWalletAccountMemberShipLevelVO);
                return ResponseResult.success();
            }
        }
        mMembershipLevelRecords.setLaterMembershipLevelId(memberUserShipLevelId);
        updateWalletAccountMemberShipLevelRecods(mMembershipLevelRecords);
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.AMOUNT_NOT_UP_STANDAR.getCode(),
                ResponseCodeAccountEnum.AMOUNT_NOT_UP_STANDAR.getDesc() + memberNum+"用户总充值金额" + rechargeAmount +
                        "还没有达到最低等级标准" + mMembershipLevelList.get(0).getBestieIntroduceAmount()));
    }

    /**
    *@Description  消费升级  自动升级
    *@Param [walletRechargeRecordVO]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/3
    *@Time 15:29
    */
    @Transactional
    @Override
    public ResponseResult consumptionUpgrade(WalletRechargeRecordRequestVO walletRechargeRecordVO){
        String memberNum = walletRechargeRecordVO.getMemberNum();
        BigDecimal amount = walletRechargeRecordVO.getRechargeAmount();
        //用户当前等级信息
        Map map = new HashMap();
        map.put("memberNum",memberNum);
        MemberUser memberUser = iMemberUserDao.selectMemberUserByMemberNum(map);
        Long memberUserShipLevelId;
        if (memberUser.getMembershipLevelId() == null) { //如果查询到当前用户等级为空
            memberUserShipLevelId = new Long(0);
        }else {
            memberUserShipLevelId = memberUser.getMembershipLevelId();
        }
        String name = memberUser.getName();
        String cardNumber = memberUser.getMemberNum();
        BigDecimal rechargeAmount = amount;
        //总金额
        MMembershipLevelRecords membershipLevelRecords = new MMembershipLevelRecords();
        membershipLevelRecords.setMemberNum(memberNum);
        //会员最后的消费升级记录和消费降级记录
        membershipLevelRecords.setMembershiplevelrecordsType(MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_3.getCode());
        List<MMembershipLevelRecords> recordsList = iMemberDao.selectMemberShipLevelUpgradeList(membershipLevelRecords);
        membershipLevelRecords.setMembershiplevelrecordsType(MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_5.getCode());
        recordsList.addAll(iMemberDao.selectMemberShipLevelUpgradeList(membershipLevelRecords));
        if(recordsList.size()!=0){
            Collections.sort(recordsList, new Comparator<MMembershipLevelRecords>() {
                @Override
                public int compare(MMembershipLevelRecords o1, MMembershipLevelRecords o2) {
                    return o1.getCreateTime().compareTo(o2.getCreateTime());
                }
            });
            rechargeAmount = recordsList.get(recordsList.size()-1).getTotalAmount().add(amount);
        }
        //查询符合要求的等级
        MMembershipLevel membershipLevel = new MMembershipLevel();
        membershipLevel.setAutoUpgrade(true);
        membershipLevel.setUpgradingWays(String.valueOf(UpgradingWaysEnum.UPGRADING_WAYS_3.getCode()));
        List<MMembershipLevel> mMembershipLevelList = iMemberDao.selectMemberShipLevelList(membershipLevel);
        if(mMembershipLevelList==null||mMembershipLevelList.size()==0){
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getCode(),
                    ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getDesc()+"符合条件"));
        }
        //等级按照StandardAmount金额排序
        Collections.sort(mMembershipLevelList, new Comparator<MMembershipLevel>() {
            @Override
            public int compare(MMembershipLevel o1, MMembershipLevel o2) {
                //升序
                return o1.getUpgradeStandardAmount().compareTo(o2.getUpgradeStandardAmount());
            }
        });

        String operateDesc = MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_3.getDesc();        //操作描述
        UpdateAccountMemberShipLevelVO updateWalletAccountMemberShipLevelVO = new UpdateAccountMemberShipLevelVO();
        //设置用户等级
        updateWalletAccountMemberShipLevelVO.setMemberNum(memberNum);
        updateWalletAccountMemberShipLevelVO.setModifyOperator(name);
        //用户等级更新记录：
        MMembershipLevelRecords mMembershipLevelRecords = new MMembershipLevelRecords();
        mMembershipLevelRecords.setMemberNum(memberNum);
        mMembershipLevelRecords.setName(name);
        // 用户当前等级,从用户表查询
        mMembershipLevelRecords.setAfterMembershipLevelId(memberUserShipLevelId);
        mMembershipLevelRecords.setCreateOperator(name);
        mMembershipLevelRecords.setMembershiplevelrecordsType(MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_3.getCode());
        mMembershipLevelRecords.setOperateDesc(operateDesc);
        mMembershipLevelRecords.setAmount(amount);
        mMembershipLevelRecords.setTotalAmount(rechargeAmount);
        //用户当前等级的消费升级金额
        BigDecimal standAmount = null;
        for(int i=0;i<mMembershipLevelList.size();i++){
            if(mMembershipLevelList.get(i).getMembershipLevelId() == memberUserShipLevelId ){
                standAmount = mMembershipLevelList.get(i).getUpgradeStandardAmount();
            }
        }
        //用户可以升级到的等级
        Long id = null;
        String memberName = null;
        BigDecimal standAmountN = null;
        //遍历每个符合条件的等级（从用户当前等级的后一个等级开始遍历）
        for(int i=0;i<mMembershipLevelList.size()-1;i++){
            int r = rechargeAmount.compareTo(mMembershipLevelList.get(i).getUpgradeStandardAmount());
            int r1 = rechargeAmount.compareTo(mMembershipLevelList.get(i+1).getUpgradeStandardAmount());

            //比最大的等级的钱还多
            if(r>0 && r1>=0){
                id = mMembershipLevelList.get(i+1).getMembershipLevelId();
                memberName = mMembershipLevelList.get(i+1).getMembershipLevelName();
                standAmountN = mMembershipLevelList.get(i+1).getUpgradeStandardAmount();
            }
            //处于两个等级的中间
            if(r>0 && r1<0 ){
                id = mMembershipLevelList.get(i).getMembershipLevelId();
                memberName = mMembershipLevelList.get(i).getMembershipLevelName();
                standAmountN = mMembershipLevelList.get(i).getUpgradeStandardAmount();
            }
        }
        if(standAmount != null && standAmountN != null && id != null && memberName !=null){
            //用户现在的金额要比升级的小
            if(standAmount.compareTo(standAmountN) < 0){
                updateWalletAccountMemberShipLevelVO.setMembershipLevelId(id);
                updateWalletAccountMemberShipLevelVO.setMembershipLevelName(memberName);

                mMembershipLevelRecords.setLaterMembershipLevelId(id);
                updateWalletAccountMemberShipLevelRecods(mMembershipLevelRecords);
                iMemberDao.updateWalletAccountMemberShipLevelById(updateWalletAccountMemberShipLevelVO);
                return ResponseResult.success();
            }
        }
        //更新记录表
        mMembershipLevelRecords.setLaterMembershipLevelId(memberUserShipLevelId);
        updateWalletAccountMemberShipLevelRecods(mMembershipLevelRecords);
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.CONSUMPTION_NOT_UP_STANDAR.getCode(),
                ResponseCodeAccountEnum.CONSUMPTION_NOT_UP_STANDAR.getDesc() + memberNum+"用户总消费金额" + amount +
                        "还没有达到最低等级标准" + mMembershipLevelList.get(0).getUpgradeStandardAmount()));
    }
    /**
    *@Description 粉丝升级
    *@Param [userId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/16
    *@Time 15:52
    */
    @Transactional
    @Override
    public ResponseResult fansUpgrade(String memberNum) {
        //查询用户的粉丝
        Integer fans = iMemberDao.selectParentIdsByMemberNum(memberNum);
        if(fans == 0){
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.FANS_NOT_UP_STANDAR.getCode(),
                    ResponseCodeAccountEnum.FANS_NOT_UP_STANDAR.getDesc()));
        }
        //查询会员等级
        Map map = new HashMap();
        map.put("memberNum",memberNum);
        MemberUser memberUser = iMemberUserDao.selectMemberUserByMemberNum(map);
        Long memberUserShipLevelId;
        if (memberUser.getMembershipLevelId() == null) { //如果查询到当前用户等级为空
            memberUserShipLevelId = new Long(0);
        }else {
            memberUserShipLevelId =memberUser.getMembershipLevelId();
        }
        String name = memberUser.getName();
        String cardNumber = memberUser.getMemberNum();
        //查询符合要求的等级
        MMembershipLevel membershipLevel = new MMembershipLevel();
        membershipLevel.setAutoUpgrade(true);
        membershipLevel.setUpgradingWays(String.valueOf(UpgradingWaysEnum.UPGRADING_WAYS_4.getCode()));
        List<MMembershipLevel> mMembershipLevelList = iMemberDao.selectMemberShipLevelList(membershipLevel);
        if(mMembershipLevelList==null||mMembershipLevelList.size()==0){
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getCode(),
                    ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getDesc()+"符合条件"));
        }
        //等级按照UpgradeStandardFans金额排序
        Collections.sort(mMembershipLevelList, new Comparator<MMembershipLevel>() {
            @Override
            public int compare(MMembershipLevel o1, MMembershipLevel o2) {
                //升序
                return o1.getUpgradeStandardFans().compareTo(o2.getUpgradeStandardFans());
            }
        });
        String operateDesc = MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_4.getDesc();        //操作描述
        UpdateAccountMemberShipLevelVO updateWalletAccountMemberShipLevelVO = new UpdateAccountMemberShipLevelVO();
        //设置用户等级
        updateWalletAccountMemberShipLevelVO.setMemberNum(memberNum);
        updateWalletAccountMemberShipLevelVO.setModifyOperator(name);
        //用户等级更新记录：
        MMembershipLevelRecords mMembershipLevelRecords = new MMembershipLevelRecords();
        mMembershipLevelRecords.setMemberNum(memberNum);
        mMembershipLevelRecords.setName(name);
        // 用户当前等级,从用户表查询
        mMembershipLevelRecords.setAfterMembershipLevelId(memberUserShipLevelId);
        mMembershipLevelRecords.setCreateOperator(name);
        mMembershipLevelRecords.setMembershiplevelrecordsType(MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_4.getCode());
        mMembershipLevelRecords.setOperateDesc(operateDesc);
//        mMembershipLevelRecords.setAmount(BigDecimal.ZERO);
//        mMembershipLevelRecords.setTotalAmount(BigDecimal.ZERO);
        //用户当前等级的粉丝
        Integer userFans = null;
        for(int i=0;i<mMembershipLevelList.size();i++){
            if(mMembershipLevelList.get(i).getMembershipLevelId() == memberUserShipLevelId ){
                userFans = mMembershipLevelList.get(i).getUpgradeStandardFans();
            }
        }
        //用户可以升级到的等级
        Long id = null;
        String memberName = null;
        Integer userFansN = null;
        //遍历每个符合条件的等级（从用户当前等级的后一个等级开始遍历）
        for(int i=0;i<mMembershipLevelList.size()-1;i++){
            int r = fans.compareTo(mMembershipLevelList.get(i).getUpgradeStandardFans());
            int r1 = fans.compareTo(mMembershipLevelList.get(i+1).getUpgradeStandardFans());

            //比最大的等级的粉丝还多
            if(r>0 && r1>=0){
                id = mMembershipLevelList.get(i+1).getMembershipLevelId();
                memberName = mMembershipLevelList.get(i+1).getMembershipLevelName();
                userFansN = mMembershipLevelList.get(i+1).getUpgradeStandardFans();
            }
            //处于两个等级的中间
            if(r>0 && r1<0 ){
                id = mMembershipLevelList.get(i).getMembershipLevelId();
                memberName = mMembershipLevelList.get(i).getMembershipLevelName();
                userFansN = mMembershipLevelList.get(i).getUpgradeStandardFans();
            }
        }
        if(userFans != null && userFansN != null && id != null && memberName !=null){
            //用户现在的金额要比升级的小
            if(userFans.compareTo(userFansN) < 0){
                updateWalletAccountMemberShipLevelVO.setMembershipLevelId(id);
                updateWalletAccountMemberShipLevelVO.setMembershipLevelName(memberName);

                mMembershipLevelRecords.setLaterMembershipLevelId(id);
                updateWalletAccountMemberShipLevelRecods(mMembershipLevelRecords);
                iMemberDao.updateWalletAccountMemberShipLevelById(updateWalletAccountMemberShipLevelVO);
                return ResponseResult.success();
            }
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.FANS_NOT_UP_STANDAR.getCode(),
                ResponseCodeAccountEnum.FANS_NOT_UP_STANDAR.getDesc()));
    }
    /**
    *@Description 退款降级(消费退款)
    *@Param [walletRechargeRecordVO]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/16
    *@Time 16:47
    */
    @Override
    public ResponseResult returnLevel(WalletRechargeRecordRequestVO walletRechargeRecordVO){
        String memberNum = walletRechargeRecordVO.getMemberNum();
        BigDecimal amount = walletRechargeRecordVO.getRechargeAmount();
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECHARGE_AMOUNT_LESS_ZERO.getCode(),
                    ResponseCodeAccountEnum.RECHARGE_AMOUNT_LESS_ZERO.getDesc()));
        }
        //会员现在等级
        Map map = new HashMap();
        map.put("memberNum",memberNum);
        MemberUser memberUser = iMemberUserDao.selectMemberUserByMemberNum(map);
        Long membershipLevelId = null;
        if(memberUser.getMembershipLevelId() != null){
            membershipLevelId = memberUser.getMembershipLevelId();
        }
        String name = memberUser.getName();
        String cardNumber = memberUser.getMemberNum();
        BigDecimal rechargeAmount = null;
        //总金额
        MMembershipLevelRecords membershipLevelRecords = new MMembershipLevelRecords();
        membershipLevelRecords.setMemberNum(memberNum);
        //会员最后的消费升级记录和消费降级记录
        membershipLevelRecords.setMembershiplevelrecordsType(MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_3.getCode());
        List<MMembershipLevelRecords> recordsList = iMemberDao.selectMemberShipLevelUpgradeList(membershipLevelRecords);
        membershipLevelRecords.setMembershiplevelrecordsType(MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_5.getCode());
        recordsList.addAll(iMemberDao.selectMemberShipLevelUpgradeList(membershipLevelRecords));
        if(recordsList.size()!=0){
            Collections.sort(recordsList, new Comparator<MMembershipLevelRecords>() {
                @Override
                public int compare(MMembershipLevelRecords o1, MMembershipLevelRecords o2) {
                    return o1.getCreateTime().compareTo(o2.getCreateTime());
                }
            });
            rechargeAmount = recordsList.get(recordsList.size()-1).getTotalAmount().subtract(amount);   //减去退款金额
        }
        //获取所有消费升级的等级
        MMembershipLevel membershipLevelTemp = new MMembershipLevel();
        membershipLevelTemp.setAutoUpgrade(true);
        membershipLevelTemp.setUpgradingWays(String.valueOf(UpgradingWaysEnum.UPGRADING_WAYS_3.getCode()));
        List<MMembershipLevel> mMembershipLevelList = iMemberDao.selectMemberShipLevelList(membershipLevelTemp);
        if(mMembershipLevelList==null||mMembershipLevelList.size()==0){
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getCode(),
                    ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getDesc()+"符合条件"));
        }
        //排序
        Collections.sort(mMembershipLevelList, new Comparator<MMembershipLevel>() {
            @Override
            public int compare(MMembershipLevel o1, MMembershipLevel o2) {
                return o1.getUpgradeStandardAmount().compareTo(o2.getUpgradeStandardAmount());
            }
        });

        String operateDesc = MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_5.getDesc();        //操作描述
        UpdateAccountMemberShipLevelVO updateWalletAccountMemberShipLevelVO = new UpdateAccountMemberShipLevelVO();
        //设置用户等级
        updateWalletAccountMemberShipLevelVO.setMemberNum(memberNum);
        updateWalletAccountMemberShipLevelVO.setModifyOperator(name);
        //用户等级更新记录：
        MMembershipLevelRecords mMembershipLevelRecords = new MMembershipLevelRecords();
        mMembershipLevelRecords.setMemberNum(memberNum);
        mMembershipLevelRecords.setName(name);
        // 用户当前等级,从用户表查询
        mMembershipLevelRecords.setAfterMembershipLevelId(membershipLevelId);
        mMembershipLevelRecords.setCreateOperator(name);
        mMembershipLevelRecords.setMembershiplevelrecordsType(MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_5.getCode());
        mMembershipLevelRecords.setOperateDesc(operateDesc);
        mMembershipLevelRecords.setAmount(amount.negate()); //负数的金额
        mMembershipLevelRecords.setTotalAmount(rechargeAmount);
        //用户当前等级的消费升级金额
        BigDecimal standAmount = null;
        for(int i=0;i<mMembershipLevelList.size();i++){
            if(mMembershipLevelList.get(i).getMembershipLevelId() == membershipLevelId ){
                standAmount = mMembershipLevelList.get(i).getUpgradeStandardAmount();
            }
        }
        //用户可以升级到的等级
        Long id = null;
        String memberName = null;
        BigDecimal standAmountN = null;
        //遍历每个符合条件的等级（从用户当前等级的后一个等级开始遍历）
        for(int i=0;i<mMembershipLevelList.size()-1;i++){
            int r = rechargeAmount.compareTo(mMembershipLevelList.get(i).getUpgradeStandardAmount());
            int r1 = rechargeAmount.compareTo(mMembershipLevelList.get(i+1).getUpgradeStandardAmount());

            //比最大的等级的钱还多
            if(r>0 && r1>=0){
                id = mMembershipLevelList.get(i+1).getMembershipLevelId();
                memberName = mMembershipLevelList.get(i+1).getMembershipLevelName();
                standAmountN = mMembershipLevelList.get(i+1).getUpgradeStandardAmount();
            }
            //处于两个等级的中间
            if(r>0 && r1<0 ){
                id = mMembershipLevelList.get(i).getMembershipLevelId();
                memberName = mMembershipLevelList.get(i).getMembershipLevelName();
                standAmountN = mMembershipLevelList.get(i).getStandardAmount();
            }
        }
        if(standAmount != null && standAmountN != null && id != null && memberName !=null){
            //用户现在的金额要比升级的小
            if(standAmount.compareTo(standAmountN) < 0){
                updateWalletAccountMemberShipLevelVO.setMembershipLevelId(id);
                updateWalletAccountMemberShipLevelVO.setMembershipLevelName(memberName);

                mMembershipLevelRecords.setLaterMembershipLevelId(id);
                updateWalletAccountMemberShipLevelRecods(mMembershipLevelRecords);
                iMemberDao.updateWalletAccountMemberShipLevelById(updateWalletAccountMemberShipLevelVO);
                return ResponseResult.success();
            }
        }
        //更新记录表
        mMembershipLevelRecords.setLaterMembershipLevelId(membershipLevelId);
        updateWalletAccountMemberShipLevelRecods(mMembershipLevelRecords);
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.CONSUMPTION_NOT_UP_STANDAR.getCode(),
                ResponseCodeAccountEnum.CONSUMPTION_NOT_UP_STANDAR.getDesc() + memberNum+"用户退款金额" + amount +
                        "还没有达到降级标准"));
    }

    /**
    *@Description 查看用户的会员升级记录
    *@Param [pageNum, pageSize, mMembershipLevelRecords]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/16
    *@Time 16:30
    */
    @Transactional
    @Override
    public ResponseResult selectMemberShipLevelUpgradeList(int pageNum, int pageSize, MMembershipLevelRecords mMembershipLevelRecords) {
        PageHelper.startPage(pageNum, pageSize);
        List<MMembershipLevelRecords> mMembershipLevelRecordsList = iMemberDao.selectMemberShipLevelUpgradeList(mMembershipLevelRecords);
        if (!mMembershipLevelRecordsList.isEmpty()) {
            PageInfo<MMembershipLevelRecords> pageInfo = new PageInfo<>(mMembershipLevelRecordsList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL_UPDRADING_RECORDS.getCode(),
                ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL_UPDRADING_RECORDS.getDesc()));
    }

    /**
    *@Description 查询等级会员金额记录
    *@Param [pageNum, pageSize, memberamountrecord]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/16
    *@Time 16:30
    */
    @Transactional
    @Override
    public ResponseResult selectMemberAmountRecordByCondition(int pageNum, int pageSize, MMemberamountrecord memberamountrecord) {
        PageHelper.startPage(pageNum, pageSize);
        List<MMemberamountrecord> memberamountrecordList = iMemberDao.selectMemberAmountRecordByCondition(memberamountrecord);
        if (!memberamountrecordList.isEmpty()) {
            PageInfo<MMemberamountrecord> pageInfo = new PageInfo<>(memberamountrecordList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.NOT_MEMBER_AMOUNT_UPDRADING_RECORDS.getCode(),
                ResponseCodeAccountEnum.NOT_MEMBER_AMOUNT_UPDRADING_RECORDS.getDesc()));
    }

    /**
    *@Description 插入等级会员金额记录
    *@Param [memberamountrecord]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/16
    *@Time 16:29
    */
    @Transactional
    @Override
    public ResponseResult insertMemberAmountRecord(MMemberamountrecord memberamountrecord) {
        int i = iMemberDao.insertMemberAmountRecord(memberamountrecord);
        return ResponseResult.success(i);
    }
    
    /**
    *@Description 用户累计充值升级
    *@Param [walletRechargeRecordVO]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019-12-14
    *@Time 16:28
    */
    @Override
    public ResponseResult userRechargeUpgradeAdd(WalletRechargeRecordRequestVO walletRechargeRecordVO) {
        String memberNum = walletRechargeRecordVO.getMemberNum();
        BigDecimal amount = walletRechargeRecordVO.getRechargeAmount();
        //用户当前等级信息
        Map map = new HashMap();
        map.put("memberNum",memberNum);
        MemberUser memberUser = iMemberUserDao.selectMemberUserByMemberNum(map);
        Long memberUserShipLevelId;
        if (memberUser.getMembershipLevelId() == null) { //如果查询到当前用户等级为空
            memberUserShipLevelId = new Long(0);
        }else {
            memberUserShipLevelId = memberUser.getMembershipLevelId();
        }
        String name = memberUser.getName();
        String cardNumber = memberUser.getMemberNum();
        BigDecimal rechargeAmount = amount;
        //总金额
        MMembershipLevelRecords membershipLevelRecords = new MMembershipLevelRecords();
        membershipLevelRecords.setMemberNum(memberNum);
        //会员最后的充值升级记录
        membershipLevelRecords.setMembershiplevelrecordsType(MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_1.getCode());
        List<MMembershipLevelRecords> recordsList = iMemberDao.selectMemberShipLevelUpgradeList(membershipLevelRecords);
        if(recordsList!=null && recordsList.size()!=0){
            Collections.sort(recordsList, new Comparator<MMembershipLevelRecords>() {
                @Override
                public int compare(MMembershipLevelRecords o1, MMembershipLevelRecords o2) {
                    return o1.getCreateTime().compareTo(o2.getCreateTime());
                }
            });
            rechargeAmount = recordsList.get(recordsList.size()-1).getTotalAmount().add(amount);
        }
        //查询符合要求的等级
        MMembershipLevel membershipLevel = new MMembershipLevel();
        membershipLevel.setAutoUpgrade(true);
        membershipLevel.setUpgradingWays(String.valueOf(UpgradingWaysEnum.UPGRADING_WAYS_1.getCode()));
        List<MMembershipLevel> mMembershipLevelList = iMemberDao.selectMemberShipLevelList(membershipLevel);
        if(mMembershipLevelList==null||mMembershipLevelList.size()==0){
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getCode(),
                    ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getDesc()+"符合条件"));
        }
        //等级按照StandardAmount金额排序
        Collections.sort(mMembershipLevelList, new Comparator<MMembershipLevel>() {
            @Override
            public int compare(MMembershipLevel o1, MMembershipLevel o2) {
                //升序
                return o1.getStandardAmount().compareTo(o2.getStandardAmount());
            }
        });

        String operateDesc = MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_1.getDesc();        //操作描述
        UpdateAccountMemberShipLevelVO updateWalletAccountMemberShipLevelVO = new UpdateAccountMemberShipLevelVO();
        //设置用户等级
        updateWalletAccountMemberShipLevelVO.setMemberNum(memberNum);
        updateWalletAccountMemberShipLevelVO.setModifyOperator(name);
        //用户等级更新记录：
        MMembershipLevelRecords mMembershipLevelRecords = new MMembershipLevelRecords();
        mMembershipLevelRecords.setMemberNum(memberNum);
        mMembershipLevelRecords.setName(name);
        // 用户当前等级,从用户表查询
        mMembershipLevelRecords.setAfterMembershipLevelId(memberUserShipLevelId);
        mMembershipLevelRecords.setCreateOperator(name);
        mMembershipLevelRecords.setMembershiplevelrecordsType(MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_1.getCode());
        mMembershipLevelRecords.setOperateDesc(operateDesc);
        mMembershipLevelRecords.setAmount(amount);
        mMembershipLevelRecords.setTotalAmount(rechargeAmount);
        //用户当前等级的消费升级金额
        BigDecimal standAmount = null;
        for(int i=0;i<mMembershipLevelList.size();i++){
            if(mMembershipLevelList.get(i).getMembershipLevelId() == memberUserShipLevelId ){
                standAmount = mMembershipLevelList.get(i).getStandardAmount();
            }
        }
        //用户可以升级到的等级
        Long id = null;
        String memberName = null;
        BigDecimal standAmountN = null;
        //遍历每个符合条件的等级（从用户当前等级的后一个等级开始遍历）
        for(int i=0;i<mMembershipLevelList.size()-1;i++){
            int r = rechargeAmount.compareTo(mMembershipLevelList.get(i).getStandardAmount());
            int r1 = rechargeAmount.compareTo(mMembershipLevelList.get(i+1).getStandardAmount());

            //比最大的等级的钱还多
            if(r>0 && r1>=0){
                id = mMembershipLevelList.get(i+1).getMembershipLevelId();
                memberName = mMembershipLevelList.get(i+1).getMembershipLevelName();
                standAmountN = mMembershipLevelList.get(i+1).getStandardAmount();
            }
            //处于两个等级的中间
            if(r>0 && r1<0 ){
                id = mMembershipLevelList.get(i).getMembershipLevelId();
                memberName = mMembershipLevelList.get(i).getMembershipLevelName();
                standAmountN = mMembershipLevelList.get(i).getStandardAmount();
            }
        }
        if(standAmount != null && standAmountN != null && id != null && memberName !=null){
            //用户现在的金额要比升级的小
            if(standAmount.compareTo(standAmountN) < 0){
                updateWalletAccountMemberShipLevelVO.setMembershipLevelId(id);
                updateWalletAccountMemberShipLevelVO.setMembershipLevelName(memberName);

                mMembershipLevelRecords.setLaterMembershipLevelId(id);
                updateWalletAccountMemberShipLevelRecods(mMembershipLevelRecords);
                iMemberDao.updateWalletAccountMemberShipLevelById(updateWalletAccountMemberShipLevelVO);
                return ResponseResult.success();
            }
        }
        //更新记录表
        mMembershipLevelRecords.setLaterMembershipLevelId(memberUserShipLevelId);
        updateWalletAccountMemberShipLevelRecods(mMembershipLevelRecords);
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.CONSUMPTION_NOT_UP_STANDAR.getCode(),
                ResponseCodeAccountEnum.CONSUMPTION_NOT_UP_STANDAR.getDesc() + memberNum+"用户总消费金额" + amount +
                        "还没有达到最低等级标准" + mMembershipLevelList.get(0).getStandardAmount()));
    }

    @Override
    public ResponseResult memberUserUpdate(String memberNumber) {
        //=================================================用户信息=====================================
        //用户当前等级信息
        Map map = new HashMap();
        map.put("memberNum",memberNumber);
        MemberUser memberUser = iMemberUserDao.selectMemberUserByMemberNum(map);
        Long memberUserShipLevelId;
        if (memberUser.getMembershipLevelId() == null) { //如果查询到当前用户等级为空
            memberUserShipLevelId = new Long(0);
        }else {
            memberUserShipLevelId = memberUser.getMembershipLevelId();
        }
        String name = memberUser.getName();
        //====================================================等级信息==============================
        //累计充值升级的等级
        MMembershipLevel membershipLevel = new MMembershipLevel();
        membershipLevel.setAutoUpgrade(true);
        membershipLevel.setUpgradingWays(String.valueOf(UpgradingWaysEnum.UPGRADING_WAYS_1.getCode()));
        List<MMembershipLevel> rechargelevel = iMemberDao.selectMemberShipLevelList(membershipLevel);
        //累计消费升级的等级
        membershipLevel.setUpgradingWays(String.valueOf(UpgradingWaysEnum.UPGRADING_WAYS_3.getCode()));
        List<MMembershipLevel> cashlevel = iMemberDao.selectMemberShipLevelList(membershipLevel);
        //单次充值
        membershipLevel.setUpgradingWays(String.valueOf(UpgradingWaysEnum.UPGRADING_WAYS_2.getCode()));
        List<MMembershipLevel> oneRechargelevel = iMemberDao.selectMemberShipLevelList(membershipLevel);

        //会员总充值金额
        BigDecimal rechargeAll = new BigDecimal(0);
        //会员一次性充值最大金额
        BigDecimal rechargeMax = new BigDecimal(0);
        ResponseResult responseResult = walletApi.selectSumAmountAll(memberNumber);
        if(responseResult!=null && responseResult.isSuccess()){
            HashMap<String,Object> result = (HashMap<String,Object>)responseResult.getResult();
            rechargeAll = new BigDecimal((Double)result.get("rechargeAll"));
            rechargeMax = new BigDecimal((Double)result.get("rechargeMax"));
        }
        //会员总消费金额
        //订单
        BigDecimal memberCashAll = new BigDecimal(0);
        BigDecimal memberCashAll1 = new BigDecimal(0);
        BigDecimal memberCashAll2 = new BigDecimal(0);
        ResponseResult responseResult1 = orderApi.selectMemberCashAll(memberNumber);
        if(responseResult1!=null&&responseResult1.isSuccess()){
            HashMap<String,Object> result = (HashMap<String,Object>)responseResult1.getResult();
            memberCashAll1 = new BigDecimal((Integer)result.get("memberCashAll"));
        }
        //体验卡订单
        ResponseResult responseResult2 = storeApi.selectExpOrderCashAll(memberNumber);
        if(responseResult2!=null&&responseResult2.isSuccess()){
            HashMap<String,Object> result = (HashMap<String,Object>)responseResult2.getResult();
            memberCashAll2 = new BigDecimal((Integer)result.get("memberCashAll"));
        }
        memberCashAll = memberCashAll1.add(memberCashAll2);

        //======================================升级对象及升级记录对象==============================
        UpdateAccountMemberShipLevelVO updateWalletAccountMemberShipLevelVO = new UpdateAccountMemberShipLevelVO();
        //设置用户等级
        updateWalletAccountMemberShipLevelVO.setMemberNum(memberNumber);
        updateWalletAccountMemberShipLevelVO.setModifyOperator("");     //自动升级，暂无等级
        //用户等级更新记录：
        MMembershipLevelRecords mMembershipLevelRecords = new MMembershipLevelRecords();
        mMembershipLevelRecords.setMemberNum(memberNumber);
        mMembershipLevelRecords.setName(name);
        // 用户当前等级,从用户表查询
        mMembershipLevelRecords.setAfterMembershipLevelId(memberUserShipLevelId);
        mMembershipLevelRecords.setCreateOperator("");
//        mMembershipLevelRecords.setAmount(amount);
//        mMembershipLevelRecords.setTotalAmount(rechargeAmount);
        //======================================判断会员总充值的等级==============================
        if(rechargelevel==null||rechargelevel.size()==0){
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getCode(),
                    ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getDesc()+"符合条件"));
        }
        //等级按照StandardAmount金额排序
        Collections.sort(rechargelevel, new Comparator<MMembershipLevel>() {
            @Override
            public int compare(MMembershipLevel o1, MMembershipLevel o2) {
                //升序
                return o1.getStandardAmount().compareTo(o2.getStandardAmount());
            }
        });
        //用户可以升级到的等级
        Long level1 = null;
        String memberName1 = null;
        BigDecimal standAmountN1 = null; //等级升级的总金额
        //遍历每个符合条件的等级（从用户当前等级的后一个等级开始遍历）
        for(int i=0;i<rechargelevel.size()-1;i++){
            int r = rechargeAll.compareTo(rechargelevel.get(i).getStandardAmount());
            int r1 = rechargeAll.compareTo(rechargelevel.get(i+1).getStandardAmount());

            //比最大的等级的钱还多
            if(r>0 && r1>=0){
                level1 = rechargelevel.get(i+1).getMembershipLevelId();
                memberName1 = rechargelevel.get(i+1).getMembershipLevelName();
                standAmountN1 = rechargelevel.get(i+1).getStandardAmount();
            }
            //处于两个等级的中间
            if(r>=0 && r1<0 ){
                level1 = rechargelevel.get(i).getMembershipLevelId();
                memberName1 = rechargelevel.get(i).getMembershipLevelName();
                standAmountN1 = rechargelevel.get(i).getStandardAmount();
            }
        }

        //====================================判断会员一次性充值的等级====================
        if(oneRechargelevel==null||oneRechargelevel.size()==0){
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getCode(),
                    ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getDesc()+"符合条件"));
        }
        //等级按照BestieIntroduceAmount金额排序
        Collections.sort(oneRechargelevel, new Comparator<MMembershipLevel>() {
            @Override
            public int compare(MMembershipLevel o1, MMembershipLevel o2) {
                //升序
                return o1.getBestieIntroduceAmount().compareTo(o2.getBestieIntroduceAmount());
            }
        });
        //用户可以升级到的等级
        Long level2 = null;
        String memberName2 = null;
        BigDecimal standAmountN2 = null; //等级升级的总金额
        //遍历每个符合条件的等级（从用户当前等级的后一个等级开始遍历）
        for(int i=0;i<oneRechargelevel.size()-1;i++){
            int r = rechargeMax.compareTo(oneRechargelevel.get(i).getBestieIntroduceAmount());
            int r1 = rechargeMax.compareTo(oneRechargelevel.get(i+1).getBestieIntroduceAmount());

            //比最大的等级的钱还多
            if(r>0 && r1>=0){
                level2 = oneRechargelevel.get(i+1).getMembershipLevelId();
                memberName2 = oneRechargelevel.get(i+1).getMembershipLevelName();
                standAmountN2 = oneRechargelevel.get(i+1).getBestieIntroduceAmount();
            }
            //处于两个等级的中间
            if(r>=0 && r1<0 ){
                level2 = oneRechargelevel.get(i).getMembershipLevelId();
                memberName2 = oneRechargelevel.get(i).getMembershipLevelName();
                standAmountN2 = oneRechargelevel.get(i).getBestieIntroduceAmount();
            }
        }

        //=======================================判断会员总消费的等级=========================
        if(cashlevel==null||cashlevel.size()==0){
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getCode(),
                    ResponseCodeAccountEnum.NOT_MEMBER_SHIP_LEVEL.getDesc()+"符合条件"));
        }
        //等级按照UpgradeStandardAmount金额排序
        Collections.sort(cashlevel, new Comparator<MMembershipLevel>() {
            @Override
            public int compare(MMembershipLevel o1, MMembershipLevel o2) {
                //升序
                return o1.getUpgradeStandardAmount().compareTo(o2.getUpgradeStandardAmount());
            }
        });
        //用户可以升级到的等级
        Long level3 = null;
        String memberName3 = null;
        BigDecimal standAmountN3 = null; //等级升级的总金额
        //遍历每个符合条件的等级（从用户当前等级的后一个等级开始遍历）
        for(int i=0;i<cashlevel.size()-1;i++){
            int r = memberCashAll.compareTo(cashlevel.get(i).getUpgradeStandardAmount());
            int r1 = memberCashAll.compareTo(cashlevel.get(i+1).getUpgradeStandardAmount());

            //与任意一个等级想等
//            if(r1 == 0){
//                level3 = cashlevel.get(i+1).getMembershipLevelId();
//                memberName3 = cashlevel.get(i+1).getMembershipLevelName();
//                standAmountN3 = cashlevel.get(i+1).getUpgradeStandardAmount();
//            }
//            if(r == 0){
//                level3 = cashlevel.get(i).getMembershipLevelId();
//                memberName3 = cashlevel.get(i).getMembershipLevelName();
//                standAmountN3 = cashlevel.get(i).getUpgradeStandardAmount();
//            }

            //比最大的等级的钱还多
            if(r>0 && r1>=0){
                level3 = cashlevel.get(i+1).getMembershipLevelId();
                memberName3 = cashlevel.get(i+1).getMembershipLevelName();
                standAmountN3 = cashlevel.get(i+1).getUpgradeStandardAmount();
            }
            //处于两个等级的中间
            if(r>=0 && r1<0 ){
                level3 = cashlevel.get(i).getMembershipLevelId();
                memberName3 = cashlevel.get(i).getMembershipLevelName();
                standAmountN3 = cashlevel.get(i).getUpgradeStandardAmount();
            }
        }
        //判断哪个等级更高
//        Long max = ((level1 > level2 ? level1 : level2) > level3) ? (level1 > level2 ? level1 : level2) : level3;
        Long max = 1l;
        if(level1!=null){
            max = level1;
        }
        BigDecimal amount = rechargeAll;        //用户等级最高的消费或充值金额
        BigDecimal standAmountN = standAmountN1;    //最高等级的金额
        mMembershipLevelRecords.setMembershiplevelrecordsType(MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_1.getCode());
        String operateDesc = MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_1.getDesc();        //操作描述
        mMembershipLevelRecords.setOperateDesc(operateDesc);
        String memberName = memberName1;
        if (level2 != null && level2.compareTo(max)>0) {
            max = level2;
            memberName = memberName2;
            standAmountN = standAmountN2;
            amount = rechargeMax;
        }
        if (level3 != null && level3.compareTo(max)>0) {
            max = level3;
            memberName = memberName3;
            standAmountN = standAmountN3;
            amount = memberCashAll;
            mMembershipLevelRecords.setMembershiplevelrecordsType(MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_3.getCode());
            operateDesc = MemberLevelUpgringTypeEnum.MEMBER_LEVEL_TYPE_3.getDesc();        //操作描述
            mMembershipLevelRecords.setOperateDesc(operateDesc);
        }
        if(amount != null && max != null && standAmountN != null && memberName != null){
            //用户等级最高的消费或充值金额 比 要升级的大
            if(amount.compareTo(standAmountN) >= 0){
                updateWalletAccountMemberShipLevelVO.setMembershipLevelId(max);
                updateWalletAccountMemberShipLevelVO.setMembershipLevelName(memberName);

                mMembershipLevelRecords.setLaterMembershipLevelId(max);
                updateWalletAccountMemberShipLevelRecods(mMembershipLevelRecords);
                iMemberDao.updateWalletAccountMemberShipLevelById(updateWalletAccountMemberShipLevelVO);
                return ResponseResult.success(memberName);
            }
        }
        //更新记录表
        // mMembershipLevelRecords.setLaterMembershipLevelId(memberUserShipLevelId);
        // updateWalletAccountMemberShipLevelRecods(mMembershipLevelRecords);
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.MEMBER_NOT_UP.getCode(),
                ResponseCodeAccountEnum.MEMBER_NOT_UP.getDesc()));
    }

}
