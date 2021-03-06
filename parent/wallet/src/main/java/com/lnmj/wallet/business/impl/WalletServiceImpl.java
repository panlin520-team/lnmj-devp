package com.lnmj.wallet.business.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.OrderTypeEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeWalletEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.NumberUtils;
import com.lnmj.wallet.business.IWalletService;
import com.lnmj.wallet.entity.*;
import com.lnmj.wallet.entity.VO.WalletAccountVO;
import com.lnmj.wallet.repository.*;
import com.lnmj.wallet.serviceProxy.AccountApi;
import com.lnmj.wallet.serviceProxy.DataApi;
import com.lnmj.wallet.serviceProxy.ProductApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


@Service("walletServiceImpl")
@Transactional
public class WalletServiceImpl implements IWalletService {
    @Resource
    private WalletDao walletDao;
    @Resource
    private WalletRechargeRecordDao walletRechargeRecordDao;
    @Resource
    private WalletAccountDao walletAccountDao;
    @Resource
    private AccountApi accountApi;
    @Resource
    private ProductApi productApi;
    @Resource
    private IProfitDao profitDao;
    @Resource
    private WalletAmountDao walletAmountDao;
    @Resource
    private DataApi dataApi;
    @Resource
    private CapitalPoolDao capitalPoolDao;

    @Override
    public ResponseResult addWallet(String carNum) {
        int result = walletDao.checkWallet(carNum);
        if (result > 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.WALLET_ISEXIST.getCode(), ResponseCodeWalletEnum.WALLET_ISEXIST.getDesc()));
        }
        Wallet wallet = new Wallet();
        wallet.setCardNumber(carNum);
        int resultInt = walletDao.addWallet(wallet);
        if (resultInt <= 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.INSERT_WALLET_FAIL.getCode(), ResponseCodeWalletEnum.INSERT_WALLET_FAIL.getDesc()));
        }
        return ResponseResult.success();
    }


    @Override
    public ResponseResult selectWalletBalanceInfoById(Long userId, Integer rechargeType) {
        Map map = new HashMap();
        //钱包用户VO
        WalletAccountVO walletAccountVO = new WalletAccountVO();
        //账户余额
        List<WalletAmount> walletAmountList = new ArrayList<>();
        Map walletAmounts = new HashMap<>();
        //根据用户查找钱包信息
        Wallet resultWallet = walletDao.selectWalletInfoById(userId);
        walletAccountVO.setWallet(resultWallet);
        map.put("walletId", resultWallet.getWalletId());
        if (rechargeType == null || rechargeType == 0) {
            //获取用户的所有账户类型的余额
            walletAmountList = walletDao.selectBalanceInfoById(map);
            walletAmounts.put("walletAmount", walletAmountList);
        } else {
            //钱包余额信息(获取用户指定账户类型余额)
            map.put("accountTypeId", rechargeType);
            walletAmountList = walletDao.selectBalanceInfoById(map);
            if (walletAmountList.size() == 0) {
                WalletAmount walletAmount = new WalletAmount();
                walletAmount.setAccountTypeId(rechargeType);
                walletAmount.setAmount(new BigDecimal(0));
                walletAmountList.add(walletAmount);
                walletAmounts.put("walletAmount", walletAmountList);
            } else {
                walletAmounts.put("walletAmount", walletAmountList);
            }
        }
        walletAccountVO.setWalletAmountsMap(walletAmounts);
        if (walletAccountVO == null) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.SELECT_WALLET_BALANCE_FAILUER.getCode(), ResponseCodeWalletEnum.SELECT_WALLET_BALANCE_FAILUER.getDesc()));
        }
        return ResponseResult.success(walletAccountVO);
    }


    @Override
    public ResponseResult recharge(WalletAmount walletAmount) {
        walletAmount.setAccountTypeId(2);
        int i = walletDao.recharge(walletAmount);
        if (i <= 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.RECHARGE_FAIL.getCode(), ResponseCodeWalletEnum.RECHARGE_FAIL.getDesc()));
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult insertStoreRechargeAuditRecord(WalletRechargeRecord walletRechargeRecord) {
        //添加一条充值审核记录
        String orderNum = NumberUtils.getRandomOrderNo();
        walletRechargeRecord.setOrderNo(OrderTypeEnum.RECHARGE_ORDER.getCode().toString() + orderNum);
        walletRechargeRecord.setTransactionTime(new Date());
        walletRechargeRecord.setPaymentTime(new Date());
        if (StringUtils.isBlank(walletRechargeRecord.getRemarks())) {
            walletRechargeRecord.setRemarks("无");
        }

        int RechargeResult = walletRechargeRecordDao.addWalletRechargeRecord(walletRechargeRecord);
        if (RechargeResult < 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.ADD_RECHARGERECORDD_FAIL.getCode(), ResponseCodeWalletEnum.ADD_RECHARGERECORDD_FAIL.getDesc()));
        }
        //添加了充值记录后直接入账
        String carNum = walletRechargeRecord.getCardNumber();
        //拿到钱包id
        Long walletId = walletDao.selectWalletIdByCardNumber(carNum);
        //更新用户钱包金额
        //查看此用户钱包此账户时候有充值记录
        Map map = new HashMap();
        map.put("walletId", walletId);
        map.put("accountTypeId", walletRechargeRecord.getAccountTypeId());

        int resultInt = walletAmountDao.checkAmountByWalletIdAndTypeId(map);
        if (resultInt <= 0) {
            //添加
            WalletAmount walletAmount = new WalletAmount();
            walletAmount.setWalletId(walletId);
            walletAmount.setAccountTypeId(walletRechargeRecord.getAccountTypeId());
            walletAmount.setAmount(walletRechargeRecord.getAmount());
            walletAmountDao.insertAmount(walletAmount);
        } else {
            //修改
            WalletAmount walletAmount = new WalletAmount();
            walletAmount.setWalletId(walletId);
            walletAmount.setAccountTypeId(walletRechargeRecord.getAccountTypeId());
            walletAmount.setAmount(walletRechargeRecord.getAmount());
            walletAmountDao.updateAmount(walletAmount);
        }

        //充值审核通过加一条充值收益记录
        Profit profit = new Profit();
        //TODO 获取充值收益
        profit.setAmount(new BigDecimal(10));
        profit.setReason("充值收益");
        int i = profitDao.addProfit(profit);
        if (i <= 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.ADD_RECHARGE_PROFIL_FAIL.getCode(), ResponseCodeWalletEnum.ADD_RECHARGE_PROFIL_FAIL.getDesc()));
        }


        //充值成功后添加客户测评
        dataApi.insertEvaluatingDetailed(orderNum, carNum, null, walletRechargeRecord.getAmount().doubleValue(), 5);   //1为商品订单，2为护理订单，5为充值订单，6体验卡订单

        //充值成功后添加门店资金池预收
        //先查看是否有预收
        //判断充值账户类型，只有充值账户类型是储值账户（要收钱）时才加入预收
        List<RechargeType> rechargeTypeList = walletAccountDao.selectWalletAccountList(map);
        boolean flag = false;
        for (RechargeType rechargeType : rechargeTypeList) {
            if (walletRechargeRecord.getAccountTypeId().toString().equals(rechargeType.getAccountTypeId().toString()) && rechargeType.getIsCuZhiYeJi().toString().equals("1")) {
                //是储值业绩
                flag = true;
            }
        }

        //如果是储值业绩
        ResponseResult responseResult = null;
        if (flag) {
            //充值成功后添加充值业绩
            if (walletRechargeRecord.getBeauticianId() != null && walletRechargeRecord.getIsAbatementLadderDetailed()) {
                //如果有推荐员工，才进行提成
                responseResult = dataApi.insertLadderDetailed(0, walletRechargeRecord.getStoreId(), walletRechargeRecord.getBeauticianId(), walletRechargeRecord.getOrderNo(), walletRechargeRecord.getPayTypeAndAmount(), 1, null, 0.00, walletRechargeRecord.getIndustryId());
            }
            CapitalPool capitalPoolCheck = new CapitalPool();
            capitalPoolCheck.setStoreId(walletRechargeRecord.getStoreId());
            capitalPoolCheck.setType(1);
            capitalPoolCheck.setAdvancesReceivedAccount(walletRechargeRecord.getAccountTypeId());
            List<CapitalPool> capitalPoolList = capitalPoolDao.selectCapitalPoolList(capitalPoolCheck);
            if (capitalPoolList.size() == 0) {
                //如果没有此账户下的预收-添加金额
                CapitalPool capitalPool = new CapitalPool();
                capitalPool.setType(1);
                capitalPool.setAmount(walletRechargeRecord.getAmount());
                capitalPool.setPayType(JSON.parseArray(walletRechargeRecord.getPayTypeAndAmount()).getJSONObject(0).getInteger("payType"));
                capitalPool.setStoreId(walletRechargeRecord.getStoreId());
                capitalPool.setResidueAmount(walletRechargeRecord.getAmount());
                capitalPool.setAdvancesReceivedAccount(walletRechargeRecord.getAccountTypeId());
                capitalPoolDao.addCapitalPool(capitalPool);
            } else {
                //如果有此账户下的预收-修改金额
                CapitalPool capitalPool = new CapitalPool();
                capitalPool.setAmount(walletRechargeRecord.getAmount());
                capitalPool.setResidueAmount(walletRechargeRecord.getAmount());
                capitalPool.setResidueAmountUpOrDown(2);
                capitalPool.setStoreId(walletRechargeRecord.getStoreId());
                capitalPool.setAdvancesReceivedAccount(walletRechargeRecord.getAccountTypeId());
                capitalPoolDao.updateCapitalPool(capitalPool);
            }
        }
        if (responseResult != null && responseResult.getResponseStatusType().getMessage().equals("Success")) {
            return ResponseResult.success("充值成功！"+responseResult.getResult());
        }
        return ResponseResult.success("充值成功！");
    }

    @Override
    public ResponseResult updateStoreRechargeAuditRecord(WalletRechargeRecord walletRechargeRecord) {
        walletRechargeRecordDao.updateWalletRechargeRecord(walletRechargeRecord);
        return ResponseResult.success();
    }


    @Override
    public ResponseResult updateRechargeAmount(Long userId, BigDecimal updateRechargeAmount) {
        //非空判断
        if (userId == null) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.USER_ID_NULL.getCode(), ResponseCodeWalletEnum.USER_ID_NULL.getDesc()));
        }
        //判断此用户是否有钱包
        int resultInt = walletDao.checkUserWallet(userId);
        if (resultInt <= 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.WALLET_IS_NOT_EXIST.getCode(), ResponseCodeWalletEnum.WALLET_IS_NOT_EXIST.getDesc()));
        }
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("updateRechargeAmount", updateRechargeAmount);
        int resultrecInt = walletDao.updateRechargeAmount(map);
        if (resultrecInt <= 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.UPDATE_RECHARGEAMOUNT_FAIL.getCode(), ResponseCodeWalletEnum.UPDATE_RECHARGEAMOUNT_FAIL.getDesc()));
        }
        return ResponseResult.success();

    }

    @Transactional
    @Override
    public ResponseResult transfer(Long userId, BigDecimal transferAmount, Long accountType) {
        //通过用户id拿到钱包
        Long walletId = walletDao.selectWalletIdByUserId(userId);
        //减去钱包金额中要转到余额的金额
        Wallet wallet = new Wallet();
        wallet.setBonus(transferAmount);
        wallet.setWalletId(walletId);
        int updateWallet = walletDao.updateWallet(wallet);
        //更新用户账户余额
        WalletAmount walletAmount = new WalletAmount();
        walletAmount.setAmount(transferAmount);
        walletAmount.setWalletId(walletId);
        int updateWalletAmount = walletDao.updateWalletAmount(walletAmount);
        if (updateWallet <= 0 && updateWalletAmount <= 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.TRANSFER_FAIL.getCode(), ResponseCodeWalletEnum.TRANSFER_FAIL.getDesc()));
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectCashBackList(int pageNum, int pageSize, String keyWord) {
        PageHelper.startPage(pageNum, pageSize);
        HashMap<Object, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(keyWord)) {
            map.put("keyWord", keyWord);
        }
        List<CashBack> cashBackList = walletDao.selectCashBackList(map);

        if (cashBackList != null && cashBackList.size() > 0) {
            PageInfo pageInfo = new PageInfo(cashBackList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeWalletEnum.CASHBACK_IS_NULL.getCode(), ResponseCodeWalletEnum.CASHBACK_IS_NULL.getDesc()));
    }

    @Override
    public ResponseResult rebateExamine(Long cashBackId, String modifyOperator) {
        CashBack cashBack = new CashBack();
        cashBack.setCashBackId(cashBackId);
        cashBack.setAuditsStatus(1);
        cashBack.setModifyOperator(modifyOperator);
        int result = walletDao.rebateExamine(cashBack);
        if (result <= 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.CASHBACK_FAIL.getCode(), ResponseCodeWalletEnum.CASHBACK_FAIL.getDesc()));
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult returnGoods(Long cashBackId, String orderNumber, String cashbackAmount, String mobile, String modifyOperator) {
        //退货扣除返利金额
        JSONObject object = accountApi.selectAccountByCondition(mobile);
        Map result = (Map) object.get("result");
        Integer userId = (Integer) result.get("userId");
        Long walletId = walletDao.selectWalletIdByUserId(userId.longValue());
        Wallet wallet = walletDao.selectWalletById(walletId);
        //奖金就是收益
        BigDecimal bonus = wallet.getBonus();
        BigDecimal bigDecimal = new BigDecimal(cashbackAmount);
        BigDecimal subtract = bonus.subtract(bigDecimal);
        wallet.setBonus(subtract);
        int updateWallet = walletDao.updateWallet(wallet);
        //更改返利记录状态
        CashBack cashBack = new CashBack();
        cashBack.setCashBackId(cashBackId);
        cashBack.setAuditsStatus(2);
        int updateCashBack = walletDao.updateCashBack(cashBack);
        //TODO 退货处理
        if (updateWallet <= 0 && updateCashBack <= 0) {
            return ResponseResult.error(new Error(ResponseCodeWalletEnum.RETURNBACK_FAIL.getCode(), ResponseCodeWalletEnum.RETURNBACK_FAIL.getDesc()));
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult statisticsProfit(String memberNum, String productIds, String serviceIds) {
        Map account = (Map) accountApi.selectMemberByNum(memberNum).getResult();
        //用户等级
        Long membershipLevelId = Long.valueOf(account.get("membershipLevelId").toString());
        //拿到用户上级等级
        String parentMemberNum = account.get("parentMemberNum").toString();
        Map<String, String> account_parent = (Map<String, String>) accountApi.selectMemberByNum(parentMemberNum).getResult();
        Long p_membershipLevelId = Long.valueOf(account.get("membershipLevelId").toString());
        //商品id集合
        Map<Object, List<Map<Object, Object>>> product_list = (Map<Object, List<Map<Object, Object>>>) productApi.selectServiceListById(productIds).getResult();
        Map<Object, List<Map<Object, Object>>> service_list = (Map<Object, List<Map<Object, Object>>>) productApi.selectServiceListById(serviceIds).getResult();
        int producttotal = 0;
        int servicetotal = 0;
        List<Map<Object, Object>> productlist = product_list.get("list");
        List<Map<Object, Object>> servicelist = service_list.get("list");
        String p_vip = "retailPriceVip" + p_membershipLevelId;
        String c_vip = "retailPriceVip" + membershipLevelId;
        for (Map<Object, Object> service : servicelist) {
            //获取上级等级价格
            Double p_price = (Double) service.get(p_vip);
            Double c_price = (Double) service.get(c_vip);
            int p = p_price.intValue();
            int c = c_price.intValue();
            servicetotal += p - c;
        }
        for (Map<Object, Object> product : productlist) {
            //获取上级等级价格
            Double p_price = (Double) product.get(p_vip);
            Double c_price = (Double) product.get(c_vip);
            int p = p_price.intValue();
            int c = c_price.intValue();
            producttotal += p - c;
        }
        int totalprice = producttotal + servicetotal;
        return ResponseResult.success(totalprice);
    }


    @Override
    public ResponseResult selectWalletByCarNum(String carNum) {
        Long walletId = walletDao.selectWalletIdByCardNumber(carNum);
        return ResponseResult.success(walletId);
    }

    @Override
    public ResponseResult selectAccountAmountByWalletId(Long walletId) {
        List<WalletAmount> walletAmountList = walletDao.selectAccountAmountByWalletId(walletId);
        return ResponseResult.success(walletAmountList);
    }

    @Override
    public ResponseResult updateWalletAccount(Long walletId, Long accountTypeId, Double amount) {
        Map map = new HashMap();
        map.put("walletId", walletId);
        map.put("accountTypeId", accountTypeId);
        map.put("amount", amount);
        walletDao.updateWalletAccount(map);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateWalletAccountDown(Long walletId, Long accountTypeId, Double amount) {
        Map map = new HashMap();
        map.put("walletId", walletId);
        map.put("accountTypeId", accountTypeId);
        map.put("amount", amount);
        walletDao.updateWalletAccountDown(map);
        return ResponseResult.success();
    }


}
