package com.lnmj.wallet.business.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.RechargeChannelEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeOrderEnum;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeWalletEnum;
import com.lnmj.common.utils.EnumUtil;
import com.lnmj.wallet.business.IWalletRechargeService;
import com.lnmj.wallet.entity.*;
import com.lnmj.wallet.repository.WalletAccountDao;
import com.lnmj.wallet.repository.WalletDao;
import com.lnmj.wallet.repository.WalletRechargeRecordDao;
import com.lnmj.wallet.serviceProxy.OrderApi;
import com.lnmj.wallet.serviceProxy.StoreApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.lnmj.common.response.Error;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("walletRechargeServiceImpl")
public class WalletRechargeServiceImpl implements IWalletRechargeService {

    @Resource
    private WalletRechargeRecordDao walletRechargeRecordDao;
    @Resource
    private StoreApi storeApi;
    @Resource
    private WalletAccountDao walletAccountDao;
    @Resource
    private WalletDao walletDao;
    @Resource
    private OrderApi orderApi;

    @Override
    public ResponseResult selectRechargeList(int pageNum, int pageSize, String keyWord, String storeId, Long accountTypeId, Integer auditStatus) {
        PageHelper.startPage(pageNum, pageSize);
        //分页查询
        String[] storeIdArray = storeId.replaceAll("]", "").replace("[", "").split(",");
        Map mapList = new HashMap();
        mapList.put("list", storeIdArray);

        //根据条件查询
        if (keyWord != null) {
            mapList.put("keyWord", keyWord);
        }
        mapList.put("accountTypeId", accountTypeId);
        mapList.put("auditStatus", auditStatus);
        List<WalletRechargeRecord> recordList = walletRechargeRecordDao.selectRechargeList(mapList);
        //查看充值渠道和员工
        Map<Integer, String> rechargeChannelMap = EnumUtil.getEnumToMap(RechargeChannelEnum.class);
        ResponseResult responseResult = storeApi.selectBeauticianListNoPage("3", storeId, null);
        List<Map> BeauticianListMap = (List<Map>) responseResult.getResult();
        for (WalletRechargeRecord walletRechargeRecord : recordList) {
            JSONArray staffArra = JSON.parseArray(walletRechargeRecord.getBeauticianId());
            for (Map.Entry entry : rechargeChannelMap.entrySet()) {
                if (walletRechargeRecord.getRechargeChannel() == entry.getKey()) {
                    walletRechargeRecord.setRechargeChannelName(entry.getValue().toString());
                }
            }
            for (Map Beautician : BeauticianListMap) {
                if (Beautician.get("staffNumber").toString().equals(staffArra.getJSONObject(0).getString("beauticianId"))) {
                    walletRechargeRecord.setBeauticianName(Beautician.get("name").toString());
                }
            }
        }
        if (recordList.size() > 0 || recordList != null) {
            PageInfo pageInfo = new PageInfo(recordList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeWalletEnum.RECHARGE_RECORD_NOTEXIST.getCode(), ResponseCodeWalletEnum.RECHARGE_RECORD_NOTEXIST.getDesc()));
    }


    @Override
    public ResponseResult selectSumAmount(String date, String cardNumber, String storeId) {
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
        Map<Object, Object> map = new HashMap<>();
        String[] storeIdArray = storeId.replaceAll("]", "").replace("[", "").split(",");
        if (!(storeIdArray[0].equals(""))) {
            map.put("list", storeIdArray);
        }
        map.put("orderDate", orderDate);
        map.put("cardNumber", cardNumber);


        //当天的所有充值信息
        List<WalletRechargeRecord> walletRechargeRecordList = walletRechargeRecordDao.selectTodayAmount(map);
        List<RechargeType> rechargeTypeList = walletAccountDao.selectWalletAccountList(map);
        List<Map> walletrechargerecordRefuseList = walletRechargeRecordDao.selectTodayAmountRefuse(map);


        //所有的充值类型
        double total = 0.00;
        for (WalletRechargeRecord walletRechargeRecord : walletRechargeRecordList) {
            for (RechargeType rechargeType : rechargeTypeList) {
                //拿到每一个充值订单的充值类型-》匹配是否是储值业绩
                if (walletRechargeRecord.getAccountTypeId().toString().equals(rechargeType.getAccountTypeId().toString()) && rechargeType.getIsCuZhiYeJi().toString().equals("1")) {
                    //是储值业绩
                    double amount = walletRechargeRecord.getAmount().doubleValue();
                    total += amount;
                }
            }
        }

        double totalRefuse = 0.00;
        for (Map maoItem : walletrechargerecordRefuseList) {
            for (RechargeType rechargeType : rechargeTypeList) {
                //拿到每一个充值订单的充值类型-》匹配是否是储值业绩
                if (maoItem.get("AccountTypeId").toString().equals(rechargeType.getAccountTypeId().toString()) && rechargeType.getIsCuZhiYeJi().toString().equals("1")) {
                    //是储值业绩
                    double amount = Double.parseDouble(maoItem.get("Amount").toString());
                    totalRefuse += amount;
                }
            }
        }


        Map mapRsult = new HashMap();
        mapRsult.put("rechargeAmount", total);
        mapRsult.put("totalRefuse", totalRefuse);
        return ResponseResult.success(mapRsult);
    }

    @Override
    public ResponseResult selectSumAmountAll(String cardNumber) {
        Map<Object, Object> map = new HashMap<>();
        map.put("cardNumber", cardNumber);
        Double rechargeAll = walletRechargeRecordDao.selectSumAmountAll(map);
        Double rechargeAllRefuse = walletRechargeRecordDao.selectSumAmountAllRefuse(map);
        rechargeAll = rechargeAll - rechargeAllRefuse;
        Double rechargeMax = walletRechargeRecordDao.selectSumAmountMax(map);
        Map<Object, Object> result = new HashMap<>();
        result.put("rechargeAll", rechargeAll);
        result.put("rechargeMax", rechargeMax);
        return ResponseResult.success(result);
    }

    @Override
    public ResponseResult selectConsumerAmount(String cardNumber) {
        int result = walletRechargeRecordDao.selectConsumerAmount(cardNumber);
        return ResponseResult.success(result);
    }

    @Override
    public ResponseResult selectRechargeById(int pageNum, int pageSize, String cardNumber, Integer accountTypeId, String date) {
        Long walletId = walletDao.selectWalletIdByCardNumber(cardNumber);
        List<WalletAmount> walletAmountList = walletDao.selectAccountAmountByWalletId(walletId);


        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("cardNumber", cardNumber);
        map.put("accountTypeId", accountTypeId);
        map.put("date", date);
        List<WalletRechargeRecord> result = walletRechargeRecordDao.selectRechargeById(map);
        for (WalletRechargeRecord walletRechargeRecord : result) {
            //查询对应的退款总和
            //判断退款金额，是否小于剩余金额
            //查看剩余金额
            Double refundSum = 0.00;
            WalletrechargerecordRefuse walletrechargerecordRefuse = new WalletrechargerecordRefuse();
            walletrechargerecordRefuse.setOrderNo(walletRechargeRecord.getOrderNo());
            List<WalletrechargerecordRefuse> mapListRefund = walletRechargeRecordDao.selectRechargeRefuseList(walletrechargerecordRefuse);
            if (mapListRefund == null || mapListRefund.size() == 0) {
                refundSum = 0.00;
            } else {
                for (WalletrechargerecordRefuse walletrechargerecordRefuseItem : mapListRefund) {
                    refundSum = refundSum + Double.parseDouble(walletrechargerecordRefuseItem.getAmount());
                }
            }


            Double memberAccountAmount = 0.00;
            for (WalletAmount walletAmount : walletAmountList) {
                if (String.valueOf(walletAmount.getAccountTypeId()).equals(walletRechargeRecord.getAccountTypeId().toString()) &&
                        String.valueOf(walletAmount.getWalletId()).equals(walletId.toString())) {
                    memberAccountAmount = walletAmount.getAmount().doubleValue();

                }
            }


            if (walletRechargeRecord.getAmount().doubleValue() - refundSum < memberAccountAmount) {
                walletRechargeRecord.setResidueAmount(walletRechargeRecord.getAmount().doubleValue() - refundSum);
            } else {
                walletRechargeRecord.setResidueAmount(memberAccountAmount);
            }

        }


        //查询所有的账户类型
        List<RechargeType> rechargeTypeList = walletAccountDao.selectWalletAccountList(new HashMap());
        for (WalletRechargeRecord walletRechargeRecord : result) {
            for (RechargeType rechargeType : rechargeTypeList) {
                if (rechargeType.getAccountTypeId().toString().equals(walletRechargeRecord.getAccountTypeId().toString())) {
                    walletRechargeRecord.setAccountTypeName(rechargeType.getAccountType());
                }
            }
        }


        if (result.size() > 0 || result != null) {
            PageInfo pageInfo = new PageInfo(result);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeWalletEnum.RECHARGE_RECORD_NOTEXIST.getCode(), ResponseCodeWalletEnum.RECHARGE_RECORD_NOTEXIST.getDesc()));
    }

    @Override
    public ResponseResult deleteRechargeRecord(Long rechargeRecordId, String[] ids) {
        int result = 0;
        if (rechargeRecordId != null) {
            //根据id删除单条
            result = walletRechargeRecordDao.deleteRechargeRecordById(rechargeRecordId);
        } else {
            //批量操作
            for (String id : ids) {
                rechargeRecordId = Long.parseLong(id);
                result = walletRechargeRecordDao.deleteRechargeRecordById(rechargeRecordId);
            }
        }
        if (result == 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.DELETE_EXPENSERECORD_FAILUER.getCode(), ResponseCodeWalletEnum.DELETE_EXPENSERECORD_FAILUER.getDesc()));
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult audit(WalletRechargeRecord walletRechargeRecord) {
        //审核
        int rechargeResult;
        if (walletRechargeRecord.getAuditStatus() == 2) {
            //审核拒绝
            if (StringUtils.isBlank(walletRechargeRecord.getFailureCause())) {
                walletRechargeRecord.setFailureCause("无");
            }

            //审核不通过  调用退款接口
            Map map = new HashMap();
            map.put("rechargeRecordId", walletRechargeRecord.getRechargeRecordId());
            WalletRechargeRecord walletRechargeRecordResult = walletRechargeRecordDao.selectRechargeById(map).get(0);
            ResponseResult responseResult = orderApi.rechargeOrderRefund(walletRechargeRecordResult.getOrderNo(),
                    walletRechargeRecordResult.getAmount().doubleValue(),
                    walletRechargeRecordResult.getName(),
                    walletRechargeRecordResult.getMobile(),
                    walletRechargeRecordResult.getCardNumber(),
                    walletRechargeRecordResult.getAccountTypeId(),
                    "1");
            if (responseResult.isSuccess()) {
                walletRechargeRecordDao.audit(walletRechargeRecord);
                return ResponseResult.success("审核拒绝成功:" + responseResult.getResult());
            } else {
                return ResponseResult.error(new Error(ResponseCodeWalletEnum.AUDIT_FAIL.getCode(), ResponseCodeWalletEnum.AUDIT_FAIL.getDesc()+":"+responseResult.getResponseStatusType().getError().getErrorMsg()));
            }
        }
        //审核通过
        rechargeResult = walletRechargeRecordDao.audit(walletRechargeRecord);
        if (rechargeResult < 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.AUDIT_FAIL.getCode(), ResponseCodeWalletEnum.AUDIT_FAIL.getDesc()));
        }else{
            return ResponseResult.success("审核通过成功");
        }


    }

    @Override
    public ResponseResult batchAudit(String walletRechargeRecordList, Integer auditStatus) {
        JSONArray array = JSONArray.parseArray(walletRechargeRecordList);
        List<Integer> list = new ArrayList();
        for (int i = 0; i < array.size(); i++) {
            list.add(array.getJSONObject(i).getInteger("rechargeRecordId"));
        }
        Map map = new HashMap();
        map.put("list", list);
        map.put("auditStatus", auditStatus);
        walletRechargeRecordDao.batchAudit(map);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectRechargeOrderList(String storeId, String date) {
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
        mapSelect.put("orderDate", orderDate);

        int i = walletRechargeRecordDao.selectRechargeOrderList(mapSelect);
        if (i > 0) {
            return ResponseResult.success(i);
        }
        return ResponseResult.error(new Error(ResponseCodeWalletEnum.RECHARGEORDERINFO_IS_NULL.getCode(), ResponseCodeWalletEnum.RECHARGEORDERINFO_IS_NULL.getDesc()));
    }

    @Override
    public ResponseResult countRechargeOrderSum(String today) {
        Date date = new Date();
        if (StringUtils.isNotBlank(today)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            try {
                date = format.parse(today);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        int sum = walletRechargeRecordDao.countRechargeOrderSum(date);
        return ResponseResult.success(sum);
    }

    @Override
    public ResponseResult selectRechargeByOrderNum(String date, String orderNo, String storeId) {
        //根据日期查询订单
        Map map = new HashMap();
        String[] storeIdArray;
        if (StringUtils.isNotBlank(storeId)) {
            storeIdArray = storeId.replaceAll("]", "").replace("[", "").split(",");
        } else {
            storeIdArray = null;
        }
        map.put("orderDate", date);
        map.put("list", storeIdArray);
        List<WalletRechargeRecord> walletRechargeRecord = walletRechargeRecordDao.selectRechargeByOrderNum(map);
        return ResponseResult.success(walletRechargeRecord);
    }

    @Override
    public ResponseResult selectRechargeTopTen(Long storeId, String memberNum) {
        Map map = new HashMap();
        map.put("storeId", storeId);
        map.put("cardNumber", memberNum);
        List<WalletRechargeRecord> walletRechargeRecordList = walletRechargeRecordDao.selectRechargeOrderListTopTen(map);
        List<Date> dateList = new ArrayList<>();
        List<Double> rechargePriceList = new ArrayList<>();
        for (WalletRechargeRecord walletRechargeRecord : walletRechargeRecordList) {
            dateList.add(walletRechargeRecord.getCreateTime());
            rechargePriceList.add(walletRechargeRecord.getAmount().doubleValue());
        }


        Map mapResult = new HashMap();
        mapResult.put("date", dateList);
        mapResult.put("price", rechargePriceList);
        return ResponseResult.success(mapResult);
    }

    @Override
    public ResponseResult insertRechargeRefuse(WalletrechargerecordRefuse walletrechargerecordRefuse) {
        int resltInt = walletRechargeRecordDao.insertRechargeRefuse(walletrechargerecordRefuse);
        return ResponseResult.success(resltInt);
    }

    @Override
    public ResponseResult selectRechargeRefuseList(int pageNum, int pageSize, WalletrechargerecordRefuse walletrechargerecordRefuse) {
        PageHelper.startPage(pageNum, pageSize);
        List<WalletrechargerecordRefuse> refuseList = walletRechargeRecordDao.selectRechargeRefuseList(walletrechargerecordRefuse);
        PageInfo pageInfo = new PageInfo(refuseList);
        return ResponseResult.success(pageInfo);
    }

    @Override
    public ResponseResult selectRechargeRefuseListNoPage(WalletrechargerecordRefuse walletrechargerecordRefuse) {
        List<WalletrechargerecordRefuse> refuseList = walletRechargeRecordDao.selectRechargeRefuseList(walletrechargerecordRefuse);
        return ResponseResult.success(refuseList);
    }

    @Override
    public ResponseResult selectRechargeByOrderNumber(String orderNo) {
        Map map = new HashMap();
        map.put("orderNo", orderNo);
        WalletRechargeRecord walletRechargeRecord = walletRechargeRecordDao.selectRechargeByOrderNumber(map);
        return ResponseResult.success(walletRechargeRecord);
    }

    @Override
    public ResponseResult auditOrderAmount(String orderList, Integer auditStatus) {
        String[] strings = orderList.split(",");
        Map mapRecharg = new HashMap();
        mapRecharg.put("list", strings);
        mapRecharg.put("auditStatus", auditStatus);
        walletRechargeRecordDao.batchAuditAmount(mapRecharg);
        return ResponseResult.success();
    }
}
