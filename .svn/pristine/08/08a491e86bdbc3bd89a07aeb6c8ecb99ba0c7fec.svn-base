package com.lnmj.wallet.business.impl;


import com.alibaba.fastjson.JSON;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeWalletEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.business.IWalletAccountService;
import com.lnmj.wallet.entity.RechargeType;
import com.lnmj.wallet.entity.WalletAmount;
import com.lnmj.wallet.repository.WalletAccountDao;
import com.lnmj.wallet.serviceProxy.DataApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("wallettAccountServiceImpl")
public class WalletAccountServiceImpl implements IWalletAccountService {
    @Resource
    private WalletAccountDao walletAccountDao;
    @Resource
    private DataApi dataApi;

    @Override
    public ResponseResult selectWalletAccountList(int pageNum, int pageSize, String keyWord) {
        Map map = new HashMap<>();
        map.put("keyWord", keyWord);
        List<RechargeType> accountList = walletAccountDao.selectWalletAccountList(map);
        return ResponseResult.success(accountList);
    }

    @Override
    public ResponseResult selectWalletAccountListNoPage() {
        Map map = new HashMap<>();
        List<RechargeType> accountList = walletAccountDao.selectWalletAccountList(map);
        return ResponseResult.success(accountList);
    }

    @Override
    public ResponseResult addWalletAccount(RechargeType account) {
        //如果是赠送账户 查看当前是否已经存在赠送账户
        if (account.getIsQingKe() == 1) {
            int resultInt = walletAccountDao.checkIsQingKe();
            if (resultInt > 0) {
                return ResponseResult.error(new Error(ResponseCodeWalletEnum.ZENGSONG_TYPE_EXIST.getCode(), ResponseCodeWalletEnum.ZENGSONG_TYPE_EXIST.getDesc()));
            }
        }
        //判断名字是否重复
        Map map = new HashMap();
        map.put("accountType", account.getAccountType());
        int resultInt = walletAccountDao.checkTypeName(map);
        if (resultInt > 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.TYPE_NAME_EXIST.getCode(), ResponseCodeWalletEnum.TYPE_NAME_EXIST.getDesc()));

        }

        int result = walletAccountDao.addWalletAccount(account);
        if (result <= 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.ADD_ACCOUNT_ERROR.getCode(), ResponseCodeWalletEnum.ADD_ACCOUNT_ERROR.getDesc()));
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateWalletAccount(RechargeType account) {
        //如果是赠送账户 查看当前是否已经存在赠送账户
        if (account.getIsQingKe() == 1) {
            int resultInt = walletAccountDao.checkIsQingKe();
            if (resultInt > 0) {
                return ResponseResult.error(new Error(ResponseCodeWalletEnum.ZENGSONG_TYPE_EXIST.getCode(), ResponseCodeWalletEnum.ZENGSONG_TYPE_EXIST.getDesc()));
            }
        }
        //判断名字是否重复
        Map map = new HashMap();
        map.put("accountType", account.getAccountType());
        map.put("accountTypeId", account.getAccountTypeId());
        int resultInt = walletAccountDao.checkTypeName(map);
        if (resultInt > 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.TYPE_NAME_EXIST.getCode(), ResponseCodeWalletEnum.TYPE_NAME_EXIST.getDesc()));

        }

        int result = walletAccountDao.updateWalletAccount(account);
        if (result <= 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.UPDATE_ACCOUNT_ERROR.getCode(), ResponseCodeWalletEnum.UPDATE_ACCOUNT_ERROR.getDesc()));
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteWalletAccount(Long accountId) {
        int result = walletAccountDao.deleteWalletAccount(accountId);
        if (result <= 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.DELETE_ACCOUNT_ERROR.getCode(), ResponseCodeWalletEnum.DELETE_ACCOUNT_ERROR.getDesc()));
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectWalletAccountBySubClass(String subClassId) {
        //查询所有的账户支付类型
        Map map = new HashMap();
        List<RechargeType> walletAmountListResult = walletAccountDao.selectWalletAccountList(map);
        List<RechargeType> walletAmountList = new ArrayList<>();
        for (RechargeType rechargeType : walletAmountListResult) {
            if (rechargeType.getSubclass().indexOf(subClassId) != -1) {
                walletAmountList.add(rechargeType);
            }
        }

        return ResponseResult.success(walletAmountList);
    }
}
