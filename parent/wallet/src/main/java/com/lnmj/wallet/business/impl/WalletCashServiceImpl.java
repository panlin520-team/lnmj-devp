package com.lnmj.wallet.business.impl;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeWalletEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.business.IWalletCashService;
import com.lnmj.wallet.entity.WalletBonudDetail;
import com.lnmj.wallet.entity.WalletCashRecord;
import com.lnmj.wallet.entity.WalletCashRecordDetail;
import com.lnmj.wallet.repository.BankCardDao;
import com.lnmj.wallet.repository.IWalletCashDao;
import com.lnmj.wallet.repository.WalletDao;
import com.lnmj.wallet.serviceProxy.AccountApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service("walletCashServiceImpl")
public class WalletCashServiceImpl implements IWalletCashService {
    @Resource
    private IWalletCashDao walletCashDao;
    @Resource
    private WalletDao walletDao;
    @Resource
    private BankCardDao bankCardDao;
    @Resource
    private AccountApi selectUserApi;

    @Override
    public ResponseResult selectCashRecordList(int pageNum, int pageSize, Integer status, String cashTime) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        if (status != null && status != 0) {
            hashMap.put("status", status);
        }
        if (StringUtils.isNotBlank(cashTime)) {
            hashMap.put("cashTime", cashTime);
        }
        //查询钱包信息
        List<WalletCashRecord> walletCashRecordListlist = walletCashDao.selectCashRecordList(hashMap);
        //查询用户信息
        for (int i = 0; i < walletCashRecordListlist.size(); i++) {
            String cardNumber = walletCashRecordListlist.get(i).getCardNumber();
            Map result = (Map) selectUserApi.selectMemberByNum(cardNumber).getResult();
            walletCashRecordListlist.get(i).setAccount(result.get("userName").toString());
            walletCashRecordListlist.get(i).setMobile(result.get("mobile").toString());
            walletCashRecordListlist.get(i).setCardNumber(result.get("memberNum").toString());

        }

        return ResponseResult.success(walletCashRecordListlist);
    }

    @Override
    public ResponseResult selectCashRecordDetailList(int pageNum, int pageSize, String bonusTypeId, String cashRecordId) {
        Map map = new HashMap();
        if (StringUtils.isNotBlank(bonusTypeId)) {
            map.put("bonusTypeId", bonusTypeId);
            map.put("cashRecordId", cashRecordId);

        }
        List<WalletBonudDetail> list = walletCashDao.selectCashRecordDetailList(map);
        return ResponseResult.success(list);
    }

    @Override
    public ResponseResult examine(String cashRecordId) {
        Long id = Long.parseLong(cashRecordId);
        int result = walletCashDao.examine(id);
        WalletCashRecordDetail detail = new WalletCashRecordDetail();
        detail.setCashRecordId(id);
        detail.setBonusStatus(1);
        int updateResult = walletCashDao.updateCashDetail(detail);
        if (result <= 0 && updateResult <= 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.AUDIT_CASH_FAIL.getCode(), ResponseCodeWalletEnum.AUDIT_CASH_FAIL.getDesc()));
        }
        return ResponseResult.success();
    }

    /**
     * @param walletCashRecord
     * @param detailList
     * @Description 发起提现
     * @Param []
     * @Return com.lnmj.common.response.ResponseResult
     * @Author Mr.Ren
     * @Date 2019/6/3
     * @Time
     */
    @Override
    public ResponseResult addCash(WalletCashRecord walletCashRecord, List<WalletCashRecordDetail> detailList) {
        //添加提现记录
        Long walletId = walletDao.selectWalletIdByCardNumber(walletCashRecord.getCardNumber());
        Long bankCardId = bankCardDao.selectBankCardId(walletCashRecord.getCardNumber());
        walletCashRecord.setWalletId(walletId);
        walletCashRecord.setBankCardId(bankCardId);
        walletCashRecord.setTransactionNo(UUID.randomUUID().toString());
        int cashResult = walletCashDao.addCash(walletCashRecord);
        //添加提现详情记录
        Long cashRecordId = walletCashRecord.getCashRecordId();
        int cashDetailResultInt = 0;
        for (WalletCashRecordDetail detail : detailList) {
            detail.setCashRecordId(cashRecordId);
            cashDetailResultInt = walletCashDao.addCashDetail(detail);
        }
        if (cashResult != 0 || cashDetailResultInt != 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error(new Error(ResponseCodeWalletEnum.ADD_CASHRECORD_FAIL.getCode(), ResponseCodeWalletEnum.ADD_CASHRECORD_FAIL.getDesc()));
    }
}
