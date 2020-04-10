package com.lnmj.account.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.account.business.IMemberUserService;
import com.lnmj.account.entity.MMembershipLevel;
import com.lnmj.account.entity.MemberUser;
import com.lnmj.account.entity.Poslabel;
import com.lnmj.account.repository.IMemberDao;
import com.lnmj.account.repository.IMemberUserDao;
import com.lnmj.account.repository.IPosLabelDao;
import com.lnmj.account.serviceProxy.PaymentApi;
import com.lnmj.account.serviceProxy.WalletApi;
import com.lnmj.common.Enum.EnterStoreChannelEnum;
import com.lnmj.common.Enum.MemberAddTypeEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAccountEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeOrderEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeWalletEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: panlin
 * @Date: 2019/9/24 10:01
 * @Description: 会员用户管理
 */
@Transactional
@Service("memberUserService")
public class MemberUserService implements IMemberUserService {
    @Resource
    private IMemberUserDao iMemberUserDao;
    @Resource
    private IPosLabelDao iPosLabelDao;
    @Resource
    private WalletApi walletApi;
    @Resource
    private PaymentApi paymentApi;
    @Resource
    private IMemberDao iMemberDao;

    @Override
    public ResponseResult selectMemberUser(int pageNum, int pageSize, String storeId, String mobile, String name, Integer newOldStatus, Integer isLost) {
        String[] storeIdArray;
        if (StringUtils.isBlank(storeId)) {
            storeIdArray = null;
        } else if ("[]".equals(storeId)) {
            storeIdArray = null;
        } else {
            storeIdArray = storeId.replaceAll("]", "").replace("[", "").split(",");
        }
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("newOldStatus", newOldStatus);
        map.put("isLost", isLost);
        map.put("list", storeIdArray);
        map.put("mobile", mobile);
        map.put("name", name);
        List<MemberUser> memberUserList = iMemberUserDao.selectMemberUser(map);
        Map<Integer, String> mapAddType = EnumUtil.getEnumToMap(MemberAddTypeEnum.class);
        Map<Integer, String> mapEnterType = EnumUtil.getEnumToMap(EnterStoreChannelEnum.class);
        for (MemberUser memberUser : memberUserList) {
            for (Map.Entry<Integer, String> entry : mapAddType.entrySet()) {
                if (memberUser.getMemberAddType().equals(entry.getKey())) {
                    memberUser.setMemberAddTypeName(entry.getValue());
                }
            }
            for (Map.Entry<Integer, String> entry : mapEnterType.entrySet()) {
                if (memberUser.getEnterChannel().equals(entry.getKey())) {
                    memberUser.setEnterChannelName(entry.getValue());
                }
            }
        }
        Map map1 = new HashMap();
        List<Poslabel> poslabelList = iPosLabelDao.selectList(map1);
        for (MemberUser memberUser : memberUserList) {
            String lableName = "";
            String[] labels = memberUser.getLableId().split(",");
            for (String label : labels) {
                for (Poslabel poslabel : poslabelList) {
                    if (label.equals(String.valueOf(poslabel.getLabelId()))) {
                        lableName = lableName + "," + poslabel.getLabelName();
                    }
                }
            }
            lableName = lableName.replaceAll(",(.*)", "$1");
            memberUser.setLableName(lableName);
        }


        if (memberUserList.size() == 0) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.STORE_MEMBER_NULL.getCode(), ResponseCodeAccountEnum.STORE_MEMBER_NULL.getDesc()));
        } else {
            PageInfo pageInfo = new PageInfo(memberUserList);
            return ResponseResult.success(pageInfo);
        }
    }

    @Override
    public ResponseResult selectMemberUserNoPage() {
        Map map = new HashMap();
        List<MemberUser> memberUserList = iMemberUserDao.selectMemberUser(map);
        if (memberUserList.size() == 0) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.STORE_MEMBER_NULL.getCode(), ResponseCodeAccountEnum.STORE_MEMBER_NULL.getDesc()));
        } else {
            return ResponseResult.success(memberUserList);
        }
    }

    @Override
    public ResponseResult selectMemberUserByPhoneOrName(int pageNum, int pageSize, String storeId, String mobile, String name) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
//        map.put("storeId", storeId);
        map.put("mobile", mobile);
        map.put("name", name);
        List<MemberUser> storeMemberList = iMemberUserDao.selectMemberUserByPhoneOrName(map);
        if (storeMemberList.size() == 0) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.STORE_MEMBER_NULL.getCode(), ResponseCodeAccountEnum.STORE_MEMBER_NULL.getDesc()));
        }
        PageInfo pageInfo = new PageInfo(storeMemberList);
        return ResponseResult.success(pageInfo);
    }

    @Override
    public ResponseResult addMemberUser(MemberUser memberUser) {
        if (memberUser.getSex() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.SEX_NULL.getCode(), ResponseCodeAccountEnum.SEX_NULL.getDesc()));
        }
        if (memberUser.getIdCard() != null) {
            try {
                String idCardResult = IdCardVerification.IDCardValidate(memberUser.getIdCard());
                if (!idCardResult.equals("该身份证有效")) {
                    return ResponseResult.error(new Error(ResponseCodeAccountEnum.IDCARD_FAIL.getCode(), idCardResult));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //判断手机号码正确性
        if (!PhoneUtils.isMobileNO(memberUser.getMobile())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.MOBILE_NUMBER_ILLEGAL.getCode(), ResponseCodeAccountEnum.MOBILE_NUMBER_ILLEGAL.getDesc()));
        }
        //判断手机号码是否已经注册
        Map map = new HashMap();
        map.put("mobile", memberUser.getMobile());
        if (iMemberUserDao.selectMemberUserByPhoneOrName(map).size() != 0) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.ACCOUNT_EXIST.getCode(), ResponseCodeAccountEnum.ACCOUNT_EXIST.getDesc()));
        }

        String carNum = NumberUtils.getRandomOrderNo();
        memberUser.setMemberNum(carNum);
        memberUser.setUserName(memberUser.getMobile());
        memberUser.setPassword(new BCryptPasswordEncoder().encode("888888"));
        memberUser.setMembershipLevelId(1l);
        MMembershipLevel mMembershipLevel = new MMembershipLevel();
//        mMembershipLevel.setMembershipLevelId(memberUser.getMembershipLevelId());
        Long ship = Long.parseLong("1");
        mMembershipLevel.setMembershipLevelId(ship);
        memberUser.setMembershipLevelName("新会员");
        iMemberUserDao.addMemberUser(memberUser);
        //生成钱包
        ResponseResult responseResult = walletApi.addWallet(carNum);
        if (responseResult == null || !"Success".equals(responseResult.getResponseStatusType().getMessage())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.REGISTER_FAILURE.getCode(), ResponseCodeAccountEnum.REGISTER_FAILURE.getDesc() + ":钱包开通失败"));
        }
        return ResponseResult.success(memberUser);
    }

    @Override
    public ResponseResult selectMemberUserAccount(int pageNum, int pageSize, String memberNum, String subClassIds, Long industry) {

        if (StringUtils.isBlank(memberNum)) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.MEMBER_CODE_NULL.getCode(), ResponseCodeAccountEnum.MEMBER_CODE_NULL.getDesc()));
        }
        PageHelper.startPage(pageNum, pageSize);
        //查看账号户类型
        ResponseResult responseResult = walletApi.selectWalletAccountList();
        List<Map<String, Object>> accountList = (ArrayList) responseResult.getResult();

        List<Map<String, Object>> accountListResult = new ArrayList<>();
        //如果没有绑定支付方式的 不显示
        //根据账户类型查询支付方式

        if (industry != null) {
            for (Map<String, Object> stringObjectMap : accountList) {
                if (paymentApi.selectPayTypeByAccountType(Long.parseLong(stringObjectMap.get("accountTypeId").toString())).getResult() != null &&
                        ((JSONArray.parseArray(((Map) paymentApi.selectPayTypeByAccountType(Long.parseLong(stringObjectMap.get("accountTypeId").toString())).getResult()).get("industry").toString()).size() == 0) ||
                                (JSONArray.parseArray(((Map) paymentApi.selectPayTypeByAccountType(Long.parseLong(stringObjectMap.get("accountTypeId").toString())).getResult()).get("industry").toString()).contains(industry.toString())))) {
                    //如果绑定 并且支付类型的行业等于指定行业
                    accountListResult.add(stringObjectMap);
                }
                ;
            }
        } else {
            for (Map<String, Object> stringObjectMap : accountList) {
                if (paymentApi.selectPayTypeByAccountType(Long.parseLong(stringObjectMap.get("accountTypeId").toString())).getResult() != null &&
                        ((JSONArray.parseArray(((Map) paymentApi.selectPayTypeByAccountType(Long.parseLong(stringObjectMap.get("accountTypeId").toString())).getResult()).get("industry").toString()).size() == 0) )) {
                    //如果绑定 并且支付类型的行业等于指定行业
                    accountListResult.add(stringObjectMap);
                }
                ;
            }
        }

        if (subClassIds != null) {
            JSONArray jsonArray = JSON.parseArray(subClassIds);
            List<Map<String, Object>> accountListRemove = new ArrayList<>();
            for (int i = 0; i < accountListResult.size(); i++) {
                for (int j = 0; j < jsonArray.size(); j++) {
                    if (accountListResult.get(i).get("subclass") != null && !accountListResult.get(i).get("subclass").toString().contains(jsonArray.getString(j))) {
                        accountListRemove.add(accountListResult.get(i));
                    }
                }
            }
            accountListResult.removeAll(accountListRemove);
        }
        //查看会员所拥有的余额
        ResponseResult responseResult1 = walletApi.selectWalletByCarNum(memberNum);
        if (responseResult1.getResult() == null) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.WALLET_IS_NOT_EXIST.getCode(), ResponseCodeWalletEnum.WALLET_IS_NOT_EXIST.getDesc()));
        }
        Long walletId = Long.parseLong(responseResult1.getResult().toString());//查看钱包id


        //查看钱包的账户余额
        List<Map<String, Object>> walletAccountAmountList = (List<Map<String, Object>>) walletApi.selectAccountAmountByWalletId(walletId).getResult();
        for (Map<String, Object> account : accountListResult) {
            account.put("amount", 0);
            for (Map<String, Object> storememberAmount : walletAccountAmountList) {
                if (Long.parseLong(account.get("accountTypeId").toString()) == Long.parseLong(storememberAmount.get("accountTypeId").toString())) {
                    account.put("amount", storememberAmount.get("amount"));
                }
            }
        }

        PageInfo pageInfo = new PageInfo(accountListResult);

        return ResponseResult.success(pageInfo);
    }

    @Override
    public ResponseResult selectStoreMemberAccountNoPage(String memberNum, String subClassIds) {
        if (StringUtils.isBlank(memberNum)) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.MEMBER_CODE_NULL.getCode(), ResponseCodeAccountEnum.MEMBER_CODE_NULL.getDesc()));
        }
        //查看账号户类型
        ResponseResult responseResult = walletApi.selectWalletAccountList();
        List<Map<String, Object>> accountList = (ArrayList) responseResult.getResult();

        if (subClassIds != null) {
            JSONArray jsonArray = JSON.parseArray(subClassIds);
            List<Map<String, Object>> accountListRemove = new ArrayList<>();
            for (int i = 0; i < accountList.size(); i++) {
                for (int j = 0; j < jsonArray.size(); j++) {
                    if (!accountList.get(i).get("subclass").toString().contains(jsonArray.getString(j))) {
                        accountListRemove.add(accountList.get(i));
                    }
                }
            }
            accountList.removeAll(accountListRemove);
        }
        //查看会员所拥有的余额
        Long walletId = Long.parseLong(walletApi.selectWalletByCarNum(memberNum).getResult().toString());//查看钱包id
        //查看钱包的账户余额
        List<Map<String, Object>> walletAccountAmountList = (List<Map<String, Object>>) walletApi.selectAccountAmountByWalletId(walletId).getResult();
        for (Map<String, Object> account : accountList) {
            account.put("amount", 0);
            for (Map<String, Object> storememberAmount : walletAccountAmountList) {
                if (Long.parseLong(account.get("accountTypeId").toString()) == Long.parseLong(storememberAmount.get("accountTypeId").toString())) {
                    account.put("amount", storememberAmount.get("amount"));
                }
            }
        }


        return ResponseResult.success(accountList);
    }

    @Override
    public ResponseResult selectMemberByNum(String memberNum) {
        Map map = new HashMap();
        map.put("memberNum", memberNum);
        MemberUser memberUser = iMemberUserDao.selectMemberUserByMemberNum(map);
        return ResponseResult.success(memberUser);
    }

    @Override
    public ResponseResult CountMemberByDay(String date, String storeId) {
        Date orderDate = new Date();
        if (date != null && !StringUtils.isBlank(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                orderDate = sdf.parse(date);
            } catch (ParseException e) {
                return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_DATE_ERROR.getCode(),
                        ResponseCodeOrderEnum.ORDER_DATE_ERROR.getDesc()));
            }
        }
        Map<Object, Object> mapSelect = new HashMap<>();
        String[] storeIdArray = storeId.replaceAll("]", "").replace("[", "").split(",");
        if (!(storeIdArray[0].equals(""))) {
            mapSelect.put("list", storeIdArray);
        }
        mapSelect.put("date", orderDate);
        int number = iMemberUserDao.CountMemberUserByDay(mapSelect);
        return ResponseResult.success(number);
    }

    @Override
    public ResponseResult updateMemberUserToOld(List<MemberUser> storeMemberInfoList) {
        Map map = new HashMap();
        map.put("list", storeMemberInfoList);
        iMemberUserDao.updateMemberUserToOld(map);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateMemberUserToLost(List<MemberUser> storeMemberInfoList) {
        Map map = new HashMap();
        map.put("list", storeMemberInfoList);
        iMemberUserDao.updateMemberUserToLost(map);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateMemberUser(MemberUser storeMember) {
        iMemberUserDao.updateMemberUser(storeMember);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectEnterChannel() {
        Map map = EnumUtil.getEnumToMap(EnterStoreChannelEnum.class);
        return ResponseResult.success(map);
    }

    @Override
    public ResponseResult resetMemberPassword(String memberNum, String passwordOld, String passwordNew) {
        MemberUser memberUser = new MemberUser();
        memberUser.setPassword(new BCryptPasswordEncoder().encode(passwordOld));
        memberUser.setMemberNum(memberNum);
        //非空判断
        if (memberNum == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.USERID_NULL.getCode(), ResponseCodeAccountEnum.USERID_NULL.getDesc()));
        }
        if (StringUtils.isBlank(passwordOld)) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.OLD_PASSWORD_NULL.getCode(), ResponseCodeAccountEnum.OLD_PASSWORD_NULL.getDesc()));
        }
        if (StringUtils.isBlank(passwordNew)) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.NEW_PASSWORD_NULL.getCode(), ResponseCodeAccountEnum.NEW_PASSWORD_NULL.getDesc()));
        }
        String resultCount = iMemberUserDao.selectPasswordByNum(memberNum);
        boolean flag = new BCryptPasswordEncoder().matches(passwordOld, resultCount);
        if (!flag) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.ACCOUNT_PASSWORD_ERR0R.getCode(), ResponseCodeAccountEnum.ACCOUNT_PASSWORD_ERR0R.getDesc()));
        }
        //判断密码合法性
        if (!CheckStrUtils.verifyPassword(passwordNew)) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.PASSWORD_ILLEGAL.getCode(), ResponseCodeAccountEnum.PASSWORD_ILLEGAL.getDesc()));
        }
        ;
        memberUser.setPassword(new BCryptPasswordEncoder().encode(passwordNew));
        int updateCount = iMemberUserDao.updateMemberUser(memberUser);
        if (updateCount > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ResponseCodeAccountEnum.PASSWORD_UPDATE_FAILUER.getCode(), ResponseCodeAccountEnum.PASSWORD_UPDATE_FAILUER.getDesc()));
    }

    @Override
    public ResponseResult batchaddStoreMember(List<MemberUser> memberUserList) {
        //创建会员
        List<String> carNumList = iMemberUserDao.batchaddStoreMember(memberUserList);
        //批量生产钱包
        ResponseResult responseResult = walletApi.batchaddWallet(carNumList);

        return ResponseResult.success();
    }
}
