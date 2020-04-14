package com.lnmj.store.business.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.*;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeExperiencecardEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeOrderEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeStockEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.DateUtil;
import com.lnmj.common.utils.NumberUtils;
import com.lnmj.common.utils.StringToListUtil;
import com.lnmj.store.business.IExperienceCardService;
import com.lnmj.store.entity.*;
import com.lnmj.store.entity.VO.ExperiencecardVO;
import com.lnmj.store.entity.VO.PayTypeAndAmountVO;
import com.lnmj.store.repository.ICustomerDao;
import com.lnmj.store.repository.IExperienceCardDao;
import com.lnmj.store.repository.IStoreDao;
import com.lnmj.store.repository.ISubordBuyLimitDao;
import com.lnmj.store.serviceProxy.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.lnmj.common.utils.DateUtil.compareTime;

/**
 * 〈〉
 *
 * @Author panlin
 * @create 2019/8/5
 */
@Transactional
@Service("experienceCardService")
public class ExperienceCardService implements IExperienceCardService {
    @Resource
    private IExperienceCardDao iExperienceCardDao;
    @Resource
    private ISubordBuyLimitDao iSubordBuyLimitDao;
    @Resource
    private IStoreDao iStoreDao;
    @Resource
    private ProductApi productApi;
    @Resource
    private OrderApi orderApi;
    @Resource
    DataApi dataApi;
    @Resource
    WalletApi walletApi;
    @Resource
    PayApi payApi;
    @Resource
    ICustomerDao iCustomerDao;

    @Override
    public ResponseResult selectExperienceCardList(int pageNum, int pageSize, String keyWordCardName, String keyWordCarNum, String storeId) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("keyWordCardName", keyWordCardName);
        map.put("keyWordCarNum", keyWordCarNum);
        List<Experiencecard> experiencecards = iExperienceCardDao.selectExperienceCardList(map);
        List<Experiencecard> experiencecardList = new ArrayList<>();
        if (storeId != null) {
            for (int i = 0; i < experiencecards.size(); i++) {
                if (experiencecards.get(i).getStoreId().indexOf(storeId) != -1 || StringUtils.isBlank(experiencecards.get(i).getStoreId())) {
                    experiencecardList.add(experiencecards.get(i));
                }
            }
        } else if (storeId == null) {
            experiencecardList.addAll(experiencecards);
        }


        if (experiencecardList.size() > 0) {
            PageInfo pageInfo = new PageInfo(experiencecardList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.EXPERIENCECARD_LIST_NULL.getCode(), ResponseCodeExperiencecardEnum.EXPERIENCECARD_LIST_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectExperienceCardByCardId(Long cardId) {
        Map map = new HashMap();
        map.put("cardId", cardId);
        Experiencecard experiencecards = iExperienceCardDao.selectExperienceCardByCardId(map);
        return ResponseResult.success(experiencecards);
    }

    @Override
    public ResponseResult selectExpCardProductListByCarNum(int pageNum, int pageSize, String cardNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<ExperiencecardProduct> experiencecardProducts = iExperienceCardDao.selectExpCardProductListByCarNum(cardNum);
        if (experiencecardProducts.size() > 0) {
            PageInfo pageInfo = new PageInfo(experiencecardProducts);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.EXPERIENCECARD_PRODUCT_LIST_NULL.getCode(), ResponseCodeExperiencecardEnum.EXPERIENCECARD_PRODUCT_LIST_NULL.getDesc()));
    }

    @Override
    public ResponseResult deleteExpCardByCarNum(String cardNum) {
        if (StringUtils.isBlank(cardNum)) {
            return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.EXPERIENCECARD_NUM_NULL.getCode(), ResponseCodeExperiencecardEnum.EXPERIENCECARD_NUM_NULL.getDesc()));
        }
        int result = iExperienceCardDao.deleteExpCardByCarNum(cardNum);
        int resultproduct = iExperienceCardDao.deleteExpCardProductByCarNum(cardNum);

        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteExpCardProductById(Long cardProductId) {
        if (cardProductId == null) {
            return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.CARDPRODUCTID_NULL.getCode(), ResponseCodeExperiencecardEnum.CARDPRODUCTID_NULL.getDesc()));
        }
        int resultproduct = iExperienceCardDao.deleteExpCardProductById(cardProductId);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateExpCardProductById(ExperiencecardProduct experiencecardProduct) {
        if (experiencecardProduct.getCardProductId() == null) {
            return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.CARDPRODUCTID_NULL.getCode(), ResponseCodeExperiencecardEnum.CARDPRODUCTID_NULL.getDesc()));
        }
        //如果修改了数量 要重新计算其出库单价
        if (experiencecardProduct.getUseTotalTimes() != null) {
            Map map = new HashMap();
            map.put("cardProductId", experiencecardProduct.getCardProductId());
            ExperiencecardProduct experiencecardProduct1 = iExperienceCardDao.selectExperienceCardProductById(map);

            experiencecardProduct.setOutStockPrice(experiencecardProduct1.getOutStockPrice() * experiencecardProduct1.getUseTotalTimes() / experiencecardProduct.getUseTotalTimes());
        }
        int resultproduct = iExperienceCardDao.updateExpCardProductById(experiencecardProduct);

        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateExpCardById(Experiencecard experiencecard) {
        if (experiencecard.getCardId() == null) {
            return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.CARDID_NULL.getCode(), ResponseCodeExperiencecardEnum.CARDID_NULL.getDesc()));
        }
        int resultproduct = iExperienceCardDao.updateExpCardById(experiencecard);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult addExpCard(Long subordBuyLimitId, Double account, String cardName, String createOperator, JSONArray productJsonArray, Long achievementPostId, String logoImage, String moreContent, Integer stockNum, Integer salesVolume, String purchaseDeadline) {
        //添加体验卡
        String CarNumStr = NumberUtils.getRandomOrderNo();
        Experiencecard experiencecard = new Experiencecard();
        experiencecard.setCardName(cardName);
        experiencecard.setAccount(account);
        experiencecard.setMoreContent(moreContent);
        experiencecard.setSubordBuyLimitId(subordBuyLimitId);
        experiencecard.setAchievementPostId(achievementPostId);
        experiencecard.setCardNum(CarNumStr);
        experiencecard.setCreateOperator(createOperator);
        experiencecard.setLogoImage(logoImage);
        experiencecard.setSalesVolume(salesVolume);
        experiencecard.setStockNum(stockNum);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
        if (purchaseDeadline != null) {
            Date date = null;
            try {
                date = simpleDateFormat.parse(purchaseDeadline);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            experiencecard.setPurchaseDeadline(date);
        }

        if (productJsonArray.size() == 0 || StringUtils.isBlank(((JSONObject) productJsonArray.get(0)).getString("carNum"))) {
            //第一次添加
            iExperienceCardDao.addExpCard(experiencecard);
        } else {
            //在原有体验卡基础上添加项目
            CarNumStr = ((JSONObject) productJsonArray.get(0)).getString("carNum");

            //添加体验卡项目
            List<ExperiencecardProduct> listProduct = productJsonArray.toJavaList(ExperiencecardProduct.class);
            for (ExperiencecardProduct experiencecardProduct : listProduct) {
                experiencecardProduct.setCardNum(CarNumStr);
                experiencecardProduct.setCreateOperator(createOperator);
                if (account != null) {
                    experiencecardProduct.setOutStockPrice(account / listProduct.size() / experiencecardProduct.getUseTotalTimes());
                }
            }

            Map map = new HashMap();
            map.put("list", listProduct);
            if (productJsonArray != null && productJsonArray.size() > 0) {
                iExperienceCardDao.addExpCardProduct(map);
            }
        }


        return ResponseResult.success();
    }

    @Override
    public ResponseResult addExpCardOrder(String memoNum, Double account, String cardName, String cardNum, String createOperator, String linkPhone, String purchaserName, Integer storeId, String memberNum, String beauticians, String payTypeAndAmount) {
        //查询水单号是否重复
        Map mapMemo = new HashMap();
        mapMemo.put("memoNum", memoNum);
        int resultInt = iExperienceCardDao.checkOrderMemo(mapMemo);
        int resultInt1 = Integer.parseInt(orderApi.checkOrderMemo(memoNum).getResult().toString());
        if (resultInt > 0 || resultInt1 > 0) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.MEMO_EXIST.getCode(),
                    ResponseCodeOrderEnum.MEMO_EXIST.getDesc()));
        }


        Experiencecard experiencecard = iExperienceCardDao.selectExperienceCardByCardNum(cardNum);

        //检验体验卡库存数量是否足够
        if (experiencecard.getStockNum() <= 0) {
            return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.EXPERIENCECARD_STOCK_NUM_NULL.getCode(), ResponseCodeExperiencecardEnum.EXPERIENCECARD_STOCK_NUM_NULL.getDesc()));
        }
        //检验当前时间是否在截至时间之后
        boolean isCanBuy = compareTime(new Date(), experiencecard.getPurchaseDeadline());
        if (isCanBuy) {
            //说明在指定时间后 无法购买
            return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.PURCHASEDEADLINE_BEYOND.getCode(), ResponseCodeExperiencecardEnum.PURCHASEDEADLINE_BEYOND.getDesc()));
        }

        //查看购买体验卡的所属限购分组
        Long buyExpSubLimitId = experiencecard.getSubordBuyLimitId();
        //查询出所有的体验卡
        Map map = new HashMap();
        List<Experiencecard> experiencecardList = iExperienceCardDao.selectExperienceCardList(map);
        //查看此用户所购买的所有体验卡订单
        map.put("memberNum", memberNum);
        List<ExperiencecardOrder> experiencecardOrderList = iExperienceCardDao.selectExpCardOrder(map);
        if (experiencecardOrderList.size() != 0 && buyExpSubLimitId != 0) {
            //查看此限购分组允许购买的个数
            SubordBuyLimit subordBuyLimit = iSubordBuyLimitDao.selectSubordBuyLimitById(buyExpSubLimitId);
            int canBuyNumber = subordBuyLimit.getSubordBuyLimitNumber();
            int buyLimitCardNum = 0;
            for (ExperiencecardOrder itemOrder : experiencecardOrderList) {
                for (Experiencecard itemCard : experiencecardList) {
                    if (StringUtils.equals(itemOrder.getCardNum(), itemCard.getCardNum()) && itemCard.getSubordBuyLimitId() == buyExpSubLimitId) {
                        buyLimitCardNum = buyLimitCardNum + 1;
                    }
                }
            }
            if (buyLimitCardNum >= canBuyNumber) {
                return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.CARDPRODUCTID_SUBORD_BUY_LIMIT.getCode(), ResponseCodeExperiencecardEnum.CARDPRODUCTID_SUBORD_BUY_LIMIT.getDesc()));
            }
        }
        //添加订单
        ExperiencecardOrder experiencecardOrder = new ExperiencecardOrder();
        String orderNum = OrderTypeEnum.EXPCARD_ORDER.getCode() + NumberUtils.getRandomOrderNo();
        experiencecardOrder.setMemoNum(memoNum);
        experiencecardOrder.setCardOrderCode(orderNum);
        experiencecardOrder.setAccount(account);
        experiencecardOrder.setCardName(cardName);
        experiencecardOrder.setCardNum(cardNum);
        experiencecardOrder.setLinkPhone(linkPhone);
        experiencecardOrder.setPurchaserName(purchaserName);
        experiencecardOrder.setStoreId(storeId);
        experiencecardOrder.setMemberNum(memberNum);
        experiencecardOrder.setCreateOperator(createOperator);
        experiencecardOrder.setOrderStatus(OrderStatusEnum.PAID.getCode());//添加后 马上支付成功
        experiencecardOrder.setPayTypeAndAmount(payTypeAndAmount);
        experiencecardOrder.setBeauticians(beauticians);
        experiencecardOrder.setOrderType(OrderTypeEnum.EXPCARD_ORDER.getCode());
        iExperienceCardDao.addExpCardOrder(experiencecardOrder);


        //添加订单详情
        List<ExperiencecardProduct> experiencecardProducts = iExperienceCardDao.selectExpCardProductListByCarNum(cardNum);
        List<ExperiencecardOrderDetail> experiencecardOrderDetailList = new ArrayList<>();
        for (ExperiencecardProduct experiencecardProduct : experiencecardProducts) {
            ExperiencecardOrderDetail experiencecardOrderDetail = new ExperiencecardOrderDetail();
            experiencecardOrderDetail.setCardOrderCode(cardNum + "_" + NumberUtils.getRandomOrderNoChild());
            experiencecardOrderDetail.setProductCode(experiencecardProduct.getProductCode());
            experiencecardOrderDetail.setProductKind(experiencecardProduct.getProductKind());
            experiencecardOrderDetail.setProductName(experiencecardProduct.getProductName());
            experiencecardOrderDetail.setUseLimit(experiencecardProduct.getUseLimit());
            experiencecardOrderDetail.setUseTotalTimes(experiencecardProduct.getUseTotalTimes());
            experiencecardOrderDetailList.add(experiencecardOrderDetail);
        }
        if (experiencecardOrderDetailList.size() == 0) {
            return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.NO_EXPERIENCECARD_PRODUCT_CAN_NOT_ORDER.getCode(), ResponseCodeExperiencecardEnum.NO_EXPERIENCECARD_PRODUCT_CAN_NOT_ORDER.getDesc()));
        }
        Map mapAdd = new HashMap();
        mapAdd.put("list", experiencecardOrderDetailList);
        iExperienceCardDao.addExpCardOrderDetail(mapAdd);
        Map result = new HashMap();

        //查看门店的所属行业
        String industryId = iStoreDao.selectStoreById(Long.parseLong(storeId.toString())).getIndustryID().toString();
        //下单成功，计算员工业绩-并支付成功
        Map mapcardId = new HashMap();
        mapcardId.put("cardId", experiencecard.getCardId());
        Experiencecard experiencecard1 = iExperienceCardDao.selectExperienceCardByCardId(mapcardId);

        ResponseResult responseResultYeji = null;
        if (experiencecard1.getAchievementPostId() == null) {
            //体验卡没有设置业绩，找储值业绩
            responseResultYeji = dataApi.insertLadderDetailed(0, Long.parseLong(storeId.toString()), beauticians, orderNum, payTypeAndAmount, 1, experiencecard.getCardId().toString(), null, industryId);
        } else {
            responseResultYeji = dataApi.insertLadderDetailed(ProductTypeEnum.EXPERIENCECARD.getCode(), Long.parseLong(storeId.toString()), beauticians, orderNum, payTypeAndAmount, 1, experiencecard.getCardId().toString(), null, industryId);
        }


        if (responseResultYeji != null && responseResultYeji.getResponseStatusType().getMessage().equals("Success")) {
            result.put("业绩：", responseResultYeji.getResult());
        }


        //下单成功，计算客户测评-并支付成功
        dataApi.insertEvaluatingDetailed(orderNum, memberNum, null, account, 6, Long.parseLong(ProductTypeEnum.EXPERIENCECARD.getCode().toString()));   //1为商品订单，2为护理订单，5为充值订单，6体验卡订单


        //下单成功后，添加用户拥有体验卡
        ExperiencecardUser experiencecardUser = new ExperiencecardUser();
        experiencecardUser.setExperiencecardNum(cardNum);
        experiencecardUser.setMemberNum(memberNum);
        iExperienceCardDao.addExpCardUser(experiencecardUser);
        List<ExperiencecardProductUser> experiencecardProductList = new ArrayList<>();
        for (ExperiencecardProduct experiencecardProduct : experiencecardProducts) {
            ExperiencecardProductUser experiencecardProductUser = new ExperiencecardProductUser();
            experiencecardProductUser.setExperiencecardNum(experiencecardProduct.getCardNum());
            experiencecardProductUser.setMemberNum(memberNum);
            experiencecardProductUser.setExperiencecardProductCode(experiencecardProduct.getProductCode());
            experiencecardProductUser.setTotalTimes(experiencecardProduct.getUseTotalTimes());
            experiencecardProductUser.setUseTimes(0);
            experiencecardProductUser.setUseLimit(experiencecardProduct.getUseLimit());
            experiencecardProductUser.setExperiencecardUserId(experiencecardUser.getId());
            experiencecardProductUser.setCardOrderCode(orderNum);
            experiencecardProductList.add(experiencecardProductUser);
        }
        Map mapForAdd = new HashMap();
        mapForAdd.put("list", experiencecardProductList);
        iExperienceCardDao.addExpCardUserProduct(mapForAdd);
        result.put("order", "下单成功");
        //下单成功后减库存，加销量
        Map map1 = new HashMap();
        map1.put("cardNum", cardNum);
        iExperienceCardDao.updateExpAddSalesVolumeReduceStock(map1);

        //支付成功后添加资金池实收
        JSONArray payTypeAndAmountArray = JSONArray.parseArray(payTypeAndAmount);
        for (int i = 0; i < payTypeAndAmountArray.size(); i++) {
            Integer payType = payTypeAndAmountArray.getJSONObject(i).getInteger("payType");
            Double amount = payTypeAndAmountArray.getJSONObject(i).getDouble("amount");
            Integer resultInteger = (Integer) (walletApi.addCapPool(payType, 2, amount, Long.parseLong(storeId.toString()), null, orderNum).getResult());
            //划拨
            //根据支付方式查看账户类型
            Map resutleMap = (Map) (payApi.selectPayTypeById(Long.parseLong(payType.toString())).getResult());
            walletApi.addTransfer(Long.parseLong(resultInteger.toString()), amount, Integer.parseInt(resutleMap.get("accountType").toString()), Long.parseLong(storeId.toString()));
        }
        return ResponseResult.success(result);
    }

    @Override
    public ResponseResult selectExpCardOrder(int pageNum, int pageSize,
                                             String keyWordLinkPhone,
                                             String keyWordLinkName,
                                             String cardName,
                                             String startdate,
                                             String endDate,
                                             String memoNum,
                                             Integer storeId) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("keyWordLinkPhone", keyWordLinkPhone);
        map.put("keyWordLinkName", keyWordLinkName);
        map.put("cardName", cardName);
        map.put("startdate", startdate);
        map.put("endDate", endDate);
        map.put("memoNum", memoNum);
        map.put("storeId", storeId);
        List<ExperiencecardOrder> experiencecardOrders = iExperienceCardDao.selectExpCardOrder(map);
        //查询所有的订单状态

        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            for (ExperiencecardOrder experiencecardOrder : experiencecardOrders) {
                if (orderStatusEnum.getCode() == experiencecardOrder.getOrderStatus()) {
                    experiencecardOrder.setOrderStatusName(orderStatusEnum.getDesc());
                }
            }
        }

        if (experiencecardOrders.size() > 0) {
            PageInfo pageInfo = new PageInfo(experiencecardOrders);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.EXPERIENCECARD_ORDER_LIST_NULL.getCode(), ResponseCodeExperiencecardEnum.EXPERIENCECARD_ORDER_LIST_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectExpCardOrderDetail(int pageNum, int pageSize, String cardOrderCode) {
        PageHelper.startPage(pageNum, pageSize);
        List<ExperiencecardOrderDetail> experiencecardOrderDetails = iExperienceCardDao.selectExpCardOrderDetail(cardOrderCode);
        if (experiencecardOrderDetails.size() > 0) {
            PageInfo pageInfo = new PageInfo(experiencecardOrderDetails);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.EXPERIENCECARD_ORDER_DETAIL_LIST_NULL.getCode(), ResponseCodeExperiencecardEnum.EXPERIENCECARD_ORDER_DETAIL_LIST_NULL.getDesc()));
    }

    @Override
    public ResponseResult updateExpCardOrderDetail(ExperiencecardOrderDetail experiencecardOrderDetail) {
        //获取订单详情

        ExperiencecardOrderDetail experiencecardOrderDetail1 = iExperienceCardDao.selectExpCardOrderDetailById(experiencecardOrderDetail.getCardOrderDetailId());
        experiencecardOrderDetail.setUseTime(experiencecardOrderDetail1.getUseTime() + 1);
        if (experiencecardOrderDetail1.getUseTotalTimes() == experiencecardOrderDetail1.getUseTime() + 1) {//如果使用次数大于等于总次数了
            experiencecardOrderDetail.setProjectUseState(0);//使用完毕
        } else if (experiencecardOrderDetail1.getUseTotalTimes() < experiencecardOrderDetail1.getUseTime() + 1) {
            return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.USE_TIMES_ERROR.getCode(), ResponseCodeExperiencecardEnum.USE_TIMES_ERROR.getDesc()));
        }
        iExperienceCardDao.updateExpCardOrderDetail(experiencecardOrderDetail);

        return ResponseResult.success();

    }

    @Override
    public ResponseResult addUseRecord(ExperiencecardUseRecord experiencecardUseRecord) {
        iExperienceCardDao.addUseRecord(experiencecardUseRecord);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateUseRecordStatus(ExperiencecardUseRecord experiencecardUseRecord) {
        iExperienceCardDao.updateUseRecordStatus(experiencecardUseRecord);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectUseRecord(int pageNum, int pageSize, Long cardOrderDetailId) {
        PageHelper.startPage(pageNum, pageSize);

        List<ExperiencecardUseRecord> experiencecardUseRecords = iExperienceCardDao.selectUseRecord(cardOrderDetailId);

        if (experiencecardUseRecords.size() > 0) {
            PageInfo pageInfo = new PageInfo(experiencecardUseRecords);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.EXPERIENCECARD_RECORD_NULL.getCode(), ResponseCodeExperiencecardEnum.EXPERIENCECARD_RECORD_NULL.getDesc()));

    }

    @Override
    public ResponseResult selectExpCardUser(String memberNum) {
        Map map = new HashMap();
        List<ExperiencecardProductUser> experiencecardProductUserList = iExperienceCardDao.selectExperienceCardProductUserList(new HashMap());//查看体验卡商品用户中间表
        List<Map> mapList = (List<Map>) productApi.selectAllProduct().getResult();
        Map map1 = new HashMap();
        map1.put("memberNum", memberNum);
        List<ExperiencecardVO> experiencecardVOS = iExperienceCardDao.selectExperienceCardVoUserList(map1);
        map.put("memberNum", memberNum);
        map.put("userExperiencecardList", experiencecardVOS);


        return ResponseResult.success(map);
    }

    @Override
    public ResponseResult selectExpCardUserProduct(String memberNum, Long experiencecardUserId) {
        Map map = new HashMap();
        map.put("memberNum", memberNum);
        map.put("experiencecardUserId", experiencecardUserId);
        List<ExperiencecardProductUser> experiencecardProductUserList = iExperienceCardDao.selectExpCardUserProduct(map);//查看体验卡商品用户中间表
        List<Map> mapProduct = (List<Map>) productApi.selectAllProduct().getResult();
        List<Map> mapSubClass = (List<Map>) dataApi.selectSubclassListAll().getResult();
        for (ExperiencecardProductUser experiencecardProductUser : experiencecardProductUserList) {
            for (Map mapItem : mapProduct) {
                if (experiencecardProductUser.getExperiencecardProductCode().equals(mapItem.get("productCode"))) {
                    experiencecardProductUser.setExperiencecardProductType(Integer.parseInt(mapItem.get("productType").toString()));
                    if (Integer.parseInt(mapItem.get("productType").toString()) == 1) {
                        experiencecardProductUser.setExperiencecardProductTypeName("实体产品");
                    } else if (Integer.parseInt(mapItem.get("productType").toString()) == 2) {
                        experiencecardProductUser.setExperiencecardProductTypeName("服务产品");
                    }
                    experiencecardProductUser.setExperiencecardProductName(mapItem.get("productName").toString());
                    experiencecardProductUser.setSubClassId(Long.parseLong(mapItem.get("subClassID").toString()));
                }
            }

            for (Map subClass : mapSubClass) {
                if (experiencecardProductUser.getSubClassId() != null && experiencecardProductUser.getSubClassId().toString().equals(subClass.get("subclassID").toString())) {
                    experiencecardProductUser.setSubClassName(subClass.get("subclassName").toString());
                }
            }
        }

        for (ExperiencecardProductUser experiencecardProductUser : experiencecardProductUserList) {
            if (experiencecardProductUser.getExperiencecardProductName()==null){
                experiencecardProductUser.setExperiencecardProductName("此商品已被删除或下架");
                experiencecardProductUser.setExperiencecardProductTypeName("此商品已被删除或下架");
                experiencecardProductUser.setSubClassName("此商品已被删除或下架");
            }
        }

        return ResponseResult.success(experiencecardProductUserList);
    }


    @Override
    public ResponseResult updateUserExpUseTimes(String staffNumber, Long id, String experiencecardNum, String memberNum, String productCode, Long storeId, String storeName, String productName, String postAndBeautician, String createOperator, String memberName, String memberMobile) {
        //查询默认客户
        String custK3Number = null;
        Customer customer = new Customer();
        customer.setCompanyId(storeId.toString());
        customer.setCompanyType("3");
        Customer customerResult = iCustomerDao.selectDefaultCust(customer);
        if (customerResult == null) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.DEFAULT_CUST_IS_NOT_FIND.getCode(),
                    ResponseCodeStockEnum.DEFAULT_CUST_IS_NOT_FIND.getDesc()));
        } else {
            custK3Number = customerResult.getK3Number();
        }


        //验证库存
        List<Map> stockList = (List<Map>) productApi.selectStockList(3l, Long.parseLong(storeId.toString())).getResult();
        List<Map> stockProductList;
        if (stockList.size() == 0) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_FIND.getCode(), ResponseCodeStockEnum.STOCK_NOT_FIND.getDesc()));
        } else {
            stockProductList = (List<Map>) productApi.selectStockProductListNoPage(stockList.get(0).get("stockCode").toString()).getResult();
        }
        if (stockProductList == null) {
            stockProductList = new ArrayList<>();
        }
        Integer stockNum = 0;
        Integer stockQuantity = 0;
        for (Map stockProduct : stockProductList) {
            if (productCode.equals(stockProduct.get("productCode"))) {
                stockNum = stockNum + Integer.parseInt(stockProduct.get("aveailableNumber").toString());
            }
        }
        //根据商品code找到商品
        if ((productApi.selectProductByCode(productCode).getResult() != null &&
                ((Map) productApi.selectProductByCode(productCode).getResult()).get("stockQuantity") != null)) {
            stockQuantity = Integer.parseInt((((Map) productApi.selectProductByCode(productCode).getResult()).get("stockQuantity").toString()));
        }

        stockNum = stockNum + stockQuantity;

        if (1 > stockNum) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.STOCK_PRODUCT_NOT_ENOUGH.getCode(),
                    ResponseCodeOrderEnum.STOCK_PRODUCT_NOT_ENOUGH.getDesc()));
        }


        Map map = new HashMap();
        map.put("id", id);
        //判断使用次数是否超出总次数
        ExperiencecardProductUser experiencecardProductUser = iExperienceCardDao.selectExperienceCardProductUserList(map).get(0);
        if (experiencecardProductUser.getUseTimes() + 1 > experiencecardProductUser.getTotalTimes()) {
            return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.USE_TIMES_SURPASS_TOTAL_TIMES.getCode(), ResponseCodeExperiencecardEnum.USE_TIMES_SURPASS_TOTAL_TIMES.getDesc()));
        }
        //验证当前时间在有效期范围内
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String uselimit = experiencecardProductUser.getUseLimit();
        String startDateStr = uselimit.split(" - ")[0].trim();
        String endDateStr = uselimit.split(" - ")[1].trim();

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = simpleDateFormat.parse(startDateStr);
            endDate = simpleDateFormat.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Boolean isEffectiveDate = DateUtil.isEffectiveDate(new Date(), startDate, endDate);
        if (!isEffectiveDate) {
            return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.NOW_LIMIT_USE_DATE_ERROR.getCode(), ResponseCodeExperiencecardEnum.NOW_LIMIT_USE_DATE_ERROR.getDesc()));

        }

        // 修改体验卡使用次数
        iExperienceCardDao.updateUserExpUseTimes(map);
        //添加使用记录
        ExperiencecardUseRecord experiencecardUseRecord = new ExperiencecardUseRecord();
        experiencecardUseRecord.setProductCode(productCode);
        experiencecardUseRecord.setStoreId(Integer.parseInt(storeId.toString()));
        experiencecardUseRecord.setUseDate(new Date());
        experiencecardUseRecord.setStoreName(storeName);
        experiencecardUseRecord.setProductName(productName);
        experiencecardUseRecord.setExperiencecardNum(experiencecardNum);
        experiencecardUseRecord.setBookingBeauticianName(postAndBeautician);
        experiencecardUseRecord.setLinkName(memberName);
        experiencecardUseRecord.setLinkMobile(memberMobile);
        experiencecardUseRecord.setExperiencecardProductUserId(id);
        //添加使用记录
        iExperienceCardDao.addUseRecord(experiencecardUseRecord);
        //根据商品code查看商品类型
        List<Map> mapList = (List<Map>) productApi.selectAllProduct().getResult();
        Long productType = null;
        Long subclassID = null;
        Double price = null;
        for (Map map1 : mapList) {
            if (map1.get("productCode").toString().equals(productCode)) {
                productType = Long.parseLong(map1.get("productType").toString());
                subclassID = Long.parseLong(map1.get("subClassID").toString());
                price = Double.parseDouble(map1.get("retailPrice").toString());
            }
        }
        if (productType == null) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTP_IS_NOTEXIT.getCode(), ResponseCodeProductEnum.PRODUCTP_IS_NOTEXIT.getDesc()));
        }

        ResponseResult responseResultYeJi = null;
        String industryId = iStoreDao.selectStoreById(Long.parseLong(storeId.toString())).getIndustryID().toString();
        if (productType == Long.parseLong(ProductTypeEnum.SERVICE.getCode().toString())) {
            //划卡成功后，计算员工业绩
            //查看当前服务商品已经有的职位
            //查看当前服务商品所属小类需要哪些职位
            List<Map> allSubclassList = (List<Map>) dataApi.selectSubclassByConditionNoPage(null).getResult();//查询所有当前行业小类
            JSONArray postAndBeauticianArray = JSONArray.parseArray(postAndBeautician);
            JSONArray needPostArray = null;
            for (Map allSubclassItem : allSubclassList) {
                if (allSubclassItem.get("subclassID").toString().equals(subclassID.toString())) {
                    String needPosts = allSubclassItem.get("postCategoryIds").toString();
                    needPostArray = JSONArray.parseArray(needPosts);
                    if (needPostArray.size() != postAndBeauticianArray.size()) {
                        return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_NO_POST.getCode(), ResponseCodeOrderEnum.ORDER_NO_POST.getDesc()));
                    }
                }
            }
            responseResultYeJi = dataApi.insertLadderDetailed(ProductTypeEnum.SERVICE.getCode(), Long.parseLong(storeId.toString()), postAndBeauticianArray.toJSONString(), "TYHK" + experiencecardUseRecord.getRecordId().toString(), null, 1, null, price, industryId);
        }


        //划卡后的库存影响
        //减库存
        JSONArray jsonArrayXiaoShou = new JSONArray();
        JSONArray jsonArrayQiTa = new JSONArray();
        //根据商品编号查看商品单位
        List<Map> mapProductAllList = (List<Map>) productApi.selectAllProduct().getResult();
        //查询所有的单位列表
        List<Map> mapUnitList = (List<Map>) productApi.selectUnitListNoPage().getResult();
        String unitId = null;
        Integer productSubordinate = null;
        for (Map map1 : mapProductAllList) {
            if (map1.get("productCode").toString().equals(productCode)) {
                unitId = map1.get("unitId").toString();
                productSubordinate = Integer.parseInt(map1.get("productSubordinate").toString());
            }
        }

        //查询订单子订单
        if (productSubordinate == 0) {
            if (productType == Long.parseLong(ProductTypeEnum.PRODUCT.getCode().toString())) {
                JSONObject jsonObjectXiaoShou = new JSONObject();
                jsonObjectXiaoShou.put("productCode", productCode);
                jsonObjectXiaoShou.put("productName", productName);
                jsonObjectXiaoShou.put("sendNumber", 1);
                jsonObjectXiaoShou.put("unit", unitId);
                jsonObjectXiaoShou.put("stock", stockList.get(0).get("stockCode"));
                jsonObjectXiaoShou.put("stockStatus", 1);
                jsonArrayXiaoShou.add(jsonObjectXiaoShou);
            } else if (productType == Long.parseLong(ProductTypeEnum.SERVICE.getCode().toString())) {
                JSONObject jsonObjectQiTa = new JSONObject();
                jsonObjectQiTa.put("productCode", productCode);
                jsonObjectQiTa.put("productName", productName);
                jsonObjectQiTa.put("sendNumber", 1);
                jsonObjectQiTa.put("unit", unitId);
                jsonObjectQiTa.put("stock", stockList.get(0).get("stockCode"));
                jsonObjectQiTa.put("stockStatus", 1);
                jsonArrayQiTa.add(jsonObjectQiTa);
            }


            //查看门店名称
            Store storeInfo = iStoreDao.selectStoreById(storeId);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = formatter.format(new Date());

            if (jsonArrayXiaoShou.size() != 0) {
                if (StringUtils.isBlank(staffNumber)){
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getCode(),
                            ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getDesc()));
                }
                ResponseResult resultStock1 = productApi.outstorage(staffNumber,stockList.get(0).get("stockId").toString(), jsonArrayXiaoShou.toJSONString(), "销售出库", "标准销售出库", dateStr, "人民币", storeInfo.getName(), storeInfo.getK3Number(), custK3Number, storeInfo.getName(), createOperator);
                if (!resultStock1.getResponseStatusType().getMessage().equals("Success") &&
                        resultStock1.getResponseStatusType().getError().getErrorMsg().equals("仓库商品没库存")) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                            ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc()));
                }
                ExperiencecardUseRecord experiencecardUseRecordUpdate = new ExperiencecardUseRecord();
                experiencecardUseRecordUpdate.setRecordId(experiencecardUseRecord.getRecordId());
                experiencecardUseRecordUpdate.setOutStorageIdXiaoShou(resultStock1.getResult().toString());
                iExperienceCardDao.updateUseRecord(experiencecardUseRecordUpdate);
            }
            if (jsonArrayQiTa.size() != 0) {
                ResponseResult resultStock2 = productApi.outstorage(
                        jsonArrayQiTa.toJSONString(),
                        "其他出库",
                        "标准其他出库",
                        dateStr,
                        stockList.get(0).get("stockCode").toString(),
                        customerResult.getName(),
                        "GENERAL",
                        OutStorageBusinessTypeEnum.STANDARD_OTHER.getDesc(),
                        "业务组织",
                        storeInfo.getName(),
                        storeInfo.getK3Number(),
                        customerResult.getK3Number(),
                        stockList.get(0).get("stockId").toString()
                );
                if (!resultStock2.getResponseStatusType().getMessage().equals("Success") &&
                        resultStock2.getResponseStatusType().getError().getErrorMsg().equals("仓库商品没库存")) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                            ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc()));
                }
                ExperiencecardUseRecord experiencecardUseRecordUpdate = new ExperiencecardUseRecord();
                experiencecardUseRecordUpdate.setRecordId(experiencecardUseRecord.getRecordId());
                experiencecardUseRecordUpdate.setOutStorageIdQiTa(resultStock2.getResult().toString());
                iExperienceCardDao.updateUseRecord(experiencecardUseRecordUpdate);
            }
        } else if (productSubordinate == 1) {
            //如果是第三方 - 直接减少第三方库存
            productApi.updateStockQuantity(productCode, "down");
        }
        if (productType == Long.parseLong(ProductTypeEnum.SERVICE.getCode().toString())) {
            //如何是护理---划卡后添加一个划卡的预约订单
            Integer orderType = OrderTypeEnum.APPOINTMENT_ORDER.getCode();
            Integer channel = OrderChannelEnum.POS_ORDER.getCode();
            String orderLink = memberName;
            String mobile = memberMobile;
            String cardNumber = memberNum;
            Long nurseStore = storeId;
            Integer orderStatus = OrderStatusEnum.PAID.getCode();
            String orderNum = "TYHK" + OrderTypeEnum.APPOINTMENT_ORDER.getCode().toString() + NumberUtils.getRandomOrderNo();
            String orderNumber = orderNum;
            orderApi.insertOrder(orderType, channel, orderLink, mobile, cardNumber, nurseStore, orderStatus, orderNumber, "TYHK" + experiencecardUseRecord.getRecordId().toString());

            String orderId = orderNum + "_" + NumberUtils.getRandomOrderNoChild();
            Integer productNum = 1;
            String bookingBeauticianIds = postAndBeautician;
            Integer productTypeId = Integer.parseInt(productType.toString());
            orderApi.insertProductOrder(orderId, orderNumber, productCode, productName, productNum, bookingBeauticianIds, productTypeId, storeId, subclassID, "TYHK" + experiencecardUseRecord.getRecordId().toString());

            Integer appointmentType = OrderTypeEnum.APPOINTMENT_ORDER.getCode();
            Integer appointmentStatus = AppointmentStatusEnum.PAY.getCode();
            orderApi.insertAppointmentOrder(orderNum, appointmentType, appointmentStatus, storeId, 1, "TYHK" + experiencecardUseRecord.getRecordId().toString());
        }

        //定制项目划卡成功后增加销量
        productApi.updateProductSales(productCode, Integer.parseInt(productType.toString()), "up");

        if (responseResultYeJi != null && responseResultYeJi.getResponseStatusType().getMessage().equals("Success")) {
            return ResponseResult.success("划卡成功"+responseResultYeJi.getResult());
        }
        return ResponseResult.success("划卡成功");
    }

    @Override
    public ResponseResult experiencecardProductUserRefuse(Long id, Integer refuseTimes) {
        //查看体验卡商品
        ExperiencecardProductUser experiencecardProductUser = new ExperiencecardProductUser();
        experiencecardProductUser.setId(id);
        ExperiencecardProductUser experiencecardProductUserResult = iExperienceCardDao.selectExperienceCardProductUserById(experiencecardProductUser);
        if (refuseTimes > experiencecardProductUserResult.getTotalTimes() - experiencecardProductUserResult.getUseTimes()) {
            return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.REFUSE_TIMES_BIGGER_REFUND_TIMES.getCode(), ResponseCodeExperiencecardEnum.REFUSE_TIMES_BIGGER_REFUND_TIMES.getDesc()));
        }
        //修改指定id用户体验卡商品的总次数
        Map map = new HashMap();
        map.put("id", id);
        map.put("refuseTimes", refuseTimes);
        iExperienceCardDao.updateExperienceCardProductUserTotalTimesById(map);
        //添加退货次数记录
        ExperiencecardProductUserRefuse experiencecardProductUserRefuse = new ExperiencecardProductUserRefuse();
        experiencecardProductUserRefuse.setExperiencecardProductUserId(id);
        experiencecardProductUserRefuse.setRefuseTimes(refuseTimes);
        iExperienceCardDao.addExperiencecardProductUserRefuse(experiencecardProductUserRefuse);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectExperiencecardProductUserRefuseList(Long id) {
        Map map = new HashMap();
        map.put("id", id);
        List<ExperiencecardProductUserRefuse> experiencecardProductUserRefuseList = iExperienceCardDao.selectExperiencecardProductUserRefuseList(map);
        return ResponseResult.success(experiencecardProductUserRefuseList);
    }

    @Override
    public ResponseResult addUserExpUseTimes(Long id) {
        // 修改体验卡使用次数
        iExperienceCardDao.addUserExpUseTimes(id);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateExpOrderMemoNum(String cardOrderCode, String memoNum, String modifyOperator) {
        Map map = new HashMap();
        map.put("cardOrderCode", cardOrderCode);
        map.put("memoNum", memoNum);
        map.put("modifyOperator", modifyOperator);
        iExperienceCardDao.updateExpOrderMemoNum(map);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult checkOrderMemo(String memoNum) {
        Map map = new HashMap();
        map.put("memoNum", memoNum);
        int resultInt = iExperienceCardDao.checkOrderMemo(map);
        return ResponseResult.success(resultInt);
    }

    @Override
    public ResponseResult updateAllExpCardProductOutStockPrice(Long cardId) {
        //根据id查找cardNum
        Map map1 = new HashMap();
        map1.put("cardId", cardId);
        String cardNum = iExperienceCardDao.selectExperienceCardByCardId(map1).getCardNum();
        //根据卡号查看所有的体验卡项目
        List<ExperiencecardProduct> experiencecardProductList = iExperienceCardDao.selectExpCardProductListByCarNum(cardNum);
        if (experiencecardProductList.size() != 0) {
            //根据卡号找到体验卡总价
            Double amount = iExperienceCardDao.selectExperienceCardByCardNum(cardNum).getAccount();
            //跟新每个项目的价格
            List<ExperiencecardProduct> listProduct = experiencecardProductList;
            for (ExperiencecardProduct experiencecardProduct : listProduct) {
                experiencecardProduct.setOutStockPrice(amount / listProduct.size() / experiencecardProduct.getUseTotalTimes());
            }

            Map map = new HashMap();
            map.put("list", listProduct);
            //批量修改
            iExperienceCardDao.updateExpCardProductBatch(map);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult cancleExpOrder(String orderNumbers, Integer orderStatus) {
        if (orderNumbers == null) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_NUMBER_NULL.getCode(), ResponseCodeOrderEnum.ORDER_NUMBER_NULL.getDesc()));
        }
        List<String> orderNumbersList = StringToListUtil.StringToListUtil(orderNumbers);
        Map map = new HashMap();
        map.put("orderStatus", OrderStatusEnum.CANCEL.getCode());
        map.put("list", orderNumbersList);
        int resultInt = iExperienceCardDao.cancleExpOrder(map);
        if (resultInt <= 0 || orderStatus >= OrderStatusEnum.PAID.getCode()) {//如果订单已经支付，无法取消
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_CANCEL_FAIL.getCode(), ResponseCodeOrderEnum.ORDER_CANCEL_FAIL.getDesc()));
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateExpOrderStatus(String cardOrderCode, Integer orderStatus) {
        Map map = new HashMap();
        map.put("cardOrderCode", cardOrderCode);
        map.put("orderStatus", orderStatus);
        int resultInt = iExperienceCardDao.updateExpOrderStatus(map);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult checkExpIsUse(String experiencecardNum, String memberCode) {
        Map map = new HashMap();
        map.put("experiencecardNum", experiencecardNum);
        map.put("memberCode", memberCode);
        int resutl = iExperienceCardDao.checkExpIsUse(map);
        return ResponseResult.success(resutl);
    }

    @Override
    public ResponseResult updateExpCarStockNumUp(String experiencecardNum) {
        iExperienceCardDao.updateExpCarStockNumUp(experiencecardNum);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectExpOrderCashAll(String memberNumber) {
        Map map = new HashMap();
        map.put("memberNum", memberNumber);
        Map result = new HashMap();
        result.put("memberCashAll", 0);
        //[{"payType":3,"amount":100,"payTypeName":"储值支付","accountType":1,"payTypeCategory":2}]
        List<String> stringList = iExperienceCardDao.selectMemberNumberPayTypeAndAmount(map);
        if (stringList != null && stringList.size() > 0) {
            BigDecimal all = new BigDecimal(0);
            for (String s : stringList) {
                if (StringUtils.isNotBlank(s) && !s.equals("0")) {
                    List<PayTypeAndAmountVO> payTypeAndAmountVOList = JSONObject.parseArray(s, PayTypeAndAmountVO.class);
                    for (PayTypeAndAmountVO payTypeAndAmountVO : payTypeAndAmountVOList) {
                            all = all.add(payTypeAndAmountVO.getAmount());
                    }
                }
            }
            result.put("memberCashAll", all);
        }
        return ResponseResult.success(result);
    }


}
