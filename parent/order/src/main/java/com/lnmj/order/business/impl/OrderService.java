package com.lnmj.order.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.*;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.*;
import com.lnmj.common.baseController.ExportController;
import com.lnmj.common.baseController.HttpServletRequestWarpper;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.EnumUtil;
import com.lnmj.common.utils.NumberUtils;
import com.lnmj.common.utils.StringToListUtil;
import com.lnmj.order.business.IOrderService;
import com.lnmj.order.entity.*;
import com.lnmj.order.entity.VO.OrderVO;
import com.lnmj.order.entity.VO.PayTypeAndAmountVO;
import com.lnmj.order.repository.IOrderDao;
import com.lnmj.order.serviceProxy.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: panlin
 * @Date: 2019/5/31 12:05
 * @Description:
 */
@Transactional
@Service("orderService")
public class OrderService implements IOrderService {
    @Resource
    IOrderDao iOrderDao;
    @Resource
    ProductApi productApi;
    @Resource
    ExportController exportController;
    @Resource
    DataApi dataApi;
    @Resource
    StoreApi storeApi;
    @Resource
    WalletApi walletApi;
    @Resource
    PayApi payApi;
    @Resource
    MemberUserApi memberUserApi;
    @Resource
    K3CLOUDApi k3CLOUDApi;

    @Override
    public ResponseResult selectAppointmentByBeauticianAndDate(String beauticianId, String nurseTime) {
        Map map = new HashMap<>();
        if (StringUtils.isNotBlank(beauticianId)) {
            map.put("beauticianId", beauticianId);
        }
        if (StringUtils.isNotBlank(nurseTime)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parse = format.parse(nurseTime);
                map.put("nurseTime", parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        List<ProductOrder> orderList = iOrderDao.selectAppointmentByBeauticianAndDate(map);
        if (orderList.size() > 0) {
            return ResponseResult.success(orderList);
        }
        return ResponseResult.error(new Error(ResponseCodeOrderEnum.NOT_FIND_APPOINTMENT.getCode(),
                ResponseCodeOrderEnum.NOT_FIND_APPOINTMENT.getDesc()));
    }

    @Override
    public ResponseResult createServiceOrder(Integer orderType,
                                             Integer channel,
                                             Long storeId,
                                             String productIds,
                                             String cardNum,
                                             String orderLink,
                                             String mobile,
                                             Long industryID,
                                             String postCategoryIds,
                                             Long appointmentId,
                                             String memoNum,
                                             Double totalPrice,
                                             String remark, String nurseDate) {
        //查询水单号是否重复
        Map mapMemo = new HashMap();
        mapMemo.put("memoNum", memoNum);
        int resultInt = iOrderDao.checkOrderMemo(mapMemo);
        int resultInt1 = Integer.parseInt(storeApi.checkOrderMemo(memoNum).getResult().toString());
        if (resultInt > 0 || resultInt1 > 0) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.MEMO_EXIST.getCode(),
                    ResponseCodeOrderEnum.MEMO_EXIST.getDesc()));
        }

        JSONArray productArray = JSONArray.parseArray(productIds);
        //验证库存
        List<Map> stockList = (List<Map>) productApi.selectStockList(3l, storeId).getResult();
        List<Map> stockProductList;
        if (stockList.size() == 0) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_FIND.getCode(), ResponseCodeStockEnum.STOCK_NOT_FIND.getDesc()));
        } else {
            stockProductList = (List<Map>) productApi.selectStockProductListNoPage(stockList.get(0).get("stockCode").toString()).getResult();
        }
        if (stockProductList == null) {
            stockProductList = new ArrayList<>();
        }
        for (int i = 0; i < productArray.size(); i++) {
            Integer stockNum = 0;
            Integer stockQuantity = 0;
            for (Map stockProduct : stockProductList) {
                if (productArray.getJSONObject(i).getString("productCode").equals(stockProduct.get("productCode"))) {
                    //根据商品code找商品第三方库存量
                    stockNum = stockNum + Integer.parseInt(stockProduct.get("aveailableNumber").toString());
                }
            }
            if ((productApi.selectProductByCode(productArray.getJSONObject(i).getString("productCode")).getResult() != null &&
                    ((Map) productApi.selectProductByCode(productArray.getJSONObject(i).getString("productCode")).getResult()).get("stockQuantity") != null)) {
                stockQuantity = Integer.parseInt((((Map) productApi.selectProductByCode(productArray.getJSONObject(i).getString("productCode")).getResult()).get("stockQuantity").toString()));
            }

            stockNum = stockNum + stockQuantity;
            if (productArray.getJSONObject(i).getInteger("productNum") > stockNum) {
                return ResponseResult.error(new Error(ResponseCodeOrderEnum.STOCK_PRODUCT_NOT_ENOUGH.getCode(),
                        ResponseCodeOrderEnum.STOCK_PRODUCT_NOT_ENOUGH.getDesc()));
            }
        }


        Order orderForAdd = new Order();
        //生成订单号
        String orderNum = null;
        if (orderType == OrderTypeEnum.SERVICE_ORDER.getCode()) {
            //如果是美容服务订单
            orderNum = "2" + NumberUtils.getRandomOrderNo();
        } else if (orderType == OrderTypeEnum.PRODUCT_ORDER.getCode()) {
            //如果是实体商品订单
            orderNum = "1" + NumberUtils.getRandomOrderNo();
        } else if (orderType == OrderTypeEnum.APPOINTMENT_ORDER.getCode()) {
            //如果是预约订单
            orderNum = "10" + NumberUtils.getRandomOrderNo();
        } else if (orderType == OrderTypeEnum.CUSTOM_ORDER.getCode()) {
            //如果是定制订单
            orderNum = "4" + NumberUtils.getRandomOrderNo();
        }
        List<Map> productList = (List<Map>) productApi.selectAllProduct().getResult();

        List<Map> allSubclassList = (List<Map>) dataApi.selectSubclassByConditionNoPage(null).getResult();//查询所有当前行业小类


        //创建主订单
        if (cardNum.equals("")) {
            //如果是散客
            orderForAdd.setOrderLink("散客");
        } else {
            //如果不是散客
            orderForAdd.setOrderLink(orderLink);
        }
        orderForAdd.setOrderNumber(orderNum);
        orderForAdd.setCardNumber(cardNum);
        orderForAdd.setChannel(channel);
        orderForAdd.setMobile(mobile);
        orderForAdd.setOrderType(orderType);
        orderForAdd.setTotalPrice(new BigDecimal(totalPrice));
        orderForAdd.setNurseStore(storeId);
        orderForAdd.setOrderStatus(OrderStatusEnum.UNPAID.getCode());
        orderForAdd.setMemoNum(memoNum);
        orderForAdd.setRemark(remark);
        iOrderDao.insertOrder(orderForAdd);


        JSONArray needPostArray = null;
        //创建子订单
        List<ProductOrder> productOrderList = new ArrayList<>();
        for (int i = 0; i < productArray.size(); i++) {
            List<Map> productMap = new ArrayList<>();
            for (Map allProduct : productList) {
                if (productArray.getJSONObject(i).getString("productCode").equals(allProduct.get("productCode").toString())) {
                    allProduct.put("productNum", productArray.getJSONObject(i).getInteger("productNum"));//订单子项商品数量
                    allProduct.put("originalPrice", productArray.getJSONObject(i).getDouble("originalPrice"));//订单子项原始单价
                    allProduct.put("discount", productArray.getJSONObject(i).getDouble("discount"));//订单子项折扣比例
                    allProduct.put("discountPrice", productArray.getJSONObject(i).getDouble("discountPrice"));//订单子项折扣后单价
                    productMap.add(allProduct);
                }
            }


            for (Map allSubclassItem : allSubclassList) {
                if (allSubclassItem.get("subclassID").toString().equals(productArray.getJSONObject(i).getString("subclassId"))) {
                    String needPosts = allSubclassItem.get("postCategoryIds").toString();
                    needPostArray = JSONArray.parseArray(needPosts);
                    if (productArray.getJSONObject(i).getInteger("productType") == ProductTypeEnum.SERVICE.getCode()
                            && orderType != OrderTypeEnum.CUSTOM_ORDER.getCode()) {
                        if (needPostArray.size() != productArray.getJSONObject(i).getJSONArray("postAndBeautician").size()) {
                            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_NO_POST.getCode(), ResponseCodeOrderEnum.ORDER_NO_POST.getDesc()));
                        }
                    }
                }
            }


            ProductOrder productOrder = new ProductOrder();
            productOrder.setOrderId(orderNum + "_" + NumberUtils.getRandomOrderNoChild());//子订单号
            productOrder.setOrderNumber(orderNum);//订单号
            productOrder.setProductCode(productMap.get(0).get("productCode").toString());//商品code
            productOrder.setProductName(productMap.get(0).get("productName").toString());//商品名称
            productOrder.setProductNum(Integer.parseInt(productMap.get(0).get("productNum").toString()));//商品数量
            productOrder.setOriginalPrice(new BigDecimal(Double.parseDouble(productMap.get(0).get("originalPrice").toString())));//商品原始单价
            productOrder.setDiscount(Double.parseDouble(productMap.get(0).get("discount").toString()));//折扣比例
            productOrder.setDiscountPrice(new BigDecimal(Double.parseDouble(productMap.get(0).get("discountPrice").toString())));//折后价
            productOrder.setBookingBeauticianIds(productArray.getJSONObject(i).getString("postAndBeautician"));
            if (StringUtils.isNotBlank(productArray.getJSONObject(i).getString("useLimit"))) {
                productOrder.setUseLimit(productArray.getJSONObject(i).getString("useLimit"));
            }
            //  productOrder.setPrice(Double.parseDouble(productMap.get(i).get("retailPrice").toString()));//以最终支付金额为准
            productOrder.setSubclassID(productMap.get(0).get("subClassID") == null ? null : Long.parseLong(productMap.get(0).get("subClassID").toString()));
            productOrder.setProductTypeId(Long.parseLong(productMap.get(0).get("productType").toString()));
            productOrder.setStoreId(storeId);
            productOrderList.add(productOrder);
        }

        Map map = new HashMap();
        map.put("list", productOrderList);
        iOrderDao.insertProductOrder(map);
        //如果是预约订单-添加预约中间表
        if (orderType == OrderTypeEnum.APPOINTMENT_ORDER.getCode()) {
            AppointmentOrder appointmentOrder = new AppointmentOrder();
            appointmentOrder.setAppointmentStatus(AppointmentStatusEnum.APPOINTMENT.getCode());
            appointmentOrder.setAppointmentType(orderType.toString());
            appointmentOrder.setOrderNumber(orderNum);
            appointmentOrder.setStoreId(storeId);
            appointmentOrder.setRemark(remark);
            iOrderDao.insertAppointmentOrder(appointmentOrder);
        }
        return ResponseResult.success(orderNum);
    }


    @Override
    public ResponseResult selectOrderList(int pageNum, int pageSize, String storeId, String keyWordOrderNum, String keyWordMobile, Integer orderType, Integer payStatus, Integer orderStatus, String date, Integer orderChannel) {
        PageHelper.startPage(pageNum, pageSize);
        if (orderType == null) {
            orderType = null;
        }
        if (payStatus == null) {
            payStatus = null;
        }
        Map map = new HashMap();
        String[] storeIdArray = storeId.replaceAll("]", "").replace("[", "").split(",");
        map.put("list", storeIdArray);
        map.put("orderChannel", orderChannel);
        map.put("keyWordOrderNum", keyWordOrderNum);
        map.put("keyWordMobile", keyWordMobile);
        map.put("orderType", orderType);
        map.put("date", date);
        map.put("orderStatus", orderStatus);
        List<OrderVO> orderVOList = new ArrayList<>();
        if (orderType != null && orderType.toString().equals(OrderTypeEnum.EXPCARD_ORDER.getCode().toString())) {
            List<Map> mapExpOrderList = (List<Map>) storeApi.selectExpOrderListByCondition(storeIdArray, keyWordOrderNum, keyWordMobile, orderType, payStatus, date, orderStatus);
        } else {
            orderVOList = iOrderDao.selectOrderListByCondition(map);
        }


        //查询所有的支付方式
        List<Map> mapList = (List<Map>) payApi.selectPayTypeList().getResult();
        for (OrderVO orderVO : orderVOList) {
            if (orderVO.getOrderStatus().equals(OrderStatusEnum.PAID)) {
                JSONArray jsonArray = JSON.parseArray(orderVO.getPayTypeAndAmount());
                for (int i = 0; i < jsonArray.size(); i++) {
                    for (Map map1 : mapList) {
                        if (jsonArray.getJSONObject(i).getInteger("payType").equals(map1.get("payTypeId"))) {
                            jsonArray.getJSONObject(i).put("payTypeName", map1.get("payTypeName"));
                        }
                    }
                }
                orderVO.setPayTypeAndAmount(JSON.toJSONString(jsonArray));
            }
        }


        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            for (OrderVO orderVO : orderVOList) {
                if (orderStatusEnum.getCode() == orderVO.getOrderStatus()) {
                    orderVO.setOrderStatueValue(orderStatusEnum.getDesc());
                }
            }
        }

        for (OrderVO orderVO : orderVOList) {
            for (OrderTypeEnum orderTypeEnum : OrderTypeEnum.values()) {
                if (orderTypeEnum.getCode() == orderVO.getOrderType()) {
                    orderVO.setOrderTypeValue(orderTypeEnum.getDesc());
                }
            }
        }

        //查询所有的子订单
        Map mapProductOrderList = new HashMap();
        List<ProductOrder> productOrderList = iOrderDao.selectProductOrderListByNum(mapProductOrderList);
        for (OrderVO orderVO : orderVOList) {
            List<ProductOrder> productOrders = new ArrayList<>();
            Double sum = 0.00;
            for (ProductOrder productOrder : productOrderList) {
                if (orderVO.getOrderNumber().toString().equals(productOrder.getOrderNumber())) {
                    productOrders.add(productOrder);
                    if (productOrder.getDiscountPrice() != null) {
                        sum = sum + productOrder.getDiscountPrice().doubleValue() * productOrder.getProductNum();
                    }
                }
            }
            orderVO.setTotalPrice(new BigDecimal(sum));
            orderVO.setProductOrderList(productOrders);
        }


        if (!orderVOList.isEmpty()) {
            PageInfo pageInfo = new PageInfo(orderVOList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_NULL.getCode(), ResponseCodeOrderEnum.ORDER_NULL.getDesc()));

    }

    @Override
    public ResponseResult selectOrderListNoPage() {
        Map map = new HashMap();
        List<OrderVO> orderVOList = iOrderDao.selectOrderList(map);
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            for (OrderVO orderVO : orderVOList) {
                if (orderStatusEnum.getCode() == orderVO.getOrderStatus()) {
                    orderVO.setOrderStatueValue(orderStatusEnum.getDesc());
                }
            }
        }
        if (!orderVOList.isEmpty()) {
            return ResponseResult.success(orderVOList);
        }
        return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_NULL.getCode(), ResponseCodeOrderEnum.ORDER_NULL.getDesc()));

    }

    @Override
    public ResponseResult selectOrderByNum(String orderNumbers) {
        if (StringUtils.isBlank(orderNumbers)) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_NUMBER_NULL.getCode(), ResponseCodeOrderEnum.ORDER_NUMBER_NULL.getDesc()));
        }
        Map map = new HashMap();
        List<String> orderNumbersList = StringToListUtil.StringToListUtil(orderNumbers);
        map.put("list", orderNumbersList);
        List<Order> orders = iOrderDao.selectOrderByNum(map);
        if (orders == null || orders.size() == 0) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.SELECT_ORDER_DETAIL_FAIL.getCode(), ResponseCodeOrderEnum.SELECT_ORDER_DETAIL_FAIL.getDesc()));
        }

        //查询出子商品订单列表
        List<ProductOrder> productOrderList = iOrderDao.selectProductOrderListByNum(map);
        for (ProductOrder productOrder : productOrderList) {
            productOrder.setBookingBeauticianIdsArray(JSON.parseArray(productOrder.getBookingBeauticianIds()));
        }
        for (Order orderItem : orders) {
            orderItem.setProductOrderList(productOrderList);
            orderItem.setPayTypeAndAmountArray(JSON.parseArray(orderItem.getPayTypeAndAmount()));
        }
        return ResponseResult.success(orders);
    }

    @Override
    public ResponseResult cancelOrder(String orderNumbers, Integer orderStatus) {
        if (orderNumbers == null) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_NUMBER_NULL.getCode(), ResponseCodeOrderEnum.ORDER_NUMBER_NULL.getDesc()));
        }
        List<String> orderNumbersList = StringToListUtil.StringToListUtil(orderNumbers);
        Map map = new HashMap();
        map.put("orderStatus", OrderStatusEnum.CANCEL.getCode());
        map.put("list", orderNumbersList);
        int resultInt = iOrderDao.cancelOrder(map);
        if (resultInt <= 0 || orderStatus >= OrderStatusEnum.PAID.getCode()) {//如果订单已经支付，无法取消
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_CANCEL_FAIL.getCode(), ResponseCodeOrderEnum.ORDER_CANCEL_FAIL.getDesc()));
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteOrder(String orderNumbers, String deleteReason) {
        if (StringUtils.isBlank(orderNumbers)) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_NUMBER_NULL.getCode(), ResponseCodeOrderEnum.ORDER_NUMBER_NULL.getDesc()));
        }
        if (StringUtils.isBlank(deleteReason)) {
            deleteReason = "无";
        }
        List<String> orderNumbersList = StringToListUtil.StringToListUtil(orderNumbers);
        Map map = new HashMap();
        map.put("list", orderNumbersList);
        map.put("deleteReason", deleteReason);

        //查看订单是否存在
        List<Order> orders = iOrderDao.selectOrderByNum(map);
        if (orders == null || orders.size() == 0) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_NULL.getCode(), ResponseCodeOrderEnum.ORDER_NULL.getDesc()));
        }

        int resultInt = iOrderDao.deleteOrder(map);
        if (resultInt <= 0) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_DELETE_FAIL.getCode(), ResponseCodeOrderEnum.ORDER_DELETE_FAIL.getDesc()));
        }
        //同时删除子订单
        iOrderDao.deleteProductOrder(map);
        return ResponseResult.success();
    }


    @Override
    public ResponseResult updateOrder(String productIds, Order order) {
        //非空判断
        if (order.getOrderNumber() == null) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_NUMBER_NULL.getCode(), ResponseCodeOrderEnum.ORDER_NUMBER_NULL.getDesc()));
        }
        if (StringUtils.isBlank(productIds)) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.PRODUCTIDS_NULL.getCode(), ResponseCodeOrderEnum.PRODUCTIDS_NULL.getDesc()));
        }
        if (order.getNurseStore() == null) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.STOREID_NULL.getCode(), ResponseCodeOrderEnum.STOREID_NULL.getDesc()));
        }
        if (order.getBookingTime() == null) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.BOOKINGTIME_NULL.getCode(), ResponseCodeOrderEnum.BOOKINGTIME_NULL.getDesc()));
        }
        if (order.getOrderType() == null) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_TYPE_NULL.getCode(), ResponseCodeOrderEnum.ORDER_TYPE_NULL.getDesc()));
        }
        if (order.getBookingBeauticianIds() == null) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.BOOKING_BEAUTICIAN_NULL.getCode(), ResponseCodeOrderEnum.BOOKING_BEAUTICIAN_NULL.getDesc()));
        }
        //判断此总订单所包含项目是否改变

        BigDecimal totalPrice = order.getTotalPrice();
        BigDecimal discountPrice = order.getTotalDiscount();
        BigDecimal payPrice = order.getTotalDiscount();
        if (productIds != null) {
            //计算总价
            ResponseResult responseResult = productApi.selectServiceListById(productIds);
            List<Map> resultMapList = (List<Map>) responseResult.getResult();
            totalPrice = new BigDecimal(0);
            for (Map productMapItem : resultMapList) {
                totalPrice = totalPrice.add(new BigDecimal(Double.parseDouble(productMapItem.get("productOriginalPrice").toString())));
            }
            //TODO 折扣逻辑处理
            discountPrice = new BigDecimal(0);
            //计算实际支付金额
            payPrice = totalPrice.subtract(discountPrice);
            //删除原来的子订单集合
            Map map = new HashMap();
            map.put("orderNumber", order.getOrderNumber().toString());
            iOrderDao.deleteProductOrder(map);
            //然后添加新的子订单集合
            List<ProductOrder> productOrderListForAdd = new ArrayList<>();
            for (Map productMapItem : resultMapList) {
                ProductOrder productOrder = new ProductOrder();
                productOrder.setOrderNumber(order.getOrderNumber());
                productOrder.setProductCode(productMapItem.get("productCode").toString());
                productOrder.setProductName((String) (productMapItem.get("productName")));
                productOrder.setProductNum(1);
                productOrder.setProductTypeId(Long.valueOf((Integer) ProductTypeEnum.SERVICE.getCode()));
                productOrder.setProductTypeName(ProductTypeEnum.SERVICE.getDesc());
                productOrder.setProductBrandId(Long.valueOf((Integer) productMapItem.get("productBrand")));
                //查看品牌名称
                String productBrandName = ((Map) (productApi.selectBrandById(Long.valueOf((Integer) productMapItem.get("productBrand"))).getResult())).get("productBrandName").toString();
                productOrder.setProductBrandName(productBrandName);//根据id查品牌名称
                productOrder.setProductCategoryId(Long.valueOf((Integer) productMapItem.get("productCategory")));
                productOrder.setStoreId(order.getNurseStore());
                productOrder.setStoreName(order.getStoreName());
                productOrder.setBookingTime(order.getBookingTime());
                productOrder.setBookingBeauticianIds(order.getBookingBeauticianIds());
                productOrderListForAdd.add(productOrder);
            }
            Map mapForAdd = new HashMap();
            mapForAdd.put("list", productOrderListForAdd);
            iOrderDao.insertProductOrder(mapForAdd);
        }
        order.setTotalPrice(totalPrice);
        order.setTotalDiscount(discountPrice);
        order.setPayPrice(payPrice);
        //创建总订单
        iOrderDao.updateOrder(order);

        return null;
    }

    @Override
    public ResponseResult exportOrder(HttpServletRequest reqs, HttpServletResponse resp, Long storeId) {
        HttpServletRequestWarpper req = new HttpServletRequestWarpper(reqs);
        Map map = new HashMap();
        map.put("storeId", storeId);
        List<OrderVO> orderVOList = iOrderDao.selectOrderList(map);
        req.addParameter("sheetName", "订单列表");
        String[] title = {"订单编号", "联系人", "联系人电话", "会员卡号", "金额"};
        req.addParameter("title", title);
        String[][] strings = new String[orderVOList.size()][title.length];
        for (int i = 0; i < orderVOList.size(); i++) {
            strings[i][0] = orderVOList.get(i).getOrderNumber().toString();
            strings[i][1] = orderVOList.get(i).getOrderLink();
            strings[i][2] = orderVOList.get(i).getMobile();
            strings[i][3] = orderVOList.get(i).getCardNumber();
            strings[i][4] = orderVOList.get(i).getPayPrice().toEngineeringString();
        }
        req.addParameter("value", strings);
        req.addParameter("fileName", "订单.xlsx");

        exportController.exportExcel(req, resp);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateOrderStatus(Long orderNumber, Integer status, Integer orderStatusOld) {
        if (orderNumber == null) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_NUMBER_NULL.getCode(), ResponseCodeOrderEnum.ORDER_NUMBER_NULL.getDesc()));
        }
        if (status == null) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_STATUS_NULL.getCode(), ResponseCodeOrderEnum.ORDER_STATUS_NULL.getDesc()));
        }
        Map map = new HashMap();
        map.put("orderNumber", orderNumber);
        map.put("status", status);
        int resultInt = iOrderDao.updateOrderStatus(map);
        if (resultInt <= 0) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_STATUS_UPDATE_FAIL.getCode(), ResponseCodeOrderEnum.ORDER_STATUS_UPDATE_FAIL.getDesc()));
        }

       /* List<ProductOrder> productOrders = null;
        if (status.equals(OrderStatusEnum.PAID.getCode())){
            //根据订单获取订单中的商品
            Map mapNum = new HashMap();
            List<String> orderNumbersList = StringToListUtil.StringToListUtil(String.valueOf(orderNumber));
            mapNum.put("list", orderNumbersList);
            productOrders = iOrderDao.selectProductOrderListByNum(mapNum);

        }
        ResponseResult responseResult;
        for (ProductOrder item:productOrders) {
            //获取每个商品的所属小类
            if (item.getProductTypeId()==2){
                //如果是服务
                 responseResult = productApi.selectServiceListById(item.getProductId().toString());
            }else{
                //如果是商品
                 responseResult = productApi.selectPorductListById(item.getProductId().toString());
            }
            //根据小类id 获取小类
            HashMap result = (HashMap)responseResult.getResult();
            ArrayList list = (ArrayList)result.get("list");
            Long subClassID = Long.parseLong(((HashMap)list.get(0)).get("subClassID").toString());


            System.out.println(subClassID);
        }
*/
        return ResponseResult.success();


    }

    @Override
    public ResponseResult updateOrderMemo(Long orderNumber, String memoNum) {
        if (orderNumber == null) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_NUMBER_NULL.getCode(), ResponseCodeOrderEnum.ORDER_NUMBER_NULL.getDesc()));
        }
        if (memoNum == null) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_MEMO_NULL.getCode(), ResponseCodeOrderEnum.ORDER_MEMO_NULL.getDesc()));
        }
        Map map = new HashMap();
        map.put("orderNumber", orderNumber);
        map.put("memoNum", memoNum);
        int resultInt = iOrderDao.updateOrderMemo(map);
        if (resultInt <= 0) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_MEMO_UPDATE_FAIL.getCode(), ResponseCodeOrderEnum.ORDER_MEMO_UPDATE_FAIL.getDesc()));
        }

        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectOrderListByCarNumber(int pageNum, int pageSize, Long cardNumber, Integer orderType) {
        if (cardNumber == null) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.CARNUMBER_NULL.getCode(), ResponseCodeOrderEnum.CARNUMBER_NULL.getDesc()));
        }
        HashMap<Object, Object> map = new HashMap<>();
        map.put("cardNumber", cardNumber);
        map.put("orderType", orderType);
        List<OrderVO> orderList = iOrderDao.selectOrderListByCarNumber(map);
        if (orderList == null || orderList.size() == 0) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_NULL.getCode(), ResponseCodeOrderEnum.ORDER_NULL.getDesc()));
        }
        return ResponseResult.success(orderList);
    }

    @Override
    public ResponseResult selectOrderProductByOrderNum(Long orderNumber) {
        ProductOrder productOrder = iOrderDao.selectProductOrderByNum(orderNumber);
        return ResponseResult.success(productOrder);
    }

    @Override
    public ResponseResult selectCreateTimeByCarNumber(String carNumber) {
        List<OrderVO> orderList = iOrderDao.selectCreateTimeByCarNumber(carNumber);
        return ResponseResult.success(orderList);
    }


    @Override
    public ResponseResult selectOrderListByCarNumAndDates(String cardNumber, String startDate, String endDate) {
        Map map = new HashMap();
        map.put("cardNumber", cardNumber);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        List<Order> orderList = iOrderDao.selectOrderListByCarNumAndDates(map);
        return ResponseResult.success(orderList);
    }


    @Override
    public ResponseResult selectTodayReceipt(String date, String storeId) {
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
        //根据日期查询订单
        String[] storeIdArray = storeId.replaceAll("]", "").replace("[", "").split(",");
        HashMap map = new HashMap();
        map.put("orderDate", orderDate);
        if (!(storeIdArray[0].equals(""))) {
            map.put("list", storeIdArray);
        }
        map.put("orderStatus", OrderStatusEnum.UNPAID.getCode());
        List<Order> orderList = iOrderDao.selectOrderListByDateAndStore(map);
        List<HashMap<String, Object>> list = new ArrayList<>();
        List<String> orderTypeNameList = new ArrayList<>();
        Map mapResult = new HashMap();
        if (orderList != null && orderList.size() > 0) {
            Map<Integer, String> mapOrderType = EnumUtil.getEnumToMap(OrderTypeEnum.class);
            Double total = 0.00;
            for (Map.Entry<Integer, String> entry : mapOrderType.entrySet()) {
              /*  if (entry.getKey() == 6 || entry.getKey() == 7 || entry.getKey() == 8 || entry.getKey() == 9) {
                    continue;
                }*/
                HashMap<String, Object> resultMap = new HashMap<>();
                Double d = 0.00;
                for (Order order : orderList) {
                    if (order.getOrderType().equals(entry.getKey())) {
                        d = d + order.getPayPrice().doubleValue();
                    }
                }
                resultMap.put("name", entry.getValue());
                resultMap.put("value", d);
                list.add(resultMap);
                orderTypeNameList.add(entry.getValue());
                total = total + d;
            }

            //查看所有的充值订单
            List<Map> mapList = (List<Map>) walletApi.selectRechargeByOrderNum(date, null, storeId).getResult();
            //查看所有的账户类型
            List<Map> mapListAccount = (List<Map>) walletApi.selectWalletAccountListNoPage().getResult();
            double sum = 0.00;
            for (Map map1 : mapList) {
                for (Map map2 : mapListAccount) {
                    if (map1.get("accountTypeId").toString().equals(map2.get("accountTypeId").toString()) && map2.get("isCuZhiYeJi").toString().equals("1")) {
                        sum = sum + Double.parseDouble(map1.get("amount").toString());
                    }
                }
            }


            mapResult.put("detailList", list);
            mapResult.put("total", total + sum);
            mapResult.put("orderTypeNameList", orderTypeNameList);
            return ResponseResult.success(mapResult);
        }
        return ResponseResult.success();
//        return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_NULL.getCode(),
//                ResponseCodeOrderEnum.ORDER_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectTodayCashReceipt(String date, String storeId) {
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
        //根据日期查询订单
        String[] storeIdArray = storeId.replaceAll("]", "").replace("[", "").split(",");
        HashMap map = new HashMap();
        map.put("orderDate", orderDate);
        if (storeIdArray[0].equals("")) {
            storeIdArray = null;
        }
        map.put("list", storeIdArray);
        List<String> orderStatusPay = new ArrayList<>();
        orderStatusPay.add(OrderStatusEnum.UNPAID.getCode().toString());
        orderStatusPay.add(OrderStatusEnum.REFUND.getCode().toString());
        map.put("orderStatusList", orderStatusPay);
        //查询只当时间指定门店所有的订单
        List<Order> orderList = iOrderDao.selectOrderListByDateAndStore(map);
        BigDecimal allCashPayPrice = new BigDecimal(0);
        if (orderList != null && orderList.size() > 0) {
            HashMap<String, Double> resultMap = new HashMap<>();
            for (Order order : orderList) {
                if (order.getPayTypeAndAmount() != null) {
                    JSONArray payTypeAndAmountArray = JSONArray.parseArray(order.getPayTypeAndAmount());
                    for (int i = 0; i < payTypeAndAmountArray.size(); i++) {
                        if (payTypeAndAmountArray.getJSONObject(i).getString("payTypeCategory").equals("1")) {
                            allCashPayPrice = allCashPayPrice.add(payTypeAndAmountArray.getJSONObject(i).getBigDecimal("amount"));
                        }
                    }

                }
            }

            //查看所有的充值订单
            List<Map> mapList = (List<Map>) walletApi.selectRechargeByOrderNum(date, null, storeId).getResult();
            //查看所有的账户类型
            List<Map> mapListAccount = (List<Map>) walletApi.selectWalletAccountListNoPage().getResult();
            double sum = 0.00;
            for (Map map1 : mapList) {
                for (Map map2 : mapListAccount) {
                    if (map1.get("accountTypeId").toString().equals(map2.get("accountTypeId").toString()) && map2.get("isCuZhiYeJi").toString().equals("1")) {
                        sum = sum + Double.parseDouble(map1.get("amount").toString());
                    }
                }
            }


            resultMap.put("allCashPayPrice", allCashPayPrice.doubleValue() + sum);
            return ResponseResult.success(resultMap);
        }
        return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_NULL.getCode(),
                ResponseCodeOrderEnum.ORDER_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectOrderTypeList() {
        Map<Integer, String> map = EnumUtil.getEnumToMap(OrderTypeEnum.class);
/*        Map mapnew = new HashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getKey().equals(5)) {
                mapnew.put(1, "充值订单");
            } else if (entry.getKey().equals(6)) {
                mapnew.put(entry.getKey(), entry.getValue());
            } else {
                mapnew.put(2, "支付订单");
            }
        }*/

        return ResponseResult.success(map);
    }

    @Override
    public ResponseResult selectOrderTypeListForYeji() {
        Map<Integer, String> map = EnumUtil.getEnumToMap(OrderTypeEnum.class);
        Map mapnew = new HashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getKey().equals(2) || entry.getKey().equals(3)) {
                mapnew.put(2, "护理订单");
            } else if (entry.getKey().equals(5)) {
                mapnew.put(5, entry.getValue());
            } else if (entry.getKey().equals(1)) {
                mapnew.put(1, entry.getValue());
            } else if (entry.getKey().equals(6)) {
                mapnew.put(6, entry.getValue());
            }
        }

        return ResponseResult.success(mapnew);
    }

    @Override
    public ResponseResult selectOrderStatusList() {
        Map<Integer, String> map = EnumUtil.getEnumToMap(OrderStatusEnum.class);
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry entry : map.entrySet()) {
            JSONObject object = new JSONObject();
            object.put("key", entry.getKey());
            object.put("value", entry.getValue());
            jsonArray.add(object);
        }
        return ResponseResult.success(jsonArray);
    }

    @Override
    public ResponseResult selectOrderChannelList() {
        Map map = EnumUtil.getEnumToMap(OrderChannelEnum.class);
        return ResponseResult.success(map);
    }

    @Override
    public ResponseResult selectOrderListByToday(String storeId, String date) {
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
        List<OrderVO> list = iOrderDao.selectOrderListByToday(mapSelect);
        return ResponseResult.success(list);
    }

    @Override
    public ResponseResult rechargeOrderRefund(String orderNumber, Double refundAmount, String name, String mobile, String memberNum, Long accountTypeId, String isAudit) {
        //判断退款金额是否大于 用户当前账户余额
        String walletId = walletApi.selectWalletByCarNum(memberNum).getResult().toString();


        List<Map> mapList = (List<Map>) (walletApi.selectAccountAmountByWalletId(Long.parseLong(walletId)).getResult());
        Double memberAccountAmount = 0.00;
        for (Map map : mapList) {
            if (map.get("walletId").toString().equals(walletId) && map.get("accountTypeId").toString().equals(accountTypeId.toString())) {
                memberAccountAmount = Double.parseDouble(map.get("amount").toString());
            }
        }
        if (StringUtils.isBlank(isAudit)) {
            //如果不是审核后的退款
            if (refundAmount > memberAccountAmount) {
                return ResponseResult.error(new Error(ResponseCodeOrderEnum.AMOUNT_CAN_NOT_BIGGER_MEMBER_AMOUNT.getCode(),
                        ResponseCodeOrderEnum.AMOUNT_CAN_NOT_BIGGER_MEMBER_AMOUNT.getDesc()));
            }
        }
        //判断退款金额，是否小于剩余金额
        //查看剩余金额
        Double refundSum = 0.00;
        Double rechargeSum = 0.00;
        List<Map> mapListRefund = (List<Map>) (walletApi.selectRechargeRefuseListNoPage(orderNumber).getResult());
        if (mapListRefund == null || mapListRefund.size() == 0) {
            refundSum = 0.00;
        } else {
            for (Map map : mapListRefund) {
                refundSum = refundSum + Double.parseDouble(map.get("amount").toString());
            }
        }
        Map rechargeOrderMap = (Map) (walletApi.selectRechargeByOrderNumber(orderNumber).getResult());
        rechargeSum = Double.parseDouble(rechargeOrderMap.get("amount").toString());
        if (StringUtils.isBlank(isAudit)) {
            //如果不是审核后的退款
            if (refundAmount > (rechargeSum - refundSum)) {
                return ResponseResult.error(new Error(ResponseCodeOrderEnum.AMOUNT_CAN_NOT_BIGGER_RESIDUE_RECHARGE.getCode(),
                        ResponseCodeOrderEnum.AMOUNT_CAN_NOT_BIGGER_RESIDUE_RECHARGE.getDesc() + "剩余退款金额为：" + (rechargeSum - refundSum)));
            }
        }

        //查询充值订单所对应业绩的有效期
        ResponseResult responseResult = dataApi.selectLadderDetailedByRechargeOrderNum(Long.parseLong(orderNumber));
        Boolean isAbatementLadderDetailed = true;//影响
        Integer resultIntYeJi = null;
        if (responseResult.getResult() != null) {
            //如果存在这样的储值订单业绩，进行业绩扣除
            Map map = (Map) responseResult.getResult();
            Long ladderDetailedAchievementID = Long.parseLong(map.get("ladderDetailedAchievementID").toString());
            //根据业绩id查询业绩信息
            Map performanceMap = (Map) dataApi.selectPerformancePostById(ladderDetailedAchievementID).getResult();

            if (performanceMap != null) {
                /* Integer achievementInterval = Integer.parseInt(performanceMap.get("achievementInterval").toString());*/
                String LadderDetailedCreateTimeStr = map.get("createTime").toString();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date LadderDetailedCreateTime = null;
                try {
                    LadderDetailedCreateTime = formatter.parse(LadderDetailedCreateTimeStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date date = new Date();//当前时间
                Long diff = date.getTime() - LadderDetailedCreateTime.getTime();
                long nd = 1000 * 24 * 60 * 60;
                long day = diff / nd;
               /* if (day > achievementInterval) {
                    //如果现在距离业绩创建的时间超过了有效期，那么退款不影响业绩
                    isAbatementLadderDetailed = false;//不影响
                } else {*/
                //删除对应业绩
                if (refundAmount == (rechargeSum - refundSum)) {
                    //如果是全额退款-直接删除对应业绩
                    resultIntYeJi = Integer.parseInt((dataApi.deleteLadderDetailedByCondition(orderNumber).getResult().toString()));
                } else {
                    //修改业绩金额
                    resultIntYeJi = Integer.parseInt(dataApi.updateLadderDetailed(orderNumber, rechargeSum - refundAmount).getResult().toString());
                    /* }*/
                }

            }
        }


        //查看充值账号对应的钱包金额进行扣除
        //扣除对应账户金额
        //通过会员号查看钱包id
        walletApi.updateWalletAccountDown(Long.parseLong(walletId), accountTypeId, refundAmount);
        //插入退款附表
        Integer resultIntKouAccount = Integer.parseInt((walletApi.insertRechargeRefuse(orderNumber, name, mobile, refundAmount).getResult().toString()));

        //如果是储值账户退款，要减少资金池金额
        List<Map> rechargeTypeList = (List<Map>) (walletApi.selectWalletAccountListNoPage().getResult());
        boolean flag = false;
        for (Map rechargeType : rechargeTypeList) {
            if (rechargeOrderMap.get("accountTypeId").toString().equals(rechargeType.get("accountTypeId").toString()) && rechargeType.get("isCuZhiYeJi").toString().equals("1")) {
                //是储值业绩
                flag = true;
            }
        }
        Integer resultIntKouYushou = null;
        if (flag) {
            //扣减，对应门店 对应账户类型 的预收金额
            resultIntKouYushou = Integer.parseInt((walletApi.reduceForwardSaleByCondition(1,
                    Long.parseLong(rechargeOrderMap.get("accountTypeId").toString()),
                    Long.parseLong(rechargeOrderMap.get("storeId").toString()),
                    refundAmount).getResult().toString()));
        }

        //修改充值记录为已退款
        if (refundAmount == (rechargeSum - refundSum)) {//全部退款
            walletApi.updateStoreRechargeAuditRecord(orderNumber, 3);
        } else if (refundAmount != (rechargeSum - refundSum) && refundAmount < (rechargeSum - refundSum)) {//部分退款
            walletApi.updateStoreRechargeAuditRecord(orderNumber, 4);
        }

        if (/*resultIntYeJi != null && resultIntYeJi > 0 &&*/ resultIntKouAccount != null && resultIntKouAccount > 0 && resultIntKouYushou != null & resultIntKouYushou > 0) {
            return ResponseResult.success("退款成功，已修改业绩信息、扣除账户余额、扣除资金池预收金额");
        } else {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.REFUSE_FAIL.getCode(),
                    ResponseCodeOrderEnum.REFUSE_FAIL.getDesc()));
        }
    }

    @Override
    public ResponseResult payOrderRefund(String outStorageIdQiTa,
                                         String outStorageIdXiaoShou,
                                         Integer isTiYanKaOrDingzhi,
                                         Integer isTiYanKa,
                                         Long expUseRecordId,
                                         Integer isHuaKa,
                                         String payTypeAndAmount,
                                         String memberNum,
                                         Long experiencecardProductUserId,
                                         String orderNumber,
                                         String expCardNum,
                                         String modifyOperator,
                                         String storeId) {

        if (isHuaKa == 0) {
            if (isTiYanKa == 0) {//如果不是体验卡
                //查看门店默认退货部门
                String deptK3Number;
                Map resultMapDept = (Map) storeApi.selectDefaultDept(Long.parseLong(storeId), 3).getResult();
                if (resultMapDept == null) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.DEFAULT_DEPT_IS_NOT_FIND.getCode(),
                            ResponseCodeStockEnum.DEFAULT_DEPT_IS_NOT_FIND.getDesc()));
                } else {
                    deptK3Number = resultMapDept.get("k3Number").toString();
                }
                //如果不是划卡类型的退货
                JSONArray payTypeAndAmountArray = JSON.parseArray(payTypeAndAmount);//前端选择的退减账户类型和金额
                //查询此会员所有的账户信息-返回扣减的账户金额
                if (StringUtils.isNotBlank(memberNum)) {
                    Long walletId = Long.parseLong(walletApi.selectWalletByCarNum(memberNum).getResult().toString());
                    List<Map> mapList = (List<Map>) walletApi.selectAccountAmountByWalletId(walletId).getResult();
                    for (int i = 0; i < payTypeAndAmountArray.size(); i++) {
                        for (Map map : mapList) {
                            if (payTypeAndAmountArray.getJSONObject(i).getInteger("payTypeCategory") == 2 &&
                                    map.get("accountTypeId").equals(payTypeAndAmountArray.getJSONObject(i).getInteger("accountType"))) {
                                //增加对应账户金额
                                walletApi.updateWalletAccount(walletId, Long.parseLong(map.get("accountTypeId").toString()), payTypeAndAmountArray.getJSONObject(i).getDouble("amount"));
                            }
                        }
                    }
                }

                //修改订单状态为已退款
                Map map = new HashMap();
                map.put("orderNumber", orderNumber);
                map.put("status", OrderStatusEnum.REFUND.getCode());
                if (isTiYanKaOrDingzhi == 1) {
                    //如果是项目定制 查看项目定制项目是否使用
                    int resultInt = iOrderDao.checkDingZhiProjectIsUse(orderNumber);
                    if (resultInt > 0) {
                        return ResponseResult.error(new Error(ResponseCodeOrderEnum.CUSTOM_USED_CAN_NOT_REFUSE.getCode(),
                                ResponseCodeOrderEnum.CUSTOM_USED_CAN_NOT_REFUSE.getDesc()));
                    }

                }


                iOrderDao.updateOrderStatus(map);
                //删除对应订单-对应子订单-对应预约辅订单
                Map mapdelete = new HashMap();
                List<String> orderNumList = new ArrayList<>();
                orderNumList.add(orderNumber);
                mapdelete.put("list", orderNumList);
                iOrderDao.deleteAppointmentOrder(mapdelete);//将预约的记录删除
            } else {//如果是体验卡
                //判断如果使用的体验卡 不能退货
                int resultInt = Integer.parseInt(storeApi.checkExpIsUse(expCardNum, memberNum).getResult().toString());
                if (resultInt == 0) {
                    //修改订单状态为已退款
                    storeApi.updateExpOrderStatus(orderNumber, OrderStatusEnum.REFUND.getCode());
                    //修改体验卡的库存数量
                    storeApi.updateExpCarStockNumUp(expCardNum);
                } else {
                    return ResponseResult.error(new Error(ResponseCodeOrderEnum.EXP_CARD_USED_CAN_NOT_REFUSE.getCode(),
                            ResponseCodeOrderEnum.EXP_CARD_USED_CAN_NOT_REFUSE.getDesc()));
                }
            }

        } else if (isHuaKa == 1) {//如果是体验卡-划卡
            //如果是划卡类型的退货--增加使用次数
            storeApi.addUserExpUseTimes(experiencecardProductUserId);
            //将使用记录的状态改为已退货
            storeApi.updateUseRecordStatus(2, expUseRecordId);
            orderNumber = "TYHK" + expUseRecordId;
            ;
            //删除对应订单-对应子订单-对应预约辅订单
            Map mapdelete = new HashMap();
            List<String> orderNumList = new ArrayList<>();
            orderNumList.add(orderNumber);
            mapdelete.put("list", orderNumList);
            iOrderDao.deleteOrderByRecordId(mapdelete);
            iOrderDao.deleteProductOrderByRecordId(mapdelete);
            iOrderDao.deleteAppointmentOrderByRecordId(mapdelete);
        } else if (isHuaKa == 2) {//如果是项目定制-划卡
            //如果是划卡类型的退货--增加使用次数
            iOrderDao.addUserCustomUseTimes(experiencecardProductUserId);
            //将使用记录的状态改为已退货
            Map map = new HashMap();
            map.put("recordStatus", 2);
            map.put("recordId", expUseRecordId);
            iOrderDao.updateUseRecordStatus(map);
            orderNumber = "DZHK" + expUseRecordId;
            //删除对应订单-对应子订单-对应预约辅订单
            Map mapdelete = new HashMap();
            List<String> orderNumList = new ArrayList<>();
            orderNumList.add(orderNumber);
            mapdelete.put("list", orderNumList);
            iOrderDao.deleteOrderByRecordId(mapdelete);
            iOrderDao.deleteProductOrderByRecordId(mapdelete);
            iOrderDao.deleteAppointmentOrderByRecordId(mapdelete);
        }


        //删除对应订单所产生的业绩
        dataApi.deleteLadderDetailedByOrder(orderNumber, modifyOperator);


        if ((isTiYanKaOrDingzhi != null && isTiYanKaOrDingzhi == 0) || isHuaKa == 1 || isHuaKa == 2) {
            //查看门店默认退货部门
            String deptK3Number;
            Map resultMapDept = (Map) storeApi.selectDefaultDept(Long.parseLong(storeId), 3).getResult();
            if (resultMapDept == null) {
                return ResponseResult.error(new Error(ResponseCodeStockEnum.DEFAULT_DEPT_IS_NOT_FIND.getCode(),
                        ResponseCodeStockEnum.DEFAULT_DEPT_IS_NOT_FIND.getDesc()));
            } else {
                deptK3Number = resultMapDept.get("k3Number").toString();
            }


            //如果不是体验卡 也不是定制项目 才与库存有关
            //添加入库
            JSONArray jsonArrayXiaoShou = new JSONArray();
            JSONArray jsonArrayQiTa = new JSONArray();
            //根据商品编号查看商品单位
            List<Map> mapProductAllList = (List<Map>) productApi.selectAllProduct().getResult();
            //查询所有的单位列表
            List<Map> mapUnitList = (List<Map>) productApi.selectUnitListNoPage().getResult();
            //根据销售出库的id查看销售出库商品
            JSONArray jsonArrayproductsXiaoShou = new JSONArray();
            JSONArray jsonArrayproductsQiTa = new JSONArray();
            Map outStorageQiTa = new HashMap();
            Map outStorageXiaoShou = new HashMap();
            if (StringUtils.isNotBlank(outStorageIdXiaoShou)) {
                List<Map> outStorageProductListXiaoShou = (List<Map>) (productApi.selectOutstorageProductById(Long.parseLong(outStorageIdXiaoShou)).getResult());
                //库存处理
                jsonArrayproductsXiaoShou = JSONArray.parseArray(JSON.toJSONString(outStorageProductListXiaoShou));
                //根据销售出库的id查看销售出库商品
                outStorageXiaoShou = (Map) (productApi.selectOutstorageById(Long.parseLong(outStorageIdXiaoShou)).getResult());
            }
            if (StringUtils.isNotBlank(outStorageIdQiTa)) {
                List<Map> outStorageProductListQiTa = (List<Map>) (productApi.selectOutstorageProductById(Long.parseLong(outStorageIdQiTa)).getResult());
                //库存处理
                jsonArrayproductsQiTa = JSONArray.parseArray(JSON.toJSONString(outStorageProductListQiTa));
                //根据销售出库的id查看销售出库商品
                outStorageQiTa = (Map) (productApi.selectOutstorageById(Long.parseLong(outStorageIdQiTa)).getResult());
            }


            for (Map map1 : mapProductAllList) {
                for (int i = 0; i < jsonArrayproductsXiaoShou.size(); i++) {
                    if (map1.get("productCode").toString().equals(jsonArrayproductsXiaoShou.getJSONObject(i).getString("productCode"))) {
                        Long unitId = Long.parseLong(map1.get("unitId").toString());
                        for (Map map2 : mapUnitList) {
                            if (map2.get("unitId").toString().equals(unitId.toString())) {
                                jsonArrayproductsXiaoShou.getJSONObject(i).put("unitId", unitId);
                            }
                        }
                        jsonArrayproductsXiaoShou.getJSONObject(i).put("productType", map1.get("productType").toString());
                        jsonArrayproductsXiaoShou.getJSONObject(i).put("productSubordinate", map1.get("productSubordinate").toString());
                    }
                }
                for (int i = 0; i < jsonArrayproductsQiTa.size(); i++) {
                    if (map1.get("productCode").toString().equals(jsonArrayproductsQiTa.getJSONObject(i).getString("productCode"))) {
                        Long unitId = Long.parseLong(map1.get("unitId").toString());
                        for (Map map2 : mapUnitList) {
                            if (map2.get("unitId").toString().equals(unitId.toString())) {
                                jsonArrayproductsQiTa.getJSONObject(i).put("unitId", unitId);
                            }
                        }
                        jsonArrayproductsQiTa.getJSONObject(i).put("productType", map1.get("productType").toString());
                        jsonArrayproductsQiTa.getJSONObject(i).put("productSubordinate", map1.get("productSubordinate").toString());
                    }
                }

            }


            //查看门店信息
            Map storeInfo = (Map) storeApi.selectStoreById(Long.parseLong(storeId)).getResult();
            //查看仓库信息
            List<Map> mapStockList = (List<Map>) (productApi.selectStockList(3l, Long.parseLong(storeInfo.get("storeId").toString())).getResult());
            for (int i = 0; i < jsonArrayproductsXiaoShou.size(); i++) {
                if (jsonArrayproductsXiaoShou.getJSONObject(i).getInteger("productSubordinate") == 0) {
                    //如果是实体产品
                    JSONObject jsonObjectXiaoShou = new JSONObject();
                    jsonObjectXiaoShou.put("productK3Number", jsonArrayproductsXiaoShou.getJSONObject(i).getString("k3Number"));
                    jsonObjectXiaoShou.put("fUnitID", "Pcs");
                    jsonObjectXiaoShou.put("fRealQty", jsonArrayproductsXiaoShou.getJSONObject(i).getString("sendNumber"));
                    jsonObjectXiaoShou.put("fOwnerId", storeInfo.get("k3Number").toString());
                    jsonObjectXiaoShou.put("fStockId", mapStockList.get(0).get("stockCode").toString());
                    jsonObjectXiaoShou.put("fSalUnitID", "Pcs");
                    jsonObjectXiaoShou.put("fSalUnitQty", jsonArrayproductsXiaoShou.getJSONObject(i).getDouble("sendNumber"));
                    jsonObjectXiaoShou.put("fSalBaseQty", jsonArrayproductsXiaoShou.getJSONObject(i).getDouble("sendNumber"));
                    jsonObjectXiaoShou.put("fPriceBaseQty", jsonArrayproductsXiaoShou.getJSONObject(i).getDouble("sendNumber"));
                    jsonObjectXiaoShou.put("fARNOTJOINQTY", jsonArrayproductsXiaoShou.getJSONObject(i).getDouble("sendNumber"));

                    jsonObjectXiaoShou.put("productCode", jsonArrayproductsXiaoShou.getJSONObject(i).getString("productCode"));
                    jsonObjectXiaoShou.put("productName", jsonArrayproductsXiaoShou.getJSONObject(i).getString("productName"));
                    jsonObjectXiaoShou.put("productType", jsonArrayproductsXiaoShou.getJSONObject(i).getInteger("productType"));
                    jsonObjectXiaoShou.put("sendNumber", jsonArrayproductsXiaoShou.getJSONObject(i).getDouble("sendNumber"));
                    jsonObjectXiaoShou.put("unit", jsonArrayproductsXiaoShou.getJSONObject(i).getString("unitId"));
                    jsonObjectXiaoShou.put("stock", jsonArrayproductsXiaoShou.getJSONObject(0).getString("stock"));
                    jsonObjectXiaoShou.put("stockStatus", 1);
                    jsonArrayXiaoShou.add(jsonObjectXiaoShou);
                } else if (jsonArrayproductsXiaoShou.getJSONObject(i).getInteger("productSubordinate") != null && jsonArrayproductsXiaoShou.getJSONObject(i).getInteger("productSubordinate") == 1) {
                    //如果是第三方 - 直接加第三方库存
                    productApi.updateStockQuantity(jsonArrayproductsXiaoShou.getJSONObject(i).getString("productCode"), "up");
                }
                //退货后 减少销量
                productApi.updateProductSales(jsonArrayproductsXiaoShou.getJSONObject(i).getString("productCode"), jsonArrayproductsXiaoShou.getJSONObject(i).getInteger("productType"), "down");
            }

            for (int i = 0; i < jsonArrayproductsQiTa.size(); i++) {
                if (jsonArrayproductsQiTa.getJSONObject(i).getInteger("productSubordinate") == 0) {
                    //如果是护理产品
                    JSONObject jsonObjectQiTa = new JSONObject();
                    jsonObjectQiTa.put("productCode", jsonArrayproductsQiTa.getJSONObject(i).getString("productCode"));
                    jsonObjectQiTa.put("productName", jsonArrayproductsQiTa.getJSONObject(i).getString("productName"));
                    jsonObjectQiTa.put("productType", jsonArrayproductsQiTa.getJSONObject(i).getInteger("productType"));
                    jsonObjectQiTa.put("sendNumber", jsonArrayproductsQiTa.getJSONObject(i).getDouble("sendNumber"));
                    jsonObjectQiTa.put("unit", jsonArrayproductsQiTa.getJSONObject(i).getString("unitId"));
                    jsonObjectQiTa.put("stock", jsonArrayproductsQiTa.getJSONObject(0).getString("stock"));
                    jsonObjectQiTa.put("stockStatus", 1);
                    jsonArrayQiTa.add(jsonObjectQiTa);
                } else if (jsonArrayproductsQiTa.getJSONObject(i).getInteger("productSubordinate") != null && jsonArrayproductsQiTa.getJSONObject(i).getInteger("productSubordinate") == 1) {
                    //如果是第三方 - 直接加第三方库存
                    productApi.updateStockQuantity(jsonArrayproductsQiTa.getJSONObject(i).getString("productCode"), "up");
                }
                //退货后 减少销量
                productApi.updateProductSales(jsonArrayproductsQiTa.getJSONObject(i).getString("productCode"), jsonArrayproductsQiTa.getJSONObject(i).getInteger("productType"), "down");
            }


            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(currentTime);

            if (jsonArrayQiTa.size() != 0) {
                String client = outStorageQiTa.get("client").toString();
                String outStorageType = "其他出库";
                String invoicesType = "标准其他出库";
                String inventoryGroup = outStorageQiTa.get("inventoryGroup").toString();
                String inventoryWay = "RETURN";
                String outStorageDate = dateString;
                String shipperType = "业务组织"/*outStorage.get("shipperType").toString()*/;
                String shipper = storeInfo.get("k3Number").toString()/*outStorage.get("shipper").toString()*/;
                String outStorageId = outStorageQiTa.get("outStorageId").toString();
                String outStorageProductJson = jsonArrayQiTa.toJSONString();
                //根据门店id查看门店的k3
                String orgK3Number = storeInfo.get("k3Number").toString();
                String createOperator = modifyOperator;
                String batchNumber = outStorageQiTa.get("batchNumber").toString();
                productApi.outstorageReturn(client,
                        outStorageType,
                        invoicesType,
                        inventoryGroup,
                        inventoryWay,
                        outStorageDate,
                        shipperType,
                        shipper,
                        outStorageId,
                        outStorageProductJson,
                        orgK3Number,
                        createOperator,
                        batchNumber,
                        mapStockList.get(0).get("stockId").toString(),
                        storeId);
            }
            ;

            if (jsonArrayXiaoShou.size() != 0) {
                String client = outStorageXiaoShou.get("client").toString();
                String outStorageType = "销售出库";
                String invoicesType = "标准销售出库";
                String inventoryGroup = outStorageXiaoShou.get("inventoryGroup").toString();
                String outStorageDate = dateString;
                String shipperType = "业务组织"/*outStorage.get("shipperType").toString()*/;
                String shipper = storeInfo.get("k3Number").toString()/*outStorage.get("shipper").toString()*/;
                String outStorageProductJson = jsonArrayXiaoShou.toJSONString();
                //根据门店id查看门店的k3
                String orgK3Number = storeInfo.get("k3Number").toString();
                String createOperator = modifyOperator;
                String batchNumber = outStorageXiaoShou.get("batchNumber").toString();
                productApi.saveMarketReturn(client,
                        storeId,
                        outStorageType,
                        invoicesType,
                        inventoryGroup,
                        "RETURN",
                        outStorageDate,
                        shipperType,
                        shipper,
                        orgK3Number,
                        createOperator,
                        mapStockList.get(0).get("stockId").toString(),
                        outStorageProductJson,
                        batchNumber);
            }


        }

        //删除对应资金池
        walletApi.deleteCapPoolByOrderNumber(orderNumber);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectOrderByCash(int pageNum, int pageSize, String storeId, String date, Integer cash) {
        Date orderDate = new Date();
        HashMap mapList = new HashMap();
        if (date != null && !StringUtils.isBlank(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                orderDate = sdf.parse(date);
            } catch (ParseException e) {
                return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_DATE_ERROR.getCode(),
                        ResponseCodeOrderEnum.ORDER_DATE_ERROR.getDesc()));
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        String[] storeIdArray = storeId.replaceAll("]", "").replace("[", "").split(",");
        if (!(storeIdArray[0].equals(""))) {
            mapList.put("list", storeIdArray);
        }
        mapList.put("orderDate", orderDate);
        mapList.put("orderStatus", OrderStatusEnum.UNPAID.getCode());
        List<Order> orderList = iOrderDao.selectOrderListByDateAndStore(mapList);
        List<Map> cashOrderMapList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<Integer, String> mapOrderType = EnumUtil.getEnumToMap(OrderTypeEnum.class);

        for (Order order : orderList) {
            if (order.getPayTypeAndAmount() != null) {
                JSONArray payAndAmountArry = JSON.parseArray(order.getPayTypeAndAmount());
                Map map = new HashMap();
                for (int i = 0; i < payAndAmountArry.size(); i++) {
                    if (payAndAmountArry.getJSONObject(i).getInteger("payType") == cash &&
                            payAndAmountArry.getJSONObject(i).getDouble("amount") != 0) {
                        map.put("amount", payAndAmountArry.getJSONObject(i).getInteger("amount"));
                        map.put("orderNum", order.getOrderNumber().toString());
                        for (Map.Entry<Integer, String> entry : mapOrderType.entrySet()) {
                            if (order.getOrderType().equals(entry.getKey())) {
                                map.put("orderTypeName", entry.getValue());
                            }
                        }
                        map.put("createTime", formatter.format(order.getCreateTime()));
                        cashOrderMapList.add(map);
                    }
                }

            }
        }

        PageInfo pageInfo = new PageInfo(cashOrderMapList);
        return ResponseResult.success(pageInfo);
    }

    @Override
    public ResponseResult selectOrderByMemberNumAndProductType(int pageNum, int pageSize, String storeId, String memberNum, Integer orderType, String date, String keywordOrderNum) {

        PageHelper.startPage(pageNum, pageSize);
        String[] storeIdArray = storeId.replaceAll("]", "").replace("[", "").split(",");
        HashMap mapList = new HashMap();
        mapList.put("orderDate", date);
        mapList.put("list", storeIdArray);
        mapList.put("orderStatus", OrderStatusEnum.UNPAID.getCode());
        mapList.put("memberNum", memberNum);
        mapList.put("orderType", orderType);
        mapList.put("keywordOrderNum", keywordOrderNum);
        List<Order> orderList = iOrderDao.selectOrderByMemberNumAndProductType(mapList);
        mapList.clear();
        List<ProductOrder> productOrderList = iOrderDao.selectProductOrderListByNum(mapList);
        Map<Integer, String> mapOrderType = EnumUtil.getEnumToMap(OrderTypeEnum.class);
        for (Order order : orderList) {
            List<ProductOrder> productOrderList1 = new ArrayList<>();
            for (Map.Entry<Integer, String> entry : mapOrderType.entrySet()) {
                if (order.getOrderType().equals(entry.getKey())) {
                    order.setOrderTypeName(entry.getValue());
                }
            }
            for (ProductOrder productOrder : productOrderList) {
                if (productOrder.getOrderNumber().equals(order.getOrderNumber())) {
                    productOrderList1.add(productOrder);
                }
            }
            order.setProductOrderList(productOrderList1);
        }

        PageInfo pageInfo = new PageInfo(orderList);
        return ResponseResult.success(pageInfo);
    }

    @Override
    public ResponseResult selectOrderProductByMemberNum(int pageNum, int pageSize, Long storeId, String memberNum, String date) {
        PageHelper.startPage(pageNum, pageSize);
        HashMap mapList = new HashMap();
        mapList.put("orderDate", date);
        mapList.put("storeId", storeId);
        mapList.put("orderStatus", OrderStatusEnum.UNPAID.getCode());
        mapList.put("memberNum", memberNum);
        mapList.put("productTypeId", ProductTypeEnum.PRODUCT.getCode());

        List<ProductOrder> productOrderList = iOrderDao.selectProductOrderListByMember(mapList);
        PageInfo pageInfo = new PageInfo(productOrderList);
        return ResponseResult.success(pageInfo);
    }

    @Override
    public ResponseResult selectConsumeTopTen(String memberNum, Long storeId) {
        Map map = new HashMap();
        map.put("storeId", storeId);
        map.put("cardNumber", memberNum);
        map.put("orderStatus", OrderStatusEnum.UNPAID.getCode());
        map.put("limit", "limit");
        List<OrderVO> orderList = iOrderDao.selectOrderList(map);
        List<Date> dateList = new ArrayList<>();
        List<Double> orderPriceList = new ArrayList<>();
        for (OrderVO orderVO : orderList) {
            dateList.add(orderVO.getCreateTime());
            orderPriceList.add(orderVO.getPayPrice().doubleValue());
        }


        Map mapResult = new HashMap();
        mapResult.put("date", dateList);
        mapResult.put("price", orderPriceList);
        return ResponseResult.success(mapResult);
    }

    @Override
    public ResponseResult selectConsumeTimesTopTen(String memberNum, Long storeId) {
        Map map = new HashMap();
        map.put("storeId", storeId);
        map.put("cardNumber", memberNum);
        map.put("productTypeId", ProductTypeEnum.SERVICE.getCode());
        map.put("orderStatus", OrderStatusEnum.UNPAID.getCode());
        List<Map> orderMapList = iOrderDao.selectConsumeTimesTopTen(map);
        List<String> productNameList = new ArrayList<>();
        List<Integer> orderTimesList = new ArrayList<>();
        for (Map orderMap : orderMapList) {
            productNameList.add(orderMap.get("productName").toString());
            orderTimesList.add(Integer.parseInt(orderMap.get("times").toString()));
        }

        Map mapResult = new HashMap();
        mapResult.put("productNameList", productNameList);
        mapResult.put("orderTimesList", orderTimesList);
        return ResponseResult.success(mapResult);
    }

    @Override
    public ResponseResult selectConsumeMoneyTopTen(String memberNum, Long storeId) {
        Map map = new HashMap();
        map.put("storeId", storeId);
        map.put("cardNumber", memberNum);
        map.put("productTypeId", ProductTypeEnum.SERVICE.getCode());
        map.put("orderStatus", OrderStatusEnum.UNPAID.getCode());
        List<OrderVO> orderMapList = iOrderDao.selectConsumeMoneyTopTen(map);
        List<Date> productNameList = new ArrayList<>();
        List<Double> orderMoneyList = new ArrayList<>();
        for (OrderVO orderVo : orderMapList) {
            productNameList.add(orderVo.getCreateTime());
            orderMoneyList.add(orderVo.getPayPrice().doubleValue());
        }
        Map mapResult = new HashMap();
        mapResult.put("productNameList", productNameList);
        mapResult.put("orderMoneyList", orderMoneyList);
        return ResponseResult.success(mapResult);
    }

    @Override
    public ResponseResult selectAppointmentOrderListByDate(Long storeId) {
        Map map = new HashMap();
        map.put("storeId", storeId);
        //查看所有的预约
        List<AppointmentOrder> appointmentOrderList = iOrderDao.selectAppointmentOrderListByDate(map);
        //查看所有的订单
        HashMap map1 = new HashMap();
        List<Long> a = new ArrayList<>();
        a.add(storeId);
        map1.put("list", a);
        List<Order> orderList = iOrderDao.selectOrderListByDateAndStore(map1);
        //查询所有的子订单
        List<String> orderNumList = new ArrayList<>();
        for (Order order : orderList) {
            orderNumList.add(order.getOrderNumber());
        }
        if (orderNumList.size() == 0) {
            map.put("list", null);
        } else {
            map.put("list", orderNumList);
        }
        List<ProductOrder> productOrderList = iOrderDao.selectProductOrderListByNum(map);

        for (Order order : orderList) {
            List<ProductOrder> productOrders = new ArrayList<>();
            double sum = 0.00;
            for (ProductOrder productOrder : productOrderList) {
                if (order.getOrderNumber().equals(productOrder.getOrderNumber())) {
                    productOrders.add(productOrder);
                    if (productOrder.getDiscountPrice() != null) {
                        sum = sum + productOrder.getDiscountPrice().doubleValue();
                    }
                }
            }
            order.setProductOrderList(productOrders);
            order.setTotalDiscount(new BigDecimal(sum));
        }

        if (appointmentOrderList.size() != 0) {
            //如果有预约
            List<Order> orderResultList = new ArrayList<>();
            for (AppointmentOrder appointmentOrder : appointmentOrderList) {
                for (Order order : orderList) {
                    if (appointmentOrder.getOrderNumber().equals(order.getOrderNumber())) {
                        order.setAppointmentOrder(appointmentOrder);
                        orderResultList.add(order);
                    }
                }
            }
            return ResponseResult.success(orderResultList);
        }
        return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_APPOINTMENT_NOT_EXIST.getCode(),
                ResponseCodeOrderEnum.ORDER_APPOINTMENT_NOT_EXIST.getDesc()));
    }

    @Override
    public ResponseResult payOrder(String staffNumber, String orderNumber, String payTypeAndAmount, String productIds, Double payPrice, Double number, String createOperator, String useLimit, Integer totalTimes) {
        Map map = new HashMap();
        List<String> list = new ArrayList<>();
        list.add(orderNumber);
        map.put("list", list);
        List<Order> orderList = iOrderDao.selectOrderByNum(map);
        if (orderList.size() == 0) {
            ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_NOT_EXIST.getCode(),
                    ResponseCodeOrderEnum.ORDER_NOT_EXIST.getDesc()));
        }


        //查询默认客户
        String custK3Number = null;
        Map resultMapCust = (Map) storeApi.selectDefaultCust(orderList.get(0).getNurseStore(), 3).getResult();
        if (resultMapCust == null) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.DEFAULT_CUST_IS_NOT_FIND.getCode(),
                    ResponseCodeStockEnum.DEFAULT_CUST_IS_NOT_FIND.getDesc()));
        } else {
            custK3Number = resultMapCust.get("k3Number").toString();
        }


        //根据门店id查看门店
        Map mapStore = (Map) storeApi.selectStoreById(orderList.get(0).getNurseStore()).getResult();
        JSONArray payTypeAndAmountArray = JSONArray.parseArray(payTypeAndAmount);

        //查看是否有账户余额
        if (StringUtils.isNotBlank(orderList.get(0).getCardNumber())) {
            //查询用户的所有账户信息
            List<Map> mapList = (List<Map>) memberUserApi.selectStoreMemberAccountNoPage(orderList.get(0).getCardNumber(), null).getResult();
            //支付成功后，如果有账户支付，扣除账户余额
            for (int i = 0; i < payTypeAndAmountArray.size(); i++) {//[{"payType":3,"amount":"200","payTypeName":"线下"}]
                if (payTypeAndAmountArray.getJSONObject(i).getString("payTypeCategory").equals("2")) {
                    for (Map map1 : mapList) {
                        if (payTypeAndAmountArray.getJSONObject(i).getString("accountType") != null &&
                                payTypeAndAmountArray.getJSONObject(i).getString("accountType").equals(map1.get("accountTypeId").toString())) {
                            if (map1.get("amount") == null || Double.parseDouble(map1.get("amount").toString()) < payTypeAndAmountArray.getJSONObject(i).getDouble("amount")) {
                                //支付金额大于账户金额，无法支付
                                return ResponseResult.error(new Error(ResponseCodeOrderEnum.ACCOUNT_BALANCE_NOT_ENOUGH.getCode(),
                                        ResponseCodeOrderEnum.ACCOUNT_BALANCE_NOT_ENOUGH.getDesc()));
                            }
                        }
                    }
                }
            }
        }
        //验证传入的支付总价与订单子项金额合计是否相符
        //根据订单号查看订单子项
        List<ProductOrder> productOrderList = iOrderDao.selectProductOrderListByNum(map);


        Double sumSubOrder = 0.00;
        for (int i = 0; i < productOrderList.size(); i++) {
            sumSubOrder = sumSubOrder + productOrderList.get(i).getDiscountPrice().doubleValue() * productOrderList.get(i).getProductNum();
        }
        if (!sumSubOrder.toString().equals(payPrice.toString())) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.TOTAL_PRICE_ALL_ERROR_SUBORDER.getCode(), ResponseCodeOrderEnum.TOTAL_PRICE_ALL_ERROR_SUBORDER.getDesc()));
        }
        //验证传入的支付总价与各支付方式支付金额合计是否相符
        Double sumPay = 0.00;
        for (int i = 0; i < payTypeAndAmountArray.size(); i++) {
            sumPay = sumPay + payTypeAndAmountArray.getJSONObject(i).getDouble("amount");
        }
        if (!sumPay.toString().equals(payPrice.toString())) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.TOTAL_PRICE_ALL_ERROR_PAY.getCode(), ResponseCodeOrderEnum.TOTAL_PRICE_ALL_ERROR_PAY.getDesc()));
        }


        //修改订单为支付成功
        Order order = new Order();
        order.setOrderStatus(OrderStatusEnum.PAID.getCode());
        order.setOrderNumber(orderNumber);
        order.setPayPrice(new BigDecimal(payPrice));
        order.setPayTypeAndAmount(payTypeAndAmount);
        Double totalprice = orderList.get(0).getTotalPrice().doubleValue();
        order.setTotalDiscount(new BigDecimal(totalprice - payPrice));
        iOrderDao.updateOrder(order);

        //查看是否为预约订单
        Map mapapp = new HashMap();
        mapapp.put("orderNumber", orderNumber);
        AppointmentOrder appointmentOrder = iOrderDao.selectAppointmentByNum(mapapp);
        if (appointmentOrder != null) {
            AppointmentOrder appointmentOrderUpdate = new AppointmentOrder();
            appointmentOrderUpdate.setAppointmentId(appointmentOrder.getAppointmentId());
            appointmentOrderUpdate.setAppointmentStatus(AppointmentStatusEnum.PAY.getCode());
            iOrderDao.updateAppointmentOrder(appointmentOrderUpdate);
        }


        //添加订单后修改下单会员的到店时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String lastArrayTimeStr = simpleDateFormat.format(date);

        //下单成功，计算员工业绩-并支付成功
        ResponseResult responseResultYeji = null;
        Map<String, String> result = new HashMap();
        if (orderList.get(0).getOrderType() != OrderTypeEnum.CUSTOM_ORDER.getCode()) {
            for (ProductOrder productOrderItem : productOrderList) {
                if (StringUtils.isNotBlank(productOrderItem.getBookingBeauticianIds())) {
                    responseResultYeji = dataApi.insertLadderDetailed(Integer.parseInt(productOrderItem.getProductTypeId().toString()), orderList.get(0).getNurseStore(), productOrderItem.getBookingBeauticianIds(), orderList.get(0).getOrderNumber(), payTypeAndAmount, productOrderItem.getProductNum(), productOrderItem.getProductCode(), null, mapStore.get("industryID").toString());
                    if (responseResultYeji.getResponseStatusType().getMessage().equals("Success"))
                        result.putAll((Map) responseResultYeji.getResult());
                }
            }
        }
        if (StringUtils.isNotBlank(orderList.get(0).getCardNumber())) {
            //下单成功后, 修改会员到店时间
            /*memberUserApi.updateStoreMember(orderList.get(0).getCardNumber(), lastArrayTimeStr);*/
            //下单成功，计算客户测评-并支付成功
            ResponseResult responseResultPingce = null;
            for (ProductOrder productOrderItem : productOrderList) {
                responseResultPingce = dataApi.insertEvaluatingDetailed(orderNumber, orderList.get(0).getCardNumber(), productOrderItem.getSubclassID(), payPrice.doubleValue(), 2, productOrderItem.getProductTypeId());   //(2支付订单)1为商品订单，2为护理订单，5为充值订单，6体验卡订单
            }
        }

        result.put("订单：", "支付成功");


        //支付成功后，如果有账户支付，扣除账户余额
        if (StringUtils.isNotBlank(orderList.get(0).getCardNumber())) {
            //查询用户的所有账户信息
            List<Map> mapList = (List<Map>) memberUserApi.selectStoreMemberAccountNoPage(orderList.get(0).getCardNumber(), null).getResult();
            //根据会员卡号查看钱包id
            Long walletId = Long.parseLong(walletApi.selectWalletByCarNum(orderList.get(0).getCardNumber()).getResult().toString());
            //支付成功后，如果有账户支付，扣除账户余额
            for (int i = 0; i < payTypeAndAmountArray.size(); i++) {//[{"payType":3,"amount":"200","payTypeName":"线下"}]
                if (payTypeAndAmountArray.getJSONObject(i).getString("payTypeCategory").equals("2")) {
                    for (Map map1 : mapList) {
                        if (payTypeAndAmountArray.getJSONObject(i).getString("accountType") != null &&
                                payTypeAndAmountArray.getJSONObject(i).getString("accountType").equals(map1.get("accountTypeId").toString())) {
                            if (map1.get("amount") == null || Double.parseDouble(map1.get("amount").toString()) < payTypeAndAmountArray.getJSONObject(i).getDouble("amount")) {
                                //支付金额大于账户金额，无法支付
                                return ResponseResult.error(new Error(ResponseCodeOrderEnum.ACCOUNT_BALANCE_NOT_ENOUGH.getCode(),
                                        ResponseCodeOrderEnum.ACCOUNT_BALANCE_NOT_ENOUGH.getDesc()));
                            } else {
                                //扣除对应账户余额
                                walletApi.updateWalletAccount(walletId, payTypeAndAmountArray.getJSONObject(i).getLong("accountType"), -payTypeAndAmountArray.getJSONObject(i).getDouble("amount"));
                            }
                        }
                    }
                }
            }
        }


        //支付成功后添加资金池实收
        for (int i = 0; i < payTypeAndAmountArray.size(); i++) {
            Integer payType = payTypeAndAmountArray.getJSONObject(i).getInteger("payType");
            Double amount = payTypeAndAmountArray.getJSONObject(i).getDouble("amount");
            Integer resultInteger = (Integer) (walletApi.addCapPool(payType, 2, amount, orderList.get(0).getNurseStore(), null, orderNumber).getResult());
            //划拨
            //根据支付方式查看账户类型
            Map resutleMap = (Map) (payApi.selectPayTypeById(Long.parseLong(payType.toString())).getResult());
            walletApi.addTransfer(Long.parseLong(resultInteger.toString()), amount, Integer.parseInt(resutleMap.get("accountType").toString()), orderList.get(0).getNurseStore());
        }


        //减库存即形成k3销售出库
        if (orderList.get(0).getOrderType() != OrderTypeEnum.CUSTOM_ORDER.getCode()) {
            //减库存
            List<Map> stockList = (List<Map>) productApi.selectStockList(3l, orderList.get(0).getNurseStore()).getResult();
            JSONArray jsonArrayXiaoShou = new JSONArray();
            JSONArray jsonArrayQiTa = new JSONArray();
            //查询订单子订单
            Map map1 = new HashMap();
            List<String> stringList = new ArrayList<>();
            stringList.add(orderNumber);
            map1.put("list", stringList);
            List<ProductOrder> productOrderList1 = iOrderDao.selectProductOrderListByNum(map1);
            //根据商品编号查看商品单位
            List<Map> mapProductAllList = (List<Map>) productApi.selectAllProduct().getResult();
            for (ProductOrder productOrder : productOrderList1) {
                String unitId = null;
                Integer productSubordinate = null;
                for (Map mapItem : mapProductAllList) {
                    if (mapItem.get("productCode").toString().equals(productOrder.getProductCode())) {
                        unitId = mapItem.get("unitId").toString();
                        productSubordinate = Integer.parseInt(mapItem.get("productSubordinate").toString());
                    }
                }

                if (productSubordinate == null) {
                    return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCT_NOT_FOUND.getCode(),
                            ResponseCodeProductEnum.PRODUCT_NOT_FOUND.getDesc()));
                }
                //根据商品编号找到商品是自营还是第三方
                if (productSubordinate == 0) {
                    //如果自营 - 添加出库单并减即时库存
                    if (productOrder.getProductTypeId() == Long.parseLong(ProductTypeEnum.PRODUCT.getCode().toString())) {
                        //如果是实体产品 加入实体产品数组
                        JSONObject jsonObjectXiaoShou = new JSONObject();
                        jsonObjectXiaoShou.put("productCode", productOrder.getProductCode());
                        //根据商品编号查看商品的k3Number
                        Map productMap = (Map) productApi.selectProductByCode(productOrder.getProductCode()).getResult();
                        if (productMap == null) {
                            productMap = (Map) productApi.selectServiceProductByCode(productOrder.getProductCode()).getResult();
                        }
                        jsonObjectXiaoShou.put("productK3Number", productMap.get("k3Number"));
                        jsonObjectXiaoShou.put("productName", productOrder.getProductName());
                        jsonObjectXiaoShou.put("sendNumber", productOrder.getProductNum());
                        jsonObjectXiaoShou.put("unit", unitId);
                        //根据单位id查看单位k3的number
                        List<Map> mapList = (List<Map>) productApi.selectUnitListNoPage().getResult();
                        String unitK3Number = null;
                        for (Map map2 : mapList) {
                            if (map2.get("unitId").toString().equals(unitId)) {
                                unitK3Number = map2.get("k3Number").toString();
                            }
                        }
                        jsonObjectXiaoShou.put("unitK3Number", unitK3Number);
                        jsonObjectXiaoShou.put("stock", stockList.get(0).get("stockCode"));
                        jsonObjectXiaoShou.put("stockStatus", 1);
                        jsonArrayXiaoShou.add(jsonObjectXiaoShou);
                    } else if (productOrder.getProductTypeId() == Long.parseLong(ProductTypeEnum.SERVICE.getCode().toString())) {
                        //如果是护理产品 加入护理产品数组
                        JSONObject jsonObjectQiTa = new JSONObject();
                        jsonObjectQiTa.put("productCode", productOrder.getProductCode());
                        //根据商品编号查看商品的k3Number
                        Map productMap = (Map) productApi.selectProductByCode(productOrder.getProductCode()).getResult();
                        if (productMap == null) {
                            productMap = (Map) productApi.selectServiceProductByCode(productOrder.getProductCode()).getResult();
                        }
                        jsonObjectQiTa.put("productK3Number", productMap.get("k3Number"));
                        jsonObjectQiTa.put("productName", productOrder.getProductName());
                        jsonObjectQiTa.put("sendNumber", productOrder.getProductNum());
                        jsonObjectQiTa.put("unit", unitId);
                        //根据单位id查看单位k3的number
                        List<Map> mapList = (List<Map>) productApi.selectUnitListNoPage().getResult();
                        String unitK3Number = null;
                        for (Map map2 : mapList) {
                            if (map2.get("unitId").toString().equals(unitId)) {
                                unitK3Number = map2.get("k3Number").toString();
                            }
                        }
                        jsonObjectQiTa.put("unitK3Number", unitK3Number);
                        jsonObjectQiTa.put("stock", stockList.get(0).get("stockCode"));
                        jsonObjectQiTa.put("stockStatus", 1);
                        jsonArrayQiTa.add(jsonObjectQiTa);
                    }
                } else if (productSubordinate == 1) {
                    //如果是第三方 - 直接减少第三方库存
                    productApi.updateStockQuantity(productOrder.getProductCode(), "down");
                }
                //支付成功后 增加产品销量
                productApi.updateProductSales(productOrder.getProductCode(), Integer.parseInt(productOrder.getProductTypeId().toString()), "up");
            }
            //查看门店名称
            Map storeInfo = (Map) storeApi.selectStoreById(orderList.get(0).getNurseStore()).getResult();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = formatter.format(new Date());
            //根据门店 查看门店仓库
            List<Map> mapStockList = (List<Map>) (productApi.selectStockList(3l, Long.parseLong(storeInfo.get("storeId").toString())).getResult());
            String batchNumber = NumberUtils.getRandomOrderNo();
            if (jsonArrayXiaoShou.size() != 0) {
                if (StringUtils.isBlank(staffNumber)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getCode(),
                            ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getDesc()));
                }
                ResponseResult resultStock1 = productApi.outstorage(staffNumber, batchNumber, mapStockList.get(0).get("stockId").toString(), jsonArrayXiaoShou.toJSONString(), "销售出库", "标准销售出库", dateStr, "人民币", storeInfo.get("name").toString(), storeInfo.get("k3Number").toString(), custK3Number, storeInfo.get("name").toString(), createOperator);
                if (!resultStock1.getResponseStatusType().getMessage().equals("Success") &&
                        resultStock1.getResponseStatusType().getError().getErrorMsg().equals("仓库商品没库存")) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                            ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc()));
                } else if (!resultStock1.isSuccess()) {
                    return resultStock1;
                }
                //出库后添加销售出库单号
                String outstorageId = resultStock1.getResult().toString();
                Order orderUpdate = new Order();
                orderUpdate.setOrderNumber(orderNumber);
                orderUpdate.setOutStorageIdXiaoShou(outstorageId);
                iOrderDao.updateOrder(orderUpdate);
            }
            if (jsonArrayQiTa.size() != 0) {
                ResponseResult resultStock2 = productApi.outstorage(
                        null,
                        jsonArrayQiTa.toJSONString(),
                        "其他出库",
                        "标准其他出库",
                        dateStr,
                        mapStockList.get(0).get("stockCode").toString(),
                        resultMapCust.get("name").toString(),
                        "GENERAL",
                        OutStorageBusinessTypeEnum.STANDARD_OTHER.getDesc(),
                        "业务组织",
                        storeInfo.get("name").toString(),
                        storeInfo.get("k3Number").toString(),
                        resultMapCust.get("k3Number").toString(),
                        mapStockList.get(0).get("stockId").toString()
                );
                if (!resultStock2.getResponseStatusType().getMessage().equals("Success") &&
                        resultStock2.getResponseStatusType().getError().getErrorMsg().equals("仓库商品没库存")) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                            ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc()));
                } else if (!resultStock2.isSuccess()) {
                    return resultStock2;
                }
                String outstorageId = resultStock2.getResult().toString();
                Order orderUpdate = new Order();
                orderUpdate.setOrderNumber(orderNumber);
                orderUpdate.setOutStorageIdQiTa(outstorageId);
                iOrderDao.updateOrder(orderUpdate);
            }
        }

        if (orderList.get(0).getOrderType() == OrderTypeEnum.CUSTOM_ORDER.getCode()) {
            //如果是定制订单  添加用户与定制订单对应表
            List<CustomProjectUser> customProjectUserList = new ArrayList();
            for (ProductOrder productOrder : productOrderList) {
                CustomProjectUser customProjectUser = new CustomProjectUser();
                customProjectUser.setId(null);
                customProjectUser.setOrderNum(orderNumber);
                customProjectUser.setSubOrderId(productOrder.getOrderId());
                customProjectUser.setProductCode(productOrder.getProductCode());
                customProjectUser.setProductName(productOrder.getProductName());
                customProjectUser.setUseLimit(productOrder.getUseLimit());
                customProjectUser.setTotalTimes(productOrder.getProductNum());
                customProjectUser.setUseTimes(0);
                customProjectUser.setMemberNum(orderList.get(0).getCardNumber());
                customProjectUserList.add(customProjectUser);
            }
            Map map1 = new HashMap();
            map1.put("list", customProjectUserList);
            iOrderDao.addCustomProjectUser(map1);
        }

        List<String> listResult = new ArrayList<>();
        for (Map.Entry<String, String> entry : result.entrySet()) {
            listResult.add(entry.getKey() + entry.getValue());
        }

        return ResponseResult.success(listResult);
    }

    @Override
    public ResponseResult cancelAppointMent(Long appointmentId, String modifyOperator) {
        AppointmentOrder appointmentOrder = new AppointmentOrder();
        appointmentOrder.setAppointmentId(appointmentId);
        appointmentOrder.setCreateOperator(modifyOperator);
        appointmentOrder.setAppointmentStatus(AppointmentStatusEnum.CANCLE.getCode());
        iOrderDao.updateAppointmentOrder(appointmentOrder);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectOrderByMemoScope(Long storeId, Integer memoNumStart, Integer memoNumEnd) {
        Map map = new HashMap();
        map.put("memoNumStart", memoNumStart);
        map.put("memoNumEnd", memoNumEnd);
        map.put("storeId", storeId);
        List<Order> orderList = iOrderDao.selectOrderByMemoScope(map);

        return ResponseResult.success(orderList);
    }

    @Override
    public ResponseResult checkOrderMemo(String memoNum) {
        Map map = new HashMap();
        map.put("memoNum", memoNum);
        int resultInt = iOrderDao.checkOrderMemo(map);
        return ResponseResult.success(resultInt);
    }

    @Override
    public ResponseResult selectAllOrder(String storeId, String date, Integer payType) {
        Date orderDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (date != null && !StringUtils.isBlank(date)) {
            try {
                orderDate = sdf.parse(date);
            } catch (ParseException e) {
                return ResponseResult.error(new Error(ResponseCodeOrderEnum.ORDER_DATE_ERROR.getCode(),
                        ResponseCodeOrderEnum.ORDER_DATE_ERROR.getDesc()));
            }
        }
        //查看账户类型
        List<Map> payList = (List<Map>) payApi.selectPayTypeList().getResult();
        Map map = new HashMap();
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add(OrderStatusEnum.UNPAID.getCode().toString());
        orderStatusList.add(OrderStatusEnum.REFUND.getCode().toString());
        String[] storeIdArray;
        if (StringUtils.isNotBlank(storeId)) {
            storeIdArray = storeId.replaceAll("]", "").replace("[", "").split(",");
        } else {
            storeIdArray = null;
        }
        map.put("orderStatusList", orderStatusList);
        map.put("list", storeIdArray);
        map.put("date", sdf.format(orderDate));
        //查看普通订单
        List<OrderVO> orderVOList = iOrderDao.selectOrderList(map);

        //查看所有的充值订单
        List<Map> mapList = (List<Map>) walletApi.selectRechargeByOrderNum(sdf.format(orderDate), null, storeId).getResult();

        for (Map map1 : mapList) {
            OrderVO orderVO = new OrderVO();
            orderVO.setPayTypeAndAmount(map1.get("payTypeAndAmount").toString());
            orderVO.setOrderType(OrderTypeEnum.RECHARGE_ORDER.getCode());
            orderVO.setOrderStatus(OrderStatusEnum.PAID.getCode());
            orderVO.setOrderNumber(map1.get("orderNo").toString());
            orderVO.setPayPrice(new BigDecimal(Double.parseDouble(map1.get("amount").toString())));
            orderVO.setAuditAmountStatus(Integer.parseInt(map1.get("auditAmountStatus").toString()));
            orderVOList.add(orderVO);
        }


        //查看所有的账户类型
        List<Map> mapListAccount = (List<Map>) walletApi.selectWalletAccountListNoPage().getResult();
        double sumRecharg = 0.00;
        for (Map map1 : mapList) {
            for (Map map2 : mapListAccount) {
                if (map1.get("accountTypeId").toString().equals(map2.get("accountTypeId").toString()) && map2.get("isCuZhiYeJi").toString().equals("1")) {
                    sumRecharg = sumRecharg + Double.parseDouble(map1.get("amount").toString());
                }
            }
        }


        Map<String, Double> map1 = new HashMap();
        for (Map payTypeItem : payList) {//循环支付类型
            Double sum = 0.00;
            for (OrderVO orderVO : orderVOList) {
                JSONArray payArray = JSON.parseArray(orderVO.getPayTypeAndAmount());//获取支付方式
                if (payArray != null) {
                    for (int i = 0; i < payArray.size(); i++) {//循环每种支付方式
                        if (payTypeItem.get("payTypeCategory").toString().equals("1")) {
                            String payTypeName = "";
                            if (payArray.getJSONObject(i).getString("payType").equals(payTypeItem.get("payTypeId").toString())) {
                                payTypeName = payTypeItem.get("payTypeName").toString();
                                sum = sum + payArray.getJSONObject(i).getDouble("amount");
                            }
                            if (payTypeName != "") {
                                map1.put(payTypeName, sum);
                            }

                        }
                    }
                }
            }
        }
        Map<Integer, String> mapOrderType = EnumUtil.getEnumToMap(OrderTypeEnum.class);
        Map<Integer, String> mapOrderStatus = EnumUtil.getEnumToMap(OrderStatusEnum.class);


        for (OrderVO orderVO : orderVOList) {
            for (Map.Entry entry : mapOrderType.entrySet()) {
                if (orderVO.getOrderType() == entry.getKey()) {
                    orderVO.setOrderTypeValue(entry.getValue().toString());
                }
            }
            for (Map.Entry entry : mapOrderStatus.entrySet()) {
                if (orderVO.getOrderStatus() == entry.getKey()) {
                    orderVO.setOrderStatueValue(entry.getValue().toString());
                }
            }

            JSONArray payJSONarray = JSON.parseArray(orderVO.getPayTypeAndAmount());
            String payTypeAndAmount = "";
            if (payJSONarray != null) {
                for (int i = 0; i < payJSONarray.size(); i++) {
                    for (Map payTypeItem : payList) {
                        if (payJSONarray.getJSONObject(i).getString("payType").equals(payTypeItem.get("payTypeId").toString())
                                && payTypeItem.get("payTypeCategory").toString().equals("1")) {
                            payTypeAndAmount = payTypeAndAmount + payTypeItem.get("payTypeName") + "：" + payJSONarray.getJSONObject(i).getInteger("amount") + ";";
                        }
                    }
                }
            }
            if (StringUtils.isNotBlank(payTypeAndAmount)) {
                orderVO.setPayTypeAndAmountStr(payTypeAndAmount.substring(0, payTypeAndAmount.length() - 1));
            }

        }


        String totalStr = "";
        for (Map.Entry entry : map1.entrySet()) {
            totalStr = totalStr + entry.getKey() + ":" + entry.getValue() + ",";
        }
        if (!totalStr.equals("")) {
            totalStr = totalStr.substring(0, totalStr.length() - 1);
        }


        List<OrderVO> orderVOListResult = new ArrayList<>();
        if (payType != null) {
            for (OrderVO orderVO : orderVOList) {
                JSONArray payJSONarray = JSON.parseArray(orderVO.getPayTypeAndAmount());
                for (int i = 0; i < payJSONarray.size(); i++) {
                    if (payJSONarray.getJSONObject(i).getString("payType").equals(payType.toString())) {
                        orderVOListResult.add(orderVO);
                        break;
                    }
                }
            }
        } else {
            for (OrderVO orderVO : orderVOList) {
                JSONArray payJSONarray = JSON.parseArray(orderVO.getPayTypeAndAmount());
                outterLoop:
                if (payJSONarray != null) {
                    for (int i = 0; i < payJSONarray.size(); i++) {
                        for (Map map2 : payList) {
                            if (payJSONarray.getJSONObject(i).getString("payType").equals(map2.get("payTypeId").toString()) &&
                                    map2.get("payTypeCategory").toString().equals("1")) {
                                orderVOListResult.add(orderVO);
                                break outterLoop;
                            }
                        }
                    }
                }
            }
        }
        Map mapResult = new HashMap();
        mapResult.put("orderList", orderVOListResult);
        mapResult.put("totalStr", totalStr);
        return ResponseResult.success(mapResult);
    }

    @Override
    public ResponseResult selectCustomProjectByMember(String memberNum) {
        Map map = new HashMap();
        map.put("memberNum", memberNum);
        List<CustomProjectUser> customProjectUserList = iOrderDao.selectCustomProjectByMember(map);
        //查询所有商品
        List<Map> mapListProduct = (List<Map>) productApi.selectAllProduct().getResult();
        for (CustomProjectUser customProjectUser : customProjectUserList) {
            for (Map mapItem : mapListProduct) {
                if (mapItem.get("productCode").toString().equals(customProjectUser.getProductCode())) {
                    customProjectUser.setProductType(Integer.parseInt(mapItem.get("productType").toString()));
                }
            }
        }

        Map<Integer, String> mapEnum = EnumUtil.getEnumToMap(ProductTypeEnum.class);

        for (CustomProjectUser customProjectUser : customProjectUserList) {
            for (Map.Entry<Integer, String> integerStringEntry : mapEnum.entrySet()) {
                if (customProjectUser.getProductType() == integerStringEntry.getKey()) {
                    customProjectUser.setProductTypeName(integerStringEntry.getValue());
                }
            }
        }

        List<Map> mapProduct = (List<Map>) productApi.selectAllProduct().getResult();
        List<Map> mapSubClass = (List<Map>) dataApi.selectSubclassListAll().getResult();
        for (CustomProjectUser customProjectUser : customProjectUserList) {
            for (Map mapItem : mapProduct) {
                if (customProjectUser.getProductCode().equals(mapItem.get("productCode"))) {
                    customProjectUser.setSubClassId(Long.parseLong(mapItem.get("subClassID").toString()));
                }
            }

            for (Map subClass : mapSubClass) {
                if (customProjectUser.getSubClassId() != null && customProjectUser.getSubClassId().toString().equals(subClass.get("subclassID").toString())) {
                    customProjectUser.setSubClassName(subClass.get("subclassName").toString());
                }
            }
        }
        return ResponseResult.success(customProjectUserList);
    }

    @Override
    public ResponseResult updateCustomProjectTimes(String staffNumber, Long id, String memberNum, String productCode, Long storeId, String storeName, String productName, String postAndBeautician, String createOperator, String memberName, String memberMobile) {
        //查询默认客户
        String custK3Number = null;
        Map resultMapCust = (Map) storeApi.selectDefaultCust(storeId, 3).getResult();
        if (resultMapCust == null) {
            return ResponseResult.error(new Error(ResponseCodeStockEnum.DEFAULT_CUST_IS_NOT_FIND.getCode(),
                    ResponseCodeStockEnum.DEFAULT_CUST_IS_NOT_FIND.getDesc()));
        } else {
            custK3Number = resultMapCust.get("k3Number").toString();
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
        for (Map stockProduct : stockProductList) {
            if (productCode.equals(stockProduct.get("productCode"))) {
                stockNum = stockNum + Integer.parseInt(stockProduct.get("aveailableNumber").toString());
            }
        }
        if (1 > stockNum) {
            return ResponseResult.error(new Error(ResponseCodeOrderEnum.STOCK_PRODUCT_NOT_ENOUGH.getCode(),
                    ResponseCodeOrderEnum.STOCK_PRODUCT_NOT_ENOUGH.getDesc()));
        }


        Map map = new HashMap();
        map.put("id", id);
        //判断使用次数是否超出总次数
        CustomProjectUser customProjectUser = iOrderDao.selectCustomProjectUserById(map);
        if (customProjectUser.getUseTimes() + 1 > customProjectUser.getTotalTimes()) {
            return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.USE_TIMES_SURPASS_TOTAL_TIMES.getCode(), ResponseCodeExperiencecardEnum.USE_TIMES_SURPASS_TOTAL_TIMES.getDesc()));
        }
        //验证当前时间在有效期范围内
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String uselimit = customProjectUser.getUseLimit();
        String endDateStr = uselimit;
        Date endDate = null;
        try {
            endDate = simpleDateFormat.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean isEffectiveDate = new Date().getTime() > endDate.getTime();
        if (isEffectiveDate) {
            return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.NOW_LIMIT_USE_DATE_ERROR.getCode(), ResponseCodeExperiencecardEnum.NOW_LIMIT_USE_DATE_ERROR.getDesc()));

        }

        // 修改使用次数
        iOrderDao.updateUserCustomUseTimes(map);
        //添加使用记录
        CustomProjectUserRecord customProjectUserRecord = new CustomProjectUserRecord();
        customProjectUserRecord.setProductCode(productCode);
        customProjectUserRecord.setStoreId(storeId);
        customProjectUserRecord.setUseDate(new Date());
        customProjectUserRecord.setStoreName(storeName);
        customProjectUserRecord.setProductName(productName);
        customProjectUserRecord.setBookingBeauticianName(postAndBeautician);
        customProjectUserRecord.setLinkName(memberName);
        customProjectUserRecord.setLinkMobile(memberMobile);
        customProjectUserRecord.setCustomProjectUserId(id);
        //添加使用记录
        iOrderDao.addUseRecord(customProjectUserRecord);
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
        String industryId = ((Map) storeApi.selectStoreById(Long.parseLong(storeId.toString())).getResult()).get("industryID").toString();
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
            responseResultYeJi = dataApi.insertLadderDetailed(ProductTypeEnum.SERVICE.getCode(), Long.parseLong(storeId.toString()), postAndBeauticianArray.toJSONString(), "DZHK" + customProjectUserRecord.getRecordId().toString(), null, 1, null, price, industryId);
        }


        //划卡后的库存影响
        //减库存
        JSONArray jsonArrayXiaoShou = new JSONArray();
        JSONArray jsonArrayQiTa = new JSONArray();
        //根据商品编号查看商品单位
        List<Map> mapProductAllList = (List<Map>) productApi.selectAllProduct().getResult();
        //查询所有的单位列表
        String unitId = null;
        Integer productSubordinate = null;
        for (Map map1 : mapProductAllList) {
            if (map1.get("productCode").toString().equals(productCode)) {
                unitId = map1.get("unitId").toString();
                productSubordinate = Integer.parseInt(map1.get("productSubordinate").toString());
            }
        }

        if (productSubordinate == 0) {
            if (productType == Long.parseLong(ProductTypeEnum.PRODUCT.getCode().toString())) {
                //如果出库的是 实体产品
                JSONObject jsonObjectXiaoShou = new JSONObject();
                jsonObjectXiaoShou.put("productCode", productCode);
                //根据商品编号查看商品的k3Number
                Map productMap = (Map) productApi.selectProductByCode(productCode).getResult();
                if (productMap == null) {
                    productMap = (Map) productApi.selectServiceProductByCode(productCode).getResult();
                }
                jsonObjectXiaoShou.put("productK3Number", productMap.get("k3Number"));
                jsonObjectXiaoShou.put("productName", productName);
                jsonObjectXiaoShou.put("sendNumber", 1);
                jsonObjectXiaoShou.put("unit", unitId);
                //根据单位id查看单位k3的number
                List<Map> mapList1 = (List<Map>) productApi.selectUnitListNoPage().getResult();
                String unitK3Number = null;
                for (Map map2 : mapList1) {
                    if (map2.get("unitId").toString().equals(unitId)) {
                        unitK3Number = map2.get("k3Number").toString();
                    }
                }
                jsonObjectXiaoShou.put("unitK3Number", unitK3Number);
                jsonObjectXiaoShou.put("stock", stockList.get(0).get("stockCode"));
                jsonObjectXiaoShou.put("stockStatus", 1);
                jsonArrayXiaoShou.add(jsonObjectXiaoShou);
            } else if (productType == Long.parseLong(ProductTypeEnum.SERVICE.getCode().toString())) {
                //如果出库的是 护理产品
                JSONObject jsonObjectQiTa = new JSONObject();
                jsonObjectQiTa.put("productCode", productCode);
                //根据商品编号查看商品的k3Number
                Map productMap = (Map) productApi.selectProductByCode(productCode).getResult();
                if (productMap == null) {
                    productMap = (Map) productApi.selectServiceProductByCode(productCode).getResult();
                }
                jsonObjectQiTa.put("productK3Number", productMap.get("k3Number"));
                jsonObjectQiTa.put("productName", productName);
                jsonObjectQiTa.put("sendNumber", 1);
                jsonObjectQiTa.put("unit", unitId);
                //根据单位id查看单位k3的number
                List<Map> mapList1 = (List<Map>) productApi.selectUnitListNoPage().getResult();
                String unitK3Number = null;
                for (Map map2 : mapList1) {
                    if (map2.get("unitId").toString().equals(unitId)) {
                        unitK3Number = map2.get("k3Number").toString();
                    }
                }
                jsonObjectQiTa.put("unitK3Number", unitK3Number);
                jsonObjectQiTa.put("stock", stockList.get(0).get("stockCode"));
                jsonObjectQiTa.put("stockStatus", 1);
                jsonArrayQiTa.add(jsonObjectQiTa);
            }

            //查看门店名称
            Map storeInfo = (Map) storeApi.selectStoreById(Long.parseLong(storeId.toString())).getResult();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = formatter.format(new Date());
            //根据门店 查看门店仓库
            List<Map> mapStockList = (List<Map>) (productApi.selectStockList(3l, Long.parseLong(storeInfo.get("storeId").toString())).getResult());
            String batchNumber = NumberUtils.getRandomOrderNo();

            if (jsonArrayXiaoShou.size() != 0) {
                if (StringUtils.isBlank(staffNumber)) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getCode(),
                            ResponseCodeStockEnum.SUPPLIER_ORG_EMPLOYEES_IS_NULL.getDesc()));
                }
                ResponseResult resultStock1 = productApi.outstorage(staffNumber, batchNumber, mapStockList.get(0).get("stockId").toString(), jsonArrayXiaoShou.toJSONString(), "销售出库", "标准销售出库", dateStr, "人民币", storeInfo.get("name").toString(), storeInfo.get("k3Number").toString(), custK3Number, storeInfo.get("name").toString(), createOperator);
                if (!resultStock1.getResponseStatusType().getMessage().equals("Success") &&
                        resultStock1.getResponseStatusType().getError().getErrorMsg().equals("仓库商品没库存")) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                            ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc()));
                }
                //出库后添加销售出库单号
                CustomProjectUserRecord customProjectUserRecordUpdate = new CustomProjectUserRecord();
                customProjectUserRecordUpdate.setRecordId(customProjectUserRecord.getRecordId());
                customProjectUserRecordUpdate.setOutStorageIdXiaoShou(resultStock1.getResult().toString());
                iOrderDao.updateUseRecord(customProjectUserRecordUpdate);
            }
            if (jsonArrayQiTa.size() != 0) {
                ResponseResult resultStock2 = productApi.outstorage(
                        null,
                        jsonArrayQiTa.toJSONString(),
                        "其他出库",
                        "标准其他出库",
                        dateStr,
                        mapStockList.get(0).get("stockCode").toString(),
                        resultMapCust.get("name").toString(),
                        "GENERAL",
                        OutStorageBusinessTypeEnum.STANDARD_OTHER.getDesc(),
                        "业务组织",
                        storeInfo.get("name").toString(),
                        storeInfo.get("k3Number").toString(),
                        resultMapCust.get("k3Number").toString(),
                        mapStockList.get(0).get("stockId").toString()
                );
                if (!resultStock2.getResponseStatusType().getMessage().equals("Success") &&
                        resultStock2.getResponseStatusType().getError().getErrorMsg().equals("仓库商品没库存")) {
                    return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getCode(),
                            ResponseCodeStockEnum.STOCKPRODUCT_NOT_ALL.getDesc()));
                }
                CustomProjectUserRecord customProjectUserRecordUpdate = new CustomProjectUserRecord();
                customProjectUserRecordUpdate.setRecordId(customProjectUserRecord.getRecordId());
                customProjectUserRecordUpdate.setOutStorageIdQiTa(resultStock2.getResult().toString());
                iOrderDao.updateUseRecord(customProjectUserRecordUpdate);
            }
        } else if (productSubordinate == 1) {
            //如果是第三方 - 直接减少第三方库存
            productApi.updateStockQuantity(productCode, "down");
        }


        if (productType == Long.parseLong(ProductTypeEnum.SERVICE.getCode().toString())) {
            //如何是护理---划卡后添加一个划卡的预约订单
            Order orderAdd = new Order();
            orderAdd.setOrderType(OrderTypeEnum.APPOINTMENT_ORDER.getCode());
            orderAdd.setChannel(OrderChannelEnum.POS_ORDER.getCode());
            orderAdd.setOrderLink(memberName);
            orderAdd.setMobile(memberMobile);
            orderAdd.setCardNumber(memberNum);
            orderAdd.setNurseStore(storeId);
            orderAdd.setOrderStatus(OrderStatusEnum.PAID.getCode());
            String orderNum = "DZHK" + OrderTypeEnum.APPOINTMENT_ORDER.getCode().toString() + NumberUtils.getRandomOrderNo();
            orderAdd.setOrderNumber(orderNum);
            orderAdd.setRecordId("DZHK" + customProjectUserRecord.getRecordId());
            iOrderDao.insertOrder(orderAdd);
            ProductOrder productOrderAdd = new ProductOrder();
            productOrderAdd.setOrderId(orderNum + "_" + NumberUtils.getRandomOrderNoChild());
            productOrderAdd.setOrderNumber(orderNum);
            productOrderAdd.setProductCode(productCode);
            productOrderAdd.setProductName(productName);
            productOrderAdd.setProductNum(1);
            productOrderAdd.setBookingBeauticianIds(postAndBeautician);
            productOrderAdd.setProductTypeId(productType);
            productOrderAdd.setStoreId(storeId);
            productOrderAdd.setSubclassID(subclassID);
            productOrderAdd.setRecordId("DZHK" + customProjectUserRecord.getRecordId());
            Map mapAdd = new HashMap();
            List<ProductOrder> productOrderList = new ArrayList<>();
            productOrderList.add(productOrderAdd);
            mapAdd.put("list", productOrderList);
            iOrderDao.insertProductOrder(mapAdd);
            AppointmentOrder appointmentOrderAdd = new AppointmentOrder();
            appointmentOrderAdd.setOrderNumber(orderNum);
            appointmentOrderAdd.setAppointmentType(OrderTypeEnum.APPOINTMENT_ORDER.getCode().toString());
            appointmentOrderAdd.setAppointmentStatus(AppointmentStatusEnum.PAY.getCode());
            appointmentOrderAdd.setStoreId(storeId);
            appointmentOrderAdd.setSource("1");
            appointmentOrderAdd.setRecordId("DZHK" + customProjectUserRecord.getRecordId());
            iOrderDao.insertAppointmentOrder(appointmentOrderAdd);
        }
        //定制项目划卡成功后增加销量
        productApi.updateProductSales(productCode, Integer.parseInt(productType.toString()), "up");


        if (responseResultYeJi != null && responseResultYeJi.getResponseStatusType().getMessage().equals("Success")) {
            return ResponseResult.success("划卡成功" + responseResultYeJi.getResult());
        }
        return ResponseResult.success("划卡成功");
    }

    @Override
    public ResponseResult selectUseRecordList(int pageNum, int pageSize, Long id) {
        PageHelper.startPage(pageNum, pageSize);

        List<CustomProjectUserRecord> customProjectUserRecordList = iOrderDao.selectUseRecordList(id);

        if (customProjectUserRecordList != null && customProjectUserRecordList.size() > 0) {
            PageInfo pageInfo = new PageInfo(customProjectUserRecordList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeOrderEnum.CUSTOM_USE_RECORD_NULL.getCode(), ResponseCodeOrderEnum.CUSTOM_USE_RECORD_NULL.getDesc()));
    }

    @Override
    public ResponseResult insertOrder(Order order) {
        iOrderDao.insertOrder(order);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult insertProductOrder(ProductOrder productOrder) {
        Map map = new HashMap();
        List<ProductOrder> productOrderList = new ArrayList<>();
        productOrderList.add(productOrder);
        map.put("list", productOrderList);
        iOrderDao.insertProductOrder(map);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult insertAppointmentOrder(AppointmentOrder appointmentOrder) {
        iOrderDao.insertAppointmentOrder(appointmentOrder);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectMemberCashAll(String memberNumber) {
        Map map = new HashMap();
        map.put("cardNumber", memberNumber);
        Map result = new HashMap();
        result.put("memberCashAll", 0);
        //[{"payType":3,"amount":100,"payTypeName":"储值支付","accountType":1,"payTypeCategory":2}]
        List<String> stringList = iOrderDao.selectMemberNumberPayTypeAndAmount(map);
        if (stringList != null && stringList.size() > 0) {
            BigDecimal all = new BigDecimal(0);
            for (String s : stringList) {
                if (StringUtils.isNotBlank(s) && !s.equals("0")) {
                    List<PayTypeAndAmountVO> payTypeAndAmountVOList = JSONObject.parseArray(s, PayTypeAndAmountVO.class);
                    for (PayTypeAndAmountVO payTypeAndAmountVO : payTypeAndAmountVOList) {
                        if (1 == payTypeAndAmountVO.getPayTypeCategory()) {
                            all = all.add(payTypeAndAmountVO.getAmount());
                        }
                    }
                }
            }
            result.put("memberCashAll", all);
        }
        return ResponseResult.success(result);
    }

    @Override
    public ResponseResult auditOrderAmount(String orderList, Integer auditStatus) {
        JSONArray array = JSONArray.parseArray(orderList);

        List<String> listRecharg = new ArrayList();
        List<String> listOrder = new ArrayList();
        for (int i = 0; i < array.size(); i++) {
            if (array.getJSONObject(i).get("orderType").toString().equals(OrderTypeEnum.RECHARGE_ORDER.getCode().toString())) {
                listRecharg.add(array.getJSONObject(i).get("orderNumber").toString());
            } else {
                listOrder.add(array.getJSONObject(i).get("orderNumber").toString());
            }
        }


        String rechargeOrderList = "";
        for (String s : listRecharg) {
            rechargeOrderList = rechargeOrderList + "," + s;
        }


        walletApi.auditOrderAmount(rechargeOrderList, auditStatus);


        Map map = new HashMap();
        if (listOrder.size() == 0) {
            listOrder = null;
        }
        map.put("list", listOrder);
        map.put("auditStatus", auditStatus);
        iOrderDao.batchAudit(map);


        return ResponseResult.success();

    }

    @Override
    public ResponseResult customProjectUserRefuse(Long id, Integer refuseTimes) {
        //查询除对应的购买订单的金额 即支付方式
        CustomProjectUser customProjectUser = new CustomProjectUser();
        customProjectUser.setId(id);
        CustomProjectUser customProjectUserResult = iOrderDao.selectCustomProjectUserAndPayById(customProjectUser);

        //查看定制项目所有用多少个项目次数总和
        customProjectUser.setOrderNum(customProjectUserResult.getOrderNum());
        List<CustomProjectUser> customProjectUserList = iOrderDao.selectCustomProjectUserByOrderNum(customProjectUser);
        Integer projectNumber = 0;
        for (CustomProjectUser projectUser : customProjectUserList) {
            projectNumber = projectNumber + projectUser.getTotalTimes();
        }
        //计算需要返还的金额
        Double returnMoney = (customProjectUserResult.getPayPrice() / projectNumber) * refuseTimes;
        //将返还的金额返回到对应的支付方式金额中
        //查看钱包id
        Long walletId = Long.parseLong(walletApi.selectWalletByCarNum(customProjectUserResult.getCardNumber()).getResult().toString());
        Long accountTypeId = JSON.parseArray(customProjectUserResult.getPayTypeAndAmount()).getJSONObject(0).getLong("accountType");
        walletApi.updateWalletAccount(walletId, accountTypeId, returnMoney);

        //修改对应ID定制项目商品在使用次数
        if (refuseTimes > customProjectUserResult.getTotalTimes() - customProjectUserResult.getUseTimes()) {
            return ResponseResult.error(new Error(ResponseCodeExperiencecardEnum.REFUSE_TIMES_BIGGER_REFUND_TIMES.getCode(), ResponseCodeExperiencecardEnum.REFUSE_TIMES_BIGGER_REFUND_TIMES.getDesc()));
        }

        Map map = new HashMap();
        map.put("id", id);
        map.put("refuseTimes", refuseTimes);
        iOrderDao.updateUserCustomTotalTimes(map);

        //插入退货记录表
        CustomProjectUserRefuse customProjectUserRefuse = new CustomProjectUserRefuse();
        customProjectUserRefuse.setCustomProjectUserId(id);
        customProjectUserRefuse.setRefuseTimes(refuseTimes);
        iOrderDao.addCustomProjectUserRefuse(customProjectUserRefuse);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectCustomProjectUserRefuseList(int pageNum, int pageSize,Long id) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("id", id);
        List<CustomProjectUserRefuse> customProjectUserRefuseList = iOrderDao.selectCustomProjectUserRefuseList(map);
        if (customProjectUserRefuseList != null && customProjectUserRefuseList.size() > 0) {
            PageInfo pageInfo = new PageInfo(customProjectUserRefuseList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeOrderEnum.CUSTOM_REFUSE_RECORD_NULL.getCode(), ResponseCodeOrderEnum.CUSTOM_REFUSE_RECORD_NULL.getDesc()));
    }

    @Override
    public ResponseResult checkAppointmentOrder(String stffNumberList,String storeId) {
        JSONArray jsonArrayStaff = JSONArray.parseArray(stffNumberList);
        //查看所有的预约订单
        Map map = new HashMap();
        map.put("orderType", OrderTypeEnum.APPOINTMENT_ORDER.getCode());
        map.put("storeId", storeId);
        List<ProductOrder> productOrderList = iOrderDao.selectOrderListByTypeAndStoreId(map);
        for (int m = 0; m < jsonArrayStaff.size(); m++) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            for (ProductOrder productOrder : productOrderList) {
                JSONArray jsonArray = JSONArray.parseArray(productOrder.getBookingBeauticianIds());
                for (int i = 0; i < jsonArray.size(); i++) {
                    //[{"beauticianName":"美发师员工001","beauticianId":10746,"nursingDate":"2020-04-15 09:00","duration":30}]
                    try {
                        Date existStartTime = sdf.parse(jsonArray.getJSONObject(i).getString("nursingDate"));
                        Date originalDate1 = jsonArray.getJSONObject(i).getDate("nursingDate");
                        Calendar newTime1 = Calendar.getInstance();
                        newTime1.setTime(originalDate1);
                        newTime1.add(Calendar.MINUTE, jsonArray.getJSONObject(i).getInteger("duration"));//日期加n分
                        Date existEndTime = newTime1.getTime();


                        Date currentStartTime = sdf.parse(jsonArrayStaff.getJSONObject(i).getString("nursingDate"));
                        Date originalDate2 = sdf.parse(jsonArrayStaff.getJSONObject(i).getString("nursingDate"));
                        Calendar newTime2 = Calendar.getInstance();
                        newTime2.setTime(originalDate2);
                        newTime2.add(Calendar.MINUTE, Integer.parseInt(jsonArrayStaff.getJSONObject(i).getString("duration")));//日期加n分
                        Date currentEndTime = newTime2.getTime();


                        if (jsonArray.getJSONObject(i).getString("beauticianId").equals(jsonArrayStaff.getJSONObject(i).getString("beauticianId")) &&
                                ((currentStartTime.getTime() >= existStartTime.getTime() && currentEndTime.getTime() <= existEndTime.getTime()) ||
                                        (currentStartTime.getTime() <= existStartTime.getTime() && currentEndTime.getTime() >= existEndTime.getTime()) ||
                                        currentStartTime.getTime() == existStartTime.getTime() || currentEndTime.getTime() == existEndTime.getTime() ||
                                        (existStartTime.getTime() < currentStartTime.getTime() && currentStartTime.getTime() < existEndTime.getTime()) ||
                                        (existStartTime.getTime() < currentEndTime.getTime() && currentEndTime.getTime() < existEndTime.getTime()))) {

                            return ResponseResult.error(new Error(ResponseCodeOrderEnum.APPOINTMENT_ORDER_TIME_OCCUPY.getCode(),jsonArrayStaff.getJSONObject(i).getString("beauticianName")+":"+ ResponseCodeOrderEnum.APPOINTMENT_ORDER_TIME_OCCUPY.getDesc()));
                        }
                    } catch (ParseException e) {
                    }
                }

            }
        }
        return ResponseResult.success();
    }

    //获取数据中心、用户名和密码
    public HashMap<String, String> getUserNameAndPassword(Long companyId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("dataCentre", null);
        map.put("userName", null);
        map.put("password", null);
        ResponseResult responseResult = storeApi.selectCompanyByID(1L);
        if (responseResult.isSuccess()) {
            HashMap<String, Object> result = (HashMap<String, Object>) responseResult.getResult();
            String dataCentre = String.valueOf(result.get("dataCentre"));
            String dataCentreUserName = String.valueOf(result.get("yewuDataCentreUserName"));
            String dataCentrePassword = String.valueOf(result.get("yewuDataCentrePassword"));
            map.put("dataCentre", dataCentre);
            map.put("userName", dataCentreUserName);
            map.put("password", dataCentrePassword);
        }
        return map;
    }
}
