package com.lnmj.wallet.business.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeWalletEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.wallet.business.IWalletConsumeService;
import com.lnmj.wallet.entity.VO.WalletConsumeRecordVO;
import com.lnmj.wallet.entity.WalletConsumeRecord;
import com.lnmj.wallet.repository.WalletConsumeRecordDao;
import com.lnmj.wallet.repository.WalletDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("walletConsumeServiceImpl")
public class WalletConsumeServiceImpl implements IWalletConsumeService {

    @Resource
    private WalletConsumeRecordDao walletConsumeRecordDao;
    @Resource
    private WalletDao walletDao;

    @Override
    public ResponseResult selectExpenseCalendarById(Long userId) {
        List<WalletConsumeRecord> result = walletConsumeRecordDao.selectExpenseCalendarById(userId);
        if (result.size() == 0 || result == null) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.CONSUMPTION_RECORD_NOTEXIST.getCode(), ResponseCodeWalletEnum.CONSUMPTION_RECORD_NOTEXIST.getDesc()));
        }
        return ResponseResult.success(result);
    }

    @Override
    public ResponseResult selectExpenseCalendarList(int pageNum, int pageSize, String cardNumber) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap<>();
        List<WalletConsumeRecord> recordList = new ArrayList<>();
        //根据条件查询
        if (StringUtils.isNotBlank(cardNumber)) {
            map.put("cardNumber", cardNumber);
            recordList = walletConsumeRecordDao.selectExpenseCalendarList(map);
        } else {
            //分页查询
            recordList = walletConsumeRecordDao.selectExpenseCalendarList(map);
        }
        if (recordList.size() > 0 || recordList != null) {
            PageInfo pageInfo = new PageInfo(recordList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeWalletEnum.EXPENSECALENDAR_RECORD_NOTEXIST.getCode(), ResponseCodeWalletEnum.EXPENSECALENDAR_RECORD_NOTEXIST.getDesc()));
    }

    @Override
    public ResponseResult deleteExpenseRecord(Long consumeRecordId, String[] ids) {
        int result = 0;
        if (consumeRecordId != null) {
            //根据id删除单条
            result = walletConsumeRecordDao.deleteExpenseRecordById(consumeRecordId);
        } else {
            //批量操作
            for (String id : ids) {
                result = walletConsumeRecordDao.deleteExpenseRecordById(Long.parseLong(id));
                if (result == 0) {
                    return ResponseResult.error(new Error(ResponseCodeWalletEnum.DELETE_EXPENSERECORD_FAILUER.getCode(), ResponseCodeWalletEnum.DELETE_EXPENSERECORD_FAILUER.getDesc()));
                }
            }
        }
        if (result == 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.DELETE_EXPENSERECORD_FAILUER.getCode(), ResponseCodeWalletEnum.DELETE_EXPENSERECORD_FAILUER.getDesc()));
        }
        return ResponseResult.success(ResponseCodeWalletEnum.DELETE_EXPENSERECORD_SUCCESS.getDesc());
    }


    @Override
    public ResponseResult selectListByUserStatistics() {
        List<WalletConsumeRecordVO> voList = walletConsumeRecordDao.selectListByUserStatistics();
        return ResponseResult.success(voList);
    }

    @Override
    public ResponseResult deleteCashRecord(Long consumeRecordId, String[] ids) {
        int result = 0;
        if (consumeRecordId != null) {
            //根据id删除单条
            result = walletConsumeRecordDao.deleteCashRecord(consumeRecordId);
        } else {
            //批量操作
            for (String id : ids) {
                result = walletConsumeRecordDao.deleteCashRecord(Long.parseLong(id));
                if (result == 0) {
                    return ResponseResult.error(new Error(ResponseCodeWalletEnum.DELETE_CASHRECORD_FAIL.getCode(), ResponseCodeWalletEnum.DELETE_CASHRECORD_FAIL.getDesc()));
                }
            }
        }
        if (result == 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.DELETE_CASHRECORD_FAIL.getCode(), ResponseCodeWalletEnum.DELETE_CASHRECORD_FAIL.getDesc()));
        }
        return ResponseResult.success(ResponseCodeWalletEnum.DELETE_CASHRECORD_SUCCESS.getDesc());
    }

    @Override
    public ResponseResult selectUserWalletConsumeRecord(WalletConsumeRecord walletConsumeRecord) {
        List<WalletConsumeRecord> userWalletConsumeRecord = walletConsumeRecordDao.selectUserWalletConsumeRecord(walletConsumeRecord);
        return ResponseResult.success(userWalletConsumeRecord);
    }
}
