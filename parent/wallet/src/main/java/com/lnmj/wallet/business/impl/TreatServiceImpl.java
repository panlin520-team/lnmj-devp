package com.lnmj.wallet.business.impl;

import com.alibaba.fastjson.JSONObject;
import com.lnmj.common.Enum.OrderTypeEnum;
import com.lnmj.common.Enum.ProductTypeEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.AccountRuleEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.NumberUtils;
import com.lnmj.wallet.business.ITreatService;
import com.lnmj.wallet.business.IWalletConsumeService;
import com.lnmj.wallet.business.IWalletService;
import com.lnmj.wallet.entity.*;
import com.lnmj.wallet.repository.IAccountRuleDao;
import com.lnmj.wallet.repository.WalletAccountDao;
import com.lnmj.wallet.repository.WalletAmountDao;
import com.lnmj.wallet.repository.WalletDao;
import com.lnmj.wallet.serviceProxy.AccountApi;
import com.lnmj.wallet.serviceProxy.ProductApi;
import com.lnmj.wallet.serviceProxy.StoreApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service("treatServiceImpl")
public class TreatServiceImpl implements ITreatService {
    @Resource
    private IAccountRuleDao accountRuleDao;
    @Resource
    private IWalletService walletService;
    @Resource
    private WalletAccountDao walletAccountDao;
    @Resource
    private WalletDao walletDao;
    @Resource
    private WalletAmountDao walletAmountDao;
    @Resource
    private StoreApi storeApi;
    @Resource
    private ProductApi productApi;
    @Resource
    private AccountApi accountApi;
    @Resource
    private IWalletConsumeService walletConsumeRecordService;


    @Override
    public ResponseResult treatAccountInit(WalletAmount walletAmount) {
        int i = walletDao.updateWalletAmount(walletAmount);
        return ResponseResult.success(i);
    }

    @Override
    public ResponseResult treatAccountRecharge(WalletAmount walletAmount) {
        return walletService.recharge(walletAmount);
    }

    @Override
    public ResponseResult selectUserWalletConsumeRecord(WalletConsumeRecord walletConsumeRecord) {
        return walletConsumeRecordService.selectUserWalletConsumeRecord(walletConsumeRecord);
    }

    @Override
    public ResponseResult selectWalletByCarNumber(String carNum) {
        Long walletId = walletDao.selectWalletIdByCardNumber(carNum);
        return ResponseResult.success(walletId);
    }

    @Override
    public ResponseResult userTreat(Long userId, String orderLink, String mobile, String cartNumber, Long productType,
                                    Long productId, String productName, String contacts, String phone) {
        //获取请客参数
        Map map = new HashMap<>();
        List<RechargeType> rechargeTypeList = walletAccountDao.selectWalletAccountList(map);
        if(rechargeTypeList != null && rechargeTypeList.size() <= 0){
            return ResponseResult.error(new Error(AccountRuleEnum.TREAT_ACOOUNT_NULL.getCode(),
                    AccountRuleEnum.TREAT_ACOOUNT_NULL.getDesc()));
        }
        Long accountTypeId = null;
        for (RechargeType rechargeType : rechargeTypeList) {
            if(rechargeType.getIsQingKe()==1){
                accountTypeId=rechargeType.getAccountTypeId();
            }
        }
        if(accountTypeId == null){  //没有请客账户
            return ResponseResult.error(new Error(AccountRuleEnum.TREAT_ACOOUNT_NULL.getCode(),
                    AccountRuleEnum.TREAT_ACOOUNT_NULL.getDesc()));
        }
        Wallet wallet = walletDao.selectWalletInfoById(userId);
        Long walletId = wallet.getWalletId();
        map.put("walletId", walletId);
        map.put("accountTypeId", accountTypeId);
        List<WalletAmount> walletAmounts = walletDao.selectBalanceInfoById(map);
        if(walletAmounts.size() <=0){
            return ResponseResult.error(new Error(AccountRuleEnum.TREAT_ACOOUNT_NULL.getCode(),
                    AccountRuleEnum.TREAT_ACOOUNT_NULL.getDesc()));
        }
        List<AccountUseRule> useRuleList = accountRuleDao.selectUseAccountRule(accountTypeId);
        BigDecimal productPrice =  BigDecimal.ZERO;
        Date presentationTime = new Date();
        Date presentationEndTime = addDate(presentationTime,31);
        if(useRuleList.size() != 0){
            //判断规则
            for(AccountUseRule useRule:useRuleList){
                //判断商品类型是否正确
                Long productTypeId = useRule.getProductType();
                if(productTypeId.equals(productType)){
                    return ResponseResult.error(new Error(AccountRuleEnum.PRODUCT_TYPE_ERROR.getCode(),
                            AccountRuleEnum.PRODUCT_TYPE_ERROR.getDesc()));
                }
                //读取商品的价格（商品）
                //productOriginalPrice 原价   retailPrice零售价  activityRetailPrice活动零售价
                //facilitatorPrice零售价（服务商） unionPrice零售价(联盟商) retailPriceVip1零售价VIP1
                // retailPriceVip2 零售价VIP2  retailPriceVip3零售价VIP3  retailPriceVip4零售价VIP4
                // retailPriceVip5 零售价VIP5  retailPriceVip6零售价VIP6
                String priceType = useRule.getPayPriceType();
                String productIds = productId.toString();
                if(productType.intValue() == ProductTypeEnum.PRODUCT.getCode()){
                    ResponseResult responseResultPS = productApi.selectPorductListById(productIds);
                    List<HashMap> ps = (List<HashMap>) responseResultPS.getResult();
                    if(ps.size()<=0){
                        return ResponseResult.error(new Error(AccountRuleEnum.PRODUCT_NULL.getCode(),
                                AccountRuleEnum.PRODUCT_NULL.getDesc()));
                    }
                    productPrice = (BigDecimal) ps.get(0).get(priceType);
                }else if(ProductTypeEnum.SERVICE.getCode().equals(productType.intValue())){
                    ResponseResult responseResultP = productApi.selectServiceListById(productIds);
                    List<HashMap> p = (List<HashMap>) responseResultP.getResult();
                    if(p.size()<=0){
                        return ResponseResult.error(new Error(AccountRuleEnum.PRODUCT_NULL.getCode(),
                                AccountRuleEnum.PRODUCT_NULL.getDesc()));
                    }
                    productPrice = (BigDecimal) p.get(0).get(priceType);
                }
                //折扣
//                BigDecimal discount = useRule.getDiscount();
//                if(discount.compareTo(BigDecimal.ZERO)==1){
//                    productPrice = productPrice.multiply(discount);
//                }
                //判断商品总价是否超出了请客基金
                if(walletAmounts.get(0).getAmount().compareTo(productPrice)<0){
                    return ResponseResult.error(new Error(AccountRuleEnum.TREAT_SMALL.getCode(),
                            AccountRuleEnum.TREAT_SMALL.getDesc()));
                }
                //判断会员等级（用户）
//                JSONObject jsonObject = accountApi.selectAccountByCondition(mobile);
//                Long membershipLevelId =  (Long)jsonObject.get("membershipLevelId");
//                if(membershipLevelId.equals(useRule.getMemberLevel())){
//                    return ResponseResult.error(new Error(AccountRuleEnum.MEMBERLEVEL_ERROR.getCode(),
//                            AccountRuleEnum.MEMBERLEVEL_ERROR.getDesc()));
//                }
                //TODO
                //判断接收用户账号类型（用户）
                Date startTime = useRule.getStartTime();
                //根据startTime 判断是老用户还是新用户
                Integer useAccount = useRule.getUseAccount();
                //判断使用总价格和总次数（门店）
                BigDecimal useMax = useRule.getUseMax();  //使用总价格
                Integer useSum = useRule.getUseSum();  //使用总次数
                //从赠送表查询有多少次数和总价格
                //判断使用类型（用户） 1 无限使用（可接受多人请客）；2 互斥使用（规则有效期内，只接受一人请客）；3 只使用一次（账号只接受一人请客一次）
                Integer useMethod = useRule.getUseMethod();
                //有效期
                Integer productUseDay = useRule.getProductUseDay();
                presentationEndTime = addDate(presentationTime,productUseDay);
            }
        }
        BigDecimal amount = walletAmounts.get(0).getAmount().subtract(productPrice);
        String orderNumber = NumberUtils.getRandomOrderNo();
        storeApi.insertPresentation(OrderTypeEnum.TEART_ORDER.getCode(),orderNumber,orderLink,mobile,cartNumber,productId,productName,contacts,phone,presentationTime,presentationEndTime);
        WalletAmount walletAmount = new WalletAmount();
        walletAmount.setWalletId(walletId);
        walletAmount.setAccountTypeId(accountTypeId);
        walletAmount.setAmount(amount);
        walletAmountDao.updateAmount(walletAmount);
        return ResponseResult.success();
    }

    public Date addDate(Date date,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }
}
