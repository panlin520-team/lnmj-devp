package com.lnmj.wallet.business.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.OrderStatusEnum;
import com.lnmj.common.Enum.OrderTypeEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeStoreEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeWalletEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.ListPageUntil;
import com.lnmj.common.utils.NumberUtils;
import com.lnmj.wallet.business.ICapitalPoolService;
import com.lnmj.wallet.business.IWalletService;
import com.lnmj.wallet.entity.*;
import com.lnmj.wallet.entity.VO.WalletAccountVO;
import com.lnmj.wallet.repository.*;
import com.lnmj.wallet.serviceProxy.*;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


@Service("capitalPoolServiceImpl")
@Transactional
public class CapitalPoolServiceImpl implements ICapitalPoolService {
    @Resource
    private CapitalPoolDao capitalPoolDao;
    @Resource
    PayApi payApi;
    @Resource
    StoreApi storeApi;
    @Resource
    private WalletDao walletDao;

    @Override
    public ResponseResult selectCapitalPool(int pageNum, int pageSize, Long storeId) {
        if (storeId!=null&&storeId==0){
            storeId = null;
        }


        CapitalPool capitalPool = new CapitalPool();
        capitalPool.setStoreId(storeId);
        List<CapitalPool> capitalPoolList = capitalPoolDao.selectCapitalPoolList(capitalPool);


        //匹配资金类型
        for (CapitalPool pool : capitalPoolList) {
            if (pool.getType() == 1) {
                pool.setTypeName("预收");
            } else if (pool.getType() == 2) {
                pool.setTypeName("实收");
            }
        }
        //匹配支付方式
        List<Map> mapList = (List<Map>) payApi.selectPayTypeList("1").getResult();
        for (CapitalPool pool : capitalPoolList) {
            for (Map map1 : mapList) {
                if (pool.getPayType() == Integer.parseInt(map1.get("payTypeId").toString())) {
                    pool.setPayTypeName(map1.get("payTypeName").toString());
                }
            }
        }
        //匹配门店
        List<Map> mapListStore = (List<Map>) storeApi.selectStoreAll().getResult();
        for (CapitalPool pool : capitalPoolList) {
            for (Map map1 : mapListStore) {
                if (pool.getStoreId() == Integer.parseInt(map1.get("storeId").toString())) {
                    pool.setStoreName(map1.get("name").toString());
                }
            }
        }
        //匹配账户

        List<RechargeType> mapListRechargeType = walletDao.selectRechargeType();
        for (CapitalPool pool : capitalPoolList) {
            for (RechargeType rechargeType : mapListRechargeType) {
                if (pool.getAdvancesReceivedAccount() != null && pool.getAdvancesReceivedAccount().toString().equals(rechargeType.getAccountTypeId().toString())) {
                    pool.setAdvancesReceivedAccountName(rechargeType.getAccountType());
                }
            }
        }
        Map mapRsultPage = ListPageUntil.listPage(capitalPoolList, pageSize, pageNum);
        Map mapResult = new HashMap();
        if (((List) mapRsultPage.get("list")).size() > 0) {
            mapResult.put("list", mapRsultPage.get("list"));
            mapResult.put("total", mapRsultPage.get("total"));
            return ResponseResult.success(mapResult);
        }
        return ResponseResult.error(new Error(ResponseCodeWalletEnum.CAPITAL_POOL_FIND_NO.getCode(), ResponseCodeWalletEnum.CAPITAL_POOL_FIND_NO.getDesc()));
    }

    @Override
    public ResponseResult addCapPool(Integer payType, Integer type, String amount, Long storeId, Long advancesReceivedAccount,String orderNumber) {
        CapitalPool capitalPool = new CapitalPool();
        capitalPool.setPayType(payType);
        capitalPool.setType(type);
        capitalPool.setAmount(new BigDecimal(amount));
        capitalPool.setResidueAmount(new BigDecimal(amount));
        capitalPool.setStoreId(storeId);
        capitalPool.setAdvancesReceivedAccount(advancesReceivedAccount);
        capitalPool.setOrderNumber(orderNumber);
        capitalPoolDao.addCapitalPool(capitalPool);
        return ResponseResult.success(capitalPool.getId());
    }

    @Override
    public ResponseResult addTransfer(Long capitalPoolId, Double payAmount, Long payType, Long toStoreId) {
        //查看这个门店此账户类型下的预收
        CapitalPool capitalPool = new CapitalPool();
        capitalPool.setStoreId(toStoreId);
        capitalPool.setType(1);
        capitalPool.setAdvancesReceivedAccount(payType);
        List<CapitalPool> capitalPoolList = capitalPoolDao.selectCapitalPoolList(capitalPool);
        Double yushouSum = 0.00;
        for (CapitalPool pool : capitalPoolList) {
            yushouSum = yushouSum + pool.getAmount().doubleValue();
        }
        Double needHauBo = 0.00;//需要划拨金额
        if (yushouSum < payAmount) {
            //预收金额小于账户支付金额，就进行划拨
            needHauBo = payAmount - yushouSum;
            //查看除此门店 其他哪些门店有此预收
            CapitalPool capitalPoolOther = new CapitalPool();
            capitalPoolOther.setNotStoreId(toStoreId);
            capitalPoolOther.setType(1);
            capitalPoolOther.setAdvancesReceivedAccount(payType);
            List<CapitalPool> capitalPoolListOther = capitalPoolDao.selectCapitalPoolList(capitalPoolOther);
            capitalPoolListOther.sort((x, y) -> Double.compare(x.getResidueAmount().doubleValue(), y.getResidueAmount().doubleValue()));
            Double leijiHuaBo = 0.00;
            for (CapitalPool pool : capitalPoolListOther) {
                leijiHuaBo = leijiHuaBo + pool.getResidueAmount().doubleValue();
                Transfer transfer = new Transfer();
                transfer.setTransferToStoreId(toStoreId);
                transfer.setTransferFromStoreId(pool.getStoreId());
                if (leijiHuaBo - needHauBo >= 0) {
                    transfer.setAmount(new BigDecimal(needHauBo - (leijiHuaBo - pool.getResidueAmount().doubleValue())));
                    transfer.setTransferType(1);
                    transfer.setCapitalPoolId(capitalPoolId);
                    capitalPoolDao.addTransfer(transfer);
                    CapitalPool capitalPool1Update = new CapitalPool();
                    capitalPool1Update.setId(pool.getId());
                    capitalPool1Update.setResidueAmount(new BigDecimal(needHauBo - (leijiHuaBo - pool.getResidueAmount().doubleValue())));
                    capitalPool1Update.setResidueAmountUpOrDown(1);
                    capitalPoolDao.updateCapitalPool(capitalPool1Update);
                    break;
                } else {
                    transfer.setAmount(new BigDecimal(pool.getResidueAmount().doubleValue()));
                }
                transfer.setTransferType(1);
                transfer.setCapitalPoolId(capitalPoolId);
                capitalPoolDao.addTransfer(transfer);
                //修改划拨来源门店剩余预收金额
                CapitalPool capitalPool1Update = new CapitalPool();
                capitalPool1Update.setId(pool.getId());
                capitalPool1Update.setResidueAmount(new BigDecimal(pool.getResidueAmount().doubleValue()));
                capitalPool1Update.setResidueAmountUpOrDown(1);
                capitalPoolDao.updateCapitalPool(capitalPool1Update);
            }

        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectTransferIn(int pageNum, int pageSize, Long capitalPoolId, Long storeId) {

        Map map = new HashMap();
        map.put("capitalPoolId", capitalPoolId);
        map.put("transferToStoreId", storeId);
        List<Transfer> transferList = capitalPoolDao.selectTransfer(map);
        List<Map> resultStoreAll = (List) (storeApi.selectStoreAll().getResult());
        for (Transfer transfer : transferList) {
            for (Map map1 : resultStoreAll) {
                if (transfer.getTransferToStoreId().toString().equals(map1.get("storeId").toString())) {
                    transfer.setTransferToStoreName(map1.get("name").toString());
                }
            }
        }
        Map mapRsultPage = ListPageUntil.listPage(transferList, pageSize, pageNum);
        Map mapResult = new HashMap();
        mapResult.put("list", mapRsultPage.get("list"));
        mapResult.put("total", mapRsultPage.get("total"));
        return ResponseResult.success(mapResult);

    }

    @Override
    public ResponseResult selectTransferOut(int pageNum, int pageSize, Long capitalPoolId, Long storeId) {

        Map map = new HashMap();
        map.put("capitalPoolId", capitalPoolId);
        map.put("transferFromStoreId", storeId);
        List<Transfer> transferList = capitalPoolDao.selectTransfer(map);
        List<Map> resultStoreAll = (List) (storeApi.selectStoreAll().getResult());
        for (Transfer transfer : transferList) {
            for (Map map1 : resultStoreAll) {
                if (transfer.getTransferToStoreId().toString().equals(map1.get("storeId").toString())) {
                    transfer.setTransferToStoreName(map1.get("name").toString());
                }
            }
        }
        Map mapRsultPage = ListPageUntil.listPage(transferList, pageSize, pageNum);
        Map mapResult = new HashMap();
        mapResult.put("list", mapRsultPage.get("list"));
        mapResult.put("total", mapRsultPage.get("total"));
        return ResponseResult.success(mapResult);
    }

    @Override
    public ResponseResult reduceForwardSaleByCondition(CapitalPool capitalPool) {
        int resultInt = capitalPoolDao.reduceForwardSaleByCondition(capitalPool);
        return ResponseResult.success(resultInt);
    }

    @Override
    public ResponseResult deleteCapPoolByOrderNumber(CapitalPool capitalPool) {
        int resultInt = capitalPoolDao.deleteCapPoolByOrderNumber(capitalPool);
        return ResponseResult.success(resultInt);
    }


}
